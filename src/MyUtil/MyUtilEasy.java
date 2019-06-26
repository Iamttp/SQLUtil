package MyUtil;

import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class MyUtilEasy {

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

    public static boolean containsDeep(String big, String small) {
        for (int i = 0; i < small.length(); i++) {
            if (!big.contains(small.substring(i, i + 1))) {
                return false;
            }
        }
        return true;
    }

    public static void message(String cellValue) {
        JOptionPane.showMessageDialog(null, cellValue, "", JOptionPane.PLAIN_MESSAGE);
    }

    /* 对字母统一排序*/
    public static String getSort(String str) {
        //1，把tempStr转换为字符数组
        char[] arrayCh = str.toCharArray();
        //2，利用数组帮助类自动排序,！！主要是考虑包含关系
        Arrays.sort(arrayCh);
        //3.排序数组去重!!leetcode
        int length = MyUtilEasy.removeDuplicates(arrayCh);
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < length; j++) {
            stringBuilder.append(arrayCh[j]);
        }
        return stringBuilder.toString();
    }

    //length用户要求产生字符串的长度
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String addStr(String str) {
        String str1 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < str1.length(); i++) {
            if (str.indexOf(str1.charAt(i)) != -1) {
                return "\'" + str + "\'";
            }
        }
        return str;
    }
}
