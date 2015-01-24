package org.vgdev.packagepanic;

import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class ToolMailDelete implements Tool {

  private static ImageIcon icon;

  @Override
  public ImageIcon getIcon() {
    if(icon == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      icon = new ImageIcon(tk.getImage(getClass().getResource("/png/ToolMailDelete.png")));
    }
    return icon;
  }

  @Override
  public Tool newInstance() {
    return new ToolMailDelete();
  }

  @Override
  public void onClick(Main frame, PPLevel level, int x, int y) {
    for(Mail mail : level.mail) {
      if(mail.getX() == x && mail.getY() == y) {
        level.mail.remove(mail);
        return;
      }
    }
  }

}
