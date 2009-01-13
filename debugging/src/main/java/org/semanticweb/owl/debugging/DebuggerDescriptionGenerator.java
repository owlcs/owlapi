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

    private OWLDescription desc;


    public DebuggerDescriptionGenerator(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }


    public OWLDescription getDebuggerDescription() {
        return desc;
    }


    public void visit(OWLSubClassAxiom axiom) {
        // A and not (B)
        OWLDescription complement = dataFactory.getOWLObjectComplementOf(axiom.getSuperClass());
        desc = dataFactory.getOWLObjectIntersectionOf(CollectionFactory.createSet(axiom.getSubClass(), complement));
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
    }


    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {

    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        OWLDescription sub = dataFactory.getOWLDataSomeRestriction(axiom.getProperty(), dataFactory.getTopDataType());
        OWLAxiom ax = dataFactory.getOWLSubClassAxiom(sub, axiom.getDomain());
        ax.accept(this);
    }


    public void visit(OWLImportsDeclaration axiom) {
        // Nothing to do
    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        // prop some Thing subclassOf domain
        OWLDescription sub = dataFactory.getOWLObjectSomeRestriction(axiom.getProperty(), dataFactory.getOWLThing());
        OWLSubClassAxiom ax = dataFactory.getOWLSubClassAxiom(sub, axiom.getDomain());
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
        OWLDescription sup = dataFactory.getOWLObjectAllRestriction(axiom.getProperty(), axiom.getRange());
        OWLSubClassAxiom ax = dataFactory.getOWLSubClassAxiom(dataFactory.getOWLThing(), sup);
        ax.accept(this);
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
    }


    public void visit(OWLObjectSubPropertyAxiom axiom) {
        //  subProp some {a} subClassOf supProp some {a}
        OWLIndividual ind = dataFactory.getOWLIndividual(URI.create("http://debugger.com#" + System.nanoTime()));
        OWLDescription sub = dataFactory.getOWLObjectValueRestriction(axiom.getSubProperty(), ind);
        OWLDescription sup = dataFactory.getOWLObjectValueRestriction(axiom.getSuperProperty(), ind);
        OWLAxiom ax = dataFactory.getOWLSubClassAxiom(sub, sup);
        ax.accept(this);
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
    }


    public void visit(OWLDeclarationAxiom axiom) {
    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
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
        OWLDescription sub = dataFactory.getOWLObjectOneOf(Collections.singleton(axiom.getIndividual()));
        OWLAxiom ax = dataFactory.getOWLSubClassAxiom(sub, axiom.getDescription());
        ax.accept(this);
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        if (axiom.getDescriptions().size() == 2 && axiom.getDescriptions().contains(dataFactory.getOWLNothing())) {
            for (OWLDescription desc : axiom.getDescriptions()) {
                if (!desc.isOWLNothing()) {
                    this.desc = desc;
                    return;
                }
            }
        }
        // (C and not D) or (not C and D)
        Set<OWLDescription> clses = axiom.getDescriptions();
        Iterator<OWLDescription> it = clses.iterator();
        OWLDescription descC = it.next();
        OWLDescription notC = dataFactory.getOWLObjectComplementOf(descC);
        OWLDescription descD = it.next();
        OWLDescription notD = dataFactory.getOWLObjectComplementOf(descD);

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


    public void visit(OWLDataSubPropertyAxiom axiom) {
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
    }


    public void visit(OWLSameIndividualsAxiom axiom) {

    }


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
    }


    public void visit(SWRLRule rule) {
    }
}
