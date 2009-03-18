package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;

import java.util.Set;
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
public class OWLOntologyWalker extends OWLObjectWalker<OWLOntology> {

    private OWLAxiom currentAxiom;

    public OWLOntologyWalker(Set<OWLOntology> objects) {
        super(objects);
    }

    public <E> void walkStructure(OWLObjectVisitorEx<E> visitor) {
        super.walkStructure(new DelegatingObjectVisitorEx<E>(visitor) {
            public E visit(OWLAsymmetricObjectPropertyAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }

            public E visit(OWLClassAssertionAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLDataPropertyAssertionAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLDataPropertyDomainAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLDataPropertyRangeAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLSubDataPropertyOfAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLDeclarationAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLDifferentIndividualsAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLDisjointClassesAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLDisjointDataPropertiesAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLDisjointObjectPropertiesAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLDisjointUnionAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLAnnotationAssertionAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLEquivalentClassesAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLEquivalentDataPropertiesAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLEquivalentObjectPropertiesAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLFunctionalDataPropertyAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLFunctionalObjectPropertyAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLImportsDeclaration axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLInverseObjectPropertiesAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLObjectPropertyAssertionAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLSubPropertyChainOfAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLObjectPropertyDomainAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLObjectPropertyRangeAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLSubObjectPropertyOfAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLReflexiveObjectPropertyAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLSameIndividualsAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLSubClassOfAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLSymmetricObjectPropertyAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(OWLTransitiveObjectPropertyAxiom axiom) {
                currentAxiom = axiom;
                return super.visit(axiom);
            }


            public E visit(SWRLRule rule) {
                currentAxiom = rule;
                return super.visit(rule);
            }
        });
    }


    public OWLAxiom getCurrentAxiom() {
        return currentAxiom;
    }

}
