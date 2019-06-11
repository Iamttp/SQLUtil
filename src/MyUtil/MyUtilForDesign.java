package MyUtil;

import java.util.ArrayList;
import java.util.Arrays;

import static MyUtil.MyUtilEasy.getSubStr;

public class MyUtilForDesign {
    // 分割字符串
    private static String SPLITABC = "->";
    private static String SPLITSTR = ",";
    private static StringBuilder ResForHelp;

    /**
     * 通过,初步分解
     *
     * @param str 用户输入
     * @return 解析，分割结果
     */
    public static String[] getSplit(String str) {
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
        return str.split(SPLITSTR);
    }

    /**
     * 求解属性集闭包
     *
     * @param testArrayStr testArrayStr = A->B A-C B->C
     * @param testStr      testStr = A
     * @return A+
     */
    public static String getResAdd(ArrayList<String> testArrayStr, String testStr) {
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
                    int length = MyUtilEasy.removeDuplicates(arrayCh);
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
        ResForHelp.append("\n")
                .append("属性集为").append(testStr).append("  ")
                .append("FD集为").append(testArrayStr).append("\t结果为：");
        for (String[] re : res) {
            ResForHelp.append(Arrays.toString(re)).append(" ");
        }
        return res.get(res.size() - 1)[testArrayStr.size()];
    }

    /**
     * 获取最小依赖集
     *
     * @param strings [A->C, C->A, B->A, B->C, D->A, D->C, BD->A]
     * @param flagStr 1 : 详细信息 0 : 附带部分信息 -1(其他) : 极简信息
     * @return 带过程的最小依赖集
     */
    public static String getRes(String[] strings, int flagStr) {
        ResForHelp = new StringBuilder();
        // ---------------------------第一步：FD写成右边为单属性.得到stringArrayList  exp: A->B, A->BC, B->C
        ArrayList<String> stringArrayList = getStrings(strings);
        // ----------------------------第二步：考虑属性集的闭包 exp: A->B, A->C, B->C A->B,A->C,B->C,A->B,AB->C
        ArrayList<Integer> doGet = new ArrayList<>();
        for (int i = 0; i < stringArrayList.size(); i++) {
            // 依赖关系 FD集
            // --------一旦冗余即刻清除 exp A->C,C->A,B->A,B->C,D->A,D->C,BD->A
            ArrayList<String> testArrayStr = new ArrayList<>();
            for (int j = 0; j < stringArrayList.size(); j++) {
                if (j == i) {
                    continue;
                }
                if (j > i || doGet.contains(j)) {
                    testArrayStr.add(stringArrayList.get(j));
                }
            }
            // -------
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
        if (flagStr == 1)
            return "\n单属性去重为：" + stringArrayList + ResForHelp + "\n最小依赖集最终结果：\n" + resRes.toString();
        else if (flagStr == 0)
            return "\n最小依赖集最终结果：\n" + resRes.toString();
        else
            return resRes.toString();
    }

    /**
     * 第一步：FD写成右边为单属性.得到stringArrayList  exp: A->B, A->BC, B->C
     */
    private static ArrayList<String> getStrings(String[] strings) {
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
        return stringArrayList;
    }

    /**
     * 获取候选键
     */
    public static String getCandidateKey(String[] strings, int flagStr) {
        ResForHelp = new StringBuilder();
        ArrayList<String> resRes = new ArrayList<>();
        // ---------------------------------暴力法
        ArrayList<String> stringArrayList = getStrings(strings);

        // ----------------------得到所有的元素 ！TODO 优化
        StringBuilder stringBuilder = new StringBuilder();
        for (String strNow : stringArrayList) {
            String[] strNowSplit = strNow.split(SPLITABC);
            stringBuilder.append(strNowSplit[0]);
            stringBuilder.append(strNowSplit[1]);
        }
        char[] arrayCh = stringBuilder.toString().toCharArray();
        Arrays.sort(arrayCh);
        int length = MyUtilEasy.removeDuplicates(arrayCh);
        stringBuilder = new StringBuilder();
        for (int j = 0; j < length; j++) {
            stringBuilder.append(arrayCh[j]);
        }

        // 假如arrayCh为A,B,C,D，那么ACD的下标即为1011(二进制),
//        ArrayList<Boolean> integerArrayList = new ArrayList<>(); TODO 优化
        for (int i = 1; i < Math.pow(2, length); i++) {
            StringBuilder inputStr = new StringBuilder();
            int j = 0, tempi = i;
            while (tempi > 0) {
                int numNow = tempi % 2;
                tempi /= 2;
                if (numNow == 1) {
//                    inputStr.append(arrayCh[length - j - 1]);
                    inputStr.append(arrayCh[j]);
                }
                j++;
            }
            String nowStrRes = getResAdd(stringArrayList, inputStr.toString());
            if (nowStrRes.equals(stringBuilder.toString())) {
                resRes.add(inputStr.toString());
            }
        }

        // ----------------------------------去除不满足候选键的元素
//        ArrayList<String> resResRes = new ArrayList<>();
        for (int i = 0; i < resRes.size(); i++) {
            String nowStr = resRes.get(i);
            for (int j = 0; j < resRes.size(); j++) {
                // 坑 contains 无法解决 BD BCD 问题
                if (i != j && MyUtilEasy.containsDeep(resRes.get(j), nowStr)) {
                    resRes.remove(resRes.get(j--));
                }
            }
        }
        if (flagStr == 1) {
            return "\n单属性去重为：" + stringArrayList + ResForHelp + "\n候选键最终结果：\n" + resRes;
        } else if (flagStr == 0) {
            return "\n候选键最终结果：\n" + resRes;
        } else {
            return resRes.toString();
        }
    }

    /**
     * 分解为3NF模式集
     */
    public static String get3NF(String[] strings, int flagStr) {
        // res1 [A->B,B->C]
        String res1 = getRes(strings, -1);
        res1 = res1.replaceAll("->", "");
        res1 = res1.substring(1, res1.length() - 1);
        // res2 [AD]
        String res2 = getCandidateKey(strings, -1);
        res2 = res2.substring(1, res2.length() - 1);
        if (flagStr == 1) {
            return "\n分解为3NF模式集为：" +  (res1 + "," + res2).replaceAll(" ", "");
        } else {
            return "\n" + (res1 + "," + res2).replaceAll(" ", "");
        }
    }
}
