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
package org.semanticweb.owlapi6.functional.renderer;

import static org.semanticweb.owlapi6.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.vocab.OWLRDFVocabulary.RDFS_LABEL;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ANNOTATION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ANNOTATION_ASSERTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY_RANGE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ASYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.BODY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.BUILT_IN_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.CLASS;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.CLASS_ASSERTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.CLASS_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATATYPE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATATYPE_DEFINITION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATATYPE_RESTRICTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_ALL_VALUES_FROM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_COMPLEMENT_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_EXACT_CARDINALITY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_HAS_VALUE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_INTERSECTION_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_MAX_CARDINALITY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_MIN_CARDINALITY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_ONE_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_PROPERTY_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_PROPERTY_RANGE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_RANGE_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_SOME_VALUES_FROM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DATA_UNION_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DECLARATION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DIFFERENT_INDIVIDUALS;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DIFFERENT_INDIVIDUALS_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DISJOINT_CLASSES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DISJOINT_DATA_PROPERTIES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DISJOINT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DISJOINT_UNION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.DL_SAFE_RULE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.EQUIVALENT_CLASSES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.EQUIVALENT_DATA_PROPERTIES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.EQUIVALENT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.FUNCTIONAL_DATA_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.HAS_KEY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.HEAD;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.IMPORT;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.INVERSE_FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.INVERSE_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.IRREFLEXIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.NAMED_INDIVIDUAL;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.NEGATIVE_DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.NEGATIVE_OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_ALL_VALUES_FROM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_COMPLEMENT_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_EXACT_CARDINALITY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_HAS_SELF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_HAS_VALUE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_INTERSECTION_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_INVERSE_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_MAX_CARDINALITY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_MIN_CARDINALITY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_ONE_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_CHAIN;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_RANGE;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_SOME_VALUES_FROM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.OBJECT_UNION_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.ONTOLOGY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.REFLEXIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SAME_INDIVIDUAL;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SAME_INDIVIDUAL_ATOM;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SUB_ANNOTATION_PROPERTY_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SUB_CLASS_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SUB_DATA_PROPERTY_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SUB_OBJECT_PROPERTY_OF;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.SYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.TRANSITIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.vocab.OWLXMLVocabulary.VARIABLE;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.semanticweb.owlapi6.annotations.Renders;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.io.OWLObjectRenderer;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataComplementOf;
import org.semanticweb.owlapi6.model.OWLDataExactCardinality;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLDataHasValue;
import org.semanticweb.owlapi6.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi6.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi6.model.OWLDataMinCardinality;
import org.semanticweb.owlapi6.model.OWLDataOneOf;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataUnionOf;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi6.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFacetRestriction;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectComplementOf;
import org.semanticweb.owlapi6.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi6.model.OWLObjectHasSelf;
import org.semanticweb.owlapi6.model.OWLObjectHasValue;
import org.semanticweb.owlapi6.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi6.model.OWLObjectInverseOf;
import org.semanticweb.owlapi6.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi6.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi6.model.OWLObjectOneOf;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectUnionOf;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.OWLPropertyExpression;
import org.semanticweb.owlapi6.model.OWLPropertyRange;
import org.semanticweb.owlapi6.model.OWLQuantifiedDataRestriction;
import org.semanticweb.owlapi6.model.OWLQuantifiedObjectRestriction;
import org.semanticweb.owlapi6.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OntologyConfigurator;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi6.model.SWRLClassAtom;
import org.semanticweb.owlapi6.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi6.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi6.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi6.model.SWRLIndividualArgument;
import org.semanticweb.owlapi6.model.SWRLLiteralArgument;
import org.semanticweb.owlapi6.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi6.model.SWRLRule;
import org.semanticweb.owlapi6.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi6.model.SWRLVariable;
import org.semanticweb.owlapi6.model.parameters.Imports;
import org.semanticweb.owlapi6.utilities.PrefixManagerImpl;
import org.semanticweb.owlapi6.utilities.ShortFormProvider;
import org.semanticweb.owlapi6.utility.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi6.utility.EscapeUtils;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.semanticweb.owlapi6.vocab.OWLXMLVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class OWLObjectRenderer.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
@Renders(FunctionalSyntaxDocumentFormat.class)
public class FunctionalSyntaxObjectRenderer implements OWLObjectVisitor, OWLObjectRenderer {
    private static final Logger LOGGER =
        LoggerFactory.getLogger(FunctionalSyntaxObjectRenderer.class);
    protected final Optional<OWLOntology> ont;
    private final Writer writer;
    protected Optional<AnnotationValueShortFormProvider> labelMaker = Optional.empty();
    private Optional<PrefixManager> prefixManager = Optional.empty();
    private boolean writeEntitiesAsURIs = true;
    private boolean addMissingDeclarations = true;
    private boolean prettyPrint = false;
    private int tabIndex = 0;

