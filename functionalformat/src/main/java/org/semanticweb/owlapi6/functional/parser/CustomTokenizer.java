package org.semanticweb.owlapi6.functional.parser;

import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ANNOTATION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ANNOTATIONASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ANNOTATIONPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ANNOTATIONPROPERTYDOMAIN;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ANNOTATIONPROPERTYRANGE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ASYMMETRICOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.BODY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.BUILTINATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.CLASS;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.CLASSASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.CLASSATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.CLOSEPAR;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.COMMENT;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAALLVALUESFROM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATACOMPLEMENTOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAEXACTCARDINALITY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAHASVALUE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAINTERSECTIONOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAMAXCARDINALITY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAMINCARDINALITY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAONEOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAPROP;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAPROPERTYASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAPROPERTYATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAPROPERTYDOMAIN;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAPROPERTYRANGE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATARANGEATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATASOMEVALUESFROM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATATYPE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATATYPEDEFINITION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATATYPEIDENTIFIER;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATATYPERESTRICTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAUNIONOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DECLARATION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DESCRIPTIONGRAPH;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DGRULE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DIFFERENTINDIVIDUALS;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DIFFERENTINDIVIDUALSATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DISJOINTCLASSES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DISJOINTDATAPROPERTIES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DISJOINTOBJECTPROPERTIES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DISJOINTUNION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DLSAFERULE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DOCUMENTATION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.EDGEASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.EDGES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.EOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.EQUALS;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.EQUIVALENTCLASSES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.EQUIVALENTDATAPROPERTIES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.EQUIVALENTOBJECTPROPERTIES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ERROR;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.FULLIRI;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.FUNCTIONALDATAPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.FUNCTIONALOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.HASKEY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.HEAD;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.IMPORT;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.INVERSEFUNCTIONALOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.INVERSEOBJECTPROPERTIES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.IRREFLEXIVEOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.LABEL;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.LANGIDENTIFIER;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.LENGTH;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.MAINCLASSES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.MAXEXCLUSIVE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.MAXINCLUSIVE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.MAXLENGTH;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.MINEXCLUSIVE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.MININCLUSIVE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.MINLENGTH;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.NAMEDINDIVIDUAL;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.NEGATIVEDATAPROPERTYASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.NEGATIVEOBJECTPROPERTYASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.NODEASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.NODEID;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.NODES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTALLVALUESFROM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTCOMPLEMENTOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTEXACTCARDINALITY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTHASSELF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTHASVALUE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTINTERSECTIONOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTINVERSEOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTMAXCARDINALITY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTMINCARDINALITY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTONEOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTPROP;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTPROPERTYASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTPROPERTYATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTPROPERTYDOMAIN;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTPROPERTYRANGE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTSOMEVALUESFROM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTUNIONOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ONTOLOGY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OPENPAR;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.PATTERN;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.PNAME_NS;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.PN_LOCAL;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.PREFIX;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.REFLEXIVEOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SAMEINDIVIDUAL;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SAMEINDIVIDUALATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.STRINGLITERAL;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SUBANNOTATIONPROPERTYOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SUBCLASSOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SUBDATAPROPERTYOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SUBOBJECTPROPERTYCHAIN;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SUBOBJECTPROPERTYOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SYMMETRICOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.TOTALDIGITS;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.TRANSITIVEOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.VARIABLE;

import java.io.EOFException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ses on 6/9/14.
 */
class CustomTokenizer implements TokenManager {

    private static final Map<String, Integer> makeTokenMap = makeTokenMap();
    private int unreadChar = -1;
    private final Provider in;
    private boolean eofSeen = false;
    private final StringBuilder buf = new StringBuilder();
    private int lineNo = 1;
    private int colNo = 0;
    private int startLine = -1;
    private int startCol = -1;
    // hack to adapt from Provider to CustomTokenizer. The Provider is already buffered, so little
    // performance impact.
    private char[] charReading = new char[1];


    public CustomTokenizer(Provider reader) {
        in = reader;
    }

    /**
     * This gets the next token from the input stream. A token of kind 0 (EOF) should be returned on
     * EOF.
     */
    @Override
    public Token getNextToken() {
        while (true) {
            char c;
            try {
                c = findTokenStart();
                switch (c) {
                    case '(':
                        return makeToken(OPENPAR, "(");
                    case ')':
                        return makeToken(CLOSEPAR, ")");
                    case '@':
                        return makeToken(LANGIDENTIFIER, "@");
                    case '^':
                        c = readChar();
                        if (c == '^') {
                            return makeToken(DATATYPEIDENTIFIER, "^^");
                        } else {
                            return makeToken(ERROR, "^" + c);
                        }
                    case '=':
                        return makeToken(EQUALS, "=");
                    case '#':
                        skipComment();
                        break;
                    case '"':
                        return readStringLiteralToken();
                    case '<':
                        return readFullIRI();
                    default:
                        return readTextualToken(c);
                }
            } catch (@SuppressWarnings("unused") IOException e) {
                return makeToken(EOF, "");
            }
        }
    }

