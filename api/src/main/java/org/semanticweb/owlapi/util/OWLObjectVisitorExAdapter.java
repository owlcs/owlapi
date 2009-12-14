package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;
/*
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 29-Jul-2008<br><br>
 *
 * Provides a default implementation of <code>OWLObjectVisitorEx</code>.  Only the methods that need specific client
 * implementation need be overridden.  The adapter can be set up to return a default value.
 */
public class OWLObjectVisitorExAdapter<O> implements OWLObjectVisitorEx<O> {

    private O defaultReturnValue = null;

    /**
     * Gets the default return value for this visitor.  By default, the default is <code>null</code>, but a fixed value
     * (independent of the specified <code>OWLObject</code> <code>object</code>) can be specified in the constructor
     * {@link org.semanticweb.owlapi.model.OWLObjectVisitorEx#()}
     * @param object The object that was visited.
     * @return The default return value
     */
    protected O getDefaultReturnValue(OWLObject object) {
        return defaultReturnValue;
    }

    public OWLObjectVisitorExAdapter() {
        this(null);
    }

    public OWLObjectVisitorExAdapter(O defaultReturnValue) {
        this.defaultReturnValue = defaultReturnValue;
    }

    public O visit(OWLAnnotationAssertionAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLClassAssertionAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLDataPropertyAssertionAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLDataPropertyDomainAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLDataPropertyRangeAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLDeclarationAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLDifferentIndividualsAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLDisjointClassesAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLDisjointDataPropertiesAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLDisjointUnionAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLEquivalentClassesAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLFunctionalDataPropertyAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLHasKeyAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLInverseObjectPropertiesAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLObjectPropertyAssertionAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLSubPropertyChainOfAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLObjectPropertyDomainAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLObjectPropertyRangeAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLSameIndividualAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLSubClassOfAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLSubDataPropertyOfAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLSubObjectPropertyOfAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(SWRLRule rule) {
        return getDefaultReturnValue(rule);
    }

    public O visit(OWLClass desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLDataAllValuesFrom desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLDataExactCardinality desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLDataMaxCardinality desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLDataMinCardinality desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLDataSomeValuesFrom desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLDataHasValue desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLObjectAllValuesFrom desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLObjectComplementOf desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLObjectExactCardinality desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLObjectHasSelf desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLObjectHasValue desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLObjectIntersectionOf desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLObjectMaxCardinality desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLObjectMinCardinality desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLObjectOneOf desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLObjectSomeValuesFrom desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLObjectUnionOf desc) {
        return getDefaultReturnValue(desc);
    }

    public O visit(OWLDataComplementOf node) {
        return getDefaultReturnValue(node);
    }

    public O visit(OWLDataIntersectionOf node) {
        return getDefaultReturnValue(node);
    }

    public O visit(OWLDataOneOf node) {
        return getDefaultReturnValue(node);
    }

    public O visit(OWLDatatype node) {
        return getDefaultReturnValue(node);
    }

    public O visit(OWLDatatypeRestriction node) {
        return getDefaultReturnValue(node);
    }

    public O visit(OWLDataUnionOf node) {
        return getDefaultReturnValue(node);
    }

    public O visit(OWLFacetRestriction node) {
        return getDefaultReturnValue(node);
    }

    public O visit(OWLStringLiteral node) {
        return getDefaultReturnValue(node);
    }

    public O visit(OWLTypedLiteral node) {
        return getDefaultReturnValue(node);
    }

    public O visit(OWLDataProperty property) {
        return getDefaultReturnValue(property);
    }

    public O visit(OWLObjectProperty property) {
        return getDefaultReturnValue(property);
    }

    public O visit(OWLObjectInverseOf property) {
        return getDefaultReturnValue(property);
    }

    public O visit(OWLNamedIndividual individual) {
        return getDefaultReturnValue(individual);
    }

    public O visit(OWLAnnotationProperty property) {
        return getDefaultReturnValue(property);
    }

    public O visit(OWLAnnotation annotation) {
        return getDefaultReturnValue(annotation);
    }

    public O visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }

    public O visit(OWLAnonymousIndividual individual) {
        return getDefaultReturnValue(individual);
    }

    public O visit(IRI iri) {
        return getDefaultReturnValue(iri);
    }

    public O visit(OWLLiteral literal) {
        return getDefaultReturnValue(literal);
    }

    public O visit(SWRLLiteralArgument node) {
        return getDefaultReturnValue(node);
    }

    public O visit(SWRLVariable node) {
        return getDefaultReturnValue(node);
    }

    public O visit(SWRLIndividualArgument node) {
        return getDefaultReturnValue(node);
    }

    public O visit(SWRLBuiltInAtom node) {
        return getDefaultReturnValue(node);
    }

    public O visit(SWRLClassAtom node) {
        return getDefaultReturnValue(node);
    }

    public O visit(SWRLDataRangeAtom node) {
        return getDefaultReturnValue(node);
    }

    public O visit(SWRLDataPropertyAtom node) {
        return getDefaultReturnValue(node);
    }

    public O visit(SWRLDifferentIndividualsAtom node) {
        return getDefaultReturnValue(node);
    }

    public O visit(SWRLObjectPropertyAtom node) {
        return getDefaultReturnValue(node);
    }

    public O visit(SWRLSameIndividualAtom node) {
        return getDefaultReturnValue(node);
    }

    public O visit(OWLOntology ontology) {
        return getDefaultReturnValue(ontology);
    }


    public O visit(OWLDatatypeDefinitionAxiom axiom) {
        return getDefaultReturnValue(axiom);
    }
}
