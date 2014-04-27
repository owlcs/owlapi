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
package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import com.google.common.base.Optional;

@SuppressWarnings("javadoc")
public class DuplicateImportTestCase extends TestBase {

    @SuppressWarnings("null")
    @Test
    public void shouldLoad() throws OWLOntologyStorageException,
            OWLOntologyCreationException, IOException {
        @Nonnull
        File importsBothNameAndVersion = folder
                .newFile("tempimportsNameAndVersion.owl");
        @Nonnull
        File importsBothNameAndOther = folder
                .newFile("tempimportsNameAndOther.owl");
        @Nonnull
        File ontologyByName = folder.newFile("tempmain.owl");
        @Nonnull
        File ontologyByVersion = folder.newFile("tempversion.owl");
        @Nonnull
        File ontologyByOtherPath = folder.newFile("tempother.owl");
        OWLOntologyManager manager = m;
        OWLOntology ontology = manager.createOntology(new OWLOntologyID(
                Optional.of(IRI.create(ontologyByName)), Optional.of(IRI
                        .create(ontologyByVersion))));
        manager.saveOntology(ontology, IRI.create(ontologyByName));
        manager.saveOntology(ontology, IRI.create(ontologyByVersion));
        manager.saveOntology(ontology, IRI.create(ontologyByOtherPath));
        manager = m1;
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLOntology ontology1 = manager.createOntology(IRI
                .create(importsBothNameAndVersion));
        OWLOntology ontology2 = manager.createOntology(IRI
                .create(importsBothNameAndOther));
        List<AddImport> changes = new ArrayList<AddImport>();
        changes.add(new AddImport(ontology1, factory
                .getOWLImportsDeclaration(IRI.create(ontologyByName))));
        changes.add(new AddImport(ontology1, factory
                .getOWLImportsDeclaration(IRI.create(ontologyByVersion))));
        changes.add(new AddImport(ontology2, factory
                .getOWLImportsDeclaration(IRI.create(ontologyByName))));
        changes.add(new AddImport(ontology2, factory
                .getOWLImportsDeclaration(IRI.create(ontologyByOtherPath))));
        manager.applyChanges(changes);
        manager.saveOntology(ontology1, IRI.create(importsBothNameAndVersion));
        manager.saveOntology(ontology2, IRI.create(importsBothNameAndOther));
        // when
        OWLOntology o1 = m.loadOntology(IRI.create(importsBothNameAndVersion));
        OWLOntology o2 = m1.loadOntology(IRI.create(importsBothNameAndOther));
        // then
        assertNotNull(o1);
        assertNotNull(o2);
    }
}
