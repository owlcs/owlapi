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
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 18/04/2012
 */
public class DefTagValueHandler extends AbstractTagValueHandler {

    private static final Pattern PATTERN = Pattern
            .compile("\"([^\"]*)\"\\s*(\\[([^\\]]*)\\])?\\s*");
    private static final int QUOTED_STRING_CONTENT_GROUP = 1;
    private static final int XREF_GROUP = 3;

    /**
     * @param consumer
     *        consumer
     */
    public DefTagValueHandler(OBOConsumer consumer) {
        super(OBOVocabulary.DEF.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        Matcher matcher = PATTERN.matcher(value);
        OWLDataFactory df = getDataFactory();
        String annotationValue;
        Set<OWLAnnotation> xrefAnnotations = Collections.emptySet();
        if (matcher.matches()) {
            annotationValue = matcher.group(QUOTED_STRING_CONTENT_GROUP);
            xrefAnnotations = getXRefAnnotations(matcher);
        } else {
            annotationValue = getUnquotedString(value);
        }
        IRI propertyIRI = getTagIRI(getTagName());
        OWLAnnotationProperty property = df
                .getOWLAnnotationProperty(propertyIRI);
        OWLEntity currentEntity = getConsumer().getCurrentEntity();
        OWLLiteral literal = df.getOWLLiteral(annotationValue);
        OWLAnnotationAssertionAxiom ax = df.getOWLAnnotationAssertionAxiom(
                property, currentEntity.getIRI(), literal, xrefAnnotations);
        applyChange(new AddAxiom(getOntology(), ax));
    }

    private Set<OWLAnnotation> getXRefAnnotations(Matcher matcher) {
        Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
        String xrefs = matcher.group(XREF_GROUP);
        if (xrefs != null) {
            StringTokenizer tokenizer = new StringTokenizer(xrefs, ",");
            while (tokenizer.hasMoreTokens()) {
                String xrefValue = tokenizer.nextToken();
                OWLAnnotation xrefAnnotation = getConsumer().parseXRef(
                        xrefValue);
                annotations.add(xrefAnnotation);
            }
        }
        return annotations;
    }
}
