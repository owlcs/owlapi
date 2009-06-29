package org.semanticweb.owlapi.util;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 04-Jan-2007<br><br>
 *
 * Certain tasks or algorithms may take some time to accomplish.  For example
 * parsing, saving etc.  In some situations, it is desirable to monitor the
 * progress of such tasks, for example to provide feedback in user interfaces.
 * Interfaces which represent long tasks can implement this progress monitor
 * interface to provide a hook for a progress monitor.
 */
public interface Monitorable {

    /**
     * Sets the progress monitor which receives information about the
     * progress of the this object which represents a long running task.
     * @param progressMonitor
     */
    void setProgressMonitor(ProgressMonitor progressMonitor);


    /**
     * Interrupts the long running task (if possible).
     * @throws InterruptedException
     */
    void interrupt() throws InterruptedException;


    /**
     * Determines if this long running task can be cancelled.
     */
    boolean canInterrupt();

}
