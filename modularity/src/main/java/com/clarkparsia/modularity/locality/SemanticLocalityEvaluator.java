package com.clarkparsia.modularity.locality;

import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.inference.OWLReasonerFactory;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.OWLAxiomVisitorAdapter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * <p/>
 * Title: </p>
 * <p/>
 * Description: </p>
 * <p/>
 * Copyright: Copyright (c) 2007 </p>
 * <p/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> </p>
 *
 * @author Evren Sirin
 */
public class SemanticLocalityEvaluator implements LocalityEvaluator {


    public static final Logger log = Logger
            .getLogger(SemanticLocalityEvaluator.class
                    .getName());

    private OWLDataFactory df;

    private AxiomLocalityVisitor axiomVisitor = new AxiomLocalityVisitor();

    private BottomReplacer bottomReplacer = new BottomReplacer();

    private OWLReasoner reasoner;


    public SemanticLocalityEvaluator(OWLOntologyManager man, OWLReasonerFactory reasonerFactory) {
        this.df = man.getOWLDataFactory();
        reasoner = reasonerFactory.createReasoner(man);
    }


    private class AxiomLocalityVisitor extends OWLAxiomVisitorAdapter implements OWLAxiomVisitor {

        private boolean isLocal;


        public AxiomLocalityVisitor() {
        }


        public boolean isLocal() {
            return isLocal;
        }


        public boolean isLocal(OWLAxiom axiom) {
            reset();
            axiom.accept(this);
            return isLocal();
        }


        public void reset() {
            isLocal = false;
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            Set<OWLDescription> disjClasses = axiom.getDescriptions();
            OWLDescription conjunction = df.getOWLObjectIntersectionOf(disjClasses);

            if (log.isLoggable(Level.FINE))
                log.fine("Calling the Reasoner");

            try {
                isLocal = reasoner.isEquivalentClass(conjunction, df.getOWLNothing());
            }
            catch (OWLReasonerException e) {
                throw new OWLRuntimeException(e);
            }

            if (log.isLoggable(Level.FINE))
                log.fine("DONE Calling the Reasoner. isLocal = " + isLocal);
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            Set<OWLDescription> eqClasses = axiom.getDescriptions();
            if (eqClasses.size() != 2)
                return;

            Iterator<OWLDescription> iter = eqClasses.iterator();
            OWLDescription first = iter.next();
            OWLDescription second = iter.next();

            if (log.isLoggable(Level.FINE))
                log.fine("Calling the Reasoner");

            try {
                isLocal = reasoner.isEquivalentClass(first, second);
            }
            catch (OWLReasonerException e) {
                throw new OWLRuntimeException(e);
            }

            if (log.isLoggable(Level.FINE))
                log.fine("DONE Calling the Reasoner. isLocal = " + isLocal);
        }


        public void visit(OWLSubClassAxiom axiom) {
            OWLDescription sup = axiom.getSuperClass();
            OWLDescription sub = axiom.getSubClass();

            if (log.isLoggable(Level.FINE))
                log.fine("Calling the Reasoner");

            try {
                isLocal = reasoner.isSubClassOf(sub, sup);
            }
            catch (OWLReasonerException e) {
                throw new OWLRuntimeException(e);
            }

            if (log.isLoggable(Level.FINE))
                log.fine("DONE Calling the Reasoner. isLocal = " + isLocal);
        }
    }


    private class BottomReplacer extends OWLAxiomVisitorAdapter implements OWLAxiomVisitor, OWLDescriptionVisitor {

        private OWLAxiom newAxiom;

        private OWLDescription newDescription;

        private Set<? extends OWLEntity> signature;


        public OWLAxiom getResult() {
            return newAxiom;
        }


        public OWLAxiom replaceBottom(OWLAxiom axiom, Set<? extends OWLEntity> signature) {
            reset(signature);
            axiom.accept(this);
            return getResult();
        }


        // Takes an OWLDescription and a signature replaces by bottom the
        // entities not in the signature
        public OWLDescription replaceBottom(OWLDescription desc) {
            newDescription = null;
            desc.accept(this);

            if (newDescription == null)
                throw new RuntimeException("Unsupported description " + desc);

            return newDescription;
        }


        public Set<OWLDescription> replaceBottom(Set<OWLDescription> descriptions) {
            Set<OWLDescription> result = new HashSet<OWLDescription>();
            for (OWLDescription desc : descriptions) {
                result.add(replaceBottom(desc));
            }
            return result;
        }


        public void reset(Set<? extends OWLEntity> signature) {
            this.signature = signature;
            this.newAxiom = null;
        }


