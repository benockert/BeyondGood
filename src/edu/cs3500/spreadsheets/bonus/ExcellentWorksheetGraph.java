package edu.cs3500.spreadsheets.bonus;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;

public interface ExcellentWorksheetGraph {

  void updateCellRegion(HashMap<Double, Double> newCells);

  List<Coord> getReferencedLocations();

  void updateCellRefLocations(List<Coord> referencedLocations);



}