    /** Empty constructor, for use from ToStringRenderer */
    @Inject
    public FunctionalSyntaxObjectRenderer() {
        this(null, new StringWriter());
    }

    /**
     * @param ontology the ontology
     * @param writer the writer
     */
    public FunctionalSyntaxObjectRenderer(@Nullable OWLOntology ontology, Writer writer) {
        ont = Optional.ofNullable(ontology);
        this.writer = writer;
        ont.ifPresent(this::initFromOntology);
    }

    private void initFromOntology(OWLOntology o) {
        prefixManager = Optional.of(new PrefixManagerImpl(o.getPrefixManager()));
        OntologyConfigurator config = o.getOWLOntologyManager().getOntologyConfigurator();
        addMissingDeclarations = config.shouldAddMissingTypes();
        prettyPrint = config.shouldPrettyPrintFunctionalSyntax();
        if (o.isNamed() && prefixManager.get().getDefaultPrefix() == null) {
            String existingDefault = prefixManager.get().getDefaultPrefix();
            String ontologyIRIString =
                o.getOntologyID().getOntologyIRI().map(Object::toString).orElse("");
            if (existingDefault == null || !existingDefault.startsWith(ontologyIRIString)) {
                String defaultPrefix = ontologyIRIString;
                if (!ontologyIRIString.endsWith("/") && !ontologyIRIString.endsWith("#")) {
                    defaultPrefix = ontologyIRIString + '#';
                }
                prefixManager.get().withDefaultPrefix(defaultPrefix);
            }
        }
        Map<OWLAnnotationProperty, List<String>> prefLangMap = new HashMap<>();
        OWLOntologyManager manager = o.getOWLOntologyManager();
        OWLDataFactory df = manager.getOWLDataFactory();
        OWLAnnotationProperty labelProp = df.getOWLAnnotationProperty(RDFS_LABEL.getIRI());
        labelMaker =
            Optional.of(new AnnotationValueShortFormProvider(Collections.singletonList(labelProp),
                prefLangMap, manager, prefixManager.get()));
    }

    /**
     * Set the add missing declaration flag.
     *
     * @param flag new value
     */
    public void setAddMissingDeclarations(boolean flag) {
        addMissingDeclarations = flag;
    }

    @Override
    public String render(OWLObject object) {
        object.accept(this);
        return writer.toString();
    }

    @Override
    public void setPrefixManager(PrefixManager prefixManager) {
        this.prefixManager = Optional.ofNullable(prefixManager);
    }

