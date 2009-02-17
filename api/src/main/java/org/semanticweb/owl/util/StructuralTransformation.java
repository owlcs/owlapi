package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;

import java.net.URI;
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
        OWLClass cls = df.getOWLClass(URI.create("http://www.semanticweb.org/ontology#X" + nameCounter));
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
            return df.getSubClassOf(lhs, rhs);
        }

        public Set<OWLAxiom> getAxioms() {
            axioms.clear();
            OWLClass lhs = df.getThing();
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
                return df.getObjectAllValuesFrom(desc.getProperty(), name);
            } else {
                return desc;
            }
        }


        public OWLClassExpression visit(OWLObjectComplementOf desc) {
            // Should be a literal
            if (desc.getOperand().isAnonymous()) {
                throw new IllegalStateException("Negation of arbitrary descriptions not allowed");
            }
            return desc;
        }


        public OWLClassExpression visit(OWLObjectExactCardinality desc) {
            if (signature.containsAll(desc.getFiller().getSignature())) {
                OWLClass name = createNewName();
                OWLClassExpression rhs = desc.getFiller().accept(this);
                axioms.add(getSCA(name, rhs));
                return df.getObjectExactCardinality(desc.getProperty(), desc.getCardinality(), name);
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
                return df.getObjectMaxCardinality(desc.getProperty(), desc.getCardinality(), name);
            } else {
                return desc;
            }
        }


        public OWLClassExpression visit(OWLObjectMinCardinality desc) {
            if (signature.containsAll(desc.getFiller().getSignature())) {
                OWLClass name = createNewName();
                OWLClassExpression rhs = desc.getFiller().accept(this);
                axioms.add(getSCA(name, rhs));
                return df.getObjectMinCardinality(desc.getProperty(), desc.getCardinality(), name);
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
                return df.getObjectSomeValuesFrom(desc.getProperty(), name);
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
                    axioms.add(df.getSubClassOf(name, flatOp));
                } else {
                    descs.add(flatOp);
                }
//                }
//                else {
//                    OWLClass name = createNewName();
//                    descs.add(name);
//                    axioms.add(df.getSubClassOf(name, op));
//                }
            }
            return df.getObjectUnionOf(descs);
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
            return Collections.singleton((OWLAxiom) df.getSubClassOf(df.getThing(),
                    df.getObjectUnionOf(df.getObjectComplementOf(
                            sub), sup).getNNF()));
        }


        private Set<OWLAxiom> toSet(OWLAxiom ax) {
            return Collections.singleton(ax);
        }


        public Set<OWLAxiom> visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLClassAssertionAxiom axiom) {
            return subClassOf(df.getObjectOneOf(axiom.getIndividual()), axiom.getDescription());
        }


        public Set<OWLAxiom> visit(OWLDataPropertyAssertionAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLDataPropertyDomainAxiom axiom) {
            return subClassOf(df.getDataSomeValuesFrom(axiom.getProperty(), df.getTopDatatype()),
                    axiom.getDomain());
        }


        public Set<OWLAxiom> visit(OWLDataPropertyRangeAxiom axiom) {
            return toSet(df.getSubClassOf(df.getThing(),
                    df.getDataAllValuesFrom(axiom.getProperty(), axiom.getRange())));
        }


        public Set<OWLAxiom> visit(OWLSubDataPropertyOfAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLDeclaration axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLDifferentIndividualsAxiom axiom) {
            // Explode into pairwise nominals?
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLIndividual> individuals = new ArrayList<OWLIndividual>(axiom.getIndividuals());
            for (int i = 0; i < individuals.size(); i++) {
                for (int j = i + 1; j < individuals.size(); j++) {
                    axioms.addAll(subClassOf(df.getObjectOneOf(individuals.get(i)),
                            df.getObjectOneOf(individuals.get(j))));
                }
            }
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLDisjointClassesAxiom axiom) {
            // Explode
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLClassExpression> classExpressions = new ArrayList<OWLClassExpression>(axiom.getDescriptions());
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
                    axioms.add(df.getDisjointDataProperties(props.get(i), props.get(j)));
                }
            }
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLDisjointObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLObjectPropertyExpression> props = new ArrayList<OWLObjectPropertyExpression>(axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getDisjointObjectProperties(props.get(i), props.get(j)));
                }
            }
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLDisjointUnionAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            axioms.addAll(df.getOWLEquivalentClassesAxiom(axiom.getOWLClass(),
                    df.getObjectUnionOf(axiom.getDescriptions())).accept(this));
            axioms.addAll(df.getDisjointClasses(axiom.getDescriptions()).accept(this));
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLAnnotationAssertionAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLEquivalentClassesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLClassExpression> classExpressions = new ArrayList<OWLClassExpression>(axiom.getDescriptions());
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
                    axioms.add(df.getDisjointDataProperties(props.get(i), props.get(j)));
                    axioms.add(df.getDisjointDataProperties(props.get(j), props.get(i)));
                }
            }
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLObjectPropertyExpression> props = new ArrayList<OWLObjectPropertyExpression>(axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getDisjointObjectProperties(props.get(i), props.get(j)));
                    axioms.add(df.getDisjointObjectProperties(props.get(j), props.get(i)));
                }
            }
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLFunctionalDataPropertyAxiom axiom) {
            return toSet(df.getSubClassOf(df.getThing(),
                    df.getDataMaxCardinality(axiom.getProperty(), 1)));
        }


        public Set<OWLAxiom> visit(OWLFunctionalObjectPropertyAxiom axiom) {
            return toSet(df.getSubClassOf(df.getThing(),
                    df.getObjectMaxCardinality(axiom.getProperty(), 1)));
        }


        public Set<OWLAxiom> visit(OWLImportsDeclaration axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            return toSet(df.getSubClassOf(df.getThing(),
                    df.getObjectMaxCardinality(axiom.getProperty().getInverseProperty(),
                            1)));
        }


        public Set<OWLAxiom> visit(OWLInverseObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            axioms.add(df.getSubObjectPropertyOf(axiom.getFirstProperty(),
                    axiom.getSecondProperty().getInverseProperty()));
            axioms.add(df.getSubObjectPropertyOf(axiom.getSecondProperty(),
                    axiom.getFirstProperty().getInverseProperty()));
            return axioms;
        }


        public Set<OWLAxiom> visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            return subClassOf(df.getObjectOneOf(axiom.getSubject()),
                    df.getDataAllValuesFrom(axiom.getProperty(),
                            df.getDataComplementOf(df.getDataOneOf(axiom.getObject()))));
        }


        public Set<OWLAxiom> visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            return subClassOf(df.getObjectOneOf(axiom.getSubject()),
                    df.getObjectAllValuesFrom(axiom.getProperty(),
                            df.getObjectComplementOf(df.getObjectOneOf(axiom.getObject()))));
        }


        public Set<OWLAxiom> visit(OWLObjectPropertyAssertionAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLSubPropertyChainOfAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLObjectPropertyDomainAxiom axiom) {
            return subClassOf(df.getObjectSomeValuesFrom(axiom.getProperty(), df.getThing()), axiom.getDomain());
        }


        public Set<OWLAxiom> visit(OWLObjectPropertyRangeAxiom axiom) {
            return toSet(df.getSubClassOf(df.getThing(),
                    df.getObjectAllValuesFrom(axiom.getProperty(), axiom.getRange())));
        }


        public Set<OWLAxiom> visit(OWLSubObjectPropertyOfAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLReflexiveObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }


        public Set<OWLAxiom> visit(OWLSameIndividualsAxiom axiom) {
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

        public Set<OWLAxiom> visit(OWLAnnotationPropertyDomain axiom) {
            return toSet(axiom);
        }

        public Set<OWLAxiom> visit(OWLAnnotationPropertyRange axiom) {
            return toSet(axiom);
        }

        public Set<OWLAxiom> visit(OWLSubAnnotationPropertyOf axiom) {
            return toSet(axiom);
        }
    }
}
