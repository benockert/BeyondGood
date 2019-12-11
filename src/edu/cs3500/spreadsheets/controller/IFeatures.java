package edu.cs3500.spreadsheets.controller;

import java.util.List;

import edu.cs3500.spreadsheets.bonus.IllegalGraphConstruct;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents a controller for a basic worksheet. Allows the model to be modified and updates the
 * view to reflect the changes.
 */
public interface IFeatures {

  /**
   * Runs the controller, which allows a model to be edited and updates the view to display.
   */
  void run();

  /**
   * Handles a graphical button's request to update the contents of a cell.
   *
   * @param location    the coordinate location of the modified cell.
   * @param rawContents the new contents of the cell.
   */
  void acceptCellEdit(Coord location, String rawContents);

  /**
   * Handles a graphical button's request to reject edits to a cell, by resetting the text back to
   * what it was before editing.
   */
  void rejectCellEdit();

  /**
   * Handles a keyboard event to move the location of the highlighted cell.
   *
   * @param arrowKey the direction of the arrow key pressed.
   */
  void moveHighlightedCell(String arrowKey);

  /**
   * Handles the delete key, which removes the contents of a cell.
   *
   * @param location the location of the cell whose contents will be cleared.
   */
  void deleteCellContents(Coord location);

  /**
   * Handles the graph button, which constructs a graph view of the given cells.
   *
   * @param cellsToGraph the list of coordinates of the highlighted cells
   */
  void updateGraphView(List<Coord> cellsToGraph) throws IllegalGraphConstruct;

}
