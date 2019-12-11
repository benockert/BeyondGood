package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import edu.cs3500.spreadsheets.bonus.IllegalGraphConstruct;
import edu.cs3500.spreadsheets.bonus.LineGraph;
import edu.cs3500.spreadsheets.cell.CellDouble;
import edu.cs3500.spreadsheets.cell.CellFormula;
import edu.cs3500.spreadsheets.cell.CellFunction;
import edu.cs3500.spreadsheets.controller.HighlightCell;
import edu.cs3500.spreadsheets.controller.IFeatures;
import edu.cs3500.spreadsheets.model.BasicWorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents the editable GUI view of a basic spreadsheet, so the user can view files and inputs on
 * a spreadsheet as well as edit cells and see the raw contents of cells.
 */
public class BasicWorksheetEditorView extends JFrame implements BasicWorksheetView {
  private BasicWorksheetGraphicalView spreadsheetView;
  private BasicWorksheetReadOnlyModel modelToDisplay;
  private ButtonEditPanel buttonPanel;
  private LineGraph graphPanel;

  /**
   * A constructor for the editable GUI view of a spreadsheet which reads an existing model and
   * displays that in the view.
   *
   * @param model The given spreadsheet model/file to be displayed and edited.
   */
  public BasicWorksheetEditorView(BasicWorksheetReadOnlyModel model) {
    super();
    this.setSize(1750, 800);
    // creates the existing view
    this.spreadsheetView = new BasicWorksheetGraphicalView(model);
    this.modelToDisplay = model;

    this.buttonPanel = new ButtonEditPanel(this.spreadsheetView.getWidth());
    spreadsheetView.add(this.buttonPanel, BorderLayout.NORTH);

    HighlightCell highlightedCell = new HighlightCell(this.spreadsheetView.spreadsheetPanel,
            this);
    this.spreadsheetView.spreadsheetPanel.addMouseListener(highlightedCell);
    this.spreadsheetView.spreadsheetPanel.addMouseMotionListener(highlightedCell);
    this.buttonPanel.setTextField(this.modelToDisplay
            .getCellAt(this.getHighlightedCell()).getRawContents());

    // adds the graph panel
    this.graphPanel = new LineGraph();
    this.graphPanel.setPreferredSize(new Dimension(420, 420));
    this.spreadsheetView.add(graphPanel, BorderLayout.EAST);

    // sets the text in thee toolbar to be the first highlighted cell
    this.setTextbox();
  }

  /**
   * Sets the text in the toolbar to be the raw contents of the highlighted cell.
   */
  public void setTextbox() {
    HighlightCell highlightedCell = new HighlightCell(this.spreadsheetView.spreadsheetPanel, this);
    String contents =
            this.modelToDisplay.getCellAt(this.getHighlightedCell()).getRawContents();
    this.spreadsheetView.spreadsheetPanel.addMouseListener(highlightedCell);
    if (contents.equals("")) {
      this.buttonPanel.setTextField(contents);
    } else {
      this.buttonPanel.setTextField("=" + contents);
    }
  }

  @Override
  public void render() {
    this.spreadsheetView.render();
  }

  /**
   * A method which both revalidates and repaints the spreadsheet.
   */
  @Override
  public void refresh() {
    this.spreadsheetView.spreadsheetPanel.revalidate();
    this.spreadsheetView.spreadsheetPanel.repaint();
    this.graphPanel.revalidate();
    this.graphPanel.repaint();
  }

  /**
   * Returns the current text in the textbox.
   *
   * @return A string representing the text in the box.
   */
  private String getViewTextField() {
    return this.buttonPanel.getInputText();
  }

  /**
   * Returns the location of the cell which is currently highlighted.
   *
   * @return A coordinate representing the location of the highlighted cell.
   */
  public Coord getHighlightedCell() {
    return this.spreadsheetView.getHighlightedCell();
  }

  /**
   * Changes the location of the highlighted cell in this spreadsheet view.
   *
   * @param columnFactor the number of columns the highlighted cell is moving
   * @param rowFactor    the number of rows the highlighted cell is moving
   */
  public void changeHighlightedCellLocation(int columnFactor, int rowFactor) {
    try {
      this.spreadsheetView.moveHighlightedCell(columnFactor - 1, rowFactor - 1);
    } catch (IllegalArgumentException e) {
      // nothing to do... don't move the highlighted cell
    }
  }

  //________________________________________________________________________________________________
  // GRAPH METHODS
  @Override
  public void updateGraph(List<Coord> cellLocations, HashMap<Double, Double> cellValues) {
    // updates the list of spreadsheet coordinates that the graph references
    this.graphPanel.updateCellRefLocations(cellLocations);
    // updates the hashmap of x-axis cells and y-axis cells
    this.graphPanel.updateCellRegion(cellValues);
    this.graphPanel.revalidate();
    this.graphPanel.repaint();
  }

