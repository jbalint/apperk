/*
 * Created by JFormDesigner on Wed Mar 22 15:53:22 EST 2006
 */

package com.ii.demo.sqlreport;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Jess Balint
 */
public class Sample1GUI extends JSplitPane {
	public Sample1GUI() {
		initComponents();
	}

	public void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextArea();
		panel1 = new JPanel();
		label1 = new JLabel();
		comboName = new JComboBox();
		label2 = new JLabel();
		scrollPane2 = new JScrollPane();
		txtDescription = new JTextArea();
		panel2 = new JPanel();
		btnRun = new JButton();
		btnExit = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setBorder(Borders.createEmptyBorder("2dlu, 2dlu, 0dlu, 2dlu"));
		setDividerSize(0);

		//======== scrollPane1 ========
		{
			scrollPane1.setPreferredSize(new Dimension(280, 120));

			//---- textArea1 ----
			textArea1.setText("This demo presents a simple test interface to the SQL report library. You can select a report and run/test the report results and the input/output interface and validations.\n\nThe database used for the reports is the Eclipse BIRT \"Classic Models\" demo database.");
			textArea1.setWrapStyleWord(true);
			textArea1.setEditable(false);
			textArea1.setLineWrap(true);
			textArea1.setBorder(new EmptyBorder(0, 1, 0, 0));
			scrollPane1.setViewportView(textArea1);
		}
		setTopComponent(scrollPane1);

		//======== panel1 ========
		{
			panel1.setBorder(Borders.TABBED_DIALOG_BORDER);
			panel1.setLayout(new FormLayout(
				new ColumnSpec[] {
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
					new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW)
				},
				new RowSpec[] {
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.LINE_GAP_ROWSPEC,
					new RowSpec(RowSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
					FormFactory.LINE_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC
				}));

			//---- label1 ----
			label1.setText("Report Name");
			panel1.add(label1, cc.xy(1, 1));

			//---- comboName ----
			comboName.setPreferredSize(new Dimension(150, 22));
			panel1.add(comboName, cc.xy(3, 1));

			//---- label2 ----
			label2.setText("Report Description");
			panel1.add(label2, new CellConstraints(1, 3, 1, 1, CellConstraints.DEFAULT, CellConstraints.TOP, new Insets( 3, 0, 0, 0)));

			//======== scrollPane2 ========
			{
				scrollPane2.setPreferredSize(new Dimension(4, 100));

				//---- txtDescription ----
				txtDescription.setEditable(false);
				txtDescription.setText("<description unavailable>");
				scrollPane2.setViewportView(txtDescription);
			}
			panel1.add(scrollPane2, cc.xy(3, 3));

			//======== panel2 ========
			{
				panel2.setLayout(new FormLayout(
					new ColumnSpec[] {
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC
					},
					RowSpec.decodeSpecs("default")));

				//---- btnRun ----
				btnRun.setText("Run");
				btnRun.setPreferredSize(new Dimension(75, 25));
				panel2.add(btnRun, cc.xy(1, 1));

				//---- btnExit ----
				btnExit.setText("Exit");
				btnExit.setPreferredSize(new Dimension(75, 25));
				panel2.add(btnExit, cc.xy(3, 1));
			}
			panel1.add(panel2, cc.xywh(1, 5, 3, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));
		}
		setBottomComponent(panel1);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane1;
	private JTextArea textArea1;
	private JPanel panel1;
	private JLabel label1;
	public JComboBox comboName;
	private JLabel label2;
	private JScrollPane scrollPane2;
	public JTextArea txtDescription;
	private JPanel panel2;
	public JButton btnRun;
	public JButton btnExit;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
