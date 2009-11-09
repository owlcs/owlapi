package org.coode.owlapi.rdf.model;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.IRI;

import java.net.URI;
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
 * Date: 06-Dec-2006<br><br>
 */
public class RDFTranslator extends AbstractTranslator<RDFNode, RDFResourceNode, RDFResourceNode, RDFLiteralNode>{

    private RDFGraph graph;

    public RDFTranslator(OWLOntologyManager manager, OWLOntology ontology, boolean useStrongTyping) {
        super(manager, ontology, useStrongTyping);
        graph = new RDFGraph();
    }


    public RDFGraph getGraph() {
        return graph;
    }


    protected void addTriple(RDFResourceNode subject, RDFResourceNode pred, RDFNode object) {
        graph.addTriple(new RDFTriple(subject, pred, object));
    }


    protected RDFResourceNode getAnonymousNode(Object key) {
        return new RDFResourceNode(System.identityHashCode(key));
    }


    protected RDFLiteralNode getLiteralNode(String literal, IRI datatype) {
        return new RDFLiteralNode(literal, datatype);
    }


    protected RDFLiteralNode getLiteralNode(String literal, String lang) {
        return new RDFLiteralNode(literal, lang);
    }


    protected RDFResourceNode getPredicateNode(IRI uri) {
        return new RDFResourceNode(uri);
    }


    protected RDFResourceNode getResourceNode(IRI uri) {
        return new RDFResourceNode(uri);
    }


    public void reset() {
        graph = new RDFGraph();
    }
}
