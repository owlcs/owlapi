/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-May-2008<br><br>
 */
public class AxiomAnnotationsRoundTrippingTestCase extends AbstractRoundTrippingTest {

    @Override
	protected OWLOntology createOntology() {
        OWLOntology ont = getOWLOntology("OntA");
        OWLDataFactory factory = getFactory();

        OWLAnnotationProperty prop = factory.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());
        addAxiom(ont, factory.getOWLDeclarationAxiom(prop));

        Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
        for (int i = 0; i < 2; i++) {
            OWLLiteral lit = getFactory().getOWLLiteral("Annotation " + (i + 1));
            annotations.add(getFactory().getOWLAnnotation(getFactory().getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI()), lit));
        }

        OWLEntity entity = factory.getOWLNamedIndividual(IRI.create("http://www.another.com/ont#peter"));
        addAxiom(ont, getFactory().getOWLDeclarationAxiom(entity));
        OWLAnnotationAssertionAxiom ax = factory.getOWLAnnotationAssertionAxiom(prop, entity.getIRI(), getFactory().getOWLLiteral("X", "en"), annotations);
        addAxiom(ont, ax);

        return ont;
    }

    @Override  @SuppressWarnings("unused")
	protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println(target);
    }

}
