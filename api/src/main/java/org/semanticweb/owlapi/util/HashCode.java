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
        return s.mapToInt(a -> a.hashCode()).sum();
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        hashCode = 3;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        hashCode = 7;
        hashCode = hashCode * MULT + axiom.getIndividual().hashCode();
        hashCode = hashCode * MULT + axiom.getClassExpression().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        hashCode = 11;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        hashCode = 13;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        hashCode = 17;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        hashCode = 19;
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        hashCode = 23;
        hashCode = hashCode * MULT + axiom.getEntity().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        hashCode = 29;
        hashCode = hashCode * MULT + hash(axiom.individuals());
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        hashCode = 31;
        hashCode = hashCode * MULT + hash(axiom.classExpressions());
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        hashCode = 37;
        hashCode = hashCode * MULT + hash(axiom.properties());
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        hashCode = 41;
        hashCode = hashCode * MULT + hash(axiom.properties());
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        hashCode = 43;
        hashCode = hashCode * MULT + axiom.getOWLClass().hashCode();
        hashCode = hashCode * MULT + hash(axiom.classExpressions());
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        hashCode = 47;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getValue().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        hashCode = 53;
        hashCode = hashCode * MULT + hash(axiom.classExpressions());
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        hashCode = 59;
        hashCode = hashCode * MULT + hash(axiom.properties());
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        hashCode = 61;
        hashCode = hashCode * MULT + hash(axiom.properties());
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        hashCode = 67;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        hashCode = 71;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        hashCode = 79;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        hashCode = 83;
        hashCode = hashCode * MULT + axiom.getFirstProperty().hashCode() + axiom.getSecondProperty().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        hashCode = 89;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        hashCode = 97;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        hashCode = 101;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        hashCode = 103;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        hashCode = 107;
        hashCode = hashCode * MULT + axiom.getPropertyChain().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        hashCode = 109;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        hashCode = 113;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        hashCode = 127;
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        hashCode = 131;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        hashCode = 137;
        hashCode = hashCode * MULT + hash(axiom.individuals());
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        hashCode = 139;
        hashCode = hashCode * MULT + axiom.getSubClass().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperClass().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        hashCode = 149;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        hashCode = 151;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + hash(axiom.annotations());
    }

    @Override
    public void visit(OWLClass ce) {
        hashCode = 157;
        hashCode = hashCode * MULT + ce.getIRI().hashCode();
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        hashCode = 163;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        hashCode = 167;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getCardinality();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        hashCode = 173;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getCardinality();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        hashCode = 179;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getCardinality();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        hashCode = 181;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        hashCode = 191;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        hashCode = 193;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        hashCode = 197;
        hashCode = hashCode * MULT + ce.getOperand().hashCode();
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        hashCode = 199;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getCardinality();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        hashCode = 211;
        hashCode = hashCode * MULT + hash(ce.operands());
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        hashCode = 223;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getCardinality();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        hashCode = 227;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getCardinality();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        hashCode = 229;
        hashCode = hashCode * MULT + hash(ce.individuals());
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        hashCode = 233;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        hashCode = 239;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        hashCode = 241;
        hashCode = hashCode * MULT + hash(ce.operands());
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        hashCode = 251;
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        hashCode = 257;
        hashCode = hashCode * MULT + node.getDataRange().hashCode();
    }

    @Override
    public void visit(OWLDataOneOf node) {
        hashCode = 263;
        hashCode = hashCode * MULT + hash(node.values());
    }

    @Override
    public void visit(OWLDatatype node) {
        hashCode = 269;
        hashCode = hashCode * MULT + node.getIRI().hashCode();
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        hashCode = 271;
        hashCode = hashCode * MULT + node.getDatatype().hashCode();
        hashCode = hashCode * MULT + hash(node.facetRestrictions());
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        hashCode = 563;
        hashCode = hashCode * MULT + node.getFacet().hashCode();
        hashCode = hashCode * MULT + node.getFacetValue().hashCode();
    }

    @Override
    public void visit(OWLLiteral node) {
        hashCode = node.hashCode();
        // hashCode = 277;
        // hashCode = hashCode * MULT + node.getDatatype().hashCode();
        // hashCode = hashCode * MULT + node.getLiteral().hashCode();
        // if(node.hasLang()) {
        // hashCode = hashCode * MULT + node.getLang().hashCode();
        // }
    }

    @Override
    public void visit(OWLDataProperty property) {
        hashCode = 283;
        hashCode = hashCode * MULT + property.getIRI().hashCode();
    }

    @Override
    public void visit(OWLObjectProperty property) {
        hashCode = 293;
        hashCode = hashCode * MULT + property.getIRI().hashCode();
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        hashCode = 307;
        hashCode = hashCode * MULT + property.getInverse().hashCode();
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        hashCode = 311;
        hashCode = hashCode * MULT + individual.getIRI().hashCode();
    }

    @Override
    public void visit(SWRLRule rule) {
        hashCode = 631;
        hashCode = hashCode * MULT + hash(rule.body());
        hashCode = hashCode * MULT + hash(rule.head());
    }

    @Override
    public void visit(SWRLClassAtom node) {
        hashCode = 641;
        hashCode = hashCode * MULT + node.getArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        hashCode = 643;
        hashCode = hashCode * MULT + node.getArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        hashCode = 647;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        hashCode = 653;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        hashCode = 659;
        hashCode = hashCode * MULT + node.getAllArguments().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(SWRLVariable node) {
        hashCode = 661;
        hashCode = hashCode * MULT + node.getIRI().hashCode();
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        hashCode = 677;
        hashCode = hashCode * MULT + node.getIndividual().hashCode();
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        hashCode = 683;
        hashCode = hashCode * MULT + node.getLiteral().hashCode();
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        hashCode = 797;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        hashCode = 811;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        hashCode = 821;
        hashCode = hashCode * MULT + axiom.getClassExpression().hashCode();
        hashCode = hashCode * MULT + hash(axiom.propertyExpressions());
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        hashCode = 823;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        hashCode = 827;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        hashCode = 829;
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        hashCode = 839;
        hashCode = hashCode * MULT + hash(node.operands());
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        hashCode = 853;
        hashCode = hashCode * MULT + hash(node.operands());
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        hashCode = 857;
        hashCode = hashCode * MULT + property.getIRI().hashCode();
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        hashCode = 859;
        hashCode = hashCode * MULT + individual.getID().hashCode();
    }

    @Override
    public void visit(IRI iri) {
        hashCode = 863;
        hashCode = hashCode * MULT + iri.toURI().hashCode();
    }

    @Override
    public void visit(OWLAnnotation node) {
        hashCode = 877;
        hashCode = hashCode * MULT + node.getProperty().hashCode();
        hashCode = hashCode * MULT + node.getValue().hashCode();
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        hashCode = 897;
        hashCode = hashCode * MULT + axiom.getDatatype().hashCode();
        hashCode = hashCode * MULT + axiom.getDataRange().hashCode();
    }
}
