package edu.cs3500.spreadsheets.view;

import java.awt.*;

public class RowPanel extends javax.swing.JPanel {

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

      for (int y = 0; y <=200; y +=20) {
        g.drawRect(0, y, 20, 20);
        g.drawString(String.valueOf((y / 20) + 1), 5, (y + 17));
      }
  }


}
