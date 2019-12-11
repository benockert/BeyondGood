package edu.cs3500.spreadsheets.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.bonus.IllegalGraphConstruct;
import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.view.BasicWorksheetView;

/**
 * Represents the controller of a basic worksheet, which controls what the model and view receive
 * when editing the worksheet.
 */
public class BasicWorksheetController implements IFeatures {
  private Worksheet model;
  private BasicWorksheetView view;

  /**
   * Constructs a {@code BasicWorksheetControllerObject}, which passes objects between the view and
   * model.
   *
   * @param model The model that we want to mutate with this controller.
   * @param view  The view that we want to use to display what the controller mutates.
   */
  public BasicWorksheetController(Worksheet model, BasicWorksheetView view) {
    this.model = model;
    this.view = view;
    this.view.addIFeatures(this);
  }

  /**
   * Runs the controller, which displays the graphical of this model.
   */
  @Override
  public void run() {
    this.view.render();
  }

  @Override
  public void acceptCellEdit(Coord location, String rawContents) {
    this.model.editCell(rawContents, location);
    for (Coord coord : this.model.getCells().keySet()) {
      String cellsRawContents = this.model.getCellAt(coord).getRawContents();
      this.model.editCell("=" + cellsRawContents, coord);
    }
    List<Coord> graphCellLocs = this.view.getGraphsReferencedCoords();
    this.updateGraphView(graphCellLocs);
    this.view.refresh();




  }

  @Override
  public void rejectCellEdit() {
    this.view.setTextbox();
  }

  @Override
  public void moveHighlightedCell(String arrowKey) {
    switch (arrowKey) {
      case "up arrow":
        this.view.changeHighlightedCellLocation(0, -1);
        this.view.setTextbox();
        break;
      case "down arrow":
        this.view.changeHighlightedCellLocation(0, 1);
        this.view.setTextbox();
        break;
      case "left arrow":
        this.view.changeHighlightedCellLocation(-1, 0);
        this.view.setTextbox();
        break;
      case "right arrow":
        this.view.changeHighlightedCellLocation(1, 0);
        this.view.setTextbox();
        break;
      default:
        break;
    }
    this.view.refresh();
  }

  @Override
  public void deleteCellContents(Coord location) {
    this.model.removeCell(location);
    this.acceptCellEdit(location, "");
    this.view.setTextbox();
    this.view.refresh();
  }

  // Code for creating the graph view
  @Override
  public void updateGraphView(List<Coord> cellsToGraph) {
    try {

      // determines which column is the x axis and which is the y axis
      List<Integer> twoColumns = getTwoColumns(cellsToGraph);
      int leftColIndex;
      int rightColIndex;
      if (twoColumns.get(0) > twoColumns.get(1)) {
        leftColIndex = twoColumns.get(1);
        rightColIndex = twoColumns.get(0);
      } else {
        leftColIndex = twoColumns.get(0);
        rightColIndex = twoColumns.get(1);
      }

      // based on the columns above, gets the list of the highlighted cells in that column
      List<Coord> xAxis = new ArrayList<>();
      List<Coord> yAxis = new ArrayList<>();
      for (Coord coord : cellsToGraph) {
        if (coord.col == leftColIndex) {
          xAxis.add(coord);
        }
        if (coord.col == rightColIndex) {
          yAxis.add(coord);
        }
      }

      HashMap<CellFormula, CellFormula> xyMap = new HashMap<>();
      if (xAxis.size() != yAxis.size()) {
        throw new IllegalGraphConstruct("Cannot construct graph: Mismatched column sizes");
      } else {
        for (int i = 0; i < xAxis.size(); i++) {
          CellFormula cellValueAtX = this.model.getCellAt(xAxis.get(i));
          CellFormula cellValueAtY = this.model.getCellAt(yAxis.get(i));
          xyMap.put(cellValueAtX, cellValueAtY);
        }
      }
      this.view.updateGraph(cellsToGraph, xyMap);
    } catch (IllegalGraphConstruct igc) {
      this.view.addGraphErrorMessage(igc.getMessage());
    }
  }


  private List<Integer> getTwoColumns(List<Coord> cells) throws IllegalGraphConstruct {
    List<Integer> cols = new ArrayList<>();
    // goes throw the list of coordinates and gets the column indexes of two columns
    for (Coord coord : cells) {
      int columnIndex = coord.col;
      if (!cols.contains(columnIndex)) {
        cols.add(columnIndex);
      }
    }
    // if there is only column represented in the list, becomes invalid
    if (cols.size() != 2) {
      throw new IllegalGraphConstruct("Cannot construct graph: Does not have exactly 2 columns " +
              "of data");
    }
    return cols;
  }


}

