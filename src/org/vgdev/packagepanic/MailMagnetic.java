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

public class MailMagnetic extends Mail implements Tool, Configurable, ActionListener {

  //an image displaying the mail
  private static Image img;

  private int polarity;

  public MailMagnetic() {
    //load the image if it is not already loaded
    if(img == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      img = tk.getImage(getClass().getResource("/png/MailMagnetic.png"));
    }
  }

  public MailMagnetic(int x, int y) {
    this();
    this.x = x;
    this.y = y;
    polarity = 1;
  }

  @Override
  public void paint(Graphics2D g) {
    g.drawImage(img,x*PP.SZ,y*PP.SZ,null);
  }

  @Override
  public void writeToJSON(JSONObject json) {
    super.writeToJSON(json);
    json.put("type","MailMagnetic");
    json.put("polarity",polarity);
  }

  @Override
  public Mail readFromJSON(JSONObject json) {
    super.readFromJSON(json);
    polarity = json.optInt("polarity",1);
    return this;
  }

  //Configurable

  public JPanel createControls() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
    String[] labels = new String[]{"+1","-1"};
    JComboBox box = new JComboBox(labels);
    box.setSelectedIndex(polarity==1?0:1);
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
    polarity = (box.getSelectedItem().equals("+1"))?1:-1;
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
    return new MailMagnetic();
  }

  @Override
  public void onClick(Main frame, PPLevel level, int x, int y) {
    level.mail.add(new MailMagnetic(x,y));
  }

}
