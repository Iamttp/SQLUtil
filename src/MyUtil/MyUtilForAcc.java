package MyUtil;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyUtilForAcc {
    private static final List<String> DATATYPE = new ArrayList<String>() {{
        add("CHAR");
        add("VARCHAR");
        add("BIT");
        add("FLOAT");
        add("INT");
    }};

    public static List<String> getRandom(String dataType, int strLen) {
        int len = 0;
        if (dataType.contains("(")) {
            len = Integer.parseInt(dataType.substring(dataType.indexOf("(") + 1, dataType.indexOf(")")));
            dataType = dataType.substring(0, dataType.indexOf("("));
        }
        if (!DATATYPE.contains(dataType)) {
            return null;
        }
        List<String> ls = new ArrayList<>();
        if ("CHAR".equals(dataType)) {
            for (int i = 0; i < strLen; i++) {
                ls.add(MyUtilEasy.getRandomString(len));
            }
        }
        if ("VARCHAR".equals(dataType)) {
            for (int i = 0; i < strLen; i++) {
                Random random = new Random();
                ls.add(MyUtilEasy.getRandomString(random.nextInt(len)));
            }
        }
        if ("BIT".equals(dataType)) {
            for (int i = 0; i < strLen; i++) {
                Random random = new Random();
                ls.add(String.valueOf(random.nextInt(2)));
            }
        }
        if ("FLOAT".equals(dataType)) {
            for (int i = 0; i < strLen; i++) {
                Random random = new Random();
                ls.add(String.valueOf(random.nextFloat()));
            }
        }
        if ("INT".equals(dataType)) {
            for (int i = 0; i < strLen; i++) {
                Random random = new Random();
                ls.add(String.valueOf(random.nextInt(10000)));
            }
        }
        return ls;
    }

    // 第一步 tableName处理多余
    public static String insertValue(Statement st, String tableName, String val) throws SQLException {
        String[] s = tableName.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            if (i != 0)
                sb.append(",").append(s[i].split(" ")[0]);
            else
                sb.append(s[i].split(" ")[0]);
        }
        StringBuilder sql1 = new StringBuilder();
        sql1.append("INSERT INTO ").append(sb.toString()).append("\n VALUES(");
        sql1.append(val).append(");").append("\n");
        st.execute(sql1.toString());
        return sql1.toString();
    }

    public static void main(String[] arg) {
        System.out.println(getRandom("INT", 5));
    }
}
