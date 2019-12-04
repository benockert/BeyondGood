package edu.cs3500.spreadsheets.adapters;

import java.util.ArrayList;
import java.util.List;

import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.provider.view.model.ISpreadsheet;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.IFormula;

public class ProviderModelAdapter implements ISpreadsheet {
  private Worksheet ourModel;

  public ProviderModelAdapter(Worksheet ourModel) {
    this.ourModel = ourModel;
  }

  @Override
  public List<Coord> getNonEmpty() {
    List<Coord> nonEmpty = new ArrayList<>();
    for (Coord c : this.ourModel.getCells().keySet()) {
      if (this.ourModel.getCellAt(c).evaluateCell() != "") {
        nonEmpty.add(c);
      }
    }
    return nonEmpty;
  }

  @Override
  public Object getAt(Coord c) {
    return this.ourModel.getCellAt(c);
  }

  @Override
  public String evaluateAt(Coord c) {
    return this.ourModel.getCellAt(c).toString();
  }

  @Override
  public void changeAt(Coord c, IFormula contents) {
    //this.ourModel.editCell(contents.accept());

  }

  @Override
  public IFormula formulaAt(Coord c) {
    CellFormula form = this.ourModel.getCellAt(c);
    FormulaAdapter adapter = new FormulaAdapter(form, this);
    //adapter.accept(new CellVisitorAdapter(this));
    return null;
  }


}
