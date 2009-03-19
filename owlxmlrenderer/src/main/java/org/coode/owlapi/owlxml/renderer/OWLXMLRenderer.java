package org.coode.owlapi.owlxml.renderer;

import org.coode.xml.OWLOntologyNamespaceManager;
import org.semanticweb.owl.io.AbstractOWLRenderer;
import org.semanticweb.owl.io.OWLRendererException;
import org.semanticweb.owl.io.OWLRendererIOException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyFormat;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.vocab.NamespaceOWLOntologyFormat;
import org.semanticweb.owl.vocab.Namespaces;

import java.io.IOException;
import java.io.Writer;
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
 * Date: 12-Dec-2006<br><br>
 */
public class OWLXMLRenderer extends AbstractOWLRenderer {

    public OWLXMLRenderer(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }

    public void render(OWLOntology ontology, Writer writer, OWLOntologyFormat format) throws OWLRendererException {
        try {
            OWLOntologyNamespaceManager nsm = new OWLOntologyNamespaceManager(getOWLOntologyManager(), ontology);
            nsm.setDefaultNamespace(Namespaces.OWL.toString());
            if(format instanceof NamespaceOWLOntologyFormat) {
                NamespaceOWLOntologyFormat namespaceFormat = (NamespaceOWLOntologyFormat) format;
                for(String prefix : namespaceFormat.getNamespacesByPrefixMap().keySet()) {
                    String ns = namespaceFormat.getNamespace(prefix);
                    if(ns != null) {
                       nsm.setPrefix(prefix, ns);
                    }
                }
                if(namespaceFormat.getDefaultNamespace() != null) {
                    nsm.setDefaultNamespace(namespaceFormat.getDefaultNamespace());
                }
            }
            nsm.setPrefix("xml", Namespaces.XML.toString());
            OWLXMLWriter w = new OWLXMLWriter(writer, nsm, ontology);
            w.startDocument(ontology);
            OWLXMLObjectRenderer ren = new OWLXMLObjectRenderer(ontology, w);
            ontology.accept(ren);
            w.endDocument();
            writer.flush();
        }
        catch (IOException e) {
            throw new OWLRendererIOException(e);
        }
    }

    public void render(OWLOntology ontology, Writer writer) throws OWLRendererException {
        render(ontology, writer, getOWLOntologyManager().getOntologyFormat(ontology));
    }
}
