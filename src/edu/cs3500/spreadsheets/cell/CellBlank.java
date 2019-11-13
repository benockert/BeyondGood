package edu.cs3500.spreadsheets.cell;

import edu.cs3500.spreadsheets.function.CellVisitor;

/**
 * Represents a blank cell in a spreadsheet. No contents, displayed as an empty string.
 */
public class CellBlank implements CellFormula {

  @Override
  public String evaluateCell() {
    return "";
  }

  @Override
  public String getRawContents() {
    return "";
  }

  @Override
  public Object accept(CellVisitor visit) {
    return visit.visitBlank(this);
  }

}