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
 * <p/>
 * A delegating visitor.  This utility class can be used to override visiting
 * a particular type of object.
 */
public class DelegatingObjectVisitorEx<O> implements OWLObjectVisitorEx<O> {

    private OWLObjectVisitorEx<O> delegate;


    /**
     * Constructs a visitor where the specified delegate will be used to
     * visit all objects unless one of the visit methods in this visitor
     * is overriden.
     *
     * @param delegate The delegate.
     */
    public DelegatingObjectVisitorEx(OWLObjectVisitorEx<O> delegate) {
        this.delegate = delegate;
    }


    public O visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLAxiomAnnotationAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLClassAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDataPropertyAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDataPropertyDomainAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDataPropertyRangeAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDataSubPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDeclarationAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDifferentIndividualsAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDisjointClassesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDisjointDataPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDisjointUnionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLEntityAnnotationAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLEquivalentClassesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLFunctionalDataPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLImportsDeclaration axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLInverseObjectPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLObjectPropertyAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLObjectPropertyDomainAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLObjectPropertyRangeAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLObjectSubPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLOntologyAnnotationAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLSameIndividualsAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLSubClassAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(SWRLRule rule) {
        return delegate.visit(rule);
    }


    public O visit(OWLClass desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataAllValuesFrom desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataExactCardinalityRestriction desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataMaxCardinalityRestriction desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataMinCardinalityRestriction desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataSomeValuesFrom desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataValueRestriction desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectAllValuesFrom desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectComplementOf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectExactCardinalityRestriction desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectIntersectionOf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectMaxCardinalityRestriction desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectMinCardinalityRestriction desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectOneOf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectSelfRestriction desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectSomeValuesFrom desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectUnionOf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectValueRestriction desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataComplementOf node) {
        return delegate.visit(node);
    }


    public O visit(OWLDataOneOf node) {
        return delegate.visit(node);
    }


    public O visit(OWLDataRangeFacetRestriction node) {
        return delegate.visit(node);
    }


    public O visit(OWLDataRangeRestriction node) {
        return delegate.visit(node);
    }


    public O visit(OWLDatatype node) {
        return delegate.visit(node);
    }


    public O visit(OWLTypedLiteral node) {
        return delegate.visit(node);
    }


    public O visit(OWLRDFTextLiteral node) {
        return delegate.visit(node);
    }


    public O visit(OWLDataProperty property) {
        return delegate.visit(property);
    }


    public O visit(OWLObjectProperty property) {
        return delegate.visit(property);
    }


    public O visit(OWLObjectPropertyInverse property) {
        return delegate.visit(property);
    }


    public O visit(OWLIndividual individual) {
        return delegate.visit(individual);
    }


    public O visit(OWLConstantAnnotation annotation) {
        return delegate.visit(annotation);
    }


    public O visit(OWLObjectAnnotation annotation) {
        return delegate.visit(annotation);
    }


    public O visit(SWRLAtomConstantObject node) {
        return delegate.visit(node);
    }


    public O visit(SWRLAtomDVariable node) {
        return delegate.visit(node);
    }


    public O visit(SWRLAtomIndividualObject node) {
        return delegate.visit(node);
    }


    public O visit(SWRLAtomIVariable node) {
        return delegate.visit(node);
    }


    public O visit(SWRLBuiltInAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLClassAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLDataRangeAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLDataValuedPropertyAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLDifferentFromAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLObjectPropertyAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLSameAsAtom node) {
        return delegate.visit(node);
    }


    public O visit(OWLOntology ontology) {
        return delegate.visit(ontology);
    }
}
