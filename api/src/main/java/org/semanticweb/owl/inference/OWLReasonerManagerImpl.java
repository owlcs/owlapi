package org.semanticweb.owl.inference;

import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.model.OWLRuntimeException;

import java.util.ArrayList;
import java.util.List;
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
 */
public class OWLReasonerManagerImpl implements OWLReasonerManager {

    private List<OWLReasonerManagerListener> listeners;

    private List<OWLReasonerFactory> reasonerFactories;

    private OWLReasonerFactory currentReasonerFactory;

    private OWLReasoner reasoner;

    private OWLOntologyManager man;

    public OWLReasonerManagerImpl(OWLOntologyManager man) {
        this.man = man;
        listeners = new ArrayList<OWLReasonerManagerListener>();
        reasonerFactories = new ArrayList<OWLReasonerFactory>();
        currentReasonerFactory = new NullReasonerFactory();
    }


    public void addListener(OWLReasonerManagerListener listener) {
        listeners.add(listener);
    }


    public void removeListener(OWLReasonerManagerListener listener) {
        listeners.remove(listener);
    }


    public List<OWLReasonerFactory> getRegisteredReasonerFactories() {
        return new ArrayList<OWLReasonerFactory>(reasonerFactories);
    }


    public void registerReasonerFactory(OWLReasonerFactory factory) {
        reasonerFactories.add(factory);
        fireReasonerFactoryAdded();
    }


    public void unregisterReasonerFactory(OWLReasonerFactory factory) {
        reasonerFactories.remove(factory);
        fireReasonerFactoryRemoved();
    }


    public void setReasonerFactory(OWLReasonerFactory factory) throws OWLReasonerException {
        if(!reasonerFactories.contains(factory)) {
            throw new OWLRuntimeException("Reasoner factory not registered: " + factory.getReasonerName());
        }
        currentReasonerFactory = factory;
        reasoner.dispose();
        fireReasonerFactoryChanged();
    }


    public OWLReasonerFactory getReasonerFactory() {
        return currentReasonerFactory;
    }


    public OWLReasoner getReasoner() {
        if(reasoner == null) {
            reasoner = currentReasonerFactory.createReasoner(man);
        }
        return reasoner;
    }


    public OWLReasoner createReasoner() {
        return currentReasonerFactory.createReasoner(man);
    }

    protected void fireReasonerFactoryAdded() {
        for(OWLReasonerManagerListener listener : new ArrayList<OWLReasonerManagerListener>(listeners)) {
            listener.reasonerFactoryRegistered(new OWLReasonerManagerEvent(this));
        }
    }

    protected void fireReasonerFactoryRemoved() {
        for(OWLReasonerManagerListener listener : new ArrayList<OWLReasonerManagerListener>(listeners)) {
            listener.reasonerFactoryUnregistered(new OWLReasonerManagerEvent(this));
        }
    }

    protected void fireReasonerFactoryChanged() {

        for(OWLReasonerManagerListener listener : new ArrayList<OWLReasonerManagerListener>(listeners)) {
            listener.reasonerFactoryChanged(new OWLReasonerManagerEvent(this));
        }
    }
}
