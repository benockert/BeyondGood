package edu.cs3500.spreadsheets.provider.view.model.cellcontents.formulas;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.model.ISpreadsheet;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.Function;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.IFormula;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.InvalidFormulaException;

import java.util.ArrayList;
import java.util.List;


/**
 * Function Object that allows us to visit an IFormula and do something based on what kind of
 * IFormula was visited.
 *
 * @param <R> The return type of whatever function object implements this
 */
public interface IFormulaVisitor<R> {

  /**
   * Visits a BooleanValue.
   *
   * @param b the Boolean the BooleanValue contained
   * @return some type determined by implementation
   */
  R visitBooleanVal(Boolean b);

  /**
   * Visits a DoubleValue.
   *
   * @param d the Double the DoubleValue contained
   * @return some type determined by implementation
   */
  R visitDoubleValue(Double d);

  /**
   * Visits a StringValue.
   *
   * @param s the String the StringValue contained
   * @return some type determined by implementation
   */
  R visitStringValue(String s, String error) throws InvalidFormulaException;

  /**
   * Visits a Function.
   *
   * @param o    the operator from the Function
   * @param args the arguments from the Function
   * @return some type determined by implementation
   * @throws InvalidFormulaException if the Function could not be visited properly
   */
  R visitFunction(Function.Func o, List<IFormula> args) throws InvalidFormulaException;

  /**
   * Visits a reference.
   *
   * @param coords the referenced coords
   * @param model  the model we are referencing
   * @return the output of the visitor
   * @throws InvalidFormulaException if the visit had a bad Formula
   */
  R visitReference(ArrayList<Coord> coords, ISpreadsheet model) throws InvalidFormulaException;

  /**
   * Allows us to get the IFormulas contained at the Coords referenced in the Reference. Requires
   * knowledge of the Worksheet we are referencing to interpret Coords.
   *
   * @param coords the Coords of the desired Cells
   * @param model  the Worksheet that is referenced
   * @return The list of IFormulas contained in the referenced Cells
   */
  static ArrayList<IFormula> referenceValues(ArrayList<Coord> coords, ISpreadsheet model) {
    ArrayList<Coord> copy = new ArrayList<Coord>(coords);
    ArrayList<IFormula> cells = new ArrayList<IFormula>();

    for (Coord c : copy) {
      if (model != null) {
        if (model.getAt(c) != null) {
          cells.add(model.formulaAt(c));
        }
      }
    }

    ArrayList<IFormula> output = new ArrayList<>();
    for (IFormula c : cells) {
      if (c != null) {
        output.add(c);
      }
    }

    return output;
  }
}