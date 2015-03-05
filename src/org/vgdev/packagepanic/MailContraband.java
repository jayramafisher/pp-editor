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

public class MailContraband extends Mail implements Tool, Configurable, ActionListener {

  //an image displaying the mail
  private static Image img;

  //1 == true
  //0 == random
  //-1== false
  private int is_contraband;

  public MailContraband() {
    //load the image if it is not already loaded
    if(img == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      img = tk.getImage(getClass().getResource("/png/MailContraband.png"));
    }
  }

  public MailContraband(int x, int y) {
    this();
    this.x = x;
    this.y = y;
    is_contraband = 0;
  }

  @Override
  public void paint(Graphics2D g) {
    g.drawImage(img,x*PP.SZ,y*PP.SZ,null);
  }

  @Override
  public void writeToJSON(JSONObject json) {
    super.writeToJSON(json);
    json.put("type","MailContraband");
    if(is_contraband != 0) json.put("is_contraband",is_contraband==1);
  }

  @Override
  public Mail readFromJSON(JSONObject json) {
    super.readFromJSON(json);
    is_contraband = json.has("is_contraband")?
      (json.getBoolean("is_contraband")?1:-1)
      :0;
    return this;
  }

  //Configurable

  public JPanel createControls() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
    String[] labels = new String[]{"true","rand","false"};
    JComboBox box = new JComboBox(labels);
    box.setSelectedIndex(is_contraband==1?0:is_contraband==0?1:2);
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
    is_contraband = (box.getSelectedItem().equals("true")) ? 1
      : (box.getSelectedItem().equals("rand")) ? 0 : -1;
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
    return new MailContraband();
  }

  @Override
  public void onClick(Main frame, PPLevel level, int x, int y) {
    level.mail.add(new MailContraband(x,y));
  }

}
