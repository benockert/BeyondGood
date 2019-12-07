package edu.cs3500.spreadsheets.adapters;

import edu.cs3500.spreadsheets.controller.IFeatures;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.view.EditableView;
import edu.cs3500.spreadsheets.view.BasicWorksheetView;

/**
 * Represents a class which adapts the provider's editor view to our view interface.
 */
public class ProviderViewAdapater implements BasicWorksheetView {
  private EditableView theirView;
  public BasicWorksheetReadOnlyModel model;

  /**
   * Constructs a {@code ProviderViewAdapter} which adapts their editor view to our interface.
   *
   * @param theirView The provider's editor view.
   * @param model     Our non-mutable model.
   */
  public ProviderViewAdapater(EditableView theirView, BasicWorksheetReadOnlyModel model) {
    this.theirView = theirView;
    this.model = model;
  }

  // renders the view
  @Override
  public void render() {
    this.theirView.render();
    this.theirView.makeVisible();
  }

  // refreshes the view
  @Override
  public void refresh() {
    this.theirView.repaint();
  }

  // adds the features to the view
  @Override
  public void addIFeatures(IFeatures feature) {
    this.theirView.addFeatures(new ProviderFeaturesAdapter(feature, this));
  }

  // sets the textbox with the contents of the highlighted cell
  @Override
  public void setTextbox() {
    Coord highlightedCell = this.theirView.getSelected();
    String rawContents = this.model.getCellAt(highlightedCell).getRawContents();
    this.theirView.setSelected(highlightedCell);
    this.theirView.formulaBox.setText(rawContents);
    this.refresh();

  }

  // changes the location of the highlighted cell
  @Override
  public void changeHighlightedCellLocation(int i, int i1) {
    Coord highlightCell = new Coord(i, i1);
    this.theirView.setSelected(highlightCell);
    this.setTextbox();
    this.refresh();

  }

  /**
   * Returns the contents of the textbox.
   *
   * @return A string with the contents of the textbox.
   */
  public String getTextbox() {
    return theirView.formulaBox.getText();
  }
}
