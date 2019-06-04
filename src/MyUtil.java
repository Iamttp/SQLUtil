import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MyUtilForQBE {
    public static void message(String cellValue) {
        JOptionPane.showMessageDialog(null, cellValue, "", JOptionPane.PLAIN_MESSAGE);
    }

    private static final String SELECT = "SELECT ";
    private static final String FROM = "FROM";
    private static final String WHERE = "WHERE ";
    private static final String PRINT_STR = "P.";
    private static final List<String> OPERATOR_STR = new ArrayList<String>() {{
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

    static String getResForMul(ArrayList<String[]> arrayLists) {
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

class MyUtilForDesign {
    // 分割字符串
    private static String SPLITABC = "->";
    private static String SPLITSTR = ",";
    private static StringBuilder ResForHelp;

    /**
     * 获取字符串出现的个数
     */
    public static int getSubStr(String str, String chs) {
        // 用空字符串替换所有要查找的字符串
        String destStr = str.replaceAll(chs, "");
        // 查找字符出现的个数 = （原字符串长度 - 替换后的字符串长度）/要查找的字符串长度
        return (str.length() - destStr.length()) / chs.length();
    }

    /**
     * 有序数组去重
     */
    public static int removeDuplicates(char[] nums) {
        if (nums.length == 0)
            return 0;
        //判断无输入,标记计数
        int number = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != nums[number]) {
                number++;
                nums[number] = nums[i];
            }
        }
        //标记+1即为数字个数
        number += 1;
        return number;
    }


    // 通过,初步分解
    public static String getSplit(String str) {
        // \s匹配任意的空白符（包括空格，制表符(Tab)，换行符，中文全角空格）
        // \S则是任意不是空白符的字符
        // 添加正则表达式可以使用// /**/注释
        str += "\n";
        str = str.replaceAll("//.*\\n", "");
        str = str.replaceAll("/\\*{1,2}[\\s\\S]*?\\*/", "");
        // 去回车空格
        str = str.replaceAll(" ", "");
        str = str.replaceAll("\n", "");
        // "->"数目应该比","多一个
        if (getSubStr(str, SPLITABC) - 1 != getSubStr(str, SPLITSTR)) {
            throw new UnsupportedOperationException("检查字符串格式");
        }
        String[] strings = str.split(SPLITSTR);
        return getRes(strings);
    }

    public static String getResAdd(ArrayList<String> testArrayStr, String testStr) {
        // 求解属性集闭包 exp : testArrayStr = A->B A-C B->C   testStr = A
        String tempStr = testStr;
        ArrayList<String[]> res = new ArrayList<>();

        // 一次do 循环即属性集扫描一次
        do {
            // temp : A ---- AB ----- ABC ---- ABC
            String[] temp = new String[testArrayStr.size() + 1];
            temp[0] = tempStr;
            for (int i = 1; i < temp.length; i++) {
                String[] strAll = testArrayStr.get(i - 1).split(SPLITABC);
                if (tempStr.contains(strAll[0])) {
                    tempStr += strAll[1];
                    //1，把tempStr转换为字符数组
                    char[] arrayCh = tempStr.toCharArray();
                    //2，利用数组帮助类自动排序,！！主要是考虑包含关系
                    Arrays.sort(arrayCh);
                    //3.排序数组去重!!leetcode
                    int length = removeDuplicates(arrayCh);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < length; j++) {
                        stringBuilder.append(arrayCh[j]);
                    }
                    tempStr = stringBuilder.toString();
                }
                temp[i] = tempStr;
            }
            res.add(temp);
        } while (res.size() - 2 < 0 ||
                (res.size() - 2 > 0 && !res.get(res.size() - 1)[testArrayStr.size() - 1]
                        .equals(res.get(res.size() - 2)[testArrayStr.size() - 1])));
        ResForHelp.append("\n\n")
                .append("属性集为").append(testStr).append("  ")
                .append("FD集为").append(testArrayStr).append("\t结果为：");
        for (String[] re : res) {
            ResForHelp.append(Arrays.toString(re)).append(" ");
        }
        return res.get(res.size() - 1)[testArrayStr.size() - 1];
    }

    public static String getRes(String[] strings) {
        ResForHelp = new StringBuilder();
        // ---------------------------第一步：FD写成右边为单属性.得到stringArrayList  exp: A->B, A->BC, B->C
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (String strNow : strings) {
            String[] strNowSplit = strNow.split(SPLITABC);
            for (int j = 0; j < strNowSplit[1].length(); j++) {
                // 去重
                if (!stringArrayList.contains(strNowSplit[0] + SPLITABC + strNowSplit[1].substring(j, j + 1))) {
                    stringArrayList.add(
                            strNowSplit[0] + SPLITABC + strNowSplit[1].substring(j, j + 1)
                    );
                }
            }
        }
        // ----------------------------第二步：考虑属性集的闭包 exp: A->B, A->C, B->C A->B,A->C,B->C,A->B,AB->C
        ArrayList<Integer> doGet = new ArrayList<>();
        for (int i = 0; i < stringArrayList.size(); i++) {
            // 依赖关系 FD集
            ArrayList<String> testArrayStr = (ArrayList<String>) stringArrayList.clone();
            // TODO 循环里面有remove
            testArrayStr.remove(i);
            // 属性集
            String[] temp = stringArrayList.get(i).split(SPLITABC);
            String testStr = temp[0];
            // 求解属性集闭包
            String res1 = getResAdd(testArrayStr, testStr);
            String res2 = getResAdd(stringArrayList, testStr);
            if (!res1.equals(res2)) {
                doGet.add(i);
            }
        }

        ArrayList<String> resRes = new ArrayList<>();
        for (Integer integer : doGet) {
            resRes.add(stringArrayList.get(integer));
        }

        return "\n单属性去重为：" + stringArrayList + ResForHelp + "\n最小依赖集最终结果：\n" + resRes.toString();
    }
}