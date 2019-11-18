import org.junit.Test;

import java.util.Arrays;

import edu.cs3500.spreadsheets.cell.CellDouble;
import edu.cs3500.spreadsheets.cell.CellFunction;
import edu.cs3500.spreadsheets.cell.CellString;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing the functionality of the cellBoolean class.
 */
public class CellFunctionTest {

  private CellFunction cellFunction1;
  private CellFunction cellFunction2;

  /**
   * Initializes the data for testing that will be reset before each test is conducted.
   */
  private void initData() {
    this.cellFunction1 = new CellFunction("REPT",
            Arrays.asList(new CellString("hi"), new CellDouble(3)));
    this.cellFunction2 = new CellFunction("REPT",
            Arrays.asList(cellFunction1, new CellDouble(3)));
  }

  @Test
  public void testCellFunctionValue1() {
    initData();
    assertEquals("hihihi", this.cellFunction1.evaluateCell());
  }

  @Test
  public void testCellFunctionValue2() {
    initData();
    assertEquals("hihihihihihihihihi", cellFunction2.evaluateCell());
  }

  @Test
  public void testCellFunction1Raw() {
    initData();
    assertEquals("(REPT \"hi\" 3.0)", cellFunction1.getRawContents());
  }

  @Test
  public void testCellFunctione2Raw() {
    initData();
    assertEquals("(REPT (REPT \"hi\" 3.0) 3.0)", cellFunction2.getRawContents());
  }


}