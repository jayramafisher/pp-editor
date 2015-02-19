package org.vgdev.packagepanic;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.json.JSONObject;

public class NodeGroupRect extends NodeGroup implements Tool, Configurable {

  int x2, y2;
  Node proto;

  public NodeGroupRect() {
    //load the image if it is not already loaded
    if(img == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      img = tk.getImage(getClass().getResource("/png/NodeGroupRect.png"));
    }
  }

  public NodeGroupRect(int x1, int y1, int x2, int y2, Node proto) {
    this();
    this.x = Math.min(x1,x2);
    this.y = Math.min(y1,y2);
    this.x2 = Math.max(x1,x2);
    this.y2 = Math.max(y1,y2);
    this.proto = proto;
  }

  @Override
  public void paint(Graphics2D g) {
    for(int i = x; i <= x2; ++i) {
      for(int j = y; j <= y2; ++j) {
        proto.setX(i);
        proto.setY(j);
        proto.paint(g);
      }
    }
  }

  @Override
  public void writeToJSON(JSONObject json) {
    proto.writeToJSON(json);
    json.put("subtype",json.getString("type"));
    json.put("type","NodeGroupRect");
    json.remove("x");
    json.remove("y");
    json.put("x1",x);
    json.put("y1",y);
    json.put("x2",x2);
    json.put("y2",y2);
  }

  @Override
  public Node readFromJSON(JSONObject json) {
    this.x = json.getInt("x1");
    this.y = json.getInt("y1");
    this.x2 = json.getInt("x2");
    this.y2 = json.getInt("y2");
    json.put("type",json.getString("subtype"));
    json.remove("subtype");
    json.remove("x1");
    json.remove("y1");
    json.remove("x2");
    json.remove("y2");
    json.put("x",y);
    json.put("y",x);
    proto = PPLevel.createNode(json);
    return this;
  }

  @Override
  public boolean containsTile(int x, int y) {
    return this.x <= x && x <= this.x2 && this.y <= y && y <= this.y2;
  }

  //Tool methods

  Image img;

  @Override
  public ImageIcon getIcon() {
    return new ImageIcon(img);
  }

  @Override
  public Tool newInstance() {
    return new NodeGroupRect();
  }

  @Override
  public void onClick(Main frame, PPLevel level, int x, int y) {
    if(proto == null) {
      for(Node node : level.nodes) {
        if(!(node instanceof NodeGroup) &&node.getX() == x && node.getY() == y) {
          proto = node;
          this.x = x;
          this.y = y;
          return;
        }
      }
    }
    else {
      level.nodes.remove(proto);
      level.nodes.add(new NodeGroupRect(this.x,this.y,x,y,proto));
      proto = null;
    }
  }

  //Configurable

  @Override
  public JPanel createControls() {
    if(proto instanceof Configurable) return ((Configurable)proto).createControls();
    else return new JPanel();
  }

}
