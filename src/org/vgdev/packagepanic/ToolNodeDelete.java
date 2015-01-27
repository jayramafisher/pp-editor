package org.vgdev.packagepanic;

import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class ToolNodeDelete implements Tool {

  private static ImageIcon icon;

  @Override
  public ImageIcon getIcon() {
    if(icon == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      icon = new ImageIcon(tk.getImage(getClass().getResource("/png/ToolNodeDelete.png")));
    }
    return icon;
  }

  @Override
  public Tool newInstance() {
    return new ToolNodeDelete();
  }

  @Override
  public void onClick(Main frame, PPLevel level, int x, int y) {
    for(Node node : level.nodes) {
      if(node.getX() == x && node.getY() == y || node instanceof NodeGroup && ((NodeGroup)node).containsTile(x,y)) {
        level.nodes.remove(node);
        return;
      }
    }
  }

}
