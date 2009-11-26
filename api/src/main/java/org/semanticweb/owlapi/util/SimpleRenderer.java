package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

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
 * A simple renderer that can be used for debugging purposes and
 * provide an implementation of the toString method for different
 * implementations.
 */
public class SimpleRenderer implements OWLObjectVisitor, OWLObjectRenderer {

    private StringBuilder sb;

    private ShortFormProvider shortFormProvider;

    private IRIShortFormProvider iriShortFormProvider;

    public SimpleRenderer() {
        sb = new StringBuilder();
        resetShortFormProvider();
    }

    public void reset() {
        sb = new StringBuilder();
    }

    public boolean isUsingDefaultShortFormProvider() {
        return shortFormProvider instanceof DefaultPrefixManager;
    }

    /**
     * Resets the short form provider to the default short form provider, which is a PrefixManager with the
     * default set of prefixes.
     */
    public void resetShortFormProvider() {
        DefaultPrefixManager defaultPrefixManager = new DefaultPrefixManager();
        shortFormProvider = defaultPrefixManager;
        iriShortFormProvider = defaultPrefixManager;
    }

    /**
     * Resets the short form provider and adds prefix name to prefix mappings based on the specified ontology's
     * format (if it is a prefix format) and possibly the ontologies in the imports closure.
     * @param ontology                  The ontology whose format will be used to obtain prefix mappings
     * @param manager                   A manager which can be used to obtain the format of the specified ontology (and possibly ontologies
     *                                  in its imports closure)
     * @param processImportedOntologies Specifies whether or not the prefix mapping should be obtained from imported
     *                                  ontologies.
     */
    public void setPrefixesFromOntologyFormat(OWLOntology ontology, OWLOntologyManager manager, boolean processImportedOntologies) {
        resetShortFormProvider();
        if (processImportedOntologies) {
            for (OWLOntology importedOntology : manager.getImportsClosure(ontology)) {
                if (!importedOntology.equals(ontology)) {
                    copyPrefixes(manager.getOntologyFormat(importedOntology));
                }
            }
        }
        OWLOntologyFormat format = manager.getOntologyFormat(ontology);
        copyPrefixes(format);
    }

    private void copyPrefixes(OWLOntologyFormat ontologyFormat) {
        if (!(ontologyFormat instanceof PrefixOWLOntologyFormat)) {
            return;
        }
        PrefixOWLOntologyFormat prefixFormat = (PrefixOWLOntologyFormat) ontologyFormat;
        for (String prefixName : prefixFormat.getPrefixName2PrefixMap().keySet()) {
            String prefix = prefixFormat.getPrefixName2PrefixMap().get(prefixName);
            this.setPrefix(prefixName, prefix);
        }
    }

    /**
     * Sets a prefix name for a given prefix.  Note that prefix names MUST end with a colon.
     * @param prefixName The prefix name (ending with a colon)
     * @param prefix     The prefix that the prefix name maps to
     */
    public void setPrefix(String prefixName, String prefix) {
        if (!isUsingDefaultShortFormProvider()) {
            resetShortFormProvider();
        }
        ((DefaultPrefixManager) shortFormProvider).setPrefix(prefixName, prefix);
    }

