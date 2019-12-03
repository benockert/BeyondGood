package edu.cs3500.spreadsheets.provider.view.model.cellcontents;

import java.util.ArrayList;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.formulas.IFormulaVisitor;

import java.util.Objects;

/**
 * A Reference to a rectangle of Cells. For example, can represent the square of Cells encompassing
 * B1 to C3 (B1, B2, B3, C1, C2, C3). A Reference also needs to know which
 * edu.cs3500.spreadsheets.model it is referencing so that it can get the Cells at the coordinates
 * it stores.
 */
public final class Reference implements IFormula {

  private final ArrayList<Coord> coords;
  private Worksheet model;

  /**
   * Constructs a new Reference using the given Cells. This has already been determined to not
   * create a cycle when the Worksheet passed in was built.
   *
   * @param references the cells to be referenced.
   * @throws IllegalArgumentException if any of the Cells in the List refer to the Cell it is
   *                                  contained within.
   */
  public Reference(ArrayList<Coord> references, Worksheet m) {
    this.coords = references;
    this.model = m;
  }

  /**
   * Creates a Reference that does not have an associated Worksheet yet. NOTE: this is why Worksheet
   * is not final. Upon creating a Worksheet in createWorksheet(), we immediately update every
   * Reference to properly refer to its Worksheet, so this will not cause an issue.
   *
   * @param references the coordinates this is referencing.
   */
  public Reference(ArrayList<Coord> references) {
    coords = references;
  }

  /**
   * Accepts some Visitor that applies a Function onto the Cells that this references.
   *
   * @param visitor the visitor that will do something with this
   * @param <R>     the return type of the visitor
   * @return the return type of the visitor
   */
  @Override
  public <R> R accept(IFormulaVisitor<R> visitor) throws InvalidFormulaException {
    return visitor.visitReference(coords, model);
  }

  /**
   * Converts this into a string using all of its reference coordinates.
   *
   * @return the String representing this
   */
  @Override
  public String toString() {
    if (coords.size() == 1) {
      return min().toString();
    } else {
      return min().toString() + ":" + max().toString();
    }
  }

  /**
   * Returns the top-left Coord in the reference. Useful for toString.
   *
   * @return the smallest Coordinate
   */
  private Coord min() {
    Coord output = coords.get(0);
    for (Coord c : coords) {
      if (c.col < output.col || c.row < output.row) {
        output = c;
      }
    }
    return output;
  }

  /**
   * Returns the bottom-right Coord in the reference. Useful for toString.
   *
   * @return the Largest Coordinate
   */
  private Coord max() {
    Coord output = coords.get(0);
    for (Coord c : coords) {
      if (c.col > output.col || c.row > output.row) {
        output = c;
      }
    }
    return output;
  }

  /**
   * Overries equals for Reference.
   *
   * @param o the comparison
   * @return whether they are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Reference)) {
      return false;
    }
    Reference r = (Reference) o;

    return coords.equals(r.coords) && model.equals(r.model);
  }

  /**
   * Overrides the hashCode method.
   *
   * @return the hash
   */
  @Override
  public int hashCode() {
    return Objects.hash(coords);
  }

}
