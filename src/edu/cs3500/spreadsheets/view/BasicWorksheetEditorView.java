package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.function.Consumer;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.HighlightCell;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;

public class BasicWorksheetEditorView extends JFrame implements BasicWorksheetView {
  private BasicWorksheetGraphicalView spreadsheetView;
  private BasicWorksheetReadOnlyModel modelToDisplayandEdit;
  private JTextField formulaInput;
  private JPanel buttonPanel;
  private JButton enter, clear;

  public BasicWorksheetEditorView(BasicWorksheetReadOnlyModel model) {
    super();
    // creates the existing view
    this.spreadsheetView = new BasicWorksheetGraphicalView(model);
    this.modelToDisplayandEdit = model;

    // creates a new button panel to house the text fields and accept/reject buttons
    this.buttonPanel = new JPanel();
    this.buttonPanel.setLayout(new FlowLayout());

    // accept button
    this.enter = new JButton("✔");
    this.enter.setActionCommand("Accept button");
    buttonPanel.add(this.enter);

    // reject button
    this.clear = new JButton("✘");
    this.clear.setActionCommand("Reject button");
    buttonPanel.add(this.clear);

    // creates the text field and sets its size to mostly fill the horizontal space of the view
    int size = this.spreadsheetView.getWidth();
    this.formulaInput = new JTextField(size / 14);
    this.formulaInput.setPreferredSize(new Dimension(size, 30));

    // adds the text field to the button panel and then adds the button panel to the existing view
    this.buttonPanel.add(formulaInput);
    spreadsheetView.add(this.buttonPanel, BorderLayout.NORTH);

    HighlightCell highlightedCell = new HighlightCell(this.spreadsheetView.spreadsheetPanel, this);
    this.spreadsheetView.spreadsheetPanel.addMouseListener(highlightedCell);
    this.formulaInput.setText(this.modelToDisplayandEdit.getCellAt(this.getHighlightedCell()).getRawContents());

    // sets the text in thee toolbar to be the first highlighted cell
    this.setTextbox();


  }

  /**
   * Sets the text in the toolbar to be the raw contents of the highlighted cell.
   */
  public void setTextbox() {
    HighlightCell highlightedCell = new HighlightCell(this.spreadsheetView.spreadsheetPanel, this);
    String contents = this.modelToDisplayandEdit.getCellAt(this.getHighlightedCell()).getRawContents();
    this.spreadsheetView.spreadsheetPanel.addMouseListener(highlightedCell);
    if (contents.equals("")) {
      this.formulaInput.setText(contents);
    } else {
      this.formulaInput.setText("=" + contents);
    }
  }

  @Override
  public void render() {
    this.spreadsheetView.setVisible(true);
  }

  public void setListener(ActionListener listener) {
    this.enter.addActionListener(listener);
    this.clear.addActionListener(listener);
  }

  public String getViewTextField() {
    return this.formulaInput.getText();
  }

  public Coord getHighlightedCell() {
    return this.spreadsheetView.getHighlightedCell();
  }

  public void repaintSpreadsheet() {
    this.spreadsheetView.spreadsheetPanel.revalidate();
    this.spreadsheetView.spreadsheetPanel.repaint();
  }
}
