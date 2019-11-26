package edu.cs3500.spreadsheets.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.view.BasicWorksheetEditorView;

public class BasicWorksheetEditorController implements WorksheetController, ActionListener {
  private Worksheet model;
  private BasicWorksheetEditorView view;

  public BasicWorksheetEditorController(Worksheet model, BasicWorksheetEditorView view) {
    this.model = model;
    this.view = view;
    view.setListener(this);
  }

  @Override
  public void run() {
    this.view.render();
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    switch (actionEvent.getActionCommand()) {
      case "Accept button":
        String text = this.view.getViewTextField();
        Coord editLocation = this.view.getHighlightedCell();
        // edits the cell at this location and reevaluates all cells
        this.model.editCell(text, editLocation);
        for (Coord coord : this.model.getCells().keySet()) {
          String cellsRawContents = this.model.getCellAt(coord).getRawContents();
          this.model.editCell("=" + cellsRawContents, coord);
        }
        this.view.repaintSpreadsheet();
      case "Reject button":
        this.view.setTextbox();
    }

  }
}