    @Override
    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        prefixManager.ifPresent(p -> p.setShortFormProvider(shortFormProvider));
    }

    protected void writePrefix(String prefix, String namespace) {
        write("Prefix");
        writeOpenBracket();
        write(prefix);
        write("=");
        write("<");
        write(namespace);
        write(">");
        writeCloseBracket();
        writeReturn();
    }

    protected void writePrefixes() {
        prefixManager.ifPresent(p -> p.getPrefixName2PrefixMap().forEach(this::writePrefix));
    }

    private void write(OWLXMLVocabulary v) {
        if (prettyPrint && tabIndex > 0) {
            space();
        }
        write(v.getShortForm());
    }

    private void write(String s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void write(IRI iri) {
        if (prefixManager.isPresent()) {
            String qname = prefixManager.get().getPrefixIRI(iri);
            if (qname != null) {
                boolean lastCharIsColon = qname.charAt(qname.length() - 1) == ':';
                if (!lastCharIsColon) {
                    write(qname);
                    return;
                }
            }
        }
        writeFullIRI(iri.toString());
    }

    private void writeFullIRI(String iri) {
        write("<");
        write(iri);
        write(">");
    }

    @Override
    public void visit(OWLOntology ontology) {
        writePrefixes();
        writeReturn();
        writeReturn();
        write(ONTOLOGY);
        writeOpenBracket();
        if (ontology.isNamed()) {
            writeFullIRI(
                ontology.getOntologyID().getOntologyIRI().map(Object::toString).orElse(""));
            Optional<IRI> versionIRI = ontology.getOntologyID().getVersionIRI();
            if (versionIRI.isPresent()) {
                writeReturn();
                writeFullIRI(versionIRI.map(Object::toString).orElse(""));
            }
            writeReturn();
        }
        ontology.importsDeclarations().forEach(decl -> {
            write(IMPORT);
            writeOpenBracket();
            writeFullIRI(decl.getIRI().toString());
            writeCloseBracket();
            writeReturn();
        });
        ontology.annotations().forEach(this::acceptAndReturn);
        writeReturn();
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        Collection<IRI> illegals = ontology.determineIllegalPunnings(addMissingDeclarations);
        ontology.signature().forEach(e -> writeDeclarations(e, writtenAxioms, illegals));
        writeSortedEntities(ontology, "Annotation Properties", "Annotation Property",
            ontology.annotationPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities(ontology, "Object Properties", "Object Property",
            ontology.objectPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities(ontology, "Data Properties", "Data Property",
            ontology.dataPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities(ontology, "Datatypes", "Datatype",
            ontology.datatypesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities(ontology, "Classes", "Class", ontology.classesInSignature(EXCLUDED),
            writtenAxioms);
        writeSortedEntities(ontology, "Named Individuals", "Individual",
            ontology.individualsInSignature(EXCLUDED), writtenAxioms);
        ontology.signature().forEach(e -> writeAxioms(e, writtenAxioms));
        ontology.axioms().filter(ax -> !writtenAxioms.contains(ax)).sorted()
            .forEach(this::acceptAndReturn);
        writeCloseBracket();
        flush();
    }

    private void writeSortedEntities(OWLOntology ontology, String bannerComment,
        String entityTypeName, Stream<? extends OWLEntity> entities, Set<OWLAxiom> writtenAxioms) {
        List<? extends OWLEntity> sortOptionally = asList(entities.sorted());
        if (!sortOptionally.isEmpty()) {
            writeEntities(ontology, bannerComment, entityTypeName, sortOptionally, writtenAxioms);
            writeReturn();
        }
    }

    private void writeln(String s) {
        write(s);
        writeReturn();
    }

    private void writeEntities(OWLOntology ontology, String comment, String entityTypeName,
        List<? extends OWLEntity> entities, Set<OWLAxiom> writtenAxioms) {
        boolean haveWrittenBanner = false;
        for (OWLEntity owlEntity : entities) {
            List<? extends OWLAxiom> axiomsForEntity = asList(
                getUnsortedAxiomsForEntity(owlEntity).filter(ax -> !writtenAxioms.contains(ax)));
            List<OWLAnnotationAssertionAxiom> list =
                asList(ontology.annotationAssertionAxioms(owlEntity.getIRI())
                    .filter(ax -> !writtenAxioms.contains(ax)));
            if (axiomsForEntity.isEmpty() && list.isEmpty()) {
                continue;
            }
            if (!haveWrittenBanner) {
                writeln("############################");
                writeln("#   " + comment);
                writeln("############################");
                writeReturn();
                haveWrittenBanner = true;
            }
            axiomsForEntity.sort(null);
            list.sort(null);
            writeEntity2(owlEntity, entityTypeName, axiomsForEntity, list, writtenAxioms);
        }
    }

    protected void writeEntity2(OWLEntity entity, String entityTypeName,
        List<? extends OWLAxiom> axiomsForEntity,
        List<OWLAnnotationAssertionAxiom> annotationAssertionAxioms,
        Set<OWLAxiom> alreadyWrittenAxioms) {
        writeln("# " + entityTypeName + ": " + getIRIString(entity) + " (" + getEntityLabel(entity)
            + ")");
        writeReturn();
        annotationAssertionAxioms.stream().filter(alreadyWrittenAxioms::add)
            .forEach(this::acceptAndReturn);
        axiomsForEntity.stream().filter(this::shouldWrite).filter(alreadyWrittenAxioms::add)
            .forEach(this::acceptAndReturn);
        writeReturn();
    }

    private boolean shouldWrite(OWLAxiom ax) {
        if (ax.getAxiomType().equals(AxiomType.DIFFERENT_INDIVIDUALS)) {
            return false;
        }
        return !ax.getAxiomType().equals(AxiomType.DISJOINT_CLASSES)
            || !(((OWLDisjointClassesAxiom) ax).getOperandsAsList().size() > 2);
    }

    private Stream<? extends OWLAxiom> getUnsortedAxiomsForEntity(OWLEntity entity) {
        if (ont.isPresent()) {
            return entity.accept(new AxiomRetriever(ont.get()));
        }
        return Stream.empty();
    }

    private String getIRIString(OWLEntity entity) {
        if (prefixManager.isPresent()) {
            return prefixManager.get().getShortForm(entity);
        }
        return entity.getIRI().toQuotedString();
    }

    private String getEntityLabel(OWLEntity entity) {
        if (labelMaker.isPresent()) {
            return labelMaker.get().getShortForm(entity).replace("\n", "\n# ");
        }
        return entity.getIRI().toQuotedString();
    }

    private void writeAxioms(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms) {
        writeAnnotations(entity, alreadyWrittenAxioms);
        List<OWLAxiom> writtenAxioms = new ArrayList<>();
        Stream<? extends OWLAxiom> stream =
            getUnsortedAxiomsForEntity(entity).filter(alreadyWrittenAxioms::contains)
                .filter(ax -> ax.getAxiomType().equals(AxiomType.DIFFERENT_INDIVIDUALS))
                .filter(ax -> ax.getAxiomType().equals(AxiomType.DISJOINT_CLASSES)
                    && ((OWLDisjointClassesAxiom) ax).classExpressions().count() > 2)
                .sorted();
        stream.forEach(ax -> {
            ax.accept(this);
            writtenAxioms.add(ax);
            writeReturn();
        });
        alreadyWrittenAxioms.addAll(writtenAxioms);
    }

    /**
     * Writes out the declaration axioms for the specified entity.
     *
     * @param entity The entity
     * @return The axioms that were written out
     */
    protected Set<OWLAxiom> writeDeclarations(OWLEntity entity) {
        Set<OWLAxiom> axioms = new HashSet<>();
        ont.ifPresent(
            o -> o.declarationAxioms(entity).sorted().forEach(ax -> acceptAndAdd(axioms, ax)));
        return axioms;
    }

    private void acceptAndAdd(Set<OWLAxiom> axioms, OWLAxiom axiom) {
        axiom.accept(this);
        axioms.add(axiom);
        writeReturn();
    }

    private void writeDeclarations(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms,
        Collection<IRI> illegals) {
        if (ont.isPresent()) {
            Collection<OWLDeclarationAxiom> axioms =
                asList(ont.get().declarationAxioms(entity).sorted());
            axioms.stream().filter(alreadyWrittenAxioms::add).forEach(this::acceptAndReturn);
            // if multiple illegal declarations already exist, they have already
            // been outputted the renderer cannot take responsibility for removing
            // them. It should not add declarations for illegally punned entities
            // here, though
            if (addMissingDeclarations && axioms.isEmpty() && !entity.isBuiltIn()
                && !illegals.contains(entity.getIRI())
                && !ont.get().isDeclared(entity, Imports.INCLUDED)) {
                OWLDeclarationAxiom declaration = ont.get().getOWLOntologyManager()
                    .getOWLDataFactory().getOWLDeclarationAxiom(entity);
                acceptAndReturn(declaration);
            }
        }
    }

    protected void acceptAndReturn(OWLObject ax) {
        ax.accept(this);
        writeReturn();
    }

    protected void acceptAndSpace(OWLObject ax) {
        ax.accept(this);
        writeSpace();
    }

    protected void spaceAndAccept(OWLObject ax) {
        writeSpace();
        ax.accept(this);
    }

    /**
     * Writes of the annotation for the specified entity.
     *
     * @param entity The entity
     * @param alreadyWrittenAxioms already written axioms, to be updated with the newly written
     *        axioms
     */
    protected void writeAnnotations(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms) {
        ont.ifPresent(o -> o.annotationAssertionAxioms(entity.getIRI()).sorted()
            .filter(alreadyWrittenAxioms::add).forEach(this::acceptAndReturn));
    }

    /**
     * Write.
     *
     * @param v the v
     * @param o the o
     */
    protected void write(OWLXMLVocabulary v, OWLObject o) {
        write(v);
        writeOpenBracket();
        o.accept(this);
        writeCloseBracket();
    }

    private void write(Stream<? extends OWLObject> objects) {
        write(asList(objects));
    }

    private void write(List<? extends OWLObject> objects) {
        tabIndex++;
        for (int i = 0; i < objects.size(); i++) {
            if (i > 0) {
                space();
            }
            objects.get(i).accept(this);
        }
        tabIndex--;
    }

    protected void space() {
        if (prettyPrint) {
            writeReturn();
            for (int j = 0; j < tabIndex * 4; j++) {
                writeSpace();
            }
        } else {
            writeSpace();
        }
    }

    protected void writeOpenBracket() {
        write("(");
    }

    protected void writeCloseBracket() {
        write(")");
    }

    protected void writeSpace() {
        write(" ");
    }

    protected void writeReturn() {
        write("\n");
    }

    protected void writeAnnotations(OWLAxiom ax) {
        List<OWLAnnotation> anns = asList(ax.annotations());
        if (anns.isEmpty()) {
            return;
        }
        tabIndex++;
        for (OWLAnnotation a : anns) {
            acceptAndSpace(a);
            space();
        }
        tabIndex--;
    }

    protected void writeAxiomStart(OWLXMLVocabulary v, OWLAxiom axiom) {
        write(v);
        writeOpenBracket();
        writeAnnotations(axiom);
    }

    protected void writeAxiomEnd() {
        writeCloseBracket();
    }

    protected void writePropertyCharacteristic(OWLXMLVocabulary v, OWLAxiom ax,
        OWLPropertyExpression prop) {
        writeAxiomStart(v, ax);
        prop.accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(ASYMMETRIC_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        writeAxiomStart(CLASS_ASSERTION, axiom);
        acceptAndSpace(axiom.getClassExpression());
        axiom.getIndividual().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_ASSERTION, axiom);
        acceptAndSpace(axiom.getProperty());
        acceptAndSpace(axiom.getSubject());
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_DOMAIN, axiom);
        acceptAndSpace(axiom.getProperty());
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_RANGE, axiom);
        acceptAndSpace(axiom.getProperty());
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_DATA_PROPERTY_OF, axiom);
        acceptAndSpace(axiom.getSubProperty());
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        writeAxiomStart(DECLARATION, axiom);
        writeEntitiesAsURIs = false;
        axiom.getEntity().accept(this);
        writeEntitiesAsURIs = true;
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        List<OWLIndividual> individuals = asList(axiom.individuals());
        if (individuals.size() < 2) {
            LOGGER.warn("{} with less than two elements skipped {}",
                axiom.getClass().getSimpleName(), axiom);
            return;
        }
        writeAxiomStart(DIFFERENT_INDIVIDUALS, axiom);
        write(individuals);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        List<OWLClassExpression> classExpressions = asList(axiom.classExpressions());
        if (classExpressions.size() < 2) {
            LOGGER.warn("{} with less than two elements skipped {}",
                axiom.getClass().getSimpleName(), axiom);
            return;
        }
        writeAxiomStart(DISJOINT_CLASSES, axiom);
        write(classExpressions);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        List<OWLDataPropertyExpression> properties = asList(axiom.properties());
        if (properties.size() < 2) {
            LOGGER.warn("{} with less than two elements skipped {}",
                axiom.getClass().getSimpleName(), axiom);
            return;
        }
        writeAxiomStart(DISJOINT_DATA_PROPERTIES, axiom);
        write(properties);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        List<OWLObjectPropertyExpression> properties = asList(axiom.properties());
        if (properties.size() < 2) {
            LOGGER.warn("{} with less than two elements skipped {}",
                axiom.getClass().getSimpleName(), axiom);
            return;
        }
        writeAxiomStart(DISJOINT_OBJECT_PROPERTIES, axiom);
        write(properties);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        writeAxiomStart(DISJOINT_UNION, axiom);
        acceptAndSpace(axiom.getOWLClass());
        write(axiom.classExpressions());
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        writeAxiomStart(ANNOTATION_ASSERTION, axiom);
        acceptAndSpace(axiom.getProperty());
        acceptAndSpace(axiom.getSubject());
        axiom.getValue().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        List<OWLClassExpression> classExpressions = asList(axiom.classExpressions());
        if (classExpressions.size() < 2) {
            LOGGER.warn("{} with less than two elements skipped {}",
                axiom.getClass().getSimpleName(), axiom);
            return;
        }
        writeAxiomStart(EQUIVALENT_CLASSES, axiom);
        write(classExpressions);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        List<OWLDataPropertyExpression> properties = asList(axiom.properties());
        if (properties.size() < 2) {
            LOGGER.warn("{} with less than two elements skipped {}",
                axiom.getClass().getSimpleName(), axiom);
            return;
        }
        writeAxiomStart(EQUIVALENT_DATA_PROPERTIES, axiom);
        write(properties);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        List<OWLObjectPropertyExpression> properties = asList(axiom.properties());
        if (properties.size() < 2) {
            LOGGER.warn("{} with less than two elements skipped {}",
                axiom.getClass().getSimpleName(), axiom);
            return;
        }
        writeAxiomStart(EQUIVALENT_OBJECT_PROPERTIES, axiom);
        write(properties);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        writePropertyCharacteristic(FUNCTIONAL_DATA_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(FUNCTIONAL_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        writeAxiomStart(INVERSE_OBJECT_PROPERTIES, axiom);
        acceptAndSpace(axiom.getFirstProperty());
        axiom.getSecondProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(IRREFLEXIVE_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        writeAxiomStart(NEGATIVE_DATA_PROPERTY_ASSERTION, axiom);
        acceptAndSpace(axiom.getProperty());
        acceptAndSpace(axiom.getSubject());
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        writeAxiomStart(NEGATIVE_OBJECT_PROPERTY_ASSERTION, axiom);
        acceptAndSpace(axiom.getProperty());
        acceptAndSpace(axiom.getSubject());
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_ASSERTION, axiom);
        acceptAndSpace(axiom.getProperty());
        acceptAndSpace(axiom.getSubject());
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        writeAxiomStart(SUB_OBJECT_PROPERTY_OF, axiom);
        write(OBJECT_PROPERTY_CHAIN);
        writeOpenBracket();
        write(axiom.getPropertyChain());
        writeCloseBracket();
        spaceAndAccept(axiom.getSuperProperty());
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_DOMAIN, axiom);
        acceptAndSpace(axiom.getProperty());
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_RANGE, axiom);
        acceptAndSpace(axiom.getProperty());
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_OBJECT_PROPERTY_OF, axiom);
        acceptAndSpace(axiom.getSubProperty());
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(REFLEXIVE_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        List<OWLIndividual> individuals = axiom.getIndividualsAsList();
        if (individuals.size() < 2) {
            LOGGER.warn("{} with less than two elements skipped {}",
                axiom.getClass().getSimpleName(), axiom);
            return;
        }
        writeAxiomStart(SAME_INDIVIDUAL, axiom);
        write(individuals);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        writeAxiomStart(SUB_CLASS_OF, axiom);
        acceptAndSpace(axiom.getSubClass());
        axiom.getSuperClass().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(SYMMETRIC_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(TRANSITIVE_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLClass ce) {
        if (!writeEntitiesAsURIs) {
            write(CLASS);
            writeOpenBracket();
        }
        ce.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    private <F extends OWLPropertyRange> void writeRestriction(OWLXMLVocabulary v,
        OWLCardinalityRestriction<F> restriction, OWLPropertyExpression p) {
        write(v);
        writeOpenBracket();
        write(Integer.toString(restriction.getCardinality()));
        spaceAndAccept(p);
        if (restriction.isQualified()) {
            spaceAndAccept(restriction.getFiller());
        }
        writeCloseBracket();
    }

    private void writeRestriction(OWLXMLVocabulary v, OWLQuantifiedDataRestriction restriction) {
        writeRestriction(v, restriction.getProperty(), restriction.getFiller());
    }

    private void writeRestriction(OWLXMLVocabulary v, OWLQuantifiedObjectRestriction restriction) {
        writeRestriction(v, restriction.getProperty(), restriction.getFiller());
    }

    private void writeRestriction(OWLXMLVocabulary v, OWLPropertyExpression prop,
        OWLObject filler) {
        write(v);
        writeOpenBracket();
        acceptAndSpace(prop);
        filler.accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        writeRestriction(DATA_ALL_VALUES_FROM, ce);
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        writeRestriction(DATA_EXACT_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        writeRestriction(DATA_MAX_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        writeRestriction(DATA_MIN_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        writeRestriction(DATA_SOME_VALUES_FROM, ce);
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        writeRestriction(DATA_HAS_VALUE, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeRestriction(OBJECT_ALL_VALUES_FROM, ce);
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        write(OBJECT_COMPLEMENT_OF, ce.getOperand());
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        writeRestriction(OBJECT_EXACT_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        if (ce.operands().count() == 1) {
            ce.operands().forEach(x -> x.accept(this));
            return;
        }
        write(OBJECT_INTERSECTION_OF);
        writeOpenBracket();
        write(ce.operands());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        writeRestriction(OBJECT_MAX_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        writeRestriction(OBJECT_MIN_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        write(OBJECT_ONE_OF);
        writeOpenBracket();
        write(ce.individuals());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        write(OBJECT_HAS_SELF, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeRestriction(OBJECT_SOME_VALUES_FROM, ce);
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        if (ce.operands().count() == 1) {
            ce.operands().forEach(x -> x.accept(this));
            return;
        }
        write(OBJECT_UNION_OF);
        writeOpenBracket();
        write(ce.operands());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        writeRestriction(OBJECT_HAS_VALUE, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        write(DATA_COMPLEMENT_OF, node.getDataRange());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        write(DATA_ONE_OF);
        writeOpenBracket();
        write(node.values());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDatatype node) {
        if (!writeEntitiesAsURIs) {
            write(DATATYPE);
            writeOpenBracket();
        }
        node.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        write(DATATYPE_RESTRICTION);
        writeOpenBracket();
        node.getDatatype().accept(this);
        node.facetRestrictions().forEach(this::spaceAndAccept);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        write(node.getFacet().getIRI());
        spaceAndAccept(node.getFacetValue());
    }

    @Override
    public void visit(OWLLiteral node) {
        write("\"");
        write(EscapeUtils.escapeString(node.getLiteral()));
        write("\"");
        if (node.hasLang()) {
            write("@");
            write(node.getLang());
        } else if (!node.isRDFPlainLiteral()
            && !OWL2Datatype.XSD_STRING.matches(node.getDatatype())) {
            write("^^");
            write(node.getDatatype().getIRI());
        }
    }

    @Override
    public void visit(OWLDataProperty property) {
        if (!writeEntitiesAsURIs) {
            write(DATA_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLObjectProperty property) {
        if (!writeEntitiesAsURIs) {
            write(OBJECT_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        write(OBJECT_INVERSE_OF);
        writeOpenBracket();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        if (!writeEntitiesAsURIs) {
            write(NAMED_INDIVIDUAL);
            writeOpenBracket();
        }
        individual.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        writeAxiomStart(HAS_KEY, axiom);
        acceptAndSpace(axiom.getClassExpression());
        writeOpenBracket();
        write(asList(axiom.objectPropertyExpressions()));
        writeCloseBracket();
        writeSpace();
        writeOpenBracket();
        write(asList(axiom.dataPropertyExpressions()));
        writeCloseBracket();
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        writeAxiomStart(ANNOTATION_PROPERTY_DOMAIN, axiom);
        acceptAndSpace(axiom.getProperty());
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        writeAxiomStart(ANNOTATION_PROPERTY_RANGE, axiom);
        acceptAndSpace(axiom.getProperty());
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_ANNOTATION_PROPERTY_OF, axiom);
        acceptAndSpace(axiom.getSubProperty());
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        if (node.operands().count() == 1) {
            node.operands().forEach(x -> x.accept(this));
            return;
        }
        write(DATA_INTERSECTION_OF);
        writeOpenBracket();
        write(node.operands());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        if (node.operands().count() == 1) {
            node.operands().forEach(x -> x.accept(this));
            return;
        }
        write(DATA_UNION_OF);
        writeOpenBracket();
        write(node.operands());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        if (!writeEntitiesAsURIs) {
            write(ANNOTATION_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        write(individual.getID().toString());
    }

    @Override
    public void visit(IRI iri) {
        write(iri);
    }

    @Override
    public void visit(OWLAnnotation node) {
        write(ANNOTATION);
        writeOpenBracket();
        node.annotations().forEach(this::acceptAndSpace);
        acceptAndSpace(node.getProperty());
        node.getValue().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        writeAxiomStart(DATATYPE_DEFINITION, axiom);
        acceptAndSpace(axiom.getDatatype());
        axiom.getDataRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(SWRLRule rule) {
        writeAxiomStart(DL_SAFE_RULE, rule);
        write(BODY);
        writeOpenBracket();
        write(rule.body());
        writeCloseBracket();
        write(HEAD);
        writeOpenBracket();
        write(rule.head());
        writeCloseBracket();
        writeAxiomEnd();
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(SWRLClassAtom node) {
        write(CLASS_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getPredicate());
        node.getArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        write(DATA_RANGE_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getPredicate());
        node.getArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        write(OBJECT_PROPERTY_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getPredicate());
        acceptAndSpace(node.getFirstArgument());
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        write(DATA_PROPERTY_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getPredicate());
        acceptAndSpace(node.getFirstArgument());
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        write(BUILT_IN_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getPredicate());
        write(node.getArguments());
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLVariable node) {
        write(VARIABLE);
        writeOpenBracket();
        String namespace = node.getIRI().getNamespace();
        if ("urn:swrl:var#".equals(namespace) || "urn:swrl#".equals(namespace)) {
            write("<urn:swrl:var#" + node.getIRI().getFragment() + ">");
        } else {
            node.getIRI().accept(this);
        }
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        write(DIFFERENT_INDIVIDUALS_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getFirstArgument());
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        write(SAME_INDIVIDUAL_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getFirstArgument());
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    class AxiomRetriever implements OWLEntityVisitorEx<Stream<? extends OWLAxiom>> {
        OWLOntology o;

        public AxiomRetriever(OWLOntology o) {
            this.o = o;
        }

        @Override
        public Stream<? extends OWLAxiom> visit(OWLClass cls) {
            return o.axioms(cls, EXCLUDED);
        }

        @Override
        public Stream<? extends OWLAxiom> visit(OWLObjectProperty property) {
            return o.axioms(property, EXCLUDED);
        }

        @Override
        public Stream<? extends OWLAxiom> visit(OWLDataProperty property) {
            return o.axioms(property, EXCLUDED);
        }

        @Override
        public Stream<? extends OWLAxiom> visit(OWLNamedIndividual individual) {
            return o.axioms(individual, EXCLUDED);
        }

        @Override
        public Stream<? extends OWLAxiom> visit(OWLDatatype datatype) {
            return o.axioms(datatype, EXCLUDED);
        }

        @Override
        public Stream<? extends OWLAxiom> visit(OWLAnnotationProperty property) {
            return o.axioms(property, EXCLUDED);
        }
    }
}
