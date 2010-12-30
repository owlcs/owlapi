package org.semanticweb.owlapi.vocab;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;


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

    CONCEPT("Concept", EntityType.CLASS),

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

    private EntityType<?> entityType;

    SKOSVocabulary(String localname, EntityType<?> entityType) {
        this.localName = localname;
        this.entityType = entityType;
        this.iri = IRI.create(Namespaces.SKOS.toString() + localname);
    }

    public EntityType<?> getEntityType() {
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
