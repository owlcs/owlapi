package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.ProgressMonitor;

import java.util.concurrent.*;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Aug-2009
 */
public abstract class OWLReasonerBase implements OWLReasoner {

    private OWLOntologyManager manager;

    private ExecutorService longTaskExecutor = null;

    private boolean interrupted;

    protected OWLReasonerBase(OWLOntologyManager manager, ExecutorService longTaskExecutor, OWLReasonerConfiguration config) {
        this.manager = manager;
        this.longTaskExecutor = longTaskExecutor;
        ProgressMonitor progressMonitor = config.getProgressMonitor();
        progressMonitor.setProgress("Classifying", 4, 5);
    }

    private void classify() throws InterruptedException {
        // Send off to the service executor?
        Future<Void> future = longTaskExecutor.submit(new Callable<Void>() {
            /**
             * Computes a result, or throws an exception if unable to do so.
             * @return computed result
             * @throws Exception if unable to compute a result
             */
            public Void call() throws Exception {
                return null;
            }
        });
        try {
            Void v = future.get();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asks the reasoner to interrupt what it is currently doing.  An InterruptedException will be thrown in the
     * thread that invoked the last reasoner operation.  The OWL API is not thread safe in general, but it is likely
     * that this method will be called from another thread than the event dispatch thread or the thread in which
     * reasoning takes place.
     */
    public void interrupt() {
        interrupted = true;
    }

    public boolean isInterrupted() {
        return interrupted;
    }


}
