package edu.cs3500.spreadsheets.provider.view.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.model.ISpreadsheet;

/**
 * Represents a Spreadsheet as a JFrame that uses a SpreadsheetPanel. The size of the window is
 * dependent on the desired row and col number, and the size of the cells.
 */
public class VisualView extends JFrame implements IView {

  private final SpreadsheetPanel table;
  protected JScrollBar shorizontal;
  protected JScrollBar svertical;

  protected int rows;
  protected int cols;
  protected final int cellWidth = SpreadsheetPanel.cellWidth;
  protected final int cellHeight = SpreadsheetPanel.cellHeight;

  /**
   * Constructs a new VisualView using the given Spreadsheet.
   *
   * @param w the spreadsheet to be visualized
   */
  public VisualView(ISpreadsheet w, String fileName) {
    super();
    this.setTitle(fileName);
    this.table = new SpreadsheetPanel(w);
    this.rows = this.table.currentMaxRow;
    this.cols = this.table.currentMaxCol;
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    int width = ((this.table.currentMaxRow + 1) * cellWidth) + 34;
    int height = ((this.table.currentMaxCol + 1) * cellHeight) + 57;
    this.setSize(800, 600);
    this.table.setPreferredSize(new Dimension(width - 32, height - 57));
    this.getContentPane().add(getScrollPane(this.table), BorderLayout.CENTER);
  }

  /**
   * Returns a formatted ScrollPane containing a Spreadsheet and scrollbars.
   *
   * @param sp represents a Spreadsheet
   * @return ScrollPane
   */
  private JScrollPane getScrollPane(SpreadsheetPanel sp) {
    JScrollPane scrollPane = new JScrollPane(sp,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.shorizontal = new JScrollBar();
    this.svertical = new JScrollBar();
    this.shorizontal.setOrientation(0);
    this.svertical.setOrientation(1);
    scrollPane.setHorizontalScrollBar(shorizontal);
    scrollPane.setVerticalScrollBar(svertical);
    return scrollPane;
  }

  /**
   * Renders this again.
   */
  @Override
  public void render() {
    this.table.currentMaxRow = this.rows;
    this.table.currentMaxCol = this.cols;
    int width = ((this.table.currentMaxCol + 1) * cellWidth) + 34;
    int height = ((this.table.currentMaxRow + 1) * cellHeight) + 57;
    this.table.setPreferredSize(new Dimension(width - 32, height - 57));
    this.repaint();
  }

  /**
   * We do not support saving the file from the gui.
   *
   * @param fileName name of the file
   */
  @Override
  public void save(String fileName) {
    throw new UnsupportedOperationException("save() is not supported in the"
        + " Visual View class");
  }

  /**
   * Allows this to be visible.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Sets the selected Cell to the one that was clicked.
   *
   * @param c the coordinate of the cell
   */
  @Override
  public void setSelected(Coord c) {
    this.table.setSelected(c);
  }

  /**
   * Gets the currently selected cell.
   *
   * @return the currently selected cell.
   */
  @Override
  public Coord getSelected() {
    return this.table.getSelected();
  }

  /**
   * Overrides addMouseListener to instead add it to the panel this contains.
   *
   * @param listener the listener
   */
  @Override
  public void addMouseListener(MouseListener listener) {
    this.table.addMouseListener(listener);
  }
}
