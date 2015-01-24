package org.vgdev.packagepanic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import org.json.JSONObject;

public class NodeBin extends Node implements Tool {

  //an image displaying the node
  private static Image img;

  public NodeBin() {
    //load the image if it is not already loaded
    if(img == null) {
      Toolkit tk = Toolkit.getDefaultToolkit();
      img = tk.getImage(getClass().getResource("/png/NodeBin.png"));
    }
  }

  public NodeBin(int x, int y) {
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
    json.put("type","NodeBin");
  }

  //Tool methods

  @Override
  public ImageIcon getIcon() {
    return new ImageIcon(img);
  }

  @Override
  public Tool newInstance() {
    return new NodeBin();
  }

  @Override
  public void onClick(Main frame, PPLevel level, int x, int y) {
    level.nodes.add(new NodeBin(x,y));
  }

}
