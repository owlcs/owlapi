package org.coode.owlapi.manchesterowlsyntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLOntologyChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
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
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
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
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.NamespaceUtil;
import org.semanticweb.owlapi.vocab.DublinCoreVocabulary;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;


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
@SuppressWarnings("unused")
public class ManchesterOWLSyntaxEditorParser {

    // This parser was built by hand!  After stuggling with terrible
    // error messages produced by ANTLR (or JavaCC) I decides to construct
    // this parser by hand.  The error messages that this parser generates
    // are specific to the Manchester OWL Syntax and are such that it should
    // be easy to use this parser in tools such as editors.

    private OWLOntologyLoaderConfiguration configuration;

    protected OWLDataFactory dataFactory;

    private List<ManchesterOWLSyntaxTokenizer.Token> tokens;

    private int tokenIndex;

    private OWLEntityChecker owlEntityChecker;

    private OWLOntologyChecker owlOntologyChecker = new OWLOntologyChecker() {
        public OWLOntology getOntology(String name) {
            return null;
        }
    };

    private String base;

    private Set<String> classNames;

    private Set<String> objectPropertyNames;

    private Set<String> dataPropertyNames;

    private Set<String> individualNames;

    private Set<String> dataTypeNames;

    private Set<String> annotationPropertyNames;

    private Map<String, SWRLBuiltInsVocabulary> ruleBuiltIns = new TreeMap<String, SWRLBuiltInsVocabulary>();

    private DefaultPrefixManager pm = new DefaultPrefixManager();

    public static final String AND = ManchesterOWLSyntax.AND.toString();

    public static final String OR = ManchesterOWLSyntax.OR.toString();

    public static final String INVERSE = ManchesterOWLSyntax.INVERSE.toString();

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

    public static final String DATATYPE = ManchesterOWLSyntax.DATATYPE.toString() + ":";

    public static final String SUB_CLASS_OF = ManchesterOWLSyntax.SUBCLASS_OF.toString() + ":";

    public static final String SUPER_CLASS_OF = ManchesterOWLSyntax.SUPERCLASS_OF.toString() + ":";

    public static final String INSTANCES = "Instances:";

    public static final String EQUIVALENT_TO = ManchesterOWLSyntax.EQUIVALENT_TO.toString() + ":";

    public static final String EQUIVALENT_CLASSES = ManchesterOWLSyntax.EQUIVALENT_CLASSES.toString() + ":";

    public static final String EQUIVALENT_PROPERTIES = ManchesterOWLSyntax.EQUIVALENT_PROPERTIES.toString() + ":";

    public static final String DISJOINT_WITH = ManchesterOWLSyntax.DISJOINT_WITH.toString() + ":";

    public static final String DISJOINT_UNION_OF = ManchesterOWLSyntax.DISJOINT_UNION_OF.toString() + ":";

    public static final String HAS_KEY = ManchesterOWLSyntax.HAS_KEY.toString() + ":";

    public static final String DISJOINT_CLASSES = ManchesterOWLSyntax.DISJOINT_CLASSES.toString() + ":";

    public static final String DISJOINT_PROPERTIES = ManchesterOWLSyntax.DISJOINT_PROPERTIES.toString() + ":";

    public static final String OBJECT_PROPERTY = ManchesterOWLSyntax.OBJECT_PROPERTY.toString() + ":";

    public static final String DATA_PROPERTY = ManchesterOWLSyntax.DATA_PROPERTY.toString() + ":";

    public static final String ANNOTATION_PROPERTY = ManchesterOWLSyntax.ANNOTATION_PROPERTY.toString() + ":";

    public static final String SUB_PROPERTY_OF = ManchesterOWLSyntax.SUB_PROPERTY_OF.toString() + ":";

    public static final String SUPER_PROPERTY_OF = ManchesterOWLSyntax.SUPER_PROPERTY_OF.toString() + ":";

    public static final String DOMAIN = ManchesterOWLSyntax.DOMAIN.toString() + ":";

    public static final String RANGE = ManchesterOWLSyntax.RANGE.toString() + ":";

    public static final String INVERSES = ManchesterOWLSyntax.INVERSES.toString() + ":";

    public static final String CHARACTERISTICS = ManchesterOWLSyntax.CHARACTERISTICS.toString() + ":";

    public static final String INDIVIDUAL = ManchesterOWLSyntax.INDIVIDUAL.toString() + ":";

    public static final String INDIVIDUALS = ManchesterOWLSyntax.INDIVIDUALS.toString() + ":";

    public static final String ANNOTATIONS = ManchesterOWLSyntax.ANNOTATIONS.toString() + ":";

    public static final String TYPES = ManchesterOWLSyntax.TYPES.toString() + ":";

    public static final String FACTS = ManchesterOWLSyntax.FACTS.toString() + ":";

    public static final String SAME_AS = ManchesterOWLSyntax.SAME_AS.toString() + ":";

    public static final String SAME_INDIVIDUAL = ManchesterOWLSyntax.SAME_INDIVIDUAL.toString() + ":";

    public static final String DIFFERENT_FROM = ManchesterOWLSyntax.DIFFERENT_FROM.toString() + ":";

    public static final String DIFFERENT_INDIVIDUALS = ManchesterOWLSyntax.DIFFERENT_INDIVIDUALS.toString() + ":";

    public static final String VALUE_PARTITION = "ValuePartition:";

    public static final String ONTOLOGY = ManchesterOWLSyntax.ONTOLOGY.toString() + ":";

    public static final String PREFIX = ManchesterOWLSyntax.PREFIX.toString() + ":";

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

    private boolean allowEmptyFrameSections = false;


    private Map<String, AnnotatedListItemParser<OWLDataProperty, ?>> dataPropertyFrameSections = new HashMap<String, AnnotatedListItemParser<OWLDataProperty, ?>>();


    public ManchesterOWLSyntaxEditorParser(OWLDataFactory dataFactory, String s) {
        this(new OWLOntologyLoaderConfiguration(), dataFactory, s);
    }

    public boolean isAllowEmptyFrameSections() {
        return allowEmptyFrameSections;
    }

    public void setAllowEmptyFrameSections(boolean allowEmptyFrameSections) {
        this.allowEmptyFrameSections = allowEmptyFrameSections;
    }

    public ManchesterOWLSyntaxEditorParser(OWLOntologyLoaderConfiguration configuration, OWLDataFactory dataFactory, String s) {
        this.configuration = configuration;
        this.dataFactory = dataFactory;
        potentialKeywords = new HashSet<String>();

        classNames = new HashSet<String>();
        objectPropertyNames = new HashSet<String>();
        dataPropertyNames = new HashSet<String>();
        individualNames = new HashSet<String>();
        dataTypeNames = new HashSet<String>();
        annotationPropertyNames = new HashSet<String>();
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
            dataTypeNames.add(v.getIRI().getFragment());
            dataTypeNames.add("xsd:" + v.getIRI().getFragment());
        }
        dataTypeNames.add(OWLRDFVocabulary.RDF_XML_LITERAL.getIRI().getFragment());
        dataTypeNames.add("rdf:" + OWLRDFVocabulary.RDF_XML_LITERAL.getIRI().getFragment());

        dataTypeNames.add(dataFactory.getTopDatatype().getIRI().getFragment());

        for (IRI iri : OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTY_IRIS) {
            String[] res = u.split(iri.toString(), null);
            annotationPropertyNames.add(u.getPrefix(res[0]) + ":" + res[1]);
        }

        base = "http://www.semanticweb.org#";
        owlEntityChecker = new DefaultEntityChecker();
        tokens = new ArrayList<ManchesterOWLSyntaxTokenizer.Token>();
        tokens.addAll(getTokenizer(s).tokenize());
        tokenIndex = 0;

