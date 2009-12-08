package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
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
 * Date: 21-Feb-2007<br><br>
 */
public class TPDeclaredAsHandler extends TriplePredicateHandler {

    public TPDeclaredAsHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_DECLARED_AS.getIRI());
    }


    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return true;
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (object.equals(OWLRDFVocabulary.OWL_CLASS.getIRI())) {
            addAxiom(getDataFactory().getOWLDeclarationAxiom(getDataFactory().getOWLClass(subject), getPendingAnnotations()));
        } else if (object.equals(OWLRDFVocabulary.OWL_OBJECT_PROPERTY.getIRI())) {
            addAxiom(getDataFactory().getOWLDeclarationAxiom(getDataFactory().getOWLObjectProperty(subject), getPendingAnnotations()));
        } else if (object.equals(OWLRDFVocabulary.OWL_DATA_PROPERTY.getIRI())) {
            addAxiom(getDataFactory().getOWLDeclarationAxiom(getDataFactory().getOWLDataProperty(subject), getPendingAnnotations()));
        } else if (object.equals(OWLRDFVocabulary.OWL_DATATYPE.getIRI())) {
            addAxiom(getDataFactory().getOWLDeclarationAxiom(getDataFactory().getOWLDatatype(subject), getPendingAnnotations()));
        }
    }
}
