package org.vgdev.packagepanic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class PPToolBar extends JToolBar implements ActionListener {

  //references to the global frame and level
  Main frame;
  PPLevel level;

  public PPToolBar(Main frame, PPLevel level) {

    //set references to global frame and level
    this.frame = frame;
    this.level = level;

    //create and add the buttons
    String[] buttonNames = new String[]{"New","Load","Save","Save As","Set Internal Name","Set External Name","Set Times"};
    for(String buttonName: buttonNames) {
      JButton button = new JButton(buttonName);
      button.setActionCommand(buttonName);
      button.addActionListener(this);
      this.add(button);
    }

    //slightly fancier GUI
    this.setRollover(true);

  }

  public void actionPerformed(ActionEvent e) {

    //respond to button presses
    if(e.getActionCommand().equals("New")) level.newFile(frame);
    else if(e.getActionCommand().equals("Load")) level.load(frame);
    else if(e.getActionCommand().equals("Save")) level.save(frame);
    else if(e.getActionCommand().equals("Save As")) level.saveAs(frame);
    else if(e.getActionCommand().equals("Set Internal Name")) level.setInternalName(frame);
    else if(e.getActionCommand().equals("Set External Name")) level.setExternalName(frame);
    else if(e.getActionCommand().equals("Set Times")) level.setTimes(frame);
    frame.repaint();

  }

}
