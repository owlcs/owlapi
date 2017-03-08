package org.semanticweb.owlapitools.decomposition;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * semantic locality checker for DL axioms
 */
public class SemanticLocalityChecker implements OWLAxiomVisitor, LocalityChecker {

    /**
     * Reasoner to detect the tautology
     */
    OWLReasoner kernel;
    OWLDataFactory df;
    OWLReasonerFactory factory;
    /**
     * map between axioms and concept expressions
     */
    Multimap<OWLAxiom, OWLClassExpression> exprMap = LinkedHashMultimap.create();
    /**
     * remember the axiom locality value here
     */
    boolean isLocal;
    /**
     * signature to keep
     */
    private Signature sig = new Signature();
    private OWLOntologyManager manager;

    /**
     * init c'tor
     *
     * @param f reasoner factory
     * @param m manager
     */
    public SemanticLocalityChecker(OWLReasonerFactory f, OWLOntologyManager m) {
        factory = f;
        manager = m;
        df = manager.getOWLDataFactory();
        isLocal = true;
    }

    /**
     * init c'tor
     *
     * @param r reasoner
     */
    public SemanticLocalityChecker(OWLReasoner r) {
        kernel = r;
        manager = r.getRootOntology().getOWLOntologyManager();
        df = manager.getOWLDataFactory();
        isLocal = true;
    }

    /**
     * @param axiom axiom
     * @return expression necessary to build query for a given type of an axiom
     */
    protected Stream<OWLClassExpression> getExpr(OWLAxiom axiom) {
        return axiom.nestedClassExpressions();
    }

    @Override
    public Signature getSignature() {
        return sig;
    }

    /**
     * set a new value of a signature (without changing a locality parameters)
     */
    @Override
    public void setSignatureValue(Signature sig) {
        this.sig = sig;
    }

    @Override
    public boolean local(OWLAxiom axiom) {
        axiom.accept(this);
        return isLocal;
    }

    /* init kernel with the ontology signature */
    @Override
    public void preprocessOntology(Collection<AxiomWrapper> axioms) {
        exprMap.clear();
        Signature s = new Signature();
        for (AxiomWrapper q : axioms) {
            if (q.isUsed()) {
                exprMap.putAll(q.getAxiom(), asList(getExpr(q.getAxiom())));
                s.addAll(q.getAxiom().signature());
            }
        }
        // register all the objects in the ontology signature
        Set<OWLAxiom> declarationAxioms = new HashSet<>();
        for (OWLEntity p : s.getSignature()) {
            declarationAxioms.add(df.getOWLDeclarationAxiom(p));
        }
        try {
            kernel = factory.createReasoner(manager.createOntology(declarationAxioms));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
        kernel.precomputeInferences(InferenceType.CLASS_HIERARCHY);
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        isLocal = true;
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        isLocal = false;
        List<OWLClassExpression> arguments = asList(axiom.classExpressions());
        int size = arguments.size();
        OWLClassExpression c = arguments.get(0);
        for (int i = 1; i < size; i++) {
            OWLClassExpression p = arguments.get(i);
            if (!kernel.isEntailed(df.getOWLEquivalentClassesAxiom(c, p))) {
                return;
            }
        }
        isLocal = true;
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        isLocal = false;
        List<OWLClassExpression> arguments = asList(axiom.classExpressions());
        int size = arguments.size();
        for (int i = 0; i < size; i++) {
            OWLClassExpression p = arguments.get(i);
            for (int j = i + 1; j < size; j++) {
                OWLClassExpression q = arguments.get(j);
                if (!kernel.isEntailed(df.getOWLDisjointClassesAxiom(p, q))) {
                    return;
                }
            }
        }
        isLocal = true;
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        isLocal = false;
        // check A = (or C1... Cn)
        if (!kernel.isEntailed(df.getOWLEquivalentClassesAxiom(axiom.getOWLClass(),
            df.getOWLObjectIntersectionOf(axiom.classExpressions())))) {
            return;
        }
        // check disjoint(C1...Cn)
        List<? extends OWLClassExpression> arguments = asList(axiom.classExpressions());
        int size = arguments.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (!kernel.isEntailed(df.getOWLDisjointClassesAxiom(arguments.get(i),
                    arguments.get(j)))) {
                    return;
                }
            }
        }
        isLocal = true;
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        isLocal = false;
        List<OWLObjectPropertyExpression> arguments = asList(axiom.properties());
        int size = arguments.size();
        OWLObjectPropertyExpression r = arguments.get(0);
        for (int i = 1; i < size; i++) {
            if (!(kernel.isEntailed(df.getOWLSubObjectPropertyOfAxiom(r, arguments.get(i)))
                && kernel.isEntailed(df.getOWLSubObjectPropertyOfAxiom(arguments.get(i),
                r)))) {
                return;
            }
        }
        isLocal = true;
    }

    // tautology if all the subsumptions Ri [= Rj holds
    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        isLocal = false;
        List<OWLDataPropertyExpression> arguments = asList(axiom.properties());
        OWLDataPropertyExpression r = arguments.get(0);
        for (int i = 1; i < arguments.size(); i++) {
            if (!(kernel.isEntailed(df.getOWLSubDataPropertyOfAxiom(r, arguments.get(i))) && kernel
                .isEntailed(df.getOWLSubDataPropertyOfAxiom(arguments.get(i), r)))) {
                return;
            }
        }
        isLocal = true;
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    // never local
    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        isLocal = false;
    }

    // never local
    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        isLocal = false;
    }

    // R = inverse(S) is tautology iff R [= S- and S [= R-
    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        isLocal = kernel.isEntailed(df.getOWLSubObjectPropertyOfAxiom(axiom.getFirstProperty(),
            axiom.getSecondProperty().getInverseProperty()))
            && kernel.isEntailed(df.getOWLSubObjectPropertyOfAxiom(
            axiom.getFirstProperty().getInverseProperty(),
            axiom.getSecondProperty()));
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    /**
     * Domain(R) = C is tautology iff ER.Top [= C
     */
    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        isLocal = true;
        for (OWLClassExpression e : exprMap.get(axiom)) {
            isLocal &= kernel.isEntailed(df.getOWLSubClassOfAxiom(e, axiom.getDomain()));
        }
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        isLocal = true;
        for (OWLClassExpression e : exprMap.get(axiom)) {
            isLocal &= kernel.isEntailed(df.getOWLSubClassOfAxiom(e, axiom.getDomain()));
        }
    }

    /**
     * Range(R) = C is tautology iff ER.~C is unsatisfiable
     */
    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        isLocal = true;
        for (OWLClassExpression e : exprMap.get(axiom)) {
            isLocal &= !kernel.isSatisfiable(e);
        }
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        isLocal = true;
        for (OWLClassExpression e : exprMap.get(axiom)) {
            isLocal &= !kernel.isSatisfiable(e);
        }
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    /**
     * for top locality, this might be local
     */
    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    /**
     * R(i,j) holds if {i} [= \ER.{j}
     */
    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    /**
     * !R(i,j) holds if {i} [= \AR.!{j}=!\ER.{j}
     */
    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    /**
     * R(i,v) holds if {i} [= \ER.{v}
     */
    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        isLocal = kernel.isEntailed(axiom);
    }

    @Override
    public boolean isTopEquivalent(OWLObject expr) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isBotEquivalent(OWLObject expr) {
        // TODO Auto-generated method stub
        return false;
    }
}
