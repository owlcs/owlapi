package org.coode.manchesterowlsyntax;

import org.semanticweb.owl.expression.OWLEntityChecker;
import org.semanticweb.owl.expression.ParserException;
import org.semanticweb.owl.expression.OWLOntologyChecker;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.CollectionFactory;
import org.semanticweb.owl.util.NamespaceUtil;
import org.semanticweb.owl.vocab.*;

import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group<br> Date:
 * 10-Sep-2007<br><br>
 * <p/>
 * A parser for the Manchester OWL Syntax. All properties must be defined before they are used.  For example, consider
 * the restriction hasPart some Leg.  The parser must know in advance whether or not hasPart is an object property or a
 * data property so that Leg gets parsed correctly.  In a tool, such as an editor, it is expected that hasPart will
 * already exists as either a data property or an object property.  If a complete ontology is being parsed, it is
 * expected that hasPart will have been defined at the top of the file before it is used in any class expressions or
 * property assertions (e.g.  ObjectProperty: hasPart)
 */
public class ManchesterOWLSyntaxEditorParser {

    // This parser was built by hand!  After stuggling with terrible
    // error messages produced by ANTLR (or JavaCC) I decides to construct
    // this parser by hand.  The error messages that this parser generates
    // are specific to the Manchester OWL Syntax and are such that it should
    // be easy to use this parser in tools such as editors.

    private OWLDataFactory dataFactory;

    private List<ManchesterOWLSyntaxTokenizer.Token> tokens;

    private int tokenIndex;

    private OWLEntityChecker owlEntityChecker;

    private OWLOntologyChecker owlOntologyChecker = new OWLOntologyChecker() {
        public OWLOntology getOntology(String name) {
            return null;
        }
    };

    private final String EOF = "<EOF>";

    private String base;

    private Set<String> classNames;

    private Set<String> objectPropertyNames;

    private Set<String> dataPropertyNames;

    private Set<String> individualNames;

    private Set<String> dataTypeNames;

    private Set<String> annotationPropertyNames;

    private Map<String, SWRLBuiltInsVocabulary> ruleBuiltIns = new TreeMap<String, SWRLBuiltInsVocabulary>();

    private Set<String> restrictionKeywords;

    private Map<String, String> namespaceMap;

    private String buffer;

    public static final String AND = ManchesterOWLSyntax.AND.toString();

    public static final String OR = ManchesterOWLSyntax.OR.toString();

    public static final String INV = ManchesterOWLSyntax.INVERSE.toString();

    public static final String SOME = ManchesterOWLSyntax.SOME.toString();

    public static final String SELF = ManchesterOWLSyntax.SELF.toString();

    public static final String ONLY = ManchesterOWLSyntax.ONLY.toString();

    public static final String VALUE = ManchesterOWLSyntax.VALUE.toString();

    public static final String MIN = ManchesterOWLSyntax.MIN.toString();

    public static final String MAX = ManchesterOWLSyntax.MAX.toString();

    public static final String EXACTLY = ManchesterOWLSyntax.EXACTLY.toString();

    public static final String ONLYSOME = ManchesterOWLSyntax.ONLYSOME.toString();

    public static final String NOT = ManchesterOWLSyntax.NOT.toString();

    public static final String CLASS = ManchesterOWLSyntax.CLASS.toString() + ":";

    public static final String SUB_CLASS_OF = ManchesterOWLSyntax.SUBCLASS_OF.toString() + ":";

    public static final String SUPER_CLASS_OF = ManchesterOWLSyntax.SUPERCLASS_OF.toString() + ":";

    public static final String INSTANCES = "Instances:";

    public static final String EQUIVALENT_TO = ManchesterOWLSyntax.EQUIVALENT_TO.toString() + ":";

    public static final String DISJOINT_WITH = ManchesterOWLSyntax.DISJOINT_WITH.toString() + ":";

    public static final String DISJOINT_UNION_OF = ManchesterOWLSyntax.DISJOINT_UNION_OF.toString() + ":";

    public static final String DISJOINT_CLASSES = ManchesterOWLSyntax.DISJOINT_CLASSES.toString() + ":";

    public static final String DISJOINT_OBJECT_PROPERTIES = ManchesterOWLSyntax.DISJOINT_OBJECT_PROPERTIES.toString() + ":";

    public static final String DISJOINT_DATA_PROPERTIES = ManchesterOWLSyntax.DISJOINT_DATA_PROPERTIES.toString() + ":";

    public static final String OBJECT_PROPERTY = ManchesterOWLSyntax.OBJECT_PROPERTY.toString() + ":";

    public static final String DATA_PROPERTY = ManchesterOWLSyntax.DATA_PROPERTY.toString() + ":";

    public static final String SUB_PROPERTY_OF = ManchesterOWLSyntax.SUB_PROPERTY_OF.toString() + ":";

    public static final String DOMAIN = ManchesterOWLSyntax.DOMAIN.toString() + ":";

    public static final String RANGE = ManchesterOWLSyntax.RANGE.toString() + ":";

    public static final String INVERSES = ManchesterOWLSyntax.INVERSES.toString() + ":";

    public static final String CHARACTERISTICS = ManchesterOWLSyntax.CHARACTERISTICS.toString() + ":";

    public static final String INDIVIDUAL = ManchesterOWLSyntax.INDIVIDUAL.toString() + ":";

    public static final String ANNOTATIONS = ManchesterOWLSyntax.ANNOTATIONS.toString() + ":";

    public static final String TYPES = ManchesterOWLSyntax.TYPES.toString() + ":";

    public static final String FACTS = ManchesterOWLSyntax.FACTS.toString() + ":";

    public static final String SAME_AS = ManchesterOWLSyntax.SAME_AS.toString() + ":";

    public static final String SAME_INDIVIDUAL = ManchesterOWLSyntax.SAME_INDIVIDUAL.toString() + ":";

    public static final String DIFFERENT_FROM = ManchesterOWLSyntax.DIFFERENT_FROM.toString() + ":";

    public static final String DIFFERENT_INDIVIDUALS = ManchesterOWLSyntax.DIFFERENT_INDIVIDUALS.toString() + ":";

    public static final String VALUE_PARTITION = "ValuePartition:";

    public static final String ONTOLOGY = ManchesterOWLSyntax.ONTOLOGY.toString() + ":";

    public static final String NAMESPACE = ManchesterOWLSyntax.NAMESPACE.toString() + ":";

    public static final String IMPORT = ManchesterOWLSyntax.IMPORT.toString() + ":";

    public static final String SUB_PROPERTY_CHAIN = ManchesterOWLSyntax.SUB_PROPERTY_CHAIN.toString() + ":";


    public static final String FUNCTIONAL = ManchesterOWLSyntax.FUNCTIONAL.toString();

    public static final String INVERSE_FUNCTIONAL = ManchesterOWLSyntax.INVERSE_FUNCTIONAL.toString();

    public static final String SYMMETRIC = ManchesterOWLSyntax.SYMMETRIC.toString();

    public static final String ANTI_SYMMETRIC = ManchesterOWLSyntax.ANTI_SYMMETRIC.toString();

    public static final String ASYMMETRIC = ManchesterOWLSyntax.ASYMMETRIC.toString();

    public static final String TRANSITIVE = ManchesterOWLSyntax.TRANSITIVE.toString();

    public static final String REFLEXIVE = ManchesterOWLSyntax.REFLEXIVE.toString();

    public static final String IRREFLEXIVE = ManchesterOWLSyntax.IRREFLEXIVE.toString();

    public static final String INVERSE_OF = ManchesterOWLSyntax.INVERSE_OF + ":";

    public static final String RULE = ManchesterOWLSyntax.RULE + ":";

    private Set<String> potentialKeywords;

    private OWLOntology defaultOntology = null;


    public ManchesterOWLSyntaxEditorParser(OWLDataFactory dataFactory, String s) {
        this.dataFactory = dataFactory;
        potentialKeywords = new HashSet<String>();
        restrictionKeywords = new HashSet<String>();
        restrictionKeywords.add(ManchesterOWLSyntax.SOME.toString());
        restrictionKeywords.add(ManchesterOWLSyntax.ONLY.toString());
        restrictionKeywords.add(ManchesterOWLSyntax.MIN.toString());
        restrictionKeywords.add(ManchesterOWLSyntax.MAX.toString());
        restrictionKeywords.add(ManchesterOWLSyntax.EXACTLY.toString());
        restrictionKeywords.add(ManchesterOWLSyntax.VALUE.toString());
        restrictionKeywords.add(ManchesterOWLSyntax.THAT.toString());


        classNames = new HashSet<String>();
        objectPropertyNames = new HashSet<String>();
        dataPropertyNames = new HashSet<String>();
        individualNames = new HashSet<String>();
        dataTypeNames = new HashSet<String>();
        annotationPropertyNames = new HashSet<String>();
        namespaceMap = new HashMap<String, String>();
        namespaceMap.put("rdf", Namespaces.RDF.toString());
        namespaceMap.put("rdfs", Namespaces.RDFS.toString());
        namespaceMap.put("owl", Namespaces.OWL.toString());
        namespaceMap.put("dc", DublinCoreVocabulary.NAME_SPACE.toString());
        NamespaceUtil u = new NamespaceUtil();

        for (XSDVocabulary v : XSDVocabulary.values()) {
            dataTypeNames.add(v.getURI().getFragment());
            dataTypeNames.add("xsd:" + v.getURI().getFragment());
        }
        dataTypeNames.add(OWLRDFVocabulary.RDF_XML_LITERAL.getURI().getFragment());
        dataTypeNames.add("rdf:" + OWLRDFVocabulary.RDF_XML_LITERAL.getURI().getFragment());

        dataTypeNames.add(dataFactory.getTopDatatype().getURI().getFragment());

        for (URI uri : OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTIES) {
            String[] res = u.split(uri.toString(), null);
            annotationPropertyNames.add(u.getPrefix(res[0]) + ":" + res[1]);
        }
        for (DublinCoreVocabulary v : DublinCoreVocabulary.values()) {
            annotationPropertyNames.add(v.getQName());
        }
        buffer = s;
        base = "http://www.semanticweb.org#";
        owlEntityChecker = new DefaultEntityChecker();
        tokens = new ArrayList<ManchesterOWLSyntaxTokenizer.Token>();
        ManchesterOWLSyntaxTokenizer tokenizer = new ManchesterOWLSyntaxTokenizer(buffer);
        tokens.addAll(tokenizer.tokenize());
        tokenIndex = 0;
        for(SWRLBuiltInsVocabulary v : SWRLBuiltInsVocabulary.values()) {
            ruleBuiltIns.put(v.getShortName(), v);
        }
    }


