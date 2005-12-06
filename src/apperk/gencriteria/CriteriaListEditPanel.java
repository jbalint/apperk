/*
 * Created by JFormDesigner on Mon Dec 05 16:06:08 EST 2005
 */

package apperk.gencriteria;

import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Jess Balint
 */
public class CriteriaListEditPanel extends JPanel {
	public CriteriaListEditPanel() {
		initComponents();
	}

	public void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label2 = new JLabel();
		button2 = new JButton();
		label3 = new JLabel();
		btnAddExpression = new JButton();
		scrollPane1 = new JScrollPane();
		tblCriteriaList = new JTable();
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
				FormFactory.DEFAULT_ROWSPEC
			}));

		//---- label2 ----
		label2.setText("text");
		add(label2, cc.xy(1, 1));

		//---- button2 ----
		button2.setText("text");
		add(button2, cc.xy(3, 1));

		//---- label3 ----
		label3.setText("text");
		add(label3, cc.xy(1, 3));

		//---- btnAddExpression ----
		btnAddExpression.setText("Add...");
		add(btnAddExpression, cc.xy(3, 3));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(tblCriteriaList);
		}
		add(scrollPane1, cc.xywh(1, 5, 3, 1));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label2;
	private JButton button2;
	private JLabel label3;
	public JButton btnAddExpression;
	private JScrollPane scrollPane1;
	public JTable tblCriteriaList;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
