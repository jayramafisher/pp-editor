package org.vgdev.packagepanic;

import java.awt.Graphics2D;
import org.json.JSONObject;

public abstract class Node {

  //0-indexed grid cell
  protected int x, y;

  protected boolean clickable;

  public abstract void paint(Graphics2D g);

  public Node readFromJSON(JSONObject json) {
    x = json.optInt("x",0);
    y = json.optInt("y",0);
    clickable = json.optBoolean("clickable",true);
    return this;
  }

  public void writeToJSON(JSONObject json) {
    json.put("x",x);
    json.put("y",y);
    json.put("clickable",clickable);
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
