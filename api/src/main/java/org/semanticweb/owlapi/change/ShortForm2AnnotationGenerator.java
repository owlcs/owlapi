/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.change;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.ImportsStructureEntitySorter;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class ShortForm2AnnotationGenerator extends
        AbstractCompositeOntologyChange {

    private static final long serialVersionUID = 40000L;

    /**
     * Instantiates a new short form2 annotation generator.
     * 
     * @param df
     *        data factory
     * @param ontologyManager
     *        the ontology manager
     * @param ontology
     *        the ontology
     * @param shortFormProvider
     *        the short form provider
     * @param annotationIRI
     *        The annotation IRI to be used
     * @param languageTag
     *        language
     */
    public ShortForm2AnnotationGenerator(@Nonnull OWLDataFactory df,
            @Nonnull OWLOntologyManager ontologyManager,
            @Nonnull OWLOntology ontology,
            @Nonnull ShortFormProvider shortFormProvider,
            @Nonnull IRI annotationIRI, @Nullable String languageTag) {
        super(df);
        generateChanges(
                checkNotNull(ontologyManager, "ontologyManager cannot be null"),
                checkNotNull(ontology, "ontology cannot be null"),
                checkNotNull(shortFormProvider,
                        "shortFormProvider cannot be null"),
                checkNotNull(annotationIRI, "annotationIRI cannot be null"),
                languageTag);
    }

    /**
     * Instantiates a new short form2 annotation generator.
     * 
     * @param df
     *        data factory
     * @param ontologyManager
     *        the ontology manager
     * @param ontology
     *        the ontology
     * @param shortFormProvider
     *        the short form provider
     * @param annotationIRI
     *        iri for annotation property
     */
    public ShortForm2AnnotationGenerator(@Nonnull OWLDataFactory df,
            @Nonnull OWLOntologyManager ontologyManager,
            @Nonnull OWLOntology ontology,
            @Nonnull ShortFormProvider shortFormProvider,
            @Nonnull IRI annotationIRI) {
        this(df, ontologyManager, ontology, shortFormProvider, annotationIRI,
                null);
    }

    private void generateChanges(OWLOntologyManager ontologyManager,
            @Nonnull OWLOntology ontology,
            @Nonnull ShortFormProvider shortFormProvider,
            @Nonnull IRI annotationIRI, @Nullable String languageTag) {
        ImportsStructureEntitySorter sorter = new ImportsStructureEntitySorter(
                ontology);
        Map<OWLOntology, Set<OWLEntity>> ontology2EntityMap = sorter
                .getObjects();
        for (OWLOntology ont : ontology2EntityMap.keySet()) {
            assert ont != null;
            for (OWLEntity ent : ontology2EntityMap.get(ont)) {
                assert ent != null;
                String shortForm = shortFormProvider.getShortForm(ent);
                OWLLiteral con;
                if (languageTag != null) {
                    con = ontologyManager.getOWLDataFactory().getOWLLiteral(
                            shortForm, languageTag);
                } else {
                    con = ontologyManager.getOWLDataFactory().getOWLLiteral(
                            shortForm);
                }
                if (ontology.containsEntityInSignature(ent)) {
                    OWLOntologyChange chg = new AddAxiom(ont, ontologyManager
                            .getOWLDataFactory()
                            .getOWLAnnotationAssertionAxiom(
                                    ontologyManager.getOWLDataFactory()
                                            .getOWLAnnotationProperty(
                                                    annotationIRI),
                                    ent.getIRI(), con));
                    addChange(chg);
                }
            }
        }
    }
}
