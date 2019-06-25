import MyUIUtil.textAreaRes3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class NewForm3 extends JFrame {
    private textAreaRes3 jToolBarRes = new textAreaRes3("随机数据生成", "执行SQL");
    private JTextArea textArea = new JTextArea();
    private Object[][] obj = {{" "}, {" "}, {" "}, {" "}, {" "}};
    private String[] columnNames = {"col1", "col2", "col3", "col4"};
    public JTable table = new JTable();
    public JTable tableRes = new JTable();

    NewForm3() throws Exception {
        System.out.println("第一步 连接");
        Class.forName("org.hsqldb.jdbcDriver");
        String url = "jdbc:hsqldb:mem:ttpDB";
        Connection c = DriverManager.getConnection(url);
        Statement st = c.createStatement();
        // ---------------------------------------------主窗口设置
        setTitle("SQL快速验证工具");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 618);

        // ----------------------------------------------table设置
        table.setModel(new DefaultTableModel(obj, columnNames));
        table.setRowHeight(50);
        table.setFont(new Font("宋体", Font.BOLD, 30));
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn firsetColumn = table.getColumnModel().getColumn(i);
            firsetColumn.setPreferredWidth(100);
            firsetColumn.setMaxWidth(100);
            firsetColumn.setMinWidth(100);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tableRes.setModel(new DefaultTableModel(obj, columnNames));
        tableRes.setRowHeight(50);
        tableRes.setFont(new Font("宋体", Font.BOLD, 30));
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn firsetColumn = tableRes.getColumnModel().getColumn(i);
            firsetColumn.setPreferredWidth(100);
            firsetColumn.setMaxWidth(100);
            firsetColumn.setMinWidth(100);
        }
        tableRes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        textArea.setFont(new Font("宋体", Font.BOLD, 30));
        JScrollPane js = new JScrollPane(textArea);
        js.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        js.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Container cp = getContentPane();
        JSplitPane jSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jToolBarRes, js);
        JSplitPane jSplitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, table, tableRes);
        JSplitPane jSplitPaneRes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jSplitPane2, jSplitPane1);
        jSplitPane1.setResizeWeight(0.1);
        jSplitPane2.setResizeWeight(0.5);
        jSplitPaneRes.setResizeWeight(0.9);
        cp.add(jSplitPaneRes, BorderLayout.CENTER);

        // TODO 按钮监听
        setVisible(true);
    }

    public static void fun(String[] args) {
//        System.out.println("第二步 执行查询语句");
//        ResultSet rs = st.executeQuery("select * from category");
//        while (rs.next()) {
//            int id = rs.getInt("id");
//            String name = rs.getString("name");
//            System.out.println(id + "****" + name);
//        }
        //TODO 没有关闭连接
//        System.out.println("第三步 关闭连接");
//        st.close();
//        c.close();
    }
}
