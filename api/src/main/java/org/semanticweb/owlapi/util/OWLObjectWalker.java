package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 29-Jul-2008<br><br>
 */
public class OWLObjectWalker<O extends OWLObject> {

    private OWLOntology ontology;

    private Set<O> objects;

    private OWLObjectVisitorEx visitor;

    private boolean visitDuplicates;

    private OWLAxiom axiom;

    private OWLAnnotation annotation;

    private List<OWLClassExpression> classExpressionPath = new ArrayList<OWLClassExpression>();

    private List<OWLDataRange> dataRangePath = new ArrayList<OWLDataRange>();
    

    public OWLObjectWalker(Set<O> objects) {
        this(objects, true);
    }

    public OWLObjectWalker(Set<O> objects, boolean visitDuplicates) {
        this.objects = new HashSet<O>(objects);
        this.visitDuplicates = visitDuplicates;
    }

    public void walkStructure(OWLObjectVisitorEx visitor) {
        this.visitor = visitor;
        StructureWalker walker = new StructureWalker();
        for (O o : objects) {
            o.accept(walker);
        }
    }

    /**
     * Gets the last ontology to be visited.
     * @return The last ontology to be visited
     */
    public OWLOntology getOntology() {
        return ontology;
    }

    /**
     * Gets the last axiom to be visited.
     * @return The last axiom to be visited, or <code>null</code> if an axiom has not be visited
     */
    public OWLAxiom getAxiom() {
        return axiom;
    }

    /**
     * Gets the last annotation to be visited.
     * @return The last annotation to be visited (may be <code>null</code>)
     */
    public OWLAnnotation getAnnotation() {
        return annotation;
    }

    /**
     * Gets the current class expression path.  The current class expression path is a list of class expressions
     * that represents the containing expressions for the current class expressions.  The first item in the path (list)
     * is the root class expression that was visited.  For 0 < i < pathLength, the item at index i+1
     * is a direct sub-expression of the item at index i.  The last item in the path is the current class expression
     * being visited.
     * @return A list of class expressions that represents the path of class expressions, with the root of
     *         the class expression being the first element in the list.
     */
    public List<OWLClassExpression> getClassExpressionPath() {
        return new ArrayList<OWLClassExpression>(classExpressionPath);
    }

    /**
     * Determines if a particular class expression is the first (or root) class expression in the
     * current class expression path
     * @param classExpression The class expression
     * @return <code>true</code> if the specified class expression is the first class expression
     * in the current class expression path, otherwise <code>false</code> (<code>false</code> if the
     * path is empty)
     */
    public boolean isFirstClassExpressionInPath(OWLClassExpression classExpression) {
        if(classExpressionPath.isEmpty()) {
            return false;
        }
        return classExpressionPath.get(0).equals(classExpression);
    }

    /**
     * Pushes a class expression onto the class expression path
     * @param ce The class expression to be pushed onto the path
     */
    private void pushClassExpression(OWLClassExpression ce) {
        classExpressionPath.add(ce);
    }

    /**
     * Pops a class expression from the class expression path.  If the path
     * is empty then this method has no effect.
     */
    private void popClassExpression() {
        if (!classExpressionPath.isEmpty()) {
            classExpressionPath.remove(classExpressionPath.size() - 1);
        }
    }

    /**
     * Gets the current data range path.  The current data range path is a list of data ranges
     * that represents the containing expressions for the current data ranges.  The first item in the path (list)
     * is the root data range that was visited.  For 0 < i < pathLength, the item at index i+1
     * is a direct sub-expression of the item at index i.  The last item in the path is the current data range
     * being visited.
     * @return A list of data ranges that represents the path of data ranges, with the root of
     *         the data range being the first element in the list.
     */
    public List<OWLDataRange> getDataRangePath() {
        return new ArrayList<OWLDataRange>(dataRangePath);
    }

    /**
     * Pushes a data range on to the data range path
     * @param dr The data range to be pushed onto the path
     */
    private void pushDataRange(OWLDataRange dr) {
        dataRangePath.add(dr);
    }

    /**
     * Pops a data range from the data range expression path.  If the path
     * is empty then this method has no effect.
     */
    private void popDataRange() {
        if (!dataRangePath.isEmpty()) {
            dataRangePath.remove(dataRangePath.size() - 1);
        }
    }


    private class StructureWalker implements OWLObjectVisitor {

        private Set<OWLObject> visited = new HashSet<OWLObject>();

