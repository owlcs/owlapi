package org.coode.manchesterowlsyntax;

import org.semanticweb.owl.expression.OWLEntityChecker;
import org.semanticweb.owl.expression.ParserException;
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
 * expected that hasPart will have been defined at the top of the file before it is used in any class descriptions or
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

    private final String EOF = "<EOF>";

    //private Set<Character> skip = new HashSet<Character>();

    //private Set<Character> delims = new HashSet<Character>();

    //private Set<Character> escapeChars = new HashSet<Character>();

    private String base;

    private Set<String> classNames;

    private Set<String> objectPropertyNames;

    private Set<String> dataPropertyNames;

    private Set<String> individualNames;

    private Set<String> dataTypeNames;

    private Set<String> annotationURIs;

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

    private Set<String> potentialKeywords;

    private static final Set<String> FRAME_TOKENS = Collections.unmodifiableSet(CollectionFactory.createSet(CLASS,
            OBJECT_PROPERTY,
            DATA_PROPERTY,
            INDIVIDUAL,
            VALUE_PARTITION,
            DISJOINT_CLASSES,
            DISJOINT_DATA_PROPERTIES,
            DISJOINT_OBJECT_PROPERTIES,
            DIFFERENT_INDIVIDUALS));


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
        annotationURIs = new HashSet<String>();
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
            annotationURIs.add(u.getPrefix(res[0]) + ":" + res[1]);
        }
        for (DublinCoreVocabulary v : DublinCoreVocabulary.values()) {
            annotationURIs.add(v.getQName());
        }
        buffer = s;
        base = "http://www.semanticweb.org#";
        owlEntityChecker = new DefaultEntityChecker();
        tokens = new ArrayList<ManchesterOWLSyntaxTokenizer.Token>();
        ManchesterOWLSyntaxTokenizer tokenizer = new ManchesterOWLSyntaxTokenizer(buffer);
        tokens.addAll(tokenizer.tokenize());
        tokenIndex = 0;
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


    public boolean isClassName(String name) {
        if (classNames.contains(name)) {
            return true;
        }
        return owlEntityChecker != null && owlEntityChecker.getOWLClass(name) != null;
    }


    public boolean isObjectPropertyName(String name) {
        if (objectPropertyNames.contains(name)) {
            return true;
        }
        return owlEntityChecker != null && owlEntityChecker.getOWLObjectProperty(name) != null;
    }


    public boolean isAnnotationURI(String name) {
        return annotationURIs.contains(name);
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


    public OWLClass getOWLClass(String name) {
        OWLClass cls = owlEntityChecker.getOWLClass(name);
        if (cls == null && objectPropertyNames.contains(name)) {
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
        if (ind == null && objectPropertyNames.contains(name)) {
            ind = getDataFactory().getOWLIndividual(getURI(name));
        }
        return ind;
    }


    public OWLDataProperty getOWLDataProperty(String name) {
        OWLDataProperty prop = owlEntityChecker.getOWLDataProperty(name);
        if (prop == null && objectPropertyNames.contains(name)) {
            prop = getDataFactory().getOWLDataProperty(getURI(name));
        }
        return prop;
    }


    public OWLDatatype getDatatype(String name) {
        if (name.startsWith("xsd:")) {
            return dataFactory.getDatatype(URI.create(Namespaces.XSD + name.substring(name.indexOf(':') + 1)));
        } else {
            return dataFactory.getDatatype(URI.create(Namespaces.XSD + name));
        }
    }


    public URI getAnnotationURI(String name) {
        return getURI(name);
    }


    private ManchesterOWLSyntaxTokenizer.Token getLastToken() {
        if (tokenIndex - 1 > -1) {
            return tokens.get(tokenIndex - 1);
        } else {
            return tokens.get(0);
        }
    }


    private String peekToken() {
        return tokens.get(tokenIndex).getToken();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Tokenizer
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////


    protected String consumeToken() {
        String token = tokens.get(tokenIndex).getToken();
        tokenIndex++;
        if (tokenIndex == tokens.size()) {
            tokenIndex--;
        }
        return token;
    }


    public ManchesterOWLSyntaxTokenizer.Token getToken() {
        return tokens.get(tokenIndex);
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
     * Parses an OWL description that is represented in Manchester OWL Syntax
     *
     * @return The parsed description
     * @throws ParserException If an description could not be parsed.
     */
    public OWLClassExpression parseDescription() throws ParserException {
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
            return dataFactory.getObjectIntersectionOf(ops);
        }
    }


    public OWLClassExpression parseUnion() throws ParserException {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        String kw = OR;
        while (kw.equalsIgnoreCase(OR)) {
            potentialKeywords.remove(OR);
            ops.add(parseNonNaryDescription());
            potentialKeywords.add(OR);
            kw = peekToken();
            if (kw.equalsIgnoreCase(OR)) {
                kw = consumeToken();
            }
        }
        if (ops.size() == 1) {
            return ops.iterator().next();
        } else {
            return dataFactory.getObjectUnionOf(ops);
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
                throwException(false, true, false, false, false, INV);
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
     * Parses all class descriptions except ObjectIntersectionOf and ObjectUnionOf
     *
     * @return The description which was parsed
     * @throws ParserException if a non-nary description could not be parsed
     */
    public OWLClassExpression parseNonNaryDescription() throws ParserException {

        String tok = peekToken();
        if (tok.equalsIgnoreCase(NOT)) {
            consumeToken();
            OWLClassExpression complemented = parseNestedClassExpression(false);
            return dataFactory.getObjectComplementOf(complemented);
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
            throwException(true, true, true, false, false, "(", "{", NOT, INV);
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
                return dataFactory.getObjectHasSelf(prop);
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
                            keywords.toArray(new String[keywords.size()]));
                }
                return dataFactory.getObjectSomeValuesFrom(prop, filler);
            }
        } else if (kw.equalsIgnoreCase(ONLY)) {
            OWLClassExpression filler = parseNestedClassExpression(false);
            return dataFactory.getObjectAllValuesFrom(prop, filler);
        } else if (kw.equalsIgnoreCase(VALUE)) {
            String indName = consumeToken();
            if (!isIndividualName(indName)) {
                throwException(false, false, false, true);
            }
            return dataFactory.getObjectHasValue(prop, getOWLIndividual(indName));
        } else if (kw.equalsIgnoreCase(MIN)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            if (filler != null) {
                return dataFactory.getObjectMinCardinality(prop, card, filler);
            } else {
                return dataFactory.getObjectMinCardinality(prop, card);
            }
        } else if (kw.equalsIgnoreCase(MAX)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            if (filler != null) {
                return dataFactory.getObjectMaxCardinality(prop, card, filler);
            } else {
                return dataFactory.getObjectMaxCardinality(prop, card);
            }
        } else if (kw.equalsIgnoreCase(EXACTLY)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            if (filler != null) {
                return dataFactory.getObjectExactCardinality(prop, card, filler);
            } else {
                return dataFactory.getObjectExactCardinality(prop, card);
            }
        } else if (kw.equalsIgnoreCase(ONLYSOME)) {
            String tok = peekToken();
            Set<OWLClassExpression> descs = new HashSet<OWLClassExpression>();
            if (!tok.equals("[")) {
                descs.add(parseIntersection());
            } else {
                descs.addAll(parseDescriptionList("[", "]"));
            }
            Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
            for (OWLClassExpression desc : descs) {
                ops.add(dataFactory.getObjectSomeValuesFrom(prop, desc));
            }
            OWLClassExpression filler;
            if (descs.size() == 1) {
                filler = descs.iterator().next();
            } else {
                filler = dataFactory.getObjectUnionOf(descs);
            }
            ops.add(dataFactory.getObjectAllValuesFrom(prop, filler));
            return dataFactory.getObjectIntersectionOf(ops);
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
            return dataFactory.getDataSomeValuesFrom(prop, rng);
        } else if (kw.equalsIgnoreCase(ONLY)) {
            OWLDataRange rng = parseDataRange(false);
            return dataFactory.getDataAllValuesFrom(prop, rng);
        } else if (kw.equalsIgnoreCase(VALUE)) {
            OWLLiteral con = parseConstant();
            return dataFactory.getDataHasValue(prop, con);
        } else if (kw.equalsIgnoreCase(MIN)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataRange(true);
            if (rng != null) {
                return dataFactory.getDataMinCardinality(prop, card, rng);
            } else {
                return dataFactory.getDataMinCardinality(prop, card);
            }
        } else if (kw.equalsIgnoreCase(EXACTLY)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataRange(true);
            if (rng != null) {
                return dataFactory.getDataExactCardinality(prop, card, rng);
            } else {
                return dataFactory.getDataExactCardinality(prop, card, rng);
            }
        } else if (kw.equalsIgnoreCase(MAX)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataRange(true);
            if (rng != null) {
                return dataFactory.getDataMaxCardinality(prop, card, rng);
            } else {
                return dataFactory.getDataMaxCardinality(prop, card, rng);
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
                        con = dataFactory.getTypedLiteral(con.getString());
                    }
                    facetRestrictions.add(dataFactory.getFacetRestriction(fv, con.asOWLTypedLiteral()));
                    sep = consumeToken();
                }
                if (!sep.equals("]")) {
                    throwException("]");
                }
                return dataFactory.getDatatypeRestriction(datatype, facetRestrictions);
            } else {
                return datatype;
            }
        } else if (tok.equalsIgnoreCase(NOT)) {
            return parseDataComplementOf();
        } else if (tok.equals("{")) {
            return parseDataOneOf();
        } else if (!allowLookahead) {
            consumeToken();
            throwException(false, false, false, false, true, NOT, "{");
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
        return dataFactory.getDataOneOf(cons);
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
        return dataFactory.getDataComplementOf(complementedDataRange);
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
                String dataType = consumeToken();
                return dataFactory.getTypedLiteral(lit, getDatatype(dataType));
            } else if (peekToken().equals("@")) {
                consumeToken();
                String lang = consumeToken();
                return dataFactory.getRDFTextLiteral(lit, lang);
            } else {
                return dataFactory.getTypedLiteral(lit);
            }
        } else {
            try {
                int i = Integer.parseInt(tok);
                return dataFactory.getTypedLiteral(i);
            }
            catch (NumberFormatException e) {
                // Ignore - not interested
            }
            if (tok.endsWith("f")) {
                try {
                    float f = Float.parseFloat(tok);
                    return dataFactory.getTypedLiteral(f);
                }
                catch (NumberFormatException e) {
                    // Ignore - not interested
                }
            }
            try {
                double d = Double.parseDouble(tok);
                return dataFactory.getTypedLiteral(tok, dataFactory.getDatatype(XSDVocabulary.DOUBLE.getURI()));
//                return dataFactory.getTypedLiteral(d);
            }
            catch (NumberFormatException e) {
                // Ignore - not interested
            }

            if (tok.equals("true")) {
                return dataFactory.getTypedLiteral(true);
            } else if (tok.equals("false")) {
                return dataFactory.getTypedLiteral(false);
            }
        }
        throwException(false,
                false,
                false,
                false,
                false,
                "true",
                "false",
                "<integer>",
                "<float>",
                "<double>",
                "\"<Literal>\"",
                "\"<Literal>\"^^<datatype>",
                "\"<Literal>\"@<lang>");
        return null;
    }


    public int parseInteger() throws ParserException {
        String i = consumeToken();
        try {
            return Integer.parseInt(i);
        }
        catch (NumberFormatException e) {
            throw new ParserException(getToken().getToken(), getTokenPos(), getTokenRow(), true, getTokenCol());
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
            throwException(true, false, false, false, false, "(", "{");
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
        return dataFactory.getObjectOneOf(inds);
    }


    public Set<OWLAxiom> parseFrames() throws ParserException {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        while (true) {
            String tok = peekToken();
            if (tok.equalsIgnoreCase(CLASS)) {
                axioms.addAll(parseClassFrame());
            } else if (tok.equalsIgnoreCase(OBJECT_PROPERTY)) {
                axioms.addAll(parseObjectPropertyFrame());
            } else if (tok.equalsIgnoreCase(DATA_PROPERTY)) {
                axioms.addAll(parseDataPropertyFrame());
            } else if (tok.equalsIgnoreCase(INDIVIDUAL)) {
                axioms.addAll(parseIndividualFrame());
            } else if (tok.equalsIgnoreCase(VALUE_PARTITION)) {
                parseValuePartitionFrame();
            } else {
                if (tok.equals(EOF)) {
                    break;
                } else {
                    throwException(CLASS, OBJECT_PROPERTY, DATA_PROPERTY, INDIVIDUAL, VALUE_PARTITION);
                }
            }
        }
        return axioms;
    }


    public Set<OWLAnnotation> parseAnnotations() throws ParserException {
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        String header = consumeToken();
        if (!header.equals(ANNOTATIONS)) {
            throwException(ANNOTATIONS);
        }
        String sep = ",";
        while (sep.equals(",")) {
            String prop = consumeToken();
            if (!isAnnotationURI(prop)) {
                throwException(annotationURIs.toArray(new String[annotationURIs.size()]));
            }
            OWLAnnotationProperty annoProp = getDataFactory().getAnnotationProperty(getURI(prop));
            String obj = peekToken();
            if (isIndividualName(obj) || isClassName(obj) || isObjectPropertyName(obj) || isDataPropertyName(obj)) {
                URI uri = getURI(obj);
                OWLAnnotation anno = dataFactory.getAnnotation(annoProp, uri);
                annos.add(anno);
            } else {
                OWLLiteral con = parseConstant();
                OWLAnnotation anno = dataFactory.getAnnotation(annoProp, con);
                annos.add(anno);
            }
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        return annos;
    }


    public Set<OWLAxiom> parseAnnotations(OWLEntity subject) throws ParserException {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        for (OWLAnnotation anno : parseAnnotations()) {
            axioms.add(dataFactory.getAnnotationAssertion(subject.getURI(), anno));
        }
        return axioms;
    }


    public Set<OWLAxiom> parseClassFrame() throws ParserException {
        return parseClassFrame(false);
    }


    public Set<OWLAxiom> parseClassFrameEOF() throws ParserException {
        return parseClassFrame(true);
    }


    private Set<OWLAxiom> parseClassFrame(boolean eof) throws ParserException {
        String tok = consumeToken();
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        if (!tok.equalsIgnoreCase(CLASS)) {
            throwException(CLASS);
        }
        String subj = consumeToken();
        if (!isClassName(subj)) {
            throwException(true, false, false, false);
        }
        OWLClass cls = getOWLClass(subj);
        while (true) {
            String sect = peekToken();
            if (sect.equalsIgnoreCase(SUB_CLASS_OF)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLClassExpression> descs = parseDescriptionList();
                for (OWLClassExpression desc : descs) {
                    axioms.add(dataFactory.getSubClassOf(cls, desc));
                }
            } else if (sect.equalsIgnoreCase(EQUIVALENT_TO)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLClassExpression> descs = parseDescriptionList();
                for (OWLClassExpression desc : descs) {
                    axioms.add(dataFactory.getEquivalentClasses(CollectionFactory.createSet(cls, desc)));
                }
            } else if (sect.equalsIgnoreCase(DISJOINT_WITH)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLClassExpression> descs = parseDescriptionList();
                for (OWLClassExpression desc : descs) {
                    axioms.add(dataFactory.getDisjointClasses(cls, desc));
                }
            } else if (sect.equalsIgnoreCase(DISJOINT_UNION_OF)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLClassExpression> descs = parseDescriptionList();
                axioms.add(dataFactory.getDisjointUnion(cls, descs));
            } else if (sect.equals(ANNOTATIONS)) {
                potentialKeywords.clear();
                axioms.addAll(parseAnnotations(cls));
            } else {
                // If force EOF then we need EOF or else everything is o.k.
                if (eof && !sect.equals(EOF)) {
                    throwException(SUB_CLASS_OF, EQUIVALENT_TO, DISJOINT_WITH, ANNOTATIONS);
                } else {
//                    if(!eof && !sect.equals(EOF) && !FRAME_TOKENS.contains(sect)) {
//                        // Some garbage
//                        // We can throw an exception with the expected frame keywords
//                        Set<String> kw = new HashSet<String>();
//                        kw.addAll(FRAME_TOKENS);
//                        kw.add(SUB_CLASS_OF);
//                        kw.add(EQUIVALENT_TO);
//                        kw.add(DISJOINT_WITH);
//                        kw.add(ANNOTATIONS);
//                        throwException(kw.toArray(new String [0]));
//                    }
                    break;
                }
            }
        }
        return axioms;
    }


    public Set<OWLAxiom> parseObjectPropertyFrame() throws ParserException {
        return parseObjectPropertyFrame(false);
    }


    public Set<OWLAxiom> parseObjectPropertyFrame(boolean eof) throws ParserException {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLObjectPropertyExpression prop = null;
        String tok = consumeToken();
        if (!tok.equalsIgnoreCase(OBJECT_PROPERTY)) {
            throwException(OBJECT_PROPERTY);
        }
        String subj = peekToken();
        objectPropertyNames.add(subj);
        prop = parseObjectPropertyExpression();
        while (true) {
            String sect = peekToken();
            if (sect.equalsIgnoreCase(SUB_PROPERTY_OF)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLObjectPropertyExpression> props = parseObjectPropertyList();
                for (OWLObjectPropertyExpression pe : props) {
                    axioms.add(dataFactory.getSubObjectPropertyOf(prop, pe));
                }
            } else if (sect.equalsIgnoreCase(EQUIVALENT_TO)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLObjectPropertyExpression> props = parseObjectPropertyList();
                for (OWLObjectPropertyExpression pe : props) {
                    axioms.add(dataFactory.getEquivalentObjectProperties(CollectionFactory.createSet(prop,
                            pe)));
                }
            } else if (sect.equalsIgnoreCase(DISJOINT_WITH)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLObjectPropertyExpression> props = parseObjectPropertyList();
                for (OWLObjectPropertyExpression pe : props) {
                    axioms.add(dataFactory.getDisjointObjectProperties(CollectionFactory.createSet(prop, pe)));
                }
            } else if (sect.equalsIgnoreCase(DOMAIN)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLClassExpression> domains = parseDescriptionList();
                for (OWLClassExpression dom : domains) {
                    axioms.add(dataFactory.getObjectPropertyDomain(prop, dom));
                }
            } else if (sect.equalsIgnoreCase(RANGE)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLClassExpression> ranges = parseDescriptionList();
                for (OWLClassExpression rng : ranges) {
                    axioms.add(dataFactory.getObjectPropertyRange(prop, rng));
                }
            } else if (sect.equalsIgnoreCase(INVERSES) || sect.equalsIgnoreCase(INVERSE_OF)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLObjectPropertyExpression> inverses = parseObjectPropertyList();
                for (OWLObjectPropertyExpression inv : inverses) {
                    axioms.add(dataFactory.getInverseObjectProperties(prop, inv));
                }
            } else if (sect.equalsIgnoreCase(CHARACTERISTICS)) {
                potentialKeywords.clear();
                consumeToken();
                axioms.addAll(parseObjectPropertyCharacteristicList(prop));
            } else if (sect.equalsIgnoreCase(ANNOTATIONS)) {
                potentialKeywords.clear();
                axioms.addAll(parseAnnotations(prop.asOWLObjectProperty()));
            } else if (sect.equalsIgnoreCase(SUB_PROPERTY_CHAIN)) {
                potentialKeywords.clear();
                consumeToken();
                List<OWLObjectPropertyExpression> props = parseObjectPropertyChain();
                axioms.add(dataFactory.getObjectPropertyChainSubProperty(props, prop));
            } else {
                // If force EOF then we need EOF or else everything is o.k.
                if (eof && !sect.equals(EOF)) {
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


    public Set<OWLAxiom> parseDataPropertyFrame() throws ParserException {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        String tok = consumeToken();
        if (!tok.equalsIgnoreCase(DATA_PROPERTY)) {
            throwException(DATA_PROPERTY);
        }
        String subj = consumeToken();
        dataPropertyNames.add(subj);
        OWLDataProperty prop = getOWLDataProperty(subj);
        while (true) {
            String sect = peekToken();
            if (sect.equalsIgnoreCase(SUB_PROPERTY_OF)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLDataProperty> props = parseDataPropertyList();
                for (OWLDataProperty pe : props) {
                    axioms.add(dataFactory.getSubDataPropertyOf(prop, pe));
                }
            } else if (sect.equalsIgnoreCase(EQUIVALENT_TO)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLDataProperty> props = parseDataPropertyList();
                for (OWLDataProperty pe : props) {
                    axioms.add(dataFactory.getEquivalentDataProperties(CollectionFactory.createSet(prop, pe)));
                }
            } else if (sect.equalsIgnoreCase(DISJOINT_WITH)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLDataProperty> props = parseDataPropertyList();
                for (OWLDataProperty pe : props) {
                    axioms.add(dataFactory.getDisjointDataProperties(CollectionFactory.createSet(prop, pe)));
                }
            } else if (sect.equalsIgnoreCase(DOMAIN)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLClassExpression> domains = parseDescriptionList();
                for (OWLClassExpression dom : domains) {
                    axioms.add(dataFactory.getDataPropertyDomain(prop, dom));
                }
            } else if (sect.equalsIgnoreCase(RANGE)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLDataRange> ranges = parseDataRangeList();
                for (OWLDataRange rng : ranges) {
                    axioms.add(dataFactory.getDataPropertyRange(prop, rng));
                }
            } else if (sect.equalsIgnoreCase(CHARACTERISTICS)) {
                potentialKeywords.clear();
                consumeToken();
                String characteristic = consumeToken();
                if (!characteristic.equals(FUNCTIONAL)) {
                    throwException(FUNCTIONAL);
                }
                axioms.add(dataFactory.getFunctionalDataProperty(prop));
            } else if (sect.equalsIgnoreCase(ANNOTATIONS)) {
                potentialKeywords.clear();
                axioms.addAll(parseAnnotations(prop));
            } else {
                break;
            }
        }
        return axioms;
    }


    public Set<OWLAxiom> parseIndividualFrame() throws ParserException {
        String tok = consumeToken();
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        if (!tok.equalsIgnoreCase(INDIVIDUAL)) {
            throwException(INDIVIDUAL);
        }
        String subj = consumeToken();
        OWLNamedIndividual ind = getOWLIndividual(subj);
        while (true) {
            String sect = peekToken();
            if (sect.equalsIgnoreCase(TYPES)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLClassExpression> descs = parseDescriptionList();
                for (OWLClassExpression desc : descs) {
                    axioms.add(dataFactory.getClassAssertion(ind, desc));
                }
            } else if (sect.equalsIgnoreCase(FACTS)) {
                potentialKeywords.clear();
                consumeToken();
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
                            axioms.add(dataFactory.getDataPropertyAssertion(ind, p, con));
                        } else {
                            axioms.add(dataFactory.getNegativeDataPropertyAssertion(ind, p, con));
                        }
                    } else if (isObjectPropertyName(prop)) {
                        OWLObjectPropertyExpression p = parseObjectPropertyExpression();
                        OWLIndividual obj = parseIndividual();
                        if (!negative) {
                            axioms.add(dataFactory.getObjectPropertyAssertion(ind, p, obj));
                        } else {
                            axioms.add(dataFactory.getNegativeObjectPropertyAssertion(ind, p, obj));
                        }
                    } else if (isAnnotationURI(prop)) {
                        URI annotationURI = getAnnotationURI(prop);
                        // Object could be a URI or literal
                        String object = peekToken();
                        OWLAnnotation annotation;
                        URI uri = getURI(object);
                        if (uri.isAbsolute()) {
                            annotation = dataFactory.getAnnotation(dataFactory.getAnnotationProperty(annotationURI), uri);
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
                                        e.getExpectedKeywords().toArray(new String[e.getExpectedKeywords().size()]));
                            }
                            annotation = dataFactory.getAnnotation(dataFactory.getAnnotationProperty(annotationURI), con);
                        }
                        axioms.add(dataFactory.getAnnotationAssertion(ind.getURI(), annotation));
                    } else {
                        consumeToken();
                        throwException(false, true, true, false, false, ",");
                    }
                    sep = peekToken();
                    if (sep.equals(",")) {
                        consumeToken();
                    }
                }
            } else if (sect.equalsIgnoreCase(SAME_AS)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLIndividual> inds = parseIndividualList();
                inds.add(ind);
                axioms.add(dataFactory.getSameIndividuals(inds));
            } else if (sect.equalsIgnoreCase(DIFFERENT_FROM)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLIndividual> inds = parseIndividualList();
                inds.add(ind);
                axioms.add(dataFactory.getDifferentIndividuals(inds));
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


    public Set<OWLAxiom> parseValuePartitionFrame() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(VALUE_PARTITION)) {
            throwException(VALUE_PARTITION);
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
        String clsName = consumeToken();
        if (clsName.equals(EOF)) {
            throwException(false, true, false, false, false);
        }
        OWLClass cls = getOWLClass(clsName);
        if (cls == null) {
            throwException(true, false, false, false);
        }


        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(parseValuePartitionValues(cls));
        axioms.add(dataFactory.getFunctionalObjectProperty(prop));
        axioms.add(dataFactory.getObjectPropertyRange(prop, cls));
        return axioms;
    }


    public Set<OWLAxiom> parseValuePartitionValues(OWLClass superclass) throws ParserException {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
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
            axioms.add(getDataFactory().getSubClassOf(cls, superclass));
            if (peekToken().equals("[")) {
                axioms.addAll(parseValuePartitionValues(cls));
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
        axioms.add(getDataFactory().getDisjointClasses(siblings));
        return axioms;
    }


    public OWLDisjointClassesAxiom parseDisjointClasses() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(DISJOINT_CLASSES)) {
            throwException(DISJOINT_CLASSES);
        }
        Set<OWLClassExpression> classExpressions = parseDescriptionList();
        return getDataFactory().getDisjointClasses(classExpressions);
    }

    public OWLSameIndividualsAxiom parseSameIndividual() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(SAME_INDIVIDUAL)) {
            throwException(SAME_INDIVIDUAL);
        }
        Set<OWLIndividual> individuals = parseIndividualList();
        return getDataFactory().getSameIndividuals(individuals);
    }

    public OWLDisjointObjectPropertiesAxiom parseDisjointObjectProperties() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(DISJOINT_OBJECT_PROPERTIES)) {
            throwException(DISJOINT_OBJECT_PROPERTIES);
        }
        Set<OWLObjectPropertyExpression> props = parseObjectPropertyList();
        return getDataFactory().getDisjointObjectProperties(props);
    }


    public OWLDisjointDataPropertiesAxiom parseDisjointDataProperties() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(DISJOINT_DATA_PROPERTIES)) {
            throwException(DISJOINT_DATA_PROPERTIES);
        }
        Set<OWLDataProperty> props = parseDataPropertyList();
        return getDataFactory().getDisjointDataProperties(props);
    }


    public OWLDifferentIndividualsAxiom parseDifferentIndividuals() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(DIFFERENT_INDIVIDUALS)) {
            throwException(DIFFERENT_INDIVIDUALS);
        }
        Set<OWLIndividual> props = parseIndividualList();
        return getDataFactory().getDifferentIndividuals(props);
    }


    public Set<OWLAxiom> parseObjectPropertyCharacteristicList(OWLObjectPropertyExpression prop) throws
            ParserException {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        String sep = ",";
        while (sep.equals(",")) {
            String characteristic = consumeToken();
            if (characteristic.equalsIgnoreCase(FUNCTIONAL)) {
                axioms.add(dataFactory.getFunctionalObjectProperty(prop));
            } else if (characteristic.equalsIgnoreCase(INVERSE_FUNCTIONAL)) {
                axioms.add(dataFactory.getInverseFunctionalObjectProperty(prop));
            } else if (characteristic.equalsIgnoreCase(SYMMETRIC)) {
                axioms.add(dataFactory.getSymmetricObjectProperty(prop));
            } else if (characteristic.equalsIgnoreCase(ANTI_SYMMETRIC) || characteristic.equalsIgnoreCase(ASYMMETRIC)) {
                axioms.add(dataFactory.getAsymmetricObjectProperty(prop));
            } else if (characteristic.equalsIgnoreCase(TRANSITIVE)) {
                axioms.add(dataFactory.getTransitiveObjectProperty(prop));
            } else if (characteristic.equalsIgnoreCase(REFLEXIVE)) {
                axioms.add(dataFactory.getReflexiveObjectProperty(prop));
            } else if (characteristic.equalsIgnoreCase(IRREFLEXIVE)) {
                axioms.add(dataFactory.getIrreflexiveObjectProperty(prop));
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


    public Set<OWLClassExpression> parseDescriptionList() throws ParserException {
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


    public Set<OWLClassExpression> parseDescriptionList(String expectedOpen, String expectedClose) throws ParserException {
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


    public OWLComplextSubPropertyAxiom parsePropertyChainSubPropertyAxiom() throws ParserException {
        // Chain followed by subPropertyOf
        List<OWLObjectPropertyExpression> props = parseObjectPropertyChain();
        String imp = consumeToken() + consumeToken();
        if (!imp.equals("->")) {
            throwException("->", "o");
        }
        OWLObjectPropertyExpression superProp = parseObjectPropertyExpression();
        return dataFactory.getObjectPropertyChainSubProperty(props, superProp);
    }


    public OWLClassAxiom parseClassAxiom() throws ParserException {
        OWLClassExpression lhs = parseIntersection();
        // subClassOf
        String kw = consumeToken();
        if (kw.equalsIgnoreCase(ManchesterOWLSyntax.SUBCLASS_OF.toString())) {
            OWLClassExpression rhs = parseIntersection();
            return dataFactory.getSubClassOf(lhs, rhs);
        } else if (kw.equalsIgnoreCase(ManchesterOWLSyntax.EQUIVALENT_TO.toString())) {
            OWLClassExpression rhs = parseIntersection();
            return dataFactory.getOWLEquivalentClassesAxiom(lhs, rhs);
        } else if (kw.equalsIgnoreCase(ManchesterOWLSyntax.DISJOINT_WITH.toString())) {
            OWLClassExpression rhs = parseIntersection();
            return dataFactory.getDisjointClasses(lhs, rhs);
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
            return dataFactory.getFunctionalObjectProperty(prop);
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
            return dataFactory.getInverseFunctionalObjectProperty(prop);
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
            return dataFactory.getTransitiveObjectProperty(prop);
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
            return dataFactory.getSymmetricObjectProperty(prop);
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
            return dataFactory.getReflexiveObjectProperty(prop);
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
            return dataFactory.getIrreflexiveObjectProperty(prop);
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
            return dataFactory.getAsymmetricObjectProperty(prop);
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
        return dataFactory.getImportsDeclaration(ont, importedOntologyURI);
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
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
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
                    Set<OWLAnnotation> annos = parseAnnotations();
                    for (OWLAnnotation anno : annos) {
                        axioms.add(dataFactory.getAnnotationAssertion(ont.getURI(), anno));
                    }
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
                axioms.add(decl);
                manager.makeLoadImportRequest(decl);
            } else if (section.equalsIgnoreCase(NAMESPACE)) {
                Map<String, URI> nsMap = parseNamespace();
                for (String ns : nsMap.keySet()) {
                    namespaceMap.put(ns, nsMap.get(ns).toString());
                }
            } else if (section.equalsIgnoreCase(DISJOINT_CLASSES)) {
                axioms.add(parseDisjointClasses());
            } else if (section.equalsIgnoreCase(DISJOINT_OBJECT_PROPERTIES)) {
                axioms.add(parseDisjointObjectProperties());
            } else if (section.equalsIgnoreCase(DISJOINT_DATA_PROPERTIES)) {
                axioms.add(parseDisjointDataProperties());
            } else if (section.equalsIgnoreCase(DIFFERENT_INDIVIDUALS)) {
                axioms.add(parseDifferentIndividuals());
            } else if (section.equalsIgnoreCase(SAME_INDIVIDUAL)) {
                axioms.add(parseSameIndividual());
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
        for (OWLAxiom ax : axioms) {
            changes.add(new AddAxiom(ont, ax));
        }
        changes.add(new SetOntologyURI(ont, ontologyURI));
        manager.applyChanges(changes);
    }


    protected void throwException(String... keywords) throws ParserException {
        Set<String> theKeywords = new HashSet<String>();
        theKeywords.addAll(Arrays.asList(keywords));
        theKeywords.addAll(potentialKeywords);
        potentialKeywords.clear();
        ManchesterOWLSyntaxTokenizer.Token lastToken = getLastToken();
        throw new ParserException(lastToken.getToken(),
                lastToken.getPos(),
                lastToken.getRow(),
                lastToken.getCol(),
                false,
                false,
                false,
                false,
                false,
                theKeywords);
    }


    protected void throwException(boolean classNameExpected, boolean objectPropertyNameExpected,
                                  boolean dataPropertyNameExpected, boolean individualNameExpected,
                                  boolean datatypeNameExpected, String... keywords) throws ParserException {
        Set<String> theKeywords = new HashSet<String>();
        theKeywords.addAll(Arrays.asList(keywords));
        if (objectPropertyNameExpected) {
            theKeywords.add(INV);
        }
        theKeywords.addAll(potentialKeywords);
        potentialKeywords.clear();
        ManchesterOWLSyntaxTokenizer.Token lastToken = getLastToken();
        throw new ParserException(lastToken.getToken(),
                lastToken.getPos(),
                lastToken.getRow(),
                lastToken.getCol(),
                classNameExpected,
                objectPropertyNameExpected,
                dataPropertyNameExpected,
                individualNameExpected,
                datatypeNameExpected,
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
        throw new ParserException(lastToken.getToken(),
                lastToken.getPos(),
                lastToken.getRow(),
                lastToken.getCol(),
                classNameExpected,
                objectPropertyNameExpected,
                dataPropertyNameExpected,
                individualNameExpected,
                false,
                keywords);
    }


    private class DefaultEntityChecker implements OWLEntityChecker {

        private Map<String, OWLDatatype> dataTypeNameMap;


        public DefaultEntityChecker() {
            dataTypeNameMap = new HashMap<String, OWLDatatype>();
            for (XSDVocabulary v : XSDVocabulary.values()) {
                dataTypeNameMap.put(v.getURI().getFragment(), dataFactory.getDatatype(v.getURI()));
                dataTypeNameMap.put("xsd:" + v.getURI().getFragment(), dataFactory.getDatatype(v.getURI()));
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
                return dataFactory.getOWLIndividual(getURI(name));
            } else {
                return null;
            }
        }


        public OWLDatatype getOWLDatatype(String name) {
            return dataTypeNameMap.get(name);
        }
    }


    private Map<String, URI> nameURIMap = new HashMap<String, URI>();


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
