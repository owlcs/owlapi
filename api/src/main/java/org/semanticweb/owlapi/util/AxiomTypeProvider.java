package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;
import static org.semanticweb.owlapi.model.AxiomType.*;
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
 * Date: 27-Jan-2008<br><br>
 */
public class AxiomTypeProvider implements OWLAxiomVisitor {

    private AxiomType axiomType;

    public void visit(OWLSubClassOfAxiom axiom) {
        axiomType = SUBCLASS_OF;
    }


    public AxiomType getAxiomType(OWLAxiom axiom) {
        axiom.accept(this);
        return axiomType;
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiomType = NEGATIVE_OBJECT_PROPERTY_ASSERTION;
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        axiomType = ASYMMETRIC_OBJECT_PROPERTY;
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        axiomType = REFLEXIVE_OBJECT_PROPERTY;
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        axiomType = DISJOINT_CLASSES;
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        axiomType = DATA_PROPERTY_DOMAIN;
    }
    

    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        axiomType = OBJECT_PROPERTY_DOMAIN;
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        axiomType = EQUIVALENT_OBJECT_PROPERTIES;
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiomType = NEGATIVE_DATA_PROPERTY_ASSERTION;
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        axiomType = DIFFERENT_INDIVIDUALS;
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        axiomType = DISJOINT_DATA_PROPERTIES;
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        axiomType = DISJOINT_OBJECT_PROPERTIES;
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        axiomType = OBJECT_PROPERTY_RANGE;
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiomType = OBJECT_PROPERTY_ASSERTION;
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        axiomType = FUNCTIONAL_OBJECT_PROPERTY;
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        axiomType = SUB_OBJECT_PROPERTY;
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        axiomType = DISJOINT_UNION;
    }


    public void visit(OWLDeclarationAxiom axiom) {
        axiomType = DECLARATION;
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        axiomType = ANNOTATION_ASSERTION;
    }

    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        axiomType = SYMMETRIC_OBJECT_PROPERTY;
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        axiomType = DATA_PROPERTY_RANGE;
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        axiomType = FUNCTIONAL_DATA_PROPERTY;
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        axiomType = EQUIVALENT_DATA_PROPERTIES;
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        axiomType = CLASS_ASSERTION;
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        axiomType = EQUIVALENT_CLASSES;
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        axiomType = DATA_PROPERTY_ASSERTION;
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        axiomType = TRANSITIVE_OBJECT_PROPERTY;
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        axiomType = IRREFLEXIVE_OBJECT_PROPERTY;
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        axiomType = SUB_DATA_PROPERTY;
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        axiomType = INVERSE_FUNCTIONAL_OBJECT_PROPERTY;
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        axiomType = SAME_INDIVIDUAL;
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        axiomType = SUB_PROPERTY_CHAIN_OF;
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        axiomType = INVERSE_OBJECT_PROPERTIES;
    }


    public void visit(SWRLRule rule) {
        axiomType = SWRL_RULE;
    }

    public void visit(OWLHasKeyAxiom axiom) {
        axiomType = HAS_KEY;
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        axiomType = ANNOTATION_PROPERTY_DOMAIN;
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        axiomType = ANNOTATION_PROPERTY_RANGE;
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        axiomType = SUB_ANNOTATION_PROPERTY_OF;
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        axiomType = DATATYPE_DEFINITION;
    }
}
