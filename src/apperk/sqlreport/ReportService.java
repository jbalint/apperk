package apperk.sqlreport;

import java.util.List;

/**
 * The interface to the SQL report backend.
 */
public interface ReportService
{
	/**
	 * Provides access to parameter lists for reports.
	 *
	 * @param reportName The report name to retrieve param defs for.
	 * @return The {@link InputParameterDefinition}s for the named report.
	 */
	List<InputParameterDefinition> getParameters(String reportName);
}

