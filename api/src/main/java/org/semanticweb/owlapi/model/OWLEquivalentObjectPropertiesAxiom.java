package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * </p>
 * Represents <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Equivalent_Object_Properties">EquivalentObjectProperties</a> axioms in the OWL 2 specification. 
 */
public interface OWLEquivalentObjectPropertiesAxiom extends OWLNaryPropertyAxiom<OWLObjectPropertyExpression>, OWLObjectPropertyAxiom {

    Set<OWLSubObjectPropertyOfAxiom> asSubObjectPropertyOfAxioms();

    OWLEquivalentObjectPropertiesAxiom getAxiomWithoutAnnotations();
}
