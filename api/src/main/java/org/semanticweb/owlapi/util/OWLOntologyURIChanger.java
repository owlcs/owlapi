package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.List;
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
 * <p/>
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
     *
     * @param ontology The ontology whose URI is to be changed.
     * @param newIRI
     * @return A list of changes, which when applied will change the URI of the
     *         specified ontology, and also update the imports declarations in any ontologies
     *         which import the specified ontology.
     */
    public List<OWLOntologyChange> getChanges(OWLOntology ontology, IRI newIRI) {
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        changes.add(new SetOntologyID(ontology, new OWLOntologyID(newIRI, ontology.getOntologyID().getVersionIRI())));
        for (OWLOntology ont : owlOntologyManager.getOntologies()) {
            for (OWLImportsDeclaration decl : ont.getImportsDeclarations()) {
                if (decl.getIRI().equals(ontology.getOntologyID().getOntologyIRI())) {
                    changes.add(new RemoveImport(ont, decl));
                    changes.add(new AddImport(ont, owlOntologyManager.getOWLDataFactory().getOWLImportsDeclaration(newIRI)));
                }
            }
        }
        return changes;
    }
}
