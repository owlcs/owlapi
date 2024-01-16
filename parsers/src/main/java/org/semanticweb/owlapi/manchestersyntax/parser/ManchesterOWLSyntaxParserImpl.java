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
package org.semanticweb.owlapi.manchestersyntax.parser;

import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.AND;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.ANNOTATIONS;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.ANNOTATION_PROPERTY;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.ANTI_SYMMETRIC;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.ASYMMETRIC;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.CHAIN_CONNECT;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.CHARACTERISTICS;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.CLASS;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.CLOSE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.CLOSEBRACE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.CLOSEBRACKET;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.COMMA;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.DASH;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.DATATYPE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.DATA_PROPERTY;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.DIFFERENT_FROM;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.DIFFERENT_INDIVIDUALS;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.DISJOINT_CLASSES;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.DISJOINT_PROPERTIES;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.DISJOINT_UNION_OF;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.DISJOINT_WITH;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.DOMAIN;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.EQUIVALENT_CLASSES;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.EQUIVALENT_PROPERTIES;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.EQUIVALENT_TO;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.EXACTLY;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.FACTS;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.FUNCTIONAL;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.HAS_KEY;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.IMPORT;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.INDIVIDUAL;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.INDIVIDUALS;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.INV;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.INVERSE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.INVERSE_FUNCTIONAL;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.INVERSE_OF;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.IRREFLEXIVE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.LITERAL_DOUBLE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.LITERAL_FALSE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.LITERAL_FLOAT;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.LITERAL_INTEGER;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.LITERAL_LITERAL;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.LITERAL_LIT_DATATYPE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.LITERAL_LIT_LANG;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.LITERAL_TRUE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.MAX;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.MAX_EXCLUSIVE_FACET;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.MAX_INCLUSIVE_FACET;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.MIN;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.MIN_EXCLUSIVE_FACET;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.MIN_INCLUSIVE_FACET;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.NOT;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.OBJECT_PROPERTY;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.ONLY;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.ONLYSOME;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.ONTOLOGY;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.OPEN;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.OPENBRACE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.OPENBRACKET;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.OR;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.PREFIX;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.RANGE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.REFLEXIVE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.RULE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.SAME_AS;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.SAME_INDIVIDUAL;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.SELF;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.SOME;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.SUBCLASS_OF;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.SUB_PROPERTY_CHAIN;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.SUB_PROPERTY_OF;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.SUPERCLASS_OF;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.SUPER_PROPERTY_OF;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.SYMMETRIC;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.THAT;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.TRANSITIVE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.TYPE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.TYPES;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.VALUE;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.VALUE_PARTITION;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.parse;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.values;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxTokenizer.EOF;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxTokenizer.eof;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLOntologyChecker;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxTokenizer.Token;
import org.semanticweb.owlapi.manchestersyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.NamespaceUtil;
import org.semanticweb.owlapi.util.OntologyAxiomPair;
import org.semanticweb.owlapi.util.RemappingIndividualProvider;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;
import org.semanticweb.owlapi.vocab.DublinCoreVocabulary;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

