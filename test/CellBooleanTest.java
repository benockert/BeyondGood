import org.junit.Test;

import edu.cs3500.spreadsheets.cell.CellBoolean;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing the functionality of the cellBoolean class.
 */
public class CellBooleanTest {

  private CellBoolean cellBoolean1;
  private CellBoolean cellBoolean2;

  /**
   * Initializes the data for testing that will be reset before each test is conducted.
   */
  private void initData() {
    this.cellBoolean1 = new CellBoolean(true);
    this.cellBoolean2 = new CellBoolean(false);
  }

  @Test
  public void testCellBoolean1String() {
    initData();
    assertEquals("true", this.cellBoolean1.getRawContents());
  }

  @Test
  public void testCellBoolean1Evaluate() {
    initData();
    assertEquals(true, this.cellBoolean1.evaluateCell());
  }

  @Test
  public void testCellBoolean2String() {
    initData();
    assertEquals("false", this.cellBoolean2.getRawContents());
  }

  @Test
  public void testCellBoolean2Evaluate() {
    initData();
    assertEquals(false, this.cellBoolean2.evaluateCell());
  }

}