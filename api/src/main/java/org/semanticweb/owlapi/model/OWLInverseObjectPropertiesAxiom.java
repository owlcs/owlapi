package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 29-Nov-2006<br><br>
 * <p/>
 * Represents an <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Inverse_Object_Properties_2">InverseObjectProperties</a> axiom in the OWL 2 Specification.
 * <p/>
 * Represents a statement that two properties are the inverse of each other.  This
 * property axiom contains a set of two properties.  inverseOf(P, Q) is considered
 * to be equal to inverseOf(Q, P) - i.e. the order in which the properties are specified
 * isn't important
 */
public interface OWLInverseObjectPropertiesAxiom extends OWLNaryPropertyAxiom<OWLObjectPropertyExpression>, OWLObjectPropertyAxiom {

    /**
     * Gets the first of the two object properties.
     */
    OWLObjectPropertyExpression getFirstProperty();


    /**
     * Gets the second of the two object properties.
     */
    OWLObjectPropertyExpression getSecondProperty();

    Set<OWLSubObjectPropertyOfAxiom> asSubObjectPropertyOfAxioms();

    OWLInverseObjectPropertiesAxiom getAxiomWithoutAnnotations();
}
