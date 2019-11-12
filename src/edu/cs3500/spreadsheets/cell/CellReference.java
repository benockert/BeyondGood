package edu.cs3500.spreadsheets.cell;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.function.CellVisitor;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.SexpVisitorHandler;

/**
 * A class that represents a cell in a spreadsheet that contains a reference. A reference is a
 * pointer to either another cell in the spreadsheet, or a rectangular region of cells.
 */
public class CellReference implements CellFormula {
  public String coordString;
  public List<CellFormula> cells;

  /**
   * Constructs a {@code CellReference} object. A constructor for this CellReference that takes in
   * the coordinate(s) as a string and a list of the associated cells.
   *
   * @param coordString the coordinate(s) of this reference, represented as a string.
   * @param cells       the list of cells that this cell references.
   */
  public CellReference(String coordString, List<CellFormula> cells) {
    this.coordString = coordString;
    this.cells = cells;

  }

  // to evaluate just a reference (ex: =A1:A3), just return the value of the first cell
  @Override
  public Object evaluateCell() {
    return this.cells.get(0).evaluateCell();
  }

  // raw contents should just be a string of the coordinates (ex: "A1:A3")
  @Override
  public String getRawContents() {
    return this.coordString;
  }

  @Override
  public Object accept(CellVisitor visit) {
    return visit.visitReference(this);
  }

}
