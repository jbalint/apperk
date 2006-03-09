package apperk.sqlreport;

import java.util.List;
import java.util.Map;

/**
 * The interface to a report backend.
 */
public interface ReportService
{
	/**
	 * Provides the report definition for a named report. This includes
	 * all information necessary to gather input data and display the
	 * results for the report.
	 *
	 * @param reportName The report name to retrieve param defs for.
	 * @return The {@link ReportDefinition} for the named report.
	 */
	ReportDefinition getReportDefinition(String reportName);

	/**
	 * Provides a list of reports available from this service.
	 *
	 * @return The names of available reports.
	 */
	List<String> getReportNames();

	/**
	 * Execute the report with the given parameters.
	 *
	 * @param reportName The report to execute.
	 * @param params The parameters for the report.
	 * @return A list of rows representing the results of the report.
	 */
	List<Map<String, Object>>
		execute(String reportName, Map<String, Object> params);
}

