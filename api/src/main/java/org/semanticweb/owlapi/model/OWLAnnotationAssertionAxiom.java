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

package org.semanticweb.owlapi.model;
/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Nov-2006<br><br>
 * <p/>
 * Represents <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Annotation_Assertion">AnnotationAssertion</a> axioms
 * in the OWL 2 specification.
 */
public interface OWLAnnotationAssertionAxiom extends OWLAnnotationAxiom {

    /**
     * Gets the subject of the annotation assertion. This is either an {@link org.semanticweb.owlapi.model.IRI} or
     * an {@link org.semanticweb.owlapi.model.OWLAnonymousIndividual}.
     * @return The subject of the annotation
     */
    OWLAnnotationSubject getSubject();

    /**
     * Gets the annotation property.
     * @return The annotation property.
     */
    OWLAnnotationProperty getProperty();

    /**
     * Gets the annotation value.  This is either an {@link org.semanticweb.owlapi.model.IRI}, an {@link org.semanticweb.owlapi.model.OWLAnonymousIndividual}
     * or an {@link OWLLiteral}. Annotation values can be visited with an {@link org.semanticweb.owlapi.model.OWLAnnotationValueVisitor}.
     * @see {@link org.semanticweb.owlapi.model.OWLAnnotationValueVisitor}
     * @see {@link OWLAnnotationValueVisitorEx}
     * @return The annotation value.
     */
    OWLAnnotationValue getValue();

    /**
     * Gets the combination of the annotation property and the annotation value as an {@link org.semanticweb.owlapi.model.OWLAnnotation}
     * object.
     * @return The annotation object that combines the property and value of this annotation.
     */
    OWLAnnotation getAnnotation();

    /**
     * Determines if this annotation assertion deprecates the IRI that is the subject of the annotation.
     * @return <code>true</code> if this annotation assertion deprecates the subject IRI of the assertion, otherwise
     * <code>false</code>.
     * @see {@link OWLAnnotation#isDeprecatedIRIAnnotation()}
     */
    boolean isDeprecatedIRIAssertion();

    OWLAnnotationAssertionAxiom getAxiomWithoutAnnotations();
}
