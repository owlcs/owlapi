package org.semanticweb.owlapi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 11-Oct-2009
 * </p>
 * Represents the different types of OWL 2 Entities.
 */
public final class EntityType<E extends OWLEntity> {

    public static final EntityType<OWLClass> CLASS = new EntityType<OWLClass>("Class", OWLRDFVocabulary.OWL_CLASS);

    public static final EntityType<OWLObjectProperty> OBJECT_PROPERTY = new EntityType<OWLObjectProperty>("ObjectProperty", OWLRDFVocabulary.OWL_OBJECT_PROPERTY);

    public static final EntityType<OWLDataProperty> DATA_PROPERTY = new EntityType<OWLDataProperty>("DataProperty", OWLRDFVocabulary.OWL_DATA_PROPERTY);

    public static final EntityType<OWLAnnotationProperty> ANNOTATION_PROPERTY = new EntityType<OWLAnnotationProperty>("AnnotationProperty", OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY);

    public static final EntityType<OWLNamedIndividual> NAMED_INDIVIDUAL = new EntityType<OWLNamedIndividual>("NamedIndividual", OWLRDFVocabulary.OWL_NAMED_INDIVIDUAL);

    public static final EntityType<OWLDatatype> DATATYPE = new EntityType<OWLDatatype>("Datatype", OWLRDFVocabulary.RDFS_DATATYPE);


    private static List<EntityType<?>> values;

    static {
    	List<EntityType<?>> l=new ArrayList<EntityType<?>>();
    	l.add(CLASS);l.add(OBJECT_PROPERTY);l.add( DATA_PROPERTY);l.add( ANNOTATION_PROPERTY);l.add( NAMED_INDIVIDUAL);l.add( DATATYPE);
        values = Collections.unmodifiableList(l);
    }

    private String name;

    private OWLRDFVocabulary vocabulary;

    private EntityType(String name, OWLRDFVocabulary vocabulary) {
        this.name = name;
        this.vocabulary = vocabulary;
    }

    public OWLRDFVocabulary getVocabulary() {
        return vocabulary;
    }

    public String getName() {
        return name;
    }


    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return name;
    }

    public static List<EntityType<?>> values() {
        return values;
    }
    
}
