package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
 * Date: 04-Feb-2008<br><br>
 */
public class OWLObjectComponentCollector implements OWLObjectVisitor {

    private Set<OWLObject> result;


    public OWLObjectComponentCollector() {
        result = new HashSet<OWLObject>();
    }


    /**
     * A convenience method that obtains the components of an
     * OWL object. Note that by definition, the components of the
     * object include the object itself.
     * @param object The object whose components are to be obtained.
     * @return The component of the specified object.
     */
    public Set<OWLObject> getComponents(OWLObject object) {
        result.clear();
        object.accept(this);
        return new HashSet<OWLObject>(result);
    }

    public Set<OWLObject> getResult() {
        return Collections.unmodifiableSet(result);
    }

    private void process(Set<? extends  OWLObject> objects) {
        for(OWLObject obj : objects) {
            obj.accept(this);
        }
    }


    /**
     * Handles an object.  By default, this method adds the object
     * to the result collection.  This method may be overriden to
     * do something else.
     * @param obj The object being added.
     */
    protected void handleObject(OWLObject obj) {
        result.add(obj);
    }

    public void visit(OWLOntology ontology) {
        process(ontology.getAxioms());
    }


    public void visit(OWLClass cls) {
        handleObject(cls);
    }


    public void visit(OWLObjectProperty property) {
        handleObject(property);
    }


    public void visit(OWLObjectPropertyInverse property) {
        handleObject(property);
        property.getInverse().accept(this);
    }


    public void visit(OWLDataProperty property) {
        handleObject(property);
    }


    public void visit(OWLIndividual individual) {
        handleObject(individual);
    }


    public void visit(OWLDataType dataType) {
        handleObject(dataType);
    }


    public void visit(OWLObjectIntersectionOf desc) {
        handleObject(desc);
        for (OWLDescription op : desc.getOperands()) {
            op.accept(this);
        }
    }


    public void visit(OWLObjectUnionOf desc) {
        handleObject(desc);
        process(desc.getOperands());
    }


    public void visit(OWLObjectComplementOf desc) {
        handleObject(desc);
        desc.getOperand().accept(this);
    }


    public void visit(OWLObjectSomeRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectAllRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectValueRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
        desc.getValue().accept(this);
    }


    public void visit(OWLObjectMinCardinalityRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectExactCardinalityRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectMaxCardinalityRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectSelfRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLObjectOneOf desc) {
        handleObject(desc);
        process(desc.getIndividuals());
    }


    public void visit(OWLDataSomeRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataAllRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataValueRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataMinCardinalityRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataExactCardinalityRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataMaxCardinalityRestriction desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLSubClassAxiom axiom) {
        handleObject(axiom);
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }


    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getDescriptions());
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLImportsDeclaration axiom) {
        handleObject(axiom);
    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getAnnotation().accept(this);
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        handleObject(axiom);
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        handleObject(axiom);
        process(axiom.getIndividuals());
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        handleObject(axiom);
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLObjectSubPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        handleObject(axiom);
        axiom.getOWLClass().accept(this);
        process(axiom.getDescriptions());
    }


    public void visit(OWLDeclarationAxiom axiom) {
        handleObject(axiom);
        axiom.getEntity().accept(this);
    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getAnnotation().accept(this);
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
        handleObject(axiom);
        axiom.getAnnotation().accept(this);
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        handleObject(axiom);
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getDescription().accept(this);
        axiom.getIndividual().accept(this);
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getDescriptions());
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLDataSubPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLSameIndividualsAxiom axiom) {
        handleObject(axiom);
        process(axiom.getIndividuals());
    }


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
        handleObject(axiom);
        for(OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            prop.accept(this);
        }
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }


    public void visit(SWRLRule node) {
        handleObject(node);
        process(node.getBody());
        process(node.getHead());
    }


    public void visit(OWLDataComplementOf node) {
        handleObject(node);
        node.getDataRange().accept(this);
    }


    public void visit(OWLDataOneOf node) {
        handleObject(node);
        process(node.getValues());
    }


    public void visit(OWLDataRangeRestriction node) {
        handleObject(node);
        node.getDataRange().accept(this);
        process(node.getFacetRestrictions());
    }


    public void visit(OWLTypedConstant node) {
        handleObject(node);
        node.getDataType().accept(this);
    }


    public void visit(OWLUntypedConstant node) {
        handleObject(node);
    }


    public void visit(OWLDataRangeFacetRestriction node) {
        handleObject(node);
        node.getFacetValue().accept(this);
    }


    public void visit(OWLObjectAnnotation node) {
        handleObject(node);
        node.getAnnotationValue().accept(this);
    }


    public void visit(OWLConstantAnnotation node) {
        handleObject(node);
        node.getAnnotationValue().accept(this);
    }


    public void visit(SWRLClassAtom node) {
        handleObject(node);
        node.getPredicate().accept(this);
        node.getArgument().accept(this);
    }


    public void visit(SWRLDataRangeAtom node) {
        handleObject(node);
        node.getPredicate().accept(this);
        node.getArgument().accept(this);
    }


    public void visit(SWRLObjectPropertyAtom node) {
        handleObject(node);
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }


    public void visit(SWRLDataValuedPropertyAtom node) {
        handleObject(node);
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }


    public void visit(SWRLBuiltInAtom node) {
        handleObject(node);
        for(SWRLAtomDObject obj : node.getArguments()) {
            obj.accept(this);
        }
    }


    public void visit(SWRLAtomDVariable node) {
        handleObject(node);
    }


    public void visit(SWRLAtomIVariable node) {
        handleObject(node);
    }


    public void visit(SWRLAtomIndividualObject node) {
        handleObject(node);
        node.getIndividual().accept(this);
    }


    public void visit(SWRLAtomConstantObject node) {
        handleObject(node);
        node.getConstant().accept(this);
    }


    public void visit(SWRLSameAsAtom node) {
        handleObject(node);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }


    public void visit(SWRLDifferentFromAtom node) {
        handleObject(node);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }
}
