package apperk.table;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

/**
 * An abstract class for implementing {@link javax.swing.table.TableModel}
 * and {@link apperk.table.TableDisplayModel}.
 * <p/>
 * This class provides distinct advantages over
 * {@link javax.swing.table.TableModel}. It allows access to:
 * <ul>
 * <li>Column widths</li>
 * <li>Cell rendering</li>
 * </ul>
 * <p/>
 * An example use is shown below. This example will build a column model
 * using the widths, headers and renderers retrieved from the table
 * display model.
 * <p/>
 * <code>
 * MyDisplayableTableModel tableModel = new MyDisplayableTableModel(someData);
 * TableColumnModel columnModel = 
 * 		TableColumnModelFactory.buildTableColumnModel(tableModel);
 * JTable jtable = new JTable(tableModel, columnModel);
 * panel.add(new JScrollPane(jtable));
 * </code>
 */
public abstract class AbstractDisplayableTableModel extends AbstractTableModel
		implements TableDisplayModel {
	private static final int DEFAULT_COLUMN_WIDTH = 75;

	/**
	 * Implement the {@link javax.swing.table.TableCellRenderer} interface
	 * and retrieve the renderer component.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component dfl = table.getDefaultRenderer(value.getClass())
			.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
		setDefaultColors(dfl, isSelected);
		return getTableCellRendererComponent(dfl, isSelected,
				hasFocus, row, column);
	}

	/**
	 * A default implementation that simply returns the default renderer
	 * component.
	 * 
	 * @param dfltComponent The default component used to render the cell.
	 * @param isSelected Whether the cell is selected. This is important
	 * 		to determine the foreground/background colors. The colors should
	 * 		be inverted or otherwise different from the non-selected colors.
	 * @param hasFocus Whether the cell is focused.
	 * @param row The row number being rendered.
	 * @param column The column number being rendered.
	 * @return A component to draw the cell.
	 */
	protected Component getTableCellRendererComponent(
			Component dfltComponent, boolean isSelected, 
			boolean hasFocus, int row, int column) {
		return dfltComponent;
	}

	/**
	 * A utility method to set the colors that would be used on the
	 * component by default.
	 * 
	 * @param component The component to apply fg/bg colors to.
	 * @param isSelected Whether this is being drawn selected.
	 */
	protected void setDefaultColors(Component component, boolean isSelected) {
		UIDefaults defaults = UIManager.getDefaults();
		if(isSelected) {
			component.setForeground(defaults.getColor("Table.selectionForeground"));
			component.setBackground(defaults.getColor("Table.selectionBackground"));
		} else {
			component.setForeground(defaults.getColor("Table.foreground"));
			component.setBackground(defaults.getColor("Table.background"));
		}
	}

	/**
	 * Retrieve the column count from the column names array.
	 */
	public int getColumnCount() {
		return getColumnNames().length;
	}

	/**
	 * Expose the header value as the column name.
	 */
	public Object getColumnHeaderValue(int columnIndex) {
		return getColumnNames()[columnIndex];
	}

	/**
	 * Retrieve the column width from the column widths array.
	 */
	public int getColumnWidth(int columnIndex) {
		int[] widths = getColumnWidths();
		if(widths == null)
			return DEFAULT_COLUMN_WIDTH;
		else
			return widths[columnIndex];
	}

	protected abstract String[] getColumnNames();

	/**
	 * Provide the column widths. Defaults to 'null' which uses the
	 * value of {@link #DEFAULT_COLUMN_WIDTH}.
	 * @return The array of column widths.
	 */
	protected int[] getColumnWidths() {
		return null;
	}

	/**
	 * Provides a better (than {@link AbstractTableModel}) default
	 * implementation that retrieves the class of the value from
	 * the first row in the table model.
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(getRowCount() == 0)
			return Object.class;
		Object o = getValueAt(0, columnIndex);
		if(o == null)
			return null;
		else
			return o.getClass();
	}
}
