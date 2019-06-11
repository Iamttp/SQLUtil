package MyUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * 自定义按钮
 */
public class ToolbarButton extends JButton {
    public ToolbarButton(String text, int width, int heigth, ActionListener l) {
        this.setText(text);
        addActionListener(l);
        this.setFont(new Font("宋体", Font.BOLD, 30));
        // 按钮大小设置
        setPreferredSize(new Dimension(width, heigth));
    }

    ToolbarButton(String text, int width, int heigth) {
        this.setText(text);
        this.setFont(new Font("宋体", Font.BOLD, 30));
        // 按钮大小设置
        setPreferredSize(new Dimension(width, heigth));
    }
}

