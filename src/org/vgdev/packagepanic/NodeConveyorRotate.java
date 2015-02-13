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

public class NodeConveyorRotate extends NodeConveyor implements Tool, Configurable, ActionListener, ItemListener {

  //an image displaying the node
  private static Image img;

  public NodeConveyorRotate() {
    //load the image if it is not already loaded
    if(img == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      img = tk.getImage(getClass().getResource("/png/NodeConveyorRotate.png"));
    }
  }

  public NodeConveyorRotate(int x, int y) {
    this();
    this.x = x;
    this.y = y;
    this.clickable = true;
  }

  @Override
  public void paint(Graphics2D g) {
    double theta = (dir == RIGHT) ? 0
                  :(dir == DOWN) ? Math.toRadians(90.0)
                  :(dir == LEFT) ? Math.toRadians(180.0)
                  :Math.toRadians(270.0);
    AffineTransform tf = new AffineTransform();
    tf.concatenate(AffineTransform.getRotateInstance(theta,(x+0.5)*PP.SZ,(y+0.5)*PP.SZ));
    tf.concatenate(AffineTransform.getTranslateInstance(x*PP.SZ,y*PP.SZ));
    g.drawImage(img,tf,null);
  }

  @Override
  public void writeToJSON(JSONObject json) {
    super.writeToJSON(json);
    json.put("type","NodeConveyorRotate");
  }

  @Override
  public JPanel createControls() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
    String[] labels = new String[]{"right","up","left","down"};
    JComboBox box = new JComboBox(labels);
    box.setSelectedIndex(dir);
    box.addActionListener(this);
    panel.add(box);
    JCheckBox check = new JCheckBox("Clickable",clickable);
    check.addItemListener(this);
    panel.add(check);
    JPanel dummy = new JPanel();
    dummy.add(panel);
    return dummy;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox box = (JComboBox)e.getSource();
    dir = strToDir((String)box.getSelectedItem());
    Container c = box.getParent();
    while(c.getParent() != null) c = c.getParent();
    c.repaint();
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    clickable = (e.getStateChange() == ItemEvent.SELECTED);
  }

  //Tool methods

  @Override
  public ImageIcon getIcon() {
    return new ImageIcon(img);
  }

  @Override
  public Tool newInstance() {
    return new NodeConveyorRotate();
  }

  @Override
  public void onClick(Main frame, PPLevel level, int x, int y) {
    level.nodes.add(new NodeConveyorRotate(x,y));
  }

}
