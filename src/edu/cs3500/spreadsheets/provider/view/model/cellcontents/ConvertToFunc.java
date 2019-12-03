package edu.cs3500.spreadsheets.provider.view.model.cellcontents;

import java.util.List;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.Function.Func;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

/**
 * Converts the IFormula to a Func if it is the first argument in an SList. If the first argument is
 * not a Function, return null to signify an error. Also returns null if it is an unsupported Func.
 */
public class ConvertToFunc implements SexpVisitor<Func> {

  /**
   * Throws an error, since a function must start with an operator.
   *
   * @param b the value
   * @throws IllegalArgumentException because this shouldn't be the first thing in an SList
   */
  @Override
  public Func visitBoolean(boolean b) {
    throw new IllegalArgumentException("#VALUE!");
  }

  /**
   * Throws an error, since a function must start with an operator.
   *
   * @param d the value
   * @throws IllegalArgumentException because this shouldn't be the first thing in an SList
   */
  @Override
  public Func visitNumber(double d) {
    throw new IllegalArgumentException("#VALUE!");
  }

  /**
   * Throws an error, since a function must start with an operator.
   *
   * @param l the value
   * @throws IllegalArgumentException because this shouldn't be the first thing in an SList
   */
  @Override
  public Func visitSList(List<Sexp> l) {
    throw new IllegalArgumentException("#VALUE!");
  }

  /**
   * Determines which Function is attempting to be represented.
   *
   * @param s the value
   * @return the Func represented by the input, or null, to signify error
   * @throws IllegalArgumentException if the symbol is not a proper function
   */
  @Override
  public Func visitSymbol(String s) {
    switch (s) {
      case "SUM":
        return Func.SUM;
      case "PRODUCT":
        return Func.PRODUCT;
      case "<":
        return Func.COMPARE;
      case "SMALLESTSTRING":
        return Func.SMALLESTSTRING;
      default:
        throw new IllegalArgumentException("#VALUE!");
    }
  }

  /**
   * Throws an error, since a function must start with an operator.
   *
   * @param s the value
   * @throws IllegalArgumentException because this shouldn't be the first thing in an SList
   */
  @Override
  public Func visitString(String s) {
    throw new IllegalArgumentException("#VALUE!");
  }
}
