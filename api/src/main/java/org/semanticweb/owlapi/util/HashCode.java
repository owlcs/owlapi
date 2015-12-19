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

import java.util.stream.Stream;

import org.semanticweb.owlapi.model.*;

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
    public static int hashCode(OWLObject object) {
        checkNotNull(object, "object cannot be null");
        HashCode hashCode = new HashCode();
        object.accept(hashCode);
        return hashCode.hashCode;
    }

    @Override
    public void visit(OWLOntology ontology) {
        hashCode = ontology.getOntologyID().hashCode();
    }

    private static int hash(Stream<?> s) {
        return s.mapToInt(Object::hashCode).sum();
    }

    private static int hash(int start, int value1) {
        return start * MULT + value1;
    }

    private static int hash(int start, int value1, int value2) {
        return (start * MULT + value1) * MULT + value2;
    }

    private static int hash(int start, int value1, int value2, int value3) {
        return ((start * MULT + value1) * MULT + value2) * MULT + value3;
    }

    private static int hash(int start, int value1, int value2, int value3, int value4) {
        return (((start * MULT + value1) * MULT + value2) * MULT + value3) * MULT + value4;
    }

    private static int hash(int start, Stream<?> s) {
        return hash(start, hash(s));
    }

    private static int hash(int start, Object o1, Stream<?> s) {
        return hash(start, o1.hashCode(), hash(s));
    }

    private static int hash(int start, Object o1) {
        return hash(start, o1.hashCode());
    }

    private static int hash(int start, Object o1, Object o2) {
        return hash(start, o1.hashCode(), o2.hashCode());
    }

    private static int hash(int start, Stream<?> s, Object o1) {
        return hash(start, hash(s), o1.hashCode());
    }

    private static int hash(int start, Object o1, Object o2, Object o3) {
        return hash(start, o1.hashCode(), o2.hashCode(), o3.hashCode());
    }

    private static int hash(int start, Object o1, int v2, Object o3) {
        return hash(start, o1.hashCode(), v2, o3.hashCode());
    }

    private static int hash(int start, int value, Object o1, Stream<?> s) {
        return hash(start, value, o1.hashCode(), hash(s));
    }

    private static int hash(int start, int value, Stream<?> s) {
        return hash(start, value, hash(s));
    }

    private static int hash(int start, Stream<?> s1, Stream<?> s2) {
        return hash(start, hash(s1), hash(s2));
    }

    private static int hash(int start, Object o1, Object o2, Stream<?> s) {
        return hash(start, o1.hashCode(), o2.hashCode(), hash(s));
    }

    private static int hash(int start, Object o1, Stream<?> s1, Stream<?> s2) {
        return hash(start, o1.hashCode(), hash(s1), hash(s2));
    }

    private static int hash(int start, OWLObject o1, OWLObject o2, OWLObject o3, Stream<?> s) {
        return hash(start, o1.hashCode(), o2.hashCode(), o3.hashCode(), hash(s));
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        hashCode = hash(3, axiom.getProperty(), axiom.annotations());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        hashCode = hash(7, axiom.getIndividual(), axiom.getClassExpression(), axiom.annotations());
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        hashCode = hash(11, axiom.getSubject(), axiom.getProperty(), axiom.getObject(), axiom.annotations());
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        hashCode = hash(13, axiom.getProperty(), axiom.getDomain(), axiom.annotations());
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        hashCode = hash(17, axiom.getProperty(), axiom.getRange(), axiom.annotations());
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        hashCode = hash(19, axiom.getSubProperty(), axiom.getSuperProperty(), axiom.annotations());
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        hashCode = hash(23, axiom.getEntity(), axiom.annotations());
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        hashCode = hash(29, axiom.individuals(), axiom.annotations());
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        hashCode = hash(31, axiom.classExpressions(), axiom.annotations());
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        hashCode = hash(37, axiom.properties(), axiom.annotations());
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        hashCode = hash(41, axiom.properties(), axiom.annotations());
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        hashCode = hash(43, axiom.getOWLClass(), axiom.classExpressions(), axiom.annotations());
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        hashCode = hash(47, axiom.getSubject(), axiom.getProperty(), axiom.getValue(), axiom.annotations());
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        hashCode = hash(53, axiom.classExpressions(), axiom.annotations());
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        hashCode = hash(59, axiom.properties(), axiom.annotations());
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        hashCode = hash(61, axiom.properties(), axiom.annotations());
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        hashCode = hash(67, axiom.getProperty(), axiom.annotations());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        hashCode = hash(71, axiom.getProperty(), axiom.annotations());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        hashCode = hash(79, axiom.getProperty(), axiom.annotations());
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        hashCode = hash(83, axiom.getFirstProperty().hashCode() + axiom.getSecondProperty().hashCode(), axiom
            .annotations());
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        hashCode = hash(89, axiom.getProperty(), axiom.annotations());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        hashCode = hash(97, axiom.getSubject(), axiom.getProperty(), axiom.getObject(), axiom.annotations());
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        hashCode = hash(101, axiom.getSubject(), axiom.getProperty(), axiom.getObject(), axiom.annotations());
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        hashCode = hash(103, axiom.getSubject(), axiom.getProperty(), axiom.getObject(), axiom.annotations());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        hashCode = hash(107, axiom.getPropertyChain().hashCode(), axiom.getSuperProperty(), axiom.annotations());
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        hashCode = hash(109, axiom.getProperty(), axiom.getDomain(), axiom.annotations());
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        hashCode = hash(113, axiom.getProperty(), axiom.getRange(), axiom.annotations());
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        hashCode = hash(127, axiom.getSubProperty(), axiom.getSuperProperty(), axiom.annotations());
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        hashCode = hash(131, axiom.getProperty(), axiom.annotations());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        hashCode = hash(137, axiom.individuals(), axiom.annotations());
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        hashCode = hash(139, axiom.getSubClass(), axiom.getSuperClass(), axiom.annotations());
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        hashCode = hash(149, axiom.getProperty(), axiom.annotations());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        hashCode = hash(151, axiom.getProperty(), axiom.annotations());
    }

    @Override
    public void visit(OWLClass ce) {
        hashCode = hash(157, ce.getIRI());
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        hashCode = hash(163, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        hashCode = hash(167, ce.getProperty(), ce.getCardinality(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        hashCode = hash(173, ce.getProperty(), ce.getCardinality(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        hashCode = hash(179, ce.getProperty(), ce.getCardinality(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        hashCode = hash(181, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        hashCode = hash(191, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        hashCode = hash(193, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        hashCode = hash(197, ce.getOperand());
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        hashCode = hash(199, ce.getProperty(), ce.getCardinality(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        hashCode = hash(211, ce.operands());
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        hashCode = hash(223, ce.getProperty(), ce.getCardinality(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        hashCode = hash(227, ce.getProperty(), ce.getCardinality(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        hashCode = hash(229, ce.individuals());
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        hashCode = hash(233, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        hashCode = hash(239, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        hashCode = hash(241, ce.operands());
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        hashCode = hash(251, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        hashCode = hash(257, node.getDataRange());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        hashCode = hash(263, node.values());
    }

    @Override
    public void visit(OWLDatatype node) {
        hashCode = hash(269, node.getIRI());
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        hashCode = hash(271, node.getDatatype(), node.facetRestrictions());
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        hashCode = hash(563, node.getFacet(), node.getFacetValue());
    }

    @Override
    public void visit(OWLLiteral node) {
        hashCode = node.hashCode();
        // hashCode = 277;
        // rely on the OWLLiteral hashcode here
    }

    @Override
    public void visit(OWLDataProperty property) {
        hashCode = hash(283, property.getIRI());
    }

    @Override
    public void visit(OWLObjectProperty property) {
        hashCode = hash(293, property.getIRI());
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        hashCode = hash(307, property.getInverse());
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        hashCode = hash(311, individual.getIRI());
    }

    @Override
    public void visit(SWRLRule rule) {
        hashCode = hash(631, rule.body(), rule.head());
    }

    @Override
    public void visit(SWRLClassAtom node) {
        hashCode = hash(641, node.getArgument(), node.getPredicate());
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        hashCode = hash(643, node.getArgument(), node.getPredicate());
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        hashCode = hash(647, node.getFirstArgument(), node.getSecondArgument(), node.getPredicate());
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        hashCode = hash(653, node.getFirstArgument(), node.getSecondArgument(), node.getPredicate());
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        hashCode = hash(659, node.arguments(), node.getPredicate());
    }

    @Override
    public void visit(SWRLVariable node) {
        hashCode = hash(661, node.getIRI());
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        hashCode = hash(677, node.getIndividual());
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        hashCode = hash(683, node.getLiteral());
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        hashCode = hash(797, node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        hashCode = hash(811, node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        hashCode = hash(821, axiom.getClassExpression(), axiom.propertyExpressions());
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        hashCode = hash(823, axiom.getProperty(), axiom.getDomain());
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        hashCode = hash(827, axiom.getProperty(), axiom.getRange());
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        hashCode = hash(829, axiom.getSubProperty(), axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        hashCode = hash(839, node.operands());
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        hashCode = hash(853, node.operands());
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        hashCode = hash(857, property.getIRI());
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        hashCode = hash(859, individual.getID());
    }

    @Override
    public void visit(IRI iri) {
        hashCode = hash(863, iri);
    }

    @Override
    public void visit(OWLAnnotation node) {
        hashCode = hash(877, node.getProperty(), node.getValue());
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        hashCode = hash(897, axiom.getDatatype(), axiom.getDataRange());
    }
}
