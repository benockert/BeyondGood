package edu.cs3500.spreadsheets.provider.view.model.cellcontents;

/**
 * Creates a custom Exception that we can handle in our other classes differently than 'normal'
 * exceptions. For example, the Compare Function Object cannot compare the size of a Boolean. So it
 * throws this. This is more informative to us and the user than simply the Java-defined < operator
 * that will throw an IllegalArgumentException.
 */
public class InvalidFormulaException extends Exception {

  /**
   * Constructor for the Exception we have created.
   *
   * @param error the error message we want to print for the specific case
   */
  public InvalidFormulaException(String error) {
    super(error);
  }
}
