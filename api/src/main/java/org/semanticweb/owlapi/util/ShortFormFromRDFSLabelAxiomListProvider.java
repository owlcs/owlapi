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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * A short form provider that generates short forms based on {@code rdfs:label} annotation values
 * taken from a list of axioms. A list of preferred annotation URIs and preferred annotation
 * languages is used to determine which annotation value to select if there are multiple annotations
 * for the entity whose short form is being generated. If there are multiple annotations the these
 * annotations are ranked by preferred IRI and then by preferred language.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 5.1.12
 */
public class ShortFormFromRDFSLabelAxiomListProvider implements ShortFormProvider {

    private final ShortFormProvider alternateShortFormProvider;
    private final IRIShortFormProvider alternateIRIShortFormProvider;
    private final List<String> preferredLanguages;
    private final StringAnnotationVisitor literalRenderer = new StringAnnotationVisitor();
    private final List<OWLAnnotationAssertionAxiom> axioms;

    /**
     * Constructs an annotation value short form provider. Using {@code SimpleShortFormProvider} as
     * the alternate short form provider
     *
     * @param preferredLanguages Languages at the start of the list have a higher priority over
     *        languages at the end of the list. This parameter may be empty but it must not be
     *        {@code null}.
     * @param axioms axioms to use.
     */
    public ShortFormFromRDFSLabelAxiomListProvider(List<String> preferredLanguages,
        List<? extends OWLAxiom> axioms) {
        this(preferredLanguages, axioms, new SimpleShortFormProvider());
    }

    /**
     * Constructs an annotation short form provider.
     *
     * @param preferredLanguages Languages at the start of the list have a higher priority over
     *        languages at the end of the list. This parameter may be empty but it must not be
     *        {@code null}.
     * @param axioms axioms to use.
     * @param alternateShortFormProvider A short form provider which will be used to generate the
     *        short form for an entity that does not have any annotations. This provider will also
     *        be used in the case where the value of an annotation is an {@code OWLIndividual} for
     *        providing the short form of the individual.
     */
    public ShortFormFromRDFSLabelAxiomListProvider(List<String> preferredLanguages,
        List<? extends OWLAxiom> axioms, ShortFormProvider alternateShortFormProvider) {
        this(axioms, alternateShortFormProvider, new SimpleIRIShortFormProvider(),
            preferredLanguages);
    }

    /**
     * @param axioms axioms to use.
     * @param alternateShortFormProvider short form provider
     * @param alternateIRIShortFormProvider iri short form provider
     * @param preferredLanguages preferred languages
     */
    public ShortFormFromRDFSLabelAxiomListProvider(List<? extends OWLAxiom> axioms,
        ShortFormProvider alternateShortFormProvider,
        IRIShortFormProvider alternateIRIShortFormProvider, List<String> preferredLanguages) {
        this.axioms = asList(checkNotNull(axioms, "axioms cannot be null").stream()
            .filter(x -> x.getAxiomType().equals(AxiomType.ANNOTATION_ASSERTION))
            .map(x -> (OWLAnnotationAssertionAxiom) x).sorted());
        this.alternateShortFormProvider =
            checkNotNull(alternateShortFormProvider, "alternateShortFormProvider cannot be null");
        this.alternateIRIShortFormProvider = checkNotNull(alternateIRIShortFormProvider,
            "alternateIRIShortFormProvider cannot be null");
        this.preferredLanguages =
            checkNotNull(preferredLanguages, "preferredLanguages cannot be null");
    }

    @Override
    public String getShortForm(OWLEntity entity) {
        List<OWLAnnotationAssertionAxiom> flatMap =
            asList(axioms.stream().filter(x -> x.getSubject().equals(entity.getIRI())));
        AnnotationLanguageFilter checker = new AnnotationLanguageFilter(preferredLanguages);
        flatMap.forEach(ax -> ax.accept(checker));
        OWLObject match = checker.getMatch();
        if (match != null) {
            return getRendering(match);
        }
        return alternateShortFormProvider.getShortForm(entity);
    }

    /**
     * Obtains the rendering of the specified object. If the object is a constant then the rendering
     * is equal to the literal value, if the object is an individual then the rendering is equal to
     * the rendering of the individual as provided by the alternate short form provider
     *
     * @param object The object to the rendered
     * @return The rendering of the object.
     */
    private String getRendering(OWLObject object) {
        // We return the literal value of constants or use the alternate
        // short form provider to render individuals.
        if (object instanceof OWLLiteral) {
            // TODO refactor this method to use the annotation value visitor
            return literalRenderer.visit((OWLLiteral) object);
        } else if (object.isIRI()) {
            return alternateIRIShortFormProvider.getShortForm((IRI) object);
        } else {
            return alternateShortFormProvider.getShortForm((OWLEntity) object);
        }
    }

    private static class AnnotationLanguageFilter implements OWLObjectVisitor {

        private final IRI prop = OWLRDFVocabulary.RDFS_LABEL.getIRI();
        private final List<String> preferredLanguages;
        @Nullable
        protected OWLObject candidateValue = null;
        int lastLangMatchIndex = Integer.MAX_VALUE;

        AnnotationLanguageFilter(@Nullable List<String> preferredLanguages) {
            this.preferredLanguages =
                preferredLanguages == null ? Collections.emptyList() : preferredLanguages;
        }

        @Nullable
        public OWLObject getMatch() {
            return candidateValue;
        }

        @Override
        public void visit(OWLAnnotationAssertionAxiom axiom) {
            if (lastLangMatchIndex > 0 && axiom.getProperty().getIRI().equals(prop)) {
                // a perfect match - no need to carry on search
                axiom.getValue().accept(this);
            }
        }

        @Override
        public void visit(OWLLiteral node) {
            if (preferredLanguages.isEmpty()) {
                // if there are no languages just match the first thing
                lastLangMatchIndex = 0;
                candidateValue = node;
            } else {
                int index = preferredLanguages.indexOf(node.getLang());
                if (index >= 0 && index < lastLangMatchIndex) {
                    lastLangMatchIndex = index;
                    candidateValue = node;
                }
            }
        }

        @Override
        public void visit(IRI iri) {
            // No language
            candidateValue = iri;
        }
    }
}
