package org.coode.string;

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
 * Date: 16-Apr-2008<br><br>
 */
public class EscapeUtils {

    /**
     * Escapes quotes and backslashes in a string.  Double
     * quotes are replaced with a backslash followed by a double
     * quote, and backslashes are replaced with a double backslash.
     * @param s The string to be escaped
     * @return The escaped string.
     */
    public static String escapeString(String s) {
        // We replace double quotes with a back slash followed
        // by a double quote.  We replace backslashes with a double
        // backslash
        if (s.indexOf('\"') == -1 && s.indexOf('\\') == -1) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 20);
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '\\') {
                sb.append('\\');
                sb.append('\\');
            }
            else if (ch == '\"') {
                sb.append('\\');
                sb.append('\"');
            }
            else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }


    public static String unescapeString(String s) {
        if (s.indexOf('\\') == -1) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '\\') {
                int j = i + 1;
                if (j < s.length()) {
                    char escCh = s.charAt(j);
                    if (escCh == '\\' || escCh == '\"') {
                        i++;
                        sb.append(escCh);
                    }
                }
                else {
                    sb.append('\\');
                }
            }
            else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }


    public static String escapeXML(String s) {
        // double quote -- quot
        // ampersand    -- amp
        // less than    -- lt
        // greater than -- gt
        // apostrophe   -- apos
        StringBuilder sb = new StringBuilder(s.length() * 2);
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '<') {
                sb.append("&lt;");
            }
            else if (ch == '>') {
                sb.append("&gt;");
            }
            else if (ch == '\"') {
                sb.append("&quot;");
            }
            else if (ch == '&') {
                sb.append("&amp;");
            }
            else if (ch == '\'') {
                sb.append("&#");
                sb.append(Integer.toString(ch, 10));
                sb.append(';');
            }
            else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }
}
