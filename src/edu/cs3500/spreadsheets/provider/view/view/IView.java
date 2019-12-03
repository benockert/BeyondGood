package edu.cs3500.spreadsheets.provider.view.view;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Constructs a view of a Spreadsheet. Functionality that is not supported throws
 * UnsupportedArgument Exceptions. For example, attempting to make a view with no interface visible
 * with makeVisible() will throw an error. Or, trying to save a view that has no interface to
 * request saving. The
 */
public interface IView {

  /**
   * Refreshes (or renders for the first time) our Spreadsheet.
   */
  void render();

  /**
   * Saves the spreadsheet as a file of the given name.
   *
   * @param fileName name of the file
   */
  void save(String fileName);

  /**
   * Makes the Spreadsheet visible, if an applicable request.
   */
  void makeVisible();

  /**
   * Sets the selected cell to the one at the given coordinate.
   *
   * @param c the coordinate
   */
  void setSelected(Coord c);

  /**
   * Gets the currently selected cell.
   *
   * @return the coordinate of the cell
   */
  Coord getSelected();
}