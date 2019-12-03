package edu.cs3500.spreadsheets.provider.view.model.cellcontents;

import edu.cs3500.spreadsheets.provider.view.model.cellcontents.formulas.IFormulaVisitor;

/**
 * Represents the contents of a Cell. Can be evaluated to produce an IValue, by using the Evaluate
 * IFormulaVisitor.
 */
public interface IFormula {

  /**
   * Allows for applying some Visitor to an IFormula based on the type of IFormula.
   *
   * @param visitor The visitor that will do different things based on the type of IFormula
   * @param <R>     the return type of the Visitor
   * @return some type determined by the Visitor
   */
  <R> R accept(IFormulaVisitor<R> visitor) throws InvalidFormulaException;
}
