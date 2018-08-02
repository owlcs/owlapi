package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.utilities.Injector;

/** Utility class to hold an injected factory for the enumerations in this package. */
class VocabFactory {
    static OWLDataFactory df = new Injector().getImplementation(OWLDataFactory.class);

    private VocabFactory() {}
}
