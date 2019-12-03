import org.junit.Test;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Coord;


import static org.junit.Assert.assertEquals;

/**
 * A test class to test the functionality of our read only worksheet model.
 **/
public class BasicWorksheetReadOnlyModelTest {
  private BasicWorksheetModel worksheet;
  private BasicWorksheetReadOnlyModel readOnly;
  private Coord location;
  private Coord location2;
  private Coord location3;

  private void initData() {
    this.worksheet = new BasicWorksheetModel();
    this.readOnly = new BasicWorksheetReadOnlyModel(this.worksheet);
    this.location = new Coord(1, 4);
    this.location2 = new Coord(1, 1);
    this.location3 = new Coord(10, 20);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void editCellBoolean() {
    initData();
    assertEquals("", this.readOnly.getCellAt(location).getRawContents());
    this.readOnly.editCell("true", location);
    assertEquals("", this.readOnly.getCellAt(location).getRawContents());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void editCellString() {
    initData();
    assertEquals("", this.readOnly.getCellAt(location).getRawContents());
    this.readOnly.editCell("hello", location);
    assertEquals("", this.readOnly.getCellAt(location).getRawContents());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void editCellFunction() {
    initData();
    assertEquals("", this.worksheet.getCellAt(location).getRawContents());
    this.readOnly.editCell("=(SUM 5 6 7)", location);
    assertEquals("", this.readOnly.getCellAt(location).getRawContents());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void editCellReference() {
    initData();
    assertEquals("", this.worksheet.getCellAt(location).getRawContents());
    this.worksheet.editCell("5.0", location2);
    this.readOnly.editCell("=(SUM 5 6 A1)", location);
    assertEquals("", this.readOnly.getCellAt(location).getRawContents());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void editCellDouble() {
    initData();
    assertEquals("", this.readOnly.getCellAt(location).getRawContents());
    this.readOnly.editCell("4.0", location);
    assertEquals("", this.readOnly.getCellAt(location).getRawContents());
  }

  @Test
  public void testGetCellAt() {
    initData();
    assertEquals("", this.readOnly.getCellAt(location).getRawContents());
    this.worksheet.editCell("5.0", location);
    assertEquals("5.0", this.readOnly.getCellAt(location).getRawContents());

  }

  @Test
  public void testGetNumRows() {
    initData();
    assertEquals(0, this.readOnly.getNumRows());
    this.worksheet.editCell("5", location);
    this.worksheet.editCell("2", location2);
    this.worksheet.editCell("\"hello\"", location3);
    assertEquals(20, this.readOnly.getNumRows());
  }

  @Test
  public void testGetNumCols() {
    initData();
    assertEquals(0, this.readOnly.getNumCols());
    this.worksheet.editCell("true", location);
    this.worksheet.editCell("4", location2);
    this.worksheet.editCell("\"goodbye\"", location3);
    assertEquals(10, this.readOnly.getNumCols());
  }

  @Test
  public void testGetNumRowsAndColsFar() {
    initData();
    assertEquals(0, this.readOnly.getNumRows());
    assertEquals(0, this.readOnly.getNumCols());
    this.worksheet.editCell("\"this is a far away cell\"", new Coord(500, 1000));
    assertEquals(1000, this.readOnly.getNumRows());
    assertEquals(500, this.readOnly.getNumCols());
  }

  @Test
  public void testGetNumRowsAndColsRemove() {
    initData();
    assertEquals(0, this.readOnly.getNumRows());
    assertEquals(0, this.readOnly.getNumCols());
    this.worksheet.editCell("false", new Coord(500, 1000));
    this.worksheet.editCell("\"hi\"", new Coord(45, 1000));
    assertEquals(1000, this.readOnly.getNumRows());
    assertEquals(500, this.readOnly.getNumCols());
    this.worksheet.removeCell(new Coord(500, 1000));
    assertEquals(45, this.readOnly.getNumCols());
    assertEquals(1000, this.readOnly.getNumRows());
    this.worksheet.removeCell(new Coord(45, 1000));
    assertEquals(0, this.readOnly.getNumRows());
    assertEquals(0, this.readOnly.getNumCols());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testRemoveCell() {
    initData();
    assertEquals("", this.readOnly.getCellAt(location).getRawContents());
    this.readOnly.removeCell(location);
    assertEquals("", this.readOnly.getCellAt(location).getRawContents());
  }
}
