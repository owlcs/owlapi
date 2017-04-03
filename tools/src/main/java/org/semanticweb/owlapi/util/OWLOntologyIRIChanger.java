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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveImport;
import org.semanticweb.owlapi.model.SetOntologyID;

/**
 * Changes the URI of an ontology and ensures that ontologies which import the ontology have their
 * imports statements updated.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLOntologyIRIChanger {

    private final OWLOntologyManager owlOntologyManager;

    /**
     * @param owlOntologyManager the ontology manager to use
     */
    public OWLOntologyIRIChanger(OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager =
            checkNotNull(owlOntologyManager, "owlOntologyManager cannot be null");
    }

    /**
     * Changes the URI of the specified ontology to the new URI.
     *
     * @param ontology The ontology whose URI is to be changed.
     * @param newIRI the new IRI
     * @return A list of changes, which when applied will change the URI of the specified ontology,
     * and also update the imports declarations in any ontologies which import the specified
     * ontology.
     */
    public List<OWLOntologyChange> getChanges(OWLOntology ontology, IRI newIRI) {
        List<OWLOntologyChange> changes = new ArrayList<>();
        changes.add(new SetOntologyID(ontology, new OWLOntologyID(optional(newIRI),
            ontology.getOntologyID().getVersionIRI())));
        OWLImportsDeclaration owlImport =
            owlOntologyManager.getOWLDataFactory().getOWLImportsDeclaration(newIRI);
        IRI ontIRI = ontology.getOntologyID().getOntologyIRI().get();
        owlOntologyManager.ontologies().forEach(ont -> ont.importsDeclarations()
            .filter(decl -> decl.getIRI().equals(ontIRI)).forEach(decl -> {
                changes.add(new RemoveImport(ont, decl));
                changes.add(new AddImport(ont, owlImport));
            }));
        return changes;
    }
}
