package org.semanticweb.owlapi.rdf;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

import com.google.common.base.Optional;

/** Helper class for absolute and relative IRIs on rendering. */
public class AbsoluteIRIHelper {
    /**
     * @param iri iri to check
     * @param f format used in rendering
     * @param o ontology being rendered
     * @return absolute version of iri; same as input if the input is absolute, else the prefix is
     *         taken from the prefix mappings or from the ontology id. If none of thee are
     *         available, a hardcoded prefix is used.
     */
    public static IRI verifyAbsolute(IRI iri, @Nullable OWLDocumentFormat f, OWLOntology o) {
        if (iri.isAbsolute()) {
            return iri;
        }
        if (f == null || f.supportsRelativeIRIs()) {
            return iri;
        }
        String defaultPrefix = null;
        if (f.isPrefixOWLOntologyFormat()) {
            defaultPrefix = f.asPrefixOWLOntologyFormat().getDefaultPrefix();
        }
        if (defaultPrefix == null) {
            Optional<IRI> ontologyIRI = o.getOntologyID().getOntologyIRI();
            if (ontologyIRI.isPresent()) {
                defaultPrefix = ontologyIRI.get().toString();
            }
        }
        if (defaultPrefix == null) {
            Optional<IRI> defaultDocumentIRI = o.getOntologyID().getDefaultDocumentIRI();
            if (defaultDocumentIRI.isPresent()) {
                defaultPrefix = defaultDocumentIRI.get().toString();
            }
        }
        if (defaultPrefix == null) {
            // no reliable information on the base IRI
            return IRI.create("urn:absoluteiri:defaultvalue#" + iri);
        }
        return IRI.create(defaultPrefix + iri);
    }
}
