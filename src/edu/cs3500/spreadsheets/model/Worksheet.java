package edu.cs3500.spreadsheets.model;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.cell.CellFormula;

/**
 * Represents a worksheet with cells.
 */
public interface Worksheet {

  /**
   * Replaces a cell at a given location in {@Code Worksheet} with a given boolean.
   *
   * @param input    the boolean value to replace the cell.
   * @param location the location of the cell being edited.
   */
  void editCell(boolean input, Coord location);

  /**
   * Replaces a cell at a given location in {@Code Worksheet} with a given String.
   *
   * @param input    the String value to replace the cell.
   * @param location the location of the cell being edited.
   */
  void editCell(String input, Coord location);

  /**
   * Replaces a cell at a given location in {@Code Worksheet} with a given double.
   *
   * @param input    the double value to replace the cell.
   * @param location the location of the cell being edited.
   */
  void editCell(double input, Coord location);

  /**
   * Returns a cell at a given location in {@Code Worksheet}.
   *
   * @param location the coordinates of the cell being returned.
   * @return a cell at a specific location.
   */
  CellFormula getCellAt(Coord location);

  /**
   * Returns all the cells in this model.
   * @return the cells of the model.
   */
  HashMap<Coord, CellFormula> getCells();

}
