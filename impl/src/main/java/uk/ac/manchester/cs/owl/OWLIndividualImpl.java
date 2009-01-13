package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.*;

import java.net.URI;
import java.util.*;
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
 * Date: 25-Oct-2006<br><br>
 */
public class OWLIndividualImpl extends OWLObjectImpl implements OWLIndividual {

    private boolean anon;

    protected URI uri;


    protected OWLIndividualImpl(OWLDataFactory dataFactory, URI uri, boolean anon) {
        super(dataFactory);
        this.uri = uri;
        this.anon = anon;
    }


    public boolean isBuiltIn() {
        return false;
    }


    /**
     * Gets the URI
     */
    public URI getURI() {
        return uri;
    }


    /**
     * Determines if this object represents an anonymous individual.
     * @return <code>true</code> if this object represents an anonymous
     *         individual (<code>OWLAnonymousIndividual)</code> or <code>false</code>
     *         if this object represents a named individual (<code>OWLIndividual</code>)
     */
    public boolean isAnonymous() {
        return anon;
    }


    public Set<OWLDescription> getTypes(OWLOntology ontology) {
        Set<OWLDescription> result = new TreeSet<OWLDescription>();
        for (OWLClassAssertionAxiom axiom : ontology.getClassAssertionAxioms(this)) {
            result.add(axiom.getDescription());
        }
        return result;
    }


    public Set<OWLDescription> getTypes(Set<OWLOntology> ontologies) {
        Set<OWLDescription> result = new TreeSet<OWLDescription>();
        for(OWLOntology ont : ontologies) {
            result.addAll(getTypes(ont));
        }
        return result;
    }


    public Map<OWLProperty, Set<OWLObject>> getPropertyValues(OWLOntology ontology) {
        Map<OWLProperty, Set<OWLObject>> results = new HashMap<OWLProperty, Set<OWLObject>>();
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> opMap = getObjectPropertyValues(ontology);
        for(OWLObjectPropertyExpression prop : opMap.keySet()) {
            results.put((OWLProperty) prop, new HashSet<OWLObject>(opMap.get(prop)));
        }
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dpMap = getDataPropertyValues(ontology);
        for(OWLDataPropertyExpression prop : dpMap.keySet()) {
            results.put((OWLProperty) prop, new HashSet<OWLObject>(dpMap.get(prop)));
        }
        return results;
    }

    public Map<OWLProperty, Set<OWLObject>> getNPropertyValues(OWLOntology ontology) {
        Map<OWLProperty, Set<OWLObject>> results = new HashMap<OWLProperty, Set<OWLObject>>();
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> opMap = getObjectPropertyValues(ontology);
        for(OWLObjectPropertyExpression prop : opMap.keySet()) {
            results.put((OWLProperty) prop, new HashSet<OWLObject>(opMap.get(prop)));
        }
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dpMap = getDataPropertyValues(ontology);
        for(OWLDataPropertyExpression prop : dpMap.keySet()) {
            results.put((OWLProperty) prop, new HashSet<OWLObject>(dpMap.get(prop)));
        }
        return results;
    }

    public Map<OWLObjectPropertyExpression, Set<OWLIndividual>> getObjectPropertyValues(OWLOntology ontology) {
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result = new HashMap<OWLObjectPropertyExpression, Set<OWLIndividual>>();
        for (OWLObjectPropertyAssertionAxiom ax : ontology.getObjectPropertyAssertionAxioms(this)) {
            Set<OWLIndividual> inds = result.get(ax.getProperty());
            if (inds == null) {
                inds = new TreeSet<OWLIndividual>();
                result.put(ax.getProperty(), inds);
            }
            inds.add(ax.getObject());
        }
        return result;
    }


    public Map<OWLObjectPropertyExpression, Set<OWLIndividual>> getNegativeObjectPropertyValues(OWLOntology ontology) {
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result = new HashMap<OWLObjectPropertyExpression, Set<OWLIndividual>>();
        for(OWLNegativeObjectPropertyAssertionAxiom ax : ontology.getNegativeObjectPropertyAssertionAxioms(this)) {
            Set<OWLIndividual> inds = result.get(ax.getProperty());
            if(inds == null) {
                inds = new TreeSet<OWLIndividual>();
                result.put(ax.getProperty(), inds);
            }
            inds.add(ax.getObject());
        }
        return result;
    }


