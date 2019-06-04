import javax.swing.*;
import java.awt.*;

/**
 * @author ttp
 */
class NewForm2 extends JFrame {
    private textAreaRes jToolBarRes = new textAreaRes("结果");

    NewForm2() {
        // ---------------------------------------------主窗口设置
        setTitle("关系数据库设计工具集");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);

        Container cp = getContentPane();
        JSplitPane jSplitPaneRes = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JLabel(
                "<html><body><p align=\"center\">" +
                        "FD集的最小依赖集。<br/>使用->代表箭头，使用,分割依赖（英文字符exp:A->B, A->BC, B->C）" +
                        "</p></body></html>") {{
            setFont(new Font("宋体", Font.BOLD, 30));
        }}, jToolBarRes);
        cp.add(jSplitPaneRes, BorderLayout.CENTER);

        jToolBarRes.butRes.addActionListener(e -> {
            String res;
            try {
                res = MyUtilForDesign.getSplit(jToolBarRes.textArea.getText());
                jToolBarRes.textArea.append(res);
                setSize(1000, 1000);
            } catch (UnsupportedOperationException ex) {
                MyUtilForQBE.message("请检查字符串的格式");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}
