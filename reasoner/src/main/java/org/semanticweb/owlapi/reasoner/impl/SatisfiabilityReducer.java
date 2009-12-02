package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.model.*;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Aug-2009
 */
public class SatisfiabilityReducer implements OWLAxiomVisitorEx<OWLClassExpression> {

    private OWLDataFactory df;


    public SatisfiabilityReducer(OWLDataFactory dataFactory) {
        this.df = dataFactory;
    }

    public OWLClassExpression visit(OWLSubClassOfAxiom axiom) {
        return df.getOWLObjectIntersectionOf(axiom.getSubClass(), df.getOWLObjectComplementOf(axiom.getSuperClass()));
    }

    public OWLClassExpression visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLDisjointClassesAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLDataPropertyDomainAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLObjectPropertyDomainAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLDifferentIndividualsAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLDisjointDataPropertiesAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLObjectPropertyRangeAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLObjectPropertyAssertionAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLSubObjectPropertyOfAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLDisjointUnionAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLDeclarationAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLDataPropertyRangeAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLFunctionalDataPropertyAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLClassAssertionAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLEquivalentClassesAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLDataPropertyAssertionAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLSubDataPropertyOfAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    public OWLClassExpression visit(OWLSameIndividualAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLSubPropertyChainOfAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLInverseObjectPropertiesAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLHasKeyAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLDatatypeDefinitionAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(SWRLRule rule) {
        return null;
    }


    public OWLClassExpression visit(OWLAnnotationAssertionAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return null;
    }

    public OWLClassExpression visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return null;
    }
}
