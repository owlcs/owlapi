package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 30-May-2008<br><br>
 */
public class ImportsStructureEntitySorter extends ImportsStructureObjectSorter<OWLEntity> {


    public ImportsStructureEntitySorter(OWLOntology ontology, OWLOntologyManager manager) {
        super(ontology, manager, new ReferencedEntitySelector());
    }

    public static class ReferencedEntitySelector implements ObjectSelector<OWLEntity> {


        public Set<OWLEntity> getObjects(OWLOntology ontology) {
            return ontology.getSignature();
        }
    }
}
