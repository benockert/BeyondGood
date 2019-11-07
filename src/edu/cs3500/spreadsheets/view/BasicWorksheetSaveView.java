package edu.cs3500.spreadsheets.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;

public class BasicWorksheetSaveView implements BasicWorksheetView {
  private final BasicWorksheetModel model;
  private PrintWriter appendable;

  /**
   * Constructs a new view of the given model, which saves the model in a readable text format.
   *
   * @param model      the model of the worksheet to be saved
   * @param appendable the appendable being added to
   */
  public BasicWorksheetSaveView(BasicWorksheetModel model, PrintWriter appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  @Override
  public void render() throws IOException {
    // adds the model (as a line-separated-String of spreadsheet cell raw contents) to a file
    this.appendable.append(this.toString());
    // writes the file and closes it
    this.appendable.close();
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
