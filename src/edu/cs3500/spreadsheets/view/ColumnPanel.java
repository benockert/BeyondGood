package edu.cs3500.spreadsheets.view;

import java.awt.*;

import edu.cs3500.spreadsheets.model.Coord;

public class ColumnPanel extends javax.swing.JPanel {

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (int x = 0; x < BasicWorksheetGraphicalView.GRID_CELLS; x++) {
      // draws a number of rectangles equal to the specified number of column cells side by side
      // along the x-axis
      g.drawRect(x * SpreadsheetPanel.CELL_WIDTH,-1, SpreadsheetPanel.CELL_WIDTH,
              SpreadsheetPanel.CELL_HEIGHT);
      // sets the font size to be 14 and bolds the header text
      g.setFont(new Font("default", Font.BOLD, 14));
      // draws the text displayed in the column header (column letter) and aligns it based on
      // the width of the text
      if (x < 26) {
        g.drawString(Coord.colIndexToName(x + 1), x * 64 + 28,
                SpreadsheetPanel.CELL_HEIGHT / 2 + 6);
      } else if (x < 26 * 26) {
        g.drawString(Coord.colIndexToName(x + 1), x * 64 + 24,
                SpreadsheetPanel.CELL_HEIGHT / 2 + 6);
      } else if (x < 26 * 26 * 26) {
        g.drawString(Coord.colIndexToName(x + 1), x * 64 + 20,
                SpreadsheetPanel.CELL_HEIGHT / 2 + 6);
      }
    }
  }
}
