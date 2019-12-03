package edu.cs3500.spreadsheets.provider.view.model.cellcontents.values;

import java.util.Objects;

import edu.cs3500.spreadsheets.provider.view.model.cellcontents.formulas.IFormulaVisitor;

/**
 * Represents a Double as an IValue.
 */
public final class DoubleValue implements IValue<Double> {

  private final Double d;

  /**
   * Constructs a DoubleValue with the given Double.
   *
   * @param dou double to be represented in this
   */
  public DoubleValue(Double dou) {
    d = dou;
  }

  /**
   * Accepts a Visitor that will apply some Function object using the Double this represents.
   *
   * @param visitor The Function we want to apply
   * @param <R>     the return type of the Function object
   * @return whatever the function object returns
   */
  @Override
  public <R> R accept(IFormulaVisitor<R> visitor) {
    return visitor.visitDoubleValue(d);
  }

  /**
   * Converts our DoubleValue into a String.
   *
   * @return the String representing our DoubleValue
   */
  @Override
  public String toString() {
    return String.format("%f", d);
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
    if (!(o instanceof DoubleValue)) {
      return false;
    }
    DoubleValue dou = (DoubleValue) o;

    return d.equals(dou.d);
  }

  /**
   * Creates a hash for our DoubleValues.
   *
   * @return the hash
   */
  @Override
  public int hashCode() {
    return Objects.hash(d);
  }

  /**
   * Returns the raw Double.
   *
   * @return the Double
   */
  @Override
  public Double val() {
    return this.d;
  }
}
