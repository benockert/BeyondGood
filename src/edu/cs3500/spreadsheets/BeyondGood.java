package edu.cs3500.spreadsheets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.IllegalFormatConversionException;
import java.util.Map;

import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImpl;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.BasicWorksheetGraphicalView;
import edu.cs3500.spreadsheets.view.BasicWorksheetSaveView;
import edu.cs3500.spreadsheets.view.BasicWorksheetView;

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
    /*
      TODO: For now, look in the args array to obtain a filename and a cell name,
      - read the file and build a model from it, 
      - evaluate all the cells, and
      - report any errors, or print the evaluated value of the requested cell.
    */

    if (args[0].equals("-in") && args[2].equals("-eval")) {
      runMainInEval(args);
    } else if (args[0].equals("-in") && args[2].equals("-save")) {
      runSaveView(args);
    } else if (args[0].equals("-in") && args[2].equals("-gui")) {
      runMainInGUI(args);
    } else if (args[0].equals("-gui")) {
      runNewGUI(args);
    }
  }

  /**
   * Creates a model from a user-given file path and evaluates a chosen cell from the model.
   *
   * @param args the command line arguments of from the client
   */
  private static void runMainInEval(String[] args) {
    // the worksheet builder
    WorksheetReader.WorksheetBuilder<BasicWorksheetModel> builder = new WorksheetBuilderImpl();
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

  /**
   * Creates a model from a user-given file path and then saves the model to a given file path for
   * later use.
   *
   * @param args the command line arguments of from the client
   */
  private static void runSaveView(String[] args) {
    // the worksheet builder
    WorksheetReader.WorksheetBuilder<BasicWorksheetModel> builder = new WorksheetBuilderImpl();
    String fileName = args[1];
    File file = new File(fileName);
    String fileOutName = args[3];
    File fileOut = new File(fileOutName);
    try {
      PrintWriter printToFile = new PrintWriter(fileOut);
      FileReader readFile = new FileReader(file);
      BasicWorksheetModel model = WorksheetReader.read(builder, readFile);
      BasicWorksheetSaveView saveView = new BasicWorksheetSaveView(model, printToFile);
      saveView.render();
    } catch (FileNotFoundException fnf) {
      System.out.println("Invalid file given");
    } catch (IOException io) {
      System.out.println("Error saving file");
    }
  }

  /**
   * Creates a model from a user-given file path and renders a graphical view of the model.
   *
   * @param args the command line arguments of from the client
   */
  private static void runMainInGUI(String[] args) {
    // the worksheet builder
    WorksheetReader.WorksheetBuilder<BasicWorksheetModel> builder = new WorksheetBuilderImpl();
    // the file from which the graphical view will be made
    String fileName = args[1];
    File file = new File(fileName);
    try {
      FileReader readFile = new FileReader(file);
      BasicWorksheetModel model = WorksheetReader.read(builder, readFile);
      BasicWorksheetGraphicalView savedFileView = new BasicWorksheetGraphicalView(model);
      savedFileView.render();
    } catch (FileNotFoundException fnf) {
      System.out.println("Invalid file given");
    } catch (IOException io) {
      System.out.println("Error reading file");
    }
  }

  /**
   * Creates a graphical view of a blank spreadsheet for the client to use.
   *
   * @param args the command line arguments of from the client
   */
  private static void runNewGUI(String[] args) {
    // the worksheet builder
    WorksheetReader.WorksheetBuilder<BasicWorksheetModel> builder = new WorksheetBuilderImpl();

    BasicWorksheetView view = new BasicWorksheetGraphicalView();
    try {
      view.render();
    } catch (IOException io) {
    }

  }

  /**
   * Formats a cell's contents based on assignment specifications.
   *
   * @param model            The given model containing the cell that needs formatting.
   * @param evaluateLocation The location of the cell that needs formatting.
   */
  private static void formatOutput(BasicWorksheetModel model, Coord evaluateLocation) {
    try {
      // if the cell is a number
      String output = String.format("%f", model.getCellAt(evaluateLocation).evaluateCell());
      System.out.println(output);
      // exits if the cells value was a number and it was printed in the proper format
      System.exit(0);
    } catch (IllegalFormatConversionException efce) {
    }
    // if the cell is a string
    String output = parseString(model.getCellAt(evaluateLocation).evaluateCell().toString());
    System.out.println(output);
  }

  /**
   * Formats a given string based on assignment specifications.
   *
   * @param unformattedOutput the unformatted string input (cell contents).
   * @return A formatted string with backslashes and double quotes escaped.
   */
  private static String parseString(String unformattedOutput) {
    // set accumulator string
    String accumulatedString = "";
    int i;
    // for each character in the string
    for (i = 0; i < unformattedOutput.length(); i++) {
      char c = unformattedOutput.charAt(i);
      // if it is a double quote or backslash, escape it
      if (c == '"' | c == '\\') {
        accumulatedString += "\\";
      }
      // append it to the accumulator
      accumulatedString += c;
    }
    // encase the whole string in double quotes
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
