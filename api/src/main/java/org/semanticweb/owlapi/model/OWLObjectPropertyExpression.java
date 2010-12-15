package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group Date: 24-Oct-2006
 */
public interface OWLObjectPropertyExpression extends OWLPropertyExpression<OWLClassExpression, OWLObjectPropertyExpression>, SWRLPredicate {

    /**
     * Determines if the specified ontology specifies that this property is inverse functional.
     *
     * @param ontology The ontology to be tested for an inverse functional property axiom.
     * @return <code>true</code> if the property is inverse functional, or <code>false</code> if the property is not
     *         inverse functional.
     */
    boolean isInverseFunctional(OWLOntology ontology);


    boolean isInverseFunctional(Set<OWLOntology> ontologies);


    boolean isSymmetric(OWLOntology ontology);


    boolean isSymmetric(Set<OWLOntology> ontologies);


    boolean isAsymmetric(OWLOntology ontology);


    boolean isAsymmetric(Set<OWLOntology> ontologies);


    boolean isReflexive(OWLOntology ontology);


    boolean isReflexive(Set<OWLOntology> ontologies);


    boolean isIrreflexive(OWLOntology ontology);


    boolean isIrreflexive(Set<OWLOntology> ontologies);


    boolean isTransitive(OWLOntology ontology);


    boolean isTransitive(Set<OWLOntology> ontologies);


    Set<OWLObjectPropertyExpression> getInverses(OWLOntology ontology);


    Set<OWLObjectPropertyExpression> getInverses(Set<OWLOntology> ontologies);


    /**
     * If the property is a named object property then this method will obtain the property as such.  The general
     * pattern of use is that the <code>isAnonymous</code> method should first be used to determine if the property is
     * named (i.e. not an object property expression such as inv(p)).  If the property is named then this method may be
     * used to obtain the property as a named property without casting.
     *
     * @return The property as an <code>OWLObjectProperty</code> if possible.
     * @throws OWLRuntimeException if the property is not a named property.
     */
    OWLObjectProperty asOWLObjectProperty();


    /**
     * Obtains the property that corresponds to the inverse of this property.
     *
     * @return The inverse of this property.  Note that this property will not necessarily be in the simplest form.
     */
    OWLObjectPropertyExpression getInverseProperty();


    /**
     * Returns this property in its simplified form.
     *
     * @return Let p be a property name and PE an object property expression. The simplification, 'simp', is defined as
     *         follows: simp(p) = p simp(inv(p)) = inv(p) simp(inv(inv(PE)) = simp(PE)
     */
    OWLObjectPropertyExpression getSimplified();


    /**
     * Get the named object property used in this property expression.
     *
     * @return P if simp(PE) = inv(P) or P if simp(PE) = P.
     */
    OWLObjectProperty getNamedProperty();
}
