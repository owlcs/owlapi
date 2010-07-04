package org.semanticweb.owlapi.reasoner;

/*
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
 * An OWLReasonerConfiguration can be used to customise the setup of a reasoner at reasoner creation time via
 * an {@link org.semanticweb.owlapi.reasoner.OWLReasonerFactory}. Specific
 * reasoners may define their own configuration objects with configuration options particular to the reasoner.
 * There are also a set of general options defined by this configuration object.
 * </p>
 * Note that once a reasoner has been created, changing fields (via setter methods or directly) on the configuration
 * object will have no effect.
 * @see {@link org.semanticweb.owlapi.reasoner.OWLReasonerFactory}
 * @see {@link org.semanticweb.owlapi.reasoner.SimpleConfiguration}
 */
public interface OWLReasonerConfiguration {

    /**
     * Gets a progress monitor that the reasoner may update with information about the progress of its reasoning
     * process.
     * @return A progress monitor.  By default this returns {@link NullReasonerProgressMonitor}
     */
    ReasonerProgressMonitor getProgressMonitor();

    /**
     * Gets the timeout in milliseconds for basic single reasoner operations (satisfiability check time out).  If the
     * value is equal to {@link Long#MAX_VALUE} then this means that the reasoner should never time out.
     * </p>
     * The reasoner will monitor the elapsed time during a satisfiability check (attempt to build a model for example)
     * and if the elapsed time exceeds the timeout then it will abort the test as soon as possible and terminate
     * all reasoning.  A {@link org.semanticweb.owlapi.reasoner.TimeOutException} will be thrown in the thread that
     * invoked the last reasoner operation.
     * </p>
     * Note that this is not a timeout for method calls such as "getSubClasses", which may involve many satisfiability
     * (or other basic reasoning task) checks, the sum of which may well exceed the timeout.
     * @return The time out in milliseconds.  By default this is set
     * to the value of {@link Long#MAX_VALUE}, which means the reasoner SHOULD NOT timeout.
     */
    long getTimeOut();

    /**
     * Gets the fresh entity policy that should be used.  By default this is set to
     * {@link FreshEntityPolicy#ALLOW}.
     * @return The fresh entity policy.
     */
    FreshEntityPolicy getFreshEntityPolicy();

    /**
     * Gets the {@link org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy} which determines how <code>NodeSet</code>s
     * of named individuals are returned from the reasoner.
     * @return The <code>IndividualNodeSetPolicy</code> that should be used.  By default the policy is
     * {@link org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy#BY_NAME} i.e. by default individuals that are
     * the same as each other are not grouped into the same node within a node set.
     */
    IndividualNodeSetPolicy getIndividualNodeSetPolicy();


}
