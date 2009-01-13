package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;
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
 * Date: 03-Jan-2007<br><br>
 */
public class HashCode implements OWLObjectVisitor, SWRLObjectVisitor {

    private int hashCode;

    private static HashCode instance = new HashCode();

    private static final int MULT = 37;


    public static int hashCode(OWLObject object) {
        object.accept(instance);
        return instance.hashCode;
    }


    public static int hashCode(SWRLObject object) {
        object.accept(instance);
        return instance.hashCode;
    }


    public void visit(OWLOntology ontology) {
        hashCode = 557;
        hashCode = hashCode * MULT + ontology.getURI().hashCode();
    }


    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
        hashCode = 3;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {
        hashCode = 5;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotation().hashCode();
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        hashCode = 7;
        hashCode = hashCode * MULT + axiom.getIndividual().hashCode();
        hashCode = hashCode * MULT + axiom.getDescription().hashCode();
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        hashCode = 11;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        hashCode = 13;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        hashCode = 17;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
    }


    public void visit(OWLDataSubPropertyAxiom axiom) {
        hashCode = 19;
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
    }


    public void visit(OWLDeclarationAxiom axiom) {
        hashCode = 23;
        hashCode = hashCode * MULT + axiom.getEntity().hashCode();
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        hashCode = 29;
        hashCode = hashCode * MULT + axiom.getIndividuals().hashCode();
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        hashCode = 31;
        hashCode = hashCode * MULT + axiom.getDescriptions().hashCode();
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        hashCode = 37;
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        hashCode = 41;
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        hashCode = 43;
        hashCode = hashCode * MULT + axiom.getOWLClass().hashCode();
        hashCode = hashCode * MULT + axiom.getDescriptions().hashCode();
    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
        hashCode = 47;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode() * 571;
        hashCode = hashCode * MULT + axiom.getAnnotation().hashCode();
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        hashCode = 53;
        hashCode = hashCode * MULT + axiom.getDescriptions().hashCode();
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        hashCode = 59;
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        hashCode = 61;
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        hashCode = 67;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        hashCode = 71;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
    }


