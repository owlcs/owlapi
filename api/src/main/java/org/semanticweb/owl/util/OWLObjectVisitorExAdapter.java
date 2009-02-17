package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;
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
 */
public class OWLObjectVisitorExAdapter<O> implements OWLObjectVisitorEx<O> {

    private O defaultReturnValue = null;

    public OWLObjectVisitorExAdapter() {
    }

    public OWLObjectVisitorExAdapter(O defaultReturnValue) {
        this.defaultReturnValue = defaultReturnValue;
    }

    public O visit(OWLAnnotationAssertionAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLClassAssertionAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLDataPropertyAssertionAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLDataPropertyDomainAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLDataPropertyRangeAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLDeclaration axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLDifferentIndividualsAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLDisjointClassesAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLDisjointDataPropertiesAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLDisjointUnionAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLEquivalentClassesAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLFunctionalDataPropertyAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLHasKeyAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLImportsDeclaration axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLInverseObjectPropertiesAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectPropertyAssertionAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLSubPropertyChainOfAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectPropertyDomainAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectPropertyRangeAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLSameIndividualsAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLSubClassOfAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLSubDataPropertyOfAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLSubObjectPropertyOfAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return defaultReturnValue;
    }

    public O visit(SWRLRule rule) {
        return defaultReturnValue;
    }

    public O visit(OWLClass desc) {
        return defaultReturnValue;
    }

    public O visit(OWLDataAllValuesFrom desc) {
        return defaultReturnValue;
    }

    public O visit(OWLDataExactCardinality desc) {
        return defaultReturnValue;
    }

    public O visit(OWLDataMaxCardinality desc) {
        return defaultReturnValue;
    }

    public O visit(OWLDataMinCardinality desc) {
        return defaultReturnValue;
    }

    public O visit(OWLDataSomeValuesFrom desc) {
        return defaultReturnValue;
    }

    public O visit(OWLDataHasValue desc) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectAllValuesFrom desc) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectComplementOf desc) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectExactCardinality desc) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectHasSelf desc) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectHasValue desc) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectIntersectionOf desc) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectMaxCardinality desc) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectMinCardinality desc) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectOneOf desc) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectSomeValuesFrom desc) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectUnionOf desc) {
        return defaultReturnValue;
    }

    public O visit(OWLDataComplementOf node) {
        return defaultReturnValue;
    }

    public O visit(OWLDataIntersectionOf node) {
        return defaultReturnValue;
    }

    public O visit(OWLDataOneOf node) {
        return defaultReturnValue;
    }

    public O visit(OWLDatatype node) {
        return defaultReturnValue;
    }

    public O visit(OWLDatatypeRestriction node) {
        return defaultReturnValue;
    }

    public O visit(OWLDataUnionOf node) {
        return defaultReturnValue;
    }

    public O visit(OWLFacetRestriction node) {
        return defaultReturnValue;
    }

    public O visit(OWLRDFTextLiteral node) {
        return defaultReturnValue;
    }

    public O visit(OWLTypedLiteral node) {
        return defaultReturnValue;
    }

    public O visit(OWLDataProperty property) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectProperty property) {
        return defaultReturnValue;
    }

    public O visit(OWLObjectPropertyInverse property) {
        return defaultReturnValue;
    }

    public O visit(OWLNamedIndividual individual) {
        return defaultReturnValue;
    }

    public O visit(OWLAnnotationProperty property) {
        return defaultReturnValue;
    }

    public O visit(OWLAnnotation annotation) {
        return defaultReturnValue;
    }

    public O visit(OWLAnnotationPropertyDomain axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLAnnotationPropertyRange axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLSubAnnotationPropertyOf axiom) {
        return defaultReturnValue;
    }

    public O visit(OWLAnonymousIndividual individual) {
        return defaultReturnValue;
    }

    public O visit(IRI iri) {
        return defaultReturnValue;
    }

    public O visit(OWLLiteral literal) {
        return defaultReturnValue;
    }

    public O visit(SWRLAtomConstantObject node) {
        return defaultReturnValue;
    }

    public O visit(SWRLAtomDVariable node) {
        return defaultReturnValue;
    }

    public O visit(SWRLAtomIndividualObject node) {
        return defaultReturnValue;
    }

    public O visit(SWRLAtomIVariable node) {
        return defaultReturnValue;
    }

    public O visit(SWRLBuiltInAtom node) {
        return defaultReturnValue;
    }

    public O visit(SWRLClassAtom node) {
        return defaultReturnValue;
    }

    public O visit(SWRLDataRangeAtom node) {
        return defaultReturnValue;
    }

    public O visit(SWRLDataValuedPropertyAtom node) {
        return defaultReturnValue;
    }

    public O visit(SWRLDifferentFromAtom node) {
        return defaultReturnValue;
    }

    public O visit(SWRLObjectPropertyAtom node) {
        return defaultReturnValue;
    }

    public O visit(SWRLSameAsAtom node) {
        return defaultReturnValue;
    }

    public O visit(OWLOntology ontology) {
        return defaultReturnValue;
    }
}
