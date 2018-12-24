package org.semanticweb.owlapi6.vocab;

import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.utilities.Injector;

/** Utility class to hold an injected factory for the enumerations in this package. */
class VocabFactory {
    static OWLDataFactory df = new Injector().getImplementation(OWLDataFactory.class);

    private VocabFactory() {}
}
