package apperk.table;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * Provide a utility method to create a
 * {@link javax.swing.table.TableColumnModel} from
 * a {@link TableDisplayModel}.
 */
public class TableColumnModelFactory
{
	/**
	 * Create the {@link TableColumnModel}. Creates {@link TableColumn}
	 * instances with the height and width specified by the
	 * {@link TableDisplayModel}. The {@link TableDisplayModel} is also
	 * set as the {@link javax.swing.table.TableCellRenderer}
	 * for every column.
	 * 
	 * @param displayModel The table display model to use.
	 * @return A new table column model.
	 */
	public static TableColumnModel buildTableColumnModel(
			TableDisplayModel displayModel)
	{
		TableColumnModel columnModel = new DefaultTableColumnModel();

		for(int i = 0; i < displayModel.getColumnCount(); ++i)
		{
			TableColumn column = new TableColumn(i, 
					displayModel.getColumnWidth(i), displayModel, null);
			column.setHeaderValue(displayModel.getColumnHeaderValue(i));
			columnModel.addColumn(column);
		}

		return columnModel;
	}
}
