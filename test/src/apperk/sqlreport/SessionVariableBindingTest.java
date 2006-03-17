package apperk.sqlreport;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.test
	.AbstractTransactionalDataSourceSpringContextTests;

/**
 * Tests for the SessionVariableBinder class.
 * <p/>
 * <font color="red">TODO: we should have a test that
 * tests a non-bound session variable.</font> Due to
 * our current use of a pooled connection, this is not possible because
 * the first test will set it and we can't clear it.
 * <p/>
 * Also, the session variables are non-transactional which means
 * a rollback won't clear it.
 * <p/>
 * Maybe we should just not be lazy and set it to null at the end. That
 * should have the same effect. OR use a DriverManagerDataSource instead.
 */
public class SessionVariableBindingTest
	extends AbstractTransactionalDataSourceSpringContextTests
{
	protected static final String SESSION_VAR_NAME = "mySessVar";
	protected static final String SESSION_PARAM_VALUE = "sOmEpArAmVaLuE";
	protected static final String REPORT_SQL =
		"select 'SUCCESS' from (select 1) as x " +
		"where '" + SESSION_PARAM_VALUE + "' = @" + SESSION_VAR_NAME;

	protected String[] getConfigLocations()
	{
		return new String[] { "classpath:mysql-datasource.xml" };
	}

	/**
	 * Test binding a variable and a proper result from running the
	 * statement.
	 */
	public void testBindingNormalUse()
	{
		ConnectionCallback test = new ConnectionCallback()
		{
			public Object doInConnection(Connection con)
				throws SQLException
			{
				PreparedStatement repStmt = con.prepareStatement(REPORT_SQL);

				Map<String, Object> params = new HashMap<String, Object>();
				params.put(SESSION_VAR_NAME, SESSION_PARAM_VALUE);

				BindingStrategy bind = new SessionVariableBinder();
				bind.bind(con, repStmt, params);

				// should return the correct result
				ResultSet rs = repStmt.executeQuery();
				rs.next();
				assertEquals("SUCCESS", rs.getString(1));
				rs.close();
				repStmt.close();
				return null;
			}
		};
		jdbcTemplate.execute(test);
	}

	/**
	 * Test binding a variable from value that will not match any rows.
	 */
	public void testBindingNoMatch()
	{
		ConnectionCallback test = new ConnectionCallback()
		{
			public Object doInConnection(Connection con)
				throws SQLException
			{
				PreparedStatement repStmt = con.prepareStatement(REPORT_SQL);

				Map<String, Object> params = new HashMap<String, Object>();
				params.put(SESSION_VAR_NAME, "SOME_OTHER_RANDOM_VALUE");

				BindingStrategy bind = new SessionVariableBinder();
				bind.bind(con, repStmt, params);

				// should return no results
				ResultSet rs = repStmt.executeQuery();
				assertFalse(rs.next());
				rs.close();
				repStmt.close();
				return null;
			}
		};
		jdbcTemplate.execute(test);
	}
}

