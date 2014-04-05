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
package org.semanticweb.owlapi.rdf.model;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.RDFLiteral;
import org.semanticweb.owlapi.io.RDFNode;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class RDFTranslator extends
        AbstractTranslator<RDFNode, RDFResource, RDFResourceIRI, RDFLiteral> {

    private RDFGraph graph = new RDFGraph();

    /**
     * @param manager
     *        the manager
     * @param ontology
     *        the ontology
     * @param useStrongTyping
     *        true if strong typing is required
     */
    public RDFTranslator(@Nonnull OWLOntologyManager manager,
            @Nonnull OWLOntology ontology, boolean useStrongTyping) {
        super(manager, ontology, useStrongTyping);
    }

    /** @return the graph */
    public RDFGraph getGraph() {
        return graph;
    }

    @Override
    protected void addTriple(@Nonnull RDFResource subject,
            @Nonnull RDFResourceIRI pred, @Nonnull RDFNode object) {
        graph.addTriple(new RDFTriple(checkNotNull(subject,
                "subject cannot be null"), checkNotNull(pred,
                "pred cannot be null"), checkNotNull(object,
                "object cannot be null")));
    }

    @Override
    protected RDFResourceBlankNode getAnonymousNode(Object key) {
        checkNotNull(key, "key cannot be null");
        if (key instanceof OWLAnonymousIndividual) {
            RDFResourceBlankNode toReturn = new RDFResourceBlankNode(
                    System.identityHashCode(((OWLAnonymousIndividual) key)
                            .getID().getID()));
            return toReturn;
        }
        RDFResourceBlankNode toReturn = new RDFResourceBlankNode(
                System.identityHashCode(key));
        return toReturn;
    }

    @Override
    protected RDFLiteral getLiteralNode(OWLLiteral literal) {
        return new RDFLiteral(literal);
    }

    @Override
    protected RDFResourceIRI getPredicateNode(IRI uri) {
        return new RDFResourceIRI(uri);
    }

    @Override
    protected RDFResourceIRI getResourceNode(IRI uri) {
        return new RDFResourceIRI(uri);
    }

    /** clear the graph */
    public void reset() {
        graph = new RDFGraph();
    }
}
