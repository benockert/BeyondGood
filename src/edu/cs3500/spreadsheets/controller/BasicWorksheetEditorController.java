package edu.cs3500.spreadsheets.controller;

import java.io.IOException;

import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.view.BasicWorksheetView;

public class BasicWorksheetEditorController implements WorksheetController {
  private Worksheet model;
  private BasicWorksheetView view;

  public BasicWorksheetEditorController(Worksheet model, BasicWorksheetView view) {
    this.model = model;
    this.view = view;
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
}
