import org.junit.Test;

import edu.cs3500.spreadsheets.cell.CellDouble;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing the functionality of the cellBoolean class.
 */
public class CellDoubleTest {

  private CellDouble cellDouble1;
  private CellDouble cellDouble2;

  /**
   * Initializes the data for testing that will be reset before each test is conducted.
   */
  private void initData() {
    this.cellDouble1 = new CellDouble(2.0);
    this.cellDouble2 = new CellDouble(-5.0);
  }

  @Test
  public void testCellDouble1Value() {
    initData();
    assertEquals((Double) 2.0, cellDouble1.evaluateCell());
  }

  @Test
  public void testCellDouble1String() {
    initData();
    assertEquals("2.0", cellDouble1.getRawContents());
  }

  @Test
  public void testCellDouble2Value() {
    initData();
    assertEquals((Double) (-5.0), cellDouble2.evaluateCell());
  }

  @Test
  public void testCellDouble2String() {
    initData();
    assertEquals("-5.0", cellDouble2.getRawContents());
  }


}