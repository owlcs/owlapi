package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;

@SuppressWarnings("javadoc")
public class PrimerTestCase extends TestBase {

    protected @Nonnull OWLOntology func = loadOntologyFromString(FUNCTIONAL, IRI.create("urn:primer#functional"),
            new FunctionalSyntaxDocumentFormat());
    OWL2DLProfile profile = new OWL2DLProfile();

    @Before
    public void setUpProfile() {
        assertTrue(profile.checkOntology(func).isInProfile());
    }

    @Test
    public void shouldManchBeEquivalent() throws OWLOntologyCreationException {
        OWLOntology manch = loadOntologyFromString(MANCHESTER, IRI.create("urn:primer#manchester"),
                new ManchesterSyntaxDocumentFormat());
        assertTrue(profile.checkOntology(manch).getViolations().isEmpty());
        // XXX Manchester OWL Syntax does not support GCIs
        // the input adopts a trick to semantically get around this, by
        // asserting a new named class equivalent to the right hand side of the
        // GCI and subclass of the left hand side
        // Rectifying this to be able to assert equality, and using a different
        // ontology
        // so that the equality test does not skip gcis because of the format
        OWLClass x = df.getOWLClass("http://example.com/owl/families/X");
        Set<OWLClassAxiom> axioms = asSet(manch.axioms(x));
        manch.remove(axioms);
        OWLClass female = df.getOWLClass("http://example.com/owl/families/Female");
        OWLClassExpression oneOf = df.getOWLObjectOneOf(
                df.getOWLNamedIndividual("http://example.com/owl/families/Bill"),
                df.getOWLNamedIndividual("http://example.com/owl/families/Mary"),
                df.getOWLNamedIndividual("http://example.com/owl/families/Meg"));
        OWLClass parent = df.getOWLClass("http://example.com/owl/families/Parent");
        OWLObjectProperty hasChild = df.getOWLObjectProperty("http://example.com/owl/families/hasChild");
        OWLClassExpression superClass = df.getOWLObjectIntersectionOf(parent,
                df.getOWLObjectAllValuesFrom(hasChild, female), df.getOWLObjectMaxCardinality(1, hasChild));
        manch.getOWLOntologyManager().addAxiom(manch,
                df.getOWLSubClassOfAxiom(df.getOWLObjectIntersectionOf(female, oneOf), superClass));
        OWLOntology replacement = OWLManager.createOWLOntologyManager().createOntology(manch.axioms(),
                get(manch.getOntologyID().getOntologyIRI()));
        equal(func, replacement);
    }

    @Test
    public void shouldRDFXMLBeEquivalent() {
        OWLOntology rdf = loadOntologyFromString(RDFXML, IRI.create("urn:primer#rdfxml"), new RDFXMLDocumentFormat());
        assertTrue(profile.checkOntology(rdf).getViolations().isEmpty());
        equal(func, rdf);
    }

    @Test
    public void shouldOWLXMLBeEquivalent() {
        OWLOntology owl = loadOntologyFromString(OWLXML, IRI.create("urn:primer#owlxml"), new OWLXMLDocumentFormat());
        assertTrue(profile.checkOntology(owl).getViolations().isEmpty());
        equal(func, owl);
    }

    @Test
    public void shouldTURTLEBeEquivalent() {
        OWLOntology turt = loadOntologyFromString(TURTLE, IRI.create("urn:primer#turtle"), new TurtleDocumentFormat());
        assertTrue(profile.checkOntology(turt).getViolations().isEmpty());
        // XXX somehow the Turtle parser introduces a tautology: the inverse of
        // inverse(hasParent) is hasParent
        // dropping said tautology to assert equality of the rest of the axioms
        OWLObjectProperty hasParent = df.getOWLObjectProperty("http://example.com/owl/families/hasParent");
        turt.remove(df.getOWLInverseObjectPropertiesAxiom(df.getOWLObjectInverseOf(hasParent), hasParent));
        equal(func, turt);
    }

