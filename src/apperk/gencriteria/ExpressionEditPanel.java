/*
 * Created by JFormDesigner on Mon Dec 05 16:05:26 EST 2005
 */

package apperk.gencriteria;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Jess Balint
 */
public class ExpressionEditPanel extends JPanel {
	public ExpressionEditPanel() {
		initComponents();
	}

	public void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		scrollPane1 = new JScrollPane();
		listFields = new JList();
		label2 = new JLabel();
		comboOperator = new JComboBox();
		label3 = new JLabel();
		panelValues = new JPanel();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setBorder(Borders.TABBED_DIALOG_BORDER);
		setLayout(new FormLayout(
			new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW)
			},
			new RowSpec[] {
				new RowSpec(RowSpec.FILL, Sizes.DEFAULT, FormSpec.NO_GROW),
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec(RowSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW)
			}));

		//---- label1 ----
		label1.setText("Field");
		add(label1, cc.xywh(1, 1, 1, 1, CellConstraints.DEFAULT, CellConstraints.TOP));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(listFields);
		}
		add(scrollPane1, cc.xy(3, 1));

		//---- label2 ----
		label2.setText("Operator");
		add(label2, cc.xy(1, 3));
		add(comboOperator, cc.xy(3, 3));

		//---- label3 ----
		label3.setText("Value(s)");
		add(label3, cc.xywh(1, 5, 1, 1, CellConstraints.DEFAULT, CellConstraints.TOP));

		//======== panelValues ========
		{
			panelValues.setLayout(new BorderLayout());
		}
		add(panelValues, cc.xy(3, 5));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JScrollPane scrollPane1;
	public JList listFields;
	private JLabel label2;
	public JComboBox comboOperator;
	private JLabel label3;
	public JPanel panelValues;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
