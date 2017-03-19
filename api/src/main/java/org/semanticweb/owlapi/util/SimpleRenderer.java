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
import java.util.stream.Stream;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * A simple renderer that can be used for debugging purposes and provide an implementation of the
 * toString method for different implementations.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class SimpleRenderer implements OWLObjectVisitor, OWLObjectRenderer {

    private StringBuilder sb;
    private ShortFormProvider shortFormProvider;
    private IRIShortFormProvider iriShortFormProvider;

    /**
     * Default constructor.
     */
    @SuppressWarnings("null")
    public SimpleRenderer() {
        sb = new StringBuilder();
        resetShortFormProvider();
    }

    /**
     * reset the renderer.
     */
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
     * Resets the short form provider to the default short form provider, which is a PrefixManager
     * with the default set of prefixes.
     */
    public final void resetShortFormProvider() {
        DefaultPrefixManager defaultPrefixManager = new DefaultPrefixManager();
        shortFormProvider = defaultPrefixManager;
        iriShortFormProvider = defaultPrefixManager;
    }

    /**
     * Resets the short form provider and adds prefix name to prefix mappings based on the specified
     * ontology's format (if it is a prefix format) and possibly the ontologies in the imports
     * closure.
     *
     * @param ontology The ontology whose format will be used to obtain prefix mappings
     * @param processImportedOntologies Specifies whether or not the prefix mapping should be
     *        obtained from imported ontologies.
     */
    public void setPrefixesFromOntologyFormat(OWLOntology ontology,
        boolean processImportedOntologies) {
        resetShortFormProvider();
        Imports.fromBoolean(processImportedOntologies).stream(ontology)
            .forEach(o -> copyPrefixes(o.getPrefixManager()));
    }

    private void copyPrefixes(PrefixManager ontologyPrefixes) {
        if (!isUsingDefaultShortFormProvider()) {
            resetShortFormProvider();
        }
        ((DefaultPrefixManager) shortFormProvider).copyPrefixesFrom(ontologyPrefixes);
    }

    /**
     * Sets a prefix name for a given prefix. Note that prefix names MUST end with a colon.
     *
     * @param prefixName The prefix name (ending with a colon)
     * @param prefix The prefix that the prefix name maps to
     */
    public void setPrefix(String prefixName, String prefix) {
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
     * @param iri the iri to shorten
     * @return the short form
     */
    public String getShortForm(IRI iri) {
        return iriShortFormProvider.getShortForm(iri);
    }

    @Override
    public String render(OWLObject object) {
        reset();
        object.accept(this);
        return sb.toString();
    }

    protected void render(Stream<? extends OWLObject> objects) {
        Iterator<? extends OWLObject> it = objects.iterator();
        while (it.hasNext()) {
            it.next().accept(this);
            if (it.hasNext()) {
                sb.append(' ');
            }
        }
    }

    @Override
    public void visit(OWLOntology ontology) {
        sb.append("Ontology(").append(ontology.getOntologyID()).append(" [Axioms: ")
            .append(ontology.getAxiomCount()).append("] [Logical axioms: ")
            .append(ontology.getLogicalAxiomCount()).append("])");
    }

    private void insertSpace() {
        sb.append(' ');
    }

    /**
     * @param axiom the axiom whose annotations should be written
     */
    public void writeAnnotations(OWLAxiom axiom) {
        axiom.annotations().forEach(a -> {
            a.accept(this);
            insertSpace();
        });
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
        render(axiom.classExpressions());
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
        render(axiom.properties());
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
        render(axiom.individuals());
        sb.append(" )");
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        sb.append("DisjointDataProperties(");
        writeAnnotations(axiom);
        render(axiom.properties());
        sb.append(" )");
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        sb.append("DisjointObjectProperties(");
        writeAnnotations(axiom);
        render(axiom.properties());
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
        render(axiom.classExpressions());
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
        render(axiom.properties());
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
        render(axiom.classExpressions());
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
        render(axiom.individuals());
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
        render(ce.operands());
        sb.append(')');
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        sb.append("ObjectUnionOf(");
        render(ce.operands());
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
        render(ce.individuals());
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
        render(node.values());
        sb.append(" )");
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        sb.append("DataRangeRestriction(");
        node.getDatatype().accept(this);
        node.facetRestrictions().forEach(r -> {
            insertSpace();
            r.accept(this);
        });
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
        if (node.isRDFPlainLiteral()
            || node.getDatatype().getIRI().equals(OWL2Datatype.RDF_LANG_STRING.getIRI())) {
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
        axiom.objectPropertyExpressions().forEach(p -> {
            p.accept(this);
            sb.append(' ');
        });
        sb.append(") (");
        axiom.dataPropertyExpressions().forEach(p -> {
            p.accept(this);
            sb.append(' ');
        });
        sb.append("))");
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        sb.append("DataIntersectionOf(");
        node.operands().forEach(r -> {
            r.accept(this);
            sb.append(' ');
        });
        sb.append(')');
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        sb.append("DataUnionOf(");
        node.operands().forEach(r -> {
            r.accept(this);
            sb.append(' ');
        });
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
        node.annotations().forEach(a -> {
            a.accept(this);
            sb.append(' ');
        });
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
        render(rule.body());
        sb.append(')');
        sb.append(" Head(");
        render(rule.head());
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
