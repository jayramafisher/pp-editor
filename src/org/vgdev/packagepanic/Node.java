package org.vgdev.packagepanic;

import java.awt.Graphics2D;
import org.json.JSONObject;

public abstract class Node {

  //0-indexed grid cell
  protected int x, y;

  public abstract void paint(Graphics2D g);

  public Node readFromJSON(JSONObject json) {
    y = json.optInt("x",0);
    x = json.optInt("y",0);
    return this;
  }

  public void writeToJSON(JSONObject json) {
    json.put("x",y);
    json.put("y",x);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

}
