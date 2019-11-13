package edu.cs3500.spreadsheets.view;

import java.awt.*;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;

public class SpreadsheetPanel extends javax.swing.JPanel {
  private int numRows;
  private int numCols;
  private BasicWorksheetModel model;

  final static int CELL_WIDTH = 64;
  final static int CELL_HEIGHT = 21;

  SpreadsheetPanel(int numRows, int numCols, BasicWorksheetModel model) {
    this.numRows = numRows;
    this.numCols = numCols;
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    for (int x = 0; x < this.numCols; x++) {
      for (int y = 0; y < this.numRows; y++) {
        g.drawRect(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
        String contentsToDraw = getAndClipContents(g, x + 1, y + 1);
        g.drawString(contentsToDraw, x * CELL_WIDTH + 3, (y + 1) * CELL_HEIGHT - 3);
      }
    }
  }

  private String getAndClipContents(Graphics g, int xPos, int yPos) {
    String cellValue = String.valueOf(this.model.getCellAt(new Coord(xPos, yPos)).evaluateCell());

    // get metrics from the graphics
    FontMetrics metrics = g.getFontMetrics();

    while (metrics.stringWidth(cellValue) > CELL_WIDTH) {
      cellValue = cellValue.substring(0, cellValue.length() - 2);
    }
    return cellValue;
  }
}
