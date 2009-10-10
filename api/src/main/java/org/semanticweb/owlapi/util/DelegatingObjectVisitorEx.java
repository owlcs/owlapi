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


    public O visit(OWLAsymmetricObjectPropertyAxiom axiom) {
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


    public O visit(OWLSubDataPropertyOfAxiom axiom) {
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


    public O visit(OWLAnnotationAssertionAxiom axiom) {
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


    public O visit(OWLSubPropertyChainOfAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLObjectPropertyDomainAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLObjectPropertyRangeAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLSubObjectPropertyOfAxiom axiom) {
        return delegate.visit(axiom);
    }

    public O visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLSameIndividualAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLSubClassOfAxiom axiom) {
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


    public O visit(OWLDataExactCardinality desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataMaxCardinality desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataMinCardinality desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataSomeValuesFrom desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataHasValue desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectAllValuesFrom desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectComplementOf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectExactCardinality desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectIntersectionOf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectMaxCardinality desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectMinCardinality desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectOneOf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectHasSelf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectSomeValuesFrom desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectUnionOf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectHasValue desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataComplementOf node) {
        return delegate.visit(node);
    }


    public O visit(OWLDataOneOf node) {
        return delegate.visit(node);
    }


    public O visit(OWLFacetRestriction node) {
        return delegate.visit(node);
    }


    public O visit(OWLDatatypeRestriction node) {
        return delegate.visit(node);
    }


    public O visit(OWLDatatype node) {
        return delegate.visit(node);
    }


    public O visit(OWLTypedLiteral node) {
        return delegate.visit(node);
    }


    public O visit(OWLStringLiteral node) {
        return delegate.visit(node);
    }


    public O visit(OWLDataProperty property) {
        return delegate.visit(property);
    }


    public O visit(OWLObjectProperty property) {
        return delegate.visit(property);
    }


    public O visit(OWLObjectInverseOf property) {
        return delegate.visit(property);
    }


    public O visit(SWRLLiteralArgument node) {
        return delegate.visit(node);
    }


    public O visit(SWRLVariable node) {
        return delegate.visit(node);
    }


    public O visit(SWRLIndividualArgument node) {
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


    public O visit(SWRLDataPropertyAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLDifferentIndividualsAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLObjectPropertyAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLSameIndividualAtom node) {
        return delegate.visit(node);
    }


    public O visit(OWLOntology ontology) {
        return delegate.visit(ontology);
    }

    public O visit(OWLAnnotation annotation) {
        return delegate.visit(annotation);
    }

    public O visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return delegate.visit(axiom);
    }

    public O visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return delegate.visit(axiom);
    }

    public O visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return delegate.visit(axiom);
    }

    public O visit(OWLAnnotationProperty property) {
        return delegate.visit(property);
    }

    public O visit(OWLHasKeyAxiom axiom) {
        return delegate.visit(axiom);
    }

    public O visit(OWLDataIntersectionOf node) {
        return delegate.visit(node);
    }

    public O visit(OWLDataUnionOf node) {
        return delegate.visit(node);
    }

    public O visit(OWLNamedIndividual individual) {
        return delegate.visit(individual);
    }

    public O visit(OWLAnonymousIndividual individual) {
        return delegate.visit(individual);
    }

    public O visit(IRI iri) {
        return delegate.visit(iri);
    }


    public O visit(OWLDatatypeDefinitionAxiom axiom) {
        return delegate.visit(axiom);
    }
}
