package org.semanticweb.owlapi.model;
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
 * Date: 16-Apr-2008<br><br>
 * </p>
 * An interface for objects that can accept visits from axioms.  (See the <a href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Patterns</a>)
 */
public interface OWLAxiomVisitorEx<O> extends OWLAnnotationAxiomVisitorEx<O> {


    O visit(OWLSubClassOfAxiom axiom);


    O visit(OWLNegativeObjectPropertyAssertionAxiom axiom);


    O visit(OWLAsymmetricObjectPropertyAxiom axiom);


    O visit(OWLReflexiveObjectPropertyAxiom axiom);


    O visit(OWLDisjointClassesAxiom axiom);


    O visit(OWLDataPropertyDomainAxiom axiom);


    O visit(OWLObjectPropertyDomainAxiom axiom);


    O visit(OWLEquivalentObjectPropertiesAxiom axiom);


    O visit(OWLNegativeDataPropertyAssertionAxiom axiom);


    O visit(OWLDifferentIndividualsAxiom axiom);


    O visit(OWLDisjointDataPropertiesAxiom axiom);


    O visit(OWLDisjointObjectPropertiesAxiom axiom);


    O visit(OWLObjectPropertyRangeAxiom axiom);


    O visit(OWLObjectPropertyAssertionAxiom axiom);


    O visit(OWLFunctionalObjectPropertyAxiom axiom);


    O visit(OWLSubObjectPropertyOfAxiom axiom);


    O visit(OWLDisjointUnionAxiom axiom);


    O visit(OWLDeclarationAxiom axiom);


    O visit(OWLAnnotationAssertionAxiom axiom);


    O visit(OWLSymmetricObjectPropertyAxiom axiom);


    O visit(OWLDataPropertyRangeAxiom axiom);


    O visit(OWLFunctionalDataPropertyAxiom axiom);


    O visit(OWLEquivalentDataPropertiesAxiom axiom);


    O visit(OWLClassAssertionAxiom axiom);


    O visit(OWLEquivalentClassesAxiom axiom);


    O visit(OWLDataPropertyAssertionAxiom axiom);


    O visit(OWLTransitiveObjectPropertyAxiom axiom);


    O visit(OWLIrreflexiveObjectPropertyAxiom axiom);


    O visit(OWLSubDataPropertyOfAxiom axiom);


    O visit(OWLInverseFunctionalObjectPropertyAxiom axiom);


    O visit(OWLSameIndividualAxiom axiom);


    O visit(OWLSubPropertyChainOfAxiom axiom);


    O visit(OWLInverseObjectPropertiesAxiom axiom);


    O visit(OWLHasKeyAxiom axiom);


    O visit(OWLDatatypeDefinitionAxiom axiom);


    O visit(SWRLRule rule);
}
