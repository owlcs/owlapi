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
 * The ReasonerProgressMonitor interface should be implemented by objects that
 * wish to montitor the progress of a resoner. The reasoner whose progress is
 * being monitored will call the methods on this interface. The progress monitor
 * is designed to monitor long running reasoner tasks such as loading,
 * preprocessing, consistency checking, classification and realisation. <br>
 * Tasks are executed sequentially. Nested tasks are not supported. <br>
 * The general contract is that the reasoner will call
 * {@link #reasonerTaskStarted(String)}, then call either
 * {@link #reasonerTaskBusy()} or {@link #reasonerTaskProgressChanged(int, int)}
 * any number of times and finally call {@link #reasonerTaskStopped()} when the
 * task ends or has been interupted. This cycle may then be repeated.
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public interface ReasonerProgressMonitor extends Serializable {

    /**
     * A standard name for the task of loading a reasoner with axioms. Note that
     * there is no guarantee that the reasoner will use this name for loading.
     */
    String LOADING = "Loading";
    /**
     * A standard name for the task of computing the class hierarchy. Note that
     * there is no guarantee that the reasoner will use this name for the task
     * of computing the class hierarchy.
     */
    String CLASSIFYING = "Classifying";
    /**
     * A standard name for the task of computing the types of individual. Note
     * that there is no guarantee that the reasoner will use this name for the
     * task of realising.
     */
    String REALIZING = "Realizing";
    /**
     * A standard name for the task of classifying and realising at the same
     * time. Note that there is no guarantee that the reasoner will use this
     * name for the task of classifying.
     */
    String CLASSIFYING_AND_REALIZING = "Classifying and Realizing";

    /**
     * Indicates that some reasoner task, for example, loading, consistency
     * checking, classification, realisation etc. has started. When the task has
     * finished the {@link #reasonerTaskStopped()} method will be called. Once
     * this method has been called it will not be called again unless the
     * {@link #reasonerTaskStopped()} method has been called. The notion of
     * subtasks is not supported. <br>
     * Note that this method may be called from a thread that is not the event
     * dispatch thread.
     * 
     * @param taskName
     *        The name of the task
     */
    void reasonerTaskStarted(String taskName);

    /**
     * Indicates that a previosly started task has now stopped. This method will
     * only be called after the {@link #reasonerTaskStarted(String)} method has
     * been called. The notion of subtasks is not supported. <br>
     * Note that this method may be called from a thread that is not the event
     * dispatch thread.
     */
    void reasonerTaskStopped();

    /**
     * Indicates that the reasoner is part way through a particular task, for
     * example consistency checking, classification or reaslisation. This method
     * will only be called after the {@link #reasonerTaskStarted(String)} method
     * has been called. It will not be called after the
     * {@link #reasonerTaskStopped()} method has been called. <br>
     * Note that this method may be called from a thread that is not the event
     * dispatch thread.
     * 
     * @param value
     *        The value or portion of the task completed
     * @param max
     *        The total size of the task
     */
    void reasonerTaskProgressChanged(int value, int max);

    /**
     * Indicates that the reasoner is busy performing a task whose size cannot
     * be determined. This method will only be called after the
     * {@link #reasonerTaskStarted(String)} method has been called. It will not
     * be called after the {@link #reasonerTaskStopped()} method has been
     * called. <br>
     * Note that this method may be called from a thread that is not the event
     * dispatch thread.
     */
    void reasonerTaskBusy();
}
