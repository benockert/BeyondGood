package edu.cs3500.spreadsheets.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents the grid of cells that make up a spreadsheet.
 */
public class SpreadsheetPanel extends javax.swing.JPanel {
  public static int CELL_WIDTH = 64;
  public static int CELL_HEIGHT = 21;
  private int numRows;
  private int numCols;
  private BasicWorksheetModel model;

  /**
   * Creates a {@code SpreadsheetPanel} object, which is the grid of cells in the view of the
   * spreadsheet.
   *
   * @param numRows The number of rows in this spreadsheet.
   * @param numCols The number of columns in this spreadsheet.
   * @param model   The given model which will be  displayed in this spreadsheet.
   */
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

  /**
   * Clips the contents of a cell if the contents are longer than the width of the cell.
   *
   * @param g    The contents of the cell that need to be potentially clipped.
   * @param xPos The x coordinate of the cell.
   * @param yPos The y coordinate of the cell.
   * @return A string of the new clipped contents of the cell.
   */
  private String getAndClipContents(Graphics g, int xPos, int yPos) {
    String cellValueToDisplay;

    try {
      cellValueToDisplay = String.valueOf(this.model.getCellAt(
              new Coord(xPos, yPos)).evaluateCell());
    } catch (IllegalArgumentException e) {
      cellValueToDisplay = "REF!";
    }


    // get metrics from the graphics
    FontMetrics metrics = g.getFontMetrics();

    while (metrics.stringWidth(cellValueToDisplay) >= CELL_WIDTH) {
      cellValueToDisplay = cellValueToDisplay.substring(0, cellValueToDisplay.length() - 2);
    }
    return cellValueToDisplay;
  }
}
