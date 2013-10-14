package org.obolibrary.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 * Tools for the layout and handling of the GUI components.
 */
public class GuiTools {
	
	/**
	 * Create a new {@link JTextField} and set text value to a give default.
	 * 
	 * @param defaultValue
	 * @return textField
	 */
	public static JTextField createTextField(String defaultValue) {
		JTextField jTextField = new JTextField();
		if (defaultValue != null) {
			jTextField.setText(defaultValue);
		}
		return jTextField;
	}
	
	/**
	 * Add an additional row with a fixed size for layout purposes. 
	 * Works only with {@link GridBagLayout} and {@link GBHelper}.
	 * 
	 * @param panel The panel to layout
	 * @param pos Layout position information
	 * @param size height of the row in pixel 
	 */
	public static void addRowGap(JPanel panel, GBHelper pos, int size) {
		panel.add(new Gap(size), pos.nextRow());
	}
	
	/**
	 * Retrieve the lines as a list of strings from a given {@link JTextArea}.
	 * 
	 * @param jTextArea
	 * @return list of strings
	 */
	public static List<String> getStrings(JTextArea jTextArea) {
		Document document = jTextArea.getDocument();
		Element paragraph = document.getDefaultRootElement();
	    int contentCount = paragraph.getElementCount();
	    if (contentCount <= 0) {
			return Collections.emptyList();
		}
	    List<String> list = new ArrayList<String>(contentCount);
	    for (int i = 0; i < contentCount; i++) {
	      Element element = paragraph.getElement(i);
	      int rangeStart = element.getStartOffset();
	      int rangeEnd = element.getEndOffset();
	      try {
	    	  String line = document.getText(rangeStart, rangeEnd - rangeStart).trim();
	    	  list.add(line);
			} catch (BadLocationException exception) {
				throw new RuntimeException("Could not parse text in area", exception);
			}
	    }
		return list;
	}
	
	/**
	 * All tabbed panels should have the same preferred size.
	 */
	public static class SizedJPanel extends JPanel {

		// Generated
		private static final long serialVersionUID = -7310308040237600280L;

		public SizedJPanel() {
			super();
			setPreferredSize(new Dimension(600-10, 400-60));
		}
	}

	/**
	 * Helper to provide simple layout objects for gaps.
	 */
	private static class Gap extends JComponent {

		// Generated
		private static final long serialVersionUID = -7493120260432598483L;

		/** Creates filler with minimum size, but expandable infinitely. */
	    private Gap() {
	        Dimension min = new Dimension(0, 0);
	        Dimension max = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	        setMinimumSize(min);
	        setPreferredSize(min);
	        setMaximumSize(max);
	    }
	    
	    /** 
	     * Creates rigid filler with the given size.
	     *  
	     * @param size 
	     */
	    private Gap(int size) {
	        Dimension dim = new Dimension(size, size);
	        setMinimumSize(dim);
	        setPreferredSize(dim);
	        setMaximumSize(dim);
	    }
	}


	/**
	 * Simplify the GridBagConstrains handling.
	 */
	public static class GBHelper extends GridBagConstraints {

		// Generated
		private static final long serialVersionUID = -7193587738437725346L;

		public GBHelper() {
			super();
			gridx = 0;
	        gridy = 0;
	        fill = GridBagConstraints.NONE;
	        ipadx = 2;
			ipady = 2;
	        anchor = GridBagConstraints.NORTHWEST;
		}
		
	    /**
	     *  Moves the helper's cursor to the right one column.
	     *
	     *  @return helper
	     */
	    public GBHelper nextCol() {
	        gridx++;
	        return this;
	    }
	    
	    /** 
	     * Moves the helper's cursor to first col in next row. 
	     * 
	     * @return helper
	     */
	    public GBHelper nextRow() {
	        gridx = 0;
	        gridy++;
	        return this;
	    }
	    
	    /**
	     * Increase indent in the left. Return new instance to minimize side effects.
	     * 
	     * @param pixel
	     * @return helper
	     */
	    public GBHelper indentLeft(int pixel) {
	    	GBHelper duplicate = (GBHelper)this.clone();
	    	duplicate.insets = new Insets(this.insets.top, this.insets.left + pixel, this.insets.bottom, this.insets.right);
	    	return duplicate;
	    }
	    
	    /**
	     * Increase indent in the left. Return new instance to minimize side effects.
	     * 
	     * @param pixel
	     * @return helper
	     */
	    public GBHelper indentRight(int pixel) {
	    	GBHelper duplicate = (GBHelper)this.clone();
	    	duplicate.insets = new Insets(this.insets.top, this.insets.left, this.insets.bottom, this.insets.right + pixel);
	    	return duplicate;
	    }
	    
	    /**
	     * Set the anchor to center. Return new instance to minimize side effects.
	     * 
	     * @return helper
	     */
	    public GBHelper anchorCenter() {
	    	GBHelper duplicate = (GBHelper)this.clone();
	    	duplicate.anchor = CENTER;
	    	return duplicate;
	    }
	    
	    /** 
	     * Expandable Width.  Returns new helper allowing horizontal expansion.
	     * Return new instance to minimize side effects.
	     *
	     * @return helper
	     */
	    public GBHelper expandW() {
	        GBHelper duplicate = (GBHelper)this.clone();
	        duplicate.weightx = 1.0;
	        return duplicate;
	    }
	    
	    /** 
	     * Expandable Height. Returns new helper allowing vertical expansion.
	     * 
	     * Return new instance to minimize side effects.
	     *
	     * @return helper
	     */
	    public GBHelper expandH() {
	        GBHelper duplicate = (GBHelper)this.clone();
	        duplicate.weighty = 1.0;
	        return duplicate;
	    }
	    
	    /** 
	     * Sets the height of the area in terms of rows.
	     * 
	     * Return new instance to minimize side effects.
	     * 
	     * @param rowsHigh 
	     * @return helper
	     */
	    public GBHelper height(int rowsHigh) {
	        GBHelper duplicate = (GBHelper)this.clone();
	        duplicate.gridheight = rowsHigh;
	        return duplicate;
	    }
	    
	    /** 
	     * Sets the width of the area in terms of rows.
	     * 
	     * Return new instance to minimize side effects.
	     * 
	     * @param columnsWide 
	     * @return helper
	     */
	    public GBHelper width(int columnsWide) {
	        GBHelper duplicate = (GBHelper)this.clone();
	        duplicate.gridwidth = columnsWide;
	        return duplicate;
	    }
	    
	    /**
	     * Set the fill to both. The component tries to 
	     * fill the cell in both directions.
	     * 
	     * Return new instance to minimize side effects.
	     * 
	     * @return helper
	     */
	    public GBHelper fill() {
	        GBHelper duplicate = (GBHelper)this.clone();
	        duplicate.fill = GridBagConstraints.BOTH;
	        return duplicate;
	    }
	}
}
