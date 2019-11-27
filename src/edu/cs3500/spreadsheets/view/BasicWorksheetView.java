package edu.cs3500.spreadsheets.view;

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

  /**
   * Informs the user of an error in rendering a view.
   * @param message
   */
  void addErrorMessage(String message);


}



