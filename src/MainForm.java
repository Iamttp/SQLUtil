import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class MainForm {
    private JPanel myJPanel;
    private JTable table;
    private JButton butRemove;
    private JButton butAdd;
    private JButton butRes;
    private JTextArea textArea1;

    private Object[][] obj = {{" "}, {" "}};
    private String[] columnNames = {"col1", "col2", "col3", "col4"};

    private MainForm() {
        table.setModel(new DefaultTableModel(obj, columnNames));
        // ------------------------------------------大小设置
        int heigth = 200;
        table.setRowHeight(heigth / 2);
        butRemove.setPreferredSize(new Dimension(10, heigth));
        butAdd.setPreferredSize(new Dimension(10, heigth));
        table.setFont(new Font("宋体", Font.BOLD, 30));
        butRemove.setFont(new Font("宋体", Font.BOLD, 30));
        butAdd.setFont(new Font("宋体", Font.BOLD, 30));
        butRes.setFont(new Font("宋体", Font.BOLD, 30));
        textArea1.setFont(new Font("宋体", Font.BOLD, 30));
        for (int i = 0; i < 4; i++) {
            TableColumn firsetColumn = table.getColumnModel().getColumn(i);
            firsetColumn.setPreferredWidth(100);
            firsetColumn.setMaxWidth(100);
            firsetColumn.setMinWidth(100);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        // ------------------------------------------监听
        butRemove.addActionListener(e -> {
            table.removeColumn(table.getColumnModel().getColumn(0));// columnIndex是要删除的列序号
        });

        butAdd.addActionListener(e -> {
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
        });

        butRes.addActionListener(e -> textArea1.setText(MyUtil.getRes((DefaultTableModel) table.getModel())));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Query By Example");
        frame.setSize(1100, 600);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(new MainForm().myJPanel);
        frame.setVisible(true);
    }
}
