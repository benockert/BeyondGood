package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cs3500.spreadsheets.cell.CellBlank;
import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.SexpVisitorHandler;

/**
 * Represents the model of a basic worksheet, with cells that can be evaluated and have formulas
 * applied to them.
 */
public class BasicWorksheetModel implements Worksheet {
  private HashMap<Coord, CellFormula> cells;

  /**
   * Constructs a {@code BasicWorksheetModel} object. The default constructor that creates a blank
   * spreadsheet and thus a blank hash map of cells.
   */
  public BasicWorksheetModel() {
    this.cells = new HashMap<>();
  }

  /**
   * Constructs a {@code BasicWorksheetModel} object. A constructor that accepts a pre-initialized
   * hash map of cells, for use in reading a spreadsheet from a file so that you can save and reload
   * a spreadsheet.
   *
   * @param cells a hash map of Coordinates to Cells representing the cells in the spreadsheet.
   */
  public BasicWorksheetModel(HashMap<Coord, CellFormula> cells) {
    this.cells = cells;
  }

  @Override
  public void editCell(boolean input, Coord location) {
    // create a new cell with the given boolean
    CellFormula editedCell = Parser.parse(Boolean.toString(input)).accept(new SexpVisitorHandler());
    // put the cell at the given location
    this.cells.put(location, editedCell);
    // updates the contents of all cells
    this.updateAllCells();
  }

  @Override
  public void editCell(String input, Coord location) {
    // initialize an edited cell
    CellFormula editedCell;
    // if the cell is a function or reference
    if (input.substring(0, 1).equals("=")) {
      // initialize a string of the input without the  "="
      String noEqualsString = input.substring(1);
      // create a new cell with the given string converted to a function or reference
      editedCell = Parser.parse(noEqualsString).accept(
              new SexpVisitorHandler(this, location));
    } else {
      // else the cell is a string, format it correctly
      String escapedString = "\"" + input + "\"";
      // create a new cell with the given string
      editedCell = Parser.parse(escapedString).accept(new SexpVisitorHandler());
    }
    // put the cell at the given locationC
    this.cells.put(location, editedCell);
    // updates the contents all cells
    this.updateAllCells();
  }

  @Override
  public void editCell(double input, Coord location) {
    // create a new cell with the given double
    CellFormula editedCell = Parser.parse(Double.toString(input)).accept(new SexpVisitorHandler());
    // put the cell at the given location
    this.cells.put(location, editedCell);
    // updates the contents of all cells
    this.updateAllCells();
  }

  @Override
  public CellFormula getCellAt(Coord location) {
    if (this.cells.get(location) == null) {
      this.cells.put(location, new CellBlank());
    }
    return this.cells.get(location);
  }

  @Override
  public HashMap<Coord, CellFormula> getCells() {
    return this.cells;
  }


  /**
   * Reevaluates all of the cells. This method is called every time a cell is edited.
   */
  private void updateAllCells() {
    for (Map.Entry<Coord, CellFormula> cell : this.cells.entrySet()) {
      cell.getValue().evaluateCell();
    }
  }
}
