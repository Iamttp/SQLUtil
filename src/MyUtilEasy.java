import javax.swing.*;

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


    public static void message(String cellValue) {
        JOptionPane.showMessageDialog(null, cellValue, "", JOptionPane.PLAIN_MESSAGE);
    }
}
