package org.semanticweb.owlapi.model;

import java.util.List;
import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 */
public interface OWLNaryIndividualAxiom extends OWLIndividualAxiom, OWLNaryAxiom, OWLSubClassOfAxiomSetShortCut {

    Set<OWLIndividual> getIndividuals();

    /**
     * Gets the individuals returned by {@link #getIndividuals()} as a list.
     * @return The individuals in this axiom as a list
     */
    List<OWLIndividual> getIndividualsAsList();

}
