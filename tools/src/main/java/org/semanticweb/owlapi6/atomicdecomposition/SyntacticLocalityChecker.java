package org.semanticweb.owlapi6.atomicdecomposition;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.semanticweb.owlapi6.model.HasOperands;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLAxiomVisitor;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.SWRLRule;

/**
 * syntactic locality checker for DL axioms
 */
public class SyntacticLocalityChecker implements OWLAxiomVisitor, LocalityChecker {

    /**
     * top evaluator
     */
    TopEquivalenceEvaluator topEval;
    /**
     * bottom evaluator
     */
    BotEquivalenceEvaluator botEval;
    /**
     * remember the axiom locality value here
     */
    boolean isLocal;
    private Signature sig = new Signature();

    /**
     * init c'tor
     */
    public SyntacticLocalityChecker() {
        topEval = new TopEquivalenceEvaluator(this);
        botEval = new BotEquivalenceEvaluator(this);
    }

    @Override
    public boolean isTopEquivalent(OWLObject expr) {
        return topEval.isTopEquivalent(expr);
    }

    @Override
    public boolean isBotEquivalent(OWLObject expr) {
        return botEval.isBotEquivalent(expr);
    }

    @Override
    public Signature getSignature() {
        return sig;
    }

    @Override
    public void setSignatureValue(Signature sig) {
        this.sig = sig;
    }

    // set fields
    @Override
    public boolean local(OWLAxiom axiom) {
        axiom.accept(this);
        return isLocal;
    }

    private <T extends OWLObject> boolean processEquivalentAxiom(HasOperands<T> axiom) {
        // 1 element => local
        if (axiom.getOperandsAsList().size() <= 1) {
            return true;
        }
        // axiom is local iff all the elements are either top- or bot-local
        List<T> args = axiom.getOperandsAsList();
        Boolean pos = null;
        for (OWLObject arg : args) {
            if (pos == null) {
                // setup polarity of an equivalence
                if (isTopEquivalent(arg)) {
                    pos = Boolean.TRUE;
                } else if (isBotEquivalent(arg)) {
                    pos = Boolean.FALSE;
                } else {
                    return false;
                }
            } else {
                if (pos.booleanValue() && !isTopEquivalent(arg) || !pos.booleanValue() && !isBotEquivalent(arg)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Processing method for all Disjoint axioms.
     * 
     * @param <T> generic type of input
     * @param axiom axiom
     * @return true if axiom is local
     */
    private <T extends OWLObject> boolean processDisjointAxiom(HasOperands<T> axiom) {
        // local iff at most 1 element is not bot-equiv
        boolean hasNBE = false;
        Iterator<T> it = axiom.operands().iterator();
        while (it.hasNext()) {
            T p = it.next();
            if (!isBotEquivalent(p)) {
                if (hasNBE) {
                    // already seen one non-bot-eq element
                    return false;
                } else {
                    // record that 1 non-bot-eq element was found
                    hasNBE = true;
                }
            }
        }
        return true;
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        isLocal = true;
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        isLocal = processEquivalentAxiom(axiom);
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        isLocal = processDisjointAxiom(axiom);
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        // DisjointUnion(A, C1,..., Cn) is local if
        // (1) A and all of Ci are bot-equivalent,
        // or (2) A and one Ci are top-equivalent and the remaining Cj are
        // bot-equivalent
        isLocal = false;
        boolean lhsIsTopEq;
        if (isTopEquivalent(axiom.getOWLClass())) {
            // need to check (2)
            lhsIsTopEq = true;
        } else if (isBotEquivalent(axiom.getOWLClass())) {
            // need to check (1)
            lhsIsTopEq = false;
        } else {
            // neither (1) nor (2)
            return;
        }
        boolean topEqDesc = false;
        for (OWLClassExpression p : axiom.getOperandsAsList()) {
            if (!isBotEquivalent(p)) {
                if (lhsIsTopEq && isTopEquivalent(p)) {
                    if (topEqDesc) {
                        // 2nd top in there -- violate (2) -- non-local
                        return;
                    } else {
                        topEqDesc = true;
                    }
                } else {
                    // either (1) or fail to have a top-eq for (2)
                    return;
                }
            }
        }
        // check whether for (2) we found a top-eq concept
        if (lhsIsTopEq && !topEqDesc) {
            return;
        }
        // it is local in the end!
        isLocal = true;
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        isLocal = processEquivalentAxiom(axiom);
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        isLocal = processEquivalentAxiom(axiom);
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        isLocal = processDisjointAxiom(axiom);
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        isLocal = processDisjointAxiom(axiom);
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        isLocal = false;
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        isLocal = false;
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        OWLObjectPropertyExpression p1 = axiom.getFirstProperty();
        OWLObjectPropertyExpression p2 = axiom.getSecondProperty();
        isLocal = isBotEquivalent(p1) && isBotEquivalent(p2) || isTopEquivalent(p1) && isTopEquivalent(p2);
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        isLocal = isTopEquivalent(axiom.getSuperProperty()) || isBotEquivalent(axiom.getSubProperty());
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        isLocal = isTopEquivalent(axiom.getSuperProperty()) || isBotEquivalent(axiom.getSubProperty());
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        isLocal = isTopEquivalent(axiom.getDomain()) || isBotEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        isLocal = isTopEquivalent(axiom.getDomain()) || isBotEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        isLocal = isTopEquivalent(axiom.getRange()) || isBotEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        isLocal = isTopEquivalent(axiom.getRange()) || isBotEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        isLocal = isBotEquivalent(axiom.getProperty()) || isTopEquivalent(axiom.getProperty());
    }

    /**
     * as BotRole is irreflexive, the only local axiom is topEquivalent(R)
     */
    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        isLocal = isTopEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        isLocal = isBotEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        isLocal = isBotEquivalent(axiom.getProperty()) || isTopEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        isLocal = isBotEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        isLocal = isBotEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        isLocal = isBotEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        isLocal = isBotEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        isLocal = isBotEquivalent(axiom.getSubClass()) || isTopEquivalent(axiom.getSuperClass());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        isLocal = isTopEquivalent(axiom.getClassExpression());
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        isLocal = isTopEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        isLocal = isBotEquivalent(axiom.getProperty());
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        isLocal = isTopEquivalent(axiom.getObject());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        isLocal = isBotEquivalent(axiom.getObject());
    }

    @Override
    public void preprocessOntology(Collection<AxiomWrapper> s) {
        sig = new Signature();
        s.forEach(ax -> sig.addAll(ax.getAxiom().signature()));
    }

    // TODO verify the following
    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        isLocal = true;
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        isLocal = true;
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        isLocal = true;
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        isLocal = true;
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        isLocal = true;
        if (isTopEquivalent(axiom.getSuperProperty())) {
            return;
        }
        for (OWLObjectPropertyExpression R : axiom.getPropertyChain()) {
            if (isBotEquivalent(R)) {
                return;
            }
        }
        isLocal = false;
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        isLocal = true;
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        isLocal = true;
    }

    @Override
    public void visit(SWRLRule rule) {
        isLocal = true;
    }
}
