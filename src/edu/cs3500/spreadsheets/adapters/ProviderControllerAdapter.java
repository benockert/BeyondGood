package edu.cs3500.spreadsheets.adapters;

import edu.cs3500.spreadsheets.controller.BasicWorksheetController;
import edu.cs3500.spreadsheets.provider.view.controller.IController;

public class ProviderControllerAdapter implements IController {
  BasicWorksheetController ourController;

  public ProviderControllerAdapter(BasicWorksheetController ourController) {
    this.ourController = ourController;
  }

  @Override
  public void start() {
    this.ourController.run();
  }
}