    public void visit(OWLImportsDeclaration axiom) {
        hashCode = 73;
        hashCode = hashCode * MULT + axiom.getSubject().getURI().hashCode();
        hashCode = hashCode * MULT + axiom.getImportedOntologyURI().hashCode();
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        hashCode = 79;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        hashCode = 83;
        hashCode = hashCode * MULT + axiom.getFirstProperty().hashCode() + axiom.getSecondProperty().hashCode();
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        hashCode = 89;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        hashCode = 97;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        hashCode = 101;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        hashCode = 103;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
    }


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
        hashCode = 107;
        hashCode = hashCode * MULT + axiom.getPropertyChain().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        hashCode = 109;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        hashCode = 113;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
    }


    public void visit(OWLObjectSubPropertyAxiom axiom) {
        hashCode = 127;
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        hashCode = 131;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
    }


    public void visit(OWLSameIndividualsAxiom axiom) {
        hashCode = 137;
        hashCode = hashCode * MULT + axiom.getIndividuals().hashCode();
    }


    public void visit(OWLSubClassAxiom axiom) {
        hashCode = 139;
        hashCode = hashCode * MULT + axiom.getSubClass().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperClass().hashCode();
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        hashCode = 149;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        hashCode = 151;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
    }


    public void visit(OWLClass desc) {
        hashCode = 157;
        hashCode = hashCode * MULT + desc.getURI().hashCode();
    }


    public void visit(OWLDataAllRestriction desc) {
        hashCode = 163;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLDataExactCardinalityRestriction desc) {
        hashCode = 167;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLDataMaxCardinalityRestriction desc) {
        hashCode = 173;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLDataMinCardinalityRestriction desc) {
        hashCode = 179;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLDataSomeRestriction desc) {
        hashCode = 181;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLDataValueRestriction desc) {
        hashCode = 191;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getValue().hashCode();
    }


    public void visit(OWLObjectAllRestriction desc) {
        hashCode = 193;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLObjectComplementOf desc) {
        hashCode = 197;
        hashCode = hashCode * MULT + desc.getOperand().hashCode();
    }


    public void visit(OWLObjectExactCardinalityRestriction desc) {
        hashCode = 199;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLObjectIntersectionOf desc) {
        hashCode = 211;
        hashCode = hashCode * MULT + desc.getOperands().hashCode();
    }


    public void visit(OWLObjectMaxCardinalityRestriction desc) {
        hashCode = 223;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLObjectMinCardinalityRestriction desc) {
        hashCode = 227;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLObjectOneOf desc) {
        hashCode = 229;
        hashCode = hashCode * MULT + desc.getIndividuals().hashCode();
    }


    public void visit(OWLObjectSelfRestriction desc) {
        hashCode = 233;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
    }


    public void visit(OWLObjectSomeRestriction desc) {
        hashCode = 239;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLObjectUnionOf desc) {
        hashCode = 241;
        hashCode = hashCode * MULT + desc.getOperands().hashCode();
    }


    public void visit(OWLObjectValueRestriction desc) {
        hashCode = 251;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getValue().hashCode();
    }


    public void visit(OWLDataComplementOf node) {
        hashCode = 257;
        hashCode = hashCode * MULT + node.getDataRange().hashCode();
    }


    public void visit(OWLDataOneOf node) {
        hashCode = 263;
        hashCode = hashCode * MULT + node.getValues().hashCode();
    }


    public void visit(OWLDataType node) {
        hashCode = 269;
        hashCode = hashCode * MULT + node.getURI().hashCode();
    }


    public void visit(OWLDataRangeRestriction node) {
        hashCode = 271;
        hashCode = hashCode * MULT + node.getDataRange().hashCode();
        hashCode = hashCode * MULT + node.getFacetRestrictions().hashCode();
    }


    public void visit(OWLDataRangeFacetRestriction node) {
        hashCode = 563;
        hashCode = hashCode * MULT + node.getFacet().hashCode();
        hashCode = hashCode * MULT + node.getFacetValue().hashCode();
    }


    public void visit(OWLTypedConstant node) {
        hashCode = 277;
        hashCode = hashCode * MULT + node.getDataType().hashCode();
        hashCode = hashCode * MULT + node.getLiteral().hashCode();
    }


    public void visit(OWLUntypedConstant node) {
        hashCode = 281;
        hashCode = hashCode * MULT + node.getLiteral().hashCode();
        if (node.hasLang()) {
            hashCode = hashCode * MULT + node.getLang().hashCode();
        }
    }


    public void visit(OWLDataProperty property) {
        hashCode = 283;
        hashCode = hashCode * MULT + property.getURI().hashCode();
    }


    public void visit(OWLObjectProperty property) {
        hashCode = 293;
        hashCode = hashCode * MULT + property.getURI().hashCode();
    }


    public void visit(OWLObjectPropertyInverse property) {
        hashCode = 307;
        hashCode = hashCode * MULT + property.getInverse().hashCode();
    }


    public void visit(OWLIndividual individual) {
        hashCode = 311;
        hashCode = hashCode * MULT + individual.getURI().hashCode();
    }


    public void visit(OWLConstantAnnotation annotation) {
        hashCode = 1073;
        hashCode = hashCode * MULT + annotation.getAnnotationValue().hashCode();
        hashCode = hashCode * MULT + annotation.getAnnotationURI().hashCode();
    }


    public void visit(OWLObjectAnnotation annotation) {
        hashCode = 1;
        hashCode = hashCode * MULT + annotation.getAnnotationValue().hashCode();
        hashCode = hashCode * MULT + annotation.getAnnotationURI().hashCode();
    }


    public void visit(SWRLRule rule) {
        hashCode = 631;
        if (!rule.isAnonymous()) {
            hashCode = hashCode * MULT + rule.getURI().hashCode();
        }
        hashCode = hashCode * MULT + rule.getBody().hashCode();
        hashCode = hashCode * MULT + rule.getHead().hashCode();
    }


    public void visit(SWRLClassAtom node) {
        hashCode = 641;
        hashCode = hashCode * MULT + node.getArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }


    public void visit(SWRLDataRangeAtom node) {
        hashCode = 643;
        hashCode = hashCode * MULT + node.getArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }


    public void visit(SWRLObjectPropertyAtom node) {
        hashCode = 647;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }


    public void visit(SWRLDataValuedPropertyAtom node) {
        hashCode = 653;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }


    public void visit(SWRLBuiltInAtom node) {
        hashCode = 659;
        hashCode = hashCode * MULT + node.getAllArguments().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }


    public void visit(SWRLAtomDVariable node) {
        hashCode = 661;
        hashCode = hashCode * MULT + node.getURI().hashCode();
    }


    public void visit(SWRLAtomIVariable node) {
        hashCode = 673;
        hashCode = hashCode * MULT + node.getURI().hashCode();
    }


    public void visit(SWRLAtomIndividualObject node) {
        hashCode = 677;
        hashCode = hashCode * MULT + node.getIndividual().hashCode();
    }


    public void visit(SWRLAtomConstantObject node) {
        hashCode = 683;
        hashCode = hashCode * MULT + node.getConstant().hashCode();
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
        hashCode = 793;
        // We don't include the ontology hashcode in the calc of
        // the annotation hashcode because it is mutable, and changing the
        // ontology URI would change the hashCode of the ontology annotation -
        // this would interact badly with the Java set and map implementations
//        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotation().hashCode();
    }


    public void visit(SWRLDifferentFromAtom node) {
        hashCode = 797;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
    }


    public void visit(SWRLSameAsAtom node) {
        hashCode = 817;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
    }
}