    private void skipComment() throws IOException {
        for (char c = readChar(); c != '\n'; c = readChar()) {
            // read to end of line
        }
    }

    private Token readStringLiteralToken() throws IOException {
        buf.setLength(0);
        buf.append('"');
        while (true) {
            char c = readChar();
            switch (c) {
                case '"':
                    buf.append(c);
                    return makeToken(STRINGLITERAL, buf.toString());
                case '\\':
                    buf.append(c);
                    c = readChar();
                    if (c != '\\' && c != '\"') {
                        return makeToken(ERROR, "Bad escape sequence in StringLiteral");
                    }
                    // fallthrough
                    // $FALL-THROUGH$
                default:
                    buf.append(c);
            }
        }
    }

    private Token readFullIRI() {
        try {
            buf.setLength(0);
            buf.append('<');
            while (true) {
                char c = readChar();
                buf.append(c);
                if (c == '>') {
                    return makeToken(FULLIRI, buf.toString());
                }
            }
        } catch (@SuppressWarnings("unused") IOException e) {
            return makeToken(ERROR, "<");
        }
    }

    private Token readTextualToken(char input) throws IOException {
        char c = input;
        if (c >= '0' && c <= '9') {
            return readNumber(c);
        }
        buf.setLength(0);
        buf.append(c);
        int colonIndex = -1;
        if (c == ':') {
            colonIndex = 0;
        }
        loop: while (true) {
            try {
                c = readChar();
                switch (c) {
                    case '=':
                    case '"':
                    case '(':
                    case ')':
                    case '<':
                    case '>':
                    case '@':
                    case '^':
                    case '\r':
                    case '\n':
                    case ' ':
                    case '\t':
                        unread(c);
                        break loop;
                    case ':':
                        colonIndex = buf.length(); // and fall through
                        //$FALL-THROUGH$
                    default:
                        buf.append(c);
                }
            } catch (@SuppressWarnings("unused") EOFException eof) {
                break;
            }
        }
        String s = buf.toString();
        if (colonIndex >= 0) {
            if (colonIndex == s.length() - 1) {
                return makeToken(PNAME_NS, s);
            } else {
                if (s.startsWith("_:")) {
                    return makeToken(NODEID, s);
                }
                return makeToken(OWLFunctionalSyntaxParserConstants.PNAME_LN, s);
            }
        }
        Integer value = makeTokenMap.get(s);
        if (value == null) {
            return makeToken(PN_LOCAL, s);
        }
        return makeToken(value.intValue(), s);
    }

    private Token readNumber(char input) throws IOException {
        char c = input;
        buf.setLength(0);
        buf.append(c);
        while (true) {
            c = readChar();
            if (!Character.isDigit(c)) {
                unread(c);
                return makeToken(OWLFunctionalSyntaxParserConstants.INT, buf.toString());
            } else {
                buf.append(c);
            }
        }
    }

