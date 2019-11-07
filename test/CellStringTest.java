import org.junit.Test;

import edu.cs3500.spreadsheets.cell.CellString;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing the functionality of the cellBoolean class.
 */
public class CellStringTest {

  private CellString cellString1;
  private CellString cellString2;
  private CellString cellString3;

  /**
   * Initializes the data for testing that will be reset before each test is conducted.
   */
  private void initData() {
    this.cellString1 = new CellString("hello");
    this.cellString2 = new CellString("");
    this.cellString3 = new CellString("Object Oriented Design");
  }

  @Test
  public void testCellString1String() {
    initData();
    assertEquals("\"hello\"", this.cellString1.getRawContents());
  }

  @Test
  public void testCellString1Evaluate() {
    initData();
    assertEquals("hello", this.cellString1.evaluateCell());
  }

  @Test
  public void testCellString2String() {
    initData();
    assertEquals("\"\"", this.cellString2.getRawContents());
  }

  @Test
  public void testCellString2Evaluate() {
    initData();
    assertEquals("", this.cellString2.evaluateCell());
  }

  @Test
  public void testCellString3String() {
    initData();
    assertEquals("\"Object Oriented Design\"", this.cellString3.getRawContents());
  }

  @Test
  public void testCellString3Evaluate() {
    initData();
    assertEquals("Object Oriented Design", this.cellString3.evaluateCell());
  }

}