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
package org.semanticweb.owlapi.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.*;

/**
 * A simple renderer that can be used for debugging purposes and provide an
 * implementation of the toString method for different implementations.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class SimpleRenderer implements OWLObjectVisitor, OWLObjectRenderer {

    private StringBuilder sb;
    private ShortFormProvider shortFormProvider;
    private IRIShortFormProvider iriShortFormProvider;

    /** default constructor */
    public SimpleRenderer() {
        sb = new StringBuilder();
        resetShortFormProvider();
    }

    /** reset the renderer. */
    public void reset() {
        sb = new StringBuilder();
    }

    /**
     * @return true if default is used
     */
    public boolean isUsingDefaultShortFormProvider() {
        return shortFormProvider instanceof DefaultPrefixManager;
    }

    /**
     * Resets the short form provider to the default short form provider, which
     * is a PrefixManager with the default set of prefixes.
     */
    public final void resetShortFormProvider() {
        DefaultPrefixManager defaultPrefixManager = new DefaultPrefixManager();
        shortFormProvider = defaultPrefixManager;
        iriShortFormProvider = defaultPrefixManager;
    }

    /**
     * Resets the short form provider and adds prefix name to prefix mappings
     * based on the specified ontology's format (if it is a prefix format) and
     * possibly the ontologies in the imports closure.
     * 
     * @param ontology
     *        The ontology whose format will be used to obtain prefix mappings
     * @param manager
     *        A manager which can be used to obtain the format of the specified
     *        ontology (and possibly ontologies in its imports closure)
     * @param processImportedOntologies
     *        Specifies whether or not the prefix mapping should be obtained
     *        from imported ontologies.
     */
    public void setPrefixesFromOntologyFormat(@Nonnull OWLOntology ontology, @Nonnull OWLOntologyManager manager,
        boolean processImportedOntologies) {
        resetShortFormProvider();
        if (processImportedOntologies) {
            for (OWLOntology importedOntology : manager.getImportsClosure(ontology)) {
                if (!importedOntology.equals(ontology)) {
                    copyPrefixes(manager.getOntologyFormat(importedOntology));
                }
            }
        }
        OWLDocumentFormat format = manager.getOntologyFormat(ontology);
        copyPrefixes(format);
    }

    private void copyPrefixes(OWLDocumentFormat ontologyFormat) {
        if (!(ontologyFormat instanceof PrefixDocumentFormat)) {
            return;
        }
        PrefixDocumentFormat prefixFormat = (PrefixDocumentFormat) ontologyFormat;
        for (Map.Entry<String, String> e : prefixFormat.getPrefixName2PrefixMap().entrySet()) {
            setPrefix(e.getKey(), e.getValue());
        }
    }

    /**
     * Sets a prefix name for a given prefix. Note that prefix names MUST end
     * with a colon.
     * 
     * @param prefixName
     *        The prefix name (ending with a colon)
     * @param prefix
     *        The prefix that the prefix name maps to
     */
    public void setPrefix(@Nonnull String prefixName, @Nonnull String prefix) {
        if (!isUsingDefaultShortFormProvider()) {
            resetShortFormProvider();
        }
        ((DefaultPrefixManager) shortFormProvider).setPrefix(prefixName, prefix);
    }

    @Override
    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
    }

    /**
     * @param iri
     *        the iri to shorten
     * @return the short form
     */
    public String getShortForm(@Nonnull IRI iri) {
        return iriShortFormProvider.getShortForm(iri);
    }

    @Override
    public String render(OWLObject object) {
        reset();
        object.accept(this);
        return sb.toString();
    }

    protected void render(Set<? extends OWLObject> objects) {
        for (Iterator<? extends OWLObject> it = CollectionFactory.sortOptionally(objects).iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                sb.append(' ');
            }
        }
    }

    @Override
    public void visit(OWLOntology ontology) {
        sb.append("Ontology(").append(ontology.getOntologyID()).append(" [Axioms: ").append(ontology.getAxiomCount())
            .append("] [Logical axioms: ").append(ontology.getLogicalAxiomCount()).append("])");
    }

    private void insertSpace() {
        sb.append(' ');
    }

    /**
     * @param axiom
     *        the axiom whose annotations should be written
     */
    public void writeAnnotations(OWLAxiom axiom) {
        for (OWLAnnotation anno : axiom.getAnnotations()) {
            anno.accept(this);
            insertSpace();
        }
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        sb.append("SubClassOf(");
        writeAnnotations(axiom);
        axiom.getSubClass().accept(this);
        insertSpace();
        axiom.getSuperClass().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        sb.append("NegativeObjectPropertyAssertion(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getObject().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        sb.append("AsymmetricObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        sb.append("ReflexiveObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        sb.append("DisjointClasses(");
        writeAnnotations(axiom);
        render(axiom.getClassExpressions());
        sb.append(')');
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        sb.append("DataPropertyDomain(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getDomain().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        sb.append("ObjectPropertyDomain(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getDomain().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        sb.append("EquivalentObjectProperties(");
        writeAnnotations(axiom);
        render(axiom.getProperties());
        sb.append(" )");
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        sb.append("NegativeDataPropertyAssertion(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getObject().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        sb.append("DifferentIndividuals(");
        writeAnnotations(axiom);
        render(axiom.getIndividuals());
        sb.append(" )");
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        sb.append("DisjointDataProperties(");
        writeAnnotations(axiom);
        render(axiom.getProperties());
        sb.append(" )");
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        sb.append("DisjointObjectProperties(");
        writeAnnotations(axiom);
        render(axiom.getProperties());
        sb.append(" )");
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        sb.append("ObjectPropertyRange(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getRange().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        sb.append("ObjectPropertyAssertion(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getObject().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        sb.append("FunctionalObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        sb.append("SubObjectPropertyOf(");
        writeAnnotations(axiom);
        axiom.getSubProperty().accept(this);
        insertSpace();
        axiom.getSuperProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        sb.append("DisjointUnion(");
        writeAnnotations(axiom);
        axiom.getOWLClass().accept(this);
        insertSpace();
        render(axiom.getClassExpressions());
        sb.append(" )");
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        sb.append("Declaration(");
        writeAnnotations(axiom);
        OWLEntity entity = axiom.getEntity();
        if (entity.isOWLClass()) {
            sb.append("Class(");
        } else if (entity.isOWLObjectProperty()) {
            sb.append("ObjectProperty(");
        } else if (entity.isOWLDataProperty()) {
            sb.append("DataProperty(");
        } else if (entity.isOWLNamedIndividual()) {
            sb.append("NamedIndividual(");
        } else if (entity.isOWLDatatype()) {
            sb.append("Datatype(");
        } else if (entity.isOWLAnnotationProperty()) {
            sb.append("AnnotationProperty(");
        }
        axiom.getEntity().accept(this);
        sb.append("))");
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        sb.append("AnnotationAssertion(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getValue().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        sb.append("SymmetricObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        sb.append("DataPropertyRange(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getRange().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        sb.append("FunctionalDataProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        sb.append("EquivalentDataProperties(");
        writeAnnotations(axiom);
        render(axiom.getProperties());
        sb.append(" )");
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        sb.append("ClassAssertion(");
        writeAnnotations(axiom);
        axiom.getClassExpression().accept(this);
        insertSpace();
        axiom.getIndividual().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        sb.append("EquivalentClasses(");
        writeAnnotations(axiom);
        render(axiom.getClassExpressions());
        sb.append(" )");
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        sb.append("DataPropertyAssertion(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        insertSpace();
        axiom.getSubject().accept(this);
        insertSpace();
        axiom.getObject().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        sb.append("TransitiveObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        sb.append("IrreflexiveObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        sb.append("SubDataPropertyOf(");
        writeAnnotations(axiom);
        axiom.getSubProperty().accept(this);
        insertSpace();
        axiom.getSuperProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        sb.append("InverseFunctionalObjectProperty(");
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        sb.append("SameIndividual(");
        writeAnnotations(axiom);
        render(axiom.getIndividuals());
        sb.append(" )");
    }

    @Override
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
        sb.append(')');
    }

    @Override
    public void visit(OWLClass ce) {
        sb.append(shortFormProvider.getShortForm(ce));
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        sb.append("ObjectIntersectionOf(");
        render(ce.getOperands());
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        sb.append("ObjectUnionOf(");
        render(ce.getOperands());
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        sb.append("ObjectComplementOf(");
        ce.getOperand().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        sb.append("ObjectSomeValuesFrom(");
        ce.getProperty().accept(this);
        insertSpace();
        ce.getFiller().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        sb.append("ObjectAllValuesFrom(");
        ce.getProperty().accept(this);
        insertSpace();
        ce.getFiller().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        sb.append("ObjectHasValue(");
        ce.getProperty().accept(this);
        insertSpace();
        ce.getFiller().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        sb.append("ObjectMinCardinality(");
        sb.append(ce.getCardinality());
        insertSpace();
        ce.getProperty().accept(this);
        insertSpace();
        ce.getFiller().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        sb.append("ObjectExactCardinality(");
        sb.append(ce.getCardinality());
        insertSpace();
        ce.getProperty().accept(this);
        insertSpace();
        ce.getFiller().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        sb.append("ObjectMaxCardinality(");
        sb.append(ce.getCardinality());
        insertSpace();
        ce.getProperty().accept(this);
        insertSpace();
        ce.getFiller().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        sb.append("ObjectHasSelf(");
        ce.getProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        sb.append("ObjectOneOf(");
        render(ce.getIndividuals());
        sb.append(')');
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        sb.append("DataSomeValuesFrom(");
        ce.getProperty().accept(this);
        insertSpace();
        ce.getFiller().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        sb.append("DataAllValuesFrom(");
        ce.getProperty().accept(this);
        insertSpace();
        ce.getFiller().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        sb.append("DataHasValue(");
        ce.getProperty().accept(this);
        insertSpace();
        ce.getFiller().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        sb.append("DataMinCardinality(");
        sb.append(ce.getCardinality());
        insertSpace();
        ce.getProperty().accept(this);
        insertSpace();
        ce.getFiller().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        sb.append("DataExactCardinality(");
        sb.append(ce.getCardinality());
        insertSpace();
        ce.getProperty().accept(this);
        insertSpace();
        ce.getFiller().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        sb.append("DataMaxCardinality(");
        sb.append(ce.getCardinality());
        insertSpace();
        ce.getProperty().accept(this);
        insertSpace();
        ce.getFiller().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLDatatype node) {
        sb.append(shortFormProvider.getShortForm(node));
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        sb.append("DataComplementOf(");
        node.getDataRange().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLDataOneOf node) {
        sb.append("DataOneOf(");
        render(node.getValues());
        sb.append(" )");
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        sb.append("DataRangeRestriction(");
        node.getDatatype().accept(this);
        for (OWLFacetRestriction restriction : node.getFacetRestrictions()) {
            insertSpace();
            restriction.accept(this);
        }
        sb.append(')');
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        sb.append("facetRestriction(");
        sb.append(node.getFacet());
        insertSpace();
        node.getFacetValue().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLLiteral node) {
        String literal = EscapeUtils.escapeString(node.getLiteral());
        if (node.isRDFPlainLiteral()) {
            // We can use a syntactic shortcut
            sb.append('"').append(literal).append('"');
            if (node.hasLang()) {
                sb.append('@').append(node.getLang());
            }
        } else {
            sb.append('"').append(literal).append("\"^^");
            node.getDatatype().accept(this);
        }
    }

    @Override
    public void visit(OWLObjectProperty property) {
        sb.append(shortFormProvider.getShortForm(property));
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        sb.append("InverseOf(");
        property.getInverse().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLDataProperty property) {
        sb.append(shortFormProvider.getShortForm(property));
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        sb.append(shortFormProvider.getShortForm(individual));
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        sb.append("InverseObjectProperties(");
        writeAnnotations(axiom);
        axiom.getFirstProperty().accept(this);
        sb.append(' ');
        axiom.getSecondProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        sb.append("HasKey(");
        writeAnnotations(axiom);
        axiom.getClassExpression().accept(this);
        sb.append(" (");
        for (OWLObjectPropertyExpression prop : axiom.getObjectPropertyExpressions()) {
            prop.accept(this);
            sb.append(' ');
        }
        sb.append(") (");
        for (OWLDataPropertyExpression prop : axiom.getDataPropertyExpressions()) {
            prop.accept(this);
            sb.append(' ');
        }
        sb.append(')');
        sb.append(')');
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        sb.append("DataIntersectionOf(");
        for (OWLDataRange rng : node.getOperands()) {
            rng.accept(this);
            sb.append(' ');
        }
        sb.append(')');
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        sb.append("DataUnionOf(");
        for (OWLDataRange rng : node.getOperands()) {
            rng.accept(this);
            sb.append(' ');
        }
        sb.append(')');
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        sb.append(shortFormProvider.getShortForm(property));
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        sb.append("AnnotationPropertyDomain(");
        axiom.getProperty().accept(this);
        sb.append(' ');
        axiom.getDomain().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        sb.append("AnnotationPropertyRange(");
        axiom.getProperty().accept(this);
        sb.append(' ');
        axiom.getRange().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        sb.append("SubAnnotationPropertyOf(");
        writeAnnotations(axiom);
        axiom.getSubProperty().accept(this);
        sb.append(' ');
        axiom.getSuperProperty().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        sb.append(individual.getID());
    }

    @Override
    public void visit(IRI iri) {
        sb.append('<');
        sb.append(iri);
        sb.append('>');
    }

    @Override
    public void visit(OWLAnnotation node) {
        sb.append("Annotation(");
        Set<OWLAnnotation> annos = node.getAnnotations();
        for (OWLAnnotation anno : annos) {
            anno.accept(this);
            sb.append(' ');
        }
        node.getProperty().accept(this);
        sb.append(' ');
        node.getValue().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(SWRLRule rule) {
        sb.append("DLSafeRule(");
        writeAnnotations(rule);
        sb.append(" Body(");
        render(rule.getBody());
        sb.append(')');
        sb.append(" Head(");
        render(rule.getHead());
        sb.append(')');
        sb.append(" )");
    }

    @Override
    public void visit(SWRLClassAtom node) {
        sb.append("ClassAtom(");
        node.getPredicate().accept(this);
        sb.append(' ');
        node.getArgument().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        sb.append("DataRangeAtom(");
        node.getPredicate().accept(this);
        sb.append(' ');
        node.getArgument().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        sb.append("DifferentFromAtom(");
        node.getFirstArgument().accept(this);
        sb.append(' ');
        node.getSecondArgument().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        sb.append("SameAsAtom(");
        node.getFirstArgument().accept(this);
        sb.append(' ');
        node.getSecondArgument().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        sb.append("ObjectPropertyAtom(");
        node.getPredicate().accept(this);
        sb.append(' ');
        node.getFirstArgument().accept(this);
        sb.append(' ');
        node.getSecondArgument().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        sb.append("DataPropertyAtom(");
        node.getPredicate().accept(this);
        sb.append(' ');
        node.getFirstArgument().accept(this);
        sb.append(' ');
        node.getSecondArgument().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        sb.append("BuiltInAtom(");
        sb.append(getShortForm(node.getPredicate()));
        sb.append(' ');
        for (SWRLArgument arg : node.getArguments()) {
            arg.accept(this);
            sb.append(' ');
        }
        sb.append(')');
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        sb.append("DatatypeDefinition(");
        writeAnnotations(axiom);
        axiom.getDatatype().accept(this);
        sb.append(' ');
        axiom.getDataRange().accept(this);
        sb.append(')');
    }

    @Override
    public void visit(SWRLVariable node) {
        sb.append("Variable(");
        sb.append(getShortForm(node.getIRI()));
        sb.append(')');
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
