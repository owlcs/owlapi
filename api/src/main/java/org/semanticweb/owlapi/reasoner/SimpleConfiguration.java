package org.semanticweb.owlapi.reasoner;

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
 * Date: 18-Mar-2009
 *
 * <p>
 * A simple configuration with the general options.
 * </p>
 */
public class SimpleConfiguration implements OWLReasonerConfiguration {

    private ReasonerProgressMonitor progressMonitor = new NullReasonerProgressMonitor();

    private UndeclaredEntityPolicy undeclaredEntityPolicy = UndeclaredEntityPolicy.ALLOW;

    private IndividualNodeSetPolicy individualNodeSetPolicy = IndividualNodeSetPolicy.BY_NAME;

    private long timeOut = Long.MAX_VALUE;

    public SimpleConfiguration() {
    }

    public SimpleConfiguration(ReasonerProgressMonitor progressMonitor) {
        this.progressMonitor = progressMonitor;
    }

    public SimpleConfiguration(ReasonerProgressMonitor progressMonitor, long timeOut) {
        this.progressMonitor = progressMonitor;
        this.timeOut = timeOut;
    }

    public SimpleConfiguration(ReasonerProgressMonitor progressMonitor, UndeclaredEntityPolicy undeclaredEntityPolicy, long timeOut, IndividualNodeSetPolicy individualNodeSetPolicy) {
        this.progressMonitor = progressMonitor;
        this.undeclaredEntityPolicy = undeclaredEntityPolicy;
        this.timeOut = timeOut;
        this.individualNodeSetPolicy = individualNodeSetPolicy;
    }

    public SimpleConfiguration(UndeclaredEntityPolicy undeclaredEntityPolicy, long timeOut) {
        this.undeclaredEntityPolicy = undeclaredEntityPolicy;
        this.timeOut = timeOut;
    }

    public SimpleConfiguration(long timeOut) {
        this.timeOut = timeOut;
    }

    public ReasonerProgressMonitor getProgressMonitor() {
        return progressMonitor;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public UndeclaredEntityPolicy getUndeclaredEntityPolicy() {
        return undeclaredEntityPolicy;
    }

    public IndividualNodeSetPolicy getIndividualNodeSetPolicy() {
        return individualNodeSetPolicy;
    }
}
