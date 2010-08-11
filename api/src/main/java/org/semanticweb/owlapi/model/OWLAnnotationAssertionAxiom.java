package org.semanticweb.owlapi.model;
/*
 * Copyright (C) 2006, University of Manchester
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
