/*package edu.cs3500.spreadsheets.controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class ActionComponent extends JPanel {
  List<IFeatures> buttonListeners = new ArrayList<>();

  void addFeatures(IFeatures f) {
    this.buttonListeners.add(f);
  }

  public ActionComponent() {
    this.getActionMap().put("Accept entry", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        for (IFeatures f : buttonListeners) {
          f.acceptCellEdit();
        }
      }
    });
    this.getActionMap().put("Reject entry", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        for (IFeatures f : buttonListeners) {
          f.rejectCellEdit();
        }
      }
    });
  }
}*/
