package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.io.RDFOntologyFormat;

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

    private Set<IRI> schemaImportsIRIs;


    public TPImportsHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_IMPORTS.getIRI());
        schemaImportsIRIs = new HashSet<IRI>();
        for (Namespaces n : Namespaces.values()) {
            String ns = n.toString();
            schemaImportsIRIs.add(IRI.create(ns.substring(0, ns.length() - 1)));
        }
        schemaImportsIRIs.add(IRI.create("http://www.daml.org/rules/proposal/swrlb.owlapi"));
        schemaImportsIRIs.add(IRI.create("http://www.daml.org/rules/proposal/swrl.owlapi"));
    }


    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return true;
    }

    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {

       // NOTE:
       // For backwards compatibility with OWL 1 DL, if G contains an owl:imports triple pointing to an RDF
       // document encoding an RDF graph G' where G' does not have an ontology header, this owl:imports triple
       // is interpreted as an include rather than an import — that is, the triples of G' are included into G and are
       // not parsed into a separate ontology.
       // WHAT A LOAD OF RUBBISH!!



        consumeTriple(subject, predicate, object);
        getConsumer().addOntology(subject);
        getConsumer().addOntology(object);
        if (!schemaImportsIRIs.contains(object)) {
            OWLImportsDeclaration importsDeclaration = getDataFactory().getOWLImportsDeclaration(object);
            getConsumer().addImport(importsDeclaration);
            OWLOntologyManager man = getConsumer().getOWLOntologyManager();
            man.makeLoadImportRequest(importsDeclaration);



            OWLOntology importedOntology = man.getImportedOntology(importsDeclaration);
            if (importedOntology != null) {
                OWLOntologyFormat importedOntologyFormat = man.getOntologyFormat(importedOntology);
                if(importedOntologyFormat instanceof RDFOntologyFormat) {
                    if(importedOntology.isAnonymous()) {
                        // We should have just included the triples rather than imported them. So,
                        // we remove the imports statement, add the axioms from the imported ontology to
                        // out importing ontology and remove the imported ontology.
                        // WHO EVER THOUGHT THAT THIS WAS A GOOD IDEA?
                            man.applyChange(new RemoveImport(getConsumer().getOntology(), importsDeclaration));

                            for(OWLImportsDeclaration decl : importedOntology.getImportsDeclarations()) {
                                man.applyChange(new AddImport(getConsumer().getOntology(), decl));
                            }
                            for(OWLAnnotation anno : importedOntology.getAnnotations()) {
                                man.applyChange(new AddOntologyAnnotation(getConsumer().getOntology(), anno));
                            }
                            for(OWLAxiom ax : importedOntology.getAxioms()) {
                                getConsumer().addAxiom(ax);
                            }
                            man.removeOntology(importedOntology);

                    }
                }
            }

            getConsumer().importsClosureChanged();

        }

    }
}