        for (SWRLBuiltInsVocabulary v : SWRLBuiltInsVocabulary.values()) {
            ruleBuiltIns.put(v.getShortName(), v);
            ruleBuiltIns.put(v.getIRI().toQuotedString(), v);
        }
    }

    protected ManchesterOWLSyntaxTokenizer getTokenizer(String s) {
        return new ManchesterOWLSyntaxTokenizer(s);
    }

    public OWLDataFactory getDataFactory() {
        return dataFactory;
    }

    private Map<String, AnnotatedListItemParser<OWLClass, ?>> classFrameSections = new HashMap<String, AnnotatedListItemParser<OWLClass, ?>>();

    public void initialiseClassFrameSections() {
        initialiseSection(new EntityAnnotationsListItemParser<OWLClass>(), classFrameSections);
        initialiseSection(new ClassSubClassOfListItemParser(), classFrameSections);
        initialiseSection(new ClassEquivalentToListItemParser(), classFrameSections);
        initialiseSection(new ClassDisjointWithListItemParser(), classFrameSections);
        initialiseSection(new ClassHasKeyListItemParser(), classFrameSections);
        // Extensions
        initialiseSection(new ClassSuperClassOfListItemParser(), classFrameSections);
        initialiseSection(new ClassDisjointClassesListItemParser(), classFrameSections);
        initialiseSection(new ClassIndividualsListItemParser(), classFrameSections);
    }

    private Map<String, AnnotatedListItemParser<OWLObjectProperty, ?>> objectPropertyFrameSections = new HashMap<String, AnnotatedListItemParser<OWLObjectProperty, ?>>();

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


    private Map<String, AnnotatedListItemParser<OWLAnnotationProperty, ?>> annotationPropertyFrameSections = new HashMap<String, AnnotatedListItemParser<OWLAnnotationProperty, ?>>();

    private void initialiseAnnotationPropertyFrameSections() {
        initialiseSection(new AnnotationPropertySubPropertyOfListItemParser(), annotationPropertyFrameSections);
        initialiseSection(new AnnotationPropertyDomainListItemParser(), annotationPropertyFrameSections);
        initialiseSection(new AnnotationPropertyRangeListItemParser(), annotationPropertyFrameSections);
        initialiseSection(new EntityAnnotationsListItemParser<OWLAnnotationProperty>(), annotationPropertyFrameSections);
    }

    private Map<String, AnnotatedListItemParser<OWLIndividual, ?>> individualFrameSections = new HashMap<String, AnnotatedListItemParser<OWLIndividual, ?>>();

    public void initialiseIndividualFrameSections() {
        initialiseSection(new IndividualAnnotationItemParser(), individualFrameSections);
        initialiseSection(new IndividualTypesItemParser(), individualFrameSections);
        initialiseSection(new IndividualFactsItemParser(), individualFrameSections);
        initialiseSection(new IndividualSameAsItemParser(), individualFrameSections);
        initialiseSection(new IndividualDifferentFromItemParser(), individualFrameSections);
        // Extensions
        initialiseSection(new IndividualDifferentIndividualsItemParser(), individualFrameSections);
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
        return classNames.contains(name) || owlEntityChecker != null && owlEntityChecker.getOWLClass(name) != null;
    }


    public OWLOntology getOntology(String name) {
        return owlOntologyChecker.getOntology(name);
    }


    public void setOWLOntologyChecker(OWLOntologyChecker owlOntologyChecker) {
        this.owlOntologyChecker = owlOntologyChecker;
    }


    public boolean isObjectPropertyName(String name) {
        return objectPropertyNames.contains(name) || owlEntityChecker != null && owlEntityChecker.getOWLObjectProperty(name) != null;
    }


    public boolean isAnnotationPropertyName(String name) {
        return annotationPropertyNames.contains(name) || owlEntityChecker != null && owlEntityChecker.getOWLAnnotationProperty(name) != null;
    }


    public boolean isDataPropertyName(String name) {
        return dataPropertyNames.contains(name) || owlEntityChecker != null && owlEntityChecker.getOWLDataProperty(name) != null;
    }


    public boolean isIndividualName(String name) {
        return individualNames.contains(name) || owlEntityChecker != null && owlEntityChecker.getOWLIndividual(name) != null;
    }


    public boolean isDatatypeName(String name) {
        return dataTypeNames.contains(name) || owlEntityChecker != null && owlEntityChecker.getOWLDatatype(name) != null;
    }


    public boolean isSWRLBuiltin(String name) {
        return ruleBuiltIns.containsKey(name);
    }


    public OWLClass getOWLClass(String name) {
        OWLClass cls = owlEntityChecker.getOWLClass(name);
        if (cls == null && classNames.contains(name)) {
            cls = dataFactory.getOWLClass(getIRI(name));
        }
        return cls;
    }


    public OWLObjectProperty getOWLObjectProperty(String name) {
        OWLObjectProperty prop = owlEntityChecker.getOWLObjectProperty(name);
        if (prop == null && objectPropertyNames.contains(name)) {
            prop = dataFactory.getOWLObjectProperty(getIRI(name));
        }
        return prop;
    }


    public OWLIndividual getOWLIndividual(String name) {
        if (name.startsWith("_:")) {
            return dataFactory.getOWLAnonymousIndividual(name);
        }
        return getOWLNamedIndividual(name);
    }

    private OWLNamedIndividual getOWLNamedIndividual(String name) {
        OWLNamedIndividual ind = owlEntityChecker.getOWLIndividual(name);
        if (ind == null && individualNames.contains(name)) {
            ind = dataFactory.getOWLNamedIndividual(getIRI(name));
        }
        return ind;
    }


    public OWLDataProperty getOWLDataProperty(String name) {
        OWLDataProperty prop = owlEntityChecker.getOWLDataProperty(name);
        if (prop == null && dataPropertyNames.contains(name)) {
            prop = dataFactory.getOWLDataProperty(getIRI(name));
        }
        return prop;
    }


    public OWLDatatype getOWLDatatype(String name) {
        OWLDatatype dt = owlEntityChecker.getOWLDatatype(name);
        if (dt == null && dataTypeNames.contains(name)) {
            dt = dataFactory.getOWLDatatype(getIRI(name));
        }
        return dt;
    }


    public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
        OWLAnnotationProperty prop = owlEntityChecker.getOWLAnnotationProperty(name);
        if (prop == null && annotationPropertyNames.contains(name)) {
            prop = dataFactory.getOWLAnnotationProperty(getIRI(name));
        }
        return prop;
    }


    protected ManchesterOWLSyntaxTokenizer.Token getLastToken() {
        if (tokenIndex - 1 > -1) {
            return tokens.get(tokenIndex - 1);
//            return tokenIndex < tokens.size() ? tokens.get(tokenIndex) : tokens.get(tokens.size() - 1);
        }
        else {
            return tokens.get(0);
        }
    }


    protected String peekToken() {
        return getToken().getToken();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Tokenizer
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////


    protected String consumeToken() {
        String token = getToken().getToken();
        if (tokenIndex < tokens.size() - 1) {
            tokenIndex++;
        }

        return token;
    }


    protected void consumeToken(String expected) throws ParserException {
        String tok = consumeToken();
        if (!tok.equals(expected)) {
            throw createException(expected);
        }
    }


    public ManchesterOWLSyntaxTokenizer.Token getToken() {
        return tokens.get((tokenIndex < tokens.size()) ? tokenIndex : tokenIndex - 1);
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
     * @return The parsed class expression
     * @throws ParserException If a class expression could not be parsed.
     */
    public OWLClassExpression parseClassExpression() throws ParserException {
        OWLClassExpression desc = parseIntersection();
        if (!consumeToken().equals(ManchesterOWLSyntaxTokenizer.EOF)) {
            throw createException(ManchesterOWLSyntaxTokenizer.EOF);
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
            }
            else if (kw.equalsIgnoreCase("that")) {
                consumeToken();
                kw = AND;
            }
        }
        if (ops.size() == 1) {
            return ops.iterator().next();
        }
        else {
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
        }
        else {
            return dataFactory.getOWLObjectUnionOf(ops);
        }
    }


    public OWLObjectPropertyExpression parseObjectPropertyExpression(boolean allowUndeclared) throws ParserException {
        String tok = consumeToken();
        if (tok.equalsIgnoreCase(INVERSE)) {
            String open = peekToken();
            boolean brackets = false;
            if (open.equals("(")) {
                consumeToken();
                brackets = true;
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            if (brackets) {
                String close = consumeToken();
                if (!close.equals(")")) {
                    throw createException(")");
                }
            }
            return dataFactory.getOWLObjectInverseOf(prop);
        }
        else {
            if (!allowUndeclared && !isObjectPropertyName(tok)) {
                throw createException(false, true, false, false, false, false, INVERSE);
            }
            return getOWLObjectProperty(tok);
        }
    }


    public OWLObjectPropertyExpression parseObjectPropertyExpression() throws ParserException {
        return parseObjectPropertyExpression(false);
    }


    public OWLPropertyExpression parsePropertyExpression() throws ParserException {
        String tok = peekToken();
        if (isObjectPropertyName(tok)) {
            return parseObjectPropertyExpression();
        }
        else if (tok.equalsIgnoreCase(INVERSE)) {
            return parseObjectPropertyExpression();
        }
        else if (isDataPropertyName(tok)) {
            return parseDataProperty();
        }
        else {
            consumeToken();
            throw createException(false, true, true, false, false, false, INVERSE);
        }
    }


    public OWLClassExpression parseRestriction() throws ParserException {
        String tok = peekToken();
        if (isObjectPropertyName(tok) || tok.equalsIgnoreCase(INVERSE)) {
            return parseObjectRestriction();
        }
        else if (isDataPropertyName(tok)) {
            return parseDataRestriction();
        }
        else {
            consumeToken();
            throw createException(false, true, true, false);
        }
    }


    /**
     * Parses all class expressions except ObjectIntersectionOf and ObjectUnionOf
     * @return The class expression which was parsed
     * @throws ParserException if a non-nary class expression could not be parsed
     */
    public OWLClassExpression parseNonNaryClassExpression() throws ParserException {

        String tok = peekToken();
        if (tok.equalsIgnoreCase(NOT)) {
            consumeToken();
            OWLClassExpression complemented = parseNestedClassExpression(false);
            return dataFactory.getOWLObjectComplementOf(complemented);
        }
        else if (isObjectPropertyName(tok) || tok.equalsIgnoreCase(INVERSE)) {
            return parseObjectRestriction();
        }
        else if (isDataPropertyName(tok)) {
            // Data restriction
            return parseDataRestriction();
        }
        else if (tok.equals("{")) {
            return parseObjectOneOf();
        }
        else if (tok.equals("(")) {
            return parseNestedClassExpression(false);
        }
        else if (isClassName(tok)) {
            consumeToken();
            return getOWLClass(tok);
        }
        // Add option for strict class name checking
        else {
            consumeToken();
            throw createException(true, true, true, false, false, false, "(", "{", NOT, INVERSE);
        }
    }


    public OWLClassExpression parseObjectRestriction() throws ParserException {
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
        String kw = consumeToken();
        if (kw.equalsIgnoreCase(SOME)) {
            String possSelfToken = peekToken();
            if (possSelfToken.equalsIgnoreCase(SELF)) {
                consumeToken();
                return dataFactory.getOWLObjectHasSelf(prop);
            }
            else {
                OWLClassExpression filler = null;
                try {
                    filler = parseNestedClassExpression(false);
                }
                catch (ParserException e) {
                    Set<String> keywords = new HashSet<String>();
                    keywords.addAll(e.getExpectedKeywords());
                    keywords.add(SELF);
                    throw createException(e.isClassNameExpected(), e.isObjectPropertyNameExpected(), e.isDataPropertyNameExpected(), e.isIndividualNameExpected(), e.isDatatypeNameExpected(), e.isAnnotationPropertyNameExpected(), keywords.toArray(new String[keywords.size()]));
                }
                return dataFactory.getOWLObjectSomeValuesFrom(prop, filler);
            }
        }
        else if (kw.equalsIgnoreCase(ONLY)) {
            OWLClassExpression filler = parseNestedClassExpression(false);
            return dataFactory.getOWLObjectAllValuesFrom(prop, filler);
        }
        else if (kw.equalsIgnoreCase(VALUE)) {
            String indName = consumeToken();
            if (!isIndividualName(indName)) {
                throw createException(false, false, false, true);
            }
            return dataFactory.getOWLObjectHasValue(prop, getOWLIndividual(indName));
        }
        else if (kw.equalsIgnoreCase(MIN)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            if (filler != null) {
                return dataFactory.getOWLObjectMinCardinality(card, prop, filler);
            }
            else {
                return dataFactory.getOWLObjectMinCardinality(card, prop);
            }
        }
        else if (kw.equalsIgnoreCase(MAX)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            if (filler != null) {
                return dataFactory.getOWLObjectMaxCardinality(card, prop, filler);
            }
            else {
                return dataFactory.getOWLObjectMaxCardinality(card, prop);
            }
        }
        else if (kw.equalsIgnoreCase(EXACTLY)) {
            int card = parseInteger();
            OWLClassExpression filler = parseNestedClassExpression(true);
            if (filler != null) {
                return dataFactory.getOWLObjectExactCardinality(card, prop, filler);
            }
            else {
                return dataFactory.getOWLObjectExactCardinality(card, prop);
            }
        }
        else if (kw.equalsIgnoreCase(ONLYSOME)) {
            String tok = peekToken();
            Set<OWLClassExpression> descs = new HashSet<OWLClassExpression>();
            if (!tok.equals("[")) {
                descs.add(parseIntersection());
            }
            else {
                descs.addAll(parseClassExpressionList("[", "]"));
            }
            Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
            for (OWLClassExpression desc : descs) {
                ops.add(dataFactory.getOWLObjectSomeValuesFrom(prop, desc));
            }
            OWLClassExpression filler;
            if (descs.size() == 1) {
                filler = descs.iterator().next();
            }
            else {
                filler = dataFactory.getOWLObjectUnionOf(descs);
            }
            ops.add(dataFactory.getOWLObjectAllValuesFrom(prop, filler));
            return dataFactory.getOWLObjectIntersectionOf(ops);
        }
        else if (kw.equalsIgnoreCase(SELF)) {
            return dataFactory.getOWLObjectHasSelf(prop);
        }
        else {
            // Error!
            throw createException(SOME, ONLY, VALUE, MIN, MAX, EXACTLY, SELF);
        }
    }


    public OWLClassExpression parseDataRestriction() throws ParserException {
        OWLDataPropertyExpression prop = parseDataProperty();
        String kw = consumeToken();
        if (kw.equalsIgnoreCase(SOME)) {
            OWLDataRange rng = parseDataRange();
            if(rng==null) {
            	throw new ParserException(getTokenSequence(), getTokenPos(), getTokenRow(), getTokenCol(), true, false, false, false, true, false, Collections.<String>emptySet());
//            	return dataFactory.getOWLDataSomeValuesFrom(prop, rng)
            }
            return dataFactory.getOWLDataSomeValuesFrom(prop, rng);
        }
        else if (kw.equalsIgnoreCase(ONLY)) {
            OWLDataRange rng = parseDataRange();
            return dataFactory.getOWLDataAllValuesFrom(prop, rng);
        }
        else if (kw.equalsIgnoreCase(VALUE)) {
            OWLLiteral con = parseConstant();
            return dataFactory.getOWLDataHasValue(prop, con);
        }
        else if (kw.equalsIgnoreCase(MIN)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataRange();
            if (rng != null) {
                return dataFactory.getOWLDataMinCardinality(card, prop, rng);
            }
            else {
                return dataFactory.getOWLDataMinCardinality(card, prop);
            }
        }
        else if (kw.equalsIgnoreCase(EXACTLY)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataRange();
            if (rng != null) {
                return dataFactory.getOWLDataExactCardinality(card, prop, rng);
            }
            else {
                return dataFactory.getOWLDataExactCardinality(card, prop);
            }
        }
        else if (kw.equalsIgnoreCase(MAX)) {
            int card = parseInteger();
            OWLDataRange rng = parseDataRange();
            if (rng != null) {
                return dataFactory.getOWLDataMaxCardinality(card, prop, rng);
            }
            else {
                return dataFactory.getOWLDataMaxCardinality(card, prop);
            }
        }
        throw createException(SOME, ONLY, VALUE, MIN, EXACTLY, MAX);
    }


    public OWLFacet parseFacet() throws ParserException {
        String facet = consumeToken();
        if (facet.equals(">")) {
            if (peekToken().equals("=")) {
                consumeToken();
                return OWLFacet.MIN_INCLUSIVE;
            }
            else {
                return OWLFacet.MIN_EXCLUSIVE;
            }
        }
        else if (facet.equals("<")) {
            if (peekToken().equals("=")) {
                consumeToken();
                return OWLFacet.MAX_INCLUSIVE;
            }
            else {
                return OWLFacet.MAX_EXCLUSIVE;
            }
        }
        return OWLFacet.getFacetBySymbolicName(facet);
    }


    public OWLDatatype parseDatatype() throws ParserException {
        String name = consumeToken();
        return getOWLDatatype(name);
    }


    public OWLDataRange parseDataRange() throws ParserException {
        return parseDataIntersectionOf();
    }

    public OWLDataRange parseDataIntersectionOf() throws ParserException {
        String sep = AND;
        Set<OWLDataRange> ranges = new HashSet<OWLDataRange>();
        while (sep.equals(AND)) {
            ranges.add(parseDataUnionOf());
            sep = peekToken();
            if (sep.equals(AND)) {
                consumeToken();
            }
        }
        if (ranges.size() == 1) {
            return ranges.iterator().next();
        }
        else {
            return dataFactory.getOWLDataIntersectionOf(ranges);
        }
    }

    public OWLDataRange parseDataUnionOf() throws ParserException {
        String sep = OR;
        Set<OWLDataRange> ranges = new HashSet<OWLDataRange>();
        while (sep.equals(OR)) {
            ranges.add(parseDataRangePrimary());
            sep = peekToken();
            if (sep.equals(OR)) {
                consumeToken();
            }
        }
        if (ranges.size() == 1) {
            return ranges.iterator().next();
        }
        else {
            return dataFactory.getOWLDataUnionOf(ranges);
        }
    }

    private OWLDataRange parseDataRangePrimary() throws ParserException {
        String tok = peekToken();

        if (isDatatypeName(tok)) {
            consumeToken();
            OWLDatatype datatype = getOWLDatatype(tok);
            String next = peekToken();
            if (next.equals("[")) {
                // Restricted data range
                consumeToken();
                String sep = ",";
                Set<OWLFacetRestriction> facetRestrictions = new HashSet<OWLFacetRestriction>();
                while (sep.equals(",")) {
                    OWLFacet fv = parseFacet();
                    if (fv == null) {
                        throw createException(OWLFacet.getFacets().toArray(new String[OWLFacet.getFacetIRIs().size()]));
                    }
					OWLLiteral con = parseConstant();
					// if (!con.isRDFPlainLiteral()) {
					// con = dataFactory.getOWLLiteral(con.getLiteral());
					// }
					facetRestrictions.add(dataFactory.getOWLFacetRestriction(fv, con));
                    sep = consumeToken();
                }
                if (!sep.equals("]")) {
                    throw createException("]");
                }
                return dataFactory.getOWLDatatypeRestriction(datatype, facetRestrictions);
            }
            else {
                return datatype;
            }
        }
        else if (tok.equalsIgnoreCase(NOT)) {
            return parseDataComplementOf();
        }
        else if (tok.equals("{")) {
            return parseDataOneOf();
        }
        else if (tok.equals("(")) {
            consumeToken();
            OWLDataRange rng = parseDataRange();
            consumeToken(")");
            return rng;
        }
        else if (!tok.equals(ManchesterOWLSyntaxTokenizer.EOF)) {
            consumeToken();
            throw createException(false, false, false, false, true, false, NOT, "{");
        }
        return null;
    }


    public Set<OWLDataRange> parseDataRangeList() throws ParserException {
        String sep = ",";
        Set<OWLDataRange> ranges = new HashSet<OWLDataRange>();
        while (sep.equals(",")) {
            potentialKeywords.remove(",");
            OWLDataRange rng = parseDataRange();
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
            throw createException(",", "}");
        }
        return dataFactory.getOWLDataOneOf(cons);
    }


    private OWLDataRange parseDataComplementOf() throws ParserException {
        String not = consumeToken();
        if (!not.equalsIgnoreCase(NOT)) {
            throw createException(NOT);
        }
        OWLDataRange complementedDataRange = parseDataRangePrimary();
        return dataFactory.getOWLDataComplementOf(complementedDataRange);
    }

    public OWLLiteral parseLiteral() throws ParserException {
        String tok = consumeToken();
        if (tok.startsWith("\"")) {
            String lit = "";
            if (tok.length() > 2) {
                lit = tok.substring(1, tok.length() - 1).trim();
            }
            if (peekToken().equals("^")) {
                consumeToken();
                if (!peekToken().equals("^")) {
                    throw createException("^");
                }
                consumeToken();
                return dataFactory.getOWLLiteral(lit, parseDatatype());
            }
            else if (peekToken().startsWith("@")) {
                // Plain literal with a language tag
                String lang = consumeToken().substring(1);
                return dataFactory.getOWLLiteral(lit, lang);
            }
            else {
                // Plain literal without a language tag
                return dataFactory.getOWLLiteral(lit, "");
            }
        }
        else {
            try {
                int i = Integer.parseInt(tok);
                return dataFactory.getOWLLiteral(tok, OWL2Datatype.XSD_INTEGER);
            }
            catch (NumberFormatException e) {
                // Ignore - not interested
            }
            if (tok.endsWith("f")||tok.endsWith("F")) {
                try {
                    float f = Float.parseFloat(tok);
                    return dataFactory.getOWLLiteral(tok.substring(0, tok.length()-1), OWL2Datatype.XSD_FLOAT);
                }
                catch (NumberFormatException e) {
                    // Ignore - not interested
                }
            }
            try {
                double d = Double.parseDouble(tok);
                return dataFactory.getOWLLiteral(tok, OWL2Datatype.XSD_DOUBLE);
            }
            catch (NumberFormatException e) {
                // Ignore - not interested
            }

            if (tok.equals("true")) {
                return dataFactory.getOWLLiteral(true);
            }
            else if (tok.equals("false")) {
                return dataFactory.getOWLLiteral(false);
            }
        }
        throw createException(false, false, false, false, false, false, "true", "false", "$integer$", "$float$", "$double$", "\"$Literal$\"", "\"$Literal$\"^^<datatype>", "\"$Literal$\"@<lang>");
    }

    /**
     * @deprecated Use {@link #parseLiteral()} instead
     */
    @Deprecated
    public OWLLiteral parseConstant() throws ParserException {
        return parseLiteral();
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
                throw createException(")");
            }
            return desc;
        }
        else if (tok.equals("{")) {
            return parseObjectOneOf();
        }
        else if (isClassName(tok)) {
            String name = consumeToken();
            return getOWLClass(name);
        }
        else if (!tok.equals(ManchesterOWLSyntaxTokenizer.EOF) || !lookaheadCheck) {
            consumeToken();
            throw createException(true, false, false, false, false, false, "(", "{");
        }
        return null;
    }


    public OWLClassExpression parseObjectOneOf() throws ParserException {
        String open = consumeToken();
        if (!open.equals("{")) {
            throw createException("{");
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
            throw createException("}", ",");
        }
        return dataFactory.getOWLObjectOneOf(inds);
    }



    private <F> void initialiseSection(AnnotatedListItemParser<F, ?> parser, Map<String, AnnotatedListItemParser<F, ?>> map, String ... synonyms) {
        map.put(parser.getFrameSectionKeyword(), parser);
        for(String syn : synonyms) {
            map.put(syn, parser);
        }
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
                possible.addAll(classFrameSections.keySet());
            }
            else if (tok.equalsIgnoreCase(OBJECT_PROPERTY)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseObjectPropertyFrame());
                possible.addAll(objectPropertyFrameSections.keySet());
            }
            else if (tok.equalsIgnoreCase(DATA_PROPERTY)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseDataPropertyFrame());
                possible.addAll(dataPropertyFrameSections.keySet());
            }
            else if (tok.equalsIgnoreCase(ANNOTATION_PROPERTY)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseAnnotationPropertyFrame());
                possible.addAll(Arrays.asList(SUB_PROPERTY_OF, DOMAIN, RANGE));
            }
            else if (tok.equalsIgnoreCase(INDIVIDUAL)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseIndividualFrame());
                possible.addAll(Arrays.asList(TYPES, FACTS, DIFFERENT_FROM, SAME_AS));
            }
            else if (tok.equalsIgnoreCase(DATATYPE)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseDatatypeFrame());
                possible.add(EQUIVALENT_TO);
            }
            else if (tok.equalsIgnoreCase(VALUE_PARTITION)) {
                potentialKeywords.clear();
                resetPossible(possible);
                parseValuePartitionFrame();
            }
            else if (tok.equalsIgnoreCase(RULE)) {
                potentialKeywords.clear();
                resetPossible(possible);
                axioms.addAll(parseRuleFrame());
            }
            else {
                if (tok.equals(ManchesterOWLSyntaxTokenizer.EOF)) {
                    break;
                }
                else {
                    consumeToken();
                    throw createException(possible.toArray(new String[possible.size()]));
                }
            }
        }
        return axioms;
    }


    public Set<OntologyAxiomPair> parseDatatypeFrame() throws ParserException {
        String tok = consumeToken();
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        if (!tok.equalsIgnoreCase(DATATYPE)) {
            throw createException(DATATYPE);
        }
        String subj = consumeToken();
        OWLDatatype datatype = getOWLDatatype(subj);
        if (datatype == null) {
            throw createException(false, false, false, false, true, false);
        }
        axioms.add(new OntologyAxiomPair(defaultOntology, dataFactory.getOWLDeclarationAxiom(datatype)));
        while (true) {
            String sect = peekToken();
            if (sect.equalsIgnoreCase(EQUIVALENT_TO)) {
                potentialKeywords.clear();
                consumeToken();
                Set<OWLOntology> onts = getOntologies();
                Set<OWLDataRange> drs = parseDataRangeList();
                for (OWLOntology ont : onts) {
                    for (OWLDataRange dr : drs) {
                        axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLDatatypeDefinitionAxiom(datatype, dr)));
                    }
                }
            }
            else if (sect.equalsIgnoreCase(ANNOTATIONS)) {
                potentialKeywords.clear();
                axioms.addAll(parseAnnotations(datatype.getIRI()));
            }
            else {
                break;
            }
        }
        return axioms;
    }


    private void resetPossible(Set<String> possible) {
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

    public Set<OntologyAxiomPair> parseNaryEquivalentClasses() throws ParserException {
        String tok = consumeToken();
        if (!tok.equalsIgnoreCase(EQUIVALENT_CLASSES)) {
            throw createException(EQUIVALENT_CLASSES);
        }
        Set<OWLOntology> ontologies = getOntologies();
        String next = peekToken();
        Set<OWLAnnotation> annotations;
        if (next.equalsIgnoreCase(ANNOTATIONS)) {
            consumeToken();
            annotations = parseAnnotationList();
        }
        else {
            annotations = Collections.emptySet();
        }
        Set<OWLClassExpression> classExpressions = parseClassExpressionList(false);
        Set<OntologyAxiomPair> pairs = new HashSet<OntologyAxiomPair>();
        for (OWLOntology ont : ontologies) {
            pairs.add(new OntologyAxiomPair(ont, dataFactory.getOWLEquivalentClassesAxiom(classExpressions, annotations)));
        }
        return pairs;
    }

    public Set<OntologyAxiomPair> parseNaryEquivalentProperties() throws ParserException {
        String tok = consumeToken();
        if (!tok.equalsIgnoreCase(EQUIVALENT_PROPERTIES)) {
            throw createException(EQUIVALENT_PROPERTIES);
        }
        Set<OWLOntology> ontologies = getOntologies();
        String next = peekToken();
        Set<OWLAnnotation> annotations;
        if (next.equalsIgnoreCase(ANNOTATIONS)) {
            consumeToken();
            annotations = parseAnnotationList();
        }
        else {
            annotations = Collections.emptySet();
        }
        Set<OWLPropertyExpression> properties = parsePropertyList();
        OWLAxiom propertyAxiom;
        if (properties.iterator().next().isObjectPropertyExpression()) {
            Set<OWLObjectPropertyExpression> ope = new HashSet<OWLObjectPropertyExpression>();
            for (OWLPropertyExpression pe : properties) {
                ope.add((OWLObjectPropertyExpression) pe);
            }
            propertyAxiom = dataFactory.getOWLEquivalentObjectPropertiesAxiom(ope, annotations);


        }
        else {
            Set<OWLDataPropertyExpression> dpe = new HashSet<OWLDataPropertyExpression>();
            for (OWLPropertyExpression pe : properties) {
                dpe.add((OWLDataPropertyExpression) pe);
            }
            propertyAxiom = dataFactory.getOWLEquivalentDataPropertiesAxiom(dpe, annotations);
        }
        Set<OntologyAxiomPair> pairs = new HashSet<OntologyAxiomPair>();
        for (OWLOntology ont : ontologies) {
            pairs.add(new OntologyAxiomPair(ont, propertyAxiom));
        }
        return pairs;
    }

    public Set<OntologyAxiomPair> parseAnnotations(OWLAnnotationSubject subject) throws ParserException {
        String header = consumeToken();
        if (!header.equals(ANNOTATIONS)) {
            throw createException(ANNOTATIONS);
        }
        Set<OWLOntology> onts = getOntologies();
        Set<OntologyAxiomPair> pairs = new HashSet<OntologyAxiomPair>();

//        if (!isEmptyClassFrameSection()) {
            Set<OWLAnnotation> annos = parseAnnotationList();
            for (OWLOntology ont : onts) {
                for (OWLAnnotation anno : annos) {
                    if (configuration.isLoadAnnotationAxioms()) {
                        OWLAnnotationAssertionAxiom ax = dataFactory.getOWLAnnotationAssertionAxiom(subject, anno);
                        pairs.add(new OntologyAxiomPair(ont, ax));
                    }
                }
            }
//        }

        return pairs;
    }


    private Set<OWLAnnotation> parseAnnotationList() throws ParserException {
        String sep = ",";
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        while (sep.equals(",")) {
            potentialKeywords.clear();
            Set<OWLAnnotation> annoAnnos;
            String nextToken = peekToken();
            if(nextToken.equals(ANNOTATIONS)) {
                consumeToken();
                annoAnnos = parseAnnotationList();
            }
            else {
                annoAnnos = Collections.emptySet();
            }
            OWLAnnotation anno = parseAnnotation();
            if (anno != null) {
                anno = anno.getAnnotatedAnnotation(annoAnnos);
                annos.add(anno);
            }
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        return annos;
    }


    private OWLAnnotation parseAnnotation() throws ParserException {
        OWLAnnotationProperty annoProp = parseAnnotationProperty();
        String obj = peekToken();
        OWLAnnotation anno = null;
        if (isIndividualName(obj) || isClassName(obj) || isObjectPropertyName(obj) || isDataPropertyName(obj)) {
            consumeToken();
            OWLAnnotationValue value;
            if(obj.startsWith("_:")) {
                value = dataFactory.getOWLAnonymousIndividual(obj);
            }
            else {
                value = getIRI(obj);
            }
            anno = dataFactory.getOWLAnnotation(annoProp, value);
        }
        else if (obj.startsWith("<")) {
            IRI value = parseIRI();
            anno = dataFactory.getOWLAnnotation(annoProp, value);
        }
        else {
            OWLLiteral con = parseLiteral();
            anno = dataFactory.getOWLAnnotation(annoProp, con);
        }
        return anno;
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
            throw createException(CLASS);
        }
        String subj = consumeToken();
        OWLClass cls = getOWLClass(subj);
        if (cls == null) {
            throw createException(true, false, false, false);
        }
        axioms.add(new OntologyAxiomPair(defaultOntology, dataFactory.getOWLDeclarationAxiom(cls)));
        parseFrameSections(eof, axioms, cls, classFrameSections);
        return axioms;
    }


    private Set<OWLOntology> parseOntologyList() throws ParserException {
        potentialKeywords.clear();
        consumeToken("[");
        consumeToken("in");
        String sep = ",";
        Set<OWLOntology> onts = new HashSet<OWLOntology>();
        while (sep.equals(",")) {
            String tok = consumeToken();
            if (isOntologyName(tok)) {
                OWLOntology ont = getOntology(tok);
                if (ont != null) {
                    onts.add(ont);
                }
            }
            else {
                throw createException(true);
            }
            sep = consumeToken();
            if (sep.equals("]")) {
                break;
            }
            else if (!sep.equals(",")) {
                throw createException(",", "]");
            }
        }
        return onts;
    }


    private Set<OWLOntology> getOntologies() throws ParserException {
        if (peekToken().equals("[")) {
            return parseOntologyList();
        }
        else {
            return Collections.singleton(defaultOntology);
        }
    }


    public void setDefaultOntology(OWLOntology defaultOntology) {
        this.defaultOntology = defaultOntology;
    }

    private boolean isEmptyFrameSection(Map<String, ?> parsers) {
        if (!allowEmptyFrameSections) {
            return false;
        }
        String next = peekToken();
        return !next.equals(ANNOTATIONS) && (parsers.containsKey(next) || next.equals(ManchesterOWLSyntaxTokenizer.EOF));
    }

    private boolean isClassFrameSection(String token) {
        return classFrameSections.containsKey(token);
    }



    private <F> void parseFrameSections(boolean eof, Set<OntologyAxiomPair> axioms, F frameSubject, Map<String, AnnotatedListItemParser<F, ?>> sectionParsers) throws ParserException{
        while(true) {
            String sect = peekToken();
                AnnotatedListItemParser<F, ?> parser = sectionParsers.get(sect);
                if(parser != null) {
                    consumeToken();
                    Set<OWLOntology> onts = getOntologies();
                    if (!isEmptyFrameSection(sectionParsers)) {
                        axioms.addAll(parseAnnotatedListItems(frameSubject, parser, onts));
                    }
                }
                else if(eof && !ManchesterOWLSyntaxTokenizer.EOF.equals(sect)) {
                    List<String> expected = new ArrayList<String>();
                    expected.addAll(sectionParsers.keySet());
                    if(frameSubject instanceof OWLAnnotationSubject || frameSubject instanceof OWLEntity) {
                        expected.add(ANNOTATIONS);
                    }
                    throw createException(expected.toArray(new String [expected.size()]));
                }
                else {
                    break;
                }
        }
    }


    public Set<OntologyAxiomPair> parseObjectPropertyFrame() throws ParserException {
        return parseObjectPropertyFrame(false);
    }



    public Set<OntologyAxiomPair> parseObjectPropertyFrame(boolean eof) throws ParserException {
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        consumeToken(OBJECT_PROPERTY);
        String token = consumeToken();
        OWLObjectProperty prop = getOWLObjectProperty(token);
        if (prop == null) {
            throw createException(false, true, false, false);
        }
        if (!prop.isAnonymous()) {
            axioms.add(new OntologyAxiomPair(defaultOntology, dataFactory.getOWLDeclarationAxiom(prop.asOWLObjectProperty())));
        }
        parseFrameSections(eof, axioms, prop, objectPropertyFrameSections);
        return axioms;
    }


    public Set<OntologyAxiomPair> parseDataPropertyFrame() throws ParserException {
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        String tok = consumeToken();
        if (!tok.equalsIgnoreCase(DATA_PROPERTY)) {
            throw createException(DATA_PROPERTY);
        }
        String subj = consumeToken();
        OWLDataProperty prop = getOWLDataProperty(subj);
        if (prop == null) {
            throw createException(false, false, true, false);
        }
        parseFrameSections(false, axioms, prop, dataPropertyFrameSections);
        return axioms;
    }


    public Set<OntologyAxiomPair> parseAnnotationPropertyFrame() throws ParserException {
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        String tok = consumeToken();
        if (!tok.equalsIgnoreCase(ANNOTATION_PROPERTY)) {
            throw createException(ANNOTATION_PROPERTY);
        }
        String subj = consumeToken();
        OWLAnnotationProperty prop = getOWLAnnotationProperty(subj);
        for (OWLOntology ont : getOntologies()) {
            axioms.add(new OntologyAxiomPair(ont, dataFactory.getOWLDeclarationAxiom(prop)));
        }
        if (prop == null) {
            throw createException(false, false, true, false);
        }
        parseFrameSections(false, axioms, prop, annotationPropertyFrameSections);
        return axioms;
    }


    public Set<OntologyAxiomPair> parseIndividualFrame() throws ParserException {
        String tok = consumeToken();
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        if (!tok.equalsIgnoreCase(INDIVIDUAL)) {
            throw createException(INDIVIDUAL);
        }
        String subj = consumeToken();
        OWLIndividual ind = getOWLIndividual(subj);
        if (ind == null) {
            throw createException(false, false, false, true);
        }
        if (!ind.isAnonymous()) {
            axioms.add(new OntologyAxiomPair(getOntology(null), dataFactory.getOWLDeclarationAxiom(ind.asOWLNamedIndividual())));
        }
        parseFrameSections(false, axioms, ind, individualFrameSections);
        return axioms;
    }

    private OWLPropertyAssertionAxiom parseFact(OWLIndividual ind) throws ParserException {
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
                    return dataFactory.getOWLDataPropertyAssertionAxiom(p, ind, con);
            }
            else {
                    return dataFactory.getOWLNegativeDataPropertyAssertionAxiom(p, ind, con);
            }
        }
        else if (isObjectPropertyName(prop)) {
            OWLObjectPropertyExpression p = parseObjectPropertyExpression();
            OWLIndividual obj = parseIndividual();
                if (!negative) {
                    return dataFactory.getOWLObjectPropertyAssertionAxiom(p, ind, obj);
                }
                else {
                    return dataFactory.getOWLNegativeObjectPropertyAssertionAxiom(p, ind, obj);
                }
        }
        else {
            consumeToken();
            throw createException(false, true, true, false, false, false);
        }
    }


    public Set<OntologyAxiomPair> parseValuePartitionFrame() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(VALUE_PARTITION)) {
            throw createException(VALUE_PARTITION);
        }
        Set<OWLOntology> onts = getOntologies();
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
        String clsName = consumeToken();
        if (clsName.equals(ManchesterOWLSyntaxTokenizer.EOF)) {
            throw createException(false, true, false, false, false, false);
        }
        OWLClass cls = getOWLClass(clsName);
        if (cls == null) {
            throw createException(true, false, false, false);
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
        consumeToken("[");
        String sep = ",";
        while (sep.equals(",")) {
            String clsName = consumeToken();
            OWLClass cls = getOWLClass(clsName);
            if (cls == null) {
                throw createException(true, false, false, false);
            }
            siblings.add(cls);
            OWLSubClassOfAxiom ax = dataFactory.getOWLSubClassOfAxiom(cls, superclass);
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
        consumeToken("]");
        OWLAxiom ax = dataFactory.getOWLDisjointClassesAxiom(siblings);
        for (OWLOntology ont : onts) {
            axioms.add(new OntologyAxiomPair(ont, ax));
        }
        return axioms;
    }


    public Set<OntologyAxiomPair> parseRuleFrame() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(RULE)) {
            throw createException(RULE);
        }
        Set<OWLOntology> ontologies = getOntologies();
        List<SWRLAtom> body = parseRuleAtoms();
        String tok = consumeToken();
        if (!tok.equals("-")) {
            throw createException("-", ",");
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
        while (sep.equals(",")) {
            potentialKeywords.remove(",");
            SWRLAtom atom = parseRuleAtom();
            atoms.add(atom);
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
            potentialKeywords.add(",");
        }
        return atoms;
    }


    public SWRLAtom parseRuleAtom() throws ParserException {
        String predicate = peekToken();
        if (isClassName(predicate)) {
            return parseClassAtom();
        }
        else if (isObjectPropertyName(predicate)) {
            return parseObjectPropertyAtom();
        }
        else if (isDataPropertyName(predicate)) {
            return parseDataPropertyAtom();
        }
        else if (predicate.equals(ManchesterOWLSyntax.DIFFERENT_FROM.toString())) {
            return parseDifferentFromAtom();
        }
        else if (predicate.equals(ManchesterOWLSyntax.SAME_AS.toString())) {
            return parseSameAsAtom();
        }
        else if (isSWRLBuiltin(predicate) || predicate.startsWith("<")) {
            return parseBuiltInAtom();
        }
        else {
            consumeToken();
            Set<String> kw = new TreeSet<String>();
            kw.addAll(ruleBuiltIns.keySet());
            kw.add(ManchesterOWLSyntax.DIFFERENT_FROM.toString());
            kw.add(ManchesterOWLSyntax.SAME_AS.toString());
            throw createException(true, true, true, false, false, false, kw.toArray(new String[ruleBuiltIns.size()]));
        }
    }


    public SWRLAtom parseDataPropertyAtom() throws ParserException {
        String predicate = consumeToken();
        if (!isDataPropertyName(predicate)) {
            throw createException(false, false, true, false);
        }
        consumeToken("(");
        SWRLIArgument obj1 = parseIObject();
        consumeToken(",");
        SWRLDArgument obj2 = parseDObject();
        consumeToken(")");
        return dataFactory.getSWRLDataPropertyAtom(getOWLDataProperty(predicate), obj1, obj2);
    }


    public SWRLAtom parseObjectPropertyAtom() throws ParserException {
        String predicate = consumeToken();
        if (!isObjectPropertyName(predicate)) {
            throw createException(false, true, false, false);
        }
        consumeToken("(");
        SWRLIArgument obj1 = parseIObject();
        consumeToken(",");
        SWRLIArgument obj2 = parseIObject();
        consumeToken(")");
        return dataFactory.getSWRLObjectPropertyAtom(getOWLObjectProperty(predicate), obj1, obj2);
    }


    public SWRLAtom parseClassAtom() throws ParserException {
        String predicate = consumeToken();
        if (!isClassName(predicate)) {
            throw createException(true, false, false, false);
        }
        consumeToken("(");
        SWRLIArgument obj = parseIObject();
        consumeToken(")");
        return dataFactory.getSWRLClassAtom(getOWLClass(predicate), obj);
    }


    public SWRLDifferentIndividualsAtom parseDifferentFromAtom() throws ParserException {
        consumeToken(ManchesterOWLSyntax.DIFFERENT_FROM.toString());
        consumeToken("(");
        SWRLIArgument obj1 = parseIObject();
        consumeToken(",");
        SWRLIArgument obj2 = parseIObject();
        consumeToken(")");
        return dataFactory.getSWRLDifferentIndividualsAtom(obj1, obj2);
    }


    public SWRLSameIndividualAtom parseSameAsAtom() throws ParserException {
        consumeToken(ManchesterOWLSyntax.SAME_AS.toString());
        consumeToken("(");
        SWRLIArgument obj1 = parseIObject();
        consumeToken(",");
        SWRLIArgument obj2 = parseIObject();
        consumeToken(")");
        return dataFactory.getSWRLSameIndividualAtom(obj1, obj2);
    }


    public SWRLIArgument parseIObject() throws ParserException {
        String s = peekToken();
        if (isIndividualName(s)) {
            return parseIIndividualObject();
        }
        else if (s.equals("?")) {
            return parseIVariable();
        }
        else {
            consumeToken();
            throw createException(false, false, false, true, false, false, "?$var$");
        }
    }

    public IRI getVariableIRI(String var) {
        if (var.startsWith("<") || var.endsWith(">")) {
            return IRI.create(var.substring(1, var.length() - 1));
        }
        else {
            return IRI.create(base + var);
        }

    }

    public SWRLVariable parseIVariable() throws ParserException {
        IRI var = parseVariable();
        return dataFactory.getSWRLVariable(var);
    }


    public SWRLIndividualArgument parseIIndividualObject() throws ParserException {
        OWLIndividual ind = parseIndividual();
        return dataFactory.getSWRLIndividualArgument(ind);
    }


    public IRI parseVariable() throws ParserException {
        consumeToken("?");
        return parseIRI();
    }


    public SWRLDArgument parseDObject() throws ParserException {
        String s = peekToken();
        if (s.equals("?")) {
            return parseDVariable();
        }
        else {
            try {
                return parseLiteralObject();
            }
            catch (ParserException e) {
                Set<String> kw = new HashSet<String>(e.getExpectedKeywords());
                kw.add("?");
                throw new ParserException(e.getTokenSequence(), e.getStartPos(), e.getLineNumber(), e.getColumnNumber(), e.isClassNameExpected(), e.isObjectPropertyNameExpected(), e.isDataPropertyNameExpected(), e.isIndividualNameExpected(), e.isDatatypeNameExpected(), e.isAnnotationPropertyNameExpected(), kw);
            }
        }
    }


    public SWRLVariable parseDVariable() throws ParserException {
        IRI var = parseVariable();
        return dataFactory.getSWRLVariable(var);
    }


    public SWRLLiteralArgument parseLiteralObject() throws ParserException {
        OWLLiteral lit = parseLiteral();
        return dataFactory.getSWRLLiteralArgument(lit);
    }


    public SWRLBuiltInAtom parseBuiltInAtom() throws ParserException {
        String predicate = consumeToken();
        consumeToken("(");
        SWRLBuiltInsVocabulary v = null;
        IRI iri = null;
        if (!ruleBuiltIns.containsKey(predicate)) {
            iri = getIRI(predicate);
        }
        else {
            v = ruleBuiltIns.get(predicate);
            iri = v.getIRI();
        }

        List<SWRLDArgument> args = new ArrayList<SWRLDArgument>();

        if (v != null && v.getMaxArity() >= 0) {
            // We know the arity!
            for (int i = 0; i < v.getMaxArity(); i++) {
                SWRLDArgument obj = parseDObject();
                args.add(obj);
                // parse at least the minumum arity
                if (i < v.getMinArity() - 1) {
                    consumeToken(",");
                }
                else if (i < v.getMaxArity() - 1) {
                    if (peekToken().equals(",")) {
                        consumeToken();
                    }
                    else {
                        break;
                    }
                }
            }
        }
        else {
            // Unknown arity so just parse as many arguments as we can
            String sep = ",";
            while (sep.equals(",")) {
                SWRLDArgument arg = parseDObject();
                args.add(arg);
                sep = peekToken();
                if (sep.equals(",")) {
                    consumeToken();
                }
            }
        }

        consumeToken(")");
        return dataFactory.getSWRLBuiltInAtom(iri, args);
    }


    public Set<OntologyAxiomPair> parseDisjointClasses() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(DISJOINT_CLASSES)) {
            throw createException(DISJOINT_CLASSES);
        }
        Set<OWLOntology> ontologies = getOntologies();
        Set<OWLAnnotation> annotations;
        if (peekToken().equals(ANNOTATIONS)) {
            consumeToken();
            annotations = parseAnnotationList();
        }
        else {
            annotations = Collections.emptySet();
        }
        Set<OWLClassExpression> classExpressions = parseClassExpressionList(false);
        Set<OntologyAxiomPair> pairs = new HashSet<OntologyAxiomPair>();
        for (OWLOntology ont : ontologies) {
            pairs.add(new OntologyAxiomPair(ont, dataFactory.getOWLDisjointClassesAxiom(classExpressions, annotations)));
        }
        return pairs;
    }


    public Set<OntologyAxiomPair> parseSameIndividual() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(SAME_INDIVIDUAL)) {
            throw createException(SAME_INDIVIDUAL);
        }
        Set<OWLIndividual> individuals = parseIndividualList();
        Set<OWLOntology> ontologies = getOntologies();
        Set<OWLAnnotation> annotations;
        if (peekToken().equals(ANNOTATIONS)) {
            annotations = parseAnnotationList();
        }
        else {
            annotations = Collections.emptySet();
        }
        Set<OntologyAxiomPair> pairs = new HashSet<OntologyAxiomPair>();
        for (OWLOntology ont : ontologies) {
            pairs.add(new OntologyAxiomPair(ont, dataFactory.getOWLSameIndividualAxiom(individuals, annotations)));
        }
        return pairs;
    }


    public Set<OntologyAxiomPair> parseDisjointProperties() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(DISJOINT_PROPERTIES)) {
            throw createException(DISJOINT_PROPERTIES);
        }
        Set<OWLOntology> ontologies = getOntologies();
        Set<OWLAnnotation> annotations;
        if (peekToken().equalsIgnoreCase(ANNOTATIONS)) {
            consumeToken();
            annotations = parseAnnotationList();
        }
        else {
            annotations = Collections.emptySet();
        }
        Set<OWLPropertyExpression> props = parsePropertyList();
        Set<OntologyAxiomPair> pairs = new HashSet<OntologyAxiomPair>();
        OWLAxiom propertiesAxiom;
        if (props.iterator().next().isObjectPropertyExpression()) {
            Set<OWLObjectPropertyExpression> ope = new HashSet<OWLObjectPropertyExpression>();
            for (OWLPropertyExpression pe : props) {
                ope.add((OWLObjectPropertyExpression) pe);
            }
            propertiesAxiom = dataFactory.getOWLDisjointObjectPropertiesAxiom(ope, annotations);
        }
        else {
            Set<OWLDataPropertyExpression> dpe = new HashSet<OWLDataPropertyExpression>();
            for (OWLPropertyExpression pe : props) {
                dpe.add((OWLDataPropertyExpression) pe);
            }
            propertiesAxiom = dataFactory.getOWLDisjointDataPropertiesAxiom(dpe, annotations);
        }
        for (OWLOntology ont : ontologies) {
            pairs.add(new OntologyAxiomPair(ont, propertiesAxiom));
        }
        return pairs;
    }