        private void process(OWLObject object) {
            if (!visitDuplicates) {
                if (!visited.contains(object)) {
                    visited.add(object);
                    object.accept(visitor);
                }
            }
            else {
                object.accept(visitor);
            }
        }

        public void visit(IRI iri) {
            process(iri);
        }

        public void visit(OWLOntology ontology) {
            OWLObjectWalker.this.ontology = ontology;
            OWLObjectWalker.this.axiom = null;
            process(ontology);
            for(OWLAnnotation anno : ontology.getAnnotations()) {
                anno.accept(this);
            }
            for (OWLAxiom ax : ontology.getAxioms()) {
                ax.accept(this);
            }
        }


        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getIndividual().accept(this);
            axiom.getClassExpression().accept(this);
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getSubject().accept(this);
            axiom.getProperty().accept(this);
            axiom.getObject().accept(this);
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getDomain().accept(this);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getRange().accept(this);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getSubProperty().accept(this);
            axiom.getSuperProperty().accept(this);
        }


        public void visit(OWLDeclarationAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getEntity().accept(this);
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            for (OWLIndividual ind : axiom.getIndividuals()) {
                ind.accept(this);
            }
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                desc.accept(this);
            }
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                prop.accept(this);
            }
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                prop.accept(this);
            }
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getOWLClass().accept(this);
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                desc.accept(this);
            }
        }


        public void visit(OWLAnnotationAssertionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getSubject().accept(this);
            axiom.getAnnotation().accept(this);
        }

        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getProperty().accept(this);
            axiom.getDomain().accept(this);
        }

        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getProperty().accept(this);
            axiom.getRange().accept(this);
        }

        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getSubProperty().accept(this);
            axiom.getSuperProperty().accept(this);
        }

        public void visit(OWLAnnotation node) {
            process(node);
            annotation = node;
            node.getProperty().accept(this);
            node.getValue().accept(this);
        }

        public void visit(OWLEquivalentClassesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                desc.accept(this);
            }
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                prop.accept(this);
            }
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                prop.accept(this);
            }
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getProperty().accept(this);
        }

        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getFirstProperty().accept(this);
            axiom.getSecondProperty().accept(this);
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getSubject().accept(this);
            axiom.getProperty().accept(this);
            axiom.getObject().accept(this);
        }


        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getSubject().accept(this);
            axiom.getProperty().accept(this);
            axiom.getObject().accept(this);
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getSubject().accept(this);
            axiom.getProperty().accept(this);
            axiom.getObject().accept(this);
        }


        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
                prop.accept(this);
            }
            axiom.getSuperProperty().accept(this);
        }


        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getDomain().accept(this);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getProperty().accept(this);
            axiom.getRange().accept(this);
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getSubProperty().accept(this);
            axiom.getSuperProperty().accept(this);
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(OWLSameIndividualAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            for (OWLIndividual ind : axiom.getIndividuals()) {
                ind.accept(this);
            }
        }


        public void visit(OWLSubClassOfAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            // -ve polarity
            axiom.getSubClass().accept(this);
            // +ve polarity
            axiom.getSuperClass().accept(this);
        }


        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getProperty().accept(this);
        }


        public void visit(SWRLRule rule) {
            process(rule);
            OWLObjectWalker.this.axiom = rule;
            for (SWRLAtom at : rule.getBody()) {
                at.accept(this);
            }
            for (SWRLAtom at : rule.getHead()) {
                at.accept(this);
            }
        }

        public void visit(OWLHasKeyAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getClassExpression().accept(this);
            for (OWLObjectPropertyExpression prop : axiom.getObjectPropertyExpressions()) {
                prop.accept(this);
            }
            for (OWLDataPropertyExpression prop : axiom.getDataPropertyExpressions()) {
                prop.accept(this);
            }
        }

        public void visit(OWLClass desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getIRI().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataAllValuesFrom desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataExactCardinality desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataMaxCardinality desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataMinCardinality desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataSomeValuesFrom desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataHasValue desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getValue().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectAllValuesFrom desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectComplementOf desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getOperand().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectExactCardinality desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectIntersectionOf desc) {
            pushClassExpression(desc);
            process(desc);

            for (OWLClassExpression op : desc.getOperands()) {
                op.accept(this);
            }
            popClassExpression();
        }


        public void visit(OWLObjectMaxCardinality desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectMinCardinality desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectOneOf desc) {
            pushClassExpression(desc);
            process(desc);
            for (OWLIndividual ind : desc.getIndividuals()) {
                ind.accept(this);
            }
            popClassExpression();
        }


        public void visit(OWLObjectHasSelf desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectSomeValuesFrom desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
            popClassExpression();
        }


        public void visit(OWLObjectUnionOf desc) {
            pushClassExpression(desc);
            process(desc);
            for (OWLClassExpression op : desc.getOperands()) {
                op.accept(this);
            }
            popClassExpression();
        }


        public void visit(OWLObjectHasValue desc) {
            pushClassExpression(desc);
            process(desc);
            desc.getProperty().accept(this);
            desc.getValue().accept(this);
            popClassExpression();
        }


        public void visit(OWLDataComplementOf node) {
            pushDataRange(node);
            process(node);
            node.getDataRange().accept(this);
            popDataRange();
        }


        public void visit(OWLDataOneOf node) {
            pushDataRange(node);
            process(node);
            for (OWLLiteral con : node.getValues()) {
                con.accept(this);
            }
            popDataRange();
        }

        public void visit(OWLDataIntersectionOf node) {
            pushDataRange(node);
            process(node);
            for (OWLDataRange rng : node.getOperands()) {
                rng.accept(this);
            }
            popDataRange();
        }

        public void visit(OWLDataUnionOf node) {
            pushDataRange(node);
            process(node);
            for (OWLDataRange rng : node.getOperands()) {
                rng.accept(this);
            }
            popDataRange();
        }

        public void visit(OWLFacetRestriction node) {
            process(node);
            node.getFacetValue().accept(this);
        }


        public void visit(OWLDatatypeRestriction node) {
            pushDataRange(node);
            process(node);
            node.getDatatype().accept(this);
            for (OWLFacetRestriction fr : node.getFacetRestrictions()) {
                fr.accept(this);
            }
            popDataRange();
        }


        public void visit(OWLDatatype node) {
            pushDataRange(node);
            process(node);
            popDataRange();
        }


        public void visit(OWLTypedLiteral node) {
            process(node);
            node.getDatatype().accept(this);
            popDataRange();
        }


        public void visit(OWLStringLiteral node) {
            process(node);
        }

        public void visit(OWLAnnotationProperty property) {
            process(property);
            property.getIRI().accept(this);
        }

        public void visit(OWLDataProperty property) {
            process(property);
            property.getIRI().accept(this);
        }


        public void visit(OWLObjectProperty property) {
            process(property);
            property.getIRI().accept(this);
        }


        public void visit(OWLObjectInverseOf property) {
            process(property);
            property.getInverse().accept(this);
        }


        public void visit(OWLNamedIndividual individual) {
            process(individual);
            individual.getIRI().accept(this);
        }

        public void visit(OWLAnonymousIndividual individual) {
            process(individual);
        }

        public void visit(SWRLLiteralArgument node) {
            process(node);
            node.getLiteral().accept(this);
        }


        public void visit(SWRLVariable node) {
            process(node);
        }


        public void visit(SWRLIndividualArgument node) {
            process(node);
            node.getIndividual().accept(this);
        }


        public void visit(SWRLBuiltInAtom node) {
            process(node);
            for (SWRLDArgument at : node.getArguments()) {
                at.accept(this);
            }
        }


        public void visit(SWRLClassAtom node) {
            process(node);
            node.getArgument().accept(this);
            node.getPredicate().accept(this);
        }


        public void visit(SWRLDataRangeAtom node) {
            process(node);
            node.getArgument().accept(this);
            node.getPredicate().accept(this);
        }


        public void visit(SWRLDataPropertyAtom node) {
            process(node);
            node.getPredicate().accept(this);
            node.getFirstArgument().accept(this);
            node.getSecondArgument().accept(this);
        }


        public void visit(SWRLDifferentIndividualsAtom node) {
            process(node);
            node.getFirstArgument().accept(this);
            node.getSecondArgument().accept(this);
        }


        public void visit(SWRLObjectPropertyAtom node) {
            process(node);
            node.getPredicate().accept(this);
            node.getFirstArgument().accept(this);
            node.getSecondArgument().accept(this);
        }


        public void visit(SWRLSameIndividualAtom node) {
            process(node);
            node.getFirstArgument().accept(this);
            node.getSecondArgument().accept(this);
        }


        public void visit(OWLDatatypeDefinitionAxiom axiom) {
            process(axiom);
            OWLObjectWalker.this.axiom = axiom;
            axiom.getDatatype().accept(this);
            axiom.getDataRange().accept(this);
        }
    }
}
