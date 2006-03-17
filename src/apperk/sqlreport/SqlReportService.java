package apperk.sqlreport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;

/**
 * A report service implemented in terms of SQL queries.
 */
public class SqlReportService implements ReportService
{
	private static final Logger log = Logger.getLogger(SqlReportService.class);

	protected Map<String, SqlReportDefinition> reports =
		new HashMap<String, SqlReportDefinition>();
	protected DataSource dataSource;
	protected BindingStrategy bindingStrategy;

	/**
	 * Retrieve the report definition.
	 *
	 * @param reportName the report name.
	 * @return the report definition if found, null otherwise.
	 */
	public ReportDefinition getReportDefinition(String reportName)
	{
		return reports.get(reportName);
	}

	/**
	 * Retrieve a list of available reports from this service.
	 *
	 * @return the list of available report names.
	 */
	public List<String> getReportNames()
	{
		return new ArrayList<String>(reports.keySet());
	}

	/**
	 * Execute the report and put the results into a list of rows.
	 *
	 * @param reportName name
	 * @param params params
	 * @return the rows
	 */
	public List<Map<String, Object>>
		execute(String reportName, Map<String, Object> params)
	{
		SqlReportExecutor executor = new SqlReportExecutor(
				reports.get(reportName), params, bindingStrategy);
		return (List<Map<String, Object>>)
			new JdbcTemplate(dataSource).execute(executor, executor);
	}

	//////////////////////////////////////////////////////
	// Inner-class(es)
	//////////////////////////////////////////////////////

	/**
	 * The actual workhorse for the report service. This has two
	 * operations. One to create the prepared statement and bind the
	 * parameters and another to execute the statement and transform
	 * the results.
	 * <p/>
	 * <font color="red">I put this in a separate class to avoid the
	 * few anonymous implementations in the method, but now it seems
	 * like it was more trouble (and duplication) than it was worth
	 * (and what I wanted).</font>
	 * We couldn't have it directly in the service because passing
	 * the service to the jdbc template would require non-threadsafe
	 * parameters to be saved in the report service (could use
	 * thread-locals).
	 */
	protected static class SqlReportExecutor
		implements PreparedStatementCreator, PreparedStatementCallback
	{
		private static final Logger log =
			Logger.getLogger(SqlReportExecutor.class);

		private SqlReportDefinition def;
		private Map<String, Object> params;
		private BindingStrategy bs;

		/**
		 * Create a new executor instance using the given ctor params.
		 *
		 * @param def The report definition to execute.
		 * @param params The input params to bind.
		 * @param bs The BindingStrategy to use.
		 */
		public SqlReportExecutor(SqlReportDefinition def,
				Map<String, Object> params, BindingStrategy bs)
		{
			this.def = def;
			this.params = params;
			this.bs = bs;
		}

		/**
		 * Create a prepared statement for the report. This method
		 * prepares the query and binds the parameters.
		 *
		 * @param con The JDBC connection to prepare and bind to.
		 * @return the PreparedStatement ready to go.
		 */
		public PreparedStatement createPreparedStatement(Connection con)
			throws SQLException
		{
			log.debug("Prepare+bind for report: " + def.getName());
			PreparedStatement pstmt =
				con.prepareStatement(def.getQueryText());
			bs.bind(con, pstmt, params);
			return pstmt;
		}

		/**
		 * Execute the statement and transform the final results. This
		 * tries to co-erce all result types into uniform types.
		 *
		 * @param pstmt The prepared statement ready to go.
		 * @return the list of rows (maps).
		 */
		public Object doInPreparedStatement(PreparedStatement pstmt)
			throws SQLException, DataAccessException
		{
			log.debug("Starting processing of report: " + def.getName());

			List<Map<String, Object>> rows =
				new ArrayList<Map<String, Object>>();
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData md = rs.getMetaData();

			int rnum = 0;

			// column fields
			String name;
			int type;

			for(; rs.next(); rnum++)
			{
				Map<String, Object> r = new HashMap<String, Object>();
				rows.add(r);
				for(int i = 0; i < md.getColumnCount(); ++i)
				{
					name = md.getColumnName(i+1);
					type = md.getColumnType(i+1);

					// TODO: could have better separation/test for this
					switch(type)
					{
						///////////////////////////////
						// Add new types recognition here
						///////////////////////////////
						case Types.DATE:
						case Types.TIMESTAMP:
							r.put(name, rs.getDate(i+1));
							if(log.isTraceEnabled())
								logColumnFill(rnum, name, type, "date",
										rs.getDate(i+1));
							break;

						case Types.DECIMAL:
						case Types.DOUBLE:
						case Types.FLOAT:
						case Types.REAL:
							r.put(name, rs.getDouble(i+1));
							if(log.isTraceEnabled())
								logColumnFill(rnum, name, type, "double",
										rs.getDouble(i+1));
							break;

						case Types.BIGINT:
						case Types.SMALLINT:
						case Types.INTEGER:
						case Types.TINYINT:
							r.put(name, rs.getLong(i+1));
							if(log.isTraceEnabled())
								logColumnFill(rnum, name, type, "long",
										rs.getLong(i+1));
							break;

						case Types.CHAR:
						case Types.LONGVARCHAR:
						case Types.VARCHAR:
							r.put(name, rs.getString(i+1));
							if(log.isTraceEnabled())
								logColumnFill(rnum, name, type, "string",
										rs.getString(i+1));
							break;

						case Types.BOOLEAN:
							r.put(name, rs.getBoolean(i+1));
							if(log.isTraceEnabled())
								logColumnFill(rnum, name, type, "boolean",
										rs.getBoolean(i+1));
							break;

						case Types.NULL:
							r.put(name, null);
							if(log.isTraceEnabled())
								logColumnFill(rnum, name, type, "null", null);
							break;

						default:
							log.warn(
									String.format("Using generic " +
										"getObject() method for field " +
										"'%s' due to unknown SQL type '%d'",
										name, type));
							r.put(name, rs.getObject(i+1));
					}
				}
			}

			log.debug("Process " + rnum +
					" rows for report: " + def.getName());

			return rows;
		}

		/**
		 * Convenience method for logging.
		 *
		 * @param rownum The report row number (0-offset).
		 * @param name The report field name.
		 * @param sqltype The SQL type of the field.
		 * @param restype The result type of the field.
		 * @param value The stringified result value.
		 */
		protected void logColumnFill(int rownum, String name, int sqltype,
				String restype, Object value)
		{
			log.trace(String.format(
						"Column filled " +
						"r='%d',col='%s',sqltype='%d',restype='%s',val='%s'",
						rownum, name, sqltype, restype, value));
		}
	}

	//////////////////////////////////////////////////////
	// Non-interface methods
	//////////////////////////////////////////////////////

	/**
	 * Set the reports available in this service.
	 *
	 * @param reportList The map of report name to report def.
	 */
	public void setReports(List<SqlReportDefinition> reportList)
	{
		for(SqlReportDefinition rep : reportList)
		{
			this.reports.put(rep.getName(), rep);
		}
	}

	/**
	 * Set the data source to use for querying.
	 *
	 * @param dataSource The data source.
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	/**
	 * Set the binding strategy implementation for this
	 * report backend.
	 *
	 * @param bindingStrategy The bindingStrategy to use.
	 */
	public void setBindingStrategy(BindingStrategy bindingStrategy)
	{
		this.bindingStrategy = bindingStrategy;
	}
}

