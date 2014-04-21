/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class HashCode implements OWLObjectVisitor, SWRLObjectVisitor {

    private int hashCode;
    private static final int MULT = 37;

    /**
     * @param object
     *        the object to compute the hashcode for
     * @return the hashcode
     */
    public static int hashCode(@Nonnull OWLObject object) {
        checkNotNull(object, "object cannot be null");
        HashCode hashCode = new HashCode();
        object.accept(hashCode);
        return hashCode.hashCode;
    }

    @Override
    public void visit(@Nonnull OWLOntology ontology) {
        hashCode = ontology.getOntologyID().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        hashCode = 3;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
        hashCode = 7;
        hashCode = hashCode * MULT + axiom.getIndividual().hashCode();
        hashCode = hashCode * MULT + axiom.getClassExpression().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        hashCode = 11;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        hashCode = 13;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        hashCode = 17;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        hashCode = 19;
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDeclarationAxiom axiom) {
        hashCode = 23;
        hashCode = hashCode * MULT + axiom.getEntity().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        hashCode = 29;
        hashCode = hashCode * MULT + axiom.getIndividuals().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        hashCode = 31;
        hashCode = hashCode * MULT + axiom.getClassExpressions().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        hashCode = 37;
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        hashCode = 41;
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        hashCode = 43;
        hashCode = hashCode * MULT + axiom.getOWLClass().hashCode();
        hashCode = hashCode * MULT + axiom.getClassExpressions().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        hashCode = 47;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getValue().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        hashCode = 53;
        hashCode = hashCode * MULT + axiom.getClassExpressions().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        hashCode = 59;
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        hashCode = 61;
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        hashCode = 67;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        hashCode = 71;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        hashCode = 79;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        hashCode = 83;
        hashCode = hashCode * MULT + axiom.getFirstProperty().hashCode()
                + axiom.getSecondProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        hashCode = 89;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        hashCode = 97;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        hashCode = 101;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        hashCode = 103;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        hashCode = 107;
        hashCode = hashCode * MULT + axiom.getPropertyChain().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        hashCode = 109;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        hashCode = 113;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        hashCode = 127;
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        hashCode = 131;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
        hashCode = 137;
        hashCode = hashCode * MULT + axiom.getIndividuals().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        hashCode = 139;
        hashCode = hashCode * MULT + axiom.getSubClass().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperClass().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        hashCode = 149;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        hashCode = 151;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLClass desc) {
        hashCode = 157;
        hashCode = hashCode * MULT + desc.getIRI().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataAllValuesFrom desc) {
        hashCode = 163;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataExactCardinality desc) {
        hashCode = 167;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataMaxCardinality desc) {
        hashCode = 173;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataMinCardinality desc) {
        hashCode = 179;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataSomeValuesFrom desc) {
        hashCode = 181;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataHasValue desc) {
        hashCode = 191;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectAllValuesFrom desc) {
        hashCode = 193;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectComplementOf desc) {
        hashCode = 197;
        hashCode = hashCode * MULT + desc.getOperand().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectExactCardinality desc) {
        hashCode = 199;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectIntersectionOf desc) {
        hashCode = 211;
        hashCode = hashCode * MULT + desc.getOperands().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectMaxCardinality desc) {
        hashCode = 223;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectMinCardinality desc) {
        hashCode = 227;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectOneOf desc) {
        hashCode = 229;
        hashCode = hashCode * MULT + desc.getIndividuals().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectHasSelf desc) {
        hashCode = 233;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectSomeValuesFrom desc) {
        hashCode = 239;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectUnionOf desc) {
        hashCode = 241;
        hashCode = hashCode * MULT + desc.getOperands().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectHasValue desc) {
        hashCode = 251;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataComplementOf node) {
        hashCode = 257;
        hashCode = hashCode * MULT + node.getDataRange().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataOneOf node) {
        hashCode = 263;
        hashCode = hashCode * MULT + node.getValues().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDatatype node) {
        hashCode = 269;
        hashCode = hashCode * MULT + node.getIRI().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDatatypeRestriction node) {
        hashCode = 271;
        hashCode = hashCode * MULT + node.getDatatype().hashCode();
        hashCode = hashCode * MULT + node.getFacetRestrictions().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLFacetRestriction node) {
        hashCode = 563;
        hashCode = hashCode * MULT + node.getFacet().hashCode();
        hashCode = hashCode * MULT + node.getFacetValue().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLLiteral node) {
        hashCode = node.hashCode();
        // hashCode = 277;
        // hashCode = hashCode * MULT + node.getDatatype().hashCode();
        // hashCode = hashCode * MULT + node.getLiteral().hashCode();
        // if(node.hasLang()) {
        // hashCode = hashCode * MULT + node.getLang().hashCode();
        // }
    }

    @Override
    public void visit(@Nonnull OWLDataProperty property) {
        hashCode = 283;
        hashCode = hashCode * MULT + property.getIRI().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectProperty property) {
        hashCode = 293;
        hashCode = hashCode * MULT + property.getIRI().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLObjectInverseOf property) {
        hashCode = 307;
        hashCode = hashCode * MULT + property.getInverse().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLNamedIndividual individual) {
        hashCode = 311;
        hashCode = hashCode * MULT + individual.getIRI().hashCode();
    }

    @Override
    public void visit(@Nonnull SWRLRule rule) {
        hashCode = 631;
        hashCode = hashCode * MULT + rule.getBody().hashCode();
        hashCode = hashCode * MULT + rule.getHead().hashCode();
    }

    @Override
    public void visit(@Nonnull SWRLClassAtom node) {
        hashCode = 641;
        hashCode = hashCode * MULT + node.getArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(@Nonnull SWRLDataRangeAtom node) {
        hashCode = 643;
        hashCode = hashCode * MULT + node.getArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(@Nonnull SWRLObjectPropertyAtom node) {
        hashCode = 647;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(@Nonnull SWRLDataPropertyAtom node) {
        hashCode = 653;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(@Nonnull SWRLBuiltInAtom node) {
        hashCode = 659;
        hashCode = hashCode * MULT + node.getAllArguments().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(@Nonnull SWRLVariable node) {
        hashCode = 661;
        hashCode = hashCode * MULT + node.getIRI().hashCode();
    }

    @Override
    public void visit(@Nonnull SWRLIndividualArgument node) {
        hashCode = 677;
        hashCode = hashCode * MULT + node.getIndividual().hashCode();
    }

    @Override
    public void visit(@Nonnull SWRLLiteralArgument node) {
        hashCode = 683;
        hashCode = hashCode * MULT + node.getLiteral().hashCode();
    }

    @Override
    public void visit(@Nonnull SWRLDifferentIndividualsAtom node) {
        hashCode = 797;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
    }

    @Override
    public void visit(@Nonnull SWRLSameIndividualAtom node) {
        hashCode = 811;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLHasKeyAxiom axiom) {
        hashCode = 821;
        hashCode = hashCode * MULT + axiom.getClassExpression().hashCode();
        hashCode = hashCode * MULT + axiom.getPropertyExpressions().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        hashCode = 823;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        hashCode = 827;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        hashCode = 829;
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataIntersectionOf node) {
        hashCode = 839;
        hashCode = hashCode * MULT + node.getOperands().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDataUnionOf node) {
        hashCode = 853;
        hashCode = hashCode * MULT + node.getOperands().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLAnnotationProperty property) {
        hashCode = 857;
        hashCode = hashCode * MULT + property.getIRI().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLAnonymousIndividual individual) {
        hashCode = 859;
        hashCode = hashCode * MULT + individual.getID().hashCode();
    }

    @Override
    public void visit(@Nonnull IRI iri) {
        hashCode = 863;
        hashCode = hashCode * MULT + iri.toURI().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLAnnotation node) {
        hashCode = 877;
        hashCode = hashCode * MULT + node.getProperty().hashCode();
        hashCode = hashCode * MULT + node.getValue().hashCode();
    }

    @Override
    public void visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        hashCode = 897;
        hashCode = hashCode * MULT + axiom.getDatatype().hashCode();
        hashCode = hashCode * MULT + axiom.getDataRange().hashCode();
    }
}
