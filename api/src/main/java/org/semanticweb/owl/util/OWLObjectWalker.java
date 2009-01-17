package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;

import java.util.HashSet;
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

    private Set<O> objects;

    private OWLObjectVisitorEx visitor;

    private boolean visitDuplicates;

    public OWLObjectWalker(Set<O> objects) {
        this(objects, true);
    }

    public OWLObjectWalker(Set<O> objects, boolean visitDuplicates) {
        this.objects = new HashSet<O>(objects);
        this.visitDuplicates = visitDuplicates;
    }

    public <E> void walkStructure(OWLObjectVisitorEx<E> visitor) {
        this.visitor = visitor;
        StructureWalker walker = new StructureWalker();
        for (O o : objects) {
            o.accept(walker);
        }
    }


    private class StructureWalker implements OWLObjectVisitor {

        private Set<OWLObject> visited = new HashSet<OWLObject>();

        private void process(OWLObject object) {
            if (visitDuplicates) {
                if (!visited.contains(object)) {
                    visited.add(object);
                    object.accept(visitor);
                }
            } else {
                object.accept(visitor);
            }
        }

        public void visit(IRI iri) {
            process(iri);
        }

        public void visit(OWLOntology ontology) {
            process(ontology);
            for (OWLAxiom ax : ontology.getAxioms()) {
                ax.accept(this);
            }
        }


        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            process(axiom);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            process(axiom);
            axiom.getIndividual().accept(this);
            axiom.getDescription().accept(this);
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            process(axiom);
            axiom.getSubject().accept(this);
            axiom.getProperty().accept(this);
            axiom.getObject().accept(this);
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
            process(axiom);
            axiom.getDomain().accept(this);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
            process(axiom);
            axiom.getRange().accept(this);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            process(axiom);
            axiom.getSubProperty().accept(this);
            axiom.getSuperProperty().accept(this);
        }


        public void visit(OWLDeclaration axiom) {
            process(axiom);
            axiom.getEntity().accept(this);
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
            process(axiom);
            for (OWLIndividual ind : axiom.getIndividuals()) {
                ind.accept(this);
            }
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            process(axiom);
            for (OWLClassExpression desc : axiom.getDescriptions()) {
                desc.accept(this);
            }
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            process(axiom);
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                prop.accept(this);
            }
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            process(axiom);
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                prop.accept(this);
            }
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
            process(axiom);
            axiom.getOWLClass().accept(this);
            for (OWLClassExpression desc : axiom.getDescriptions()) {
                desc.accept(this);
            }
        }


        public void visit(OWLAnnotationAssertionAxiom axiom) {
            process(axiom);
            axiom.getSubject().accept(this);
            axiom.getAnnotation().accept(this);
        }

        public void visit(OWLAnnotationPropertyDomain axiom) {
            process(axiom);
            axiom.getProperty().accept(this);
            axiom.getDomain().accept(this);
        }

        public void visit(OWLAnnotationPropertyRange axiom) {
            process(axiom);
            axiom.getProperty().accept(this);
            axiom.getRange().accept(this);
        }

        public void visit(OWLSubAnnotationPropertyOf axiom) {
            process(axiom);
            axiom.getSubProperty().accept(this);
            axiom.getSuperProperty().accept(this);
        }

        public void visit(OWLAnnotation node) {
            process(node);
            node.getProperty().accept(this);
            node.getValue().accept(this);
        }

        public void visit(OWLEquivalentClassesAxiom axiom) {
            process(axiom);
            for (OWLClassExpression desc : axiom.getDescriptions()) {
                desc.accept(this);
            }
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            process(axiom);
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                prop.accept(this);
            }
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            process(axiom);
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                prop.accept(this);
            }
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            process(axiom);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            process(axiom);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLImportsDeclaration axiom) {
            process(axiom);
        }


        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            process(axiom);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            process(axiom);
            axiom.getFirstProperty().accept(this);
            axiom.getSecondProperty().accept(this);
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            process(axiom);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            process(axiom);
            axiom.getSubject().accept(this);
            axiom.getProperty().accept(this);
            axiom.getObject().accept(this);
        }


        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            process(axiom);
            axiom.getSubject().accept(this);
            axiom.getProperty().accept(this);
            axiom.getObject().accept(this);
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            process(axiom);
            axiom.getSubject().accept(this);
            axiom.getProperty().accept(this);
            axiom.getObject().accept(this);
        }


        public void visit(OWLComplextSubPropertyAxiom axiom) {
            process(axiom);
            for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
                prop.accept(this);
            }
            axiom.getSuperProperty().accept(this);
        }


        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            process(axiom);
            axiom.getDomain().accept(this);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            process(axiom);
            axiom.getProperty().accept(this);
            axiom.getRange().accept(this);
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            process(axiom);
            axiom.getSubProperty().accept(this);
            axiom.getSuperProperty().accept(this);
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            process(axiom);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLSameIndividualsAxiom axiom) {
            process(axiom);
            for (OWLIndividual ind : axiom.getIndividuals()) {
                ind.accept(this);
            }
        }


        public void visit(OWLSubClassOfAxiom axiom) {
            process(axiom);
            axiom.getSubClass().accept(this);
            axiom.getSuperClass().accept(this);
        }


        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            process(axiom);
            axiom.getProperty().accept(this);
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            process(axiom);
            axiom.getProperty().accept(this);
        }


        public void visit(SWRLRule rule) {
            process(rule);
            for (SWRLAtom at : rule.getBody()) {
                at.accept(this);
            }
            for (SWRLAtom at : rule.getHead()) {
                at.accept(this);
            }
        }

        public void visit(OWLHasKeyAxiom axiom) {
            process(axiom);
            axiom.getClassExpression().accept(this);
            for (OWLObjectPropertyExpression prop : axiom.getObjectPropertyExpressions()) {
                prop.accept(this);
            }
            for (OWLDataPropertyExpression prop : axiom.getDataPropertyExpressions()) {
                prop.accept(this);
            }
        }

        public void visit(OWLClass desc) {
            process(desc);
            desc.getIRI().accept(this);
        }


        public void visit(OWLDataAllValuesFrom desc) {
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
        }


        public void visit(OWLDataExactCardinality desc) {
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
        }


        public void visit(OWLDataMaxCardinality desc) {
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
        }


        public void visit(OWLDataMinCardinality desc) {
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
        }


        public void visit(OWLDataSomeValuesFrom desc) {
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
        }


        public void visit(OWLDataValueRestriction desc) {
            process(desc);
            desc.getProperty().accept(this);
            desc.getValue().accept(this);
        }


        public void visit(OWLObjectAllValuesFrom desc) {
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
        }


        public void visit(OWLObjectComplementOf desc) {
            process(desc);
            desc.getOperand().accept(this);
        }


        public void visit(OWLObjectExactCardinality desc) {
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
        }


        public void visit(OWLObjectIntersectionOf desc) {
            process(desc);
            for (OWLClassExpression op : desc.getOperands()) {
                op.accept(this);
            }
        }


        public void visit(OWLObjectMaxCardinality desc) {
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
        }


        public void visit(OWLObjectMinCardinality desc) {
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
        }


        public void visit(OWLObjectOneOf desc) {
            process(desc);
            for (OWLIndividual ind : desc.getIndividuals()) {
                ind.accept(this);
            }
        }


        public void visit(OWLObjectHasSelf desc) {
            process(desc);
            desc.getProperty().accept(this);
        }


        public void visit(OWLObjectSomeValuesFrom desc) {
            process(desc);
            desc.getProperty().accept(this);
            desc.getFiller().accept(this);
        }


        public void visit(OWLObjectUnionOf desc) {
            process(desc);
            for (OWLClassExpression op : desc.getOperands()) {
                op.accept(this);
            }
        }


        public void visit(OWLObjectHasValue desc) {
            process(desc);
            desc.getProperty().accept(this);
            desc.getValue().accept(this);
        }


        public void visit(OWLDataComplementOf node) {
            process(node);
            node.getDataRange().accept(this);
        }


        public void visit(OWLDataOneOf node) {
            process(node);
            for (OWLLiteral con : node.getValues()) {
                con.accept(this);
            }
        }

        public void visit(OWLDataIntersectionOf node) {
            process(node);
            for (OWLDataRange rng : node.getOperands()) {
                rng.accept(this);
            }
        }

        public void visit(OWLDataUnionOf node) {
            process(node);
            for (OWLDataRange rng : node.getOperands()) {
                rng.accept(this);
            }
        }

        public void visit(OWLFacetRestriction node) {
            process(node);
            node.getFacetValue().accept(this);
        }


        public void visit(OWLDatatypeRestriction node) {
            process(node);
            node.getDatatype().accept(this);
            for (OWLFacetRestriction fr : node.getFacetRestrictions()) {
                fr.accept(this);
            }
        }


        public void visit(OWLDatatype node) {
            process(node);
        }


        public void visit(OWLTypedLiteral node) {
            process(node);
            node.getDataType().accept(this);
        }


        public void visit(OWLRDFTextLiteral node) {
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


        public void visit(OWLObjectPropertyInverse property) {
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

        public void visit(SWRLAtomConstantObject node) {
            process(node);
            node.getConstant().accept(this);
        }


        public void visit(SWRLAtomDVariable node) {
            process(node);
        }


        public void visit(SWRLAtomIndividualObject node) {
            process(node);
            node.getIndividual().accept(this);
        }


        public void visit(SWRLAtomIVariable node) {
            process(node);
        }


        public void visit(SWRLBuiltInAtom node) {
            process(node);
            for (SWRLAtomDObject at : node.getArguments()) {
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


        public void visit(SWRLDataValuedPropertyAtom node) {
            process(node);
            node.getPredicate().accept(this);
            node.getFirstArgument().accept(this);
            node.getSecondArgument().accept(this);
        }


        public void visit(SWRLDifferentFromAtom node) {
            process(node);
            node.getPredicate().accept(this);
            node.getFirstArgument().accept(this);
            node.getSecondArgument().accept(this);
        }


        public void visit(SWRLObjectPropertyAtom node) {
            process(node);
            node.getPredicate().accept(this);
            node.getFirstArgument().accept(this);
            node.getSecondArgument().accept(this);
        }


        public void visit(SWRLSameAsAtom node) {
            process(node);
            node.getPredicate().accept(this);
            node.getFirstArgument().accept(this);
            node.getSecondArgument().accept(this);
        }
    }
}
