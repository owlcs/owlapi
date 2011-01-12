package org.semanticweb.owlapi.util;

import java.util.List;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiomChange;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeVisitor;
import org.semanticweb.owlapi.model.RemoveAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 17-Dec-2006<br><br>
 * <p/>
 * Provides a convenient method to filter add/remove axiom changes based
 * on the type of axiom that is being added or removed from an ontology.
 * <p/>
 * The general pattern of use is to simply create an instance of the <code>OWLOntologyChangeFilter</code>
 * and override the appropriate visit methods corresponding to the types of axioms that are of interest.
 * Each visit corresponds to a single change and the <code>isAdd</code> or <code>isRemove</code> methods can
 * be used to determine if the axiom corresponding to the change is being added or removed from an ontology
 * - the ontology can be obtained via the <code>getOntology</code> method.
 * <p/>
 * Example:  Suppose we are interested in changes that alter the domain of an object property.  We receive
 * a list of changes, <code>ontChanges</code>, from an ontology change listener.  We can use the
 * <code>OWLOntologyChangeFilter</code> to filter out the changes that alter the domain of an object
 * property in the following way:
 * <p/>
 * <pre>
 * OWLOntologyChangeFilter filter = new OWLOntologyChangeFilter() {
 * <p/>
 *      // Override the object property domain visit method
 *      public void visit(OWLObjectPropertyDomainAxiom axiom) {
 *          // Determine if the axiom is being added or removed
 *          if(isAdd()) {
 *              // Get hold of the ontology that the change applied to
 *              OWLOntology ont = getOntology();
 *              // Do something here
 *          }
 *      }
 * }
 * // Process the list of changes
 * filter.processChanges(ontChanges);
 * </pre>
 */
public class OWLOntologyChangeFilter extends OWLAxiomVisitorAdapter implements OWLAxiomVisitor {

    private boolean add;

    private OWLOntology ontology;

    private OWLOntologyChangeVisitor changeVisitor;


    public OWLOntologyChangeFilter() {
        changeVisitor = new OWLOntologyChangeVisitorAdapter() {
            @Override
			public void visit(AddAxiom change) {
                add = true;
                processChange(change);
            }


            @Override
			public void visit(RemoveAxiom change) {
                add = false;
                processChange(change);
            }
        };
    }


    final public void processChanges(List<? extends OWLOntologyChange> changes) {
        for (OWLOntologyChange change : changes) {
            change.accept(changeVisitor);
        }
    }


    private void processChange(OWLAxiomChange change) {
        ontology = change.getOntology();
        change.getAxiom().accept(this);
        ontology = null;
    }


    /**
     * Determines if the current change caused an axiom to be added to an ontology.
     */
    final protected boolean isAdd() {
        return add;
    }


    /**
     * Determines if the current change caused an axiom to be removed from an ontology.
     */
    final protected boolean isRemove() {
        return !add;
    }


    /**
     * Gets the ontology which the current change being visited was applied to.
     *
     * @return The ontology or <code>null</code> if the filter is not in a change
     *         visit cycle.  When called from within a <code>visit</code> method, the
     *         return value is guarenteed not to be <code>null</code>.
     */
    final protected OWLOntology getOntology() {
        return ontology;
    }
}
