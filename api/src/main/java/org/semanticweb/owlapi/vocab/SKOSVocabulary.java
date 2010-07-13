package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.*;

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

    BROADMATCH("broadMatch", EntityType.OBJECT_PROPERTY),

    BROADER("broader", EntityType.OBJECT_PROPERTY),

    BROADERTRANSITIVE("broaderTransitive", EntityType.OBJECT_PROPERTY),

    CLOSEMATCH("closeMatch", EntityType.OBJECT_PROPERTY),

    EXACTMATCH("exactMatch", EntityType.OBJECT_PROPERTY),

    HASTOPCONCEPT("hasTopConcept", EntityType.OBJECT_PROPERTY),

    INSCHEME("inScheme", EntityType.OBJECT_PROPERTY),

    MAPPINGRELATION("mappingRelation", EntityType.OBJECT_PROPERTY),

    MEMBER("member", EntityType.OBJECT_PROPERTY),

    MEMBERLIST("memberList", EntityType.OBJECT_PROPERTY),

    NARROWMATCH("narrowMatch", EntityType.OBJECT_PROPERTY),

    NARROWER("narrower", EntityType.OBJECT_PROPERTY),

    NARROWTRANSITIVE("narrowTransitive", EntityType.OBJECT_PROPERTY),

    RELATED("related", EntityType.OBJECT_PROPERTY),

    RELATEDMATCH("relatedMatch", EntityType.OBJECT_PROPERTY),

    SEMANTICRELATION("semanticRelation", EntityType.OBJECT_PROPERTY),

    TOPCONCEPTOF("topConceptOf", EntityType.OBJECT_PROPERTY),




    COLLECTION("Collection", EntityType.CLASS),

    CONCPET("Concept", EntityType.CLASS),

    CONCEPTSCHEME("ConceptScheme", EntityType.CLASS),

    ORDEREDCOLLECTION("OrderedCollection", EntityType.CLASS),

    TOPCONCEPT("TopConcept", EntityType.CLASS),


    ALTLABEL("altLabel", EntityType.ANNOTATION_PROPERTY),

    CHANGENOTE("changeNote", EntityType.ANNOTATION_PROPERTY),

    DEFINITION("definition", EntityType.ANNOTATION_PROPERTY),

    EDITORIALNOTE("editorialNote", EntityType.ANNOTATION_PROPERTY),

    EXAMPLE("example", EntityType.ANNOTATION_PROPERTY),

    HIDDENLABEL("hiddenLabel", EntityType.ANNOTATION_PROPERTY),

    HISTORYNOTE("historyNote", EntityType.ANNOTATION_PROPERTY),

    NOTE("note", EntityType.ANNOTATION_PROPERTY),

    PREFLABEL("prefLabel", EntityType.ANNOTATION_PROPERTY),

    SCOPENOTE("scopeNote", EntityType.ANNOTATION_PROPERTY),


    /**
     * @Deprecated No longer used
     */
    DOCUMENT("Document", EntityType.CLASS),


     /**
     * @Deprecated No longer used
     */
    IMAGE("Image", EntityType.CLASS),


     /**
     * @Deprecated No longer used
     */
    COLLECTABLEPROPERTY("CollectableProperty", EntityType.ANNOTATION_PROPERTY),

     /**
     * @Deprecated No longer used
     */
    RESOURCE("Resource", EntityType.CLASS),


     /**
     * @Deprecated No longer used
     */
    COMMENT("comment", EntityType.ANNOTATION_PROPERTY);

    public static final Set<IRI> ALL_IRIS;

    static {
        ALL_IRIS = new HashSet<IRI>();
        for(SKOSVocabulary v : SKOSVocabulary.values()) {
            ALL_IRIS.add(v.getIRI());
        }
    }

    private String localName;

    private IRI iri;

    private EntityType entityType;

    SKOSVocabulary(String localname, EntityType entityType) {
        this.localName = localname;
        this.entityType = entityType;
        this.iri = IRI.create(Namespaces.SKOS.toString() + localname);
    }

    public EntityType getEntityType() {
        return entityType;
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

    public static Set<OWLAnnotationProperty> getAnnotationProperties(OWLDataFactory dataFactory) {
        Set<OWLAnnotationProperty> result = new HashSet<OWLAnnotationProperty>();
        for(SKOSVocabulary v : values()) {
            if(v.entityType.equals(EntityType.ANNOTATION_PROPERTY)) {
                result.add(dataFactory.getOWLAnnotationProperty(v.iri));
            }
        }
        return result;
    }


    public static Set<OWLObjectProperty> getObjectProperties(OWLDataFactory dataFactory) {
        Set<OWLObjectProperty> result = new HashSet<OWLObjectProperty>();
        for(SKOSVocabulary v : values()) {
            if(v.entityType.equals(EntityType.OBJECT_PROPERTY)) {
                result.add(dataFactory.getOWLObjectProperty(v.iri));
            }
        }
        return result;
    }

    public static Set<OWLDataProperty> getDataProperties(OWLDataFactory dataFactory) {
        Set<OWLDataProperty> result = new HashSet<OWLDataProperty>();
        for(SKOSVocabulary v : values()) {
            if(v.entityType.equals(EntityType.DATA_PROPERTY)) {
                result.add(dataFactory.getOWLDataProperty(v.iri));
            }
        }
        return result;
    }

    public static Set<OWLClass> getClasses(OWLDataFactory dataFactory) {
        Set<OWLClass> result = new HashSet<OWLClass>();
        for(SKOSVocabulary v : values()) {
            if(v.entityType.equals(EntityType.CLASS)) {
                result.add(dataFactory.getOWLClass(v.iri));
            }
        }
        return result;
    }

}
