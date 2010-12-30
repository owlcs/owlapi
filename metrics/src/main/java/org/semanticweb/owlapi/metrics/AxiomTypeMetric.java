package org.semanticweb.owlapi.metrics;

import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class AxiomTypeMetric extends AxiomCountMetric {

    private AxiomType<?> axiomType;


    public AxiomTypeMetric(OWLOntologyManager owlOntologyManager, AxiomType<?> axiomType) {
        super(owlOntologyManager);
        this.axiomType = axiomType;
    }


    @Override
	protected String getObjectTypeName() {
        return axiomType.getName() + " axioms";
    }


    @Override
	protected Set<? extends OWLAxiom> getObjects(OWLOntology ont) {
        return ont.getAxioms(axiomType);
    }


    public AxiomType<?> getAxiomType() {
        return axiomType;
    }
}
