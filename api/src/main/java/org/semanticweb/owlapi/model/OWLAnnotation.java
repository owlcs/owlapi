package org.semanticweb.owlapi.model;

import java.util.Set;
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
 * Date: 18-Dec-2006<br><br>
 * <p/>
 * Annotations are used in the various types of annotation axioms, which
 * bind annotations to their subjects (i.e. axioms or declarations).
 * <p/>
 * An annotation is equal to another annotation if both objects have equal
 * annotation URIs and have equal annotation values
 */
public interface OWLAnnotation extends OWLObject {

    /**
     * Gets the property that this annotation acts along
     *
     * @return The annotation property
     */
    OWLAnnotationProperty getProperty();

    /**
     * Gets the annotation value.  The type of value will depend upon
     * the type of the annotation e.g. whether the annotation is an {@link org.semanticweb.owlapi.model.OWLLiteral},
     * an {@link org.semanticweb.owlapi.model.IRI} or an {@link org.semanticweb.owlapi.model.OWLAnonymousIndividual}.
     * @see org.semanticweb.owlapi.model.OWLAnnotationValueVisitor
     * @see org.semanticweb.owlapi.model.OWLAnnotationValueVisitorEx
     * @return The annotation value.
     */
    OWLAnnotationValue getValue();


    /**
     * Determines if this annotation is an annotation used to deprecate an IRI.  This is the case if the annotation
     * property has an IRI of <code>owl:deprecated</code> and the value of the annotation is <code>"true"^^xsd:boolean</code>
     * @return <code>true</code> if this annotation is an annotation that can be used to deprecate an IRI, otherwise
     * <code>false</code>.
     */
    boolean isDeprecatedIRIAnnotation();


    /**
     * Gets the annotations on this annotation
     *
     * @return A (possibly empty) set of annotations that annotate this annotation
     */
    Set<OWLAnnotation> getAnnotations();

    void accept(OWLAnnotationObjectVisitor visitor);

    <O> O accept(OWLAnnotationObjectVisitorEx<O> visitor);

}
