package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 15-Nov-2006<br><br>
 */
public abstract class AbstractInMemOWLOntologyFactory implements OWLOntologyFactory {

    private OWLOntologyManager ontologyManager;

    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        this.ontologyManager = owlOntologyManager;
    }


    public OWLOntologyManager getOWLOntologyManager() {
        return ontologyManager;
    }


    public boolean canCreateFromDocumentIRI(IRI documentIRI) {
        return true;
    }

    /**
     * Creates an empty ontology that a concrete representation can be
     * parsed into.  Subclasses can override this method to change the implementation
     * of the ontology.
     * @param documentIRI
     */
    public OWLOntology createOWLOntology(OWLOntologyID ontologyID, IRI documentIRI, OWLOntologyCreationHandler handler) throws OWLOntologyCreationException {
        if (ontologyManager == null) {
            throw new NullPointerException();
        }
        OWLOntology ont = new OWLOntologyImpl(ontologyManager, ontologyID);
        handler.ontologyCreated(ont);
        return ont;
    }
}
