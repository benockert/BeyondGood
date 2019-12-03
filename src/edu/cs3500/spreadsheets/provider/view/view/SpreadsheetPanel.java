package edu.cs3500.spreadsheets.provider.view.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.List;


import javax.swing.JPanel;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.model.ISpreadsheet;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.InvalidFormulaException;

/**
 * A JPanel representation of an ISpreadsheet. Specifies a default minimum row and col to display if
 * the ISpreadsheet does not initially exceed those minimums. Also has specified dimensions for the
 * cells.
 */
public class SpreadsheetPanel extends JPanel {

  private final ISpreadsheet model;
  protected int currentMaxRow = 100;
  protected int currentMaxCol = 42;
  protected static int cellHeight = 15;
  protected static int cellWidth = 70;
  private static Color borderColor = Color.BLACK;
  private static Color textColor = Color.BLACK;
  private static Color rowColBackground = Color.LIGHT_GRAY;
  private Coord selectedCell;

  /**
   * Creates the panel using the given Spreadsheet.
   *
   * @param m the given Spreadsheet model
   */
  public SpreadsheetPanel(ISpreadsheet m) {
    model = m;
    currentMaxRow = maxRow(model.getNonEmpty());
    currentMaxCol = maxCol(model.getNonEmpty());
  }

  /**
   * Returns the maximum row to be used for this display.
   *
   * @param coords the coords in the represented spreadsheet.
   * @return the maximum row referenced in the input, or defaultRow. Whichever is greater.
   */
  private int maxRow(List<Coord> coords) {
    int output = currentMaxRow;
    for (Coord c : coords) {
      if (c.row > output) {
        output = c.row;
      }
    }
    return output;
  }

  /**
   * Returns the maximum col to be used for this display.
   *
   * @param coords the coords in the represented spreadsheet.
   * @return the maximum col referenced in the input, or defaultCol. Whichever is greater.
   */
  private int maxCol(List<Coord> coords) {
    int output = currentMaxCol;
    for (Coord c : coords) {
      if (c.col > output) {
        output = c.col;
      }
    }
    return output;
  }


  /**
   * Overrides the paintComponent method of JPanel. Draws each as a Rectangle, with either the data
   * it contains (or nothing) or as the row/column indicator cells.
   *
   * @param g the Graphics object we are using to draw the cells.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    AffineTransform originalTransform = g2d.getTransform(); // so that we can reset at the end
    g2d.setColor(borderColor);

    // the amount of rows and columns we will draw
    if (model != null) {
      List<Coord> coords = model.getNonEmpty();

      for (int col = 0; col < currentMaxCol + 1; col++) {
        for (int row = 0; row < currentMaxRow + 1; row++) {
          // draw the outline of the cell
          g2d.drawRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);

          // draw the row/col indicator cells
          if ((col == 0 || row == 0) && !(col == 0 && row == 0)) {
            drawRowCol(g2d, col, row);

            // draw every other cell
          } else if (row > 0 && col > 0) {
            drawCellWithData(g2d, new Coord(col, row), coords);
            // draws selected cell
            if (selectedCell != null && selectedCell.equals(new Coord(col, row))) {
              g2d.setColor(Color.RED);
              g2d.drawRect((col * cellWidth) - 1, (row * cellHeight) - 1, cellWidth, cellHeight);
              g2d.setColor(borderColor);
            }
          }
        }
      }

      g2d.setTransform(originalTransform);
    }
    // resets the Graphics object
  }

  /**
   * Draws the row and col indicator cells of the Spreadsheet.
   *
   * @param g   the Graphics object
   * @param col the current column
   * @param row the current row
   */
  private void drawRowCol(Graphics2D g, int col, int row) {
    g.setColor(rowColBackground);
    g.fillRect((col * cellWidth) + 1, (row * cellHeight) + 1, cellWidth - 1, cellHeight - 1);
    g.setColor(textColor);
    if (col == 0) {
      g.drawString(String.valueOf(row), (col * (cellWidth)) + 3,
          ((row + 1) * (cellHeight) - ((cellHeight / 2) - 5)));
    } else {
      g.drawString(Coord.colIndexToName(col), (col * (cellWidth)) + (cellWidth / 2),
          ((row + 1) * (cellHeight) - ((cellHeight / 2) - 5)));
    }
    g.setColor(borderColor);
  }

  /**
   * Draws a cell that contains data. If it doesn't actually have data, don't do anything. If the
   * contents of the cell are problematic, report the type of error.
   *
   * @param g      the graphics object
   * @param cell   the cell we are drawing
   * @param coords all the coords we are working with
   */
  private void drawCellWithData(Graphics2D g, Coord cell, List<Coord> coords) {
    g.setColor(textColor);
    Shape clip = g.getClip();
    g.clipRect(cell.col * cellWidth, cell.row * cellHeight, cellWidth, cellHeight);
    if (coords.contains(cell)) {
      try {
        g.drawString(model.evaluateAt(cell), (cell.col * (cellWidth)) + 2,
            ((cell.row + 1) * (cellHeight)) - 2);
      } catch (InvalidFormulaException e) {
        g.drawString(e.getMessage(), (cell.col * (cellWidth)) + 2,
            ((cell.row + 1) * (cellHeight)) - 2);
      }
    }
    g.setColor(borderColor);
    g.setClip(clip);
  }

  /**
   * Sets the selected cell to the input.
   *
   * @param c the coordinate
   */
  public void setSelected(Coord c) {
    this.selectedCell = c;
  }

  /**
   * Gets the currently selected cell. If none currently selected, returns null.
   *
   * @return the current coordinate of the selected cell, or null
   */
  public Coord getSelected() {
    if (selectedCell != null) {
      return new Coord(selectedCell.col, selectedCell.row);
    } else {
      return null;
    }
  }
}