import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImpl;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.BasicWorksheetSaveView;

import static org.junit.Assert.assertEquals;

/**
 * A class to test all of the functionality of text view.
 */
public class BasicWorksheetSaveViewTest {

  @Test
  public void testRoundTrip() throws IOException {
    // sets the file path string of the file we will read in
    String fileInName = "C:\\Users\\bocke\\Documents\\Object Oriented Design\\BeyondGood\\"
            + "Resources\\InputFile1";
    // sets the file path string of the file we will write to
    String fileOutName = "C:\\Users\\bocke\\Documents\\Object Oriented Design\\BeyondGood\\"
            + "Resources\\SavedFile1";
    // makes the files, Readables, and Appendables
    File fileIn = new File(fileInName);
    File fileOut = new File(fileOutName);
    FileReader readFile = new FileReader(fileIn);
    PrintWriter writeFile = new PrintWriter(fileOut);
    // males a worksheet builder
    WorksheetReader.WorksheetBuilder<BasicWorksheetModel> builder = new WorksheetBuilderImpl();

    // creates the initial model and renders it as a file
    BasicWorksheetModel modelIn = WorksheetReader.read(builder, readFile);
    BasicWorksheetReadOnlyModel readOnlyModel = new BasicWorksheetReadOnlyModel(modelIn);
    BasicWorksheetSaveView savedModelInView = new BasicWorksheetSaveView(readOnlyModel, writeFile);
    savedModelInView.render();

    // reads the file and creates another model from it
    FileReader readSavedFile = new FileReader(fileOut);
    BasicWorksheetModel savedModel = WorksheetReader.read(builder, readSavedFile);

    // checks the equivalance of two models
    assertEquals(true, modelIn.equals(savedModel));
  }

  @Test
  public void testRoundTrip2() throws IOException {
    // sets the file path string of the file we will read in
    String fileInName = "C:\\Users\\bocke\\Documents\\Object Oriented Design\\BeyondGood\\"
            + "Resources\\InputFile2";
    // sets the file path string of the file we will write to
    String fileOutName = "C:\\Users\\bocke\\Documents\\Object Oriented Design\\BeyondGood\\"
            + "Resources\\SavedFile2";
    // makes the files, Readables, and Appendables
    File fileIn = new File(fileInName);
    File fileOut = new File(fileOutName);
    FileReader readFile = new FileReader(fileIn);
    PrintWriter writeFile = new PrintWriter(fileOut);
    // males a worksheet builder
    WorksheetReader.WorksheetBuilder<BasicWorksheetModel> builder = new WorksheetBuilderImpl();

    // creates the initial model and renders it as a file
    BasicWorksheetModel modelIn = WorksheetReader.read(builder, readFile);
    BasicWorksheetReadOnlyModel readOnlyModel = new BasicWorksheetReadOnlyModel(modelIn);
    BasicWorksheetSaveView savedModelInView = new BasicWorksheetSaveView(readOnlyModel, writeFile);
    savedModelInView.render();

    // reads the file and creates another model from it
    FileReader readSavedFile = new FileReader(fileOut);
    BasicWorksheetModel savedModel = WorksheetReader.read(builder, readSavedFile);

    // checks the equivalance of two models
    assertEquals(true, modelIn.equals(savedModel));
  }

  @Test
  public void testRoundTrip3() throws IOException {
    // sets the file path string of the file we will read in
    String fileInName = "C:\\Users\\bocke\\Documents\\Object Oriented Design\\BeyondGood\\"
            + "Resources\\InputFile3";
    // sets the file path string of the file we will write to
    String fileOutName = "C:\\Users\\bocke\\Documents\\Object Oriented Design\\BeyondGood\\"
            + "Resources\\SavedFile3";
    // makes the files, Readables, and Appendables
    File fileIn = new File(fileInName);
    File fileOut = new File(fileOutName);
    FileReader readFile = new FileReader(fileIn);
    PrintWriter writeFile = new PrintWriter(fileOut);
    // males a worksheet builder
    WorksheetReader.WorksheetBuilder<BasicWorksheetModel> builder = new WorksheetBuilderImpl();

    // creates the initial model and renders it as a file
    BasicWorksheetModel modelIn = WorksheetReader.read(builder, readFile);
    BasicWorksheetReadOnlyModel readOnlyModel = new BasicWorksheetReadOnlyModel(modelIn);
    BasicWorksheetSaveView savedModelInView = new BasicWorksheetSaveView(readOnlyModel, writeFile);
    savedModelInView.render();

    // reads the file and creates another model from it
    FileReader readSavedFile = new FileReader(fileOut);
    BasicWorksheetModel savedModel = WorksheetReader.read(builder, readSavedFile);

    // checks the equivalance of two models
    assertEquals(true, modelIn.equals(savedModel));
  }

}


