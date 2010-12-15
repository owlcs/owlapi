package com.clarkparsia.owlapi.explanation;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;


/**
 * <p/>
 * Description: The explanation generator interface for returning a single
 * explanation for an unsatisfiable class. Use {@link SatisfiabilityConverter}
 * to convert an arbitrary axiom into a class description that can be used to
 * generate explanations for that axiom.
 * </p>
 * <p/>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com>
 * </p>
 * @author Evren Sirin
 */
public interface SingleExplanationGenerator {

    /**
     * Get the ontology manager for this explanation generator.
     */
    public OWLOntologyManager getOntologyManager();

//	/**
//	 * Sets the set of ontologies according to which the explanations are
//	 * generated to the import closure of the given ontologies.
//	 */
//    public void setOntology(OWLOntology ontology);
//
//    /**
//     * Sets the set of ontologies according to which the explanations are generated.
//     */
//    public void setOntologies(Set<OWLOntology> ontologies);

	/**
	 * Gets the ontologies according to which the explanations are generated
     * @return ont
	 */
    public OWLOntology getOntology();
//
//    /**
//     * Returns the set of ontologies according to which the explanations are generated.
//     */
//    public Set<OWLOntology> getOntologies();


//    /**
//     * Sets the reasoner that will be used to generate explanations. This
//     * function is provided in addition to
//     * {@link #setReasonerFactory(OWLReasonerFactory)} because the reasoning
//     * results already computed by the given reasoner can be reused. It is
//     * guaranteed that the state of this reasoner will not be invalidated by
//     * explanation generation, i.e. if the reasoner was in classified state it
//     * will stay in classified state.
//     */
//    public void setReasoner(OWLReasoner reasoner);


    /**
     * Returns the reasoner associated with this generator.
     */
    public OWLReasoner getReasoner();


//    /**
//     * Sets the reasoner factory that will be used to generate fresh reasoners.
//     * We create new reasoner instances to avoid invalidating the reasoning
//     * state of existing reasoners. Explanation generation process will modify
//     * the original ontology and/or reason over a subset of the original
//     * ontology. Using an alternate fresh reasoner for these tasks ensures
//     * efficient explanation generation without side effects to anything outside
//     * the explanation generator.
//     */
//    public void setReasonerFactory(OWLReasonerFactory reasonerFactory);


    /**
     * Returns the reasoner factory used to create fresh reasoners.
     */
    public OWLReasonerFactory getReasonerFactory();


    /**
     * Get a single explanation for an arbitrary class expression, or empty set
     * if the given expression is satisfiable.
     * @param unsatClass arbitrary class expression whose unsatisfiability will be
     *                   explained
     * @return set of axioms explaining the unsatisfiability of given class
     *         expression, or empty set if the given expression is satisfiable.
     */
    public Set<OWLAxiom> getExplanation(OWLClassExpression unsatClass);
}
