package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;

import edu.cs3500.spreadsheets.model.Coord;

public class BasicWorksheetGraphicalView extends JFrame implements BasicWorksheetView {
  private JPanel columnPanel;
  private JPanel rowPanel;
  private SpreadsheetPanel gridPanel;
  private JPanel A1Panel;
  private JPanel A2Panel;

  public BasicWorksheetGraphicalView() {
    super();
    this.setTitle("Microsoft Excel 2019 - New Spreadsheet");
    this.setSize(900, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
    this.gridPanel = new SpreadsheetPanel();
    this.gridPanel.setPreferredSize(new Dimension(900, 500));

    //A1 Cell Panel
    this.A1Panel = new JPanel();
    Border blackline = BorderFactory.createLineBorder(Color.black);
    this.A1Panel.setBorder(blackline);
    this.A1Panel.setPreferredSize(new Dimension(64, 20));
    this.gridPanel.add(this.A1Panel);
    this.add(this.gridPanel);

    //A2 Cell Panel
    this.A2Panel = new JPanel();
    this.A2Panel.setBorder(blackline);
    this.A2Panel.setPreferredSize(new Dimension(64, 20));
    this.gridPanel.add(this.A2Panel);
    this.add(this.gridPanel);

    //row panel
    this.rowPanel = new JPanel();
    this.rowPanel.setPreferredSize(new Dimension(30, 500));
    this.rowPanel.setLayout(new FlowLayout());
    this.rowPanel.setBackground(Color.GRAY);
    this.add(this.rowPanel, BorderLayout.WEST);

    //column panel
    this.columnPanel = new JPanel();
    this.columnPanel.setPreferredSize(new Dimension(900, 30));
    this.columnPanel.setLayout(new FlowLayout());
    this.columnPanel.setBackground(Color.GRAY);
    this.add(this.columnPanel, BorderLayout.NORTH);

    this.pack();

  }

  @Override
  public void render() {
    this.setVisible(true);
  }
}
