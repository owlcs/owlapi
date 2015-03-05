package org.semanticweb.owlapi.vocab;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * A mapping between some known ontology formats and the common file extensions
 * used for them. This mapping is not necessarily complete and file extensions
 * are not mandatory, so do not rely on this as a filter to determine what is
 * the format of an input file.
 */
public enum Extensions {
    /** RDF/XML, common extensions: owl, rdf, rdfs */
    RDFXML(
            "org.semanticweb.owlapi.model.OWLOntologyFormat",
            ".owl",
            ".rdf",
            ".rdfs"),
    /** OWL/XML, common extensions: xml, owl, rdf */
    OWLXML("org.semanticweb.owlapi.io.OWLXMLOntologyFormat", ".xml"),
    /** Turtle, common extensions: ttl, owl */
    TURTLE("org.coode.owlapi.turtle.TurtleOntologyFormat", ".ttl"),
    /** OBO, common extensions: obo */
    OBO("org.coode.owlapi.obo.parser.OBOOntologyFormat", ".obo"),
    /** Manchester OWL syntax, common extensions: omn, owl */
    MANCHESTERSYNTAX(
            "org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat",
            ".omn"),
    /** Functional sytax, common extensions: fss, owl */
    FUNCTIONALSYNTAX(
            "org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat",
            ".fss");

    private List<String> extensions;
    private String documentFormat;

    private Extensions(String d, String... knownExtensions) {
        documentFormat = d;
        extensions = Arrays.asList(knownExtensions);
    }

    /**
     * @return common extensions for this type
     */
    public Collection<String> getCommonExtensions() {
        return extensions;
    }

    /**
     * @param format
     *        the format for which extensions are desired
     * @return common extensions list. Empty list if no matching type is found.
     */
    public static Collection<String> getCommonExtensions(
            Class<? extends OWLOntologyFormat> format) {
        if (format == null) {
            return Collections.emptyList();
        }
        for (Extensions e : values()) {
            if (e.documentFormat.equals(format.getCanonicalName())) {
                return e.getCommonExtensions();
            }
        }
        return Collections.emptyList();
    }

    /**
     * @param format
     *        the format for which extensions are desired
     * @return common extensions list. Empty list if no matching type is found.
     */
    public static Collection<String> getCommonExtensions(
            OWLOntologyFormat format) {
        if (format == null) {
            return Collections.emptyList();
        }
        if (format.getCommonExtensions().isEmpty()) {
            return getCommonExtensions(format.getClass());
        }
        return format.getCommonExtensions();
    }
}
