package edu.cs3500.spreadsheets.view;

import java.awt.*;

public class SpreadsheetPanel extends javax.swing.JPanel {

  final static int CELL_WIDTH = 64;
  final static int CELL_HEIGHT = 21;

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (int x = 0; x < BasicWorksheetGraphicalView.GRID_CELLS; x ++)  {
      for (int y = 0; y < BasicWorksheetGraphicalView.GRID_CELLS; y ++) {
        g.drawRect(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
      }
    }
  }

}
