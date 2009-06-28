package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

import java.net.URI;
import java.util.Collections;
import java.util.Set;/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 14-Jan-2009
 */
public class OWLAnnotationPropertyImpl extends OWLObjectImpl implements OWLAnnotationProperty {

    private IRI iri;

    public OWLAnnotationPropertyImpl(OWLDataFactory dataFactory, IRI iri) {
        super(dataFactory);
        this.iri = iri;
    }

    public IRI getIRI() {
        return iri;
    }

    public boolean isDeprecated() {
        return iri.toURI().equals(OWLRDFVocabulary.OWL_DEPRECATED.getURI());
    }

    protected int compareObjectOfSameType(OWLObject object) {
        return iri.compareTo(((OWLAnnotationProperty) object).getIRI());
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public boolean isComment() {
        return iri.toURI().equals(OWLRDFVocabulary.RDFS_COMMENT.getURI());
    }

    public boolean isLabel() {
        return iri.toURI().equals(OWLRDFVocabulary.RDFS_LABEL.getURI());
    }


    public void accept(OWLEntityVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLEntityVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public OWLClass asOWLClass() {
        throw new OWLRuntimeException("Not OWLClass");
    }

    public OWLDataProperty asOWLDataProperty() {
        throw new OWLRuntimeException("Not OWLDataProperty");
    }

    public OWLDatatype asOWLDatatype() {
        throw new OWLRuntimeException("Not OWLDatatype");
    }

    public OWLNamedIndividual asOWLNamedIndividual() {
        throw new OWLRuntimeException("Not OWLIndividual");
    }

    public OWLObjectProperty asOWLObjectProperty() {
        throw new OWLRuntimeException("Not OWLObjectProperty");
    }

    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLOntology ontology) {
        return ImplUtils.getAnnotationAxioms(this, Collections.singleton(ontology));
    }

    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
        return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    }

    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology, OWLAnnotationProperty annotationProperty) {
        return ImplUtils.getAnnotations(this, annotationProperty, Collections.singleton(ontology));
    }

    public boolean isBuiltIn() {
        return OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTIES.contains(getURI());
    }

    public boolean isOWLClass() {
        return false;
    }

    public boolean isOWLDataProperty() {
        return false;
    }

    public boolean isOWLDatatype() {
        return false;
    }

    public boolean isOWLNamedIndividual() {
        return false;
    }

    public boolean isOWLObjectProperty() {
        return false;
    }

    public OWLAnnotationProperty asOWLAnnotationProperty() {
        return this;
    }

    public boolean isOWLAnnotationProperty() {
        return true;
    }

    public void accept(OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }

    public URI getURI() {
        return iri.toURI();
    }


    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof OWLAnnotationProperty)) {
            return false;
        }
        OWLAnnotationProperty other = (OWLAnnotationProperty) obj;
        return iri.equals(other.getIRI());
    }

}
