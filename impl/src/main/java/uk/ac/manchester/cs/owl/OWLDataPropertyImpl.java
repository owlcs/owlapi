package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group<br> Date:
 * 26-Oct-2006<br><br>
 */
public class OWLDataPropertyImpl extends OWLPropertyExpressionImpl<OWLDataPropertyExpression, OWLDataRange> implements OWLDataProperty {

    private IRI iri;


    private boolean builtin;


    public OWLDataPropertyImpl(OWLDataFactory dataFactory, IRI iri) {
        super(dataFactory);
        this.iri = iri;
        this.builtin = getURI().equals(OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getURI()) ||
                getURI().equals(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getURI());
    }

    public boolean isDataPropertyExpression() {
        return true;
    }

    public boolean isObjectPropertyExpression() {
        return false;
    }

    public IRI getIRI() {
        return iri;
    }

    public URI getURI() {
        return iri.toURI();
    }


    public boolean isBuiltIn() {
        return builtin;
    }


    public boolean isFunctional(OWLOntology ontology) {
        return ontology.getFunctionalDataPropertyAxioms(this) != null;
    }


    public boolean isFunctional(Set<OWLOntology> ontologies) {
        for (OWLOntology ont : ontologies) {
            if (isFunctional(ont)) {
                return true;
            }
        }
        return false;
    }


    protected Set<? extends OWLNaryPropertyAxiom<OWLDataPropertyExpression>> getDisjointPropertiesAxioms(
            OWLOntology ontology) {
        return ontology.getDisjointDataPropertiesAxioms(this);
    }


    protected Set<? extends OWLPropertyDomainAxiom> getDomainAxioms(OWLOntology ontology) {
        return ontology.getDataPropertyDomainAxioms(this);
    }


    protected Set<? extends OWLPropertyRangeAxiom<OWLDataPropertyExpression, OWLDataRange>> getRangeAxioms(
            OWLOntology ontology) {
        return ontology.getDataPropertyRangeAxioms(this);
    }


    protected Set<? extends OWLSubPropertyAxiom<OWLDataPropertyExpression>> getSubPropertyAxioms(OWLOntology ontology) {
        return ontology.getDataSubPropertyAxiomsForSubProperty(this);
    }


    protected Set<? extends OWLNaryPropertyAxiom<OWLDataPropertyExpression>> getEquivalentPropertiesAxioms(
            OWLOntology ontology) {
        return ontology.getEquivalentDataPropertiesAxioms(this);
    }


    protected Set<? extends OWLSubPropertyAxiom<OWLDataPropertyExpression>> getSubPropertyAxiomsForRHS(
            OWLOntology ont) {
        return ont.getDataSubPropertyAxiomsForSuperProperty(this);
    }


    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLDataProperty)) {
                return false;
            }

            URI otherURI = ((OWLDataProperty) obj).getURI();
            String otherFragment = otherURI.getFragment();
            URI uri = getURI();
            String thisFragment = uri.getFragment();
            if (otherFragment != null && thisFragment != null && !otherFragment.equals(thisFragment)) {
                return false;
            }
            return otherURI.equals(uri);
        }
        return false;
    }


    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
        return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    }


    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLOntology ontology) {
        return ImplUtils.getAnnotationAxioms(this, Collections.singleton(ontology));
    }


    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology, URI annotationURI) {
        return ImplUtils.getAnnotations(this, annotationURI, Collections.singleton(ontology));
    }


    public void accept(OWLEntityVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLPropertyExpressionVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLEntityVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLPropertyExpressionVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public boolean isAnonymous() {
        return false;
    }


    public OWLDataProperty asOWLDataProperty() {
        return this;
    }


    public OWLClass asOWLClass() {
        throw new OWLRuntimeException("Not an OWLClass!");
    }


    public OWLDatatype asOWLDatatype() {
        throw new OWLRuntimeException("Not an OWLDatatype!");
    }


    public OWLNamedIndividual asOWLIndividual() {
        throw new OWLRuntimeException("Not an OWLIndividual!");
    }


    public OWLObjectProperty asOWLObjectProperty() {
        throw new OWLRuntimeException("Not an OWLObjectProperty!");
    }


    public boolean isOWLClass() {
        return false;
    }


    public boolean isOWLDataProperty() {
        return true;
    }


    public boolean isOWLDatatype() {
        return false;
    }


    public boolean isOWLIndividual() {
        return false;
    }


    public boolean isOWLObjectProperty() {
        return false;
    }

    public OWLAnnotationProperty asOWLAnnotationProperty() {
        throw new OWLRuntimeException("Not an annotation property");
    }

    public boolean isOWLAnnotationProperty() {
        return false;
    }


    protected int compareObjectOfSameType(OWLObject object) {
        return getURI().compareTo(((OWLDataProperty) object).getURI());
    }
}
