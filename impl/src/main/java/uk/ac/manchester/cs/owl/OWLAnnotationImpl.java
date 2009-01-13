package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLObject;

import java.net.URI;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 19-Dec-2006<br><br>
 */
public abstract class OWLAnnotationImpl<O extends OWLObject> extends OWLObjectImpl implements OWLAnnotation<O> {

    private URI uri;

    private O object;


    public OWLAnnotationImpl(OWLDataFactory dataFactory, URI uri, O object) {
        super(dataFactory);
        this.uri = uri;
        this.object = object;
    }


    public URI getAnnotationURI() {
        return uri;
    }


    public O getAnnotationValue() {
        return object;
    }

    public boolean isComment() {
        return false;
    }


    public boolean isLabel() {
        return false;
    }

    public boolean equals(Object obj) {
        if(super.equals(obj)) {
            if(obj instanceof OWLAnnotation) {
               OWLAnnotation other = (OWLAnnotation) obj;
               return other.getAnnotationURI().equals(uri) && other.getAnnotationValue().equals(object);
            }
        }
        return false;
    }

    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotation other = (OWLAnnotation) object;
        int diff = getAnnotationURI().compareTo(other.getAnnotationURI());
        if(diff != 0) {
            return diff;
        }
        else {
            return getAnnotationValue().compareTo(other.getAnnotationValue());
        }
    }
}
