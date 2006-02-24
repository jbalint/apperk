package apperk.sqlreport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * An implementation of BindingStrategy that creates a temporary table
 * with a column for each parameter.
 * <p/>
 * <font color="red">TODO: not implemented</font>
 * <p/>
 * We might have to provide the parameters in a separate list if
 * we need full type information.
 */
public class TempTableBinder implements BindingStrategy
{
	public void bind(Connection con, PreparedStatement stmt, Map params)
		throws SQLException
	{
		throw new RuntimeException("TempTableBinder not implemented");
	}
}

