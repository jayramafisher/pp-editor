package org.vgdev.packagepanic;

import javax.swing.ImageIcon;

public interface Tool {

  public ImageIcon getIcon();
  public Tool newInstance();
  public void onClick(Main frame, PPLevel level, int x, int y);

}
