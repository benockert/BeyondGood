package edu.cs3500.spreadsheets.view;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;

/**
 * Represents the GUI view of a basic spreadsheeet, so the user can view files and inputs on a
 * spreadsheet.
 */
public class BasicWorksheetGraphicalView extends JFrame implements BasicWorksheetView {
  private SpreadsheetPanel spreadsheetPanel;
  private RowPanel rowPanel;
  private ColumnPanel columnPanel;

  // sets the total number of cells to be 100
  final int GRID_CELLS = 100;

  /**
   * A constructor for the GUI view of a spreadsheet that creates a new blank spreadsheet.
   */
  public BasicWorksheetGraphicalView() {
    super();
    this.setTitle("Microsoft Excel 2019 - New Spreadsheet"); // sets window title
    this.setSize(1350, 750); // sets window size
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets window close button action

    // draws the row panel with the index of each row displayed in a grey-ed out cell
    this.rowPanel = new RowPanel(GRID_CELLS);
    this.rowPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH,
            GRID_CELLS * SpreadsheetPanel.CELL_WIDTH));
    this.rowPanel.setBackground(Color.GRAY);

    // draws the column panel with the index of each row displayed as a letter or series of
    // letters in a grey-ed out cell
    this.columnPanel = new ColumnPanel(GRID_CELLS);
    this.columnPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH * GRID_CELLS,
            SpreadsheetPanel.CELL_HEIGHT));
    this.columnPanel.setBackground(Color.GRAY);

    // draws all of the cells along with their components
    this.spreadsheetPanel = new SpreadsheetPanel(GRID_CELLS, GRID_CELLS, new BasicWorksheetModel());
    this.spreadsheetPanel.setPreferredSize(new Dimension(
            GRID_CELLS * SpreadsheetPanel.CELL_WIDTH,
            GRID_CELLS * SpreadsheetPanel.CELL_HEIGHT));

    this.assembleFrame();


  }

  /**
   * A constructor for the GUI view of a spreadsheet which reads an existing model and displays that
   * in the view.
   *
   * @param model The given spreadsheet model/file to be displayed
   */
  public BasicWorksheetGraphicalView(BasicWorksheetModel model) {
    super();
    this.setTitle("Microsoft Excel 2019 - New Spreadsheet"); // sets window title
    this.setSize(1350, 750); // sets window size
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets window close button action

    // establishes the number of rows and columns of cells to draw, based on the model requirements
    int numRowsToDraw;
    int numColsToDraw;
    // the default number of rows to draw is 100, unless the model requires more
    if (model.getNumRows() > 100) {
      numRowsToDraw = model.getNumRows() + 1;
    } else {
      numRowsToDraw = 100;
    }
    // the default number of columns to draw is 50, unless the model requires more
    if (model.getNumCols() > 50) {
      numColsToDraw = model.getNumCols() + 1;
    } else {
      numColsToDraw = 50;
    }

    // draws the row panel with the index of each row displayed in a grey-ed out cell
    this.rowPanel = new RowPanel(numRowsToDraw);
    this.rowPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH,
            GRID_CELLS * SpreadsheetPanel.CELL_WIDTH));
    this.rowPanel.setBackground(Color.GRAY);

    // draws the column panel with the index of each row displayed as a letter or series of
    // letters in a grey-ed out cell
    this.columnPanel = new ColumnPanel(numColsToDraw);
    this.columnPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH * GRID_CELLS,
            SpreadsheetPanel.CELL_HEIGHT));
    this.columnPanel.setBackground(Color.GRAY);

    // draws all of the cells along with their components
    this.spreadsheetPanel = new SpreadsheetPanel(numRowsToDraw, numColsToDraw, model);
    this.spreadsheetPanel.setPreferredSize(new Dimension(
            numColsToDraw * SpreadsheetPanel.CELL_WIDTH,
            numRowsToDraw * SpreadsheetPanel.CELL_HEIGHT));

    this.assembleFrame(); 
  }

  @Override
  public void render() {
    this.setVisible(true);
  }

  /**W
   * Adds the spreadsheet panel to the view along with vertical and horizontal scroll bars and the
   * row/column header panels.
   */
  private void assembleFrame() {
    JScrollPane scroller = new JScrollPane();
    scroller.setViewportView(this.spreadsheetPanel);
    scroller.setRowHeaderView(this.rowPanel);
    scroller.setColumnHeaderView(this.columnPanel);
    add(scroller, BorderLayout.CENTER);
  }
}
