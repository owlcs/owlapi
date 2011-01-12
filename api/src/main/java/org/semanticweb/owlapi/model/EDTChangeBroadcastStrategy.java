package org.semanticweb.owlapi.model;

import java.util.List;

import javax.swing.SwingUtilities;

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

    private static class BroadcastException extends RuntimeException {

        private BroadcastException(OWLException cause) {
            super(cause);
        }

        @Override
		public OWLException getCause() {
            return (OWLException) super.getCause();
        }
    }

}