    public OWLDataFactory getDataFactory() {
        return dataFactory;
    }


    protected List<ManchesterOWLSyntaxTokenizer.Token> getTokens() {
        return tokens;
    }


    protected void reset() {
        tokenIndex = 0;
    }


    public String getBase() {
        return base;
    }


    public void setBase(String base) {
        this.base = base;
    }


    public OWLEntityChecker getOWLEntityChecker() {
        return owlEntityChecker;
    }


    public void setOWLEntityChecker(OWLEntityChecker owlEntityChecker) {
        this.owlEntityChecker = owlEntityChecker;
    }

    public boolean isOntologyName(String name) {
        return owlOntologyChecker.getOntology(name) != null;
    }

    public boolean isClassName(String name) {
        if (classNames.contains(name)) {
            return true;
        }
        return owlEntityChecker != null && owlEntityChecker.getOWLClass(name) != null;
    }

    public OWLOntology getOntology(String name) {
        return owlOntologyChecker.getOntology(name);
    }

    public void setOWLOntologyChecker(OWLOntologyChecker owlOntologyChecker) {
        this.owlOntologyChecker = owlOntologyChecker;
    }

    public boolean isObjectPropertyName(String name) {
        if (objectPropertyNames.contains(name)) {
            return true;
        }
        return owlEntityChecker != null && owlEntityChecker.getOWLObjectProperty(name) != null;
    }


    public boolean isAnnotationPropertyName(String name) {
        if (annotationPropertyNames.contains(name)) {
            return true;
        }
        return owlEntityChecker != null && owlEntityChecker.getOWLAnnotationProperty(name) != null;
    }


    public boolean isDataPropertyName(String name) {
        if (dataPropertyNames.contains(name)) {
            return true;
        }
        return owlEntityChecker != null && owlEntityChecker.getOWLDataProperty(name) != null;
    }


    public boolean isIndividualName(String name) {
        if (individualNames.contains(name)) {
            return true;
        }
        return owlEntityChecker != null && owlEntityChecker.getOWLIndividual(name) != null;
    }


    public boolean isDatatypeName(String name) {
        if (dataTypeNames.contains(name)) {
            return true;
        }
        return owlEntityChecker != null && owlEntityChecker.getOWLDatatype(name) != null;
    }

    public boolean isSWRLBuiltin(String name) {
        return ruleBuiltIns.containsKey(name);
    }


    public OWLClass getOWLClass(String name) {
        OWLClass cls = owlEntityChecker.getOWLClass(name);
        if (cls == null && classNames.contains(name)) {
            cls = getDataFactory().getOWLClass(getURI(name));
        }
        return cls;
    }


    public OWLObjectProperty getOWLObjectProperty(String name) {
        OWLObjectProperty prop = owlEntityChecker.getOWLObjectProperty(name);
        if (prop == null && objectPropertyNames.contains(name)) {
            prop = getDataFactory().getOWLObjectProperty(getURI(name));
        }
        return prop;
    }


    public OWLNamedIndividual getOWLIndividual(String name) {
        OWLNamedIndividual ind = owlEntityChecker.getOWLIndividual(name);
        if (ind == null && individualNames.contains(name)) {
            ind = getDataFactory().getOWLNamedIndividual(getURI(name));
        }
        return ind;
    }


    public OWLDataProperty getOWLDataProperty(String name) {
        OWLDataProperty prop = owlEntityChecker.getOWLDataProperty(name);
        if (prop == null && dataPropertyNames.contains(name)) {
            prop = getDataFactory().getOWLDataProperty(getURI(name));
        }
        return prop;
    }


    public OWLDatatype getDatatype(String name) {
        if (name.startsWith("xsd:")) {
            return dataFactory.getOWLDatatype(URI.create(Namespaces.XSD + name.substring(name.indexOf(':') + 1)));
        } else {
            return dataFactory.getOWLDatatype(URI.create(Namespaces.XSD + name));
        }
    }


    public OWLAnnotationProperty getAnnotationProperty(String name) {
        OWLAnnotationProperty prop = owlEntityChecker.getOWLAnnotationProperty(name);
        if (prop == null && annotationPropertyNames.contains(name)) {
            prop = getDataFactory().getOWLAnnotationProperty(getURI(name));
        }
        return prop;
    }


    private ManchesterOWLSyntaxTokenizer.Token getLastToken() {
        if (tokenIndex - 1 > -1) {
            return tokens.get(tokenIndex - 1);
        } else {
            return tokens.get(0);
        }
    }


