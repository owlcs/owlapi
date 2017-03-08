package org.semanticweb.owlapi.rdf.rdfxml.parser;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

class TripleIndex {

    /**
     * Subject, predicate, object
     */
    private final TripleMapCollection<IRI> resTriples = new TripleMapCollection<>();
    /**
     * Predicate, subject, object
     */
    private final TripleMap<IRI> singlePredicateResources = new TripleMap<>();
    /**
     * Literal triples
     */
    private final TripleMapCollection<OWLLiteral> litTriples = new TripleMapCollection<>();
    /**
     * Predicate, subject, object
     */
    private final TripleMap<OWLLiteral> singlePredicateLiterals = new TripleMap<>();
    private final TripleLogger tripleLogger;

    public TripleIndex(TripleLogger logger) {
        singlePredicateResources.setupSinglePredicateMaps();
        tripleLogger = logger;
    }

    public void dumpRemainingTriples() {
        singlePredicateResources.dumpRemainingTriples();
        singlePredicateLiterals.dumpRemainingTriples();
        resTriples.dumpRemainingTriples();
        litTriples.dumpRemainingTriples();


    }

    public void clear() {
        resTriples.clear();
        litTriples.clear();
        singlePredicateLiterals.clear();
        singlePredicateResources.clear();


    }

    @Nullable
    protected IRI resource(IRI subject, HasIRI predicate, boolean consume) {
        return resource(subject, predicate.getIRI(), consume);
    }

    protected Stream<IRI> getPredicatesBySubject(IRI subject) {
        return Stream.concat(resTriples.keys(subject), litTriples.keys(subject)).distinct();
    }

    @Nullable
    protected IRI resource(IRI subject, IRI predicate, boolean consume) {
        IRI obj = singlePredicateResources.get(subject, predicate, consume);
        if (obj != null) {
            return obj;
        }
        return resTriples.get(subject, predicate, consume);
    }

    @Nullable
    protected OWLLiteral literal(IRI subject, IRI predicate, boolean consume) {
        OWLLiteral obj = singlePredicateLiterals.get(subject, predicate, consume);
        if (obj != null) {
            return obj;
        }
        return litTriples.get(subject, predicate, consume);
    }


    protected Stream<OWLLiteral> getLiteralObjects(IRI subject, IRI predicate) {
        OWLLiteral obj = singlePredicateLiterals.get(subject, predicate, false);
        return Stream.concat(obj == null ? Stream.empty() : Stream.of(obj),
            litTriples.getAll(subject, predicate, false));
    }

    protected Stream<IRI> getResourceObjects(IRI subject, IRI predicate) {
        IRI obj = singlePredicateResources.get(subject, predicate, false);
        return Stream.concat(obj == null ? Stream.empty() : Stream.of(obj),
            resTriples.getAll(subject, predicate, false));
    }

    protected boolean consumeIfPresent(IRI subject, IRI predicate, IRI object) {
        return singlePredicateResources.consume(subject, predicate, object)
            || resTriples.consume(subject, predicate, object);
    }

    protected boolean consumeIfPresent(IRI subject, IRI predicate, OWLLiteral object) {
        return singlePredicateLiterals.consume(subject, predicate, object)
            || litTriples.consume(subject, predicate, object);
    }

    protected boolean hasPredicate(IRI subject, IRI predicate) {
        return singlePredicateResources.contains(subject, predicate)
            || singlePredicateLiterals.contains(subject, predicate)
            || resTriples.contains(subject, predicate)
            || litTriples.contains(subject, predicate);
    }

    protected boolean consumeTriple(IRI subject, IRI predicate, IRI object) {
        tripleLogger.justLog(subject, predicate, object);
        return consumeIfPresent(subject, predicate, object);
    }

    protected boolean consumeTriple(IRI subject, IRI predicate, OWLLiteral con) {
        tripleLogger.justLog(subject, predicate, con);
        return consumeIfPresent(subject, predicate, con);
    }

    protected void addTriple(IRI subject, IRI predicate, IRI object) {
        if (!singlePredicateResources.add(subject, predicate, object)) {
            resTriples.add(subject, predicate, object);
        }
    }

    protected void addTriple(IRI subject, IRI predicate, OWLLiteral l) {
        if (!singlePredicateLiterals.add(subject, predicate, l)) {
            litTriples.add(subject, predicate, l);
        }
    }

    protected boolean isLiteralPresent(IRI mainNode, OWLRDFVocabulary p) {
        return literal(mainNode, p.getIRI(), false) != null;
    }

    protected boolean isNonNegativeIntegerStrict(IRI mainNode, OWLRDFVocabulary p) {
        OWLLiteral literal = literal(mainNode, p.getIRI(), false);
        if (literal == null) {
            return false;
        }
        return OWL2Datatype.XSD_NON_NEGATIVE_INTEGER.matches(literal.getDatatype())
            && OWL2Datatype.XSD_NON_NEGATIVE_INTEGER
            .isInLexicalSpace(literal.getLiteral());
    }

    protected boolean isNonNegativeIntegerLax(IRI mainNode, OWLRDFVocabulary p) {
        OWLLiteral literal = literal(mainNode, p.getIRI(), false);
        if (literal == null) {
            return false;
        }
        return OWL2Datatype.XSD_INTEGER
            .isInLexicalSpace(verifyNotNull(literal.getLiteral().trim()));
    }

    protected int integer(IRI mainNode, OWLRDFVocabulary p) {
        OWLLiteral literal = literal(mainNode, p.getIRI(), true);
        if (literal == null) {
            return 0;
        }
        try {
            return Integer.parseInt(literal.getLiteral().trim());
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
            return 0;
        }
    }

    protected boolean isResourcePresent(IRI mainNode, OWLRDFVocabulary p) {
        return resource(mainNode, p, false) != null;
    }

    protected Set<RDFTriple> getRemainingTriples(Predicate<IRI> anon) {
        Set<RDFTriple> remaining = new HashSet<>();
        resTriples.iterate((s, p, o) -> remaining.add(new RDFTriple(s, anon.test(s), isAxiom(s), p,
            o, anon.test(o), isAxiom(o))));
        litTriples.iterate((s, p, o) -> remaining
            .add(new RDFTriple(s, anon.test(s), isAxiom(s), p, o)));
        return remaining;
    }

    protected boolean isAxiom(IRI s) {
        return resTriples.contains(s, OWLRDFVocabulary.RDF_TYPE.getIRI(),
            OWLRDFVocabulary.OWL_AXIOM.getIRI());
    }

    public void iterate(TripleIterator<IRI> t1) {
        // Inverse property axioms
        resTriples.iterate(t1);
    }

    public void iterate(TripleIterator<IRI> t1, TripleIterator<IRI> t2,
        TripleIterator<OWLLiteral> t3, TripleIterator<IRI> t4, TripleIterator<IRI> t5,
        TripleIterator<OWLLiteral> t6) {
        resTriples.iterate(t1);
        // Now handle non-reserved predicate triples
        resTriples.iterate(t2);
        litTriples.iterate(t3);
        // Now axiom annotations
        resTriples.iterate(t4);
        resTriples.iterate(t5);
        litTriples.iterate(t6);

    }

}
