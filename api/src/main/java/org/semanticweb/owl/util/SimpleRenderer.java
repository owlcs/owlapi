package org.semanticweb.owl.util;

import org.semanticweb.owl.io.OWLObjectRenderer;
import org.semanticweb.owl.model.*;

import java.net.URI;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
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
 * Date: 26-Nov-2006<br><br>
 * <p/>
 * A simple renderer that can be used for debugging purposes and
 * provide an implementation of the toString method for different
 * implementations.
 */
public class SimpleRenderer implements OWLObjectVisitor, OWLObjectRenderer {

    private StringBuilder sb;

    private ShortFormProvider shortFormProvider;

    private URIShortFormProvider uriShortFormProvider;

    public SimpleRenderer() {
        sb = new StringBuilder();
        shortFormProvider = new SimpleShortFormProvider();
        uriShortFormProvider = new SimpleURIShortFormProvider();
    }


    public void reset() {
        sb = new StringBuilder();
    }


    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
    }

    protected void append(String s) {
        sb.append(s);
    }

    public String render(OWLObject object) {
        reset();
        object.accept(this);
        return sb.toString();
    }

    protected void render(Set<? extends OWLObject> objects) {
        for (Iterator<? extends OWLObject> it = toSortedSet(objects).iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                sb.append(" ");
            }
        }
    }

    public void visit(OWLOntology ontology) {
        sb.append("Ontology(" + ontology.getURI() + " [Axioms: " + ontology.getAxiomCount() + "] [Logical axioms: " + ontology.getLogicalAxiomCount() + "])");
    }


    private void insertSpace() {
        sb.append(" ");
    }


    private <N extends OWLObject> Set<N> toSortedSet(Set<N> set) {
        return new TreeSet<N>(set);
//        Set<N> sorted = new TreeSet<N>(new Comparator() {
//            public int compare(Object o1, Object o2) {
//                return o1.toString().compareTo(o2.toString());
//            }
//        });
//        sorted.addAll(set);
//        return sorted;
    }


    public void visit(OWLSubClassOfAxiom axiom) {
        sb.append("SubClassOf(");
        axiom.getSubClass().accept(this);
        insertSpace();
        axiom.getSuperClass().accept(this);
        sb.append(")");
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        sb.append("NegativeObjectPropertyAssertion(");
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getObject().accept(this);
        sb.append(")");
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        sb.append("AntiSymmetricObjectProperty(");
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        sb.append("ReflexiveObjectProperty(");
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        sb.append("DisjointClasses(");
        render(axiom.getDescriptions());
        sb.append(")");
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        sb.append("DataPropertyDomain(");
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getDomain().accept(this);
        sb.append(")");
    }


    public void visit(OWLImportsDeclaration axiom) {
        sb.append("Imports(");
        sb.append(axiom.getSubject().getURI());
        sb.append(" -> ");
        sb.append(axiom.getImportedOntologyURI());
        sb.append(")");
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        sb.append("ObjectPropertyDomain(");
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getDomain().accept(this);
        sb.append(")");
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        sb.append("EquivalentObjectProperties(");
        render(axiom.getProperties());
        sb.append(" )");
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        sb.append("NegativeDataPropertyAssertion(");
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getObject().accept(this);
        sb.append(")");
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        sb.append("DifferentIndividuals(");
        render(axiom.getIndividuals());
        sb.append(" )");
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        sb.append("DisjointDataProperties(");
        render(axiom.getProperties());
        sb.append(" )");
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        sb.append("DisjointObjectProperties(");
        render(axiom.getProperties());
        sb.append(" )");
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        sb.append("ObjectPropertyRange(");
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getRange().accept(this);
        sb.append(")");
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        sb.append("ObjectPropertyAssertion(");
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getObject().accept(this);
        sb.append(")");
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        sb.append("FunctionalObjectProperty(");
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        sb.append("SubObjectPropertyOf(");
        axiom.getSubProperty().accept(this);
        insertSpace();
        axiom.getSuperProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        sb.append("DisjointUnion(");
        axiom.getOWLClass().accept(this);
        insertSpace();
        render(axiom.getDescriptions());
        sb.append(" )");
    }


    public void visit(OWLDeclaration axiom) {
        sb.append("Declaration(");
        OWLEntity entity = axiom.getEntity();
        if (entity.isOWLClass()) {
            sb.append("OWLClass(");
        } else if (entity.isOWLObjectProperty()) {
            sb.append("ObjectProperty(");
        } else if (entity.isOWLDataProperty()) {
            sb.append("DataProperty(");
        } else if (entity.isOWLIndividual()) {
            sb.append("Individual(");
        } else if (entity.isOWLDatatype()) {
            sb.append("Datatype(");
        }
        axiom.getEntity().accept(this);
        sb.append("))");
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        sb.append("EntityAnnotationAxiom(");
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getAnnotation().accept(this);
        sb.append(")");
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        sb.append("SymmetricObjectProperty(");
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        sb.append("DataPropertyRange(");
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getRange().accept(this);
        sb.append(")");
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        sb.append("FunctionalDataProperty(");
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        sb.append("EquivalentDataProperties(");
        render(axiom.getProperties());
        sb.append(" )");
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        sb.append("ClassAssertion(");
        axiom.getDescription().accept(this);
        insertSpace();
        axiom.getIndividual().accept(this);
        sb.append(")");
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        sb.append("EquivalentClasses(");
        render(axiom.getDescriptions());
        sb.append(" )");
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        sb.append("DataPropertyAssertion(");
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getObject().accept(this);
        sb.append(")");
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        sb.append("TransitiveObjectProperty(");
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        sb.append("IrreflexiveObjectProperty(");
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        sb.append("SubDataProperty(");
        axiom.getSubProperty().accept(this);
        insertSpace();
        axiom.getSuperProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        sb.append("InverseFunctionalObjectProperty(");
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLSameIndividualsAxiom axiom) {
        sb.append("SameIndividual(");
        render(axiom.getIndividuals());
        sb.append(" )");
    }


    public void visit(OWLComplextSubPropertyAxiom axiom) {
        sb.append("ObjectPropertyChainSubProperty(");
        sb.append("(");
        for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            insertSpace();
            prop.accept(this);
        }
        sb.append(" )");
        insertSpace();
        axiom.getSuperProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLClass desc) {
        sb.append(shortFormProvider.getShortForm(desc));
    }


    public void visit(OWLObjectIntersectionOf desc) {
        sb.append("ObjectIntersectionOf(");
        render(desc.getOperands());
        sb.append(")");
    }


    public void visit(OWLObjectUnionOf desc) {
        sb.append("ObjectUnionOf(");
        render(desc.getOperands());
        sb.append(")");
    }


    public void visit(OWLObjectComplementOf desc) {
        sb.append("ObjectComplementOf(");
        desc.getOperand().accept(this);
        sb.append(")");
    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        sb.append("ObjectSomeValuesFrom(");
        desc.getProperty().accept(this);
        insertSpace();
        desc.getFiller().accept(this);
        sb.append(")");
    }


    public void visit(OWLObjectAllValuesFrom desc) {
        sb.append("ObjectAllValuesFrom(");
        desc.getProperty().accept(this);
        insertSpace();
        desc.getFiller().accept(this);
        sb.append(")");
    }


    public void visit(OWLObjectHasValue desc) {
        sb.append("ObjectHasValue(");
        desc.getProperty().accept(this);
        insertSpace();
        desc.getValue().accept(this);
        sb.append(")");
    }


    public void visit(OWLObjectMinCardinality desc) {
        sb.append("ObjectMinCardinality(");
        sb.append(desc.getCardinality());
        insertSpace();
        desc.getProperty().accept(this);
        insertSpace();
        desc.getFiller().accept(this);
        sb.append(")");
    }


    public void visit(OWLObjectExactCardinality desc) {
        sb.append("ObjectExactCardinality(");
        sb.append(desc.getCardinality());
        insertSpace();
        desc.getProperty().accept(this);
        insertSpace();
        desc.getFiller().accept(this);
        sb.append(")");
    }


    public void visit(OWLObjectMaxCardinality desc) {
        sb.append("ObjectMaxCardinality(");
        sb.append(desc.getCardinality());
        insertSpace();
        desc.getProperty().accept(this);
        insertSpace();
        desc.getFiller().accept(this);
        sb.append(")");
    }


    public void visit(OWLObjectHasSelf desc) {
        sb.append("ObjectExistsSelf(");
        desc.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLObjectOneOf desc) {
        sb.append("ObjectOneOf(");
        render(desc.getIndividuals());
        sb.append(")");
    }


    public void visit(OWLDataSomeValuesFrom desc) {
        sb.append("DataSomeValuesFrom(");
        desc.getProperty().accept(this);
        insertSpace();
        desc.getFiller().accept(this);
        sb.append(")");
    }


    public void visit(OWLDataAllValuesFrom desc) {
        sb.append("DataAllValuesFrom(");
        desc.getProperty().accept(this);
        insertSpace();
        desc.getFiller().accept(this);
        sb.append(")");
    }


    public void visit(OWLDataValueRestriction desc) {
        sb.append("DataValue(");
        desc.getProperty().accept(this);
        insertSpace();
        desc.getValue().accept(this);
        sb.append(")");
    }


    public void visit(OWLDataMinCardinality desc) {
        sb.append("DataMinCardinality(");
        sb.append(desc.getCardinality());
        insertSpace();
        desc.getProperty().accept(this);
        insertSpace();
        desc.getFiller().accept(this);
        sb.append(")");
    }


    public void visit(OWLDataExactCardinality desc) {
        sb.append("DataExactCardinality(");
        sb.append(desc.getCardinality());
        insertSpace();
        desc.getProperty().accept(this);
        insertSpace();
        desc.getFiller().accept(this);
        sb.append(")");
    }


    public void visit(OWLDataMaxCardinality desc) {
        sb.append("DataMaxCardinality(");
        sb.append(desc.getCardinality());
        insertSpace();
        desc.getProperty().accept(this);
        insertSpace();
        desc.getFiller().accept(this);
        sb.append(")");
    }


    public void visit(OWLDatatype node) {
        sb.append(node.getURI().getFragment());
    }


    public void visit(OWLDataComplementOf node) {
        sb.append("DataComplementOf(");
        node.getDataRange().accept(this);
        sb.append(")");
    }


    public void visit(OWLDataOneOf node) {
        sb.append("DataOneOf(");
        render(node.getValues());
        sb.append(" )");
    }


    public void visit(OWLDatatypeRestriction node) {
        sb.append("DataRangeRestriction(");
        node.getDatatype().accept(this);
        for (OWLFacetRestriction restriction : node.getFacetRestrictions()) {
            insertSpace();
            restriction.accept(this);
        }
        sb.append(")");
    }


    public void visit(OWLFacetRestriction node) {
        sb.append("facetRestriction(");
        sb.append(node.getFacet());
        insertSpace();
        node.getFacetValue().accept(this);
        sb.append(")");
    }


    public void visit(OWLTypedLiteral node) {
        sb.append("\"");
        sb.append(node.getString());
        sb.append("\"^^");
        node.getDatatype().accept(this);
    }


    public void visit(OWLRDFTextLiteral node) {
        sb.append("\"");
        sb.append(node.getString());
        sb.append("\"");
        sb.append("@");
        sb.append(node.getLang());
    }


    public void visit(OWLObjectProperty property) {
        sb.append(shortFormProvider.getShortForm(property));
    }


    public void visit(OWLObjectPropertyInverse property) {
        sb.append("InverseOf(");
        property.getInverse().accept(this);
        sb.append(")");
    }


    public void visit(OWLDataProperty property) {
        sb.append(shortFormProvider.getShortForm(property));
    }


    public void visit(OWLNamedIndividual individual) {
        sb.append(shortFormProvider.getShortForm(individual));
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        sb.append("InverseObjectProperties(");
        axiom.getFirstProperty().accept(this);
        sb.append(" ");
        axiom.getSecondProperty().accept(this);
        sb.append(")");
    }

    public void visit(OWLHasKeyAxiom axiom) {
        sb.append("HasKey(");
        axiom.getClassExpression().accept(this);
        sb.append(" ");
        for (OWLObjectPropertyExpression prop : axiom.getObjectPropertyExpressions()) {
            prop.accept(this);
            sb.append(" ");
        }
        for (OWLDataPropertyExpression prop : axiom.getDataPropertyExpressions()) {
            prop.accept(this);
            sb.append(" ");
        }
        sb.append(")");
    }

    public void visit(OWLDataIntersectionOf node) {
        sb.append("DataIntersectionOf(");
        for (OWLDataRange rng : node.getOperands()) {
            rng.accept(this);
            sb.append(" ");
        }
        sb.append(")");
    }

    public void visit(OWLDataUnionOf node) {
        sb.append("DataUnionOf(");
        for (OWLDataRange rng : node.getOperands()) {
            rng.accept(this);
            sb.append(" ");
        }
        sb.append(")");
    }

    public void visit(OWLAnnotationProperty property) {
        sb.append(shortFormProvider.getShortForm(property));
    }

    public void visit(OWLAnnotationPropertyDomain axiom) {
        sb.append("AnnotationPropertyDomain(");
        axiom.getProperty().accept(this);
        sb.append(" ");
        axiom.getDomain().accept(this);
        sb.append(")");
    }

    public void visit(OWLAnnotationPropertyRange axiom) {
        sb.append("AnnotationPropertyRange(");
        axiom.getProperty().accept(this);
        sb.append(" ");
        axiom.getRange().accept(this);
        sb.append(")");
    }

    public void visit(OWLSubAnnotationPropertyOf axiom) {
        sb.append("SubAnnotationPropertyOf(");
        axiom.getSubProperty().accept(this);
        sb.append(" ");
        axiom.getSuperProperty().accept(this);
        sb.append(")");
    }

    public void visit(OWLAnonymousIndividual individual) {
        sb.append(individual.getID().toString());
    }

    public void visit(IRI iri) {
        sb.append(iri);
    }

    public void visit(OWLAnnotation node) {
        sb.append("Annotation(");
        Set<OWLAnnotation> annos = node.getAnnotations();
        for(OWLAnnotation anno : annos) {
            anno.accept(this);
            sb.append(" ");
        }
        node.getProperty().accept(this);
        sb.append(" ");
        node.getValue().accept(this);
        sb.append(")");
    }

    public void visit(SWRLRule rule) {
        sb.append("Rule(");
        if (!rule.isAnonymous()) {
            sb.append(getShortForm(rule.getURI()));
            sb.append(" ");
        }
        sb.append(" antecedent(");
        render(rule.getBody());
        sb.append(")");
        sb.append(" consequent(");
        render(rule.getHead());
        sb.append(")");
        sb.append(" )");
    }


    public void visit(SWRLClassAtom node) {
        node.getPredicate().accept(this);
        sb.append("(");
        node.getArgument().accept(this);
        sb.append(")");
    }


    public void visit(SWRLDataRangeAtom node) {
        node.getPredicate().accept(this);
        sb.append("(");
        node.getArgument().accept(this);
        sb.append(")");
    }


    public void visit(SWRLDifferentFromAtom node) {
        sb.append("differentFromAtom(");
        node.getFirstArgument().accept(this);
        sb.append(" ");
        node.getSecondArgument().accept(this);
        sb.append(")");
    }


    public void visit(SWRLSameAsAtom node) {
        sb.append("sameAsAtom(");
        node.getFirstArgument().accept(this);
        sb.append(" ");
        node.getSecondArgument().accept(this);
        sb.append(")");
    }


    public void visit(SWRLObjectPropertyAtom node) {
        node.getPredicate().accept(this);
        sb.append("(");
        node.getFirstArgument().accept(this);
        sb.append(" ");
        node.getSecondArgument().accept(this);
        sb.append(")");
    }


    public void visit(SWRLDataValuedPropertyAtom node) {
        node.getPredicate().accept(this);
        sb.append("(");
        node.getFirstArgument().accept(this);
        sb.append(" ");
        node.getSecondArgument().accept(this);
        sb.append(")");
    }


    public void visit(SWRLBuiltInAtom node) {
        sb.append(getShortForm(node.getPredicate().getURI()));
        sb.append("(");
        for (SWRLAtomObject arg : node.getArguments()) {
            arg.accept(this);
            sb.append(" ");
        }
        sb.append(")");
    }


    public void visit(SWRLAtomDVariable node) {
        sb.append("?");
        sb.append(getShortForm(node.getURI()));
    }


    public void visit(SWRLAtomIVariable node) {
        sb.append("?");
        sb.append(getShortForm(node.getURI()));
    }

    private String getShortForm(URI uri) {
        return uriShortFormProvider.getShortForm(uri);
    }

    public void visit(SWRLAtomIndividualObject node) {
        node.getIndividual().accept(this);
    }


    public void visit(SWRLAtomConstantObject node) {
        node.getConstant().accept(this);
    }


    public String toString() {
        return sb.toString();
    }
}
