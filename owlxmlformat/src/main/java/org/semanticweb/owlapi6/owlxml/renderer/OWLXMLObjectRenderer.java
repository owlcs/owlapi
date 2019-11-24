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
package org.semanticweb.owlapi6.owlxml.renderer;

import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLFacetRestriction;
import org.semanticweb.owlapi6.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectType;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.model.SWRLBinaryAtom;
import org.semanticweb.owlapi6.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi6.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi6.model.SWRLIndividualArgument;
import org.semanticweb.owlapi6.model.SWRLLiteralArgument;
import org.semanticweb.owlapi6.model.SWRLRule;
import org.semanticweb.owlapi6.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi6.model.SWRLVariable;
import org.semanticweb.owlapi6.model.parameters.Imports;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.semanticweb.owlapi6.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLXMLObjectRenderer implements OWLObjectVisitor {

    private final OWLXMLWriter writer;
    private final OWLDataFactory df;

    /**
     * @param writer writer
     * @param df data factory
     */
    public OWLXMLObjectRenderer(OWLXMLWriter writer, OWLDataFactory df) {
        this.writer = checkNotNull(writer, "writer cannot be null");
        this.df = df;
    }

    @Override
    public void visit(OWLOntology ontology) {
        checkNotNull(ontology, "ontology cannot be null");
        ontology.importsDeclarations().sorted().forEach(decl -> {
            writer.writeStartElement(OWLXMLVocabulary.IMPORT);
            writer.writeTextContent(decl.getIRI().toString());
            writer.writeEndElement();
        });
        ontology.annotationsAsList().forEach(this::accept);
        // treat declarations separately from other axioms
        Set<OWLEntity> declared = asUnorderedSet(ontology.unsortedSignature());
        ontology.axioms(AxiomType.DECLARATION).sorted().forEach(ax -> {
            ax.accept(this);
            declared.remove(ax.getEntity());
        });
        // any undeclared entities?
        if (!declared.isEmpty()) {
            boolean addMissing =
                ontology.getOWLOntologyManager().getOntologyConfigurator().shouldAddMissingTypes();
            if (addMissing) {
                Collection<IRI> illegalPunnings = ontology.determineIllegalPunnings(addMissing);
                for (OWLEntity e : declared) {
                    if (!e.isBuiltIn() && !illegalPunnings.contains(e.getIRI())
                        && !ontology.isDeclared(e, Imports.INCLUDED)) {
                        ontology.getOWLOntologyManager().getOWLDataFactory()
                            .getOWLDeclarationAxiom(e).accept(this);
                    }
                }
            }
        }
        Stream<? extends OWLAxiom> flatMap =
            AxiomType.skipDeclarations().flatMap(t -> ontology.axioms(t));
        flatMap.distinct().sorted().forEach(this::accept);
    }

    private void accept(OWLObject o) {
        o.accept(this);
    }

    private static EnumMap<OWLObjectType, OWLXMLVocabulary> tags = tags();

    private static EnumMap<OWLObjectType, OWLXMLVocabulary> tags() {
        EnumMap<OWLObjectType, OWLXMLVocabulary> map = new EnumMap<>(OWLObjectType.class);
        map.put(OWLObjectType.ASYMMETRIC, OWLXMLVocabulary.ASYMMETRIC_OBJECT_PROPERTY);
        map.put(OWLObjectType.CLASS_ASSERTION, OWLXMLVocabulary.CLASS_ASSERTION);
        map.put(OWLObjectType.DATA_ASSERTION, OWLXMLVocabulary.DATA_PROPERTY_ASSERTION);
        map.put(OWLObjectType.DATA_DOMAIN, OWLXMLVocabulary.DATA_PROPERTY_DOMAIN);
        map.put(OWLObjectType.DATA_RANGE, OWLXMLVocabulary.DATA_PROPERTY_RANGE);
        map.put(OWLObjectType.SUB_DATA, OWLXMLVocabulary.SUB_DATA_PROPERTY_OF);
        map.put(OWLObjectType.DECLARATION, OWLXMLVocabulary.DECLARATION);
        map.put(OWLObjectType.DIFFERENT_INDIVIDUALS, OWLXMLVocabulary.DIFFERENT_INDIVIDUALS);
        map.put(OWLObjectType.DISJOINT_CLASSES, OWLXMLVocabulary.DISJOINT_CLASSES);
        map.put(OWLObjectType.DISJOINT_DATA, OWLXMLVocabulary.DISJOINT_DATA_PROPERTIES);
        map.put(OWLObjectType.DISJOINT_OBJECT, OWLXMLVocabulary.DISJOINT_OBJECT_PROPERTIES);
        map.put(OWLObjectType.DISJOINT_UNION, OWLXMLVocabulary.DISJOINT_UNION);
        map.put(OWLObjectType.ANNOTATION_ASSERTION, OWLXMLVocabulary.ANNOTATION_ASSERTION);
        map.put(OWLObjectType.EQUIVALENT_CLASSES, OWLXMLVocabulary.EQUIVALENT_CLASSES);
        map.put(OWLObjectType.EQUIVALENT_DATA, OWLXMLVocabulary.EQUIVALENT_DATA_PROPERTIES);
        map.put(OWLObjectType.EQUIVALENT_OBJECT, OWLXMLVocabulary.EQUIVALENT_OBJECT_PROPERTIES);
        map.put(OWLObjectType.FUNCTIONAL_DATA, OWLXMLVocabulary.FUNCTIONAL_DATA_PROPERTY);
        map.put(OWLObjectType.FUNCTIONAL_OBJECT, OWLXMLVocabulary.FUNCTIONAL_OBJECT_PROPERTY);
        map.put(OWLObjectType.INVERSE_FUNCTIONAL,
            OWLXMLVocabulary.INVERSE_FUNCTIONAL_OBJECT_PROPERTY);
        map.put(OWLObjectType.INVERSE, OWLXMLVocabulary.INVERSE_OBJECT_PROPERTIES);
        map.put(OWLObjectType.IRREFLEXIVE, OWLXMLVocabulary.IRREFLEXIVE_OBJECT_PROPERTY);
        map.put(OWLObjectType.NEGATIVE_DATA_ASSERTION,
            OWLXMLVocabulary.NEGATIVE_DATA_PROPERTY_ASSERTION);
        map.put(OWLObjectType.NEGATIVE_OBJECT_ASSERTION,
            OWLXMLVocabulary.NEGATIVE_OBJECT_PROPERTY_ASSERTION);
        map.put(OWLObjectType.OBJECT_ASSERTION, OWLXMLVocabulary.OBJECT_PROPERTY_ASSERTION);
        map.put(OWLObjectType.OBJECT_DOMAIN, OWLXMLVocabulary.OBJECT_PROPERTY_DOMAIN);
        map.put(OWLObjectType.OBJECT_RANGE, OWLXMLVocabulary.OBJECT_PROPERTY_RANGE);
        map.put(OWLObjectType.SUB_OBJECT, OWLXMLVocabulary.SUB_OBJECT_PROPERTY_OF);
        map.put(OWLObjectType.REFLEXIVE, OWLXMLVocabulary.REFLEXIVE_OBJECT_PROPERTY);
        map.put(OWLObjectType.SAME_INDIVIDUAL, OWLXMLVocabulary.SAME_INDIVIDUAL);
        map.put(OWLObjectType.SUB_CLASS, OWLXMLVocabulary.SUB_CLASS_OF);
        map.put(OWLObjectType.SYMMETRIC, OWLXMLVocabulary.SYMMETRIC_OBJECT_PROPERTY);
        map.put(OWLObjectType.TRANSITIVE, OWLXMLVocabulary.TRANSITIVE_OBJECT_PROPERTY);
        map.put(OWLObjectType.CLASS, OWLXMLVocabulary.CLASS);
        map.put(OWLObjectType.FORALL_DATA, OWLXMLVocabulary.DATA_ALL_VALUES_FROM);
        map.put(OWLObjectType.EXACT_DATA, OWLXMLVocabulary.DATA_EXACT_CARDINALITY);
        map.put(OWLObjectType.MAX_DATA, OWLXMLVocabulary.DATA_MAX_CARDINALITY);
        map.put(OWLObjectType.MIN_DATA, OWLXMLVocabulary.DATA_MIN_CARDINALITY);
        map.put(OWLObjectType.SOME_DATA, OWLXMLVocabulary.DATA_SOME_VALUES_FROM);
        map.put(OWLObjectType.HASVALUE_DATA, OWLXMLVocabulary.DATA_HAS_VALUE);
        map.put(OWLObjectType.FORALL_OBJECT, OWLXMLVocabulary.OBJECT_ALL_VALUES_FROM);
        map.put(OWLObjectType.NOT_OBJECT, OWLXMLVocabulary.OBJECT_COMPLEMENT_OF);
        map.put(OWLObjectType.EXACT_OBJECT, OWLXMLVocabulary.OBJECT_EXACT_CARDINALITY);
        map.put(OWLObjectType.AND_OBJECT, OWLXMLVocabulary.OBJECT_INTERSECTION_OF);
        map.put(OWLObjectType.MAX_OBJECT, OWLXMLVocabulary.OBJECT_MAX_CARDINALITY);
        map.put(OWLObjectType.MIN_OBJECT, OWLXMLVocabulary.OBJECT_MIN_CARDINALITY);
        map.put(OWLObjectType.ONEOF_OBJECT, OWLXMLVocabulary.OBJECT_ONE_OF);
        map.put(OWLObjectType.HASSELF_OBJECT, OWLXMLVocabulary.OBJECT_HAS_SELF);
        map.put(OWLObjectType.SOME_OBJECT, OWLXMLVocabulary.OBJECT_SOME_VALUES_FROM);
        map.put(OWLObjectType.OR_OBJECT, OWLXMLVocabulary.OBJECT_UNION_OF);
        map.put(OWLObjectType.HASVALUE_OBJECT, OWLXMLVocabulary.OBJECT_HAS_VALUE);
        map.put(OWLObjectType.NOT_DATA, OWLXMLVocabulary.DATA_COMPLEMENT_OF);
        map.put(OWLObjectType.ONEOF_DATA, OWLXMLVocabulary.DATA_ONE_OF);
        map.put(OWLObjectType.DATATYPE, OWLXMLVocabulary.DATATYPE);
        map.put(OWLObjectType.DATATYPE_RESTRICTION, OWLXMLVocabulary.DATATYPE_RESTRICTION);
        map.put(OWLObjectType.DATA_PROPERTY, OWLXMLVocabulary.DATA_PROPERTY);
        map.put(OWLObjectType.OBJECT_PROPERTY, OWLXMLVocabulary.OBJECT_PROPERTY);
        map.put(OWLObjectType.INVERSE_OBJECT, OWLXMLVocabulary.OBJECT_INVERSE_OF);
        map.put(OWLObjectType.NAMED_INDIVIDUAL, OWLXMLVocabulary.NAMED_INDIVIDUAL);
        map.put(OWLObjectType.AND_DATA, OWLXMLVocabulary.DATA_INTERSECTION_OF);
        map.put(OWLObjectType.OR_DATA, OWLXMLVocabulary.DATA_UNION_OF);
        map.put(OWLObjectType.ANNOTATION_PROPERTY, OWLXMLVocabulary.ANNOTATION_PROPERTY);
        map.put(OWLObjectType.ANNOTATION, OWLXMLVocabulary.ANNOTATION);
        map.put(OWLObjectType.ANNOTATION_DOMAIN, OWLXMLVocabulary.ANNOTATION_PROPERTY_DOMAIN);
        map.put(OWLObjectType.ANNOTATION_RANGE, OWLXMLVocabulary.ANNOTATION_PROPERTY_RANGE);
        map.put(OWLObjectType.SUB_ANNOTATION, OWLXMLVocabulary.SUB_ANNOTATION_PROPERTY_OF);
        map.put(OWLObjectType.DATATYPE_DEFINITION, OWLXMLVocabulary.DATATYPE_DEFINITION);
        map.put(OWLObjectType.SWRL_CLASS, OWLXMLVocabulary.CLASS_ATOM);
        map.put(OWLObjectType.SWRL_DATA_RANGE, OWLXMLVocabulary.DATA_RANGE_ATOM);
        map.put(OWLObjectType.SWRL_OBJECT_PROPERTY, OWLXMLVocabulary.OBJECT_PROPERTY_ATOM);
        map.put(OWLObjectType.SWRL_DATA_PROPERTY, OWLXMLVocabulary.DATA_PROPERTY_ATOM);
        map.put(OWLObjectType.SWRL_SAME_INDIVIDUAL, OWLXMLVocabulary.SAME_INDIVIDUAL_ATOM);
        map.put(OWLObjectType.SWRL_DIFFERENT_INDIVIDUAL,
            OWLXMLVocabulary.DIFFERENT_INDIVIDUALS_ATOM);
        return map;
    }

    private void swrlInd(SWRLBinaryAtom<?, ?> node) {
        writer.writeStartElement(tags.get(node.type()));
        accept(node.getFirstArgument());
        accept(node.getSecondArgument());
        writer.writeEndElement();
    }

    private void axiom(OWLXMLVocabulary v, List<? extends OWLObject> o) {
        writer.writeStartElement(v);
        o.forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        writer.writeStartElement(tags.get(axiom.type()));
        axiom.annotationsAsList().forEach(this::accept);
        accept(axiom.getEntity());
        writer.writeEndElement();
    }

    private void iri(OWLEntity o) {
        writer.writeStartElement(tags.get(o.type()));
        writer.writeIRIAttribute(o.getIRI());
        writer.writeEndElement();
    }

    private void card(OWLCardinalityRestriction<? extends OWLObject> ce) {
        writer.writeStartElement(tags.get(ce.type()));
        writer.writeCardinalityAttribute(ce.getCardinality());
        accept(ce.getProperty());
        if (ce.isQualified()) {
            accept(ce.getFiller());
        }
        writer.writeEndElement();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doDefault(OWLObject object) {
        if (object instanceof OWLCardinalityRestriction) {
            card((OWLCardinalityRestriction<? extends OWLObject>) object);
            return;
        }
        if (object instanceof OWLEntity) {
            iri((OWLEntity) object);
            return;
        }
        writer.writeStartElement(tags.get(object.type()));
        object.componentsAnnotationsFirst().forEach(this::render);
        writer.writeEndElement();
    }

    @SuppressWarnings("unchecked")
    private void render(Object o) {
        if (o instanceof Collection) {
            ((Collection<? extends OWLObject>) o).forEach(this::accept);
        } else if (o instanceof OWLObject) {
            accept((OWLObject) o);
        }
    }

    @Override
    public void visit(IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        writer.writeIRIElement(iri);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        writer.writeStartElement(OWLXMLVocabulary.ANONYMOUS_INDIVIDUAL);
        writer.writeNodeIDAttribute(individual.getID());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        writer.writeStartElement(OWLXMLVocabulary.SUB_OBJECT_PROPERTY_OF);
        axiom.annotationsAsList().forEach(this::accept);
        axiom(OWLXMLVocabulary.OBJECT_PROPERTY_CHAIN, axiom.getPropertyChain());
        accept(axiom.getSuperProperty());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        writer.writeStartElement(OWLXMLVocabulary.FACET_RESTRICTION);
        writer.writeFacetAttribute(node.getFacet());
        accept(node.getFacetValue());
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLLiteral node) {
        writer.writeStartElement(OWLXMLVocabulary.LITERAL);
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
    public void visit(OWLHasKeyAxiom axiom) {
        writer.writeStartElement(OWLXMLVocabulary.HAS_KEY);
        axiom.annotationsAsList().forEach(this::accept);
        accept(axiom.getClassExpression());
        axiom.objectPropertyExpressionsAsList().forEach(this::accept);
        axiom.dataPropertyExpressionsAsList().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(OWLAnnotation node) {
        writer.writeStartElement(tags.get(node.type()));
        node.annotationsAsList().forEach(this::accept);
        accept(node.getProperty());
        accept(node.getValue());
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLRule rule) {
        writer.writeStartElement(OWLXMLVocabulary.DL_SAFE_RULE);
        rule.annotationsAsList().forEach(this::accept);
        axiom(OWLXMLVocabulary.BODY, rule.bodyList());
        axiom(OWLXMLVocabulary.HEAD, rule.headList());
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        writer.writeStartElement(OWLXMLVocabulary.BUILT_IN_ATOM);
        writer.writeIRIAttribute(node.getPredicate());
        node.argumentsAsList().forEach(this::accept);
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLVariable node) {
        writer.writeStartElement(OWLXMLVocabulary.VARIABLE);
        if ("urn:swrl:var#".equals(node.getIRI().getNamespace())
            || "urn:swrl#".equals(node.getIRI().getNamespace())) {
            writer.writeIRIAttribute(df.getIRI("urn:swrl:var#", node.getIRI().getFragment()));
        } else {
            writer.writeIRIAttribute(node.getIRI());
        }
        writer.writeEndElement();
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        accept(node.getIndividual());
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        accept(node.getLiteral());
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        swrlInd(node);
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        swrlInd(node);
    }
}
