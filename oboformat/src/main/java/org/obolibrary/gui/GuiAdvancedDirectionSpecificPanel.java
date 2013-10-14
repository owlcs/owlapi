package org.obolibrary.gui;

import static org.obolibrary.gui.GuiTools.addRowGap;
import static org.obolibrary.gui.GuiTools.createTextField;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.obolibrary.gui.GuiTools.GBHelper;
import org.obolibrary.gui.GuiTools.SizedJPanel;

/**
 * GUI elements for the advanced settings of the converter
 */
public class GuiAdvancedDirectionSpecificPanel extends SizedJPanel {

	// generated
	private static final long serialVersionUID = -6706259947812407420L;
	
	final JCheckBox danglingCheckbox;
	final JCheckBox followImportsCheckBox;
	final JCheckBox expandMacrosCheckbox;
	final JTextField defaultOntologyField;
	
	final JCheckBox strictCheckBox;
	
	final JRadioButton formatRDFButton;
	final JRadioButton formatOWLXMLButton;
	final JRadioButton formatManchesterButton;

	private final List<JComponent> obo2owlComponents = new ArrayList<JComponent>();
	private final List<JComponent> owl2oboComponents = new ArrayList<JComponent>();
	
	/**
	 * Create GUI panel for advanced settings specific for either 
	 * conversion direction with the given default values.
	 * 
	 * @param allowDanglingDefault
	 * @param expandMacrosDefault
	 * @param followImports
	 * @param defaultOntologyConfigValue
	 * @param isObo2Owl
	 * @param strictConversion 
	 */
	public GuiAdvancedDirectionSpecificPanel(
			boolean allowDanglingDefault, 
			boolean expandMacrosDefault,
			boolean followImports,
			String defaultOntologyConfigValue, 
			boolean isObo2Owl,
			boolean strictConversion)
	{
		super();
		danglingCheckbox = new JCheckBox("Allow Dangling", allowDanglingDefault);
		followImportsCheckBox = new JCheckBox("Follow Imports", followImports);
		expandMacrosCheckbox = new JCheckBox("Expand OWL Macros", expandMacrosDefault);
		defaultOntologyField = createTextField(defaultOntologyConfigValue);
		formatRDFButton = new JRadioButton("RDF/XML", true);
		formatOWLXMLButton = new JRadioButton("OWL/XML");
		formatManchesterButton = new JRadioButton("Manchester");
		strictCheckBox = new JCheckBox("Strict Conversion", strictConversion);
		
		setLayout(new GridBagLayout());
		GBHelper pos = new GBHelper();
	
		//----------- OBO2OWL specific options
		add(new JLabel("OBO2OWL"), pos);
		addRowGap(this, pos, 10);
		
		// ontology format panel
		createOntologyFormatPanel(pos);
		addRowGap(this, pos, 5);

		// flag for allow dangling
		createDanglingPanel(pos);
		addRowGap(this, pos, 5);

		// flag for allow dangling
		createFollowImportsPanel(pos);
		addRowGap(this, pos, 10);
		
		// flag for expand macros
		createExpandMacros(pos);
		addRowGap(this, pos, 20);
		
		// default ontology
		createDefaultOntologyPanel(pos);
		addRowGap(this, pos, 10);
		
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setSize(new Dimension(-1, 1));
		add(separator, pos.nextRow().width(2).fill().expandW());
		addRowGap(this, pos, 10);
		
		// OWL2OBO label
		add(new JLabel("OWL2OBO"), pos.nextRow());
		
		createStrictPanel(pos);
		addRowGap(this, pos, 5);
		
		setObo2Owl(isObo2Owl);
	}

	/**
	 * Layout for the ontology format buttons.
	 * 
	 * @param pos
	 */
	private void createOntologyFormatPanel(GBHelper pos) {
		JLabel label = new JLabel("OWL Format");
		obo2owlComponents.add(label);
		add(label, pos.nextRow().nextCol());
		ButtonGroup formatGroup = new ButtonGroup();
		formatGroup.add(formatRDFButton);
		formatGroup.add(formatOWLXMLButton);
		formatGroup.add(formatManchesterButton);
		JPanel formatPanel = new JPanel(new GridLayout(1,0));
		formatPanel.add(formatRDFButton);
		formatPanel.add(formatOWLXMLButton);
		formatPanel.add(formatManchesterButton);
		obo2owlComponents.add(formatRDFButton);
		obo2owlComponents.add(formatOWLXMLButton);
		obo2owlComponents.add(formatManchesterButton);
		add(formatPanel, pos.nextRow().nextCol());
	}
	
	/**
	 * Layout for the dangling check box.
	 * 
	 * @param pos
	 */
	private void createDanglingPanel(GBHelper pos) {
		obo2owlComponents.add(danglingCheckbox);
		add(danglingCheckbox, pos.nextRow().nextCol());
	}

	/**
	 * Layout for the follow imports check box.
	 * 
	 * @param pos
	 */
	private void createFollowImportsPanel(GBHelper pos) {
		obo2owlComponents.add(followImportsCheckBox);
		add(followImportsCheckBox, pos.nextRow().nextCol());
	}
	
	/**
	 * Layout for the expand macros check box.
	 * 
	 * @param pos
	 */
	private void createExpandMacros(GBHelper pos) {
		obo2owlComponents.add(expandMacrosCheckbox);
		add(expandMacrosCheckbox, pos.nextRow().nextCol());
	}
	
	/**
	 * Layout for the default ontology field.
	 * 
	 * @param pos
	 */
	private void createDefaultOntologyPanel(GBHelper pos) {
		JLabel label = new JLabel("DefaultOntology");
		obo2owlComponents.add(label);
		add(label,pos.nextRow().nextCol());
		obo2owlComponents.add(defaultOntologyField);
		add(defaultOntologyField, pos.nextRow().nextCol().expandW().fill());
	}
	
	private void createStrictPanel(GBHelper pos) {
		owl2oboComponents.add(strictCheckBox);
		add(strictCheckBox, pos.nextRow().nextCol().expandH());
	}
	
	/**
	 * Set the advanced tab options to reflect 
	 * the selected conversion direction
	 * 
	 * @param isObo2Owl
	 */
	void setObo2Owl(boolean isObo2Owl) {
		//OBO2OWL
		for(JComponent component : obo2owlComponents) {
			component.setEnabled(isObo2Owl);
		}
		//OWL2OBO
		for(JComponent component : owl2oboComponents) {
			component.setEnabled(!isObo2Owl);
		}
	}
}
