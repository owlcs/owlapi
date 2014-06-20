package org.semanticweb.owlapi.functional.parser;

import com.sun.javafx.css.Declaration;
import com.sun.org.apache.xpath.internal.operations.Variable;
import com.sun.tools.internal.ws.wsdl.document.Documentation;

import java.io.EOFException;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import static org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxParserConstants.*;

/**
 * Created by ses on 6/9/14.
 */
public class CustomTokenizer implements TokenManager {

    private final PushbackReader in;
    private boolean eofSeen = false;

    public CustomTokenizer(Reader reader) {
        if (reader instanceof PushbackReader) {
            PushbackReader pushbackReader = (PushbackReader) reader;
            this.in = pushbackReader;
        } else {
            this.in = new PushbackReader(reader);
        }
    }

    /**
     * This gets the next token from the input stream.
     * A token of kind 0 (<EOF>) should be returned on EOF.
     */
    @Override
    public Token getNextToken() {
        while (true) {
            char c;
            try {
                c = findTokenStart();
                switch (c) {
                    case '(':
                        return new Token(OPENPAR,"(");
                    case ')':
                        return new Token(CLOSEPAR,")");
                    case '@':
                        return new Token(LANGIDENTIFIER,"@");
                    case '^':
                        c = readChar();
                        if(c == '^') {
                          return new Token(DATATYPEIDENTIFIER,"^^");
                        }  else {
                            return new Token(ERROR,"^" + c);
                        }
                    case '=':
                        return new Token(EQUALS,"=");
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
                return new Token(EOF, "");
            }
        }
    }

    private void skipComment() throws IOException {
        for(char c = readChar(); c != '\n'; c = readChar()) {

        }

    }

    private Token readStringLiteralToken() throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append('"');
        while (true) {
            char c = readChar();
            switch(c) {
                case '"':
                    buf.append(c);
                    return new Token(STRINGLITERAL,buf.toString());
                case '\\':
                    buf.append(c);
                    c = readChar();
                    if(c != '\\' && c != '\"') {
                        return new Token(ERROR,"Bad escape sequence in StringLiteral");
                    }
                    // fallthrough
                default:
                    buf.append(c);
            }
        }
    }

    private Token readFullIRI() {
        try {
            StringBuilder buf = new StringBuilder();
            buf.append('<');
            while (true) {
                char c = readChar();
                buf.append(c);
                if (c == '>') {
                    return new Token(FULLIRI, buf.toString());
                }
            }
        } catch (IOException e) {
            return new Token(ERROR, "<");
        }

    }

