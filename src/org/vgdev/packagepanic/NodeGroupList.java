package org.vgdev.packagepanic;

import java.awt.geom.AffineTransform;
import java.awt.Point;
import java.awt.image.AffineTransformOp;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.json.JSONArray;
import org.json.JSONObject;

public class NodeGroupList extends NodeGroup implements Tool, Configurable {

  List<Point> list;
  Node proto;

  public NodeGroupList() {
    //load the image if it is not already loaded
    if(img == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      img = tk.getImage(getClass().getResource("/png/NodeGroupList.png"));
    }
    list = new ArrayList<Point>();
  }

  public NodeGroupList(Node proto) {
    this();
    this.x = proto.getX();
    this.y = proto.getY();
    this.proto = proto;
    list.add(new Point(proto.getX(),proto.getY()));
  }

  @Override
  public void paint(Graphics2D g) {
    for(Point point : list) {
      proto.setX(point.x);
      proto.setY(point.y);
      proto.paint(g);
    }
  }

  @Override
  public void writeToJSON(JSONObject json) {
    proto.writeToJSON(json);
    json.put("subtype",json.getString("type"));
    json.put("type","NodeGroupList");
    json.remove("x");
    json.remove("y");
    JSONArray jsonlist = new JSONArray();
    for(Point point : list) {
      JSONObject obj = new JSONObject();
      obj.put("x",point.x);
      obj.put("y",point.y);
      jsonlist.put(obj);
    }
    json.put("list",jsonlist);
  }

  @Override
  public Node readFromJSON(JSONObject json) {
    JSONArray jsonlist = json.getJSONArray("list");
    for(int i = 0; i < jsonlist.length(); ++i) {
      JSONObject obj = jsonlist.getJSONObject(i);
      list.add(new Point(obj.getInt("x"), obj.getInt("y")));
    }
    json.put("type",json.getString("subtype"));
    json.remove("subtype");
    json.put("x",list.get(0).x);
    json.put("y",list.get(0).y);
    proto = PPLevel.createNode(json);
    return this;
  }

  @Override
  public boolean containsTile(int x, int y) {
    return list.contains(new Point(x,y));
  }

  public void add(Point point) {
    list.add(point);
  }

  //Tool methods

  Image img;

  NodeGroupList ngl;

  @Override
  public ImageIcon getIcon() {
    return new ImageIcon(img);
  }

  @Override
  public Tool newInstance() {
    return new NodeGroupList();
  }

  @Override
  public void onClick(Main frame, PPLevel level, int x, int y) {
    if(proto == null) {
      for(Node node : level.nodes) {
        if(!(node instanceof NodeGroup) && node.getX() == x && node.getY() == y) {
          proto = node;
          level.nodes.remove(node);
          ngl = new NodeGroupList(proto);
          level.nodes.add(ngl);
          return;
        }
      }
    }
    else {
      ngl.add(new Point(x,y));
    }
  }

  //Configurable

  @Override
  public JPanel createControls() {
    if(proto instanceof Configurable) return ((Configurable)proto).createControls();
    else return new JPanel();
  }

}
