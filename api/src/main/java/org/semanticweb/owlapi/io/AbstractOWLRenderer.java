/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Nov-2006<br><br>
 */
public abstract class AbstractOWLRenderer implements OWLRenderer {

    private OWLOntologyManager owlOntologyManager;


    protected AbstractOWLRenderer(OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
    }


    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
    }


    protected OWLOntologyManager getOWLOntologyManager() {
        return owlOntologyManager;
    }


    public void render(OWLOntology ontology, OutputStream os) throws OWLRendererException {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            render(ontology, writer);
            writer.flush();
        }
        catch (IOException e) {
            throw new OWLRendererIOException(e);
        }
    }


    /**
     * Renders the specified ontology using the specified writer.
     * @param writer The writer that should be used to write the ontology.
     * Note that this writer need not be wrapped with a <code>BufferedWriter</code>
     * because this is taken care of by this abstract implementation.
     */
    public abstract void render(OWLOntology ontology, Writer writer) throws OWLRendererException;
}
