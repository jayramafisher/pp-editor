package org.vgdev.packagepanic;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.json.JSONObject;

public class NodeSlide extends Node implements Tool, Configurable, ItemListener {

  //an image displaying the node
  private static Image img;

  boolean up, down, left, right;

  public NodeSlide() {
    //load the image if it is not already loaded
    if(img == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      img = tk.getImage(getClass().getResource("/png/NodeSlide.png"));
    }
    up = true;
    down = true;
    left = true;
    right = true;
  }

  public NodeSlide(int x, int y) {
    this();
    this.x = x;
    this.y = y;
  }

  @Override
  public void paint(Graphics2D g) {
    g.drawImage(img,PP.SZ*x,PP.SZ*y,null);
  }

  @Override
  public void writeToJSON(JSONObject json) {
    super.writeToJSON(json);
    json.put("type","NodeSlide");
    json.put("up",up);
    json.put("down",down);
    json.put("left",left);
    json.put("right",right);
  }

  @Override
  public Node readFromJSON(JSONObject json) {
    super.readFromJSON(json);
    up = json.optBoolean("up",true);
    down = json.optBoolean("down",true);
    left = json.optBoolean("left",true);
    right = json.optBoolean("right",true);
    return this;
  }

  //Configurable

  @Override
  public JPanel createControls() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
    String[] labels = new String[]{"open up", "open down", "open left", "open right"};
    boolean[] bools = new boolean[]{up, down, left, right};
    for(int i = 0; i < labels.length; ++i) {
      JCheckBox check = new JCheckBox(labels[i],bools[i]);
      check.addItemListener(this);
      panel.add(check);
    }
    JPanel dummy = new JPanel();
    dummy.add(panel);
    return dummy;
  }

  //Item Listener

  @Override
  public void itemStateChanged(ItemEvent e) {
    String src = ((JCheckBox)e.getSource()).getText();
    if(src.equals("open up")) up = (e.getStateChange() == ItemEvent.SELECTED);
    else if(src.equals("open down")) down = (e.getStateChange() == ItemEvent.SELECTED);
    else if(src.equals("open left")) left = (e.getStateChange() == ItemEvent.SELECTED);
    else if(src.equals("open right")) right = (e.getStateChange() == ItemEvent.SELECTED);
    else System.out.println("[PP-EDITOR] [ERROR] A NodeSlide configurable had an event with an unrecognized source.");
  }

  //Tool methods

  @Override
  public ImageIcon getIcon() {
    return new ImageIcon(img);
  }

  @Override
  public Tool newInstance() {
    return new NodeSlide();
  }

  @Override
  public void onClick(Main frame, PPLevel level, int x, int y) {
    level.nodes.add(new NodeSlide(x,y));
  }

}
