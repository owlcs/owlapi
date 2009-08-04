package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;

import java.net.URI;
import java.util.*;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 02-Jan-2007<br><br>
 */
public class RDFXMLOntologyFormat extends RDFOntologyFormat {

    private ParserMetaData parserMetaData;

    public RDFXMLOntologyFormat() {
        parserMetaData = new ParserMetaData(-1, new HashSet<Triple>(0));
    }

    public ParserMetaData getParserMetaData() {
        return parserMetaData;
    }

    public void setParserMetaData(ParserMetaData parserMetaData) {
        this.parserMetaData = parserMetaData;
    }

    public String toString() {
        return "RDF/XML";
    }

    public interface Triple {

        public IRI getSubject();

        public IRI getPredicate();

        public OWLObject getObject();
    }

    public class ResourceTriple implements Triple {

        private IRI subject;

        private IRI predicate;

        private IRI object;

        public ResourceTriple(IRI subject, IRI predicate, IRI object) {
            this.subject = subject;
            this.predicate = predicate;
            this.object = object;
        }

        public IRI getSubject() {
            return subject;
        }

        public IRI getPredicate() {
            return predicate;
        }

        public IRI getObject() {
            return object;
        }
    }

    public class LiteralTriple implements Triple {

        private IRI subject;

        private IRI predicate;

        private OWLLiteral object;

        public LiteralTriple(IRI subject, IRI predicate, OWLLiteral object) {
            this.subject = subject;
            this.predicate = predicate;
            this.object = object;
        }

        public IRI getSubject() {
            return subject;
        }

        public IRI getPredicate() {
            return predicate;
        }

        public OWLLiteral getObject() {
            return object;
        }
    }

    public class ParserMetaData {

        private int tripleCount;

        private Set<Triple> unconsumedTriples = new HashSet<Triple>();

        public ParserMetaData(int tripleCount, Set<Triple> unconsumedTriples) {
            this.tripleCount = tripleCount;
            this.unconsumedTriples = new HashSet<Triple>(unconsumedTriples);
        }

        public int getNumberOfTripleParsed() {
            return tripleCount;
        }

        public Set<Triple> getUnconsumedTriples() {
            return Collections.unmodifiableSet(unconsumedTriples);
        }
    }

}
