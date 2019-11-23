package edu.cs3500.spreadsheets.view;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;

/**
 * Represents the GUI view of a basic spreadsheeet, so the user can view files and inputs on a
 * spreadsheet.
 */
public class BasicWorksheetGraphicalView extends JFrame implements BasicWorksheetView {
  SpreadsheetPanel spreadsheetPanel;
  private RowPanel rowPanel;
  private ColumnPanel columnPanel;
  JScrollPane scroller;

  // sets the total number of cells to be 100
  int numRows = 100;
  int numCols = 50;

  /**
   * A constructor for the GUI view of a spreadsheet that creates a new blank spreadsheet.
   */
  public BasicWorksheetGraphicalView() {
    super();
    this.setTitle("Microsoft Excel 2019 - New Spreadsheet"); // sets window title
    this.setSize(1350, 750); // sets window size
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets window close button action

    // draws the row panel with the index of each row displayed in a grey-ed out cell
    this.rowPanel = new RowPanel(numRows);
    this.rowPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH,
            numRows * SpreadsheetPanel.CELL_WIDTH));
    this.rowPanel.setBackground(Color.GRAY);

    // draws the column panel with the index of each row displayed as a letter or series of
    // letters in a grey-ed out cell
    this.columnPanel = new ColumnPanel(numCols);
    this.columnPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH * numCols,
            SpreadsheetPanel.CELL_HEIGHT));
    this.columnPanel.setBackground(Color.GRAY);

    // draws all of the cells along with their components
    this.spreadsheetPanel = new SpreadsheetPanel(numRows, numCols, new BasicWorksheetModel());
    this.spreadsheetPanel.setPreferredSize(new Dimension(
            numCols * SpreadsheetPanel.CELL_WIDTH,
            numRows * SpreadsheetPanel.CELL_HEIGHT));

    this.addScrollPane();
  }

  /**
   * A constructor for the GUI view of a spreadsheet which reads an existing model and displays that
   * in the view.
   *
   * @param model The given spreadsheet model/file to be displayed
   */
  public BasicWorksheetGraphicalView(BasicWorksheetReadOnlyModel model) {
    super();
    this.setTitle("Microsoft Excel 2019 - New Spreadsheet"); // sets window title
    this.setSize(1350, 750); // sets window size
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets window close button action

    // establishes the number of rows and columns of cells to draw, based on the model requirements
    int numRowsToDraw;
    int numColsToDraw;
    // the default number of rows to draw is 100, unless the model requires more
    if (model.getNumRows() > numRows) {
      numRowsToDraw = model.getNumRows() + 1;
    } else {
      numRowsToDraw = numRows;
    }
    // the default number of columns to draw is 50, unless the model requires more
    if (model.getNumCols() > numCols) {
      numColsToDraw = model.getNumCols() + 1;
    } else {
      numColsToDraw = numCols;
    }

    // draws the row panel with the index of each row displayed in a grey-ed out cell
    this.rowPanel = new RowPanel(numRowsToDraw);
    this.rowPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH,
            numRows * SpreadsheetPanel.CELL_WIDTH));
    this.rowPanel.setBackground(Color.GRAY);

    // draws the column panel with the index of each row displayed as a letter or series of
    // letters in a grey-ed out cell
    this.columnPanel = new ColumnPanel(numColsToDraw);
    this.columnPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH * numColsToDraw,
            SpreadsheetPanel.CELL_HEIGHT));
    this.columnPanel.setBackground(Color.GRAY);

    // draws all of the cells along with their components
    this.spreadsheetPanel = new SpreadsheetPanel(numRowsToDraw, numColsToDraw, model);
    this.spreadsheetPanel.setPreferredSize(new Dimension(
            numColsToDraw * SpreadsheetPanel.CELL_WIDTH,
            numRowsToDraw * SpreadsheetPanel.CELL_HEIGHT));

    this.addScrollPane();

  }

  @Override
  public void render() {
    this.setVisible(true);
  }

  private void addScrollPane() {
    final JScrollPane scroller = new JScrollPane();
    // sets up the scroller panels
    scroller.setViewportView(this.spreadsheetPanel);
    scroller.setRowHeaderView(this.rowPanel);
    scroller.setColumnHeaderView(this.columnPanel);
    // increases the speed
    scroller.getVerticalScrollBar().setUnitIncrement(16);
    scroller.getHorizontalScrollBar().setUnitIncrement(16);
    // adds a listener for infinite scrolling
    scroller.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
      @Override
      public void adjustmentValueChanged(AdjustmentEvent ae) {
        // checks if the scroll bars has been moved
        inifinteScroll(ae, true);
      }
    });
    scroller.getHorizontalScrollBar().addAdjustmentListener(ae -> {
      // checks if the scroll bars has been moved
      inifinteScroll(ae, false);
    });
    this.add(scroller, BorderLayout.CENTER);
  }

  private void inifinteScroll(AdjustmentEvent ae, boolean isVert) {
    if (!ae.getValueIsAdjusting()) {
      JScrollBar scrollBar = (JScrollBar) ae.getAdjustable();
      int extent = scrollBar.getModel().getExtent();
      int max = scrollBar.getModel().getMaximum();
      int value = ae.getValue();
      if (extent + value == max) {
        if (isVert) {
          this.spreadsheetPanel.addRowAndChangeSize();
          this.rowPanel.addRowAndChangePanelSize();
        } else {
          this.spreadsheetPanel.addColAndChangeSize();
          this.columnPanel.addColAndChangePanelSize();
        }
      }
    }
  }
}
