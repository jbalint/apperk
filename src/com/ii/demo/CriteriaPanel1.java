/*
 * Created by JFormDesigner on Tue Jan 03 17:40:33 EST 2006
 */

package com.ii.demo;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Jess Balint
 */
public class CriteriaPanel1 extends JPanel {
	public CriteriaPanel1() {
		initComponents();
	}

	public void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		comboEntity = new JComboBox();
		label2 = new JLabel();
		comboProperty = new JComboBox();
		label3 = new JLabel();
		comboOperator = new JComboBox();
		label4 = new JLabel();
		scrollPane1 = new JScrollPane();
		list1 = new JList();
		panel1 = new JPanel();
		btnReset = new JButton();
		btnAdd = new JButton();
		separator1 = new JSeparator();
		label5 = new JLabel();
		scrollPane2 = new JScrollPane();
		list2 = new JList();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setBorder(Borders.TABBED_DIALOG_BORDER);
		setLayout(new FormLayout(
			new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC
			},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec(RowSpec.FILL, Sizes.DEFAULT, FormSpec.NO_GROW),
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec(RowSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW)
			}));

		//---- label1 ----
		label1.setText("Entity");
		add(label1, cc.xy(1, 1));

		//---- comboEntity ----
		comboEntity.setPreferredSize(new Dimension(150, 20));
		add(comboEntity, cc.xy(3, 1));

		//---- label2 ----
		label2.setText("Property");
		add(label2, cc.xy(1, 3));
		add(comboProperty, cc.xy(3, 3));

		//---- label3 ----
		label3.setText("Operator");
		add(label3, cc.xy(1, 5));
		add(comboOperator, cc.xy(3, 5));

		//---- label4 ----
		label4.setText("Parameters");
		add(label4, new CellConstraints(1, 7, 1, 1, CellConstraints.DEFAULT, CellConstraints.TOP, new Insets( 2, 0, 0, 0)));

		//======== scrollPane1 ========
		{
			scrollPane1.setPreferredSize(new Dimension(33, 75));
			scrollPane1.setViewportView(list1);
		}
		add(scrollPane1, cc.xy(3, 7));

		//======== panel1 ========
		{
			panel1.setLayout(new FormLayout(
				new ColumnSpec[] {
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC
				},
				RowSpec.decodeSpecs("default")));
			
			//---- btnReset ----
			btnReset.setText("Reset");
			btnReset.setPreferredSize(new Dimension(75, 22));
			panel1.add(btnReset, cc.xy(1, 1));
			
			//---- btnAdd ----
			btnAdd.setText("Add");
			btnAdd.setPreferredSize(new Dimension(75, 22));
			panel1.add(btnAdd, cc.xy(3, 1));
		}
		add(panel1, cc.xywh(1, 9, 3, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));
		add(separator1, cc.xywh(1, 11, 3, 1));

		//---- label5 ----
		label5.setText("Expressions");
		add(label5, new CellConstraints(1, 13, 1, 1, CellConstraints.DEFAULT, CellConstraints.TOP, new Insets( 2, 0, 0, 0)));

		//======== scrollPane2 ========
		{
			scrollPane2.setViewportView(list2);
		}
		add(scrollPane2, cc.xy(3, 13));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	public JComboBox comboEntity;
	private JLabel label2;
	public JComboBox comboProperty;
	private JLabel label3;
	public JComboBox comboOperator;
	private JLabel label4;
	private JScrollPane scrollPane1;
	public JList list1;
	private JPanel panel1;
	public JButton btnReset;
	public JButton btnAdd;
	private JSeparator separator1;
	private JLabel label5;
	private JScrollPane scrollPane2;
	public JList list2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
