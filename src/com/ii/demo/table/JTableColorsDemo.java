package com.ii.demo.table;

 import java.awt.*;
 import javax.swing.*;
 import javax.swing.table.*;

 /**
  * Some shitty code from delvinj in ##swing (irc.freenode.net). This is
  * probably the worst possible way to accomplish this.
  * <p/>
  * Also, the selection colors are bad.
  * <p/>
  * Kept around for debugging and/or illustration purposes only.
  */

 public class JTableColorsDemo extends JFrame
 {
     JTable table;


     JTableColorsDemo()
     {
         super();
         table = new JTable()
         {
             public Component prepareRenderer(TableCellRenderer renderer,
                     int row, int column)
             {
                 Component c = super.prepareRenderer(renderer, row, column);
                 c.setBackground((row % 2 == 0 && !isCellSelected(row,column)) ?
                         Color.LIGHT_GRAY :
                             getBackground());
                 return c;
             }
         };


         TableModel model = new AbstractTableModel()
         {
             public int getColumnCount()
             {
                 return 4;
             }
             public int getRowCount()
             {
                 return 64;
             }
             public Object getValueAt(int rowIndex, int columnIndex)
             {
                 return Integer.toString(columnIndex << rowIndex);
             }
         };


         table.setModel(model);


         Container cp = getContentPane();
         cp.add(new JScrollPane(table),BorderLayout.CENTER);
         setSize(420,420);
         setLocationRelativeTo(null);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
     }


     public static void main(String[] args)
     {
         new JTableColorsDemo().setVisible(true);
     }


 }