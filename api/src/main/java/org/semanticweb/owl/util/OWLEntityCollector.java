package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
 * Date: 13-Nov-2006<br><br>
 * <p/>
 * A utiliity class that visits axioms, descriptions etc. and accumulates the named objects
 * that are referred to in those axioms, descriptions etc.  For example, if the collector
 * visited the axiom (propP some C) subClassOf (propQ some D), it would contain the objects
 * propP, C, propQ and D.
 */
public class OWLEntityCollector implements OWLObjectVisitor, SWRLObjectVisitor {

    private Set<OWLEntity> objects;

    private boolean collectClasses = true;

    private boolean collectObjectProperties = true;

    private boolean collectDataProperties = true;

    private boolean collectIndividuals = true;

    private boolean collectDataTypes = true;


    public OWLEntityCollector() {
        objects = new HashSet<OWLEntity>();
    }


    /**
     * Clears all objects that have accumulated during the course
     * of visiting axioms, descriptions etc.
     */
    public void reset() {
        objects.clear();
    }


    public void setCollectClasses(boolean collectClasses) {
        this.collectClasses = collectClasses;
    }


    public void setCollectObjectProperties(boolean collectObjectProperties) {
        this.collectObjectProperties = collectObjectProperties;
    }


    public void setCollectDataProperties(boolean collectDataProperties) {
        this.collectDataProperties = collectDataProperties;
    }


    public void setCollectIndividuals(boolean collectIndividuals) {
        this.collectIndividuals = collectIndividuals;
    }


    public void setCollectDataTypes(boolean collectDataTypes) {
        this.collectDataTypes = collectDataTypes;
    }


