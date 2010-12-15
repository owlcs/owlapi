package com.clarkparsia.owlapi.explanation;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import com.clarkparsia.owlapi.explanation.util.DefinitionTracker;

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
	
    private OWLOntologyManager owlOntologyManager;

    private OWLOntology ontology;

    private OWLReasoner reasoner;

    private OWLReasonerFactory reasonerFactory;

    private DefinitionTracker definitionTracker;

    public SingleExplanationGeneratorImpl(OWLOntology ontology, OWLReasonerFactory reasonerFactory, OWLReasoner reasoner) {
        this.ontology = ontology;
        this.reasonerFactory = reasonerFactory;
        this.reasoner = reasoner;
        this.owlOntologyManager = ontology.getOWLOntologyManager();
        definitionTracker = new DefinitionTracker(ontology);
    }


    public OWLOntologyManager getOntologyManager() {
        return owlOntologyManager;
    }

    public OWLReasoner getReasoner() {
        return reasoner;
    }

    public DefinitionTracker getDefinitionTracker() {
        return definitionTracker;
    }

    /**
     * {@inheritDoc}
     */
    public OWLOntology getOntology() {
        return ontology;
    }

    public OWLReasonerFactory getReasonerFactory() {
        return reasonerFactory;
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
