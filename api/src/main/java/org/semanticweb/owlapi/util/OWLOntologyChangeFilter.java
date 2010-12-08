package org.semanticweb.owlapi.util;

import java.util.List;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiomChange;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeVisitor;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.model.SWRLRule;


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
public class OWLOntologyChangeFilter implements OWLAxiomVisitor {

    private boolean add;

    private OWLOntology ontology;

    private OWLOntologyChangeVisitor changeVisitor;


    public OWLOntologyChangeFilter() {
        changeVisitor = new OWLOntologyChangeVisitorAdapter() {
            public void visit(AddAxiom change) {
                add = true;
                processChange(change);
            }


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


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
    }

    public void visit(OWLClassAssertionAxiom axiom) {
    }

    public void visit(OWLSubPropertyChainOfAxiom axiom) {
    }

    public void visit(OWLDataPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLDataPropertyDomainAxiom axiom) {
    }

    public void visit(OWLDataPropertyRangeAxiom axiom) {
    }

    public void visit(OWLDeclarationAxiom axiom) {
    }

    public void visit(OWLDifferentIndividualsAxiom axiom) {
    }

    public void visit(OWLDisjointClassesAxiom axiom) {
    }

    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
    }

    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
    }

    public void visit(OWLDisjointUnionAxiom axiom) {
    }

    public void visit(OWLEquivalentClassesAxiom axiom) {
    }

    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
    }

    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
    }

    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
    }

    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
    }

    public void visit(OWLHasKeyAxiom axiom) {
    }

    public void visit(OWLImportsDeclaration axiom) {
    }

    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
    }

    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
    }

    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
    }

    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLObjectPropertyDomainAxiom axiom) {
    }

    public void visit(OWLObjectPropertyRangeAxiom axiom) {
    }

    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }

    public void visit(OWLSameIndividualAxiom axiom) {
    }

    public void visit(OWLSubClassOfAxiom axiom) {
    }

    public void visit(OWLSubDataPropertyOfAxiom axiom) {
    }

    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
    }

    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
    }

    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
    }

    public void visit(SWRLRule rule) {
    }

    public void visit(OWLAnnotationAssertionAxiom axiom) {
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
    }

    public void visit(OWLDatatypeDefinitionAxiom axiom) {
    }
}
