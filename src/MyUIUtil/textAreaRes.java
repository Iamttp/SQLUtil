package MyUIUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 自定义显示框，加个按钮
 */
public class textAreaRes extends JToolBar {
    public JTextArea textArea = new JTextArea();
    public ToolbarButton butRes;

    public textAreaRes() {
        butRes = new ToolbarButton("结果", 100, 100);
        textArea.setFont(new Font("宋体", Font.BOLD, 30));
        this.add(textArea);

        JToolBar sonToolBar = new JToolBar();
        sonToolBar.add(butRes);
        sonToolBar.setFloatable(false);
        sonToolBar.setOrientation(VERTICAL);
        this.add(sonToolBar);

        this.setFloatable(false);
    }

    textAreaRes(String butStr) {
        butRes = new ToolbarButton(butStr, 100, 100);
        textArea.setFont(new Font("宋体", Font.BOLD, 30));
        this.add(textArea);

        JToolBar sonToolBar = new JToolBar();
        sonToolBar.add(butRes);
        sonToolBar.setFloatable(false);
        sonToolBar.setOrientation(VERTICAL);
        this.add(sonToolBar);

        this.setFloatable(false);
    }
}
