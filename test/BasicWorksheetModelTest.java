import org.junit.Test;


import edu.cs3500.spreadsheets.cell.CellBlank;
import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * A test class to test the functionality of our worksheet model.
 */
public class BasicWorksheetModelTest {

  private BasicWorksheetModel worksheet;
  private Coord location;
  private Coord location2;
  private Coord location3;

  private void initData() {
    this.worksheet = new BasicWorksheetModel();
    this.location = new Coord(1, 4);
    this.location2 = new Coord(1, 1);
    this.location3 = new Coord(10, 20);
  }

  @Test
  public void createNewSpreadsheet() {
    initData();
    BasicWorksheetModel newModel = new BasicWorksheetModel();
    assertEquals("", this.worksheet.getCellAt(location).getRawContents());
  }

  @Test
  public void addCellsToSpreadsheet() {
    initData();
    assertEquals("", this.worksheet.getCellAt(location).getRawContents());
    assertEquals("", this.worksheet.getCellAt(location).getRawContents());
    assertEquals("", this.worksheet.getCellAt(location).getRawContents());
    this.worksheet.editCell("hello", this.location);
    this.worksheet.editCell(10.0, this.location2);
    this.worksheet.editCell(true, this.location3);
    assertEquals("hello", this.worksheet.getCellAt(this.location).evaluateCell());
    assertEquals(10.0, this.worksheet.getCellAt(this.location2).evaluateCell());
    assertEquals(true, this.worksheet.getCellAt(this.location3).evaluateCell());
  }

  @Test
  public void editCellBoolean() {
    initData();
    assertEquals("", this.worksheet.getCellAt(location).getRawContents());
    this.worksheet.editCell(false, location);
    assertEquals(false, this.worksheet.getCellAt(location).evaluateCell());
    this.worksheet.editCell(true, this.location);
    assertEquals(true, this.worksheet.getCellAt(this.location).evaluateCell());
  }

  @Test
  public void editCellString() {
    initData();
    assertEquals("", this.worksheet.getCellAt(location).getRawContents());
    this.worksheet.editCell("hello", location);
    assertEquals("hello", this.worksheet.getCellAt(location).evaluateCell());
  }

  @Test
  public void editCellFunction() {
    initData();
    assertEquals("", this.worksheet.getCellAt(location).getRawContents());
    this.worksheet.editCell("=(SUM 5 6 7)", location);
    assertEquals("=(SUM 5.0 6.0 7.0)", this.worksheet.getCellAt(location).getRawContents());
    assertEquals(18.0, this.worksheet.getCellAt(location).evaluateCell());
  }

  @Test
  public void editCellReference() {
    initData();
    assertEquals("", this.worksheet.getCellAt(location).getRawContents());
    this.worksheet.editCell(5.0, location2);
    this.worksheet.editCell("=(SUM 5 6 A1)", location);
    assertEquals("=(SUM 5.0 6.0 A1)", this.worksheet.getCellAt(location).getRawContents());
    assertEquals(16.0, this.worksheet.getCellAt(location).evaluateCell());
  }

  @Test
  public void editCellDouble() {
    initData();
    assertEquals("", this.worksheet.getCellAt(location).getRawContents());
    this.worksheet.editCell(2.1, location);
    assertEquals(2.1, this.worksheet.getCellAt(location).evaluateCell());
    this.worksheet.editCell(4.0, location);
    assertEquals(4.0, this.worksheet.getCellAt(location).evaluateCell());
  }

  @Test
  public void getCellAt() {
    initData();
    assertEquals("", this.worksheet.getCellAt(location).getRawContents());
    this.worksheet.editCell(2.1, location);
    assertEquals(2.1, this.worksheet.getCellAt(location).evaluateCell());
  }

}