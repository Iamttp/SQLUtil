package MyUIUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * 自定义表格（加两个按钮）
 */
public class tableBar extends JToolBar {
    private Object[][] obj = {{" "}, {" "}};
    private String[] columnNames = {"col1", "col2", "col3", "col4"};
    private final int heigth = 200;
    public JTable table = new JTable();

    public tableBar() {
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


