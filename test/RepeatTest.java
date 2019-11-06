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
public class RepeatTest {

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
  void initData() {
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
  public void testRepeatHelloFive() {
    initData();
    this.model.editCell("=(REPT \"hello\" 5)", this.locationB1);
    assertEquals("hellohellohellohellohello", this.model.
            getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testRepeatTrueTen() {
    initData();
    this.model.editCell("=(REPT true 10)", this.locationB1);
    assertEquals("truetruetruetruetruetruetruetruetruetrue", this.model.
            getCellAt(this.locationB1).evaluateCell());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testRepeatTooMany() {
    initData();
    this.model.editCell("=(REPT \"hello\" 15 100)", this.locationB1);
    this.model.getCellAt(this.locationB1).evaluateCell();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRepeatTooFew() {
    initData();
    this.model.editCell("=(REPT )", this.locationB1);
    this.model.getCellAt(this.locationB1).evaluateCell();
  }

  @Test
  public void testRepeatHelloSumFiveTwo() {
    initData();
    this.model.editCell("=(REPT \"hello\" (SUM -2 4))", this.locationB1);
    assertEquals("hellohello", this.model.getCellAt(this.locationB1).evaluateCell());
  }
  
  @Test
  public void testRepeatA1Once() {
    initData();
    this.model.editCell("=(REPT \"A1\" 1)", this.locationB1);
    this.model.editCell("=B1", this.locationA1);
    assertEquals("A1", this.model.getCellAt(this.locationA1).evaluateCell());
  }

  @Test
  public void testRepeatReference() {
    initData();
    this.model.editCell("t", this.locationA1);
    this.model.editCell("=(REPT A1 5)", this.locationB1);
    assertEquals("ttttt", this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testRepeatReferenceB2() {
    initData();
    this.model.editCell("OOD", this.locationB2);
    this.model.editCell("=(REPT B2 0)", this.locationB1);
    assertEquals("", this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTwoProductCyclicThree() {
    initData();
    this.model.editCell("=(REPT A1 5)", this.locationB1);
    this.model.editCell("=B1", this.locationA1);
    assertEquals("A1A1A1A1A1", this.model.getCellAt(this.locationB1).evaluateCell());
  }

  // TESTS FOR THE ADD FUNCTION OBJECT AND THE SEXP VISITOR HANDLER
  @Test
  public void testMultiplyFunctionNormal() {
    initData();
    assertEquals("hellohello", this.parser.parse("(REPT \"hello\" 2)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testRepeatFunctionZero() {
    initData();
    assertEquals("", this.parser.parse("(REPT \"five\" 0)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testRepeatFunctionNothing() {
    initData();
    assertEquals("", this.parser.parse("(REPT \"\" 10)")
            .accept(this.visitor).evaluateCell());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRepeatFunctionNone() {
    initData();
    this.parser.parse("(REPT )").accept(this.visitor).evaluateCell();
  }

  @Test
  public void testRepeatFunctionVisitFunction() {
    initData();
    assertEquals("hihihihihihi", this.parser.parse("(REPT (REPT \"hi\" 2) 3)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testRepeatFunctionVisitReference() {
    initData();
    this.model.editCell("hey", this.locationB3);
    this.model.editCell("=B3", this.locationA1);
    this.model.editCell("=(REPT A1 3)", this.locationA2);
    assertEquals("heyheyhey", this.model.getCellAt(this.locationA2).evaluateCell());
  }

  @Test
  public void testRepeatFunctionVisitBoolean() {
    initData();
    assertEquals("falsefalsefalse", this.parser.parse("(REPT false 3)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testRepeatFunctionVisitDouble() {
    initData();
    assertEquals("18.018.018.0", this.parser.parse("(REPT 18 3)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testRepeatAllFunctions() {
    initData();
    assertEquals("10.010.010.010.010.010.0", this.parser.parse("(REPT (REPT (SUM 6 4 "
            + "(REPT \"yo\" 2)) 2) (PRODUCT 3 (SUM 1 0)))").accept(this.visitor).evaluateCell());
  }
}
