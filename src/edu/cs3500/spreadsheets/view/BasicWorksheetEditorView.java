package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.function.Consumer;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.HighlightCell;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Worksheet;

public class BasicWorksheetEditorView extends JFrame implements BasicWorksheetView {
  private BasicWorksheetGraphicalView spreadsheetView;
  private JTextField formulaInput;
  private JPanel buttonPanel;
  private JButton enter, clear;

  public BasicWorksheetEditorView(BasicWorksheetReadOnlyModel model) {
    super();
    // creates the existing view
    this.spreadsheetView = new BasicWorksheetGraphicalView(model);

    // creates a new button panel to house the text fields and accept/reject buttons
    this.buttonPanel = new JPanel();
    this.buttonPanel.setLayout(new FlowLayout());

    // accept button
    this.enter = new JButton("✔");
    buttonPanel.add(this.enter);

    // reject button
    this.clear = new JButton("✘");
    buttonPanel.add(this.clear);

    // creates the text field and sets its size to mostly fill the horizontal space of the view
    int size = this.spreadsheetView.getWidth();
    this.formulaInput = new JTextField(size / 14);
    this.formulaInput.setPreferredSize(new Dimension(size, 30));

    // adds the text field to the button panel and then adds the button panel to the existing view
    this.buttonPanel.add(formulaInput);
    spreadsheetView.add(this.buttonPanel, BorderLayout.NORTH);

    HighlightCell highlightedCell = new HighlightCell(this.spreadsheetView.spreadsheetPanel, model, this);
    this.spreadsheetView.spreadsheetPanel.addMouseListener(highlightedCell);
    this.formulaInput.setText(highlightedCell.getCellContents());

    // sets the text in thee toolbar to be the first highlighted cell
    this.setTextbox(model);


  }

  /**
   * Sets the text in the toolbar to be the raw contents of the highlighted cell.
   *
   * @param model The read-only-model used to find the highlighted cell.
   */
  public void setTextbox(BasicWorksheetReadOnlyModel model) {
    HighlightCell highlightedCell = new HighlightCell(this.spreadsheetView.spreadsheetPanel, model, this);
    this.spreadsheetView.spreadsheetPanel.addMouseListener(highlightedCell);
    this.formulaInput.setText("=" + highlightedCell.getCellContents());
  }


  @Override
  public void render() throws IOException {
    this.spreadsheetView.setVisible(true);
  }

}
