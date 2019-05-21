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

    // 完成最终的拼接
    private static StringBuilder getResStr(ArrayList<String> formItem, ArrayList<String> printItem, ArrayList<String> equelItem) {
        StringBuilder res = new StringBuilder();
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

        res.append(FROM);
        for (int i = 0; i < formItem.size(); i++) {
            if (i != formItem.size() - 1)
                res.append(formItem.get(i)).append(",");
            else
                res.append(formItem.get(i)).append("\n");
        }

        if (equelItem.size() != 0) {
            res.append(WHERE);
            for (int i = 0; i < equelItem.size(); i++) {
                if (i != equelItem.size() - 1)
                    res.append(equelItem.get(i)).append(" AND ");
                else
                    res.append(equelItem.get(i));
            }
        }
        return res;
    }

    static String getRes(DefaultTableModel tableModel) {
        StringBuilder res;
        // 即SELECT后面的元素
        ArrayList<String> printItem = new ArrayList<>();
        // WHERE后面的字符串 V
        ArrayList<String> equelItem = new ArrayList<>();
        // FROM后面的字符串
        ArrayList<String> formItem = new ArrayList<>();

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
        formItem.add((String) tableModel.getValueAt(0, 0));
        res = getResStr(formItem, printItem, equelItem);
        return res.toString();
    }

    private static final List<String> XYZStr = new ArrayList<String>() {{
        add("_X");
        add("_Y");
        add("_Z");
    }};
    private static final List<String> XYZStr2 = new ArrayList<String>() {{
        add("P._X");
        add("P._Y");
        add("P._Z");
    }};

    static String getResForMul(ArrayList<String[]> arrayLists) {
        StringBuilder res;
        // 即SELECT后面的元素
        ArrayList<String> printItem = new ArrayList<>();
        // WHERE后面的字符串 V
        ArrayList<String> equelItem = new ArrayList<>();
        // FROM后面的字符串
        ArrayList<String> formItem = new ArrayList<>();

        // 匹配的_X
        ArrayList<String> Xp = new ArrayList<>();
        // 匹配的_Y
        ArrayList<String> Yp = new ArrayList<>();
        // 匹配的_Z
        ArrayList<String> Zp = new ArrayList<>();

        for (int i = 1; i < arrayLists.size(); i += 2) {
            // 取单行（值）
            String[] nowStr = arrayLists.get(i);
            // 表名
            formItem.add(arrayLists.get(i - 1)[0]);

            for (int j = 1; j < nowStr.length; j++) {
                String cellValue = nowStr[j];
                if (cellValue != null && cellValue.length() != 0) {
                    // 检查P._X ...
                    if (XYZStr2.contains(cellValue)) {
                        printItem.add(arrayLists.get(i - 1)[j]);
                        if (cellValue.substring(2, 4).equals("_X")) {
                            Xp.add(arrayLists.get(i - 1)[0] + "." + arrayLists.get(i - 1)[j]);
                        }
                        if (cellValue.substring(2, 4).equals("_Y")) {
                            Yp.add(arrayLists.get(i - 1)[0] + "." + arrayLists.get(i - 1)[j]);
                        }
                        if (cellValue.substring(2, 4).equals("_Z")) {
                            Zp.add(arrayLists.get(i - 1)[0] + "." + arrayLists.get(i - 1)[j]);
                        }
                        continue;
                    }

                    // 检查P. ...
                    if (cellValue.equals(PrintStr)) {
                        printItem.add(arrayLists.get(i - 1)[j]);
                        continue;
                    }

                    // 检查X. ...
                    if (XYZStr.contains(cellValue)) {
                        if (cellValue.equals("_X")) {
                            Xp.add(arrayLists.get(i - 1)[0] + "." + arrayLists.get(i - 1)[j]);
                        }
                        if (cellValue.equals("_Y")) {
                            Yp.add(arrayLists.get(i - 1)[0] + "." + arrayLists.get(i - 1)[j]);
                        }
                        if (cellValue.equals("_Z")) {
                            Zp.add(arrayLists.get(i - 1)[0] + "." + arrayLists.get(i - 1)[j]);
                        }
                        continue;
                    }
                    // 检查> < ...
                    if (operatorStr.contains(cellValue.substring(0, 1))) {
                        equelItem.add(arrayLists.get(i - 1)[j]
                                + arrayLists.get(i)[j]);
                        continue;
                    }
                    // 检查= （最终，包括普通字符串）
                    equelItem.add(arrayLists.get(i - 1)[j] + "="
                            + arrayLists.get(i)[j]);
                }
            }
        }
        res = getResStr(formItem, printItem, equelItem);
        // 补充WHERE 后面的_X_Y_Z
        // QAQ 发现 Xp 大小为2
        if (Xp.size() != 0)
            res.append(" AND").append(Xp.get(0)).append("=").append(Xp.get(1));
        if (Yp.size() != 0)
            res.append(" AND").append(Yp.get(0)).append("=").append(Yp.get(1));
        if (Zp.size() != 0)
            res.append(" AND").append(Zp.get(0)).append("=").append(Zp.get(1));
        return res.toString();
    }
}