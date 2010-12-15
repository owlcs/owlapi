package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * <p/>
 * Represents an <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Enumeration_of_Individuals">ObjectOneOf</a> class expression in the OWL 2 Specification.
 */
public interface OWLObjectOneOf extends OWLAnonymousClassExpression {

    /**
     * Gets the individuals that are in the oneOf.  These
     * individuals represent the exact instances (extension) of
     * this class expression.
     * @return The individiauls that are the values of this <code>ObjectOneOf</code> class expression.
     */
    Set<OWLIndividual> getIndividuals();


    /**
     * Simplifies this enumeration to a union of singleton nominals
     * @return This enumeration in a more standard DL form.
     *         simp({a}) = {a}
     *         simp({a0, ... , {an}) = unionOf({a0}, ... , {an})
     */
    OWLClassExpression asObjectUnionOf();
}
