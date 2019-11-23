package org.semanticweb.owlapi6.rdf.rdfxml.parser;

import static org.semanticweb.owlapi6.utility.CollectionFactory.createMap;

import java.util.Map;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLLiteral;

class ListTriples {

    /**
     * Maps rdf:next triple subjects to objects
     */
    private final Map<IRI, IRI> listRestTripleMap = createMap();
    /**
     * The list first resource triple map.
     */
    private final Map<IRI, IRI> listFirstResourceTripleMap = createMap();
    /**
     * The list first literal triple map.
     */
    private final Map<IRI, OWLLiteral> listFirstLiteralTripleMap = createMap();

    protected void clear() {
        listFirstLiteralTripleMap.clear();
        listFirstResourceTripleMap.clear();
        listRestTripleMap.clear();


    }

    protected void addRest(IRI subject, IRI object) {
        listRestTripleMap.put(subject, object);
    }

    @Nullable
    protected IRI getFirstResource(IRI subject, boolean consume) {
        return consume ? listFirstResourceTripleMap.remove(subject)
            : listFirstResourceTripleMap.get(subject);
    }

    @Nullable
    protected OWLLiteral getFirstLiteral(IRI subject) {
        return listFirstLiteralTripleMap.get(subject);
    }

    @Nullable
    protected IRI getRest(IRI subject, boolean consume) {
        return consume ? listRestTripleMap.remove(subject) : listRestTripleMap.get(subject);
    }

    protected void addFirst(IRI s, IRI o) {
        listFirstResourceTripleMap.put(s, o);
    }

    protected void addFirst(IRI s, OWLLiteral o) {
        listFirstLiteralTripleMap.put(s, o);
    }

}
