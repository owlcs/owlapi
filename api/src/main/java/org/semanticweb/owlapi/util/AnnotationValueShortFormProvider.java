package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;

import java.util.List;
import java.util.Map;
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
 * Date: 18-Apr-2007<br><br>
 * <p/>
 * A short form provider that generates short forms based on entity annotation values.
 * A list of preferred annotation URIs and preferred annotation languages is used to
 * determine which annotation value to select if there are multiple annotations for the
 * entity whose short form is being generated.  If there are multiple annotations the
 * these annotations are ranked by preferred URI and then by preferred language.
 */
public class AnnotationValueShortFormProvider implements ShortFormProvider {

    private OWLOntologySetProvider ontologySetProvider;

    private ShortFormProvider alternateShortFormProvider;

    private List<OWLAnnotationProperty> annotationProperties;

    private Map<OWLAnnotationProperty, List<String>> preferredLanguageMap;


    /**
     * Constructs an annotation value short form provider. Using <code>SimpleShortFormProvider</code> as the
     * alternate short form provider (see other constructor for details).
     */
    public AnnotationValueShortFormProvider(List<OWLAnnotationProperty> annotationProperties,
                                            Map<OWLAnnotationProperty, List<String>> preferredLanguageMap,
                                            OWLOntologySetProvider ontologySetProvider) {
        this(annotationProperties, preferredLanguageMap, ontologySetProvider, new SimpleShortFormProvider());
    }


    /**
     * Constructs an annotation short form provider.
     *
     * @param annotationProperties       A <code>List</code> of preferred annotation properties.  The list is searched from
     *                                   start to end, so that annotations that have a property at the start of the list have a higher
     *                                   priority and are selected over annotations with properties that appear towards or at the end of the list.
     * @param preferredLanguageMap       A map which maps annotation properties to preferred languages.  For any given
     *                                   annotation property there may be a list of preferred languages.  Languages at the start of the list
     *                                   have a higher priority over languages at the end of the list.  This parameter may be empty but it
     *                                   must not be <code>null</code>.
     * @param ontologySetProvider        An <code>OWLOntologySetProvider</code> which provides a set of ontology
     *                                   from which candidate annotation axioms should be taken.  For a given entity, all ontologies are
     *                                   examined.
     * @param alternateShortFormProvider A short form provider which will be used to generate the short form
     *                                   for an entity that does not have any annotations.  This provider will also be used in the case where
     *                                   the value of an annotation is an <code>OWLIndividual</code> for providing the short form of the individual.
     */
    public AnnotationValueShortFormProvider(List<OWLAnnotationProperty> annotationProperties,
                                            Map<OWLAnnotationProperty, List<String>> preferredLanguageMap,
                                            OWLOntologySetProvider ontologySetProvider,
                                            ShortFormProvider alternateShortFormProvider) {
        this.annotationProperties = annotationProperties;
        this.preferredLanguageMap = preferredLanguageMap;
        this.ontologySetProvider = ontologySetProvider;
        this.alternateShortFormProvider = alternateShortFormProvider;
    }


    public String getShortForm(OWLEntity entity) {

        for (OWLAnnotationProperty prop : annotationProperties) { // visit the properties in order of preference
            AnnotationLanguageFilter checker = new AnnotationLanguageFilter(prop, preferredLanguageMap.get(prop));

            for (OWLOntology ontology : ontologySetProvider.getOntologies()) {
                for (OWLAnnotationAssertionAxiom ax : entity.getAnnotationAssertionAxioms(ontology)) {
                    ax.accept(checker);
                }
            }
            if (checker.getMatch() != null) {
                return getRendering(checker.getMatch());
            }
        }

        return alternateShortFormProvider.getShortForm(entity);
    }


    /**
     * Obtains the rendering of the specified object.  If the object
     * is a constant then the rendering is equal to the literal value,
     * if the object is an individual then the rendering is equal to
     * the rendering of the individual as provided by the alternate
     * short form provider
     *
     * @param object The object to the rendered
     * @return The rendering of the object.
     */
    private String getRendering(OWLObject object) {
        // We return the literal value of constants or use the alternate
        // short form provider to render individuals.
        if (object instanceof OWLLiteral) {
            return ((OWLLiteral) object).getLiteral();
        } else {
            return alternateShortFormProvider.getShortForm((OWLEntity) object);
        }
    }


    /**
     * Gets the annotation URIs that this short form provider uses.
     */
    public List<OWLAnnotationProperty> getAnnotationProperties() {
        return annotationProperties;
    }


    public Map<OWLAnnotationProperty, List<String>> getPreferredLanguageMap() {
        return preferredLanguageMap;
    }


    public void dispose() {
    }


    private class AnnotationLanguageFilter extends OWLObjectVisitorAdapter {

        private OWLAnnotationProperty prop;

        private List<String> preferredLanguages;

        OWLObject candidateValue = null;

        int lastLangMatchIndex = Integer.MAX_VALUE;


        private AnnotationLanguageFilter(OWLAnnotationProperty prop, List<String> preferredLanguages) {
            this.prop = prop;
            this.preferredLanguages = preferredLanguages;
        }


        public OWLObject getMatch() {
            return candidateValue;
        }

        public void visit(OWLAnnotationAssertionAxiom anno) {
            if (lastLangMatchIndex > 0 && // a perfect match - no need to carry on search
                anno.getProperty().equals(prop)){
                anno.getValue().accept(this);
            }
        }


        public void visit(OWLStringLiteral untypedConstantVal) {
            if (preferredLanguages == null || preferredLanguages.isEmpty()) { // if there are no languages just match the first thing
                lastLangMatchIndex = 0;
                candidateValue = untypedConstantVal;
            } else {
                final int index = preferredLanguages.indexOf(untypedConstantVal.getLang());
                if (index >= 0 && index < lastLangMatchIndex) {
                    lastLangMatchIndex = index;
                    candidateValue = untypedConstantVal;
                }
            }
        }


        public void visit(OWLTypedLiteral node) {
            if (preferredLanguages == null || preferredLanguages.isEmpty()) {
                lastLangMatchIndex = 0;
                candidateValue = node;
            } else {
                final int index = preferredLanguages.indexOf(null);
                if (index >= 0 && index < lastLangMatchIndex) {
                    lastLangMatchIndex = index;
                    candidateValue = node;
                }
            }
        }
    }
}
