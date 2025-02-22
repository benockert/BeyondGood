package edu.cs3500.spreadsheets.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
  public void editCell(String input, Coord location) {
    // initialize an edited cell
    CellFormula editedCell;
    // if the cell is a function or reference
    if (input.equals("") || input.equals("=")) {
      editedCell = new CellBlank("");
    } else if (input.substring(0, 1).equals("=")) {
      // initialize a string of the input without the  "="
      String noEqualsString = input.substring(1);
      // create a new cell with the given string converted to a function or reference
      editedCell = Parser.parse(noEqualsString).accept(
              new SexpVisitorHandler(this, location));
    } else {
      if (input.substring(0, 1).equals("\"")) {
        editedCell = Parser.parse(input).accept(new SexpVisitorHandler());
      } else {
        try {
          editedCell = Parser.parse(input).accept(new SexpVisitorHandler());
        } catch (Exception e) {
          editedCell = Parser.parse("\"" + input + "\"").accept(new SexpVisitorHandler());
        }
      }
    }
    // put the cell at the given locationC
    this.cells.put(location, editedCell);
    // updates the contents all cells
    this.updateAllCells();
  }

  @Override
  public CellFormula getCellAt(Coord location) {
    if (this.cells.get(location) == null) {
      this.cells.put(location, new CellBlank(""));
    }
    return this.cells.get(location);
  }

  @Override
  public HashMap<Coord, CellFormula> getCells() {
    return this.cells;
  }

  @Override
  public int getNumRows() {
    int numRows = 0;
    for (Coord coord : this.cells.keySet()) {
      if (coord.row > numRows) {
        numRows = coord.row;
      }
    }
    return numRows;
  }

  @Override
  public int getNumCols() {
    int numCols = 0;
    for (Coord coord : this.cells.keySet()) {
      if (coord.col > numCols) {
        numCols = coord.col;
      }
    }
    return numCols;
  }

  @Override
  public void removeCell(Coord location) {
    this.cells.remove(location);
  }

  /**
   * Reevaluates all of the cells. This method is called every time a cell is edited.
   */
  private void updateAllCells() {
    for (Map.Entry<Coord, CellFormula> cell : this.cells.entrySet()) {
      this.cells.put(cell.getKey(), cell.getValue());
    }
  }

  @Override
  public boolean equals(Object o) {
    boolean returnValue = true;
    if (this == o) {
      // no empty blocks
    } else if (o == null || getClass() != o.getClass()) {
      returnValue = false;
    } else {
      BasicWorksheetModel that = (BasicWorksheetModel) o;
      for (Coord coord : cells.keySet()) {
        if (this.getCellAt(coord).evaluateCell().equals(that.getCellAt(coord).evaluateCell())) {
          returnValue = true;
        } else {
          returnValue = false;
        }
      }
    }
    return returnValue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cells);
  }
}
