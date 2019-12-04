package edu.cs3500.spreadsheets.adapters;

import java.util.ArrayList;
import java.util.List;

import edu.cs3500.spreadsheets.cell.CellBoolean;
import edu.cs3500.spreadsheets.cell.CellDouble;
import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.cell.CellString;
import edu.cs3500.spreadsheets.function.CellVisitor;
import edu.cs3500.spreadsheets.provider.view.model.ISpreadsheet;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.Function;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.formulas.IFormulaVisitor;

public class ProviderVisitorAdapter implements IFormulaVisitor {
  private CellVisitor ourVisitor;

  public ProviderVisitorAdapter(CellVisitor ourVisitor) {
    this.ourVisitor = ourVisitor;
  }

  @Override
  public Object visitBooleanVal(Boolean b) {
    return this.ourVisitor.visitBoolean(new CellBoolean(b));
  }

  @Override
  public Object visitDoubleValue(Double d) {
    return this.ourVisitor.visitDouble(new CellDouble(d));
  }

  @Override
  public Object visitStringValue(String s, String error) {
    return this.ourVisitor.visitString(new CellString(s));
  }

  @Override
  public Object visitReference(ArrayList arrayList, ISpreadsheet model) {
    return null; // not enough information to visit our cell reference class
  }

  @Override
  public Object visitFunction(Function.Func o, List args) {
    String functionName = o.name();
    List<CellFormula> formulaList = new ArrayList<>();
    //for (Object form : args) {
      //form

    //}
    //return this.ourVisitor.visitFunction();
    return null;
  }
}
