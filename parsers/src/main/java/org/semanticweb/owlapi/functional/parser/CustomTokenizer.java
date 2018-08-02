package org.semanticweb.owlapi.functional.parser;

import static org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxParserConstants.*;

import java.io.EOFException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ses on 6/9/14.
 */
class CustomTokenizer implements TokenManager {

    private static final Map<String, Integer> makeTokenMap=makeTokenMap();
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

    private Token readTextualToken(char c) throws IOException {
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
        Integer value=makeTokenMap.get(s);
        if(value==null) {
            return makeToken(PN_LOCAL, s);
        }
        return makeToken(value.intValue(), s);
    }
 
    private Token readNumber(char c) throws IOException {
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
        Map<String, Integer> map=new ConcurrentHashMap<>();
        map.put( "Ontology",Integer.valueOf(ONTOLOGY));
        map.put( "Label",Integer.valueOf(LABEL));
        map.put( "Import",Integer.valueOf(IMPORT));
        map.put( "Comment",Integer.valueOf(COMMENT));
        map.put( "SubClassOf",Integer.valueOf(SUBCLASSOF));
        map.put( "EquivalentClasses",Integer.valueOf(EQUIVALENTCLASSES));
        map.put( "DisjointClasses",Integer.valueOf(DISJOINTCLASSES));
        map.put( "DisjointUnion",Integer.valueOf(DISJOINTUNION));
        map.put( "Annotation",Integer.valueOf(ANNOTATION));
        map.put( "AnnotationProperty",Integer.valueOf(ANNOTATIONPROPERTY));
        map.put( "AnnotationAssertion",Integer.valueOf(ANNOTATIONASSERTION));
        map.put( "SubAnnotationPropertyOf",Integer.valueOf(SUBANNOTATIONPROPERTYOF));
        map.put( "AnnotationPropertyDomain",Integer.valueOf(ANNOTATIONPROPERTYDOMAIN));
        map.put( "AnnotationPropertyRange",Integer.valueOf(ANNOTATIONPROPERTYRANGE));
        map.put( "HasKey",Integer.valueOf(HASKEY));
        map.put( "Declaration",Integer.valueOf(DECLARATION));
        map.put( "Documentation",Integer.valueOf(DOCUMENTATION));
        map.put( "Class",Integer.valueOf(CLASS));
        map.put( "ObjectProperty",Integer.valueOf(OBJECTPROP));
        map.put( "DataProperty",Integer.valueOf(DATAPROP));
        map.put( "NamedIndividual",Integer.valueOf(NAMEDINDIVIDUAL));
        map.put( "Datatype",Integer.valueOf(DATATYPE));
        map.put( "DataOneOf",Integer.valueOf(DATAONEOF));
        map.put( "DataUnionOf",Integer.valueOf(DATAUNIONOF));
        map.put( "DataIntersectionOf",Integer.valueOf(DATAINTERSECTIONOF));
        map.put( "ObjectOneOf",Integer.valueOf(OBJECTONEOF));
        map.put( "ObjectUnionOf",Integer.valueOf(OBJECTUNIONOF));
        map.put( "ObjectHasValue",Integer.valueOf(OBJECTHASVALUE));
        map.put( "ObjectInverseOf",Integer.valueOf(OBJECTINVERSEOF));
        map.put( "InverseObjectProperties",Integer.valueOf(INVERSEOBJECTPROPERTIES));
        map.put( "DataComplementOf",Integer.valueOf(DATACOMPLEMENTOF));
        map.put( "DatatypeRestriction",Integer.valueOf(DATATYPERESTRICTION));
        map.put( "DatatypeDefinition",Integer.valueOf(DATATYPEDEFINITION));
        map.put( "ObjectIntersectionOf",Integer.valueOf(OBJECTINTERSECTIONOF));
        map.put( "ObjectComplementOf",Integer.valueOf(OBJECTCOMPLEMENTOF));
        map.put( "ObjectAllValuesFrom",Integer.valueOf(OBJECTALLVALUESFROM));
        map.put( "ObjectSomeValuesFrom",Integer.valueOf(OBJECTSOMEVALUESFROM));
        map.put( "ObjectHasSelf",Integer.valueOf(OBJECTHASSELF));
        map.put( "ObjectMinCardinality",Integer.valueOf(OBJECTMINCARDINALITY));
        map.put( "ObjectMaxCardinality",Integer.valueOf(OBJECTMAXCARDINALITY));
        map.put( "ObjectExactCardinality",Integer.valueOf(OBJECTEXACTCARDINALITY));
        map.put( "DataAllValuesFrom",Integer.valueOf(DATAALLVALUESFROM));
        map.put( "DataSomeValuesFrom",Integer.valueOf(DATASOMEVALUESFROM));
        map.put( "DataHasValue",Integer.valueOf(DATAHASVALUE));
        map.put( "DataMinCardinality",Integer.valueOf(DATAMINCARDINALITY));
        map.put( "DataMaxCardinality",Integer.valueOf(DATAMAXCARDINALITY));
        map.put( "DataExactCardinality",Integer.valueOf(DATAEXACTCARDINALITY));
        map.put( "ObjectPropertyChain",Integer.valueOf(SUBOBJECTPROPERTYCHAIN));
        map.put( "SubObjectPropertyOf",Integer.valueOf(SUBOBJECTPROPERTYOF));
        map.put( "EquivalentObjectProperties",Integer.valueOf(EQUIVALENTOBJECTPROPERTIES));
        map.put( "DisjointObjectProperties",Integer.valueOf(DISJOINTOBJECTPROPERTIES));
        map.put( "ObjectPropertyDomain",Integer.valueOf(OBJECTPROPERTYDOMAIN));
        map.put( "ObjectPropertyRange",Integer.valueOf(OBJECTPROPERTYRANGE));
        map.put( "FunctionalObjectProperty",Integer.valueOf(FUNCTIONALOBJECTPROPERTY));
        map.put( "InverseFunctionalObjectProperty",Integer.valueOf(INVERSEFUNCTIONALOBJECTPROPERTY));
        map.put( "ReflexiveObjectProperty",Integer.valueOf(REFLEXIVEOBJECTPROPERTY));
        map.put( "IrreflexiveObjectProperty",Integer.valueOf(IRREFLEXIVEOBJECTPROPERTY));
        map.put( "SymmetricObjectProperty",Integer.valueOf(SYMMETRICOBJECTPROPERTY));
        map.put( "AsymmetricObjectProperty",Integer.valueOf(ASYMMETRICOBJECTPROPERTY));
        map.put( "TransitiveObjectProperty",Integer.valueOf(TRANSITIVEOBJECTPROPERTY));
        map.put( "SubDataPropertyOf",Integer.valueOf(SUBDATAPROPERTYOF));
        map.put( "EquivalentDataProperties",Integer.valueOf(EQUIVALENTDATAPROPERTIES));
        map.put( "DisjointDataProperties",Integer.valueOf(DISJOINTDATAPROPERTIES));
        map.put( "DataPropertyDomain",Integer.valueOf(DATAPROPERTYDOMAIN));
        map.put( "DataPropertyRange",Integer.valueOf(DATAPROPERTYRANGE));
        map.put( "FunctionalDataProperty",Integer.valueOf(FUNCTIONALDATAPROPERTY));
        map.put( "SameIndividual",Integer.valueOf(SAMEINDIVIDUAL));
        map.put( "DifferentIndividuals",Integer.valueOf(DIFFERENTINDIVIDUALS));
        map.put( "ClassAssertion",Integer.valueOf(CLASSASSERTION));
        map.put( "ObjectPropertyAssertion",Integer.valueOf(OBJECTPROPERTYASSERTION));
        map.put( "NegativeObjectPropertyAssertion",Integer.valueOf(NEGATIVEOBJECTPROPERTYASSERTION));
        map.put( "DataPropertyAssertion",Integer.valueOf(DATAPROPERTYASSERTION));
        map.put( "NegativeDataPropertyAssertion",Integer.valueOf(NEGATIVEDATAPROPERTYASSERTION));
        map.put( "Prefix",Integer.valueOf(PREFIX));
        map.put( "length",Integer.valueOf(LENGTH));
        map.put( "minLength",Integer.valueOf(MINLENGTH));
        map.put( "maxLength",Integer.valueOf(MAXLENGTH));
        map.put( "pattern",Integer.valueOf(PATTERN));
        map.put( "minInclusive",Integer.valueOf(MININCLUSIVE));
        map.put( "maxInclusive",Integer.valueOf(MAXINCLUSIVE));
        map.put( "minExclusive",Integer.valueOf(MINEXCLUSIVE));
        map.put( "maxExclusive",Integer.valueOf(MAXEXCLUSIVE));
        map.put( "totalDigits",Integer.valueOf(TOTALDIGITS));
        map.put( "DLSafeRule",Integer.valueOf(DLSAFERULE));
        map.put( "Body",Integer.valueOf(BODY));
        map.put( "Head",Integer.valueOf(HEAD));
        map.put( "ClassAtom",Integer.valueOf(CLASSATOM));
        map.put( "DataRangeAtom",Integer.valueOf(DATARANGEATOM));
        map.put( "ObjectPropertyAtom",Integer.valueOf(OBJECTPROPERTYATOM));
        map.put( "DataPropertyAtom",Integer.valueOf(DATAPROPERTYATOM));
        map.put( "BuiltInAtom",Integer.valueOf(BUILTINATOM));
        map.put( "SameIndividualAtom",Integer.valueOf(SAMEINDIVIDUALATOM));
        map.put( "DifferentIndividualsAtom",Integer.valueOf(DIFFERENTINDIVIDUALSATOM));
        map.put( "Variable",Integer.valueOf(VARIABLE));
        map.put( "DescriptionGraphRule",Integer.valueOf(DGRULE));
        map.put( "DescriptionGraph",Integer.valueOf(DESCRIPTIONGRAPH));
        map.put( "Nodes",Integer.valueOf(NODES));
        map.put( "NodeAssertion",Integer.valueOf(NODEASSERTION));
        map.put( "Edges",Integer.valueOf(EDGES));
        map.put( "EdgeAssertion",Integer.valueOf(EDGEASSERTION));
        map.put( "MainClasses",Integer.valueOf(MAINCLASSES));

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
