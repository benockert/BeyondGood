package edu.cs3500.spreadsheets.adapters;

import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.IFormula;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.formulas.IFormulaVisitor;

/**
 * Represents a class which adapts our CellFormula interface to the provider's IFormula interface.
 */
public class FormulaAdapter implements IFormula {
  private CellFormula form;
  private ProviderModelAdapter model;

  /**
   * Constructs a {@code FormulaAdapter} object, which adapts our cell interface to theirs.
   *
   * @param form  Our interface for representing different types of cells.
   * @param model The adapted model (our model adapted to their interface).
   */
  FormulaAdapter(CellFormula form, ProviderModelAdapter model) {
    this.form = form;
    this.model = model;
  }

  @Override
  public <R> R accept(IFormulaVisitor<R> visitor) {
    return (R) this.form.accept(new CellVisitorAdapter(visitor, this.model));
  }

}