    /**
     * Override the short form provider that is used to generate abreviated names for entities
     * @param shortFormProvider The short form provider to use
     */
    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
    }

    protected void append(String s) {
        sb.append(s);
    }

    public String getShortForm(IRI iri) {
        return iriShortFormProvider.getShortForm(iri);
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
        sb.append("Ontology(" + ontology.getOntologyID() + " [Axioms: " + ontology.getAxiomCount() + "] [Logical axioms: " + ontology.getLogicalAxiomCount() + "])");
    }


    private void insertSpace() {
        sb.append(" ");
    }

    public void writeAnnotations(OWLAxiom axiom) {
        for (OWLAnnotation anno : axiom.getAnnotations()) {
            anno.accept(this);
            insertSpace();
        }
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
        writeAnnotations(axiom);
        axiom.getSubClass().accept(this);
        insertSpace();
        axiom.getSuperClass().accept(this);
        sb.append(")");
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        sb.append("NegativeObjectPropertyAssertion(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getObject().accept(this);
        sb.append(")");
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        sb.append("AsymmetricObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        sb.append("ReflexiveObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        sb.append("DisjointClasses(");
        writeAnnotations(axiom);
        render(axiom.getClassExpressions());
        sb.append(")");
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        sb.append("DataPropertyDomain(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getDomain().accept(this);
        sb.append(")");
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        sb.append("ObjectPropertyDomain(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getDomain().accept(this);
        sb.append(")");
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        sb.append("EquivalentObjectProperties(");
        writeAnnotations(axiom);
        render(axiom.getProperties());
        sb.append(" )");
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        sb.append("NegativeDataPropertyAssertion(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getObject().accept(this);
        sb.append(")");
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        sb.append("DifferentIndividuals(");
        writeAnnotations(axiom);
        render(axiom.getIndividuals());
        sb.append(" )");
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        sb.append("DisjointDataProperties(");
        writeAnnotations(axiom);
        render(axiom.getProperties());
        sb.append(" )");
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        sb.append("DisjointObjectProperties(");
        writeAnnotations(axiom);
        render(axiom.getProperties());
        sb.append(" )");
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        sb.append("ObjectPropertyRange(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getRange().accept(this);
        sb.append(")");
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        sb.append("ObjectPropertyAssertion(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getObject().accept(this);
        sb.append(")");
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        sb.append("FunctionalObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        sb.append("SubObjectPropertyOf(");
        writeAnnotations(axiom);
        axiom.getSubProperty().accept(this);
        insertSpace();
        axiom.getSuperProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        sb.append("DisjointUnion(");
        writeAnnotations(axiom);
        axiom.getOWLClass().accept(this);
        insertSpace();
        render(axiom.getClassExpressions());
        sb.append(" )");
    }


    public void visit(OWLDeclarationAxiom axiom) {
        sb.append("Declaration(");
        writeAnnotations(axiom);
        OWLEntity entity = axiom.getEntity();
        if (entity.isOWLClass()) {
            sb.append("OWLClass(");
        }
        else if (entity.isOWLObjectProperty()) {
            sb.append("ObjectProperty(");
        }
        else if (entity.isOWLDataProperty()) {
            sb.append("DataProperty(");
        }
        else if (entity.isOWLNamedIndividual()) {
            sb.append("NamedIndividual(");
        }
        else if (entity.isOWLDatatype()) {
            sb.append("Datatype(");
        }
        else if (entity.isOWLAnnotationProperty()) {
            sb.append("AnnotationProperty(");
        }
        axiom.getEntity().accept(this);
        sb.append("))");
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        sb.append("AnnotationAssertion(");
        writeAnnotations(axiom);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getValue().accept(this);
        sb.append(")");
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        sb.append("SymmetricObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        sb.append("DataPropertyRange(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getRange().accept(this);
        sb.append(")");
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        sb.append("FunctionalDataProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        sb.append("EquivalentDataProperties(");
        writeAnnotations(axiom);
        render(axiom.getProperties());
        sb.append(" )");
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        sb.append("ClassAssertion(");
        writeAnnotations(axiom);
        axiom.getClassExpression().accept(this);
        insertSpace();
        axiom.getIndividual().accept(this);
        sb.append(")");
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        sb.append("EquivalentClasses(");
        writeAnnotations(axiom);
        render(axiom.getClassExpressions());
        sb.append(" )");
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        sb.append("DataPropertyAssertion(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getObject().accept(this);
        sb.append(")");
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        sb.append("TransitiveObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        sb.append("IrreflexiveObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        sb.append("SubDataProperty(");
        writeAnnotations(axiom);
        axiom.getSubProperty().accept(this);
        insertSpace();
        axiom.getSuperProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        sb.append("InverseFunctionalObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(")");
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        sb.append("SameIndividual(");
        writeAnnotations(axiom);
        render(axiom.getIndividuals());
        sb.append(" )");
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        sb.append("SubObjectPropertyOf(");
        writeAnnotations(axiom);
        sb.append("ObjectPropertyChain(");
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
        sb.append("ObjectHasSelf(");
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


    public void visit(OWLDataHasValue desc) {
        sb.append("DataHasValue(");
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
        sb.append(shortFormProvider.getShortForm(node));
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
        sb.append(node.getLiteral());
        sb.append("\"^^");
        node.getDatatype().accept(this);
    }


    public void visit(OWLStringLiteral node) {
        sb.append("\"");
        sb.append(node.getLiteral());
        sb.append("\"");
            sb.append("@");
        if (node.getLang() != null) {
            sb.append(node.getLang());
        }
    }


    public void visit(OWLObjectProperty property) {
        sb.append(shortFormProvider.getShortForm(property));
    }


    public void visit(OWLObjectInverseOf property) {
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
        sb.append(" (");
        for (OWLObjectPropertyExpression prop : axiom.getObjectPropertyExpressions()) {
            prop.accept(this);
            sb.append(" ");
        }
        sb.append(") (");
        for (OWLDataPropertyExpression prop : axiom.getDataPropertyExpressions()) {
            prop.accept(this);
            sb.append(" ");
        }
        sb.append(")");
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

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        sb.append("AnnotationPropertyDomain(");
        axiom.getProperty().accept(this);
        sb.append(" ");
        axiom.getDomain().accept(this);
        sb.append(")");
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        sb.append("AnnotationPropertyRange(");
        axiom.getProperty().accept(this);
        sb.append(" ");
        axiom.getRange().accept(this);
        sb.append(")");
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
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
        sb.append("<");
        sb.append(iri);
        sb.append(">");
    }

    public void visit(OWLAnnotation node) {
        sb.append("Annotation(");
        Set<OWLAnnotation> annos = node.getAnnotations();
        for (OWLAnnotation anno : annos) {
            anno.accept(this);
            sb.append(" ");
        }
        node.getProperty().accept(this);
        sb.append(" ");
        node.getValue().accept(this);
        sb.append(")");
    }

    public void visit(SWRLRule rule) {
        sb.append("DLSafeRule(");
        sb.append(" Body(");
        render(rule.getBody());
        sb.append(")");
        sb.append(" Head(");
        render(rule.getHead());
        sb.append(")");
        sb.append(" )");
    }


    public void visit(SWRLClassAtom node) {
        sb.append("ClassAtom(");
        node.getPredicate().accept(this);
        sb.append(" ");        
        node.getArgument().accept(this);
        sb.append(")");
    }


    public void visit(SWRLDataRangeAtom node) {
        sb.append("DataRangeAtom(");
        node.getPredicate().accept(this);
        sb.append(" ");        
        node.getArgument().accept(this);
        sb.append(")");
    }


    public void visit(SWRLDifferentIndividualsAtom node) {
        sb.append("DifferentFromAtom(");
        node.getFirstArgument().accept(this);
        sb.append(" ");
        node.getSecondArgument().accept(this);
        sb.append(")");
    }


    public void visit(SWRLSameIndividualAtom node) {
        sb.append("SameAsAtom(");
        node.getFirstArgument().accept(this);
        sb.append(" ");
        node.getSecondArgument().accept(this);
        sb.append(")");
    }


    public void visit(SWRLObjectPropertyAtom node) {
        sb.append("ObjectPropertyAtom(");
        node.getPredicate().accept(this);
        sb.append(" ");
        node.getFirstArgument().accept(this);
        sb.append(" ");
        node.getSecondArgument().accept(this);
        sb.append(")");
    }


    public void visit(SWRLDataPropertyAtom node) {
        sb.append("DataPropertyAtom(");
        node.getPredicate().accept(this);
        sb.append(" ");        
        node.getFirstArgument().accept(this);
        sb.append(" ");
        node.getSecondArgument().accept(this);
        sb.append(")");
    }


    public void visit(SWRLBuiltInAtom node) {
        sb.append("BuiltInAtom(");
        sb.append(getShortForm(node.getPredicate()));
        sb.append(" ");
        for (SWRLArgument arg : node.getArguments()) {
            arg.accept(this);
            sb.append(" ");
        }
        sb.append(")");
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        sb.append("DatatypeDefinition(");
        axiom.getDatatype().accept(this);
        sb.append(" ");
        axiom.getDataRange().accept(this);
        sb.append(")");
    }


    public void visit(SWRLVariable node) {
        sb.append("Variable(");
        sb.append(getShortForm(node.getIRI()));
        sb.append(")");
    }


    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }


    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }


    public String toString() {
        return sb.toString();
    }
}
