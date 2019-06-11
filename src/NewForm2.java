import MyUIUtil.textAreaRes2;
import MyUtil.MyUtilEasy;
import MyUtil.MyUtilForDesign;

import javax.swing.*;
import java.awt.*;

/**
 * @author ttp
 */
class NewForm2 extends JFrame {
    private textAreaRes2 jToolBarRes = new textAreaRes2("最小依赖集", "候选键");

    NewForm2() {
        // ---------------------------------------------主窗口设置
        setTitle("关系数据库设计工具集");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 618);

        Container cp = getContentPane();
        JSplitPane jSplitPaneRes = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JLabel(
                "<html><body><p align=\"center\">" +
                        "FD集的最小依赖集。<br/>使用->代表箭头，使用,分割依赖（英文字符exp:A->B, A->BC, B->C）" +
                        "对于不在依赖集的字母，则输入为D->D，exp：A->B, A->BC, B->C, D->D </p></body></html> ") {{
            setFont(new Font("宋体", Font.BOLD, 30));
        }}, jToolBarRes);
        cp.add(jSplitPaneRes, BorderLayout.CENTER);

        jToolBarRes.butRes1.addActionListener(e -> {
            String res;
            try {
                String[] temp = MyUtilForDesign.getSplit(jToolBarRes.textArea.getText());
                res = MyUtilForDesign.getRes(temp, jToolBarRes.s1.isSelected());
                jToolBarRes.textArea.append(res);
                setSize(1000, 1000);
            } catch (UnsupportedOperationException ex) {
                MyUtilEasy.message("请检查字符串的格式");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        jToolBarRes.butRes2.addActionListener(e -> {
            try {
                String[] temp = MyUtilForDesign.getSplit(jToolBarRes.textArea.getText());
                String res = MyUtilForDesign.getCandidateKey(temp, jToolBarRes.s1.isSelected());
                jToolBarRes.textArea.append(res);
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
