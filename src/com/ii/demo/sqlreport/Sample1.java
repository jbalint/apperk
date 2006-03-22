package com.ii.demo.sqlreport;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import apperk.sqlreport.ReportService;

import javax.swing.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * A demo application to show off the SQL report library.
 */
public class Sample1
{
	public static void main(String[] args)
	{
		String[] ctxFiles = new String[] {
				"com/ii/demo/sqlreport/reportDefs.xml",
				"mysql-datasource.xml" };
		ApplicationContext ctx = new ClassPathXmlApplicationContext(ctxFiles);
		//
		ReportService reportService = (ReportService)
				ctx.getBean("reportService");
		List<String> reportNames = reportService.getReportNames();

		Sample1GUI panel = new Sample1GUI();
		for(String n : reportNames)
			panel.comboName.addItem(n);
		panel.comboName.setSelectedIndex(-1);

		final JFrame f = new JFrame("SQL Report Demo");
		f.setContentPane(panel);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.btnExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				f.dispose();
			}
		});

		f.pack();
		f.setVisible(true);
	}
}
