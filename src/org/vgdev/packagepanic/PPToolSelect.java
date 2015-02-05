package org.vgdev.packagepanic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class PPToolSelect extends JToolBar implements ActionListener, MouseListener {

  //references to the global frame and level
  Main frame;
  PPLevel level;

  //an instance of the tool currently selected
  private Tool currentTool;

  //a map of tools
  private Map<String,Tool> tools;

  public PPToolSelect(Main frame, PPLevel level) {

    super(null,JToolBar.VERTICAL);

    //set references to global frame and level
    this.frame = frame;
    this.level = level;

    tools = new TreeMap<String,Tool>();

    //create and add the buttons
    this.addTool("ToolNodeSelect", new ToolNodeSelect());
    this.addTool("ToolMailSelect", new ToolMailSelect());
    this.addTool("ToolNodeDelete", new ToolNodeDelete());
    this.addTool("ToolMailDelete", new ToolMailDelete());
    this.addTool("ToolNodeConveyorNormal", new NodeConveyorNormal());
    this.addTool("ToolNodeGroupRect", new NodeGroupRect());
    this.addTool("ToolNodeBin", new NodeBin());
    this.addTool("ToolMailNormal", new MailNormal());

    //slightly fancier GUI
    this.setRollover(true);

  }

  private void addTool(String name, Tool tool) {
    JButton button = new JButton(tool.getIcon());
    button.setActionCommand(name);
    button.addActionListener(this);
    this.add(button);
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
