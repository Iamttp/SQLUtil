import javax.swing.*;
import java.awt.*;

public class QForm extends JFrame {
    public QForm() {
        // ---------------------------------------------主窗口设置
        setTitle("");
        setSize(500, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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
        JRadioButton s5 = new JRadioButton("5") {{
            setFont(new Font("宋体", Font.BOLD, 30));
        }};
        ButtonGroup bg = new ButtonGroup() {{
            add(s1);
            add(s2);
            add(s3);
            add(s4);
            add(s5);
        }};
        JToolBar jToolBar = new JToolBar() {{
            add(s1);
            add(s2);
            add(s3);
            add(s4);
//            add(s5);
        }};

        Container cp = getContentPane();
        JSplitPane jp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JLabel("选择你需要表的数量") {{
            setFont(new Font("宋体", Font.BOLD, 30));
        }}, jToolBar);
        JSplitPane jp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jp, new ToolbarButton("确定", 50, 50, e -> {
            setVisible(false);
            int num = 2;
            if (s1.isSelected())
                num = 1;
            if (s2.isSelected())
                num = 2;
            if (s3.isSelected())
                num = 3;
            if (s4.isSelected())
                num = 4;
            if (s5.isSelected())
                num = 5;
            new NewForm(num);
        }));
        cp.add(jp2, BorderLayout.CENTER);
        setVisible(true);
    }
}

class Main {
    public static void main(String[] args) {
        new QForm();
    }
}