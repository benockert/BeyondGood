import org.junit.Test;

import edu.cs3500.spreadsheets.controller.BasicWorksheetController;
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
  private BasicWorksheetEditorView view;
  private BasicWorksheetController controller;
  private Coord location;
  private Coord location2;

  private void initData() {
    this.location = new Coord(1, 4);
    this.location2 = new Coord(1, 1);
    this.worksheet = new BasicWorksheetModel();
    BasicWorksheetReadOnlyModel readonly = new BasicWorksheetReadOnlyModel(this.worksheet);
    SpreadsheetPanel spreadsheet = new SpreadsheetPanel(15, 30, readonly);
    this.view = new BasicWorksheetEditorView(readonly);
    this.controller = new BasicWorksheetController(this.worksheet, this.view);
  }

  @Test
  public void testAcceptButton() {
    initData();
    this.worksheet.editCell("5.0", this.location);
    assertEquals(5.0, this.worksheet.getCellAt(this.location).evaluateCell());
    // tests that the accept button properly changes the contents of a cell at a location
    this.controller.acceptCellEdit(this.location, "hello");
    assertEquals("hello", this.worksheet.getCellAt(this.location).evaluateCell());
  }

  @Test
  public void testAcceptButtonUpdatesOtherCells() {
    initData();
    this.worksheet.editCell("5.0", this.location);
    this.worksheet.editCell("=A4", this.location2);
    assertEquals(5.0, this.worksheet.getCellAt(this.location).evaluateCell());
    assertEquals(5.0, this.worksheet.getCellAt(this.location2).evaluateCell());
    // tests that the accept button properly changes the contents of a cell at a location and also
    // updates the values of the cells that refer to the updated cell, without changing its
    // raw contents
    this.controller.acceptCellEdit(this.location, "10.0");
    assertEquals(10.0, this.worksheet.getCellAt(this.location).evaluateCell());
    assertEquals(10.0, this.worksheet.getCellAt(this.location2).evaluateCell());
    assertEquals("A4", this.worksheet.getCellAt(this.location2).getRawContents());
  }

  @Test
  public void testRejectButton() {
    initData();
    this.worksheet.editCell("=(SUM 5.0 6.0)", this.location);
    assertEquals(11.0, this.worksheet.getCellAt(this.location).evaluateCell());
    // tests that the reject button disregards the user input and keeps the cell the same
    this.controller.rejectCellEdit();
    assertEquals(11.0, this.worksheet.getCellAt(this.location).evaluateCell());
  }

  @Test
  public void testArrows() {
    initData();
    this.worksheet.editCell("=(SUM 5.0 6.0)", this.location);
    assertEquals(11.0, this.worksheet.getCellAt(this.location).evaluateCell());
    assertEquals(this.location2, this.view.getHighlightedCell());
    // tests that the arrow keys properly move the highlighted cell location around
    this.controller.moveHighlightedCell("down arrow");
    assertEquals(new Coord(1, 2), this.view.getHighlightedCell());
    this.controller.moveHighlightedCell("right arrow");
    assertEquals(new Coord(2, 2), this.view.getHighlightedCell());
    this.controller.moveHighlightedCell("up arrow");
    assertEquals(new Coord(2, 1), this.view.getHighlightedCell());
    this.controller.moveHighlightedCell("left arrow");
    assertEquals(this.location2, this.view.getHighlightedCell());
  }

  @Test
  public void testDelete() {
    initData();
    this.worksheet.editCell("=(SUM 5.0 6.0)", this.location2);
    this.worksheet.editCell("hello", this.location);
    this.worksheet.editCell("true", new Coord(2, 3));
    this.worksheet.editCell("=A1", new Coord(4, 4));
    assertEquals(this.location2, this.view.getHighlightedCell());
    assertEquals("hello", this.worksheet.getCellAt(this.location).evaluateCell());
    assertEquals(11.0, this.worksheet.getCellAt(this.location2).evaluateCell());
    assertEquals("(SUM 5.0 6.0)", this.worksheet.getCellAt(this.location2).getRawContents());
    assertEquals(true, this.worksheet.getCellAt(new Coord(2, 3)).evaluateCell());
    assertEquals(11.0, this.worksheet.getCellAt(new Coord(4, 4)).evaluateCell());
    // tests that the delete key properly removes the cell from the spreadsheet and updates
    // all cells
    this.controller.deleteCellContents(this.location2);
    assertEquals("", this.worksheet.getCellAt(this.location2).evaluateCell());
    this.controller.deleteCellContents(this.location);
    assertEquals("", this.worksheet.getCellAt(this.location).evaluateCell());
  }

}
