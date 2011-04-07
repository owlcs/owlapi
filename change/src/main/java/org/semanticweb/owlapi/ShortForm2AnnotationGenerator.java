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
 * Copyright 2011, The University of Manchester
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

package org.semanticweb.owlapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.ImportsStructureEntitySorter;
import org.semanticweb.owlapi.util.ShortFormProvider;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Feb-2008<br><br>
 */
public class ShortForm2AnnotationGenerator implements OWLCompositeOntologyChange {

    // The annotation URI to be used.
    private IRI annotationIRI;

    // An optional language tag to be used - could be null;
    private String languageTag;

    private OWLOntologyManager ontologyManager;

    private ShortFormProvider shortFormProvider;

    private OWLOntology ontology;


    public ShortForm2AnnotationGenerator(OWLOntologyManager ontologyManager, OWLOntology ontology,
                                         ShortFormProvider shortFormProvider, IRI annotationIRI, String languageTag) {
        this.ontologyManager = ontologyManager;
        this.shortFormProvider = shortFormProvider;
        this.annotationIRI = annotationIRI;
        this.languageTag = languageTag;
        this.ontology = ontology;
    }


    public ShortForm2AnnotationGenerator(OWLOntologyManager ontologyManager, OWLOntology ontology,
                                         ShortFormProvider shortFormProvider, IRI annotationIRI) {
        this(ontologyManager, ontology, shortFormProvider, annotationIRI, null);
    }


    public List<OWLOntologyChange> getChanges() {
        ImportsStructureEntitySorter sorter = new ImportsStructureEntitySorter(ontology, ontologyManager);
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        Map<OWLOntology, Set<OWLEntity>> ontology2EntityMap = sorter.getObjects();
        for (OWLOntology ont : ontology2EntityMap.keySet()) {
            for (OWLEntity ent : ontology2EntityMap.get(ont)) {
                String shortForm = shortFormProvider.getShortForm(ent);
                OWLLiteral con;
                if (languageTag != null) {
                    con = ontologyManager.getOWLDataFactory().getOWLLiteral(shortForm, languageTag);
                } else {
                    con = ontologyManager.getOWLDataFactory().getOWLLiteral(shortForm);
                }
                if (ontology.containsEntityInSignature(ent)) {
                    OWLOntologyChange chg = new AddAxiom(ont,
                            ontologyManager.getOWLDataFactory().getOWLAnnotationAssertionAxiom(ontologyManager.getOWLDataFactory().getOWLAnnotationProperty(annotationIRI), ent.getIRI(), con));
                    changes.add(chg);
                }
            }
        }
        return changes;
    }
}

