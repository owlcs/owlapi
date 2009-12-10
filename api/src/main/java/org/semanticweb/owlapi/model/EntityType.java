package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
/*
 * Copyright (C) 2009, University of Manchester
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


    private static EntityType [] values;

    static {
        values = new EntityType [] {CLASS, OBJECT_PROPERTY, DATA_PROPERTY, ANNOTATION_PROPERTY, NAMED_INDIVIDUAL, DATATYPE};
    }

    private String name;

    private OWLRDFVocabulary vocabulary;

    EntityType(String name, OWLRDFVocabulary vocabulary) {
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

    public static EntityType [] values() {
        return values;
    }
    
}
