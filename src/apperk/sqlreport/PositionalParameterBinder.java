package apperk.sqlreport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * An implementation of BindingStrategy that maps the named parameters
 * to positional parameters. This the farthest from convenient because
 * it requires a query that is different from the one used for
 * testing/developing.
 * <p/>
 * <font color="red">TODO: not implemented</font>
 * <p/>
 * We will have to provide a mapping of num-&gt;name.
 */
public class PositionalParameterBinder implements BindingStrategy
{
	public void bind(Connection con, PreparedStatement stmt, Map params)
		throws SQLException
	{
		throw new RuntimeException(
				"PositionalParameterBinder not implemented");
	}
}

