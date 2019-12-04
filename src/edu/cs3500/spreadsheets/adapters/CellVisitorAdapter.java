package edu.cs3500.spreadsheets.adapters;

import java.util.ArrayList;

import edu.cs3500.spreadsheets.cell.CellBlank;
import edu.cs3500.spreadsheets.cell.CellBoolean;
import edu.cs3500.spreadsheets.cell.CellDouble;
import edu.cs3500.spreadsheets.cell.CellFunction;
import edu.cs3500.spreadsheets.cell.CellReference;
import edu.cs3500.spreadsheets.cell.CellString;
import edu.cs3500.spreadsheets.function.CellVisitor;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.Function;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.InvalidFormulaException;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.formulas.IFormulaVisitor;

public class CellVisitorAdapter implements CellVisitor {
  private IFormulaVisitor theirVisitor;
  private ProviderModelAdapter model;

  public CellVisitorAdapter(IFormulaVisitor theirVisitor, ProviderModelAdapter model) {
    this.theirVisitor = theirVisitor;
    this.model = model;
  }

  @Override
  public Object visitDouble(CellDouble d) {
    return theirVisitor.visitDoubleValue(d.evaluateCell());
  }

  @Override
  public Object visitBoolean(CellBoolean b) {
    return theirVisitor.visitBooleanVal(b.evaluateCell());
  }

  @Override
  public Object visitFunction(CellFunction f) {
    Function.Func function = Function.Func.valueOf(f.func);
    try {
      return theirVisitor.visitFunction(function, f.arguments);
    } catch (InvalidFormulaException ife) {
      throw new IllegalArgumentException("Invalid formula");
    }
  }

  @Override
  public Object visitReference(CellReference r) {
    ArrayList<Coord> referencedCoords = new ArrayList<>();
    for (Coord coord : r.referencedCells.keySet()) {
      referencedCoords.add(coord);
    }
    try {
      return theirVisitor.visitReference(referencedCoords, this.model);
    } catch (InvalidFormulaException ife) {
      throw new IllegalArgumentException("Invalid formula");
    }
  }

  @Override
  public Object visitString(CellString s) {
    try {
      return theirVisitor.visitStringValue(s.getRawContents(), s.evaluateCell());
    } catch (InvalidFormulaException ife) {
      throw new IllegalArgumentException("Invalid input");
    }
  }

  @Override
  public Object visitBlank(CellBlank b) {
    return null; // the provider code does not represent blank cells
  }

  @Override
  public Object apply(Object o) {
    return null; // the provider code does not represent functions as function object
  }
}
