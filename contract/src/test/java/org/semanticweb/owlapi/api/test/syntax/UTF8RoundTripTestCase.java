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
package org.semanticweb.owlapi.api.test.syntax;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class UTF8RoundTripTestCase {

    @Test
    public void testRoundTrip() throws Exception {
        String NS = "http://protege.org/ontologies/UTF8RoundTrip.owl";
        OWLClass C = Class(IRI(NS + "#C"));
        /*
         * The two unicode characters entered here are valid and can be found in
         * the code chart http://www.unicode.org/charts/PDF/U4E00.pdf. It has
         * been said that they are chinese and they do look the part. In UTF-8
         * these characters are encoded as \u8655 --> \350\231\225 \u65b9 -->
         * \346\226\271 where the right hand side is in octal. (I chose octal
         * because this is how emacs represents it with find-file-literally).
         */
        String CHINESE = "Rx\u8655\u65b9";
        System.setProperty("file.encoding", "UTF-8");
        OWLOntology ontology = createOriginalOntology(NS, C, CHINESE);
        checkOntology(ontology, C, CHINESE);
        OWLOntology newOntology = roundTrip(ontology);
        checkOntology(newOntology, C, CHINESE);
    }

    private static OWLOntology createOriginalOntology(String NS, OWLClass C,
            String CHINESE) throws OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology(IRI(NS));
        OWLAxiom annotationAxiom = AnnotationAssertion(RDFSLabel(), C.getIRI(),
                Literal(CHINESE));
        manager.addAxiom(ontology, annotationAxiom);
        return ontology;
    }

    private static boolean checkOntology(OWLOntology ontology, OWLClass C,
            String CHINESE) {
        for (OWLAnnotation annotation : C.getAnnotations(ontology)) {
            String value = ((OWLLiteral) annotation.getValue()).getLiteral();
            return CHINESE.equals(value);
        }
        return false;
    }

    private static OWLOntology roundTrip(OWLOntology ontology)
            throws OWLOntologyStorageException, OWLOntologyCreationException {
        OWLOntologyManager oldManager = ontology.getOWLOntologyManager();
        StringDocumentTarget target = new StringDocumentTarget();
        oldManager.saveOntology(ontology, new RDFXMLOntologyFormat(), target);
        OWLOntologyManager newManager = Factory.getManager();
        OWLOntology newOntology;
        newOntology = newManager
                .loadOntologyFromOntologyDocument(new StringDocumentSource(
                        target.toString()));
        return newOntology;
    }
}
