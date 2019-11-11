package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.HashMap;

import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.SexpVisitorHandler;

/**
 * Represents a worksheeet builder that creates cells, coordinates, and a worksheet.
 */
public class WorksheetBuilderImpl implements WorksheetReader.WorksheetBuilder<BasicWorksheetModel> {
  private HashMap<Coord, CellFormula> cellHashMap = new HashMap<>();

  @Override
  public WorksheetReader.WorksheetBuilder createCell(int col, int row, String contents) {
    try {
      // constructs a cell from the given string contents
      CellFormula cell;
      // constructs a coordinate from the given row and column number
      Coord location = new Coord(col, row);
      // if the cell is a reference or a formula
      if (contents.substring(0, 1).equals("=")) {
        // create a string without the "="
        String contentsWithoutEquals = contents.substring(1);
        // create a new cell with the reference or formula
        cell = Parser.parse(contentsWithoutEquals).accept(
                new SexpVisitorHandler(new BasicWorksheetModel(this.cellHashMap), location));
      } else {
        cell = Parser.parse(contents).accept(new SexpVisitorHandler(new BasicWorksheetModel(this.cellHashMap), location));
      }

      // adds the cell to the HashMap
      this.cellHashMap.put(location, cell);
    } catch (IllegalArgumentException e) {
      // prints out errors in creating cells with the error message they throw
      System.out.println("Error in cell " + Coord.colIndexToName(col) + row
              + ": " + e.toString().substring(36));
    }
    return this;
  }

  @Override
  public BasicWorksheetModel createWorksheet() {
    return new BasicWorksheetModel(this.cellHashMap);
  }
}
