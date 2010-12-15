package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * <p/>
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Individual_Inequality">DifferentIndividuals</a> axiom in the OWL 2 Specification.
 */
public interface OWLDifferentIndividualsAxiom extends OWLNaryIndividualAxiom {

    OWLDifferentIndividualsAxiom getAxiomWithoutAnnotations();


    /**
     * Determines whether this axiom contains anonymous individuals.  Anonymous individuals are not allowed in
     * different individuals axioms.
     * @return <code>true</code> if this axioms contains anonymous individual axioms
     */
    boolean containsAnonymousIndividuals();

    Set<OWLDifferentIndividualsAxiom> asPairwiseAxioms();
}
