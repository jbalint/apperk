package apperk.sqlreport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * An implementation of BindingStrategy that uses session variables.
 * Currently this is only known to work as a MySQL-specific feature.
 */
public class SessionVariableBinder implements BindingStrategy
{
	/**
	 * Bind the given parameters by creating session variables.
	 *
	 * @param con The connection used to execute the variable creation
	 * 			statements.
	 * @param stmt Not used in this implementation.
	 * @param params to bind.
	 * @throws SQLException if there is any error
	 */
	public void bind(Connection con, PreparedStatement stmt, Map params)
		throws SQLException
	{
		for(Object pobj : params.entrySet())
		{
			Map.Entry<String, Object> param = (Map.Entry<String, Object>)pobj;
			String sql = String.format("set @%s = ?", param.getKey());
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setObject(1, param.getValue());
			ps.executeUpdate();
			ps.close();
		}
	}
}

