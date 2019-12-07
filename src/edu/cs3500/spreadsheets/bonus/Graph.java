package edu.cs3500.spreadsheets.bonus;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.model.Coord;

public interface Graph {

  /**
   * Gets the list of cell coordinates in a spreadsheet that will make up the data points of
   * this graph
   * @return a list of cell coordinates
   */
  public List<Coord> getCellRegion();

//  public HashMap<CellFormula, CellFormula>




}
