/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Ulm University
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.krss2.renderer;

import java.io.Writer;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.formats.KRSSOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;

/**
 * See {@link org.semanticweb.owlapi.krss2.renderer.KRSSObjectRenderer
 * KRSSObjectRenderer} for definition/explanation of the syntax.
 * 
 * @author Olaf Noppens, Ulm University, Institute of Artificial Intelligence
 */
public class KRSSSyntaxOntologyStorer extends AbstractOWLOntologyStorer {

    private static final long serialVersionUID = 40000L;

    @Override
    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat instanceof KRSSOntologyFormat;
    }

    @Override
    protected void storeOntology(@Nonnull OWLOntology ontology,
            @Nonnull Writer writer,
            @SuppressWarnings("unused") OWLOntologyFormat format)
            throws OWLOntologyStorageException {
        new KRSSSyntaxRenderer().render(ontology, writer);
    }
}
