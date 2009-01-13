package org.semanticweb.owl.model;

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
 */
public interface OWLAxiomVisitor {

    public void visit(OWLSubClassAxiom axiom);


    void visit(OWLNegativeObjectPropertyAssertionAxiom axiom);


    void visit(OWLAntiSymmetricObjectPropertyAxiom axiom);


    void visit(OWLReflexiveObjectPropertyAxiom axiom);


    void visit(OWLDisjointClassesAxiom axiom);


    void visit(OWLDataPropertyDomainAxiom axiom);


    void visit(OWLImportsDeclaration axiom);


    void visit(OWLAxiomAnnotationAxiom axiom);


    void visit(OWLObjectPropertyDomainAxiom axiom);


    void visit(OWLEquivalentObjectPropertiesAxiom axiom);


    void visit(OWLNegativeDataPropertyAssertionAxiom axiom);


    void visit(OWLDifferentIndividualsAxiom axiom);


    void visit(OWLDisjointDataPropertiesAxiom axiom);


    void visit(OWLDisjointObjectPropertiesAxiom axiom);


    void visit(OWLObjectPropertyRangeAxiom axiom);


    void visit(OWLObjectPropertyAssertionAxiom axiom);


    void visit(OWLFunctionalObjectPropertyAxiom axiom);


    void visit(OWLObjectSubPropertyAxiom axiom);


    void visit(OWLDisjointUnionAxiom axiom);


    void visit(OWLDeclarationAxiom axiom);


    void visit(OWLEntityAnnotationAxiom axiom);

    
    void visit(OWLOntologyAnnotationAxiom axiom);


    void visit(OWLSymmetricObjectPropertyAxiom axiom);


    void visit(OWLDataPropertyRangeAxiom axiom);


    void visit(OWLFunctionalDataPropertyAxiom axiom);


    void visit(OWLEquivalentDataPropertiesAxiom axiom);


    void visit(OWLClassAssertionAxiom axiom);


    void visit(OWLEquivalentClassesAxiom axiom);


    void visit(OWLDataPropertyAssertionAxiom axiom);


    void visit(OWLTransitiveObjectPropertyAxiom axiom);


    void visit(OWLIrreflexiveObjectPropertyAxiom axiom);


    void visit(OWLDataSubPropertyAxiom axiom);


    void visit(OWLInverseFunctionalObjectPropertyAxiom axiom);


    void visit(OWLSameIndividualsAxiom axiom);


    void visit(OWLObjectPropertyChainSubPropertyAxiom axiom);

    
    void visit(OWLInverseObjectPropertiesAxiom axiom);

    
    void visit(SWRLRule rule);
}
