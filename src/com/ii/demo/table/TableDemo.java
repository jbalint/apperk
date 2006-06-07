package com.ii.demo.table;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import apperk.table.AbstractDisplayableTableModel;
import apperk.table.TableColumnModelFactory;

/**
 * Our standard kind of table model implementation. This extends the abstract
 * displayable table model so we can include so display data in it.
 */
class DemoTableModel extends AbstractDisplayableTableModel
{
	private static final String[] columnNames = {"Name", "Address", "Deleted?", "State", "Zip Code" };
	private static final int[] columnWidths = {50, 80, 30, 20, 40};
	
	private static final int BOOLEAN_COLUMN = 2;
	
	List<List<Object>> data = new ArrayList<List<Object>>();

	public DemoTableModel() {
		data.add(Arrays.asList(new Object[] {"Jess Balint", "959 W Armitage Ave", false, "IL", "60614"}));
		data.add(Arrays.asList(new Object[] {"Jenny Jones", "959 W Armitage Ave", false, "IL", "60614"}));
		data.add(Arrays.asList(new Object[] {"Frank Roberts", "959 W Armitage Ave", false, "IL", "60614"}));
		data.add(Arrays.asList(new Object[] {"Randy Roberts", "959 W Armitage Ave", false, "IL", "60614"}));
		data.add(Arrays.asList(new Object[] {"Fondue", "959 W Armitage Ave", false, "IL", "60614"}));
		data.add(Arrays.asList(new Object[] {"Jess Balint", "959 W Armitage Ave", false, "IL", "60614"}));
		data.add(Arrays.asList(new Object[] {"Jess Balint", "959 W Armitage Ave", false, "IL", "60614"}));
	}

	public int getRowCount() {
		return data.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(columnIndex != BOOLEAN_COLUMN)
			return;
		data.get(rowIndex).set(columnIndex, aValue);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == BOOLEAN_COLUMN;
	}

	protected Component getTableCellRendererComponent(Component component,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if(((String)data.get(row).get(0)).startsWith("F")) {
			if(isSelected) {
				component.setBackground(Color.RED);
				component.setForeground(Color.WHITE);
			} else {
				component.setBackground(Color.WHITE);
				component.setForeground(Color.RED);
			}
		}
		return component;
	}

	protected String[] getColumnNames() {
		return columnNames;
	}

	protected int[] getColumnWidths() {
		return columnWidths;
	}
}

public class TableDemo {
	JFrame frame = new JFrame("Table Demo");
	
	public TableDemo() {
		DemoTableModel tableModel = new DemoTableModel();
		JTable table = new JTable(tableModel, 
				TableColumnModelFactory.buildTableColumnModel(tableModel));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(new JScrollPane(table));
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new TableDemo();
	}

}
