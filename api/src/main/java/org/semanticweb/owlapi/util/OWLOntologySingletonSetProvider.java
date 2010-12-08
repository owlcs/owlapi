package org.semanticweb.owlapi.util;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologySetProvider;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Apr-2007<br><br>
 * <p/>
 * An ontology set provider which provides a singleton set - i.e. a set containing
 * just one ontology.
 */
public class OWLOntologySingletonSetProvider implements OWLOntologySetProvider {

    private Set<OWLOntology> ontologySingletonSet;


    /**
     * Constructs an <code>OWLOntologySingletonSetProvider</code> which provides a singleton
     * set contain the specified ontology.
     * @param ontology The one and only ontology which should be contained in the sets provided
     *                 by this provider.
     */
    public OWLOntologySingletonSetProvider(OWLOntology ontology) {
        ontologySingletonSet = Collections.singleton(ontology);
    }


    public Set<OWLOntology> getOntologies() {
        return ontologySingletonSet;
    }
}
