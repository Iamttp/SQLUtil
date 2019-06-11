import MyUIUtil.ToolbarButton;

import javax.swing.*;
import java.awt.*;

/**
 * Ubuntu运行参考：https://blog.csdn.net/weixin_42039699/article/details/82110799
 *
 * @author ttp
 */
class MainForm extends JFrame {
    public MainForm() {
        // ---------------------------------------------主窗口设置
        setTitle("数据库工具集");
        setSize(800, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ----------------------------------------------暂时支持4张表
        JRadioButton s1 = new JRadioButton("1") {{
            setFont(new Font("宋体", Font.BOLD, 30));
        }};
        JRadioButton s2 = new JRadioButton("2", true) {{
            setFont(new Font("宋体", Font.BOLD, 30));
        }};
        JRadioButton s3 = new JRadioButton("3") {{
            setFont(new Font("宋体", Font.BOLD, 30));
        }};
        JRadioButton s4 = new JRadioButton("4") {{
            setFont(new Font("宋体", Font.BOLD, 30));
        }};
        new ButtonGroup() {{
            add(s1);
            add(s2);
            add(s3);
            add(s4);
        }};
        JToolBar jToolBar = new JToolBar() {{
            add(s1);
            add(s2);
            add(s3);
            add(s4);
        }};

        // ---------------------------------------------主窗口布局 for QBE
        JSplitPane jp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JLabel("QBE工具：请先选择你需要表的数量,然后点击确认!") {{
                    setFont(new Font("宋体", Font.BOLD, 30));
                }}, jToolBar);
        JSplitPane jp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jp, new ToolbarButton("确定", 50, 50, e -> {
//            setVisible(false);
            int num = 2;
            if (s1.isSelected()) {
                num = 1;
            }
            if (s2.isSelected()) {
                num = 2;
            }
            if (s3.isSelected()) {
                num = 3;
            }
            if (s4.isSelected()) {
                num = 4;
            }
            new NewForm(num);
        }));

        // ---------------------------------------------主窗口布局 for 数据库设计
        JSplitPane jp3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JLabel("关系数据库工具:点击下面的确认继续!") {{
                    setFont(new Font("宋体", Font.BOLD, 30));
                }}, new ToolbarButton("确定", 50, 50, e -> {
//            setVisible(false);
            new NewForm2();
        }));

        Container cp = getContentPane();
        JSplitPane jpRes = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jp2, jp3);
        cp.add(jpRes, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainForm();
    }
}