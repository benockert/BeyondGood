package edu.cs3500.spreadsheets.bonus;

/**
 * A custom exception class that we can use to inform the user if they attempt to construct
 * an invalid graph for cells in their worksheet. For example, in a line graph, it will be invalid
 * if there are more than two columns highlighted, or if the number of cells in each column does
 * not match.
 */
public class IllegalGraphConstruct extends Exception {

  /**
   * Constructs the custom exception class.
   * @param message the message that will be displayed with the exception
   */
  public IllegalGraphConstruct(String message) {
    super(message);
  }


}
