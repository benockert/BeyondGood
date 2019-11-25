package edu.cs3500.spreadsheets.cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cs3500.spreadsheets.function.CellVisitor;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * A class that represents a cell in a spreadsheet that contains a reference. A reference is a
 * pointer to either another cell in the spreadsheet, or a rectangular region of cells.
 */
public class CellReference implements CellFormula {
  public String coordString;
  public HashMap<Coord, CellFormula> referencedCells;
  public Coord thisLocation;

  /**
   * Constructs a {@code CellReference} object. A constructor for this CellReference that takes in
   * the coordinate(s) as a string and a list of the associated cells.
   *
   * @param coordString     the coordinate(s) of this reference, represented as a string.
   * @param referencedCells the cells and their locations referenced by this cell.
   * @param loc             the location of this cell.
   */
  public CellReference(String coordString, HashMap<Coord, CellFormula> referencedCells, Coord loc) {
    this.coordString = coordString;
    this.referencedCells = referencedCells;
    this.thisLocation = loc;

  }

  // to evaluate just a reference (ex: =A1:A3), just return the value of the first cell
  @Override
  public Object evaluateCell() {
    for (Map.Entry<Coord, CellFormula> cell : referencedCells.entrySet()) {
      cell.getValue().evaluateCell();
      if (cell.getKey().equals(this.thisLocation)) {
        throw new IllegalArgumentException("Direct cyclic reference");
      }
    }
    Coord evaluateCoord = (Coord) referencedCells.keySet().toArray()[0];
    return this.referencedCells.get(evaluateCoord).evaluateCell();
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


