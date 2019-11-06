package edu.cs3500.spreadsheets.function;

import edu.cs3500.spreadsheets.cell.CellBlank;
import edu.cs3500.spreadsheets.cell.CellBoolean;
import edu.cs3500.spreadsheets.cell.CellDouble;
import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.cell.CellFunction;
import edu.cs3500.spreadsheets.cell.CellReference;
import edu.cs3500.spreadsheets.cell.CellString;

/**
 * A function object for the Product function of a spreadsheet, which multiplies an arbitrary number
 * of values together.
 */
public class Multiply implements CellVisitor<CellFormula, Double> {

  // evaluates the given CellDouble for use in the product function
  @Override
  public Double visitDouble(CellDouble d) {
    return d.evaluateCell();
  }

  // since a boolean is a non-numeric value, the Product function ignores it by treating it as an
  //  // unlikely user input such that it can be identified as a non-numeric cell
  @Override
  public Double visitBoolean(CellBoolean b) {
    return 0.12345;
  }

  // evaluates the function and applies the multiply function object to it to handle what
  // return type it is
  @Override
  public Double visitFunction(CellFunction f) {
    return this.apply(f.cellFunctionEvaluated);
  }

  // applies the visitor to each cell in the reference for use by the Product function
  @Override
  public Double visitReference(CellReference r) {
    Double result = 1.0;
    // for each cell in r.cells
    for (CellFormula cell : r.cells) {
      // if it is a double, multiply it by the result
      result *= this.apply(cell);
    }
    return result;
  }

  // since a string is a non-numeric value, the Product function ignores it by treating it as an
  // unlikely user input such that it can be identified as a non-numeric cell
  @Override
  public Double visitString(CellString s) {
    return 0.12345;
  }


  // since a blank cell contains no numeric values, the Product function ignores it by treating
  // it as 1
  @Override
  public Double visitBlank(CellBlank b) {
    return 1.0;
  }

  @Override
  public Double apply(CellFormula formula) {
    try {
      return (Double) formula.accept(this);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("this is a cyclic reference");
    }
  }
}
