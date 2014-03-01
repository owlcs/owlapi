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
package org.coode.owlapi.obo.parser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics
 *         Group, Date: 03/02/2011
 */
public class SynonymTagValueHandler extends AbstractTagValueHandler {

    private static final String TAG_NAME = OBOVocabulary.SYNONYM.toString();
    // synonym: "synonym" (EXACT|BROAD|NARROW|RELATED) TYPE? XRefList
    private static final Pattern valuePattern = Pattern
            .compile("\"([^\"]*)\"\\s*([^\\s]*)\\s*([^\\[\\s]+)?\\s*\\[([^\\]]*)\\]");
    private static final int VALUE_GROUP = 1;
    private static final int SCOPE_GROUP = 2;
    private static final int SYNONYM_TYPE_GROUP = 3;
    private static final int XREF_GROUP = 4;
    /** synonym type iri */
    public static final IRI SYNONYM_TYPE_IRI = OBOVocabulary.SYNONYM_TYPE
            .getIRI();
    /** xref iri */
    public static final IRI XREF_IRI = OBOVocabulary.XREF.getIRI();

    /**
     * @param consumer
     *        consumer
     */
    public SynonymTagValueHandler(OBOConsumer consumer) {
        super(TAG_NAME, consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        Matcher matcher = valuePattern.matcher(value);
        if (matcher.matches()) {
            OWLDataFactory df = getDataFactory();
            OWLAnnotationProperty property = getSynonymAnnotationProperty(matcher);
            Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
            annotations.addAll(getSynonymTypeAnnotations(matcher));
            annotations.addAll(getXRefAnnotations(matcher));
            OWLEntity subject = getConsumer().getCurrentEntity();
            String synonym = matcher.group(VALUE_GROUP);
            OWLLiteral synonymLiteral = df.getOWLLiteral(synonym);
            OWLAnnotationAssertionAxiom annoAssertion = df
                    .getOWLAnnotationAssertionAxiom(property, subject.getIRI(),
                            synonymLiteral, annotations);
            applyChange(new AddAxiom(getOntology(), annoAssertion));
        }
    }

    private Set<OWLAnnotation> getSynonymTypeAnnotations(Matcher matcher) {
        if (matcher.group(SYNONYM_TYPE_GROUP) != null) {
            OWLAnnotation typeAnnotation = getSynonymTypeAnnotation(matcher);
            return Collections.singleton(typeAnnotation);
        } else {
            return Collections.emptySet();
        }
    }

    private Set<OWLAnnotation> getXRefAnnotations(Matcher matcher) {
        Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
        String xrefs = matcher.group(XREF_GROUP);
        if (xrefs != null) {
            StringTokenizer tokenizer = new StringTokenizer(xrefs, ",");
            while (tokenizer.hasMoreTokens()) {
                String xref = tokenizer.nextToken();
                OWLAnnotation xrefAnnotation = getConsumer().parseXRef(xref);
                annotations.add(xrefAnnotation);
            }
        }
        return annotations;
    }

    private OWLAnnotationProperty getSynonymAnnotationProperty(Matcher matcher) {
        String synonymScope = matcher.group(SCOPE_GROUP);
        IRI annotationPropertyIRI;
        if (SynonymScope.BROAD.name().equals(synonymScope)) {
            annotationPropertyIRI = getTagIRI(OBOVocabulary.BROAD_SYNONYM);
        } else if (SynonymScope.EXACT.name().equals(synonymScope)) {
            annotationPropertyIRI = getTagIRI(OBOVocabulary.EXACT_SYNONYM);
        } else if (SynonymScope.NARROW.name().equals(synonymScope)) {
            annotationPropertyIRI = getTagIRI(OBOVocabulary.NARROW_SYNONYM);
        } else if (SynonymScope.RELATED.name().equals(synonymScope)) {
            annotationPropertyIRI = getTagIRI(OBOVocabulary.RELATED_SYNONYM);
        } else {
            annotationPropertyIRI = getTagIRI(OBOVocabulary.SYNONYM);
        }
        return getDataFactory().getOWLAnnotationProperty(annotationPropertyIRI);
    }

    private OWLAnnotation getSynonymTypeAnnotation(Matcher matcher) {
        OWLDataFactory df = getDataFactory();
        String synonymType = matcher.group(SYNONYM_TYPE_GROUP);
        return df.getOWLAnnotation(
                df.getOWLAnnotationProperty(SYNONYM_TYPE_IRI),
                df.getOWLLiteral(synonymType));
    }
}
