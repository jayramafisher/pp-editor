package org.vgdev.packagepanic;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class ToolMailSelect implements Tool {

  private static ImageIcon icon;

  public ImageIcon getIcon() {
    if(icon == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      icon = new ImageIcon(tk.getImage(getClass().getResource("/png/ToolMailSelect.png")));
    }
    return icon;
  }

  public Tool newInstance() {
    return new ToolMailSelect();
  }

  public void onClick(Main frame, PPLevel level, int x, int y) {
    Component cmp = ((BorderLayout)frame.getContentPane().getLayout()).getLayoutComponent(BorderLayout.EAST);
    if(cmp != null) frame.getContentPane().remove(cmp);
    for(Mail mail : level.mail) {
      if(mail.getX() == x && mail.getY() == y) {
        if(mail instanceof Configurable) {
          Configurable conf = (Configurable)mail;
          frame.getContentPane().add(conf.createControls(),BorderLayout.EAST);
        }
      }
    }
    frame.repaint();
  }

}
