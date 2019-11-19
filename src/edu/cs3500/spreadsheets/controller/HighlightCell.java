package edu.cs3500.spreadsheets.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.cs3500.spreadsheets.view.SpreadsheetPanel;

public class HighlightCell implements MouseListener {

  SpreadsheetPanel spreadsheetPanel;

  public HighlightCell(SpreadsheetPanel spreadsheetPanel) {
    this.spreadsheetPanel = spreadsheetPanel;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int xPos = e.getX();
    int yPos = e.getY();

    int xCoord = xPos / SpreadsheetPanel.CELL_WIDTH;
    int yCoord = yPos / SpreadsheetPanel.CELL_HEIGHT;

    this.spreadsheetPanel.setHighlightLocation(xCoord, yCoord);
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent mouseEvent) {
  }

  @Override
  public void mouseEntered(MouseEvent mouseEvent) {
  }

  @Override
  public void mouseExited(MouseEvent mouseEvent) {
  }

}
