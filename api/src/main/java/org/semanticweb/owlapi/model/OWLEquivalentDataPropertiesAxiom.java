package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * <p/>
 * Represents an <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Equivalent_Data_Properties">EquivalentDataProperties</a> axiom in the OWL 2 Specification.
 */
public interface OWLEquivalentDataPropertiesAxiom extends OWLNaryPropertyAxiom<OWLDataPropertyExpression>, OWLDataPropertyAxiom {

    OWLEquivalentDataPropertiesAxiom getAxiomWithoutAnnotations();
    Set<OWLSubDataPropertyOfAxiom> asSubDataPropertyOfAxioms();
}
