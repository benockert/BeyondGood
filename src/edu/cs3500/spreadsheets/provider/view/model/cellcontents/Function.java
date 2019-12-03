package edu.cs3500.spreadsheets.provider.view.model.cellcontents;

import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.provider.view.model.cellcontents.formulas.IFormulaVisitor;

/**
 * Represents a Function that can be evaluated as an IFormula. Contains an operator and the list of
 * operands (arguments).
 */
public final class Function implements IFormula {

  private final Func operator;
  private final List<IFormula> args;

  /**
   * Constructs a function that does 'o' to 'arguments'. For example, if 'o' is SUM, it will attempt
   * to add all of the contents in 'arguments' together.
   *
   * @param o         the operator
   * @param arguments the arguments of the function
   */
  public Function(Func o, List<IFormula> arguments) {
    operator = o;
    args = arguments;
  }

  /**
   * The currently-supported operations in our Spreadsheet program. SUM - adds all of the values in
   * its arguments. Ignores non-numeric values. PRODUCT - multiples all numeric values in its
   * arguments. Outputs 0.0 if no numerics found. COMPARE - returns if the 1st arg is smaller than
   * the 2nd. Throws if more/less than 2 args. SMALLESTSTRING - returns the smallest string found in
   * its args. If none found, returns null. Ignores non-Strings.
   */
  public enum Func {
    SUM, PRODUCT, COMPARE, SMALLESTSTRING;

    /**
     * Overrides the toString method for our enum. Only cares about our Funcs that differ from
     * default ie. COMPARE -> '<'
     *
     * @return the String representation
     */
    @Override
    public String toString() {
      if (this.equals(COMPARE)) {
        return "<";
      } else {
        return super.toString();
      }
    }
  }

  /**
   * Accepts a Visitor that will apply some function onto the evaluation of this.
   *
   * @param visitor the type of Function we want to apply to this
   * @param <R>     the return type of the Function object
   * @return some type determined by visitor
   * @throws InvalidFormulaException if either this could not be properly evaluated, or the function
   *                                 applied to the evaluation of this could not be evaluated
   */
  @Override
  public <R> R accept(IFormulaVisitor<R> visitor) throws InvalidFormulaException {
    return visitor.visitFunction(operator, args);
  }

  /**
   * Represents our Function as a String. String contains our operator name and the arguments.
   *
   * @return the String representing our Function
   */
  @Override
  public String toString() {
    String retVal = "";
    retVal += "(" + operator + " ";
    for (IFormula f : args) {
      retVal += f.toString() + " ";
    }
    retVal = retVal.substring(0, retVal.length() - 1);
    retVal += ")";
    return retVal.trim();
  }

  /**
   * Determines equality by checking the operator and the arguments.
   *
   * @param o the comparison
   * @return whether they are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Function)) {
      return false;
    }
    Function f = (Function) o;

    return operator.equals(f.operator) && args.equals(f.args);
  }

  /**
   * Overrides hashCode for Functions.
   *
   * @return the hash
   */
  @Override
  public int hashCode() {
    return Objects.hash(operator, args);
  }
}