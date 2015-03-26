package org.vgdev.packagepanic;

import java.awt.FileDialog;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

public class PPLevel {

  //access to this object's members is safe only if ready is true
  private boolean ready;

  //where to save the level
  private String filename;

  //json objects for storing metadata and written notes
  private JSONObject meta;
  private JSONArray notes;

  //the level's nodes and mail objects
  public ArrayList<Node> nodes;
  public ArrayList<Mail> mail;

  public PPLevel() {
    //until new or load is selected, the level is unavailable.
    ready = false;
  }

  public boolean isReady() {
    return ready;
  }

  public void newFile(JFrame parent) {

    //prevent the level from being accessed
    ready = false;

    //setup metadata
    String internalName = JOptionPane.showInputDialog(parent,"Internal name:");
    String externalName = JOptionPane.showInputDialog(parent,"External name:");
    if(internalName == null || externalName == null || internalName.equals("") || externalName.equals("")) return;
    meta = new JSONObject();
    meta.put("format","PP-level");
    meta.put("format-version",1);
    meta.put("name-internal",internalName);
    meta.put("name-external",externalName);
    meta.put("times", new JSONArray(new int[]{0,0}));

    //initialize notes, nodes, and mail
    notes = new JSONArray();
    nodes = new ArrayList<Node>();
    mail = new ArrayList<Mail>();

    //the level is now ready for editing
    ready = true;
    parent.repaint();

  }

  public void load(JFrame parent) {

    //prevent the level from being accessed
    ready = false;

    //select a file
    FileDialog dialog = new FileDialog(parent,"Load",FileDialog.LOAD);
    dialog.setVisible(true);
    filename = dialog.getDirectory()+dialog.getFile();

    //if a file wasn't selected, cancel
    if(filename == null) return;

    if(PP.DEBUG) System.err.println("[DEBUG] Loading: "+filename);

    //read the file into a json object
    String content = "";
    try {
      content = new Scanner(new File(filename)).useDelimiter("\\Z").next();
    }
    catch(Exception e) {
      System.err.println("[ERROR] Failed to load: "+filename);
      e.printStackTrace();
      return;
    }
    JSONObject json = new JSONObject(content);

    //extract the metadata and notes
    meta = json.getJSONObject("meta");
    notes = json.getJSONArray("notes");

    //load the nodes
    nodes = new ArrayList<Node>();
    JSONArray jsonNodes = json.getJSONArray("nodes");
    for(int i = 0; i < jsonNodes.length(); ++i) {
      JSONObject node = jsonNodes.getJSONObject(i);
      nodes.add(createNode(node));
    }

    //load the mail
    mail = new ArrayList<Mail>();
    JSONArray jsonMail = json.getJSONArray("mail");
    for(int i = 0; i < jsonMail.length(); ++i) {
      JSONObject mailobj = jsonMail.getJSONObject(i);
      if(mailobj.getString("type").equals("MailNormal")) {
        mail.add(new MailNormal().readFromJSON(mailobj));
      }
      else if(mailobj.getString("type").equals("MailMagnetic")) {
        mail.add(new MailMagnetic().readFromJSON(mailobj));
      }
      else if(mailobj.getString("type").equals("MailGarbage")) {
        mail.add(new MailGarbage().readFromJSON(mailobj));
      }
      else if(mailobj.getString("type").equals("MailContraband")) {
        mail.add(new MailContraband().readFromJSON(mailobj));
      }
    }

    //the level is now ready for editing
    ready = true;
    parent.repaint();

  }

  public void save(JFrame parent) {

    //if we don't have a file, save as instead
    if(filename == null) {
      this.saveAs(parent);
      return;
    }

    if(PP.DEBUG) System.err.println("[DEBUG] Saving: "+filename);

    //create a json object to write to file
    JSONObject json = new JSONObject();

    //append the metadata
    json.put("meta",meta);

    //append the nodes
    JSONArray jsonNodes = new JSONArray();
    for(Node node : nodes) {
      JSONObject jsonNode = new JSONObject();
      node.writeToJSON(jsonNode);
      jsonNodes.put(jsonNode);
    }
    json.put("nodes",jsonNodes);

    //append the mail
    JSONArray jsonMail = new JSONArray();
    for(Mail mailobj : mail) {
      JSONObject jsonMailobj = new JSONObject();
      mailobj.writeToJSON(jsonMailobj);
      jsonMail.put(jsonMailobj);
    }
    json.put("mail",jsonMail);

    //append the notes
    json.put("notes",notes);

    //write to file
    try {
      PrintWriter out = new PrintWriter(filename);
      out.print(json);
      out.close();
    }
    catch(Exception e) {
      System.err.println("[ERROR] Failed to save: "+filename);
      e.printStackTrace();
      return;
    }

  }

  public void saveAs(JFrame parent) {

    //select a file
    FileDialog dialog = new FileDialog(parent,"Save As",FileDialog.SAVE);
    dialog.setVisible(true);
    filename = dialog.getDirectory()+dialog.getFile();

    //if a file wasn't selected, cancel
    if(filename == null) return;

    //otherwise, piggyback off of the save function
    this.save(parent);

  }

  public static Node createNode(JSONObject node) {
    if(node.getString("type").equals("NodeBin")) {
      return new NodeBin().readFromJSON(node);
    }
    else if(node.getString("type").equals("NodeConveyorNormal")) {
      return new NodeConveyorNormal().readFromJSON(node);
    }
    else if(node.getString("type").equals("NodeConveyorRotate")) {
      return new NodeConveyorRotate().readFromJSON(node);
    }
    else if(node.getString("type").equals("NodeAirTable")) {
      return new NodeAirTable().readFromJSON(node);
    }
    else if(node.getString("type").equals("NodeMagnet")) {
      return new NodeMagnet().readFromJSON(node);
    }
    else if(node.getString("type").equals("NodeChute")) {
      return new NodeChute().readFromJSON(node);
    }
    else if(node.getString("type").equals("NodeBarrier")) {
      return new NodeBarrier().readFromJSON(node);
    }
    else if(node.getString("type").equals("NodeGroupRect")) {
      return new NodeGroupRect().readFromJSON(node);
    }
    else if(node.getString("type").equals("NodeGroupList")) {
      return new NodeGroupList().readFromJSON(node);
    }
    else if(node.getString("type").equals("NodeIncinerator")) {
      return new NodeIncinerator().readFromJSON(node);
    }
    else if(node.getString("type").equals("NodeHolder")) {
      return new NodeHolder().readFromJSON(node);
    }
    else if(node.getString("type").equals("NodeSlide")) {
      return new NodeSlide().readFromJSON(node);
    }
    else return null;
  }

  public void setInternalName(JFrame parent) {
    String internalName = JOptionPane.showInputDialog(parent,"Internal name:");
    meta.put("name-internal",internalName);
  }

  public void setExternalName(JFrame parent) {
    String externalName = JOptionPane.showInputDialog(parent,"External name:");
    meta.put("name-external",externalName);
  }

  public void setTimes(JFrame parent) {
    String time1 = JOptionPane.showInputDialog(parent,"Time to lose first star (in 33ms ticks):");
    String time2 = JOptionPane.showInputDialog(parent,"Time to lose second star (in 33ms ticks):");
    try {
      meta.put("times",new JSONArray(new int[]{Integer.parseInt(time1),Integer.parseInt(time2)}));
    } catch(Exception exc) {
      JOptionPane.showMessageDialog(parent,"Error: failed to parse times.");
    }
  }

}



















