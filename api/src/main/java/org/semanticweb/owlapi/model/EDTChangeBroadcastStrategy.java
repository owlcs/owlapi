package org.semanticweb.owlapi.model;

import javax.swing.*;
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
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 14-Mar-2009
 * <p/>
 * A change broadcast strategy which broadcasts all ontology changes in the Swing Even Dispatch Thread (EDT).
 */
public class EDTChangeBroadcastStrategy implements OWLOntologyChangeBroadcastStrategy {

    public void broadcastChanges(final OWLOntologyChangeListener listener, final List<? extends OWLOntologyChange> changes) throws Exception {
        if (SwingUtilities.isEventDispatchThread()) {
            listener.ontologiesChanged(changes);
        }
        else {
            try {
                Runnable r = new Runnable() {
                    public void run() {
                        try {
                            listener.ontologiesChanged(changes);
                        } catch (OWLException e) {
                            throw new BroadcastException(e);
                        }
                    }
                };
                SwingUtilities.invokeLater(r);
            } catch (BroadcastException e) {
                throw e.getCause();
            }
        }
    }

    private class BroadcastException extends RuntimeException {

        private BroadcastException(OWLException cause) {
            super(cause);
        }

        public OWLException getCause() {
            return (OWLException) super.getCause();
        }
    }

}
