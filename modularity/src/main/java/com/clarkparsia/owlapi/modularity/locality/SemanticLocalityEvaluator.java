package com.clarkparsia.owlapi.modularity.locality;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;


/**
 * <p/>
 * Title: </p>
 * <p/>
 * Description: </p>
 * <p/>
 * Copyright: Copyright (c) 2007 </p>
 * <p/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> </p>
 * @author Evren Sirin
 */
public class SemanticLocalityEvaluator implements LocalityEvaluator {


    public static final Logger log = Logger.getLogger(SemanticLocalityEvaluator.class.getName());

    protected OWLDataFactory df;

    private AxiomLocalityVisitor axiomVisitor = new AxiomLocalityVisitor();

    private BottomReplacer bottomReplacer = new BottomReplacer();

    private OWLReasoner reasoner;


    public SemanticLocalityEvaluator(OWLOntologyManager man, OWLReasonerFactory reasonerFactory) {
        this.df = man.getOWLDataFactory();
        try {
            reasoner = reasonerFactory.createNonBufferingReasoner(man.createOntology());
        }
        catch (Exception e) {
            throw new OWLRuntimeException(e);
        }
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


        @Override
		public void visit(OWLDisjointClassesAxiom axiom) {
            //XXX this seems wrong, doesn't use the input and isLocal can only possibly be set to true
        	Set<OWLClassExpression> disjClasses = axiom.getClassExpressions();
            OWLClassExpression conjunction = df.getOWLObjectIntersectionOf(disjClasses);

            if (log.isLoggable(Level.FINE))
                log.fine("Calling the Reasoner");

            isLocal = !reasoner.isSatisfiable(df.getOWLNothing());

            if (log.isLoggable(Level.FINE))
                log.fine("DONE Calling the Reasoner. isLocal = " + isLocal);
        }


        @Override
		public void visit(OWLEquivalentClassesAxiom axiom) {
            Set<OWLClassExpression> eqClasses = axiom.getClassExpressions();
            if (eqClasses.size() != 2)
                return;

            //Iterator<OWLClassExpression> iter = eqClasses.iterator();

            if (log.isLoggable(Level.FINE))
                log.fine("Calling the Reasoner");

            isLocal = reasoner.isEntailed(axiom);

            if (log.isLoggable(Level.FINE))
                log.fine("DONE Calling the Reasoner. isLocal = " + isLocal);
        }


        @Override
		public void visit(OWLSubClassOfAxiom axiom) {

            if (log.isLoggable(Level.FINE))
                log.fine("Calling the Reasoner");

            isLocal = reasoner.isEntailed(axiom);

            if (log.isLoggable(Level.FINE))
                log.fine("DONE Calling the Reasoner. isLocal = " + isLocal);
        }
    }

    @SuppressWarnings("unused")
    private class BottomReplacer extends OWLAxiomVisitorAdapter implements OWLAxiomVisitor, OWLClassExpressionVisitor {

        private OWLAxiom newAxiom;

        private OWLClassExpression newClassExpression;

        private Set<? extends OWLEntity> signature;


        public OWLAxiom getResult() {
            return newAxiom;
        }


        public OWLAxiom replaceBottom(OWLAxiom axiom, Set<? extends OWLEntity> signature) {
            reset(signature);
            axiom.accept(this);
            return getResult();
        }


        // Takes an OWLClassExpression and a signature replaces by bottom the
        // entities not in the signature

        public OWLClassExpression replaceBottom(OWLClassExpression desc) {
            newClassExpression = null;
            desc.accept(this);

            if (newClassExpression == null)
                throw new RuntimeException("Unsupported class expression " + desc);

            return newClassExpression;
        }


        public Set<OWLClassExpression> replaceBottom(Set<OWLClassExpression> classExpressions) {
            Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
            for (OWLClassExpression desc : classExpressions) {
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
                newClassExpression = desc;
            else
                newClassExpression = df.getOWLNothing();
        }


        public void visit(OWLDataAllValuesFrom desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty()))
                newClassExpression = desc;
            else
                newClassExpression = df.getOWLThing();
        }


