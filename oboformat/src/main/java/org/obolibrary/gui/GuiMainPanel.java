package org.obolibrary.gui;

import static org.obolibrary.gui.GuiTools.addRowGap;
import static org.obolibrary.gui.GuiTools.createTextField;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.obolibrary.gui.GuiTools.GBHelper;
import org.obolibrary.gui.GuiTools.SizedJPanel;

/**
 * GUI component for the minimum number of configurations required for the conversion.
 */
public class GuiMainPanel extends SizedJPanel {

	/// Generated
	private static final long serialVersionUID = 1281395185956670966L;

	final GuiMainFrame frame;
	final JTextField inputFileTextField;
	final JTextField outputFileTextField;
	final JRadioButton obo2owlButton;
	final JRadioButton owl2oboButton;
	

	/**
	 * Constructor allows to build a panel with default values
	 * 
	 * @param frame
	 * @param defaultInputFiles
	 * @param defaultOutputFile
	 * @param obo2owl
	 */
	public GuiMainPanel(GuiMainFrame frame, Iterable<String> defaultInputFiles, String defaultOutputFile, boolean obo2owl) {
		super();
		this.frame = frame;
		// create accessible fields
		// add default values to these fields
		
		// ontology file input files
		String defaultInputFileName = null;
		Iterator<String> iterator = defaultInputFiles.iterator();
		if (iterator.hasNext()) {
			defaultInputFileName = iterator.next();
		}
		inputFileTextField = createTextField(defaultInputFileName);
		
		// conversion direction buttons
		obo2owlButton = new JRadioButton("OBO2OWL");
		owl2oboButton = new JRadioButton("OWL2OBO");
		
		// output file
		outputFileTextField = createTextField(defaultOutputFile);
		
		setLayout(new GridBagLayout());
		GBHelper pos = new GBHelper();
		addRowGap(this, pos, 10);
		addLabelLine(pos, "Minimal options required to convert an ontology file");
		addRowGap(this, pos, 20);
		
		// input panel
		createInputPanel(pos, defaultInputFileName);
		addRowGap(this, pos, 20);
		
		// output panel
		createOutputPanel(pos, defaultOutputFile);
		addRowGap(this, pos, 20);
		
		// direction
		createDirectionPanel(pos, obo2owl);
	}
	
	private void addLabelLine(GBHelper pos, String text) {
		add(new JLabel(text), pos.nextRow().expandW().width(3));
		addRowGap(this, pos, 10);
	}
	
	/**
	 * Create layout and listeners for the ontology input files to be converted.
	 * 
	 * @param pos
	 * @param defaultFolder
	 */
	private void createInputPanel(GBHelper pos, String defaultFolder) {
		final SelectDialog dialog = SelectDialog.getFileSelector(frame, 
				SelectDialog.LOAD, 
				defaultFolder, 
				"Input ontology file choose dialog", 
				"OBO & OWL files", 
				new String[]{"obo","owl"});
		
		JButton fileDialogAddButton = new JButton("Select");
		
		// add listener for adding a file to the list model
		fileDialogAddButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dialog.show();
				String selected = dialog.getSelectedCanonicalPath();
				if (selected != null) {
					inputFileTextField.setText(selected);
				}
			}
		});
		
		addLabelLine(pos, "Input Ontology File");
		add(inputFileTextField, pos.nextRow().indentLeft(40).expandW().fill());
		add(fileDialogAddButton, pos.nextCol().indentLeft(10));
		pos.nextRow();
	}

	/**
	 * Create layout and listener for the output fields.
	 * 
	 * @param pos
	 * @param defaultFolder
	 */
	private void createOutputPanel(GBHelper pos, String defaultFolder) {
		final SelectDialog fileDialog = SelectDialog.getFileSelector(frame, 
				SelectDialog.SAVE, 
				defaultFolder, 
				"Output ontology file choose dialog", 
				"OBO & OWL files", 
				new String[]{"obo","owl"});
		
		final JButton fileButton = new JButton("Select");
		
		// add listener for selecting an output file name
		fileButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				fileDialog.show();
				String selected = fileDialog.getSelectedCanonicalPath();
				if (selected != null) {
					outputFileTextField.setText(selected);
				}
			}
		});
		
		addLabelLine(pos, "Output Ontology File");
		
		add(outputFileTextField, pos.nextRow().indentLeft(40).expandW().fill());
		add(fileButton, pos.nextCol().indentLeft(10));
	}
	
	/**
	 * Create layout for ontology conversion buttons
	 * 
	 * @param pos
	 * @param isObo2Owl
	 */
	private void createDirectionPanel(GBHelper pos, boolean isObo2Owl) {
		addLabelLine(pos, "Direction");
		ButtonGroup directionButtonGroup = new ButtonGroup();
		directionButtonGroup.add(obo2owlButton);
		directionButtonGroup.add(owl2oboButton);
		JPanel buttonPanel =  new JPanel(new GridLayout(1, 2));
		buttonPanel.add(obo2owlButton);
		buttonPanel.add(owl2oboButton);
		add(buttonPanel, pos.nextRow().indentLeft(40).expandW().expandH());
		
		// set default value for buttons
		obo2owlButton.setSelected(isObo2Owl);
		obo2owlButton.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				GuiAdvancedDirectionSpecificPanel panel = frame.getSpecificAdvancedPanel();
				panel.setObo2Owl(obo2owlButton.isSelected());
			}
		});
	}
}
