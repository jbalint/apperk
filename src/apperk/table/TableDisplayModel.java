package apperk.table;

import javax.swing.table.TableCellRenderer;

/**
 * An interface for exposing display parameters. Can be used to construct
 * a {@link javax.swing.table.TableColumnModel} for a JTable.
 * <p/>
 * <font color="red">TODO</font>: Add TableCellEditor support.
 */
public interface TableDisplayModel extends TableCellRenderer
{
	/**
	 * Provide the column count.
	 * 
	 * @return The column count.
	 */
	int getColumnCount();

	/**
	 * Used to set the width of a given column when creating a
	 * {@link javax.swing.table.TableColumn}.
	 * 
	 * @param columnIndex The column index to retrieve the width for.
	 * @return The desired width of the given column.
	 */
	int getColumnWidth(int columnIndex);

	/**
	 * Used to set the header value of a given column when creating
	 * a {@link javax.swing.table.TableColumn}.
	 * 
	 * @param columnIndex The column to obtain a header value for.
	 * @return The header value of the given column.
	 */
	Object getColumnHeaderValue(int columnIndex);
}
