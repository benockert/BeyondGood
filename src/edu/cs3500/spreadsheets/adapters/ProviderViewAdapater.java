package edu.cs3500.spreadsheets.adapters;

import edu.cs3500.spreadsheets.provider.view.view.EditableView;
import edu.cs3500.spreadsheets.view.BasicWorksheetView;

public class ProviderViewAdapater implements BasicWorksheetView {
  public EditableView theirView;

  public ProviderViewAdapater(EditableView theirView) {
    this.theirView = theirView;
  }

  @Override
  public void render() {
    this.theirView.render();
  }

  @Override
  public void refresh() {
    // their render method already repaints the view, thus refreshing it
  }
}
