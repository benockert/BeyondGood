package edu.cs3500.spreadsheets.view;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.controller.IFeatures;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents the view for a worksheet which saves a given model to a new text file.
 */
public class BasicWorksheetSaveView implements BasicWorksheetView {
  private final BasicWorksheetReadOnlyModel model;
  private PrintWriter appendable;

  /**
   * Constructs a new view of the given model, which saves the model in a readable text format.
   *
   * @param model      the model of the worksheet to be saved
   * @param appendable the appendable being added to
   */
  public BasicWorksheetSaveView(BasicWorksheetReadOnlyModel model, PrintWriter appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  @Override
  public void render() {
    // adds the model (as a line-separated-String of spreadsheet cell raw contents) to a file
    this.appendable.append(this.toString());
    // writes the file and closes it
    this.appendable.close();
  }

  @Override
  public void refresh() {
    this.render();
  }

  @Override
  public void addIFeatures(IFeatures feature) {
    // this view does not have any features
  }

  @Override
  public void setTextbox() {
    // this view does not have a textbox
  }

  @Override
  public void changeHighlightedCellLocation(int i, int i1) {
    // this view does not support changing the highlighted cell
  }

  @Override
  public void updateGraph(List<Coord> cellLocs, HashMap<Double, Double> cellVals) {
    // nothing to do yet
  }

  // gets the coordinate that the currently displayed graph references
  @Override
  public List<Coord> getGraphsReferencedCoords() {
    // nothing to do yet
    return new ArrayList<Coord>();
  }

  @Override
  public void addGraphErrorMessage(String message) {
    // no error message to display in this view
  }

  @Override
  public String toString() {
    String rawContents = "";
    HashMap<Coord, CellFormula> modelCells = model.getCells();
    for (Map.Entry<Coord, CellFormula> cell : modelCells.entrySet()) {
      String coordinate = cell.getKey().toString();
      rawContents += coordinate + " =" + cell.getValue().getRawContents() + "\n";
    }
    return rawContents;
  }
}
