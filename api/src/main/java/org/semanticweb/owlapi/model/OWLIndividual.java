package org.semanticweb.owlapi.model;

import java.util.Map;
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
 * Date: 25-Oct-2006<br><br>
 * Represents a named or anonymous individual.
 */
public interface OWLIndividual extends OWLObject, OWLPropertyAssertionObject {

    /**
     * Determines if this individual is an instance of {@link org.semanticweb.owlapi.model.OWLNamedIndividual}.
     * Note that this method  is the dual of {@link #isAnonymous()}.
     * @return <code>true</code> if this individual is an instance of {@link org.semanticweb.owlapi.model.OWLNamedIndividual}
     * because it is a named individuals, otherwise <code>false</code>
     */
    boolean isNamed();

    /**
     * Determines if this object is an instance of {@link org.semanticweb.owlapi.model.OWLAnonymousIndividual}
     * Note that this method  is the dual of {@link #isNamed()}.
     * @return <code>true</code> if this object represents an anonymous
     *         individual (<code>OWLAnonymousIndividual)</code> or <code>false</code>
     *         if this object represents a named individual (<code>OWLIndividual</code>)
     */
    boolean isAnonymous();

    /**
     * Obtains this individual as a named individual if it is indeed named.
     * @return The individual as a named individual
     * @throws OWLRuntimeException if this individual is anonymous
     */
    OWLNamedIndividual asOWLNamedIndividual();

    /**
     * Obtains this individual an anonymous individual if it is indeed anonymous
     * @return The individual as an anonymous individual
     * @throws OWLRuntimeException if this individual is named
     */
    OWLAnonymousIndividual asOWLAnonymousIndividual();


    /**
     * A convenience method, which gets the types of this individual, that
     * correspond to the types asserted with axioms in the specified ontology.
     * @param ontology The ontology that should be examined for class assertion
     *                 axioms in order to get the types for this individual.
     * @return A set of class expressions that correspond the asserted types of this
     *         individual in the specified ontology.
     */
    Set<OWLClassExpression> getTypes(OWLOntology ontology);


    /**
     * A convenience method that gets the types of this individual by
     * examining the specified ontologies.
     * @param ontologies The ontologies to be examined for class assertions
     * @return A set of class expressions that represent the types of this
     *         individual as asserted in the specified ontologies.
     */
    Set<OWLClassExpression> getTypes(Set<OWLOntology> ontologies);


    /**
     * Gets the object property values for this individual.
     * @param ontology The ontology to search for the property values.
     * @return A map, which maps object properties to sets of individuals.
     */
    Map<OWLObjectPropertyExpression, Set<OWLIndividual>> getObjectPropertyValues(OWLOntology ontology);

    /**
     * Gets the asserted object property values for this individual and the specified property.
     * @param ontology The ontology to be examined for axioms that assert property values for this individual
     * @param property The property for which values will be returned.
     * @return The set of individuals that are the values of this property.  More precisely, the set of individuals
     *         such that for each individual i in the set, is in a property assertion axiom property(this, i) is in the specified ontology.
     */
    Set<OWLIndividual> getObjectPropertyValues(OWLObjectPropertyExpression property, OWLOntology ontology);


    /**
     * Test whether a specific value for a specific object property on this individual has been asserted.
     * @param property The property whose values will be examined
     * @param individual The individual value of the property that will be tested for
     * @param ontology The ontology to search for the property value
     * @return <code>true</code> if the individual has the specified property value, that is, <code>true</code>
     * if the specified ontology contains an object property assertion ObjectPropertyAssertion(property, this, individual),
     * otherwise <code>false</code>
     */
    boolean hasObjectPropertyValue(OWLObjectPropertyExpression property, OWLIndividual individual, OWLOntology ontology);

    /**
     * Test whether a specific value for a specific object property has been asserted not to hold for this individual.
     * @param property The property to test for
     * @param individual The value to test for
     * @param ontology The ontology to search for the assertion
     * @return <code>true</code> if the specified property value has explicitly been asserted not to hold, that is,
     * <code>true</code> if the specified ontology contains a negative object property assertion
     * NegativeObjectPropertyAssertion(property, this, individual), otherwise <code>false</code>
     */
    boolean hasNegativeObjectPropertyValue(OWLObjectPropertyExpression property, OWLIndividual individual, OWLOntology ontology);

    /**
     * Gets the object property values that are explicitly asserted NOT to hold
     * for this individual
     * @param ontology The ontology that should be examined for axioms
     * @return A map containing the negative object property values
     */
    Map<OWLObjectPropertyExpression, Set<OWLIndividual>> getNegativeObjectPropertyValues(OWLOntology ontology);

    /**
     * Gets the data property values for this individual
     */
    Map<OWLDataPropertyExpression, Set<OWLLiteral>> getDataPropertyValues(OWLOntology ontology);

    /**
     * Gets the values that this individual has for a specific data property
     * @param ontology The ontology to examine for property assertions
     * @return The values that this individual has for the specified property in the specified ontology.  This is
     * the set of values such that each value LV in the set is in an axiom of the form
     * DataPropertyAssertion(property, thisIndividual, LV) in the ontology specified by the ontology parameter.
     */
    Set<OWLLiteral> getDataPropertyValues(OWLDataPropertyExpression property, OWLOntology ontology);

    /**
     * Gets the data property values that are explicitly asserted NOT to hold
     * for this individual
     * @param ontology The ontology that should be examined for axioms
     * @return A map containing the negative data property values
     */
    Map<OWLDataPropertyExpression, Set<OWLLiteral>> getNegativeDataPropertyValues(OWLOntology ontology);

    /**
     * Test whether a specific value for a specific data property has been asserted not to hold for this individual.
     * @param property The property to test for
     * @param literal The value to test for
     * @param ontology The ontology to search for the assertion
     * @return <code>true</code> if the specified property value has explicitly been asserted not to hold, that is,
     * <code>true</code> if the specified ontology contains a negative data property assertion
     * NegativeDataPropertyAssertion(property, this, literal), otherwise <code>false</code>
     */
    boolean hasNegativeDataPropertyValue(OWLDataPropertyExpression property, OWLLiteral literal, OWLOntology ontology);

    /**
     * A convenience method that examines axioms in the specified ontology
     * to determine the individuals that are asserted to be the same as
     * this individual.
     * @return Individuals that have been asserted to be the same as this individual.
     */
    Set<OWLIndividual> getSameIndividuals(OWLOntology ontology);


    /**
     * A convenience method that examines axioms in the specified ontology
     * to determine the individuals that are asserted to be different
     * to this individual.
     * @param ontology
     * @return
     */
    Set<OWLIndividual> getDifferentIndividuals(OWLOntology ontology);

    /**
     * Returns a string representation that can be used as the ID of this individual.  This is the toString
     * representation of the node ID of this individual
     * @return A string representing the toString of the node ID of this entity.
     */
    String toStringID();


    void accept(OWLIndividualVisitor visitor);

    <O> O accept(OWLIndividualVisitorEx<O> visitor);
}
