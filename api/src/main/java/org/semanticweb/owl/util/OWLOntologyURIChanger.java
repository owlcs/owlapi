package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;

import java.util.List;
import java.util.ArrayList;
import java.net.URI;
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
 * Date: 25-May-2007<br><br>
 *
 * Changes the URI of an ontology and ensures that ontologies which import
 * the ontology have their imports statements updated
 */
public class OWLOntologyURIChanger {

    private OWLOntologyManager owlOntologyManager;


    public OWLOntologyURIChanger(OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
    }


    /**
     * Changes the URI of the specified ontology to the new URI.
     * @param ontology The ontology whose URI is to be changed.
     * @param newURI The new URI for the ontology
     * @return A list of changes, which when applied will change the URI of the
     * specified ontology, and also update the imports declarations in any ontologies
     * which import the specified ontology.
     */
    public List<OWLOntologyChange> getChanges(OWLOntology ontology, URI newURI) {
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        changes.add(new SetOntologyURI(ontology, newURI));
        for(OWLOntology ont : owlOntologyManager.getOntologies()) {
            for(OWLImportsDeclaration decl : ont.getImportsDeclarations()) {
                if(decl.getImportedOntologyURI().equals(ontology.getURI())) {
                    changes.add(new RemoveAxiom(ont, decl));
                    changes.add(new AddAxiom(ont, owlOntologyManager.getOWLDataFactory().getOWLImportsDeclarationAxiom(ontology, newURI)));
                }
            }
        }
        return changes;
    }
}
