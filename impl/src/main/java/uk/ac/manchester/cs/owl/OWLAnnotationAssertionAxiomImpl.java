package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.*;

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
 * Date: 25-Nov-2006<br><br>
 */
public class OWLAnnotationAssertionAxiomImpl extends OWLAxiomImpl implements OWLAnnotationAssertionAxiom {

    private OWLAnnotationSubject subject;

    private OWLAnnotation annotation;

    private URI uri;

    public OWLAnnotationAssertionAxiomImpl(OWLDataFactory dataFactory, URI uri, OWLAnnotation annotation) {
        super(dataFactory);
        this.subject = subject;
        this.annotation = annotation;
        this.uri = uri;
    }

    public OWLAnnotationValue getValue() {
        return annotation.getValue();
    }

    public OWLAnnotationSubject getSubject() {
        throw new OWLRuntimeException("TODO");
//        return subject;
    }

    public OWLAnnotationProperty getProperty() {
        return annotation.getProperty();
    }

    public OWLAnnotation getAnnotation() {
        return annotation;
    }

    public boolean isLogicalAxiom() {
        return false;
    }

    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotationAssertionAxiom other = (OWLAnnotationAssertionAxiom) object;
        int diff = 0;
        diff = subject.compareTo(other.getSubject());
        if (diff != 0) {
            return diff;
        }
        return annotation.compareTo(other.getAnnotation());
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public AxiomType getAxiomType() {
        return AxiomType.ANNOTATION_ASSERTION;
    }
}
