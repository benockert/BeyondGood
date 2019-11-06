package edu.cs3500.spreadsheets.function;

import edu.cs3500.spreadsheets.cell.CellBlank;
import edu.cs3500.spreadsheets.cell.CellBoolean;
import edu.cs3500.spreadsheets.cell.CellDouble;
import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.cell.CellFunction;
import edu.cs3500.spreadsheets.cell.CellReference;
import edu.cs3500.spreadsheets.cell.CellString;

/**
 * A function object for the Repeat function of a spreadsheet, which repeats a value a set number of
 * times.
 */
public class Repeat implements CellVisitor<CellFormula, String> {

  // gets the raw contents (a.k.a the string representation of the double) because our
  // functionality of repeat allows doubles to be repeated (just like in Excel)
  @Override
  public String visitDouble(CellDouble d) {
    return d.getRawContents();
  }

  // gets the raw contents (a.k.a the string representation of the boolean) because our
  // functionality of repeat allows booleans to be repeated (just like in Excel)
  @Override
  public String visitBoolean(CellBoolean b) {
    return b.getRawContents();
  }

  // evaluates the function and applies the repeat function object to it to handle what
  // return type it is
  @Override
  public String visitFunction(CellFunction f) {
    return this.apply(f.cellFunctionEvaluated);
  }

  // evaluate the reference, convert it to a string, and this will be the value that is repeated
  @Override
  public String visitReference(CellReference r) {
    return r.evaluateCell().toString();
  }

  // gets the raw contents (a.k.a the string representation of the double) because our
  // functionality of repeat allows strings to be repeated (just like in Excel)
  @Override
  public String visitString(CellString s) {
    return s.evaluateCell();
  }

  // blank cells should not add anything to the repeated string, so they return their default
  // raw contents of an empty string
  @Override
  public String visitBlank(CellBlank b) {
    return b.getRawContents();
  }

  @Override
  public String apply(CellFormula formula) {
    try {
      return (String) formula.accept(this);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("this is a cyclic reference");
    }
  }
}