        public void visit(OWLClass desc) {
            if (signature.contains(desc))
                newDescription = desc;
            else
                newDescription = df.getOWLNothing();
        }


        public void visit(OWLDataAllRestriction desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty()))
                newDescription = desc;
            else
                newDescription = df.getOWLThing();
        }


        public void visit(OWLDataExactCardinalityRestriction desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty()))
                newDescription = desc;
            else
                newDescription = df.getOWLNothing();
        }


        public void visit(OWLDataMaxCardinalityRestriction desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty()))
                newDescription = desc;
            else
                newDescription = df.getOWLThing();
        }


        public void visit(OWLDataMinCardinalityRestriction desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty()))
                newDescription = desc;
            else
                newDescription = df.getOWLNothing();
        }


        public void visit(OWLDataSomeRestriction desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty()))
                newDescription = desc;
            else
                newDescription = df.getOWLNothing();
        }


        public void visit(OWLDataValueRestriction desc) {
            throw new RuntimeException();
        }


        public void visit(OWLDisjointClassesAxiom ax) {
            Set<OWLDescription> disjointclasses = replaceBottom(ax.getDescriptions());
            newAxiom = df.getOWLDisjointClassesAxiom(disjointclasses);
        }


        public void visit(OWLEquivalentClassesAxiom ax) {
            Set<OWLDescription> eqclasses = replaceBottom(ax.getDescriptions());
            newAxiom = df.getOWLEquivalentClassesAxiom(eqclasses);
        }


        public void visit(OWLObjectAllRestriction desc) {
            if (signature.contains(desc.getProperty().getNamedProperty()))
                newDescription = df.getOWLObjectAllRestriction(desc.getProperty(), replaceBottom(desc.getFiller()));
            else
                newDescription = df.getOWLThing();
        }


        public void visit(OWLObjectComplementOf desc) {
            newDescription = df.getOWLObjectComplementOf(replaceBottom(desc.getOperand()));
        }


        public void visit(OWLObjectExactCardinalityRestriction desc) {
            if (signature.contains(desc.getProperty().getNamedProperty()))
                newDescription = desc;
            else
                newDescription = df.getOWLNothing();
        }


        public void visit(OWLObjectIntersectionOf desc) {
            Set<OWLDescription> operands = desc.getOperands();
            newDescription = df.getOWLObjectIntersectionOf(replaceBottom(operands));
        }


        public void visit(OWLObjectMaxCardinalityRestriction desc) {
            if (signature.contains(desc.getProperty().getNamedProperty()))
                newDescription = desc;
            else
                newDescription = df.getOWLThing();
        }


        public void visit(OWLObjectMinCardinalityRestriction desc) {
            if (signature.contains(desc.getProperty().getNamedProperty()))
                newDescription = desc;
            else
                newDescription = df.getOWLNothing();
        }


        public void visit(OWLObjectOneOf desc) {
            throw new RuntimeException();
        }


        public void visit(OWLObjectSelfRestriction desc) {
            throw new RuntimeException();
        }


        public void visit(OWLObjectSomeRestriction desc) {
            if (signature.contains(desc.getProperty().getNamedProperty())) {
                newDescription = df.getOWLObjectSomeRestriction(desc.getProperty(),
                                                                    replaceBottom(desc.getFiller()));
            }
            else
                newDescription = df.getOWLNothing();
        }


        public void visit(OWLObjectUnionOf desc) {
            Set<OWLDescription> operands = desc.getOperands();
            newDescription = df.getOWLObjectUnionOf(replaceBottom(operands));
        }


        public void visit(OWLObjectValueRestriction desc) {
            throw new RuntimeException();
        }


        public void visit(OWLSubClassAxiom ax) {
            OWLDescription sup = replaceBottom(ax.getSuperClass());
            OWLDescription sub = replaceBottom(ax.getSubClass());
            newAxiom = df.getOWLSubClassAxiom(sub, sup);
        }
    }


    /**
     * True if the axiom is semantically local w.r.t. given signature
     */
    public boolean isLocal(OWLAxiom axiom, Set<? extends OWLEntity> signature) {

        if (log.isLoggable(Level.FINE))
            log.fine("Replacing axiom by Bottom");

        OWLAxiom newAxiom = bottomReplacer.replaceBottom(axiom, signature);

        if (log.isLoggable(Level.FINE))
            log.fine("DONE Replacing axiom by Bottom. Success: " + (newAxiom != null));

        return newAxiom != null && axiomVisitor.isLocal(newAxiom);
    }
}
