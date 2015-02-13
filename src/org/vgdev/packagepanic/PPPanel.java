package org.vgdev.packagepanic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import org.json.JSONArray;
import org.json.JSONObject;

public class PPPanel extends JPanel {

  //an image displaying an empty node
  private static Image imgBlank;

  //references to the global frame and level
  private Main frame;
  private PPLevel level;

  public PPPanel(Main frame, PPLevel level) {

    //set references to global frame and level
    this.frame = frame;
    this.level = level;

    //set the GUI element size
    this.setPreferredSize(new Dimension(PP.NX*PP.SZ,PP.NY*PP.SZ));

    //load the image displaying an empty node
    Toolkit tk = Toolkit.getDefaultToolkit();
    imgBlank = tk.getImage(getClass().getResource("/png/blank.png"));

  }

  @Override
  public void paint(Graphics g) {

    //if the level isn't ready, don't draw it
    if(!level.isReady()) return;

    //draw the background, i.e. empty nodes
    for(int i = 0; i < PP.NX*PP.NY; ++i) {
      g.drawImage(imgBlank,(i%PP.NX)*PP.SZ,(i/PP.NX)*PP.SZ,null);
    }

    //draw the nodes and mail
    for(Node node : level.nodes) node.paint((Graphics2D)g);
    for(Mail mail : level.mail) mail.paint((Graphics2D)g);

  }

}
