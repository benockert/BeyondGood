import org.junit.Test;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.SexpVisitorHandler;

import static org.junit.Assert.assertEquals;

/**
 * A class to test all of the functionality of the product function. Tests both the evaluate method
 * and the accept method.
 */
public class MultiplyTest {

  private Parser parser;
  private SexpVisitorHandler visitor;
  private BasicWorksheetModel model;
  private Coord locationA1;
  private Coord locationA2;
  private Coord locationB1;
  private Coord locationA3;

  /**
   * A method that initializes the fields of this testing class which will be used to rest the
   * fields of this class prior to each test.
   */
  private void initData() {
    this.parser = new Parser();
    this.visitor = new SexpVisitorHandler();
    this.model = new BasicWorksheetModel();
    this.locationA1 = new Coord(1, 1);
    this.locationA2 = new Coord(1, 2);
    this.locationB1 = new Coord(2, 1);
    this.locationA3 = new Coord(1, 3);
  }

  // TESTS FOR EDITING A CELL AND EVALUATING IT (PRODUCT FUNCTION)
  @Test
  public void testProductTenFive() {
    initData();
    this.model.editCell("=(PRODUCT 10 5)", this.locationB1);
    assertEquals(50.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testProductTrueTen() {
    initData();
    this.model.editCell("=(PRODUCT true 10)", this.locationB1);
    assertEquals(10.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testProductHelloFifteenHundred() {
    initData();
    this.model.editCell("=(PRODUCT \"hello\" 15 100)", this.locationB1);
    assertEquals(1500.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddBlank() {
    initData();
    this.model.editCell("=(PRODUCT )", this.locationB1);
    assertEquals(0.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testProductTenProduct10Five() {
    initData();
    this.model.editCell("=(PRODUCT 10.5 (PRODUCT 10 5))", this.locationB1);
    assertEquals(525.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testProductTwoSumSixThree() {
    initData();
    this.model.editCell("=(PRODUCT 2 (SUM 6 3))", this.locationB1);
    assertEquals(18.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testProductTwoA1() {
    initData();
    this.model.editCell("=(PRODUCT 2 (PRODUCT 5 3))", this.locationB1);
    this.model.editCell("=B1", this.locationA1);
    assertEquals(30.0, this.model.getCellAt(this.locationA1).evaluateCell());
  }

  @Test
  public void testProductB1B1() {
    initData();
    this.model.editCell(0.0, this.locationB1);
    this.model.editCell("=(PRODUCT B1 B1)", this.locationA1);
    assertEquals(0.0, this.model.getCellAt(this.locationA1).evaluateCell());
  }

  @Test
  public void testProductSquare() {
    initData();
    this.model.editCell("=(PRODUCT 10 10)", this.locationA1);
    assertEquals(100.0, this.model.getCellAt(this.locationA1).evaluateCell());
  }

  @Test
  public void testProductNoValids() {
    initData();
    this.model.editCell(true, this.locationB1);
    this.model.editCell("hello", this.locationA1);
    this.model.editCell("=(PRODUCT A1 B1)", this.locationA2);
    assertEquals(0.0, this.model.getCellAt(this.locationA2).evaluateCell());
  }

  @Test
  public void testProductTrueFalse() {
    initData();
    this.model.editCell("=(PRODUCT true false)", this.locationB1);
    assertEquals(0.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testProductHelloHi() {
    initData();
    this.model.editCell("=(PRODUCT \"hello\" \"hi\")", this.locationB1);
    assertEquals(0.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testProductA2Five() {
    initData();
    this.model.editCell(10.0, this.locationA2);
    this.model.editCell("=(PRODUCT A2 5)", this.locationA1);
    assertEquals(50.0, this.model.getCellAt(this.locationA1).evaluateCell());
  }

  @Test
  public void testProductA2A2() {
    initData();
    this.model.editCell(100.0, this.locationA2);
    this.model.editCell("=(PRODUCT A2 A2)", this.locationA1);
    assertEquals(10000.0, this.model.getCellAt(this.locationA1).evaluateCell());
  }

  @Test
  public void testProductA1D2() {
    initData();
    this.model.editCell(5, this.locationA1);
    this.model.editCell(10, this.locationB1);
    this.model.editCell(2, new Coord(3, 1));
    this.model.editCell(4, new Coord(4, 1));
    this.model.editCell("=(PRODUCT A1:D2)", this.locationA3);
    assertEquals(400.0, this.model.getCellAt(this.locationA3).evaluateCell());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTwoProductCyclicThree() {
    initData();
    this.model.editCell("=(PRODUCT 2 (PRODUCT B1 3))", this.locationB1);
    this.model.getCellAt(this.locationB1).evaluateCell();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTwoProductCyclicIndirect() {
    initData();
    this.model.editCell("=(PRODUCT 5 (SUM A1 2))", this.locationB1);
    this.model.editCell("=B1", this.locationA1);
    this.model.getCellAt(this.locationB1).evaluateCell();
  }

  // TESTS FOR THE ADD FUNCTION OBJECT AND THE SEXP VISITOR HANDLER
  @Test
  public void testMultiplyFunctionNormal() {
    initData();
    assertEquals(21.0, this.parser.parse("(PRODUCT 7 3)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testProductFunctionArbitrary() {
    initData();
    assertEquals(0.0, this.parser.parse("(PRODUCT 0 1 2 3 4 5)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testMultiplyFunctionOne() {
    initData();
    assertEquals(3.0, this.parser.parse("(PRODUCT 3)")
            .accept(this.visitor).evaluateCell());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testProductFunctionNone() {
    initData();
    this.parser.parse("(PRODUCT )").accept(this.visitor).evaluateCell();
  }

  @Test
  public void testProductFunctionArbitrary2() {
    initData();
    assertEquals(120.0, this.parser.parse("(PRODUCT 1 2 3 4 5)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testProductFunctionVisitFunction() {
    initData();
    assertEquals(84.0, this.parser.parse("(PRODUCT (PRODUCT 3 1) (PRODUCT 4 7))")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testProductFunctionVisitBoolean() {
    initData();
    assertEquals(51.0, this.parser.parse("(PRODUCT 1 false 17 3)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testAddFunctionVisitString() {
    initData();
    assertEquals(180.0, this.parser.parse("(PRODUCT 15 4 1 \"what\" 3)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testProductFunctionDecimals() {
    initData();
    assertEquals(173.1, this.parser.parse("(PRODUCT 2.5 4 17.31)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testProductAllFunctions() {
    initData();
    assertEquals(1200.0, this.parser.parse("(PRODUCT (SUM 6 4 (REPT \"yo\" 2))"
            + " (SUM (PRODUCT 3 true 2) 2) 15 (LESSTHAN 5 (SUM 2 7)))")
            .accept(this.visitor).evaluateCell());
  }
}
