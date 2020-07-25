package org.semanticweb.owlapi.rdf;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

/** Helper class for absolute and relative IRIs on rendering. */
public class AbsoluteIRIHelper {
    /**
     * @param iri iri to check
     * @param f format used in rendering
     * @param o ontology being rendered
     * @return absolute version of iri; same as input if the input is absolute, else the prefix is
     *         taken from the prefix mappings or from the ontology id. If none of there are
     *         available, a hard coded prefix is used.
     */
    public static IRI verifyAbsolute(IRI iri, @Nullable OWLDocumentFormat f, OWLOntology o) {
        if (iri.isAbsolute()) {
            return iri;
        }
        if (f == null || f.supportsRelativeIRIs()) {
            return iri;
        }
        String defaultPrefix = null;
        if (f.isPrefixOWLDocumentFormat()) {
            defaultPrefix = f.asPrefixOWLDocumentFormat().getDefaultPrefix();
        }
        if (defaultPrefix == null) {
            defaultPrefix = o.getOntologyID().getOntologyIRI().map(IRI::toString).orElse(null);
        }
        if (defaultPrefix == null) {
            defaultPrefix =
                o.getOntologyID().getDefaultDocumentIRI().map(IRI::toString).orElse(null);
        }
        if (defaultPrefix == null) {
            // no reliable information on the base IRI
            return IRI.create("urn:absoluteiri:defaultvalue#" + iri);
        }
        return IRI.create(defaultPrefix + iri);
    }
}
