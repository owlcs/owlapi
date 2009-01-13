package uk.ac.manchester.cs.owl.turtle.parser;

import org.coode.owl.rdfxml.parser.AnonymousNodeChecker;
import org.coode.owl.rdfxml.parser.OWLRDFConsumer;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.model.OWLRuntimeException;
import org.xml.sax.SAXException;

import java.net.URI;
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
public class OWLRDFConsumerAdapter extends OWLRDFConsumer implements TripleHandler {


    public OWLRDFConsumerAdapter(OWLOntologyManager owlOntologyManager, OWLOntology ontology, AnonymousNodeChecker checker) {
        super(owlOntologyManager, ontology, checker);
    }


    public void handlePrefixDirective(String prefix, String namespace) {
        
    }


    public void handleBaseDirective(String base) {
        setXMLBase(base);
    }


    public void handleComment(String comment) {

    }


    public void handleTriple(URI subject, URI predicate, URI object) {
        try {
            statementWithResourceValue(subject.toString(), predicate.toString(), object.toString());
        }
        catch (SAXException e) {
            throw new OWLRuntimeException(e);
        }
    }


    public void handleTriple(URI subject, URI predicate, String object) {
        try {
            statementWithLiteralValue(subject.toString(), predicate.toString(), object, null, null);
        }
        catch (SAXException e) {
            throw new OWLRuntimeException(e);
        }
    }


    public void handleTriple(URI subject, URI predicate, String object, String lang) {
        try {
            statementWithLiteralValue(subject.toString(), predicate.toString(), object, lang, null);
        }
        catch (SAXException e) {
            throw new OWLRuntimeException(e);
        }
    }


    public void handleTriple(URI subject, URI predicate, String object, URI datatype) {
        try {
            statementWithLiteralValue(subject.toString(), predicate.toString(), object, null, datatype.toString());
        }
        catch (SAXException e) {
            throw new OWLRuntimeException(e);
        }
    }


    public void handleEnd() {
        try {
            endModel();
        }
        catch (SAXException e) {
            throw new OWLRuntimeException(e);
        }
    }
}


