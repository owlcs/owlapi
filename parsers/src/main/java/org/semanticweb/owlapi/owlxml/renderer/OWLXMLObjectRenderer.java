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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.*;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLXMLObjectRenderer implements OWLObjectVisitor {

    private final OWLXMLWriter writer;

    /**
     * @param writer
     *        writer
     */
    public OWLXMLObjectRenderer(OWLXMLWriter writer) {
        this.writer = checkNotNull(writer, "writer cannot be null");
    }

    private void writeAnnotations(OWLAxiom axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        render(axiom.annotations());
    }

    @Override
    public void visit(OWLOntology ontology) {
        checkNotNull(ontology, "ontology cannot be null");
        ontology.importsDeclarations().sorted().forEach(decl -> {
            writer.writeStartElement(IMPORT);
            writer.writeTextContent(decl.getIRI().toString());
            writer.writeEndElement();
        });
        render(ontology.annotations());
        // treat declarations separately from other axioms
        Set<OWLEntity> declared = asUnorderedSet(ontology.signature());
        ontology.axioms(AxiomType.DECLARATION).sorted().forEach(ax -> {
            ax.accept(this);
            declared.remove(ax.getEntity());
        });
        // any undeclared entities?
        if (!declared.isEmpty()) {
            OWLDocumentFormat format = ontology.getFormat();
            boolean addMissing = format.isAddMissingTypes();
            if (addMissing) {
                Collection<IRI> illegalPunnings = OWLDocumentFormat.determineIllegalPunnings(addMissing, ontology
                    .signature(), ontology.getPunnedIRIs(Imports.INCLUDED));
                for (OWLEntity e : declared) {
                    if (!e.isBuiltIn() && !illegalPunnings.contains(e.getIRI()) && !ontology.isDeclared(e,
                        Imports.INCLUDED)) {
                        ontology.getOWLOntologyManager().getOWLDataFactory().getOWLDeclarationAxiom(e).accept(this);
                    }
                }
            }
        }
        Stream<AxiomType<? extends OWLAxiom>> skipDeclarations = AxiomType.AXIOM_TYPES.stream().filter(t -> !t.equals(
            AxiomType.DECLARATION));
        Stream<? extends OWLAxiom> axioms = skipDeclarations.flatMap(t -> ontology.axioms(t));
        render(CollectionFactory.sortOptionally(axioms).stream());
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
        render(axiom.individuals());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        writer.writeStartElement(DISJOINT_CLASSES);
        writeAnnotations(axiom);
        render(axiom.classExpressions());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        writer.writeStartElement(DISJOINT_DATA_PROPERTIES);
        writeAnnotations(axiom);
        render(axiom.properties());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        writer.writeStartElement(DISJOINT_OBJECT_PROPERTIES);
        writeAnnotations(axiom);
        render(axiom.properties());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        writer.writeStartElement(DISJOINT_UNION);
        writeAnnotations(axiom);
        axiom.getOWLClass().accept(this);
        render(axiom.classExpressions());
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
        render(axiom.classExpressions());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_DATA_PROPERTIES);
        writeAnnotations(axiom);
        render(axiom.properties());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_OBJECT_PROPERTIES);
        writeAnnotations(axiom);
        render(axiom.properties());
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
        render(axiom.getPropertyChain().stream());
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
        render(axiom.individuals());
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
        render(ce.operands());
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
        render(ce.individuals());
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
        render(ce.operands());
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
        render(node.values());
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
        render(node.facetRestrictions());
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
        }
        writer.writeDatatypeAttribute(node.getDatatype());
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
        render(axiom.objectPropertyExpressions());
        render(axiom.dataPropertyExpressions());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        writer.writeStartElement(DATA_INTERSECTION_OF);
        render(node.operands());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        writer.writeStartElement(DATA_UNION_OF);
        render(node.operands());
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
        render(node.annotations());
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
        render(rule.body());
        writer.writeEndElement();
        writer.writeStartElement(HEAD);
        render(rule.head());
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
        render(node.arguments());
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLVariable node) {
        writer.writeStartElement(VARIABLE);
        writer.writeIRIAttribute(node.getIRI().getRemainder().get());
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

    private void render(Stream<? extends OWLObject> objects) {
        objects.forEach(a -> a.accept(this));
    }
}
