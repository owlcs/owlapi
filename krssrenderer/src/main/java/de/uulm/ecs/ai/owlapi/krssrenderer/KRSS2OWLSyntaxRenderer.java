package de.uulm.ecs.ai.owlapi.krssrenderer;

import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.io.OWLRendererIOException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;

import java.io.IOException;
import java.io.Writer;
/*
* Copyright (C) 2007, Ulm University
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
 * @author Olaf Noppens
 */
public class KRSS2OWLSyntaxRenderer extends AbstractOWLRenderer {

    public KRSS2OWLSyntaxRenderer(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }

    public void render(OWLOntology ontology, Writer writer) throws OWLRendererException {
        try {
            KRSS2OWLObjectRenderer ren = new KRSS2OWLObjectRenderer(ontology, writer);
            ontology.accept(ren);
            writer.flush();
        } catch (IOException io) {
            throw new OWLRendererIOException(io);
        } catch (OWLRuntimeException e) {
            throw new OWLRendererException(e);
        }
    }
}
