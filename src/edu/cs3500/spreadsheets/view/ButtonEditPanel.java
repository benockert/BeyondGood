package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.IFeatures;

public class ButtonEditPanel extends javax.swing.JPanel {
  public JButton accept;
  public JButton reject;
  public JTextField textInput;

  public ButtonEditPanel(int width) {
    // creates a new button panel to house the text fields and accept/reject buttons
    this.setLayout(new FlowLayout());

    // accept button
    this.accept = new JButton("✔");
    this.accept.setActionCommand("Accept button");
    this.add(this.accept);

    // reject button
    this.reject = new JButton("✘");
    this.reject.setActionCommand("Reject button");
    this.add(this.reject);

    // creates the text field and sets its size to mostly fill the horizontal space of the view
    this.textInput = new JTextField(width / 14);
    this.textInput.setPreferredSize(new Dimension(width, 30));

    // adds the text field to the button panel and then adds the button panel to the existing view
    this.add(textInput);
  }

  public void setTextField(String contents) {
    this.textInput.setText(contents);
  }

  public String getInputText() {
    return this.textInput.getText();
  }

  public void addIFeatures(IFeatures feature) {

  }

}
