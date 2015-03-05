package org.vgdev.packagepanic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.json.JSONObject;

public class MailGarbage extends Mail implements Tool {

  //an image displaying the mail
  private static Image img;

  public MailGarbage() {
    //load the image if it is not already loaded
    if(img == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      img = tk.getImage(getClass().getResource("/png/MailGarbage.png"));
    }
  }

  public MailGarbage(int x, int y) {
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
    json.put("type","MailGarbage");
  }

  @Override
  public Mail readFromJSON(JSONObject json) {
    super.readFromJSON(json);
    return this;
  }

  //Tool methods

  @Override
  public ImageIcon getIcon() {
    return new ImageIcon(img);
  }

  @Override
  public Tool newInstance() {
    return new MailGarbage();
  }

  @Override
  public void onClick(Main frame, PPLevel level, int x, int y) {
    level.mail.add(new MailGarbage(x,y));
  }

}
