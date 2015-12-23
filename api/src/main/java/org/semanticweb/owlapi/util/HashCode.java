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
        hashCode = hash(0, ontology.components());
    }

    /**
     * Streams from components need a start point and the order of the
     * components is important.
     * 
     * @param start
     *        start index
     * @param s
     *        stream to hash
     * @return hash for the stream
     */
    private static int hash(int start, Stream<?> s) {
        return s.sequential().mapToInt(o -> o instanceof Stream ? hashStreamFromCollections((Stream<?>) o)
            : o.hashCode()).reduce(start, (a, b) -> a * MULT + b);
    }

    /**
     * Streams from collections simply add the hashcodes - collection order is
     * unimportant for hashing.
     * 
     * @param s
     *        stream to hash
     * @return hash for the stream
     */
    private static int hashStreamFromCollections(Stream<?> s) {
        return s.mapToInt(Object::hashCode).sum();
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        hashCode = hash(3, axiom.components());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        hashCode = hash(7, axiom.components());
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        hashCode = hash(11, axiom.components());
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        hashCode = hash(13, axiom.components());
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        hashCode = hash(17, axiom.components());
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        hashCode = hash(19, axiom.components());
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        hashCode = hash(23, axiom.components());
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        hashCode = hash(29, axiom.components());
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        hashCode = hash(31, axiom.components());
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        hashCode = hash(37, axiom.components());
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        hashCode = hash(41, axiom.components());
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        hashCode = hash(43, axiom.components());
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        hashCode = hash(47, axiom.components());
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        hashCode = hash(53, axiom.components());
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        hashCode = hash(59, axiom.components());
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        hashCode = hash(61, axiom.components());
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        hashCode = hash(67, axiom.components());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        hashCode = hash(71, axiom.components());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        hashCode = hash(79, axiom.components());
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        hashCode = hash(83, axiom.components());
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        hashCode = hash(89, axiom.components());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        hashCode = hash(97, axiom.components());
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        hashCode = hash(101, axiom.components());
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        hashCode = hash(103, axiom.components());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        hashCode = hash(107, axiom.components());
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        hashCode = hash(109, axiom.components());
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        hashCode = hash(113, axiom.components());
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        hashCode = hash(127, axiom.components());
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        hashCode = hash(131, axiom.components());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        hashCode = hash(137, axiom.components());
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        hashCode = hash(139, axiom.components());
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        hashCode = hash(149, axiom.components());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        hashCode = hash(151, axiom.components());
    }

    @Override
    public void visit(OWLClass ce) {
        hashCode = hash(157, ce.components());
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        hashCode = hash(163, ce.components());
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        hashCode = hash(167, ce.components());
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        hashCode = hash(173, ce.components());
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        hashCode = hash(179, ce.components());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        hashCode = hash(181, ce.components());
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        hashCode = hash(191, ce.components());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        hashCode = hash(193, ce.components());
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        hashCode = hash(197, ce.components());
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        hashCode = hash(199, ce.components());
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        hashCode = hash(211, ce.components());
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        hashCode = hash(223, ce.components());
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        hashCode = hash(227, ce.components());
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        hashCode = hash(229, ce.components());
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        hashCode = hash(233, ce.components());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        hashCode = hash(239, ce.components());
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        hashCode = hash(241, ce.components());
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        hashCode = hash(251, ce.components());
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        hashCode = hash(257, node.components());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        hashCode = hash(263, node.components());
    }

    @Override
    public void visit(OWLDatatype node) {
        hashCode = hash(269, node.components());
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        hashCode = hash(271, node.components());
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        hashCode = hash(563, node.components());
    }

    @Override
    public void visit(OWLLiteral node) {
        hashCode = hash(277, node.components());
    }

    @Override
    public void visit(OWLDataProperty property) {
        hashCode = hash(283, property.components());
    }

    @Override
    public void visit(OWLObjectProperty property) {
        hashCode = hash(293, property.components());
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        hashCode = hash(307, property.components());
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        hashCode = hash(311, individual.components());
    }

    @Override
    public void visit(SWRLRule rule) {
        hashCode = hash(631, rule.components());
    }

    @Override
    public void visit(SWRLClassAtom node) {
        hashCode = hash(641, node.components());
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        hashCode = hash(643, node.components());
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        hashCode = hash(647, node.components());
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        hashCode = hash(653, node.components());
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        hashCode = hash(659, node.components());
    }

    @Override
    public void visit(SWRLVariable node) {
        hashCode = hash(661, node.components());
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        hashCode = hash(677, node.components());
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        hashCode = hash(683, node.components());
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        hashCode = hash(797, node.components());
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        hashCode = hash(811, node.components());
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        hashCode = hash(821, axiom.components());
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        hashCode = hash(823, axiom.components());
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        hashCode = hash(827, axiom.components());
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        hashCode = hash(829, axiom.components());
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        hashCode = hash(839, node.components());
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        hashCode = hash(853, node.components());
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        hashCode = hash(857, property.components());
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        hashCode = hash(859, individual.components());
    }

    @Override
    public void visit(IRI iri) {
        hashCode = hash(863, iri.components());
    }

    @Override
    public void visit(OWLAnnotation node) {
        hashCode = hash(877, node.components());
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        hashCode = hash(897, axiom.components());
    }
}
