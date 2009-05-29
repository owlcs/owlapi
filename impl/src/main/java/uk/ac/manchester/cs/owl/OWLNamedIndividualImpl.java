package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.*;

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
 * Date: 15-Jan-2009
 */
public class OWLNamedIndividualImpl extends OWLIndividualImpl implements OWLNamedIndividual {

    private IRI iri;

    public OWLNamedIndividualImpl(OWLDataFactory dataFactory, IRI iri) {
        super(dataFactory);
        this.iri = iri;
    }

    public boolean isOWLIndividual() {
        return true;
    }

    public IRI getIRI() {
        return iri;
    }

    public URI getURI() {
        return getIRI().toURI();
    }

    public boolean isAnonymous() {
        return false;
    }

    public OWLNamedIndividual asNamedIndividual() {
        return this;
    }

    public OWLNamedIndividual asOWLIndividual() {
        return this;
    }

    public OWLAnonymousIndividual asAnonymousIndividual() {
        throw new OWLRuntimeException("Not an anonymous individual");
    }

    public OWLAnnotationProperty asOWLAnnotationProperty() {
        throw new OWLRuntimeException("Not an annotation property");
    }

    public boolean isOWLAnnotationProperty() {
        return false;
    }

    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLIndividual)) {
                return false;
            }
            URI otherURI = ((OWLNamedIndividual) obj).getURI();
            String otherFragment = otherURI.getFragment();
            String thisFragment = getURI().getFragment();
            if (otherFragment != null && thisFragment != null && !otherFragment.equals(thisFragment)) {
                return false;
            }
            return ((OWLNamedIndividual) obj).getURI().equals(getURI());
        }
        return false;
    }


    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
        return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    }


    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLOntology ontology) {
        return ImplUtils.getAnnotationAxioms(this, Collections.singleton(ontology));
    }


    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology, OWLAnnotationProperty annotationProperty) {
        return ImplUtils.getAnnotations(this, annotationProperty, Collections.singleton(ontology));
    }

    protected int compareObjectOfSameType(OWLObject object) {
        OWLNamedIndividual other = (OWLNamedIndividual) object;
        return getURI().compareTo(other.getURI());
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLEntityVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLEntityVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLIndividualVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLIndividualVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


}
