package edu.cs3500.spreadsheets.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.view.BasicWorksheetEditorView;
import edu.cs3500.spreadsheets.view.SpreadsheetPanel;

public class HighlightCell implements MouseListener {
  SpreadsheetPanel spreadsheetPanel;
  BasicWorksheetReadOnlyModel model;

  public HighlightCell(SpreadsheetPanel spreadsheetPanel, BasicWorksheetReadOnlyModel model) {
    this.spreadsheetPanel = spreadsheetPanel;
    this.model = model;
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
  }

  public String getCellContents() {
    return this.model.getCellAt(this.spreadsheetPanel.highlightCellLocation()).getRawContents();
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
