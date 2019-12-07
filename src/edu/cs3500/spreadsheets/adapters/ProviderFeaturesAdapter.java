package edu.cs3500.spreadsheets.adapters;

import edu.cs3500.spreadsheets.controller.IFeatures;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.controller.Features;

/**
 * Represents a class which adapts our features interface to the provider's features.
 */
public class ProviderFeaturesAdapter implements Features {
  private IFeatures ourFeatures;
  public ProviderViewAdapater view;

  /**
   * Constructs a {@code ProviderFeatures} object, which adapts our IFeatures interface to theirs.
   *
   * @param ourFeatures Our IFeatures interface for representing the different features in the
   *                    view.
   * @param view        The adapted view (their view adapted to our interface).
   */
  public ProviderFeaturesAdapter(IFeatures ourFeatures, ProviderViewAdapater view) {
    this.ourFeatures = ourFeatures;
    this.view = view;
  }

  // selects a highlighted cell
  @Override
  public void selectCell(Coord c) {
    this.view.changeHighlightedCellLocation(c.col, c.row);
  }

  // rejects changes made in the textbox
  @Override
  public void rejectChanges(Coord c) {
    this.ourFeatures.rejectCellEdit();

  }

  // accepts changes made in the textbox and updates the cell & textbox
  @Override
  public void acceptChanges(Coord c) {
    String rawContents = this.view.getTextbox();
    this.ourFeatures.acceptCellEdit(c, rawContents);
  }
}
