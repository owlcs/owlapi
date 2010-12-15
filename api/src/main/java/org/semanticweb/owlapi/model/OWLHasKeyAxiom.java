package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 15-Jan-2009
 * <p/>
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Keys">HasKey</a> axiom in the OWL 2 Specification.
 */
public interface OWLHasKeyAxiom extends OWLLogicalAxiom {

    /**
     * Gets the class expression, instances of which, this axiom acts as the key for
     * @return The class expression
     */
    OWLClassExpression getClassExpression();

    /**
     * Gets the set of property expressions that form the key
     * @return The set of property expression that form the key
     */
    Set<OWLPropertyExpression> getPropertyExpressions();

    /**
     * Gets the set of object property expressions that make up the key.  This is simply a convenience method that
     * filteres out the object property expressions in the key.  All of the properties returned by this method are
     * included in the return value of the {@link OWLHasKeyAxiom#getPropertyExpressions()} method.
     * @return The set of object property expressions in the key described by this axiom
     */
    Set<OWLObjectPropertyExpression> getObjectPropertyExpressions();

    /**
     * Gets the set of data property expressions that make up the key.  This is simply a convenience method that
     * filteres out the data property expressions in the key.  All of the properties returned by this method are
     * included in the return value of the {@link OWLHasKeyAxiom#getPropertyExpressions()} method.
     * @return The set of object property expressions in the key described by this axiom
     */
    Set<OWLDataPropertyExpression> getDataPropertyExpressions();

    OWLHasKeyAxiom getAxiomWithoutAnnotations();
}
