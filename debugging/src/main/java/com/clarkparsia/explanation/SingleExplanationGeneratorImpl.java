package com.clarkparsia.explanation;

import java.util.Set;

import org.semanticweb.owl.inference.OWLClassReasoner;
import org.semanticweb.owl.inference.OWLReasonerFactory;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

import com.clarkparsia.explanation.util.DefinitionTracker;

/**
 * <p/>
 * Title: SingleExplanationGeneratorImpl
 * </p>
 * <p/>
 * Description: An abstract implementation of SingleExplanationGenerator that
 * can be used as the basis for different explanation generator techniques.
 * </p>
 * <p/>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com>
 * </p>
 * @author Evren Sirin
 */
public abstract class SingleExplanationGeneratorImpl implements TransactionAwareSingleExpGen {

	private boolean inTransaction;
	
    protected OWLOntologyManager owlOntologyManager;

//    protected OWLOntology ontology;

    protected OWLClassReasoner reasoner;

    protected OWLReasonerFactory reasonerFactory;

    protected DefinitionTracker definitionTracker;

    protected OWLClassReasoner altReasoner;

    public SingleExplanationGeneratorImpl(OWLOntologyManager manager) {
        this.owlOntologyManager = manager;

        definitionTracker = new DefinitionTracker(manager);
    }


    public OWLOntologyManager getOntologyManager() {
        return owlOntologyManager;
    }


    public void setOntology(OWLOntology ontology) {
        this.altReasoner = null;

        definitionTracker.setOntology(ontology);
    }
    
    public void setOntologies(Set<OWLOntology> ontologies) {
        this.altReasoner = null;
    	
    	definitionTracker.setOntologies( ontologies );
    }


    public OWLClassReasoner getReasoner() {
        return reasoner;
    }


    public void setReasoner(OWLClassReasoner reasoner) {
        this.reasoner = reasoner;
        this.altReasoner = null;
    }

    /**
     * {@inheritDoc}
     */
    public OWLOntology getOntology() {
        return getOntologies().iterator().next();
    }
    
    public Set<OWLOntology> getOntologies() {
        return definitionTracker.getOntologies();
    }
    
    public OWLReasonerFactory getReasonerFactory() {
        return reasonerFactory;
    }


    public void setReasonerFactory(OWLReasonerFactory reasonerFactory) {
        this.reasonerFactory = reasonerFactory;
        this.altReasoner = null;
    }


    public OWLClassReasoner getAltReasoner() {
        if (altReasoner == null) {
            altReasoner = reasonerFactory.createReasoner(owlOntologyManager);
        }

        return altReasoner;
    }


    protected boolean isFirstExplanation() {
    	return !inTransaction;
    }
    
    public void beginTransaction() {
		if (inTransaction)
			throw new RuntimeException( "Already in transaction" );
		
		inTransaction = true;
	}

	public void endTransaction() {
		if (!inTransaction)
			throw new RuntimeException( "Cannot end transaction" );
		
		inTransaction = false;
	}
}