    private Token readTextualToken(char c) throws IOException {
        if(c >='0' && c <='9') {
            return readNumber(c);
        }
        StringBuilder buf = new StringBuilder();
        buf.append(c);
        int colonIndex = -1;
        loop:
        while (true) {
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
                        in.unread(c);
                        break loop;
                    case ':':
                        colonIndex = buf.length();  // and fall through
                    default:
                        buf.append(c);
                }
            } catch (EOFException eof) {
                break;
            }
        }
        String s = buf.toString();
        if(colonIndex >=0) {
           // System.out.println("colonIndex >=0 - so expect abbreviated IRI from " + buf);
            if(colonIndex == s.length()-1) {
                return new Token(PNAME_NS,s);
            } else {
                return new Token(OWLFunctionalSyntaxParserConstants.PNAME_LN, s);
            }
        }
        switch (s) {
            case "Ontology":
                return new Token(ONTOLOGY, s);
            case "Label":
                return new Token(LABEL, s);
            case "Comment":
                return new Token(LABEL, s);
            case "SubClassOf":
                return new Token(SUBCLASSOF, s);
            case "EquivalentClasses":
                return new Token(EQUIVALENTCLASSES, s);
            case "DisjointClasses":
                return new Token(DISJOINTCLASSES, s);
            case "DisjointUnion":
                return new Token(DISJOINTUNION, s);
            case "Annotation":
                return new Token(ANNOTATION, s);
            case "AnnotationProperty":
                return new Token(ANNOTATIONPROPERTY, s);
            case "AnnotationAssertion":
                return new Token(ANNOTATIONASSERTION, s);
            case "SubAnnotationPropertyOf":
                return new Token(SUBANNOTATIONPROPERTYOF, s);
            case "AnnotationPropertyDomain":
                return new Token(ANNOTATIONPROPERTYDOMAIN, s);
            case "AnnotationPropertyRange":
                return new Token(ANNOTATIONPROPERTYRANGE, s);
            case "HasKey":
                return new Token(HASKEY, s);
            case "Declaration":
                return new Token(DECLARATION , s);
            case "Documentation":
                return new Token(DOCUMENTATION, s);
            case "Class":
                return new Token(CLASS, s);
            case "ObjectProperty":
                return new Token(OBJECTPROP, s);
            case "DataProperty":
                return new Token(DATAPROP, s);
            case "NamedIndividual":
                return new Token(NAMEDINDIVIDUAL, s);
            case "Datatype":
                return new Token(DATATYPE, s);
            case "DataOneOf":
                return new Token(DATAONEOF, s);
            case "DataUnionOf":
                return new Token(DATAUNIONOF, s);
            case "DataIntersectionOf":
                return new Token(DATAINTERSECTIONOF, s);
            case "ObjectUnionOf":
                return new Token(OBJECTUNIONOF, s);
            case "ObjectHasValue":
                return new Token(OBJECTHASVALUE, s);
            case "ObjectInverseOf":
                return new Token(OBJECTINVERSEOF, s);
            case "InverseObjectProperties":
                return new Token(INVERSEOBJECTPROPERTIES, s);
            case "DataComplementOf":
                return new Token(DATACOMPLEMENTOF, s);
            case "DatatypeRestriction":
                return new Token(DATATYPERESTRICTION, s);
            case "ObjectIntersectionOf":
                return new Token(OBJECTINTERSECTIONOF, s);
            case "ObjectComplementOf":
                return new Token(OBJECTCOMPLEMENTOF, s);
            case "ObjectAllValuesFrom":
                return new Token(OBJECTALLVALUESFROM, s);
            case "ObjectSomeValuesFrom":
                return new Token(OBJECTSOMEVALUESFROM, s);
            case "ObjectHasSelf":
                return new Token(OBJECTHASSELF, s);
            case "ObjectMinCardinality":
                return new Token(OBJECTMINCARDINALITY, s);
            case "ObjectMaxCardinality":
                return new Token(OBJECTMAXCARDINALITY, s);
            case "ObjectExactCardinality":
                return new Token(OBJECTEXACTCARDINALITY, s);
            case "DataAllValuesFrom":
                return new Token(DATAALLVALUESFROM, s);
            case "DataSomeValuesFrom":
                return new Token(DATASOMEVALUESFROM, s);
            case "DataHasValue":
                return new Token(DATAHASVALUE, s);
            case "DataMinCardinality":
                return new Token(DATAMINCARDINALITY, s);
            case "DataMaxCardinality":
                return new Token(DATAMAXCARDINALITY, s);
            case "DataExactCardinality":
                return new Token(DATAEXACTCARDINALITY, s);
            case "ObjectPropertyChain":
                return new Token(SUBOBJECTPROPERTYCHAIN, s);
            case "SubObjectPropertyOf":
                return new Token(SUBOBJECTPROPERTYOF, s);
            case "EquivalentObjectProperties":
                return new Token(EQUIVALENTOBJECTPROPERTIES, s);
            case "DisjointObjectProperties":
                return new Token(DISJOINTOBJECTPROPERTIES, s);
            case "ObjectPropertyDomain":
                return new Token(OBJECTPROPERTYDOMAIN, s);
            case "ObjectPropertyRange":
                return new Token(OBJECTPROPERTYRANGE, s);
            case "FunctionalObjectProperty":
                return new Token(FUNCTIONALOBJECTPROPERTY, s);
            case "InverseFunctionalObjectProperty":
                return new Token(INVERSEFUNCTIONALOBJECTPROPERTY, s);
            case "ReflexiveObjectProperty":
                return new Token(REFLEXIVEOBJECTPROPERTY, s);
            case "IrreflexiveObjectProperty":
                return new Token(IRREFLEXIVEOBJECTPROPERTY, s);
            case "SymmetricObjectProperty":
                return new Token(SYMMETRICOBJECTPROPERTY, s);
            case "AsymmetricObjectProperty":
                return new Token(ASYMMETRICOBJECTPROPERTY, s);
            case "TransitiveObjectProperty":
                return new Token(TRANSITIVEOBJECTPROPERTY, s);
            case "SubDataPropertyOf":
                return new Token(SUBDATAPROPERTYOF, s);
            case "EquivalentDataProperties":
                return new Token(EQUIVALENTDATAPROPERTIES, s);
            case "DisjointDataProperties":
                return new Token(DISJOINTDATAPROPERTIES, s);
            case "DataPropertyDomain":
                return new Token(DATAPROPERTYDOMAIN, s);
            case "DataPropertyRange":
                return new Token(DATAPROPERTYRANGE, s);
            case "FunctionalDataProperty":
                return new Token(FUNCTIONALDATAPROPERTY, s);
            case "SameIndividual":
                return new Token(SAMEINDIVIDUAL, s);
            case "DifferentIndividuals":
                return new Token(DIFFERENTINDIVIDUALS, s);
            case "ClassAssertion":
                return new Token(CLASSASSERTION, s);
            case "OBJECTPROPERTYASSERTION":
                return new Token(OBJECTPROPERTYASSERTION, s);
            case "NegativeObjectPropertyAssertion":
                return new Token(NEGATIVEOBJECTPROPERTYASSERTION, s);
            case "DataPropertyAssertion":
                return new Token(DATAPROPERTYASSERTION, s);
            case "NegativeDataPropertyAssertion":
                return new Token(NEGATIVEDATAPROPERTYASSERTION, s);
            case "Prefix":
                return new Token(PREFIX, s);
            case "length":
                return new Token(LENGTH, s);
            case "minLength":
                return new Token(MINLENGTH, s);
            case "maxLength":
                return new Token(MAXLENGTH, s);
            case "pattern":
                return new Token(PATTERN, s);
            case "minInclusive":
                return new Token(MININCLUSIVE, s);
            case "maxInclusive":
                return new Token(MAXINCLUSIVE, s);
            case "minExclusive":
                return new Token(MINEXCLUSIVE, s);
            case "maxExclusive":
                return new Token(MAXEXCLUSIVE, s);
            case "totalDigits":
                return new Token(TOTALDIGITS, s);
            case "DLSafeRule":
                return new Token(DLSAFERULE, s);
            case "Body":
                return new Token(BODY, s);
            case "Head":
                return new Token(HEAD, s);
            case "ClassAtom":
                return new Token(CLASSATOM, s);
            case "DataRangeAtom":
                return new Token(DATARANGEATOM, s);
            case "ObjectPropertyAtom":
                return new Token(OBJECTPROPERTYATOM, s);
            case "DataPropertyAtom":
                return new Token(DATAPROPERTYATOM, s);
            case "BuiltInAtom":
                return new Token(BUILTINATOM, s);
            case "SameIndividualAtom":
                return new Token(SAMEINDIVIDUALATOM, s);
            case "DifferentIndividualsAtom":
                return new Token(DIFFERENTINDIVIDUALSATOM, s);
            case "Variable":
                return new Token(VARIABLE, s);
            case "DescriptionGraphRule":
                return new Token(OWLFunctionalSyntaxParserConstants.DGRULE, s);
            case "DescriptionGraph":
                return new Token(DESCRIPTIONGRAPH, s);
            case "Nodes":
                return new Token(NODES, s);
            case "NodeAssertion":
                return new Token(NODEASSERTION, s);
            case "Edges":
                return new Token(EDGES, s);
            case "EdgeAssertion":
                return new Token(EDGEASSERTION, s);
            case "MainClasses":
                return new Token(MAINCLASSES, s);
            default:
                return new Token(PNAME_NS,s);
        }

    }

    private Token readNumber(char c) throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append(c);
        while(true) {
            c = readChar();
            if(!Character.isDigit(c)) {
                in.unread(c);
                return new Token(OWLFunctionalSyntaxParserConstants.INT,buf.toString());
            }
        }
    }


    private char findTokenStart() throws IOException {
        while (true) {
            char c = readChar();
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return c;
            }
        }
    }

    private char readChar() throws IOException {
        if (eofSeen) {
            throw new EOFException();
        }
        int c = in.read();
        if (c < 0) {
            eofSeen = true;
            throw new EOFException();
        } else {
            return (char) c;
        }
    }
}
