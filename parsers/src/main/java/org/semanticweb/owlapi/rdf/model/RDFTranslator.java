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

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.RDFLiteral;
import org.semanticweb.owlapi.io.RDFNode;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AxiomAppearance;
import org.semanticweb.owlapi.util.IndividualAppearance;

import gnu.trove.map.custom_hash.TObjectIntCustomHashMap;
import gnu.trove.strategy.IdentityHashingStrategy;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class RDFTranslator
    extends AbstractTranslator<RDFNode, RDFResource, RDFResourceIRI, RDFLiteral> {

    protected final AxiomAppearance axiomOccurrences;
    private final AtomicInteger nextBlankNodeId;
    private TObjectIntCustomHashMap<Object> blankNodeMap =
        new TObjectIntCustomHashMap<>(new IdentityHashingStrategy<>());

    /**
     * @param manager the manager
     * @param ontology the ontology
     * @param useStrongTyping true if strong typing is required
     * @param occurrences will tell whether anonymous individuals need an id or not
     * @param axiomOccurrences axiom occurrences
     * @param counter counter for blank nodes
     */
    public RDFTranslator(OWLOntologyManager manager, OWLOntology ontology, boolean useStrongTyping,
        IndividualAppearance occurrences, AxiomAppearance axiomOccurrences,
        AtomicInteger counter) {
        super(manager, ontology, useStrongTyping, occurrences);
        this.axiomOccurrences = axiomOccurrences;
        nextBlankNodeId = counter;
    }

    @Override
    protected void addTriple(RDFResource subject, RDFResourceIRI pred, @Nonnull RDFNode object) {
        graph.addTriple(new RDFTriple(subject, pred, object));
    }

    @Override
    protected RDFResourceBlankNode getAnonymousNode(Object key) {
        checkNotNull(key, "key cannot be null");
        boolean isIndividual = key instanceof OWLAnonymousIndividual;
        boolean isAxiom = false;
        boolean needId = false;
        if (isIndividual) {
            OWLAnonymousIndividual anonymousIndividual = (OWLAnonymousIndividual) key;
            needId = multipleOccurrences.appearsMultipleTimes(anonymousIndividual);
            return getBlankNodeFor(anonymousIndividual.getID().getID(), isIndividual, isAxiom,
                needId);
        } else if (key instanceof OWLAxiom) {
            isIndividual = false;
            isAxiom = true;
            needId = axiomOccurrences.appearsMultipleTimes((OWLAxiom) key);
        }
        return getBlankNodeFor(key, isIndividual, isAxiom, needId);
    }

    protected RDFResourceBlankNode getBlankNodeFor(Object key, boolean isIndividual,
        boolean isAxiom, boolean needId) {
        int id = blankNodeMap.get(key);
        if (id == 0) {
            id = nextBlankNodeId.getAndIncrement();
            blankNodeMap.put(key, id);
        }
        return new RDFResourceBlankNode(id, isIndividual, needId, isAxiom);
    }

    @Override
    protected RDFLiteral getLiteralNode(@Nonnull OWLLiteral literal) {
        return new RDFLiteral(literal);
    }

    @Override
    protected RDFResourceIRI getPredicateNode(@Nonnull IRI iri) {
        return new RDFResourceIRI(iri);
    }

    @Override
    protected RDFResourceIRI getResourceNode(@Nonnull IRI iri) {
        return new RDFResourceIRI(iri);
    }
}
