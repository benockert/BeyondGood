package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Represents a view of a basic worksheet - either graphical or text.
 */
public interface BasicWorksheetView {

  /**
   * Renders a {@link edu.cs3500.spreadsheets.model.BasicWorksheetModel} in some manner.
   */
  void render() throws IOException;

  /**
   * Enables the view to forward any events received to the listener.
   *
   * @param listener the action listener
   */
  void setListener(ActionListener listener);


}
