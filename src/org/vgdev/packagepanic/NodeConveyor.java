package org.vgdev.packagepanic;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.json.JSONObject;

public abstract class NodeConveyor extends Node implements Configurable, ActionListener {

  //node facing directions
  protected static final int RIGHT = 0;
  protected static final int UP = 1;
  protected static final int LEFT = 2;
  protected static final int DOWN = 3;

  //the direction of this node
  protected int dir;

  @Override
  public Node readFromJSON(JSONObject json) {
    super.readFromJSON(json);
    String dirString = json.optString("dir");
    if(dirString.equals("up")) dir = UP;
    else if(dirString.equals("left")) dir = LEFT;
    else if(dirString.equals("down")) dir = DOWN;
    else dir = RIGHT;
    return this;
  }

  @Override
  public void writeToJSON(JSONObject json) {
    super.writeToJSON(json);
    json.put("dir",
      dir == UP ? "up" :
      dir == LEFT ? "left" :
      dir == DOWN ? "down" :
      "right"
    );
  }

  @Override
  public JPanel createControls() {
    JPanel panel = new JPanel();
    String[] labels = new String[]{"right","up","left","down"};
    JComboBox box = new JComboBox(labels);
    box.setSelectedIndex(dir);
    box.addActionListener(this);
    panel.add(box);
    return panel;
  }

  private String dirToStr(int dir) {
    if(dir == RIGHT) return "right";
    if(dir == LEFT) return "left";
    if(dir == DOWN) return "down";
    if(dir == UP) return "up";
    return "null";
  }

  private int strToDir(String str) {
    if(str.equals("right")) return RIGHT;
    if(str.equals("left")) return LEFT;
    if(str.equals("down")) return DOWN;
    if(str.equals("up")) return UP;
    return -1;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox box = (JComboBox)e.getSource();
    dir = strToDir((String)box.getSelectedItem());
    Container c = box.getParent();
    while(c.getParent() != null) c = c.getParent();
    c.repaint();
  }

}
