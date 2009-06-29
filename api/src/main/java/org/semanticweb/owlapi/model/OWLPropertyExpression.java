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
 * Represents a property or possibly the inverse of a property.
 */
public interface OWLPropertyExpression<P extends OWLPropertyExpression, R extends OWLPropertyRange> extends OWLObject {

    /**
     * Gets the asserted domains of this property.
     * @param ontology The ontology that should be examined for axioms which
     *                 assert a domain of this property
     * @return A set of <code>OWLClassExpression</code>s corresponding to the
     *         domains of this property (the domain of the property is essentially the
     *         intersection of these class expressions).
     */
    Set<OWLClassExpression> getDomains(OWLOntology ontology);


    /**
     * Gets the asserted domains of this property by examining the
     * axioms in the specified ontologies.
     * @param ontologies The ontologies to be examined.
     * @return A set of <code>OWLClassExpression</code>s that represent the asserted
     *         domains of this property.
     */
    Set<OWLClassExpression> getDomains(Set<OWLOntology> ontologies);


    /**
     * Gets the ranges of this property that have been asserted in the specified ontology.
     * @param ontology The ontology to be searched for axioms which assert a range
     *                 for this property.
     * @return A set of ranges for this property.
     */
    Set<R> getRanges(OWLOntology ontology);


    /**
     * Gets the asserted ranges of this property by examining the axioms in the
     * specified ontologies.
     * @param ontologies The ontologies to be examined for range axioms.
     * @return A set of ranges for this property, which have been asserted
     *         by axioms in the specified ontologies.
     */
    Set<R> getRanges(Set<OWLOntology> ontologies);


    /**
     * Gets the asserted/told super properties by examining the axioms
     * in the specified ontology.
     * @param ontology The ontology which will be examined for subproperty axioms.
     * @return A set of properties which represent the explicitly asserted/told superproperties
     *         of this property.
     */
    Set<P> getSuperProperties(OWLOntology ontology);


    /**
     * Gets the asserted super properties by examining the axioms in the
     * specified set of ontologies.
     * @param ontologies The ontologies which will bne examined for axioms
     * @return A set of properties which represent the asserted super properties
     *         of this property.
     */
    Set<P> getSuperProperties(Set<OWLOntology> ontologies);


    /**
     * Gets the asserted/told sub properties by examining the axioms
     * in the specified ontology.
     * @param ontology The ontology which will be examined for subproperty axioms.
     * @return A set of properties which represent the explicitly asserted/told subproperties
     *         of this property.
     */
    Set<P> getSubProperties(OWLOntology ontology);


    /**
     * Gets the asserted sub properties by examining the axioms
     * in the specified ontologies.
     * @param ontologies The ontologies which will be examined for subproperty axioms.
     * @return A set of properties which represent the explicitly asserted subproperties
     *         of this property.
     */
    Set<P> getSubProperties(Set<OWLOntology> ontologies);


    /**
     * Gets the asserted/told equivalent properties by examining the axioms
     * in the specified ontology.
     * @param ontology The ontology which will be examined for equivalent properties axioms.
     * @return A set of properties which represent the explicitly asserted/told equivalent properties
     *         of this property.
     */
    Set<P> getEquivalentProperties(OWLOntology ontology);


    /**
     * Gets the asserted equivalent properties by examining the axioms
     * in the specified ontologies.
     * @param ontologies The ontologies which will be examined for equivalent properties axioms.
     * @return A set of properties which represent the explicitly asserted/told equivalent properties
     *         of this property.
     */
    Set<P> getEquivalentProperties(Set<OWLOntology> ontologies);


    Set<P> getDisjointProperties(OWLOntology ontology);

    Set<P> getDisjointProperties(Set<OWLOntology> ontologies);

    /**
     * Determines if this property is functional in the specified
     * ontology
     * @param ontology The ontology to be tested for a functional
     *                 property axiom.
     * @return <code>true</code> if the specified ontology contains
     *         an axiom stating that the property is functional, other wise
     *         <code>false</code>.
     */
    boolean isFunctional(OWLOntology ontology);


    /**
     * Determines if the property is functional because there
     * is an axiom in one of the specified ontologies that assert this
     * to be the case.
     * @param ontologies The ontologies which will be searched for axioms
     *                   which specify that this property is fuctional.
     * @return <code>true</code> if the property is functional, or <code>false</code>
     *         if the property is not functional.
     */
    boolean isFunctional(Set<OWLOntology> ontologies);


    /**
     * Determines if this property expression is anonymous.
     * @return <code>true</code> if the property expression is anonymous
     *         (because it is the inverse of a property). <code>false</code>
     *         if this property is a named object property or named data property.
     */
    public boolean isAnonymous();


    void accept(OWLPropertyExpressionVisitor visitor);

    <O> O accept(OWLPropertyExpressionVisitorEx<O> visitor);

    boolean isDataPropertyExpression();

    boolean isObjectPropertyExpression();

    /**
     * Determines if this is the owl:topObjectProperty
     * @return <code>true</code> if this property is the owl:topObjectProperty otherwise <code>false</code>
     */
    boolean isOWLTopObjectProperty();

    /**
     * Determines if this is the owl:bottomObjectProperty
     * @return <code>true</code> if this property is the owl:bottomObjectProperty otherwise <code>false</code>
     */
    boolean isOWLBottomObjectProperty();

    /**
     * Determines if this is the owl:topDataProperty
     * @return <code>true</code> if this property is the owl:topDataProperty otherwise <code>false</code>
     */
    boolean isOWLTopDataProperty();


    /**
     * Determines if this is the owl:bottomDataProperty
     * @return <code>true</code> if this property is the owl:bottomDataProperty otherwise <code>false</code>
     */
    boolean isOWLBottomDataProperty();
}
