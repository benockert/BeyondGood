package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.BasicWorksheetController;
import edu.cs3500.spreadsheets.controller.IFeatures;

/**
 * Represents a view of a basic worksheet - either graphical or text.
 */
public interface BasicWorksheetView {

  /**
   * Renders a {@link edu.cs3500.spreadsheets.model.BasicWorksheetModel} in some manner.
   */
  void render();

  /**
   * Refreshes a view.
   */
  void refresh();

}



