package org.coode.mansyntax.editor;

import org.coode.manchesterowlsyntax.ManchesterOWLSyntax;
import org.coode.manchesterowlsyntax.ManchesterOWLSyntaxClassFrameParser;
import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.expression.OWLExpressionParser;
import org.semanticweb.owl.expression.ParserException;
import org.semanticweb.owl.expression.ShortFormEntityChecker;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.BidirectionalShortFormProvider;
import org.semanticweb.owl.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owl.util.SimpleShortFormProvider;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.*;
import java.util.List;

/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Sep-2007<br><br>
 */
public class OWLExpressionEditor<O extends Object> extends JTextPane implements DocumentListener, WordMatcher {

    private OWLExpressionParser<O> expressionParser;

    private BidirectionalShortFormProvider provider;

    private Map<String, Style> keywordMap;

    private boolean highlightKeywords = true;

    private Style defaultStyle;

    private Style hyperlinkStyle;

    private Set<Character> delims;

    private Timer timer;

    private MouseMotionListener mouseMotionListener;

    private Cursor hyperlinkCursor;

    private HyperlinkListener hyperlinkListener;

    private Set<String> sectionKeywords;

    private Map<String, String> substitutionMap;

    private boolean activateHyperlinksOnMouseOver;

    private Style restrictionKeywordStyle;

    private Style classConstructorKeywordStyle;

    private Style sectionKeywordStyle;


