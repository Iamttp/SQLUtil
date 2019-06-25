package MyUIUtil;

import javax.swing.*;

public class textAreaRes3 extends JToolBar {
    public ToolbarButton butRes1;
    public ToolbarButton butRes2;

    public textAreaRes3(String butStr1, String butStr2) {
        butRes1 = new ToolbarButton(butStr1, 200, 100);
        butRes2 = new ToolbarButton(butStr2, 200, 100);
        this.add(butRes1);
        this.add(butRes2);
        this.setOrientation(VERTICAL);
        this.setFloatable(false);
    }
}
