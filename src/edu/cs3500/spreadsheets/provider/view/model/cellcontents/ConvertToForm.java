package edu.cs3500.spreadsheets.provider.view.model.cellcontents;

import edu.cs3500.spreadsheets.provider.view.ReadCoord;
import java.util.ArrayList;
import java.util.List;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.values.BooleanVal;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.values.DoubleValue;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.values.StringValue;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

/**
 * Converts a Sexp into an IFormula, returning the conversion.
 */
public class ConvertToForm implements SexpVisitor<IFormula> {

  /**
   * Returns a new BooleanVal based on the input.
   *
   * @param b the value
   * @return the BooleanVal
   */
  @Override
  public IFormula visitBoolean(boolean b) {
    return new BooleanVal(b);
  }

  /**
   * Returns a new DoubleValue based on the input.
   *
   * @param d the value
   * @return the DoubleValue
   */
  @Override
  public IFormula visitNumber(double d) {
    return new DoubleValue(d);
  }

  /**
   * First, ensures the first argument is a function, and stores it. Then, goes through and converts
   * all of its argument, storing both as a new Function. Will return null if the method that calls
   * this should error (improper format).
   *
   * @param l the contents of the list (not yet visited)
   * @return the converted Function
   */
  @Override
  public IFormula visitSList(List<Sexp> l) {
    Function.Func f = l.get(0).accept(new ConvertToFunc());

    ArrayList<Sexp> args = new ArrayList<>(l);
    //remove Func to distinguish arguments
    args.remove(0);

    if (args.size() == 0) {
      throw new IllegalArgumentException("#VALUE!");
    }

    ArrayList<IFormula> convertedArgs = new ArrayList<>();

    for (Sexp s : args) {
      convertedArgs.add(s.accept(new ConvertToForm()));
    }
    return new Function(f, convertedArgs);
  }

  /**
   * Symbols that are not the first in an SList are references. So, converts the symbol to a
   * reference.
   *
   * @param s the value
   * @return the converted Reference
   */
  @Override
  public IFormula visitSymbol(String s) {
    try {
      return new Reference(convertToReference(s));
    } catch (InvalidFormulaException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Returns a new StringValue based on the input.
   *
   * @param s the value
   * @return the StringValue
   */
  @Override
  public IFormula visitString(String s) {
    return new StringValue(s);
  }

  /**
   * Converts the given String (from visitSymbol) into a reference, or throws an error if it
   * cannot.
   *
   * @param s the String to be converted
   * @return the ArrayList that the String represented
   * @throws InvalidFormulaException if the Symbol could not be converted properly (malformed). It
   *                                 is considered malformed not only if it doesn't follow the
   *                                 proper format, but does not refer to either a single cell, a
   *                                 rectangular region of MULTIPLE cells, or an in-bounds cell.
   */
  private ArrayList<Coord> convertToReference(String s) throws InvalidFormulaException {
    try {
      return ReadCoord.coordinates(s);
    } catch (InvalidFormulaException e) {
      throw new InvalidFormulaException("#REF!");
    }
  }
}