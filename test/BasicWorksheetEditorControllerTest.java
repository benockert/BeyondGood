import org.junit.Test;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.BasicWorksheetController;
import edu.cs3500.spreadsheets.controller.HighlightCell;
import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.view.BasicWorksheetEditorView;
import edu.cs3500.spreadsheets.view.SpreadsheetPanel;

import static org.junit.Assert.assertEquals;

/**
 * A test class to test the functionality of our controller.
 */
public class BasicWorksheetEditorControllerTest {
  private BasicWorksheetModel worksheet;
  private BasicWorksheetReadOnlyModel readonly;
  private SpreadsheetPanel spreadsheet;
  private BasicWorksheetEditorView view;
  private BasicWorksheetController controller;
  private HighlightCell highlightedCell;
  private JButton clear;
  private JButton enter;
  private Coord location;
  private Coord location2;

  private void initData() {
    this.location = new Coord(1, 4);
    this.location2 = new Coord(1, 1);
    this.worksheet = new BasicWorksheetModel();
    this.readonly = new BasicWorksheetReadOnlyModel(this.worksheet);
    this.spreadsheet = new SpreadsheetPanel(15, 30, this.readonly);
    this.spreadsheet.setHighlightLocation(0, 3);
    this.view = new BasicWorksheetEditorView(this.readonly);
    this.controller = new BasicWorksheetController(this.worksheet, this.view);
    this.highlightedCell = new HighlightCell(this.spreadsheet, this.view);
    this.clear = new JButton("✘");
    this.clear.setActionCommand("Reject button");
    this.enter = new JButton("✔");
    this.enter.setActionCommand("Accept button");

  }

}
