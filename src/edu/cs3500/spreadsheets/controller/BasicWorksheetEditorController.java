package edu.cs3500.spreadsheets.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.view.BasicWorksheetEditorView;

/**
 * Represents the controller of a basic worksheet, which controls what the model and view receive
 * when editing the worksheet.
 */
public class BasicWorksheetEditorController implements WorksheetController, ActionListener {
  private Worksheet model;
  private BasicWorksheetEditorView view;

  /**
   * Constructs a {@code BasicWorksheetControllerObject}, which passes objects between the view and
   * model.
   *
   * @param model The model that we want to mutate with this controller.
   * @param view  The view that we want to use to display what the controller mutates.
   */
  public BasicWorksheetEditorController(Worksheet model, BasicWorksheetEditorView view) {
    this.model = model;
    this.view = view;
    view.setListener(this);
  }

  @Override
  public void run() {
    this.view.render();
  }

  /**
   * Determines what should happen to the model based on which button is pressed (either the cell's
   * contents are mutated or the edits are denied).
   *
   * @param actionEvent The action event (pressing a button) that the user performs.
   */
  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    if (actionEvent.getActionCommand().equals("Accept button")) {
      String text = this.view.getViewTextField();
      Coord editLocation = this.view.getHighlightedCell();
      // edits the cell at this location and reevaluates all cells
      this.model.editCell(text, editLocation);
      for (Coord coord : this.model.getCells().keySet()) {
        String cellsRawContents = this.model.getCellAt(coord).getRawContents();
        this.model.editCell("=" + cellsRawContents, coord);
      }
      this.view.repaintSpreadsheet();
    } else if (actionEvent.getActionCommand().equals("Reject button")) {
      this.view.setTextbox();
    }
  }

}

