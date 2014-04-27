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

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.RDFNode;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.io.RDFTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class RDFGraph {

    private static final Logger logger = LoggerFactory
            .getLogger(RDFGraph.class);
    @Nonnull
    private final Map<RDFResource, Set<RDFTriple>> triplesBySubject = new HashMap<RDFResource, Set<RDFTriple>>();
    @Nonnull
    private final Set<RDFResourceBlankNode> rootAnonymousNodes = new HashSet<RDFResourceBlankNode>();
    @Nonnull
    private final Set<RDFTriple> triples = new HashSet<RDFTriple>();

    /**
     * Determines if this graph is empty (i.e. whether or not it contains any
     * triples).
     * 
     * @return {@code true} if the graph contains triples, otherwise
     *         {@code false}
     * @since 3.5
     */
    public boolean isEmpty() {
        return triples.isEmpty();
    }

    /**
     * @param triple
     *        triple to add
     */
    public void addTriple(@Nonnull RDFTriple triple) {
        checkNotNull(triple, "triple cannot be null");
        // Reset the computation of root anon nodes
        rootAnonymousNodes.clear();
        triples.add(triple);
        Set<RDFTriple> tripleSet = triplesBySubject.get(triple.getSubject());
        if (tripleSet == null) {
            tripleSet = new HashSet<RDFTriple>();
            triplesBySubject.put(triple.getSubject(), tripleSet);
        }
        tripleSet.add(triple);
    }

    /**
     * @param subject
     *        subject
     * @param sort
     *        sort
     * @return sorted triples
     */
    public Collection<RDFTriple> getTriplesForSubject(RDFNode subject,
            boolean sort) {
        Set<RDFTriple> set = triplesBySubject.get(subject);
        if (set == null) {
            return Collections.emptyList();
        }
        if (!sort) {
            return set;
        }
        List<RDFTriple> toReturn = new ArrayList<RDFTriple>(set);
        try {
            Collections.sort(toReturn);
        } catch (IllegalArgumentException e) {
            // catch possible sorting misbehaviour
            if (!e.getMessage().contains(
                    "Comparison method violates its general contract!")) {
                throw e;
            }
            // otherwise print a warning and leave the list unsorted
            logger.warn(
                    "Misbehaving triple comparator, leaving triples unsorted: {}",
                    e, toReturn);
        }
        return toReturn;
    }

    /** @return root anonymous nodes */
    @Nonnull
    public Set<RDFResourceBlankNode> getRootAnonymousNodes() {
        if (rootAnonymousNodes.isEmpty()) {
            rebuildAnonRoots();
        }
        return rootAnonymousNodes;
    }

    private void rebuildAnonRoots() {
        rootAnonymousNodes.clear();
        for (RDFTriple triple : triples) {
            if (triple.getSubject() instanceof RDFResourceBlankNode) {
                rootAnonymousNodes.add((RDFResourceBlankNode) triple
                        .getSubject());
            }
        }
        for (RDFTriple triple : triples) {
            if (!triple.getObject().isLiteral()) {
                rootAnonymousNodes.remove(triple.getObject());
            }
            if (!triple.getSubject().isAnonymous()) {
                rootAnonymousNodes.remove(triple.getSubject());
            }
        }
    }

    /**
     * @param w
     *        writer to write to
     * @throws IOException
     *         if exceptions happen
     */
    public void dumpTriples(@Nonnull Writer w) throws IOException {
        checkNotNull(w, "w cannot be null");
        for (Set<RDFTriple> set : triplesBySubject.values()) {
            for (RDFTriple triple : set) {
                w.write(triple.toString());
                w.write("\n");
            }
        }
        w.flush();
    }

    /** @return all triples in an unmodifiable set */
    public Set<RDFTriple> getAllTriples() {
        return Collections.unmodifiableSet(triples);
    }
}
