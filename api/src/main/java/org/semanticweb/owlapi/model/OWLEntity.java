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
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * </p>
 * Represents <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Entities.2C_Literals.2C_and_Anonymous_Individuals">Entities</a> in the OWL 2 Specification.
 * </p>
 */
public interface OWLEntity extends OWLObject, OWLNamedObject {

    boolean isTopEntity();

    boolean isBottomEntity();

    /**
     * Gets the entity type for this entity
     * @return The entity type
     */
    EntityType getEntityType();

    /**
     * Gets an entity that has the same IRI as this entity but is of the specified type.
     * @param entityType The type of the entity to obtain.  This entity is not affected in any way.
     * @return An entity that has the same IRI as this entity and is of the specified type
     */
    <E extends OWLEntity> E getOWLEntity(EntityType<E> entityType);

    /**
     * Tests to see if this entity is of the specified type
     * @param entityType The entity type
     * @return <code>true</code> if this entity is of the specified type, otherwise <code>false</code>.
     */
    boolean isType(EntityType entityType);

    /**
     * Gets the annotations for this entity.  These are deemed to be annotations in annotation assertion
     * axioms that have a subject that is an IRI that is equal to the IRI of this entity.
     *
     * @param ontology The ontology to be examined for annotation assertion axioms
     * @return The annotations that participate directly in an annotation assertion whose subject is an
     *         IRI corresponding to the IRI of this entity.
     */
    Set<OWLAnnotation> getAnnotations(OWLOntology ontology);


    /**
     * Obtains the annotations on this entity where the annotation has the specified
     * URI.
     *
     * @param ontology              The ontology to examine for annotation axioms
     * @param annotationProperty    The annotation property
     * @return A set of <code>OWLAnnotation</code> objects that have the specified
     *         URI.
     */
    Set<OWLAnnotation> getAnnotations(OWLOntology ontology, OWLAnnotationProperty annotationProperty);


    Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLOntology ontology);


    /**
     * Determines if this entity is a built in entity. The entity is a built in entity if it is:
     * <ul>
     * <li>a class and the URI corresponds to owl:Thing or owl:Nothing</li>
     * <li>an object property and the URI corresponds to owl:topObjectProperty or owl:bottomObjectProperty</li>
     * <li>a data property and the URI corresponds to owl:topDataProperty or owl:bottomDataProperty</li>
     * <li>a datatype and the IRI is rdfs:Literal or is in the OWL 2 datatype map</li>
     * <li>an annotation property and the URI is in the set of built in annotation property URIs</li>
     * </ul>
     * @return <code>true</code> if this entity is a built in entity, or <code>false</code>
     *         if this entity is not a builtin entity.
     */
    boolean isBuiltIn();

    /**
     * A convenience method that determines if this entity is an OWLClass
     *
     * @return <code>true</code> if this entity is an OWLClass, otherwise <code>false</code>
     */
    boolean isOWLClass();


    /**
     * A convenience method that obtains this entity as an OWLClass (in order to
     * avoid explicit casting).
     *
     * @return The entity as an OWLClass.
     * @throws OWLRuntimeException if this entity is not an OWLClass (check with the
     *                             isOWLClass method first).
     */
    OWLClass asOWLClass();

    /**
     * A convenience method that determines if this entity is an OWLObjectProperty
     *
     * @return <code>true</code> if this entity is an OWLObjectProperty, otherwise <code>false</code>
     */
    boolean isOWLObjectProperty();

    /**
     * A convenience method that obtains this entity as an OWLObjectProperty (in order to
     * avoid explicit casting).
     *
     * @return The entity as an OWLObjectProperty.
     * @throws OWLRuntimeException if this entity is not an OWLObjectProperty (check with the
     *                             isOWLObjectProperty method first).
     */
    OWLObjectProperty asOWLObjectProperty();

    /**
     * A convenience method that determines if this entity is an OWLDataProperty
     *
     * @return <code>true</code> if this entity is an OWLDataProperty, otherwise <code>false</code>
     */
    boolean isOWLDataProperty();

    /**
     * A convenience method that obtains this entity as an OWLDataProperty (in order to
     * avoid explicit casting).
     *
     * @return The entity as an OWLDataProperty.
     * @throws OWLRuntimeException if this entity is not an OWLDataProperty (check with the
     *                             isOWLDataProperty method first).
     */
    OWLDataProperty asOWLDataProperty();

    /**
     * A convenience method that determines if this entity is an OWLNamedIndividual
     *
     * @return <code>true</code> if this entity is an OWLNamedIndividual, otherwise <code>false</code>
     */
    boolean isOWLNamedIndividual();

    /**
     * A convenience method that obtains this entity as an OWLNamedIndividual (in order to
     * avoid explicit casting).
     *
     * @return The entity as an OWLNamedIndividual.
     * @throws OWLRuntimeException if this entity is not an OWLIndividual (check with the
     *                             isOWLIndividual method first).
     */
    OWLNamedIndividual asOWLNamedIndividual();

    /**
     * A convenience method that determines if this entity is an OWLDatatype
     *
     * @return <code>true</code> if this entity is an OWLDatatype, otherwise <code>false</code>
     */
    boolean isOWLDatatype();

    /**
     * A convenience method that obtains this entity as an OWLDatatype (in order to
     * avoid explicit casting).
     *
     * @return The entity as an OWLDatatype.
     * @throws OWLRuntimeException if this entity is not an OWLDatatype (check with the
     *                             isOWLDatatype method first).
     */
    OWLDatatype asOWLDatatype();

    /**
     * A convenience method that determines if this entity is an OWLAnnotationProperty
     *
     * @return <code>true</code> if this entity is an OWLAnnotationProperty, otherwise <code>false</code>
     */
    boolean isOWLAnnotationProperty();

    /**
     * A convenience method that obtains this entity as an OWLAnnotationProperty (in order to
     * avoid explicit casting).
     *
     * @return The entity as an OWLAnnotationProperty.
     * @throws OWLRuntimeException if this entity is not an OWLAnnotationProperty
     */
    OWLAnnotationProperty asOWLAnnotationProperty();

    /**
     * Returns a string representation that can be used as the ID of this entity.  This is the toString
     * representation of the IRI
     * @return A string representing the toString of the IRI of this entity.
     */
    String toStringID();

    /**
     * Gets the axioms in the specified ontology that contain this entity in their signature.
     * @param ontology The ontology that will be searched for axioms
     * @return The axioms in the specified ontology whose signature contains this entity.
     */
    Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology);

    /**
     * Gets the axioms in the specified ontology and possibly its imports closure that contain this entity in their
     * signature.
     * @param ontology The ontology that will be searched for axioms
     * @param includeImports If <code>true</code> then axioms in the imports closure will also be returned, if
     * <code>false</code> then only the axioms in the specified ontology will be returned.
     * @return The axioms in the specified ontology whose signature contains this entity.
     */
    Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology, boolean includeImports);

    void accept(OWLEntityVisitor visitor);

    <O> O accept(OWLEntityVisitorEx<O> visitor);
}