    /**
     * Constructs an expression editor which uses the specified expression parser to check
     * syntax and generate autocompletions and the specified short form provider to obtain
     * references to entities.
     */
    public OWLExpressionEditor(OWLOntologyManager owlOntologyManager, OWLExpressionParser<O> expressionParser,
                               BidirectionalShortFormProvider provider) {
        this.expressionParser = expressionParser;
        this.provider = provider;
        delims = new HashSet<Character>();
        delims.add(' ');
        delims.add('\n');
        delims.add('\r');
        delims.add('\t');
        delims.add('.');
        delims.add(',');
        delims.add('[');
        delims.add(']');
        delims.add('(');
        delims.add(')');
        delims.add('{');
        delims.add('}');

        hyperlinkListener = new NullHyperLinkListener();

        sectionKeywords = new HashSet<String>();
        for (ManchesterOWLSyntax kw : ManchesterOWLSyntax.values()) {
            if (kw.isSectionKeyword()) {
                sectionKeywords.add(kw.toString());
            }
        }

        keywordMap = new HashMap<String, Style>();
        defaultStyle = addStyle("plain", null);
        StyleConstants.setForeground(defaultStyle, Color.BLACK);
        setupDefaultKeywords();
        getDocument().addDocumentListener(this);
        timer = new Timer(1400, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                timer.stop();
                checkForError();
                repaint();
            }
        });
        timer.setRepeats(false);
        mouseMotionListener = new MouseMotionAdapter() {

            public void mouseMoved(MouseEvent e) {
                if (e.isMetaDown() || activateHyperlinksOnMouseOver) {
                    renderHyperlink();
                }
            }
        };
        addMouseMotionListener(mouseMotionListener);
        addMouseListener(new MouseAdapter() {

            public void mouseExited(MouseEvent e) {
                clearHyperlink(true);
            }


            public void mouseReleased(MouseEvent e) {
                if (hyperlinkStart != -1) {
                    navigateToHyperLink();
                }
                else {

                }
            }
        });
        hyperlinkCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                if (!e.isMetaDown()) {
                    clearHyperlink(true);
                }
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,
                                                                        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),
                                                 "GO!");
        getActionMap().put("GO!", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                navigate();
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.ALT_MASK),
                                                 "NAV_DOWN");
        getActionMap().put("NAV_DOWN", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                navigateDown();
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.ALT_MASK), "NAV_UP");
        getActionMap().put("NAV_UP", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                navigateUp();
            }
        });
        substitutionMap = new HashMap<String, String>();
    }


    public void select(int selectionStart, int selectionEnd) {
        super.select(selectionStart, selectionEnd);
    }


    public void setExpressionParser(OWLExpressionParser<O> expressionParser) {
        this.expressionParser = expressionParser;
    }


    public void setupDefaultKeywords() {
        sectionKeywordStyle = addStyle("sectionkeyword", null);
        StyleConstants.setForeground(sectionKeywordStyle, new Color(0, 130, 200));
        StyleConstants.setBold(sectionKeywordStyle, true);
        for (ManchesterOWLSyntax v : ManchesterOWLSyntax.values()) {
            if (v.isSectionKeyword()) {
                keywordMap.put(v.toString() + ":", sectionKeywordStyle);
            }
        }
        restrictionKeywordStyle = addStyle("restrictionKeyword", null);
        StyleConstants.setForeground(restrictionKeywordStyle, Color.MAGENTA.darker());
        StyleConstants.setBold(restrictionKeywordStyle, true);
        keywordMap.put("some", restrictionKeywordStyle);
        keywordMap.put("only", restrictionKeywordStyle);
        keywordMap.put("value", restrictionKeywordStyle);
        keywordMap.put("min", restrictionKeywordStyle);
        keywordMap.put("max", restrictionKeywordStyle);
        keywordMap.put("exactly", restrictionKeywordStyle);

        classConstructorKeywordStyle = addStyle("classConstructorKeywordStyle", null);
        StyleConstants.setForeground(classConstructorKeywordStyle, Color.CYAN.darker());
        StyleConstants.setBold(classConstructorKeywordStyle, true);
        keywordMap.put("and", classConstructorKeywordStyle);
        keywordMap.put("or", classConstructorKeywordStyle);
        keywordMap.put("not", classConstructorKeywordStyle);
        keywordMap.put("that", classConstructorKeywordStyle);
        keywordMap.put("inv", classConstructorKeywordStyle);

        hyperlinkStyle = addStyle("hyperlink", null);
        StyleConstants.setForeground(hyperlinkStyle, Color.BLUE);
        StyleConstants.setUnderline(hyperlinkStyle, true);

        AutoCompleter completer = new AutoCompleter(this, this);
        setFont(new Font("monospaced", Font.PLAIN, 14));
    }

    public void clearKeywords() {
        keywordMap.clear();
    }

    public void addKeyword(String keyword, Style style) {
        StyledDocument doc = getStyledDocument();
        if(doc.getStyle(keyword + "_style") == null) {
            doc.addStyle(keyword + "_style", style);
            keywordMap.put(keyword, style);
        }
    }

    public Style getRestrictionKeywordStyle() {
        return restrictionKeywordStyle;
    }

    public Style getClassConstructorKeywordStyle() {
        return classConstructorKeywordStyle;
    }

    public Style getAxiomKeywordStyle() {
        return sectionKeywordStyle;
    }


    private void navigate() {
        int caretPos = getCaretPosition();
        if (caretPos == -1) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        String word = getWordAt(caretPos);
        OWLEntity entity = provider.getEntity(word);
        if (entity != null) {
            hyperlinkListener.hyperlinkClicked(entity);
        }
        else {
            Toolkit.getDefaultToolkit().beep();
        }
    }


    private void navigateDown() {
        try {
            int caretPos = getCaretPosition();
            String text = getDocument().getText(caretPos, getDocument().getLength() - caretPos - 1);
            int nextSpace = text.indexOf(' ');
            if (nextSpace == -1) {
                return;
            }
            int nextKeywordIndex = Integer.MAX_VALUE;
            for (String kw : sectionKeywords) {
                int index = text.indexOf(kw + ": ", nextSpace);
                if (index != -1 && index < nextKeywordIndex) {
                    nextKeywordIndex = index;
                }
            }
            if (nextKeywordIndex != Integer.MAX_VALUE) {
                setCaretPosition(caretPos + nextKeywordIndex);
            }
            else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    private void navigateUp() {
        try {
            int caretPos = getCaretPosition();
            String text = getDocument().getText(0, caretPos);
            int lastKeywordIndex = -1;
            int nextKeywordIndex = -1;
            for (String kw : sectionKeywords) {
                int index = text.lastIndexOf(kw + ": ");
                if (index > lastKeywordIndex) {
                    nextKeywordIndex = index;
                }
            }
            if (nextKeywordIndex != -1) {
                setCaretPosition(nextKeywordIndex);
            }
            else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public O getParsedObject() throws ParserException {
        if(!SwingUtilities.isEventDispatchThread()) {
            throw new RuntimeException("MUST CALL FROM EDT");
        }
        return expressionParser.parse(getText());
    }


    public boolean isHighlightKeywords() {
        return highlightKeywords;
    }


    public void setHighlightKeywords(boolean highlightKeywords) {
        this.highlightKeywords = highlightKeywords;
    }


    public OWLClass getOWLClass(String name) {
        for (OWLEntity entity : provider.getEntities(name)) {
            if (entity.isOWLClass()) {
                return entity.asOWLClass();
            }
        }
        return null;
    }


    public OWLObjectProperty getOWLObjectProperty(String name) {
        for (OWLEntity entity : provider.getEntities(name)) {
            if (entity.isOWLObjectProperty()) {
                return entity.asOWLObjectProperty();
            }
        }
        return null;
    }


    public OWLDataProperty getOWLDataProperty(String name) {
        for (OWLEntity entity : provider.getEntities(name)) {
            if (entity.isOWLDataProperty()) {
                return entity.asOWLDataProperty();
            }
        }
        return null;
    }


    public OWLIndividual getOWLIndividual(String name) {
        for (OWLEntity entity : provider.getEntities(name)) {
            if (entity.isOWLIndividual()) {
                return entity.asOWLIndividual();
            }
        }
        return null;
    }


    public OWLDataType getOWLDataType(String name) {
        return null;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Document listener stuff
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////


    public void insertUpdate(DocumentEvent e) {
        performHighlighting();
        if (isEditable()) {
            timer.restart();
        }
        errorStartPos = -1;
    }


    public void removeUpdate(DocumentEvent e) {
        performHighlighting();
        if (isEditable()) {
            timer.restart();
        }
        errorStartPos = -1;
    }


    public void changedUpdate(DocumentEvent e) {
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Highlighting
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////


    private void performHighlighting() {
        Thread t = new Thread(new Runnable() {

            public void run() {
                highlight();
            }
        });
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }


    private void highlight() {
        StringTokenizer tokenizer = new StringTokenizer(getText(), " .,[]{}()\t\n\"", true);
        int pos = 0;
        boolean inQuotes = false;
        while (tokenizer.hasMoreTokens()) {
            String tok = tokenizer.nextToken();
            if(tok.equals("\"")) {
                inQuotes = !inQuotes;
            }
            else if(!inQuotes) {
                Style style = getWordStyle(tok);
                getStyledDocument().setCharacterAttributes(pos, tok.length(), style, true);
            }

            pos += tok.length();
        }
    }


    protected Style getWordStyle(String word) {
        Style style = keywordMap.get(word);
        if (style == null) {
            return defaultStyle;
        }
        else {
            return style;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Autocompletion
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////


    public List<Object> match(String start) {
        List<Object> result = new ArrayList<Object>();
        try {
            int caretPos = getCaretPosition();
            String s = getText().substring(0, caretPos) + "*";
            expressionParser.parse(s);
            Toolkit.getDefaultToolkit().beep();
            return new ArrayList<Object>();
        }
        catch (ParserException e) {
            if (e.isClassNameExpected() || e.isObjectPropertyNameExpected() || e.isDataPropertyNameExpected() || e.isIndividualNameExpected()) {

                for (String s : provider.getShortForms()) {
                    if (s.toLowerCase().startsWith(start.toLowerCase())) {
                        OWLEntity ent = provider.getEntity(s);
                        if (ent.isOWLClass() && e.isClassNameExpected()) {
                            result.add(ent);
                        }
                        else if (ent.isOWLObjectProperty() && e.isObjectPropertyNameExpected()) {
                            result.add(ent);
                        }
                        else if (ent.isOWLDataProperty() && e.isDataPropertyNameExpected()) {
                            result.add(ent);
                        }
                        else if (ent.isOWLIndividual() && e.isIndividualNameExpected()) {
                            result.add(ent);
                        }
                    }
                }
            }
            for (String kw : e.getExpectedKeywords()) {
                if (kw.toLowerCase().startsWith(start.toLowerCase())) {
                    result.add(kw);
                }
            }
        }

        return result;
    }


    public String convertToString(Object chosenObject) {
        if (chosenObject instanceof OWLEntity) {
            return provider.getShortForm((OWLEntity) chosenObject);
        }
        return chosenObject.toString();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Hyper links
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    private int hyperlinkStart;

    private int hyperlinkEnd;

    private OWLEntity hyperlinkedEntity;


    public void setHyperlinkListener(HyperlinkListener hyperlinkListener) {
        this.hyperlinkListener = hyperlinkListener;
    }


    private void clearHyperlink(boolean resetCursor) {
        if (resetCursor) {
            setCursor(Cursor.getDefaultCursor());
        }
        if (hyperlinkStart == -1) {
            return;
        }
        getStyledDocument().setCharacterAttributes(hyperlinkStart, hyperlinkEnd - hyperlinkStart, defaultStyle, true);
        hyperlinkStart = -1;
        hyperlinkEnd = -1;
    }


    private void navigateToHyperLink() {
        if (hyperlinkStart != -1) {
            OWLEntity entity = hyperlinkedEntity;
            clearHyperlink(true);
            if (hyperlinkListener != null) {
                hyperlinkListener.hyperlinkClicked(entity);
            }
        }
    }


    private void renderHyperlink() {
        clearHyperlink(false);
        Point mousePos = getMousePosition();
        if (mousePos == null) {
            clearHyperlink(true);
            return;
        }
        int pos = viewToModel(mousePos);
        try {
            if (delims.contains(getDocument().getText(pos, 1).charAt(0))) {
                clearHyperlink(true);
                return;
            }
            int end, start;
            for (start = pos; start > -1; start--) {
                String text = getDocument().getText(start, 1);
                if (delims.contains(text.charAt(0))) {
                    start = start + 1;
                    break;
                }
            }
            for (end = pos + 1; end < getDocument().getLength(); end++) {
                String text = getDocument().getText(end, 1);
                if (delims.contains(text.charAt(0))) {
                    break;
                }
            }
            if (start > -1 && end - start > 1) {
                String candidate = getDocument().getText(start, end - start);
                // Got a word
                if ((hyperlinkedEntity = provider.getEntity(candidate.trim())) != null) {
                    hyperlinkStart = start;
                    hyperlinkEnd = end;
                    getStyledDocument().setCharacterAttributes(hyperlinkStart,
                                                               hyperlinkEnd - hyperlinkStart,
                                                               hyperlinkStyle,
                                                               true);
                    setCursor(hyperlinkCursor);
                }
                else {
                    clearHyperlink(true);
                }
            }
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    public OWLEntity getEntityAt(Point pt) {
        String word = getWordAt(viewToModel(pt));
        if (word != null) {
            return provider.getEntity(word);
        }
        return null;
    }


    public String getWordAt(Point pt) {
        return getWordAt(viewToModel(pt));
    }


    public String getWordAt(int pos) {
        try {
            int end, start = 0;
            for (start = pos; start > -1; start--) {
                String text = getDocument().getText(start, 1);
                if (delims.contains(text.charAt(0))) {
                    break;
                }
            }
            for (end = pos + 1; end < getDocument().getLength(); end++) {
                String text = getDocument().getText(end, 1);
                if (delims.contains(text.charAt(0))) {
                    break;
                }
            }
            if (end - start > 1) {
                return getDocument().getText(start, end - start);
            }
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void setEditable(boolean b) {
        super.setEditable(b);
        setActivateHyperlinksOnMouseOver(!b);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Error highlighting
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    private int errorStartPos = -1;

    private String errorToken = "";


    private void checkForError() {
        if(!SwingUtilities.isEventDispatchThread()) {
            throw new RuntimeException("MUST CALL FROM EDT");
        }
        if(expressionParser == null) {
            return;
        }
        try {
            expressionParser.parse(getText());
            errorStartPos = -1;
            setToolTipText(null);
        }
        catch (ParserException e) {
            setToolTipText(e.getMessage());
            errorStartPos = e.getStartPos();
            errorToken = e.getCurrentToken();
            repaint();
        }
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (errorStartPos != -1) {
            try {
                int drawPos = getText().length() - 1;
                if (drawPos < 0) {
                    drawPos = 0;
                }
                if (errorStartPos < getText().length()) {
                    drawPos = errorStartPos;
                }
                Rectangle r = modelToView(drawPos);
                if (errorToken != null) {
                    Color oldColor = g.getColor();
                    g.setColor(Color.RED);
                    int y = r.y + r.height;
                    int w = g.getFont().getStringBounds(errorToken,
                                                        ((Graphics2D) g).getFontRenderContext()).getBounds().width;


                    g.fillRect(r.x, y, w, 3);
                    g.setColor(oldColor);
                }
            }
            catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean getScrollableTracksViewportWidth() {
        Component parent = getParent();
        ComponentUI ui = getUI();

        return parent != null ? (ui.getPreferredSize(this).width <= parent
                .getSize().width) : true;
    }




    public void setActivateHyperlinksOnMouseOver(boolean b) {
        activateHyperlinksOnMouseOver = b;
    }


    public boolean isActivateHyperlinksOnMouseOver() {
        return activateHyperlinksOnMouseOver;
    }


    private class NullHyperLinkListener implements HyperlinkListener {
        public void hyperlinkClicked(OWLEntity entity) {

        }
    }


    public static void main(String[] args) {
        try {
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            final OWLOntology ont = man.loadOntology(URI.create(
                    "http://www.co-ode.org/ontologies/pizza/2007/02/12/pizza.owl"));
            JFrame f = new JFrame();

            BidirectionalShortFormProvider sfp = new BidirectionalShortFormProviderAdapter(Collections.singleton(ont),
                                                                                           new SimpleShortFormProvider());

            final OWLExpressionParser<Set<OWLAxiom>> parser = new ManchesterOWLSyntaxClassFrameParser(man.getOWLDataFactory(),
                                                                                                      new ShortFormEntityChecker(
                                                                                                              sfp));

            final OWLExpressionEditor<Set<OWLAxiom>> editor = new OWLExpressionEditor<Set<OWLAxiom>>(man, parser, sfp);

            final JTextArea code = new JTextArea();
            code.setFont(new Font("monospaced", Font.PLAIN, 12));
            f.setContentPane(new JScrollPane(editor));
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(new Dimension(500, 400));
            f.setVisible(true);
        }
        catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }
}
