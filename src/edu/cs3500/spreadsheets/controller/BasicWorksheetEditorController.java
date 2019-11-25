package edu.cs3500.spreadsheets.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.view.BasicWorksheetEditorView;
import edu.cs3500.spreadsheets.view.BasicWorksheetView;

public class BasicWorksheetEditorController implements WorksheetController, ActionListener {
  private Worksheet model;
  private BasicWorksheetEditorView view;

  public BasicWorksheetEditorController(Worksheet model, BasicWorksheetEditorView view) {
    this.model = model;
    this.view = view;
    view.setListener(this);
  }


  @Override
  public String processCommand(String command) {
    return null;
  }

  @Override
  public void run() {
    try {
      this.view.render();
    } catch (IOException io) {
      throw new IllegalArgumentException("Error rendering view");
    }
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    switch (actionEvent.getActionCommand()) {
      case "Accept button":
        String text = this.view.getViewTextField();
        Sexp exp = Parser.parse(text);
        Coord editLocation = this.view.getHighlightedCell();
        // edits the cell at this location and reevaluates all cells
        this.model.editCell(text, editLocation);
        this.view.repaintSpreadsheet();
      case "Reject button":
        this.view.setTextbox();
    }

  }
}
