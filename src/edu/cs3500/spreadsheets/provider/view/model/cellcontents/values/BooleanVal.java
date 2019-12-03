package edu.cs3500.spreadsheets.provider.view.model.cellcontents.values;

import java.util.Objects;

import edu.cs3500.spreadsheets.provider.view.model.cellcontents.formulas.IFormulaVisitor;

/**
 * Represents a Boolean Value as an IValue.
 */
public final class BooleanVal implements IValue<Boolean> {

  private final Boolean b;

  /**
   * Constructs a BooleanVal with the given boolean.
   *
   * @param boo the boolean to be represented by this
   */
  public BooleanVal(Boolean boo) {
    b = boo;
  }

  /**
   * Accepts a Visitor that will apply some Function object using the Boolean this represents.
   *
   * @param visitor The Function we want to apply
   * @param <R>     the return type of the Function object
   * @return whatever the function object returns
   */
  @Override
  public <R> R accept(IFormulaVisitor<R> visitor) {
    return visitor.visitBooleanVal(b);
  }

  /**
   * Converts our BooleanVal into a String.
   *
   * @return the String representing our BooleanVal
   */
  @Override
  public String toString() {
    return String.format("%b", b);
  }

  /**
   * Determines equality against a comparison.
   *
   * @param o the comparison
   * @return whether they are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BooleanVal)) {
      return false;
    }

    BooleanVal boo = (BooleanVal) o;

    return b.equals(boo.b);
  }

  /**
   * Creates a hash for our DoubleValues.
   *
   * @return the hash
   */
  @Override
  public int hashCode() {
    return Objects.hash(b);
  }

  /**
   * Returns the Boolean contained in this.
   *
   * @return true or false
   */
  @Override
  public Boolean val() {
    return this.b;
  }
}