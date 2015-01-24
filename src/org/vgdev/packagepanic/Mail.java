package org.vgdev.packagepanic;

import java.awt.Graphics2D;
import org.json.JSONObject;

public abstract class Mail {

  //0-indexed grid cell
  protected int x, y;

  public abstract void paint(Graphics2D g);

  public Mail readFromJSON(JSONObject json) {
    x = json.optInt("x",0);
    y = json.optInt("y",0);
    return this;
  }

  public void writeToJSON(JSONObject json) {
    json.put("x",x);
    json.put("y",y);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

}
