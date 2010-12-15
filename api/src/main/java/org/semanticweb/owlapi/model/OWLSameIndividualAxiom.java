package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * <p/>
 * Represents an <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Individual_Equality">SameIndividual</a> axiom in the OWL 2 Specification.
 */
public interface OWLSameIndividualAxiom extends OWLNaryIndividualAxiom {

    OWLSameIndividualAxiom getAxiomWithoutAnnotations();

    /**
     * Determines whether this axiom contains anonymous individuals.  Anonymous individuals are not allowed in
     * same individuals axioms.
     * @return <code>true</code> if this axioms contains anonymous individual axioms
     */
    boolean containsAnonymousIndividuals();

    /**
     * Returns this axiom represented as set of {@link org.semanticweb.owlapi.model.OWLSubClassOfAxiom}s.
     * @return This axiom represented as a set of {@link org.semanticweb.owlapi.model.OWLSubClassOfAxiom}s.
     */
    Set<OWLSameIndividualAxiom> asPairwiseAxioms();
}
