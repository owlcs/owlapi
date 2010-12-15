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
        Set<RDFTriple> triples = triplesBySubject.get(triple.getSubject());
        if (triples == null) {
            triples = new HashSet<RDFTriple>();
            triplesBySubject.put(triple.getSubject(), triples);
        }
        triples.add(triple);
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
