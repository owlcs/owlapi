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

package org.coode.owlapi.rdf.model;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Dec-2006<br><br>
 */
public class RDFGraph {

    private Map<RDFResourceNode, Set<RDFTriple>> triplesBySubject;

//    private Map<RDFNode, Set<RDFTriple>> triplesByObject;

    private Set<RDFResourceNode> rootAnonymousNodes;

    private Set<RDFTriple> triples;


    public RDFGraph() {
        triples = new HashSet<RDFTriple>();
        triplesBySubject = new HashMap<RDFResourceNode, Set<RDFTriple>>();
//        triplesByObject = new HashMap<RDFNode, Set<RDFTriple>>();
        rootAnonymousNodes = null;
    }


    public void addTriple(RDFTriple triple) {
        // Reset the computation of root anon nodes
        rootAnonymousNodes = null;
        triples.add(triple);
        Set<RDFTriple> tripleSet = triplesBySubject.get(triple.getSubject());
        if (tripleSet == null) {
            tripleSet = new HashSet<RDFTriple>();
            triplesBySubject.put(triple.getSubject(), tripleSet);
        }
        tripleSet.add(triple);
//
//        Set<RDFTriple> triples2 = triplesByObject.get(triple.getResourceObject());
//        if(triples2 == null) {
//            triples2 = new HashSet<RDFTriple>();
//            triplesByObject.put(triple.getResourceObject(), triples2);
//        }
//        triples2.add(triple);
//
//
//            if(triple.getSubject().isAnonymous()) {
//                // Might be a root - i.e. no incoming edges
//                if(!triplesByObject.keySet().contains(triple.getSubject())) {
//                    rootAnonymousNodes.add(triple.getSubject());
//                }
//            }
//            if(triple.getResourceObject().isAnonymous()) {
//                // Incoming edge for the anon obj
//                rootAnonymousNodes.remove(triple.getResourceObject());
//            }
    }


    public Set<RDFTriple> getTriplesForSubject(RDFNode subject) {
        if (triplesBySubject.containsKey(subject)) {
            return new HashSet<RDFTriple>(triplesBySubject.get(subject));
        }
        else {
            return Collections.emptySet();
        }
    }


    public boolean isAnonymousNodeSharedSubject(RDFResourceNode node) {
        if(!node.isAnonymous()) {
            return false;
        }
        int count = 0;
        for(RDFTriple triple : triples) {
            if(!triple.getObject().isLiteral()) {
                RDFResourceNode object = (RDFResourceNode) triple.getObject();
                if(object.equals(node)) {
                    count++;
                    if(count > 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Set<RDFResourceNode> getRootAnonymousNodes() {
        if (rootAnonymousNodes == null) {
            rebuildAnonRoots();
        }
        return rootAnonymousNodes;
    }


    private void rebuildAnonRoots() {
        rootAnonymousNodes = new HashSet<RDFResourceNode>();
        for (RDFTriple triple : triples) {
            rootAnonymousNodes.add(triple.getSubject());
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

    public void dumpTriples(Writer w) throws IOException {
        for(RDFNode subj : triplesBySubject.keySet()) {
            for(RDFTriple triple : triplesBySubject.get(subj)) {
                w.write(triple.toString());
                w.write("\n");
            }
        }
        w.flush();
    }
}
