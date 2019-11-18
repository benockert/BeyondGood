package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class BasicWorksheetEditorView extends JFrame implements BasicWorksheetView {
  private BasicWorksheetGraphicalView spreadsheetView;
  private JTextField formulaInput;
  private JPanel buttonPanel;
  private JButton accept, reject;

  public BasicWorksheetEditorView() {
    super();
    this.spreadsheetView = new BasicWorksheetGraphicalView();
    this.formulaInput = new JTextField(50);
    this.buttonPanel = new JPanel();
    this.buttonPanel.setLayout(new FlowLayout());
    this.buttonPanel.add(formulaInput);
    this.accept = new JButton("✔");
    this.reject = new JButton("✘");
    this.buttonPanel.add(accept);
    this.buttonPanel.add(reject);
    this.add(spreadsheetView);
    this.add(this.buttonPanel, BorderLayout.SOUTH);
  }


  @Override
  public void render() throws IOException {

  }
}
