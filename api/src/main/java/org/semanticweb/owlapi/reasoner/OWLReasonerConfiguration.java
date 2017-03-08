/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.reasoner;

import java.io.Serializable;

/**
 * An OWLReasonerConfiguration can be used to customise the setup of a reasoner at reasoner creation
 * time via an {@link org.semanticweb.owlapi.reasoner.OWLReasonerFactory}. Specific reasoners may
 * define their own configuration objects with configuration options particular to the reasoner.
 * There are also a set of general options defined by this configuration object. <br>
 * Note that once a reasoner has been created, changing fields (via setter methods or directly) on
 * the configuration object will have no effect.
 *
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @see org.semanticweb.owlapi.reasoner.OWLReasonerFactory
 * @see org.semanticweb.owlapi.reasoner.SimpleConfiguration
 * @since 3.0.0
 */
public interface OWLReasonerConfiguration extends Serializable {

    /**
     * Gets a progress monitor that the reasoner may update with information about the progress of
     * its reasoning process.
     *
     * @return A progress monitor. By default this returns {@link NullReasonerProgressMonitor}
     */
    ReasonerProgressMonitor getProgressMonitor();

    /**
     * Gets the timeout in milliseconds for basic single reasoner operations (satisfiability check
     * time out). If the value is equal to {@link Long#MAX_VALUE} then this means that the reasoner
     * should never time out. <br>
     * The reasoner will monitor the elapsed time during a satisfiability check (attempt to build a
     * model for example) and if the elapsed time exceeds the timeout then it will abort the test as
     * soon as possible and terminate all reasoning. A
     * {@link org.semanticweb.owlapi.reasoner.TimeOutException} will be thrown in the thread that
     * invoked the last reasoner operation. <br>
     * Note that this is not a timeout for method calls such as "getSubClasses", which may involve
     * many satisfiability (or other basic reasoning task) checks, the sum of which may well exceed
     * the timeout.
     *
     * @return The time out in milliseconds. By default this is set to the value of {@link
     * Long#MAX_VALUE}, which means the reasoner SHOULD NOT timeout.
     */
    long getTimeOut();

    /**
     * Gets the fresh entity policy that should be used. By default this is set to
     * {@link FreshEntityPolicy#ALLOW}.
     *
     * @return The fresh entity policy.
     */
    FreshEntityPolicy getFreshEntityPolicy();

    /**
     * Gets the {@link org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy} which determines how
     * {@code NodeSet}s of named individuals are returned from the reasoner.
     *
     * @return The {@code IndividualNodeSetPolicy} that should be used. By default the policy is
     * {@link org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy#BY_NAME} i.e. by default
     * individuals that are the same as each other are not grouped into the same node within a node
     * set.
     */
    IndividualNodeSetPolicy getIndividualNodeSetPolicy();
}
