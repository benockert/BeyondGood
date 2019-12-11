package edu.cs3500.spreadsheets.view;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.controller.IFeatures;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents a view of a basic worksheet - either graphical or text.
 */
public interface BasicWorksheetView {

  /**
   * Renders a {@link edu.cs3500.spreadsheets.model.BasicWorksheetModel} in some manner.
   */
  void render();

  /**
   * Refreshes a view.
   */
  void refresh();

  /**
   * Adds the given features to this view.
   *
   * @param feature The features to be added to the view.
   */
  void addIFeatures(IFeatures feature);

  /**
   * Sets the text in the textbox to be the highlighted cell's contents.
   */
  void setTextbox();

  /**
   * Changes the location of the highlighted cell based on integer inputs.
   *
   * @param i  The number of columns the highlighted cell is moving.
   * @param i1 The number of rows the highlighted cell is moving.
   */
  void changeHighlightedCellLocation(int i, int i1);

  /**
   * Updates the graph view of the highlighted cells.
   *
   * @param cellLocations the list of coordinates that are referenced within the graph
   * @param cellValues the hashmap of lef column to right column cells
   */
  void updateGraph(List<Coord> cellLocations, HashMap<Double, Double> cellValues);

  /**
   * Gets the list of cells from the spreadsheet model that the graph references, such that the
   * graph can be updated with the proper cells when the cells change.
   *
   * @return the list of spreadsheet coordinates that the graph refers to
   */
  List<Coord> getGraphsReferencedCoords();

  /**
   * Adds an error message to the view if the graph is invalid.
   * @param message The message to display.
   */
  void addGraphErrorMessage(String message);

}



