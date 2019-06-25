import MyUIUtil.textAreaRes2;
import MyUtil.MyUtilEasy;
import MyUtil.MyUtilForDesign;

import javax.swing.*;
import java.awt.*;

/**
 * @author ttp
 */
class NewForm2 extends JFrame {
    private textAreaRes2 jToolBarRes = new textAreaRes2("最小依赖集", "候选键", "3NF模式集");
    private JTextArea textArea = new JTextArea();

    NewForm2() {
        // ---------------------------------------------主窗口设置
        setTitle("关系数据库设计工具集");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 618);

        textArea.setFont(new Font("宋体", Font.BOLD, 30));
        //分别设置水平和垂直滚动条自动出现
        JScrollPane js = new JScrollPane(textArea);
        js.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        js.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Container cp = getContentPane();
        JSplitPane jSplitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, js, jToolBarRes);
        JSplitPane jSplitPaneRes = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JLabel(
                "<html><body><p align=\"center\">" +
                        "FD集的最小依赖集,候选键,3NF模式集。<br/>使用->代表箭头，使用,分割依赖（英文字符exp:A->B, A->BC, B->C）" +
                        "对于不在依赖集的字母，则输入为D->D，exp：A->B, D->D </p></body></html> ") {{
            setFont(new Font("宋体", Font.BOLD, 30));
        }}, jSplitPane1);
        jSplitPane1.setResizeWeight(0.95);
        jSplitPaneRes.setResizeWeight(0.3);
        cp.add(jSplitPaneRes, BorderLayout.CENTER);
        jToolBarRes.butRes1.addActionListener(e -> {
            try {
                String[] temp = MyUtilForDesign.getSplit(textArea.getText());
                String res = MyUtilForDesign.getRes(temp, (jToolBarRes.s1.isSelected()) ? 1 : 0);
                textArea.append(res);
                setSize(1000, 1000);
            } catch (UnsupportedOperationException ex) {
                MyUtilEasy.message("请检查字符串的格式");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        jToolBarRes.butRes2.addActionListener(e -> {
            try {
                String[] temp = MyUtilForDesign.getSplit(textArea.getText());
                String res = MyUtilForDesign.getCandidateKey(temp, (jToolBarRes.s1.isSelected()) ? 1 : 0);
                textArea.append(res);
                setSize(1000, 1000);
            } catch (UnsupportedOperationException ex) {
                MyUtilEasy.message("请检查字符串的格式");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        jToolBarRes.butRes3.addActionListener(e -> {
            try {
                String[] temp = MyUtilForDesign.getSplit(textArea.getText());
                String res = MyUtilForDesign.get3NF(temp, (jToolBarRes.s1.isSelected()) ? 1 : 0);
                textArea.append(res);
                setSize(1000, 1000);
            } catch (UnsupportedOperationException ex) {
                MyUtilEasy.message("请检查字符串的格式");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}
