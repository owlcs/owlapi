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

import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.*;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxTokenizer.*;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.util.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLOntologyChecker;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxTokenizer.Token;
import org.semanticweb.owlapi.manchestersyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.NamespaceUtil;
import org.semanticweb.owlapi.util.OntologyAxiomPair;
import org.semanticweb.owlapi.util.RemappingIndividualProvider;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;
import org.semanticweb.owlapi.vocab.*;

/**
 * A parser for the Manchester OWL Syntax. All properties must be defined before
 * they are used. For example, consider the restriction hasPart some Leg. The
 * parser must know in advance whether or not hasPart is an object property or a
 * data property so that Leg gets parsed correctly. In a tool, such as an
 * editor, it is expected that hasPart will already exists as either a data
 * property or an object property. If a complete ontology is being parsed, it is
 * expected that hasPart will have been defined at the top of the file before it
 * is used in any class expressions or property assertions (e.g. ObjectProperty:
 * hasPart)
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class ManchesterOWLSyntaxParserImpl implements ManchesterOWLSyntaxParser {

    // This parser was built by hand! After struggling with terrible
    // error messages produced by ANTLR (or JavaCC) I decides to construct
    // this parser by hand. The error messages that this parser generates
    // are specific to the Manchester OWL Syntax and are such that it should
    // be easy to use this parser in tools such as editors.
    private @Nonnull Provider<OWLOntologyLoaderConfiguration> configProvider;
    private @Nonnull Optional<OWLOntologyLoaderConfiguration> config = emptyOptional();
    protected OWLDataFactory df;
    private List<Token> tokens;
    private int tokenIndex;
    private OWLEntityChecker owlEntityChecker;
    private OWLOntologyChecker owlOntologyChecker = name -> null;
    protected final @Nonnull Set<String> classNames = new HashSet<>();
    protected final @Nonnull Set<String> objectPropertyNames = new HashSet<>();
    protected final @Nonnull Set<String> dataPropertyNames = new HashSet<>();
    protected final @Nonnull Set<String> individualNames = new HashSet<>();
    protected final @Nonnull Set<String> dataTypeNames = new HashSet<>();
    protected final @Nonnull Set<String> annotationPropertyNames = new HashSet<>();
    private final @Nonnull Map<String, SWRLBuiltInsVocabulary> ruleBuiltIns = new TreeMap<>();
    protected @Nonnull DefaultPrefixManager pm = new DefaultPrefixManager();
    protected final @Nonnull Set<ManchesterOWLSyntax> potentialKeywords = new HashSet<>();
    private OWLOntology defaultOntology;
    private final boolean allowEmptyFrameSections = false;
    private final Map<ManchesterOWLSyntax, AnnotatedListItemParser<OWLDataProperty, ?>> dataPropertyFrameSections = new EnumMap<>(
        ManchesterOWLSyntax.class);
    protected RemappingIndividualProvider anonProvider;

    /**
     * @param configurationProvider
     *        configuration provider
     * @param dataFactory
     *        dataFactory
     */
    @Inject
    public ManchesterOWLSyntaxParserImpl(Provider<OWLOntologyLoaderConfiguration> configurationProvider,
        OWLDataFactory dataFactory) {
        configProvider = configurationProvider;
        df = dataFactory;
        anonProvider = new RemappingIndividualProvider(df);
        pm.setPrefix("rdf:", Namespaces.RDF.toString());
        pm.setPrefix("rdfs:", Namespaces.RDFS.toString());
        pm.setPrefix("owl:", Namespaces.OWL.toString());
        pm.setPrefix("dc:", DublinCoreVocabulary.NAME_SPACE);
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
        dataTypeNames.add(OWLRDFVocabulary.RDFS_LITERAL.getPrefixedName());
        dataTypeNames.add(OWLRDFVocabulary.RDF_XML_LITERAL.getShortForm());
        dataTypeNames.add(OWLRDFVocabulary.RDF_XML_LITERAL.getPrefixedName());
        for (IRI iri : OWLRDFVocabulary.BUILT_IN_AP_IRIS) {
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

    @Override
    public OWLOntologyLoaderConfiguration getOntologyLoaderConfiguration() {
        if (config.isPresent()) {
            return config.get();
        }
        config = optional(configProvider.get());
        return config.get();
    }

    @Override
    public void setOntologyLoaderConfigurationProvider(Provider<OWLOntologyLoaderConfiguration> provider) {
        configProvider = provider;
    }

    @Override
    public void setOntologyLoaderConfiguration(OWLOntologyLoaderConfiguration config) {
        this.config = optional(config);
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

    private final Map<ManchesterOWLSyntax, AnnotatedListItemParser<OWLClass, ?>> classFrameSections = new EnumMap<>(
        ManchesterOWLSyntax.class);

    private void initialiseClassFrameSections() {
        initialiseSection(new EntityAnnotationsListItemParser<OWLClass>(), classFrameSections);
        initialiseSection(new ClassSubClassOfListItemParser(), classFrameSections);
        initialiseSection(new ClassEquivalentToListItemParser(), classFrameSections);
        initialiseSection(new ClassDisjointWithListItemParser(), classFrameSections);
        initialiseSection(new ClassHasKeyListItemParser(), classFrameSections);
        initialiseSection(new ClassDisjointUnionOfListItemParser(), classFrameSections);
        // Extensions
        initialiseSection(new ClassSuperClassOfListItemParser(), classFrameSections);
        initialiseSection(new ClassDisjointClassesListItemParser(), classFrameSections);
        initialiseSection(new ClassIndividualsListItemParser(), classFrameSections);
    }

    private final Map<ManchesterOWLSyntax, AnnotatedListItemParser<OWLObjectProperty, ?>> objectPropertyFrameSections = new EnumMap<>(
        ManchesterOWLSyntax.class);

    private void initialiseObjectPropertyFrameSections() {
        initialiseSection(new EntityAnnotationsListItemParser<OWLObjectProperty>(), objectPropertyFrameSections);
        initialiseSection(new ObjectPropertySubPropertyOfListItemParser(), objectPropertyFrameSections);
        initialiseSection(new ObjectPropertyEquivalentToListItemParser(), objectPropertyFrameSections);
        initialiseSection(new ObjectPropertyDisjointWithListItemParser(), objectPropertyFrameSections);
        initialiseSection(new ObjectPropertyDomainListItemParser(), objectPropertyFrameSections);
        initialiseSection(new ObjectPropertyRangeListItemParser(), objectPropertyFrameSections);
        initialiseSection(new ObjectPropertyInverseOfListItemParser(), objectPropertyFrameSections);
        initialiseSection(new ObjectPropertyCharacteristicsItemParser(), objectPropertyFrameSections);
        initialiseSection(new ObjectPropertySubPropertyChainListItemParser(), objectPropertyFrameSections);
        // Extensions
        initialiseSection(new ObjectPropertySuperPropertyOfListItemParser(), objectPropertyFrameSections);
    }

    private void initialiseDataPropertyFrameSections() {
        initialiseSection(new DataPropertySubPropertyOfListItemParser(), dataPropertyFrameSections);
        initialiseSection(new DataPropertyEquivalentToListItemParser(), dataPropertyFrameSections);
        initialiseSection(new DataPropertyDisjointWithListItemParser(), dataPropertyFrameSections);
        initialiseSection(new DataPropertyDomainListItemParser(), dataPropertyFrameSections);
        initialiseSection(new DataPropertyRangeListItemParser(), dataPropertyFrameSections);
        initialiseSection(new DataPropertyCharacteristicsItemParser(), dataPropertyFrameSections);
        initialiseSection(new EntityAnnotationsListItemParser<OWLDataProperty>(), dataPropertyFrameSections);
    }

    private final Map<ManchesterOWLSyntax, AnnotatedListItemParser<OWLAnnotationProperty, ?>> annotationPropertyFrameSections = new EnumMap<>(
        ManchesterOWLSyntax.class);

    private void initialiseAnnotationPropertyFrameSections() {
        initialiseSection(new AnnotationPropertySubPropertyOfListItemParser(), annotationPropertyFrameSections);
        initialiseSection(new AnnotationPropertyDomainListItemParser(), annotationPropertyFrameSections);
        initialiseSection(new AnnotationPropertyRangeListItemParser(), annotationPropertyFrameSections);
        initialiseSection(new EntityAnnotationsListItemParser<OWLAnnotationProperty>(),
            annotationPropertyFrameSections);
    }

    private final Map<ManchesterOWLSyntax, AnnotatedListItemParser<OWLIndividual, ?>> individualFrameSections = new EnumMap<>(
        ManchesterOWLSyntax.class);

    private void initialiseIndividualFrameSections() {
        initialiseSection(new IndividualAnnotationItemParser(), individualFrameSections);
        initialiseSection(new IndividualTypesItemParser(), individualFrameSections);
        initialiseSection(new IndividualFactsItemParser(), individualFrameSections);
        initialiseSection(new IndividualSameAsItemParser(), individualFrameSections);
        initialiseSection(new IndividualDifferentFromItemParser(), individualFrameSections);
        // Extensions
        initialiseSection(new IndividualDifferentIndividualsItemParser(), individualFrameSections);
    }

    @Override
    public void setOWLEntityChecker(OWLEntityChecker owlEntityChecker) {
        this.owlEntityChecker = owlEntityChecker;
    }

    private boolean isOntologyName(String name) {
        return owlOntologyChecker.getOntology(name) != null;
    }

    private boolean isClassName(String name) {
        return classNames.contains(name) || owlEntityChecker != null && owlEntityChecker.getOWLClass(name) != null;
    }

    private @Nullable OWLOntology getOntology(@Nullable String name) {
        return owlOntologyChecker.getOntology(name);
    }

    @Override
    public void setOWLOntologyChecker(OWLOntologyChecker owlOntologyChecker) {
        this.owlOntologyChecker = owlOntologyChecker;
    }

    private boolean isObjectPropertyName(String name) {
        return objectPropertyNames.contains(name)
            || owlEntityChecker != null && owlEntityChecker.getOWLObjectProperty(name) != null;
    }

    private boolean isAnnotationPropertyName(String name) {
        return annotationPropertyNames.contains(name)
            || owlEntityChecker != null && owlEntityChecker.getOWLAnnotationProperty(name) != null;
    }

    private boolean isDataPropertyName(String name) {
        return dataPropertyNames.contains(name)
            || owlEntityChecker != null && owlEntityChecker.getOWLDataProperty(name) != null;
    }

    private boolean isIndividualName(String name) {
        return individualNames.contains(name)
            || owlEntityChecker != null && owlEntityChecker.getOWLIndividual(name) != null;
    }

    private boolean isDatatypeName(String name) {
        return dataTypeNames.contains(name)
            || owlEntityChecker != null && owlEntityChecker.getOWLDatatype(name) != null;
    }

    private boolean isSWRLBuiltin(String name) {
        return ruleBuiltIns.containsKey(name);
    }

    private OWLClass getOWLClass(String name) {
        OWLClass cls = owlEntityChecker.getOWLClass(name);
        if (cls == null && classNames.contains(name)) {
            cls = df.getOWLClass(getIRI(name));
        }
        if (cls == null) {
            throw new ExceptionBuilder().withKeyword(potentialKeywords).withClass().build();
        }
        return cls;
    }

    private OWLObjectProperty getOWLObjectProperty(String name) {
        OWLObjectProperty prop = owlEntityChecker.getOWLObjectProperty(name);
        if (prop == null && objectPropertyNames.contains(name)) {
            prop = df.getOWLObjectProperty(getIRI(name));
        }
        if (prop == null) {
            throw new ExceptionBuilder().withObject().build();
        }
        return prop;
    }

    private OWLIndividual getOWLIndividual(String name) {
        if (name.startsWith("_:")) {
            return anonProvider.getOWLAnonymousIndividual(name);
        }
        return getOWLNamedIndividual(name);
    }

    private OWLNamedIndividual getOWLNamedIndividual(String name) {
        OWLNamedIndividual ind = owlEntityChecker.getOWLIndividual(name);
        if (ind == null && individualNames.contains(name)) {
            ind = df.getOWLNamedIndividual(getIRI(name));
        }
        if (ind == null) {
            throw new ExceptionBuilder().withInd().build();
        }
        return ind;
    }

    private OWLDataProperty getOWLDataProperty(String name) {
        OWLDataProperty prop = owlEntityChecker.getOWLDataProperty(name);
        if (prop == null && dataPropertyNames.contains(name)) {
            prop = df.getOWLDataProperty(getIRI(name));
        }
        if (prop == null) {
            throw new ExceptionBuilder().withData().build();
        }
        return prop;
    }

    private OWLDatatype getOWLDatatype(String name) {
        OWLDatatype dt = owlEntityChecker.getOWLDatatype(name);
        if (dt == null && dataTypeNames.contains(name)) {
            dt = df.getOWLDatatype(getIRI(name));
        }
        if (dt == null) {
            throw new ExceptionBuilder().withDt().build();
        }
        return dt;
    }

    private OWLAnnotationProperty getOWLAnnotationProperty(String name) {
        OWLAnnotationProperty prop = owlEntityChecker.getOWLAnnotationProperty(name);
        if (prop == null && annotationPropertyNames.contains(name)) {
            prop = df.getOWLAnnotationProperty(getIRI(name));
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

    private String peekToken() {
        return getToken().getToken();
    }

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
    @Override
    public OWLClassExpression parseClassExpression() {
        OWLClassExpression desc = parseUnion();
        if (!eof(consumeToken())) {
            throw new ExceptionBuilder().withKeyword(EOF).build();
        }
        return desc;
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
            return df.getOWLObjectIntersectionOf(ops);
        }
    }

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
            return df.getOWLObjectUnionOf(ops);
        }
    }

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
            if (prop.isAnonymous()) {
                throw new ExceptionBuilder().withKeyword(
                    "Inverse construct uses nested object property expression, but object property only is allowed")
                    .build();
            }
            if (brackets) {
                String close = consumeToken();
                if (!CLOSE.matches(close)) {
                    throw new ExceptionBuilder().withKeyword(CLOSE).build();
                }
            }
            return df.getOWLObjectInverseOf(prop.asOWLObjectProperty());
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

    /**
     * Parses all class expressions except ObjectIntersectionOf and
     * ObjectUnionOf.
     * 
     * @return The class expression which was parsed @ * if a non-nary class
     *         expression could not be parsed
     */
    private OWLClassExpression parseNonNaryClassExpression() {
        String tok = peekToken();
        if (NOT.matches(tok)) {
            consumeToken();
            OWLClassExpression complemented = parseNestedClassExpression(false);
            return df.getOWLObjectComplementOf(complemented);
        } else if (isObjectPropertyName(tok) || INVERSE.matches(tok)) {
            return parseObjectRestriction();
        } else if (isDataPropertyName(tok)) {
            // Data restriction
            return parseDataRestriction();
        } else if (OPENBRACE.matches(tok)) {
            return parseObjectOneOf();
        } else if (OPEN.matches(tok)) {
            return parseNestedClassExpression(false);
        } else if (isClassName(tok)) {
            consumeToken();
            return getOWLClass(tok);
        }
        // Add option for strict class name checking
        else {
            consumeToken();
            throw new ExceptionBuilder().withClass().withObject().withData().withKeyword(OPEN, OPENBRACE, NOT, INVERSE)
                .build();
        }
    }

    private OWLClassExpression parseObjectRestriction() {
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        String kw = consumeToken();
        if (SOME.matches(kw)) {
            String possSelfToken = peekToken();
            if (SELF.matches(possSelfToken)) {
                consumeToken();
                return df.getOWLObjectHasSelf(prop);
            } else {
                OWLClassExpression filler = null;
                try {
                    filler = parseNestedClassExpression(false);
                } catch (ParserException e) {
                    e.getExpectedKeywords().add(SELF.keyword());
                    throw e;
                }
                return df.getOWLObjectSomeValuesFrom(prop, filler);
            }
        } else if (ONLY.matches(kw)) {
            OWLClassExpression filler = parseNestedClassExpression(false);
            return df.getOWLObjectAllValuesFrom(prop, filler);
        } else if (VALUE.matches(kw)) {
            String indName = consumeToken();
            if (!isIndividualName(indName)) {
                throw new ExceptionBuilder().withInd().build();
            }
            return df.getOWLObjectHasValue(prop, getOWLIndividual(indName));
        } else if (MIN.matches(kw)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            return df.getOWLObjectMinCardinality(card, prop, filler);
        } else if (MAX.matches(kw)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            return df.getOWLObjectMaxCardinality(card, prop, filler);
        } else if (EXACTLY.matches(kw)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            return df.getOWLObjectExactCardinality(card, prop, filler);
        } else if (ONLYSOME.matches(kw)) {
            String tok = peekToken();
            Set<OWLClassExpression> descs = new HashSet<>();
            if (!OPENBRACKET.matches(tok)) {
                descs.add(parseUnion());
            } else {
                descs.addAll(parseClassExpressionList(OPENBRACKET, CLOSEBRACKET));
            }
            Set<OWLClassExpression> ops = new HashSet<>();
            descs.forEach(d -> ops.add(df.getOWLObjectSomeValuesFrom(prop, d)));
            OWLClassExpression filler;
            if (descs.size() == 1) {
                filler = descs.iterator().next();
            } else {
                filler = df.getOWLObjectUnionOf(descs);
            }
            ops.add(df.getOWLObjectAllValuesFrom(prop, filler));
            return df.getOWLObjectIntersectionOf(ops);
        } else if (SELF.matches(kw)) {
            return df.getOWLObjectHasSelf(prop);
        } else {
            // Error!
            throw new ExceptionBuilder().withKeyword(SOME, ONLY, VALUE, MIN, MAX, EXACTLY, SELF).build();
        }
    }

    private OWLClassExpression parseDataRestriction() {
        OWLDataPropertyExpression prop = parseDataProperty();
        String kw = consumeToken();
        if (SOME.matches(kw)) {
            OWLDataRange rng = parseDataRange();
            return df.getOWLDataSomeValuesFrom(prop, rng);
        } else if (ONLY.matches(kw)) {
            OWLDataRange rng = parseDataRange();
            return df.getOWLDataAllValuesFrom(prop, rng);
        } else if (VALUE.matches(kw)) {
            OWLLiteral con = parseLiteral(null);
            return df.getOWLDataHasValue(prop, con);
        } else if (MIN.matches(kw)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataRange();
            return df.getOWLDataMinCardinality(card, prop, rng);
        } else if (EXACTLY.matches(kw)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataRange();
            return df.getOWLDataExactCardinality(card, prop, rng);
        } else if (MAX.matches(kw)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataRange();
            return df.getOWLDataMaxCardinality(card, prop, rng);
        }
        throw new ExceptionBuilder().withKeyword(SOME, ONLY, VALUE, MIN, EXACTLY, MAX).build();
    }

    private @Nullable OWLFacet parseFacet() {
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

    private OWLDatatype parseDatatype() {
        String name = consumeToken();
        return getOWLDatatype(name);
    }

    @Override
    public OWLDataRange parseDataRange() {
        return parseDataIntersectionOf();
    }

    private OWLDataRange parseDataIntersectionOf() {
        String sep = AND.keyword();
        Set<OWLDataRange> ranges = new HashSet<>();
        while (AND.matches(sep)) {
            ranges.add(parseDataUnionOf());
            sep = peekToken();
            if (AND.matches(sep)) {
                consumeToken();
            }
        }
        if (ranges.isEmpty()) {
            return df.getTopDatatype();
        }
        if (ranges.size() == 1) {
            return ranges.iterator().next();
        }
        return df.getOWLDataIntersectionOf(ranges);
    }

    private OWLDataRange parseDataUnionOf() {
        String sep = OR.keyword();
        Set<OWLDataRange> ranges = new HashSet<>();
        while (OR.matches(sep)) {
            ranges.add(parseDataRangePrimary());
            sep = peekToken();
            if (OR.matches(sep)) {
                consumeToken();
            }
        }
        if (ranges.size() == 1) {
            return ranges.iterator().next();
        } else {
            return df.getOWLDataUnionOf(ranges);
        }
    }

    private OWLDataRange parseDataRangePrimary() {
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
                    facetRestrictions.add(df.getOWLFacetRestriction(fv, con));
                    sep = consumeToken();
                }
                if (!CLOSEBRACKET.matches(sep)) {
                    throw new ExceptionBuilder().withKeyword(CLOSEBRACKET).build();
                }
                return df.getOWLDatatypeRestriction(datatype, facetRestrictions);
            } else {
                return datatype;
            }
        } else if (NOT.matches(tok)) {
            return parseDataComplementOf();
        } else if (OPENBRACE.matches(tok)) {
            return parseDataOneOf();
        } else if (OPEN.matches(tok)) {
            consumeToken();
            OWLDataRange rng = parseDataRange();
            consumeToken(CLOSE.keyword());
            return rng;
        } else {
            consumeToken();
            throw new ExceptionBuilder().withDt().withKeyword(OPENBRACE, NOT).build();
        }
    }

    @Override
    public Set<OWLDataRange> parseDataRangeList() {
        String sep = COMMA.keyword();
        Set<OWLDataRange> ranges = new HashSet<>();
        while (COMMA.matches(sep)) {
            potentialKeywords.remove(COMMA);
            OWLDataRange rng = parseDataRange();
            ranges.add(rng);
            potentialKeywords.add(COMMA);
            sep = peekToken();
            if (COMMA.matches(sep)) {
                consumeToken();
            }
        }
        return ranges;
    }

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
        return df.getOWLDataOneOf(cons);
    }

    private OWLDataRange parseDataComplementOf() {
        String not = consumeToken();
        if (!NOT.matches(not)) {
            throw new ExceptionBuilder().withKeyword(NOT).build();
        }
        OWLDataRange complementedDataRange = parseDataRangePrimary();
        return df.getOWLDataComplementOf(complementedDataRange);
    }

    @Override
    public OWLLiteral parseLiteral(@Nullable OWLDatatype datatype) {
        String tok = consumeToken();
        if (tok.startsWith("\"")) {
            String lit = unquoteLiteral(tok);
            if (peekToken().equals("^")) {
                consumeToken();
                if (!peekToken().equals("^")) {
                    throw new ExceptionBuilder().withKeyword("^").build();
                }
                consumeToken();
                return df.getOWLLiteral(lit, parseDatatype());
            } else if (peekToken().startsWith("@")) {
                // Plain literal with a language tag
                String lang = consumeToken().substring(1);
                return df.getOWLLiteral(lit, lang);
            } else {
                // Plain literal without a language tag
                return df.getOWLLiteral(lit, "");
            }
        } else {
            if (datatype != null) {
                // datatype is known from context
                return df.getOWLLiteral(tok, datatype);
            }
            try {
                int i = Integer.parseInt(tok);
                return df.getOWLLiteral(i);
            } catch (@SuppressWarnings("unused") NumberFormatException e) {
                // Ignore - not interested
            }
            if (tok.endsWith("f") || tok.endsWith("F")) {
                try {
                    // this extra F might qualify as Float a Double INF/-INF
                    float f = Float.parseFloat(tok.replace("INF", "Infinity").replace("inf", "Infinity"));
                    return df.getOWLLiteral(asFloat(f), OWL2Datatype.XSD_FLOAT);
                } catch (@SuppressWarnings("unused") NumberFormatException e) {
                    // Ignore - not interested
                }
            }
            try {
                // ensure it's a valid double, or skip
                Double.parseDouble(tok);
                return df.getOWLLiteral(tok, OWL2Datatype.XSD_DECIMAL);
            } catch (@SuppressWarnings("unused") NumberFormatException e) {
                // Ignore - not interested
            }
            if (LITERAL_TRUE.matches(tok)) {
                return df.getOWLLiteral(true);
            } else if (LITERAL_FALSE.matches(tok)) {
                return df.getOWLLiteral(false);
            }
        }
        throw new ExceptionBuilder().withKeyword(LITERAL_TRUE, LITERAL_FALSE, LITERAL_INTEGER, LITERAL_FLOAT,
            LITERAL_DOUBLE, LITERAL_LITERAL, LITERAL_LIT_DATATYPE, LITERAL_LIT_LANG).build();
    }

    private String unquoteLiteral(String tok) {
        String lit = "";
        if (!tok.endsWith("\"")) {
            consumeToken();
            throw new ExceptionBuilder().withKeyword("\"").build();
        }
        if (tok.length() > 2) {
            lit = tok.substring(1, tok.length() - 1);
        }
        return verifyNotNull(lit);
    }

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
                return df.getOWLThing();
            }
        }
        if (eof(tok)) {
            return df.getOWLThing();
        }
        if (!eof(tok) || !lookaheadCheck) {
            consumeToken();
            throw new ExceptionBuilder().withKeyword(OPEN, OPENBRACE).withClass().build();
        }
        return df.getOWLThing();
    }

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
        return df.getOWLObjectOneOf(inds);
    }

    private static <F> void initialiseSection(AnnotatedListItemParser<F, ?> parser,
        Map<ManchesterOWLSyntax, AnnotatedListItemParser<F, ?>> map, ManchesterOWLSyntax... synonyms) {
        map.put(parser.getFrameSectionKeyword(), parser);
        for (ManchesterOWLSyntax syn : synonyms) {
            map.put(syn, parser);
        }
    }

    @Override
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
                possible.addAll(classFrameSections.keySet());
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
        String subj = consumeToken();
        OWLDatatype datatype = getOWLDatatype(subj);
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        axioms.add(new OntologyAxiomPair(defaultOntology, df.getOWLDeclarationAxiom(datatype)));
        while (true) {
            String sect = peekToken();
            if (EQUIVALENT_TO.matches(sect)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLDataRange> drs = parseDataRangeList();
                for (OWLOntology ont : onts) {
                    for (OWLDataRange dr : drs) {
                        axioms.add(new OntologyAxiomPair(ont, df.getOWLDatatypeDefinitionAxiom(datatype, dr)));
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
            pairs.add(new OntologyAxiomPair(ont, df.getOWLEquivalentClassesAxiom(classExpressions, annotations)));
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
            properties.forEach(pe -> ope.add(pe.asObjectPropertyExpression()));
            propertyAxiom = df.getOWLEquivalentObjectPropertiesAxiom(ope, annotations);
        } else {
            Set<OWLDataPropertyExpression> dpe = new HashSet<>();
            properties.forEach(pe -> dpe.add(pe.asDataPropertyExpression()));
            propertyAxiom = df.getOWLEquivalentDataPropertiesAxiom(dpe, annotations);
        }
        Set<OntologyAxiomPair> pairs = new HashSet<>();
        for (OWLOntology ont : ontologies) {
            pairs.add(new OntologyAxiomPair(ont, propertyAxiom));
        }
        return pairs;
    }

    private Set<OWLAnnotation> parseAnnotations() {
        String next = peekToken();
        Set<OWLAnnotation> annotations = Collections.emptySet();
        if (ANNOTATIONS.matches(next)) {
            consumeToken();
            annotations = parseAnnotationList();
        }
        return annotations;
    }

    private Set<OntologyAxiomPair> parseAnnotations(OWLAnnotationSubject s) {
        String header = consumeToken();
        if (!ANNOTATIONS.matches(header)) {
            throw new ExceptionBuilder().withKeyword(ANNOTATIONS).build();
        }
        Set<OWLOntology> onts = getOntologies();
        Set<OntologyAxiomPair> pairs = new HashSet<>();
        Set<OWLAnnotation> annos = parseAnnotationList();
        for (OWLOntology ont : onts) {
            for (OWLAnnotation anno : annos) {
                if (getOntologyLoaderConfiguration().isLoadAnnotationAxioms()) {
                    pairs.add(new OntologyAxiomPair(ont, df.getOWLAnnotationAssertionAxiom(s, anno)));
                }
            }
        }
        return pairs;
    }

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

    protected OWLAnnotation parseAnnotation() {
        OWLAnnotationProperty annoProp = parseAnnotationProperty();
        String obj = peekToken();
        OWLAnnotation anno = null;
        if (isIndividualName(obj) || isClassName(obj) || isObjectPropertyName(obj) || isDataPropertyName(obj)) {
            consumeToken();
            OWLAnnotationValue value;
            if (obj.startsWith("_:")) {
                value = anonProvider.getOWLAnonymousIndividual(obj);
            } else {
                value = getIRI(obj);
            }
            anno = df.getOWLAnnotation(annoProp, value);
        } else if (obj.startsWith("<")) {
            IRI value = parseIRI();
            anno = df.getOWLAnnotation(annoProp, value);
        } else {
            OWLLiteral con = parseLiteral(null);
            anno = df.getOWLAnnotation(annoProp, con);
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

    private Set<OntologyAxiomPair> parseClassFrame(boolean eof) {
        String tok = consumeToken();
        if (!CLASS.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(CLASS).build();
        }
        String subj = consumeToken();
        OWLClass cls = getOWLClass(subj);
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        axioms.add(new OntologyAxiomPair(defaultOntology, df.getOWLDeclarationAxiom(cls)));
        parseFrameSections(eof, axioms, cls, classFrameSections);
        return axioms;
    }

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

    private Set<OWLOntology> getOntologies() {
        if (peekToken().equals(OPENBRACKET.keyword())) {
            return parseOntologyList();
        } else {
            return CollectionFactory.createSet(defaultOntology);
        }
    }

    @Override
    public void setDefaultOntology(OWLOntology defaultOntology) {
        this.defaultOntology = defaultOntology;
    }

    private boolean isEmptyFrameSection(Map<ManchesterOWLSyntax, ?> parsers) {
        if (!allowEmptyFrameSections) {
            return false;
        }
        String next = peekToken();
        return !ANNOTATIONS.matches(next) && (parsers.containsKey(parse(next)) || eof(next));
    }

    private <F> void parseFrameSections(boolean eof, Set<OntologyAxiomPair> axioms, F frameSubject,
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
                if (frameSubject instanceof OWLAnnotationSubject || frameSubject instanceof OWLEntity) {
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

    private Set<OntologyAxiomPair> parseObjectPropertyFrame(boolean eof) {
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        consumeToken(OBJECT_PROPERTY);
        String token = consumeToken();
        OWLObjectProperty prop = getOWLObjectProperty(token);
        if (!prop.isAnonymous()) {
            axioms.add(new OntologyAxiomPair(defaultOntology, df.getOWLDeclarationAxiom(prop.asOWLObjectProperty())));
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
        String subj = consumeToken();
        OWLDataProperty prop = getOWLDataProperty(subj);
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        axioms.add(new OntologyAxiomPair(defaultOntology, df.getOWLDeclarationAxiom(prop)));
        parseFrameSections(false, axioms, prop, dataPropertyFrameSections);
        return axioms;
    }

    @Override
    public Set<OntologyAxiomPair> parseAnnotationPropertyFrame() {
        String tok = consumeToken();
        if (!ANNOTATION_PROPERTY.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(ANNOTATION_PROPERTY).build();
        }
        String subj = consumeToken();
        OWLAnnotationProperty prop = getOWLAnnotationProperty(subj);
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        for (OWLOntology ont : getOntologies()) {
            axioms.add(new OntologyAxiomPair(ont, df.getOWLDeclarationAxiom(prop)));
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
        String subj = consumeToken();
        OWLIndividual ind = getOWLIndividual(subj);
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        if (!ind.isAnonymous()) {
            axioms.add(new OntologyAxiomPair(getOntology(null), df.getOWLDeclarationAxiom(ind.asOWLNamedIndividual())));
        }
        parseFrameSections(false, axioms, ind, individualFrameSections);
        return axioms;
    }

    protected OWLPropertyAssertionAxiom<?, ?> parseFact(OWLIndividual ind) {
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
                return df.getOWLDataPropertyAssertionAxiom(p, ind, con);
            } else {
                return df.getOWLNegativeDataPropertyAssertionAxiom(p, ind, con);
            }
        } else if (isObjectPropertyName(prop) || INVERSE.matches(prop)) {
            OWLObjectPropertyExpression p = parseObjectPropertyExpression(false);
            if (!negative) {
                return df.getOWLObjectPropertyAssertionAxiom(p, ind, parseIndividual());
            } else {
                return df.getOWLNegativeObjectPropertyAssertionAxiom(p, ind, parseIndividual());
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
            axioms.add(new OntologyAxiomPair(ont, df.getOWLFunctionalObjectPropertyAxiom(prop)));
            axioms.add(new OntologyAxiomPair(ont, df.getOWLObjectPropertyRangeAxiom(prop, cls)));
        }
        return axioms;
    }

    private Set<OntologyAxiomPair> parseValuePartitionValues(Set<OWLOntology> onts, OWLClass superclass) {
        Set<OntologyAxiomPair> axioms = new HashSet<>();
        Set<OWLClass> siblings = new HashSet<>();
        consumeToken(OPENBRACKET.keyword());
        String sep = COMMA.keyword();
        while (COMMA.matches(sep)) {
            String clsName = consumeToken();
            OWLClass cls = getOWLClass(clsName);
            siblings.add(cls);
            OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(cls, superclass);
            for (OWLOntology ont : onts) {
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
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(siblings);
        for (OWLOntology ont : onts) {
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
        Set<OWLOntology> ontologies = getOntologies();
        List<SWRLAtom> body = parseRuleAtoms();
        String tok = consumeToken();
        if (!DASH.matches(tok)) {
            throw new ExceptionBuilder().withKeyword(DASH, COMMA).build();
        }
        consumeToken(">");
        List<SWRLAtom> head = parseRuleAtoms();
        SWRLRule rule = df.getSWRLRule(new LinkedHashSet<>(body), new LinkedHashSet<>(head));
        List<OntologyAxiomPair> pairs = new ArrayList<>();
        for (OWLOntology ont : ontologies) {
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
            Set<String> kw = new TreeSet<>();
            kw.addAll(ruleBuiltIns.keySet());
            kw.add(DIFFERENT_FROM.toString());
            kw.add(SAME_AS.toString());
            throw new ExceptionBuilder().withKeyword(kw).withClass().withObject().withData().build();
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
        return df.getSWRLDataPropertyAtom(getOWLDataProperty(predicate), obj1, obj2);
    }

    private SWRLAtom parseDataRangeAtom() {
        OWLDataRange range = parseDataRange();
        consumeToken(OPEN.keyword());
        SWRLVariable obj1 = parseDVariable();
        consumeToken(CLOSE.keyword());
        return df.getSWRLDataRangeAtom(range, obj1);
    }

    private SWRLAtom parseObjectPropertyAtom() {
        String predicate = consumeToken();
        if (!isObjectPropertyName(predicate)) {
            throw new ExceptionBuilder().withObject().build();
        }
        consumeToken(OPEN.keyword());
        SWRLIArgument obj1 = parseIObject();
        consumeToken(COMMA.keyword());
        SWRLIArgument obj2 = parseIObject();
        consumeToken(CLOSE.keyword());
        return df.getSWRLObjectPropertyAtom(getOWLObjectProperty(predicate), obj1, obj2);
    }

    private SWRLAtom parseClassAtom() {
        OWLClassExpression predicate = parseUnion();
        consumeToken(OPEN.keyword());
        SWRLIArgument obj = parseIObject();
        consumeToken(CLOSE.keyword());
        return df.getSWRLClassAtom(predicate, obj);
    }

    private SWRLDifferentIndividualsAtom parseDifferentFromAtom() {
        consumeToken(ManchesterOWLSyntax.DIFFERENT_FROM.toString());
        consumeToken(OPEN.keyword());
        SWRLIArgument obj1 = parseIObject();
        consumeToken(COMMA.keyword());
        SWRLIArgument obj2 = parseIObject();
        consumeToken(CLOSE.keyword());
        return df.getSWRLDifferentIndividualsAtom(obj1, obj2);
    }

    private SWRLSameIndividualAtom parseSameAsAtom() {
        consumeToken(ManchesterOWLSyntax.SAME_AS.toString());
        consumeToken(OPEN.keyword());
        SWRLIArgument obj1 = parseIObject();
        consumeToken(COMMA.keyword());
        SWRLIArgument obj2 = parseIObject();
        consumeToken(CLOSE.keyword());
        return df.getSWRLSameIndividualAtom(obj1, obj2);
    }

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

    private SWRLVariable parseIVariable() {
        return df.getSWRLVariable(parseVariable());
    }

    private SWRLIndividualArgument parseIIndividualObject() {
        return df.getSWRLIndividualArgument(parseIndividual());
    }

    @Override
    public IRI parseVariable() {
        consumeToken("?");
        String fragment = peekToken();
        if (fragment.startsWith("<")) {
            // then the variable was saved with a full IRI
            // preserve the namespace
            return parseIRI();
        } else {
            consumeToken();
        }
        return IRI.create("urn:swrl#", fragment);
    }

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

    private SWRLVariable parseDVariable() {
        IRI var = parseVariable();
        return df.getSWRLVariable(var);
    }

    private SWRLLiteralArgument parseLiteralObject() {
        OWLLiteral lit = parseLiteral(null);
        return df.getSWRLLiteralArgument(lit);
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
        return df.getSWRLBuiltInAtom(iri, args);
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
            pairs.add(new OntologyAxiomPair(ont, df.getOWLDisjointClassesAxiom(classExpressions, annotations)));
        }
        return pairs;
    }

    private Set<OntologyAxiomPair> parseSameIndividual() {
        String section = consumeToken();
        if (!SAME_INDIVIDUAL.matches(section)) {
            throw new ExceptionBuilder().withKeyword(SAME_INDIVIDUAL).build();
        }
        Set<OWLIndividual> individuals = parseIndividualList();
        Set<OWLOntology> ontologies = getOntologies();
        Set<OWLAnnotation> annotations = parseAnnotations();
        Set<OntologyAxiomPair> pairs = new HashSet<>();
        for (OWLOntology ont : ontologies) {
            pairs.add(new OntologyAxiomPair(ont, df.getOWLSameIndividualAxiom(individuals, annotations)));
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
            props.forEach(pe -> ope.add(pe.asObjectPropertyExpression()));
            propertiesAxiom = df.getOWLDisjointObjectPropertiesAxiom(ope, annotations);
        } else {
            Set<OWLDataPropertyExpression> dpe = new HashSet<>();
            props.forEach(pe -> dpe.add(pe.asDataPropertyExpression()));
            propertiesAxiom = df.getOWLDisjointDataPropertiesAxiom(dpe, annotations);
        }
        for (OWLOntology ont : ontologies) {
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
            pairs.add(new OntologyAxiomPair(ontology, df.getOWLDifferentIndividualsAxiom(individuals, annotations)));
        }
        return pairs;
    }

    protected OWLObjectPropertyCharacteristicAxiom parseObjectPropertyCharacteristic(OWLObjectPropertyExpression prop) {
        String characteristic = consumeToken();
        if (FUNCTIONAL.matches(characteristic)) {
            return df.getOWLFunctionalObjectPropertyAxiom(prop);
        } else if (INVERSE_FUNCTIONAL.matches(characteristic)) {
            return df.getOWLInverseFunctionalObjectPropertyAxiom(prop);
        } else if (SYMMETRIC.matches(characteristic)) {
            return df.getOWLSymmetricObjectPropertyAxiom(prop);
        } else if (ANTI_SYMMETRIC.matches(characteristic) || ASYMMETRIC.matches(characteristic)) {
            return df.getOWLAsymmetricObjectPropertyAxiom(prop);
        } else if (TRANSITIVE.matches(characteristic)) {
            return df.getOWLTransitiveObjectPropertyAxiom(prop);
        } else if (REFLEXIVE.matches(characteristic)) {
            return df.getOWLReflexiveObjectPropertyAxiom(prop);
        } else if (IRREFLEXIVE.matches(characteristic)) {
            return df.getOWLIrreflexiveObjectPropertyAxiom(prop);
        } else {
            throw new ExceptionBuilder().withKeyword(FUNCTIONAL, INVERSE_FUNCTIONAL, SYMMETRIC, ANTI_SYMMETRIC,
                TRANSITIVE, REFLEXIVE, IRREFLEXIVE).build();
        }
    }

    protected OWLDataPropertyCharacteristicAxiom parseDataPropertyCharacteristic(OWLDataPropertyExpression prop) {
        String characteristic = consumeToken();
        if (FUNCTIONAL.matches(characteristic)) {
            return df.getOWLFunctionalDataPropertyAxiom(prop);
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

    protected OWLIndividual parseIndividual() {
        String name = consumeToken();
        if (!isIndividualName(name) && !name.startsWith("_:")) {
            throw new ExceptionBuilder().withInd().build();
        }
        return getOWLIndividual(name);
    }

    protected OWLDataProperty parseDataProperty() {
        String name = consumeToken();
        if (!isDataPropertyName(name)) {
            throw new ExceptionBuilder().withData().build();
        }
        return getOWLDataProperty(name);
    }

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
        return df.getOWLImportsDeclaration(parseIRI());
    }

    protected IRI parseIRI() {
        String iriString = consumeToken();
        if (!(iriString.startsWith("<") && iriString.endsWith(">"))) {
            throw new ExceptionBuilder().withKeyword("<$IRI$>").build();
        }
        return IRI.create(iriString.substring(1, iriString.length() - 1));
    }

    private void processDeclaredEntities() {
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i).getToken();
            String name = null;
            if (i + 1 < tokens.size()) {
                name = tokens.get(i + 1).getToken();
            }
            if (CLASS.matches(token)) {
                if (name != null) {
                    classNames.add(name);
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

            @Override
            public void visit(OWLAnnotationProperty property) {
                annotationPropertyNames.add(pm.getShortForm(property.getIRI()));
            }

            @Override
            public void visit(OWLDatatype datatype) {
                dataTypeNames.add(pm.getShortForm(datatype.getIRI()));
            }

            @Override
            public void visit(OWLNamedIndividual individual) {
                individualNames.add(pm.getShortForm(individual.getIRI()));
            }

            @Override
            public void visit(OWLDataProperty property) {
                dataPropertyNames.add(pm.getShortForm(property.getIRI()));
            }

            @Override
            public void visit(OWLObjectProperty property) {
                objectPropertyNames.add(pm.getShortForm(property.getIRI()));
            }

            @Override
            public void visit(OWLClass cls) {
                classNames.add(pm.getShortForm(cls.getIRI()));
            }
        });
    }

    @Override
    public ManchesterSyntaxDocumentFormat parseOntology(OWLOntology ont) {
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
                    ont.getOWLOntologyManager().makeLoadImportRequest(decl, getOntologyLoaderConfiguration());
                    OWLOntology imported = ont.getOWLOntologyManager().getImportedOntology(decl);
                    if (imported != null) {
                        imported.axioms(AxiomType.DECLARATION).forEach(d -> processDeclaredEntities(d));
                    }
                }
                for (OWLAnnotation anno : header.getAnnotations()) {
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
                ont.getOWLOntologyManager().makeLoadImportRequest(decl, getOntologyLoaderConfiguration());
                imports.add(new AddImport(ont, decl));
                OWLOntology imported = ont.getOWLOntologyManager().getImportedOntology(decl);
                if (imported != null) {
                    imported.axioms(AxiomType.DECLARATION).forEach(d -> processDeclaredEntities(d));
                }
            } else if (PREFIX.matches(section)) {
                parsePrefixDeclaration().forEach((k, v) -> pm.setPrefix(k, v.toString()));
            } else if (RULE.matches(section)) {
                axioms.addAll(parseRuleFrame());
            } else if (eof(section)) {
                break;
            } else {
                consumeToken();
                throw new ExceptionBuilder().withKeyword(CLASS, OBJECT_PROPERTY, DATA_PROPERTY, INDIVIDUAL, DATATYPE,
                    ANNOTATION_PROPERTY, IMPORT, VALUE_PARTITION, PREFIX, EQUIVALENT_CLASSES, DISJOINT_CLASSES,
                    DISJOINT_PROPERTIES, DIFFERENT_INDIVIDUALS, SAME_INDIVIDUAL).build();
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
        if (peekToken().startsWith("<")) {
            ontologyIRI = parseIRI();
            if (peekToken().startsWith("<")) {
                versionIRI = parseIRI();
            }
        }
        Set<OWLAnnotation> annotations = new HashSet<>();
        Set<OWLImportsDeclaration> imports = new HashSet<>();
        while (true) {
            String section = peekToken();
            if (IMPORT.matches(section)) {
                consumeToken();
                tok = peekToken();
                Optional<IRI> importedIRI = emptyOptional();
                if (tok.startsWith("<")) {
                    importedIRI = optional(parseIRI());
                } else if (isOntologyName(tok)) {
                    consumeToken();
                    OWLOntology ont = getOntology(tok);
                    if (ont != null) {
                        importedIRI = ont.getOntologyID().getOntologyIRI();
                    }
                } else {
                    consumeToken();
                    throw new ExceptionBuilder().withOnto().withKeyword("<$ONTOLOGYYURI$>").build();
                }
                if (!importedIRI.isPresent()) {
                    throw new ExceptionBuilder().withOnto().withKeyword("Imported IRI is null").build();
                }
                IRI importedOntologyIRI = importedIRI.get();
                imports.add(df.getOWLImportsDeclaration(importedOntologyIRI));
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

    protected class ExceptionBuilder {

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
        }

        public ExceptionBuilder withOnto() {
            ontologyNameExpected = true;
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
            return new ParserException(tokenSequence, start, line, column, ontologyNameExpected, classNameExpected,
                objectPropertyNameExpected, dataPropertyNameExpected, individualNameExpected, datatypeNameExpected,
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
        public @Nullable OWLClass getOWLClass(String name) {
            if (name.equals("Thing") || name.equals("owl:Thing")) {
                return df.getOWLThing();
            } else if (name.equals("Nothing") || name.equals("owl:Nothing")) {
                return df.getOWLNothing();
            } else if (classNames.contains(name)) {
                return df.getOWLClass(getIRI(name));
            }
            return null;
        }

        @Override
        public @Nullable OWLObjectProperty getOWLObjectProperty(String name) {
            if (objectPropertyNames.contains(name)) {
                return df.getOWLObjectProperty(getIRI(name));
            }
            return null;
        }

        @Override
        public @Nullable OWLDataProperty getOWLDataProperty(String name) {
            if (dataPropertyNames.contains(name)) {
                return df.getOWLDataProperty(getIRI(name));
            }
            return null;
        }

        @Override
        public @Nullable OWLNamedIndividual getOWLIndividual(String name) {
            if (individualNames.contains(name)) {
                return df.getOWLNamedIndividual(getIRI(name));
            }
            return null;
        }

        @Override
        public @Nullable OWLDatatype getOWLDatatype(String name) {
            if (dataTypeNames.contains(name)) {
                return df.getOWLDatatype(getIRI(name));
            }
            return null;
        }

        @Override
        public @Nullable OWLAnnotationProperty getOWLAnnotationProperty(String name) {
            if (annotationPropertyNames.contains(name)) {
                return df.getOWLAnnotationProperty(getIRI(name));
            }
            return null;
        }
    }

    private final Map<String, IRI> nameIRIMap = new HashMap<>();

    protected IRI getIRI(String inputName) {
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
        throw new ExceptionBuilder().withClass().withObject().withData().withKeyword(OPEN, OPENBRACE, INV, FUNCTIONAL,
            INVERSE_FUNCTIONAL, SYMMETRIC, ASYMMETRIC, TRANSITIVE, REFLEXIVE, IRREFLEXIVE).build();
    }

    @Override
    public OWLClassAxiom parseClassAxiom() {
        return (OWLClassAxiom) parseAxiom();
    }

    private OWLAxiom parseAxiomWithIndividualStart() {
        OWLIndividual ind = parseIndividual();
        String kw = consumeToken();
        if (TYPE.matches(kw)) {
            OWLClassExpression type = parseClassExpression();
            return df.getOWLClassAssertionAxiom(type, ind);
        }
        throw new ExceptionBuilder().withKeyword(TYPE).build();
    }

    private OWLAxiom parseAxiomWithDataPropertyStart() {
        OWLDataPropertyExpression prop = parseDataProperty();
        String kw = consumeToken();
        if (SOME.matches(kw)) {
            OWLDataRange dataRange = parseDataIntersectionOf();
            return parseClassAxiomRemainder(df.getOWLDataSomeValuesFrom(prop, dataRange));
        } else if (ONLY.matches(kw)) {
            OWLDataRange dataRange = parseDataIntersectionOf();
            return parseClassAxiomRemainder(df.getOWLDataAllValuesFrom(prop, dataRange));
        } else if (MIN.matches(kw)) {
            int cardi = parseInteger();
            OWLDataRange dataRange = parseDataIntersectionOf();
            return parseClassAxiomRemainder(df.getOWLDataMinCardinality(cardi, prop, dataRange));
        } else if (MAX.matches(kw)) {
            int cardi = parseInteger();
            OWLDataRange dataRange = parseDataIntersectionOf();
            return parseClassAxiomRemainder(df.getOWLDataMaxCardinality(cardi, prop, dataRange));
        } else if (EXACTLY.matches(kw)) {
            int cardi = parseInteger();
            OWLDataRange dataRange = parseDataIntersectionOf();
            return parseClassAxiomRemainder(df.getOWLDataExactCardinality(cardi, prop, dataRange));
        } else if (SUB_PROPERTY_OF.matches(kw)) {
            OWLDataPropertyExpression superProperty = parseDataPropertyExpression();
            return df.getOWLSubDataPropertyOfAxiom(prop, superProperty);
        } else if (EQUIVALENT_TO.matches(kw)) {
            OWLDataPropertyExpression equivProp = parseDataPropertyExpression();
            return df.getOWLEquivalentDataPropertiesAxiom(prop, equivProp);
        } else if (DISJOINT_WITH.matches(kw)) {
            OWLDataPropertyExpression disjProp = parseDataPropertyExpression();
            return df.getOWLDisjointDataPropertiesAxiom(prop, disjProp);
        } else if (DOMAIN.matches(kw)) {
            OWLClassExpression domain = parseClassExpression();
            return df.getOWLDataPropertyDomainAxiom(prop, domain);
        } else if (RANGE.matches(kw)) {
            OWLDataRange range = parseDataRange();
            return df.getOWLDataPropertyRangeAxiom(prop, range);
        } else {
            throw new ExceptionBuilder().withKeyword(SOME, ONLY, MIN, MAX, EXACTLY, SUB_PROPERTY_OF, EQUIVALENT_TO,
                DISJOINT_WITH, DOMAIN, RANGE).build();
        }
    }

    private OWLDataPropertyExpression parseDataPropertyExpression() {
        String tok = consumeToken();
        if (!isDataPropertyName(tok)) {
            throw new ExceptionBuilder().withData().build();
        }
        return getOWLDataProperty(tok);
    }

    private OWLAxiom parseAxiomWithClassExpressionStart() {
        return parseClassAxiomRemainder(parseUnion());
    }

    private OWLAxiom parseClassAxiomRemainder(OWLClassExpression startExpression) {
        String kw = consumeToken();
        if (SUBCLASS_OF.matchesEitherForm(kw)) {
            OWLClassExpression superClass = parseClassExpression();
            return df.getOWLSubClassOfAxiom(startExpression, superClass);
        } else if (DISJOINT_WITH.matchesEitherForm(kw)) {
            OWLClassExpression disjointClass = parseClassExpression();
            return df.getOWLDisjointClassesAxiom(startExpression, disjointClass);
        } else if (EQUIVALENT_TO.matchesEitherForm(kw)) {
            OWLClassExpression equivClass = parseClassExpression();
            return df.getOWLEquivalentClassesAxiom(startExpression, equivClass);
        } else if (AND.matchesEitherForm(kw)) {
            OWLClassExpression conjunct = parseIntersection();
            Set<OWLClassExpression> conjuncts = conjunct.asConjunctSet();
            conjuncts.add(startExpression);
            OWLClassExpression ce = df.getOWLObjectIntersectionOf(conjuncts);
            return parseClassAxiomRemainder(ce);
        } else if (OR.matchesEitherForm(kw)) {
            OWLClassExpression disjunct = parseUnion();
            Set<OWLClassExpression> disjuncts = disjunct.asDisjunctSet();
            disjuncts.add(startExpression);
            OWLClassExpression ce = df.getOWLObjectUnionOf(disjuncts);
            return parseClassAxiomRemainder(ce);
        } else {
            throw new ExceptionBuilder().withKeyword(SUBCLASS_OF, DISJOINT_WITH, EQUIVALENT_TO, AND, OR).build();
        }
    }

    private OWLAxiom parseAxiomWithObjectPropertyStart() {
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        String kw = consumeToken();
        if (SOME.matches(kw)) {
            OWLClassExpression filler = parseUnion();
            return parseClassAxiomRemainder(df.getOWLObjectSomeValuesFrom(prop, filler));
        } else if (ONLY.matches(kw)) {
            OWLClassExpression filler = parseUnion();
            return parseClassAxiomRemainder(df.getOWLObjectAllValuesFrom(prop, filler));
        } else if (MIN.matches(kw)) {
            int cardi = parseInteger();
            OWLClassExpression filler = parseUnion();
            return parseClassAxiomRemainder(df.getOWLObjectMinCardinality(cardi, prop, filler));
        } else if (MAX.matches(kw)) {
            int cardi = parseInteger();
            OWLClassExpression filler = parseUnion();
            return parseClassAxiomRemainder(df.getOWLObjectMaxCardinality(cardi, prop, filler));
        } else if (EXACTLY.matches(kw)) {
            int cardi = parseInteger();
            OWLClassExpression filler = parseUnion();
            return parseClassAxiomRemainder(df.getOWLObjectExactCardinality(cardi, prop, filler));
        } else if (SUB_PROPERTY_OF.matches(kw)) {
            OWLObjectPropertyExpression superProperty = parseObjectPropertyExpression(false);
            return df.getOWLSubObjectPropertyOfAxiom(prop, superProperty);
        } else if (EQUIVALENT_TO.matches(kw)) {
            OWLObjectPropertyExpression equivProp = parseObjectPropertyExpression(false);
            return df.getOWLEquivalentObjectPropertiesAxiom(prop, equivProp);
        } else if (INVERSE_OF.matches(kw)) {
            OWLObjectPropertyExpression invProp = parseObjectPropertyExpression(false);
            return df.getOWLInverseObjectPropertiesAxiom(prop, invProp);
        } else if (DISJOINT_WITH.matches(kw)) {
            OWLObjectPropertyExpression disjProp = parseObjectPropertyExpression(false);
            return df.getOWLDisjointObjectPropertiesAxiom(prop, disjProp);
        } else if (DOMAIN.matches(kw)) {
            OWLClassExpression domain = parseClassExpression();
            return df.getOWLObjectPropertyDomainAxiom(prop, domain);
        } else if (RANGE.matches(kw)) {
            OWLClassExpression range = parseClassExpression();
            return df.getOWLObjectPropertyRangeAxiom(prop, range);
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
            return df.getOWLSubPropertyChainOfAxiom(chain, superProp);
        } else {
            throw new ExceptionBuilder().withKeyword(SOME, ONLY, MIN, MAX, EXACTLY, SUB_PROPERTY_OF, EQUIVALENT_TO,
                INVERSE_OF, DISJOINT_WITH, DOMAIN, RANGE, CHAIN_CONNECT).build();
        }
    }

    private OWLAxiom parseInverseFunctionalPropertyAxiom() {
        String kw = consumeToken();
        if (!INVERSE_FUNCTIONAL.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(INVERSE_FUNCTIONAL).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        return df.getOWLInverseFunctionalObjectPropertyAxiom(prop);
    }

    private OWLAxiom parseSymmetricPropertyAxiom() {
        String kw = consumeToken();
        if (!SYMMETRIC.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(SYMMETRIC).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        return df.getOWLSymmetricObjectPropertyAxiom(prop);
    }

    private OWLAxiom parseAsymmetricPropertyAxiom() {
        String kw = consumeToken();
        if (!ASYMMETRIC.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(ASYMMETRIC).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        return df.getOWLAsymmetricObjectPropertyAxiom(prop);
    }

    private OWLAxiom parseTransitivePropertyAxiom() {
        String kw = consumeToken();
        if (!TRANSITIVE.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(TRANSITIVE).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        return df.getOWLTransitiveObjectPropertyAxiom(prop);
    }

    private OWLAxiom parseReflexivePropertyAxiom() {
        String kw = consumeToken();
        if (!REFLEXIVE.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(REFLEXIVE).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        return df.getOWLReflexiveObjectPropertyAxiom(prop);
    }

    private OWLAxiom parseIrreflexivePropertyAxiom() {
        String kw = consumeToken();
        if (!IRREFLEXIVE.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(IRREFLEXIVE).build();
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
        return df.getOWLIrreflexiveObjectPropertyAxiom(prop);
    }

    private OWLAxiom parseFunctionPropertyAxiom() {
        String kw = consumeToken();
        if (!FUNCTIONAL.matches(kw)) {
            throw new ExceptionBuilder().withKeyword(FUNCTIONAL).build();
        }
        String name = peekToken();
        if (isObjectPropertyName(name)) {
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression(false);
            return df.getOWLFunctionalObjectPropertyAxiom(prop);
        } else if (isDataPropertyName(name)) {
            OWLDataProperty prop = parseDataProperty();
            return df.getOWLFunctionalDataPropertyAxiom(prop);
        } else {
            consumeToken();
            throw new ExceptionBuilder().withObject().withData().build();
        }
    }

    private <F, O> Set<OntologyAxiomPair> parseAnnotatedListItems(F s, AnnotatedListItemParser<F, O> itemParser,
        Set<OWLOntology> ontologies) {
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

        O parseItem(F s);

        OWLAxiom createAxiom(F s, O o, Set<OWLAnnotation> anns);

        ManchesterOWLSyntax getFrameSectionKeyword();
    }

    abstract class AnnotatedClassExpressionListItemParser<F> implements AnnotatedListItemParser<F, OWLClassExpression> {

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

    abstract class AnnotatedIndividualsListItemParser<F> implements AnnotatedListItemParser<F, OWLIndividual> {

        @Override
        public OWLIndividual parseItem(F s) {
            return parseIndividual();
        }
    }

    abstract class AnnotationListItemParser<F> implements AnnotatedListItemParser<F, OWLAnnotation> {

        @Override
        public OWLAnnotation parseItem(F s) {
            return parseAnnotation();
        }
    }

    class ClassSubClassOfListItemParser extends AnnotatedClassExpressionListItemParser<OWLClass> {

        @Override
        public OWLAxiom createAxiom(OWLClass s, OWLClassExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLSubClassOfAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUBCLASS_OF;
        }
    }

    class ClassEquivalentToListItemParser extends AnnotatedClassExpressionListItemParser<OWLClass> {

        @Override
        public OWLAxiom createAxiom(OWLClass s, OWLClassExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLEquivalentClassesAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return EQUIVALENT_TO;
        }
    }

    class ClassDisjointWithListItemParser extends AnnotatedClassExpressionListItemParser<OWLClass> {

        @Override
        public OWLAxiom createAxiom(OWLClass s, OWLClassExpression o, Set<OWLAnnotation> anns) {
            Set<OWLClassExpression> disjointClasses = new HashSet<>();
            disjointClasses.add(s);
            disjointClasses.add(o);
            return df.getOWLDisjointClassesAxiom(disjointClasses, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DISJOINT_WITH;
        }
    }

    class ClassDisjointClassesListItemParser extends AnnotatedClassExpressionSetListItemParser<OWLClass> {

        @Override
        public OWLAxiom createAxiom(OWLClass s, Set<OWLClassExpression> o, Set<OWLAnnotation> anns) {
            // o.add(s);
            return df.getOWLDisjointClassesAxiom(o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DISJOINT_CLASSES;
        }
    }

    class ClassDisjointUnionOfListItemParser extends AnnotatedClassExpressionSetListItemParser<OWLClass> {

        @Override
        public OWLAxiom createAxiom(OWLClass s, Set<OWLClassExpression> o, Set<OWLAnnotation> anns) {
            return df.getOWLDisjointUnionAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DISJOINT_UNION_OF;
        }
    }

    class ClassHasKeyListItemParser extends AnnotatedPropertyListListItemParser<OWLClass> {

        @Override
        public OWLAxiom createAxiom(OWLClass s, Set<OWLPropertyExpression> o, Set<OWLAnnotation> anns) {
            return df.getOWLHasKeyAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return HAS_KEY;
        }
    }

    class ClassSuperClassOfListItemParser extends AnnotatedClassExpressionListItemParser<OWLClass> {

        @Override
        public OWLAxiom createAxiom(OWLClass s, OWLClassExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLSubClassOfAxiom(o, s, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUPERCLASS_OF;
        }
    }

    class ClassIndividualsListItemParser extends AnnotatedIndividualsListItemParser<OWLClass> {

        @Override
        public OWLAxiom createAxiom(OWLClass s, OWLIndividual o, Set<OWLAnnotation> anns) {
            return df.getOWLClassAssertionAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return INDIVIDUALS;
        }
    }

    class EntityAnnotationsListItemParser<E extends OWLEntity> extends AnnotationListItemParser<E> {

        @Override
        public OWLAxiom createAxiom(E s, OWLAnnotation o, Set<OWLAnnotation> anns) {
            return df.getOWLAnnotationAssertionAxiom(s.getIRI(), o, anns);
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

    class ObjectPropertySubPropertyOfListItemParser extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLObjectPropertyExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLSubObjectPropertyOfAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUB_PROPERTY_OF;
        }
    }

    class ObjectPropertySuperPropertyOfListItemParser
        extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLObjectPropertyExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLSubObjectPropertyOfAxiom(o, s, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUPER_PROPERTY_OF;
        }
    }

    class ObjectPropertyEquivalentToListItemParser extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLObjectPropertyExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLEquivalentObjectPropertiesAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return EQUIVALENT_TO;
        }
    }

    class ObjectPropertyDisjointWithListItemParser extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLObjectPropertyExpression o, Set<OWLAnnotation> anns) {
            Set<OWLObjectPropertyExpression> properties = new HashSet<>();
            properties.add(s);
            properties.add(o);
            return df.getOWLDisjointObjectPropertiesAxiom(properties, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DISJOINT_WITH;
        }
    }

    class ObjectPropertyDomainListItemParser extends AnnotatedClassExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLClassExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLObjectPropertyDomainAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DOMAIN;
        }
    }

    class ObjectPropertyRangeListItemParser extends AnnotatedClassExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLClassExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLObjectPropertyRangeAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return RANGE;
        }
    }

    class ObjectPropertyInverseOfListItemParser extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {

        @Override
        public OWLAxiom createAxiom(OWLObjectProperty s, OWLObjectPropertyExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLInverseObjectPropertiesAxiom(s, o, anns);
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
        public OWLAxiom createAxiom(OWLObjectProperty s, List<OWLObjectPropertyExpression> o, Set<OWLAnnotation> anns) {
            return df.getOWLSubPropertyChainOfAxiom(o, s, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUB_PROPERTY_CHAIN;
        }
    }

    class ObjectPropertyCharacteristicsItemParser
        implements AnnotatedListItemParser<OWLObjectProperty, OWLObjectPropertyCharacteristicAxiom> {

        @Override
        public OWLObjectPropertyCharacteristicAxiom parseItem(OWLObjectProperty s) {
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

    class DataPropertySubPropertyOfListItemParser extends DataPropertyExpressionListItemParser<OWLDataProperty> {

        @Override
        public OWLAxiom createAxiom(OWLDataProperty s, OWLDataPropertyExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLSubDataPropertyOfAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUB_PROPERTY_OF;
        }
    }

    class DataPropertyEquivalentToListItemParser extends DataPropertyExpressionListItemParser<OWLDataProperty> {

        @Override
        public OWLAxiom createAxiom(OWLDataProperty s, OWLDataPropertyExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLEquivalentDataPropertiesAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return EQUIVALENT_TO;
        }
    }

    class DataPropertyDisjointWithListItemParser extends DataPropertyExpressionListItemParser<OWLDataProperty> {

        @Override
        public OWLAxiom createAxiom(OWLDataProperty s, OWLDataPropertyExpression o, Set<OWLAnnotation> anns) {
            Set<OWLDataPropertyExpression> properties = new HashSet<>();
            properties.add(s);
            properties.add(o);
            return df.getOWLDisjointDataPropertiesAxiom(properties, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DISJOINT_WITH;
        }
    }

    class DataPropertyDomainListItemParser extends AnnotatedClassExpressionListItemParser<OWLDataProperty> {

        @Override
        public OWLAxiom createAxiom(OWLDataProperty s, OWLClassExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLDataPropertyDomainAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DOMAIN;
        }
    }

    abstract class AnnotatedDataRangeListItemParser<F> implements AnnotatedListItemParser<F, OWLDataRange> {

        @Override
        public OWLDataRange parseItem(F s) {
            return parseDataRange();
        }
    }

    class DataPropertyRangeListItemParser extends AnnotatedDataRangeListItemParser<OWLDataProperty> {

        @Override
        public OWLAxiom createAxiom(OWLDataProperty s, OWLDataRange o, Set<OWLAnnotation> anns) {
            return df.getOWLDataPropertyRangeAxiom(s, o, anns);
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
        public OWLAxiom createAxiom(OWLDataProperty s, OWLDataPropertyCharacteristicAxiom o, Set<OWLAnnotation> anns) {
            return o.getAnnotatedAxiom(anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return CHARACTERISTICS;
        }
    }

    class IndividualTypesItemParser extends AnnotatedClassExpressionListItemParser<OWLIndividual> {

        @Override
        public OWLAxiom createAxiom(OWLIndividual s, OWLClassExpression o, Set<OWLAnnotation> anns) {
            return df.getOWLClassAssertionAxiom(o, s, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return TYPES;
        }
    }

    class IndividualFactsItemParser implements AnnotatedListItemParser<OWLIndividual, OWLPropertyAssertionAxiom<?, ?>> {

        @Override
        public OWLPropertyAssertionAxiom<?, ?> parseItem(OWLIndividual s) {
            return parseFact(s);
        }

        @Override
        public OWLAxiom createAxiom(OWLIndividual s, OWLPropertyAssertionAxiom<?, ?> o, Set<OWLAnnotation> anns) {
            return o.getAnnotatedAxiom(anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return FACTS;
        }
    }

    class IndividualSameAsItemParser extends AnnotatedIndividualsListItemParser<OWLIndividual> {

        @Override
        public OWLAxiom createAxiom(OWLIndividual s, OWLIndividual o, Set<OWLAnnotation> anns) {
            Set<OWLIndividual> individuals = new HashSet<>();
            individuals.add(s);
            individuals.add(o);
            return df.getOWLSameIndividualAxiom(individuals, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SAME_AS;
        }
    }

    class IndividualDifferentFromItemParser extends AnnotatedIndividualsListItemParser<OWLIndividual> {

        @Override
        public OWLAxiom createAxiom(OWLIndividual s, OWLIndividual o, Set<OWLAnnotation> anns) {
            Set<OWLIndividual> individuals = new HashSet<>();
            individuals.add(s);
            individuals.add(o);
            return df.getOWLDifferentIndividualsAxiom(individuals, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DIFFERENT_FROM;
        }
    }

    class IndividualDifferentIndividualsItemParser
        implements AnnotatedListItemParser<OWLIndividual, Set<OWLIndividual>> {

        @Override
        public Set<OWLIndividual> parseItem(OWLIndividual s) {
            return parseIndividualList();
        }

        @Override
        public OWLAxiom createAxiom(OWLIndividual s, Set<OWLIndividual> o, Set<OWLAnnotation> anns) {
            Set<OWLIndividual> individuals = new HashSet<>();
            individuals.add(s);
            individuals.addAll(o);
            return df.getOWLDifferentIndividualsAxiom(individuals, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DIFFERENT_INDIVIDUALS;
        }
    }

    class IndividualAnnotationItemParser implements AnnotatedListItemParser<OWLIndividual, OWLAnnotation> {

        @Override
        public OWLAnnotation parseItem(OWLIndividual s) {
            return parseAnnotation();
        }

        @Override
        public OWLAxiom createAxiom(OWLIndividual s, OWLAnnotation o, Set<OWLAnnotation> anns) {
            if (s.isAnonymous()) {
                return df.getOWLAnnotationAssertionAxiom(s.asOWLAnonymousIndividual(), o, anns);
            } else {
                return df.getOWLAnnotationAssertionAxiom(s.asOWLNamedIndividual().getIRI(), o, anns);
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
        public OWLAxiom createAxiom(OWLAnnotationProperty s, OWLAnnotationProperty o, Set<OWLAnnotation> anns) {
            return df.getOWLSubAnnotationPropertyOfAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return SUB_PROPERTY_OF;
        }
    }

    class AnnotationPropertyDomainListItemParser extends AnnotatedIRIListItemParser<OWLAnnotationProperty> {

        @Override
        public OWLAxiom createAxiom(OWLAnnotationProperty s, IRI o, Set<OWLAnnotation> anns) {
            return df.getOWLAnnotationPropertyDomainAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return DOMAIN;
        }
    }

    class AnnotationPropertyRangeListItemParser extends AnnotatedIRIListItemParser<OWLAnnotationProperty> {

        @Override
        public OWLAxiom createAxiom(OWLAnnotationProperty s, IRI o, Set<OWLAnnotation> anns) {
            return df.getOWLAnnotationPropertyRangeAxiom(s, o, anns);
        }

        @Override
        public ManchesterOWLSyntax getFrameSectionKeyword() {
            return RANGE;
        }
    }
}
