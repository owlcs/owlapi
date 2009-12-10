package org.semanticweb.owlapi.model;

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
 * Date: 26-Oct-2006<br><br>
 * </p>
 * An interface for objects that can accept visits from axioms.  (See the <a href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Patterns</a>)
 */
public interface OWLAxiomVisitor extends OWLAnnotationAxiomVisitor {

    void visit(OWLDeclarationAxiom axiom);


    void visit(OWLSubClassOfAxiom axiom);


    void visit(OWLNegativeObjectPropertyAssertionAxiom axiom);


    void visit(OWLAsymmetricObjectPropertyAxiom axiom);


    void visit(OWLReflexiveObjectPropertyAxiom axiom);


    void visit(OWLDisjointClassesAxiom axiom);


    void visit(OWLDataPropertyDomainAxiom axiom);


    void visit(OWLObjectPropertyDomainAxiom axiom);


    void visit(OWLEquivalentObjectPropertiesAxiom axiom);


    void visit(OWLNegativeDataPropertyAssertionAxiom axiom);


    void visit(OWLDifferentIndividualsAxiom axiom);


    void visit(OWLDisjointDataPropertiesAxiom axiom);


    void visit(OWLDisjointObjectPropertiesAxiom axiom);


    void visit(OWLObjectPropertyRangeAxiom axiom);


    void visit(OWLObjectPropertyAssertionAxiom axiom);


    void visit(OWLFunctionalObjectPropertyAxiom axiom);


    void visit(OWLSubObjectPropertyOfAxiom axiom);


    void visit(OWLDisjointUnionAxiom axiom);


    void visit(OWLSymmetricObjectPropertyAxiom axiom);


    void visit(OWLDataPropertyRangeAxiom axiom);


    void visit(OWLFunctionalDataPropertyAxiom axiom);


    void visit(OWLEquivalentDataPropertiesAxiom axiom);


    void visit(OWLClassAssertionAxiom axiom);


    void visit(OWLEquivalentClassesAxiom axiom);


    void visit(OWLDataPropertyAssertionAxiom axiom);


    void visit(OWLTransitiveObjectPropertyAxiom axiom);


    void visit(OWLIrreflexiveObjectPropertyAxiom axiom);


    void visit(OWLSubDataPropertyOfAxiom axiom);


    void visit(OWLInverseFunctionalObjectPropertyAxiom axiom);


    void visit(OWLSameIndividualAxiom axiom);


    void visit(OWLSubPropertyChainOfAxiom axiom);


    void visit(OWLInverseObjectPropertiesAxiom axiom);


    void visit(OWLHasKeyAxiom axiom);


    void visit(OWLDatatypeDefinitionAxiom axiom);

    
    void visit(SWRLRule rule);




}
