package org.semanticweb.owl.model;

import java.net.URI;
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
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * <p/>
 * A marker interface for classes, properties, individuals (including anonymous individuals)
 * and datatypes. Entities are the fundamental building blocks of ontologies, and all entities
 * are identified by a URI.  Note that OWL 1.1 supports punning, which means that an OWLClass
 * may have the same URI as an OWLIndividual (or other entity) but will still be considered to
 * be a separate object.
 */
public interface OWLEntity extends OWLObject, OWLNamedObject {

    /**
     * Obtains annotations on this entity where the annotation have been asserted on
     * the specified entity.
     */
    Set<OWLAnnotation> getAnnotations(OWLOntology ontology);


    /**
     * Obtains the annotations on this entity where the annotation has the specified
     * URI.
     * @param ontology The ontology to examine for annotation axioms
     * @param annotationURI The annotation URI
     * @return A set of <code>OWLAnnotation</code> objects that have the specified
     * URI.
     */
    Set<OWLAnnotation> getAnnotations(OWLOntology ontology, URI annotationURI);


    Set<OWLAnnotationAxiom> getAnnotationAxioms(OWLOntology ontology);


    /**
     * Determines if this entity is a built in entity. The entity is a built in entity if it is:
     * <ul>
     * <li>a class and the URI corresponds to owl:Thing or owl:Nothing</li>
     * <li>an object property and the URI corresponds to owl:topObjectProperty or owl:bottomObjectProperty</li>
     * <li>a data property and the URI corresponds to owl:topDataProperty or owl:bottomDataProperty</li>
     * <li>a datatype and the URI is in the set of built in data type URIs</li>
     * </ul>
     * @return <code>true</code> if this entity is a built in entity, or <code>false</code>
     * if this entity is not a builtin entity.
     */
    boolean isBuiltIn();

    /**
     * A convenience method that determines if this entity is an OWLClass
     * @return <code>true</code> if this entity is an OWLClass, otherwise <code>false</code>
     */
    boolean isOWLClass();


    /**
     * A convenience method that obtains this entity as an OWLClass (in order to
     * avoid explicit casting).
     * @return The entity as an OWLClass.
     * @throws OWLRuntimeException if this entity is not an OWLClass (check with the
     * isOWLClass method first).
     */
    OWLClass asOWLClass();

    /**
     * A convenience method that determines if this entity is an OWLObjectProperty
     * @return <code>true</code> if this entity is an OWLObjectProperty, otherwise <code>false</code>
     */
    boolean isOWLObjectProperty();

    /**
     * A convenience method that obtains this entity as an OWLObjectProperty (in order to
     * avoid explicit casting).
     * @return The entity as an OWLObjectProperty.
     * @throws OWLRuntimeException if this entity is not an OWLObjectProperty (check with the
     * isOWLObjectProperty method first).
     */
    OWLObjectProperty asOWLObjectProperty();

    /**
     * A convenience method that determines if this entity is an OWLDataProperty
     * @return <code>true</code> if this entity is an OWLDataProperty, otherwise <code>false</code>
     */
    boolean isOWLDataProperty();

    /**
     * A convenience method that obtains this entity as an OWLDataProperty (in order to
     * avoid explicit casting).
     * @return The entity as an OWLDataProperty.
     * @throws OWLRuntimeException if this entity is not an OWLDataProperty (check with the
     * isOWLDataProperty method first).
     */
    OWLDataProperty asOWLDataProperty();

    /**
     * A convenience method that determines if this entity is an OWLIndividual
     * @return <code>true</code> if this entity is an OWLIndividual, otherwise <code>false</code>
     */
    boolean isOWLIndividual();

    /**
     * A convenience method that obtains this entity as an OWLIndividual (in order to
     * avoid explicit casting).
     * @return The entity as an OWLIndividual.
     * @throws OWLRuntimeException if this entity is not an OWLIndividual (check with the
     * isOWLIndividual method first).
     */
    OWLIndividual asOWLIndividual();

    /**
     * A convenience method that determines if this entity is an OWLDataType
     * @return <code>true</code> if this entity is an OWLDataType, otherwise <code>false</code>
     */
    boolean isOWLDataType();

    /**
     * A convenience method that obtains this entity as an OWLDataType (in order to
     * avoid explicit casting).
     * @return The entity as an OWLDataType.
     * @throws OWLRuntimeException if this entity is not an OWLDataType (check with the
     * isOWLDataType method first).
     */
    OWLDataType asOWLDataType();

    void accept(OWLEntityVisitor visitor);

    <O> O accept(OWLEntityVisitorEx<O> visitor);
}
