package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

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
public abstract class OWLIndividualImpl extends OWLObjectImpl implements OWLIndividual {


    protected OWLIndividualImpl(OWLDataFactory dataFactory) {
        super(dataFactory);
    }


    public boolean isBuiltIn() {
        return false;
    }

    public boolean equals(Object obj) {
        return obj instanceof OWLIndividual;
    }

    public Set<OWLClassExpression> getTypes(OWLOntology ontology) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLClassAssertionAxiom axiom : ontology.getClassAssertionAxioms(this)) {
            result.add(axiom.getClassExpression());
        }
        return result;
    }


    public Set<OWLClassExpression> getTypes(Set<OWLOntology> ontologies) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getTypes(ont));
        }
        return result;
    }


    public Map<OWLProperty, Set<OWLObject>> getPropertyValues(OWLOntology ontology) {
        Map<OWLProperty, Set<OWLObject>> results = new HashMap<OWLProperty, Set<OWLObject>>();
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> opMap = getObjectPropertyValues(ontology);
        for (OWLObjectPropertyExpression prop : opMap.keySet()) {
            results.put((OWLProperty) prop, new HashSet<OWLObject>(opMap.get(prop)));
        }
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dpMap = getDataPropertyValues(ontology);
        for (OWLDataPropertyExpression prop : dpMap.keySet()) {
            results.put((OWLProperty) prop, new HashSet<OWLObject>(dpMap.get(prop)));
        }
        return results;
    }

    /**
     * Gets the asserted object property values for this individual and the specified property.
     * @param ontology The ontology to be examined for axioms that assert property values for this individual
     * @return The set of individuals that are the values of this property.  More precisely, the set of individuals
     *         such that for each individual i in the set, is in a property assertion axiom property(this, i) is in the specified ontology.
     */
    public Set<OWLIndividual> getObjectPropertyValues(OWLObjectPropertyExpression property, OWLOntology ontology) {
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> map = getObjectPropertyValues(ontology);
        Set<OWLIndividual> vals = map.get(property);
        if(vals == null) {
            return Collections.emptySet();
        }
        else {
            return new HashSet<OWLIndividual>(vals);
        }
    }

    public Map<OWLProperty, Set<OWLObject>> getNPropertyValues(OWLOntology ontology) {
        Map<OWLProperty, Set<OWLObject>> results = new HashMap<OWLProperty, Set<OWLObject>>();
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> opMap = getObjectPropertyValues(ontology);
        for (OWLObjectPropertyExpression prop : opMap.keySet()) {
            results.put((OWLProperty) prop, new HashSet<OWLObject>(opMap.get(prop)));
        }
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dpMap = getDataPropertyValues(ontology);
        for (OWLDataPropertyExpression prop : dpMap.keySet()) {
            results.put((OWLProperty) prop, new HashSet<OWLObject>(dpMap.get(prop)));
        }
        return results;
    }

    /**
     * Gets the values that this individual has for a specific data property
     * @param ontology The ontology to examine for property assertions
     * @return The values that this individual has for the specified property in the specified ontology.  This is
     *         the set of values such that each value LV in the set is in an axiom of the form
     *         DataPropertyAssertion(property, thisIndividual, LV) in the ontology specified by the ontology parameter.
     */
    public Set<OWLLiteral> getDataPropertyValues(OWLDataPropertyExpression property, OWLOntology ontology) {
        Set<OWLLiteral> result = new HashSet<OWLLiteral>();
        for(OWLDataPropertyAssertionAxiom ax : ontology.getDataPropertyAssertionAxioms(this)) {
            if(ax.getProperty().equals(property)) {
                result.add(ax.getObject());
            }
        }
        return result;
    }

    /**
     * Test whether a specific value for a specific object property on this individual has been asserted.
     * @param property   The property whose values will be examined
     * @param individual The individual value of the property that will be tested for
     * @param ontology   The ontology to search for the property value
     * @return <code>true</code> if the individual has the specified property value, that is, <code>true</code>
     *         if the specified ontology contains an object property assertion ObjectPropertyAssertion(property, this, individual),
     *         otherwise <code>false</code>
     */
    public boolean hasObjectPropertyValue(OWLObjectPropertyExpression property, OWLIndividual individual, OWLOntology ontology) {
        for(OWLObjectPropertyAssertionAxiom ax : ontology.getObjectPropertyAssertionAxioms(this)) {
            if(ax.getProperty().equals(property)) {
                if(ax.getObject().equals(individual)) {
                    return true;
                }
            }
        }
        return false;
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
        for (OWLNegativeObjectPropertyAssertionAxiom ax : ontology.getNegativeObjectPropertyAssertionAxioms(this)) {
            Set<OWLIndividual> inds = result.get(ax.getProperty());
            if (inds == null) {
                inds = new TreeSet<OWLIndividual>();
                result.put(ax.getProperty(), inds);
            }
            inds.add(ax.getObject());
        }
        return result;
    }

    /**
     * Test whether a specific value for a specific object property has been asserted not to hold for this individual.
     * @param property   The property to test for
     * @param individual The value to test for
     * @param ontology   The ontology to search for the assertion
     * @return <code>true</code> if the specified property value has explicitly been asserted not to hold, that is,
     *         <code>true</code> if the specified ontology contains a negative object property assertion
     *         NegativeObjectPropertyAssertion(property, this, individual), otherwise <code>false</code>
     */
    public boolean hasNegativeObjectPropertyValue(OWLObjectPropertyExpression property, OWLIndividual individual, OWLOntology ontology) {
        for(OWLNegativeObjectPropertyAssertionAxiom ax : ontology.getNegativeObjectPropertyAssertionAxioms(this)) {
            if(ax.getProperty().equals(property)) {
                if(ax.getObject().equals(individual)) {
                    return true;
                }
            }
        }
        return false;
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
        for (OWLNegativeDataPropertyAssertionAxiom ax : ontology.getNegativeDataPropertyAssertionAxioms(this)) {
            Set<OWLLiteral> inds = result.get(ax.getProperty());
            if (inds == null) {
                inds = new TreeSet<OWLLiteral>();
                result.put(ax.getProperty(), inds);
            }
            inds.add(ax.getObject());
        }
        return result;
    }

    /**
     * Test whether a specific value for a specific data property has been asserted not to hold for this individual.
     * @param property The property to test for
     * @param literal  The value to test for
     * @param ontology The ontology to search for the assertion
     * @return <code>true</code> if the specified property value has explicitly been asserted not to hold, that is,
     *         <code>true</code> if the specified ontology contains a negative data property assertion
     *         NegativeDataPropertyAssertion(property, this, literal), otherwise <code>false</code>
     */
    public boolean hasNegativeDataPropertyValue(OWLDataPropertyExpression property, OWLLiteral literal, OWLOntology ontology) {
        for(OWLNegativeDataPropertyAssertionAxiom ax : ontology.getNegativeDataPropertyAssertionAxioms(this)) {
            if(ax.getProperty().equals(property)) {
                if(ax.getObject().equals(literal)) {
                    return true;
                }
            }
        }
        return false;
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
        for (OWLSameIndividualAxiom ax : ontology.getSameIndividualAxioms(this)) {
            result.addAll(ax.getIndividuals());
        }
        result.remove(this);
        return result;
    }


    public Set<OWLIndividual> getDifferentIndividuals(OWLOntology ontology) {
        Set<OWLIndividual> result = new TreeSet<OWLIndividual>();
        for (OWLDifferentIndividualsAxiom ax : ontology.getDifferentIndividualAxioms(this)) {
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


    public OWLDatatype asOWLDatatype() {
        throw new OWLRuntimeException("Not a data type!");
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


    public boolean isOWLDatatype() {
        return false;
    }


    public boolean isOWLObjectProperty() {
        return false;
    }
}
