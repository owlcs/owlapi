package uk.ac.manchester.cs.factplusplusad;

import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
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
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

/**
 * update signature by adding the signature of a given axiom to it
 */
class TSignatureUpdater implements OWLAxiomVisitor {

    /**
     * helper with expressions
     */
    TExpressionSignatureUpdater updater;

    /**
     * init c'tor
     *
     * @param sig signature
     */
    TSignatureUpdater(Signature sig) {
        updater = new TExpressionSignatureUpdater(sig);
    }

    void v(OWLObject e) {
        e.accept(updater);
    }

    void v(Stream<? extends OWLObject> begin) {
        begin.forEach(this::v);
    }

    // visitor interface
    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        v(axiom.getEntity());
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        v(axiom.operands());
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        v(axiom.operands());
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        v(axiom.getOWLClass());
        v(axiom.classExpressions());
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        v(axiom.operands());
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        v(axiom.operands());
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        v(axiom.operands());
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        v(axiom.operands());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        v(axiom.operands());
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        v(axiom.operands());
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        v(axiom.getFirstProperty());
        v(axiom.getSecondProperty());
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        v(axiom.getSuperProperty());
        v(axiom.getSubProperty());
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        v(axiom.getSuperProperty());
        v(axiom.getSubProperty());
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        v(axiom.getProperty());
        v(axiom.getDomain());
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        v(axiom.getProperty());
        v(axiom.getDomain());
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        v(axiom.getProperty());
        v(axiom.getRange());
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        v(axiom.getProperty());
        v(axiom.getRange());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        v(axiom.getProperty());
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        v(axiom.getProperty());
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        v(axiom.getProperty());
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        v(axiom.getProperty());
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        v(axiom.getProperty());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        v(axiom.getProperty());
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        v(axiom.getProperty());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        v(axiom.getProperty());
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        v(axiom.getSubClass());
        v(axiom.getSuperClass());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        v(axiom.getIndividual());
        v(axiom.getClassExpression());
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        v(axiom.getSubject());
        v(axiom.getProperty());
        v(axiom.getObject());
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        v(axiom.getSubject());
        v(axiom.getProperty());
        v(axiom.getObject());
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        v(axiom.getSubject());
        v(axiom.getProperty());
        v(axiom.getObject());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        v(axiom.getSubject());
        v(axiom.getProperty());
        v(axiom.getObject());
    }
}
