package edu.cs3500.spreadsheets.provider.view.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.view.controller.Features;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.ConvertToForm;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.IFormula;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.InvalidFormulaException;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.values.BooleanVal;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.values.DoubleValue;
import edu.cs3500.spreadsheets.provider.view.model.cellcontents.values.StringValue;
import edu.cs3500.spreadsheets.sexp.Parser;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Constructs a view that can have cells edited by clicking on them, entering the new desired
 * formula into the text box, and confirming/denying the new formula with the buttons. Also supports
 * infinite scrolling.
 */
public class EditableView extends JFrame implements IEditableView {

  private final JButton rejectButton;
  private final JButton acceptButton;
  public final JTextField formulaBox;
  private final VisualView oldView;

  /**
   * Constructs our Editable view using the VisualView that was previously created that was
   * read-only.
   *
   * @param v the old view we are now essentially adding functionality to.
   */
  public EditableView(VisualView v, String filename) {
    super();

    this.oldView = v;
    this.rejectButton = new JButton("X");
    this.acceptButton = new JButton("!");
    this.formulaBox = new JTextField(10);

    this.setTitle(filename);
    this.setSize(oldView.getWidth(), this.oldView.getHeight());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().add(this.setPanel(), BorderLayout.BEFORE_FIRST_LINE);
    this.getContentPane().add(oldView.getContentPane(), BorderLayout.CENTER);
  }

  /**
   * Sets the layout of the widgets on the panel.
   *
   * @return
   */
  private JPanel setPanel() {
    JPanel p = new JPanel();
    p.setLayout(new BorderLayout());
    p.add(rejectButton, BorderLayout.WEST);
    p.add(acceptButton, BorderLayout.EAST);
    p.add(formulaBox, BorderLayout.CENTER);
    return p;
  }

  /**
   * Renders the view by wrapping all of the new components around the composite spreadsheet.
   */
  @Override
  public void render() {
    this.oldView.render();
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
   * Makes this Spreadsheet visible.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Adds the desired Features to this View. Adds a reject button that rejects the input in the text
   * box, an accept button that accepts the textbox, and allows for selecting cells with the mouse.
   *
   * @param features the Features
   */
  @Override
  public void addFeatures(Features features) {
    rejectButton.addActionListener(evt -> features.rejectChanges(this.getSelected()));
    acceptButton.addActionListener(evt -> features.acceptChanges(this.getSelected()));

    oldView.addMouseListener(new MouseAdapter() {

      /**
       * Overrides the mouseClicked method so that we can select cells.
       * @param e the MouseEvent
       */
      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          features.selectCell(new Coord(e.getX() / oldView.cellWidth,
              e.getY() / oldView.cellHeight));
        } catch (IllegalArgumentException ex) {
          //this means that we clicked on not a cell and we don't need to handle that
        }
      }
    });

    oldView.shorizontal.addAdjustmentListener(e -> {
      if (e.getValue() >= (oldView.shorizontal.getMaximum() * .5)) {
        oldView.cols++;
        EditableView.this.render();
      }
    });

    oldView.svertical.addAdjustmentListener(e -> {
      if (e.getValue() >= (oldView.svertical.getMaximum() * .5)) {
        oldView.rows++;
        EditableView.this.render();
      }
    });

    oldView.setFocusable(true);
    oldView.requestFocus();
  }

  /**
   * Sets the selected Cell to the one that was clicked, and reflects that in the textBox.
   *
   * @param c            the Coordinate
   * @param cellContents the String rep. of the Cell's contents
   */
  @Override
  public void setSelected(Coord c, String cellContents) {
    String contents = cellContents;
    String equal = "=";
    if (!cellContents.startsWith(equal)) {
      contents = equal.concat(contents);
    }
    this.formulaBox.setText(contents);
    oldView.setSelected(c);
  }

  /**
   * Sets the selected Cell to the one that was clicked.
   *
   * @param c the coordinate of the cell
   */
  @Override
  public void setSelected(Coord c) {
    oldView.setSelected(c);
  }

  /**
   * Gets the currently selected cell.
   *
   * @return the currently selected cell.
   */
  @Override
  public Coord getSelected() {
    return oldView.getSelected();
  }

  /**
   * Gets the text from the formulaBox and returns it as an IFormula.
   *
   * @return the IFormula representation of the input
   */
  @Override
  public IFormula getFormulaInBox() {
    if (formulaBox.getText().isEmpty()) {
      return null;
    }
    try {
      if (formulaBox.getText().trim().startsWith("=")) {
        return Parser.parse(formulaBox.getText().trim().substring(1)).accept(new ConvertToForm());
      } else {
        return parseValue(formulaBox.getText().trim());
      }
    } catch (IllegalArgumentException | InvalidFormulaException e) {
      return new StringValue(formulaBox.getText(), e.getMessage());
    }
  }

  /**
   * Parses the given string as simply an IValue (cannot be a Sexp).
   *
   * @param contents the contents we will parse
   * @return the IFormula the contents represent
   * @throws InvalidFormulaException if the Value was formatted incorrectly
   */
  private IFormula parseValue(String contents) throws InvalidFormulaException {
    if (contents.equals("false") || contents.equals("true")) {
      return new BooleanVal(Boolean.parseBoolean(contents));
    }
    try {
      return new DoubleValue(Double.parseDouble(contents));
    } catch (NumberFormatException e) {
      if (contents.startsWith("\"") && contents.endsWith("\"")) {
        return Parser.parse(contents).accept(new ConvertToForm());
      } else {
        throw new InvalidFormulaException("#NAME?");
      }
    }
  }
}