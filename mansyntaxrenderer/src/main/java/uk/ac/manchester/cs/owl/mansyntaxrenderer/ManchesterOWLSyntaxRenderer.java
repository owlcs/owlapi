package uk.ac.manchester.cs.owl.mansyntaxrenderer;

import org.semanticweb.owl.io.AbstractOWLRenderer;
import org.semanticweb.owl.io.OWLRendererException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.model.OWLOntologyFormat;
import org.semanticweb.owl.vocab.NamespaceOWLOntologyFormat;
import org.coode.xml.OWLOntologyNamespaceManager;

import java.io.Writer;
import java.util.Map;
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
 * Date: 10-May-2007<br><br>
 */
public class ManchesterOWLSyntaxRenderer extends AbstractOWLRenderer {

    public ManchesterOWLSyntaxRenderer(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }


    public void render(OWLOntology ontology, Writer writer) throws OWLRendererException {
        ManchesterOWLSyntaxFrameRenderer ren = new ManchesterOWLSyntaxFrameRenderer(getOWLOntologyManager(), ontology, writer);
        OWLOntologyFormat format = getOWLOntologyManager().getOntologyFormat(ontology);
        if(format instanceof NamespaceOWLOntologyFormat) {
            NamespaceOWLOntologyFormat namespaceFormat = (NamespaceOWLOntologyFormat) format;
            Map<String, String> prefixMap = namespaceFormat.getNamespacesByPrefixMap();
            for(String prefix : prefixMap.keySet()) {
                ren.getNamespaceManager().setPrefix(prefix, prefixMap.get(prefix));
            }
        }
        ren.writeOntology();
        ren.flush();
    }
}
