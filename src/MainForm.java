import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class MainForm {
    private JPanel myJPanel;
    private JTable table1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JTextArea textArea1;

    Object[][] obj = {{" "}, {" "}};
    String[] columnNames = {"col1", "col2", "col3", "col4"};

    public MainForm() {
        table1.setModel(new DefaultTableModel(obj, columnNames));
        // ------------------------------------------大小设置
        int heigth = 200;
        table1.setRowHeight(heigth / 2);
        button1.setPreferredSize(new Dimension(10, heigth));
        button2.setPreferredSize(new Dimension(10, heigth));
        table1.setFont(new Font("宋体", Font.BOLD, 30));
        button1.setFont(new Font("宋体", Font.BOLD, 30));
        button2.setFont(new Font("宋体", Font.BOLD, 30));
        textArea1.setFont(new Font("宋体", Font.BOLD, 30));
        for (int i = 0; i < 4; i++) {
            TableColumn firsetColumn = table1.getColumnModel().getColumn(i);
            firsetColumn.setPreferredWidth(100);
            firsetColumn.setMaxWidth(100);
            firsetColumn.setMinWidth(100);
        }
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//        MyUtil.FitTableColumns(table1);
        // ------------------------------------------监听
        button1.addActionListener(e -> {
            table1.removeColumn(table1.getColumnModel().getColumn(0));// columnIndex是要删除的列序号
        });

        // TODO 重复内容
        button2.addActionListener(e -> {
            table1.addColumn(new TableColumn());

            String[] columnNamesTemp = new String[columnNames.length + 1];
            System.arraycopy(columnNames, 0, columnNamesTemp, 0, columnNames.length);
            columnNames = columnNamesTemp;
            columnNames[columnNames.length - 1] = "col" + (columnNames.length + 1);
            table1.setModel(new DefaultTableModel(obj, columnNames));

            for (int i = 0; i < columnNames.length; i++) {
                TableColumn firsetColumn = table1.getColumnModel().getColumn(i);
                firsetColumn.setPreferredWidth(100);
                firsetColumn.setMaxWidth(100);
                firsetColumn.setMinWidth(100);
            }
        });

        button3.addActionListener(e -> {
            textArea1.setText(MyUtil.getRes((DefaultTableModel) table1.getModel()));
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Query By Example");
        frame.setSize(1100, 600);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(new MainForm().myJPanel);
        frame.setVisible(true);
    }

}
