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

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.ImportsStructureEntitySorter;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public class ShortForm2AnnotationGenerator extends AbstractCompositeOntologyChange {

    /**
     * Instantiates a new short form2 annotation generator.
     *
     * @param df data factory
     * @param ontologyManager the ontology manager
     * @param ontology the ontology
     * @param shortFormProvider the short form provider
     * @param annotationIRI The annotation IRI to be used
     * @param languageTag language
     */
    public ShortForm2AnnotationGenerator(OWLDataFactory df, OWLOntologyManager ontologyManager,
        OWLOntology ontology, ShortFormProvider shortFormProvider, IRI annotationIRI,
        @Nullable String languageTag) {
        super(df);
        generateChanges(checkNotNull(ontologyManager, "ontologyManager cannot be null"),
            checkNotNull(ontology, "ontology cannot be null"),
            checkNotNull(shortFormProvider, "shortFormProvider cannot be null"),
            checkNotNull(annotationIRI, "annotationIRI cannot be null"), languageTag);
    }

    /**
     * Instantiates a new short form2 annotation generator.
     *
     * @param df data factory
     * @param ontologyManager the ontology manager
     * @param ontology the ontology
     * @param shortFormProvider the short form provider
     * @param annotationIRI iri for annotation property
     */
    public ShortForm2AnnotationGenerator(OWLDataFactory df, OWLOntologyManager ontologyManager,
        OWLOntology ontology, ShortFormProvider shortFormProvider, IRI annotationIRI) {
        this(df, ontologyManager, ontology, shortFormProvider, annotationIRI, null);
    }

    private static void generateChanges(OWLOntologyManager ontologyManager, OWLOntology o,
        ShortFormProvider provider, IRI annotationIRI, @Nullable String lang) {
        OWLDataFactory df = ontologyManager.getOWLDataFactory();
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty(annotationIRI);
        new ImportsStructureEntitySorter(o).getObjects().forEach((ont, ent) -> ent.forEach(e -> {
            if (o.containsEntityInSignature(e)) {
                ont.add(df.getOWLAnnotationAssertionAxiom(ap, e.getIRI(),
                    action(e, lang, provider, df)));
            }
        }));
    }

    private static OWLLiteral action(OWLEntity e, @Nullable String lang, ShortFormProvider provider,
        OWLDataFactory df) {
        if (lang != null) {
            return df.getOWLLiteral(provider.getShortForm(e), lang);
        }
        return df.getOWLLiteral(provider.getShortForm(e));
    }
}