        public void visit(OWLDataExactCardinality desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty()))
                newClassExpression = desc;
            else
                newClassExpression = df.getOWLNothing();
        }


        public void visit(OWLDataMaxCardinality desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty()))
                newClassExpression = desc;
            else
                newClassExpression = df.getOWLThing();
        }


        public void visit(OWLDataMinCardinality desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty()))
                newClassExpression = desc;
            else
                newClassExpression = df.getOWLNothing();
        }


        public void visit(OWLDataSomeValuesFrom desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty()))
                newClassExpression = desc;
            else
                newClassExpression = df.getOWLNothing();
        }


        public void visit(OWLDataHasValue desc) {
            throw new RuntimeException();
        }


        @Override
		public void visit(OWLDisjointClassesAxiom ax) {
            Set<OWLClassExpression> disjointclasses = replaceBottom(ax.getClassExpressions());
            newAxiom = df.getOWLDisjointClassesAxiom(disjointclasses);
        }


        @Override
		public void visit(OWLEquivalentClassesAxiom ax) {
            Set<OWLClassExpression> eqclasses = replaceBottom(ax.getClassExpressions());
            newAxiom = df.getOWLEquivalentClassesAxiom(eqclasses);
        }


        public void visit(OWLObjectAllValuesFrom desc) {
            if (signature.contains(desc.getProperty().getNamedProperty()))
                newClassExpression = df.getOWLObjectAllValuesFrom(desc.getProperty(), replaceBottom(desc.getFiller()));
            else
                newClassExpression = df.getOWLThing();
        }


        public void visit(OWLObjectComplementOf desc) {
            newClassExpression = df.getOWLObjectComplementOf(replaceBottom(desc.getOperand()));
        }


        public void visit(OWLObjectExactCardinality desc) {
            if (signature.contains(desc.getProperty().getNamedProperty()))
                newClassExpression = desc;
            else
                newClassExpression = df.getOWLNothing();
        }


        public void visit(OWLObjectIntersectionOf desc) {
            Set<OWLClassExpression> operands = desc.getOperands();
            newClassExpression = df.getOWLObjectIntersectionOf(replaceBottom(operands));
        }


        public void visit(OWLObjectMaxCardinality desc) {
            if (signature.contains(desc.getProperty().getNamedProperty()))
                newClassExpression = desc;
            else
                newClassExpression = df.getOWLThing();
        }


        public void visit(OWLObjectMinCardinality desc) {
            if (signature.contains(desc.getProperty().getNamedProperty()))
                newClassExpression = desc;
            else
                newClassExpression = df.getOWLNothing();
        }


        public void visit(OWLObjectOneOf desc) {
            throw new RuntimeException();
        }


        public void visit(OWLObjectHasSelf desc) {
            throw new RuntimeException();
        }


        public void visit(OWLObjectSomeValuesFrom desc) {
            if (signature.contains(desc.getProperty().getNamedProperty())) {
                newClassExpression = df.getOWLObjectSomeValuesFrom(desc.getProperty(), replaceBottom(desc.getFiller()));
            }
            else
                newClassExpression = df.getOWLNothing();
        }


        public void visit(OWLObjectUnionOf desc) {
            Set<OWLClassExpression> operands = desc.getOperands();
            newClassExpression = df.getOWLObjectUnionOf(replaceBottom(operands));
        }


        public void visit(OWLObjectHasValue desc) {
            throw new RuntimeException();
        }


        @Override
		public void visit(OWLSubClassOfAxiom ax) {
            OWLClassExpression sup = replaceBottom(ax.getSuperClass());
            OWLClassExpression sub = replaceBottom(ax.getSubClass());
            newAxiom = df.getOWLSubClassOfAxiom(sub, sup);
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
