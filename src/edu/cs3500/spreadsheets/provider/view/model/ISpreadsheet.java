package edu.cs3500.spreadsheets.provider.view.model;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.IFormula;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.InvalidFormulaException;

import java.util.List;

/**
 * Represents a Spreadsheet program. For example, can return the K at specified coordinates.
 *
 * @param <K> the class that represents the cells in the Spreadsheet
 */
public interface ISpreadsheet<K> {

  /**
   * Gets the list of Coords in our spreadsheet that refer to cells that have data.
   */
  List<Coord> getNonEmpty();

  /**
   * Returns the K at the inputted Coord.
   *
   * @param c Coord of the desired K
   * @return K object at the Coord
   */
  K getAt(Coord c);

  /**
   * Returns the evaluation of the K at the given coordinates.
   *
   * @param c the Coord of the desired K
   * @return the value as a String
   */
  String evaluateAt(Coord c) throws InvalidFormulaException;

  /**
   * Allows you to change the contents of the Cell at the Inputted Coord.
   *
   * @param c        the coordinate of the desired Cell
   * @param contents the desired change
   */
  void changeAt(Coord c, IFormula contents);

  /**
   * Returns the IFormula of the K at the input Coord.
   *
   * @param c the Coord
   * @return the IFormula at the Cell there
   */
  IFormula formulaAt(Coord c);
}