    private static final @Nonnull String RDFXML = "<!DOCTYPE rdf:RDF [\n"
            + "    <!ENTITY owl \"http://www.w3.org/2002/07/owl#\" >\n"
            + "    <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n"
            + "    <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" >\n"
            + "    <!ENTITY otherOnt \"http://example.org/otherOntologies/families/\" >\n" + "]>\n" + '\n'
            + " <rdf:RDF xml:base=\"http://example.com/owl/families/\" xmlns=\"http://example.com/owl/families/\" xmlns:otherOnt=\"http://example.org/otherOntologies/families/\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\">\n"
            + "   <owl:Ontology rdf:about=\"http://example.com/owl/families\"/>\n"
            + "<owl:ObjectProperty rdf:about=\"hasUncle\"/>\n"
            + "<owl:ObjectProperty rdf:about=\"http://example.org/otherOntologies/families/child\"/>\n"
            + "<owl:DatatypeProperty rdf:about=\"http://example.org/otherOntologies/families/age\"/>\n"
            + "<owl:ObjectProperty rdf:about=\"hasDaughter\"/>\n"
            + "<owl:ObjectProperty rdf:about=\"hasGrandparent\"/>\n" + "<owl:ObjectProperty rdf:about=\"parentOf\"/>\n"
            + "<owl:ObjectProperty rdf:about=\"loves\"/>\n" + "<owl:ObjectProperty rdf:about=\"hasRelative\"/>\n"
            + "<owl:DatatypeProperty rdf:about=\"hasSSN\"/>\n"
            + "<owl:Class rdf:about=\"http://example.org/otherOntologies/families/Grownup\"/>\n"
            + "<owl:Class rdf:about=\"SocialRole\"/>\n" + "<owl:Class rdf:about=\"Dead\"/>\n"
            + "<owl:Class rdf:about=\"Human\"/>\n"
            + "<rdfs:Datatype rdf:about=\"http://example.com/owl/families/personAge\"/>\n"
            + "<rdfs:Datatype rdf:about=\"http://example.com/owl/families/minorAge\"/>\n"
            + "<rdfs:Datatype rdf:about=\"http://example.com/owl/families/majorAge\"/>\n"
            + "<rdfs:Datatype rdf:about=\"http://example.com/owl/families/toddlerAge\"/>\n"
            + "   <owl:ObjectProperty rdf:about=\"hasWife\"><rdfs:subPropertyOf rdf:resource=\"hasSpouse\"/><rdfs:domain rdf:resource=\"Man\"/><rdfs:range rdf:resource=\"Woman\"/></owl:ObjectProperty>\n"
            + "   <owl:ObjectProperty rdf:about=\"hasParent\"><owl:inverseOf rdf:resource=\"hasChild\"/><owl:propertyDisjointWith rdf:resource=\"hasSpouse\"/></owl:ObjectProperty>\n"
            + "   <owl:ObjectProperty rdf:about=\"hasSon\"><owl:propertyDisjointWith rdf:resource=\"hasDaughter\"/></owl:ObjectProperty>\n"
            + "   <owl:ObjectProperty rdf:about=\"hasFather\"><rdfs:subPropertyOf rdf:resource=\"hasParent\"/></owl:ObjectProperty>\n"
            + '\n' + "   <owl:SymmetricProperty rdf:about=\"hasSpouse\"/>\n"
            + "   <owl:AsymmetricProperty rdf:about=\"hasChild\"/>\n"
            + "   <owl:ReflexiveProperty rdf:about=\"hasRelative\"/>\n"
            + "   <owl:IrreflexiveProperty rdf:about=\"parentOf\"/>\n"
            + "   <owl:FunctionalProperty rdf:about=\"hasHusband\"/>\n"
            + "   <owl:InverseFunctionalProperty rdf:about=\"hasHusband\"/>\n"
            + "   <owl:TransitiveProperty rdf:about=\"hasAncestor\"/>\n" + '\n'
            + "   <rdf:Description rdf:about=\"hasGrandparent\"><owl:propertyChainAxiom rdf:parseType=\"Collection\"><owl:ObjectProperty rdf:about=\"hasParent\"/><owl:ObjectProperty rdf:about=\"hasParent\"/></owl:propertyChainAxiom></rdf:Description>\n"
            + "   <rdf:Description rdf:about=\"hasUncle\"><owl:propertyChainAxiom rdf:parseType=\"Collection\"><owl:ObjectProperty rdf:about=\"hasFather\"/><owl:ObjectProperty rdf:about=\"hasBrother\"/></owl:propertyChainAxiom></rdf:Description>\n"
            + "   <owl:ObjectProperty rdf:about=\"hasChild\"><owl:equivalentProperty rdf:resource=\"&otherOnt;child\"/></owl:ObjectProperty>\n"
            + "    <owl:DatatypeProperty rdf:about=\"hasAge\"><rdfs:domain rdf:resource=\"Person\"/><rdfs:range rdf:resource=\"&xsd;nonNegativeInteger\"/><owl:equivalentProperty rdf:resource=\"&otherOnt;age\"/></owl:DatatypeProperty>\n"
            + "   <owl:FunctionalProperty rdf:about=\"hasAge\"/>\n" + '\n'
            + "   <owl:Class rdf:about=\"Woman\"><rdfs:subClassOf rdf:resource=\"Person\"/></owl:Class>\n"
            + "   <owl:Class rdf:about=\"Mother\"><rdfs:subClassOf rdf:resource=\"Woman\"/><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"Woman\"/><owl:Class rdf:about=\"Parent\"/></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n"
            + "   <owl:Class rdf:about=\"Person\"><rdfs:comment>Represents the set of all people.</rdfs:comment><owl:equivalentClass rdf:resource=\"Human\"/><owl:hasKey rdf:parseType=\"Collection\"><owl:DatatypeProperty rdf:about=\"hasSSN\"/></owl:hasKey></owl:Class>\n"
            + "   <owl:Class rdf:about=\"Parent\"><owl:equivalentClass><owl:Class><owl:unionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"Mother\"/><owl:Class rdf:about=\"Father\"/></owl:unionOf></owl:Class></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"hasChild\"/><owl:someValuesFrom rdf:resource=\"Person\"/></owl:Restriction></owl:equivalentClass></owl:Class>\n"
            + "   <owl:Class rdf:about=\"ChildlessPerson\"><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"Person\"/><owl:Class><owl:complementOf rdf:resource=\"Parent\"/></owl:Class></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n"
            + "   <owl:Class rdf:about=\"Grandfather\"><rdfs:subClassOf><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"Man\"/><owl:Class rdf:about=\"Parent\"/></owl:intersectionOf></owl:Class></rdfs:subClassOf></owl:Class>\n"
            + "   <owl:Class rdf:about=\"HappyPerson\"><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"hasChild\"/><owl:allValuesFrom rdf:resource=\"HappyPerson\"/></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"hasChild\"/><owl:someValuesFrom rdf:resource=\"HappyPerson\"/></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n"
            + "   <owl:Class rdf:about=\"JohnsChildren\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"hasParent\"/><owl:hasValue rdf:resource=\"John\"/></owl:Restriction></owl:equivalentClass></owl:Class>\n"
            + "   <owl:Class rdf:about=\"NarcisticPerson\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"loves\"/><owl:hasSelf rdf:datatype=\"&xsd;boolean\">true</owl:hasSelf></owl:Restriction></owl:equivalentClass></owl:Class>\n"
            + "   <owl:Class rdf:about=\"MyBirthdayGuests\"><owl:equivalentClass><owl:Class><owl:oneOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"Bill\"/><rdf:Description rdf:about=\"John\"/><rdf:Description rdf:about=\"Mary\"/></owl:oneOf></owl:Class></owl:equivalentClass></owl:Class>\n"
            + "   <owl:Class rdf:about=\"Orphan\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty><owl:inverseOf rdf:resource=\"hasChild\"/></owl:ObjectProperty></owl:onProperty><owl:allValuesFrom rdf:resource=\"Dead\"/></owl:Restriction></owl:equivalentClass></owl:Class>\n"
            + "   <owl:Class rdf:about=\"Teenager\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"hasAge\"/><owl:someValuesFrom><rdfs:Datatype><owl:onDatatype rdf:resource=\"&xsd;integer\"/><owl:withRestrictions rdf:parseType=\"Collection\"><rdf:Description><xsd:minExclusive rdf:datatype=\"&xsd;integer\">12</xsd:minExclusive></rdf:Description><rdf:Description><xsd:maxInclusive rdf:datatype=\"&xsd;integer\">19</xsd:maxInclusive></rdf:Description></owl:withRestrictions></rdfs:Datatype></owl:someValuesFrom></owl:Restriction></rdfs:subClassOf></owl:Class>\n"
            + "   <owl:Class rdf:about=\"Man\"><rdfs:subClassOf rdf:resource=\"Person\"/></owl:Class>\n"
            + "   <owl:Axiom><owl:annotatedSource rdf:resource=\"Man\"/><owl:annotatedProperty rdf:resource=\"&rdfs;subClassOf\"/><owl:annotatedTarget rdf:resource=\"Person\"/><rdfs:comment>States that every man is a person.</rdfs:comment></owl:Axiom>\n"
            + "   <owl:Class rdf:about=\"Adult\"><owl:equivalentClass rdf:resource=\"&otherOnt;Grownup\"/></owl:Class>\n"
            + "   <owl:Class rdf:about=\"Father\"><rdfs:subClassOf><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"Man\"/><owl:Class rdf:about=\"Parent\"/></owl:intersectionOf></owl:Class></rdfs:subClassOf></owl:Class>\n"
            + "   <owl:Class rdf:about=\"ChildlessPerson\"><rdfs:subClassOf><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"Person\"/><owl:Class><owl:complementOf><owl:Restriction><owl:onProperty><owl:ObjectProperty><owl:inverseOf rdf:resource=\"hasParent\"/></owl:ObjectProperty></owl:onProperty><owl:someValuesFrom rdf:resource=\"&owl;Thing\"/></owl:Restriction></owl:complementOf></owl:Class></owl:intersectionOf></owl:Class></rdfs:subClassOf></owl:Class>\n"
            + "   <owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class><owl:oneOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"Mary\"/><rdf:Description rdf:about=\"Bill\"/><rdf:Description rdf:about=\"Meg\"/></owl:oneOf></owl:Class><owl:Class rdf:about=\"Female\"/></owl:intersectionOf><rdfs:subClassOf><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"Parent\"/><owl:Restriction><owl:maxCardinality rdf:datatype=\"&xsd;nonNegativeInteger\">1</owl:maxCardinality><owl:onProperty rdf:resource=\"hasChild\"/></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"hasChild\"/><owl:allValuesFrom rdf:resource=\"Female\"/></owl:Restriction></owl:intersectionOf></owl:Class></rdfs:subClassOf></owl:Class>\n"
            + "   <owl:AllDisjointClasses><owl:members rdf:parseType=\"Collection\"><owl:Class rdf:about=\"Woman\"/><owl:Class rdf:about=\"Man\"/></owl:members></owl:AllDisjointClasses>\n"
            + "   <owl:AllDisjointClasses><owl:members rdf:parseType=\"Collection\"><owl:Class rdf:about=\"Mother\"/><owl:Class rdf:about=\"Father\"/><owl:Class rdf:about=\"YoungChild\"/></owl:members></owl:AllDisjointClasses>\n"
            + '\n'
            + "   <rdf:Description rdf:about=\"personAge\"><owl:equivalentClass><rdfs:Datatype><owl:onDatatype rdf:resource=\"&xsd;integer\"/><owl:withRestrictions rdf:parseType=\"Collection\"><rdf:Description><xsd:minInclusive rdf:datatype=\"&xsd;integer\">0</xsd:minInclusive></rdf:Description><rdf:Description><xsd:maxInclusive rdf:datatype=\"&xsd;integer\">150</xsd:maxInclusive></rdf:Description></owl:withRestrictions></rdfs:Datatype></owl:equivalentClass></rdf:Description>\n"
            + "   <rdf:Description rdf:about=\"minorAge\"><owl:equivalentClass><rdfs:Datatype><owl:onDatatype rdf:resource=\"&xsd;integer\"/><owl:withRestrictions rdf:parseType=\"Collection\"><rdf:Description><xsd:minInclusive rdf:datatype=\"&xsd;integer\">0</xsd:minInclusive></rdf:Description><rdf:Description><xsd:maxInclusive rdf:datatype=\"&xsd;integer\">18</xsd:maxInclusive></rdf:Description></owl:withRestrictions></rdfs:Datatype></owl:equivalentClass></rdf:Description>\n"
            + "   <rdf:Description rdf:about=\"majorAge\"><owl:equivalentClass><rdfs:Datatype><owl:intersectionOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"personAge\"/><rdfs:Datatype><owl:datatypeComplementOf rdf:resource=\"minorAge\"/></rdfs:Datatype></owl:intersectionOf></rdfs:Datatype></owl:equivalentClass></rdf:Description>\n"
            + "   <rdf:Description rdf:about=\"toddlerAge\"><owl:equivalentClass><rdfs:Datatype><owl:oneOf><rdf:Description><rdf:first rdf:datatype=\"&xsd;integer\">1</rdf:first><rdf:rest><rdf:Description><rdf:first rdf:datatype=\"&xsd;integer\">2</rdf:first><rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil\"/></rdf:Description></rdf:rest></rdf:Description></owl:oneOf></rdfs:Datatype></owl:equivalentClass></rdf:Description>\n"
            + '\n'
            + "   <Person rdf:about=\"Mary\"><rdf:type rdf:resource=\"Woman\"/><owl:sameAs rdf:resource=\"&otherOnt;MaryBrown\"/></Person>\n"
            + "   <owl:NamedIndividual rdf:about=\"James\"><owl:sameAs rdf:resource=\"Jim\"/></owl:NamedIndividual>\n"
            + "   <rdf:Description rdf:about=\"Jack\"><rdf:type><owl:Class><owl:intersectionOf  rdf:parseType=\"Collection\"><owl:Class rdf:about=\"Person\"/><owl:Class><owl:complementOf rdf:resource=\"Parent\"/></owl:Class></owl:intersectionOf></owl:Class></rdf:type></rdf:Description>\n"
            + "   <owl:NamedIndividual rdf:about=\"John\"><hasWife rdf:resource=\"Mary\"/><hasAge rdf:datatype=\"&xsd;integer\">51</hasAge><owl:differentFrom rdf:resource=\"Bill\"/><owl:sameAs rdf:resource=\"&otherOnt;JohnBrown\"/><rdf:type rdf:resource=\"Father\"/><rdf:type><owl:Restriction><owl:maxQualifiedCardinality rdf:datatype=\"&xsd;nonNegativeInteger\">4</owl:maxQualifiedCardinality><owl:onProperty rdf:resource=\"hasChild\"/><owl:onClass rdf:resource=\"Parent\"/></owl:Restriction></rdf:type><rdf:type><owl:Restriction><owl:minQualifiedCardinality rdf:datatype=\"&xsd;nonNegativeInteger\">2</owl:minQualifiedCardinality><owl:onProperty rdf:resource=\"hasChild\"/><owl:onClass rdf:resource=\"Parent\"/></owl:Restriction></rdf:type><rdf:type><owl:Restriction><owl:qualifiedCardinality rdf:datatype=\"&xsd;nonNegativeInteger\">3</owl:qualifiedCardinality><owl:onProperty rdf:resource=\"hasChild\"/><owl:onClass rdf:resource=\"Parent\"/></owl:Restriction></rdf:type><rdf:type><owl:Restriction><owl:cardinality rdf:datatype=\"&xsd;nonNegativeInteger\">5</owl:cardinality><owl:onProperty rdf:resource=\"hasChild\"/></owl:Restriction></rdf:type></owl:NamedIndividual>\n"
            + '\n' + "   <SocialRole rdf:about=\"Father\"/>\n" + '\n'
            + "   <owl:NegativePropertyAssertion><owl:sourceIndividual rdf:resource=\"Bill\"/><owl:assertionProperty rdf:resource=\"hasWife\"/><owl:targetIndividual rdf:resource=\"Mary\"/></owl:NegativePropertyAssertion>\n"
            + "   <owl:NegativePropertyAssertion><owl:sourceIndividual rdf:resource=\"Jack\"/><owl:assertionProperty rdf:resource=\"hasAge\"/><owl:targetValue rdf:datatype=\"&xsd;integer\">53</owl:targetValue></owl:NegativePropertyAssertion>\n"
            + "   <owl:NegativePropertyAssertion><owl:sourceIndividual rdf:resource=\"Bill\"/><owl:assertionProperty rdf:resource=\"hasDaughter\"/><owl:targetIndividual rdf:resource=\"Susan\"/></owl:NegativePropertyAssertion>\n"
            + " </rdf:RDF>";
    private static final @Nonnull String OWLXML = "<!DOCTYPE Ontology [\n"
            + "        <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n"
            + "        <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" >\n" + "        ]>\n" + '\n'
            + "<Ontology xml:base=\"http://example.com/owl/families/\" ontologyIRI=\"http://example.com/owl/families\" xmlns=\"http://www.w3.org/2002/07/owl#\">\n"
            + "    <Prefix name=\"owl\" IRI=\"http://www.w3.org/2002/07/owl#\"/>\n"
            + "    <Prefix name=\"otherOnt\" IRI=\"http://example.org/otherOntologies/families/\"/>\n" + '\n'
            + "    <Declaration><NamedIndividual IRI=\"John\"/></Declaration>\n"
            + "    <Declaration><NamedIndividual IRI=\"Mary\"/></Declaration>\n"
            + "    <Declaration><NamedIndividual IRI=\"Jim\"/></Declaration>\n"
            + "    <Declaration><NamedIndividual IRI=\"James\"/></Declaration>\n"
            + "    <Declaration><NamedIndividual IRI=\"Jack\"/></Declaration>\n"
            + "    <Declaration><NamedIndividual IRI=\"Bill\"/></Declaration>\n"
            + "    <Declaration><NamedIndividual IRI=\"Susan\"/></Declaration>\n"
            + "    <Declaration><NamedIndividual IRI=\"Meg\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"Person\"/></Declaration>\n"
            + "    <AnnotationAssertion><AnnotationProperty IRI=\"&rdfs;comment\"/><IRI>Person</IRI><Literal>Represents the set of all people.</Literal></AnnotationAssertion>\n"
            + "    <Declaration><Class IRI=\"Woman\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"Parent\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"Father\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"Mother\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"SocialRole\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"Man\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"Teenager\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"ChildlessPerson\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"Human\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"Female\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"HappyPerson\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"JohnsChildren\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"NarcisticPerson\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"MyBirthdayGuests\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"Dead\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"Orphan\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"Adult\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"YoungChild\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"Grandfather\"/></Declaration>\n"
            + "    <Declaration><Class IRI=\"http://example.org/otherOntologies/families/Grownup\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasWife\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasChild\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasDaughter\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"loves\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasSpouse\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasGrandparent\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasParent\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasBrother\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasUncle\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasSon\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasAncestor\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasHusband\"/></Declaration>\n"
            + "    <Declaration><DataProperty IRI=\"hasAge\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"parentOf\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasFather\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"hasRelative\"/></Declaration>\n"
            + "    <Declaration><ObjectProperty IRI=\"http://example.org/otherOntologies/families/child\"/></Declaration>\n"
            + "    <Declaration><DataProperty IRI=\"http://example.org/otherOntologies/families/age\"/></Declaration>\n"
            + "    <Declaration><DataProperty IRI=\"hasSSN\"/></Declaration>\n"
            + "    <Declaration><Datatype IRI=\"personAge\"/></Declaration>\n"
            + "    <Declaration><Datatype IRI=\"minorAge\"/></Declaration>\n"
            + "    <Declaration><Datatype IRI=\"majorAge\"/></Declaration>\n"
            + "    <Declaration><Datatype IRI=\"toddlerAge\"/></Declaration>\n" + '\n'
            + "    <DatatypeDefinition><Datatype IRI=\"personAge\"/><DatatypeRestriction><Datatype IRI=\"&xsd;integer\"/><FacetRestriction facet=\"&xsd;minInclusive\"><Literal datatypeIRI=\"&xsd;integer\">0</Literal></FacetRestriction><FacetRestriction facet=\"&xsd;maxInclusive\"><Literal datatypeIRI=\"&xsd;integer\">150</Literal></FacetRestriction></DatatypeRestriction></DatatypeDefinition>\n"
            + "    <DatatypeDefinition><Datatype IRI=\"minorAge\"/><DatatypeRestriction><Datatype IRI=\"&xsd;integer\"/><FacetRestriction facet=\"&xsd;minInclusive\"><Literal datatypeIRI=\"&xsd;integer\">0</Literal></FacetRestriction><FacetRestriction facet=\"&xsd;maxInclusive\"><Literal datatypeIRI=\"&xsd;integer\">18</Literal></FacetRestriction></DatatypeRestriction></DatatypeDefinition>\n"
            + "    <DatatypeDefinition><Datatype IRI=\"majorAge\"/><DataIntersectionOf><Datatype IRI=\"personAge\"/><DataComplementOf><Datatype IRI=\"minorAge\"/></DataComplementOf></DataIntersectionOf></DatatypeDefinition>\n"
            + "    <DatatypeDefinition><Datatype IRI=\"toddlerAge\"/><DataOneOf><Literal datatypeIRI=\"&xsd;integer\">1</Literal><Literal datatypeIRI=\"&xsd;integer\">2</Literal></DataOneOf></DatatypeDefinition>\n"
            + '\n' + "    <SymmetricObjectProperty><ObjectProperty IRI=\"hasSpouse\"/></SymmetricObjectProperty>\n"
            + "    <AsymmetricObjectProperty><ObjectProperty IRI=\"hasChild\"/></AsymmetricObjectProperty>\n"
            + "    <DisjointObjectProperties><ObjectProperty IRI=\"hasParent\"/><ObjectProperty IRI=\"hasSpouse\"/></DisjointObjectProperties>\n"
            + "    <ReflexiveObjectProperty><ObjectProperty IRI=\"hasRelative\"/></ReflexiveObjectProperty>\n"
            + "    <IrreflexiveObjectProperty><ObjectProperty IRI=\"parentOf\"/></IrreflexiveObjectProperty>\n"
            + "    <FunctionalObjectProperty><ObjectProperty IRI=\"hasHusband\"/></FunctionalObjectProperty>\n"
            + "    <InverseFunctionalObjectProperty><ObjectProperty IRI=\"hasHusband\"/></InverseFunctionalObjectProperty>\n"
            + "    <TransitiveObjectProperty><ObjectProperty IRI=\"hasAncestor\"/></TransitiveObjectProperty>\n" + '\n'
            + "    <ObjectPropertyDomain><ObjectProperty IRI=\"hasWife\"/><Class IRI=\"Man\"/></ObjectPropertyDomain>\n"
            + "    <ObjectPropertyRange><ObjectProperty IRI=\"hasWife\"/><Class IRI=\"Woman\"/></ObjectPropertyRange>\n"
            + '\n'
            + "    <InverseObjectProperties><ObjectProperty IRI=\"hasParent\"/><ObjectProperty IRI=\"hasChild\"/></InverseObjectProperties>\n"
            + '\n'
            + "    <DisjointObjectProperties><ObjectProperty IRI=\"hasSon\"/><ObjectProperty IRI=\"hasDaughter\"/></DisjointObjectProperties>\n"
            + '\n'
            + "    <EquivalentObjectProperties><ObjectProperty IRI=\"hasChild\"/><ObjectProperty abbreviatedIRI=\"otherOnt:child\"/></EquivalentObjectProperties>\n"
            + '\n'
            + "    <SubObjectPropertyOf><ObjectProperty IRI=\"hasWife\"/><ObjectProperty IRI=\"hasSpouse\"/></SubObjectPropertyOf>\n"
            + "    <SubObjectPropertyOf><ObjectProperty IRI=\"hasFather\"/><ObjectProperty IRI=\"hasParent\"/></SubObjectPropertyOf>\n"
            + "    <SubObjectPropertyOf><ObjectPropertyChain><ObjectProperty IRI=\"hasParent\"/><ObjectProperty IRI=\"hasParent\"/></ObjectPropertyChain><ObjectProperty IRI=\"hasGrandparent\"/></SubObjectPropertyOf>\n"
            + "    <SubObjectPropertyOf><ObjectPropertyChain><ObjectProperty IRI=\"hasFather\"/><ObjectProperty IRI=\"hasBrother\"/></ObjectPropertyChain><ObjectProperty IRI=\"hasUncle\"/></SubObjectPropertyOf>\n"
            + "    <SubObjectPropertyOf><ObjectPropertyChain><ObjectProperty IRI=\"hasFather\"/><ObjectProperty IRI=\"hasBrother\"/></ObjectPropertyChain><ObjectProperty IRI=\"hasUncle\"/></SubObjectPropertyOf>\n"
            + '\n' + "    <HasKey><Class IRI=\"Person\"/><DataProperty IRI=\"hasSSN\"/></HasKey>\n" + '\n'
            + "    <DataPropertyDomain><DataProperty IRI=\"hasAge\"/><Class IRI=\"Person\"/></DataPropertyDomain>\n"
            + "    <DataPropertyRange><DataProperty IRI=\"hasAge\"/><Datatype IRI=\"&xsd;nonNegativeInteger\"/></DataPropertyRange>\n"
            + "    <FunctionalDataProperty><DataProperty IRI=\"hasAge\"/></FunctionalDataProperty>\n"
            + "    <EquivalentDataProperties><DataProperty IRI=\"hasAge\"/><DataProperty abbreviatedIRI=\"otherOnt:age\"/></EquivalentDataProperties>\n"
            + '\n' + "    <SubClassOf><Class IRI=\"Woman\"/><Class IRI=\"Person\"/></SubClassOf>\n"
            + "    <SubClassOf><Class IRI=\"Mother\"/><Class IRI=\"Woman\"/></SubClassOf>\n"
            + "    <SubClassOf><Class IRI=\"Grandfather\"/><ObjectIntersectionOf><Class IRI=\"Man\"/><Class IRI=\"Parent\"/></ObjectIntersectionOf></SubClassOf>\n"
            + "    <SubClassOf><Class IRI=\"Father\"/><ObjectIntersectionOf><Class IRI=\"Man\"/><Class IRI=\"Parent\"/></ObjectIntersectionOf></SubClassOf>\n"
            + "    <SubClassOf><Class IRI=\"ChildlessPerson\"/><ObjectIntersectionOf><Class IRI=\"Person\"/><ObjectComplementOf><ObjectSomeValuesFrom><ObjectInverseOf><ObjectProperty IRI=\"hasParent\"/></ObjectInverseOf><Class abbreviatedIRI=\"owl:Thing\"/></ObjectSomeValuesFrom></ObjectComplementOf></ObjectIntersectionOf></SubClassOf>\n"
            + "    <SubClassOf><ObjectIntersectionOf><ObjectOneOf><NamedIndividual IRI=\"Mary\"/><NamedIndividual IRI=\"Bill\"/><NamedIndividual IRI=\"Meg\"/></ObjectOneOf><Class IRI=\"Female\"/></ObjectIntersectionOf><ObjectIntersectionOf><Class IRI=\"Parent\"/><ObjectMaxCardinality cardinality=\"1\"><ObjectProperty IRI=\"hasChild\"/></ObjectMaxCardinality><ObjectAllValuesFrom><ObjectProperty IRI=\"hasChild\"/><Class IRI=\"Female\"/></ObjectAllValuesFrom></ObjectIntersectionOf></SubClassOf>\n"
            + "    <SubClassOf><Class IRI=\"Teenager\"/><DataSomeValuesFrom><DataProperty IRI=\"hasAge\"/><DatatypeRestriction><Datatype IRI=\"&xsd;integer\"/><FacetRestriction facet=\"&xsd;minExclusive\"><Literal datatypeIRI=\"&xsd;integer\">12</Literal></FacetRestriction><FacetRestriction facet=\"&xsd;maxInclusive\"><Literal datatypeIRI=\"&xsd;integer\">19</Literal></FacetRestriction></DatatypeRestriction></DataSomeValuesFrom></SubClassOf>\n"
            + "    <SubClassOf><Annotation><AnnotationProperty IRI=\"&rdfs;comment\"/><Literal>States that every man is a person.</Literal></Annotation><Class IRI=\"Man\"/><Class IRI=\"Person\"/></SubClassOf>\n"
            + '\n'
            + "    <EquivalentClasses><Class IRI=\"HappyPerson\"/><ObjectIntersectionOf><ObjectAllValuesFrom><ObjectProperty IRI=\"hasChild\"/><Class IRI=\"HappyPerson\"/></ObjectAllValuesFrom><ObjectSomeValuesFrom><ObjectProperty IRI=\"hasChild\"/><Class IRI=\"HappyPerson\"/></ObjectSomeValuesFrom></ObjectIntersectionOf></EquivalentClasses>\n"
            + "    <EquivalentClasses><Class IRI=\"JohnsChildren\"/><ObjectHasValue><ObjectProperty IRI=\"hasParent\"/><NamedIndividual IRI=\"John\"/></ObjectHasValue></EquivalentClasses>\n"
            + "    <EquivalentClasses><Class IRI=\"NarcisticPerson\"/><ObjectHasSelf><ObjectProperty IRI=\"loves\"/></ObjectHasSelf></EquivalentClasses>\n"
            + "    <EquivalentClasses><Class IRI=\"Orphan\"/><ObjectAllValuesFrom><ObjectInverseOf><ObjectProperty IRI=\"hasChild\"/></ObjectInverseOf><Class IRI=\"Dead\"/></ObjectAllValuesFrom></EquivalentClasses>\n"
            + "    <EquivalentClasses><Class IRI=\"MyBirthdayGuests\"/><ObjectOneOf><NamedIndividual IRI=\"Bill\"/><NamedIndividual IRI=\"John\"/><NamedIndividual IRI=\"Mary\"/></ObjectOneOf></EquivalentClasses>\n"
            + "    <EquivalentClasses><Class IRI=\"Adult\"/><Class abbreviatedIRI=\"otherOnt:Grownup\"/></EquivalentClasses>\n"
            + "    <EquivalentClasses><Class IRI=\"Parent\"/><ObjectSomeValuesFrom><ObjectProperty IRI=\"hasChild\"/><Class IRI=\"Person\"/></ObjectSomeValuesFrom></EquivalentClasses>\n"
            + "    <EquivalentClasses><Class IRI=\"Parent\"/><ObjectSomeValuesFrom><ObjectProperty IRI=\"hasChild\"/><Class IRI=\"Person\"/></ObjectSomeValuesFrom></EquivalentClasses>\n"
            + "    <EquivalentClasses><Class IRI=\"Person\"/><Class IRI=\"Human\"/></EquivalentClasses>\n"
            + "    <EquivalentClasses><Class IRI=\"Mother\"/><ObjectIntersectionOf><Class IRI=\"Woman\"/><Class IRI=\"Parent\"/></ObjectIntersectionOf></EquivalentClasses>\n"
            + "    <EquivalentClasses><Class IRI=\"Parent\"/><ObjectUnionOf><Class IRI=\"Mother\"/><Class IRI=\"Father\"/></ObjectUnionOf></EquivalentClasses>\n"
            + "    <EquivalentClasses><Class IRI=\"ChildlessPerson\"/><ObjectIntersectionOf><Class IRI=\"Person\"/><ObjectComplementOf><Class IRI=\"Parent\"/></ObjectComplementOf></ObjectIntersectionOf></EquivalentClasses>\n"
            + "    <DisjointClasses><Class IRI=\"Woman\"/><Class IRI=\"Man\"/></DisjointClasses>\n"
            + "    <DisjointClasses><Class IRI=\"Father\"/><Class IRI=\"Mother\"/><Class IRI=\"YoungChild\"/></DisjointClasses>\n"
            + '\n'
            + "    <DifferentIndividuals><NamedIndividual IRI=\"John\"/><NamedIndividual IRI=\"Bill\"/></DifferentIndividuals>\n"
            + "    <SameIndividual><NamedIndividual IRI=\"James\"/><NamedIndividual IRI=\"Jim\"/></SameIndividual>\n"
            + "    <SameIndividual><NamedIndividual IRI=\"John\"/><NamedIndividual abbreviatedIRI=\"otherOnt:JohnBrown\"/></SameIndividual>\n"
            + "    <SameIndividual><NamedIndividual IRI=\"Mary\"/><NamedIndividual abbreviatedIRI=\"otherOnt:MaryBrown\"/></SameIndividual>\n"
            + "    <ObjectPropertyAssertion><ObjectProperty IRI=\"hasWife\"/><NamedIndividual IRI=\"John\"/><NamedIndividual IRI=\"Mary\"/></ObjectPropertyAssertion>\n"
            + "    <DataPropertyAssertion><DataProperty IRI=\"hasAge\"/><NamedIndividual IRI=\"John\"/><Literal datatypeIRI=\"&xsd;integer\">51</Literal></DataPropertyAssertion>\n"
            + "    <ClassAssertion><Class IRI=\"Person\"/><NamedIndividual IRI=\"Mary\"/></ClassAssertion>\n"
            + "    <ClassAssertion><Class IRI=\"Woman\"/><NamedIndividual IRI=\"Mary\"/></ClassAssertion>\n"
            + "    <ClassAssertion><ObjectIntersectionOf><Class IRI=\"Person\"/><ObjectComplementOf><Class IRI=\"Parent\"/></ObjectComplementOf></ObjectIntersectionOf><NamedIndividual IRI=\"Jack\"/></ClassAssertion>\n"
            + "    <ClassAssertion><ObjectMaxCardinality cardinality=\"4\"><ObjectProperty IRI=\"hasChild\"/><Class IRI=\"Parent\"/></ObjectMaxCardinality><NamedIndividual IRI=\"John\"/></ClassAssertion>\n"
            + "    <ClassAssertion><ObjectMinCardinality cardinality=\"2\"><ObjectProperty IRI=\"hasChild\"/><Class IRI=\"Parent\"/></ObjectMinCardinality><NamedIndividual IRI=\"John\"/></ClassAssertion>\n"
            + "    <ClassAssertion><ObjectExactCardinality cardinality=\"3\"><ObjectProperty IRI=\"hasChild\"/><Class IRI=\"Parent\"/></ObjectExactCardinality><NamedIndividual IRI=\"John\"/></ClassAssertion>\n"
            + "    <ClassAssertion><ObjectExactCardinality cardinality=\"5\"><ObjectProperty IRI=\"hasChild\"/></ObjectExactCardinality><NamedIndividual IRI=\"John\"/></ClassAssertion>\n"
            + "    <ClassAssertion><Class IRI=\"Father\"/><NamedIndividual IRI=\"John\"/></ClassAssertion>\n"
            + "    <ClassAssertion><Class IRI=\"SocialRole\"/><NamedIndividual IRI=\"Father\"/></ClassAssertion>\n"
            + "    <NegativeObjectPropertyAssertion><ObjectProperty IRI=\"hasWife\"/><NamedIndividual IRI=\"Bill\"/><NamedIndividual IRI=\"Mary\"/></NegativeObjectPropertyAssertion>\n"
            + "    <NegativeDataPropertyAssertion><DataProperty IRI=\"hasAge\"/><NamedIndividual IRI=\"Jack\"/><Literal datatypeIRI=\"&xsd;integer\">53</Literal></NegativeDataPropertyAssertion>\n"
            + "    <NegativeObjectPropertyAssertion><ObjectProperty IRI=\"hasDaughter\"/><NamedIndividual IRI=\"Bill\"/><NamedIndividual IRI=\"Susan\"/></NegativeObjectPropertyAssertion>\n"
            + '\n' + "</Ontology>";
    private static final @Nonnull String FUNCTIONAL = " Prefix(:=<http://example.com/owl/families/>)\n"
            + " Prefix(otherOnt:=<http://example.org/otherOntologies/families/>)\n"
            + " Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n" + " Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
            + " Ontology(<http://example.com/owl/families>\n" + '\n' + "   Declaration( NamedIndividual( :John ) )\n"
            + "   Declaration( NamedIndividual( :Mary ) )\n" + "   Declaration( NamedIndividual( :Jim ) )\n"
            + "   Declaration( NamedIndividual( :James ) )\n" + "   Declaration( NamedIndividual( :Jack ) )\n"
            + "   Declaration( NamedIndividual( :Bill ) )\n" + "   Declaration( NamedIndividual( :Susan ) )\n"
            + "   Declaration( Class( :Person ) )\n"
            + "   AnnotationAssertion( rdfs:comment :Person \"Represents the set of all people.\" )\n"
            + "   Declaration( Class( :Woman ) )\n" + "   Declaration( Class( :Parent ) )\n"
            + "   Declaration( Class( :Father ) )\n" + "   Declaration( Class( :Mother ) )\n"
            + "   Declaration( Class( :SocialRole ) )\n" + "   Declaration( Class( :Man ) )\n"
            + "   Declaration( Class( :Teenager ) )\n" + "   Declaration( Class( :ChildlessPerson ) )\n"
            + "   Declaration( Class( :Human ) )\n" + "   Declaration( Class( :Female ) )\n"
            + "   Declaration( Class( :HappyPerson ) )\n" + "   Declaration( Class( :JohnsChildren ) )\n"
            + "   Declaration( Class( :NarcisticPerson ) )\n" + "   Declaration( Class( :MyBirthdayGuests ) )\n"
            + "   Declaration( Class( :Dead ) )\n" + "   Declaration( Class( :Orphan ) )\n"
            + "   Declaration( Class( :Adult ) )\n" + "   Declaration( Class( :YoungChild ) )\n"
            + "   Declaration( ObjectProperty( :hasWife ) )\n" + "   Declaration( ObjectProperty( :hasChild ) )\n"
            + "   Declaration( ObjectProperty( :hasDaughter ) )\n" + "   Declaration( ObjectProperty( :loves ) )\n"
            + "   Declaration( ObjectProperty( :hasSpouse ) )\n"
            + "   Declaration( ObjectProperty( :hasGrandparent ) )\n"
            + "   Declaration( ObjectProperty( :hasParent ) )\n" + "   Declaration( ObjectProperty( :hasBrother ) )\n"
            + "   Declaration( ObjectProperty( :hasUncle ) )\n" + "   Declaration( ObjectProperty( :hasSon ) )\n"
            + "   Declaration( ObjectProperty( :hasAncestor ) )\n" + "   Declaration( ObjectProperty( :hasHusband ) )\n"
            + "   Declaration( ObjectProperty( :hasRelative ) )\n"
            + "   Declaration( ObjectProperty( otherOnt:child ) )\n"
            + "   Declaration( ObjectProperty( :hasFather ) )\n" + "   Declaration( ObjectProperty( :hasSon ) )\n"
            + "   Declaration( ObjectProperty( :parentOf ) )\n" + "   Declaration( DataProperty( otherOnt:age ) )\n"
            + "   Declaration( Class( otherOnt:Grownup ) )\n" + "   Declaration( Class( :Father ) )\n"
            + "   Declaration( Class( :Grandfather ) )\n" + "   Declaration( DataProperty( :hasSSN ) )\n"
            + "   Declaration( DataProperty( :hasAge ) )\n" + "   Declaration( Datatype( :personAge ) )\n"
            + "   Declaration( Datatype( :minorAge ) )\n" + "   Declaration( Datatype( :majorAge ) )\n"
            + "   Declaration( Datatype( :toddlerAge ) )\n" + '\n' + "   SubObjectPropertyOf( :hasWife :hasSpouse )\n"
            + "   SubObjectPropertyOf( ObjectPropertyChain( :hasParent :hasParent ) :hasGrandparent )\n"
            + "   SubObjectPropertyOf( ObjectPropertyChain( :hasFather :hasBrother ) :hasUncle )\n"
            + "   SubObjectPropertyOf( :hasFather :hasParent )\n" + '\n'
            + "   EquivalentObjectProperties( :hasChild otherOnt:child )\n"
            + "   InverseObjectProperties( :hasParent :hasChild )\n"
            + "   EquivalentDataProperties( :hasAge otherOnt:age )\n"
            + "   DisjointObjectProperties( :hasSon :hasDaughter )\n" + "   ObjectPropertyDomain( :hasWife :Man )\n"
            + "   ObjectPropertyRange( :hasWife :Woman )\n" + "   DataPropertyDomain( :hasAge :Person )\n"
            + "   DataPropertyRange( :hasAge xsd:nonNegativeInteger )\n" + '\n'
            + "   SymmetricObjectProperty( :hasSpouse )\n" + "   AsymmetricObjectProperty( :hasChild )\n"
            + "   DisjointObjectProperties( :hasParent :hasSpouse )\n" + "   ReflexiveObjectProperty( :hasRelative )\n"
            + "   IrreflexiveObjectProperty( :parentOf )\n" + "   FunctionalObjectProperty( :hasHusband )\n"
            + "   InverseFunctionalObjectProperty( :hasHusband )\n" + "   TransitiveObjectProperty( :hasAncestor )\n"
            + "   FunctionalDataProperty( :hasAge )\n" + '\n' + "   SubClassOf( :Woman :Person )\n"
            + "   SubClassOf( :Mother :Woman )\n"
            + "   SubClassOf( :Grandfather ObjectIntersectionOf( :Man :Parent ) )\n"
            + "   SubClassOf( :Teenager DataSomeValuesFrom( :hasAge DatatypeRestriction( xsd:integer xsd:minExclusive \"12\"^^xsd:integer xsd:maxInclusive \"19\"^^xsd:integer ) ) )\n"
            + "   SubClassOf( Annotation( rdfs:comment \"States that every man is a person.\" ) :Man :Person )\n"
            + "   SubClassOf( :Father ObjectIntersectionOf( :Man :Parent ) )\n"
            + "   SubClassOf( :ChildlessPerson ObjectIntersectionOf( :Person ObjectComplementOf( ObjectSomeValuesFrom( ObjectInverseOf( :hasParent ) owl:Thing ) ) ) )\n"
            + "   SubClassOf( ObjectIntersectionOf( ObjectOneOf( :Mary :Bill :Meg ) :Female ) ObjectIntersectionOf( :Parent ObjectMaxCardinality( 1 :hasChild ) ObjectAllValuesFrom( :hasChild :Female ) ) )\n"
            + '\n' + "   EquivalentClasses( :Person :Human )\n"
            + "   EquivalentClasses( :Mother ObjectIntersectionOf( :Woman :Parent ) )\n"
            + "   EquivalentClasses( :Parent ObjectUnionOf( :Mother :Father ) )\n"
            + "   EquivalentClasses( :ChildlessPerson ObjectIntersectionOf( :Person ObjectComplementOf( :Parent ) ) )\n"
            + "   EquivalentClasses( :Parent ObjectSomeValuesFrom( :hasChild :Person ) )\n"
            + "   EquivalentClasses( :HappyPerson ObjectIntersectionOf( ObjectAllValuesFrom( :hasChild :HappyPerson ) ObjectSomeValuesFrom( :hasChild :HappyPerson ) ) )\n"
            + "   EquivalentClasses( :JohnsChildren ObjectHasValue( :hasParent :John ) )\n"
            + "   EquivalentClasses( :NarcisticPerson ObjectHasSelf( :loves ) )\n"
            + "   EquivalentClasses( :MyBirthdayGuests ObjectOneOf( :Bill :John :Mary) )\n"
            + "   EquivalentClasses( :Orphan ObjectAllValuesFrom( ObjectInverseOf( :hasChild ) :Dead ) )\n"
            + "   EquivalentClasses( :Adult otherOnt:Grownup )\n"
            + "   EquivalentClasses( :Parent ObjectSomeValuesFrom( :hasChild :Person ) )\n" + '\n'
            + "   DisjointClasses( :Woman :Man )\n" + "   DisjointClasses( :Mother :Father :YoungChild )\n"
            + "   HasKey( :Person () ( :hasSSN ) )\n" + '\n'
            + "   DatatypeDefinition( :personAge DatatypeRestriction( xsd:integer xsd:minInclusive \"0\"^^xsd:integer xsd:maxInclusive \"150\"^^xsd:integer ) )\n"
            + "   DatatypeDefinition( :minorAge DatatypeRestriction( xsd:integer xsd:minInclusive \"0\"^^xsd:integer xsd:maxInclusive \"18\"^^xsd:integer ) )\n"
            + "   DatatypeDefinition( :majorAge DataIntersectionOf( :personAge DataComplementOf( :minorAge ) ) )\n"
            + "   DatatypeDefinition( :toddlerAge DataOneOf( \"1\"^^xsd:integer \"2\"^^xsd:integer ) )\n" + '\n'
            + "   ClassAssertion( :Person :Mary )\n" + "   ClassAssertion( :Woman :Mary )\n"
            + "   ClassAssertion( ObjectIntersectionOf( :Person ObjectComplementOf( :Parent ) ) :Jack )\n"
            + "   ClassAssertion( ObjectMaxCardinality( 4 :hasChild :Parent ) :John )\n"
            + "   ClassAssertion( ObjectMinCardinality( 2 :hasChild :Parent ) :John )\n"
            + "   ClassAssertion( ObjectExactCardinality( 3 :hasChild :Parent ) :John )\n"
            + "   ClassAssertion( ObjectExactCardinality( 5 :hasChild ) :John )\n"
            + "   ClassAssertion( :Father :John )\n" + "   ClassAssertion( :SocialRole :Father )\n" + '\n'
            + "   ObjectPropertyAssertion( :hasWife :John :Mary )\n"
            + "   NegativeObjectPropertyAssertion( :hasWife :Bill :Mary )\n"
            + "   NegativeObjectPropertyAssertion( :hasDaughter :Bill :Susan )\n"
            + "   DataPropertyAssertion( :hasAge :John \"51\"^^xsd:integer )\n"
            + "   NegativeDataPropertyAssertion( :hasAge :Jack \"53\"^^xsd:integer )\n" + '\n'
            + "   SameIndividual( :James :Jim )\n" + "   SameIndividual( :John otherOnt:JohnBrown )\n"
            + "   SameIndividual( :Mary otherOnt:MaryBrown )\n" + "   DifferentIndividuals( :John :Bill )\n" + " )";
    private static final @Nonnull String MANCHESTER = "Prefix: : <http://example.com/owl/families/>\n"
            + "Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n" + "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"
            + "Prefix: otherOnt: <http://example.org/otherOntologies/families/>\n"
            + "Ontology: <http://example.com/owl/families>\n"
            + "#Import: <http://example.org/otherOntologies/families.owl>\n" + '\n' + "ObjectProperty: otherOnt:child\n"
            + "DataProperty: otherOnt:age\n" + "ObjectProperty: hasWife\n" + "   SubPropertyOf: hasSpouse\n"
            + "   Domain:        Man\n" + "   Range:         Woman\n" + "ObjectProperty: hasParent\n"
            + "   InverseOf: hasChild\n" + "ObjectProperty: hasSpouse\n" + "   Characteristics: Symmetric\n"
            + "ObjectProperty: hasChild\n" + "   Characteristics: Asymmetric\n" + "ObjectProperty: hasRelative\n"
            + "   Characteristics: Reflexive\n" + "ObjectProperty: parentOf\n" + "   Characteristics: Irreflexive\n"
            + "ObjectProperty: hasHusband\n" + "   Characteristics: Functional\n"
            + "   Characteristics: InverseFunctional\n" + "ObjectProperty: hasAncestor\n"
            + "   Characteristics: Transitive\n" + "ObjectProperty: hasGrandparent\n"
            + "   SubPropertyChain: hasParent o hasParent\n" + "ObjectProperty: hasUncle\n"
            + "   SubPropertyChain: hasFather o hasBrother\n" + "ObjectProperty: hasFather\n"
            + "   SubPropertyOf: hasParent\n" + "ObjectProperty: hasBrother\n" + "ObjectProperty: hasDaughter\n"
            + "ObjectProperty: hasSon\n" + "ObjectProperty: loves\n" + '\n'
            + "DisjointProperties: hasParent, hasSpouse\n" + "DisjointProperties: hasSon,    hasDaughter\n"
            + "EquivalentProperties: hasChild, otherOnt:child\n" + "EquivalentProperties: hasAge,   otherOnt:age\n"
            + '\n' + "DataProperty: hasAge\n" + "   Domain: Person\n" + "   Range:  xsd:nonNegativeInteger\n"
            + "   Characteristics: Functional\n" + "DataProperty: hasSSN\n" + '\n' + "Datatype: personAge\n"
            + "   EquivalentTo: xsd:integer[>= 0 , <= 150]\n" + "Datatype: minorAge\n"
            + "   EquivalentTo: xsd:integer[>= 0 , <= 18]\n" + "Datatype: majorAge\n"
            + "   EquivalentTo: personAge and not minorAge\n" + "Datatype: toddlerAge\n" + "   EquivalentTo: { 1, 2 }\n"
            + "Datatype: minorAge\n" + '\n' + "Class: Woman\n" + "   SubClassOf: Person\n" + "Class: Mother\n"
            + "   SubClassOf:   Woman\n" + "   EquivalentTo: Woman and Parent\n" + "Class: Person\n"
            + "   Annotations:  rdfs:comment \"Represents the set of all people.\"\n" + "   EquivalentTo: Human\n"
            + "   HasKey: hasSSN\n" + "Class: Parent\n" + "   EquivalentTo: hasChild some Person\n"
            + "   EquivalentTo: Mother or Father\n" + "Class: ChildlessPerson\n"
            + "   EquivalentTo: Person and not Parent\n"
            + "   SubClassOf:   Person and not ( inverse hasParent  some owl:Thing)\n" + "Class: Grandfather\n"
            + "   SubClassOf: Man and Parent\n" + "Class: HappyPerson\n"
            + "   EquivalentTo: hasChild only HappyPerson and hasChild some HappyPerson\n" + "Class: JohnsChildren\n"
            + "   EquivalentTo: hasParent value John\n" + "Class: NarcisticPerson\n" + "   EquivalentTo: loves Self\n"
            + "Class: Orphan\n" + "   EquivalentTo: inverse hasChild only Dead\n" + "Class: Teenager\n"
            + "   SubClassOf: hasAge some xsd:integer[<= 19 , > 12]\n" + "Class: Man\n"
            + "   SubClassOf: Annotations: rdfs:comment \"States that every man is a person.\" Person\n"
            + "Class: MyBirthdayGuests\n" + "   EquivalentTo: { Bill, John, Mary }\n" + "Class: Father\n"
            + "   SubClassOf: Man and Parent\n"
            // this class X is a trick to represent a GCI
            + "Class: X\n" + "   SubClassOf:   Parent and hasChild max 1 and hasChild only Female\n"
            + "   EquivalentTo: {Mary, Bill, Meg} and Female\n" + "Class: Dead\n" + "Class: Father\n"
            + "Class: Female\n" + "Class: Happy\n" + "Class: Human\n" + "Class: SocialRole\n" + "Class: YoungChild\n"
            + "Class: Adult\n" + "DisjointClasses: Mother, Father, YoungChild\n" + "DisjointClasses: Woman, Man\n"
            + "Class: otherOnt:Grownup\n" + "EquivalentClasses: Adult, otherOnt:Grownup\n" + '\n' + "Individual: Mary\n"
            + "   Types: Person\n" + "   Types: Woman\n" + "Individual: Jack\n" + "   Types: Person and not Parent\n"
            + "Individual: John\n" + "   Types: Father\n" + "   Types: hasChild max 4 Parent\n"
            + "   Types: hasChild min 2 Parent\n" + "   Types: hasChild exactly 3 Parent\n"
            + "   Types: hasChild exactly 5\n" + "   Facts: hasAge \"51\"^^xsd:integer\n" + "   Facts: hasWife Mary\n"
            + "   DifferentFrom: Bill\n" + "Individual: Bill\n" + "   Facts: not hasWife     Mary\n"
            + "   Facts: not hasDaughter Susan\n" + "Individual: James\n" + "   SameAs: Jim\n" + "Individual: Jack\n"
            + "   Facts: not hasAge \"53\"^^xsd:integer\n" + "Individual: Father\n" + "   Types: SocialRole\n"
            + "Individual: Meg\n" + "Individual: Susan\n" + "Individual: Jim\n" + "Individual: otherOnt:JohnBrown\n"
            + "Individual: otherOnt:MaryBrown\n" + '\n' + "SameIndividual: John, otherOnt:JohnBrown\n"
            + "SameIndividual: Mary, otherOnt:MaryBrown";
    private static final @Nonnull String TURTLE = "@prefix : <http://example.com/owl/families/> .\n"
            + "@prefix otherOnt: <http://example.org/otherOntologies/families/> .\n"
            + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
            + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n" + '\n' + "<http://example.com/owl/families>\n"
            + "     rdf:type owl:Ontology .\n" + '\n'
            + "<http://example.com/owl/families/majorAge> rdf:type rdfs:Datatype .\n"
            + "<http://example.com/owl/families/minorAge> rdf:type rdfs:Datatype .\n"
            + "<http://example.com/owl/families/personAge> rdf:type rdfs:Datatype .\n"
            + "<http://example.com/owl/families/toddlerAge> rdf:type rdfs:Datatype .\n"
            + "<http://example.com/owl/families/Man> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/Female> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/MyBirthdayGuests> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/Mother> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/YoungChild> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/Parent> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/Father> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/Woman> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/Grandfather> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/Human> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/NarcisticPerson> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/HappyPerson> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/Teenager> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/Orphan> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/SocialRole> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/ChildlessPerson> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/Dead> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/JohnsChildren> rdf:type owl:Class .\n"
            + "<http://example.com/owl/families/Adult> rdf:type owl:Class .\n"
            + "<http://example.org/otherOntologies/families/Grownup> rdf:type owl:Class .\n"
            + "<http://example.org/otherOntologies/families/age> rdf:type owl:Class .\n" + "" + ""
            + "<http://example.com/owl/families/hasAge> rdf:type owl:DatatypeProperty .\n"
            + "<http://example.com/owl/families/hasSSN> rdf:type owl:DatatypeProperty .\n"
            + "<http://example.com/owl/families/hasGrandparent> rdf:type owl:ObjectProperty .\n"
            + "<http://example.com/owl/families/hasRelative> rdf:type owl:ObjectProperty .\n"
            + "<http://example.com/owl/families/loves> rdf:type owl:ObjectProperty .\n"
            + "<http://example.com/owl/families/hasBrother> rdf:type owl:ObjectProperty .\n"
            + "<http://example.com/owl/families/hasUncle> rdf:type owl:ObjectProperty .\n"
            + "<http://example.com/owl/families/parentOf> rdf:type owl:ObjectProperty .\n"
            + "<http://example.com/owl/families/hasSon> rdf:type owl:ObjectProperty .\n"
            + "<http://example.org/otherOntologies/families/child>  rdf:type owl:ObjectProperty .\n"
            + "<http://example.org/otherOntologies/families/age>  rdf:type owl:DatatypeProperty .\n" + ""
            + "<http://example.com/owl/families/hasChild> rdf:type owl:ObjectProperty .\n"
            + "<http://example.com/owl/families/hasParent> rdf:type owl:ObjectProperty .\n"
            + "<http://example.com/owl/families/hasDaughter> rdf:type owl:ObjectProperty .\n"
            + "<http://example.com/owl/families/hasFather> rdf:type owl:ObjectProperty .\n"
            + "<http://example.com/owl/families/minorAge> rdf:type rdfs:Datatype ;\n"
            + "    owl:equivalentClass [ rdf:type rdfs:Datatype ; owl:onDatatype xsd:integer ;\n"
            + "        owl:withRestrictions ( [ xsd:minInclusive 0 ]\n" + "       [ xsd:maxInclusive 18 ] ) ] .\n"
            + "<http://example.com/owl/families/ChildlessPerson> rdf:type owl:Class ;\n"
            + "      rdfs:subClassOf [ rdf:type owl:Class ;\n"
            + "          owl:intersectionOf ( <http://example.com/owl/families/Person>\n"
            + "          [ rdf:type owl:Class ;\n"
            + "          owl:complementOf [ rdf:type owl:Restriction ; owl:onProperty [ owl:inverseOf <http://example.com/owl/families/hasParent> ] ; owl:someValuesFrom owl:Thing ]\n"
            + "        ] ) ] .\n" + ":Mary rdf:type :Person .\n" + ":Mary rdf:type :Woman .\n"
            + ":Woman rdfs:subClassOf :Person .\n" + ":Mother rdfs:subClassOf :Woman .\n"
            + ":Person owl:equivalentClass :Human .\n" + "[]  rdf:type     owl:AllDisjointClasses ;\n"
            + "    owl:members  ( :Woman  :Man ) .\n" + ":John :hasWife :Mary .\n"
            + "[]  rdf:type               owl:NegativePropertyAssertion ;\n" + "    owl:sourceIndividual   :Bill ;\n"
            + "    owl:assertionProperty  :hasWife ;\n" + "    owl:targetIndividual   :Mary .\n"
            + ":hasWife rdfs:subPropertyOf :hasSpouse .\n" + ":hasWife rdfs:domain :Man ;\n"
            + "         rdfs:range  :Woman .\n" + ":John  owl:differentFrom :Bill .\n" + ":James owl:sameAs :Jim .\n"
            + ":John  :hasAge  51 .\n" + "[]  rdf:type               owl:NegativePropertyAssertion ;\n"
            + "    owl:sourceIndividual   :Jack ;\n" + "    owl:assertionProperty  :hasAge ;\n"
            + "    owl:targetValue        53 .\n" + ":hasAge  rdfs:domain  :Person ;\n"
            + "         rdfs:range   xsd:nonNegativeInteger .\n" + ":Mother  owl:equivalentClass  [\n"
            + "  rdf:type            owl:Class ;\n" + "  owl:intersectionOf  ( :Woman :Parent )\n" + "] .\n"
            + ":Parent  owl:equivalentClass  [\n" + "  rdf:type     owl:Class ;\n"
            + "  owl:unionOf  ( :Mother :Father )\n" + "] .\n" + ":ChildlessPerson  owl:equivalentClass  [\n"
            + "  rdf:type            owl:Class ;\n"
            + "  owl:intersectionOf  ( :Person  [ owl:complementOf  :Parent ] )\n" + "] .\n"
            + ":Grandfather  rdfs:subClassOf  [\n" + "  rdf:type            owl:Class ;\n"
            + "  owl:intersectionOf  ( :Man  :Parent )\n" + "] .\n" + ":Jack  rdf:type  [\n"
            + "  rdf:type            owl:Class ;\n" + "  owl:intersectionOf  ( :Person\n"
            + "                        [ rdf:type owl:Class ; owl:complementOf  :Parent ]\n"
            + "                      )\n" + "] .\n" + ":Parent  owl:equivalentClass  [\n"
            + "  rdf:type            owl:Restriction ;\n" + "  owl:onProperty      :hasChild ;\n"
            + "  owl:someValuesFrom  :Person\n" + "] .\n" + ":HappyPerson\n" + "    owl:equivalentClass  [\n"
            + "      rdf:type            owl:Class ;\n"
            + "      owl:intersectionOf  ( [ rdf:type owl:Restriction ; owl:onProperty :hasChild ; owl:allValuesFrom   :HappyPerson      ]\n"
            + "                            [ rdf:type owl:Restriction ; owl:onProperty :hasChild ; owl:someValuesFrom  :HappyPerson      ] )\n"
            + "    ] .\n" + ":JohnsChildren  owl:equivalentClass  [\n" + "  rdf:type        owl:Restriction ;\n"
            + "  owl:onProperty  :hasParent ;\n" + "  owl:hasValue    :John\n" + "] .\n"
            + ":NarcisticPerson owl:equivalentClass  [\n" + "  rdf:type        owl:Restriction ;\n"
            + "  owl:onProperty  :loves ;\n" + "  owl:hasSelf     \"true\"^^xsd:boolean .\n" + "] .\n"
            + ":John  rdf:type  [\n" + "  rdf:type                     owl:Restriction ;\n"
            + "  owl:maxQualifiedCardinality  \"4\"^^xsd:nonNegativeInteger ;\n"
            + "  owl:onProperty               :hasChild ;\n" + "  owl:onClass                  :Parent\n" + "] .\n"
            + ":John  rdf:type  [\n" + "  rdf:type                     owl:Restriction ;\n"
            + "  owl:minQualifiedCardinality  \"2\"^^xsd:nonNegativeInteger ;\n"
            + "  owl:onProperty               :hasChild ;\n" + "  owl:onClass                  :Parent\n" + "] .\n"
            + ":John  rdf:type  [\n" + "  rdf:type                  owl:Restriction ;\n"
            + "  owl:qualifiedCardinality  \"3\"^^xsd:nonNegativeInteger ;\n"
            + "  owl:onProperty            :hasChild ;\n" + "  owl:onClass               :Parent\n" + "] .\n"
            + ":John  rdf:type  [\n" + "  rdf:type         owl:Restriction ;\n"
            + "  owl:cardinality  \"5\"^^xsd:nonNegativeInteger ;\n" + "  owl:onProperty   :hasChild\n" + "] .\n"
            + ":MyBirthdayGuests  owl:equivalentClass  [\n" + "  rdf:type   owl:Class ;\n"
            + "  owl:oneOf  ( :Bill  :John  :Mary )\n" + "] .\n" + ":hasParent owl:inverseOf :hasChild .\n"
            + ":Orphan  owl:equivalentClass  [\n" + "  rdf:type           owl:Restriction ;\n"
            + "  owl:onProperty     [ owl:inverseOf  :hasChild ] ;\n" + "  owl:allValuesFrom  :Dead\n" + "] .\n"
            + ":hasSpouse  rdf:type  owl:SymmetricProperty .\n" + ":hasChild  rdf:type  owl:AsymmetricProperty .\n"
            + ":hasParent  owl:propertyDisjointWith  :hasSpouse .\n"
            + ":hasRelative  rdf:type  owl:ReflexiveProperty .\n" + ":parentOf  rdf:type  owl:IrreflexiveProperty .\n"
            + ":hasHusband  rdf:type  owl:FunctionalProperty .\n"
            + ":hasHusband  rdf:type  owl:InverseFunctionalProperty .\n"
            + ":hasAncestor  rdf:type  owl:TransitiveProperty .\n"
            + ":hasGrandparent  owl:propertyChainAxiom  ( :hasParent  :hasParent ) .\n"
            + ":Person owl:hasKey ( :hasSSN ) .\n" + ":personAge  owl:equivalentClass\n"
            + " [ rdf:type rdfs:Datatype;\n" + "   owl:onDatatype xsd:integer;\n"
            + "   owl:withRestrictions ( [ xsd:minInclusive \"0\"^^xsd:integer ] [ xsd:maxInclusive \"150\"^^xsd:integer ] )\n"
            + " ] .\n" + ":majorAge  owl:equivalentClass\n" + "  [ rdf:type rdfs:Datatype;\n"
            + "    owl:intersectionOf ( :personAge\n"
            + "                         [ rdf:type rdfs:Datatype; owl:datatypeComplementOf :minorAge ]\n" + "    )\n"
            + "  ] .\n" + ":toddlerAge  owl:equivalentClass\n"
            + "  [ rdf:type rdfs:Datatype; owl:oneOf (  \"1\"^^xsd:integer  \"2\"^^xsd:integer ) ] .\n"
            + ":hasAge  rdf:type  owl:FunctionalProperty .\n" + ":Teenager  rdfs:subClassOf\n"
            + "      [ rdf:type             owl:Restriction ;\n" + "        owl:onProperty       :hasAge ;\n"
            + "        owl:someValuesFrom\n" + "         [ rdf:type             rdfs:Datatype ;\n"
            + "           owl:onDatatype       xsd:integer ;\n"
            + "           owl:withRestrictions (  [ xsd:minExclusive     \"12\"^^xsd:integer ] [ xsd:maxInclusive     \"19\"^^xsd:integer ] )\n"
            + "         ]\n" + "      ] .\n" + ":Person  rdfs:comment  \"Represents the set of all people.\" .\n"
            + ":Man rdfs:subClassOf :Person .\n" + "[]  rdf:type       owl:Axiom ;\n"
            + "    owl:annotatedSource    :Man ;\n" + "    owl:annotatedProperty  rdfs:subClassOf ;\n"
            + "    owl:annotatedTarget    :Person ;\n"
            + "    rdfs:comment     \"States that every man is a person.\" .\n" + '\n'
            + ":Mary      owl:sameAs              otherOnt:MaryBrown .\n"
            + ":John      owl:sameAs              otherOnt:JohnBrown .\n"
            + ":Adult     owl:equivalentClass     otherOnt:Grownup .\n"
            + ":hasChild  owl:equivalentProperty  otherOnt:child .\n"
            + ":hasAge    owl:equivalentProperty  otherOnt:age .\n" + ":John    rdf:type owl:NamedIndividual .\n"
            + ":Person  rdf:type owl:Class .\n" + ":hasWife rdf:type owl:ObjectProperty .\n"
            + ":hasAge  rdf:type owl:DatatypeProperty .\n" + ":John rdf:type :Father .\n"
            + ":Father rdf:type :SocialRole .\n" + ":Father  rdfs:subClassOf  [\n"
            + "  rdf:type            owl:Class ;\n" + "  owl:intersectionOf  ( :Man  :Parent )\n" + "] .\n" + '\n'
            + ":Parent  owl:equivalentClass  [\n" + "  rdf:type            owl:Restriction ;\n"
            + "  owl:onProperty      :hasChild ;\n" + "  owl:someValuesFrom  :Person\n" + "] .\n" + '\n'
            + ":NarcisticPerson  owl:equivalentClass  [\n" + "  rdf:type        owl:Restriction ;\n"
            + "  owl:onProperty  :loves ;\n" + "  owl:hasSelf     true\n" + "] .\n" + '\n'
            + "[] rdf:type     owl:AllDisjointClasses ;   owl:members  ( :Mother  :Father  :YoungChild ) .\n" + '\n'
            + ":hasUncle  owl:propertyChainAxiom  ( :hasFather  :hasBrother ) .\n" + '\n'
            + "[]  rdf:type               owl:NegativePropertyAssertion ;\n" + "    owl:sourceIndividual   :Bill ;\n"
            + "    owl:assertionProperty  :hasDaughter ;\n" + "    owl:targetIndividual   :Susan .\n"
            + ":ChildlessPerson  owl:subClassOf  [\n" + "  rdf:type            owl:Class ;\n"
            + "  owl:intersectionOf  ( :Person\n" + "                        [ owl:complementOf  [\n"
            + "                            rdf:type            owl:Restriction ;\n"
            + "                            owl:onProperty      [ owl:inverseOf  :hasParent ] ;\n"
            + "                            owl:someValuesFrom  owl:Thing\n" + "                          ]\n"
            + "                        ]\n" + "                      )\n" + "] .\n" + '\n'
            + ":hasSon  owl:propertyDisjointWith  :hasDaughter.\n" + '\n'
            + ":hasFather  rdfs:subPropertyOf  :hasParent.\n" + "[]  rdf:type            owl:Class ;\n"
            + "    owl:intersectionOf  ( [ rdf:type   owl:Class ; owl:oneOf  ( :Mary  :Bill  :Meg ) ]\n"
            + "                          :Female\n" + "                        ) ;\n" + "    rdfs:subClassOf     [\n"
            + "      rdf:type            owl:Class ;\n" + "      owl:intersectionOf  ( :Parent\n"
            + "                            [ rdf:type            owl:Restriction ; owl:maxCardinality  \"1\"^^xsd:nonNegativeInteger ; owl:onProperty      :hasChild ]\n"
            + "                            [ rdf:type           owl:Restriction ; owl:onProperty     :hasChild ; owl:allValuesFrom  :Female ]\n"
            + "                          )\n" + "    ] .";
}
