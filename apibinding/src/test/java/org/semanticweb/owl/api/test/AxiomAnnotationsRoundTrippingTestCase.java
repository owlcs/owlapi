package org.semanticweb.owl.api.test;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import org.semanticweb.owl.io.StringOutputTarget;

import java.util.Set;
import java.util.HashSet;
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
 * Date: 10-May-2008<br><br>
 */
public class AxiomAnnotationsRoundTrippingTestCase extends AbstractRoundTrippingTest {

    protected OWLOntology createOntology() {
        OWLOntology ont = getOWLOntology("OntA");
        OWLDataFactory factory = getFactory();

        OWLAnnotationProperty prop = factory.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getURI());

        Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
        for(int i = 0; i < 2; i++) {
            Set<OWLAnnotation> innerAnnotations = new HashSet<OWLAnnotation>();
            for(int j = 0 ; j < 2; j++) {
                OWLLiteral lit = getFactory().getOWLTypedLiteral("Annotation " + (j + 1) * 10);
                innerAnnotations.add(getFactory().getOWLAnnotation(getFactory().getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getURI()),
                                                                lit));
            }
            OWLLiteral lit = getFactory().getOWLTypedLiteral("Annotation " + (i + 1));
            annotations.add(getFactory().getOWLAnnotation(getFactory().getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getURI()),
                                                       lit,
                                                       innerAnnotations));
        }

        OWLEntity entity = factory.getOWLNamedIndividual(URI.create("http://www.another.com/ont#peter"));
//        addAxiom(ont, factory.getOWLDeclarationAxiom(entity));
        OWLAnnotationAssertionAxiom ax = factory.getOWLAnnotationAssertionAxiom(entity.getIRI(),
                factory.getOWLAnnotation(prop,
                                      getFactory().getRDFTextLiteral("X", "en"), annotations));
        addAxiom(ont, ax);

        return ont;
    }

    protected void handleSaved(StringOutputTarget target, OWLOntologyFormat format) {
        System.out.println(target);
    }

}
