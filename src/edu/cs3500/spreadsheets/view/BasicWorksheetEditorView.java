package edu.cs3500.spreadsheets.view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;


import edu.cs3500.spreadsheets.controller.HighlightCell;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents the editable GUI view of a basic spreadsheet, so the user can view files and inputs on
 * a spreadsheet as well as edit cells and see the raw contents of cells.
 */
public class BasicWorksheetEditorView extends JFrame implements BasicWorksheetView {
  private BasicWorksheetGraphicalView spreadsheetView;
  private BasicWorksheetReadOnlyModel modelToDisplayandEdit;
  private JTextField formulaInput;
  private JPanel buttonPanel;
  private JButton enter;
  private JButton clear;

  /**
   * A constructor for the editable GUI view of a spreadsheet which reads an existing model and
   * displays that in the view.
   *
   * @param model The given spreadsheet model/file to be displayed and edited.
   */
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

    HighlightCell highlightedCell = new HighlightCell(this.spreadsheetView.spreadsheetPanel,
            this);
    this.spreadsheetView.spreadsheetPanel.addMouseListener(highlightedCell);
    this.formulaInput.setText(this.modelToDisplayandEdit
            .getCellAt(this.getHighlightedCell()).getRawContents());

    // sets the text in thee toolbar to be the first highlighted cell
    this.setTextbox();


  }

  /**
   * Sets the text in the toolbar to be the raw contents of the highlighted cell.
   */
  public void setTextbox() {
    HighlightCell highlightedCell = new HighlightCell(this.spreadsheetView.spreadsheetPanel, this);
    String contents =
            this.modelToDisplayandEdit.getCellAt(this.getHighlightedCell()).getRawContents();
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

  /**
   * Returns the current text in the textbox.
   *
   * @return A string representing the text in the box.
   */
  public String getViewTextField() {
    return this.formulaInput.getText();
  }

  /**
   * Returns the location of the cell which is currently highlighted.
   *
   * @return A coordinate representing the location of the highlighted cell.
   */
  public Coord getHighlightedCell() {
    return this.spreadsheetView.getHighlightedCell();
  }

  /**
   * A method which both revalidates and repaints the spreadsheet.
   */
  public void repaintSpreadsheet() {
    this.spreadsheetView.spreadsheetPanel.revalidate();
    this.spreadsheetView.spreadsheetPanel.repaint();
  }
}
