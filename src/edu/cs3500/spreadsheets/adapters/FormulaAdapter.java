package edu.cs3500.spreadsheets.adapters;

import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.IFormula;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.InvalidFormulaException;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.formulas.IFormulaVisitor;

public class FormulaAdapter implements IFormula {
  private CellFormula form;
  private ProviderModelAdapter model;

  FormulaAdapter(CellFormula form, ProviderModelAdapter model) {
    this.form = form;
    this.model = model;
  }

  @Override
  public <R> R accept(IFormulaVisitor<R> visitor) throws InvalidFormulaException {
    return (R) this.form.accept(new CellVisitorAdapter(visitor, this.model));
  }

}
