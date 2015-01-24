package org.vgdev.packagepanic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import org.json.JSONObject;

public class MailNormal extends Mail implements Tool {

  //an image displaying the mail
  private static Image img;

  public MailNormal() {
    //load the image if it is not already loaded
    if(img == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      img = tk.getImage(getClass().getResource("/png/MailNormal.png"));
    }
  }

  public MailNormal(int x, int y) {
    this();
    this.x = x;
    this.y = y;
  }

  @Override
  public void paint(Graphics2D g) {
    g.drawImage(img,x*PP.SZ,y*PP.SZ,null);
  }

  @Override
  public void writeToJSON(JSONObject json) {
    super.writeToJSON(json);
    json.put("type","MailNormal");
  }

  //Tool methods

  @Override
  public ImageIcon getIcon() {
    return new ImageIcon(img);
  }

  @Override
  public Tool newInstance() {
    return new MailNormal();
  }

  @Override
  public void onClick(Main frame, PPLevel level, int x, int y) {
    level.mail.add(new MailNormal(x,y));
  }

}
