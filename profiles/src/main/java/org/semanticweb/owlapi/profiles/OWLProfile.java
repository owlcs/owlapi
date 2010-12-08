package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Apr-2008<br><br>
 *
 */
public interface OWLProfile {

    /**
     * Gets the name of the profile.
     * @return A string that represents the name of the profile
     */
    String getName();


    /**
     * Checks an ontology and its import closure to see if it is within
     * this profile.
     * @param ontology The ontology to be checked.
     * @return An <code>OWLProfileReport</code> that describes whether or not the
     * ontology is within this profile.
     */
    OWLProfileReport checkOntology(OWLOntology ontology);
    
    
}
