package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.IRI;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 03-Oct-2007<br><br>
 */
public enum SKOSVocabulary {

    BROADER("broader"),

    NARROWER("narrower"),

    RELATED("related"),

    HASTOPCONCEPT("hasTopConcept"),

    SEMANTICRELATION("semanticRelation"),

    CONCPET("Concept"),

    CONCEPTSCHEME("ConceptScheme"),

    TOPCONCEPT("TopConcept"),

    DOCUMENT("Document"),

    IMAGE("Image"),

    ORDEREDCOLLECTION("OrderedCollection"),

    COLLECTABLEPROPERTY("CollectableProperty"),

    RESOURCE("Resource"),

    PREFLABEL("prefLabel"),

    ALTLABEL("altLabel"),

    COMMENT("comment"),

    SCOPENOTE("scopeNote"),

    HIDDENLABEL("hiddenLabel"),

    EDITORIALNOTE("editorialNote"),

    DEFINITION("definition"),

    CHANGENOTE("changeNote");

    public static final Set<URI> ALL_URIS;

    static {
        ALL_URIS = new HashSet<URI>();
        for(SKOSVocabulary v : SKOSVocabulary.values()) {
            ALL_URIS.add(v.getURI());
        }
    }

    private String localName;

    private IRI iri;

    SKOSVocabulary(String localname) {
        this.localName = localname;
        this.iri = IRI.create(Namespaces.SKOS.toString() + localname);
    }


    public String getLocalName() {
        return localName;
    }

    public IRI getIRI() {
        return iri;
    }

    public URI getURI() {
        return iri.toURI();
    }

}
