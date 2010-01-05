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
 * Date: 05-Jan-2010
 */
public class ConsoleProgressMonitor implements ReasonerProgressMonitor {

    private int lastPercentage = 0;

    public void reasonerTaskStarted(String taskName) {
        System.out.print(taskName);
        System.out.println(" ...");
    }

    public void reasonerTaskStopped() {
        System.out.println("    ... finished");
        lastPercentage = 0;
    }

    public void reasonerTaskProgressChanged(int value, int max) {
        int percent = (value * 100) / max;
        if (lastPercentage != percent) {
            System.out.print("    ");
            System.out.print(percent);
            System.out.println("%");
            lastPercentage = percent;
        }
    }

    public void reasonerTaskBusy() {
        System.out.println("    busy ...");
    }
}
