package org.coode.owl.rdfxml.parser;

import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
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
 * Date: 10-Dec-2006<br><br>
 */
public class TPImportsHandler extends TriplePredicateHandler {

    private Set<URI> schemaImportsURIs;


    public TPImportsHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_IMPORTS.getURI());
        schemaImportsURIs = new HashSet<URI>();
        for (Namespaces n : Namespaces.values()) {
            String ns = n.toString();
            schemaImportsURIs.add(URI.create(ns.substring(0, ns.length() - 1)));
        }
        schemaImportsURIs.add(URI.create("http://www.daml.org/rules/proposal/swrlb.owlapi"));
        schemaImportsURIs.add(URI.create("http://www.daml.org/rules/proposal/swrl.owlapi"));
    }


    public boolean canHandleStreaming(URI subject, URI predicate, URI object) {
        return true;
    }


    public void handleTriple(URI subject, URI predicate, URI object) {
        consumeTriple(subject, predicate, object);
        getConsumer().addOntology(subject);
        getConsumer().addOntology(object);
        if (!schemaImportsURIs.contains(object)) {
            OWLImportsDeclaration importsDeclaration = getDataFactory().getOWLImportsDeclaration(object);
            getConsumer().addImport(importsDeclaration);
            OWLOntologyManager man = getConsumer().getOWLOntologyManager();
            try {
                man.makeLoadImportRequest(importsDeclaration);
            }
            catch (OWLOntologyCreationException e) {
                OWLRDFConsumer.logger.severe(e.getMessage());
            }
            getConsumer().importsClosureChanged();
        }
        // We need to think about what we should do when the subject of the imports statement doesn't
        // match the ontology being parsed
//        OWLOntology importing = getConsumer().getOWLOntologyManager().getOntology(subject);

    }
}
