package org.vgdev.packagepanic;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class ToolNodeSelect implements Tool {

  private static ImageIcon icon;

  public ImageIcon getIcon() {
    if(icon == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      icon = new ImageIcon(tk.getImage(getClass().getResource("/png/ToolNodeSelect.png")));
    }
    return icon;
  }

  public Tool newInstance() {
    return new ToolNodeSelect();
  }

  public void onClick(Main frame, PPLevel level, int x, int y) {
    Component cmp = ((BorderLayout)frame.getContentPane().getLayout()).getLayoutComponent(BorderLayout.EAST);
    if(cmp != null) frame.getContentPane().remove(cmp);
    for(Node node : level.nodes) {
      if(node.getX() == x && node.getY() == y || node instanceof NodeGroup && ((NodeGroup)node).containsTile(x,y)) {
        if(node instanceof Configurable) {
          Configurable conf = (Configurable)node;
          frame.getContentPane().add(conf.createControls(),BorderLayout.EAST);
        }
      }
    }
    frame.repaint();
  }

}
