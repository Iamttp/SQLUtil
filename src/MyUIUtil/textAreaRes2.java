package MyUIUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 自定义显示框，加3个按钮以及一个勾选框
 */
public class textAreaRes2 extends JToolBar {
    public JTextArea textArea = new JTextArea();
    public ToolbarButton butRes1;
    public ToolbarButton butRes2;
    public ToolbarButton butRes3;

    public JRadioButton s1 = new JRadioButton("显示详细信息") {{
        setFont(new Font("宋体", Font.BOLD, 30));
    }};

    public textAreaRes2(String butStr1, String butStr2, String butStr3) {
        butRes1 = new ToolbarButton(butStr1, 200, 100);
        butRes2 = new ToolbarButton(butStr2, 200, 100);
        butRes3 = new ToolbarButton(butStr3, 200, 100);
        textArea.setFont(new Font("宋体", Font.BOLD, 30));
        this.add(textArea);

        JToolBar sonToolBar = new JToolBar();
        sonToolBar.add(s1);
        sonToolBar.add(butRes1);
        sonToolBar.add(butRes2);
        sonToolBar.add(butRes3);
        sonToolBar.setFloatable(false);
        sonToolBar.setOrientation(VERTICAL);
        this.add(sonToolBar);

        this.setFloatable(false);
    }
}