    public Map<OWLDataPropertyExpression, Set<OWLLiteral>> getDataPropertyValues(OWLOntology ontology) {
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result = new HashMap<OWLDataPropertyExpression, Set<OWLLiteral>>();
        for (OWLDataPropertyAssertionAxiom ax : ontology.getDataPropertyAssertionAxioms(this)) {
            Set<OWLLiteral> vals = result.get(ax.getProperty());
            if (vals == null) {
                vals = new TreeSet<OWLLiteral>();
                result.put(ax.getProperty(), vals);
            }
            vals.add(ax.getObject());
        }
        return result;
    }


    public Map<OWLDataPropertyExpression, Set<OWLLiteral>> getNegativeDataPropertyValues(OWLOntology ontology) {
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result = new HashMap<OWLDataPropertyExpression, Set<OWLLiteral>>();
        for(OWLNegativeDataPropertyAssertionAxiom ax : ontology.getNegativeDataPropertyAssertionAxioms(this)) {
            Set<OWLLiteral> inds = result.get(ax.getProperty());
            if(inds == null) {
                inds = new TreeSet<OWLLiteral>();
                result.put(ax.getProperty(), inds);
            }
            inds.add(ax.getObject());
        }
        return result;
    }


    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
        return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    }


    public Set<OWLAnnotationAxiom> getAnnotationAxioms(OWLOntology ontology) {
        return ImplUtils.getAnnotationAxioms(this, Collections.singleton(ontology));
    }


    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology, URI annotationURI) {
        return ImplUtils.getAnnotations(this, annotationURI, Collections.singleton(ontology));
    }


    public Set<OWLClassAssertionAxiom> getIndividualTypeAxioms(OWLOntology ontology) {
        return ontology.getClassAssertionAxioms(this);
    }


    public Set<OWLObjectPropertyAssertionAxiom> getIndividualObjectRelationshipAxioms(OWLOntology ontology) {
        return ontology.getObjectPropertyAssertionAxioms(this);
    }


    public Set<OWLDataPropertyAssertionAxiom> getIndividualDataRelationshipAxioms(OWLOntology ontology) {
        return ontology.getDataPropertyAssertionAxioms(this);
    }


    public Set<OWLIndividual> getSameIndividuals(OWLOntology ontology) {
        Set<OWLIndividual> result = new TreeSet<OWLIndividual>();
        for (OWLSameIndividualsAxiom ax : ontology.getSameIndividualAxioms(this)) {
            result.addAll(ax.getIndividuals());
        }
        result.remove(this);
        return result;
    }


    public Set<OWLIndividual> getDifferentIndividuals(OWLOntology ontology) {
        Set<OWLIndividual> result = new TreeSet<OWLIndividual>();
        for(OWLDifferentIndividualsAxiom ax : ontology.getDifferentIndividualAxioms(this)) {
            result.addAll(ax.getIndividuals());
        }
        result.remove(this);
        return result;
    }


    public Set<OWLNegativeObjectPropertyAssertionAxiom> getIndividualNotObjectRelationshipAxioms(
            OWLOntology ontology) throws OWLException {
        return ontology.getNegativeObjectPropertyAssertionAxioms(this);
    }


    public Set<OWLNegativeDataPropertyAssertionAxiom> getIndividualNotDataRelationshipAxioms(
            OWLOntology ontology) throws OWLException {
        return ontology.getNegativeDataPropertyAssertionAxioms(this);
    }


    public OWLClass asOWLClass() {
        throw new OWLRuntimeException("Not an OWLClass!");
    }


    public OWLDataProperty asOWLDataProperty() {
        throw new OWLRuntimeException("Not a data property!");
    }


    public OWLDatatype asOWLDataType() {
        throw new OWLRuntimeException("Not a data type!");
    }


    public OWLIndividual asOWLIndividual() {
        return this;
    }


    public OWLObjectProperty asOWLObjectProperty() {
        throw new OWLRuntimeException("Not an object property");
    }


    public boolean isOWLClass() {
        return false;
    }


    public boolean isOWLDataProperty() {
        return false;
    }


    public boolean isOWLDataType() {
        return false;
    }


    public boolean isOWLIndividual() {
        return true;
    }


    public boolean isOWLObjectProperty() {
        return false;
    }


    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLIndividual)) {
                return false;
            }
            URI otherURI = ((OWLIndividual) obj).getURI();
            String otherFragment = otherURI.getFragment();
            String thisFragment = uri.getFragment();
            if (otherFragment != null && thisFragment != null && !otherFragment.equals(thisFragment)) {
                return false;
            }
            return ((OWLIndividual) obj).getURI().equals(uri);
        }
        return false;
    }


    public void accept(OWLEntityVisitor visitor) {
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


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    protected int compareObjectOfSameType(OWLObject object) {
        return uri.compareTo(((OWLIndividual) object).getURI());
    }
}
