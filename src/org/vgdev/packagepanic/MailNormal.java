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

public class MailNormal extends Mail implements Tool, Configurable, ActionListener {

  //an image displaying the mail
  private static Image img;

  //color (http://en.wikipedia.org/wiki/Web_colors#HTML_color_names)
  private int color;

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
    color = 15;
  }

  @Override
  public void paint(Graphics2D g) {
    g.drawImage(img,x*PP.SZ,y*PP.SZ,null);
    g.setColor(ColorUtils.intToColor(color));
    g.fillRect(x*PP.SZ+25,y*PP.SZ+15,9,6);
  }

  @Override
  public void writeToJSON(JSONObject json) {
    super.writeToJSON(json);
    json.put("type","MailNormal");
    json.put("color",color);
  }

  @Override
  public Mail readFromJSON(JSONObject json) {
    super.readFromJSON(json);
    color = json.optInt("color",15);
    return this;
  }

  //Configurable

  public JPanel createControls() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
    //TODO: do this elegantly...
    String[] labels = new String[]{"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
    JComboBox box = new JComboBox(labels);
    box.setSelectedIndex(color);
    box.addActionListener(this);
    panel.add(box);
    JPanel dummy = new JPanel();
    dummy.add(panel);
    return dummy;
  }

  //ActionListener

  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox box = (JComboBox)e.getSource();
    color = Integer.parseInt((String)box.getSelectedItem());
    Container c = box.getParent();
    while(c.getParent() != null) c = c.getParent();
    c.repaint();
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
