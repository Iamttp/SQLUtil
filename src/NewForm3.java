import MyUIUtil.textAreaRes3;
import MyUtil.MyUtilEasy;
import MyUtil.MyUtilForAcc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static MyUtil.MyUtilForAcc.insertValue;

public class NewForm3 extends JFrame {
    private textAreaRes3 jToolBarRes = new textAreaRes3("创建表并生成随机数据", "修改下方文本框执行SQL");
    private JTextArea textAreaIn = new JTextArea();
    private JTextArea textAreaOut = new JTextArea();
    private Object[][] obj = {{""}, {""}, {""}, {""}, {""}, {""}, {""}};
    private String[] columnNames = {"col1", "col2", "col3", "col4"};
    public JTable table = new JTable();
    public JTable tableRes = new JTable();
    private int realCol = 0;

    NewForm3() throws Exception {
        // ---------------------------------------------主窗口设置
        setTitle("SQL快速验证工具");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);

        // ----------------------------------------------table设置
        table.setModel(new DefaultTableModel(obj, columnNames));
        table.setRowHeight(50);
        table.setFont(new Font("宋体", Font.BOLD, 30));
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn firsetColumn = table.getColumnModel().getColumn(i);
            firsetColumn.setPreferredWidth(200);
            firsetColumn.setMaxWidth(200);
            firsetColumn.setMinWidth(200);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tableRes.setModel(new DefaultTableModel(obj, columnNames));
        tableRes.setRowHeight(50);
        tableRes.setFont(new Font("宋体", Font.BOLD, 30));
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn firsetColumn = tableRes.getColumnModel().getColumn(i);
            firsetColumn.setPreferredWidth(200);
            firsetColumn.setMaxWidth(200);
            firsetColumn.setMinWidth(200);
        }
        tableRes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        textAreaIn.setFont(new Font("宋体", Font.BOLD, 30));
        textAreaOut.setFont(new Font("宋体", Font.BOLD, 30));
        JScrollPane js = new JScrollPane(textAreaOut);
        js.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        js.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane js2 = new JScrollPane(textAreaIn);
        js2.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        js2.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Container cp = getContentPane();
        JSplitPane jSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jToolBarRes, js2);
        JSplitPane jSplitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jSplitPane1, js);
        JSplitPane jSplitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, table, tableRes);
        JSplitPane jSplitPaneRes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jSplitPane3, jSplitPane2);
        jSplitPane3.setResizeWeight(0.5);
        jSplitPane1.setResizeWeight(0.1);
        jSplitPane2.setResizeWeight(0.5);
        jSplitPaneRes.setResizeWeight(0.9);
        cp.add(jSplitPaneRes, BorderLayout.CENTER);
        setAddition();
        setVisible(true);
    }

    void setAddition() throws ClassNotFoundException, SQLException {
        System.out.println("第一步 连接");
        Class.forName("org.hsqldb.jdbcDriver");
        String url = "jdbc:hsqldb:mem:ttpDB";
        Connection c = DriverManager.getConnection(url);
        Statement st = c.createStatement();

        jToolBarRes.butRes1.addActionListener(e -> {
            try {
                st.execute("drop table t");
            } catch (SQLException ex) {
                System.out.print("第一次drop");
            }
            System.out.println("第二步 执行创建表语句");
            StringBuilder sql = new StringBuilder();
            // 表名为t
            sql.append("CREATE TABLE t (\n");
            for (int i = 0; i < table.getColumnCount(); i++) {
                if (i == 0) {
                    sql.append(((String) table.getValueAt(0, i))).append("\n");
                    realCol++;
                    continue;
                }
                if (null == table.getValueAt(0, i)) {
                    continue;
                }
                realCol++;
                sql.append(",").append(((String) table.getValueAt(0, i))).append("\n");
            }
            sql.append(");\n");
            try {
                textAreaOut.append(sql.toString());
                st.executeUpdate(sql.toString());
            } catch (SQLException ex) {
                textAreaOut.append("SQL语句出错\n");
                ex.printStackTrace();
            }
            System.out.println("第三步 生成随机数据");
            for (int i = 0; i < table.getColumnCount(); i++) {
                String nowStr = (String) table.getValueAt(0, i);
                if (nowStr == null) {
                    continue;
                }
                nowStr = nowStr.trim();
                List<String> res = MyUtilForAcc.getRandom(nowStr.split(" ")[1], 5);
                for (int j = 1; j < res.size(); j++) {
                    table.setValueAt(res.get(j), j, i);
                }
            }
        });

        jToolBarRes.butRes2.addActionListener(e -> {
            if ((textAreaIn == null) || ("".equals(textAreaIn.getText()))) {
                MyUtilEasy.message("输入框为空！");
                return;
            }
            // 清空数据库的表重新插入， 清空表
            try {
                st.execute("delete from t");
                textAreaOut.append("delete from t\n");
            } catch (SQLException ex) {
                System.out.print("第一次drop");
            }
            for (int j = 0; j < tableRes.getColumnCount(); j++) {
                for (int i = 0; i < tableRes.getRowCount(); i++) {
                    tableRes.setValueAt("", i, j);
                }
            }
            // 1 重新读取表格数据， 然后插入 相当于将UPDATE DELETE 语句用可视化方式结合起来
            ArrayList<String> as = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                StringBuilder rows = new StringBuilder();
                for (int i = 0; i < table.getColumnCount(); i++) {
                    String nowStr = (String) table.getValueAt(j, i);
                    if (nowStr == null) {
                        continue;
                    }
                    nowStr = nowStr.trim();
                    if (i != 0)
                        rows.append(",").append(nowStr);
                    else
                        rows.append(nowStr);
                }
                as.add(rows.toString());
            }

            for (int i = 1; i < as.size(); i++) {
                try {
                    textAreaOut.append(insertValue(st, "t", as.get(i)));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // 2 执行输入框的SQL数据   一般用SELECT 方便测试SQL语句字符数据加引号
            try {
                ResultSet rs = st.executeQuery(textAreaIn.getText());
                // 3 根据返回结果，更新下面的表格
                int j = 0;
                // 获得结果集结构信息,元数据
                ResultSetMetaData md = rs.getMetaData();
                // 获得列数
                for (int i = 1; i <= realCol; i++) {
                    tableRes.setValueAt(md.getColumnLabel(i), j, i - 1);
                }
                while (rs.next()) {
                    j++;
                    for (int i = 1; i <= realCol; i++) {
                        String name = rs.getString(i);
                        tableRes.setValueAt(name, j, i - 1);
                    }
                }
            } catch (SQLException ex) {
                textAreaOut.append("SQL语句出错\n");
                ex.printStackTrace();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("drop table t");
                try {
                    st.execute("drop table t");
                    if (st != null)
                        st.close();
                    c.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