  // gets the coordinate that the currently displayed graph references
  @Override
  public List<Coord> getGraphsReferencedCoords() {
    return this.graphPanel.getReferencedLocations();
  }

  @Override
  public void addGraphErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  public void updateGraphView(List<Coord> cellsToGraph) {
    try {
      // determines which column is the x axis and which is the y axis
      List<Integer> twoColumns = getTwoColumns(cellsToGraph);
      int leftColIndex;
      int rightColIndex;
      if (twoColumns.get(0) > twoColumns.get(1)) {
        leftColIndex = twoColumns.get(1);
        rightColIndex = twoColumns.get(0);
      } else {
        leftColIndex = twoColumns.get(0);
        rightColIndex = twoColumns.get(1);
      }

      // based on the columns above, gets the list of the highlighted cells in that column
      List<Coord> xAxis = new ArrayList<>();
      List<Coord> yAxis = new ArrayList<>();
      for (Coord coord : cellsToGraph) {
        if (coord.col == leftColIndex) {
          xAxis.add(coord);
        }
        if (coord.col == rightColIndex) {
          yAxis.add(coord);
        }
      }

      HashMap<Double, Double> xyMap = new HashMap<>();
      if (xAxis.size() != yAxis.size()) {
        throw new IllegalGraphConstruct("Cannot construct graph: Mismatched column sizes");
      } else {
        for (int i = 0; i < xAxis.size(); i++) {
          Double cellValueAtX;
          Double cellValueAtY;
          if (this.modelToDisplay.getCellAt(xAxis.get(i)) instanceof CellDouble ||
                  this.modelToDisplay.getCellAt(xAxis.get(i)) instanceof CellFunction) {
            cellValueAtX = (Double) this.modelToDisplay.getCellAt(xAxis.get(i)).evaluateCell();
          } else {
            cellValueAtX = 0.0;
          }
          if (this.modelToDisplay.getCellAt(yAxis.get(i)) instanceof CellDouble ||
                  this.modelToDisplay.getCellAt(yAxis.get(i)) instanceof CellFunction) {
            cellValueAtY = (Double) this.modelToDisplay.getCellAt(yAxis.get(yAxis.size() - (i + 1)))
                    .evaluateCell();
          } else {
            cellValueAtY = 0.0;
          }
          xyMap.put(cellValueAtX, cellValueAtY);
        }
      }
      this.updateGraph(cellsToGraph, xyMap);
    } catch (IllegalGraphConstruct igc) {
      this.addGraphErrorMessage(igc.getMessage());
    }
  }


  private List<Integer> getTwoColumns(List<Coord> cells) throws IllegalGraphConstruct {
    List<Integer> cols = new ArrayList<>();
    // goes throw the list of coordinates and gets the column indexes of two columns
    for (Coord coord : cells) {
      int columnIndex = coord.col;
      if (!cols.contains(columnIndex)) {
        cols.add(columnIndex);
      }
    }
    // if there is only column represented in the list, becomes invalid
    if (cols.size() != 2) {
      throw new IllegalGraphConstruct("Cannot construct graph: Does not have exactly 2 columns " +
              "of data");
    }
    return cols;
  }

  //____________________________________________________________________________________________

  /**
   * Determines what features should be executed based on the action that occurs is pressed (either
   * the cell's contents are mutated or the edits are denied).
   *
   * @param feature the feature of the spreadsheet that will be added
   */
  public void addIFeatures(IFeatures feature) {
    this.buttonPanel.accept.addActionListener(actionEvent -> {
      String text = getViewTextField();
      Coord location = getHighlightedCell();
      feature.acceptCellEdit(location, text);
    });
    this.buttonPanel.reject.addActionListener(actionEvent -> feature.rejectCellEdit());

    // adds actions to the graph button
    this.buttonPanel.graph.addActionListener(actionEvent -> {
      this.updateGraphView(this.spreadsheetView.getHighlightedCells());
    });

    // adds actions to the spreadsheet view for changing cell locations with arrow keys
    this.spreadsheetView.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent keyEvent) {
        //nothing else to do
      }

      @Override
      public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
          feature.moveHighlightedCell("up arrow");
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
          feature.moveHighlightedCell("down arrow");
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
          feature.moveHighlightedCell("right arrow");
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
          feature.moveHighlightedCell("left arrow");
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE) {
          feature.deleteCellContents(getHighlightedCell());
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
          String text = getViewTextField();
          Coord location = getHighlightedCell();
          feature.acceptCellEdit(location, text);
        }
      }

      @Override
      public void keyReleased(KeyEvent keyEvent) {
        //nothing else to do
      }
    });
    this.spreadsheetView.setFocusable(true);
  }
}
