package org.semanticweb.owlapi.debugging;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 01-Mar-2007<br><br>
 */
public class DebuggerClassExpressionGenerator implements OWLAxiomVisitor {

    private OWLDataFactory dataFactory;

    private OWLClassExpression desc;


    public DebuggerClassExpressionGenerator(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }


    public OWLClassExpression getDebuggerClassExpression() {
        return desc;
    }


    public void visit(OWLSubClassOfAxiom axiom) {
        // A and not (B)
        OWLClassExpression complement = dataFactory.getOWLObjectComplementOf(axiom.getSuperClass());
        desc = dataFactory.getOWLObjectIntersectionOf(CollectionFactory.createSet(axiom.getSubClass(), complement));
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {

    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        OWLClassExpression sub = dataFactory.getOWLDataSomeValuesFrom(axiom.getProperty(), dataFactory.getTopDatatype());
        OWLAxiom ax = dataFactory.getOWLSubClassOfAxiom(sub, axiom.getDomain());
        ax.accept(this);
    }


    public void visit(OWLImportsDeclaration axiom) {
        // Nothing to do
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        // prop some Thing subclassOf domain
        OWLClassExpression sub = dataFactory.getOWLObjectSomeValuesFrom(axiom.getProperty(), dataFactory.getOWLThing());
        OWLSubClassOfAxiom ax = dataFactory.getOWLSubClassOfAxiom(sub, axiom.getDomain());
        ax.accept(this);
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {

    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        // Thing subclassOf prop only Range
        OWLClassExpression sup = dataFactory.getOWLObjectAllValuesFrom(axiom.getProperty(), axiom.getRange());
        OWLSubClassOfAxiom ax = dataFactory.getOWLSubClassOfAxiom(dataFactory.getOWLThing(), sup);
        ax.accept(this);
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        //  subProp some {a} subClassOf supProp some {a}
        OWLIndividual ind = dataFactory.getOWLNamedIndividual(IRI.create("http://debugger.com#" + System.nanoTime()));
        OWLClassExpression sub = dataFactory.getOWLObjectHasValue(axiom.getSubProperty(), ind);
        OWLClassExpression sup = dataFactory.getOWLObjectHasValue(axiom.getSuperProperty(), ind);
        OWLAxiom ax = dataFactory.getOWLSubClassOfAxiom(sub, sup);
        ax.accept(this);
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
    }


    public void visit(OWLDeclarationAxiom axiom) {
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        OWLClassExpression sub = dataFactory.getOWLObjectOneOf(Collections.singleton(axiom.getIndividual()));
        OWLAxiom ax = dataFactory.getOWLSubClassOfAxiom(sub, axiom.getClassExpression());
        ax.accept(this);
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        if (axiom.getClassExpressions().size() == 2 && axiom.getClassExpressions().contains(dataFactory.getOWLNothing())) {
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isOWLNothing()) {
                    this.desc = desc;
                    return;
                }
            }
        }
        // (C and not D) or (not C and D)
        Set<OWLClassExpression> clses = axiom.getClassExpressions();
        Iterator<OWLClassExpression> it = clses.iterator();
        OWLClassExpression descC = it.next();
        OWLClassExpression notC = dataFactory.getOWLObjectComplementOf(descC);
        OWLClassExpression descD = it.next();
        OWLClassExpression notD = dataFactory.getOWLObjectComplementOf(descD);

        OWLObjectIntersectionOf left = dataFactory.getOWLObjectIntersectionOf(CollectionFactory.createSet(descC, notD));
        OWLObjectIntersectionOf right = dataFactory.getOWLObjectIntersectionOf(CollectionFactory.createSet(notC,
                descD));
        desc = dataFactory.getOWLObjectUnionOf(CollectionFactory.createSet(left, right));
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
    }


    public void visit(OWLSameIndividualAxiom axiom) {

    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
    }


    public void visit(SWRLRule rule) {
    }

    public void visit(OWLHasKeyAxiom axiom) {
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
    }
}
