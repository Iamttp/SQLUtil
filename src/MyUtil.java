import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
//import javax.swing.table.JTableHeader;
//import javax.swing.table.TableColumn;
//import java.util.Enumeration;

public class MyUtil {
    public static void Message(String cellValue) {
        JOptionPane.showMessageDialog(null, cellValue, "", JOptionPane.PLAIN_MESSAGE);
    }

//    public static void FitTableColumns(JTable myTable) {
//        JTableHeader header = myTable.getTableHeader();
//        int rowCount = myTable.getRowCount();
//
//        Enumeration columns = myTable.getColumnModel().getColumns();
//        while (columns.hasMoreElements()) {
//            TableColumn column = (TableColumn) columns.nextElement();
//            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
//            int width = (int) myTable.getTableHeader().getDefaultRenderer()
//                    .getTableCellRendererComponent(myTable, column.getIdentifier()
//                            , false, false, -1, col).getPreferredSize().getWidth();
//            for (int row = 0; row < rowCount; row++) {
//                int preferedWidth = (int) myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
//                        myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
//                width = Math.max(width, preferedWidth);
//            }
//            header.setResizingColumn(column); // 此行很重要
//            column.setWidth(width + myTable.getIntercellSpacing().width);
//        }
//    }

    static String SELECT = "SELECT ";
    static String FROM = "FROM";
    static String WHERE = "WHERE ";
    static String PrintStr = "P.";
    static List<String> operatorStr = new ArrayList<String>() {{
        add(">");
        add("<");
        add("=");
    }};

    public static String getRes(DefaultTableModel tableModel) {
        StringBuilder res = new StringBuilder();
        // 即SELECT后面的元素
        ArrayList<String> printItem = new ArrayList<>();
        // WHERE后面的字符串 V
        ArrayList<String> equelItem = new ArrayList<>();
        for (int i = 1; i < tableModel.getColumnCount(); i++) {
            String cellValue = (String) tableModel.getValueAt(1, i);

            if (cellValue != null && cellValue.length() != 0) {
                if (cellValue.equals(PrintStr)) {
                    printItem.add((String) tableModel.getValueAt(0, i));
                    continue;
                }

                // > <
                if (operatorStr.contains(cellValue.substring(0, 1))) {
                    equelItem.add((String) tableModel.getValueAt(0, i)
                            + (String) tableModel.getValueAt(1, i));
                    continue;
                }

                // =
                equelItem.add((String) tableModel.getValueAt(0, i) + "="
                        + (String) tableModel.getValueAt(1, i));
            }
        }

        res.append(SELECT);
        if (printItem.size() != 0)
            for (int i = 0; i < printItem.size(); i++) {
                if (i != printItem.size() - 1)
                    res.append(printItem.get(i)).append(",");
                else
                    res.append(printItem.get(i)).append("\n");
            }
        else {
            res.append("*\n");
        }
        res.append(FROM).append((String) tableModel.getValueAt(0, 0)).append("\n");

        if (equelItem.size() != 0) {
            res.append(WHERE);
            for (int i = 0; i < equelItem.size(); i++) {
                if (i != equelItem.size() - 1)
                    res.append(equelItem.get(i)).append(" AND ");
                else
                    res.append(equelItem.get(i)).append("\n");
            }
        }
        return res.toString();
    }
}