import MyUIUtil.textAreaRes3;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class NewForm3 extends JFrame {
    private textAreaRes3 jToolBarRes = new textAreaRes3("随机数据生成", "执行SQL");
    private JTextArea textArea = new JTextArea();

    NewForm3() {
        // ---------------------------------------------主窗口设置
        setTitle("SQL快速验证工具");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 618);

        textArea.setFont(new Font("宋体", Font.BOLD, 30));
        JScrollPane js = new JScrollPane(textArea);
        js.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        js.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Container cp = getContentPane();
        JSplitPane jSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jToolBarRes, js);
        jSplitPane1.setResizeWeight(0.1);
        cp.add(jSplitPane1, BorderLayout.CENTER);

        // TODO 按钮监听
        setVisible(true);
    }

    public static void fun(String[] args) throws Exception {
        System.out.println("第一步 连接");
        Class.forName("org.hsqldb.jdbcDriver");
        String url = "jdbc:hsqldb:mem:ttpDB";
        Connection c = DriverManager.getConnection(url);
        Statement st = c.createStatement();
        System.out.println("第二步 执行查询语句");
        ResultSet rs = st.executeQuery("select * from category");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println(id + "****" + name);
        }
        System.out.println("第三步 关闭连接");
        st.close();
        c.close();
    }
}
