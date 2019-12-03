package edu.cs3500.spreadsheets.provider.view.controller;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Describes a list of Features that an editable spreadsheet program supports.
 */
public interface Features {

  /**
   * Selects a cell at the given Coord.
   * @param c the Coordinate
   */
  void selectCell(Coord c);

  /**
   * Rejects the changes that were inputted for the cell at the given Coord.
   * @param c the coordinate of the cell
   */
  void rejectChanges(Coord c);

  /**
   * Accepts the changes that were inputted for the cell at the given Coord.
   * @param c the coordinate of the cell
   */
  void acceptChanges(Coord c);
}
