package org.semanticweb.owl.debugging;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.CollectionFactory;

import java.net.URI;
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
public class DebuggerDescriptionGenerator implements OWLAxiomVisitor {

    private OWLDataFactory dataFactory;

    private OWLClassExpression desc;


    public DebuggerDescriptionGenerator(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }


    public OWLClassExpression getDebuggerDescription() {
        return desc;
    }


    public void visit(OWLSubClassOfAxiom axiom) {
        // A and not (B)
        OWLClassExpression complement = dataFactory.getObjectComplementOf(axiom.getSuperClass());
        desc = dataFactory.getObjectIntersectionOf(CollectionFactory.createSet(axiom.getSubClass(), complement));
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
        OWLClassExpression sub = dataFactory.getDataSomeValuesFrom(axiom.getProperty(), dataFactory.getTopDataType());
        OWLAxiom ax = dataFactory.getSubClassOf(sub, axiom.getDomain());
        ax.accept(this);
    }


    public void visit(OWLImportsDeclaration axiom) {
        // Nothing to do
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        // prop some Thing subclassOf domain
        OWLClassExpression sub = dataFactory.getObjectSomeValuesFrom(axiom.getProperty(), dataFactory.getOWLThing());
        OWLSubClassOfAxiom ax = dataFactory.getSubClassOf(sub, axiom.getDomain());
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
        OWLClassExpression sup = dataFactory.getObjectAllValuesFrom(axiom.getProperty(), axiom.getRange());
        OWLSubClassOfAxiom ax = dataFactory.getSubClassOf(dataFactory.getOWLThing(), sup);
        ax.accept(this);
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        //  subProp some {a} subClassOf supProp some {a}
        OWLIndividual ind = dataFactory.getOWLIndividual(URI.create("http://debugger.com#" + System.nanoTime()));
        OWLClassExpression sub = dataFactory.getObjectHasValue(axiom.getSubProperty(), ind);
        OWLClassExpression sup = dataFactory.getObjectHasValue(axiom.getSuperProperty(), ind);
        OWLAxiom ax = dataFactory.getSubClassOf(sub, sup);
        ax.accept(this);
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
    }


    public void visit(OWLDeclaration axiom) {
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
        OWLClassExpression sub = dataFactory.getObjectOneOf(Collections.singleton(axiom.getIndividual()));
        OWLAxiom ax = dataFactory.getSubClassOf(sub, axiom.getDescription());
        ax.accept(this);
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        if (axiom.getDescriptions().size() == 2 && axiom.getDescriptions().contains(dataFactory.getOWLNothing())) {
            for (OWLClassExpression desc : axiom.getDescriptions()) {
                if (!desc.isOWLNothing()) {
                    this.desc = desc;
                    return;
                }
            }
        }
        // (C and not D) or (not C and D)
        Set<OWLClassExpression> clses = axiom.getDescriptions();
        Iterator<OWLClassExpression> it = clses.iterator();
        OWLClassExpression descC = it.next();
        OWLClassExpression notC = dataFactory.getObjectComplementOf(descC);
        OWLClassExpression descD = it.next();
        OWLClassExpression notD = dataFactory.getObjectComplementOf(descD);

        OWLObjectIntersectionOf left = dataFactory.getObjectIntersectionOf(CollectionFactory.createSet(descC, notD));
        OWLObjectIntersectionOf right = dataFactory.getObjectIntersectionOf(CollectionFactory.createSet(notC,
                descD));
        desc = dataFactory.getObjectUnionOf(CollectionFactory.createSet(left, right));
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


    public void visit(OWLSameIndividualsAxiom axiom) {

    }


    public void visit(OWLComplextSubPropertyAxiom axiom) {
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
    }


    public void visit(SWRLRule rule) {
    }

    public void visit(OWLHasKeyAxiom axiom) {
    }

    public void visit(OWLAnnotationPropertyDomain axiom) {
    }

    public void visit(OWLAnnotationPropertyRange axiom) {
    }

    public void visit(OWLSubAnnotationPropertyOf axiom) {
    }
}
