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

  public BasicWorksheetGraphicalView() {
    super();
    this.setTitle("Microsoft Excel 2019 - New Spreadsheet");
    this.setSize(900, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.rowPanel = new RowPanel();
    this.rowPanel.setPreferredSize(new Dimension(20, 200));
    this.rowPanel.setBackground(Color.GRAY);
    this.add(this.rowPanel, BorderLayout.WEST);

    this.columnPanel = new ColumnPanel();
    this.columnPanel.setPreferredSize(new Dimension(600, 20));
    this.columnPanel.setBackground(Color.GRAY);
    this.add(this.columnPanel, BorderLayout.NORTH);

    // adds the spreadsheet panel to the view along with vertical and horizontal scroll bars
    this.spreadsheetPanel = new SpreadsheetPanel();
    this.spreadsheetPanel.setPreferredSize(new Dimension(10000, 500));
    this.scroller = new JScrollPane(this.spreadsheetPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.add(scroller, BorderLayout.CENTER);

    //______________________________________________________________________________________________


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
