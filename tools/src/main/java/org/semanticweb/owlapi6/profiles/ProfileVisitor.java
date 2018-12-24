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
package org.semanticweb.owlapi6.profiles;

import static org.semanticweb.owlapi6.model.parameters.Imports.INCLUDED;

import java.util.Collection;

import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataComplementOf;
import org.semanticweb.owlapi6.model.OWLDataExactCardinality;
import org.semanticweb.owlapi6.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi6.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi6.model.OWLDataMinCardinality;
import org.semanticweb.owlapi6.model.OWLDataOneOf;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataUnionOf;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi6.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi6.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectComplementOf;
import org.semanticweb.owlapi6.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi6.model.OWLObjectHasSelf;
import org.semanticweb.owlapi6.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi6.model.OWLObjectInverseOf;
import org.semanticweb.owlapi6.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi6.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi6.model.OWLObjectOneOf;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLObjectUnionOf;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyID;
import org.semanticweb.owlapi6.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.SWRLRule;
import org.semanticweb.owlapi6.utility.OWLOntologyWalker;

/**
 * Profile visitor - walks the imports closure and adds all violations found to the violation
 * collection. This class is public for use in extensions of the profile detection framework. Any
 * other use should be through the OWLProfile implementations. See Profiles for convenience methods.
 * 
 * @author ignazio
 *
 */
public class ProfileVisitor extends ProfileVisitorBase {

    /**
     * @param walker onotlogy walker to use
     * @param violations collection of violations; the collection is modified during the visit
     * @param profiles the profiles to check. An empty collection means OWL 2 FULL will be the
     *        pofile used.
     */
    public ProfileVisitor(OWLOntologyWalker walker, Collection<OWLProfileViolation> violations,
        Collection<Profiles> profiles) {
        super(walker, violations, profiles);
    }

