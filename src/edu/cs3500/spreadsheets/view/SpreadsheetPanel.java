package edu.cs3500.spreadsheets.view;

import java.awt.*;

public class SpreadsheetPanel extends javax.swing.JPanel {

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (int x = -1; x <= 1220; x += 60)  {
      for (int y = -1; y <=200; y +=20) {
        g.drawRect(x, y, 60, 20);
      }
    }



  }


}
