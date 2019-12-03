package edu.cs3500.spreadsheets.provider.view.model.cellcontents.values;

import java.util.Objects;

import edu.cs3500.spreadsheets.provider.view.model.cellcontents.InvalidFormulaException;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.formulas.IFormulaVisitor;

/**
 * A representation of String using IValue. Contains either a proper String, or the String
 * representation of an Invalid Formula. If so, contains the error associated with the
 * InvalidFormula.
 */
public final class StringValue implements IValue<String> {

  private final String s;
  private final String exceptionMessage;

  /**
   * This represents a proper string that has no errors.
   *
   * @param str the represented string
   */
  public StringValue(String str) {
    s = str;
    exceptionMessage = null;
  }

  /**
   * This constructor exists solely to store a bad input that could not be properly converted into a
   * formula.
   *
   * @param str      the bad input
   * @param eMessage the Exception message that the bad input threw
   */
  public StringValue(String str, String eMessage) {
    s = str;
    exceptionMessage = eMessage;
  }

  /**
   * Accepts a visitor object that will perform a function on this string. If this is a proper
   * string, does so. Otherwise, throws an error, as this String cannot have anything done to it.
   *
   * @param visitor The visitor that will do different things based on the type of IFormula
   * @param <R>     the output of the visitor
   * @return the output of the visitor
   * @throws InvalidFormulaException if this is representing an InvalidFormula
   */
  @Override
  public <R> R accept(IFormulaVisitor<R> visitor) throws InvalidFormulaException {
    return visitor.visitStringValue(s, exceptionMessage);
  }

  /**
   * Returns the String. It is already a String.
   *
   * @return the String represented in our StringValue
   */
  @Override
  public String toString() {
    if (exceptionMessage != null) {
      return s;
    }
    return "\"" + s + "\"";
  }

  /**
   * Determines equality against a comparison. Note that two StringValues may be different depending
   * on whether one represents an InvalidIFormula and the other just happens to represent a String
   * that looks like an InvalidIFormula.
   *
   * @param o the comparison
   * @return whether they are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof StringValue)) {
      return false;
    }
    StringValue str = (StringValue) o;

    if (exceptionMessage == null && str.exceptionMessage == null) {
      return s.equals(str.s);
    }

    if (exceptionMessage != null && str.exceptionMessage != null) {
      return s.equals(str.s) && exceptionMessage.equals(str.exceptionMessage);
    } else {
      return false;
    }
  }

  /**
   * Creates a hash for our DoubleValues.
   *
   * @return the hash
   */
  @Override
  public int hashCode() {
    return Objects.hash(s, exceptionMessage);
  }

  /**
   * Returns the raw String.
   *
   * @return the String in this
   */
  @Override
  public String val() {
    return this.s;
  }
}
