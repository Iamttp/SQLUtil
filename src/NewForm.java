import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

/**
 * 自定义表格（加两个按钮）
 */
class tableBar extends JToolBar {
    private Object[][] obj = {{" "}, {" "}};
    private String[] columnNames = {"col1", "col2", "col3", "col4"};
    private final int heigth = 200;
    JTable table = new JTable();

    tableBar() {
        // ----------------------------------------------table设置
        table.setModel(new DefaultTableModel(obj, columnNames));
        table.setRowHeight(heigth / 2);
        table.setFont(new Font("宋体", Font.BOLD, 30));
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn firsetColumn = table.getColumnModel().getColumn(i);
            firsetColumn.setPreferredWidth(100);
            firsetColumn.setMaxWidth(100);
            firsetColumn.setMinWidth(100);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // -----------------------------------------------事件监听
        this.add(new ToolbarButton("↑", 100, 100
                , e -> table.removeColumn(table.getColumnModel().getColumn(0))));

        this.add(table);

        this.add(new ToolbarButton("↓", 100, 100, e -> {
            table.addColumn(new TableColumn());

            String[] columnNamesTemp = new String[columnNames.length + 1];
            System.arraycopy(columnNames, 0, columnNamesTemp, 0, columnNames.length);
            columnNames = columnNamesTemp;
            columnNames[columnNames.length - 1] = "col" + (columnNames.length + 1);
            table.setModel(new DefaultTableModel(obj, columnNames));

            for (int i = 0; i < columnNames.length; i++) {
                TableColumn firsetColumn = table.getColumnModel().getColumn(i);
                firsetColumn.setPreferredWidth(100);
                firsetColumn.setMaxWidth(100);
                firsetColumn.setMinWidth(100);
            }
        }));

        this.setFloatable(false);
    }
}



class NewForm extends JFrame {

    private int nowTableNum;
    private ArrayList<tableBar> jToolBarTable = new ArrayList<tableBar>() {{
    }};
    private textAreaRes jToolBarRes = new textAreaRes();

    private void setTable() {
        JSplitPane jSplitPaneRes, jSplitPaneTemp = null;
        // ------------------------------------------------------按照num新建对象和设置布局
        for (int i = 0; i < nowTableNum; i++) {
            jToolBarTable.add(new tableBar());
        }
        for (int i = 0; i < nowTableNum; i++) {
            jSplitPaneTemp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jSplitPaneTemp, jToolBarTable.get(i));
        }
        jSplitPaneRes = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jSplitPaneTemp, jToolBarRes);
        Container cp = getContentPane();
        cp.add(jSplitPaneRes, BorderLayout.CENTER);
        setSize(1100, 400 + nowTableNum * 200);
    }

    NewForm(int num) {
        // ---------------------------------------------主窗口设置
        setTitle("Query By Example");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nowTableNum = num;
        setTable();

        jToolBarRes.butRes.addActionListener(e -> {
            if (nowTableNum == 1) {
                jToolBarRes.textArea.setText(MyUtilForQBE.getRes((DefaultTableModel) jToolBarTable.get(0).table.getModel()));
            } else {
                ArrayList<String[]> arrayList = new ArrayList<>();
                for (int i = 0; i < nowTableNum; i++) {
                    JTable jTable = jToolBarTable.get(i).table;
                    String[] tempStr = new String[jTable.getColumnCount()];
                    for (int j = 0; j < jTable.getColumnCount(); j++) {
                        tempStr[j] = ((String) jTable.getValueAt(0, j));
                    }
                    arrayList.add(tempStr);
                    String[] tempStr2 = new String[jTable.getColumnCount()];
                    for (int j = 0; j < jTable.getColumnCount(); j++) {
                        tempStr2[j] = ((String) jTable.getValueAt(1, j));
                    }
                    arrayList.add(tempStr2);
                }
                // ----------------------------------------------向Util里面的解析函数传入 ArrayList<String[]>
                jToolBarRes.textArea.setText(MyUtilForQBE.getResForMul(arrayList));
            }
        });
        setVisible(true);
    }
}
