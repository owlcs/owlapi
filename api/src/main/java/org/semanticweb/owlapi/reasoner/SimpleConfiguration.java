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

/**
 * A simple configuration with the general options.
 *
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public class SimpleConfiguration implements OWLReasonerConfiguration {

    private ReasonerProgressMonitor progressMonitor = new NullReasonerProgressMonitor();
    private FreshEntityPolicy freshEntityPolicy = FreshEntityPolicy.ALLOW;
    private IndividualNodeSetPolicy individualNodeSetPolicy = IndividualNodeSetPolicy.BY_NAME;
    private long timeOut = Long.MAX_VALUE;

    /**
     * Default constructor.
     */
    public SimpleConfiguration() {
        super();
    }

    /**
     * @param progressMonitor the progress monitor to use
     */
    public SimpleConfiguration(ReasonerProgressMonitor progressMonitor) {
        this.progressMonitor = progressMonitor;
    }

    /**
     * @param progressMonitor the progress monitor to use
     * @param timeOut the timeout in milliseconds
     */
    public SimpleConfiguration(ReasonerProgressMonitor progressMonitor, long timeOut) {
        this.progressMonitor = progressMonitor;
        this.timeOut = timeOut;
    }

    /**
     * @param progressMonitor the progress monitor to use
     * @param freshEntityPolicy the policy for fresh entities
     * @param timeOut the timeout in milliseconds
     * @param individualNodeSetPolicy the policy for individual nodes
     */
    public SimpleConfiguration(ReasonerProgressMonitor progressMonitor,
        FreshEntityPolicy freshEntityPolicy, long timeOut,
        IndividualNodeSetPolicy individualNodeSetPolicy) {
        this.progressMonitor = progressMonitor;
        this.freshEntityPolicy = freshEntityPolicy;
        this.timeOut = timeOut;
        this.individualNodeSetPolicy = individualNodeSetPolicy;
    }

    /**
     * @param freshEntityPolicy the policy for fresh entities
     * @param timeOut the timeout in milliseconds
     */
    public SimpleConfiguration(FreshEntityPolicy freshEntityPolicy, long timeOut) {
        this.freshEntityPolicy = freshEntityPolicy;
        this.timeOut = timeOut;
    }

    /**
     * @param timeOut the timeout in milliseconds
     */
    public SimpleConfiguration(long timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public ReasonerProgressMonitor getProgressMonitor() {
        return progressMonitor;
    }

    @Override
    public long getTimeOut() {
        return timeOut;
    }

    @Override
    public FreshEntityPolicy getFreshEntityPolicy() {
        return freshEntityPolicy;
    }

    @Override
    public IndividualNodeSetPolicy getIndividualNodeSetPolicy() {
        return individualNodeSetPolicy;
    }
}
