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
 * Date: 29-Nov-2009
 * </p>
 * The ReasonerProgressMonitor interface should be implemented by objects that wish to montitor the progress of a resoner.
 * The reasoner whose progress is being monitored will call the methods on this interface.
 * The progress monitor is designed to monitor long running reasoner
 * tasks such as loading, preprocessing, consistency checking, classification and realisation.
 * </p>
 * Tasks are executed sequentially.  Nested tasks are not supported.
 * </p>
 * The general contract is that the reasoner will call {@link #reasonerTaskStarted(String)}, then call
 * either {@link #reasonerTaskBusy()} or {@link #reasonerTaskProgressChanged(int, int)} any number of times
 * and finally call {@link #reasonerTaskStopped()} when the task ends or has been interupted.  This cycle may
 * then be repeated.
 */
public interface ReasonerProgressMonitor {

    /**
     * A standard name for the task of loading a reasoner with axioms.  Note that there is no guarantee that the
     * reasoner will use this name for loading.
     */
    public static final String LOADING = "Loading";

    /**
     * A standard name for the task of computing the class hierarchy.  Note that there is no guarantee that the
     * reasoner will use this name for the task of computing the class hierarchy.
     */
    public static final String CLASSIFYING = "Classifying";

    /**
     * A standard name for the task of computing the types of individual.    Note that there is no guarantee that the
     * reasoner will use this name for the task of realising.
     */
    public static final String REALIZING = "Realizing";

    /**
     * A standard name for the task of classifying and realising at the same time.   Note that there is no guarantee that the
     * reasoner will use this name for the task of classifying.
     */
    public static final String CLASSIFYING_AND_REALIZING = "Classifying and Realizing";

    /**
     * Indicates that some reasoner task, for example, loading, consistency checking, classification, realisation etc.
     * has started.  When the task has finished the {@link #reasonerTaskStopped()} method will be called.
     * Once this method has been called it will not be called again unless the
     * {@link #reasonerTaskStopped()} method has been called. The notion of subtasks is not supported.
     * </p>
     * Note that this method may be called from a thread that is not the event dispatch thread.
     * @param taskName The name of the task
     */
    void reasonerTaskStarted(String taskName);

    /**
     * Indicates that a previosly started task has now stopped.  This method will only be called after the
     * {@link #reasonerTaskStarted(String)} method has been called.  The notion of subtasks is not supported.
     * </p>
     * Note that this method may be called from a thread that is not the event dispatch thread.
     */
    void reasonerTaskStopped();

    /**
     * Indicates that the reasoner is part way through a particular task, for example consistency checking,
     * classification or reaslisation.  This method will only be called after the {@link #reasonerTaskStarted(String)}
     * method has been called.  It will not be called after the {@link #reasonerTaskStopped()} method has been called.
     * </p>
     * Note that this method may be called from a thread that is not the event
     * dispatch thread.
     * @param value The value or portion of the task completed
     * @param max The total size of the task
     */
    void reasonerTaskProgressChanged(int value, int max);

    /**
     * Indicates that the reasoner is busy performing a task whose size cannot be determined.  This method will only
     * be called after the {@link #reasonerTaskStarted(String)} method has been called.  It will not be called after
     * the {@link #reasonerTaskStopped()} method has been called.
     * </p>
     * Note that this
     * method may be called from a thread that is not the event dispatch thread.
     */
    void reasonerTaskBusy();
}
