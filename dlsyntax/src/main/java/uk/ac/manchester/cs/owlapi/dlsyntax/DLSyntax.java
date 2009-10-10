package uk.ac.manchester.cs.owlapi.dlsyntax;
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
 * Date: 10-Feb-2008<br><br>
 */
public enum DLSyntax {

    SUBCLASS("\u2291"),

    EQUIVALENT_TO("\u2261"),

    NOT("\u00AC"),

    DISJOINT_WITH(SUBCLASS + " " + NOT),

    EXISTS("\u2203"),

    FORALL("\u2200"),

    IN("\u2208"),

    MIN("\u2265"),

    EQUAL("="),

    NOT_EQUAL("\u2260"),

    MAX("\u2264"),

    INVERSE("\u207B"),  // Superscript minus

    AND("\u2293"),

    TOP("\u22A4"),

    BOTTOM("\u22A5"),

    OR("\u2294"),

    COMP("\u2218"),

    WEDGE("\u22C0"),

    IMPLIES("\u2190"),

    COMMA(","),

    SELF("self");




    private String unicodeSymbol;


    DLSyntax(String unicode) {
        this.unicodeSymbol = unicode;
    }


    public String toString() {
        return unicodeSymbol;
    }
}
