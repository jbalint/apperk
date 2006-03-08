package apperk.sqlreport;

import java.util.List;

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
}

