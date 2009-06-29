package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;

import java.util.*;
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
 * 01-Aug-2008<br><br>
 */
public class StructuralTransformation {

    private OWLDataFactory df;

    private int nameCounter = 0;

    private Set<OWLEntity> signature;

    public StructuralTransformation(OWLDataFactory dataFactory) {
        this.df = dataFactory;
        signature = new HashSet<OWLEntity>();
    }


    private OWLClass createNewName() {
        OWLClass cls = df.getOWLClass(IRI.create("http://www.semanticweb.org/ontology#X" + nameCounter));
        nameCounter++;
        return cls;
    }


    public Set<OWLAxiom> getTransformedAxioms(Set<OWLAxiom> axioms) {
        signature.clear();
        for (OWLAxiom ax : axioms) {
            signature.addAll(ax.getSignature());
        }
        AxiomRewriter rewriter = new AxiomRewriter();
        Set<OWLAxiom> transformedAxioms = new HashSet<OWLAxiom>();
        for (OWLAxiom ax : axioms) {
            for (OWLAxiom transAx : ax.accept(rewriter)) {
                if (transAx instanceof OWLSubClassOfAxiom) {
                    AxiomFlattener flattener = new AxiomFlattener(df, ((OWLSubClassOfAxiom) transAx).getSuperClass());
                    Set<OWLAxiom> flattenedAxioms = flattener.getAxioms();
                    if (!flattenedAxioms.isEmpty()) {
                        transformedAxioms.addAll(flattenedAxioms);
                    } else {
                        transformedAxioms.add(transAx);
                    }
                } else {
                    transformedAxioms.add(transAx);
                }
            }
        }
        return transformedAxioms;
    }


    private class AxiomFlattener implements OWLClassExpressionVisitorEx<OWLClassExpression> {

        private OWLDataFactory df;

        private Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

        private OWLClassExpression rhs;

//        private OWLClass lefthandSide;


        public AxiomFlattener(OWLDataFactory df, OWLClassExpression rhs) {
            this.df = df;
//            this.lefthandSide = lhs;
            this.rhs = rhs;
        }

        private OWLSubClassOfAxiom getSCA(OWLClass lhs, OWLClassExpression rhs) {
            return df.getOWLSubClassOfAxiom(lhs, rhs);
        }

        public Set<OWLAxiom> getAxioms() {
            axioms.clear();
            OWLClass lhs = df.getOWLThing();
            OWLClassExpression rhs2 = rhs.accept(this);
            axioms.add(getSCA(lhs, rhs2));
            return axioms;
        }


        public OWLClassExpression visit(OWLClass desc) {
            return desc;
        }


        public OWLClassExpression visit(OWLDataAllValuesFrom desc) {
            return desc;
        }


        public OWLClassExpression visit(OWLDataExactCardinality desc) {
            return desc;
        }


        public OWLClassExpression visit(OWLDataMaxCardinality desc) {
            return desc;
        }


        public OWLClassExpression visit(OWLDataMinCardinality desc) {
            return desc;
        }


        public OWLClassExpression visit(OWLDataSomeValuesFrom desc) {
            return desc;
        }


        public OWLClassExpression visit(OWLDataHasValue desc) {
            return desc;
        }


        public OWLClassExpression visit(OWLObjectAllValuesFrom desc) {
            if (signature.containsAll(desc.getFiller().getSignature())) {
                OWLClass name = createNewName();
                OWLClassExpression rhs = desc.getFiller().accept(this);
                axioms.add(getSCA(name, rhs));
                return df.getOWLObjectAllValuesFrom(desc.getProperty(), name);
            } else {
                return desc;
            }
        }


        public OWLClassExpression visit(OWLObjectComplementOf desc) {
            // Should be a literal
            if (desc.getOperand().isAnonymous()) {
                throw new IllegalStateException("Negation of arbitrary class expressions not allowed");
            }
            return desc;
        }


        public OWLClassExpression visit(OWLObjectExactCardinality desc) {
            if (signature.containsAll(desc.getFiller().getSignature())) {
                OWLClass name = createNewName();
                OWLClassExpression rhs = desc.getFiller().accept(this);
                axioms.add(getSCA(name, rhs));
                return df.getOWLObjectExactCardinality(desc.getCardinality(), desc.getProperty(), name);
            } else {
                return desc;
            }
        }


        public OWLClassExpression visit(OWLObjectIntersectionOf desc) {
            OWLClass name = createNewName();
            for (OWLClassExpression op : desc.getOperands()) {
                OWLClassExpression flatOp = op.accept(this);
                axioms.add(getSCA(name, flatOp));
            }
            return name;
        }


        public OWLClassExpression visit(OWLObjectMaxCardinality desc) {
            if (signature.containsAll(desc.getFiller().getSignature())) {
                OWLClass name = createNewName();
                OWLClassExpression rhs = desc.getFiller().accept(this);
                axioms.add(getSCA(name, rhs));
                return df.getOWLObjectMaxCardinality(desc.getCardinality(), desc.getProperty(), name);
            } else {
                return desc;
            }
        }


