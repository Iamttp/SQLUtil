import MyUIUtil.tableBar;
import MyUIUtil.textAreaRes;
import MyUtil.MyUtilForQBE;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

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
