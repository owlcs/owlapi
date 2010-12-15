package org.semanticweb.owlapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.RemoveAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Jul-2007<br><br>
 * Given a set of ontologies, this composite change will remove all disjoint
 * classes axioms from these ontologies.
 */
public class RemoveAllDisjointAxioms extends AbstractCompositeOntologyChange {

    private Set<OWLOntology> ontologies;

    private List<OWLOntologyChange> changes;

    public RemoveAllDisjointAxioms(OWLDataFactory dataFactory, Set<OWLOntology> ontologies) {
        super(dataFactory);
        this.ontologies = ontologies;
        generateChanges();
    }

    private void generateChanges() {
        changes = new ArrayList<OWLOntologyChange>();
        for (OWLOntology ont : ontologies) {
            for (OWLClassAxiom ax : ont.getAxioms(AxiomType.DISJOINT_CLASSES)) {
                changes.add(new RemoveAxiom(ont, ax));
            }
        }
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }
}