        public OWLClassExpression visit(OWLObjectMinCardinality desc) {
            if (signature.containsAll(desc.getFiller().getSignature())) {
                OWLClass name = createNewName();
                OWLClassExpression rhs = desc.getFiller().accept(this);
                axioms.add(getSCA(name, rhs));
                return df.getOWLObjectMinCardinality(desc.getCardinality(), desc.getProperty(), name);
            } else {
                return desc;
            }
        }


        public OWLClassExpression visit(OWLObjectOneOf desc) {
            if (desc.getIndividuals().size() > 1) {
                throw new IllegalStateException("ObjectOneOf with more than one individual!");
            }
            return desc;
        }


        public OWLClassExpression visit(OWLObjectHasSelf desc) {
            return desc;
        }


        public OWLClassExpression visit(OWLObjectSomeValuesFrom desc) {
            if (desc.getFiller().isAnonymous()) {
                OWLClass name = createNewName();
                OWLClassExpression rhs = desc.getFiller().accept(this);
                axioms.add(getSCA(name, rhs));
                return df.getOWLObjectSomeValuesFrom(desc.getProperty(), name);
            } else {
                return desc;
            }
        }


        public OWLClassExpression visit(OWLObjectUnionOf desc) {
            Set<OWLClassExpression> descs = new HashSet<OWLClassExpression>();
            for (OWLClassExpression op : desc.getOperands()) {
//                if(!op.isClassExpressionLiteral()) {
                OWLClassExpression flatOp = op.accept(this);
                if (flatOp.isAnonymous() || signature.contains(flatOp.asOWLClass())) {
                    OWLClass name = createNewName();
                    descs.add(name);
                    axioms.add(df.getOWLSubClassOfAxiom(name, flatOp));
                } else {
                    descs.add(flatOp);
                }
//                }
//                else {
//                    OWLClass name = createNewName();
//                    descs.add(name);
//                    axioms.add(df.getOWLSubClassOfAxiom(name, op));
//                }
            }
            return df.getOWLObjectUnionOf(descs);
        }


        public OWLClassExpression visit(OWLObjectHasValue desc) {
            return desc;
        }
    }


    /**
     * Rewrites axioms into GCIs.
     * <p/>
     * For example:  SubClassOf(A, C) becomes SubClassOf(TOP, not(A) or C)
     */
    private class AxiomRewriter implements OWLAxiomVisitorEx<Set<OWLAxiom>> {


        private Set<OWLAxiom> subClassOf(OWLClassExpression sub, OWLClassExpression sup) {
            return Collections.singleton((OWLAxiom) df.getOWLSubClassOfAxiom(df.getOWLThing(),
                    df.getOWLObjectUnionOf(df.getOWLObjectComplementOf(
                            sub), sup).getNNF()));
        }


        private Set<OWLAxiom> toSet(OWLAxiom ax) {
            return Collections.singleton(ax);
        }


        public Set<OWLAxiom> visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLClassAssertionAxiom axiom) {
            return subClassOf(df.getOWLObjectOneOf(axiom.getIndividual()), axiom.getClassExpression());
        }


        public Set<OWLAxiom> visit(OWLDataPropertyAssertionAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLDataPropertyDomainAxiom axiom) {
            return subClassOf(df.getOWLDataSomeValuesFrom(axiom.getProperty(), df.getTopDatatype()),
                    axiom.getDomain());
        }


