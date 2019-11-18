package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Worksheet;

public class BasicWorksheetEditorView extends JFrame implements BasicWorksheetView {
  private BasicWorksheetGraphicalView spreadsheetView;
  private JTextField formulaInput;
  private JPanel buttonPanel;
  private JButton accept, reject;

  public BasicWorksheetEditorView() {
    super();
    // creates the existing view
    this.spreadsheetView = new BasicWorksheetGraphicalView();

    // creates a new button panel to house the text fields and accept/reject buttons
    this.buttonPanel = new JPanel();
    this.buttonPanel.setLayout(new FlowLayout());
    this.accept = new JButton("✔");
    this.buttonPanel.add(accept);
    this.reject = new JButton("✘");
    this.buttonPanel.add(reject);

    // creates the text field and sets its size to mostly fill the horizontal space of the view
    int size = this.spreadsheetView.getWidth();
    this.formulaInput = new JTextField(size / 14);
    this.formulaInput.setPreferredSize(new Dimension(size, 30));

    // adds the text field to the button panel and then adds the button panel to the existing view
    this.buttonPanel.add(formulaInput);
    spreadsheetView.add(this.buttonPanel, BorderLayout.NORTH);
  }

  public BasicWorksheetEditorView(Worksheet model) {
    super();
    // creates the existing view
    this.spreadsheetView = new BasicWorksheetGraphicalView(model);

    // creates a new button panel to house the text fields and accept/reject buttons
    this.buttonPanel = new JPanel();
    this.buttonPanel.setLayout(new FlowLayout());
    this.accept = new JButton("✔");
    this.buttonPanel.add(accept);
    this.reject = new JButton("✘");
    this.buttonPanel.add(reject);

    // creates the text field and sets its size to mostly fill the horizontal space of the view
    int size = this.spreadsheetView.getWidth();
    this.formulaInput = new JTextField(size / 14);
    this.formulaInput.setPreferredSize(new Dimension(size, 30));

    // adds the text field to the button panel and then adds the button panel to the existing view
    this.buttonPanel.add(formulaInput);
    spreadsheetView.add(this.buttonPanel, BorderLayout.NORTH);
  }


  @Override
  public void render() throws IOException {
    this.spreadsheetView.setVisible(true);
  }
}
