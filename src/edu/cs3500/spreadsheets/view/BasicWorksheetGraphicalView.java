package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;

public class BasicWorksheetGraphicalView extends JFrame implements BasicWorksheetView {
  private SpreadsheetPanel spreadsheetPanel;
  private RowPanel rowPanel;
  private ColumnPanel columnPanel;
  private JScrollPane scroller;

  final static int GRID_CELLS = 200;

  public BasicWorksheetGraphicalView() {
    super();
    this.setTitle("Microsoft Excel 2019 - New Spreadsheet");
    this.setSize(1350, 750);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.rowPanel = new RowPanel();
    this.rowPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH,
            GRID_CELLS * SpreadsheetPanel.CELL_WIDTH));
    this.rowPanel.setBackground(Color.GRAY);

    this.columnPanel = new ColumnPanel();
    this.columnPanel.setPreferredSize(new Dimension(SpreadsheetPanel.CELL_WIDTH * GRID_CELLS,
            SpreadsheetPanel.CELL_HEIGHT));
    this.columnPanel.setBackground(Color.GRAY);

    // adds the spreadsheet panel to the view along with vertical and horizontal
    // scroll bars and panels
    this.spreadsheetPanel = new SpreadsheetPanel();
    this.spreadsheetPanel.setPreferredSize(new Dimension(
            GRID_CELLS * SpreadsheetPanel.CELL_WIDTH,
            GRID_CELLS * SpreadsheetPanel.CELL_HEIGHT));
    this.scroller = new JScrollPane(this.spreadsheetPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.scroller.setRowHeaderView(this.rowPanel);
    this.scroller.setColumnHeaderView(this.columnPanel);
    this.add(scroller, BorderLayout.CENTER);

  }

  public BasicWorksheetGraphicalView(BasicWorksheetModel model) {
    HashMap<Coord, CellFormula> cells = model.getCells();
    for (Map.Entry<Coord, CellFormula> entry : cells.entrySet()) {
      entry.getValue().evaluateCell();
    }
  }

  @Override
  public void render() {
    this.setVisible(true);
  }
}
