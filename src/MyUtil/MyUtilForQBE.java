package MyUtil;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class MyUtilForQBE {
    private static final String SELECT = "SELECT ";
    private static final String FROM = "FROM";
    private static final String WHERE = "WHERE ";
    private static final String PRINT_STR = "P.";
    private static final List<String> OPERATOR_STR = new ArrayList<String>() {{
        add(">");
        add("<");
        add("=");
    }};

    private static final List<String> XYZ_STR = new ArrayList<String>() {{
        add("_X");
        add("_Y");
        add("_Z");
    }};
    private static final List<String> XYZ_STR2 = new ArrayList<String>() {{
        add("P._X");
        add("P._Y");
        add("P._Z");
    }};

    /**
     * 完成最终的拼接
     *
     * @param fromItem  FROM后面的项目
     * @param printItem SELECT后面的项目
     * @param equelItem WHERE后面的项目
     * @return 拼接结果
     */
    private static StringBuilder getResStr(ArrayList<String> fromItem, ArrayList<String> printItem, ArrayList<String> equelItem) {
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
        for (int i = 0; i < fromItem.size(); i++) {
            if (i != fromItem.size() - 1)
                res.append(fromItem.get(i)).append(",");
            else
                res.append(fromItem.get(i)).append("\n");
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

    /**
     * 单张表解析
     */
    public static String getRes(DefaultTableModel tableModel) {
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
                if (cellValue.equals(PRINT_STR)) {
                    printItem.add((String) tableModel.getValueAt(0, i));
                    continue;
                }

                // > <
                if (OPERATOR_STR.contains(cellValue.substring(0, 1))) {
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

    /**
     * 多张表解析
     */
    public static String getResForMul(ArrayList<String[]> arrayLists) {
        StringBuilder res;
        // 即SELECT后面的元素
        ArrayList<String> printItem = new ArrayList<>();
        // WHERE后面的字符串 V
        ArrayList<String> equelItem = new ArrayList<>();
        // FROM后面的字符串
        ArrayList<String> formItem = new ArrayList<>();

        // 匹配的_X
        ArrayList<String> xp = new ArrayList<>();
        // 匹配的_Y
        ArrayList<String> yp = new ArrayList<>();
        // 匹配的_Z
        ArrayList<String> zp = new ArrayList<>();

        for (int i = 1; i < arrayLists.size(); i += 2) {
            // 取单行（值）
            String[] nowStr = arrayLists.get(i);
            // 表名
            formItem.add(arrayLists.get(i - 1)[0]);

            for (int j = 1; j < nowStr.length; j++) {
                String cellValue = nowStr[j];
                if (cellValue != null && cellValue.length() != 0) {
                    // 检查P._X ...
                    if (XYZ_STR2.contains(cellValue)) {
                        printItem.add(arrayLists.get(i - 1)[j]);
                        if ("_X".equals(cellValue.substring(2, 4))) {
                            xp.add(arrayLists.get(i - 1)[0] + "." + arrayLists.get(i - 1)[j]);
                        }
                        if ("_Y".equals(cellValue.substring(2, 4))) {
                            yp.add(arrayLists.get(i - 1)[0] + "." + arrayLists.get(i - 1)[j]);
                        }
                        if ("_Z".equals(cellValue.substring(2, 4))) {
                            zp.add(arrayLists.get(i - 1)[0] + "." + arrayLists.get(i - 1)[j]);
                        }
                        continue;
                    }

                    // 检查P. ...
                    if (cellValue.equals(PRINT_STR)) {
                        printItem.add(arrayLists.get(i - 1)[j]);
                        continue;
                    }

                    // 检查X. ...
                    if (XYZ_STR.contains(cellValue)) {
                        if ("_X".equals(cellValue)) {
                            xp.add(arrayLists.get(i - 1)[0] + "." + arrayLists.get(i - 1)[j]);
                        }
                        if ("_Y".equals(cellValue)) {
                            yp.add(arrayLists.get(i - 1)[0] + "." + arrayLists.get(i - 1)[j]);
                        }
                        if ("_Z".equals(cellValue)) {
                            zp.add(arrayLists.get(i - 1)[0] + "." + arrayLists.get(i - 1)[j]);
                        }
                        continue;
                    }
                    // 检查> < ...
                    if (OPERATOR_STR.contains(cellValue.substring(0, 1))) {
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
        // QAQ 发现 xp 大小为2
        if (xp.size() != 0)
            res.append(" AND").append(xp.get(0)).append("=").append(xp.get(1));
        if (yp.size() != 0)
            res.append(" AND").append(yp.get(0)).append("=").append(yp.get(1));
        if (zp.size() != 0)
            res.append(" AND").append(zp.get(0)).append("=").append(zp.get(1));
        return res.toString();
    }
}

