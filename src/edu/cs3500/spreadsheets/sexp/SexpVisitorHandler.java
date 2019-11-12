package edu.cs3500.spreadsheets.sexp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.cell.CellBlank;
import edu.cs3500.spreadsheets.cell.CellBoolean;
import edu.cs3500.spreadsheets.cell.CellDouble;
import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.cell.CellFunction;
import edu.cs3500.spreadsheets.cell.CellReference;
import edu.cs3500.spreadsheets.cell.CellString;
import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * A class that handles the visitor pattern for s-expressions.
 */
public class SexpVisitorHandler implements SexpVisitor<CellFormula> {
  private BasicWorksheetModel model;
  private Coord locationOfCell;

  /**
   * Constructs a {@code SexpVisitorHandler} object. The default constructor for a Sexp visitor.
   */
  public SexpVisitorHandler() {
    // this is a blank constructor because we often times only need to create a new visitor
    // with no fields to pass into our accept method
  }

  /**
   * Constructs a {@code SexpVisitorHandler} object. A constructor of a Sexp visitor that takes in
   * the cells of a spreadsheet for use in determining the cells of a reference.
   *
   * @param modelCells the model of a spreadsheet
   */
  public SexpVisitorHandler(BasicWorksheetModel modelCells, Coord location) {
    this.model = modelCells;
    this.locationOfCell = location;
  }

  // returns a new CellBoolean with the given boolean value
  @Override
  public CellFormula visitBoolean(boolean b) {
    return new CellBoolean(b);
  }

  // returns a new CellDouble with the given double value
  @Override
  public CellFormula visitNumber(double d) {
    return new CellDouble(d);
  }

  @Override
  public CellFormula visitSList(List<Sexp> l) {
    // initialize a result list of cell formulas
    List<CellFormula> result = new ArrayList<>();
    // creates a modifiable copy of the list
    List<Sexp> copy = new ArrayList<>();
    // adds all items of the original list to the copy
    copy.addAll(l);
    // removes the first item from the list, which is guaranteed to be a sumbol
    copy.remove(0);
    // for each expression in copy
    for (Sexp expr : copy) {
      // visit the expression and add it to the result list
      result.add(expr.accept(new SexpVisitorHandler(this.model, this.locationOfCell)));
    }
    // return a new cell function with the first item (name) and the result list
    return new CellFunction(l.get(0).toString(), result);
  }

  // a symbol is guaranteed to be a reference with our implementation
  // creates a list of cells being referenced
  // returns a new CellReference with the given name and computed list
  @Override
  public CellReference visitSymbol(String s) {
    List<CellFormula> listOfReferencedCells = getReferencedCells(s, this.locationOfCell);
    // creates a new cell reference, whose constructor checks for direct or indirect references
    return new CellReference(s, listOfReferencedCells);
  }

  // returns a new CellString with the given string
  @Override
  public CellFormula visitString(String s) {
    return new CellString(s);
  }

  /**
   * Creates a list of cells being referenced from the given string reference.
   *
   * @param referenceSymbol a string representing the region of cells being referenced.
   * @return a list of CellFormula representing the cells being referenced.
   */
  // TODO check here for references
  private List<CellFormula> getReferencedCells(String referenceSymbol, Coord location) {
    List<CellFormula> referencedCells = new ArrayList<>();
    Coord cell1coordinate;
    Coord cell2coordinate;
    // if the reference is just to one cell
    if (!referenceSymbol.contains(":")) {
      // set the coordinate of the cell
      cell1coordinate = getCoord(referenceSymbol);
      // CYCLES
      // checks if there is a direct cyclic reference
      if (cell1coordinate.equals(location)) {
        throw new IllegalArgumentException("Cycle reference");
      } else {
        if (this.model.getCellAt(cell1coordinate) == null) {
          referencedCells.add(new CellBlank());
        } else {
          // get the cell from the worksheet at that coordinate, makes a blank cell if necessary
          CellFormula refCell = this.model.getCellAt(cell1coordinate);
          if (refCell instanceof CellReference &&
                  ((CellReference) refCell).coordString.equals(location.toString())) {
            throw new IllegalArgumentException("Cyclic reference");
          } else {
            referencedCells.add(refCell);
          }
        }
      }
    } else {
      // else the reference is to two cells, split the string at the colon
      String[] splitSymbol = referenceSymbol.split(":");
      // set the coordinate of the first cell
      cell1coordinate = getCoord(splitSymbol[0]);
      // set the coordinate of the second cell
      cell2coordinate = getCoord(splitSymbol[1]);
      // add all the cells in that area to the referenced list
      referencedCells = referenceCellArea(cell1coordinate, cell2coordinate, this.locationOfCell);
    }
    return referencedCells;
  }

  /**
   * Creates a list of cells representing all the cells in a region of cells.
   *
   * @param c1 the top left coordinate of the region.
   * @param c2 the bottom right coordinate of the region.
   * @return
   */
  private List<CellFormula> referenceCellArea(Coord c1, Coord c2, Coord locationOfCell) {
    // set a result list
    List<CellFormula> result = new ArrayList<>();
    // for every column in the region
    for (int i = c1.col; i < c2.col + 1; i++) {
      // for every row in the region
      for (int j = c1.row; j < c2.row + 1; j++) {
        // set a new coordinate
        Coord tempCoord = new Coord(i, j);
        // checks for direct cyclic references in multi-cell references
        if (tempCoord.equals(locationOfCell)) {
          throw new IllegalArgumentException("Cyclic reference.");
        } else {
          // add the cell at that coordinate to the result list
          result.add(this.model.getCellAt(tempCoord));
        }
      }
    }
    return result;
  }

  /**
   * Returns the coordinate of a cell given its string coordinate.
   *
   * @param cellName the string coordinate of the cell.
   * @return A Coord representing the given string.
   */
  private static Coord getCoord(String cellName) {
    // parses the cellName to get the String representation of the column name
    int col = Coord.colNameToIndex(cellName.substring(0, 1));
    // parses the cellName to get the row number of the cell
    int row = cellName.charAt(1) - 48;
    // returns a new coordinate based on the row and column of the given cell name
    return new Coord(col, row);
  }
}