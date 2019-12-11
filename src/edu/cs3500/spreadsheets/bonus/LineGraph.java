package edu.cs3500.spreadsheets.bonus;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;

public class LineGraph extends JPanel implements ExcellentWorksheetGraph {
  private List<Coord> referencedCellLocations;
  private HashMap<Double, Double> cellsInGraph;

  public LineGraph() {
    this.referencedCellLocations = new ArrayList<Coord>();
    this.cellsInGraph = new HashMap<Double, Double>();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    final int WIDTH = 400;
    final int HEIGHT = 400;

    // titles the graph
    String contentsToDisplay = "Graph";
    g2d.drawString(contentsToDisplay, 198, 9);

    // draws the graph outline
    g2d.drawRect(10, 10, WIDTH, HEIGHT);
    g2d.setColor(Color.BLACK);

    double maxYValue = 0.0;
    double maxXValue = 0.0;

    // determines the maximum value of each x and y axis
    for (Double xVal : this.cellsInGraph.keySet()) {
      if (this.cellsInGraph.get(xVal) > maxYValue) {
        maxYValue = this.cellsInGraph.get(xVal);
      }
      if (xVal > maxXValue) {
        maxXValue = xVal;
      }
    }

    // gets the magnitude by which numbers should be multiplied by to fit within the graph view
    double xMagnitude = (WIDTH - 10) / maxXValue;
    double yMagnitude = (HEIGHT - 10) / maxYValue;

    for (Double xVal : this.cellsInGraph.keySet()) {
      double xPos = xVal * xMagnitude + 10;
      double yPos = 410 - this.cellsInGraph.get(xVal) * yMagnitude;
      g2d.drawRect((int)xPos, (int)yPos, 5, 5);
      g2d.drawString(Double.toString(xVal) + ", " + Double.toString(this.cellsInGraph.get(xVal)),
              (int)xPos - 10, (int)yPos + 20);
    }

  }

  @Override
  public void updateCellRegion(HashMap<Double, Double> newCells) {
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
