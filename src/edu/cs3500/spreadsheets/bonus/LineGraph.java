package edu.cs3500.spreadsheets.bonus;

import java.util.ArrayList;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;

public class LineGraph implements Graph {
  private ArrayList<Coord> cellsInGraph;

  public LineGraph(ArrayList<Coord> cells) {
    this.cellsInGraph = cells;
  }

  @Override
  public List<Coord> getCellRegion() {
    return this.cellsInGraph;
  }
}
