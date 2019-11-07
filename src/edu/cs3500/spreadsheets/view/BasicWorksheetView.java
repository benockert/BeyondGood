package edu.cs3500.spreadsheets.view;

import java.io.IOException;

public interface BasicWorksheetView {

  /**
   * Renders a {@link edu.cs3500.spreadsheets.model.BasicWorksheetModel} in some manner.
   */
  void render() throws IOException;


}
