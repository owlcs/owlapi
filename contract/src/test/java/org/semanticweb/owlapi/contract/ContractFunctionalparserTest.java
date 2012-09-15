package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Set;

import org.coode.owlapi.functionalparser.JJTOWLFunctionalSyntaxParserState;
import org.coode.owlapi.functionalparser.Node;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxOWLParser;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParser;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParserConstants;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParserFactory;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParserTokenManager;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParserTreeConstants;
import org.coode.owlapi.functionalparser.ParseException;
import org.coode.owlapi.functionalparser.SimpleNode;
import org.coode.owlapi.functionalparser.Token;
import org.coode.owlapi.functionalparser.TokenMgrError;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractFunctionalparserTest {
    // public void shouldTestJavaCharStream() throws Exception {
    // JavaCharStream testSubject0 = new JavaCharStream(mock(InputStream.class),
    // "", 0,
    // 0, 0);
    // JavaCharStream testSubject1 = new JavaCharStream(mock(InputStream.class),
    // "", 0,
    // 0);
    // JavaCharStream testSubject2 = new JavaCharStream(mock(Reader.class), 0,
    // 0, 0);
    // JavaCharStream testSubject3 = new JavaCharStream(mock(Reader.class), 0,
    // 0);
    // char result0 = testSubject0.readChar();
    // testSubject0.backup(0);
    // char result1 = testSubject0.BeginToken();
    // int result4 = testSubject0.getEndColumn();
    // int result5 = testSubject0.getEndLine();
    // int result6 = testSubject0.getBeginColumn();
    // int result7 = testSubject0.getBeginLine();
    // testSubject0.ReInit(mock(Reader.class), 0, 0, 0);
    // testSubject0.ReInit(mock(InputStream.class), "", 0, 0, 0);
    // testSubject0.ReInit(mock(InputStream.class), "", 0, 0);
    // testSubject0.ReInit(mock(Reader.class), 0, 0);
    // String result8 = testSubject0.GetImage();
    // char[] result9 = testSubject0.GetSuffix(0);
    // testSubject0.Done();
    // testSubject0.adjustBeginLineColumn(0, 0);
    // String result10 = testSubject0.toString();
    // }

    public void shouldTestJJTOWLFunctionalSyntaxParserState() throws Exception {
        JJTOWLFunctionalSyntaxParserState testSubject0 = new JJTOWLFunctionalSyntaxParserState();
        testSubject0.reset();
        boolean result0 = testSubject0.nodeCreated();
        Node result1 = testSubject0.rootNode();
        testSubject0.pushNode(mock(Node.class));
        Node result2 = testSubject0.popNode();
        Node result3 = testSubject0.peekNode();
        int result4 = testSubject0.nodeArity();
        testSubject0.clearNodeScope(mock(Node.class));
        testSubject0.openNodeScope(mock(Node.class));
        testSubject0.closeNodeScope(mock(Node.class), 0);
        testSubject0.closeNodeScope(mock(Node.class), false);
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceNode() throws Exception {
        Node testSubject0 = mock(Node.class);
        testSubject0.jjtOpen();
        testSubject0.jjtSetParent(mock(Node.class));
        testSubject0.jjtAddChild(mock(Node.class), 0);
        testSubject0.jjtClose();
        Node result0 = testSubject0.jjtGetParent();
        Node result1 = testSubject0.jjtGetChild(0);
        int result2 = testSubject0.jjtGetNumChildren();
    }

    public void shouldTestOWLFunctionalSyntaxOWLParser() throws Exception {
        OWLFunctionalSyntaxOWLParser testSubject0 = new OWLFunctionalSyntaxOWLParser();
        OWLOntologyFormat result0 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result1 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
        OWLOntologyFormat result2 = testSubject0.parse(IRI.create("urn:aFake"),
                Utils.getMockOntology());
        String result4 = testSubject0.toString();
    }

    public void shouldTestOWLFunctionalSyntaxParser() throws Exception {
        OWLFunctionalSyntaxParser testSubject0 = new OWLFunctionalSyntaxParser(
                mock(OWLFunctionalSyntaxParserTokenManager.class));
        OWLFunctionalSyntaxParser testSubject1 = new OWLFunctionalSyntaxParser(
                mock(InputStream.class));
        OWLFunctionalSyntaxParser testSubject2 = new OWLFunctionalSyntaxParser(
                mock(InputStream.class), "");
        OWLFunctionalSyntaxParser testSubject3 = new OWLFunctionalSyntaxParser(
                mock(Reader.class));
        OWLFunctionalSyntaxOntologyFormat result0 = testSubject0.parse();
        IRI result1 = testSubject0.getIRI("");
        OWLAxiom result2 = testSubject0.Declaration();
        OWLClassAxiom result3 = testSubject0.EquivalentClasses();
        OWLClassAxiom result4 = testSubject0.SubClassOf();
        OWLClassAxiom result5 = testSubject0.DisjointClasses();
        OWLClassAxiom result6 = testSubject0.DisjointUnion();
        OWLIndividualAxiom result7 = testSubject0.ClassAssertion();
        OWLIndividualAxiom result8 = testSubject0.DifferentIndividuals();
        OWLIndividualAxiom result9 = testSubject0.ObjectPropertyAssertion();
        OWLIndividualAxiom result10 = testSubject0.NegativeObjectPropertyAssertion();
        OWLIndividualAxiom result11 = testSubject0.DataPropertyAssertion();
        OWLIndividualAxiom result12 = testSubject0.NegativeDataPropertyAssertion();
        OWLPropertyAxiom result13 = testSubject0.EquivalentObjectProperties();
        OWLPropertyAxiom result14 = testSubject0.SubObjectPropertyOf();
        OWLPropertyAxiom result15 = testSubject0.InverseObjectProperties();
        OWLPropertyAxiom result16 = testSubject0.FunctionalObjectProperty();
        OWLPropertyAxiom result17 = testSubject0.InverseFunctionalObjectProperty();
        OWLPropertyAxiom result18 = testSubject0.SymmetricObjectProperty();
        OWLPropertyAxiom result19 = testSubject0.AsymmetricObjectProperty();
        OWLPropertyAxiom result20 = testSubject0.TransitiveObjectProperty();
        OWLPropertyAxiom result21 = testSubject0.ReflexiveObjectProperty();
        OWLPropertyAxiom result22 = testSubject0.ObjectPropertyDomain();
        OWLPropertyAxiom result23 = testSubject0.ObjectPropertyRange();
        OWLPropertyAxiom result24 = testSubject0.DisjointObjectProperties();
        OWLPropertyAxiom result25 = testSubject0.EquivalentDataProperties();
        OWLPropertyAxiom result26 = testSubject0.SubDataPropertyOf();
        OWLPropertyAxiom result27 = testSubject0.FunctionalDataProperty();
        OWLPropertyAxiom result28 = testSubject0.DataPropertyDomain();
        OWLPropertyAxiom result29 = testSubject0.DataPropertyRange();
        OWLPropertyAxiom result30 = testSubject0.DisjointDataProperties();
        OWLHasKeyAxiom result31 = testSubject0.HasKey();
        OWLAnnotationAssertionAxiom result32 = testSubject0.AnnotationAssertion();
        OWLSubAnnotationPropertyOfAxiom result33 = testSubject0.SubAnnotationPropertyOf();
        OWLAnnotationPropertyDomainAxiom result34 = testSubject0
                .AnnotationPropertyDomain();
        OWLClass result35 = testSubject0.Class();
        OWLClassExpression result36 = testSubject0.ObjectSomeValuesFrom();
        OWLClassExpression result37 = testSubject0.ObjectAllValuesFrom();
        OWLClassExpression result38 = testSubject0.ObjectMinCardinality();
        OWLClassExpression result39 = testSubject0.ObjectMaxCardinality();
        OWLClassExpression result40 = testSubject0.ObjectExactCardinality();
        OWLClassExpression result41 = testSubject0.ObjectHasValue();
        OWLClassExpression result42 = testSubject0.DataSomeValuesFrom();
        OWLClassExpression result43 = testSubject0.DataAllValuesFrom();
        OWLClassExpression result44 = testSubject0.DataMinCardinality();
        OWLClassExpression result45 = testSubject0.DataMaxCardinality();
        OWLClassExpression result46 = testSubject0.DataExactCardinality();
        OWLClassExpression result47 = testSubject0.DataHasValue();
        OWLClassExpression result48 = testSubject0.ObjectIntersectionOf();
        OWLClassExpression result49 = testSubject0.ObjectUnionOf();
        OWLClassExpression result50 = testSubject0.ObjectComplementOf();
        OWLClassExpression result51 = testSubject0.ObjectOneOf();
        OWLDatatype result52 = testSubject0.Datatype();
        OWLDataRange result53 = testSubject0.DataOneOf();
        OWLDataRange result54 = testSubject0.DataComplementOf();
        OWLDataRange result55 = testSubject0.DataUnionOf();
        OWLDataRange result56 = testSubject0.DataIntersectionOf();
        OWLObjectProperty result57 = testSubject0.ObjectProperty();
        OWLDataProperty result58 = testSubject0.DataProperty();
        OWLAnnotationProperty result59 = testSubject0.AnnotationProperty();
        OWLNamedIndividual result60 = testSubject0.NamedIndividual();
        testSubject0.Ontology();
        OWLAnnotation result61 = testSubject0.Annotation();
        OWLIndividual result62 = testSubject0.Individual();
        OWLLiteral result63 = testSubject0.Literal();
        OWLDataRange result64 = testSubject0.DataRange();
        OWLAxiom result65 = testSubject0.Axiom();
        IRI result66 = testSubject0.IRI();
        OWLAnonymousIndividual result67 = testSubject0.AnonymousIndividual();
        OWLPropertyAxiom result68 = testSubject0.IrreflexiveObjectProperty();
        OWLAnnotationPropertyRangeAxiom result69 = testSubject0.AnnotationPropertyRange();
        testSubject0.Prefix();
        IRI result70 = testSubject0.AbbreviatedIRI();
        SWRLRule result71 = testSubject0.DLSafeRule();
        SWRLClassAtom result72 = testSubject0.ClassAtom();
        SWRLDataRangeAtom result73 = testSubject0.DataRangeAtom();
        SWRLObjectPropertyAtom result74 = testSubject0.ObjectPropertyAtom();
        SWRLDataPropertyAtom result75 = testSubject0.DataPropertyAtom();
        SWRLBuiltInAtom result76 = testSubject0.BuiltInAtom();
        SWRLSameIndividualAtom result77 = testSubject0.SameIndividualAtom();
        SWRLDifferentIndividualsAtom result78 = testSubject0.DifferentIndividualsAtom();
        testSubject0.ReInit(mock(InputStream.class));
        testSubject0.ReInit(mock(InputStream.class), "");
        testSubject0.ReInit(mock(Reader.class));
        testSubject0.ReInit(mock(OWLFunctionalSyntaxParserTokenManager.class));
        int result79 = testSubject0.Integer();
        Token result80 = testSubject0.getNextToken();
        ParseException result81 = testSubject0.generateParseException();
        Token result82 = testSubject0.getToken(0);
        testSubject0.enable_tracing();
        testSubject0.disable_tracing();
        testSubject0.setIgnoreAnnotationsAndDeclarations(false);
        testSubject0.setUp(Utils.getMockOntology(), new OWLOntologyLoaderConfiguration());
        testSubject0.setPrefixes(new DefaultPrefixManager());
        OWLImportsDeclaration result83 = testSubject0.ImportsDeclaration();
        String result84 = testSubject0.PrefixName();
        IRI result85 = testSubject0.FullIRI();
        OWLClassExpression result86 = testSubject0.ClassExpression();
        OWLClass result87 = testSubject0.ClassIRI();
        OWLClassExpression result88 = testSubject0.ObjectSelf();
        Set<OWLClassExpression> result89 = testSubject0.ClassExpressionSet();
        Set<OWLIndividual> result90 = testSubject0.IndividualMinOneSet();
        OWLObjectPropertyExpression result91 = testSubject0.ObjectPropertyExpression();
        int result92 = testSubject0.Cardinality();
        OWLDataPropertyExpression result93 = testSubject0.DataPropertyExpression();
        OWLObjectPropertyExpression result94 = testSubject0.InverseObjectProperty();
        OWLObjectProperty result95 = testSubject0.ObjectPropertyIRI();
        OWLDataProperty result96 = testSubject0.DataPropertyIRI();
        OWLAnnotationProperty result97 = testSubject0.AnnotationPropertyIRI();
        OWLNamedIndividual result98 = testSubject0.IndividualIRI();
        OWLDatatype result99 = testSubject0.DatatypeIRI();
        OWLDatatypeDefinitionAxiom result100 = testSubject0.DatatypeDefinitionAxiom();
        Set<OWLAnnotation> result101 = testSubject0.AxiomAnnotationSet();
        OWLDataRange result102 = testSubject0.DataRangeRestriction();
        OWLFacetRestriction result103 = testSubject0.DataRangeFacetRestriction();
        OWLClassAxiom result104 = testSubject0.ClassAxiom();
        OWLPropertyAxiom result105 = testSubject0.ObjectPropertyAxiom();
        OWLPropertyAxiom result106 = testSubject0.DataPropertyAxiom();
        OWLIndividualAxiom result107 = testSubject0.IndividualAxiom();
        OWLAxiom result108 = testSubject0.AnnotationAxiom();
        List<OWLObjectPropertyExpression> result109 = testSubject0
                .SubObjectPropertyChain();
        Set<OWLObjectPropertyExpression> result110 = testSubject0.ObjectPropertySet();
        Set<OWLDataPropertyExpression> result111 = testSubject0.DataPropertySet();
        OWLIndividualAxiom result112 = testSubject0.SameIndividuals();
        Set<OWLIndividual> result113 = testSubject0.IndividualSet();
        OWLAnnotationValue result114 = testSubject0.AnnotationValue();
        OWLAnnotationSubject result115 = testSubject0.AnnotationSubject();
        OWLEntity result116 = testSubject0.Entity();
        String result117 = testSubject0.QuotedString();
        String result118 = testSubject0.LangTag();
        SWRLAtom result119 = testSubject0.Atom();
        SWRLIArgument result120 = testSubject0.IArg();
        SWRLDArgument result121 = testSubject0.DArg();
        String result122 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLFunctionalSyntaxParserConstants() throws Exception {
        OWLFunctionalSyntaxParserConstants testSubject0 = mock(OWLFunctionalSyntaxParserConstants.class);
    }

    @Test
    public void shouldTestOWLFunctionalSyntaxParserFactory() throws Exception {
        OWLFunctionalSyntaxParserFactory testSubject0 = new OWLFunctionalSyntaxParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
        String result1 = testSubject0.toString();
    }

    public void shouldTestInterfaceOWLFunctionalSyntaxParserTreeConstants()
            throws Exception {
        OWLFunctionalSyntaxParserTreeConstants testSubject0 = mock(OWLFunctionalSyntaxParserTreeConstants.class);
    }

    public void shouldTestParseException() throws Exception {
        ParseException testSubject0 = new ParseException(mock(Token.class),
                mock(int[][].class), new String[0]);
        ParseException testSubject1 = new ParseException();
        ParseException testSubject2 = new ParseException("");
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    public void shouldTestSimpleNode() throws Exception {
        SimpleNode testSubject0 = new SimpleNode(mock(OWLFunctionalSyntaxParser.class), 0);
        SimpleNode testSubject1 = new SimpleNode(0);
        String result0 = testSubject0.toString();
        String result1 = testSubject0.toString("");
        testSubject0.dump("");
        testSubject0.jjtOpen();
        testSubject0.jjtSetParent(mock(Node.class));
        testSubject0.jjtAddChild(mock(Node.class), 0);
        testSubject0.jjtClose();
        Node result2 = testSubject0.jjtGetParent();
        Node result3 = testSubject0.jjtGetChild(0);
        int result4 = testSubject0.jjtGetNumChildren();
        testSubject0.jjtSetValue(mock(Object.class));
        Object result5 = testSubject0.jjtGetValue();
    }

    @Test
    public void shouldTestToken() throws Exception {
        Token testSubject0 = new Token();
        Token testSubject1 = new Token(0);
        Token testSubject2 = new Token(0, "");
        String result0 = testSubject0.toString();
        Object result1 = testSubject0.getValue();
        Token result2 = Token.newToken(0, "");
        Token result3 = Token.newToken(0);
    }

    public void shouldTestTokenMgrError() throws Exception {
        TokenMgrError testSubject0 = new TokenMgrError();
        TokenMgrError testSubject1 = new TokenMgrError("", 0);
        TokenMgrError testSubject2 = new TokenMgrError(false, 0, 0, 0, "",
                mock(char.class), 0);
        String result0 = testSubject0.getMessage();
        Throwable result2 = testSubject0.getCause();
        String result4 = testSubject0.toString();
        String result5 = testSubject0.getLocalizedMessage();
    }
}