//    public Set<OntologyAxiomPair> parseDisjointDataProperties() throws ParserException {
//        String section = consumeToken();
//        if (!section.equalsIgnoreCase(DISJOINT_PROPERTIES)) {
//            throw createException(DISJOINT_PROPERTIES);
//        }
//        Set<OWLOntology> ontologies = getOntologies();
//        Set<OWLAnnotation> annotations;
//        if(peekToken().equalsIgnoreCase(ANNOTATIONS)) {
//            consumeToken();
//            annotations = parseAnnotationList();
//        }
//        else {
//            annotations = Collections.emptySet();
//        }
//        Set<OWLDataProperty> props = parseDataPropertyList();
//        Set<OntologyAxiomPair> pairs = new HashSet<OntologyAxiomPair>();
//        for(OWLOntology ont : ontologies) {
//            pairs.add(new OntologyAxiomPair(ont, dataFactory.getOWLDisjointDataPropertiesAxiom(props, annotations)));
//        }
//        return pairs;
//    }


    public Set<OntologyAxiomPair> parseDifferentIndividuals() throws ParserException {
        String section = consumeToken();
        if (!section.equalsIgnoreCase(DIFFERENT_INDIVIDUALS)) {
            throw createException(DIFFERENT_INDIVIDUALS);
        }
        Set<OWLOntology> ontologies = getOntologies();
        Set<OWLAnnotation> annotations;
        if (peekToken().equalsIgnoreCase(ANNOTATIONS)) {
            consumeToken();
            annotations = parseAnnotationList();
        }
        else {
            annotations = Collections.emptySet();
        }

        Set<OWLIndividual> individuals = parseIndividualList();
        Set<OntologyAxiomPair> pairs = new HashSet<OntologyAxiomPair>();
        for (OWLOntology ontology : ontologies) {
            pairs.add(new OntologyAxiomPair(ontology, dataFactory.getOWLDifferentIndividualsAxiom(individuals, annotations)));
        }
        return pairs;
    }


    public Set<OWLAxiom> parseObjectPropertyCharacteristicList(OWLObjectPropertyExpression prop) throws ParserException {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        String sep = ",";
        while (sep.equals(",")) {
            String next = peekToken();
            Set<OWLAnnotation> annos;
            if (next.equals(ANNOTATIONS)) {
                consumeToken();
                annos = parseAnnotationList();
            }
            else {
                annos = Collections.emptySet();
            }
            parseObjectPropertyCharacteristic(prop);
            sep = peekToken();
            if (sep.equals(",")) {
                sep = consumeToken();
            }
        }
        return axioms;
    }

    private OWLObjectPropertyCharacteristicAxiom parseObjectPropertyCharacteristic(OWLObjectPropertyExpression prop) throws ParserException {
        String characteristic = consumeToken();
        if (characteristic.equalsIgnoreCase(FUNCTIONAL)) {
            return dataFactory.getOWLFunctionalObjectPropertyAxiom(prop);
        }
        else if (characteristic.equalsIgnoreCase(INVERSE_FUNCTIONAL)) {
            return dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(prop);
        }
        else if (characteristic.equalsIgnoreCase(SYMMETRIC)) {
            return dataFactory.getOWLSymmetricObjectPropertyAxiom(prop);
        }
        else if (characteristic.equalsIgnoreCase(ANTI_SYMMETRIC) || characteristic.equalsIgnoreCase(ASYMMETRIC)) {
            return dataFactory.getOWLAsymmetricObjectPropertyAxiom(prop);
        }
        else if (characteristic.equalsIgnoreCase(TRANSITIVE)) {
            return dataFactory.getOWLTransitiveObjectPropertyAxiom(prop);
        }
        else if (characteristic.equalsIgnoreCase(REFLEXIVE)) {
            return dataFactory.getOWLReflexiveObjectPropertyAxiom(prop);
        }
        else if (characteristic.equalsIgnoreCase(IRREFLEXIVE)) {
            return dataFactory.getOWLIrreflexiveObjectPropertyAxiom(prop);
        }
        else {
            throw createException(FUNCTIONAL, INVERSE_FUNCTIONAL, SYMMETRIC, ANTI_SYMMETRIC, TRANSITIVE, REFLEXIVE, IRREFLEXIVE);
        }
    }


    private OWLDataPropertyCharacteristicAxiom parseDataPropertyCharacteristic(OWLDataPropertyExpression prop) throws ParserException {
        String characteristic = consumeToken();
        if (characteristic.equalsIgnoreCase(FUNCTIONAL)) {
            return dataFactory.getOWLFunctionalDataPropertyAxiom(prop);
        }
        else {
            throw createException(FUNCTIONAL);
        }
    }

    public Map<OWLClassExpression, Set<Set<OWLAnnotation>>> parseAnnotatedClassExpressionList() throws ParserException {
        Map<OWLClassExpression, Set<Set<OWLAnnotation>>> descs = new HashMap<OWLClassExpression, Set<Set<OWLAnnotation>>>();
        String sep = ",";
        while (sep.equals(",")) {
            potentialKeywords.remove(",");
            String next = peekToken();
            Set<OWLAnnotation> annos;
            if (next.equals(ANNOTATIONS)) {
                consumeToken();
                annos = parseAnnotationList();
            }
            else {
                annos = Collections.emptySet();
            }
            OWLClassExpression classExpression = parseIntersection();
            Set<Set<OWLAnnotation>> annosSet = descs.get(classExpression);
            if(annosSet == null) {
                annosSet = new HashSet<Set<OWLAnnotation>>();
                descs.put(classExpression, annosSet);
            }
            annosSet.add(annos);
            potentialKeywords.add(",");
            sep = peekToken();
            if (sep.equals(",")) {
                sep = consumeToken();
            }
        }
        return descs;
    }

    public Set<OWLClassExpression> parseClassExpressionList(boolean allowAnnotations) throws ParserException {
        Set<OWLClassExpression> descs = new HashSet<OWLClassExpression>();
        String sep = ",";
        while (sep.equals(",")) {
            potentialKeywords.remove(",");
            String next = peekToken();
            if (next.equals(ANNOTATIONS)) {
                consumeToken();
                Set<OWLAnnotation> annos = parseAnnotationList();
            }
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
            throw createException(expectedOpen);
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
            throw createException(expectedClose);
        }
        return descs;
    }


    public Set<OWLDataProperty> parseDataPropertyList() throws ParserException {
        Set<OWLDataProperty> props = new HashSet<OWLDataProperty>();
        String sep = ",";
        while (sep.equals(",")) {
            OWLDataProperty prop = parseDataProperty();
            props.add(prop);
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        return props;
    }

    public Map<OWLDataProperty, Set<OWLAnnotation>> parseAnnotatedDataPropertyList() throws ParserException {
        Map<OWLDataProperty, Set<OWLAnnotation>> props = new HashMap<OWLDataProperty, Set<OWLAnnotation>>();
        String sep = ",";
        while (sep.equals(",")) {
            String next = peekToken();
            Set<OWLAnnotation> annos;
            if (next.equals(ANNOTATIONS)) {
                consumeToken();
                annos = parseAnnotationList();
            }
            else {
                annos = Collections.emptySet();
            }
            OWLDataProperty prop = parseDataProperty();
            props.put(prop, annos);
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        return props;
    }

    public Set<OWLAnnotationProperty> parseAnnotationPropertyList() throws ParserException {
        Set<OWLAnnotationProperty> props = new HashSet<OWLAnnotationProperty>();
        String sep = ",";
        while (sep.equals(",")) {
            sep = peekToken();
            OWLAnnotationProperty prop = parseAnnotationProperty();
            props.add(prop);
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        return props;
    }

    public Map<OWLPropertyExpression, Set<OWLAnnotation>> parseAnnotatedPropertyList() throws ParserException {
        Map<OWLPropertyExpression, Set<OWLAnnotation>> props = new HashMap<OWLPropertyExpression, Set<OWLAnnotation>>();
        String sep = ",";
        while (sep.equals(",")) {
            String next = peekToken();
            Set<OWLAnnotation> annos;
            if (next.equals(ANNOTATIONS)) {
                consumeToken();
                annos = parseAnnotationList();
            }
            else {
                annos = Collections.emptySet();
            }
            OWLPropertyExpression prop = parsePropertyExpression();
            props.put(prop, annos);
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        return props;
    }

    public Set<OWLPropertyExpression> parsePropertyList() throws ParserException {
        Set<OWLPropertyExpression> props = new HashSet<OWLPropertyExpression>();
        String sep = ",";
        while (sep.equals(",")) {
            OWLPropertyExpression prop = parsePropertyExpression();
            props.add(prop);
            sep = peekToken();
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

    public Map<OWLObjectPropertyExpression, Set<OWLAnnotation>> parseAnnotatedObjectPropertyList() throws ParserException {
        Map<OWLObjectPropertyExpression, Set<OWLAnnotation>> props = new HashMap<OWLObjectPropertyExpression, Set<OWLAnnotation>>();
        String sep = ",";
        while (sep.equals(",")) {
            String next = peekToken();
            Set<OWLAnnotation> annos;
            if (next.equals(ANNOTATIONS)) {
                consumeToken();
                annos = parseAnnotationList();
            }
            else {
                annos = Collections.emptySet();
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            props.put(prop, annos);
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
            throw createException("->", "o");
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
        }
        else if (kw.equalsIgnoreCase(ManchesterOWLSyntax.EQUIVALENT_TO.toString())) {
            OWLClassExpression rhs = parseIntersection();
            return dataFactory.getOWLEquivalentClassesAxiom(lhs, rhs);
        }
        else if (kw.equalsIgnoreCase(ManchesterOWLSyntax.DISJOINT_WITH.toString())) {
            OWLClassExpression rhs = parseIntersection();
            return dataFactory.getOWLDisjointClassesAxiom(lhs, rhs);
        }
        throw createException(ManchesterOWLSyntax.SUBCLASS_OF.toString(), ManchesterOWLSyntax.EQUIVALENT_TO.toString(), ManchesterOWLSyntax.DISJOINT_WITH.toString());
    }


    public OWLObjectPropertyAxiom parseObjectPropertyAxiom() throws ParserException {

        String tok = peekToken();
        if (tok.equals(ManchesterOWLSyntax.FUNCTIONAL.toString())) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throw createException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throw createException(")");
            }
            return dataFactory.getOWLFunctionalObjectPropertyAxiom(prop);
        }
        else if (tok.equals(ManchesterOWLSyntax.INVERSE_FUNCTIONAL.toString())) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throw createException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throw createException(")");
            }
            return dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(prop);
        }
        else if (tok.equals(ManchesterOWLSyntax.TRANSITIVE.toString())) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throw createException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throw createException(")");
            }
            return dataFactory.getOWLTransitiveObjectPropertyAxiom(prop);
        }
        else if (tok.equals(ManchesterOWLSyntax.SYMMETRIC.toString())) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throw createException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throw createException(")");
            }
            return dataFactory.getOWLSymmetricObjectPropertyAxiom(prop);
        }
        else if (tok.equals(ManchesterOWLSyntax.REFLEXIVE.toString())) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throw createException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throw createException(")");
            }
            return dataFactory.getOWLReflexiveObjectPropertyAxiom(prop);
        }
        else if (tok.equals(ManchesterOWLSyntax.IRREFLEXIVE.toString())) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throw createException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throw createException(")");
            }
            return dataFactory.getOWLIrreflexiveObjectPropertyAxiom(prop);
        }
        else if (tok.equals(ManchesterOWLSyntax.ASYMMETRIC.toString())) {
            consumeToken();
            String open = consumeToken();
            if (!open.equals("(")) {
                throw createException("(");
            }
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            String close = consumeToken();
            if (!close.equals(")")) {
                throw createException(")");
            }
            return dataFactory.getOWLAsymmetricObjectPropertyAxiom(prop);
        }
        return null;
    }


    public OWLIndividual parseIndividual() throws ParserException {
        String name = consumeToken();
        if (!isIndividualName(name) && !name.startsWith("_:")) {
            throw createException(false, false, false, true);
        }
        return getOWLIndividual(name);
    }


    public OWLIndividual parseIndividual(boolean defined) throws ParserException {
        String name = consumeToken();
        if (defined && !isIndividualName(name)) {
            throw createException(false, false, false, true);
        }
        return getOWLIndividual(name);
    }


    public OWLDataProperty parseDataProperty() throws ParserException {
        String name = consumeToken();
        if (!isDataPropertyName(name)) {
            throw createException(false, false, true, false);
        }
        return getOWLDataProperty(name);
    }


    public OWLAnnotationProperty parseAnnotationProperty() throws ParserException {
        String name = consumeToken();
        if (!isAnnotationPropertyName(name)) {
            throw createException(false, false, false, false, false, true);
        }
        return getOWLAnnotationProperty(name);
    }


    public Map<String, IRI> parsePrefixDeclaration() throws ParserException {
        consumeToken(PREFIX);
        Map<String, IRI> map = new HashMap<String, IRI>(2);
        String prefixName = consumeToken();
        // Handle legacy = character if necessart
        String delim = peekToken();
        if (delim.equals("=")) {
            // Legacy
            consumeToken();
        }
        IRI iri = parseIRI();
        map.put(prefixName, iri);
        return map;
    }


    public OWLImportsDeclaration parseImportsDeclaration(OWLOntology ont) throws ParserException {
        consumeToken(IMPORT);
        IRI importedOntologyIRI = parseIRI();
        return dataFactory.getOWLImportsDeclaration(importedOntologyIRI);
    }


    public IRI parseIRI() throws ParserException {
        String iriString = consumeToken();
        if (!(iriString.startsWith("<") && iriString.endsWith(">"))) {
            throw createException("<$IRI$>");
        }
        return IRI.create(iriString.substring(1, iriString.length() - 1));
    }


    public Set<IRI> parseNameList() throws ParserException {
        String sep = ",";
        Set<IRI> iris = new HashSet<IRI>();
        while (sep.equals(",")) {
            potentialKeywords.clear();
            String token = peekToken();
            if (isClassName(token)) {
                iris.add(owlEntityChecker.getOWLClass(token).getIRI());
                consumeToken();
            }
            else if (isObjectPropertyName(token)) {
                iris.add(owlEntityChecker.getOWLObjectProperty(token).getIRI());
                consumeToken();
            }
            else if (isDataPropertyName(token)) {
                iris.add(owlEntityChecker.getOWLDataProperty(token).getIRI());
                consumeToken();
            }
            else if (isIndividualName(token)) {
                iris.add(owlEntityChecker.getOWLIndividual(token).getIRI());
                consumeToken();
            }
            else if (isAnnotationPropertyName(token)) {
                iris.add(owlEntityChecker.getOWLAnnotationProperty(token).getIRI());
                consumeToken();
            }
            else if (isDatatypeName(token)) {
                iris.add(owlEntityChecker.getOWLDatatype(token).getIRI());
                consumeToken();
            }
            else if (isOntologyName(token)) {
                iris.add(getOntology(token).getOntologyID().getOntologyIRI());
                consumeToken();
            }
            else if (token.equals("<")) {
                IRI iri = parseIRI();
                iris.add(iri);
            }
            else {
                throw createException(true, true, true, true, true, true, "<$URI$>");
            }
            sep = peekToken();
            if (sep.equals(",")) {
                consumeToken();
            }
        }
        return iris;
    }


    private void processDeclaredEntities() {
        for (int i = 0; i < tokens.size(); i++) {
            ManchesterOWLSyntaxTokenizer.Token tok = tokens.get(i);
            if (tok.getToken().equalsIgnoreCase(CLASS)) {
                if (i + 1 < tokens.size()) {
                    classNames.add(tokens.get(i + 1).getToken());
                }
            }
            else if (tok.getToken().equalsIgnoreCase(OBJECT_PROPERTY)) {
                if (i + 1 < tokens.size()) {
                    objectPropertyNames.add(tokens.get(i + 1).getToken());
                }
            }
            else if (tok.getToken().equalsIgnoreCase(DATA_PROPERTY)) {
                if (i + 1 < tokens.size()) {
                    dataPropertyNames.add(tokens.get(i + 1).getToken());
                }
            }
            else if (tok.getToken().equalsIgnoreCase(INDIVIDUAL)) {
                if (i + 1 < tokens.size()) {
                    individualNames.add(tokens.get(i + 1).getToken());
                }
            }
            else if (tok.getToken().equalsIgnoreCase(DATATYPE)) {
                if (i + 1 < tokens.size()) {
                    dataTypeNames.add(tokens.get(i + 1).getToken());
                }
            }
            else if (tok.getToken().equalsIgnoreCase(ANNOTATION_PROPERTY)) {
                if (i + 1 < tokens.size()) {
                    annotationPropertyNames.add(tokens.get(i + 1).getToken());
                }
            }
            else if (tok.getToken().equalsIgnoreCase(VALUE_PARTITION)) {
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


    public ManchesterOWLSyntaxOntologyFormat parseOntology(OWLOntologyManager manager, OWLOntology ont) throws ParserException, UnloadableImportException {
        Set<OntologyAxiomPair> axioms = new HashSet<OntologyAxiomPair>();
        OWLOntologyID ontologyID = new OWLOntologyID();
        Set<AddImport> imports = new HashSet<AddImport>();
        Set<AddOntologyAnnotation> ontologyAnnotations = new HashSet<AddOntologyAnnotation>();
        this.defaultOntology = ont;
        processDeclaredEntities();
        while (true) {
            String section = peekToken();
            if (section.equals(ONTOLOGY)) {
                ManchesterOWLSyntaxOntologyHeader header = parseOntologyHeader(false);
                for (OWLImportsDeclaration decl : header.getImportsDeclarations()) {
                    manager.makeLoadImportRequest(decl, configuration);
                    imports.add(new AddImport(ont, decl));
                }
                for (OWLAnnotation anno : header.getAnnotations()) {
                    ontologyAnnotations.add(new AddOntologyAnnotation(ont, anno));
                }
                ontologyID = header.getOntologyID();
            }
            else if (section.equalsIgnoreCase(CLASS)) {
                axioms.addAll(parseClassFrame());
            }
            else if (section.equalsIgnoreCase(OBJECT_PROPERTY)) {
                axioms.addAll(parseObjectPropertyFrame());
            }
            else if (section.equalsIgnoreCase(DATA_PROPERTY)) {
                axioms.addAll(parseDataPropertyFrame());
            }
            else if (section.equalsIgnoreCase(INDIVIDUAL)) {
                axioms.addAll(parseIndividualFrame());
            }
            else if (section.equalsIgnoreCase(DATATYPE)) {
                axioms.addAll(parseDatatypeFrame());
            }
            else if (section.equalsIgnoreCase(ANNOTATION_PROPERTY)) {
                axioms.addAll(parseAnnotationPropertyFrame());
            }
            else if (section.equalsIgnoreCase(VALUE_PARTITION)) {
                axioms.addAll(parseValuePartitionFrame());
            }
            else if (section.equalsIgnoreCase(IMPORT)) {
                OWLImportsDeclaration decl = parseImportsDeclaration(ont);
                imports.add(new AddImport(ont, decl));
                manager.makeLoadImportRequest(decl, configuration);
            }
            else if (section.equalsIgnoreCase(PREFIX)) {
                Map<String, IRI> nsMap = parsePrefixDeclaration();
                for (String ns : nsMap.keySet()) {
                    pm.setPrefix(ns, nsMap.get(ns).toString());
                }
            }
            else if (section.equalsIgnoreCase(DISJOINT_CLASSES)) {
                axioms.addAll(parseDisjointClasses());
            }
            else if (section.equalsIgnoreCase(EQUIVALENT_CLASSES)) {
                axioms.addAll(parseNaryEquivalentClasses());
            }
            else if (section.equalsIgnoreCase(EQUIVALENT_PROPERTIES)) {
                axioms.addAll(parseNaryEquivalentProperties());
            }
            else if (section.equalsIgnoreCase(DISJOINT_PROPERTIES)) {
                axioms.addAll(parseDisjointProperties());
            }
            else if (section.equalsIgnoreCase(DIFFERENT_INDIVIDUALS)) {
                axioms.addAll(parseDifferentIndividuals());
            }
            else if (section.equalsIgnoreCase(SAME_INDIVIDUAL)) {
                axioms.addAll(parseSameIndividual());
            }
            else if (section.equalsIgnoreCase(RULE)) {
                axioms.addAll(parseRuleFrame());
            }
            else if (section.equals(ManchesterOWLSyntaxTokenizer.EOF)) {
                break;
            }
            else {
                consumeToken();
                throw createException(CLASS, OBJECT_PROPERTY, DATA_PROPERTY, INDIVIDUAL, DATATYPE, ANNOTATION_PROPERTY, IMPORT, VALUE_PARTITION, PREFIX, EQUIVALENT_CLASSES, DISJOINT_CLASSES, DISJOINT_PROPERTIES, DIFFERENT_INDIVIDUALS, SAME_INDIVIDUAL);
            }
        }

        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>(axioms.size());
        changes.addAll(imports);
        changes.addAll(ontologyAnnotations);
        for (OntologyAxiomPair pair : axioms) {
            changes.add(new AddAxiom(ont, pair.getAxiom()));
        }
        changes.add(new SetOntologyID(ont, ontologyID));
        manager.applyChanges(changes);
        ManchesterOWLSyntaxOntologyFormat format = new ManchesterOWLSyntaxOntologyFormat();
        format.copyPrefixesFrom(pm);
        return format;
    }


    public ManchesterOWLSyntaxOntologyHeader parseOntologyHeader(boolean toEOF) throws ParserException {
        String tok = consumeToken();
        if (!tok.equalsIgnoreCase(ONTOLOGY)) {
            throw createException(ONTOLOGY);
        }
        IRI ontologyIRI = null;
        IRI versionIRI = null;
        if (peekToken().startsWith("<")) {
            ontologyIRI = parseIRI();
            if (peekToken().startsWith("<")) {
                versionIRI = parseIRI();
            }
        }
        Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
        Set<OWLImportsDeclaration> imports = new HashSet<OWLImportsDeclaration>();
        while (true) {
            String section = peekToken();
            if (section.equals(IMPORT)) {
                consumeToken();
                tok = peekToken();
                IRI importedIRI = null;
                if (tok.startsWith("<")) {
                    importedIRI = parseIRI();
                }
                else if (isOntologyName(tok)) {
                    consumeToken();
                    OWLOntology ont = getOntology(tok);
                    if (ont != null) {
                        importedIRI = ont.getOntologyID().getOntologyIRI();
                    }
                }
                else {
                    consumeToken();
                    throwOntologyNameOrURIExpectedException();
                }
                imports.add(dataFactory.getOWLImportsDeclaration(importedIRI));
            }
            else if (section.equals(ANNOTATIONS)) {
                consumeToken();
                annotations.addAll(parseAnnotationList());
            }
            else if (section.equalsIgnoreCase(ManchesterOWLSyntaxTokenizer.EOF)) {
                break;
            }
            else if (toEOF) {
                throw createException(IMPORT, ANNOTATIONS);
            }
            else {
                break;
            }
        }
        return new ManchesterOWLSyntaxOntologyHeader(ontologyIRI, versionIRI, annotations, imports);
    }


    protected ParserException createException(boolean ontologyNameExpected) throws ParserException {
        ManchesterOWLSyntaxTokenizer.Token lastToken = getLastToken();
        return new ParserException(getTokenSequence(), lastToken.getPos(), lastToken.getRow(), lastToken.getCol(), ontologyNameExpected);
    }


    protected void throwOntologyNameOrURIExpectedException() throws ParserException {
        ManchesterOWLSyntaxTokenizer.Token lastToken = getLastToken();
        throw new ParserException(getTokenSequence(), lastToken.getPos(), lastToken.getRow(), lastToken.getCol(), true, "<$ONTOLOGYYURI$>");
    }


    protected ParserException createException(String... keywords) throws ParserException {
        Set<String> theKeywords = new HashSet<String>();
        theKeywords.addAll(Arrays.asList(keywords));
        theKeywords.addAll(potentialKeywords);
        potentialKeywords.clear();
        ManchesterOWLSyntaxTokenizer.Token lastToken = getLastToken();
        return new ParserException(getTokenSequence(), lastToken.getPos(), lastToken.getRow(), lastToken.getCol(), false, false, false, false, false, false, theKeywords);
    }


    protected ParserException createException(boolean classNameExpected, boolean objectPropertyNameExpected, boolean dataPropertyNameExpected, boolean individualNameExpected, boolean datatypeNameExpected, boolean annotationPropertyNameExpected, String... keywords) throws ParserException {
        Set<String> theKeywords = new HashSet<String>();
        theKeywords.addAll(Arrays.asList(keywords));
        if (objectPropertyNameExpected) {
            theKeywords.add(INVERSE);
        }
        theKeywords.addAll(potentialKeywords);
        potentialKeywords.clear();
        ManchesterOWLSyntaxTokenizer.Token lastToken = getLastToken();
        return new ParserException(getTokenSequence(), lastToken.getPos(), lastToken.getRow(), lastToken.getCol(), classNameExpected, objectPropertyNameExpected, dataPropertyNameExpected, individualNameExpected, datatypeNameExpected, annotationPropertyNameExpected, theKeywords);
    }


    protected ParserException createException(boolean classNameExpected, boolean objectPropertyNameExpected, boolean dataPropertyNameExpected, boolean individualNameExpected) throws ParserException {
        Set<String> keywords = new HashSet<String>();
        if (objectPropertyNameExpected) {
            keywords.add(INVERSE);
        }
        keywords.addAll(potentialKeywords);
        potentialKeywords.clear();
        ManchesterOWLSyntaxTokenizer.Token lastToken = getLastToken();
        return new ParserException(getTokenSequence(), lastToken.getPos(), lastToken.getRow(), lastToken.getCol(), classNameExpected, objectPropertyNameExpected, dataPropertyNameExpected, individualNameExpected, false, false, keywords);
    }


    protected List<String> getTokenSequence() {
        List<String> seq = new ArrayList<String>();
        int index = tokenIndex - 1;
        if (index < 0) {
            index = 0;
        }
        while (index < tokens.size() && seq.size() < 4 && seq.indexOf(ManchesterOWLSyntaxTokenizer.EOF) == -1) {
            seq.add(tokens.get(index).getToken());
            index++;
        }
        if (seq.size() == 0) {
            seq.add(ManchesterOWLSyntaxTokenizer.EOF);
        }
        return seq;
    }


    private class DefaultEntityChecker implements OWLEntityChecker {

        private Map<String, OWLDatatype> dataTypeNameMap;


        public DefaultEntityChecker() {
            dataTypeNameMap = new HashMap<String, OWLDatatype>();
            for (XSDVocabulary v : XSDVocabulary.values()) {
                dataTypeNameMap.put(v.getIRI().getFragment(), dataFactory.getOWLDatatype(v.getIRI()));
                dataTypeNameMap.put("xsd:" + v.getIRI().getFragment(), dataFactory.getOWLDatatype(v.getIRI()));
            }
        }


        public OWLClass getOWLClass(String name) {
            if (name.equals("Thing") || name.equals("owl:Thing")) {
                return dataFactory.getOWLThing();
            }
            else if (name.equals("Nothing") || name.equals("owl:Nothing")) {
                return dataFactory.getOWLNothing();
            }
            else if (classNames.contains(name)) {
                return dataFactory.getOWLClass(getIRI(name));
            }
            else {
                return null;
            }
        }


        public OWLObjectProperty getOWLObjectProperty(String name) {
            if (objectPropertyNames.contains(name)) {
                return dataFactory.getOWLObjectProperty(getIRI(name));
            }
            else {
                return null;
            }
        }


        public OWLDataProperty getOWLDataProperty(String name) {
            if (dataPropertyNames.contains(name)) {
                return dataFactory.getOWLDataProperty(getIRI(name));
            }
            else {
                return null;
            }
        }


        public OWLNamedIndividual getOWLIndividual(String name) {
            if (individualNames.contains(name)) {
                return dataFactory.getOWLNamedIndividual(getIRI(name));
            }
            else {
                return null;
            }
        }


        public OWLDatatype getOWLDatatype(String name) {
            if (dataTypeNames.contains(name)) {
                return dataFactory.getOWLDatatype(getIRI(name));
            }
            else {
                return null;
            }
        }


        public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
            if (annotationPropertyNames.contains(name)) {
                return dataFactory.getOWLAnnotationProperty(getIRI(name));
            }
            else {
                return null;
            }
        }
    }


    private Map<String, IRI> nameIRIMap = new HashMap<String, IRI>();


    public IRI getIRI(String name) {
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
        }
        else {
            int colonIndex = name.indexOf(':');
            if (colonIndex == -1) {
                name = ":" + name;
            }
            uri = pm.getIRI(name);
        }
        nameIRIMap.put(name, uri);
        return uri;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Parsing "Inline" Axioms
    //


	public OWLAxiom parseAxiom() throws ParserException {
		String token = peekToken();
		if (isClassName(token)) {
			return parseAxiomWithClassExpressionStart();
		} else if (isObjectPropertyName(token)) {
			return parseAxiomWithObjectPropertyStart();
		} else if (isDataPropertyName(token)) {
			return parseAxiomWithDataPropertyStart();
		} else if (isIndividualName(token)) {
		} else if (token.equalsIgnoreCase("inv")) {
			return parseAxiomWithObjectPropertyStart();
		} else if (token.equalsIgnoreCase("(")) {
			return parseAxiomWithClassExpressionStart();
		} else if (token.equalsIgnoreCase("{")) {
			return parseAxiomWithClassExpressionStart();
		} else if (token.equalsIgnoreCase(FUNCTIONAL)) {
			return parseFunctionPropertyAxiom();
		} else if (token.equalsIgnoreCase(INVERSE_FUNCTIONAL)) {
			return parseInverseFunctionalPropertyAxiom();
		} else if (token.equalsIgnoreCase(SYMMETRIC)) {
			return parseSymmetricPropertyAxiom();
		} else if (token.equalsIgnoreCase(ASYMMETRIC)) {
			return parseAsymmetricPropertyAxiom();
		} else if (token.equalsIgnoreCase(TRANSITIVE)) {
			return parseTransitivePropertyAxiom();
		} else if (token.equalsIgnoreCase(REFLEXIVE)) {
			return parseReflexivePropertyAxiom();
		} else if (token.equalsIgnoreCase(IRREFLEXIVE)) {
			return parseIrreflexivePropertyAxiom();
		} else {
			throw createException(true, true, true, true, false, false, "(",
					"{", "inv", FUNCTIONAL, INVERSE_FUNCTIONAL, SYMMETRIC,
					ASYMMETRIC, TRANSITIVE, REFLEXIVE, IRREFLEXIVE);
		}
		return null;
	}

	public OWLAxiom parseAxiomWithDataPropertyStart() throws ParserException {
		OWLDataPropertyExpression prop = parseDataProperty();
		String kw = consumeToken();
		if (kw.equalsIgnoreCase(SOME)) {
			OWLDataRange dataRange = parseDataIntersectionOf();
			return parseClassAxiomRemainder(getDataFactory()
					.getOWLDataSomeValuesFrom(prop, dataRange));
		} else if (kw.equalsIgnoreCase(ONLY)) {
			OWLDataRange dataRange = parseDataIntersectionOf();
			return parseClassAxiomRemainder(getDataFactory()
					.getOWLDataAllValuesFrom(prop, dataRange));
		} else if (kw.equalsIgnoreCase(MIN)) {
			int cardi = parseInteger();
			OWLDataRange dataRange = parseDataIntersectionOf();
			return parseClassAxiomRemainder(getDataFactory()
					.getOWLDataMinCardinality(cardi, prop, dataRange));
		} else if (kw.equalsIgnoreCase(MAX)) {
			int cardi = parseInteger();
			OWLDataRange dataRange = parseDataIntersectionOf();
			return parseClassAxiomRemainder(getDataFactory()
					.getOWLDataMaxCardinality(cardi, prop, dataRange));
		} else if (kw.equalsIgnoreCase(EXACTLY)) {
			int cardi = parseInteger();
			OWLDataRange dataRange = parseDataIntersectionOf();
			return parseClassAxiomRemainder(getDataFactory()
					.getOWLDataExactCardinality(cardi, prop, dataRange));
		} else if (kw.equalsIgnoreCase(SUB_PROPERTY_OF)) {
			OWLDataPropertyExpression superProperty = parseDataPropertyExpression();
			return getDataFactory().getOWLSubDataPropertyOfAxiom(prop,
					superProperty);
		} else if (kw.equalsIgnoreCase(EQUIVALENT_TO)) {
			OWLDataPropertyExpression equivProp = parseDataPropertyExpression();
			return getDataFactory().getOWLEquivalentDataPropertiesAxiom(prop,
					equivProp);
		} else if (kw.equalsIgnoreCase(DISJOINT_WITH)) {
			OWLDataPropertyExpression disjProp = parseDataPropertyExpression();
			return getDataFactory().getOWLDisjointDataPropertiesAxiom(prop,
					disjProp);
		} else if (kw.equals(DOMAIN)) {
			OWLClassExpression domain = parseClassExpression();
			return getDataFactory().getOWLDataPropertyDomainAxiom(prop, domain);
		} else if (kw.equals(RANGE)) {
			OWLDataRange range = parseDataRange();
			return getDataFactory().getOWLDataPropertyRangeAxiom(prop, range);
		} else {
			throw createException(SOME, ONLY, MIN, MAX, EXACTLY,
					SUB_PROPERTY_OF, EQUIVALENT_TO, DISJOINT_WITH, DOMAIN,
					RANGE);
		}
	}

	public OWLDataPropertyExpression parseDataPropertyExpression()
			throws ParserException {
		return parseDataPropertyExpression(false);
	}

	public OWLDataPropertyExpression parseDataPropertyExpression(
			boolean allowUndeclared) throws ParserException {
		String tok = consumeToken();
		if (!allowUndeclared && !isDataPropertyName(tok)) {
			throw createException(false, false, true, false, false, false);
		}
		return getOWLDataProperty(tok);
	}

    public OWLAxiom parseAxiomWithClassExpressionStart() throws ParserException {
        OWLClassExpression ce = parseIntersection();
        return parseClassAxiomRemainder(ce);
    }

    public OWLAxiom parseClassAxiomRemainder(OWLClassExpression startExpression) throws ParserException {
        String kw = consumeToken();
        System.out.println("Parse class axiom rem: " + startExpression);
        if (kw.equalsIgnoreCase(SUB_CLASS_OF)) {
            OWLClassExpression superClass = parseClassExpression();
            return dataFactory.getOWLSubClassOfAxiom(startExpression, superClass);
        }
        else if (kw.equalsIgnoreCase(DISJOINT_WITH)) {
            OWLClassExpression disjointClass = parseClassExpression();
            return dataFactory.getOWLDisjointClassesAxiom(startExpression, disjointClass);
        }
        else if (kw.equalsIgnoreCase(EQUIVALENT_TO)) {
            OWLClassExpression equivClass = parseClassExpression();
            return dataFactory.getOWLEquivalentClassesAxiom(startExpression, equivClass);
        }
        else if (kw.equalsIgnoreCase(AND)) {
            OWLClassExpression conjunct = parseIntersection();
            Set<OWLClassExpression> conjuncts = conjunct.asConjunctSet();
            conjuncts.add(startExpression);
            OWLClassExpression ce = dataFactory.getOWLObjectIntersectionOf(conjuncts);
            return parseClassAxiomRemainder(ce);
        }
        else if (kw.equalsIgnoreCase(OR)) {
            OWLClassExpression disjunct = parseIntersection();
            Set<OWLClassExpression> disjuncts = disjunct.asDisjunctSet();
            disjuncts.add(startExpression);
            OWLClassExpression ce = dataFactory.getOWLObjectUnionOf(disjuncts);
            return parseClassAxiomRemainder(ce);

        }
        else {
            System.out.println("Throwing exception!");
            throw createException(SUB_CLASS_OF, DISJOINT_WITH, EQUIVALENT_TO, AND, OR);
        }
    }

    public OWLAxiom parseAxiomWithObjectPropertyStart() throws ParserException {
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
        String kw = consumeToken();
        if (kw.equalsIgnoreCase(SOME)) {
            OWLClassExpression filler = parseIntersection();
            return parseClassAxiomRemainder(dataFactory.getOWLObjectSomeValuesFrom(prop, filler));
        }
        else if (kw.equalsIgnoreCase(ONLY)) {
            OWLClassExpression filler = parseIntersection();
            return parseClassAxiomRemainder(dataFactory.getOWLObjectAllValuesFrom(prop, filler));

        }
        else if (kw.equalsIgnoreCase(MIN)) {
            int cardi = parseInteger();
            OWLClassExpression filler = parseIntersection();
            return parseClassAxiomRemainder(dataFactory.getOWLObjectMinCardinality(cardi, prop, filler));

        }
        else if (kw.equalsIgnoreCase(MAX)) {
            int cardi = parseInteger();
            OWLClassExpression filler = parseIntersection();
            return parseClassAxiomRemainder(dataFactory.getOWLObjectMaxCardinality(cardi, prop, filler));

        }
        else if (kw.equalsIgnoreCase(EXACTLY)) {
            int cardi = parseInteger();
            OWLClassExpression filler = parseIntersection();
            return parseClassAxiomRemainder(dataFactory.getOWLObjectExactCardinality(cardi, prop, filler));
        }
        else if (kw.equalsIgnoreCase(SUB_PROPERTY_OF)) {
            OWLObjectPropertyExpression superProperty = parseObjectPropertyExpression();
            return dataFactory.getOWLSubObjectPropertyOfAxiom(prop, superProperty);
        }
        else if (kw.equalsIgnoreCase(EQUIVALENT_TO)) {
            OWLObjectPropertyExpression equivProp = parseObjectPropertyExpression();
            return dataFactory.getOWLEquivalentObjectPropertiesAxiom(prop, equivProp);

        }
        else if (kw.equalsIgnoreCase(INVERSE_OF)) {
            OWLObjectPropertyExpression invProp = parseObjectPropertyExpression();
            return dataFactory.getOWLInverseObjectPropertiesAxiom(prop, invProp);

        }
        else if (kw.equalsIgnoreCase(DISJOINT_WITH)) {
            OWLObjectPropertyExpression disjProp = parseObjectPropertyExpression();
            return dataFactory.getOWLDisjointObjectPropertiesAxiom(prop, disjProp);
        }
        else if (kw.equals(DOMAIN)) {
            OWLClassExpression domain = parseClassExpression();
            return dataFactory.getOWLObjectPropertyDomainAxiom(prop, domain);
        }
        else if (kw.equals(RANGE)) {
            OWLClassExpression range = parseClassExpression();
            return dataFactory.getOWLObjectPropertyRangeAxiom(prop, range);
        }
        else if (kw.equalsIgnoreCase("o")) {
            String sep = kw;
            List<OWLObjectPropertyExpression> chain = new ArrayList<OWLObjectPropertyExpression>();
            chain.add(prop);
            while (sep.equals("o")) {
                OWLObjectPropertyExpression chainProp = parseObjectPropertyExpression();
                chain.add(chainProp);
                sep = consumeToken();
            }
            if (!sep.equalsIgnoreCase(SUB_PROPERTY_OF)) {
                throw createException(SUB_PROPERTY_OF);
            }
            OWLObjectPropertyExpression superProp = parseObjectPropertyExpression();
            return dataFactory.getOWLSubPropertyChainOfAxiom(chain, superProp);
        }
        else {
            throw createException(SOME, ONLY, MIN, MAX, EXACTLY, SUB_PROPERTY_OF, EQUIVALENT_TO, INVERSE_OF, DISJOINT_WITH, DOMAIN, RANGE, "o");
        }
    }

//    public OWLAxiom parseAxiomWithDataPropertyStart() throws ParserException {
//        OWLDataPropertyExpression prop = parseDataProperty();
//        return null;
//    }


    public OWLAxiom parseInverseFunctionalPropertyAxiom() throws ParserException {
        String kw = consumeToken();
        if (!kw.equalsIgnoreCase(INVERSE_FUNCTIONAL)) {
            throw createException(INVERSE_FUNCTIONAL);
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
        return dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(prop);
    }


    public OWLAxiom parseSymmetricPropertyAxiom() throws ParserException {
        String kw = consumeToken();
        if (!kw.equalsIgnoreCase(SYMMETRIC)) {
            throw createException(SYMMETRIC);
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
        return dataFactory.getOWLSymmetricObjectPropertyAxiom(prop);
    }


    public OWLAxiom parseAsymmetricPropertyAxiom() throws ParserException {
        String kw = consumeToken();
        if (!kw.equalsIgnoreCase(ASYMMETRIC)) {
            throw createException(ASYMMETRIC);
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
        return dataFactory.getOWLAsymmetricObjectPropertyAxiom(prop);
    }


    public OWLAxiom parseTransitivePropertyAxiom() throws ParserException {
        String kw = consumeToken();
        if (!kw.equalsIgnoreCase(TRANSITIVE)) {
            throw createException(TRANSITIVE);
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
        return dataFactory.getOWLTransitiveObjectPropertyAxiom(prop);
    }


    public OWLAxiom parseReflexivePropertyAxiom() throws ParserException {
        String kw = consumeToken();
        if (!kw.equalsIgnoreCase(REFLEXIVE)) {
            throw createException(REFLEXIVE);
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
        return dataFactory.getOWLReflexiveObjectPropertyAxiom(prop);
    }


    public OWLAxiom parseIrreflexivePropertyAxiom() throws ParserException {
        String kw = consumeToken();
        if (!kw.equalsIgnoreCase(IRREFLEXIVE)) {
            throw createException(IRREFLEXIVE);
        }
        OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
        return dataFactory.getOWLIrreflexiveObjectPropertyAxiom(prop);
    }


    public OWLAxiom parseFunctionPropertyAxiom() throws ParserException {
        String kw = consumeToken();
        if (!kw.equalsIgnoreCase(FUNCTIONAL)) {
            throw createException(FUNCTIONAL);
        }
        String name = peekToken();
        if (isObjectPropertyName(name)) {
            OWLObjectPropertyExpression prop = parseObjectPropertyExpression();
            return dataFactory.getOWLFunctionalObjectPropertyAxiom(prop);
        }
        else if (isDataPropertyName(name)) {
            OWLDataProperty prop = parseDataProperty();
            return dataFactory.getOWLFunctionalDataPropertyAxiom(prop);
        }
        else {
            consumeToken();
            throw createException(false, true, true, false);
        }
    }


    private <F, O> Set<OntologyAxiomPair> parseAnnotatedListItems(F subject, AnnotatedListItemParser<F, O> itemParser, Set<OWLOntology> ontologies) throws ParserException {
        Set<OntologyAxiomPair> result = new HashSet<OntologyAxiomPair>();
        String sep = ",";
        while(sep.equals(",")) {
            String tok = peekToken();
            Set<OWLAnnotation> annotations = null;
            if (tok.equals(ANNOTATIONS)) {
                consumeToken();
                annotations = parseAnnotationList();
            }
            else {
                annotations = Collections.emptySet();
            }
            O item = itemParser.parseItem(subject);
            OWLAxiom axiom = itemParser.createAxiom(subject, item, annotations);
            for(OWLOntology ontology : ontologies) {
                result.add(new OntologyAxiomPair(ontology, axiom));
            }
            sep = peekToken();
            if(sep.equals(",")) {
                consumeToken();
            }
        }
        return result;
    }


    private interface AnnotatedListItemParser<F, O> {

        O parseItem(F subject) throws ParserException;

        OWLAxiom createAxiom(F subject, O object, Set<OWLAnnotation> annotations);

        String getFrameSectionKeyword();
    }

    private abstract class AnnotatedClassExpressionListItemParser<F> implements AnnotatedListItemParser<F, OWLClassExpression> {

        public OWLClassExpression parseItem(F subject) throws ParserException {
            return parseIntersection();
        }
    }

    private abstract class AnnotatedClassExpressionSetListItemParser<F> implements AnnotatedListItemParser<F, Set<OWLClassExpression>> {
        public Set<OWLClassExpression> parseItem(F subject) throws ParserException {
            return parseClassExpressionList(false);
        }
    }

    private abstract class AnnotatedPropertyListListItemParser<F> implements AnnotatedListItemParser<F, Set<OWLPropertyExpression>> {

        public Set<OWLPropertyExpression> parseItem(F subject) throws ParserException {
            return parsePropertyList();
        }
    }

    private abstract class AnnotatedIndividualsListItemParser<F> implements AnnotatedListItemParser<F, OWLIndividual> {
        public OWLIndividual parseItem(F subject) throws ParserException {
            return parseIndividual();
        }
    }

    private abstract class AnnotationListItemParser<F> implements AnnotatedListItemParser<F, OWLAnnotation> {
        public OWLAnnotation parseItem(F subject) throws ParserException {
            return parseAnnotation();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private class ClassSubClassOfListItemParser extends AnnotatedClassExpressionListItemParser<OWLClass> {
        public OWLAxiom createAxiom(OWLClass subject, OWLClassExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLSubClassOfAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return SUB_CLASS_OF;
        }
    }

    private class ClassEquivalentToListItemParser extends AnnotatedClassExpressionListItemParser<OWLClass> {
        public OWLAxiom createAxiom(OWLClass subject, OWLClassExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLEquivalentClassesAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return EQUIVALENT_TO;
        }
    }

    private class ClassDisjointWithListItemParser extends AnnotatedClassExpressionListItemParser<OWLClass> {
        public OWLAxiom createAxiom(OWLClass subject, OWLClassExpression object, Set<OWLAnnotation> annotations) {
            Set<OWLClassExpression> disjointClasses = new HashSet<OWLClassExpression>();
            disjointClasses.add(subject);
            disjointClasses.add(object);
            return dataFactory.getOWLDisjointClassesAxiom(disjointClasses, annotations);
        }

        public String getFrameSectionKeyword() {
            return DISJOINT_WITH;
        }
    }

    private class ClassDisjointClassesListItemParser extends AnnotatedClassExpressionSetListItemParser<OWLClass> {
        public OWLAxiom createAxiom(OWLClass subject, Set<OWLClassExpression> object, Set<OWLAnnotation> annotations) {
            object.add(subject);
            return dataFactory.getOWLDisjointClassesAxiom(object, annotations);
        }

        public String getFrameSectionKeyword() {
            return DISJOINT_CLASSES;
        }
    }

    private class ClassDisjointUnionOfListItemParser extends AnnotatedClassExpressionSetListItemParser<OWLClass> {
        public OWLAxiom createAxiom(OWLClass subject, Set<OWLClassExpression> object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLDisjointUnionAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return DISJOINT_UNION_OF;
        }
    }

    private class ClassHasKeyListItemParser extends AnnotatedPropertyListListItemParser<OWLClass> {
        public OWLAxiom createAxiom(OWLClass subject, Set<OWLPropertyExpression> object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLHasKeyAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return HAS_KEY;
        }
    }

    private class ClassSuperClassOfListItemParser extends AnnotatedClassExpressionListItemParser<OWLClass> {
        public OWLAxiom createAxiom(OWLClass subject, OWLClassExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLSubClassOfAxiom(object, subject, annotations);
        }

        public String getFrameSectionKeyword() {
            return SUPER_CLASS_OF;
        }
    }

    private class ClassIndividualsListItemParser extends AnnotatedIndividualsListItemParser<OWLClass> {
        public OWLAxiom createAxiom(OWLClass subject, OWLIndividual object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLClassAssertionAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return INDIVIDUALS;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private class EntityAnnotationsListItemParser<E extends OWLEntity> extends AnnotationListItemParser<E> {
        public OWLAxiom createAxiom(E subject, OWLAnnotation object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLAnnotationAssertionAxiom(subject.getIRI(), object, annotations);
        }

        public String getFrameSectionKeyword() {
            return ANNOTATIONS;
        }
    }


    private class ClassAnnotationsListItemParser extends EntityAnnotationsListItemParser<OWLClass> {
    }

    private class AnnotationSubjectAnnotationsListItemParser extends AnnotationListItemParser<OWLAnnotationSubject> {
        public OWLAxiom createAxiom(OWLAnnotationSubject subject, OWLAnnotation object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLAnnotationAssertionAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return ANNOTATIONS;
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////

    private abstract class ObjectPropertyExpressionListItemParser<F> implements AnnotatedListItemParser<F, OWLObjectPropertyExpression> {
        public OWLObjectPropertyExpression parseItem(F subject) throws ParserException {
            return parseObjectPropertyExpression();
        }
    }

    private class ObjectPropertySubPropertyOfListItemParser extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {
        public OWLAxiom createAxiom(OWLObjectProperty subject, OWLObjectPropertyExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLSubObjectPropertyOfAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return SUB_PROPERTY_OF;
        }
    }


    private class ObjectPropertySuperPropertyOfListItemParser extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {
        public OWLAxiom createAxiom(OWLObjectProperty subject, OWLObjectPropertyExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLSubObjectPropertyOfAxiom(object, subject, annotations);
        }

        public String getFrameSectionKeyword() {
            return SUPER_PROPERTY_OF;
        }
    }


    private class ObjectPropertyEquivalentToListItemParser extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {
        public OWLAxiom createAxiom(OWLObjectProperty subject, OWLObjectPropertyExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLEquivalentObjectPropertiesAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return EQUIVALENT_TO;
        }
    }

    private class ObjectPropertyDisjointWithListItemParser extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {
        public OWLAxiom createAxiom(OWLObjectProperty subject, OWLObjectPropertyExpression object, Set<OWLAnnotation> annotations) {
            Set<OWLObjectPropertyExpression> properties = new HashSet<OWLObjectPropertyExpression>();
            properties.add(subject);
            properties.add(object);
            return dataFactory.getOWLDisjointObjectPropertiesAxiom(properties, annotations);
        }

        public String getFrameSectionKeyword() {
            return DISJOINT_WITH;
        }
    }
    
    private class ObjectPropertyDomainListItemParser extends AnnotatedClassExpressionListItemParser<OWLObjectProperty> {
        public OWLAxiom createAxiom(OWLObjectProperty subject, OWLClassExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLObjectPropertyDomainAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return DOMAIN;
        }
    }
    
    private class ObjectPropertyRangeListItemParser extends AnnotatedClassExpressionListItemParser<OWLObjectProperty> {
        public OWLAxiom createAxiom(OWLObjectProperty subject, OWLClassExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLObjectPropertyRangeAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return RANGE;
        }
    }

    private class ObjectPropertyInverseOfListItemParser extends ObjectPropertyExpressionListItemParser<OWLObjectProperty> {
        public OWLAxiom createAxiom(OWLObjectProperty subject, OWLObjectPropertyExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLInverseObjectPropertiesAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return INVERSE_OF;
        }
    }

    private class ObjectPropertySubPropertyChainListItemParser implements AnnotatedListItemParser<OWLObjectProperty, List<OWLObjectPropertyExpression>> {
        public List<OWLObjectPropertyExpression> parseItem(OWLObjectProperty subject) throws ParserException {
            return parseObjectPropertyChain();
        }

        public OWLAxiom createAxiom(OWLObjectProperty subject, List<OWLObjectPropertyExpression> object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLSubPropertyChainOfAxiom(object, subject, annotations);
        }

        public String getFrameSectionKeyword() {
            return SUB_PROPERTY_CHAIN;
        }
    }

    private class ObjectPropertyCharacteristicsItemParser implements AnnotatedListItemParser<OWLObjectProperty, OWLObjectPropertyCharacteristicAxiom> {
        public OWLObjectPropertyCharacteristicAxiom parseItem(OWLObjectProperty subject) throws ParserException {
            return parseObjectPropertyCharacteristic(subject);
        }

        public OWLAxiom createAxiom(OWLObjectProperty subject, OWLObjectPropertyCharacteristicAxiom object, Set<OWLAnnotation> annotations) {
            return object.getAnnotatedAxiom(annotations);
        }

        public String getFrameSectionKeyword() {
            return CHARACTERISTICS;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private abstract class DataPropertyExpressionListItemParser<F> implements AnnotatedListItemParser<F, OWLDataPropertyExpression> {
        public OWLDataProperty parseItem(F subject) throws ParserException {
            return parseDataProperty();
        }
    }

    private class DataPropertySubPropertyOfListItemParser extends DataPropertyExpressionListItemParser<OWLDataProperty> {

        public OWLAxiom createAxiom(OWLDataProperty subject, OWLDataPropertyExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLSubDataPropertyOfAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return SUB_PROPERTY_OF;
        }
    }


    private class DataPropertySuperPropertyOfListItemParser extends DataPropertyExpressionListItemParser<OWLDataProperty> {
        public OWLAxiom createAxiom(OWLDataProperty subject, OWLDataPropertyExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLSubDataPropertyOfAxiom(object, subject, annotations);
        }

        public String getFrameSectionKeyword() {
            return SUPER_PROPERTY_OF;
        }
    }


    private class DataPropertyEquivalentToListItemParser extends DataPropertyExpressionListItemParser<OWLDataProperty> {
        public OWLAxiom createAxiom(OWLDataProperty subject, OWLDataPropertyExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLEquivalentDataPropertiesAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return EQUIVALENT_TO;
        }
    }

    private class DataPropertyDisjointWithListItemParser extends DataPropertyExpressionListItemParser<OWLDataProperty> {
        public OWLAxiom createAxiom(OWLDataProperty subject, OWLDataPropertyExpression object, Set<OWLAnnotation> annotations) {
            Set<OWLDataPropertyExpression> properties = new HashSet<OWLDataPropertyExpression>();
            properties.add(subject);
            properties.add(object);
            return dataFactory.getOWLDisjointDataPropertiesAxiom(properties, annotations);
        }

        public String getFrameSectionKeyword() {
            return DISJOINT_WITH;
        }
    }
    
    private class DataPropertyDomainListItemParser extends AnnotatedClassExpressionListItemParser<OWLDataProperty> {
        public OWLAxiom createAxiom(OWLDataProperty subject, OWLClassExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLDataPropertyDomainAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return DOMAIN;
        }
    }

    private abstract class AnnotatedDataRangeListItemParser<F> implements AnnotatedListItemParser<F, OWLDataRange> {
        public OWLDataRange parseItem(F subject) throws ParserException {
            return parseDataRange();
        }
    }

    private class DataPropertyRangeListItemParser extends AnnotatedDataRangeListItemParser<OWLDataProperty> {
        public OWLAxiom createAxiom(OWLDataProperty subject, OWLDataRange object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLDataPropertyRangeAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return RANGE;
        }
    }


    private class DataPropertyCharacteristicsItemParser implements AnnotatedListItemParser<OWLDataProperty, OWLDataPropertyCharacteristicAxiom> {
        public OWLDataPropertyCharacteristicAxiom parseItem(OWLDataProperty subject) throws ParserException {
            return parseDataPropertyCharacteristic(subject);
        }

        public OWLAxiom createAxiom(OWLDataProperty subject, OWLDataPropertyCharacteristicAxiom object, Set<OWLAnnotation> annotations) {
            return object.getAnnotatedAxiom(annotations);
        }

        public String getFrameSectionKeyword() {
            return CHARACTERISTICS;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    private class IndividualTypesItemParser extends AnnotatedClassExpressionListItemParser<OWLIndividual> {
        public OWLAxiom createAxiom(OWLIndividual subject, OWLClassExpression object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLClassAssertionAxiom(object, subject, annotations);
        }

        public String getFrameSectionKeyword() {
            return TYPES;
        }
    }

    private class IndividualFactsItemParser implements AnnotatedListItemParser<OWLIndividual, OWLPropertyAssertionAxiom> {
        public OWLPropertyAssertionAxiom parseItem(OWLIndividual subject) throws ParserException {
            return parseFact(subject);
        }

        public OWLAxiom createAxiom(OWLIndividual subject, OWLPropertyAssertionAxiom object, Set<OWLAnnotation> annotations) {
            return object.getAnnotatedAxiom(annotations);
        }

        public String getFrameSectionKeyword() {
            return FACTS;
        }
    }

    private class IndividualSameAsItemParser extends AnnotatedIndividualsListItemParser<OWLIndividual> {
        public OWLAxiom createAxiom(OWLIndividual subject, OWLIndividual object, Set<OWLAnnotation> annotations) {
            Set<OWLIndividual> individuals = new HashSet<OWLIndividual>();
            individuals.add(subject);
            individuals.add(object);
            return dataFactory.getOWLSameIndividualAxiom(individuals, annotations);
        }

        public String getFrameSectionKeyword() {
            return SAME_AS;
        }
    }

    private class IndividualDifferentFromItemParser extends AnnotatedIndividualsListItemParser<OWLIndividual> {
        public OWLAxiom createAxiom(OWLIndividual subject, OWLIndividual object, Set<OWLAnnotation> annotations) {
            Set<OWLIndividual> individuals = new HashSet<OWLIndividual>();
            individuals.add(subject);
            individuals.add(object);
            return dataFactory.getOWLDifferentIndividualsAxiom(individuals, annotations);
        }

        public String getFrameSectionKeyword() {
            return DIFFERENT_FROM;
        }
    }

    private class IndividualDifferentIndividualsItemParser implements AnnotatedListItemParser<OWLIndividual, Set<OWLIndividual>> {

        public Set<OWLIndividual> parseItem(OWLIndividual subject) throws ParserException {
            return parseIndividualList();
        }

        public OWLAxiom createAxiom(OWLIndividual subject, Set<OWLIndividual> object, Set<OWLAnnotation> annotations) {
            Set<OWLIndividual> individuals = new HashSet<OWLIndividual>();
            individuals.add(subject);
            individuals.addAll(object);
            return dataFactory.getOWLDifferentIndividualsAxiom(individuals, annotations);
        }


        public String getFrameSectionKeyword() {
            return DIFFERENT_INDIVIDUALS;
        }
    }


    private class IndividualAnnotationItemParser implements AnnotatedListItemParser<OWLIndividual, OWLAnnotation> {
        public OWLAnnotation parseItem(OWLIndividual subject) throws ParserException {
            return parseAnnotation();
        }

        public OWLAxiom createAxiom(OWLIndividual subject, OWLAnnotation object, Set<OWLAnnotation> annotations) {
            if (subject.isAnonymous()) {
                return dataFactory.getOWLAnnotationAssertionAxiom(subject.asOWLAnonymousIndividual(), object, annotations);
            }
            else {
                return dataFactory.getOWLAnnotationAssertionAxiom(subject.asOWLNamedIndividual().getIRI(), object, annotations);
            }
        }

        public String getFrameSectionKeyword() {
            return ANNOTATIONS;
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////

    private abstract class AnnotatedIRIListItemParser<F> implements AnnotatedListItemParser<F, IRI> {
        public IRI parseItem(F subject) throws ParserException {
            return parseIRI();
        }
    }

    private class AnnotationPropertySubPropertyOfListItemParser implements AnnotatedListItemParser<OWLAnnotationProperty, OWLAnnotationProperty> {

        public OWLAnnotationProperty parseItem(OWLAnnotationProperty subject) throws ParserException {
            return parseAnnotationProperty();
        }

        public OWLAxiom createAxiom(OWLAnnotationProperty subject, OWLAnnotationProperty object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLSubAnnotationPropertyOfAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return SUB_PROPERTY_OF;
        }
    }

    private class AnnotationPropertyDomainListItemParser extends AnnotatedIRIListItemParser<OWLAnnotationProperty> {
        public OWLAxiom createAxiom(OWLAnnotationProperty subject, IRI object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLAnnotationPropertyDomainAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return DOMAIN;
        }
    }


    private class AnnotationPropertyRangeListItemParser extends AnnotatedIRIListItemParser<OWLAnnotationProperty> {
        public OWLAxiom createAxiom(OWLAnnotationProperty subject, IRI object, Set<OWLAnnotation> annotations) {
            return dataFactory.getOWLAnnotationPropertyRangeAxiom(subject, object, annotations);
        }

        public String getFrameSectionKeyword() {
            return RANGE;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  

}