    /**
     * Gets the objects that are used by all axioms, descriptions etc. that this
     * collector has visited since it was constructed or reset.
     *
     * @return An unmodifiable set of objects.
     */
    public Set<OWLEntity> getObjects() {
        return Collections.unmodifiableSet(objects);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Axiom Visitor stuff
    //
    //////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLSubClassAxiom axiom) {
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }


    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        for (OWLClassExpression desc : axiom.getDescriptions()) {
            desc.accept(this);
        }
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLImportsDeclaration axiom) {
    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getAnnotation().accept(this);
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        for (OWLIndividual ind : axiom.getIndividuals()) {
            ind.accept(this);
        }
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        for (OWLDataPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
    }


    public void visit(OWLObjectSubPropertyAxiom axiom) {
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        axiom.getOWLClass().accept((OWLEntityVisitor) this);
        for (OWLClassExpression desc : axiom.getDescriptions()) {
            desc.accept(this);
        }
    }


    public void visit(OWLDeclarationAxiom axiom) {
        axiom.getEntity().accept(this);
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        for (OWLDataPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getDescription().accept(this);
        axiom.getIndividual().accept(this);
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        for (OWLClassExpression desc : axiom.getDescriptions()) {
            desc.accept(this);
        }
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
    }


    public void visit(OWLDataSubPropertyAxiom axiom) {
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
    }


    public void visit(OWLSameIndividualsAxiom axiom) {
        for (OWLIndividual ind : axiom.getIndividuals()) {
            ind.accept(this);
        }
    }


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
        for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            prop.accept(this);
        }
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        axiom.getFirstProperty().accept(this);
        axiom.getSecondProperty().accept(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // OWLDescriptionVisitor
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLClass desc) {
        if (collectClasses) {
            objects.add(desc);
        }
    }


    public void visit(OWLObjectIntersectionOf desc) {
        for (OWLClassExpression operand : desc.getOperands()) {
            operand.accept(this);
        }
    }


    public void visit(OWLObjectUnionOf desc) {
        for (OWLClassExpression operand : desc.getOperands()) {
            operand.accept(this);
        }
    }


    public void visit(OWLObjectComplementOf desc) {
        desc.getOperand().accept(this);
    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectAllRestriction desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectValueRestriction desc) {
        desc.getProperty().accept(this);
        desc.getValue().accept(this);
    }


    public void visit(OWLObjectMinCardinalityRestriction desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectExactCardinalityRestriction desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectMaxCardinalityRestriction desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectSelfRestriction desc) {
        desc.getProperty().accept(this);
    }


    public void visit(OWLObjectOneOf desc) {
        for (OWLIndividual ind : desc.getIndividuals()) {
            ind.accept(this);
        }
    }


    public void visit(OWLDataSomeRestriction desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLDataAllRestriction desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLDataValueRestriction desc) {
        desc.getProperty().accept(this);
        desc.getValue().accept(this);
    }


    public void visit(OWLDataMinCardinalityRestriction desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLDataExactCardinalityRestriction desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLDataMaxCardinalityRestriction desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Data visitor
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLDataComplementOf node) {
        node.getDataRange().accept(this);
    }


    public void visit(OWLDataOneOf node) {
        for (OWLLiteral val : node.getValues()) {
            val.accept(this);
        }
    }


    public void visit(OWLDataRangeRestriction node) {
        node.getDataRange().accept(this);
        for (OWLDataRangeFacetRestriction facetRestriction : node.getFacetRestrictions()) {
            facetRestriction.accept(this);
        }
    }


    public void visit(OWLDataRangeFacetRestriction node) {
        node.getFacetValue().accept(this);
    }


    public void visit(OWLTypedLiteral node) {
        node.getDataType().accept((OWLEntityVisitor) this);
    }


    public void visit(OWLRDFTextLiteral node) {
        // No objects here - only a string
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Property expression visitor
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLObjectPropertyInverse expression) {
        expression.getInverse().accept(this);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Entity  visitor
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLObjectProperty property) {
        if (collectObjectProperties) {
            objects.add(property);
        }
    }


    public void visit(OWLDataProperty property) {
        if (collectDataProperties) {
            objects.add(property);
        }
    }


    public void visit(OWLIndividual individual) {
        if (collectIndividuals) {
            objects.add(individual);
        }
    }


    public void visit(OWLDatatype datatype) {
        if (collectDataTypes) {
            objects.add(datatype);
        }
    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getAnnotation().accept(this);
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
        axiom.getAnnotation().accept(this);
    }


    public void visit(OWLOntology ontology) {
        objects.addAll(ontology.getReferencedEntities());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Entity  visitor
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLConstantAnnotation annotation) {
        // Might have a datatype on the constant
        annotation.getAnnotationValue().accept(this);
    }


    public void visit(OWLObjectAnnotation annotation) {
        annotation.getAnnotationValue().accept(this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // SWRL Object Visitor
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(SWRLRule rule) {
        for (SWRLAtom atom : rule.getBody()) {
            atom.accept(this);
        }
        for (SWRLAtom atom : rule.getHead()) {
            atom.accept(this);
        }
    }


    public void visit(SWRLClassAtom node) {
        node.getArgument().accept(this);
        node.getPredicate().accept(this);
    }


    public void visit(SWRLDataRangeAtom node) {
        node.getArgument().accept(this);
        node.getPredicate().accept(this);
    }


    public void visit(SWRLObjectPropertyAtom node) {
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }


    public void visit(SWRLDataValuedPropertyAtom node) {
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }


    public void visit(SWRLBuiltInAtom node) {
        for (SWRLAtomObject atomObj : node.getAllArguments()) {
            atomObj.accept(this);
        }
    }


    public void visit(SWRLAtomDVariable node) {
    }


    public void visit(SWRLAtomIVariable node) {
    }


    public void visit(SWRLAtomIndividualObject node) {
        node.getIndividual().accept(this);
    }


    public void visit(SWRLAtomConstantObject node) {
        node.getConstant().accept(this);
    }


    public void visit(SWRLDifferentFromAtom node) {
        node.getFirstArgument().accept(this);
    }


    public void visit(SWRLSameAsAtom node) {
        node.getSecondArgument().accept(this);
    }
}
