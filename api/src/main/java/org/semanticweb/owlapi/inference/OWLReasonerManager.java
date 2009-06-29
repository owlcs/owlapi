package org.semanticweb.owlapi.inference;

import org.semanticweb.owlapi.model.OWLOntology;

import java.util.List;
import java.util.Set;
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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 02-Jun-2008<br><br>
 *
 * A convenience point for managing various reasoners (for
 * use in applications etc.).  The reasoner manager maintains
 * a list of reasoner factories and a currently "selected" reasoner
 * factory.  It also maintains a cached copy of a reasoner that
 * corresponds to a reasoner that has been instantiated by the current
 * reasoner factory.  The manager fires events if the current reasoner
 * factory is changed.
 */
public interface OWLReasonerManager {

    /**
     * Adds a listener to this reasoner manager.
     * @param listener The listener to be added.
     */
    void addListener(OWLReasonerManagerListener listener);


    /**
     * Removes a previously added listener to this manager.
     * @param listener The listener to be removed.
     */
    void removeListener(OWLReasonerManagerListener listener);

    /**
     * Gets a list of currently registered reasoner factories.
     * @return A list of reasoner factories.
     */
    List<OWLReasonerFactory> getRegisteredReasonerFactories();


    /**
     * Registers (adds) a new reasoner factory. An event will be
     * fired to indicate that a reasoner factory has be registered.
     * @param factory The factory to be registered.
     */
    void registerReasonerFactory(OWLReasonerFactory factory);


    /**
     * Unregisters (removes) a previously registerd reasoner
     * factory.  An event will be fired to indicated that a
     * reasoner factory has been unregistered.
     * @param factory The factory to be unregisterd.
     */
    void unregisterReasonerFactory(OWLReasonerFactory factory);

    /**
     * Sets the current reasoner factory.  This will
     * result in an event being fired to indicate the reasoner
     * factory has changed.  The currently cached reasoner will
     * be disposed of an a new reasoner that is instantiated
     * by the specified reasoner factory will be cached as the
     * current reasoner.
     * @param factory The reasoner factory that should be set
     * as the current factory. Note that a runtime exception will be
     * thrown if the specified reasoner factory is not a registered
     * reasoner factory.
     * @throws OWLReasonerException if there was a problem in
     * disposing of the currently cached reasoner.
     */
    void setReasonerFactory(OWLReasonerFactory factory) throws OWLReasonerException ;

    /**
     * Gets the current reasoner factory.
     * @return The current reasoner factory which will not be <code>null</code>
     */
    OWLReasonerFactory getReasonerFactory();


    /**
     * Gets the currently cached reasoner.
     * If the reasoner factory has changed since this method
     * call then a new instance of a reasoner will be created
     * and returned.
     * @return The currently cached reasoner that was created (first)
     * from the current reasoner factory.  This will not be <code>null</code>.
     */
    OWLReasoner getReasoner(Set<OWLOntology> ontologies) throws OWLReasonerException;


    /**
     * Creates a new instance of a reasoner using the
     * current reasoner factory.
     * @return A new instance of a reasoner created with
     * the current reasoner factory.
     */
    OWLReasoner createReasoner(Set<OWLOntology> ontologies) throws OWLReasonerException;
}
