package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 12-Dec-2006<br><br>
 */
public abstract class OWLAxiomChange extends OWLOntologyChange {

    private OWLAxiom axiom;


    public OWLAxiomChange(OWLOntology ont, OWLAxiom axiom) {
        super(ont);
        this.axiom = axiom;
    }

    @Override
	public boolean isAxiomChange() {
        return true;
    }


    /**
     * Determines if this change is an import change
     * @return <code>true</code> if this change is an import change, otherwise <code>false</code>.
     */
    @Override
	public boolean isImportChange() {
        return false;
    }


    /**
     * Determines if the change will add an axiom to an ontology,
     * or remove an axiom from an ontology.
     * @return <code>true</code> if the change will add an axiom
     *         to an ontology, <code>false</code> if the change will remove
     *         an axiom from an ontology.
     */
    protected abstract boolean isAdd();


    /**
     * Gets the axiom that is involved in the change (the
     * axiom to either be added or removed)
     */
    @Override
	public OWLAxiom getAxiom() {
        return axiom;
    }


    /**
     * A convenience method that obtains the entities which are
     * referenced in the axiom contained within this change.
     * @return A <code>Set</code> of entities which are referenced
     *         by the axiom contained within this change.
     */
    public Set<OWLEntity> getEntities() {
    	return axiom.getSignature();
    }
}
