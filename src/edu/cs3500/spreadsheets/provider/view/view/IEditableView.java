package edu.cs3500.spreadsheets.provider.view.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.controller.Features;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.IFormula;

/**
 * Represents a Spreadsheet view that is Editable.
 */
public interface IEditableView extends IView {

  /**
   * Adds the Features desired for the program.
   *
   * @param features the Features
   */
  void addFeatures(Features features);

  /**
   * Selects the cell at the coordinate and puts the formula in the input.
   *
   * @param c            the Coordinate
   * @param cellContents the String rep. of the Cell's contents
   */
  void setSelected(Coord c, String cellContents);

  /**
   * Returns the formula represented by the input.
   *
   * @return the IFormula represented
   */
  IFormula getFormulaInBox();
}
