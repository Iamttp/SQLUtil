import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * 自定义按钮
 */
class ToolbarButton extends JButton {
    ToolbarButton(String text, int width, int heigth, ActionListener l) {
        this.setText(text);
        addActionListener(l);
        this.setFont(new Font("宋体", Font.BOLD, 30));
        setPreferredSize(new Dimension(width, heigth)); // 按钮大小设置
    }
}

/**
 * 自定义表格（加两个按钮）
 */
class tableBar extends JToolBar {
    private Object[][] obj = {{" "}, {" "}};
    private String[] columnNames = {"col1", "col2", "col3", "col4"};
    int heigth = 200;

    public tableBar() {
        // ----------------------------------------------table设置
        JTable table = new JTable();
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

/**
 * 自定义显示框，加个按钮
 */
class textAreaRes extends JToolBar {
    JTextArea textArea = new JTextArea();
    JToolBar sonToolBar = new JToolBar();

    public textAreaRes() {
        ToolbarButton butRes = new ToolbarButton("结果", 100, 100, e -> {
            //TODO
        });
        ToolbarButton butAdd = new ToolbarButton("添加表格", 200, 100, e -> {
            //TODO
        });
        ToolbarButton butRemove = new ToolbarButton("删除表格", 200, 100, e -> {
            //TODO
        });
        textArea.setFont(new Font("宋体", Font.BOLD, 30));
        this.add(textArea);

        sonToolBar.add(butRes);
        sonToolBar.add(butAdd);
        sonToolBar.add(butRemove);
        sonToolBar.setFloatable(false);
        sonToolBar.setOrientation(VERTICAL);
        this.add(sonToolBar);

        this.setFloatable(false);
    }
}

public class newForm extends JFrame {

    // 窗口的初始化
    public newForm() {
        // ---------------------------------------------主窗口设置
        setTitle("Query By Example");
        setSize(1100, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JSplitPane jSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new tableBar(), new tableBar());
        JSplitPane jSplitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jSplitPane1, new textAreaRes());
        Container cp = getContentPane();
        cp.add(jSplitPane2, BorderLayout.CENTER);
        setVisible(true);
    }
}

class Main {
    public static void main(String[] args) {
        new newForm();
    }
}