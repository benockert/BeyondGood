package edu.cs3500.spreadsheets.view;

import java.awt.*;

public class RowPanel extends javax.swing.JPanel {

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (int y = 1; y <= BasicWorksheetGraphicalView.GRID_CELLS; y++) {
      // draws a number of rectangles equal to the specified number of column cells one on top of
      // the other along the y-axis
      g.drawRect(-1, (y - 1) * SpreadsheetPanel.CELL_HEIGHT, SpreadsheetPanel.CELL_WIDTH,
              SpreadsheetPanel.CELL_HEIGHT);
      // sets the font size to be 14 and bolds the text
      g.setFont(new Font("default", Font.BOLD, 14));
      // draws the text displayed in the row header (row number) and aligns it based on
      // the width of the text (size of the number)
      if (y < 9) {
        g.drawString(String.valueOf(y), SpreadsheetPanel.CELL_WIDTH / 2 - 4,
                y * 21 - 5);
      } else if (y < 99) {
        g.drawString(String.valueOf(y + 1), SpreadsheetPanel.CELL_WIDTH / 2 - 8,
                y * 21 - 5);
      } else if (y < 999) {
        g.drawString(String.valueOf(y + 1), SpreadsheetPanel.CELL_WIDTH / 2 - 12,
                y * 21 - 5);
      } else {
        g.drawString(String.valueOf(y + 1), SpreadsheetPanel.CELL_WIDTH / 2 - 16,
                y * 21 - 5);
      }
    }
  }


}
