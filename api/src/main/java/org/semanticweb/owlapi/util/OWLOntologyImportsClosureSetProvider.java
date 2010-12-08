package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologySetProvider;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Apr-2007<br><br>
 * <p/>
 * An <code>OWLOntologySetProvider</code> which provides a set of ontologies
 * which correspond to the imports closure of a given ontology.  Note that
 * the set of provided ontologies will be updated if the imports closure gets
 * updated.
 */
public class OWLOntologyImportsClosureSetProvider implements OWLOntologySetProvider {

    private OWLOntologyManager manager;

    private OWLOntology rootOntology;


    /**
     * Constructs an <code>OWLOntologySetProvider</code> which provides a set containing the imports
     * closure of a given ontology.
     * @param manager The manager which should be used to determine the imports closure.
     * @param rootOntology The ontology which is the "root" of the imports closure.
     */
    public OWLOntologyImportsClosureSetProvider(OWLOntologyManager manager, OWLOntology rootOntology) {
        this.manager = manager;
        this.rootOntology = rootOntology;
    }


    public Set<OWLOntology> getOntologies() {
        return manager.getImportsClosure(rootOntology);
    }
}
