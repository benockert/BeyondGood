import org.junit.Test;

import edu.cs3500.spreadsheets.controller.HighlightCell;
import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.view.BasicWorksheetEditorView;
import edu.cs3500.spreadsheets.view.SpreadsheetPanel;

import java.awt.*;
import java.awt.event.InputEvent;

import static org.junit.Assert.assertEquals;

/**
 * A test class to test the functionality of highlighting a cell.
 **/
public class HighlightCellTest {
  private BasicWorksheetModel worksheet;
  private BasicWorksheetReadOnlyModel readonly;
  private SpreadsheetPanel spreadsheet;
  private BasicWorksheetEditorView view;
  private HighlightCell highlightedCell;
  private Coord location;
  private Coord location2;


  private void initData() {
    this.worksheet = new BasicWorksheetModel();
    this.readonly = new BasicWorksheetReadOnlyModel(this.worksheet);
    this.spreadsheet = new SpreadsheetPanel(15, 30, this.readonly);
    this.spreadsheet.setHighlightLocation(0, 3);
    this.view = new BasicWorksheetEditorView(this.readonly);
    this.highlightedCell = new HighlightCell(this.spreadsheet, this.view);
    this.location = new Coord(1, 4);
    this.location2 = new Coord(1, 1);
  }

  @Test
  public void testMouseClicked() throws AWTException {
    initData();
    Robot bot = new Robot();
    bot.mouseMove(64, 84);
    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    assertEquals(location2, view.getHighlightedCell());
  }

}
