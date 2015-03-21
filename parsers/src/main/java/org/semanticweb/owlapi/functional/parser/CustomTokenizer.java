package org.semanticweb.owlapi.functional.parser;

import static org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxParserConstants.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by ses on 6/9/14.
 */
class CustomTokenizer implements TokenManager {

    private int unreadChar = -1;
    private final Reader in;
    private boolean eofSeen = false;
    private final StringBuilder buf = new StringBuilder();

    public CustomTokenizer(Reader reader) {
        in = reader;
    }

    /**
     * This gets the next token from the input stream. A token of kind 0 (EOF)
     * should be returned on EOF.
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
            } catch (IOException e) {
                return makeToken(EOF, "");
            }
        }
    }

    private void skipComment() throws IOException {
        for (char c = readChar(); c != '\n'; c = readChar()) {}
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
                        return makeToken(ERROR,
                                "Bad escape sequence in StringLiteral");
                    }
                    // fallthrough
                    //$FALL-THROUGH$
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
        } catch (IOException e) {
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
                        colonIndex = buf.length();  // and fall through
                        //$FALL-THROUGH$
                    default:
                        buf.append(c);
                }
            } catch (EOFException eof) {
                break;
            }
        }
        String s = buf.toString();
        if (colonIndex >= 0) {
            // System.out.println("colonIndex >=0 - so expect abbreviated IRI from "
            // + buf);
            if (colonIndex == s.length() - 1) {
                return makeToken(PNAME_NS, s);
            } else {
                if (s.startsWith("_:")) {
                    return makeToken(NODEID, s);
                }
                return makeToken(OWLFunctionalSyntaxParserConstants.PNAME_LN, s);
            }
        }
        switch (s) {
            case "Ontology":
                return makeToken(ONTOLOGY, s);
            case "Label":
                return makeToken(LABEL, s);
            case "Import":
                return makeToken(IMPORT, s);
            case "Comment":
                return makeToken(COMMENT, s);
            case "SubClassOf":
                return makeToken(SUBCLASSOF, s);
            case "EquivalentClasses":
                return makeToken(EQUIVALENTCLASSES, s);
            case "DisjointClasses":
                return makeToken(DISJOINTCLASSES, s);
            case "DisjointUnion":
                return makeToken(DISJOINTUNION, s);
            case "Annotation":
                return makeToken(ANNOTATION, s);
            case "AnnotationProperty":
                return makeToken(ANNOTATIONPROPERTY, s);
            case "AnnotationAssertion":
                return makeToken(ANNOTATIONASSERTION, s);
            case "SubAnnotationPropertyOf":
                return makeToken(SUBANNOTATIONPROPERTYOF, s);
            case "AnnotationPropertyDomain":
                return makeToken(ANNOTATIONPROPERTYDOMAIN, s);
            case "AnnotationPropertyRange":
                return makeToken(ANNOTATIONPROPERTYRANGE, s);
            case "HasKey":
                return makeToken(HASKEY, s);
            case "Declaration":
                return makeToken(DECLARATION, s);
            case "Documentation":
                return makeToken(DOCUMENTATION, s);
            case "Class":
                return makeToken(CLASS, s);
            case "ObjectProperty":
                return makeToken(OBJECTPROP, s);
            case "DataProperty":
                return makeToken(DATAPROP, s);
            case "NamedIndividual":
                return makeToken(NAMEDINDIVIDUAL, s);
            case "Datatype":
                return makeToken(DATATYPE, s);
            case "DataOneOf":
                return makeToken(DATAONEOF, s);
            case "DataUnionOf":
                return makeToken(DATAUNIONOF, s);
            case "DataIntersectionOf":
                return makeToken(DATAINTERSECTIONOF, s);
            case "ObjectOneOf":
                return makeToken(OBJECTONEOF, s);
            case "ObjectUnionOf":
                return makeToken(OBJECTUNIONOF, s);
            case "ObjectHasValue":
                return makeToken(OBJECTHASVALUE, s);
            case "ObjectInverseOf":
                return makeToken(OBJECTINVERSEOF, s);
            case "InverseObjectProperties":
                return makeToken(INVERSEOBJECTPROPERTIES, s);
            case "DataComplementOf":
                return makeToken(DATACOMPLEMENTOF, s);
            case "DatatypeRestriction":
                return makeToken(DATATYPERESTRICTION, s);
            case "DatatypeDefinition":
                return makeToken(DATATYPEDEFINITION, s);
            case "ObjectIntersectionOf":
                return makeToken(OBJECTINTERSECTIONOF, s);
            case "ObjectComplementOf":
                return makeToken(OBJECTCOMPLEMENTOF, s);
            case "ObjectAllValuesFrom":
                return makeToken(OBJECTALLVALUESFROM, s);
            case "ObjectSomeValuesFrom":
                return makeToken(OBJECTSOMEVALUESFROM, s);
            case "ObjectHasSelf":
                return makeToken(OBJECTHASSELF, s);
            case "ObjectMinCardinality":
                return makeToken(OBJECTMINCARDINALITY, s);
            case "ObjectMaxCardinality":
                return makeToken(OBJECTMAXCARDINALITY, s);
            case "ObjectExactCardinality":
                return makeToken(OBJECTEXACTCARDINALITY, s);
            case "DataAllValuesFrom":
                return makeToken(DATAALLVALUESFROM, s);
            case "DataSomeValuesFrom":
                return makeToken(DATASOMEVALUESFROM, s);
            case "DataHasValue":
                return makeToken(DATAHASVALUE, s);
            case "DataMinCardinality":
                return makeToken(DATAMINCARDINALITY, s);
            case "DataMaxCardinality":
                return makeToken(DATAMAXCARDINALITY, s);
            case "DataExactCardinality":
                return makeToken(DATAEXACTCARDINALITY, s);
            case "ObjectPropertyChain":
                return makeToken(SUBOBJECTPROPERTYCHAIN, s);
            case "SubObjectPropertyOf":
                return makeToken(SUBOBJECTPROPERTYOF, s);
            case "EquivalentObjectProperties":
                return makeToken(EQUIVALENTOBJECTPROPERTIES, s);
            case "DisjointObjectProperties":
                return makeToken(DISJOINTOBJECTPROPERTIES, s);
            case "ObjectPropertyDomain":
                return makeToken(OBJECTPROPERTYDOMAIN, s);
            case "ObjectPropertyRange":
                return makeToken(OBJECTPROPERTYRANGE, s);
            case "FunctionalObjectProperty":
                return makeToken(FUNCTIONALOBJECTPROPERTY, s);
            case "InverseFunctionalObjectProperty":
                return makeToken(INVERSEFUNCTIONALOBJECTPROPERTY, s);
            case "ReflexiveObjectProperty":
                return makeToken(REFLEXIVEOBJECTPROPERTY, s);
            case "IrreflexiveObjectProperty":
                return makeToken(IRREFLEXIVEOBJECTPROPERTY, s);
            case "SymmetricObjectProperty":
                return makeToken(SYMMETRICOBJECTPROPERTY, s);
            case "AsymmetricObjectProperty":
                return makeToken(ASYMMETRICOBJECTPROPERTY, s);
            case "TransitiveObjectProperty":
                return makeToken(TRANSITIVEOBJECTPROPERTY, s);
            case "SubDataPropertyOf":
                return makeToken(SUBDATAPROPERTYOF, s);
            case "EquivalentDataProperties":
                return makeToken(EQUIVALENTDATAPROPERTIES, s);
            case "DisjointDataProperties":
                return makeToken(DISJOINTDATAPROPERTIES, s);
            case "DataPropertyDomain":
                return makeToken(DATAPROPERTYDOMAIN, s);
            case "DataPropertyRange":
                return makeToken(DATAPROPERTYRANGE, s);
            case "FunctionalDataProperty":
                return makeToken(FUNCTIONALDATAPROPERTY, s);
            case "SameIndividual":
                return makeToken(SAMEINDIVIDUAL, s);
            case "DifferentIndividuals":
                return makeToken(DIFFERENTINDIVIDUALS, s);
            case "ClassAssertion":
                return makeToken(CLASSASSERTION, s);
            case "ObjectPropertyAssertion":
                return makeToken(OBJECTPROPERTYASSERTION, s);
            case "NegativeObjectPropertyAssertion":
                return makeToken(NEGATIVEOBJECTPROPERTYASSERTION, s);
            case "DataPropertyAssertion":
                return makeToken(DATAPROPERTYASSERTION, s);
            case "NegativeDataPropertyAssertion":
                return makeToken(NEGATIVEDATAPROPERTYASSERTION, s);
            case "Prefix":
                return makeToken(PREFIX, s);
            case "length":
                return makeToken(LENGTH, s);
            case "minLength":
                return makeToken(MINLENGTH, s);
            case "maxLength":
                return makeToken(MAXLENGTH, s);
            case "pattern":
                return makeToken(PATTERN, s);
            case "minInclusive":
                return makeToken(MININCLUSIVE, s);
            case "maxInclusive":
                return makeToken(MAXINCLUSIVE, s);
            case "minExclusive":
                return makeToken(MINEXCLUSIVE, s);
            case "maxExclusive":
                return makeToken(MAXEXCLUSIVE, s);
            case "totalDigits":
                return makeToken(TOTALDIGITS, s);
            case "DLSafeRule":
                return makeToken(DLSAFERULE, s);
            case "Body":
                return makeToken(BODY, s);
            case "Head":
                return makeToken(HEAD, s);
            case "ClassAtom":
                return makeToken(CLASSATOM, s);
            case "DataRangeAtom":
                return makeToken(DATARANGEATOM, s);
            case "ObjectPropertyAtom":
                return makeToken(OBJECTPROPERTYATOM, s);
            case "DataPropertyAtom":
                return makeToken(DATAPROPERTYATOM, s);
            case "BuiltInAtom":
                return makeToken(BUILTINATOM, s);
            case "SameIndividualAtom":
                return makeToken(SAMEINDIVIDUALATOM, s);
            case "DifferentIndividualsAtom":
                return makeToken(DIFFERENTINDIVIDUALSATOM, s);
            case "Variable":
                return makeToken(VARIABLE, s);
            case "DescriptionGraphRule":
                return makeToken(OWLFunctionalSyntaxParserConstants.DGRULE, s);
            case "DescriptionGraph":
                return makeToken(DESCRIPTIONGRAPH, s);
            case "Nodes":
                return makeToken(NODES, s);
            case "NodeAssertion":
                return makeToken(NODEASSERTION, s);
            case "Edges":
                return makeToken(EDGES, s);
            case "EdgeAssertion":
                return makeToken(EDGEASSERTION, s);
            case "MainClasses":
                return makeToken(MAINCLASSES, s);
            default:
                return makeToken(PN_LOCAL, s);
        }
    }

    private Token readNumber(char c) throws IOException {
        buf.setLength(0);
        buf.append(c);
        while (true) {
            c = readChar();
            if (!Character.isDigit(c)) {
                unread(c);
                return makeToken(OWLFunctionalSyntaxParserConstants.INT,
                        buf.toString());
            } else {
                buf.append(c);
            }
        }
    }

    private int lineNo = 1;
    private int colNo = 0;
    private int startLine = -1;
    private int startCol = -1;

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
            c = in.read();
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
