package edu.cs3500.spreadsheets.cell;

import edu.cs3500.spreadsheets.function.CellVisitor;

/**
 * A class that represents a cell in a spreadsheet that contains a double.
 */
public class CellDouble<R> implements CellFormula {
  private double value;

  /**
   * Constructs a {@code CellDouble} object. A constructor for this CellDouble that takes in the
   * value as a double.
   *
   * @param value the double value in the cell.
   */
  public CellDouble(double value) {
    this.value = value;
  }

  @Override
  public Double evaluateCell() {
    return this.value;
  }

  @Override
  public String getRawContents() {
    return Double.toString(this.value);
  }

  @Override
  public Object accept(CellVisitor visit) {
    return visit.visitDouble(this);
  }

  @Override
  public String toString() {
    return Double.toString(this.value);
  }
}
