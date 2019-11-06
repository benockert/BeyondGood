package edu.cs3500.spreadsheets.cell;

import edu.cs3500.spreadsheets.function.CellVisitor;

/**
 * A class that represents a cell in a spreadsheet with a String value.
 */
public class CellString implements CellFormula {
  private String value;

  /**
   * Constructs a {@code CellString} object. A basic constructor for this CellString that takes in a
   * String and sets it as the value of this cell.
   *
   * @param value the string value of this cell.
   */
  public CellString(String value) {
    this.value = value;
  }

  @Override
  public String evaluateCell() {
    return this.value;
  }

  @Override
  public String getRawContents() {
    return this.value;
  }

  @Override
  public Object accept(CellVisitor visit) {
    return visit.visitString(this);
  }
}
