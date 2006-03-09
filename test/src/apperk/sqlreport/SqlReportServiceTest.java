package apperk.sqlreport;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.test
	.AbstractTransactionalDataSourceSpringContextTests;

public class SqlReportServiceTest
	extends AbstractTransactionalDataSourceSpringContextTests
{
	protected ReportService reportService;

	protected String[] getConfigLocations()
	{
		return new String[] { "classpath:mysql-datasource.xml",
			"classpath:sqlreports1.xml" };
	}

	public void onSetUpBeforeTransaction()
	{
		reportService = (ReportService)
			applicationContext.getBean("reportService");
	}

	public void testSimpleReport()
	{
		String reportName = "Test Report 1";
		ReportDefinition simpleReport =
			reportService.getReportDefinition(reportName);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bal", 100);
		List<Map<String, Object>> result = reportService
			.execute(reportName, params);
		System.out.println("Result: " + result);
		assertEquals("Should have 1 record w/balance >100", 1, result.size());
	}
}

