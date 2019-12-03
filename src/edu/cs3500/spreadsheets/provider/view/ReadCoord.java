package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.InvalidFormulaException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Provides a class that allows us to parse strings as cell references.
 */
public final class ReadCoord {

  /**
   * Uses coordinates to parse the String and return the single Coord represented.
   *
   * @param s the String trying to represent a Coord
   * @return The Coord represented
   * @throws IllegalArgumentException if the String is malformed or refers to multiple Coordinates
   */
  static Coord coordinate(String s) throws IllegalArgumentException {
    try {
      ArrayList<Coord> c = coordinates(s);
      if (c.size() != 1) {
        throw new InvalidFormulaException("Coordinate-setting must be a singular coordinate");
      }
      return c.get(0);
    } catch (InvalidFormulaException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Returns the ArrayList of Coordinates represented by the String. String format follows either
   * '[a-zA-z]+\d+' for a single Coordinate or '[a-zA-z]+\d+\:[a-zA-z]+\d+' for a range.
   *
   * @param s the String to be parsed
   * @return the ArrayList of Coordinates represented by the String
   * @throws InvalidFormulaException if the String is malformed ie doesn't follow above format, or
   *                                 first coordinate does not come before second
   */
  public static ArrayList<Coord> coordinates(String s) throws InvalidFormulaException {
    HashMap<String, String> rowsCols = convert(s);

    String col1 = rowsCols.get("col1");
    String row1 = rowsCols.get("row1");
    String col2 = rowsCols.get("col2");
    String row2 = rowsCols.get("row2");

    Coord c1 = new Coord(Coord.colNameToIndex(col1), Integer.parseInt(row1));

    if (col1 != null && col2 != null) {
      Coord c2 = new Coord(Coord.colNameToIndex(col2), Integer.parseInt(row2));
      return getInBetween(c1, c2);
    } else {
      return getInBetween(c1, c1);
    }
  }

  /**
   * Converts the String into the Coordinates represented by it. Stores the rows and columns in a
   * HashMap, continually adding to what we represent as we parse the String. If the second col and
   * row are not stored, then the String only represented one Coord.
   *
   * @param s the String to be parsed
   * @return the HashMap with values stored
   * @throws InvalidFormulaException if the String is malformed [see guideline in coordinates
   *                                 javadoc]
   */
  private static HashMap<String, String> convert(String s) throws InvalidFormulaException {
    HashMap<String, String> output = new HashMap<>();
    boolean isRange = false;
    int currentIndex = 0;
    for (int i = 0; i < s.length(); i++) {
      Character current = s.charAt(i);
      switch (currentIndex) {
        case 0:
          if (Character.isLetter(current)) {
            output.put("col1", output.getOrDefault("col1", "") + current);
            break;
          } else if (Character.isDigit(current) && output.get("col1") != null) {
            output.put("row1", output.getOrDefault("row1", "") + current);
            currentIndex += 1;
            break;
          } else {
            throw new InvalidFormulaException("First Column must be followed by First Row");
          }
        case 1:
          if (Character.isDigit(current)) {
            output.put("row1", output.getOrDefault("row1", "") + current);
            break;
          } else if (current.equals(':') && output.get("row1") != null) {
            currentIndex += 1;
            isRange = true;
            break;
          } else {
            throw new InvalidFormulaException(
                    "First Row must be followed by a colon (or end)");
          }
        case 2:
          if (Character.isLetter(current)) {
            output.put("col2", output.getOrDefault("col2", "") + current);
            break;
          } else if (Character.isDigit(current) && output.get("col2") != null) {
            output.put("row2", output.getOrDefault("row2", "") + current);
            currentIndex += 1;
            break;
          } else {
            throw new InvalidFormulaException("Second Column must be followed by Second Row");
          }
        case 3:
          if (Character.isDigit(current)) {
            output.put("row2", output.getOrDefault("row2", "") + current);
            break;
          } else {
            throw new InvalidFormulaException("Second Row must not have any trailing characters");
          }
        default:
          throw new IllegalStateException("convert: Dunno how we got here");
      }
    }
    if (output.get("col1") == null || output.get("row1") == null) {
      throw new InvalidFormulaException("String must refer to at least one Coordinate");
    }

    if (isRange && (output.getOrDefault("col2", null) == null
            || output.getOrDefault("row2", null) == null)) {
      throw new InvalidFormulaException(
              "If there exists a colon, it must be followed by a second column and row");
    }
    return output;
  }

  /**
   * Gets the inclusive coordinates between two coordinates.
   *
   * @param start first Coord
   * @param end   end Coord
   * @return all in between (inclusive)
   */
  private static ArrayList<Coord> getInBetween(Coord start, Coord end)
          throws InvalidFormulaException {

    if ((start.col > end.col) || (start.row > end.row)) {
      throw new InvalidFormulaException("Coord has to be from top left to bottom right");
    }

    ArrayList<Coord> output = new ArrayList<>();
    for (int i = start.col; i <= end.col; i++) {
      for (int j = start.row; j <= end.row; j++) {
        output.add(new Coord(i, j));
      }
    }
    return output;
  }
}
