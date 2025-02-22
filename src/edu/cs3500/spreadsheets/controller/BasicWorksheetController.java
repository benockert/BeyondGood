package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.view.BasicWorksheetView;

/**
 * Represents the controller of a basic worksheet, which controls what the model and view receive
 * when editing the worksheet.
 */
public class BasicWorksheetController implements IFeatures {
  private Worksheet model;
  private BasicWorksheetView view;

  /**
   * Constructs a {@code BasicWorksheetControllerObject}, which passes objects between the view and
   * model.
   *
   * @param model The model that we want to mutate with this controller.
   * @param view  The view that we want to use to display what the controller mutates.
   */
  public BasicWorksheetController(Worksheet model, BasicWorksheetView view) {
    this.model = model;
    this.view = view;
    this.view.addIFeatures(this);
  }

  /**
   * Runs the controller, which displays the graphical of this model.
   */
  @Override
  public void run() {
    this.view.render();
  }

  @Override
  public void acceptCellEdit(Coord location, String rawContents) {
    this.model.editCell(rawContents, location);
    for (Coord coord : this.model.getCells().keySet()) {
      String cellsRawContents = this.model.getCellAt(coord).getRawContents();
      this.model.editCell("=" + cellsRawContents, coord);
    }
    this.view.refresh();
  }

  @Override
  public void rejectCellEdit() {
    this.view.setTextbox();
  }

  @Override
  public void moveHighlightedCell(String arrowKey) {
    switch (arrowKey) {
      case "up arrow":
        this.view.changeHighlightedCellLocation(0, -1);
        this.view.setTextbox();
        break;
      case "down arrow":
        this.view.changeHighlightedCellLocation(0, 1);
        this.view.setTextbox();
        break;
      case "left arrow":
        this.view.changeHighlightedCellLocation(-1, 0);
        this.view.setTextbox();
        break;
      case "right arrow":
        this.view.changeHighlightedCellLocation(1, 0);
        this.view.setTextbox();
        break;
      default:
        break;
    }
    this.view.refresh();
  }

  @Override
  public void deleteCellContents(Coord location) {
    this.model.removeCell(location);
    this.acceptCellEdit(location, "");
    this.view.setTextbox();
    this.view.refresh();
  }

}

