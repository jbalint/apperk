package apperk.sqlreport;

/**
 * A SQL-specific report definition.
 * <p/>
 * <font color="red">TODO: add some type of init method for validating the
 * params. Implement Spring's InitializingBean?
 * <br/>
 * TODO: Add support for Spring resource or URLs</font>
 */
public class SqlReportDefinition extends ReportDefinition
{
	private String queryText;

	/**
	 * The SQL query text used to generate the report. If not specified,
	 * the query resource needs to be given.
	 *
	 * @return the query text
	 */
	public String getQueryText()
	{
		return queryText;
	}

	/**
	 * Set the query text.
	 *
	 * @param queryText The new query text.
	 */
	public void setQueryText(String queryText)
	{
		this.queryText = queryText;
	}
}

