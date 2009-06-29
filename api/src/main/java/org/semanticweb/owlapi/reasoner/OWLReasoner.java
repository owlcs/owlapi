package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.Set;/*
 * Copyright (C) 2008, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 21-Jan-2009
 *
 * <p>
 * An OWLReasoner reasons over a set of ontologies.  The set of ontologies is defined at
 * reasoner creation time and remains fixed from then on.  The set of ontologies can be obtained using the
 * {@link #getOntologies()} method.  When the client responsible for creating the reasoner has finished with the
 * reasoner instance it must call the {@link #dispose()} method to free any resources that are used by the reasoner.
 * In general, reasoners should not be instantiated directly, but should be created using the appropriate
 * {@link org.semanticweb.owlapi.reasoner.OWLReasonerFactory}.
 * </p>
 * <p>
 * At creation time, an OWLReasoner will attach itself as a listener to the {@link org.semanticweb.owlapi.model.OWLOntologyManager}
 * that manages the ontologies contained within the reasoner.  The reasoner will listen to any
 * {@link org.semanticweb.owlapi.model.OWLOntologyChange}s and respond to these so that any queries that are asked after
 * the ontology changes are answered with respect to the changed ontologies.
 * </p>
 */
public interface OWLReasoner {

    /**
     * Gets the set of ontologies that this reasoner operates on.
     * @return The set of ontologies that the reasoner operates on.  This corresponds to the imports closure of
     * the ontology that was specified at the time when the reasoner was created.
     */
    Set<OWLOntology> getOntologies();

    /**
     * Asks the reasoner to interrupt what it is currently doing.  An InterruptedException will be thrown in the
     * thread that invoked the last reasoner operation.
     */
    void interrupt();

    /**
     * A convenience method that determines if the set of reasoner ontologies (the set of ontologies returned by
     * the {@link #getOntologies()} method) is consistent.
     * @return <code>true</code> if the set of reasoner ontologies is consistent,
     * or <code>false</code> if the set of reasoner ontologies is inconsistent.
     * @throws ExpressivenessOutOfScopeException If the set of reasoner ontologies contains axioms that are
     * out of the scope of expressiveness that is supported by this reasoner
     * @throws InterruptedException if the reasoning process was interrupted for any particular reason (for example if
     * reasoning was cancelled by a client process)
     */
    boolean isConsistent() throws InterruptedException;

    /**
     * A convenience method that determines if the specified class expression is satisfiable with respect to the
     * set of reasoner ontologies (the set of ontologies returned by the {@link #getOntologies()} method)
     * @param classExpression The class expression
     * @return <code>true</code> if classExpression is satisfiable with respect to the set of reasoner ontologies, or
     * <code>false</code> if classExpression is unsatisfiable with respect to the set of reasoner ontologies.
     * @throws InconsistentOntologiesException if the reasoner's axiom set is inconsistent
     * @throws EntitiesNotInSignatureException if the signature of the classExpression is not contained within the signature
     * of the reasoner's axiom set.
     * @throws ExpressivenessOutOfScopeException If the class expression contains constructs that are out of the scope
     * of expressiveness that is supported by this reasoner, or if the reasoner's axiom set contains axioms that are
     * out of the scope of expressiveness that is supported by this reasoner
     * @throws InterruptedException if the reasoning process was interrupted for any particular reason (for example if
     * reasoning was cancelled by a client process)
     */
    boolean isSatisfiable(OWLClassExpression classExpression) throws InterruptedException;


    /**
     * A convenience method that determines if the specified axiom is entailed by the set of reasoner ontologies
     * (the set of ontologies returned by the {@link #getOntologies()} method)
     * @param axiom The axiom
     * @return <code>true</code> if {@code axiom} is entailed by the reasoner ontologies or <code>false</code> if
     * {@code axiom} is not entailed by the reasoner ontologies.
     * @throws InterruptedException if the reasoning process was interupped for any particular reason (for example if
     * reasoning was cancelled by a client process).
     * @throws UnsupportedEntailmentTypeException if the reasoner cannot perform a check to see if the specified
     * axiom is entailed
     */
    boolean isEntailed(OWLAxiom axiom) throws InterruptedException, UnsupportedEntailmentTypeException;

    /**
     * Asks the reasoner to answer a query.
     * @param query The query to be answered.  The specific type of query defines the query semantics and what answers
     * are expected.
     * @return The query answer.  The type of query specifies the semantics of the query and the query answers.
     * @throws UnsupportedQueryTypeException if this reasoner cannot answer the type of query being asked
     * @throws InconsistentOntologiesException if the reasoner's axiom set is inconsistent
     * @throws EntitiesNotInSignatureException if the signature of the query is not contained within the signature
     * of the reasoner's axiom set.
     * @throws ExpressivenessOutOfScopeException If the reasoner's axiom set contains axioms that are
     * out of the scope of expressiveness that is supported by this reasoner
     * @throws InterruptedException if the reasoning process was interrupted for any particular reason (for example if
     * reasoning was cancelled by a client process)
     */
    <R> R answerQuery(Query<R> query) throws UnsupportedQueryTypeException, InterruptedException;

    /**
     * Disposes of this reasoner.  This frees up any resources used by the reasoner and detaches the reasoner
     * as an {@link org.semanticweb.owlapi.model.OWLOntologyChangeListener} from the {@link org.semanticweb.owlapi.model.OWLOntologyManager}
     * that manages the ontologies contained within the reasoner.
     */
    void dispose();

    

}
