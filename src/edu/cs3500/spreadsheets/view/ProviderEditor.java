package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.HighlightCell;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.controller.Features;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.IFormula;
import edu.cs3500.spreadsheets.provider.view.view.IEditableView;

public class ProviderEditor extends BasicWorksheetEditorView implements IEditableView {

  /**
   * A constructor for the editable GUI view of a spreadsheet which reads an existing model and
   * displays that in the view.
   *
   * @param model The given spreadsheet model/file to be displayed and edited.
   */
  public ProviderEditor(BasicWorksheetReadOnlyModel model) {
    super(model);
  }

  @Override
  public void addFeatures(Features features) {

  }

  @Override
  public void setSelected(Coord c, String cellContents) {

  }

  @Override
  public IFormula getFormulaInBox() {
    return null;
  }

  @Override
  public void save(String fileName) {
    // optional feature
  }

  @Override
  public void makeVisible() {

  }

  @Override
  public void setSelected(Coord c) {
    new HighlightCell(super.getSpreadsheetPanel(), this);
    this.setTextbox();
  }

  @Override
  public Coord getSelected() {
    return this.getHighlightedCell();
  }
}
