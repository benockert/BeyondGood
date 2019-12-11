package edu.cs3500.spreadsheets.bonus;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents a type of graph in a worksheet.
 */
public interface ExcellentWorksheetGraph {

  /**
   * Updates the region of cells used in the graph with the given cells.
   *
   * @param newCells The given cells to update the graph with.
   */
  void updateCellRegion(HashMap<Double, Double> newCells);

  /**
   * Returns the list of the coordinates of the referenced cells.
   *
   * @return An arraylist of the coordinates of the cells referenced in this graph.
   */
  List<Coord> getReferencedLocations();

  /**
   * Updates the coordinates of the referenced cells with the given coordinates.
   *
   * @param referencedLocations A list of the locations of the referenced cells.
   */
  void updateCellRefLocations(List<Coord> referencedLocations);


}
