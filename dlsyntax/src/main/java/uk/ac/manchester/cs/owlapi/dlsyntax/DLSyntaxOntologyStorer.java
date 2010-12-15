package uk.ac.manchester.cs.owlapi.dlsyntax;

import org.semanticweb.owlapi.model.OWLOntologyFormat;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Feb-2008<br><br>
 */
public class DLSyntaxOntologyStorer extends DLSyntaxOntologyStorerBase {

    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat.equals(new DLSyntaxOntologyFormat());
    }
}
