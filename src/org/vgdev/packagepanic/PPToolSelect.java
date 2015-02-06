package org.vgdev.packagepanic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.GridLayout;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class PPToolSelect extends JToolBar implements ActionListener, MouseListener {

  //references to the global frame and level
  Main frame;
  PPLevel level;

  //columns of tools
  JPanel[] cols;

  //an instance of the tool currently selected
  private Tool currentTool;

  //a map of tools
  private Map<String,Tool> tools;

  public PPToolSelect(Main frame, PPLevel level) {

    //set references to global frame and level
    this.frame = frame;
    this.level = level;

    tools = new TreeMap<String,Tool>();

    cols = new JPanel[2];
    cols[0] = new JPanel(new GridLayout(7,1));
    cols[1] = new JPanel(new GridLayout(3,1));
    this.add(cols[0]);
    this.add(cols[1]);

    //create and add the buttons
    this.addTool("ToolNodeSelect",         new ToolNodeSelect(),     0);
    this.addTool("ToolNodeDelete",         new ToolNodeDelete(),     0);
    this.addTool("ToolNodeConveyorNormal", new NodeConveyorNormal(), 0);
    this.addTool("ToolNodeConveyorRotate", new NodeConveyorRotate(), 0);
    this.addTool("ToolNodeAirTable",       new NodeAirTable(),       0);
    this.addTool("ToolNodeGroupRect",      new NodeGroupRect(),      0);
    this.addTool("ToolNodeBin",            new NodeBin(),            0);

    this.addTool("ToolMailSelect",         new ToolMailSelect(),     1);
    this.addTool("ToolMailDelete",         new ToolMailDelete(),     1);
    this.addTool("ToolMailNormal",         new MailNormal(),         1);

    //slightly fancier GUI
    this.setRollover(true);

  }

  private void addTool(String name, Tool tool, int column) {
    JButton button = new JButton(tool.getIcon());
    button.setActionCommand(name);
    button.addActionListener(this);
    cols[column].add(button);
    tools.put(name,tool);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    //respond to button presses
    String cmd = e.getActionCommand();
    if(tools.containsKey(cmd)) currentTool = tools.get(cmd).newInstance();
    frame.repaint();

  }

  @Override
  public void mousePressed(MouseEvent event) {
    if(currentTool != null) {
      currentTool.onClick(frame,level,event.getX()/PP.SZ,event.getY()/PP.SZ);
      frame.repaint();
    }
  }

  @Override public void mouseEntered(MouseEvent event) {}
  @Override public void mouseExited(MouseEvent event) {}
  @Override public void mouseClicked(MouseEvent event) {}
  @Override public void mouseReleased(MouseEvent event) {}

}
