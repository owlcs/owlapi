package org.semanticweb.owlapi.vocab;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Mar-2007<br><br>
 */
public enum DublinCoreVocabulary {

    CONTRIBUTOR("contributor"),
    COVERAGE("coverage"),
    CREATOR("creator"),
    DATE("date"),
    DESCRIPTION("description"),
    FORMAT("format"),
    IDENTIFIER("identifier"),
    LANGUAGE("language"),
    PUBLISHER("publisher"),
    RELATION("relation"),
    RIGHTS("rights"),
    SOURCE("source"),
    SUBJECT("subject"),
    TITLE("title"),
    TYPE("type");

    private String shortName;

    private String qname;

    private IRI iri;

    public static final String NAME_SPACE = "http://purl.org/dc/elements/1.1/";


    DublinCoreVocabulary(String name) {
        shortName = name;
        qname = "dc:" + name;
        iri = IRI.create(NAME_SPACE + name);
    }

    public String getShortName() {
        return shortName;
    }

    public String getQName() {
        return qname;
    }

    public URI getURI() {
        return iri.toURI();
    }

    public IRI getIRI() {
        return iri;
    }

    public static final Set<IRI> ALL_URIS;


    static {
        ALL_URIS = new HashSet<IRI>();
        for (DublinCoreVocabulary v : DublinCoreVocabulary.values()) {
            ALL_URIS.add(v.getIRI());
        }
    }


    @Override
	public String toString() {
        return iri.toString();
    }
}
