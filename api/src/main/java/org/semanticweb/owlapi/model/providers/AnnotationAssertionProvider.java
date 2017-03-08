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
package org.semanticweb.owlapi.model.providers;

import java.util.Collection;
import java.util.Collections;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;

/**
 * Annotation assertion provider.
 */
public interface AnnotationAssertionProvider extends LiteralProvider {

    /**
     * @param property property
     * @param subject subject
     * @param value value
     * @return an annotation assertion axiom
     */
    default OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
        OWLAnnotationProperty property, OWLAnnotationSubject subject,
        OWLAnnotationValue value) {
        return getOWLAnnotationAssertionAxiom(property, subject, value, Collections.emptySet());
    }

    /**
     * @param subject subject
     * @param annotation annotation
     * @return an annotation assertion axiom
     */
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject,
        OWLAnnotation annotation);

    /**
     * @param property property
     * @param subject subject
     * @param value value
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return an annotation assertion axiom - with annotations
     */
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationProperty property,
        OWLAnnotationSubject subject, OWLAnnotationValue value,
        Collection<OWLAnnotation> annotations);

    /**
     * @param subject subject
     * @param annotation annotation
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return an annotation assertion axiom - with annotations
     */
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject,
        OWLAnnotation annotation, Collection<OWLAnnotation> annotations);

    /**
     * Gets an annotation assertion that specifies that an IRI is deprecated. The annotation
     * property is owl:deprecated and the value of the annotation is {@code "true"^^xsd:boolean}.
     * (See <a href= "http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Annotation_Properties"
     * >Annotation Properties</a> in the OWL 2 Specification
     *
     * @param subject The IRI to be deprecated.
     * @return The annotation assertion that deprecates the specified IRI.
     */
    OWLAnnotationAssertionAxiom getDeprecatedOWLAnnotationAssertionAxiom(IRI subject);
}
