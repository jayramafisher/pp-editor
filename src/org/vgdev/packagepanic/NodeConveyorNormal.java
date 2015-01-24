package org.vgdev.packagepanic;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import org.json.JSONObject;

public class NodeConveyorNormal extends NodeConveyor implements Tool {

  //an image displaying the node
  private static Image img;

  public NodeConveyorNormal() {
    //load the image if it is not already loaded
    if(img == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      img = tk.getImage(getClass().getResource("/png/NodeConveyorNormal.png"));
    }
  }

  public NodeConveyorNormal(int x, int y) {
    this();
    this.x = x;
    this.y = y;
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
    json.put("type","NodeConveyorNormal");
  }

  //Tool methods

  @Override
  public ImageIcon getIcon() {
    return new ImageIcon(img);
  }

  @Override
  public Tool newInstance() {
    return new NodeConveyorNormal();
  }

  @Override
  public void onClick(Main frame, PPLevel level, int x, int y) {
    level.nodes.add(new NodeConveyorNormal(x,y));
  }

}
