/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, Clark & Parsia, LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, Clark & Parsia, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarkparsia.owlapi.explanation;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import com.clarkparsia.owlapi.explanation.util.DefinitionTracker;

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
