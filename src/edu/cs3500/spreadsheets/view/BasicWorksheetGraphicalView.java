package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;

public class BasicWorksheetGraphicalView extends JFrame implements BasicWorksheetView {
  private SpreadsheetPanel spreadsheetPanel;
  private RowPanel rowPanel;
  private ColumnPanel columnPanel;
  private JScrollPane scroller;

  final static int GRID_CELLS = 100;

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

  public BasicWorksheetGraphicalView(BasicWorksheetModel model) {
    super();
    this.setTitle("Microsoft Excel 2019 - New Spreadsheet"); // sets window title
    this.setSize(1350, 750); // sets window size
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets window close button action

    // establishes the number of rows and columns of cells to draw, based on the model requirements
    int numRowsToDraw, numColsToDraw;
    // the default number of rows to draw is 100, unless the model requires more
    if (model.getNumRows() > 100) {
      numRowsToDraw = model.getNumRows();
    } else {
      numRowsToDraw = 100;
    }
    // the default number of columns to draw is 50, unless the model requires more
    if (model.getNumCols() > 50) {
      numColsToDraw = model.getNumCols();
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

  private void assembleFrame() {
    // adds the spreadsheet panel to the view along with vertical and horizontal
    // scroll bars and the row/column header panels
    this.scroller = new JScrollPane(this.spreadsheetPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.scroller.setRowHeaderView(this.rowPanel);
    this.scroller.setColumnHeaderView(this.columnPanel);
    this.add(scroller, BorderLayout.CENTER);
  }
}
