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
 * Date: 27-Jul-2007<br><br>
 */
public abstract class OWLAxiomTypeProcessor implements OWLAxiomVisitor {

    protected abstract void process(OWLAxiom axiom, AxiomType type);

    public void visit(OWLSubClassOfAxiom axiom) {
        process(axiom, SUBCLASS_OF);
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        process(axiom, NEGATIVE_OBJECT_PROPERTY_ASSERTION);
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        process(axiom, ASYMMETRIC_OBJECT_PROPERTY);
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        process(axiom, REFLEXIVE_OBJECT_PROPERTY);
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        process(axiom, DISJOINT_CLASSES);
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        process(axiom, DATA_PROPERTY_DOMAIN);
    }

    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        process(axiom, OBJECT_PROPERTY_DOMAIN);
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        process(axiom, EQUIVALENT_OBJECT_PROPERTIES);
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        process(axiom, NEGATIVE_DATA_PROPERTY_ASSERTION);
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        process(axiom, DIFFERENT_INDIVIDUALS);
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        process(axiom, DISJOINT_DATA_PROPERTIES);
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        process(axiom, DISJOINT_OBJECT_PROPERTIES);
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        process(axiom, OBJECT_PROPERTY_RANGE);
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        process(axiom, DATA_PROPERTY_ASSERTION);
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        process(axiom, FUNCTIONAL_OBJECT_PROPERTY);
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        process(axiom, SUB_OBJECT_PROPERTY);
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        process(axiom, DISJOINT_UNION);
    }


    public void visit(OWLDeclarationAxiom axiom) {
        process(axiom, DECLARATION);
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        process(axiom, ANNOTATION_ASSERTION);
    }

    public void visit(OWLHasKeyAxiom axiom) {
        process(axiom, HAS_KEY);
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        process(axiom, ANNOTATION_PROPERTY_DOMAIN);
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        process(axiom, ANNOTATION_PROPERTY_RANGE);
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        process(axiom, SUB_ANNOTATION_PROPERTY_OF);
    }

    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        process(axiom, SYMMETRIC_OBJECT_PROPERTY);
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        process(axiom, DATA_PROPERTY_RANGE);
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        process(axiom, FUNCTIONAL_DATA_PROPERTY);
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        process(axiom, EQUIVALENT_DATA_PROPERTIES);
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        process(axiom, CLASS_ASSERTION);

    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        process(axiom, EQUIVALENT_CLASSES);
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        process(axiom, DATA_PROPERTY_ASSERTION);
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        process(axiom, TRANSITIVE_OBJECT_PROPERTY);
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        process(axiom, IRREFLEXIVE_OBJECT_PROPERTY);
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        process(axiom, SUB_DATA_PROPERTY);
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        process(axiom, INVERSE_FUNCTIONAL_OBJECT_PROPERTY);
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        process(axiom, SAME_INDIVIDUAL);
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        process(axiom, SUB_PROPERTY_CHAIN_OF);
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        process(axiom, INVERSE_OBJECT_PROPERTIES);
    }


    public void visit(SWRLRule rule) {
        process(rule, SWRL_RULE);
    }
}
