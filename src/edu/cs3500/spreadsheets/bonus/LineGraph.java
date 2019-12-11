package edu.cs3500.spreadsheets.bonus;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.model.Coord;

public class LineGraph extends JPanel implements ExcellentWorksheetGraph {
  private List<Coord> referencedCellLocations;
  private HashMap<CellFormula, CellFormula> cellsInGraph;
  private JPanel xAxis;
  private JPanel yAxis;
  private JPanel dataPlot;

  public LineGraph() {
    this.referencedCellLocations = new ArrayList<Coord>();
    this.cellsInGraph = new HashMap<CellFormula, CellFormula>();
  }

  public LineGraph(List<Coord> ref, HashMap<CellFormula, CellFormula> cells) {
    this.referencedCellLocations = ref;
    this.cellsInGraph = cells;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.drawRect(10, 10, 400, 400);
    g2d.setColor(Color.BLACK);

    for (CellFormula xCell : this.cellsInGraph.keySet()) {
      String contentsToDisplay = this.cellsInGraph.get(xCell).getRawContents();
      g2d.drawString(contentsToDisplay, 10, 10);
    }

  }

  @Override
  public HashMap<CellFormula, CellFormula> getCellRegion() {
    return this.cellsInGraph;
  }

  @Override
  public void updateCellRegion(HashMap<CellFormula, CellFormula> newCells) {
    this.cellsInGraph = newCells;
  }

  @Override
  public List<Coord> getReferencedLocations() {
    return this.referencedCellLocations;
  }

  @Override
  public void updateCellRefLocations(List<Coord> referencedLocations) {
    this.referencedCellLocations = referencedLocations;

  }
}
