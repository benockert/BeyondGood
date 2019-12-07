package edu.cs3500.spreadsheets.view;

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

  /**
   * Adds the given features to this view.
   *
   * @param feature The features to be added to the view.
   */
  void addIFeatures(IFeatures feature);

  /**
   * Sets the text in the textbox to be the highlighted cell's contents.
   */
  void setTextbox();

  /**
   * Changes the location of the highlighted cell based on integer inputs.
   *
   * @param i  The number of columns the highlighted cell is moving.
   * @param i1 The number of rows the highlighted cell is moving.
   */
  void changeHighlightedCellLocation(int i, int i1);
}



