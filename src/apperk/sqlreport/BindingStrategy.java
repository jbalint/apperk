package apperk.sqlreport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * The interface for parameter binding.
 */
public interface BindingStrategy
{
	/**
	 * Bind the given parameters for the statement. It is up to the
	 * implementation as to whether this is done directly on the
	 * PreparedStatement or via some other statements issued against
	 * the Connection.
	 *
	 * @param con The database connection used for this query.
	 * @param stmt The prepared statement of the query.
	 * @param params The parameters to be bound.
	 * @throws SQLException If there is a JDBC error.
	 */
	void bind(Connection con, PreparedStatement stmt, Map<String, Object> params)
		throws SQLException;
}

