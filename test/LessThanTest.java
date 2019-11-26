import org.junit.Test;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.SexpVisitorHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * A class to test all of the functionality of the less than function. Tests both the evaluate
 * method and the accept method.
 */
public class LessThanTest {

  private Parser parser;
  private SexpVisitorHandler visitor;
  private BasicWorksheetModel model;
  private Coord locationA1;
  private Coord locationA2;
  private Coord locationB1;

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
  }

  // TESTS FOR EDITING A CELL AND EVALUATING IT
  @Test
  public void testLessThanTenFive() {
    initData();
    this.model.editCell("=(< 10 5)", this.locationB1);
    assertEquals(false, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessThanTrueTen() {
    initData();
    this.model.editCell("=(< true 10)", this.locationB1);
    this.model.getCellAt(this.locationB1).evaluateCell();
  }

  @Test
  public void testLessThanProductTenFiveSumElevenThree() {
    initData();
    this.model.editCell("=(< (PRODUCT 2 5) (SUM 11 3))", this.locationB1);
    assertEquals(true, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testLessThanTwoProductThreeSeven() {
    initData();
    this.model.editCell("=(< 2 (PRODUCT 3 7))", this.locationB1);
    assertEquals(true, this.model.getCellAt(this.locationB1).evaluateCell());
  }

  @Test
  public void testLessThanTwoA1() {
    initData();
    this.model.editCell("=(< 2 (PRODUCT 5 3))", this.locationB1);
    this.model.editCell("=B1", this.locationA1);
    assertEquals(true, this.model.getCellAt(this.locationA1).evaluateCell());
  }

  @Test
  public void testLessThanA1B2() {
    initData();
    this.model.editCell("=(SUM 6 (PRODUCT 2 7))", this.locationB1);
    this.model.editCell("=(PRODUCT (SUM 2 (PRODUCT 5 3)) 4)", this.locationA1);
    this.model.editCell("=(< A1 B1)", this.locationA2);
    assertEquals(false, this.model.getCellAt(this.locationA2).evaluateCell());
  }

  @Test
  public void testLessThanA1B2InA1() {
    initData();
    this.model.editCell("=(SUM 6 (PRODUCT 2 7))", this.locationB1);
    this.model.editCell("=(PRODUCT (SUM 2 (PRODUCT 5 3)) 4)", this.locationA1);
    this.model.editCell("=(< A1 B1)", this.locationA1);
    assertEquals("REF!", this.model.getCellAt(this.locationA1).evaluateCell());
  }

  @Test
  public void testLessThanCyclic() {
    initData();
    this.model.editCell("=(< 2 (PRODUCT B1 3))", this.locationB1);
    assertEquals("REF!", this.model.getCellAt(this.locationB1).evaluateCell());
  }

  // TESTS FOR THE ADD FUNCTION OBJECT AND THE SEXP VISITOR HANDLER
  @Test
  public void testLessThanFunctionNormal() {
    initData();
    assertEquals(true, this.parser.parse("(< 26 33)")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testLessThanFunctionNormal2() {
    initData();
    assertEquals(false, this.parser.parse("(< 13 5)")
            .accept(this.visitor).evaluateCell());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessThanFunction1Argument() {
    initData();
    this.parser.parse("(< 5)").accept(this.visitor).evaluateCell();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessThanFunction3Numbers() {
    initData();
    this.parser.parse("(< 13 5 7)").accept(this.visitor).evaluateCell();
  }

  @Test
  public void testLessThanFunctionVisitFunction() {
    initData();
    assertEquals(true, this.parser.parse("(< (SUM 7 4 3) (SUM 17 5))")
            .accept(this.visitor).evaluateCell());
  }

  @Test
  public void testLessThanVisitReference() {
    initData();
    this.model.editCell(10, this.locationA1);
    this.model.editCell("=(< A1 10)", this.locationA2);
    assertEquals(false, this.model.getCellAt(this.locationA2).evaluateCell());
  }

  @Test
  public void testLessThanFunctionVisitFunction2() {
    initData();
    assertEquals(false, this.parser.parse("(< (PRODUCT 7 4 3) (SUM 17 5))")
            .accept(this.visitor).evaluateCell());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessThanFunctionVisitBoolean() {
    initData();
    this.parser.parse("(< true 5 0)").accept(this.visitor).evaluateCell();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessThanFunctionVisitString() {
    initData();
    this.parser.parse("(< 5 \"hey\")").accept(this.visitor).evaluateCell();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessThanFunctionVisitStringAfter3() {
    initData();
    this.parser.parse("(< 5 4 1 \"hello\" 3)").accept(this.visitor).evaluateCell();
  }

  @Test
  public void testAddAllFunctions() {
    initData();
    assertEquals(false, this.parser.parse("(< (PRODUCT 2.5 4 (REPT \"less\" 4))"
            + " (PRODUCT (SUM 3 true 2) 2))").accept(this.visitor).evaluateCell());
  }
}