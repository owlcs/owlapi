package org.obolibrary.gui;

import static org.obolibrary.gui.GuiTools.addRowGap;
import static org.obolibrary.gui.GuiTools.createTextField;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.obolibrary.gui.GuiTools.GBHelper;
import org.obolibrary.gui.GuiTools.SizedJPanel;

/**
 * GUI elements for the advanced settings of the converter
 */
public class GuiAdvancedPanel extends SizedJPanel {

	// generated
	private static final long serialVersionUID = -1694788715411761694L;
	
	final Frame frame;
	final JTextArea downloadOntologies;
	final JTextArea omitDownloadOntologies;
	final JCheckBox downloadOntologiesCheckBox;
	final JCheckBox omitDownloadOntologiesCheckBox;
	final JTextField ontologyDownloadFolderField;

	/**
	 * Create GUI panel for advanced settings with the given default values.
	 * 
	 * @param frame
	 * @param defaultDownloadOntologies
	 * @param defaultOmitDownloadOntologies
	 * @param defaultBuildDir
	 */
	public GuiAdvancedPanel(Frame frame,
			Collection<String> defaultDownloadOntologies, 
			Collection<String> defaultOmitDownloadOntologies,
			String defaultBuildDir)
	{
		super();
		this.frame = frame;
		
		// create Field for downloadOntologies
		// if values are available set it and activate the field
		downloadOntologies = new JTextArea(0, 1);
		boolean downloadOntologiesCheckBoxIsActive = false;
		if (defaultDownloadOntologies != null && !defaultDownloadOntologies.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (String url : defaultDownloadOntologies) {
				sb.append(url);
				sb.append('\n');
			}
			downloadOntologies.setText(sb.toString());
			downloadOntologiesCheckBoxIsActive = true;
		}
		downloadOntologiesCheckBox = new JCheckBox("Download Ontologies", downloadOntologiesCheckBoxIsActive);
		
		// create Field for omitDownloadOntologies
		// if values are available set it and activate the field
		omitDownloadOntologies = new JTextArea(0, 1);
		boolean omitDownloadOntologiesCheckBoxIsActive = false;
		if (defaultOmitDownloadOntologies != null && !defaultOmitDownloadOntologies.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (String url : defaultOmitDownloadOntologies) {
				sb.append(url);
				sb.append('\n');
			}
			omitDownloadOntologies.setText(sb.toString());
			omitDownloadOntologiesCheckBoxIsActive = true;
		}
		omitDownloadOntologiesCheckBox = new JCheckBox("Omit Download Ontologies", omitDownloadOntologiesCheckBoxIsActive);
		
		ontologyDownloadFolderField = createTextField(defaultBuildDir);
		
		setLayout(new GridBagLayout());
		GBHelper pos = new GBHelper();
	
		// advanced label
		add(new JLabel("ADVANCED"), pos);
		addRowGap(this, pos, 10);
		
		// download ontologies
		createOntologiesPanel(pos, downloadOntologies, downloadOntologiesCheckBox);
		addRowGap(this, pos, 5);
		
		// work dir for downloading ontologies
		createDownloadDirPanel(pos, defaultBuildDir);
		addRowGap(this, pos, 5);
		
		// omit download ontologies
		createOntologiesPanel(pos, omitDownloadOntologies, omitDownloadOntologiesCheckBox);
		addRowGap(this, pos, 5);
		
	}

	/**
	 * Layout and events for the build dir
	 * 
	 * @param pos
	 * @param defaultFolder
	 */
	private void createDownloadDirPanel(GBHelper pos, String defaultFolder) {
		final SelectDialog dialog = SelectDialog.getFolderSelector(frame, defaultFolder, "Work directory choose dialog"); 
		
		JButton selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dialog.show();
				String selected = dialog.getSelectedCanonicalPath();
				if (selected != null) {
					ontologyDownloadFolderField.setText(selected);
				}
			}
		});
		
		add(new JLabel("Download Directory"), pos.nextRow());
		add(ontologyDownloadFolderField, pos.nextCol().expandW().fill());
		add(selectButton,pos.nextCol().indentLeft(10));
	}

	/**
	 * Layout and events for the input of ontologies to be downloaded or omitted.
	 * 
	 * @param pos
	 * @param ontologyList
	 * @param checkBox
	 */
	private void createOntologiesPanel(GBHelper pos, final JTextArea ontologyList, final JCheckBox checkBox) {
		ontologyList.setEditable(checkBox.isSelected());
		ontologyList.setEnabled(checkBox.isSelected());
		final JScrollPane scrollPane = new JScrollPane(ontologyList);
		
		checkBox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				boolean selected = checkBox.isSelected();
				ontologyList.setEditable(selected);
				ontologyList.setEnabled(selected);
			}
		});
		
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(checkBox,pos.nextRow());
		add(scrollPane, pos.nextCol().expandW().expandH().anchorCenter().fill().height(3));
		pos.nextRow();
		pos.nextRow();
	}
}
