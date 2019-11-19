package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.view.BasicWorksheetEditorView;

public class EditorController {
  private Worksheet model;
  private BasicWorksheetEditorView view;

  public EditorController(Worksheet model, BasicWorksheetEditorView view) {
    this.model = model;
    this.view = view;
  }


}
