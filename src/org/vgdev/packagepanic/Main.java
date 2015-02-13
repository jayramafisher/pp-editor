package org.vgdev.packagepanic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Main extends JFrame {

  //representation of the level - instantiated only once
  private PPLevel level;

  //GUI elements
  private PPToolBar toolbar;
  private PPToolSelect toolselect;
  private PPPanel panel;

  public Main() {

    //call the JFrame ctor, setting the title
    super("Package Panic! Level Editor");

    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch(Exception e) { System.out.println("[PP-EDITOR] [WARNING] Failed to create look-and-feel.");}

    //create a blank level - this is the only instantiation
    level = new PPLevel();

    JPanel north = new JPanel();
    north.setLayout(new BoxLayout(north,BoxLayout.Y_AXIS));

    //add the toolbar for new/load/save/save as operations
    toolbar = new PPToolBar(this,level);
    north.add(toolbar);

    //add the toolbar for selecting tools
    toolselect = new PPToolSelect(this,level);
    north.add(toolselect);

    this.getContentPane().add(north,BorderLayout.NORTH);

    //add the panel for displaying and interaction with the level
    panel = new PPPanel(this,level);
    panel.addMouseListener(toolselect);
    this.getContentPane().add(panel,BorderLayout.CENTER);

    //JFrame stuff
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.pack();
    this.setVisible(true);

  }

  @Override
  public void repaint() {
    this.revalidate();
    this.pack();
    panel.repaint();
  }

  public static void main(String[] args) {

    //create the frame
    Main frame = new Main();

  }

}