    private static Map<String, Integer> makeTokenMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("Ontology", Integer.valueOf(ONTOLOGY));
        map.put("Label", Integer.valueOf(LABEL));
        map.put("Import", Integer.valueOf(IMPORT));
        map.put("Comment", Integer.valueOf(COMMENT));
        map.put("SubClassOf", Integer.valueOf(SUBCLASSOF));
        map.put("EquivalentClasses", Integer.valueOf(EQUIVALENTCLASSES));
        map.put("DisjointClasses", Integer.valueOf(DISJOINTCLASSES));
        map.put("DisjointUnion", Integer.valueOf(DISJOINTUNION));
        map.put("Annotation", Integer.valueOf(ANNOTATION));
        map.put("AnnotationProperty", Integer.valueOf(ANNOTATIONPROPERTY));
        map.put("AnnotationAssertion", Integer.valueOf(ANNOTATIONASSERTION));
        map.put("SubAnnotationPropertyOf", Integer.valueOf(SUBANNOTATIONPROPERTYOF));
        map.put("AnnotationPropertyDomain", Integer.valueOf(ANNOTATIONPROPERTYDOMAIN));
        map.put("AnnotationPropertyRange", Integer.valueOf(ANNOTATIONPROPERTYRANGE));
        map.put("HasKey", Integer.valueOf(HASKEY));
        map.put("Declaration", Integer.valueOf(DECLARATION));
        map.put("Documentation", Integer.valueOf(DOCUMENTATION));
        map.put("Class", Integer.valueOf(CLASS));
        map.put("ObjectProperty", Integer.valueOf(OBJECTPROP));
        map.put("DataProperty", Integer.valueOf(DATAPROP));
        map.put("NamedIndividual", Integer.valueOf(NAMEDINDIVIDUAL));
        map.put("Datatype", Integer.valueOf(DATATYPE));
        map.put("DataOneOf", Integer.valueOf(DATAONEOF));
        map.put("DataUnionOf", Integer.valueOf(DATAUNIONOF));
        map.put("DataIntersectionOf", Integer.valueOf(DATAINTERSECTIONOF));
        map.put("ObjectOneOf", Integer.valueOf(OBJECTONEOF));
        map.put("ObjectUnionOf", Integer.valueOf(OBJECTUNIONOF));
        map.put("ObjectHasValue", Integer.valueOf(OBJECTHASVALUE));
        map.put("ObjectInverseOf", Integer.valueOf(OBJECTINVERSEOF));
        map.put("InverseObjectProperties", Integer.valueOf(INVERSEOBJECTPROPERTIES));
        map.put("DataComplementOf", Integer.valueOf(DATACOMPLEMENTOF));
        map.put("DatatypeRestriction", Integer.valueOf(DATATYPERESTRICTION));
        map.put("DatatypeDefinition", Integer.valueOf(DATATYPEDEFINITION));
        map.put("ObjectIntersectionOf", Integer.valueOf(OBJECTINTERSECTIONOF));
        map.put("ObjectComplementOf", Integer.valueOf(OBJECTCOMPLEMENTOF));
        map.put("ObjectAllValuesFrom", Integer.valueOf(OBJECTALLVALUESFROM));
        map.put("ObjectSomeValuesFrom", Integer.valueOf(OBJECTSOMEVALUESFROM));
        map.put("ObjectHasSelf", Integer.valueOf(OBJECTHASSELF));
        map.put("ObjectMinCardinality", Integer.valueOf(OBJECTMINCARDINALITY));
        map.put("ObjectMaxCardinality", Integer.valueOf(OBJECTMAXCARDINALITY));
        map.put("ObjectExactCardinality", Integer.valueOf(OBJECTEXACTCARDINALITY));
        map.put("DataAllValuesFrom", Integer.valueOf(DATAALLVALUESFROM));
        map.put("DataSomeValuesFrom", Integer.valueOf(DATASOMEVALUESFROM));
        map.put("DataHasValue", Integer.valueOf(DATAHASVALUE));
        map.put("DataMinCardinality", Integer.valueOf(DATAMINCARDINALITY));
        map.put("DataMaxCardinality", Integer.valueOf(DATAMAXCARDINALITY));
        map.put("DataExactCardinality", Integer.valueOf(DATAEXACTCARDINALITY));
        map.put("ObjectPropertyChain", Integer.valueOf(SUBOBJECTPROPERTYCHAIN));
        map.put("SubObjectPropertyOf", Integer.valueOf(SUBOBJECTPROPERTYOF));
        map.put("EquivalentObjectProperties", Integer.valueOf(EQUIVALENTOBJECTPROPERTIES));
        map.put("DisjointObjectProperties", Integer.valueOf(DISJOINTOBJECTPROPERTIES));
        map.put("ObjectPropertyDomain", Integer.valueOf(OBJECTPROPERTYDOMAIN));
        map.put("ObjectPropertyRange", Integer.valueOf(OBJECTPROPERTYRANGE));
        map.put("FunctionalObjectProperty", Integer.valueOf(FUNCTIONALOBJECTPROPERTY));
        map.put("InverseFunctionalObjectProperty",
            Integer.valueOf(INVERSEFUNCTIONALOBJECTPROPERTY));
        map.put("ReflexiveObjectProperty", Integer.valueOf(REFLEXIVEOBJECTPROPERTY));
        map.put("IrreflexiveObjectProperty", Integer.valueOf(IRREFLEXIVEOBJECTPROPERTY));
        map.put("SymmetricObjectProperty", Integer.valueOf(SYMMETRICOBJECTPROPERTY));
        map.put("AsymmetricObjectProperty", Integer.valueOf(ASYMMETRICOBJECTPROPERTY));
        map.put("TransitiveObjectProperty", Integer.valueOf(TRANSITIVEOBJECTPROPERTY));
        map.put("SubDataPropertyOf", Integer.valueOf(SUBDATAPROPERTYOF));
        map.put("EquivalentDataProperties", Integer.valueOf(EQUIVALENTDATAPROPERTIES));
        map.put("DisjointDataProperties", Integer.valueOf(DISJOINTDATAPROPERTIES));
        map.put("DataPropertyDomain", Integer.valueOf(DATAPROPERTYDOMAIN));
        map.put("DataPropertyRange", Integer.valueOf(DATAPROPERTYRANGE));
        map.put("FunctionalDataProperty", Integer.valueOf(FUNCTIONALDATAPROPERTY));
        map.put("SameIndividual", Integer.valueOf(SAMEINDIVIDUAL));
        map.put("DifferentIndividuals", Integer.valueOf(DIFFERENTINDIVIDUALS));
        map.put("ClassAssertion", Integer.valueOf(CLASSASSERTION));
        map.put("ObjectPropertyAssertion", Integer.valueOf(OBJECTPROPERTYASSERTION));
        map.put("NegativeObjectPropertyAssertion",
            Integer.valueOf(NEGATIVEOBJECTPROPERTYASSERTION));
        map.put("DataPropertyAssertion", Integer.valueOf(DATAPROPERTYASSERTION));
        map.put("NegativeDataPropertyAssertion", Integer.valueOf(NEGATIVEDATAPROPERTYASSERTION));
        map.put("Prefix", Integer.valueOf(PREFIX));
        map.put("length", Integer.valueOf(LENGTH));
        map.put("minLength", Integer.valueOf(MINLENGTH));
        map.put("maxLength", Integer.valueOf(MAXLENGTH));
        map.put("pattern", Integer.valueOf(PATTERN));
        map.put("minInclusive", Integer.valueOf(MININCLUSIVE));
        map.put("maxInclusive", Integer.valueOf(MAXINCLUSIVE));
        map.put("minExclusive", Integer.valueOf(MINEXCLUSIVE));
        map.put("maxExclusive", Integer.valueOf(MAXEXCLUSIVE));
        map.put("totalDigits", Integer.valueOf(TOTALDIGITS));
        map.put("DLSafeRule", Integer.valueOf(DLSAFERULE));
        map.put("Body", Integer.valueOf(BODY));
        map.put("Head", Integer.valueOf(HEAD));
        map.put("ClassAtom", Integer.valueOf(CLASSATOM));
        map.put("DataRangeAtom", Integer.valueOf(DATARANGEATOM));
        map.put("ObjectPropertyAtom", Integer.valueOf(OBJECTPROPERTYATOM));
        map.put("DataPropertyAtom", Integer.valueOf(DATAPROPERTYATOM));
        map.put("BuiltInAtom", Integer.valueOf(BUILTINATOM));
        map.put("SameIndividualAtom", Integer.valueOf(SAMEINDIVIDUALATOM));
        map.put("DifferentIndividualsAtom", Integer.valueOf(DIFFERENTINDIVIDUALSATOM));
        map.put("Variable", Integer.valueOf(VARIABLE));
        map.put("DescriptionGraphRule", Integer.valueOf(DGRULE));
        map.put("DescriptionGraph", Integer.valueOf(DESCRIPTIONGRAPH));
        map.put("Nodes", Integer.valueOf(NODES));
        map.put("NodeAssertion", Integer.valueOf(NODEASSERTION));
        map.put("Edges", Integer.valueOf(EDGES));
        map.put("EdgeAssertion", Integer.valueOf(EDGEASSERTION));
        map.put("MainClasses", Integer.valueOf(MAINCLASSES));

        return map;
    }

    private Token makeToken(int kind, String image) {
        Token result = new Token(kind, image);
        result.beginLine = startLine;
        result.beginColumn = startCol;
        return result;
    }

    private char findTokenStart() throws IOException {
        while (true) {
            char c = readChar();
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                startLine = lineNo;
                startCol = colNo;
                return c;
            }
        }
    }

    private void unread(char c) {
        unreadChar = c;
        if (c != '\n') {
            colNo--;
        }
    }

    private char readChar() throws IOException {
        if (eofSeen) {
            throw new EOFException();
        }
        int c;
        if (unreadChar < 0) {
            c = in.read(charReading, 0, 1);
            if (c == 1) {
                c = charReading[0];
            } else {
                c = -1;
            }
            if (c == '\n') {
                lineNo++;
                colNo = 0;
            }
        } else {
            c = unreadChar;
            unreadChar = -1;
        }
        colNo++;
        if (c < 0) {
            eofSeen = true;
            throw new EOFException();
        } else {
            return (char) c;
        }
    }
}
