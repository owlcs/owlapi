package org.semanticweb.owlapi6.formats;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.semanticweb.owlapi6.model.OWLDocumentFormat;

/**
 * A mapping between some known ontology formats and the common file extensions used for them. This
 * mapping is not necessarily complete and file extensions are not mandatory, so do not rely on this
 * as a filter to determine what is the format of an input file.
 */
public enum Extensions {
    //@formatter:off
    /** RDF/XML, common extensions: owl, rdf, rdfs */           RDFXML              (RDFXMLDocumentFormat.class,            ".owl", ".rdf", ".rdfs"), 
    /** OWL/XML, common extensions: xml, owl, rdf */            OWLXML              (OWLXMLDocumentFormat.class,            ".xml", ".owl", ".rdf"), 
    /** Turtle, common extensions: ttl, owl */                  TURTLE              (TurtleDocumentFormat.class,            ".ttl", ".owl"), 
    /** OBO, common extensions: obo */                          OBO                 (OBODocumentFormat.class,               ".obo"), 
    /** Manchester OWL syntax, common extensions: omn, owl */   MANCHESTERSYNTAX    (ManchesterSyntaxDocumentFormat.class,  ".omn", ".owl"), 
    /** Functional sytax, common extensions: fss, owl */        FUNCTIONALSYNTAX    (FunctionalSyntaxDocumentFormat.class,  ".fss", ".owl");
    //@formatter:on

    private List<String> extensionList;
    private Class<? extends OWLDocumentFormat> documentFormat;

    private Extensions(Class<? extends OWLDocumentFormat> d, String... knownExtensions) {
        documentFormat = d;
        extensionList = Arrays.asList(knownExtensions);
    }

    /**
     * @param format the format for which extensions are desired
     * @return common extensions list. Empty list if no matching type is found.
     */
    public static Iterable<String> getCommonExtensions(Class<? extends OWLDocumentFormat> format) {
        for (Extensions e : values()) {
            if (e.documentFormat.equals(format)) {
                return e.getCommonExtensions();
            }
        }
        return Collections.emptyList();
    }

    /**
     * @return common extensions for this type
     */
    public Iterable<String> getCommonExtensions() {
        return extensionList;
    }
}
