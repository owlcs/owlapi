/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.model.AxiomType.*;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jan-2008<br><br>
 */
//XXX visitorEx?
@SuppressWarnings("unused")
public class AxiomTypeProvider implements OWLAxiomVisitor {

    private AxiomType<?> axiomType;

    public void visit(OWLSubClassOfAxiom axiom) {
        axiomType = SUBCLASS_OF;
    }


    public AxiomType<?> getAxiomType(OWLAxiom axiom) {
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