    @Override
    public void visit(IRI iri) {
        relativeIRI(iri);
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        // The datatype MUST be declared
        undeclaredDatatype(axiom.getDatatype());
        dl(() -> reservedForDatatype(axiom), () -> cycleInDefinition(axiom));
        rl(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        // The datatype should not be defined with a datatype definition
        // axiom
        OWLDatatype datatype = node.getDatatype();
        getCurrentOntology().importsClosure().flatMap(o -> o.axioms(AxiomType.DATATYPE_DEFINITION))
            .filter(ax -> datatype.equals(ax.getDatatype())).forEach(ax -> definedDatatype(node));
        // All facets must be allowed for the restricted datatype
        node.facetRestrictions().forEach(r -> illegalFacet(node, datatype, r));
        el(() -> illegalDataRange(node));
        ql(() -> illegalDataRange(node));
        rl(() -> illegalDataRange(node));
    }

    @Override
    public void visit(OWLLiteral node) {
        notInLexicalSpace(node);
    }

    @Override
    public void visit(OWLOntology ontology) {
        propertyManager = null;
        // The ontology IRI and version IRI must be absolute and must not be
        // from the reserved vocab
        OWLOntologyID id = ontology.getOntologyID();
        if (id.isNamed()) {
            ontologyIRINotAbsolute(id);
            versionNotAbsolute(id);
        }
        dl(() -> reservedForOntologyIRI(ontology.getOntologyID()),
            () -> reservedForVersionIRI(ontology.getOntologyID()));
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        dl(() -> reservedForAnnotation(property), () -> undeclaredAnnotation(property),
            () -> punningAnnotation(property));
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        dl(() -> asymmetricNonSimple(axiom));
        el(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLClass ce) {
        dl(() -> reservedForClass(ce), () -> undeclaredClass(ce),
            () -> punningDatatypeAndClass(ce));
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        dl(() -> insufficientOperands(node));
    }

    @Override
    public void visit(OWLDataOneOf node) {
        dl(() -> emptyOneOf(node));
        el(() -> multipleOneOf(node));
        ql(() -> illegalDataRange(node));
        rl(() -> illegalDataRange(node));
    }

    @Override
    public void visit(OWLDataProperty property) {
        dl(() -> reservedForDataProperty(property), () -> undeclaredDataProperty(property),
            () -> illegalDataPropertyPunning(property));
    }

    @Override
    public void visit(OWLDatatype node) {
        // Each datatype MUST statisfy the following:
        // An IRI used to identify a datatype MUST
        // - Identify a datatype in the OWL 2 datatype map (Section 4.1
        // lists them), or
        // - Have the xsd: prefix, or
        // - Be rdfs:Literal, or
        // - Not be in the reserved vocabulary of OWL 2
        dl(() -> unknownDatatype(node), () -> undeclaredDatatype(node),
            () -> punningDatatypeAndClass(node));
        el(() -> elDataRange(node));
        ql(() -> illegalQLDatatype(node));
        rl(() -> illegalRLDatatype(node));
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        dl(() -> insufficientOperands(node));
        el(() -> illegalDataRange(node));
        ql(() -> illegalDataRange(node));
        rl(() -> illegalDataRange(node));
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        dl(() -> insufficientIndividuals(axiom));
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        dl(() -> insufficientOperands(axiom));
        ql(() -> axiom.classExpressions().filter(ce -> !isOWL2QLSubClassExpression(ce))
            .forEach(this::nonSubclass));
        rl(() -> axiom.classExpressions()
            .filter(ce -> !ce.accept(equivalentClassExpressionChecker).booleanValue())
            .forEach(this::nonSubclass));
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        dl(() -> insufficientProperties(axiom));
        el(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        dl(() -> insufficientProperties(axiom), () -> axiom.properties()
            .filter(getPropertyManager()::isNonSimple).forEach(this::disjointNonSimple));
        el(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        dl(() -> insufficientOperands(axiom));
        el(() -> illegalAxiom());
        ql(() -> illegalAxiom());
        rl(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        dl(() -> insufficientOperands(axiom));
        ql(() -> axiom.classExpressions().filter(ce -> !isOWL2QLSubClassExpression(ce))
            .forEach(this::nonSubclass));
        rl(() -> axiom.classExpressions()
            .filter(ce -> !ce.accept(equivalentClassExpressionChecker).booleanValue())
            .forEach(this::nonEquivalentClass));
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        dl(() -> insufficientProperties(axiom));
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        dl(() -> insufficientProperties(axiom));
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        dl(() -> functionalNonSimple(axiom));
        el(() -> illegalAxiom());
        ql(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        dl(() -> emptyProperties(axiom));
        ql(() -> illegalAxiom());
        rl(() -> nonSubClassRL(axiom.getClassExpression()));
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        dl(() -> inverseFunctionalNonSimple(axiom));
        el(() -> illegalAxiom());
        ql(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        dl(() -> irreflexiveNonSimple(axiom));
        el(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        rl(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        dl(() -> reservedForIndividual(individual));
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        dl(() -> nonSimple(ce));
        el(() -> illegalClass(ce));
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        dl(() -> hasSelfNonSimple(ce));
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        dl(() -> insufficientOperands(ce));
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        dl(() -> nonSimple(ce));
        el(() -> illegalClass(ce));
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        dl(() -> nonSimple(ce));
        el(() -> illegalClass(ce));
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        dl(() -> emptyOneOf(ce));
        el(() -> multipleOneOf(ce));
    }

    @Override
    public void visit(OWLObjectProperty property) {
        dl(() -> reservedForObjectProperty(property), () -> undeclaredObjectProperty(property),
            () -> illegalObjectPropertyPunning(property));
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        dl(() -> insufficientOperands(ce));
        el(() -> illegalClass(ce));
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        dl(() -> insufficientIndividuals(axiom));
        ql(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        dl(() -> topAsSubProperty(axiom));
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        // Restriction on the Property Hierarchy. A strict partial order
        // (i.e., an irreflexive and transitive relation) < on AllOPE(Ax)
        // exists that fulfills the following conditions:
        //
        // OP1 < OP2 if and only if INV(OP1) < OP2 for all object properties
        // OP1 and OP2 occurring in AllOPE(Ax).
        // If OPE1 < OPE2 holds, then OPE2 ->* OPE1 does not hold.
        // Each axiom in Ax of the form SubObjectPropertyOf(
        // ObjectPropertyChain( OPE1 ... OPEn ) OPE ) with n => 2 fulfills
        // the following conditions:
        // OPE is equal to owl:topObjectProperty, or [TOP]
        // n = 2 and OPE1 = OPE2 = OPE, or [TRANSITIVE_PROP]
        // OPEi < OPE for each 1 <= i <= n, or [ALL_SMALLER]
        // OPE1 = OPE and OPEi < OPE for each 2 <= i <= n, or [FIRST_EQUAL]
        // OPEn = OPE and OPEi < OPE for each 1 <= i <= n-1. [LAST_EQUAL]
        dl(() -> insufficientProperties(axiom), () -> chainCycle(axiom));
        // Do we have a range restriction imposed on our super property?
        el(() -> getCurrentOntology().axioms(AxiomType.OBJECT_PROPERTY_RANGE, INCLUDED)
            .forEach(this::chainRange));
        ql(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        el(() -> anonIndividual(individual));
        ql(() -> anonIndividual(individual));
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        el(() -> axiom.getClassExpression().accept(this));
        ql(() -> nonAtomic(axiom));
        rl(() -> nonSuperClassRL(axiom.getClassExpression()));
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        el(() -> illegalClass(ce));
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        el(() -> illegalDataRange(node));
        ql(() -> illegalDataRange(node));
        rl(() -> illegalDataRange(node));
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        el(() -> illegalClass(ce));
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        el(() -> illegalClass(ce));
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        el(() -> illegalClass(ce));
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        el(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        el(() -> illegalClass(ce));
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        el(() -> illegalClass(ce));
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        el(() -> inverse(property));
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        el(() -> illegalAxiom());
    }

    @Override
    public void visit(SWRLRule rule) {
        el(() -> illegalAxiom());
        ql(() -> illegalAxiom());
        rl(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        ql(() -> nonSuperClassQL(axiom.getDomain()));
        rl(() -> nonSuperClassRL(axiom.getDomain()));
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        ql(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        ql(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        ql(() -> illegalAxiom());
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        ql(() -> nonSuperClassQL(axiom.getDomain()));
        rl(() -> nonSuperClassRL(axiom.getDomain()));
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        ql(() -> nonSuperClassQL(axiom.getRange()));
        rl(() -> nonSuperClassRL(axiom.getRange()));
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        ql(() -> nonSubClassQL(axiom), () -> nonSuperClassQL(axiom.getSuperClass()));
        rl(() -> nonSubClassRL(axiom.getSubClass()), () -> nonSuperClassRL(axiom.getSuperClass()));
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        ql(() -> illegalAxiom());
    }
}
