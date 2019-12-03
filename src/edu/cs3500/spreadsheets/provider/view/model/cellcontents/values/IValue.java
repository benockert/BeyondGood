package edu.cs3500.spreadsheets.provider.view.model.cellcontents.values;

import edu.cs3500.spreadsheets.provider.view.model.cellcontents.IFormula;

/**
 * A way of representing values as IFormulas (String, Boolean, Number). These values do not simply
 * implement IFormula themselves because we must specify that our Evaluate function object returns
 * the specific type IValue (rather than just any IFormula).
 */
public interface IValue<K> extends IFormula {

  /**
   * Returns the raw value contained in an IValue.
   *
   * @return the raw value
   */
  K val();
}