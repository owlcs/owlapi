package org.coode.mansyntax.editor;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.ShortFormProvider;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class AutoCompleter {

    private JTextComponent textComponent;

    private WordMatcher matcher;

    private JWindow window;

    private JList list;

//    private String lastAutoInsert;

    private KeyListener listener = new KeyAdapter() {

        public void keyPressed(KeyEvent e) {
            if (isAutoCompletionKeyEvent(e)) {
                e.consume();
                doAutoComplete();
            }
            else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cancelAutoComplete();
            }
            else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (isAutcompleteInProgress()) {
                    e.consume();
                    finishAutoComplete();
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (isAutcompleteInProgress()) {
                    cancelAutoComplete();
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (isAutcompleteInProgress()) {
                    e.consume();
                    handleUp();
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (isAutcompleteInProgress()) {
                    e.consume();
                    handleDown();
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                cancelAutoComplete();
            }
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                cancelAutoComplete();
            }
            else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                cancelAutoComplete();
            }
        }


        public void keyReleased(KeyEvent e) {
            if (isAutcompleteInProgress()) {
                if (e.getKeyCode() != KeyEvent.VK_UP && e.getKeyCode() != KeyEvent.VK_DOWN && e.getKeyCode() != KeyEvent.VK_ESCAPE && e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_SPACE && e.getKeyCode() != KeyEvent.VK_CONTROL && e.getKeyCode() != KeyEvent.VK_SHIFT) {
                    doAutoComplete();
                }
            }
        }
    };


    private boolean isAutcompleteInProgress() {
        return window != null && window.isVisible();
    }


    private FocusListener focusListener = new FocusAdapter() {

        public void focusLost(FocusEvent e) {
            cancelAutoComplete();
        }
    };


    private MouseListener mouseListener = new MouseAdapter() {

        public void mousePressed(MouseEvent e) {
            cancelAutoComplete();
        }
    };

    private Set<Character> delims;

    private ShortFormProvider shortFormProvider;

    public AutoCompleter(JTextComponent textComponent, WordMatcher matcher, ShortFormProvider shortFormProvider) {
        this.textComponent = textComponent;
        this.matcher = matcher;
        textComponent.addKeyListener(listener);
        delims = new HashSet<Character>();
        delims.add(' ');
        delims.add('\n');
        delims.add('\t');
        delims.add('.');
        delims.add(',');
        delims.add('[');
        delims.add(']');
        delims.add('(');
        delims.add(')');
        delims.add('{');
        delims.add('}');
        this.shortFormProvider = shortFormProvider;
    }


    private void createPopupWindow() {
        list = new JList();
        window = new JWindow((Frame) SwingUtilities.getAncestorOfClass(Frame.class, textComponent));
        JScrollPane sp = new JScrollPane(list);
        sp.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        window.setContentPane(sp);
        window.setAlwaysOnTop(true);
        window.setFocusableWindowState(false);
        textComponent.addFocusListener(focusListener);
        textComponent.addMouseListener(mouseListener);

        list.addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    finishAutoComplete();
                }
            }
        });
        list.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof OWLEntity) {
                    label.setText(shortFormProvider.getShortForm((OWLEntity) value));
                }
                return label;
            }
        });
    }


    public String getWordAfterCaret() {
        int caretPos = textComponent.getCaretPosition();
        String text = textComponent.getText();
        int i;
        for (i = caretPos; i < text.length(); i++) {
            if (delims.contains(text.charAt(i))) {
                break;
            }
        }
        if (i == caretPos) {
            return "";
        }
        else {
            return text.substring(caretPos, i);
        }
    }


    public String getWordBeforeCaret() {
        try {
            int caretPos = textComponent.getCaretPosition();
            if (caretPos == -1) {
                return "";
            }
            int start = getWordStartIndex();
            return textComponent.getDocument().getText(start, caretPos - start);
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
        return "";
    }


    public int getWordStartIndex() {
        try {
            int caretPos = textComponent.getCaretPosition();
            if (caretPos == -1) {
                return -1;
            }
            int end = caretPos - 1;
            int start = 0;
            for (int i = end; i > -1; i--) {
                char ch = textComponent.getDocument().getText(i, 1).charAt(0);
                if (delims.contains(ch)) {
                    start = i + 1;
                    break;
                }
            }
            return start;
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
        return -1;
    }


    protected boolean isAutoCompletionKeyEvent(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_SPACE && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0;
    }


    protected void hideAutoCompleter() {
        window.setVisible(false);
    }


    private void handleDown() {
        try {
            int selIndex = list.getSelectedIndex();
            selIndex++;
            if (selIndex > list.getModel().getSize() - 1) {
                selIndex = 0;
            }
            list.setSelectedIndex(selIndex);
            int selStart = textComponent.getSelectionStart();
            int selEnd = textComponent.getSelectionEnd();
            textComponent.getDocument().remove(selStart, selEnd - selStart);
            textComponent.setCaretPosition(selStart);
            insertTemp();
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    private void handleUp() {
        try {
            int selIndex = list.getSelectedIndex();
            selIndex--;
            if (selIndex < 0) {
                selIndex = list.getModel().getSize() - 1;
            }
            list.setSelectedIndex(selIndex);
            int selStart = textComponent.getSelectionStart();
            int selEnd = textComponent.getSelectionEnd();
            textComponent.getDocument().remove(selStart, selEnd - selStart);
            textComponent.setCaretPosition(selStart);
            insertTemp();
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    private void insertTemp() {
        Object selObj = list.getSelectedValue();
        if (selObj == null) {
            return;
        }
        String chosen = matcher.convertToString(selObj);
        int caretPos = textComponent.getCaretPosition();
        String word = getWordBeforeCaret();
        String ins = chosen.substring(word.length(), chosen.length());
        try {
//            textComponent.getDocument().remove(textComponent.getCaretPosition(), getWordAfterCaret().length());
            textComponent.getDocument().insertString(caretPos, ins, null);
            textComponent.setCaretPosition(caretPos + ins.length());
            textComponent.select(caretPos, caretPos + ins.length());
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    protected void doAutoComplete() {

        try {
            if (window == null) {
                createPopupWindow();
            }
            String start = getWordBeforeCaret();
            String selText = textComponent.getSelectedText();
            List<Object> matches = matcher.match(start);
            if (!matches.isEmpty() || window.isVisible()) {
                list.setListData(matches.toArray());
                // We use a slightly complicated technique to select the
                // first possible autocompletion.  The reason for this is
                // so that the editor will work with the various screen
                // readers (e.g. JAWS)
                try {
                    Robot robot = new Robot();
                    robot.keyPress(KeyEvent.VK_DOWN);
                }
                catch (AWTException e) {
                    e.printStackTrace();
                }
                int pos = getWordStartIndex();
                Rectangle caretRect = textComponent.modelToView(pos);
                Point pt = new Point(caretRect.x, caretRect.y + caretRect.height);
                SwingUtilities.convertPointToScreen(pt, textComponent);
                if (!window.isVisible()) {
                    window.setLocation(pt);
                    window.setSize(300, 200);
                    window.setVisible(true);
                }
                insertTemp();
            }
            else {
                Toolkit.getDefaultToolkit().beep();
            }
//            else if (matches.size() == 1) {
//                String ins = matcher.convertToString(matches.iterator().next());
//                insertWordCompletion(ins);
//            }
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    protected void finishAutoComplete() {
        Object o = list.getSelectedValue();
        String ins = matcher.convertToString(o);
        insertWordCompletion(ins);
        hideAutoCompleter();
    }


    protected void cancelAutoComplete() {
//        if(lastAutoInsert.length() > 0) {
//            try {
//                textComponent.getDocument().remove(textComponent.getCaretPosition(), lastAutoInsert.length());
//            }
//            catch (BadLocationException e) {
//                e.printStackTrace();
//            }
//        }
        if (isAutcompleteInProgress()) {
            hideAutoCompleter();
        }
//        lastAutoInsert = "";
    }


    protected void insertWordCompletion(String ins) {
        try {
            String start = getWordBeforeCaret();
            if (start == null) {
                textComponent.getDocument().insertString(textComponent.getCaretPosition(), ins, null);
            }
            String toInsert = ins.substring(start.length(), ins.length());
            String wordAfterCaret = getWordAfterCaret();
            int endCaretPos = textComponent.getCaretPosition() + toInsert.length();
            if (wordAfterCaret.length() != 0) {
                textComponent.getDocument().remove(textComponent.getCaretPosition(), getWordAfterCaret().length());
                textComponent.getDocument().insertString(textComponent.getCaretPosition(), toInsert, null);
            }
            else {
                textComponent.getDocument().insertString(textComponent.getCaretPosition(), toInsert, null);
            }
            textComponent.setCaretPosition(endCaretPos);
//            lastAutoInsert = "";

        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    public void dispose() {
        textComponent.removeKeyListener(listener);
        textComponent.removeMouseListener(mouseListener);
        textComponent.removeFocusListener(focusListener);
    }
}