/**
 * A parser for the Manchester OWL Syntax. All properties must be defined before they are used. For
 * example, consider the restriction hasPart some Leg. The parser must know in advance whether or
 * not hasPart is an object property or a data property so that Leg gets parsed correctly. In a
 * tool, such as an editor, it is expected that hasPart will already exists as either a data
 * property or an object property. If a complete ontology is being parsed, it is expected that
 * hasPart will have been defined at the top of the file before it is used in any class expressions
 * or property assertions (e.g. ObjectProperty: hasPart)
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public class ManchesterOWLSyntaxParserImpl implements ManchesterOWLSyntaxParser {

    private static final String DC = "dc:";
    // This parser was built by hand! After struggling with terrible
    // error messages produced by ANTLR (or JavaCC) I decides to construct
    // this parser by hand. The error messages that this parser generates
    // are specific to the Manchester OWL Syntax and are such that it should
    // be easy to use this parser in tools such as editors.
    @Nonnull
    private Supplier<OWLOntologyLoaderConfiguration> configProvider;
    @Nonnull
    private Optional<OWLOntologyLoaderConfiguration> config = Optional.empty();
    protected OWLDataFactory dataFactory;
    private List<Token> tokens;
    private int tokenIndex;
    private OWLEntityChecker owlEntityChecker;
    private OWLOntologyChecker owlOntologyChecker = new OWLOntologyChecker() {

        @Nullable
        @Override
        public OWLOntology getOntology(String name) {
            return null;
        }
    };
    @Nonnull
    protected final Set<String> classNames = new HashSet<>();
    @Nonnull
    protected final Set<String> objectPropertyNames = new HashSet<>();
    @Nonnull
    protected final Set<String> dataPropertyNames = new HashSet<>();
    @Nonnull
    protected final Set<String> individualNames = new HashSet<>();
    @Nonnull
    protected final Set<String> dataTypeNames = new HashSet<>();
    @Nonnull
    protected final Set<String> annotationPropertyNames = new HashSet<>();
    @Nonnull
    private final Map<String, SWRLBuiltInsVocabulary> ruleBuiltIns = new HashMap<>();
    @Nonnull
    protected final DefaultPrefixManager pm = new DefaultPrefixManager();
    @Nonnull
    protected final Set<ManchesterOWLSyntax> potentialKeywords = new HashSet<>();
    private OWLOntology defaultOntology;
    private final boolean allowEmptyFrameSections = false;
    private final Map<ManchesterOWLSyntax, AnnotatedListItemParser<OWLDataProperty, ?>> dataPropertyFrameSections =
        new EnumMap<>(ManchesterOWLSyntax.class);
    protected RemappingIndividualProvider anonProvider;

    /**
     * @param configurationProvider configuration provider
     * @param dataFactory dataFactory
     */
    @Inject
    public ManchesterOWLSyntaxParserImpl(
        @Nonnull Supplier<OWLOntologyLoaderConfiguration> configurationProvider,
        @Nonnull OWLDataFactory dataFactory) {
        configProvider = configurationProvider;
        this.dataFactory = dataFactory;
        anonProvider = new RemappingIndividualProvider(this.dataFactory);
        pm.setPrefix("rdf:", Namespaces.RDF.toString());
        pm.setPrefix("rdfs:", Namespaces.RDFS.toString());
        pm.setPrefix("owl:", Namespaces.OWL.toString());
        NamespaceUtil u = new NamespaceUtil();
        initialiseClassFrameSections();
        initialiseObjectPropertyFrameSections();
        initialiseDataPropertyFrameSections();
        initialiseAnnotationPropertyFrameSections();
        initialiseIndividualFrameSections();
        for (XSDVocabulary v : XSDVocabulary.values()) {
            dataTypeNames.add(v.getIRI().toString());
            dataTypeNames.add(v.getIRI().toQuotedString());
            dataTypeNames.add(v.getPrefixedName());
        }
        for (OWL2Datatype v : OWL2Datatype.values()) {
            dataTypeNames.add(v.getIRI().toString());
            dataTypeNames.add(v.getIRI().toQuotedString());
            dataTypeNames.add(v.getPrefixedName());
        }
        dataTypeNames.add(OWLRDFVocabulary.RDFS_LITERAL.getPrefixedName());
        dataTypeNames.add(OWLRDFVocabulary.RDF_XML_LITERAL.getShortForm());
        dataTypeNames.add(OWLRDFVocabulary.RDF_XML_LITERAL.getPrefixedName());
        for (IRI iri : OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTY_IRIS) {
            String string = iri.toString();
            String ns = XMLUtils.getNCNamePrefix(string);
            String fragment = XMLUtils.getNCNameSuffix(string);
            annotationPropertyNames.add(u.getPrefix(ns) + ':' + (fragment != null ? fragment : ""));
        }
        owlEntityChecker = new DefaultEntityChecker();
        for (SWRLBuiltInsVocabulary v : SWRLBuiltInsVocabulary.values()) {
            ruleBuiltIns.put(v.getShortForm(), v);
            ruleBuiltIns.put(v.getIRI().toQuotedString(), v);
        }
    }

    /** @return the prefix manager used by this parser */
    // XXX add this method to the interface in next release
    public PrefixManager getPrefixManager() {
        return pm;
    }

    @Override
    @Nonnull
    public OWLOntologyLoaderConfiguration getOntologyLoaderConfiguration() {
        if (config.isPresent()) {
            return config.get();
        }
        config = Optional.of(configProvider.get());
        return config.get();
    }

    @Override
    public void setOntologyLoaderConfigurationProvider(
        Supplier<OWLOntologyLoaderConfiguration> provider) {
        configProvider = provider;
    }

    @Override
    public void setOntologyLoaderConfiguration(OWLOntologyLoaderConfiguration config) {
        this.config = Optional.ofNullable(config);
    }

    @Override
    public void setStringToParse(String s) {
        tokens = new ArrayList<>();
        tokens.addAll(getTokenizer(s).tokenize());
        tokenIndex = 0;
    }

    protected static ManchesterOWLSyntaxTokenizer getTokenizer(String s) {
        return new ManchesterOWLSyntaxTokenizer(s);
    }

    private final Map<ManchesterOWLSyntax, AnnotatedListItemParser<OWLClass, ?>> simpleClassFrameSections =
            new EnumMap<>(ManchesterOWLSyntax.class);
    private final Map<ManchesterOWLSyntax, AnnotatedListItemParser<OWLClassExpression, ?>> complexClassFrameSections =
            new EnumMap<>(ManchesterOWLSyntax.class);

    private void initialiseClassFrameSections() {
        initialiseSection(new EntityAnnotationsListItemParser<OWLClass>(), simpleClassFrameSections);
        initialiseSection(new ClassSubClassOfListItemParser(), complexClassFrameSections);
        initialiseSection(new ClassEquivalentToListItemParser(), complexClassFrameSections);
        initialiseSection(new ClassDisjointWithListItemParser(), complexClassFrameSections);
        initialiseSection(new ClassHasKeyListItemParser(), complexClassFrameSections);
        initialiseSection(new ClassDisjointUnionOfListItemParser(), simpleClassFrameSections);
        // Extensions
        initialiseSection(new ClassSuperClassOfListItemParser(), complexClassFrameSections);
        initialiseSection(new ClassDisjointClassesListItemParser(), complexClassFrameSections);
        initialiseSection(new ClassIndividualsListItemParser(), complexClassFrameSections);
    }

    private final Map<ManchesterOWLSyntax, AnnotatedListItemParser<OWLObjectProperty, ?>> objectPropertyFrameSections =
        new EnumMap<>(ManchesterOWLSyntax.class);

    private void initialiseObjectPropertyFrameSections() {
        initialiseSection(new EntityAnnotationsListItemParser<OWLObjectProperty>(),
            objectPropertyFrameSections);
        initialiseSection(new ObjectPropertySubPropertyOfListItemParser(),
            objectPropertyFrameSections);
        initialiseSection(new ObjectPropertyEquivalentToListItemParser(),
            objectPropertyFrameSections);
        initialiseSection(new ObjectPropertyDisjointWithListItemParser(),
            objectPropertyFrameSections);
        initialiseSection(new ObjectPropertyDomainListItemParser(), objectPropertyFrameSections);
        initialiseSection(new ObjectPropertyRangeListItemParser(), objectPropertyFrameSections);
        initialiseSection(new ObjectPropertyInverseOfListItemParser(), objectPropertyFrameSections);
        initialiseSection(new ObjectPropertyCharacteristicsItemParser(),
            objectPropertyFrameSections);
        initialiseSection(new ObjectPropertySubPropertyChainListItemParser(),
            objectPropertyFrameSections);
        // Extensions
        initialiseSection(new ObjectPropertySuperPropertyOfListItemParser(),
            objectPropertyFrameSections);
    }

    private void initialiseDataPropertyFrameSections() {
        initialiseSection(new DataPropertySubPropertyOfListItemParser(), dataPropertyFrameSections);
        initialiseSection(new DataPropertyEquivalentToListItemParser(), dataPropertyFrameSections);
        initialiseSection(new DataPropertyDisjointWithListItemParser(), dataPropertyFrameSections);
        initialiseSection(new DataPropertyDomainListItemParser(), dataPropertyFrameSections);
        initialiseSection(new DataPropertyRangeListItemParser(), dataPropertyFrameSections);
        initialiseSection(new DataPropertyCharacteristicsItemParser(), dataPropertyFrameSections);
        initialiseSection(new EntityAnnotationsListItemParser<OWLDataProperty>(),
            dataPropertyFrameSections);
    }

    private final Map<ManchesterOWLSyntax, AnnotatedListItemParser<OWLAnnotationProperty, ?>> annotationPropertyFrameSections =
        new EnumMap<>(ManchesterOWLSyntax.class);

    private void initialiseAnnotationPropertyFrameSections() {
        initialiseSection(new AnnotationPropertySubPropertyOfListItemParser(),
            annotationPropertyFrameSections);
        initialiseSection(new AnnotationPropertyDomainListItemParser(),
            annotationPropertyFrameSections);
        initialiseSection(new AnnotationPropertyRangeListItemParser(),
            annotationPropertyFrameSections);
        initialiseSection(new EntityAnnotationsListItemParser<OWLAnnotationProperty>(),
            annotationPropertyFrameSections);
    }

    private final Map<ManchesterOWLSyntax, AnnotatedListItemParser<OWLIndividual, ?>> individualFrameSections =
        new EnumMap<>(ManchesterOWLSyntax.class);

    private void initialiseIndividualFrameSections() {
        initialiseSection(new IndividualAnnotationItemParser(), individualFrameSections);
        initialiseSection(new IndividualTypesItemParser(), individualFrameSections);
        initialiseSection(new IndividualFactsItemParser(), individualFrameSections);
        initialiseSection(new IndividualSameAsItemParser(), individualFrameSections);
        initialiseSection(new IndividualDifferentFromItemParser(), individualFrameSections);
    }

    @Override
    public void setOWLEntityChecker(OWLEntityChecker owlEntityChecker) {
        this.owlEntityChecker = owlEntityChecker;
    }

    private boolean isOntologyName(@Nonnull String name) {
        return owlOntologyChecker.getOntology(name) != null;
    }

    private boolean isClassName(@Nonnull String name) {
        return classNames.contains(name)
            || owlEntityChecker != null && owlEntityChecker.getOWLClass(name) != null;
    }

    private OWLOntology getOntology(String name) {
        return owlOntologyChecker.getOntology(name);
    }

    @Override
    public void setOWLOntologyChecker(@Nonnull OWLOntologyChecker owlOntologyChecker) {
        this.owlOntologyChecker = owlOntologyChecker;
    }

    private boolean isObjectPropertyName(@Nonnull String name) {
        return objectPropertyNames.contains(name)
            || owlEntityChecker != null && owlEntityChecker.getOWLObjectProperty(name) != null;
    }

    private boolean isAnnotationPropertyName(@Nonnull String name) {
        return annotationPropertyNames.contains(name)
            || owlEntityChecker != null && owlEntityChecker.getOWLAnnotationProperty(name) != null;
    }

    private boolean isDataPropertyName(@Nonnull String name) {
        return dataPropertyNames.contains(name)
            || owlEntityChecker != null && owlEntityChecker.getOWLDataProperty(name) != null;
    }

    private boolean isIndividualName(@Nonnull String name) {
        return individualNames.contains(name)
            || owlEntityChecker != null && owlEntityChecker.getOWLIndividual(name) != null;
    }

    private boolean isDatatypeName(@Nonnull String name) {
        return dataTypeNames.contains(name)
            || owlEntityChecker != null && owlEntityChecker.getOWLDatatype(name) != null;
    }

    private boolean isSWRLBuiltin(@Nonnull String name) {
        return ruleBuiltIns.containsKey(name);
    }

    @Nonnull
    private OWLClass getOWLClass(@Nonnull String name) {
        OWLClass cls = owlEntityChecker.getOWLClass(name);
        if (cls == null && classNames.contains(name)) {
            cls = dataFactory.getOWLClass(getIRI(name));
        }
        if (cls == null) {
            throw new ExceptionBuilder().withKeyword(potentialKeywords).withClass().build();
        }
        return cls;
    }

    @Nonnull
    private OWLObjectProperty getOWLObjectProperty(@Nonnull String name) {
        OWLObjectProperty prop = owlEntityChecker.getOWLObjectProperty(name);
        if (prop == null && objectPropertyNames.contains(name)) {
            prop = dataFactory.getOWLObjectProperty(getIRI(name));
        }
        if (prop == null) {
            throw new ExceptionBuilder().withObject().build();
        }
        return prop;
    }

    @Nonnull
    private OWLIndividual getOWLIndividual(@Nonnull String name) {
        if (name.startsWith("_:")) {
            return anonProvider.getOWLAnonymousIndividual(name);
        }
        return getOWLNamedIndividual(name);
    }

    @Nonnull
    private OWLNamedIndividual getOWLNamedIndividual(@Nonnull String name) {
        OWLNamedIndividual ind = owlEntityChecker.getOWLIndividual(name);
        if (ind == null && individualNames.contains(name)) {
            ind = dataFactory.getOWLNamedIndividual(getIRI(name));
        }
        if (ind == null) {
            throw new ExceptionBuilder().withInd().build();
        }
        return ind;
    }

    @Nonnull
    private OWLDataProperty getOWLDataProperty(@Nonnull String name) {
        OWLDataProperty prop = owlEntityChecker.getOWLDataProperty(name);
        if (prop == null && dataPropertyNames.contains(name)) {
            prop = dataFactory.getOWLDataProperty(getIRI(name));
        }
        if (prop == null) {
            throw new ExceptionBuilder().withData().build();
        }
        return prop;
    }

    @Nonnull
    private OWLDatatype getOWLDatatype(@Nonnull String name) {
        OWLDatatype dt = owlEntityChecker.getOWLDatatype(name);
        if (dt == null && dataTypeNames.contains(name)) {
            dt = dataFactory.getOWLDatatype(getIRI(name));
        }
        if (dt == null) {
            throw new ExceptionBuilder().withDt().build();
        }
        return dt;
    }

    @Nonnull
    private OWLAnnotationProperty getOWLAnnotationProperty(@Nonnull String name) {
        OWLAnnotationProperty prop = owlEntityChecker.getOWLAnnotationProperty(name);
        if (prop == null && annotationPropertyNames.contains(name)) {
            prop = dataFactory.getOWLAnnotationProperty(getIRI(name));
        }
        if (prop == null) {
            throw new ExceptionBuilder().withAnn().build();
        }
        return prop;
    }

    protected Token getLastToken() {
        if (tokenIndex - 1 > -1) {
            return tokens.get(tokenIndex - 1);
        } else {
            return tokens.get(0);
        }
    }

    @Nonnull
    private String peekToken() {
        return getToken().getToken();
    }

    @Nonnull
    private String peekToken(int ahead) {
        return tokens.get(tokenIndex + ahead).getToken();
    }

    @Nonnull
    private String consumeToken() {
        String token = getToken().getToken();
        if (tokenIndex < tokens.size()) {
            tokenIndex++;
        }
        return token;
    }

    private void consumeToken(String expected) {
        String tok = consumeToken();
        if (!tok.equals(expected)) {
            throw new ExceptionBuilder().withKeyword(expected).build();
        }
    }

    private void consumeToken(ManchesterOWLSyntax expected) {
        String tok = consumeToken();
        if (!expected.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(expected).build();
        }
    }

    private Token getToken() {
        return tokens.get(tokenIndex < tokens.size() ? tokenIndex : tokenIndex - 1);
    }

    /* Parser */
    @Nonnull
    @Override
    public OWLClassExpression parseClassExpression() {
        OWLClassExpression desc = parseUnion();
        if (!eof(consumeToken())) {
            throw new ExceptionBuilder().withKeyword(EOF).build();
        }
        return desc;
    }

    @Override
    public OWLClassExpression parseClassExpression(String s) {
        setStringToParse(s);
        return parseClassExpression();
    }

    protected OWLClassExpression parseIntersection() {
        Set<OWLClassExpression> ops = new HashSet<>();
        String kw = AND.keyword();
        while (AND.matches(kw)) {
            potentialKeywords.remove(AND);
            ops.add(parseNonNaryClassExpression());
            potentialKeywords.add(AND);
            kw = peekToken();
            if (AND.matches(kw)) {
                kw = consumeToken();
            } else if (THAT.matches(kw)) {
                consumeToken();
                kw = AND.keyword();
            }
        }
        if (ops.size() == 1) {
            return ops.iterator().next();
        } else {
            return dataFactory.getOWLObjectIntersectionOf(ops);
        }
    }

    @Nonnull
    protected OWLClassExpression parseUnion() {
        Set<OWLClassExpression> ops = new HashSet<>();
        String kw = OR.keyword();
        while (OR.matches(kw)) {
            potentialKeywords.remove(OR);
            ops.add(parseIntersection());
            potentialKeywords.add(OR);
            kw = peekToken();
            if (OR.matches(kw)) {
                kw = consumeToken();
            }
        }
        if (ops.size() == 1) {
            return ops.iterator().next();
        } else {
            return dataFactory.getOWLObjectUnionOf(ops);
        }
    }

    @Nonnull
    protected OWLObjectPropertyExpression parseObjectPropertyExpression(boolean allowUndeclared) {
        String tok = consumeToken();
        if (INVERSE.matches(tok)) {
            String open = peekToken();
            boolean brackets = false;
            if (OPEN.matches(open)) {
                consumeToken();
                brackets = true;
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
            if (brackets) {
                String close = consumeToken();
                if (!CLOSE.matches(close)) {
                    throw new ExceptionBuilder().withKeyword(CLOSE).build();
                }
            }
            return dataFactory.getOWLObjectInverseOf(prop);
        } else {
            if (!allowUndeclared && !isObjectPropertyName(tok)) {
                throw new ExceptionBuilder().withObject().build();
            }
            return getOWLObjectProperty(tok);
        }
    }

    private OWLPropertyExpression parsePropertyExpression() {
        String tok = peekToken();
        if (isObjectPropertyName(tok)) {
            return parseObjectPropertyExpression(false);
        } else if (INVERSE.matches(tok)) {
            return parseObjectPropertyExpression(false);
        } else if (isDataPropertyName(tok)) {
            return parseDataProperty();
        } else {
            consumeToken();
            throw new ExceptionBuilder().withObject().withData().build();
        }
    }

    private static EnumSet<ManchesterOWLSyntax> TOKENS_FOR_CLASSEXPRESSONS =
        EnumSet.of(SOME, ONLY, VALUE, MIN, MAX, EXACTLY, ONLYSOME, SELF);

    /**
     * Parses all class expressions except ObjectIntersectionOf and ObjectUnionOf.
     * 
     * @return The class expression which was parsed @ * if a non-nary class expression could not be
     *         parsed
     */
    private OWLClassExpression parseNonNaryClassExpression() {
        String tok = peekToken();
        boolean isClassName = isClassName(tok);
        boolean isDataPropertyName = isDataPropertyName(tok);
        boolean isObjectPropertyName = isObjectPropertyName(tok);
        if (isClassName && (isDataPropertyName || isObjectPropertyName)) {
            if (isDataPropertyName && isObjectPropertyName && getOntologyLoaderConfiguration().isStrict()) {
                throw new ExceptionBuilder().withMessage(
                    "Illegal punning: " + tok + " is a data property and an object property name.")
                    .build();
            }
            // handle punning of classes and property names in nested class expressions where a
            // property might have the same name as a class
            String tokenAhead = peekToken(1);
            if (TOKENS_FOR_CLASSEXPRESSONS.stream().anyMatch(x -> x.matches(tokenAhead))) {
                if (isObjectPropertyName) {
                    return parseObjectRestriction();
                }
                return parseDataRestriction();
            } else {
                consumeToken();
                return getOWLClass(tok);
            }
        } else if (NOT.matches(tok)) {
            consumeToken();
            OWLClassExpression complemented = parseNestedClassExpression(false);
            return dataFactory.getOWLObjectComplementOf(complemented);
        } else if (isClassName) {
            consumeToken();
            return getOWLClass(tok);
        } else if (isObjectPropertyName || INVERSE.matches(tok)) {
            return parseObjectRestriction();
        } else if (isDataPropertyName) {
            // Data restriction
            return parseDataRestriction();
        } else if (OPENBRACE.matches(tok)) {
            return parseObjectOneOf();
        } else if (OPEN.matches(tok)) {
            return parseNestedClassExpression(false);
        }
        // Add option for strict class name checking
        else {
            consumeToken();
            throw new ExceptionBuilder().withClass().withObject().withData()
                .withKeyword(OPEN, OPENBRACE, NOT, INVERSE).build();
        }
    }

    private OWLClassExpression parseObjectRestriction() {
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        String kw = consumeToken();
        if (SOME.matches(kw)) {
            String possSelfToken = peekToken();
            if (SELF.matches(possSelfToken)) {
                consumeToken();
                return dataFactory.getOWLObjectHasSelf(prop);
            } else {
                OWLClassExpression filler = null;
                try {
                    filler = parseNestedClassExpression(false);
                } catch (ParserException e) {
                    e.getExpectedKeywords().add(SELF.keyword());
                    throw e;
                }
                return dataFactory.getOWLObjectSomeValuesFrom(prop, filler);
            }
        } else if (ONLY.matches(kw)) {
            OWLClassExpression filler = parseNestedClassExpression(false);
            return dataFactory.getOWLObjectAllValuesFrom(prop, filler);
        } else if (VALUE.matches(kw)) {
            String indName = consumeToken();
            if (!isIndividualName(indName)) {
                throw new ExceptionBuilder().withInd().build();
            }
            return dataFactory.getOWLObjectHasValue(prop, getOWLIndividual(indName));
        } else if (MIN.matches(kw)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            return dataFactory.getOWLObjectMinCardinality(card, prop, filler);
        } else if (MAX.matches(kw)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            return dataFactory.getOWLObjectMaxCardinality(card, prop, filler);
        } else if (EXACTLY.matches(kw)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            return dataFactory.getOWLObjectExactCardinality(card, prop, filler);
        } else if (ONLYSOME.matches(kw)) {
            String tok = peekToken();
            Set<OWLClassExpression> descs = new HashSet<>();
            if (!OPENBRACKET.matches(tok)) {
                descs.add(parseUnion());
            } else {
                descs.addAll(parseClassExpressionList(OPENBRACKET, CLOSEBRACKET));
            }
            Set<OWLClassExpression> ops = new HashSet<>();
            for (OWLClassExpression desc : descs) {
                assert desc != null;
                ops.add(dataFactory.getOWLObjectSomeValuesFrom(prop, desc));
            }
            OWLClassExpression filler;
            if (descs.size() == 1) {
                filler = descs.iterator().next();
            } else {
                filler = dataFactory.getOWLObjectUnionOf(descs);
            }
            assert filler != null;
            ops.add(dataFactory.getOWLObjectAllValuesFrom(prop, filler));
            return dataFactory.getOWLObjectIntersectionOf(ops);
        } else if (SELF.matches(kw)) {
            return dataFactory.getOWLObjectHasSelf(prop);
        } else {
            // Error!
            throw new ExceptionBuilder().withKeyword(SOME, ONLY, VALUE, MIN, MAX, EXACTLY, SELF)
                .build();
        }
    }

    private OWLClassExpression parseDataRestriction() {
        OWLDataPropertyExpression prop = parseDataProperty();
        String kw = consumeToken();
        if (SOME.matches(kw)) {
            OWLDataRange rng = parseDataIntersectionOf(false);
            return dataFactory.getOWLDataSomeValuesFrom(prop, rng);
        } else if (ONLY.matches(kw)) {
            OWLDataRange rng = parseDataIntersectionOf(false);
            return dataFactory.getOWLDataAllValuesFrom(prop, rng);
        } else if (VALUE.matches(kw)) {
            OWLLiteral con = parseLiteral(null);
            return dataFactory.getOWLDataHasValue(prop, con);
        } else if (MIN.matches(kw)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataIntersectionOf(true);
            return dataFactory.getOWLDataMinCardinality(card, prop, rng);
        } else if (EXACTLY.matches(kw)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataIntersectionOf(true);
            return dataFactory.getOWLDataExactCardinality(card, prop, rng);
        } else if (MAX.matches(kw)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataIntersectionOf(true);
            return dataFactory.getOWLDataMaxCardinality(card, prop, rng);
        }
        throw new ExceptionBuilder().withKeyword(SOME, ONLY, VALUE, MIN, EXACTLY, MAX).build();
    }

    private OWLFacet parseFacet() {
        String facet = consumeToken();
        if (MIN_INCLUSIVE_FACET.matches(facet, peekToken())) {
            consumeToken();
            return OWLFacet.MIN_INCLUSIVE;
        }
        if (MAX_INCLUSIVE_FACET.matches(facet, peekToken())) {
            consumeToken();
            return OWLFacet.MAX_INCLUSIVE;
        }
        if (MIN_EXCLUSIVE_FACET.matches(facet)) {
            return OWLFacet.MIN_EXCLUSIVE;
        }
        if (MAX_EXCLUSIVE_FACET.matches(facet)) {
            return OWLFacet.MAX_EXCLUSIVE;
        }
        return OWLFacet.getFacetBySymbolicName(facet);
    }

    @Nonnull
    private OWLDatatype parseDatatype() {
        String name = consumeToken();
        return getOWLDatatype(name);
    }

    @Override
    public OWLDataRange parseDataRange() {
        return parseDataIntersectionOf(false);
    }

    @Nonnull
    protected OWLDataRange parseDataIntersectionOf(boolean lookaheadCheck) {
        String sep = AND.keyword();
        Set<OWLDataRange> ranges = new HashSet<>();
        while (AND.matches(sep)) {
            ranges.add(parseDataUnionOf(lookaheadCheck));
            sep = peekToken();
            if (AND.matches(sep)) {
                consumeToken();
            }
        }
        if (ranges.isEmpty()) {
            return dataFactory.getTopDatatype();
        }
        if (ranges.size() == 1) {
            return ranges.iterator().next();
        }
        return dataFactory.getOWLDataIntersectionOf(ranges);
    }

    private OWLDataRange parseDataUnionOf(boolean lookaheadCheck) {
        String sep = OR.keyword();
        Set<OWLDataRange> ranges = new HashSet<>();
        while (OR.matches(sep)) {
            ranges.add(parseDataRangePrimary(lookaheadCheck));
            sep = peekToken();
            if (OR.matches(sep)) {
                consumeToken();
            }
        }
        if (ranges.size() == 1) {
            return ranges.iterator().next();
        } else {
            return dataFactory.getOWLDataUnionOf(ranges);
        }
    }

    @Nonnull
    private OWLDataRange parseDataRangePrimary(boolean lookaheadCheck) {
        String tok = peekToken();
        if (isDatatypeName(tok)) {
            consumeToken();
            OWLDatatype datatype = getOWLDatatype(tok);
            String next = peekToken();
            if (OPENBRACKET.matches(next)) {
                // Restricted data range
                consumeToken();
                String sep = COMMA.keyword();
                Set<OWLFacetRestriction> facetRestrictions = new HashSet<>();
                while (COMMA.matches(sep)) {
                    OWLFacet fv = parseFacet();
                    if (fv == null) {
                        throw new ExceptionBuilder().withKeyword(OWLFacet.getFacets()).build();
                    }
                    OWLLiteral con = parseLiteral(datatype);
                    facetRestrictions.add(dataFactory.getOWLFacetRestriction(fv, con));
                    sep = consumeToken();
                }
                if (!CLOSEBRACKET.matches(sep)) {
                    throw new ExceptionBuilder().withKeyword(CLOSEBRACKET).build();
                }
                return dataFactory.getOWLDatatypeRestriction(datatype, facetRestrictions);
            } else {
                return datatype;
            }
        } else if (NOT.matches(tok)) {
            return parseDataComplementOf(false);
        } else if (OPENBRACE.matches(tok)) {
            return parseDataOneOf();
        } else if (OPEN.matches(tok)) {
            consumeToken();
            OWLDataRange rng = parseDataIntersectionOf(false);
            consumeToken(CLOSE.keyword());
            return rng;
        } else {
            // XXX problem: if the class expression is missing, we should return
            // owl:Thing. But there are many ways in which it could be missing.
            // Hard
            // to tell what sort of lookahead is needed.
            // The next two checks should cover most cases.
            for (ManchesterOWLSyntax x : values()) {
                if (x.matches(tok)) {
                    return dataFactory.getTopDatatype();
                }
            }
            if (eof(tok) && lookaheadCheck) {
                return dataFactory.getTopDatatype();
            }
            consumeToken();
            throw new ExceptionBuilder().withDt().withKeyword(OPENBRACE, NOT).build();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDataRange> parseDataRangeList() {
        String sep = COMMA.keyword();
        Set<OWLDataRange> ranges = new HashSet<>();
        while (COMMA.matches(sep)) {
            potentialKeywords.remove(COMMA);
            OWLDataRange rng = parseDataIntersectionOf(false);
            ranges.add(rng);
            potentialKeywords.add(COMMA);
            sep = peekToken();
            if (COMMA.matches(sep)) {
                consumeToken();
            }
        }
        return ranges;
    }

    @Nonnull
    private OWLDataRange parseDataOneOf() {
        consumeToken();
        Set<OWLLiteral> cons = new HashSet<>();
        String sep = COMMA.keyword();
        while (COMMA.matches(sep)) {
            OWLLiteral con = parseLiteral(null);
            cons.add(con);
            sep = consumeToken();
        }
        if (!CLOSEBRACE.matches(sep)) {
            throw new ExceptionBuilder().withKeyword(COMMA, CLOSEBRACE).build();
        }
        return dataFactory.getOWLDataOneOf(cons);
    }

    @Nonnull
    private OWLDataRange parseDataComplementOf(boolean lookaheadCheck) {
        String not = consumeToken();
        if (!NOT.matches(not)) {
            throw new ExceptionBuilder().withKeyword(NOT).build();
        }
        OWLDataRange complementedDataRange = parseDataRangePrimary(lookaheadCheck);
        return dataFactory.getOWLDataComplementOf(complementedDataRange);
    }

    @Nonnull
    @Override
    public OWLLiteral parseLiteral(OWLDatatype datatype) {
        String tok = consumeToken();
        if (tok.startsWith("\"")) {
            @Nonnull
            String lit = unquoteLiteral(tok);
            if (peekToken().equals("^")) {
                consumeToken();
                if (!peekToken().equals("^")) {
                    throw new ExceptionBuilder().withKeyword("^").build();
                }
                consumeToken();
                return dataFactory.getOWLLiteral(lit, parseDatatype());
            } else if (peekToken().startsWith("@")) {
                // Plain literal with a language tag
                String lang = consumeToken().substring(1);
                return dataFactory.getOWLLiteral(lit, lang);
            } else {
                // Plain literal without a language tag
                return dataFactory.getOWLLiteral(lit, "");
            }
        } else {
            if (datatype != null) {
                // datatype is known from context
                return dataFactory.getOWLLiteral(tok, datatype);
            }
            try {
                int i = Integer.parseInt(tok);
                return dataFactory.getOWLLiteral(i);
            } catch (@SuppressWarnings("unused") NumberFormatException e) {
                // Ignore - not interested
            }
            if (tok.endsWith("f") || tok.endsWith("F")) {
                try {
                    // XXX this extra F might qualify as Float a Double INF/-INF
                    float f =
                        Float.parseFloat(tok.replace("INF", "Infinity").replace("inf", "Infinity"));
                    return dataFactory.getOWLLiteral(asFloat(f), OWL2Datatype.XSD_FLOAT);
                } catch (@SuppressWarnings("unused") NumberFormatException e) {
                    // Ignore - not interested
                }
            }
            try {
                // ensure it's a valid double, or skip
                Double.parseDouble(tok);
                return dataFactory.getOWLLiteral(tok, OWL2Datatype.XSD_DECIMAL);
            } catch (@SuppressWarnings("unused") NumberFormatException e) {
                // Ignore - not interested
            }
            if (LITERAL_TRUE.matches(tok)) {
                return dataFactory.getOWLLiteral(true);
            } else if (LITERAL_FALSE.matches(tok)) {
                return dataFactory.getOWLLiteral(false);
            }
        }
        throw new ExceptionBuilder().withKeyword(LITERAL_TRUE, LITERAL_FALSE, LITERAL_INTEGER,
            LITERAL_FLOAT, LITERAL_DOUBLE, LITERAL_LITERAL, LITERAL_LIT_DATATYPE, LITERAL_LIT_LANG)
            .build();
    }

    @Nonnull
    private String unquoteLiteral(String tok) {
        if (!tok.endsWith("\"")) {
            consumeToken();
            throw new ExceptionBuilder().withKeyword("\"").build();
        }
        String lit = "";
        if (tok.length() > 2) {
            lit = tok.substring(1, tok.length() - 1);
        }
        return verifyNotNull(lit);
    }

    @Nonnull
    private static String asFloat(float f) {
        return Float.toString(f).replace("Infinity", "INF");
    }

    private int parseInteger() {
        String i = consumeToken();
        try {
            return Integer.parseInt(i);
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
            throw new ExceptionBuilder().withInt().build();
        }
    }

    @Nonnull
    private OWLClassExpression parseNestedClassExpression(boolean lookaheadCheck) {
        String tok = peekToken();
        if (OPEN.matches(tok)) {
            consumeToken();
            OWLClassExpression desc = parseUnion();
            String closeBracket = consumeToken();
            if (!CLOSE.matches(closeBracket)) {
                // Error!
                throw new ExceptionBuilder().withKeyword(CLOSE).build();
            }
            return desc;
        } else if (OPENBRACE.matches(tok)) {
            return parseObjectOneOf();
        } else if (isClassName(tok)) {
            String name = consumeToken();
            return getOWLClass(name);
        }
        // XXX problem: if the class expression is missing, we should return
        // owl:Thing. But there are many ways in which it could be missing. Hard
        // to tell what sort of lookahead is needed.
        // The next two checks should cover most cases.
        for (ManchesterOWLSyntax x : values()) {
            if (x.matches(tok)) {
                return dataFactory.getOWLThing();
            }
        }
        if (eof(tok)) {
            return dataFactory.getOWLThing();
        }
        if (!eof(tok) || !lookaheadCheck) {
            consumeToken();
            throw new ExceptionBuilder().withKeyword(OPEN, OPENBRACE).withClass().build();
        }
        return dataFactory.getOWLThing();
    }

    @Nonnull
    private OWLClassExpression parseObjectOneOf() {
        String open = consumeToken();
        if (!OPENBRACE.matches(open)) {
            throw new ExceptionBuilder().withKeyword(OPENBRACE).build();
        }
        String sep = COMMA.keyword();
        Set<OWLIndividual> inds = new HashSet<>();
        while (COMMA.matches(sep)) {
            inds.add(parseIndividual());
            sep = peekToken();
            if (COMMA.matches(sep)) {
                consumeToken();
            }
        }
        String close = consumeToken();
        if (!CLOSEBRACE.matches(close)) {
            throw new ExceptionBuilder().withKeyword(CLOSEBRACE, COMMA).build();
        }
        return dataFactory.getOWLObjectOneOf(inds);
    }

    private static <F> void initialiseSection(AnnotatedListItemParser<F, ?> parser,
        Map<ManchesterOWLSyntax, AnnotatedListItemParser<F, ?>> map,
        ManchesterOWLSyntax... synonyms) {
        map.put(parser.getFrameSectionKeyword(), parser);
        for (ManchesterOWLSyntax syn : synonyms) {
            map.put(syn, parser);
        }
    }

    @Override
    @Nonnull
    public Set<OntologyAxiomPair> parseFrames() {
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        Set<ManchesterOWLSyntax> possible = new HashSet<>();
        resetPossible(possible);
        while (true) {
            String tok = peekToken();
            if (CLASS.matches(tok)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseClassFrame());
                possible.addAll(simpleClassFrameSections.keySet());
            } else if (OBJECT_PROPERTY.matches(tok)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseObjectPropertyFrame());
                possible.addAll(objectPropertyFrameSections.keySet());
            } else if (DATA_PROPERTY.matches(tok)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseDataPropertyFrame());
                possible.addAll(dataPropertyFrameSections.keySet());
            } else if (ANNOTATION_PROPERTY.matches(tok)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseAnnotationPropertyFrame());
                possible.addAll(Arrays.asList(SUB_PROPERTY_OF, DOMAIN, RANGE));
            } else if (INDIVIDUAL.matches(tok)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseIndividualFrame());
                possible.addAll(Arrays.asList(TYPES, FACTS, DIFFERENT_FROM, SAME_AS));
            } else if (DATATYPE.matches(tok)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseDatatypeFrame());
                possible.add(EQUIVALENT_TO);
            } else if (VALUE_PARTITION.matches(tok)) {
                potentialKeywords.clear();
                resetPossible(possible);
                parseValuePartitionFrame();
            } else if (RULE.matches(tok)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseRuleFrame());
            } else {
                if (eof(tok)) {
                    break;
                } else {
                    consumeToken();
                    throw new ExceptionBuilder().withKeyword(possible).build();
                }
            }
        }
        return axioms;
    }

    @Override
    public Set<OntologyAxiomPair> parseDatatypeFrame() {
        String tok = consumeToken();
        if (!DATATYPE.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(DATATYPE).build();
        }

        Set<OWLAnnotation> annotations = parseAnnotations();
        String subj = consumeToken();
        OWLDatatype datatype = getOWLDatatype(subj);
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        axioms.add(new OntologyAxiomPair(defaultOntology, dataFactory.getOWLDeclarationAxiom(datatype, annotations)));
        while (true) {
            String sect = peekToken();
            if (EQUIVALENT_TO.matches(sect)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLDataRange> drs = parseDataRangeList();
                for (OWLOntology ont : onts) {
                    assert ont != null;
                    for (OWLDataRange dr : drs) {
                        assert dr != null;
                        axioms.add(new OntologyAxiomPair(ont,
                            dataFactory.getOWLDatatypeDefinitionAxiom(datatype, dr)));
                    }
                }
            } else if (ANNOTATIONS.matches(sect)) {
                potentialKeywords.clear();
                axioms.addAll(parseAnnotations(datatype.getIRI()));
            } else {
                break;
            }
        }
        return axioms;
    }

    private static void resetPossible(Set<ManchesterOWLSyntax> possible) {
        possible.clear();
        possible.add(ANNOTATIONS);
        possible.add(ANNOTATION_PROPERTY);
        possible.add(CLASS);
        possible.add(OBJECT_PROPERTY);
        possible.add(DATATYPE);
        possible.add(DATA_PROPERTY);
        possible.add(INDIVIDUAL);
        possible.add(VALUE_PARTITION);
        possible.add(RULE);
    }

    private Set<OntologyAxiomPair> parseNaryEquivalentClasses() {
        String tok = consumeToken();
        if (!EQUIVALENT_CLASSES.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(EQUIVALENT_CLASSES).build();
        }
        Set<OWLOntology> ontologies = getOntologies();
        Set<OWLAnnotation> annotations = parseAnnotations();
        Set<OWLClassExpression> classExpressions = parseClassExpressionList();
        Set<OntologyAxiomPair> pairs = new HashSet<>();
        for (OWLOntology ont : ontologies) {
            assert ont != null;
            pairs.add(new OntologyAxiomPair(ont,
                dataFactory.getOWLEquivalentClassesAxiom(classExpressions, annotations)));
        }
        return pairs;
    }

    private Set<OntologyAxiomPair> parseNaryEquivalentProperties() {
        String tok = consumeToken();
        if (!EQUIVALENT_PROPERTIES.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(EQUIVALENT_PROPERTIES).build();
        }
        Set<OWLOntology> ontologies = getOntologies();
        Set<OWLAnnotation> annotations = parseAnnotations();
        Set<OWLPropertyExpression> properties = parsePropertyList();
        OWLAxiom propertyAxiom;
        if (properties.iterator().next().isObjectPropertyExpression()) {
            Set<OWLObjectPropertyExpression> ope = new HashSet<>();
            for (OWLPropertyExpression pe : properties) {
                ope.add((OWLObjectPropertyExpression) pe);
            }
            propertyAxiom = dataFactory.getOWLEquivalentObjectPropertiesAxiom(ope, annotations);
        } else {
            Set<OWLDataPropertyExpression> dpe = new HashSet<>();
            for (OWLPropertyExpression pe : properties) {
                dpe.add((OWLDataPropertyExpression) pe);
            }
            propertyAxiom = dataFactory.getOWLEquivalentDataPropertiesAxiom(dpe, annotations);
        }
        Set<OntologyAxiomPair> pairs = new HashSet<>();
        for (OWLOntology ont : ontologies) {
            assert ont != null;
            pairs.add(new OntologyAxiomPair(ont, propertyAxiom));
        }
        return pairs;
    }

    @Nonnull
    private Set<OWLAnnotation> parseAnnotations() {
        String next = peekToken();
        @Nonnull
        Set<OWLAnnotation> annotations = CollectionFactory.emptySet();
        if (ANNOTATIONS.matches(next)) {
            consumeToken();
            annotations = parseAnnotationList();
        }
        return annotations;
    }

    private Set<OntologyAxiomPair> parseAnnotations(@Nonnull OWLAnnotationSubject s) {
        String header = consumeToken();
        if (!ANNOTATIONS.matches(header)) {
            throw new ExceptionBuilder().withKeyword(ANNOTATIONS).build();
        }
        Set<OWLOntology> onts = getOntologies();
        Set<OntologyAxiomPair> pairs = new HashSet<>();
        Set<OWLAnnotation> annos = parseAnnotationList();
        for (OWLOntology ont : onts) {
            assert ont != null;
            for (OWLAnnotation anno : annos) {
                assert anno != null;
                if (getOntologyLoaderConfiguration().isLoadAnnotationAxioms()) {
                    pairs.add(new OntologyAxiomPair(ont,
                        dataFactory.getOWLAnnotationAssertionAxiom(s, anno)));
                }
            }
        }
        return pairs;
    }

    @Nonnull
    private Set<OWLAnnotation> parseAnnotationList() {
        String sep = COMMA.keyword();
        Set<OWLAnnotation> annos = new HashSet<>();
        while (COMMA.matches(sep)) {
            potentialKeywords.clear();
            Set<OWLAnnotation> annotations = parseAnnotations();
            OWLAnnotation anno = parseAnnotation();
            anno = anno.getAnnotatedAnnotation(annotations);
            annos.add(anno);
            sep = peekToken();
            if (COMMA.matches(sep)) {
                consumeToken();
            }
        }
        return annos;
    }

    @Nonnull
    protected OWLAnnotation parseAnnotation() {
        OWLAnnotationProperty annoProp = parseAnnotationProperty();
        String obj = peekToken();
        OWLAnnotation anno = null;
        if (isIndividualName(obj) || isClassName(obj) || isObjectPropertyName(obj)
            || isDataPropertyName(obj)) {
            consumeToken();
            OWLAnnotationValue value;
            if (obj.startsWith("_:")) {
                value = anonProvider.getOWLAnonymousIndividual(obj);
            } else {
                value = getIRI(obj);
            }
            anno = dataFactory.getOWLAnnotation(annoProp, value);
        } else if (obj.startsWith("<")) {
            IRI value = parseIRI();
            anno = dataFactory.getOWLAnnotation(annoProp, value);
        } else {
            OWLLiteral con = parseLiteral(null);
            anno = dataFactory.getOWLAnnotation(annoProp, con);
        }
        return anno;
    }

    @Override
    public Set<OntologyAxiomPair> parseClassFrame() {
        return parseClassFrame(false);
    }

    @Override
    public Set<OntologyAxiomPair> parseClassFrameEOF() {
        return parseClassFrame(true);
    }

    @Nonnull
    private Set<OntologyAxiomPair> parseClassFrame(boolean eof) {
        String tok = consumeToken();
        if (!CLASS.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(CLASS).build();
        }
        Set<OWLAnnotation> annotations = parseAnnotations();
        OWLClassExpression cls = parseUnion();
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        if (cls.isNamed()) {
            axioms.add(new OntologyAxiomPair(defaultOntology,
                    dataFactory.getOWLDeclarationAxiom(cls.asOWLClass(), annotations)));
            parseFrameSections(eof, axioms, cls.asOWLClass(), simpleClassFrameSections);
        }

        parseFrameSections(eof, axioms, cls, complexClassFrameSections);
        return axioms;
    }

    @Nonnull
    private Set<OWLOntology> parseOntologyList() {
        potentialKeywords.clear();
        consumeToken(OPENBRACKET.keyword());
        consumeToken("in");
        String sep = COMMA.keyword();
        Set<OWLOntology> onts = new HashSet<>();
        while (COMMA.matches(sep)) {
            String tok = consumeToken();
            if (isOntologyName(tok)) {
                OWLOntology ont = getOntology(tok);
                if (ont != null) {
                    onts.add(ont);
                }
            } else {
                throw new ExceptionBuilder().withOnto().build();
            }
            sep = consumeToken();
            if (sep.equals(CLOSEBRACKET.keyword())) {
                break;
            } else if (!COMMA.matches(sep)) {
                throw new ExceptionBuilder().withKeyword(COMMA, CLOSEBRACKET).build();
            }
        }
        return onts;
    }

    @Nonnull
    private Set<OWLOntology> getOntologies() {
        if (peekToken().equals(OPENBRACKET.keyword())) {
            return parseOntologyList();
        } else {
            return CollectionFactory.createSet(defaultOntology);
        }
    }

    @Override
    public void setDefaultOntology(@Nonnull OWLOntology defaultOntology) {
        this.defaultOntology = defaultOntology;
        for (OWLDeclarationAxiom d : defaultOntology.getAxioms(AxiomType.DECLARATION,
            Imports.INCLUDED)) {
            processDeclaredEntities(d);
        }
    }

    private boolean isEmptyFrameSection(Map<ManchesterOWLSyntax, ?> parsers) {
        if (!allowEmptyFrameSections) {
            return false;
        }
        String next = peekToken();
        return !ANNOTATIONS.matches(next) && (parsers.containsKey(parse(next)) || eof(next));
    }

    private <F> void parseFrameSections(boolean eof, Set<OntologyAxiomPair> axioms,
        @Nonnull F frameSubject,
        Map<ManchesterOWLSyntax, AnnotatedListItemParser<F, ?>> sectionParsers) {
        while (true) {
            String sect = peekToken();
            AnnotatedListItemParser<F, ?> parser = sectionParsers.get(parse(sect));
            if (parser != null) {
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                if (!isEmptyFrameSection(sectionParsers)) {
                    axioms.addAll(parseAnnotatedListItems(frameSubject, parser, onts));
                }
            } else if (eof && !eof(sect)) {
                List<ManchesterOWLSyntax> expected = new ArrayList<>();
                expected.addAll(sectionParsers.keySet());
                if (frameSubject instanceof OWLAnnotationSubject
                    || frameSubject instanceof OWLEntity) {
                    expected.add(ANNOTATIONS);
                }
                throw new ExceptionBuilder().withKeyword(expected).build();
            } else {
                break;
            }
        }
    }

    @Override
    public Set<OntologyAxiomPair> parseObjectPropertyFrame() {
        return parseObjectPropertyFrame(false);
    }

    @Nonnull
    private Set<OntologyAxiomPair> parseObjectPropertyFrame(boolean eof) {
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        consumeToken(OBJECT_PROPERTY);
        Set<OWLAnnotation> annotations = parseAnnotations();
        String token = consumeToken();
        OWLObjectProperty prop = getOWLObjectProperty(token);
        if (!prop.isAnonymous()) {
            axioms.add(new OntologyAxiomPair(defaultOntology,
                dataFactory.getOWLDeclarationAxiom(prop.asOWLObjectProperty(), annotations)));
        }
        parseFrameSections(eof, axioms, prop, objectPropertyFrameSections);
        return axioms;
    }

    @Override
    public Set<OntologyAxiomPair> parseDataPropertyFrame() {
        String tok = consumeToken();
        if (!DATA_PROPERTY.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(DATA_PROPERTY).build();
        }

        Set<OWLAnnotation> annotations = parseAnnotations();
        String subj = consumeToken();
        OWLDataProperty prop = getOWLDataProperty(subj);
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        axioms
            .add(new OntologyAxiomPair(defaultOntology, dataFactory.getOWLDeclarationAxiom(prop, annotations)));
        parseFrameSections(false, axioms, prop, dataPropertyFrameSections);
        return axioms;
    }

    @Override
    public Set<OntologyAxiomPair> parseAnnotationPropertyFrame() {
        String tok = consumeToken();
        if (!ANNOTATION_PROPERTY.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(ANNOTATION_PROPERTY).build();
        }

        Set<OWLAnnotation> annotations = parseAnnotations();
        String subj = consumeToken();
        OWLAnnotationProperty prop = getOWLAnnotationProperty(subj);
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        for (OWLOntology ont : getOntologies()) {
            axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLDeclarationAxiom(prop, annotations)));
        }
        parseFrameSections(false, axioms, prop, annotationPropertyFrameSections);
        return axioms;
    }

    @Override
    public Set<OntologyAxiomPair> parseIndividualFrame() {
        String tok = consumeToken();
        if (!INDIVIDUAL.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(INDIVIDUAL).build();
        }

        Set<OWLAnnotation> annotations = parseAnnotations();
        String subj = consumeToken();
        OWLIndividual ind = getOWLIndividual(subj);
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        if (!ind.isAnonymous()) {
            axioms.add(new OntologyAxiomPair(getOntology(null),
                dataFactory.getOWLDeclarationAxiom(ind.asOWLNamedIndividual(), annotations)));
        }
        parseFrameSections(false, axioms, ind, individualFrameSections);
        return axioms;
    }

    @Nonnull
    protected OWLPropertyAssertionAxiom<?, ?> parseFact(@Nonnull OWLIndividual ind) {
        boolean negative = false;
        if (NOT.matches(peekToken())) {
            consumeToken();
            negative = true;
        }
        String prop = peekToken();
        if (isDataPropertyName(prop)) {
            OWLDataProperty p = parseDataProperty();
            OWLLiteral con = parseLiteral(null);
            if (!negative) {
                return dataFactory.getOWLDataPropertyAssertionAxiom(p, ind, con);
            } else {
                return dataFactory.getOWLNegativeDataPropertyAssertionAxiom(p, ind, con);
            }
        } else if (isObjectPropertyName(prop) || INVERSE.matches(prop)) {
            OWLObjectPropertyExpression p = parseObjectPropertyExpression(false);
            if (!negative) {
                return dataFactory.getOWLObjectPropertyAssertionAxiom(p, ind, parseIndividual());
            } else {
                return dataFactory.getOWLNegativeObjectPropertyAssertionAxiom(p, ind,
                    parseIndividual());
            }
        } else {
            consumeToken();
            throw new ExceptionBuilder().withObject().withData().build();
        }
    }

    @Override
    public Set<OntologyAxiomPair> parseValuePartitionFrame() {
        String section = consumeToken();
        if (!VALUE_PARTITION.matches(section)) {
            throw new ExceptionBuilder().withKeyword(VALUE_PARTITION).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        String clsName = consumeToken();
        if (eof(clsName)) {
            throw new ExceptionBuilder().withObject().build();
        }
        OWLClass cls = getOWLClass(clsName);
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        Set<OWLOntology> onts = getOntologies();
        axioms.addAll(parseValuePartitionValues(onts, cls));
        for (OWLOntology ont : onts) {
            assert ont != null;
            axioms.add(
                new OntologyAxiomPair(ont, dataFactory.getOWLFunctionalObjectPropertyAxiom(prop)));
            axioms.add(
                new OntologyAxiomPair(ont, dataFactory.getOWLObjectPropertyRangeAxiom(prop, cls)));
        }
        return axioms;
    }

    @Nonnull
    private Set<OntologyAxiomPair> parseValuePartitionValues(@Nonnull Set<OWLOntology> onts,
        @Nonnull OWLClass superclass) {
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        Set<OWLClass> siblings = new HashSet<>();
        consumeToken(OPENBRACKET.keyword());
        String sep = COMMA.keyword();
        while (COMMA.matches(sep)) {
            String clsName = consumeToken();
            OWLClass cls = getOWLClass(clsName);
            siblings.add(cls);
            OWLSubClassOfAxiom ax = dataFactory.getOWLSubClassOfAxiom(cls, superclass);
            for (OWLOntology ont : onts) {
                assert ont != null;
                axioms.add(new OntologyAxiomPair(ont, ax));
            }
            if (peekToken().equals(OPENBRACKET.keyword())) {
                axioms.addAll(parseValuePartitionValues(onts, cls));
            }
            sep = peekToken();
            if (COMMA.matches(sep)) {
                consumeToken();
            }
        }
        consumeToken(CLOSEBRACKET.keyword());
        OWLAxiom ax = dataFactory.getOWLDisjointClassesAxiom(siblings);
        for (OWLOntology ont : onts) {
            assert ont != null;
            axioms.add(new OntologyAxiomPair(ont, ax));
        }
        return axioms;
    }

    @Override
    public List<OntologyAxiomPair> parseRuleFrame() {
        String section = consumeToken();
        if (!RULE.matches(section)) {
            throw new ExceptionBuilder().withKeyword(RULE).build();
        }
        List<SWRLAtom> body = parseRuleAtoms();
        String tok = consumeToken();
        if (!DASH.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(DASH, COMMA).build();
        }
        consumeToken(">");
        List<SWRLAtom> head = parseRuleAtoms();
        SWRLRule rule =
            dataFactory.getSWRLRule(new LinkedHashSet<>(body), new LinkedHashSet<>(head));
        List<OntologyAxiomPair> pairs = new ArrayList<>();
        for (OWLOntology ont : getOntologies()) {
            assert ont != null;
            pairs.add(new OntologyAxiomPair(ont, rule));
        }
        return pairs;
    }

    private List<SWRLAtom> parseRuleAtoms() {
        String sep = COMMA.keyword();
        List<SWRLAtom> atoms = new ArrayList<>();
        while (COMMA.matches(sep)) {
            potentialKeywords.remove(COMMA);
            SWRLAtom atom = parseRuleAtom();
            atoms.add(atom);
            sep = peekToken();
            if (COMMA.matches(sep)) {
                consumeToken();
            }
            potentialKeywords.add(COMMA);
        }
        return atoms;
    }

    private SWRLAtom parseRuleAtom() {
        String predicate = peekToken();
        if (isClassName(predicate)) {
            return parseClassAtom();
        } else if (OPEN.matches(predicate)) {
            return parseClassAtom();
        } else if (isObjectPropertyName(predicate)) {
            return parseObjectPropertyAtom();
        } else if (isDataPropertyName(predicate)) {
            return parseDataPropertyAtom();
        } else if (isDatatypeName(predicate)) {
            return parseDataRangeAtom();
        } else if (DIFFERENT_FROM.matchesEitherForm(predicate)) {
            return parseDifferentFromAtom();
        } else if (SAME_AS.matchesEitherForm(predicate)) {
            return parseSameAsAtom();
        } else if (isSWRLBuiltin(predicate) || predicate.startsWith("<")) {
            return parseBuiltInAtom();
        } else {
            consumeToken();
            List<String> kw = new ArrayList<>(ruleBuiltIns.keySet());
            kw.add(DIFFERENT_FROM.toString());
            kw.add(SAME_AS.toString());
            Collections.sort(kw);
            throw new ExceptionBuilder().withKeyword(kw).withClass().withObject().withData()
                .build();
        }
    }

    private SWRLAtom parseDataPropertyAtom() {
        String predicate = consumeToken();
        if (!isDataPropertyName(predicate)) {
            throw new ExceptionBuilder().withData().build();
        }
        consumeToken(OPEN.keyword());
        SWRLIArgument obj1 = parseIObject();
        consumeToken(COMMA.keyword());
        SWRLDArgument obj2 = parseDObject();
        consumeToken(CLOSE.keyword());
        return dataFactory.getSWRLDataPropertyAtom(getOWLDataProperty(predicate), obj1, obj2);
    }

    private SWRLAtom parseDataRangeAtom() {
        OWLDataRange range = parseDataIntersectionOf(false);
        consumeToken(OPEN.keyword());
        SWRLVariable obj1 = parseDVariable();
        consumeToken(CLOSE.keyword());
        return dataFactory.getSWRLDataRangeAtom(range, obj1);
    }

    private SWRLAtom parseObjectPropertyAtom() {
        String predicate = consumeToken();
        if (!isObjectPropertyName(predicate)) {
            throw new ExceptionBuilder().withObject().build();
        }
        assert predicate != null;
        consumeToken(OPEN.keyword());
        SWRLIArgument obj1 = parseIObject();
        consumeToken(COMMA.keyword());
        SWRLIArgument obj2 = parseIObject();
        consumeToken(CLOSE.keyword());
        return dataFactory.getSWRLObjectPropertyAtom(getOWLObjectProperty(predicate), obj1, obj2);
    }

    private SWRLAtom parseClassAtom() {
        OWLClassExpression predicate = parseUnion();
        consumeToken(OPEN.keyword());
        SWRLIArgument obj = parseIObject();
        consumeToken(CLOSE.keyword());
        return dataFactory.getSWRLClassAtom(predicate, obj);
    }

    private SWRLDifferentIndividualsAtom parseDifferentFromAtom() {
        consumeToken(ManchesterOWLSyntax.DIFFERENT_FROM.toString());
        consumeToken(OPEN.keyword());
        SWRLIArgument obj1 = parseIObject();
        consumeToken(COMMA.keyword());
        SWRLIArgument obj2 = parseIObject();
        consumeToken(CLOSE.keyword());
        return dataFactory.getSWRLDifferentIndividualsAtom(obj1, obj2);
    }

    private SWRLSameIndividualAtom parseSameAsAtom() {
        consumeToken(ManchesterOWLSyntax.SAME_AS.toString());
        consumeToken(OPEN.keyword());
        SWRLIArgument obj1 = parseIObject();
        consumeToken(COMMA.keyword());
        SWRLIArgument obj2 = parseIObject();
        consumeToken(CLOSE.keyword());
        return dataFactory.getSWRLSameIndividualAtom(obj1, obj2);
    }

    @Nonnull
    private SWRLIArgument parseIObject() {
        String s = peekToken();
        if (isIndividualName(s)) {
            return parseIIndividualObject();
        } else if (s.equals("?")) {
            return parseIVariable();
        } else {
            consumeToken();
            throw new ExceptionBuilder().withInd().withKeyword("?$var$").build();
        }
    }

    @Nonnull
    private SWRLVariable parseIVariable() {
        return dataFactory.getSWRLVariable(parseVariable());
    }

    @Nonnull
    private SWRLIndividualArgument parseIIndividualObject() {
        return dataFactory.getSWRLIndividualArgument(parseIndividual());
    }

    @Override
    public IRI parseVariable() {
        consumeToken("?");
        String fragment = peekToken();
        if (fragment.startsWith("<")) {
            // then the variable was saved with a full IRI
            // preserve the namespace
            IRI parseIRI = parseIRI();
            // old style namespace? change it
            if ("urn:swrl#".equals(parseIRI.getNamespace())) {
                parseIRI = IRI.create("urn:swrl:var#", parseIRI.getFragment());
            }
            return parseIRI;
        } else {
            consumeToken();
        }
        return IRI.create("urn:swrl:var#", fragment);
    }

    @Nonnull
    private SWRLDArgument parseDObject() {
        String s = peekToken();
        if (s.equals("?")) {
            return parseDVariable();
        } else {
            try {
                return parseLiteralObject();
            } catch (ParserException e) {
                e.getExpectedKeywords().add("?");
                throw e;
            }
        }
    }

    @Nonnull
    private SWRLVariable parseDVariable() {
        IRI var = parseVariable();
        return dataFactory.getSWRLVariable(var);
    }

    @Nonnull
    private SWRLLiteralArgument parseLiteralObject() {
        OWLLiteral lit = parseLiteral(null);
        return dataFactory.getSWRLLiteralArgument(lit);
    }

    private SWRLBuiltInAtom parseBuiltInAtom() {
        String predicate = consumeToken();
        consumeToken(OPEN.keyword());
        SWRLBuiltInsVocabulary v = null;
        IRI iri = null;
        if (!ruleBuiltIns.containsKey(predicate)) {
            iri = getIRI(predicate);
        } else {
            v = ruleBuiltIns.get(predicate);
            iri = v.getIRI();
        }
        List<SWRLDArgument> args = new ArrayList<>();
        if (v != null && v.getMaxArity() >= 0) {
            // We know the arity!
            for (int i = 0; i < v.getMaxArity(); i++) {
                SWRLDArgument obj = parseDObject();
                args.add(obj);
                // parse at least the minumum arity
                if (i < v.getMinArity() - 1) {
                    consumeToken(COMMA.keyword());
                } else if (i < v.getMaxArity() - 1) {
                    if (peekToken().equals(COMMA.keyword())) {
                        consumeToken();
                    } else {
                        break;
                    }
                }
            }
        } else {
            // Unknown arity so just parse as many arguments as we can
            String sep = COMMA.keyword();
            while (COMMA.matches(sep)) {
                SWRLDArgument arg = parseDObject();
                args.add(arg);
                sep = peekToken();
                if (COMMA.matches(sep)) {
                    consumeToken();
                }
            }
        }
        consumeToken(CLOSE.keyword());
        return dataFactory.getSWRLBuiltInAtom(iri, args);
    }

    private Set<OntologyAxiomPair> parseDisjointClasses() {
        String section = consumeToken();
        if (!DISJOINT_CLASSES.matches(section)) {
            throw new ExceptionBuilder().withKeyword(DISJOINT_CLASSES).build();
        }
        Set<OWLOntology> ontologies = getOntologies();
        Set<OWLAnnotation> annotations = parseAnnotations();
        Set<OWLClassExpression> classExpressions = parseClassExpressionList();
        Set<OntologyAxiomPair> pairs = new HashSet<>();
        for (OWLOntology ont : ontologies) {
            assert ont != null;
            pairs.add(new OntologyAxiomPair(ont,
                dataFactory.getOWLDisjointClassesAxiom(classExpressions, annotations)));
        }
        return pairs;
    }

    private Set<OntologyAxiomPair> parseSameIndividual() {
        String section = consumeToken();
        if (!SAME_INDIVIDUAL.matches(section)) {
            throw new ExceptionBuilder().withKeyword(SAME_INDIVIDUAL).build();
        }
        Set<OWLOntology> ontologies = getOntologies();
        Set<OWLAnnotation> annotations = parseAnnotations();
        Set<OWLIndividual> individuals = parseIndividualList();
        Set<OntologyAxiomPair> pairs = new HashSet<>();
        for (OWLOntology ont : ontologies) {
            assert ont != null;
            pairs.add(new OntologyAxiomPair(ont,
                dataFactory.getOWLSameIndividualAxiom(individuals, annotations)));
        }
        return pairs;
    }

    private Set<OntologyAxiomPair> parseDisjointProperties() {
        String section = consumeToken();
        if (!DISJOINT_PROPERTIES.matches(section)) {
            throw new ExceptionBuilder().withKeyword(DISJOINT_PROPERTIES).build();
        }
        Set<OWLOntology> ontologies = getOntologies();
        Set<OWLAnnotation> annotations = parseAnnotations();
        Set<OWLPropertyExpression> props = parsePropertyList();
        Set<OntologyAxiomPair> pairs = new HashSet<>();
        OWLAxiom propertiesAxiom;
        if (props.iterator().next().isObjectPropertyExpression()) {
            Set<OWLObjectPropertyExpression> ope = new HashSet<>();
            for (OWLPropertyExpression pe : props) {
                ope.add((OWLObjectPropertyExpression) pe);
            }
            propertiesAxiom = dataFactory.getOWLDisjointObjectPropertiesAxiom(ope, annotations);
        } else {
            Set<OWLDataPropertyExpression> dpe = new HashSet<>();
            for (OWLPropertyExpression pe : props) {
                dpe.add((OWLDataPropertyExpression) pe);
            }
            propertiesAxiom = dataFactory.getOWLDisjointDataPropertiesAxiom(dpe, annotations);
        }
        for (OWLOntology ont : ontologies) {
            assert ont != null;
            pairs.add(new OntologyAxiomPair(ont, propertiesAxiom));
        }
        return pairs;
    }

    private Set<OntologyAxiomPair> parseDifferentIndividuals() {
        String section = consumeToken();
        if (!DIFFERENT_INDIVIDUALS.matches(section)) {
            throw new ExceptionBuilder().withKeyword(DIFFERENT_INDIVIDUALS).build();
        }
        Set<OWLOntology> ontologies = getOntologies();
        Set<OWLAnnotation> annotations = parseAnnotations();
        Set<OWLIndividual> individuals = parseIndividualList();
        Set<OntologyAxiomPair> pairs = new HashSet<>();
        for (OWLOntology ontology : ontologies) {
            assert ontology != null;
            pairs.add(new OntologyAxiomPair(ontology,
                dataFactory.getOWLDifferentIndividualsAxiom(individuals, annotations)));
        }
        return pairs;
    }

    @Nonnull
    protected OWLObjectPropertyCharacteristicAxiom parseObjectPropertyCharacteristic(
        @Nonnull OWLObjectPropertyExpression prop) {
        String characteristic = consumeToken();
        if (FUNCTIONAL.matches(characteristic)) {
            return dataFactory.getOWLFunctionalObjectPropertyAxiom(prop);
        } else if (INVERSE_FUNCTIONAL.matches(characteristic)) {
            return dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(prop);
        } else if (SYMMETRIC.matches(characteristic)) {
            return dataFactory.getOWLSymmetricObjectPropertyAxiom(prop);
        } else if (ANTI_SYMMETRIC.matches(characteristic) || ASYMMETRIC.matches(characteristic)) {
            return dataFactory.getOWLAsymmetricObjectPropertyAxiom(prop);
        } else if (TRANSITIVE.matches(characteristic)) {
            return dataFactory.getOWLTransitiveObjectPropertyAxiom(prop);
        } else if (REFLEXIVE.matches(characteristic)) {
            return dataFactory.getOWLReflexiveObjectPropertyAxiom(prop);
        } else if (IRREFLEXIVE.matches(characteristic)) {
            return dataFactory.getOWLIrreflexiveObjectPropertyAxiom(prop);
        } else {
            throw new ExceptionBuilder().withKeyword(FUNCTIONAL, INVERSE_FUNCTIONAL, SYMMETRIC,
                ANTI_SYMMETRIC, TRANSITIVE, REFLEXIVE, IRREFLEXIVE).build();
        }
    }

    @Nonnull
    protected OWLDataPropertyCharacteristicAxiom parseDataPropertyCharacteristic(
        @Nonnull OWLDataPropertyExpression prop) {
        String characteristic = consumeToken();
        if (FUNCTIONAL.matches(characteristic)) {
            return dataFactory.getOWLFunctionalDataPropertyAxiom(prop);
        } else {
            throw new ExceptionBuilder().withKeyword(FUNCTIONAL).build();
        }
    }

    @Override
    public Set<OWLClassExpression> parseClassExpressionList() {
        Set<OWLClassExpression> descs = new HashSet<>();
        String sep = COMMA.keyword();
        while (COMMA.matches(sep)) {
            potentialKeywords.remove(COMMA);
            descs.add(parseUnion());
            potentialKeywords.add(COMMA);
            sep = peekToken();
            if (COMMA.matches(sep)) {
                sep = consumeToken();
            }
        }
        return descs;
    }

    private Set<OWLClassExpression> parseClassExpressionList(ManchesterOWLSyntax expectedOpen,
        ManchesterOWLSyntax expectedClose) {
        String open = consumeToken();
        if (!expectedOpen.matches(open)) {
            throw new ExceptionBuilder().withKeyword(expectedOpen).build();
        }
        String sep = COMMA.keyword();
        Set<OWLClassExpression> descs = new HashSet<>();
        while (COMMA.matches(sep)) {
            potentialKeywords.remove(COMMA);
            OWLClassExpression desc = parseUnion();
            potentialKeywords.add(COMMA);
            descs.add(desc);
            sep = peekToken();
            if (COMMA.matches(sep)) {
                sep = consumeToken();
            }
        }
        String close = consumeToken();
        if (!expectedClose.matches(close)) {
            throw new ExceptionBuilder().withKeyword(expectedClose).build();
        }
        return descs;
    }

    @Override
    public Set<OWLPropertyExpression> parsePropertyList() {
        Set<OWLPropertyExpression> props = new HashSet<>();
        String sep = COMMA.keyword();
        while (COMMA.matches(sep)) {
            OWLPropertyExpression prop = parsePropertyExpression();
            props.add(prop);
            sep = peekToken();
            if (COMMA.matches(sep)) {
                consumeToken();
            }
        }
        return props;
    }

    @Override
    public Set<OWLObjectPropertyExpression> parseObjectPropertyList() {
        Set<OWLObjectPropertyExpression> props = new HashSet<>();
        String sep = COMMA.keyword();
        while (COMMA.matches(sep)) {
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
            props.add(prop);
            sep = peekToken();
            if (COMMA.matches(sep)) {
                consumeToken();
            }
        }
        return props;
    }

    @Override
    public Set<OWLDataProperty> parseDataPropertyList() {
        Set<OWLDataProperty> props = new HashSet<>();
        String sep = COMMA.keyword();
        while (COMMA.matches(sep)) {
            OWLDataProperty prop = parseDataProperty();
            props.add(prop);
            sep = peekToken();
            if (COMMA.matches(sep)) {
                consumeToken();
            }
        }
        return props;
    }

    @Override
    public Set<OWLAnnotationProperty> parseAnnotationPropertyList() {
        Set<OWLAnnotationProperty> props = new HashSet<>();
        String sep = COMMA.keyword();
        while (COMMA.matches(sep)) {
            OWLAnnotationProperty prop = parseAnnotationProperty();
            props.add(prop);
            sep = peekToken();
            if (COMMA.matches(sep)) {
                consumeToken();
            }
        }
        return props;
    }

    @Override
    @Nonnull
    public Set<OWLIndividual> parseIndividualList() {
        Set<OWLIndividual> inds = new HashSet<>();
        String sep = COMMA.keyword();
        while (COMMA.matches(sep)) {
            inds.add(parseIndividual());
            sep = peekToken();
            if (COMMA.matches(sep)) {
                consumeToken();
            }
        }
        return inds;
    }

    @Override
    public List<OWLObjectPropertyExpression> parseObjectPropertyChain() {
        String delim = "o";
        List<OWLObjectPropertyExpression> properties = new ArrayList<>();
        while (delim.equals("o")) {
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
            properties.add(prop);
            delim = peekToken();
            if (delim.equals("o")) {
                consumeToken();
            }
        }
        return properties;
    }

    @Nonnull
    protected OWLIndividual parseIndividual() {
        String name = consumeToken();
        if (!isIndividualName(name) && !name.startsWith("_:")) {
            throw new ExceptionBuilder().withInd().build();
        }
        return getOWLIndividual(name);
    }

    @Nonnull
    protected OWLDataProperty parseDataProperty() {
        String name = consumeToken();
        if (!isDataPropertyName(name)) {
            throw new ExceptionBuilder().withData().build();
        }
        return getOWLDataProperty(name);
    }

    @Nonnull
    protected OWLAnnotationProperty parseAnnotationProperty() {
        String name = consumeToken();
        if (!isAnnotationPropertyName(name)) {
            throw new ExceptionBuilder().withAnn().build();
        }
        return getOWLAnnotationProperty(name);
    }

    private Map<String, IRI> parsePrefixDeclaration() {
        consumeToken(PREFIX);
        Map<String, IRI> map = new HashMap<>(2);
        String prefixName = consumeToken();
        // Handle legacy = character if necessart
        if (peekToken().equals("=")) {
            // Legacy
            consumeToken();
        }
        IRI iri = parseIRI();
        map.put(prefixName, iri);
        return map;
    }

    private OWLImportsDeclaration parseImportsDeclaration() {
        consumeToken(IMPORT);
        if (peekToken().startsWith("<")) {
            return dataFactory.getOWLImportsDeclaration(parseIRI());
        }
        IRI iri = getIRI(peekToken());
        if (iri != null) {
            consumeToken();
        } else {
            throw new ExceptionBuilder().withKeyword("<$IRI$>").build();
        }
        return dataFactory.getOWLImportsDeclaration(iri);
    }

    @Nonnull
    protected IRI parseIRI() {
        String iriString = consumeToken();
        if (!(iriString.startsWith("<") && iriString.endsWith(">"))) {
            throw new ExceptionBuilder().withKeyword("<$IRI$>").build();
        }
        return IRI.create(iriString.substring(1, iriString.length() - 1));
    }

    private int skipAnnotationsProcessDeclaredEntities(int start) {
        if (start < tokens.size()) {
            String nameToken = tokens.get(start).getToken();
            if (ANNOTATIONS.matches(nameToken)) {
                do {
                    start = skipAnnotationsProcessDeclaredEntities(start + 1) + 2;
                    nameToken = tokens.get(start).getToken();
                } while (start < tokens.size()
                        && (ANNOTATIONS.matches(nameToken) || COMMA.matches(nameToken)));
            }
        }

        return start;
    }

    private void processDeclaredEntities() {
        int j = -1;
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i).getToken();
            String name = null;
            j = skipAnnotationsProcessDeclaredEntities(i + 1);
            if (j < tokens.size()) {
                name = tokens.get(j).getToken();
            }
            if (CLASS.matches(token)) {
                if (name != null) {
                    if (j + 1 < tokens.size()) {
                        String nextToken = tokens.get(j + 1).getToken();
                        ManchesterOWLSyntax a = parse(nextToken);
                        if (eof(nextToken) || (a != null && !(a.isClassExpressionConnectiveKeyword()
                                || a.isClassExpressionQuantiferKeyword())) {
                            classNames.add(name);
                        }
                    } else {
                        classNames.add(name);
                    }
                }
            } else if (OBJECT_PROPERTY.matches(token)) {
                if (name != null) {
                    objectPropertyNames.add(name);
                }
            } else if (DATA_PROPERTY.matches(token)) {
                if (name != null) {
                    dataPropertyNames.add(name);
                }
            } else if (INDIVIDUAL.matches(token)) {
                if (name != null) {
                    individualNames.add(name);
                }
            } else if (DATATYPE.matches(token)) {
                if (name != null) {
                    dataTypeNames.add(name);
                }
            } else if (ANNOTATION_PROPERTY.matches(token)) {
                if (name != null) {
                    annotationPropertyNames.add(name);
                }
            } else if (VALUE_PARTITION.matches(token)) {
                if (name != null) {
                    objectPropertyNames.add(name);
                }
                if (i + 2 < tokens.size()) {
                    classNames.add(tokens.get(i + 2).getToken());
                }
            }
        }
    }

    private void processDeclaredEntities(OWLDeclarationAxiom ax) {
        ax.getEntity().accept(new OWLEntityVisitor() {

            private String shortForm(HasIRI i) {
                if (DublinCoreVocabulary.NAME_SPACE.equals(i.getIRI().getNamespace())) {
                    pm.setPrefix(DC, DublinCoreVocabulary.NAME_SPACE);
                }
                return pm.getShortForm(i.getIRI());
            }

            @Override
            public void visit(OWLAnnotationProperty property) {
                annotationPropertyNames.add(shortForm(property));
            }

            @Override
            public void visit(OWLDatatype datatype) {
                dataTypeNames.add(shortForm(datatype));
            }

            @Override
            public void visit(OWLNamedIndividual individual) {
                individualNames.add(shortForm(individual));
            }

            @Override
            public void visit(OWLDataProperty property) {
                dataPropertyNames.add(shortForm(property));
            }

            @Override
            public void visit(OWLObjectProperty property) {
                objectPropertyNames.add(shortForm(property));
            }

            @Override
            public void visit(OWLClass cls) {
                classNames.add(shortForm(cls));
            }
        });
    }

    @Override
    public ManchesterSyntaxDocumentFormat parseOntology(@Nonnull OWLOntology ont) {
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        OWLOntologyID ontologyID = new OWLOntologyID();
        Set<AddImport> imports = new HashSet<>();
        Set<AddOntologyAnnotation> ontologyAnnotations = new HashSet<>();
        defaultOntology = ont;
        processDeclaredEntities();
        while (true) {
            String section = peekToken();
            if (ONTOLOGY.matches(section)) {
                ManchesterOWLSyntaxOntologyHeader header = parseOntologyHeader(false);
                for (OWLImportsDeclaration decl : header.getImportsDeclarations()) {
                    assert decl != null;
                    imports.add(new AddImport(ont, decl));
                    ont.getOWLOntologyManager().makeLoadImportRequest(decl,
                        getOntologyLoaderConfiguration());
                    OWLOntology imported = ont.getOWLOntologyManager().getImportedOntology(decl);
                    assert imported != null;
                    for (OWLDeclarationAxiom declaration : imported
                        .getAxioms(AxiomType.DECLARATION)) {
                        processDeclaredEntities(declaration);
                    }
                }
                for (OWLAnnotation anno : header.getAnnotations()) {
                    assert anno != null;
                    ontologyAnnotations.add(new AddOntologyAnnotation(ont, anno));
                }
                ontologyID = header.getOntologyID();
            } else if (DISJOINT_CLASSES.matches(section)) {
                axioms.addAll(parseDisjointClasses());
            } else if (EQUIVALENT_CLASSES.matches(section)) {
                axioms.addAll(parseNaryEquivalentClasses());
            } else if (EQUIVALENT_PROPERTIES.matches(section)) {
                axioms.addAll(parseNaryEquivalentProperties());
            } else if (DISJOINT_PROPERTIES.matches(section)) {
                axioms.addAll(parseDisjointProperties());
            } else if (DIFFERENT_INDIVIDUALS.matches(section)) {
                axioms.addAll(parseDifferentIndividuals());
            } else if (SAME_INDIVIDUAL.matches(section)) {
                axioms.addAll(parseSameIndividual());
            } else if (CLASS.matches(section)) {
                axioms.addAll(parseClassFrame());
            } else if (OBJECT_PROPERTY.matches(section)) {
                axioms.addAll(parseObjectPropertyFrame());
            } else if (DATA_PROPERTY.matches(section)) {
                axioms.addAll(parseDataPropertyFrame());
            } else if (INDIVIDUAL.matches(section)) {
                axioms.addAll(parseIndividualFrame());
            } else if (DATATYPE.matches(section)) {
                axioms.addAll(parseDatatypeFrame());
            } else if (ANNOTATION_PROPERTY.matches(section)) {
                axioms.addAll(parseAnnotationPropertyFrame());
            } else if (VALUE_PARTITION.matches(section)) {
                axioms.addAll(parseValuePartitionFrame());
            } else if (IMPORT.matches(section)) {
                OWLImportsDeclaration decl = parseImportsDeclaration();
                ont.getOWLOntologyManager().makeLoadImportRequest(decl,
                    getOntologyLoaderConfiguration());
                imports.add(new AddImport(ont, decl));
                OWLOntology imported = ont.getOWLOntologyManager().getImportedOntology(decl);
                assert imported != null;
                for (OWLDeclarationAxiom declaration : imported.getAxioms(AxiomType.DECLARATION)) {
                    processDeclaredEntities(declaration);
                }
            } else if (PREFIX.matches(section)) {
                Map<String, IRI> nsMap = parsePrefixDeclaration();
                for (String ns : nsMap.keySet()) {
                    assert ns != null;
                    pm.setPrefix(ns, nsMap.get(ns).toString());
                }
            } else if (RULE.matches(section)) {
                axioms.addAll(parseRuleFrame());
            } else if (eof(section)) {
                break;
            } else {
                consumeToken();
                throw new ExceptionBuilder().withKeyword(CLASS, OBJECT_PROPERTY, DATA_PROPERTY,
                    INDIVIDUAL, DATATYPE, ANNOTATION_PROPERTY, IMPORT, VALUE_PARTITION, PREFIX,
                    EQUIVALENT_CLASSES, DISJOINT_CLASSES, DISJOINT_PROPERTIES,
                    DIFFERENT_INDIVIDUALS, SAME_INDIVIDUAL).build();
            }
        }
        List<OWLOntologyChange> changes = new ArrayList<>(axioms.size());
        changes.addAll(imports);
        changes.addAll(ontologyAnnotations);
        for (OntologyAxiomPair pair : axioms) {
            changes.add(new AddAxiom(ont, pair.getAxiom()));
        }
        changes.add(new SetOntologyID(ont, ontologyID));
        ont.getOWLOntologyManager().applyChanges(changes);
        ManchesterSyntaxDocumentFormat format = new ManchesterSyntaxDocumentFormat();
        format.copyPrefixesFrom(pm);
        return format;
    }

    private ManchesterOWLSyntaxOntologyHeader parseOntologyHeader(boolean toEOF) {
        String tok = consumeToken();
        if (!ONTOLOGY.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(ONTOLOGY).build();
        }
        IRI ontologyIRI = null;
        IRI versionIRI = null;
        // Next token can be either a full IRI or an abbreviated/simple IRI, for ontology IRI.
        // The token after that has the same constraints, for version IRI.
        // Both can be missing, in which case the ontology is anonymous.
        // If the next token is a keyword, IRI parsing should not be attempted.
        if (peekToken().startsWith("<")) {
            ontologyIRI = parseIRI();

        } else if (ManchesterOWLSyntax.parse(peekToken()) == null) {
            ontologyIRI = getIRI(peekToken());
            if (ontologyIRI != null) {
                consumeToken();
            }
        }
        if (peekToken().startsWith("<")) {
            versionIRI = parseIRI();
        } else if (ManchesterOWLSyntax.parse(peekToken()) == null) {
            versionIRI = getIRI(peekToken());
            if (versionIRI != null) {
                consumeToken();
            }
        }
        Set<OWLAnnotation> annotations = new HashSet<>();
        Set<OWLImportsDeclaration> imports = new HashSet<>();
        while (true) {
            String section = peekToken();
            if (IMPORT.matches(section)) {
                handleImport(imports);
            } else if (ANNOTATIONS.matches(section)) {
                consumeToken();
                annotations.addAll(parseAnnotationList());
            } else if (eof(section)) {
                break;
            } else if (toEOF) {
                throw new ExceptionBuilder().withKeyword(IMPORT, ANNOTATIONS).build();
            } else {
                break;
            }
        }
        return new ManchesterOWLSyntaxOntologyHeader(ontologyIRI, versionIRI, annotations, imports);
    }

    protected void handleImport(Set<OWLImportsDeclaration> imports) {
        String tok;
        consumeToken();
        tok = peekToken();
        IRI importedOntologyIRI = null;
        if (tok.startsWith("<")) {
            importedOntologyIRI = parseIRI();
        } else if (isOntologyName(tok)) {
            consumeToken();
            OWLOntology ont = getOntology(tok);
            if (ont != null) {
                importedOntologyIRI = ont.getOntologyID().getOntologyIRI().orNull();
            }
        } else {
            consumeToken();
            throw new ExceptionBuilder().withOnto().withKeyword("<$ONTOLOGYYURI$>").build();
        }
        if (importedOntologyIRI == null) {
            throw new ExceptionBuilder().withOnto().withKeyword("Imported IRI is null").build();
        }
        assert importedOntologyIRI != null;
        imports.add(dataFactory.getOWLImportsDeclaration(importedOntologyIRI));
    }

    protected class ExceptionBuilder {
        String message;
        boolean ontologyNameExpected = false;
        boolean classNameExpected = false;
        boolean objectPropertyNameExpected = false;
        boolean dataPropertyNameExpected = false;
        boolean individualNameExpected = false;
        boolean datatypeNameExpected = false;
        boolean annotationPropertyNameExpected = false;
        boolean integerExpected = false;
        Set<String> keywords = new HashSet<>();
        List<String> tokenSequence;
        int start = -1;
        int line = -1;
        int column = -1;

        ExceptionBuilder() {
            withKeyword(potentialKeywords);
        }

        ExceptionBuilder(ParserException e) {
            ontologyNameExpected = e.isOntologyNameExpected();
            classNameExpected = e.isClassNameExpected();
            objectPropertyNameExpected = e.isObjectPropertyNameExpected();
            dataPropertyNameExpected = e.isDataPropertyNameExpected();
            individualNameExpected = e.isIndividualNameExpected();
            dataPropertyNameExpected = e.isDatatypeNameExpected();
            annotationPropertyNameExpected = e.isAnnotationPropertyNameExpected();
            integerExpected = e.isIntegerExpected();
            withKeyword(e.getExpectedKeywords());
            tokenSequence = e.getTokenSequence();
            start = e.getStartPos();
            line = e.getLineNumber();
            column = e.getColumnNumber();
            message = e.getMessage();
        }

        public ExceptionBuilder withOnto() {
            ontologyNameExpected = true;
            return this;
        }

        public ExceptionBuilder withMessage(String m) {
            message = m;
            return this;
        }

        public ExceptionBuilder withInt() {
            integerExpected = true;
            return this;
        }

        public ExceptionBuilder withClass() {
            classNameExpected = true;
            return this;
        }

        public ExceptionBuilder withObject() {
            objectPropertyNameExpected = true;
            withKeyword(INVERSE);
            return this;
        }

        public ExceptionBuilder withData() {
            dataPropertyNameExpected = true;
            return this;
        }

        public ExceptionBuilder withInd() {
            individualNameExpected = true;
            return this;
        }

        public ExceptionBuilder withDt() {
            datatypeNameExpected = true;
            return this;
        }

        public ExceptionBuilder withAnn() {
            annotationPropertyNameExpected = true;
            return this;
        }

        public ExceptionBuilder withKeyword(String s) {
            keywords.add(s);
            return this;
        }

        public ExceptionBuilder withKeyword(ManchesterOWLSyntax s) {
            keywords.add(s.keyword());
            return this;
        }

        public ExceptionBuilder withKeyword(String... strings) {
            for (String s : strings) {
                keywords.add(s);
            }
            return this;
        }

        public ExceptionBuilder withKeyword(ManchesterOWLSyntax... keys) {
            for (ManchesterOWLSyntax s : keys) {
                keywords.add(s.keyword());
            }
            return this;
        }

        public <T> ExceptionBuilder withKeyword(Collection<T> keys) {
            for (T s : keys) {
                if (s instanceof String) {
                    withKeyword((String) s);
                }
                if (s instanceof ManchesterOWLSyntax) {
                    withKeyword((ManchesterOWLSyntax) s);
                }
            }
            return this;
        }

        public ParserException build() {
            if (tokenSequence == null) {
                Token lastToken = getLastToken();
                tokenSequence = getTokenSequence();
                start = lastToken.getPos();
                line = lastToken.getRow();
                column = lastToken.getCol();
            }
            return new ParserException(message, tokenSequence, start, line, column,
                ontologyNameExpected, classNameExpected, objectPropertyNameExpected,
                dataPropertyNameExpected, individualNameExpected, datatypeNameExpected,
                annotationPropertyNameExpected, integerExpected, keywords);
        }
    }

    protected List<String> getTokenSequence() {
        List<String> seq = new ArrayList<>();
        int index = tokenIndex - 1;
        if (index < 0) {
            index = 0;
        }
        while (index < tokens.size() && seq.size() < 4 && !seq.contains(EOF)) {
            seq.add(tokens.get(index).getToken());
            index++;
        }
        if (seq.isEmpty()) {
            seq.add(EOF);
        }
        return seq;
    }

    class DefaultEntityChecker implements OWLEntityChecker {

        @Override
        public OWLClass getOWLClass(String name) {
            if (name.equals("Thing") || name.equals("owl:Thing")) {
                return dataFactory.getOWLThing();
            } else if (name.equals("Nothing") || name.equals("owl:Nothing")) {
                return dataFactory.getOWLNothing();
            } else if (classNames.contains(name)) {
                return dataFactory.getOWLClass(getIRI(name));
            }
            return null;
        }

        @Override
        public OWLObjectProperty getOWLObjectProperty(String name) {
            if (objectPropertyNames.contains(name)) {
                return dataFactory.getOWLObjectProperty(getIRI(name));
            }
            return null;
        }

        @Override
        public OWLDataProperty getOWLDataProperty(String name) {
            if (dataPropertyNames.contains(name)) {
                return dataFactory.getOWLDataProperty(getIRI(name));
            }
            return null;
        }

        @Override
        public OWLNamedIndividual getOWLIndividual(String name) {
            if (individualNames.contains(name)) {
                return dataFactory.getOWLNamedIndividual(getIRI(name));
            }
            return null;
        }

        @Override
        public OWLDatatype getOWLDatatype(String name) {
            if (dataTypeNames.contains(name)) {
                return dataFactory.getOWLDatatype(getIRI(name));
            }
            return null;
        }

        @Override
        public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
            if (annotationPropertyNames.contains(name)) {
                return dataFactory.getOWLAnnotationProperty(getIRI(name));
            }
            return null;
        }
    }

    private final Map<String, IRI> nameIRIMap = new HashMap<>();

    @Nonnull
    protected IRI getIRI(@Nonnull String inputName) {
        String name = inputName;
        boolean fullIRI = name.equals("<");
        if (fullIRI) {
            name = consumeToken();
            consumeToken();
        }
        IRI uri = nameIRIMap.get(name);
        if (uri != null) {
            return uri;
        }
        if (fullIRI) {
            uri = IRI.create(name);
        } else {
            int colonIndex = name.indexOf(':');
            if (colonIndex == -1) {
                name = ":" + name;
            }
            if (name.startsWith(DC) && !pm.containsPrefixMapping(DC)) {
                pm.setPrefix(DC, DublinCoreVocabulary.NAME_SPACE);
            }
            uri = pm.getIRI(name);
        }
        nameIRIMap.put(name, uri);
        return uri;
    }

    @Override
    public OWLAxiom parseAxiom() {
        String token = peekToken();
        if (isClassName(token)) {
            return parseAxiomWithClassExpressionStart();
        } else if (isObjectPropertyName(token)) {
            return parseAxiomWithObjectPropertyStart();
        } else if (isDataPropertyName(token)) {
            return parseAxiomWithDataPropertyStart();
        } else if (isIndividualName(token)) {
            return parseAxiomWithIndividualStart();
        } else if (INV.matches(token)) {
            return parseAxiomWithObjectPropertyStart();
        } else if (OPEN.matches(token)) {
            return parseAxiomWithClassExpressionStart();
        } else if (OPENBRACE.matches(token)) {
            return parseAxiomWithClassExpressionStart();
        } else if (FUNCTIONAL.matches(token)) {
            return parseFunctionPropertyAxiom();
        } else if (INVERSE_FUNCTIONAL.matches(token)) {
            return parseInverseFunctionalPropertyAxiom();
        } else if (SYMMETRIC.matches(token)) {
            return parseSymmetricPropertyAxiom();
        } else if (ASYMMETRIC.matches(token)) {
            return parseAsymmetricPropertyAxiom();
        } else if (TRANSITIVE.matches(token)) {
            return parseTransitivePropertyAxiom();
        } else if (REFLEXIVE.matches(token)) {
            return parseReflexivePropertyAxiom();
        } else if (IRREFLEXIVE.matches(token)) {
            return parseIrreflexivePropertyAxiom();
        }
        throw new ExceptionBuilder()
            .withClass().withObject().withData().withKeyword(OPEN, OPENBRACE, INV, FUNCTIONAL,
                INVERSE_FUNCTIONAL, SYMMETRIC, ASYMMETRIC, TRANSITIVE, REFLEXIVE, IRREFLEXIVE)
            .build();
    }

    @Override
    public OWLClassAxiom parseClassAxiom() {
        return (OWLClassAxiom) parseAxiom();
    }

    @Nonnull
    private OWLAxiom parseAxiomWithIndividualStart() {
        OWLIndividual ind = parseIndividual();
        String kw = consumeToken();
        if (TYPE.matches(kw)) {
            OWLClassExpression type = parseClassExpression();
            return dataFactory.getOWLClassAssertionAxiom(type, ind);
        }
        throw new ExceptionBuilder().withKeyword(TYPE).build();
    }

    @Nonnull
    private OWLAxiom parseAxiomWithDataPropertyStart() {
        OWLDataPropertyExpression prop = parseDataProperty();
        String kw = consumeToken();
        if (SOME.matches(kw)) {
            OWLDataRange dataRange = parseDataIntersectionOf(false);
            return parseClassAxiomRemainder(dataFactory.getOWLDataSomeValuesFrom(prop, dataRange));
        } else if (ONLY.matches(kw)) {
            OWLDataRange dataRange = parseDataIntersectionOf(false);
            return parseClassAxiomRemainder(dataFactory.getOWLDataAllValuesFrom(prop, dataRange));
        } else if (MIN.matches(kw)) {
            int cardi = parseInteger();
            OWLDataRange dataRange = parseDataIntersectionOf(true);
            return parseClassAxiomRemainder(
                dataFactory.getOWLDataMinCardinality(cardi, prop, dataRange));
        } else if (MAX.matches(kw)) {
            int cardi = parseInteger();
            OWLDataRange dataRange = parseDataIntersectionOf(true);
            return parseClassAxiomRemainder(
                dataFactory.getOWLDataMaxCardinality(cardi, prop, dataRange));
        } else if (EXACTLY.matches(kw)) {
            int cardi = parseInteger();
            OWLDataRange dataRange = parseDataIntersectionOf(true);
            return parseClassAxiomRemainder(
                dataFactory.getOWLDataExactCardinality(cardi, prop, dataRange));
        } else if (SUB_PROPERTY_OF.matches(kw)) {
            OWLDataPropertyExpression superProperty = parseDataPropertyExpression();
            return dataFactory.getOWLSubDataPropertyOfAxiom(prop, superProperty);
        } else if (EQUIVALENT_TO.matches(kw)) {
            OWLDataPropertyExpression equivProp = parseDataPropertyExpression();
            return dataFactory.getOWLEquivalentDataPropertiesAxiom(prop, equivProp);
        } else if (DISJOINT_WITH.matches(kw)) {
            OWLDataPropertyExpression disjProp = parseDataPropertyExpression();
            return dataFactory.getOWLDisjointDataPropertiesAxiom(prop, disjProp);
        } else if (DOMAIN.matches(kw)) {
            OWLClassExpression domain = parseClassExpression();
            return dataFactory.getOWLDataPropertyDomainAxiom(prop, domain);
        } else if (RANGE.matches(kw)) {
            OWLDataRange range = parseDataIntersectionOf(true);
            return dataFactory.getOWLDataPropertyRangeAxiom(prop, range);
        } else {
            throw new ExceptionBuilder().withKeyword(SOME, ONLY, MIN, MAX, EXACTLY, SUB_PROPERTY_OF,
                EQUIVALENT_TO, DISJOINT_WITH, DOMAIN, RANGE).build();
        }
    }

    @Nonnull
    private OWLDataPropertyExpression parseDataPropertyExpression() {
        String tok = consumeToken();
        if (!isDataPropertyName(tok)) {
            throw new ExceptionBuilder().withData().build();
        }
        return getOWLDataProperty(tok);
    }

    @Nonnull
    private OWLAxiom parseAxiomWithClassExpressionStart() {
        return parseClassAxiomRemainder(parseUnion());
    }

    @Nonnull
    private OWLAxiom parseClassAxiomRemainder(@Nonnull OWLClassExpression startExpression) {
        String kw = consumeToken();
        if (SUBCLASS_OF.matchesEitherForm(kw)) {
            OWLClassExpression superClass = parseClassExpression();
            return dataFactory.getOWLSubClassOfAxiom(startExpression, superClass);
        } else if (DISJOINT_WITH.matchesEitherForm(kw)) {
            OWLClassExpression disjointClass = parseClassExpression();
            return dataFactory.getOWLDisjointClassesAxiom(startExpression, disjointClass);
        } else if (EQUIVALENT_TO.matchesEitherForm(kw)) {
            OWLClassExpression equivClass = parseClassExpression();
            return dataFactory.getOWLEquivalentClassesAxiom(startExpression, equivClass);
        } else if (AND.matchesEitherForm(kw)) {
            OWLClassExpression conjunct = parseIntersection();
            Set<OWLClassExpression> conjuncts = conjunct.asConjunctSet();
            conjuncts.add(startExpression);
            OWLClassExpression ce = dataFactory.getOWLObjectIntersectionOf(conjuncts);
            return parseClassAxiomRemainder(ce);
        } else if (OR.matchesEitherForm(kw)) {
            OWLClassExpression disjunct = parseUnion();
            Set<OWLClassExpression> disjuncts = disjunct.asDisjunctSet();
            disjuncts.add(startExpression);
            OWLClassExpression ce = dataFactory.getOWLObjectUnionOf(disjuncts);
            return parseClassAxiomRemainder(ce);
        } else {
            throw new ExceptionBuilder()
                .withKeyword(SUBCLASS_OF, DISJOINT_WITH, EQUIVALENT_TO, AND, OR).build();
        }
    }

    @Nonnull
    private OWLAxiom parseAxiomWithObjectPropertyStart() {
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        String kw = consumeToken();
        if (SOME.matches(kw)) {
            OWLClassExpression filler = parseUnion();
            return parseClassAxiomRemainder(dataFactory.getOWLObjectSomeValuesFrom(prop, filler));
        } else if (ONLY.matches(kw)) {
            OWLClassExpression filler = parseUnion();
            return parseClassAxiomRemainder(dataFactory.getOWLObjectAllValuesFrom(prop, filler));
        } else if (MIN.matches(kw)) {
            int cardi = parseInteger();
            OWLClassExpression filler = parseUnion();
            return parseClassAxiomRemainder(
                dataFactory.getOWLObjectMinCardinality(cardi, prop, filler));
        } else if (MAX.matches(kw)) {
            int cardi = parseInteger();
            OWLClassExpression filler = parseUnion();
            return parseClassAxiomRemainder(
                dataFactory.getOWLObjectMaxCardinality(cardi, prop, filler));
        } else if (EXACTLY.matches(kw)) {
            int cardi = parseInteger();
            OWLClassExpression filler = parseUnion();
            return parseClassAxiomRemainder(
                dataFactory.getOWLObjectExactCardinality(cardi, prop, filler));
        } else if (SUB_PROPERTY_OF.matches(kw)) {
            OWLObjectPropertyExpression superProperty = parseObjectPropertyExpression(false);
            return dataFactory.getOWLSubObjectPropertyOfAxiom(prop, superProperty);
        } else if (EQUIVALENT_TO.matches(kw)) {
            OWLObjectPropertyExpression equivProp = parseObjectPropertyExpression(false);
            return dataFactory.getOWLEquivalentObjectPropertiesAxiom(prop, equivProp);
        } else if (INVERSE_OF.matches(kw)) {
            OWLObjectPropertyExpression invProp = parseObjectPropertyExpression(false);
            return dataFactory.getOWLInverseObjectPropertiesAxiom(prop, invProp);
        } else if (DISJOINT_WITH.matches(kw)) {
            OWLObjectPropertyExpression disjProp = parseObjectPropertyExpression(false);
            return dataFactory.getOWLDisjointObjectPropertiesAxiom(prop, disjProp);
        } else if (DOMAIN.matches(kw)) {
            OWLClassExpression domain = parseClassExpression();
            return dataFactory.getOWLObjectPropertyDomainAxiom(prop, domain);
        } else if (RANGE.matches(kw)) {
            OWLClassExpression range = parseClassExpression();
            return dataFactory.getOWLObjectPropertyRangeAxiom(prop, range);
        } else if (CHAIN_CONNECT.matches(kw)) {
            String sep = kw;
            List<OWLObjectPropertyExpression> chain = new ArrayList<>();
            chain.add(prop);
            while (sep.equals("o")) {
                OWLObjectPropertyExpression chainProp = parseObjectPropertyExpression(false);
                chain.add(chainProp);
                sep = consumeToken();
            }
            if (!SUB_PROPERTY_OF.matches(sep)) {
                throw new ExceptionBuilder().withKeyword(SUB_PROPERTY_OF).build();
            }
            OWLObjectPropertyExpression superProp = parseObjectPropertyExpression(false);
            return dataFactory.getOWLSubPropertyChainOfAxiom(chain, superProp);
        } else {
            throw new ExceptionBuilder().withKeyword(SOME, ONLY, MIN, MAX, EXACTLY, SUB_PROPERTY_OF,
                EQUIVALENT_TO, INVERSE_OF, DISJOINT_WITH, DOMAIN, RANGE, CHAIN_CONNECT).build();
        }
    }

    @Nonnull
    private OWLAxiom parseInverseFunctionalPropertyAxiom() {
        String kw = consumeToken();
        if (!INVERSE_FUNCTIONAL.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(INVERSE_FUNCTIONAL).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        return dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(prop);
    }

    @Nonnull
    private OWLAxiom parseSymmetricPropertyAxiom() {
        String kw = consumeToken();
        if (!SYMMETRIC.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(SYMMETRIC).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        return dataFactory.getOWLSymmetricObjectPropertyAxiom(prop);
    }

    @Nonnull
    private OWLAxiom parseAsymmetricPropertyAxiom() {
        String kw = consumeToken();
        if (!ASYMMETRIC.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(ASYMMETRIC).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        return dataFactory.getOWLAsymmetricObjectPropertyAxiom(prop);
    }

    @Nonnull
    private OWLAxiom parseTransitivePropertyAxiom() {
        String kw = consumeToken();
        if (!TRANSITIVE.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(TRANSITIVE).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        return dataFactory.getOWLTransitiveObjectPropertyAxiom(prop);
    }

    @Nonnull
    private OWLAxiom parseReflexivePropertyAxiom() {
        String kw = consumeToken();
        if (!REFLEXIVE.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(REFLEXIVE).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        return dataFactory.getOWLReflexiveObjectPropertyAxiom(prop);
    }

    @Nonnull
    private OWLAxiom parseIrreflexivePropertyAxiom() {
        String kw = consumeToken();
        if (!IRREFLEXIVE.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(IRREFLEXIVE).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        return dataFactory.getOWLIrreflexiveObjectPropertyAxiom(prop);
    }

    @Nonnull
    private OWLAxiom parseFunctionPropertyAxiom() {
        String kw = consumeToken();
        if (!FUNCTIONAL.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(FUNCTIONAL).build();
        }
        String name = peekToken();
        if (isObjectPropertyName(name)) {
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
            return dataFactory.getOWLFunctionalObjectPropertyAxiom(prop);
        } else if (isDataPropertyName(name)) {
            OWLDataProperty prop = parseDataProperty();
            return dataFactory.getOWLFunctionalDataPropertyAxiom(prop);
        } else {
            consumeToken();
            throw new ExceptionBuilder().withObject().withData().build();
        }
    }

    @Nonnull
    private <F, O> Set<OntologyAxiomPair> parseAnnotatedListItems(@Nonnull F s,
        @Nonnull AnnotatedListItemParser<F, O> itemParser, @Nonnull Set<OWLOntology> ontologies) {
        Set<OntologyAxiomPair> result = new HashSet<>();
        String sep = COMMA.keyword();
        while (COMMA.matches(sep)) {
            Set<OWLAnnotation> annotations = parseAnnotations();
            O item = itemParser.parseItem(s);
            OWLAxiom axiom = itemParser.createAxiom(s, item, annotations);
            for (OWLOntology ontology : ontologies) {
                result.add(new OntologyAxiomPair(ontology, axiom));
            }
            sep = peekToken();
            if (COMMA.matches(sep)) {
                consumeToken();
            }
        }
        return result;
    }

    interface AnnotatedListItemParser<F, O> {

        @Nonnull
        O parseItem(@Nonnull F s);

        @Nonnull
        OWLAxiom createAxiom(@Nonnull F s, @Nonnull O o, @Nonnull Set<OWLAnnotation> anns);

        ManchesterOWLSyntax getFrameSectionKeyword();
    }

    abstract class AnnotatedClassExpressionListItemParser<F>
        implements AnnotatedListItemParser<F, OWLClassExpression> {

        @Override
        public OWLClassExpression parseItem(F s) {
            return parseUnion();
        }
    }

    abstract class AnnotatedClassExpressionSetListItemParser<F>
        implements AnnotatedListItemParser<F, Set<OWLClassExpression>> {

        @Override
        public Set<OWLClassExpression> parseItem(F s) {
            return parseClassExpressionList();
        }
    }

    abstract class AnnotatedPropertyListListItemParser<F>
        implements AnnotatedListItemParser<F, Set<OWLPropertyExpression>> {

        @Override
        public Set<OWLPropertyExpression> parseItem(F s) {
            return parsePropertyList();
        }
    }

    abstract class AnnotatedIndividualsListItemParser<F>
        implements AnnotatedListItemParser<F, OWLIndividual> {

        @Override
        public OWLIndividual parseItem(F s) {
            return parseIndividual();
        }
    }

    abstract class AnnotationListItemParser<F>
        implements AnnotatedListItemParser<F, OWLAnnotation> {

        @Override
        public OWLAnnotation parseItem(F s) {
            return parseAnnotation();
        }
    }

    class ClassSubClassOfListItemParser extends AnnotatedClassExpressionListItemParser<OWLClassExpression> {

        @Override
        public OWLAxiom createAxiom(OWLClassExpression s, OWLClassExpression o, Set<OWLAnnotation> anns) {
            return dataFactory.getOWLSubClassOfAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUBCLASS_OF;
        }
    }

    class ClassEquivalentToListItemParser extends AnnotatedClassExpressionListItemParser<OWLClassExpression> {

        @Override
        public OWLAxiom createAxiom(OWLClassExpression s, OWLClassExpression o, Set<OWLAnnotation> anns) {
            return dataFactory.getOWLEquivalentClassesAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return EQUIVALENT_TO;
        }
    }

    class ClassDisjointWithListItemParser extends AnnotatedClassExpressionListItemParser<OWLClassExpression> {

        @Override
        public OWLAxiom createAxiom(OWLClassExpression s, OWLClassExpression o, Set<OWLAnnotation> anns) {
            Set<OWLClassExpression> disjointClasses = new HashSet<>();
            disjointClasses.add(s);
            disjointClasses.add(o);
            return dataFactory.getOWLDisjointClassesAxiom(disjointClasses, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DISJOINT_WITH;
        }
    }

    class ClassDisjointClassesListItemParser
        extends AnnotatedClassExpressionSetListItemParser<OWLClassExpression> {

        @Override
        public OWLAxiom createAxiom(OWLClassExpression s, Set<OWLClassExpression> o,
            Set<OWLAnnotation> anns) {
            // o.add(s);
            return dataFactory.getOWLDisjointClassesAxiom(o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DISJOINT_CLASSES;
        }
    }

    class ClassDisjointUnionOfListItemParser
        extends AnnotatedClassExpressionSetListItemParser<OWLClass> {

        @Override
        public OWLAxiom createAxiom(OWLClass s, Set<OWLClassExpression> o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLDisjointUnionAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DISJOINT_UNION_OF;
        }
    }

    class ClassHasKeyListItemParser extends AnnotatedPropertyListListItemParser<OWLClassExpression> {

        @Override
        public OWLAxiom createAxiom(OWLClassExpression s, Set<OWLPropertyExpression> o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLHasKeyAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return HAS_KEY;
        }
    }

    class ClassSuperClassOfListItemParser extends AnnotatedClassExpressionListItemParser<OWLClassExpression> {

        @Override
        public OWLAxiom createAxiom(OWLClassExpression s, OWLClassExpression o, Set<OWLAnnotation> anns) {
            return dataFactory.getOWLSubClassOfAxiom(o, s, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUPERCLASS_OF;
        }
    }

    class ClassIndividualsListItemParser extends AnnotatedIndividualsListItemParser<OWLClassExpression> {

        @Override
        public OWLAxiom createAxiom(OWLClassExpression s, OWLIndividual o, Set<OWLAnnotation> anns) {
            return dataFactory.getOWLClassAssertionAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return INDIVIDUALS;
        }
    }

    class EntityAnnotationsListItemParser<E extends OWLEntity> extends AnnotationListItemParser<E> {

        @Override
        public OWLAxiom createAxiom(E s, OWLAnnotation o, Set<OWLAnnotation> anns) {
            return dataFactory.getOWLAnnotationAssertionAxiom(s.getIRI(), o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return ANNOTATIONS;
        }
    }

    abstract class ObjectPropertyExpressionListItemParser<F>
        implements AnnotatedListItemParser<F, OWLObjectPropertyExpression> {

        @Override
        public OWLObjectPropertyExpression parseItem(F s) {
            return parseObjectPropertyExpression(false);
        }
    }

    class ObjectPropertySubPropertyOfListItemParser
        extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLObjectPropertyExpression o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLSubObjectPropertyOfAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUB_PROPERTY_OF;
        }
    }

    class ObjectPropertySuperPropertyOfListItemParser
        extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLObjectPropertyExpression o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLSubObjectPropertyOfAxiom(o, s, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUPER_PROPERTY_OF;
        }
    }

    class ObjectPropertyEquivalentToListItemParser
        extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLObjectPropertyExpression o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLEquivalentObjectPropertiesAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return EQUIVALENT_TO;
        }
    }

    class ObjectPropertyDisjointWithListItemParser
        extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLObjectPropertyExpression o,
            Set<OWLAnnotation> anns) {
            Set<OWLObjectPropertyExpression> properties = new HashSet<>();
            properties.add(s);
            properties.add(o);
            return dataFactory.getOWLDisjointObjectPropertiesAxiom(properties, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DISJOINT_WITH;
        }
    }

    class ObjectPropertyDomainListItemParser
        extends AnnotatedClassExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLClassExpression o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLObjectPropertyDomainAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DOMAIN;
        }
    }

    class ObjectPropertyRangeListItemParser
        extends AnnotatedClassExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLClassExpression o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLObjectPropertyRangeAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return RANGE;
        }
    }

    class ObjectPropertyInverseOfListItemParser
        extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLObjectPropertyExpression o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLInverseObjectPropertiesAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return INVERSE_OF;
        }
    }

    class ObjectPropertySubPropertyChainListItemParser
        implements AnnotatedListItemParser<OWLObjectProperty, List<OWLObjectPropertyExpression>> {

        @Override
        public List<OWLObjectPropertyExpression> parseItem(OWLObjectProperty s) {
            return parseObjectPropertyChain();
        }

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, List<OWLObjectPropertyExpression> o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLSubPropertyChainOfAxiom(o, s, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUB_PROPERTY_CHAIN;
        }
    }

    class ObjectPropertyCharacteristicsItemParser implements
        AnnotatedListItemParser<OWLObjectProperty, OWLObjectPropertyCharacteristicAxiom> {

        @Override
        public OWLObjectPropertyCharacteristicAxiom parseItem(@Nonnull OWLObjectProperty s) {
            return parseObjectPropertyCharacteristic(s);
        }

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLObjectPropertyCharacteristicAxiom o,
            Set<OWLAnnotation> anns) {
            return o.getAnnotatedAxiom(anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return CHARACTERISTICS;
        }
    }

    abstract class DataPropertyExpressionListItemParser<F>
        implements AnnotatedListItemParser<F, OWLDataPropertyExpression> {

        @Override
        public OWLDataProperty parseItem(F s) {
            return parseDataProperty();
        }
    }

    class DataPropertySubPropertyOfListItemParser
        extends DataPropertyExpressionListItemParser<OWLDataProperty> {

        @Override
        public OWLAxiom createAxiom(OWLDataProperty s, OWLDataPropertyExpression o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLSubDataPropertyOfAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUB_PROPERTY_OF;
        }
    }

    class DataPropertyEquivalentToListItemParser
        extends DataPropertyExpressionListItemParser<OWLDataProperty> {

        @Override
        public OWLAxiom createAxiom(OWLDataProperty s, OWLDataPropertyExpression o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLEquivalentDataPropertiesAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return EQUIVALENT_TO;
        }
    }

    class DataPropertyDisjointWithListItemParser
        extends DataPropertyExpressionListItemParser<OWLDataProperty> {

        @Override
        public OWLAxiom createAxiom(OWLDataProperty s, OWLDataPropertyExpression o,
            Set<OWLAnnotation> anns) {
            Set<OWLDataPropertyExpression> properties = new HashSet<>();
            properties.add(s);
            properties.add(o);
            return dataFactory.getOWLDisjointDataPropertiesAxiom(properties, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DISJOINT_WITH;
        }
    }

    class DataPropertyDomainListItemParser
        extends AnnotatedClassExpressionListItemParser<OWLDataProperty> {

        @Override
        public OWLAxiom createAxiom(OWLDataProperty s, OWLClassExpression o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLDataPropertyDomainAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DOMAIN;
        }
    }

    abstract class AnnotatedDataRangeListItemParser<F>
        implements AnnotatedListItemParser<F, OWLDataRange> {

        @Override
        public OWLDataRange parseItem(F s) {
            return parseDataIntersectionOf(false);
        }
    }

    class DataPropertyRangeListItemParser
        extends AnnotatedDataRangeListItemParser<OWLDataProperty> {

        @Override
        public OWLAxiom createAxiom(OWLDataProperty s, OWLDataRange o, Set<OWLAnnotation> anns) {
            return dataFactory.getOWLDataPropertyRangeAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return RANGE;
        }
    }

    class DataPropertyCharacteristicsItemParser
        implements AnnotatedListItemParser<OWLDataProperty, OWLDataPropertyCharacteristicAxiom> {

        @Override
        public OWLDataPropertyCharacteristicAxiom parseItem(OWLDataProperty s) {
            return parseDataPropertyCharacteristic(s);
        }

        @Override
        public OWLAxiom createAxiom(OWLDataProperty s, OWLDataPropertyCharacteristicAxiom o,
            Set<OWLAnnotation> anns) {
            return o.getAnnotatedAxiom(anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return CHARACTERISTICS;
        }
    }

    class IndividualTypesItemParser extends AnnotatedClassExpressionListItemParser<OWLIndividual> {

        @Override
        public OWLAxiom createAxiom(OWLIndividual s, OWLClassExpression o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLClassAssertionAxiom(o, s, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return TYPES;
        }
    }

    class IndividualFactsItemParser
        implements AnnotatedListItemParser<OWLIndividual, OWLPropertyAssertionAxiom<?, ?>> {

        @Override
        public OWLPropertyAssertionAxiom<?, ?> parseItem(OWLIndividual s) {
            return parseFact(s);
        }

        @Override
        public OWLAxiom createAxiom(@Nonnull OWLIndividual s, OWLPropertyAssertionAxiom<?, ?> o,
            Set<OWLAnnotation> anns) {
            return o.getAnnotatedAxiom(anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return FACTS;
        }
    }

    class IndividualSameAsItemParser extends AnnotatedIndividualsListItemParser<OWLIndividual> {

        @Override
        public OWLAxiom createAxiom(OWLIndividual s, OWLIndividual o,
            @Nonnull Set<OWLAnnotation> anns) {
            Set<OWLIndividual> individuals = new HashSet<>();
            individuals.add(s);
            individuals.add(o);
            return dataFactory.getOWLSameIndividualAxiom(individuals, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SAME_AS;
        }
    }

    class IndividualDifferentFromItemParser
        extends AnnotatedIndividualsListItemParser<OWLIndividual> {

        @Override
        public OWLAxiom createAxiom(OWLIndividual s, OWLIndividual o, Set<OWLAnnotation> anns) {
            Set<OWLIndividual> individuals = new HashSet<>();
            individuals.add(s);
            individuals.add(o);
            return dataFactory.getOWLDifferentIndividualsAxiom(individuals, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DIFFERENT_FROM;
        }
    }

    class IndividualAnnotationItemParser
        implements AnnotatedListItemParser<OWLIndividual, OWLAnnotation> {

        @Override
        public OWLAnnotation parseItem(OWLIndividual s) {
            return parseAnnotation();
        }

        @Override
        public OWLAxiom createAxiom(OWLIndividual s, OWLAnnotation o, Set<OWLAnnotation> anns) {
            if (s.isAnonymous()) {
                return dataFactory.getOWLAnnotationAssertionAxiom(s.asOWLAnonymousIndividual(), o,
                    anns);
            } else {
                return dataFactory.getOWLAnnotationAssertionAxiom(s.asOWLNamedIndividual().getIRI(),
                    o, anns);
            }
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return ANNOTATIONS;
        }
    }

    abstract class AnnotatedIRIListItemParser<F> implements AnnotatedListItemParser<F, IRI> {

        @Override
        public IRI parseItem(F s) {
            return parseIRI();
        }
    }

    class AnnotationPropertySubPropertyOfListItemParser
        implements AnnotatedListItemParser<OWLAnnotationProperty, OWLAnnotationProperty> {

        @Override
        public OWLAnnotationProperty parseItem(OWLAnnotationProperty s) {
            return parseAnnotationProperty();
        }

        @Override
        public OWLAxiom createAxiom(OWLAnnotationProperty s, OWLAnnotationProperty o,
            Set<OWLAnnotation> anns) {
            return dataFactory.getOWLSubAnnotationPropertyOfAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUB_PROPERTY_OF;
        }
    }

    class AnnotationPropertyDomainListItemParser
        extends AnnotatedIRIListItemParser<OWLAnnotationProperty> {

        @Override
        public OWLAxiom createAxiom(OWLAnnotationProperty s, IRI o, Set<OWLAnnotation> anns) {
            return dataFactory.getOWLAnnotationPropertyDomainAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DOMAIN;
        }
    }

    class AnnotationPropertyRangeListItemParser
        extends AnnotatedIRIListItemParser<OWLAnnotationProperty> {

        @Override
        public OWLAxiom createAxiom(OWLAnnotationProperty s, IRI o, Set<OWLAnnotation> anns) {
            return dataFactory.getOWLAnnotationPropertyRangeAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return RANGE;
        }
    }
}
