package org.vgdev.packagepanic;

import java.awt.Color;

public class ColorUtils {

  private static final Color[] colors = new Color[]{
    new Color(0.0f,0.0f,0.0f),	//black
    new Color(0.0f,0.0f,0.5f),	//navy
    new Color(0.0f,0.5f,0.0f),	//green
    new Color(0.0f,0.5f,0.5f),	//cyan
    new Color(0.5f,0.0f,0.0f),	//red
    new Color(0.5f,0.0f,0.5f),	//purple
    new Color(0.5f,0.5f,0.0f),	//olive
    new Color(0.75f,0.75f,0.75f),	//silver
    new Color(0.5f,0.5f,0.5f),	//gray
    new Color(0.0f,0.0f,1.0f),	//blue
    new Color(0.0f,1.0f,0.0f),	//lime
    new Color(0.0f,1.0f,1.0f),	//aqua
    new Color(1.0f,0.0f,0.0f),	//red
    new Color(1.0f,0.0f,1.0f),	//fuchsia
    new Color(1.0f,0.0f,0.0f),	//yellow
    new Color(1.0f,1.0f,1.0f)	//white
  };

  public static Color intToColor(int color) {
    return colors[color];
  }

}
