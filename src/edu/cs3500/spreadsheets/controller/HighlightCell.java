package edu.cs3500.spreadsheets.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.cs3500.spreadsheets.view.BasicWorksheetEditorView;
import edu.cs3500.spreadsheets.view.SpreadsheetPanel;

/**
 * Represents the act of highlighting a cell in a worksheet.
 */
public class HighlightCell implements MouseListener {
  private SpreadsheetPanel spreadsheetPanel;
  private BasicWorksheetEditorView view;

  /**
   * Constructs a {@code HighlightCell} object, which takes in a model and a view and
   * determines/displays the highlighted cell.
   *
   * @param spreadsheetPanel The spreadsheet panel with all the cells, one of which is highlighted.
   * @param view             The given view which will display the highlighted cell.
   */
  public HighlightCell(SpreadsheetPanel spreadsheetPanel, BasicWorksheetEditorView view) {
    this.spreadsheetPanel = spreadsheetPanel;
    this.view = view;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int xPos = e.getX();
    int yPos = e.getY();

    int xCoord = xPos / SpreadsheetPanel.CELL_WIDTH;
    int yCoord = yPos / SpreadsheetPanel.CELL_HEIGHT;

    this.spreadsheetPanel.setHighlightLocation(xCoord, yCoord);
    this.spreadsheetPanel.revalidate();
    this.spreadsheetPanel.repaint();

    this.view.setTextbox();
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // no action taken for a mouse press
  }

  @Override
  public void mouseReleased(MouseEvent mouseEvent) {
    // no action taken for a mouse release
  }

  @Override
  public void mouseEntered(MouseEvent mouseEvent) {
    // no action taken for a mouse enter
  }

  @Override
  public void mouseExited(MouseEvent mouseEvent) {
    // no action taken for a mouse exit
  }

}
