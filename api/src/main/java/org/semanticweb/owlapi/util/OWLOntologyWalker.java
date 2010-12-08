package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 29-Jul-2008<br><br>
 */
public class OWLOntologyWalker extends OWLObjectWalker<OWLOntology> {

    public OWLOntologyWalker(Set<OWLOntology> objects) {
        super(objects);
    }
}
