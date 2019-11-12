package edu.cs3500.spreadsheets.view;

import java.awt.*;

import edu.cs3500.spreadsheets.model.Coord;

public class ColumnPanel extends javax.swing.JPanel {

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (int x = 20; x <= 1220; x += 60) {
      g.drawRect(x, 0, 60, 20);
      g.drawString(Coord.colIndexToName((x - 20) / 60 + 1), x + 27, 15);
    }
  }
}
