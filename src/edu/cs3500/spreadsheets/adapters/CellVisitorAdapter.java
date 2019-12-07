package edu.cs3500.spreadsheets.adapters;

import java.util.ArrayList;

import edu.cs3500.spreadsheets.cell.CellBlank;
import edu.cs3500.spreadsheets.cell.CellBoolean;
import edu.cs3500.spreadsheets.cell.CellDouble;
import edu.cs3500.spreadsheets.cell.CellFunction;
import edu.cs3500.spreadsheets.cell.CellReference;
import edu.cs3500.spreadsheets.cell.CellString;
import edu.cs3500.spreadsheets.function.CellVisitor;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.Function;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.InvalidFormulaException;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.formulas.IFormulaVisitor;

/**
 * Represents a class which adapts the provider's visitor to our visitor interface.
 */
public class CellVisitorAdapter implements CellVisitor {
  private IFormulaVisitor theirVisitor;
  private ProviderModelAdapter model;

  /**
   * Constructs a {@code CellVisitorAdapter} object which adapts the provider's visitor to our.
   *
   * @param theirVisitor The provider's visitor interface.
   * @param model        The adapted model (our model adapted to their interface).
   */
  public CellVisitorAdapter(IFormulaVisitor theirVisitor, ProviderModelAdapter model) {
    this.theirVisitor = theirVisitor;
    this.model = model;
  }

  // visits a double using their visitor and evaluates the value
  @Override
  public Object visitDouble(CellDouble d) {
    return theirVisitor.visitDoubleValue(d.evaluateCell());
  }

  // visits a boolean using their visitor and evaluates the value
  @Override
  public Object visitBoolean(CellBoolean b) {
    return theirVisitor.visitBooleanVal(b.evaluateCell());
  }

  // visits a function using their visitor and evaluates the value
  @Override
  public Object visitFunction(CellFunction f) {
    Function.Func function = Function.Func.valueOf(f.func);
    try {
      // tries to visit the function
      return theirVisitor.visitFunction(function, f.arguments);
    } catch (InvalidFormulaException ife) {
      // or throws an exception
      throw new IllegalArgumentException("Invalid formula");
    }
  }

  // visits a reference using their visitor and evaluates the value
  @Override
  public Object visitReference(CellReference r) {
    ArrayList<Coord> referencedCoords = new ArrayList<>();
    for (Coord coord : r.referencedCells.keySet()) {
      // adds every cord in our hashmap to their arraylist
      referencedCoords.add(coord);
    }
    // tries to visit the reference
    try {
      return theirVisitor.visitReference(referencedCoords, this.model);
    } catch (InvalidFormulaException ife) {
      // or throws an exception
      throw new IllegalArgumentException("Invalid formula");
    }
  }

  // visits a string using their visitor and evaluates the value
  @Override
  public Object visitString(CellString s) {
    try {
      // tries to visit the string
      return theirVisitor.visitStringValue(s.getRawContents(), s.evaluateCell());
    } catch (InvalidFormulaException ife) {
      // or throws an exception
      throw new IllegalArgumentException("Invalid input");
    }
  }

  @Override
  public Object visitBlank(CellBlank b) {
    return null; // the provider code does not represent blank cells
  }

  @Override
  public Object apply(Object o) {
    return null; // the provider code does not represent functions as function object
  }
}
