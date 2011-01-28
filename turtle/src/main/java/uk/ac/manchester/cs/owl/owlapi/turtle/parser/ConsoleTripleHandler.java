package uk.ac.manchester.cs.owl.owlapi.turtle.parser;

import org.semanticweb.owlapi.model.IRI;

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
 * Date: 24-Feb-2008<br><br>
 */
public class ConsoleTripleHandler implements TripleHandler {


    public void handleTriple(IRI subject, IRI predicate, IRI object) {
        System.out.println(subject + " --> " + predicate + " --> " + object);
    }


    public void handleTriple(IRI subject, IRI predicate, String object) {
        System.out.println(subject + " --> " + predicate + " --> " + object);
    }


    public void handleTriple(IRI subject, IRI predicate, String object, String lang) {
        System.out.println(subject + " --> " + predicate + " --> " + object + "@" + lang);
    }


    public void handleTriple(IRI subject, IRI predicate, String object, IRI datatype) {
        System.out.println(subject + " --> " + predicate + " --> " + object + "^^" + datatype);
    }


    public void handlePrefixDirective(String prefixName, String prefix) {
        System.out.println("PREFIX: " + prefixName + " -> " + prefix);
    }


    public void handleBaseDirective(String base) {
        System.out.println("BASE: " + base);
    }


    public void handleComment(String comment) {
        System.out.println("COMMENT: " + comment);
    }


    public void handleEnd() {
        System.out.println("END");
    }
}
