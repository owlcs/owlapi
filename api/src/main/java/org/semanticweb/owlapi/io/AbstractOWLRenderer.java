package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.*;
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
            Writer writer = new BufferedWriter(new OutputStreamWriter(os));
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
     *               Note that this writer need not be wrapped with a <code>BufferedWriter</code>
     *               because this is taken care of by this abstract implementation.
     */
    public abstract void render(OWLOntology ontology, Writer writer) throws OWLRendererException;
}