        public Set<OWLAxiom> visit(OWLDataPropertyRangeAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                    df.getOWLDataAllValuesFrom(axiom.getProperty(), axiom.getRange())));
        }


        public Set<OWLAxiom> visit(OWLSubDataPropertyOfAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLDeclarationAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLDifferentIndividualsAxiom axiom) {
            // Explode into pairwise nominals?
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLIndividual> individuals = new ArrayList<OWLIndividual>(axiom.getIndividuals());
            for (int i = 0; i < individuals.size(); i++) {
                for (int j = i + 1; j < individuals.size(); j++) {
                    axioms.addAll(subClassOf(df.getOWLObjectOneOf(individuals.get(i)),
                            df.getOWLObjectOneOf(individuals.get(j))));
                }
            }
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLDisjointClassesAxiom axiom) {
            // Explode
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLClassExpression> classExpressions = new ArrayList<OWLClassExpression>(axiom.getClassExpressions());
            for (int i = 0; i < classExpressions.size(); i++) {
                for (int j = i + 1; j < classExpressions.size(); j++) {
                    axioms.addAll(subClassOf(classExpressions.get(i), classExpressions.get(j)));
                }
            }
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLDisjointDataPropertiesAxiom axiom) {
            // Explode
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLDataPropertyExpression> props = new ArrayList<OWLDataPropertyExpression>(axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getOWLDisjointDataPropertiesAxiom(props.get(i), props.get(j)));
                }
            }
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLDisjointObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLObjectPropertyExpression> props = new ArrayList<OWLObjectPropertyExpression>(axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getOWLDisjointObjectPropertiesAxiom(props.get(i), props.get(j)));
                }
            }
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLDisjointUnionAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            axioms.addAll(df.getOWLEquivalentClassesAxiom(axiom.getOWLClass(),
                    df.getOWLObjectUnionOf(axiom.getClassExpressions())).accept(this));
            axioms.addAll(df.getOWLDisjointClassesAxiom(axiom.getClassExpressions()).accept(this));
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLAnnotationAssertionAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLEquivalentClassesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLClassExpression> classExpressions = new ArrayList<OWLClassExpression>(axiom.getClassExpressions());
            for (int i = 0; i < classExpressions.size(); i++) {
                for (int j = i + 1; j < classExpressions.size(); j++) {
                    axioms.addAll(subClassOf(classExpressions.get(i), classExpressions.get(j)));
                    axioms.addAll(subClassOf(classExpressions.get(j), classExpressions.get(i)));
                }
            }
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLEquivalentDataPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLDataPropertyExpression> props = new ArrayList<OWLDataPropertyExpression>(axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getOWLDisjointDataPropertiesAxiom(props.get(i), props.get(j)));
                    axioms.add(df.getOWLDisjointDataPropertiesAxiom(props.get(j), props.get(i)));
                }
            }
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLObjectPropertyExpression> props = new ArrayList<OWLObjectPropertyExpression>(axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getOWLDisjointObjectPropertiesAxiom(props.get(i), props.get(j)));
                    axioms.add(df.getOWLDisjointObjectPropertiesAxiom(props.get(j), props.get(i)));
                }
            }
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLFunctionalDataPropertyAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                    df.getOWLDataMaxCardinality(1, axiom.getProperty())));
        }


        public Set<OWLAxiom> visit(OWLFunctionalObjectPropertyAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                    df.getOWLObjectMaxCardinality(1, axiom.getProperty())));
        }


        public Set<OWLAxiom> visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                    df.getOWLObjectMaxCardinality(1, axiom.getProperty().getInverseProperty())));
        }


        public Set<OWLAxiom> visit(OWLInverseObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            axioms.add(df.getOWLSubObjectPropertyOfAxiom(axiom.getFirstProperty(),
                    axiom.getSecondProperty().getInverseProperty()));
            axioms.add(df.getOWLSubObjectPropertyOfAxiom(axiom.getSecondProperty(),
                    axiom.getFirstProperty().getInverseProperty()));
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            return subClassOf(df.getOWLObjectOneOf(axiom.getSubject()),
                    df.getOWLDataAllValuesFrom(axiom.getProperty(),
                            df.getOWLDataComplementOf(df.getOWLDataOneOf(axiom.getObject()))));
        }


        public Set<OWLAxiom> visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            return subClassOf(df.getOWLObjectOneOf(axiom.getSubject()),
                    df.getOWLObjectAllValuesFrom(axiom.getProperty(),
                            df.getOWLObjectComplementOf(df.getOWLObjectOneOf(axiom.getObject()))));
        }


        public Set<OWLAxiom> visit(OWLObjectPropertyAssertionAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLSubPropertyChainOfAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLObjectPropertyDomainAxiom axiom) {
            return subClassOf(df.getOWLObjectSomeValuesFrom(axiom.getProperty(), df.getOWLThing()), axiom.getDomain());
        }


        public Set<OWLAxiom> visit(OWLObjectPropertyRangeAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                    df.getOWLObjectAllValuesFrom(axiom.getProperty(), axiom.getRange())));
        }


        public Set<OWLAxiom> visit(OWLSubObjectPropertyOfAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLReflexiveObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLSameIndividualAxiom axiom) {
            return null;
        }


        public Set<OWLAxiom> visit(OWLSubClassOfAxiom axiom) {
            return subClassOf(axiom.getSubClass(), axiom.getSuperClass());
        }


        public Set<OWLAxiom> visit(OWLSymmetricObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLTransitiveObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(SWRLRule rule) {
            return toSet(rule);
        }

        public Set<OWLAxiom> visit(OWLHasKeyAxiom axiom) {
            return toSet(axiom);
        }

        public Set<OWLAxiom> visit(OWLAnnotationPropertyDomainAxiom axiom) {
            return toSet(axiom);
        }

        public Set<OWLAxiom> visit(OWLAnnotationPropertyRangeAxiom axiom) {
            return toSet(axiom);
        }

        public Set<OWLAxiom> visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLDatatypeDefinitionAxiom axiom) {
            return toSet(axiom);
        }
    }
}