    private String peekToken() {
        return getToken().getToken();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Tokenizer
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////


    protected String consumeToken() {
        String token = tokens.get(tokenIndex).getToken();
        tokenIndex++;
        return token;
    }

    protected void consumeToken(String expected) throws ParserException {
        String tok = consumeToken();
        if(!tok.equals(expected)) {
            throwException(expected);
        }
    }

    public ManchesterOWLSyntaxTokenizer.Token getToken() {
        return tokens.get((tokenIndex < tokens.size()) ? tokenIndex : tokenIndex-1);
    }


    public int getTokenPos() {
        return getToken().getPos();
    }


    public int getTokenCol() {
        return getToken().getCol();
    }


    public int getTokenRow() {
        return getToken().getRow();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Parser
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Parses an OWL class expression that is represented in Manchester OWL Syntax
     *
     * @return The parsed class expression
     * @throws ParserException If a class expression could not be parsed.
     */
    public OWLClassExpression parseClassExpression() throws ParserException {
        OWLClassExpression desc = parseIntersection();
        if (!consumeToken().equals(EOF)) {
            throwException(EOF);
        }
        return desc;
    }


    public OWLClassExpression parseIntersection() throws ParserException {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        String kw = AND;
        while (kw.equalsIgnoreCase(AND)) {
            potentialKeywords.remove(AND);
            ops.add(parseUnion());
            potentialKeywords.add(AND);
            kw = peekToken();
            if (kw.equalsIgnoreCase(AND)) {
                kw = consumeToken();
            } else if (kw.equalsIgnoreCase("that")) {
                consumeToken();
                kw = AND;
            }
        }
        if (ops.size() == 1) {
            return ops.iterator().next();
        } else {
            return dataFactory.getOWLObjectIntersectionOf(ops);
        }
    }


    public OWLClassExpression parseUnion() throws ParserException {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        String kw = OR;
        while (kw.equalsIgnoreCase(OR)) {
            potentialKeywords.remove(OR);
            ops.add(parseNonNaryClassExpression());
            potentialKeywords.add(OR);
            kw = peekToken();
            if (kw.equalsIgnoreCase(OR)) {
                kw = consumeToken();
            }
        }
        if (ops.size() == 1) {
            return ops.iterator().next();
        } else {
            return dataFactory.getOWLObjectUnionOf(ops);
        }
    }


    public OWLObjectPropertyExpression parseObjectPropertyExpression(boolean allowUndeclared) throws ParserException {
        String tok = consumeToken();
        if (tok.equalsIgnoreCase(INV)) {
            String open = consumeToken();
            if (!open.equals("(")) {
                throwException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throwException(")");
            }
            return dataFactory.getOWLObjectPropertyInverse(prop);
        } else {
            if (!allowUndeclared && !isObjectPropertyName(tok)) {
                throwException(false, true, false, false, false, false, INV);
            }
            return getOWLObjectProperty(tok);
        }
    }


    public OWLObjectPropertyExpression parseObjectPropertyExpression() throws ParserException {
        return parseObjectPropertyExpression(false);
    }


    public OWLClassExpression parseRestriction() throws ParserException {
        String tok = peekToken();
        if (isObjectPropertyName(tok) || tok.equalsIgnoreCase(INV)) {
            return parseObjectRestriction();
        } else if (isDataPropertyName(tok)) {
            return parseDataRestriction();
        } else {
            consumeToken();
            throwException(false, true, true, false);
        }
        return null;
    }


    /**
     * Parses all class expressions except ObjectIntersectionOf and ObjectUnionOf
     *
     * @return The class expression which was parsed
     * @throws ParserException if a non-nary class expression could not be parsed
     */
    public OWLClassExpression parseNonNaryClassExpression() throws ParserException {

        String tok = peekToken();
        if (tok.equalsIgnoreCase(NOT)) {
            consumeToken();
            OWLClassExpression complemented = parseNestedClassExpression(false);
            return dataFactory.getOWLObjectComplementOf(complemented);
        } else if (isObjectPropertyName(tok) || tok.equalsIgnoreCase(INV)) {
            return parseObjectRestriction();
        } else if (isDataPropertyName(tok)) {
            // Data restriction
            return parseDataRestriction();
        } else if (tok.equals("{")) {
            return parseObjectOneOf();
        } else if (tok.equals("(")) {
            return parseNestedClassExpression(false);
        } else if (isClassName(tok)) {
            consumeToken();
            return getOWLClass(tok);
        }
        // Add option for strict class name checking
        else {
            consumeToken();
            throwException(true, true, true, false, false, false, "(", "{", NOT, INV);
        }
        return null;
    }


    private OWLClassExpression parseObjectRestriction() throws ParserException {
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
        String kw = consumeToken();
        if (kw.equalsIgnoreCase(SOME)) {
            String possSelfToken = peekToken();
            if (possSelfToken.equalsIgnoreCase(SELF)) {
                consumeToken();
                return dataFactory.getOWLObjectHasSelf(prop);
            } else {
                OWLClassExpression filler = null;
                try {
                    filler = parseNestedClassExpression(false);
                }
                catch (ParserException e) {
                    Set<String> keywords = new HashSet<String>();
                    keywords.addAll(e.getExpectedKeywords());
                    keywords.add(SELF);
                    throwException(e.isClassNameExpected(),
                            e.isObjectPropertyNameExpected(),
                            e.isDataPropertyNameExpected(),
                            e.isIndividualNameExpected(),
                            e.isDatatypeNameExpected(),
                            e.isAnnotationPropertyNameExpected(),
                            keywords.toArray(new String[keywords.size()]));
                }
                return dataFactory.getOWLObjectSomeValuesFrom(prop, filler);
            }
        } else if (kw.equalsIgnoreCase(ONLY)) {
            OWLClassExpression filler = parseNestedClassExpression(false);
            return dataFactory.getOWLObjectAllValuesFrom(prop, filler);
        } else if (kw.equalsIgnoreCase(VALUE)) {
            String indName = consumeToken();
            if (!isIndividualName(indName)) {
                throwException(false, false, false, true);
            }
            return dataFactory.getOWLObjectHasValue(prop, getOWLIndividual(indName));
        } else if (kw.equalsIgnoreCase(MIN)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            if (filler != null) {
                return dataFactory.getOWLObjectMinCardinality(prop, card, filler);
            } else {
                return dataFactory.getOWLObjectMinCardinality(prop, card);
            }
        } else if (kw.equalsIgnoreCase(MAX)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            if (filler != null) {
                return dataFactory.getOWLObjectMaxCardinality(prop, card, filler);
            } else {
                return dataFactory.getOWLObjectMaxCardinality(prop, card);
            }
        } else if (kw.equalsIgnoreCase(EXACTLY)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            if (filler != null) {
                return dataFactory.getOWLObjectExactCardinality(prop, card, filler);
            } else {
                return dataFactory.getOWLObjectExactCardinality(prop, card);
            }
        } else if (kw.equalsIgnoreCase(ONLYSOME)) {
            String tok = peekToken();
            Set<OWLClassExpression> descs = new HashSet<OWLClassExpression>();
            if (!tok.equals("[")) {
                descs.add(parseIntersection());
            } else {
                descs.addAll(parseClassExpressionList("[", "]"));
            }
            Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
            for (OWLClassExpression desc : descs) {
                ops.add(dataFactory.getOWLObjectSomeValuesFrom(prop, desc));
            }
            OWLClassExpression filler;
            if (descs.size() == 1) {
                filler = descs.iterator().next();
            } else {
                filler = dataFactory.getOWLObjectUnionOf(descs);
            }
            ops.add(dataFactory.getOWLObjectAllValuesFrom(prop, filler));
            return dataFactory.getOWLObjectIntersectionOf(ops);
        } else {
            // Error!
            throwException(SOME, ONLY, VALUE, MIN, MAX, EXACTLY);
        }
        return null;
    }


    public OWLClassExpression parseDataRestriction() throws ParserException {
        OWLDataPropertyExpression prop = parseDataProperty();
        String kw = consumeToken();
        if (kw.equalsIgnoreCase(SOME)) {
            OWLDataRange rng = parseDataRange(false);
            return dataFactory.getOWLDataSomeValuesFrom(prop, rng);
        } else if (kw.equalsIgnoreCase(ONLY)) {
            OWLDataRange rng = parseDataRange(false);
            return dataFactory.getOWLDataAllValuesFrom(prop, rng);
        } else if (kw.equalsIgnoreCase(VALUE)) {
            OWLLiteral con = parseConstant();
            return dataFactory.getOWLDataHasValue(prop, con);
        } else if (kw.equalsIgnoreCase(MIN)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataRange(true);
            if (rng != null) {
                return dataFactory.getOWLDataMinCardinality(prop, card, rng);
            } else {
                return dataFactory.getOWLDataMinCardinality(prop, card);
            }
        } else if (kw.equalsIgnoreCase(EXACTLY)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataRange(true);
            if (rng != null) {
                return dataFactory.getOWLDataExactCardinality(prop, card, rng);
            } else {
                return dataFactory.getOWLDataExactCardinality(prop, card, rng);
            }
        } else if (kw.equalsIgnoreCase(MAX)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataRange(true);
            if (rng != null) {
                return dataFactory.getOWLDataMaxCardinality(prop, card, rng);
            } else {
                return dataFactory.getOWLDataMaxCardinality(prop, card, rng);
            }
        }
        throwException(SOME, ONLY, VALUE, MIN, EXACTLY, MAX);
        return null;
    }


    public OWLFacet parseFacet() throws ParserException {
        String facet = consumeToken();
        if (facet.equals(">")) {
            if (peekToken().equals("=")) {
                consumeToken();
                return OWLFacet.MIN_INCLUSIVE;
            } else {
                return OWLFacet.MIN_EXCLUSIVE;
            }
        } else if (facet.equals("<")) {
            if (peekToken().equals("=")) {
                consumeToken();
                return OWLFacet.MAX_INCLUSIVE;
            } else {
                return OWLFacet.MAX_EXCLUSIVE;
            }
        }
        return OWLFacet.getFacetBySymbolicName(facet);
    }

    public OWLDatatype parseDatatype() throws ParserException {
        String name = consumeToken();
        return getDatatype(name);
    }


    public OWLDataRange parseDataRange(boolean allowLookahead) throws ParserException {
        String tok = peekToken();
        if (isDatatypeName(tok)) {
            consumeToken();
            OWLDatatype datatype = getDatatype(tok);
            String next = peekToken();
            if (next.equals("[")) {
                // Restricted data range
                consumeToken();
                String sep = ",";
                Set<OWLFacetRestriction> facetRestrictions = new HashSet<OWLFacetRestriction>();
                while (sep.equals(",")) {
                    OWLFacet fv = parseFacet();
                    if (fv == null) {
                        throwException(OWLFacet.getFacets().toArray(new String[OWLFacet.getFacetURIs().size()]));
                    }
                    OWLLiteral con = parseConstant();
                    if (!con.isTyped()) {
                        con = dataFactory.getOWLTypedLiteral(con.getLiteral());
                    }
                    facetRestrictions.add(dataFactory.getOWLFacetRestriction(fv, con.asOWLTypedLiteral()));
                    sep = consumeToken();
                }
                if (!sep.equals("]")) {
                    throwException("]");
                }
                return dataFactory.getOWLDatatypeRestriction(datatype, facetRestrictions);
            } else {
                return datatype;
            }
        } else if (tok.equalsIgnoreCase(NOT)) {
            return parseDataComplementOf();
        } else if (tok.equals("{")) {
            return parseDataOneOf();
        } else if (!allowLookahead) {
            consumeToken();
            throwException(false, false, false, false, true, false, NOT, "{");
        }
        return null;
    }


    public Set<OWLDataRange> parseDataRangeList() throws ParserException {
        String sep = ",";
        Set<OWLDataRange> ranges = new HashSet<OWLDataRange>();
        while (sep.equals(",")) {
            potentialKeywords.remove(",");
            OWLDataRange rng = parseDataRange(false);
            ranges.add(rng);
            potentialKeywords.add(",");
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        return ranges;
    }


    private OWLDataRange parseDataOneOf() throws ParserException {
        consumeToken();
        Set<OWLLiteral> cons = new HashSet<OWLLiteral>();
        String sep = ",";
        while (sep.equals(",")) {
            OWLLiteral con = parseConstant();
            cons.add(con);
            sep = consumeToken();
        }
        if (!sep.equals("}")) {
            throwException(",", "}");
        }
        return dataFactory.getOWLDataOneOf(cons);
    }


    private OWLDataRange parseDataComplementOf() throws ParserException {
        String not = consumeToken();
        if (!not.equalsIgnoreCase(NOT)) {
            throwException(NOT);
        }
        String open = consumeToken();
        if (!open.equals("(")) {
            throwException("(");
        }
        OWLDataRange complementedDataRange = parseDataRange(false);
        String close = consumeToken();
        if (!close.equals(")")) {
            throwException(")");
        }
        return dataFactory.getOWLDataComplementOf(complementedDataRange);
    }


    public OWLLiteral parseConstant() throws ParserException {
        String tok = consumeToken();
        if (tok.startsWith("\"")) {
            String lit = tok.substring(1, tok.length() - 1).trim();
            if (peekToken().equals("^")) {
                consumeToken();
                if (!peekToken().equals("^")) {
                    throwException("^");
                }
                consumeToken();
                return dataFactory.getOWLTypedLiteral(lit, parseDatatype());
            } else if (peekToken().equals("@")) {
                consumeToken();
                String lang = consumeToken();
                return dataFactory.getRDFTextLiteral(lit, lang);
            } else {
                return dataFactory.getOWLTypedLiteral(lit);
            }
        } else {
            try {
                int i = Integer.parseInt(tok);
                return dataFactory.getOWLTypedLiteral(i);
            }
            catch (NumberFormatException e) {
                // Ignore - not interested
            }
            if (tok.endsWith("f")) {
                try {
                    float f = Float.parseFloat(tok);
                    return dataFactory.getOWLTypedLiteral(f);
                }
                catch (NumberFormatException e) {
                    // Ignore - not interested
                }
            }
            try {
                double d = Double.parseDouble(tok);
                return dataFactory.getOWLTypedLiteral(tok, dataFactory.getOWLDatatype(XSDVocabulary.DOUBLE.getURI()));
//                return dataFactory.getOWLTypedLiteral(d);
            }
            catch (NumberFormatException e) {
                // Ignore - not interested
            }

            if (tok.equals("true")) {
                return dataFactory.getOWLTypedLiteral(true);
            } else if (tok.equals("false")) {
                return dataFactory.getOWLTypedLiteral(false);
            }
        }
        throwException(false,
                false,
                false,
                false,
                false,
                false,
                "true",
                "false",
                "$integer$",
                "$float$",
                "$double$",
                "\"$Literal$\"",
                "\"$Literal$\"^^<datatype>",
                "\"$Literal$\"@<lang>");
        return null;
    }


    public int parseInteger() throws ParserException {
        String i = consumeToken();
        try {
            return Integer.parseInt(i);
        }
        catch (NumberFormatException e) {
            throw new ParserException(Arrays.asList(getToken().getToken()), getTokenPos(), getTokenRow(), true, getTokenCol());
        }
    }


    public String getLineCol() {
        return "Encountered " + getLastToken() + " at " + getTokenRow() + ":" + getTokenCol() + " ";
    }


    private OWLClassExpression parseNestedClassExpression(boolean lookaheadCheck) throws ParserException {
        String tok = peekToken();
        if (tok.equals("(")) {
            consumeToken();
            OWLClassExpression desc = parseIntersection();
            String closeBracket = consumeToken();
            if (!closeBracket.equals(")")) {
                // Error!
                throwException(")");
            }
            return desc;
        } else if (tok.equals("{")) {
            return parseObjectOneOf();
        } else if (isClassName(tok)) {
            String name = consumeToken();
            return getOWLClass(name);
        } else if (!tok.equals(EOF) || !lookaheadCheck) {
            consumeToken();
            throwException(true, false, false, false, false, false, "(", "{");
        }
        return null;
    }


    public OWLClassExpression parseObjectOneOf() throws ParserException {
        String open = consumeToken();
        if (!open.equals("{")) {
            throwException("{");
        }
        String sep = ",";
        Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
        while (sep.equals(",")) {
            OWLIndividual ind = parseIndividual();
            inds.add(ind);
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        String close = consumeToken();
        if (!close.equals("}")) {
            throwException("}", ",");
        }
        return dataFactory.getOWLObjectOneOf(inds);
    }


    public Set<OntologyAxiomPair> parseFrames() throws ParserException {
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        Set<String> possible = new HashSet<String>();
        resetPossible(possible);
        while (true) {
            String tok = peekToken();
            if (tok.equalsIgnoreCase(CLASS)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseClassFrame());
                possible.addAll(Arrays.asList(SUB_CLASS_OF, EQUIVALENT_TO, DISJOINT_WITH));
                possible.add(SUPER_CLASS_OF);
            } else if (tok.equalsIgnoreCase(OBJECT_PROPERTY)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseObjectPropertyFrame());
                possible.addAll(Arrays.asList(SUB_PROPERTY_OF, EQUIVALENT_TO, DISJOINT_WITH, INVERSES, CHARACTERISTICS, DOMAIN, RANGE));
            } else if (tok.equalsIgnoreCase(DATA_PROPERTY)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseDataPropertyFrame());
                possible.addAll(Arrays.asList(SUB_PROPERTY_OF, EQUIVALENT_TO, DISJOINT_WITH, CHARACTERISTICS, DOMAIN, RANGE));
            } else if (tok.equalsIgnoreCase(INDIVIDUAL)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseIndividualFrame());
                possible.addAll(Arrays.asList(TYPES, FACTS, DIFFERENT_FROM, SAME_AS));
            } else if (tok.equalsIgnoreCase(VALUE_PARTITION)) {
                potentialKeywords.clear();
                resetPossible(possible);
                parseValuePartitionFrame();
            } else if(tok.equalsIgnoreCase(RULE)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseRuleFrame());
            } else {
                if (tok.equals(EOF)) {
                    break;
                } else {
                    consumeToken();
                    throwException(possible.toArray(new String[possible.size()]));
                }
            }
        }
        return axioms;
    }

    private void resetPossible(Set<String> possible) {
        possible.clear();
        possible.add(ANNOTATIONS);
        possible.add(CLASS);
        possible.add(OBJECT_PROPERTY);
        possible.add(DATA_PROPERTY);
        possible.add(INDIVIDUAL);
        possible.add(VALUE_PARTITION);
        possible.add(RULE);
    }


    public Set<OntologyAxiomPair> parseAnnotations(URI subject) throws ParserException {
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        String header = consumeToken();
        if (!header.equals(ANNOTATIONS)) {
            throwException(ANNOTATIONS);
        }
        Set<OWLOntology> onts = getOntologies();
        String sep = ",";
        while (sep.equals(",")) {
            potentialKeywords.clear();
            String prop = consumeToken();
            if (!isAnnotationPropertyName(prop)) {
                throwException(false, false, false, false, false, true);
            }
            OWLAnnotationProperty annoProp = getAnnotationProperty(prop);
            String obj = peekToken();
            if (isIndividualName(obj) || isClassName(obj) || isObjectPropertyName(obj) || isDataPropertyName(obj)) {
                consumeToken();
                URI uri = getURI(obj);
                OWLAnnotation anno = dataFactory.getOWLAnnotation(annoProp, getIRI(obj));
                annos.add(anno);
            } else {
                OWLLiteral con = parseConstant();
                OWLAnnotation anno = dataFactory.getOWLAnnotation(annoProp, con);
                annos.add(anno);
            }
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        Set<OntologyAxiomPair> pairs = new HashSet<OntologyAxiomPair>();
        for(OWLOntology ont : onts) {
            for(OWLAnnotation anno : annos) {
                OWLAnnotationAssertionAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(getDataFactory().getIRI(subject), anno);
                pairs.add(new OntologyAxiomPair(ont, ax));
            }
        }

        return pairs;
    }


    public Set<OntologyAxiomPair> parseAnnotations(OWLEntity subject) throws ParserException {
        return parseAnnotations(subject.getURI());
    }


    public Set<OntologyAxiomPair> parseClassFrame() throws ParserException {
        return parseClassFrame(false);
    }


    public Set<OntologyAxiomPair> parseClassFrameEOF() throws ParserException {
        return parseClassFrame(true);
    }


    private Set<OntologyAxiomPair> parseClassFrame(boolean eof) throws ParserException {
        String tok = consumeToken();
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        if (!tok.equalsIgnoreCase(CLASS)) {
            throwException(CLASS);
        }
        String subj = consumeToken();
        OWLClass cls = getOWLClass(subj);
        if (cls == null) {
            throwException(true, false, false, false);
        }
        axioms.add(new OntologyAxiomPair(defaultOntology, getDataFactory().getOWLDeclarationAxiom(cls)));
        parseClassFrameSections(eof, axioms, cls);
        return axioms;
    }

    private Set<OWLOntology> parseOntologyList() throws ParserException {
        potentialKeywords.clear();
        String tok = consumeToken();
        if (!tok.equals("[")) {
            throwException("[");
        }
        tok = consumeToken();
        if (!tok.equalsIgnoreCase("in")) {
            throwException("in");
        }
        String sep = ",";
        Set<OWLOntology> onts = new HashSet<OWLOntology>();
        while (sep.equals(",")) {
            tok = consumeToken();
            if (isOntologyName(tok)) {
                OWLOntology ont = getOntology(tok);
                if (ont != null) {
                    onts.add(ont);
                }
            } else {
                throwException(true);
            }
            sep = consumeToken();
            if (sep.equals("]")) {
                break;
            } else if (!sep.equals(",")) {
                throwException(",", "]");
            }
        }
        return onts;
    }

    private Set<OWLOntology> getOntologies() throws ParserException {
        if (peekToken().equals("[")) {
            return parseOntologyList();
        } else {
            return Collections.singleton(defaultOntology);
        }
    }

    public void setDefaultOntology(OWLOntology defaultOntology) {
        this.defaultOntology = defaultOntology;
    }

    private void parseClassFrameSections(boolean eof, Set<OntologyAxiomPair> axioms, OWLClass cls) throws ParserException {
        while (true) {
            String sect = peekToken();
            if (sect.equalsIgnoreCase(SUB_CLASS_OF)) {
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLClassExpression> descs = parseClassExpressionList();
                for (OWLOntology ont : onts) {
                    for (OWLClassExpression desc : descs) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLSubClassOfAxiom(cls, desc)));
                    }
                }

            } else if (sect.equalsIgnoreCase(EQUIVALENT_TO)) {
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLClassExpression> descs = parseClassExpressionList();
                for (OWLOntology ont : onts) {
                    for (OWLClassExpression desc : descs) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLEquivalentClassesAxiom(CollectionFactory.createSet(cls, desc))));
                    }
                }
            } else if (sect.equalsIgnoreCase(DISJOINT_WITH)) {
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLClassExpression> descs = parseClassExpressionList();
                for (OWLOntology ont : onts) {
                    for (OWLClassExpression desc : descs) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLDisjointClassesAxiom(cls, desc)));
                    }
                }
            } else if (sect.equalsIgnoreCase(DISJOINT_UNION_OF)) {
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLClassExpression> descs = parseClassExpressionList();
                for (OWLOntology ont : onts) {
                    axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLDisjointUnionAxiom(cls, descs)));
                }
            } else if (sect.equals(SUPER_CLASS_OF)) {
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLClassExpression> ces = parseClassExpressionList();
                for (OWLOntology ont : onts) {
                    for (OWLClassExpression ce : ces) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLSubClassOfAxiom(ce, cls)));
                    }
                }

            } else if (sect.equals(ANNOTATIONS)) {
                axioms.addAll(parseAnnotations(cls));
            } else {
                // If force EOF then we need EOF or else everything is o.k.
                if (eof && !sect.equals(EOF)) {
                    consumeToken();
                    throwException(SUB_CLASS_OF, EQUIVALENT_TO, DISJOINT_WITH, ANNOTATIONS);
                } else {
                    break;
                }
            }
        }
    }


    public Set<OntologyAxiomPair> parseObjectPropertyFrame() throws ParserException {
        return parseObjectPropertyFrame(false);
    }


    public Set<OntologyAxiomPair> parseObjectPropertyFrame(boolean eof) throws ParserException {
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        OWLObjectPropertyExpression prop = null;
        String tok = consumeToken();
        if (!tok.equalsIgnoreCase(OBJECT_PROPERTY)) {
            throwException(OBJECT_PROPERTY);
        }
        String subj = peekToken();
        prop = parseObjectPropertyExpression();
        if(prop == null) {
            throwException(false, true, false, false);
        }
        if (!prop.isAnonymous()) {
            axioms.add(new OntologyAxiomPair(defaultOntology, dataFactory.getOWLDeclarationAxiom(prop.asOWLObjectProperty())));
        }
        while (true) {
            String sect = peekToken();
            if (sect.equalsIgnoreCase(SUB_PROPERTY_OF)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLObjectPropertyExpression> props = parseObjectPropertyList();
                for (OWLOntology ont : onts) {
                    for (OWLObjectPropertyExpression pe : props) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLSubObjectPropertyOfAxiom(prop, pe)));
                    }
                }
            } else if (sect.equalsIgnoreCase(EQUIVALENT_TO)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLObjectPropertyExpression> props = parseObjectPropertyList();
                for (OWLOntology ont : onts) {
                    for (OWLObjectPropertyExpression pe : props) {
                        OWLAxiom ax = dataFactory.getOWLEquivalentObjectPropertiesAxiom(CollectionFactory.createSet(prop, pe));
                        axioms.add(new OntologyAxiomPair(ont, ax));
                    }
                }
            } else if (sect.equalsIgnoreCase(DISJOINT_WITH)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLObjectPropertyExpression> props = parseObjectPropertyList();
                for (OWLOntology ont : onts) {
                    for (OWLObjectPropertyExpression pe : props) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLDisjointObjectPropertiesAxiom(CollectionFactory.createSet(prop, pe))));
                    }
                }
            } else if (sect.equalsIgnoreCase(DOMAIN)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLClassExpression> domains = parseClassExpressionList();
                for (OWLOntology ont : onts) {
                    for (OWLClassExpression dom : domains) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLObjectPropertyDomainAxiom(prop, dom)));
                    }
                }
            } else if (sect.equalsIgnoreCase(RANGE)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLClassExpression> ranges = parseClassExpressionList();
                for (OWLOntology ont : onts) {
                    for (OWLClassExpression rng : ranges) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLObjectPropertyRangeAxiom(prop, rng)));
                    }
                }
            } else if (sect.equalsIgnoreCase(INVERSES) || sect.equalsIgnoreCase(INVERSE_OF)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLObjectPropertyExpression> inverses = parseObjectPropertyList();
                for (OWLOntology ont : onts) {
                    for (OWLObjectPropertyExpression inv : inverses) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLInverseObjectPropertiesAxiom(prop, inv)));
                    }
                }
            } else if (sect.equalsIgnoreCase(CHARACTERISTICS)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLAxiom> axs = parseObjectPropertyCharacteristicList(prop);
                for (OWLOntology ont : onts) {
                    for (OWLAxiom ax : axs) {
                        axioms.add(new OntologyAxiomPair(ont, ax));
                    }
                }
            } else if (sect.equalsIgnoreCase(ANNOTATIONS)) {
                potentialKeywords.clear();
                Set<OWLOntology> onts = getOntologies();
                axioms.addAll(parseAnnotations(prop.asOWLObjectProperty()));
            } else if (sect.equalsIgnoreCase(SUB_PROPERTY_CHAIN)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                List<OWLObjectPropertyExpression> props = parseObjectPropertyChain();
                OWLAxiom ax = dataFactory.getOWLSubPropertyChainOfAxiom(props, prop);
                for (OWLOntology ont : onts) {
                    axioms.add(new OntologyAxiomPair(ont, ax));
                }
            } else {
                // If force EOF then we need EOF or else everything is o.k.
                if (eof && !sect.equals(EOF)) {
                    consumeToken();
                    throwException(SUB_PROPERTY_OF,
                            EQUIVALENT_TO,
                            DISJOINT_WITH,
                            ANNOTATIONS,
                            DOMAIN,
                            RANGE,
                            INVERSES,
                            CHARACTERISTICS,
                            SUB_PROPERTY_CHAIN);
                } else {
                    break;
                }
            }
        }
        return axioms;
    }


    public Set<OntologyAxiomPair> parseDataPropertyFrame() throws ParserException {
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        String tok = consumeToken();
        if (!tok.equalsIgnoreCase(DATA_PROPERTY)) {
            throwException(DATA_PROPERTY);
        }
        String subj = consumeToken();
        OWLDataProperty prop = getOWLDataProperty(subj);
        if(prop == null) {
            throwException(false, false, true, false);
        }
        while (true) {
            String sect = peekToken();
            if (sect.equalsIgnoreCase(SUB_PROPERTY_OF)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLDataProperty> props = parseDataPropertyList();
                for (OWLOntology ont : onts) {
                    for (OWLDataProperty pe : props) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLSubDataPropertyOfAxiom(prop, pe)));
                    }
                }
            } else if (sect.equalsIgnoreCase(EQUIVALENT_TO)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLDataProperty> props = parseDataPropertyList();
                for (OWLOntology ont : onts) {
                    for (OWLDataProperty pe : props) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLEquivalentDataPropertiesAxiom(CollectionFactory.createSet(prop, pe))));
                    }
                }
            } else if (sect.equalsIgnoreCase(DISJOINT_WITH)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLDataProperty> props = parseDataPropertyList();
                for (OWLOntology ont : onts) {
                    for (OWLDataProperty pe : props) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLDisjointDataPropertiesAxiom(CollectionFactory.createSet(prop, pe))));
                    }
                }
            } else if (sect.equalsIgnoreCase(DOMAIN)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLClassExpression> domains = parseClassExpressionList();
                for (OWLOntology ont : onts) {
                    for (OWLClassExpression dom : domains) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLDataPropertyDomainAxiom(prop, dom)));
                    }
                }
            } else if (sect.equalsIgnoreCase(RANGE)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLDataRange> ranges = parseDataRangeList();
                for (OWLOntology ont : onts) {
                    for (OWLDataRange rng : ranges) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLDataPropertyRangeAxiom(prop, rng)));
                    }
                }
            } else if (sect.equalsIgnoreCase(CHARACTERISTICS)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                String characteristic = consumeToken();
                if (!characteristic.equals(FUNCTIONAL)) {
                    throwException(FUNCTIONAL);
                }
                for (OWLOntology ont : onts) {
                    axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLFunctionalDataPropertyAxiom(prop)));
                }
            } else if (sect.equalsIgnoreCase(ANNOTATIONS)) {
                potentialKeywords.clear();
                axioms.addAll(parseAnnotations(prop));
            } else {
                break;
            }
        }
        return axioms;
    }


    public Set<OntologyAxiomPair> parseIndividualFrame() throws ParserException {
        String tok = consumeToken();
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        if (!tok.equalsIgnoreCase(INDIVIDUAL)) {
            throwException(INDIVIDUAL);
        }
        String subj = consumeToken();
        OWLNamedIndividual ind = getOWLIndividual(subj);
        if(ind == null) {
            throwException(false, false, false, true);
        }
        while (true) {
            String sect = peekToken();
            if (sect.equalsIgnoreCase(TYPES)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLClassExpression> descs = parseClassExpressionList();
                for (OWLOntology ont : onts) {
                    for (OWLClassExpression desc : descs) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLClassAssertionAxiom(ind, desc)));
                    }
                }
            } else if (sect.equalsIgnoreCase(FACTS)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                String sep = ",";
                while (sep.equals(",")) {
                    boolean negative = false;
                    if (peekToken().equals(NOT)) {
                        consumeToken();
                        negative = true;
                    }
                    String prop = peekToken();
                    if (isDataPropertyName(prop)) {
                        OWLDataProperty p = parseDataProperty();
                        OWLLiteral con = parseConstant();
                        if (!negative) {
                            for (OWLOntology ont : onts) {
                                axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLDataPropertyAssertionAxiom(ind, p, con)));
                            }
                        } else {
                            for (OWLOntology ont : onts) {
                                axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLNegativeDataPropertyAssertionAxiom(ind, p, con)));
                            }
                        }
                    } else if (isObjectPropertyName(prop)) {
                        OWLObjectPropertyExpression p = parseObjectPropertyExpression();
                        OWLIndividual obj = parseIndividual();
                        for (OWLOntology ont : onts) {
                            if (!negative) {
                                axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLObjectPropertyAssertionAxiom(ind, p, obj)));
                            } else {
                                axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLNegativeObjectPropertyAssertionAxiom(ind, p, obj)));
                            }
                        }
                    } else if (isAnnotationPropertyName(prop)) {
                        OWLAnnotationProperty annotationProp = getAnnotationProperty(prop);
                        // Object could be a URI or literal
                        String object = peekToken();
                        OWLAnnotation annotation;
                        IRI iri = getIRI(object);
                        if (iri.toURI().isAbsolute()) {
                            annotation = dataFactory.getOWLAnnotation(annotationProp, iri);
                        } else {
                            // Assume constant
                            OWLLiteral con = null;
                            try {
                                con = parseConstant();
                            }
                            catch (ParserException e) {
                                throwException(e.isClassNameExpected(),
                                        e.isObjectPropertyNameExpected(),
                                        e.isDataPropertyNameExpected(),
                                        true,
                                        e.isDatatypeNameExpected(),
                                        e.isAnnotationPropertyNameExpected(),
                                        e.getExpectedKeywords().toArray(new String[e.getExpectedKeywords().size()]));
                            }
                            annotation = dataFactory.getOWLAnnotation(annotationProp, con);
                        }
                        for (OWLOntology ont : onts) {
                            axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLAnnotationAssertionAxiom(ind.getIRI(), annotation)));
                        }
                    } else {
                        consumeToken();
                        throwException(false, true, true, false, false, true, ",");
                    }
                    sep = peekToken();
                    if (sep.equals(",")) {
                        consumeToken();
                    }
                }
            } else if (sect.equalsIgnoreCase(SAME_AS)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLIndividual> inds = parseIndividualList();
                inds.add(ind);
                OWLAxiom ax = dataFactory.getOWLSameIndividualAxiom(inds);
                for (OWLOntology ont : onts) {
                    axioms.add(new OntologyAxiomPair(ont, ax));
                }
            } else if (sect.equalsIgnoreCase(DIFFERENT_FROM)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLIndividual> inds = parseIndividualList();
                inds.add(ind);
                OWLAxiom ax = dataFactory.getOWLDifferentIndividualsAxiom(inds);
                for (OWLOntology ont : onts) {
                    axioms.add(new OntologyAxiomPair(ont, ax));
                }
            } else if (sect.equalsIgnoreCase(ANNOTATIONS)) {
                potentialKeywords.clear();
                axioms.addAll(parseAnnotations(ind));
            } else {
//                // If force EOF then we need EOF or else everything is o.k.
//                if (eof && !sect.equals(EOF)) {
//                    throwException(SUB_CLASS_OF, EQUIVALENT_TO, DISJOINT_WITH);
//                }
//                else {
                break;
//                }
            }
        }
        return axioms;
    }


    public Set<OntologyAxiomPair> parseValuePartitionFrame() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(VALUE_PARTITION)) {
            throwException(VALUE_PARTITION);
        }
        Set<OWLOntology> onts = getOntologies();
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
        String clsName = consumeToken();
        if (clsName.equals(EOF)) {
            throwException(false, true, false, false, false, false);
        }
        OWLClass cls = getOWLClass(clsName);
        if (cls == null) {
            throwException(true, false, false, false);
        }


        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        axioms.addAll(parseValuePartitionValues(onts, cls));
        for (OWLOntology ont : onts) {
            axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLFunctionalObjectPropertyAxiom(prop)));
            axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLObjectPropertyRangeAxiom(prop, cls)));
        }

        return axioms;
    }


    public Set<OntologyAxiomPair> parseValuePartitionValues(Set<OWLOntology> onts, OWLClass superclass) throws ParserException {
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        Set<OWLClass> siblings = new HashSet<OWLClass>();
        String sep = ",";
        String open = consumeToken();
        if (!open.equals("[")) {
            throwException("[");
        }
        while (sep.equals(",")) {
            String clsName = consumeToken();
            OWLClass cls = getOWLClass(clsName);
            if (cls == null) {
                throwException(true, false, false, false);
            }
            siblings.add(cls);
            OWLSubClassOfAxiom ax = getDataFactory().getOWLSubClassOfAxiom(cls, superclass);
            for (OWLOntology ont : onts) {
                axioms.add(new OntologyAxiomPair(ont, ax));
            }
            if (peekToken().equals("[")) {
                axioms.addAll(parseValuePartitionValues(onts, cls));
            }
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        String close = consumeToken();
        if (!close.equals("]")) {
            throwException("]");
        }
        OWLAxiom ax = getDataFactory().getOWLDisjointClassesAxiom(siblings);
        for (OWLOntology ont : onts) {
            axioms.add(new OntologyAxiomPair(ont, ax));
        }
        return axioms;
    }

    public Set<OntologyAxiomPair> parseRuleFrame() throws ParserException {
        String section = consumeToken();
        if(!section.equalsIgnoreCase(RULE)) {
            throwException(RULE);
        }
        Set<OWLOntology> ontologies = getOntologies();
        List<SWRLAtom> body = parseRuleAtoms();
        String tok = consumeToken();
        if(!tok.equals("-")) {
            throwException("-", ",");
        }
        consumeToken(">");
        List<SWRLAtom> head = parseRuleAtoms();
        SWRLRule rule = dataFactory.getSWRLRule(new LinkedHashSet<SWRLAtom>(body), new LinkedHashSet<SWRLAtom>(head));
        Set<OntologyAxiomPair> pairs = new HashSet<OntologyAxiomPair>();
        for (OWLOntology ont : ontologies) {
            pairs.add(new OntologyAxiomPair(ont, rule));
        }
        return pairs;
    }

    public List<SWRLAtom> parseRuleAtoms() throws ParserException {
        String sep = ",";
        List<SWRLAtom> atoms = new ArrayList<SWRLAtom>();
        while(sep.equals(",")) {
            potentialKeywords.remove(",");
            SWRLAtom atom = parseRuleAtom();
            atoms.add(atom);
            sep = peekToken();
            if(sep.equals(",")) {
                consumeToken();
            }
            potentialKeywords.add(",");
        }
        return atoms;
    }


    public SWRLAtom parseRuleAtom() throws ParserException {
            String predicate = peekToken();
            if(isClassName(predicate)) {
                return parseClassAtom();
            }
            else if(isObjectPropertyName(predicate)) {
                return parseObjectPropertyAtom();
            }
            else if(isDataPropertyName(predicate)) {
                return parseDataPropertyAtom();

            }
            else if(isSWRLBuiltin(predicate)) {
                return parseBuiltInAtom();
            }
            else if(predicate.equals(ManchesterOWLSyntax.DIFFERENT_FROM.toString())) {
                return parseDifferentFromAtom();
            }
            else if(predicate.equals(ManchesterOWLSyntax.SAME_AS.toString())) {
                return parseSameAsAtom();
            }
            else {
                consumeToken();
                Set<String> kw = new TreeSet<String>();
                kw.addAll(ruleBuiltIns.keySet());
                kw.add(ManchesterOWLSyntax.DIFFERENT_FROM.toString());
                kw.add(ManchesterOWLSyntax.SAME_AS.toString());
                throwException(true, true, true, false, false, false, kw.toArray(new String [ruleBuiltIns.size()]));
            }
           return null;
    }

    public SWRLAtom parseDataPropertyAtom() throws ParserException {
        String predicate = consumeToken();
        if(!isDataPropertyName(predicate)) {
            throwException(false, false, true, false);
        }
        consumeToken("(");
        SWRLAtomIObject obj1 = parseIObject();
        consumeToken(",");
        SWRLAtomDObject obj2 = parseDObject();
        consumeToken(")");
        return dataFactory.getSWRLDataValuedPropertyAtom(getOWLDataProperty(predicate), obj1, obj2);
    }

    public SWRLAtom parseObjectPropertyAtom() throws ParserException {
        String predicate = consumeToken();
        if(!isObjectPropertyName(predicate)) {
            throwException(false, true, false, false);
        }
        consumeToken("(");
        SWRLAtomIObject obj1 = parseIObject();
        consumeToken(",");
        SWRLAtomIObject obj2 = parseIObject();
        consumeToken(")");
        return dataFactory.getSWRLObjectPropertyAtom(getOWLObjectProperty(predicate), obj1, obj2);
    }

    public SWRLAtom parseClassAtom() throws ParserException {
        String predicate = consumeToken();
        if(!isClassName(predicate)) {
            throwException(true, false, false, false);
        }
        consumeToken("(");
        SWRLAtomIObject obj = parseIObject();
        consumeToken(")");
        return dataFactory.getSWRLClassAtom(getOWLClass(predicate), obj);
    }

    public SWRLDifferentFromAtom parseDifferentFromAtom() throws ParserException {
        consumeToken(ManchesterOWLSyntax.DIFFERENT_FROM.toString());
        consumeToken("(");
        SWRLAtomIObject obj1 = parseIObject();
        consumeToken(",");
        SWRLAtomIObject obj2 = parseIObject();
        consumeToken(")");
        return dataFactory.getSWRLDifferentFromAtom(obj1, obj2);
    }

    public SWRLSameAsAtom parseSameAsAtom() throws ParserException {
        consumeToken(ManchesterOWLSyntax.SAME_AS.toString());
        consumeToken("(");
        SWRLAtomIObject obj1 = parseIObject();
        consumeToken(",");
        SWRLAtomIObject obj2 = parseIObject();
        consumeToken(")");
        return dataFactory.getSWRLSameAsAtom(obj1, obj2);
    }

    public SWRLAtomIObject parseIObject() throws ParserException {
        String s = peekToken();
        if(isIndividualName(s)) {
            return parseIIndividualObject();
        }
        else if(s.equals("?")) {
            return parseIVariable();
        }
        else {
            consumeToken();
            throwException(false, false, false, true, false, false, "?$var$");
        }
        return null;
    }

    public SWRLAtomIVariable parseIVariable() throws ParserException {
        String var = parseVariable();
        return dataFactory.getSWRLAtomIVariable(getURI(var));
    }

    public SWRLAtomIndividualObject parseIIndividualObject() throws ParserException {
        OWLIndividual ind = parseIndividual();
        return dataFactory.getSWRLAtomIndividualObject(ind);
    }

    public String parseVariable() throws ParserException {
        consumeToken("?");
        return consumeToken();
    }

    public SWRLAtomDObject parseDObject() throws ParserException {
        String s = peekToken();
        if(s.equals("?")) {
            return parseDVariable();
        }
        else {
            try {
                return parseLiteralObject();
            } catch (ParserException e) {
                Set<String> kw = new HashSet<String>(e.getExpectedKeywords());
                kw.add("?");
                throw new ParserException(e.getTokenSequence(), e.getStartPos(), e.getLineNumber(), e.getColumnNumber(), e.isClassNameExpected(),
                        e.isObjectPropertyNameExpected(), e.isDataPropertyNameExpected(), e.isIndividualNameExpected(), e.isDatatypeNameExpected(), e.isAnnotationPropertyNameExpected(), kw);
            }
        }
    }

    public SWRLAtomDVariable parseDVariable() throws ParserException {
        String var = parseVariable();
        return dataFactory.getSWRLAtomDVariable(getURI(var));
    }

    public SWRLAtomConstantObject parseLiteralObject() throws ParserException {
        OWLLiteral lit = parseConstant();
        return dataFactory.getSWRLAtomConstantObject(lit);
    }

    public SWRLBuiltInAtom parseBuiltInAtom() throws ParserException {
        String predicate = consumeToken();
        consumeToken("(");
        if(!ruleBuiltIns.containsKey(predicate)) {
            throwException(ruleBuiltIns.keySet().toArray(new String [ruleBuiltIns.size()]));
        }
        SWRLBuiltInsVocabulary v = ruleBuiltIns.get(predicate);
        List<SWRLAtomDObject> args = new ArrayList<SWRLAtomDObject>();
        for(int i = 0; i < v.getArity();i++) {
            SWRLAtomDObject obj = parseDObject();
            args.add(obj);
            if(i != v.getArity() - 1) {
                consumeToken(",");
            }
        }
        consumeToken(")");
        return dataFactory.getSWRLBuiltInAtom(v, args);
    }

    public OWLDisjointClassesAxiom parseDisjointClasses() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(DISJOINT_CLASSES)) {
            throwException(DISJOINT_CLASSES);
        }
        Set<OWLClassExpression> classExpressions = parseClassExpressionList();
        return getDataFactory().getOWLDisjointClassesAxiom(classExpressions);
    }

    public OWLSameIndividualAxiom parseSameIndividual() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(SAME_INDIVIDUAL)) {
            throwException(SAME_INDIVIDUAL);
        }
        Set<OWLIndividual> individuals = parseIndividualList();
        return getDataFactory().getOWLSameIndividualAxiom(individuals);
    }

    public OWLDisjointObjectPropertiesAxiom parseDisjointObjectProperties() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(DISJOINT_OBJECT_PROPERTIES)) {
            throwException(DISJOINT_OBJECT_PROPERTIES);
        }
        Set<OWLObjectPropertyExpression> props = parseObjectPropertyList();
        return getDataFactory().getOWLDisjointObjectPropertiesAxiom(props);
    }


    public OWLDisjointDataPropertiesAxiom parseDisjointDataProperties() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(DISJOINT_DATA_PROPERTIES)) {
            throwException(DISJOINT_DATA_PROPERTIES);
        }
        Set<OWLDataProperty> props = parseDataPropertyList();
        return getDataFactory().getOWLDisjointDataPropertiesAxiom(props);
    }


    public OWLDifferentIndividualsAxiom parseDifferentIndividuals() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(DIFFERENT_INDIVIDUALS)) {
            throwException(DIFFERENT_INDIVIDUALS);
        }
        Set<OWLIndividual> props = parseIndividualList();
        return getDataFactory().getOWLDifferentIndividualsAxiom(props);
    }


    public Set<OWLAxiom> parseObjectPropertyCharacteristicList(OWLObjectPropertyExpression prop) throws
            ParserException {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        String sep = ",";
        while (sep.equals(",")) {
            String characteristic = consumeToken();
            if (characteristic.equalsIgnoreCase(FUNCTIONAL)) {
                axioms.add(dataFactory.getOWLFunctionalObjectPropertyAxiom(prop));
            } else if (characteristic.equalsIgnoreCase(INVERSE_FUNCTIONAL)) {
                axioms.add(dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(prop));
            } else if (characteristic.equalsIgnoreCase(SYMMETRIC)) {
                axioms.add(dataFactory.getOWLSymmetricObjectPropertyAxiom(prop));
            } else if (characteristic.equalsIgnoreCase(ANTI_SYMMETRIC) || characteristic.equalsIgnoreCase(ASYMMETRIC)) {
                axioms.add(dataFactory.getOWLAsymmetricObjectPropertyAxiom(prop));
            } else if (characteristic.equalsIgnoreCase(TRANSITIVE)) {
                axioms.add(dataFactory.getOWLTransitiveObjectPropertyAxiom(prop));
            } else if (characteristic.equalsIgnoreCase(REFLEXIVE)) {
                axioms.add(dataFactory.getOWLReflexiveObjectPropertyAxiom(prop));
            } else if (characteristic.equalsIgnoreCase(IRREFLEXIVE)) {
                axioms.add(dataFactory.getOWLIrreflexiveObjectPropertyAxiom(prop));
            } else {
                throwException(FUNCTIONAL,
                        INVERSE_FUNCTIONAL,
                        SYMMETRIC,
                        ANTI_SYMMETRIC,
                        TRANSITIVE,
                        REFLEXIVE,
                        IRREFLEXIVE);
            }
            sep = peekToken();
            if (sep.equals(",")) {
                sep = consumeToken();
            }
        }
        return axioms;
    }


    public Set<OWLClassExpression> parseClassExpressionList() throws ParserException {
        Set<OWLClassExpression> descs = new HashSet<OWLClassExpression>();
        String sep = ",";
        while (sep.equals(",")) {
            potentialKeywords.remove(",");
            descs.add(parseIntersection());
            potentialKeywords.add(",");
            sep = peekToken();
            if (sep.equals(",")) {
                sep = consumeToken();
            }
        }
        return descs;
    }


    public Set<OWLClassExpression> parseClassExpressionList(String expectedOpen, String expectedClose) throws ParserException {
        String open = consumeToken();
        Set<OWLClassExpression> descs = new HashSet<OWLClassExpression>();
        if (!open.equals(expectedOpen)) {
            throwException(expectedOpen);
        }
        String sep = ",";
        while (sep.equals(",")) {
            potentialKeywords.remove(",");
            OWLClassExpression desc = parseIntersection();
            potentialKeywords.add(",");
            descs.add(desc);
            sep = peekToken();
            if (sep.equals(",")) {
                sep = consumeToken();
            }
        }
        String close = consumeToken();
        if (!close.equals(expectedClose)) {
            throwException(expectedClose);
        }
        return descs;
    }


    public Set<OWLDataProperty> parseDataPropertyList() throws ParserException {
        Set<OWLDataProperty> props = new HashSet<OWLDataProperty>();
        String sep = ",";
        while (sep.equals(",")) {
            sep = peekToken();
            OWLDataProperty prop = parseDataProperty();
            props.add(prop);
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        return props;
    }


    public Set<OWLObjectPropertyExpression> parseObjectPropertyList() throws ParserException {
        Set<OWLObjectPropertyExpression> props = new HashSet<OWLObjectPropertyExpression>();
        String sep = ",";
        while (sep.equals(",")) {
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            props.add(prop);
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        return props;
    }


    public Set<OWLIndividual> parseIndividualList() throws ParserException {
        Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
        String sep = ",";
        while (sep.equals(",")) {
            inds.add(parseIndividual());
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        return inds;
    }


    public List<OWLObjectPropertyExpression> parseObjectPropertyChain() throws ParserException {
        String delim = "o";
        List<OWLObjectPropertyExpression> properties = new ArrayList<OWLObjectPropertyExpression>();
        while (delim.equals("o")) {
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            properties.add(prop);
            delim = peekToken();
            if (delim.equals("o")) {
                consumeToken();
            }
        }
        return properties;
    }


    public OWLSubPropertyChainOfAxiom parsePropertyChainSubPropertyAxiom() throws ParserException {
        // Chain followed by subPropertyOf
        List<OWLObjectPropertyExpression> props = parseObjectPropertyChain();
        String imp = consumeToken() + consumeToken();
        if (!imp.equals("->")) {
            throwException("->", "o");
        }
        OWLObjectPropertyExpression superProp = parseObjectPropertyExpression();
        return dataFactory.getOWLSubPropertyChainOfAxiom(props, superProp);
    }


    public OWLClassAxiom parseClassAxiom() throws ParserException {
        OWLClassExpression lhs = parseIntersection();
        // subClassOf
        String kw = consumeToken();
        if (kw.equalsIgnoreCase(ManchesterOWLSyntax.SUBCLASS_OF.toString())) {
            OWLClassExpression rhs = parseIntersection();
            return dataFactory.getOWLSubClassOfAxiom(lhs, rhs);
        } else if (kw.equalsIgnoreCase(ManchesterOWLSyntax.EQUIVALENT_TO.toString())) {
            OWLClassExpression rhs = parseIntersection();
            return dataFactory.getOWLEquivalentClassesAxiom(lhs, rhs);
        } else if (kw.equalsIgnoreCase(ManchesterOWLSyntax.DISJOINT_WITH.toString())) {
            OWLClassExpression rhs = parseIntersection();
            return dataFactory.getOWLDisjointClassesAxiom(lhs, rhs);
        }
        throwException(ManchesterOWLSyntax.SUBCLASS_OF.toString(),
                ManchesterOWLSyntax.EQUIVALENT_TO.toString(),
                ManchesterOWLSyntax.DISJOINT_WITH.toString());
        return null;
    }


    public OWLObjectPropertyAxiom parseObjectPropertyAxiom() throws ParserException {

        String tok = peekToken();
        if (tok.equals(ManchesterOWLSyntax.FUNCTIONAL)) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throwException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throwException(")");
            }
            return dataFactory.getOWLFunctionalObjectPropertyAxiom(prop);
        } else if (tok.equals(ManchesterOWLSyntax.INVERSE_FUNCTIONAL)) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throwException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throwException(")");
            }
            return dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(prop);
        } else if (tok.equals(ManchesterOWLSyntax.TRANSITIVE)) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throwException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throwException(")");
            }
            return dataFactory.getOWLTransitiveObjectPropertyAxiom(prop);
        } else if (tok.equals(ManchesterOWLSyntax.SYMMETRIC)) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throwException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throwException(")");
            }
            return dataFactory.getOWLSymmetricObjectPropertyAxiom(prop);
        } else if (tok.equals(ManchesterOWLSyntax.REFLEXIVE)) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throwException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throwException(")");
            }
            return dataFactory.getOWLReflexiveObjectPropertyAxiom(prop);
        } else if (tok.equals(ManchesterOWLSyntax.IRREFLEXIVE)) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throwException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throwException(")");
            }
            return dataFactory.getOWLIrreflexiveObjectPropertyAxiom(prop);
        } else if (tok.equals(ManchesterOWLSyntax.ANTI_SYMMETRIC)) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throwException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throwException(")");
            }
            return dataFactory.getOWLAsymmetricObjectPropertyAxiom(prop);
        }
        return null;
    }


    public OWLIndividual parseIndividual() throws ParserException {
        String name = consumeToken();
        if (!isIndividualName(name)) {
            throwException(false, false, false, true);
        }
        return getOWLIndividual(name);
    }


    public OWLIndividual parseIndividual(boolean defined) throws ParserException {
        String name = consumeToken();
        if (defined && !isIndividualName(name)) {
            throwException(false, false, false, true);
        }
        return getOWLIndividual(name);
    }


    public OWLDataProperty parseDataProperty() throws ParserException {
        String name = consumeToken();
        if (!isDataPropertyName(name)) {
            throwException(false, false, true, false);
        }
        return getOWLDataProperty(name);
    }


    public Map<String, URI> parseNamespace() throws ParserException {
        String nsTok = consumeToken();
        if (!nsTok.equals(NAMESPACE)) {
            throwException(NAMESPACE);
        }
        // Namespaces are of the form
        //  prefix <URI> (legacy prefix '=' <URI>)
        // The prefix might be empty
        String tok = peekToken();
        Map<String, URI> map = new HashMap<String, URI>(2);
        if (tok.startsWith("<")) {
            // Default namespace
            URI uri = parseURI();
            map.put("", uri);
        } else {
            String prefix = consumeToken();
            // Handle legacy = character if necessart
            String delim = peekToken();
            if (delim.equals("=")) {
                consumeToken();
            }
            URI uri = parseURI();
            map.put(tok, uri);
        }
        return map;
    }


    public OWLImportsDeclaration parseImportsDeclaration(OWLOntology ont) throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(IMPORT)) {
            throwException(IMPORT);
        }
        URI importedOntologyURI = parseURI();
        return dataFactory.getOWLImportsDeclaration(importedOntologyURI);
    }


    public URI parseURI() throws ParserException {
        String open = consumeToken();
        if (!open.equals("<")) {
            throwException("<");
        }
        String uri = consumeToken();
        String close = consumeToken();
        if (!close.equals(">")) {
            throwException(">");
        }
        return URI.create(uri);
    }


    private void processDeclaredEntities() {
        for (int i = 0; i < tokens.size(); i++) {
            ManchesterOWLSyntaxTokenizer.Token tok = tokens.get(i);
            if (tok.getToken().equalsIgnoreCase(CLASS)) {
                if (i + 1 < tokens.size()) {
                    classNames.add(tokens.get(i + 1).getToken());
                }
            } else if (tok.getToken().equalsIgnoreCase(OBJECT_PROPERTY)) {
                if (i + 1 < tokens.size()) {
                    objectPropertyNames.add(tokens.get(i + 1).getToken());
                }
            } else if (tok.getToken().equalsIgnoreCase(DATA_PROPERTY)) {
                if (i + 1 < tokens.size()) {
                    dataPropertyNames.add(tokens.get(i + 1).getToken());
                }
            } else if (tok.getToken().equalsIgnoreCase(INDIVIDUAL)) {
                if (i + 1 < tokens.size()) {
                    individualNames.add(tokens.get(i + 1).getToken());
                }
            } else if (tok.getToken().equalsIgnoreCase(VALUE_PARTITION)) {
                if (i + 1 < tokens.size()) {
                    objectPropertyNames.add(tokens.get(i + 1).getToken());
                }
                if (i + 2 < tokens.size()) {
                    classNames.add(tokens.get(i + 2).getToken());
                }
            }
        }
    }


    private static void addNamesToSet(String buffer, String sectionName, Set<String> names) {
        Pattern p = Pattern.compile("(" + sectionName + "\\s*)(\\S*)");
        Matcher matcher = p.matcher(buffer);
        while (matcher.find()) {
            names.add(matcher.group(2));
        }
    }


    public void parseOntology(OWLOntologyManager manager, OWLOntology ont) throws ParserException,
            OWLOntologyCreationException,
            OWLOntologyChangeException {
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        Set<AddImport> imports = new HashSet<AddImport>();
        this.defaultOntology = ont;
        URI ontologyURI = null;
        processDeclaredEntities();
        while (true) {
            String section = peekToken();
            if (ontologyURI == null && section.equals(ONTOLOGY)) {
                // Consume ontology header token
                consumeToken();
                ontologyURI = parseURI();
                setBase(ontologyURI + "#");
                // Annotations?
                while (peekToken().equals(ANNOTATIONS)) {
                    axioms.addAll(parseAnnotations(ontologyURI));
                }
            } else if (section.equalsIgnoreCase(CLASS)) {
                axioms.addAll(parseClassFrame());
            } else if (section.equalsIgnoreCase(OBJECT_PROPERTY)) {
                axioms.addAll(parseObjectPropertyFrame());
            } else if (section.equalsIgnoreCase(DATA_PROPERTY)) {
                axioms.addAll(parseDataPropertyFrame());
            } else if (section.equalsIgnoreCase(INDIVIDUAL)) {
                axioms.addAll(parseIndividualFrame());
            } else if (section.equalsIgnoreCase(VALUE_PARTITION)) {
                axioms.addAll(parseValuePartitionFrame());
            } else if (section.equalsIgnoreCase(IMPORT)) {
                OWLImportsDeclaration decl = parseImportsDeclaration(ont);
                imports.add(new AddImport(ont, decl));
                manager.makeLoadImportRequest(decl);
            } else if (section.equalsIgnoreCase(NAMESPACE)) {
                Map<String, URI> nsMap = parseNamespace();
                for (String ns : nsMap.keySet()) {
                    namespaceMap.put(ns, nsMap.get(ns).toString());
                }
            } else if (section.equalsIgnoreCase(DISJOINT_CLASSES)) {
                axioms.add(new OntologyAxiomPair(ont, parseDisjointClasses()));
            } else if (section.equalsIgnoreCase(DISJOINT_OBJECT_PROPERTIES)) {
                axioms.add(new OntologyAxiomPair(ont, parseDisjointObjectProperties()));
            } else if (section.equalsIgnoreCase(DISJOINT_DATA_PROPERTIES)) {
                axioms.add(new OntologyAxiomPair(ont, parseDisjointDataProperties()));
            } else if (section.equalsIgnoreCase(DIFFERENT_INDIVIDUALS)) {
                axioms.add(new OntologyAxiomPair(ont, parseDifferentIndividuals()));
            } else if (section.equalsIgnoreCase(SAME_INDIVIDUAL)) {
                axioms.add(new OntologyAxiomPair(ont, parseSameIndividual()));
            } else if (section.equals(EOF)) {
                break;
            } else {
                consumeToken();
                throwException(CLASS,
                        OBJECT_PROPERTY,
                        DATA_PROPERTY,
                        INDIVIDUAL, IMPORT,
                        VALUE_PARTITION,
                        NAMESPACE,
                        DISJOINT_CLASSES,
                        DISJOINT_OBJECT_PROPERTIES,
                        DISJOINT_DATA_PROPERTIES,
                        DIFFERENT_INDIVIDUALS,
                        SAME_INDIVIDUAL);
            }
        }

        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>(axioms.size());
        for (OntologyAxiomPair pair : axioms) {
            changes.add(new AddAxiom(ont, pair.getAxiom()));
        }
        changes.add(new SetOntologyURI(ont, ontologyURI));
        manager.applyChanges(changes);
    }

    protected void throwException(boolean ontologyNameExpected) throws ParserException {
        ManchesterOWLSyntaxTokenizer.Token lastToken = getLastToken();
        throw new ParserException(getTokenSequence(),
                lastToken.getPos(),
                lastToken.getRow(),
                lastToken.getCol(), ontologyNameExpected);
    }

    protected void throwException(String... keywords) throws ParserException {
        Set<String> theKeywords = new HashSet<String>();
        theKeywords.addAll(Arrays.asList(keywords));
        theKeywords.addAll(potentialKeywords);
        potentialKeywords.clear();
        ManchesterOWLSyntaxTokenizer.Token lastToken = getLastToken();
        throw new ParserException(getTokenSequence(),
                lastToken.getPos(),
                lastToken.getRow(),
                lastToken.getCol(),
                false,
                false,
                false,
                false,
                false,
                false,
                theKeywords);
    }


    protected void throwException(boolean classNameExpected, boolean objectPropertyNameExpected,
                                  boolean dataPropertyNameExpected, boolean individualNameExpected,
                                  boolean datatypeNameExpected,
                                  boolean annotationPropertyNameExpected,
                                  String... keywords) throws ParserException {
        Set<String> theKeywords = new HashSet<String>();
        theKeywords.addAll(Arrays.asList(keywords));
        if (objectPropertyNameExpected) {
            theKeywords.add(INV);
        }
        theKeywords.addAll(potentialKeywords);
        potentialKeywords.clear();
        ManchesterOWLSyntaxTokenizer.Token lastToken = getLastToken();
        throw new ParserException(getTokenSequence(),
                lastToken.getPos(),
                lastToken.getRow(),
                lastToken.getCol(),
                classNameExpected,
                objectPropertyNameExpected,
                dataPropertyNameExpected,
                individualNameExpected,
                datatypeNameExpected,
                annotationPropertyNameExpected,
                theKeywords);
    }


    protected void throwException(boolean classNameExpected, boolean objectPropertyNameExpected,
                                  boolean dataPropertyNameExpected, boolean individualNameExpected) throws
            ParserException {
        Set<String> keywords = new HashSet<String>();
        if (objectPropertyNameExpected) {
            keywords.add(INV);
        }
        keywords.addAll(potentialKeywords);
        potentialKeywords.clear();
        ManchesterOWLSyntaxTokenizer.Token lastToken = getLastToken();
        throw new ParserException(getTokenSequence(),
                lastToken.getPos(),
                lastToken.getRow(),
                lastToken.getCol(),
                classNameExpected,
                objectPropertyNameExpected,
                dataPropertyNameExpected,
                individualNameExpected,
                false,
                false,
                keywords);
    }


    protected List<String> getTokenSequence() {
        List<String> seq = new ArrayList<String>();
        int index = tokenIndex - 1;
        if(index < 0) {
            index = 0;
        }
        while(index < tokens.size() && seq.size() < 4 && seq.indexOf(EOF) == -1) {
            seq.add(tokens.get(index).getToken());
            index++;
        }
        if(seq.size() == 0) {
            seq.add(EOF);
        }
        return seq;
    }

    private class DefaultEntityChecker implements OWLEntityChecker {

        private Map<String, OWLDatatype> dataTypeNameMap;


        public DefaultEntityChecker() {
            dataTypeNameMap = new HashMap<String, OWLDatatype>();
            for (XSDVocabulary v : XSDVocabulary.values()) {
                dataTypeNameMap.put(v.getURI().getFragment(), dataFactory.getOWLDatatype(v.getURI()));
                dataTypeNameMap.put("xsd:" + v.getURI().getFragment(), dataFactory.getOWLDatatype(v.getURI()));
            }
        }


        public OWLClass getOWLClass(String name) {
            if (name.equals("Thing") || name.equals("owl:Thing")) {
                return dataFactory.getOWLThing();
            } else if (name.equals("Nothing") || name.equals("owl:Nothing")) {
                return dataFactory.getOWLNothing();
            } else if (classNames.contains(name)) {
                return dataFactory.getOWLClass(getURI(name));
            } else {
                return null;
            }
        }


        public OWLObjectProperty getOWLObjectProperty(String name) {
            if (objectPropertyNames.contains(name)) {
                return dataFactory.getOWLObjectProperty(getURI(name));
            } else {
                return null;
            }
        }


        public OWLDataProperty getOWLDataProperty(String name) {
            if (dataPropertyNames.contains(name)) {
                return dataFactory.getOWLDataProperty(getURI(name));
            } else {
                return null;
            }
        }


        public OWLNamedIndividual getOWLIndividual(String name) {
            if (individualNames.contains(name)) {
                return dataFactory.getOWLNamedIndividual(getURI(name));
            } else {
                return null;
            }
        }


        public OWLDatatype getOWLDatatype(String name) {
            return dataTypeNameMap.get(name);
        }

        public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
            if (annotationPropertyNames.contains(name)) {
                return dataFactory.getOWLAnnotationProperty(getURI(name));
            } else {
                return null;
            }
        }
    }


    private Map<String, URI> nameURIMap = new HashMap<String, URI>();

    public IRI getIRI(String name) {
        return getDataFactory().getIRI(getURI(name));
    }

    public URI getURI(String name) {
        URI uri = nameURIMap.get(name);
        if (uri != null) {
            return uri;
        }
        int colonIndex = name.indexOf(':');
        if (colonIndex != -1) {
            String prefix = name.substring(0, colonIndex);
            String ns = namespaceMap.get(prefix);
            if (ns != null) {
                uri = URI.create(ns + name.substring(colonIndex + 1, name.length()));
                nameURIMap.put(name, uri);
                return uri;
            }
        }
        uri = URI.create(base + name);
        nameURIMap.put(name, uri);
        return uri;
    }
}
