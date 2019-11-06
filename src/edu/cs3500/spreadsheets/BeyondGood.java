package edu.cs3500.spreadsheets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.IllegalFormatConversionException;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImpl;
import edu.cs3500.spreadsheets.model.WorksheetReader;

/**
 * The main class for our program. This class actually runs the program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   *
   * @param args any command-line arguments.
   */
  public static void main(String[] args) {
    // the worksheet builder
    WorksheetReader.WorksheetBuilder<BasicWorksheetModel> builder = new WorksheetBuilderImpl();
    /*
      TODO: For now, look in the args array to obtain a filename and a cell name,
      - read the file and build a model from it, 
      - evaluate all the cells, and
      - report any errors, or print the evaluated value of the requested cell.
    */

    // the path to the text file that this spreadsheet will be created from
    String fileName = args[1];
    File file = new File(fileName);
    // the String name of the cell that we will eventually want to evaluate
    String cellName = args[3];
    // the Coordinate of the cell that we will eventually want to evaluate
    Coord evaluateLocation = getCoord(cellName);

    try {
      // attempts to make a readable file out of the given file name
      FileReader readFile = new FileReader(file);
      BasicWorksheetModel model = WorksheetReader.read(builder, readFile);
      formatOutput(model, evaluateLocation);
    } catch (FileNotFoundException fnf) {
      System.out.println("Invalid file given");
    }
  }

  private static void formatOutput(BasicWorksheetModel model, Coord evaluateLocation) {
    try {
      String output = String.format("%f", model.getCellAt(evaluateLocation).evaluateCell());
      System.out.println(output);
    } catch (IllegalFormatConversionException efce) {
    }
    String output = parseString(model.getCellAt(evaluateLocation).evaluateCell().toString());
    System.out.println(output);
  }

  private static String parseString(String unformattedOutput) {
    String accumulatedString = "";

    int i;
    for (i = 0; i < unformattedOutput.length(); i++) {
      char c = unformattedOutput.charAt(i);

      if (c == '"' | c == '\\') {
        accumulatedString += "\\";
      }
      accumulatedString += c;
    }
    return "\"" + accumulatedString + "\"";
  }

  /**
   * Gets the Coordinate representation of the given string cell name
   *
   * @param cellName the cell name represented as a string ([col][index])
   * @return the given cell String as a Coord
   */
  private static Coord getCoord(String cellName) {
    // parses the cellName to get the String representation of the column name
    int col = Coord.colNameToIndex(cellName.substring(0, 1));
    // parses the cellName to get the row number of the cell
    int row = Integer.parseInt(cellName.substring(1));
    // returns a new coordinate based on the row and column of the given cell name
    return new Coord(col, row);
  }
}
