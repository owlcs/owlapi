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
package org.semanticweb.owlapi.owlxml.renderer;

import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY_RANGE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANONYMOUS_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ASYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.BODY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.BUILT_IN_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.CLASS;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.CLASS_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.CLASS_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATATYPE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATATYPE_DEFINITION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATATYPE_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_ALL_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_EXACT_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_HAS_VALUE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_INTERSECTION_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_MAX_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_MIN_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_ONE_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY_RANGE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_RANGE_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_SOME_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_UNION_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DECLARATION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DIFFERENT_INDIVIDUALS;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DIFFERENT_INDIVIDUALS_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DISJOINT_CLASSES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DISJOINT_DATA_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DISJOINT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DISJOINT_UNION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DL_SAFE_RULE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.EQUIVALENT_CLASSES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.EQUIVALENT_DATA_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.EQUIVALENT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.FACET_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.FUNCTIONAL_DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.HAS_KEY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.HEAD;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.IMPORT;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.INVERSE_FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.INVERSE_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.IRREFLEXIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.LITERAL;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.NAMED_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.NEGATIVE_DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.NEGATIVE_OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_ALL_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_EXACT_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_HAS_SELF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_HAS_VALUE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_INTERSECTION_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_INVERSE_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_MAX_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_MIN_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_ONE_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_CHAIN;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_RANGE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_SOME_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_UNION_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.REFLEXIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SAME_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SAME_INDIVIDUAL_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SUB_ANNOTATION_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SUB_CLASS_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SUB_DATA_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SUB_OBJECT_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.TRANSITIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.VARIABLE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
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
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatImpl;
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
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
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
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLXMLObjectRenderer implements OWLObjectVisitor {

    private final OWLXMLWriter writer;

    /**
     * @param writer writer
     */
    public OWLXMLObjectRenderer(@Nonnull OWLXMLWriter writer) {
        this.writer = checkNotNull(writer, "writer cannot be null");
    }

    private void writeAnnotations(@Nonnull OWLAxiom axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        for (OWLAnnotation anno : sortOptionally(axiom.getAnnotations())) {
            anno.accept(this);
        }
    }

    @Override
    public void visit(OWLOntology ontology) {
        checkNotNull(ontology, "ontology cannot be null");
        ontology.getImportsDeclarations().stream().sorted().forEach(decl -> {
            writer.writeStartElement(IMPORT);
            writer.writeTextContent(decl.getIRI().toString());
            writer.writeEndElement();
        });
        for (OWLAnnotation annotation : sortOptionally(ontology.getAnnotations())) {
            annotation.accept(this);
        }
        // treat declarations separately from other axioms
        List<OWLDeclarationAxiom> declarations =
            new ArrayList<>(ontology.getAxioms(AxiomType.DECLARATION));
        declarations.sort(null);
        Set<OWLEntity> declared = new HashSet<>(ontology.getSignature());
        for (OWLDeclarationAxiom ax : declarations) {
            ax.accept(this);
            declared.remove(ax.getEntity());
        }
        // any undeclared entities?
        if (!declared.isEmpty()) {
            boolean addMissing = true;
            OWLDocumentFormat format = ontology.getOWLOntologyManager().getOntologyFormat(ontology);
            if (format != null) {
                addMissing = format.isAddMissingTypes();
            }
            if (addMissing) {
                Collection<IRI> illegalPunnings = OWLDocumentFormatImpl.determineIllegalPunnings(
                    addMissing, ontology.getSignature(), ontology.getPunnedIRIs(Imports.INCLUDED));
                for (OWLEntity e : declared) {
                    if (!e.isBuiltIn() && !illegalPunnings.contains(e.getIRI())
                        && !ontology.isDeclared(e, Imports.INCLUDED)) {
                        ontology.getOWLOntologyManager().getOWLDataFactory()
                            .getOWLDeclarationAxiom(e).accept(this);
                    }
                }
            }
        }
        Collection<OWLAxiom> axioms = new ArrayList<>();
        for (AxiomType<?> t : AxiomType.AXIOM_TYPES) {
            if (!t.equals(AxiomType.DECLARATION)) {
                axioms.addAll(ontology.getAxioms(t));
            }
        }
        for (OWLAxiom ax : CollectionFactory.sortOptionally(axioms)) {
            ax.accept(this);
        }
    }

    @Override
    public void visit(IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        writer.writeIRIElement(iri);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        writer.writeStartElement(ANONYMOUS_INDIVIDUAL);
        writer.writeNodeIDAttribute(individual.getID());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        writer.writeStartElement(ASYMMETRIC_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        writer.writeStartElement(CLASS_ASSERTION);
        writeAnnotations(axiom);
        axiom.getClassExpression().accept(this);
        axiom.getIndividual().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        writer.writeStartElement(DATA_PROPERTY_ASSERTION);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getObject().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        writer.writeStartElement(DATA_PROPERTY_DOMAIN);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        writer.writeStartElement(DATA_PROPERTY_RANGE);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        writer.writeStartElement(SUB_DATA_PROPERTY_OF);
        writeAnnotations(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        writer.writeStartElement(DECLARATION);
        writeAnnotations(axiom);
        axiom.getEntity().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        writer.writeStartElement(DIFFERENT_INDIVIDUALS);
        writeAnnotations(axiom);
        render(axiom.getIndividuals());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        writer.writeStartElement(DISJOINT_CLASSES);
        writeAnnotations(axiom);
        render(axiom.getClassExpressions());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        writer.writeStartElement(DISJOINT_DATA_PROPERTIES);
        writeAnnotations(axiom);
        render(axiom.getProperties());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        writer.writeStartElement(DISJOINT_OBJECT_PROPERTIES);
        writeAnnotations(axiom);
        render(axiom.getProperties());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        writer.writeStartElement(DISJOINT_UNION);
        writeAnnotations(axiom);
        axiom.getOWLClass().accept(this);
        render(axiom.getClassExpressions());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        writer.writeStartElement(ANNOTATION_ASSERTION);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getValue().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_CLASSES);
        writeAnnotations(axiom);
        render(axiom.getClassExpressions());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_DATA_PROPERTIES);
        writeAnnotations(axiom);
        render(axiom.getProperties());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_OBJECT_PROPERTIES);
        writeAnnotations(axiom);
        render(axiom.getProperties());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        writer.writeStartElement(FUNCTIONAL_DATA_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        writer.writeStartElement(FUNCTIONAL_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writer.writeStartElement(INVERSE_FUNCTIONAL_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        writer.writeStartElement(INVERSE_OBJECT_PROPERTIES);
        writeAnnotations(axiom);
        axiom.getFirstProperty().accept(this);
        axiom.getSecondProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        writer.writeStartElement(IRREFLEXIVE_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        writer.writeStartElement(NEGATIVE_DATA_PROPERTY_ASSERTION);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getObject().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        writer.writeStartElement(NEGATIVE_OBJECT_PROPERTY_ASSERTION);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getObject().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writer.writeStartElement(OBJECT_PROPERTY_ASSERTION);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getObject().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        writer.writeStartElement(SUB_OBJECT_PROPERTY_OF);
        writeAnnotations(axiom);
        writer.writeStartElement(OBJECT_PROPERTY_CHAIN);
        for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            prop.accept(this);
        }
        writer.writeEndElement();
        axiom.getSuperProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writer.writeStartElement(OBJECT_PROPERTY_DOMAIN);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writer.writeStartElement(OBJECT_PROPERTY_RANGE);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        writer.writeStartElement(SUB_OBJECT_PROPERTY_OF);
        writeAnnotations(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        writer.writeStartElement(REFLEXIVE_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        writer.writeStartElement(SAME_INDIVIDUAL);
        writeAnnotations(axiom);
        render(axiom.getIndividuals());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        writer.writeStartElement(SUB_CLASS_OF);
        writeAnnotations(axiom);
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        writer.writeStartElement(SYMMETRIC_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        writer.writeStartElement(TRANSITIVE_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLClass ce) {
        writer.writeStartElement(CLASS);
        writer.writeIRIAttribute(ce.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        writer.writeStartElement(DATA_ALL_VALUES_FROM);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        writer.writeStartElement(DATA_EXACT_CARDINALITY);
        writer.writeCardinalityAttribute(ce.getCardinality());
        ce.getProperty().accept(this);
        if (ce.isQualified()) {
            ce.getFiller().accept(this);
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        writer.writeStartElement(DATA_MAX_CARDINALITY);
        writer.writeCardinalityAttribute(ce.getCardinality());
        ce.getProperty().accept(this);
        if (ce.isQualified()) {
            ce.getFiller().accept(this);
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        writer.writeStartElement(DATA_MIN_CARDINALITY);
        writer.writeCardinalityAttribute(ce.getCardinality());
        ce.getProperty().accept(this);
        if (ce.isQualified()) {
            ce.getFiller().accept(this);
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        writer.writeStartElement(DATA_SOME_VALUES_FROM);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        writer.writeStartElement(DATA_HAS_VALUE);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writer.writeStartElement(OBJECT_ALL_VALUES_FROM);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        writer.writeStartElement(OBJECT_COMPLEMENT_OF);
        ce.getOperand().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        writer.writeStartElement(OBJECT_EXACT_CARDINALITY);
        writer.writeCardinalityAttribute(ce.getCardinality());
        ce.getProperty().accept(this);
        if (ce.isQualified()) {
            ce.getFiller().accept(this);
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        writer.writeStartElement(OBJECT_INTERSECTION_OF);
        render(ce.getOperands());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        writer.writeStartElement(OBJECT_MAX_CARDINALITY);
        writer.writeCardinalityAttribute(ce.getCardinality());
        ce.getProperty().accept(this);
        if (ce.isQualified()) {
            ce.getFiller().accept(this);
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        writer.writeStartElement(OBJECT_MIN_CARDINALITY);
        writer.writeCardinalityAttribute(ce.getCardinality());
        ce.getProperty().accept(this);
        if (ce.isQualified()) {
            ce.getFiller().accept(this);
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        writer.writeStartElement(OBJECT_ONE_OF);
        render(ce.getIndividuals());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        writer.writeStartElement(OBJECT_HAS_SELF);
        ce.getProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writer.writeStartElement(OBJECT_SOME_VALUES_FROM);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        writer.writeStartElement(OBJECT_UNION_OF);
        render(ce.getOperands());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        writer.writeStartElement(OBJECT_HAS_VALUE);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        writer.writeStartElement(DATA_COMPLEMENT_OF);
        node.getDataRange().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataOneOf node) {
        writer.writeStartElement(DATA_ONE_OF);
        render(node.getValues());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDatatype node) {
        writer.writeStartElement(DATATYPE);
        writer.writeIRIAttribute(node.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        writer.writeStartElement(DATATYPE_RESTRICTION);
        node.getDatatype().accept(this);
        render(node.getFacetRestrictions());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        writer.writeStartElement(FACET_RESTRICTION);
        writer.writeFacetAttribute(node.getFacet());
        node.getFacetValue().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLLiteral node) {
        writer.writeStartElement(LITERAL);
        if (node.hasLang()) {
            writer.writeLangAttribute(node.getLang());
        } else if (!node.isRDFPlainLiteral()
            && !OWL2Datatype.XSD_STRING.getIRI().equals(node.getDatatype().getIRI())) {
            writer.writeDatatypeAttribute(node.getDatatype());
        }
        writer.writeTextContent(node.getLiteral());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataProperty property) {
        writer.writeStartElement(DATA_PROPERTY);
        writer.writeIRIAttribute(property.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectProperty property) {
        writer.writeStartElement(OBJECT_PROPERTY);
        writer.writeIRIAttribute(property.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        writer.writeStartElement(OBJECT_INVERSE_OF);
        property.getInverse().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        writer.writeStartElement(NAMED_INDIVIDUAL);
        writer.writeIRIAttribute(individual.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        writer.writeStartElement(HAS_KEY);
        writeAnnotations(axiom);
        axiom.getClassExpression().accept(this);
        render(axiom.getObjectPropertyExpressions());
        render(axiom.getDataPropertyExpressions());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        writer.writeStartElement(DATA_INTERSECTION_OF);
        render(node.getOperands());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        writer.writeStartElement(DATA_UNION_OF);
        render(node.getOperands());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        writer.writeStartElement(ANNOTATION_PROPERTY);
        writer.writeIRIAttribute(property.getIRI());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAnnotation node) {
        writer.writeStartElement(ANNOTATION);
        for (OWLAnnotation anno : sortOptionally(node.getAnnotations())) {
            anno.accept(this);
        }
        node.getProperty().accept(this);
        node.getValue().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        writer.writeStartElement(ANNOTATION_PROPERTY_DOMAIN);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        writer.writeStartElement(ANNOTATION_PROPERTY_RANGE);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        writer.writeStartElement(SUB_ANNOTATION_PROPERTY_OF);
        writeAnnotations(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        writer.writeStartElement(DATATYPE_DEFINITION);
        writeAnnotations(axiom);
        axiom.getDatatype().accept(this);
        axiom.getDataRange().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLRule rule) {
        writer.writeStartElement(DL_SAFE_RULE);
        writeAnnotations(rule);
        writer.writeStartElement(BODY);
        for (SWRLAtom atom : rule.getBody()) {
            atom.accept(this);
        }
        writer.writeEndElement();
        writer.writeStartElement(HEAD);
        for (SWRLAtom atom : rule.getHead()) {
            atom.accept(this);
        }
        writer.writeEndElement();
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLClassAtom node) {
        writer.writeStartElement(CLASS_ATOM);
        node.getPredicate().accept(this);
        node.getArgument().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        writer.writeStartElement(DATA_RANGE_ATOM);
        node.getPredicate().accept(this);
        node.getArgument().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        writer.writeStartElement(OBJECT_PROPERTY_ATOM);
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        writer.writeStartElement(DATA_PROPERTY_ATOM);
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        writer.writeStartElement(BUILT_IN_ATOM);
        writer.writeIRIAttribute(node.getPredicate());
        for (SWRLDArgument arg : node.getArguments()) {
            arg.accept(this);
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLVariable node) {
        writer.writeStartElement(VARIABLE);
        if ("urn:swrl:var#".equals(node.getIRI().getNamespace())
            || "urn:swrl#".equals(node.getIRI().getNamespace())) {
            writer.writeIRIAttribute(IRI.create("urn:swrl:var#", node.getIRI().getFragment()));
        } else {
            writer.writeIRIAttribute(node.getIRI());
        }
        writer.writeEndElement();
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
    public void visit(SWRLDifferentIndividualsAtom node) {
        writer.writeStartElement(DIFFERENT_INDIVIDUALS_ATOM);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        writer.writeStartElement(SAME_INDIVIDUAL_ATOM);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        writer.writeEndElement();
    }

    private void render(Set<? extends OWLObject> objects) {
        for (OWLObject obj : objects) {
            obj.accept(this);
        }
    }
}
