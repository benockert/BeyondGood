package edu.cs3500.spreadsheets.function;

import edu.cs3500.spreadsheets.cell.CellBlank;
import edu.cs3500.spreadsheets.cell.CellBoolean;
import edu.cs3500.spreadsheets.cell.CellDouble;
import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.cell.CellFunction;
import edu.cs3500.spreadsheets.cell.CellReference;
import edu.cs3500.spreadsheets.cell.CellString;

/**
 * A function object for the LessThan function of a spreadsheet, which compares the value of two
 * numbers and determines if the first is less than the second.
 */
public class LessThan implements CellVisitor<CellFormula, Double> {

  // evaluates the given CellDouble for use in the less than function
  @Override
  public Double visitDouble(CellDouble d) {
    return d.evaluateCell();
  }

  // since a boolean is a non-numeric value, we throw an exception
  @Override
  public Double visitBoolean(CellBoolean b) {
    throw new IllegalArgumentException("Cannot compare booleans");
  }

  // evaluates the function and applies the less than function object to it to handle what
  // return type it is
  @Override
  public Double visitFunction(CellFunction f) {
    return this.apply(f.cellFunctionEvaluated);
  }

  // evaluates the first cell of a given reference for use in the less than function
  @Override
  public Double visitReference(CellReference r) {
    return this.apply(r.cells.get(0));
  }

  // since a boolean is a non-numeric value, we throw an exception
  @Override
  public Double visitString(CellString s) {
    throw new IllegalArgumentException("Cannot compare strings");
  }

  @Override
  public Double visitBlank(CellBlank b) {
    throw new IllegalArgumentException("Cannot compare two blank cells");
  }

  @Override
  public Double apply(CellFormula formula) {
      return (Double) formula.accept(this);
  }
}
