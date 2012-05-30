/*
 * Copyright (c) 2012 Richard Scott McNew.
 *
 * This file is part of ParseChop.
 *
 * ParseChop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ParseChop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ParseChop.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.mcnewfamily.rmcnew.view;

import net.mcnewfamily.rmcnew.controller.ParseController;
import net.mcnewfamily.rmcnew.model.SheetNotFoundException;
import net.mcnewfamily.rmcnew.model.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ParseTab extends JComponent implements ActionListener {

    protected String buttonText;
    protected String successText;
    protected ParseController controller;
    protected final String inputPrompt = "Select Excel manifest file from CRC";
	protected final String outputPrompt = "Select Output filename (.xlsx)";
	protected final String inputFilenameLabelBasis = "Excel manifest file from CRC:  ";
	protected final String outputFilenameLabelBasis = "Output filename:  ";
	protected final String errorMessageTitle = "File Not Specified";
	protected final String inputErrorMessage = "Please select the Excel manifest input file";
	protected final String outputErrorMessage = "Please select the desired name for the output file";

	protected File manifestInputFile = null;
	protected File manifestOutputFile = null;

	protected JButton inputButton;
	protected JButton outputButton;
	protected JLabel inputFilenameLabel;
	protected JLabel outputFilenameLabel;
	protected JButton generateButton;

	protected JFileChooser inputFileChooser;
	protected JFileChooser outputFileChooser;

	public ParseTab(String buttonText, String successText, ParseController controller) {
		this.buttonText = buttonText;
        this.successText = successText;
        this.controller = controller;
        inputButton = new JButton(inputPrompt);
		inputButton.addActionListener(this);
		outputButton = new JButton(outputPrompt);
		outputButton.addActionListener(this);
		inputFilenameLabel = new JLabel(inputFilenameLabelBasis);
		outputFilenameLabel = new JLabel(outputFilenameLabelBasis);
		generateButton = new JButton(buttonText);
		generateButton.addActionListener(this);

		inputFileChooser = new JFileChooser();
		inputFileChooser.setDialogTitle(inputPrompt);
		inputFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		inputFileChooser.setFileFilter(Util.EXCEL_FILTER);
		inputFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		outputFileChooser = new JFileChooser();
		outputFileChooser.setDialogTitle(outputPrompt);
		outputFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		outputFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// UI layout
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setMinimumSize(new Dimension(300,200));
		this.setPreferredSize(new Dimension(300, 200));
		this.add(inputButton);
		this.add(inputFilenameLabel);
		this.add(Box.createRigidArea(new Dimension(5, 20)));
		this.add(outputButton);
		this.add(outputFilenameLabel);
		this.add(Box.createRigidArea(new Dimension(5,35)));
		this.add(generateButton);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource() == inputButton) {
			int returnValue = inputFileChooser.showOpenDialog(ParseTab.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				manifestInputFile = inputFileChooser.getSelectedFile();
				inputFilenameLabel.setText(inputFilenameLabelBasis + manifestInputFile.getName());
			}
		} else if (actionEvent.getSource() == outputButton) {
			int returnValue = outputFileChooser.showSaveDialog(ParseTab.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				manifestOutputFile = outputFileChooser.getSelectedFile();
                manifestOutputFile = Util.attachXlsxExtensionIfMissing(manifestOutputFile);
				outputFilenameLabel.setText(outputFilenameLabelBasis + manifestOutputFile.getName());
			}
		} else if (actionEvent.getSource() == generateButton) {
			if (manifestInputFile == null) {
				JOptionPane.showMessageDialog(this, inputErrorMessage, errorMessageTitle, JOptionPane.ERROR_MESSAGE);
			} else if (manifestOutputFile == null) {
				JOptionPane.showMessageDialog(this, outputErrorMessage, errorMessageTitle, JOptionPane.ERROR_MESSAGE);
			} else {
                try {
				    controller.runWorkflow(manifestInputFile, manifestOutputFile);
				    JOptionPane.showMessageDialog(this, successText, "Success!", JOptionPane.PLAIN_MESSAGE);
                } catch (SheetNotFoundException snfe) {
                    JOptionPane.showMessageDialog(this, snfe.getMessage(), snfe.getClass().getName(), JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e+"\n"+ Util.convertStackTraceToString(e.getStackTrace()), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
                }
			}
		}
	}
}
