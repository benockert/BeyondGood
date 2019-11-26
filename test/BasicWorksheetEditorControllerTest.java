import org.junit.Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.BasicWorksheetEditorController;
import edu.cs3500.spreadsheets.controller.HighlightCell;
import edu.cs3500.spreadsheets.model.BasicWorksheetModel;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.view.BasicWorksheetEditorView;
import edu.cs3500.spreadsheets.view.SpreadsheetPanel;
//import javafx.scene.input.KeyEvent;

import static org.junit.Assert.assertEquals;

/**
 * A test class to test the functionality of our controller.
 */
public class BasicWorksheetEditorControllerTest {
  private BasicWorksheetModel worksheet;
  private BasicWorksheetReadOnlyModel readonly;
  private SpreadsheetPanel spreadsheet;
  private BasicWorksheetEditorView view;
  private BasicWorksheetEditorController controller;
  private HighlightCell highlightedCell;
  private JButton clear;
  private JButton enter;
  private Coord location;
  private Coord location2;

  private void initData() {
    this.location = new Coord(1, 4);
    this.location2 = new Coord(1, 1);
    this.worksheet = new BasicWorksheetModel();
    this.worksheet.editCell("=(SUM 5.0 6.0)", location);
    this.readonly = new BasicWorksheetReadOnlyModel(this.worksheet);
    this.spreadsheet = new SpreadsheetPanel(15, 30, this.readonly);
    this.spreadsheet.setHighlightLocation(0, 3);
    this.view = new BasicWorksheetEditorView(this.readonly);
    this.controller = new BasicWorksheetEditorController(this.worksheet, this.view);
    this.highlightedCell = new HighlightCell(this.spreadsheet, this.view);
    this.clear = new JButton("✘");
    this.clear.setActionCommand("Reject button");
    this.enter = new JButton("✔");
    this.enter.setActionCommand("Accept button");

  }

  // TODO
  @Test
  public void testAccept() {
  }

  @Test
  public void testReject() throws AWTException {
    initData();
    //this.controller.actionPerformed(new ActionEvent(this.clear, ActionEvent.ACTION_PERFORMED,
   //         "Reject button"));
    Robot bot = new Robot();
    assertEquals("", this.view.getViewTextField());
    assertEquals(location2, view.getHighlightedCell());
    bot.mouseMove(44, 70);
    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    assertEquals(location, view.getHighlightedCell());
    assertEquals("=(SUM 5.0 6.0)", this.view.getViewTextField());
    bot.mouseMove(164, 12);
    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    bot.keyPress(65);
    bot.keyRelease(65);
    bot.keyPress(66);
    bot.keyRelease(66);
    bot.keyPress(67);
    bot.keyRelease(67);
    assertEquals("abc", this.view.getViewTextField());
    bot.mouseMove(124, 10);
    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    assertEquals("=(SUM 5.0 6.0)", this.view.getViewTextField());
  }
}
