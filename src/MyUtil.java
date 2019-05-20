import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class MyUtil {
    public static void Message(String cellValue) {
        JOptionPane.showMessageDialog(null, cellValue, "", JOptionPane.PLAIN_MESSAGE);
    }

    private static final String SELECT = "SELECT ";
    private static final String FROM = "FROM";
    private static final String WHERE = "WHERE ";
    private static final String PrintStr = "P.";
    private static final List<String> operatorStr = new ArrayList<String>() {{
        add(">");
        add("<");
        add("=");
    }};

    static String getRes(DefaultTableModel tableModel) {
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
                            + tableModel.getValueAt(1, i));
                    continue;
                }

                // =
                equelItem.add(tableModel.getValueAt(0, i) + "="
                        + tableModel.getValueAt(1, i));
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