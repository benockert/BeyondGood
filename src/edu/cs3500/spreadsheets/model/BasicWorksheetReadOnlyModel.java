package edu.cs3500.spreadsheets.model;

import java.util.HashMap;

import edu.cs3500.spreadsheets.cell.CellFormula;

public class BasicWorksheetReadOnlyModel implements Worksheet {
  Worksheet mutableModel;

  public BasicWorksheetReadOnlyModel(Worksheet model) {
    this.mutableModel = model;
  }

  @Override
  public void editCell(boolean input, Coord location) {
    throw new UnsupportedOperationException("we do not allow this model to be edited");
  }

  @Override
  public void editCell(String input, Coord location) {
    throw new UnsupportedOperationException("we do not allow this model to be edited");
  }

  @Override
  public void editCell(double input, Coord location) {
    throw new UnsupportedOperationException("we do not allow this model to be edited");
  }

  @Override
  public CellFormula getCellAt(Coord location) {
    return this.mutableModel.getCellAt(location);
  }

  @Override
  public HashMap<Coord, CellFormula> getCells() {
    return this.mutableModel.getCells();
  }

  @Override
  public int getNumRows() {
    return this.mutableModel.getNumRows();
  }

  @Override
  public int getNumCols() {
    return this.mutableModel.getNumCols();
  }

  @Override
  public void removeCell(Coord location) {
    throw new UnsupportedOperationException("we do not allow this model to be edited");
  }
}
