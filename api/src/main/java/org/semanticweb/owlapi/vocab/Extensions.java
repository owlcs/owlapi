package org.semanticweb.owlapi.vocab;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.coode.owlapi.obo.parser.OBOOntologyFormat;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * A mapping between some known ontology formats and the common file extensions
 * used for them. This mapping is not necessarily complete and file extensions
 * are not mandatory, so do not rely on this as a filter to determine what is
 * the format of an input file.
 */
public enum Extensions {
    /** RDF/XML, common extensions: owl, rdf, rdfs */
    RDFXML(RDFXMLOntologyFormat.class, ".owl", ".rdf", ".rdfs"),
    /** OWL/XML, common extensions: xml, owl, rdf */
    OWLXML(OWLXMLOntologyFormat.class, ".xml", ".owl", ".rdf"),
    /** Turtle, common extensions: ttl, owl */
    TURTLE(TurtleOntologyFormat.class, ".ttl", ".owl"),
    /** OBO, common extensions: obo */
    OBO(OBOOntologyFormat.class, ".obo"),
    /** Manchester OWL syntax, common extensions: omn, owl */
    MANCHESTERSYNTAX(ManchesterOWLSyntaxOntologyFormat.class, ".omn", ".owl"),
    /** Functional sytax, common extensions: fss, owl */
    FUNCTIONALSYNTAX(OWLFunctionalSyntaxOntologyFormat.class, ".fss", ".owl");

    private List<String> extensions;
    private Class<? extends OWLOntologyFormat> documentFormat;

    private Extensions(Class<? extends OWLOntologyFormat> d,
            String... knownExtensions) {
        documentFormat = d;
        extensions = Arrays.asList(knownExtensions);
    }

    /**
     * @return common extensions for this type
     */
    @Nonnull
    public Iterable<String> getCommonExtensions() {
        return extensions;
    }

    /**
     * @param format
     *        the format for which extensions are desired
     * @return common extensions list. Empty list if no matching type is found.
     */
    @Nonnull
    public static Iterable<String> getCommonExtensions(
            Class<? extends OWLOntologyFormat> format) {
        for (Extensions e : values()) {
            if (e.documentFormat.equals(format)) {
                return e.getCommonExtensions();
            }
        }
        return Collections.emptyList();
    }
}
