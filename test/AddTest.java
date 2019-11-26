import org.junit.Test;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.SexpVisitorHandler;

import static org.junit.Assert.assertEquals;

/**
 * A class to test all of the functionality of the sum function. Tests both the functionality of the
 * accept and the evaluate method.
 */
public class AddTest {

  private Parser parser;
  private SexpVisitorHandler visitor;
  private BasicWorksheetModel model;
  private Coord locationA1;
  private Coord locationA2;
  private Coord locationB1;
  private Coord locationB2;
  private Coord locationB3;

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
    this.locationB2 = new Coord(2, 2);
    this.locationB3 = new Coord(2, 3);
  }

  // TESTS FOR EDITING A CELL AND EVALUATING IT
  @Test
  public void testAddTenFive() {
    initData();
    this.model.editCell("=(SUM 10 5)", this.locationB1);
    assertEquals(15.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testAddTrueTen() {
    initData();
    this.model.editCell("=(SUM true 10)", this.locationB1);
    assertEquals(10.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testAddTrueFalse() {
    initData();
    this.model.editCell("=(SUM true false)", this.locationB1);
    assertEquals(0.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testAddHelloFifteenHundred() {
    initData();
    this.model.editCell("=(SUM \"hello\" 15 100)", this.locationB1);
    assertEquals(115.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddBlank() {
    initData();
    this.model.editCell("=(SUM )", this.locationB1);
    assertEquals(0.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testAddHelloHi() {
    initData();
    this.model.editCell("=(SUM \"hello\" \"hi\")", this.locationB1);
    assertEquals(0.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testAddTenSum10Five() {
    initData();
    this.model.editCell("=(SUM 10 (SUM 10 5))", this.locationB1);
    assertEquals(25.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testAddTwoProductSixThree() {
    initData();
    this.model.editCell("=(SUM 2 (PRODUCT 6 3))", this.locationB1);
    assertEquals(20.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testAddTwoA1() {
    initData();
    this.model.editCell("=(SUM 2 (PRODUCT 6 3))", this.locationB1);
    this.model.editCell("=B1", this.locationA1);
    assertEquals(20.0, this.model.getCellAt(this.locationA1).evaluateCell());
  }

  @Test
  public void testAddA1A1() {
    initData();
    this.model.editCell(5, this.locationA1);
    this.model.editCell("=(SUM A1 A1)", this.locationB1);
    assertEquals(10.0, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testAddA1ToB1() {
    initData();
    this.model.editCell(5, this.locationA1);
    this.model.editCell(6, this.locationB1);
    this.model.editCell("=(SUM A1:B1)", this.locationB3);
    assertEquals(11.0, this.model.getCellAt(this.locationB3).evaluateCell());
  }

  @Test
  public void testAddDirectCycle() {
    initData();
    this.model.editCell("=A1", this.locationA1);
    assertEquals("REF!", this.model.getCellAt(this.locationA1).evaluateCell());
  }

  @Test
  public void testAddInDirectCycle() {
    initData();
    this.model.editCell("=B1", this.locationA1);
    this.model.editCell("=A1", this.locationB1);
    assertEquals("REF!", this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testAddInDirectCycle2() {
    initData();
    this.model.editCell("=B1", this.locationA1);
    this.model.editCell("=(SUM A1 1)", this.locationB1);
    assertEquals("REF!", this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testAddDirectCycleRange() {
    initData();
    this.model.editCell("=(SUM A1:C3)", this.locationB2);
    assertEquals("REF!", this.model.getCellAt(this.locationB2).evaluateCell());
  }


  @Test
  public void testAddTwoProductCyclicThree() {
    initData();
    this.model.editCell("=(SUM 2 (PRODUCT B1 3))", this.locationB1);
    this.model.editCell("=B1", this.locationA1);
    assertEquals("REF!", this.model.getCellAt(this.locationA1).evaluateCell());
  }

  @Test
  public void testAddTwoProductCyclicA1() {
    initData();
    this.model.editCell("=B1", this.locationB1);
    assertEquals("REF!", this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testAddTwoProductCyclicA1B1() {
    initData();
    this.model.editCell("=B1", this.locationA1);
    this.model.editCell("=A1", this.locationB1);
    assertEquals("REF!", this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testAddAllInRangeA1B1() {
    initData();
    this.model.editCell(4.0, this.locationA1);
    this.model.editCell(5.0, this.locationB1);
    this.model.editCell("=(SUM A1:B1)", this.locationB2);
    assertEquals(9.0, this.model.getCellAt(this.locationB2).evaluateCell());
  }

  @Test
  public void testAddAllInRangeA1B2() {
    initData();
    this.model.editCell(4.0, this.locationA1);
    this.model.editCell(5.0, this.locationB2);
    this.model.editCell("=(SUM A1:B2)", new Coord(1, 3));
    assertEquals(9.0, this.model.getCellAt(new Coord(1, 3)).evaluateCell());
  }

  @Test
  public void testAddFunctionWithIndirectReference2() {
    initData();
    model.editCell("=B3", this.locationA1);
    model.editCell("=A1", this.locationA2);
    model.editCell("=A2", this.locationB1);
    model.editCell("=B3", this.locationB2);
    // set equal to B1 above fails
    model.editCell("=(SUM B2 10)", this.locationB3);
    assertEquals("REF!", this.model.getCellAt(this.locationB3).evaluateCell());
  }

  @Test
  public void testAddFunctionReferenceOppositeOrder() {
    initData();
    model.editCell(5, this.locationB2);
    model.editCell("=B2", this.locationA1);
    model.editCell("=(SUM A1 10)", this.locationB3);
    assertEquals(15.0, this.model.getCellAt(this.locationB3).evaluateCell());
    //swap first two order fails
  }

  @Test
  public void testAddFunctionWithReference3() {
    initData();
    model.editCell(5, this.locationA1);
    model.editCell("=(SUM A1 A2)", this.locationA2);
    assertEquals("REF!", this.model.getCellAt(this.locationA2).evaluateCell());
  }

  @Test
  public void testFunctionWithReference() {
    initData();
    model.editCell("=A1", this.locationA1);
    assertEquals("REF!", this.model.getCellAt(this.locationA1).evaluateCell());
  }

  // TESTS FOR THE ADD FUNCTION OBJECT AND THE SEXP VISITOR HANDLER
  @Test
  public void testAddFunctionNormal() {
    initData();
    assertEquals(71.0, this.parser.parse("(SUM 16 55)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testAddFunctionArbitrary() {
    initData();
    assertEquals(36.0, this.parser.parse("(SUM 0 1 2 3 4 5 6 7 8)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testAddFunctionOne() {
    initData();
    assertEquals(4.0, this.parser.parse("(SUM 4)")
            .accept(this.visitor).evaluateCell());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFunctionNone() {
    initData();
    this.parser.parse("(SUM )").accept(this.visitor).evaluateCell();
  }

  @Test
  public void testAddFunctionVisitFunction() {
    initData();
    assertEquals(22.0, this.parser.parse("(SUM (SUM 6 5) (SUM 6 5))")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testAddFunctionWithReference() {
    initData();
    model.editCell(5, this.locationA1);
    model.editCell(10, this.locationA2);
    model.editCell(15, this.locationB1);
    model.editCell(25, this.locationB2);
    model.editCell("=(SUM A1:B2 5)", this.locationB3);
    assertEquals(60.0, this.model.getCellAt(this.locationB3).evaluateCell());
  }

  @Test
  public void testAddFunctionVisitBoolean() {
    initData();
    assertEquals(5.0, this.parser.parse("(SUM true 5 0)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testAddFunctionVisitBooleanArbitrary() {
    initData();
    assertEquals(20.0, this.parser.parse("(SUM true 5 7 8 false)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testAddFunctionVisitString() {
    initData();
    assertEquals(13.0, this.parser.parse("(SUM 5 4 1 \"hello\" 3)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testAddFunctionDecimals() {
    initData();
    assertEquals(1024.3, this.parser.parse("(SUM 5.1 4 1.9 13.3 1000)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testAddAllFunctions() {
    initData();
    assertEquals(33.0, this.parser.parse("(SUM (PRODUCT 2.5 4 (REPT \"repeat\" 2))"
            + " 13 (PRODUCT (SUM 3 true 2) 2))").accept(this.visitor).evaluateCell());
  }

}