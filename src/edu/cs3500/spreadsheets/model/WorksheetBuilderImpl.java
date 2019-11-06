package edu.cs3500.spreadsheets.model;

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
    // constructs a cell from the given string contents
    CellFormula cell;
    // if the cell is a reference or a formula
    if (contents.substring(0, 1).equals("=")) {
      // create a string without the "="
      String contentsWithoutEquals = contents.substring(1);
      // create a new cell with the reference or formula
      cell = Parser.parse(contentsWithoutEquals).accept(new SexpVisitorHandler(this.cellHashMap));
    } else {
      cell = Parser.parse(contents).accept(new SexpVisitorHandler(this.cellHashMap));
    }
    // constructs a coordinate from the given row and column number
    Coord location = new Coord(col, row);
    // adds the cell to the HashMap
    this.cellHashMap.put(location, cell);
    return this;
  }

  @Override
  public BasicWorksheetModel createWorksheet() {
    return new BasicWorksheetModel(this.cellHashMap);
  }
}
