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
package org.semanticweb.owlapi.examples;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.EntitySearcher.getAnnotationObjects;
import static org.semanticweb.owlapi.search.Searcher.sup;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;
import static org.semanticweb.owlapi.vocab.OWLFacet.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;

import javax.annotation.Nonnull;

import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.Filters;
import org.semanticweb.owlapi.util.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
@SuppressWarnings({ "javadoc", "unused" })
public class Examples extends TestBase {

    @Nonnull public static final String KOALA = "<?xml version=\"1.0\"?>\n"
        + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#\" xml:base=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl\">\n"
        + "  <owl:Ontology rdf:about=\"\"/>\n"
        + "  <owl:Class rdf:ID=\"Female\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:FunctionalProperty rdf:about=\"#hasGender\"/></owl:onProperty><owl:hasValue><Gender rdf:ID=\"female\"/></owl:hasValue></owl:Restriction></owl:equivalentClass></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"Marsupials\"><owl:disjointWith><owl:Class rdf:about=\"#Person\"/></owl:disjointWith><rdfs:subClassOf><owl:Class rdf:about=\"#Animal\"/></rdfs:subClassOf></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"Student\"><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"#Person\"/><owl:Restriction><owl:onProperty><owl:FunctionalProperty rdf:about=\"#isHardWorking\"/></owl:onProperty><owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</owl:hasValue></owl:Restriction><owl:Restriction><owl:someValuesFrom><owl:Class rdf:about=\"#University\"/></owl:someValuesFrom><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasHabitat\"/></owl:onProperty></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"KoalaWithPhD\"><owl:versionInfo>1.2</owl:versionInfo><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:hasValue><Degree rdf:ID=\"PhD\"/></owl:hasValue><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasDegree\"/></owl:onProperty></owl:Restriction><owl:Class rdf:about=\"#Koala\"/></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"University\"><rdfs:subClassOf><owl:Class rdf:ID=\"Habitat\"/></rdfs:subClassOf></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"Koala\"><rdfs:subClassOf><owl:Restriction><owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">false</owl:hasValue><owl:onProperty><owl:FunctionalProperty rdf:about=\"#isHardWorking\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:someValuesFrom><owl:Class rdf:about=\"#DryEucalyptForest\"/></owl:someValuesFrom><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasHabitat\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf rdf:resource=\"#Marsupials\"/></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"Animal\"><rdfs:seeAlso>Male</rdfs:seeAlso><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasHabitat\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:cardinality><owl:onProperty><owl:FunctionalProperty rdf:about=\"#hasGender\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><owl:versionInfo>1.1</owl:versionInfo></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"Forest\"><rdfs:subClassOf rdf:resource=\"#Habitat\"/></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"Rainforest\"><rdfs:subClassOf rdf:resource=\"#Forest\"/></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"GraduateStudent\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasDegree\"/></owl:onProperty><owl:someValuesFrom><owl:Class><owl:oneOf rdf:parseType=\"Collection\"><Degree rdf:ID=\"BA\"/><Degree rdf:ID=\"BS\"/></owl:oneOf></owl:Class></owl:someValuesFrom></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf rdf:resource=\"#Student\"/></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"Parent\"><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"#Animal\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasChildren\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass><rdfs:subClassOf rdf:resource=\"#Animal\"/></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"DryEucalyptForest\"><rdfs:subClassOf rdf:resource=\"#Forest\"/></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"Quokka\"><rdfs:subClassOf><owl:Restriction><owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</owl:hasValue><owl:onProperty><owl:FunctionalProperty rdf:about=\"#isHardWorking\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf rdf:resource=\"#Marsupials\"/></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"TasmanianDevil\"><rdfs:subClassOf rdf:resource=\"#Marsupials\"/></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"MaleStudentWith3Daughters\"><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"#Student\"/><owl:Restriction><owl:onProperty><owl:FunctionalProperty rdf:about=\"#hasGender\"/></owl:onProperty><owl:hasValue><Gender rdf:ID=\"male\"/></owl:hasValue></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasChildren\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">3</owl:cardinality></owl:Restriction><owl:Restriction><owl:allValuesFrom rdf:resource=\"#Female\"/><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasChildren\"/></owl:onProperty></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"Degree\"/>\n  <owl:Class rdf:ID=\"Gender\"/>\n"
        + "  <owl:Class rdf:ID=\"Male\"><owl:equivalentClass><owl:Restriction><owl:hasValue rdf:resource=\"#male\"/><owl:onProperty><owl:FunctionalProperty rdf:about=\"#hasGender\"/></owl:onProperty></owl:Restriction></owl:equivalentClass></owl:Class>\n"
        + "  <owl:Class rdf:ID=\"Person\"><rdfs:subClassOf rdf:resource=\"#Animal\"/><owl:disjointWith rdf:resource=\"#Marsupials\"/></owl:Class>\n"
        + "  <owl:ObjectProperty rdf:ID=\"hasHabitat\"><rdfs:range rdf:resource=\"#Habitat\"/><rdfs:domain rdf:resource=\"#Animal\"/></owl:ObjectProperty>\n"
        + "  <owl:ObjectProperty rdf:ID=\"hasDegree\"><rdfs:domain rdf:resource=\"#Person\"/><rdfs:range rdf:resource=\"#Degree\"/></owl:ObjectProperty>\n"
        + "  <owl:ObjectProperty rdf:ID=\"hasChildren\"><rdfs:range rdf:resource=\"#Animal\"/><rdfs:domain rdf:resource=\"#Animal\"/></owl:ObjectProperty>\n"
        + "  <owl:FunctionalProperty rdf:ID=\"hasGender\"><rdfs:range rdf:resource=\"#Gender\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#ObjectProperty\"/><rdfs:domain rdf:resource=\"#Animal\"/></owl:FunctionalProperty>\n"
        + "  <owl:FunctionalProperty rdf:ID=\"isHardWorking\"><rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#boolean\"/><rdfs:domain rdf:resource=\"#Person\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#DatatypeProperty\"/></owl:FunctionalProperty>\n"
        + "  <Degree rdf:ID=\"MA\"/>\n</rdf:RDF>";

    /**
     * The examples here show how to load ontologies.
     * 
     * @throws Exception
     *         exception
     */
    public void shouldLoad() throws Exception {
        // Get hold of an ontology manager
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = load(manager);
        // We can always obtain the location where an ontology was loaded from;
        // for this test, though, since the ontology was loaded from a string,
        // this does not return a file
        IRI documentIRI = manager.getOntologyDocumentIRI(ontology);
        // In cases where a local copy of one of more ontologies is used, an
        // ontology IRI mapper can be used to provide a redirection mechanism.
        // This means that ontologies can be loaded as if they were located on
        // the web. In this example, we simply redirect the loading from
        // an IRI to our local copy above.
        // iri and file here are used as examples
        IRI iri = ontology.getOntologyID().getOntologyIRI().get();
        IRI remoteOntology = IRI.create("http://remote.ontology/we/dont/want/to/load");
        manager.getIRIMappers().add(new SimpleIRIMapper(remoteOntology, iri));
        // Load the ontology as if we were loading it from the web (from its
        // ontology IRI)
        OWLOntology redirectedOntology = manager.loadOntology(remoteOntology);
        assertEquals(redirectedOntology, ontology);
        // Note that when imports are loaded an ontology manager will be
        // searched for mappings
    }

    /**
     * @param manager
     *        manager
     * @return loaded ontology
     * @throws OWLOntologyCreationException
     *         if a problem pops up
     */
    OWLOntology load(OWLOntologyManager manager) throws OWLOntologyCreationException {
        // in this test, the ontology is loaded from a string
        return manager.loadOntologyFromOntologyDocument(new StringDocumentSource(KOALA));
    }

    /**
     * This example shows how an ontology can be saved in various formats to
     * various locations and streams.
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldSaveOntologies() throws Exception {
        // Get hold of an ontology manager
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = load(manager);
        // Now save a local copy of the ontology. (Specify a path appropriate to
        // your setup)
        File file = folder.newFile("owlapiexamples_saving.owl");
        manager.saveOntology(ontology, IRI.create(file.toURI()));
        // By default ontologies are saved in the format from which they were
        // loaded. In this case the ontology was loaded from rdf/xml. We
        // can get information about the format of an ontology from its manager
        OWLDocumentFormat format = ontology.getFormat();
        // We can save the ontology in a different format. Lets save the
        // ontology
        // in owl/xml format
        OWLXMLDocumentFormat owlxmlFormat = new OWLXMLDocumentFormat();
        // Some ontology formats support prefix names and prefix IRIs. In our
        // case we loaded the Koala ontology from an rdf/xml format, which
        // supports prefixes. When we save the ontology in the new format we
        // will copy the prefixes over so that we have nicely abbreviated IRIs
        // in the new ontology document
        if (format.isPrefixOWLOntologyFormat()) {
            owlxmlFormat.copyPrefixesFrom(format.asPrefixOWLOntologyFormat());
        }
        manager.saveOntology(ontology, owlxmlFormat, IRI.create(file.toURI()));
        // We can also dump an ontology to System.out by specifying a different
        // OWLOntologyOutputTarget. Note that we can write an ontology to a
        // stream in a similar way using the StreamOutputTarget class
        // Try another format - The Manchester OWL Syntax
        ManchesterSyntaxDocumentFormat manSyntaxFormat = new ManchesterSyntaxDocumentFormat();
        if (format.isPrefixOWLOntologyFormat()) {
            manSyntaxFormat.copyPrefixesFrom(format.asPrefixOWLOntologyFormat());
        }
        // Replace the ByteArrayOutputStream wth an actual output stream to save
        // to a file.
        manager.saveOntology(ontology, manSyntaxFormat, new StreamDocumentTarget(new ByteArrayOutputStream()));
    }

    /**
     * This example shows how to get access to objects that represent entities.
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldAccessEntities() throws Exception {
        // In order to create objects that represent entities we need a
        // data factory.
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        // We can get a reference to a data factory from an OWLOntologyManager.
        OWLDataFactory factory = manager.getOWLDataFactory();
        // In OWL, entities are named objects that are used to build class
        // expressions and axioms. They include classes, properties (object,
        // data and annotation), named individuals and datatypes. All entities
        // may be obtained from an OWLDataFactory. Let's create an object to
        // represent a class. In this case, we'll choose
        // http://www.semanticweb.org/owlapi/ontologies/ontology#A as the IRI
        // for our class. There are two ways we can create classes (and other
        // entities). The first is by specifying the full IRI. First we create
        // an IRI object:
        IRI iri = IRI.create("http://www.semanticweb.org/owlapi/ontologies/ontology#A");
        // Now we create the class
        OWLClass clsAMethodA = factory.getOWLClass(iri);
        // The second is to use a prefix manager and specify abbreviated IRIs.
        // This is useful for creating lots of entities with the same prefix
        // IRIs. First create our prefix manager and specify that the default
        // prefix IRI (bound to the empty prefix name) is
        // http://www.semanticweb.org/owlapi/ontologies/ontology#
        PrefixManager pm = new DefaultPrefixManager(null, null,
            "http://www.semanticweb.org/owlapi/ontologies/ontology#");
        // Now we use the prefix manager and just specify an abbreviated IRI
        OWLClass clsAMethodB = factory.getOWLClass(":A", pm);
        // Note that clsAMethodA will be equal to clsAMethodB because they are
        // both OWLClass objects and have the same IRI. Creating entities in the
        // above manner does not "add them to an ontology". They are merely
        // objects that allow us to reference certain objects (classes etc.) for
        // use in class expressions, and axioms (which can be added to an
        // ontology). Lets create an ontology, and add a declaration axiom to
        // the ontology that declares the above class
        OWLOntology ontology = manager.createOntology(IRI.create("http://anyiri.com/can/be/used/ontology"));
        // We can add a declaration axiom to the ontology, that essentially adds
        // the class to the signature of our ontology.
        OWLDeclarationAxiom declarationAxiom = factory.getOWLDeclarationAxiom(clsAMethodA);
        manager.addAxiom(ontology, declarationAxiom);
        // Note that it isn't necessary to add declarations to an ontology in
        // order to use an entity. Declarations will automatically be added in
        // the
        // saved version of the ontology.
    }

    /**
     * This example shows how to create dataranges.
     */
    @Test
    public void shouldBuildDataRanges() throws Exception {
        // OWLDataRange is the superclass of all data ranges in the OWL API.
        // Data ranges are used as the types of literals, as the ranges for data
        // properties, as filler for data reatrictions. Get hold of a manager to
        // work with
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();
        // OWLDatatype represents named datatypes in OWL. These are a bit like
        // classes whose instances are data values OWLDatatype objects are
        // obtained from a data factory. The OWL2Datatype enum defines built in
        // OWL 2 Datatypes Get hold of the integer datatype
        OWLDatatype integer = factory.getOWLDatatype(OWL2Datatype.XSD_INTEGER);
        // For common data types there are some convenience methods of
        // OWLDataFactory. For example
        OWLDatatype integerDatatype = factory.getIntegerOWLDatatype();
        OWLDatatype floatDatatype = factory.getFloatOWLDatatype();
        OWLDatatype doubleDatatype = factory.getDoubleOWLDatatype();
        OWLDatatype booleanDatatype = factory.getBooleanOWLDatatype();
        // The top datatype (analgous to owl:Thing) is rdfs:Literal, which can
        // be obtained from the data factory
        OWLDatatype rdfsLiteral = factory.getTopDatatype();
        // Custom data ranges can be built up from these basic datatypes. For
        // example, it is possible to restrict a datatype using facets from XML
        // Schema Datatypes. For example, lets create a data range that
        // describes integers that are greater or equal to 18 To do this, we
        // restrict the xsd:integer datatype using the xsd:minInclusive facet
        // with a value of 18. Get hold of a literal that is an integer value 18
        OWLLiteral eighteen = factory.getOWLLiteral(18);
        // Now create the restriction. The OWLFacet enum provides an enumeration
        // of the various facets that can be used
        OWLDatatypeRestriction integerGE18 = factory.getOWLDatatypeRestriction(integer, MIN_INCLUSIVE, eighteen);
        // We could use this datatype in restriction, as the range of data
        // properties etc. For example, if we want to restrict the range of the
        // :hasAge data property to 18 or more we specify its range as this data
        // range
        PrefixManager pm = new DefaultPrefixManager(null, null, "http://www.semanticweb.org/ontologies/dataranges#");
        OWLDataProperty hasAge = factory.getOWLDataProperty(":hasAge", pm);
        OWLDataPropertyRangeAxiom rangeAxiom = factory.getOWLDataPropertyRangeAxiom(hasAge, integerGE18);
        OWLOntology ontology = manager.createOntology(IRI.create("http://www.semanticweb.org/ontologies/dataranges"));
        // Add the range axiom to our ontology
        manager.addAxiom(ontology, rangeAxiom);
        // For creating datatype restrictions on integers or doubles there are
        // some convenience methods on OWLDataFactory For example: Create a data
        // range of integers greater or equal to 60
        OWLDatatypeRestriction integerGE60 = factory.getOWLDatatypeMinInclusiveRestriction(60);
        // Create a data range of integers less than 16
        OWLDatatypeRestriction integerLT16 = factory.getOWLDatatypeMaxExclusiveRestriction(18);
        // In OWL 2 it is possible to represent the intersection, union and
        // complement of data types For example, we could create a union of data
        // ranges of the data range integer less than 16 or integer greater or
        // equal to 60
        OWLDataUnionOf concessionaryAge = factory.getOWLDataUnionOf(integerLT16, integerGE60);
        // We can also coin names for custom data ranges. To do this we use an
        // OWLDatatypeDefintionAxiom Get hold of a named datarange (datatype)
        // that will be used to assign a name to our above datatype
        OWLDatatype concessionaryAgeDatatype = factory.getOWLDatatype(":ConcessionaryAge", pm);
        // Now create a datatype definition axiom
        OWLDatatypeDefinitionAxiom datatypeDef = factory.getOWLDatatypeDefinitionAxiom(concessionaryAgeDatatype,
            concessionaryAge);
        // Add the definition to our ontology
        manager.addAxiom(ontology, datatypeDef);
        // Dump our ontology
        manager.saveOntology(ontology, new StreamDocumentTarget(new ByteArrayOutputStream()));
    }

    /**
     * This example shows how to work with dataranges. OWL 1.1 (and newer)
     * allows data ranges to be created by taking a base datatype e.g. int,
     * string etc. and then by applying facets to restrict the datarange. For
     * example, int greater than 18
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldUseDataranges() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        String base = "http://org.semanticweb.datarangeexample";
        OWLOntology ontology = manager.createOntology(IRI.create(base));
        // We want to add an axiom to our ontology that states that adults have
        // an age greater than 18. To do this, we will create a restriction
        // along a hasAge property, with a filler that corresponds to the set of
        // integers greater than 18. First get a reference to our hasAge
        // property
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLDataProperty hasAge = factory.getOWLDataProperty(base + "hasAge");
        // For completeness, we will make hasAge functional by adding an axiom
        // to state this
        OWLFunctionalDataPropertyAxiom funcAx = factory.getOWLFunctionalDataPropertyAxiom(hasAge);
        manager.applyChange(new AddAxiom(ontology, funcAx));
        // Now create the data range which correponds to int greater than 18. To
        // do this, we get hold of the int datatype and then restrict it with a
        // minInclusive facet restriction.
        OWLDatatype intDatatype = factory.getIntegerOWLDatatype();
        // Create the value "18", which is an int.
        OWLLiteral eighteenConstant = factory.getOWLLiteral(18);
        // Now create our custom datarange, which is int greater than or equal
        // to 18. To do this, we need the minInclusive facet
        OWLFacet facet = MIN_INCLUSIVE;
        // Create the restricted data range by applying the facet restriction
        // with a value of 18 to int
        OWLDataRange intGreaterThan18 = factory.getOWLDatatypeRestriction(intDatatype, facet, eighteenConstant);
        // Now we can use this in our datatype restriction on hasAge
        OWLClassExpression thingsWithAgeGreaterOrEqualTo18 = factory.getOWLDataSomeValuesFrom(hasAge, intGreaterThan18);
        // Now we want to say all adults have an age that is greater or equal to
        // 18 - i.e. Adult is a subclass of hasAge some int[>= 18] Obtain a
        // reference to the Adult class
        OWLClass adult = factory.getOWLClass(base + "#Adult");
        // Now make adult a subclass of the things that have an age greater to
        // or equal to 18
        OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(adult, thingsWithAgeGreaterOrEqualTo18);
        // Add our axiom to the ontology
        manager.applyChange(new AddAxiom(ontology, ax));
    }

    @Test
    public void shouldInstantiateLiterals() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();
        // Get an plain literal with an empty language tag
        OWLLiteral literal1 = factory.getOWLLiteral("My string literal", "");
        // Get an untyped string literal with a language tag
        OWLLiteral literal2 = factory.getOWLLiteral("My string literal", "en");
        // Typed literals are literals that are typed with a datatype Create a
        // typed literal to represent the integer 33
        OWLDatatype integerDatatype = factory.getOWLDatatype(OWL2Datatype.XSD_INTEGER);
        OWLLiteral literal3 = factory.getOWLLiteral("33", integerDatatype);
        // There is are short cut methods on OWLDataFactory for creating typed
        // literals with common datatypes Internallym these methods create
        // literals as above Create a literal to represent the integer 33
        OWLLiteral literal4 = factory.getOWLLiteral(33);
        // Create a literal to represent the double 33.3
        OWLLiteral literal5 = factory.getOWLLiteral(33.3);
        // Create a literal to represent the boolean value true
        OWLLiteral literal6 = factory.getOWLLiteral(true);
    }

    /**
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldLoadAndSave() throws Exception {
        // A simple example of how to load and save an ontology We first need to
        // obtain a copy of an OWLOntologyManager, which, as the name suggests,
        // manages a set of ontologies. An ontology is unique within an ontology
        // manager. Each ontology knows its ontology manager. To load multiple
        // copies of an ontology, multiple managers would have to be used.
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        // In this test we don't rely on a remote ontology and load it from
        // a string
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(KOALA));
        // Print out all of the classes which are contained in the signature of
        // the ontology. These are the classes that are referenced by axioms in
        // the ontology.
        // for (OWLClass cls : ontology.getClassesInSignature()) {
        // System.out.println(cls);
        // }
        // Now save a copy to another location in OWL/XML format (i.e. disregard
        // the format that the ontology was loaded in).
        IRI destination = IRI.create(folder.newFile("owlapiexample_example1.xml"));
        manager.saveOntology(ontology, new OWLXMLDocumentFormat(), destination);
    }

    @Test
    public void shouldAddAxiom() throws Exception {
        // Create the manager that we will use to load ontologies.
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        // Ontologies can have an IRI, which is used to identify the ontology.
        // You should think of the ontology IRI as the "name" of the ontology.
        // This IRI frequently resembles a Web address (i.e. http://...), but it
        // is important to realise that the ontology IRI might not necessarily
        // be resolvable. In other words, we can't necessarily get a document
        // from the URL corresponding to the ontology IRI, which represents the
        // ontology. In order to have a concrete representation of an ontology
        // (e.g. an RDF/XML file), we MAP the ontology IRI to a PHYSICAL IRI. We
        // do this using an IRIMapper Let's create an ontology and name it
        // "http://www.co-ode.org/ontologies/testont.owl" We need to set up a
        // mapping which points to a concrete file where the ontology will be
        // stored. (It's good practice to do this even if we don't intend to
        // save the ontology).
        IRI ontologyIRI = IRI.create("http://www.co-ode.org/ontologies/testont.owl");
        // Create the document IRI for our ontology
        IRI documentIRI = IRI.create("file:/tmp/MyOnt.owl");
        // Set up a mapping, which maps the ontology to the document IRI
        SimpleIRIMapper mapper = new SimpleIRIMapper(ontologyIRI, documentIRI);
        manager.getIRIMappers().add(mapper);
        // Now create the ontology - we use the ontology IRI (not the physical
        // IRI)
        OWLOntology ontology = manager.createOntology(ontologyIRI);
        // Now we want to specify that A is a subclass of B. To do this, we add
        // a subclass axiom. A subclass axiom is simply an object that specifies
        // that one class is a subclass of another class. We need a data factory
        // to create various object from. Each manager has a reference to a data
        // factory that we can use.
        OWLDataFactory factory = manager.getOWLDataFactory();
        // Get hold of references to class A and class B. Note that the ontology
        // does not contain class A or classB, we simply get references to
        // objects from a data factory that represent class A and class B
        OWLClass clsA = factory.getOWLClass(ontologyIRI + "#A");
        OWLClass clsB = factory.getOWLClass(ontologyIRI + "#B");
        // Now create the axiom
        OWLAxiom axiom = factory.getOWLSubClassOfAxiom(clsA, clsB);
        // We now add the axiom to the ontology, so that the ontology states
        // that A is a subclass of B. To do this we create an AddAxiom change
        // object. At this stage neither classes A or B, or the axiom are
        // contained in the ontology. We have to add the axiom to the ontology.
        AddAxiom addAxiom = new AddAxiom(ontology, axiom);
        // We now use the manager to apply the change
        manager.applyChange(addAxiom);
        // The ontology will now contain references to class A and class B -
        // that is, class A and class B are contained within the SIGNATURE of
        // the ontology let's print them out
        ontology.classesInSignature().forEach(Object::toString);
        // do anything that's necessary, e.g., print them out
        // System.out.println("Referenced class: " + cls);
        // We should also find that B is an ASSERTED superclass of A
        Iterable<OWLClassExpression> superClasses = asList(
            sup(ontology.axioms(Filters.subClassWithSub, clsA, INCLUDED), OWLClassExpression.class));
        // Now save the ontology. The ontology will be saved to the location
        // where we loaded it from, in the default ontology format
        manager.saveOntology(ontology);
    }

    /**
     * These examples show how to create new ontologies.
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldCreateOntology() throws Exception {
        // We first need to create an OWLOntologyManager, which will provide a
        // point for creating, loading and saving ontologies. We can create a
        // default ontology manager with the OWLManager class. This provides a
        // common setup of an ontology manager. It registers parsers etc. for
        // loading ontologies in a variety of syntaxes
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        // In OWL 2, an ontology may be named with an IRI (Internationalised
        // Resource Identifier) We can create an instance of the IRI class as
        // follows:
        IRI ontologyIRI = IRI.create("http://www.semanticweb.org/ontologies/myontology");
        // Here we have decided to call our ontology
        // "http://www.semanticweb.org/ontologies/myontology" If we publish our
        // ontology then we should make the location coincide with the ontology
        // IRI Now we have an IRI we can create an ontology using the manager
        OWLOntology ontology = manager.createOntology(ontologyIRI);
        // System.out.println("Created ontology: " + ontology);
        // In OWL 2 if an ontology has an ontology IRI it may also have a
        // version IRI The OWL API encapsulates ontology IRI and possible
        // version IRI information in an OWLOntologyID Each ontology knows about
        // its ID
        OWLOntologyID ontologyID = ontology.getOntologyID();
        // In this case our ontology has an IRI but does not have a version IRI
        // System.out.println("Ontology IRI: " + ontologyID.getOntologyIRI());
        // Our version IRI will be Optional.empty() to indicate that we don't
        // have a version IRI
        // An ontology may not have a version IRI - in this case, we count the
        // ontology as an anonymous ontology. Our ontology does have an IRI so
        // it is not anonymous:
        // System.out.println("Anonymous Ontology: " +
        // ontologyID.isAnonymous());
        // Once an ontology has been created its ontology ID (Ontology IRI and
        // version IRI can be changed) to do this we must apply a SetOntologyID
        // change through the ontology manager. Lets specify a version IRI for
        // our ontology. In our case we will just "extend" our ontology IRI with
        // some version information. We could of course specify any IRI for our
        // version IRI.
        IRI versionIRI = IRI.create(ontologyIRI + "/version1");
        // Note that we MUST specify an ontology IRI if we want to specify a
        // version IRI
        OWLOntologyID newOntologyID = new OWLOntologyID(optional(ontologyIRI), optional(versionIRI));
        // Create the change that will set our version IRI
        SetOntologyID setOntologyID = new SetOntologyID(ontology, newOntologyID);
        // Apply the change
        manager.applyChange(setOntologyID);
        // We can also just specify the ontology IRI and possibly the version
        // IRI at ontology creation time Set up our ID by specifying an ontology
        // IRI and version IRI
        IRI ontologyIRI2 = IRI.create("http://www.semanticweb.org/ontologies/myontology2");
        IRI versionIRI2 = IRI.create("http://www.semanticweb.org/ontologies/myontology2/newversion");
        OWLOntologyID ontologyID2 = new OWLOntologyID(optional(ontologyIRI2), optional(versionIRI2));
        // Now create the ontology
        OWLOntology ontology2 = manager.createOntology(ontologyID2);
        // Finally, if we don't want to give an ontology an IRI, in OWL 2 we
        // don't have to
        OWLOntology anonOntology = manager.createOntology();
        // This ontology is anonymous
        // System.out.println("Anonymous: " + anonOntology.isAnonymous());
    }

    /**
     * This example shows how to specify various property assertions for
     * individuals.
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldCreatePropertyAssertions() throws Exception {
        // We can specify the properties of an individual using property
        // assertions Get a copy of an ontology manager
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        IRI ontologyIRI = IRI.create("http://example.com/owl/families/");
        OWLOntology ontology = manager.createOntology(ontologyIRI);
        // Get hold of a data factory from the manager and set up a prefix
        // manager to make things easier
        OWLDataFactory factory = manager.getOWLDataFactory();
        PrefixManager pm = new DefaultPrefixManager(null, null, ontologyIRI.toString());
        // Let's specify the :John has a wife :Mary Get hold of the necessary
        // individuals and object property
        OWLNamedIndividual john = factory.getOWLNamedIndividual(":John", pm);
        OWLNamedIndividual mary = factory.getOWLNamedIndividual(":Mary", pm);
        OWLObjectProperty hasWife = factory.getOWLObjectProperty(":hasWife", pm);
        // To specify that :John is related to :Mary via the :hasWife property
        // we create an object property assertion and add it to the ontology
        OWLObjectPropertyAssertionAxiom propertyAssertion = factory.getOWLObjectPropertyAssertionAxiom(hasWife, john,
            mary);
        manager.addAxiom(ontology, propertyAssertion);
        // Now let's specify that :John is aged 51. Get hold of a data property
        // called :hasAge
        OWLDataProperty hasAge = factory.getOWLDataProperty(":hasAge", pm);
        // To specify that :John has an age of 51 we create a data property
        // assertion and add it to the ontology
        OWLDataPropertyAssertionAxiom dataPropertyAssertion = factory.getOWLDataPropertyAssertionAxiom(hasAge, john,
            51);
        manager.addAxiom(ontology, dataPropertyAssertion);
        // Note that the above is a shortcut for creating a typed literal and
        // specifying this typed literal as the value of the property assertion.
        // That is, Get hold of the xsd:integer datatype
        OWLDatatype integerDatatype = factory.getOWLDatatype(OWL2Datatype.XSD_INTEGER);
        // Create a typed literal. We type the literal "51" with the datatype
        OWLLiteral literal = factory.getOWLLiteral("51", integerDatatype);
        // Create the property assertion and add it to the ontology
        OWLAxiom ax = factory.getOWLDataPropertyAssertionAxiom(hasAge, john, literal);
        manager.addAxiom(ontology, ax);
        // Dump the ontology to System.out
        manager.saveOntology(ontology, new StreamDocumentTarget(new ByteArrayOutputStream()));
    }

    /**
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldAddClassAssertion() throws Exception {
        // For more information on classes and instances see the OWL 2 Primer
        // http://www.w3.org/TR/2009/REC-owl2-primer-20091027/#Classes_and_Instances
        // In order to say that an individual is an instance of a class (in an
        // ontology), we can add a ClassAssertion to the ontology. For example,
        // suppose we wanted to specify that :Mary is an instance of the class
        // :Person. First we need to obtain the individual :Mary and the class
        // :Person Create an ontology manager to work with
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        // The IRIs used here are taken from the OWL 2 Primer
        String base = "http://example.com/owl/families/";
        PrefixManager pm = new DefaultPrefixManager(null, null, base);
        // Get the reference to the :Person class (the full IRI will be
        // <http://example.com/owl/families/Person>)
        OWLClass person = dataFactory.getOWLClass(":Person", pm);
        // Get the reference to the :Mary class (the full IRI will be
        // <http://example.com/owl/families/Mary>)
        OWLNamedIndividual mary = dataFactory.getOWLNamedIndividual(":Mary", pm);
        // Now create a ClassAssertion to specify that :Mary is an instance of
        // :Person
        OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(person, mary);
        // We need to add the class assertion to the ontology that we want
        // specify that :Mary is a :Person
        OWLOntology ontology = manager.createOntology(IRI.create(base));
        // Add the class assertion
        manager.addAxiom(ontology, classAssertion);
        // Dump the ontology to stdout
        manager.saveOntology(ontology, new StreamDocumentTarget(new ByteArrayOutputStream()));
    }

    /**
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldCreateAndSaveOntology() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        // Let's create an ontology and name it
        // "http://www.co-ode.org/ontologies/testont.owl" We need to set up a
        // mapping which points to a concrete file where the ontology will be
        // stored. (It's good practice to do this even if we don't intend to
        // save the ontology).
        IRI ontologyIRI = IRI.create("http://www.co-ode.org/ontologies/testont.owl");
        // Create a document IRI which can be resolved to point to where our
        // ontology will be saved.
        IRI documentIRI = IRI.create("file:/tmp/SWRLTest.owl");
        // Set up a mapping, which maps the ontology to the document IRI
        SimpleIRIMapper mapper = new SimpleIRIMapper(ontologyIRI, documentIRI);
        manager.getIRIMappers().add(mapper);
        // Now create the ontology - we use the ontology IRI (not the physical
        // IRI)
        OWLOntology ontology = manager.createOntology(ontologyIRI);
        OWLDataFactory factory = manager.getOWLDataFactory();
        // Get hold of references to class A and class B. Note that the ontology
        // does not contain class A or classB, we simply get references to
        // objects from a data factory that represent class A and class B
        OWLClass clsA = factory.getOWLClass(ontologyIRI + "#A");
        OWLClass clsB = factory.getOWLClass(ontologyIRI + "#B");
        SWRLVariable var = factory.getSWRLVariable(ontologyIRI + "#x");
        SWRLRule rule = factory.getSWRLRule(singleton(factory.getSWRLClassAtom(clsA, var)),
            singleton(factory.getSWRLClassAtom(clsB, var)));
        manager.applyChange(new AddAxiom(ontology, rule));
        OWLObjectProperty prop = factory.getOWLObjectProperty(ontologyIRI + "#propA");
        OWLObjectProperty propB = factory.getOWLObjectProperty(ontologyIRI + "#propB");
        SWRLObjectPropertyAtom propAtom = factory.getSWRLObjectPropertyAtom(prop, var, var);
        SWRLObjectPropertyAtom propAtom2 = factory.getSWRLObjectPropertyAtom(propB, var, var);
        Set<SWRLAtom> antecedent = new HashSet<SWRLAtom>();
        antecedent.add(propAtom);
        antecedent.add(propAtom2);
        SWRLRule rule2 = factory.getSWRLRule(antecedent, Collections.singleton(propAtom));
        manager.applyChange(new AddAxiom(ontology, rule2));
        // Now save the ontology. The ontology will be saved to the location
        // where we loaded it from, in the default ontology format
        manager.saveOntology(ontology);
    }

    /**
     * This example shows how add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b).
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldAddObjectPropertyAssertions() throws Exception {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        String base = "http://www.semanticweb.org/ontologies/individualsexample";
        OWLOntology ont = man.createOntology(IRI.create(base));
        OWLDataFactory dataFactory = man.getOWLDataFactory();
        // In this case, we would like to state that matthew has a father who is
        // peter. We need a subject and object - matthew is the subject and
        // peter is the object. We use the data factory to obtain references to
        // these individuals
        OWLIndividual matthew = dataFactory.getOWLNamedIndividual(base + "#matthew");
        OWLIndividual peter = dataFactory.getOWLNamedIndividual(base + "#peter");
        // We want to link the subject and object with the hasFather property,
        // so use the data factory to obtain a reference to this object
        // property.
        OWLObjectProperty hasFather = dataFactory.getOWLObjectProperty(base + "#hasFather");
        // Now create the actual assertion (triple), as an object property
        // assertion axiom matthew --> hasFather --> peter
        OWLObjectPropertyAssertionAxiom assertion = dataFactory.getOWLObjectPropertyAssertionAxiom(hasFather, matthew,
            peter);
        // Finally, add the axiom to our ontology and save
        AddAxiom addAxiomChange = new AddAxiom(ont, assertion);
        man.applyChange(addAxiomChange);
        // We can also specify that matthew is an instance of Person. To do this
        // we use a ClassAssertion axiom. First we need a reference to the
        // person class
        OWLClass personClass = dataFactory.getOWLClass(base + "#Person");
        // Now we will create out Class Assertion to specify that matthew is an
        // instance of Person (or rather that Person has matthew as an instance)
        OWLClassAssertionAxiom ax = dataFactory.getOWLClassAssertionAxiom(personClass, matthew);
        // Add this axiom to our ontology. We can use a short cut method -
        // instead of creating the AddAxiom change ourselves, it will be created
        // automatically and the change applied
        man.addAxiom(ont, ax);
        // Save our ontology
        man.saveOntology(ont, IRI.create("file:/tmp/example.owl"));
    }

    /**
     * An example which shows how to "delete" entities, in this case
     * individuals, from and ontology.
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldDeleteIndividuals() throws Exception {
        // The Koala ontology contains an individual of type Degree.
        // In this example we will delete it..
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology ont = load(man);
        // We can't directly delete individuals, properties or classes from an
        // ontology because ontologies don't directly contain entities -- they
        // are merely referenced by the axioms that the ontology contains. For
        // example, if an ontology contained a subclass axiom SubClassOf(A, B)
        // which stated A was a subclass of B, then that ontology would contain
        // references to classes A and B. If we essentially want to "delete"
        // classes A and B from this ontology we have to remove all axioms that
        // contain class A and class B in their SIGNATURE (in this case just one
        // axiom SubClassOf(A, B)). To do this, we can use the OWLEntityRemove
        // utility class, which will remove an entity (class, property or
        // individual) from a set of ontologies. Create the entity remover - in
        // this case we just want to remove the individuals from the
        // ontology, so pass our reference to the ontology in as a
        // singleton set.
        OWLEntityRemover remover = new OWLEntityRemover(singleton(ont));
        // System.out.println("Number of individuals: "
        // + ont.getIndividualsInSignature().size());
        // Loop through each individual that is referenced in the
        // ontology, and ask it to accept a visit from the entity remover. The
        // remover will automatically accumulate the changes which are necessary
        // to remove the individual from the ontologies which it knows about
        ont.individualsInSignature().forEach(i -> i.accept(remover));
        // Now we get all of the changes from the entity remover, which should
        // be applied to remove all of the individuals that we have visited from
        // the ontology. Notice that "batch" deletes can essentially be
        // performed - we simply visit all of the classes, properties and
        // individuals that we want to remove and then apply ALL of the changes
        // after using the entity remover to collect them
        man.applyChanges(remover.getChanges());
        // System.out.println("Number of individuals: "
        // + ont.getIndividualsInSignature().size());
        // At this point, if we wanted to reuse the entity remover, we would
        // have to reset it
        remover.reset();
    }

    /**
     * An example which shows how to create restrictions and add them as
     * superclasses of a class (i.e. "adding restrictions to classes")
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldCreateRestrictions() throws Exception {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        String base = "http://org.semanticweb.restrictionexample";
        OWLOntology ont = man.createOntology(IRI.create(base));
        // In this example we will add an axiom to state that all Heads have
        // parts that are noses (in fact, here we merely state that a Head has
        // at least one nose!). We do this by creating an existential (some)
        // restriction to describe the class of things which have a part that is
        // a nose (hasPart some Nose), and then we use this restriction in a
        // subclass axiom to state that Head is a subclass of things that have
        // parts that are Noses SubClassOf(Head, hasPart some Nose) -- in other
        // words, Heads have parts that are noses! First we need to obtain
        // references to our hasPart property and our Nose class
        OWLDataFactory factory = man.getOWLDataFactory();
        OWLObjectProperty hasPart = factory.getOWLObjectProperty(base + "#hasPart");
        OWLClass nose = factory.getOWLClass(base + "#Nose");
        // Now create a restriction to describe the class of individuals that
        // have at least one part that is a kind of nose
        OWLClassExpression hasPartSomeNose = factory.getOWLObjectSomeValuesFrom(hasPart, nose);
        // Obtain a reference to the Head class so that we can specify that
        // Heads have noses
        OWLClass head = factory.getOWLClass(base + "#Head");
        // We now want to state that Head is a subclass of hasPart some Nose, to
        // do this we create a subclass axiom, with head as the subclass and
        // "hasPart some Nose" as the superclass (remember, restrictions are
        // also classes - they describe classes of individuals -- they are
        // anonymous classes).
        OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(head, hasPartSomeNose);
        // Add the axiom to our ontology
        AddAxiom addAx = new AddAxiom(ont, ax);
        man.applyChange(addAx);
    }

    /**
     * An example which shows how to interact with a reasoner. In this example
     * Pellet is used as the reasoner. You must get hold of the pellet libraries
     * from pellet.owldl.com.
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldUseReasoner() throws Exception {
        // Create our ontology manager in the usual way.
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ont = load(manager);
        // We need to create an instance of OWLReasoner. An OWLReasoner provides
        // the basic query functionality that we need, for example the ability
        // obtain the subclasses of a class etc. To do this we use a reasoner
        // factory. Create a reasoner factory. In this case, we will use HermiT,
        // but we could also use FaCT++ (http://code.google.com/p/factplusplus/)
        // or Pellet(http://clarkparsia.com/pellet) Note that (as of 03 Feb
        // 2010) FaCT++ and Pellet OWL API 3.0.0 compatible libraries are
        // expected to be available in the near future). For now, we'll use
        // HermiT HermiT can be downloaded from http://hermit-reasoner.com Make
        // sure you get the HermiT library and add it to your class path. You
        // can then instantiate the HermiT reasoner factory: Comment out the
        // first line below and uncomment the second line below to instantiate
        // the HermiT reasoner factory. You'll also need to import the
        // org.semanticweb.HermiT.Reasoner package.
        OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        // OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
        // We'll now create an instance of an OWLReasoner (the implementation
        // being provided by HermiT as we're using the HermiT reasoner factory).
        // The are two categories of reasoner, Buffering and NonBuffering. In
        // our case, we'll create the buffering reasoner, which is the default
        // kind of reasoner. We'll also attach a progress monitor to the
        // reasoner. To do this we set up a configuration that knows about a
        // progress monitor. Create a console progress monitor. This will print
        // the reasoner progress out to the console.
        // ConsoleProgressMonitor progressMonitor = new
        // ConsoleProgressMonitor();
        // Specify the progress monitor via a configuration. We could also
        // specify other setup parameters in the configuration, and different
        // reasoners may accept their own defined parameters this way.
        // OWLReasonerConfiguration config = new SimpleConfiguration(
        // progressMonitor);
        // Create a reasoner that will reason over our ontology and its imports
        // closure. Pass in the configuration.
        // OWLReasoner reasoner = reasonerFactory.createReasoner(ont, config);
        OWLReasoner reasoner = reasonerFactory.createReasoner(ont);
        // Ask the reasoner to do all the necessary work now
        reasoner.precomputeInferences();
        // We can determine if the ontology is actually consistent (in this
        // case, it should be).
        boolean consistent = reasoner.isConsistent();
        // System.out.println("Consistent: " + consistent);
        // We can easily get a list of unsatisfiable classes. (A class is
        // unsatisfiable if it can't possibly have any instances). Note that the
        // getUnsatisfiableClasses method is really just a convenience method
        // for obtaining the classes that are equivalent to owl:Nothing.
        Node<OWLClass> bottomNode = reasoner.getUnsatisfiableClasses();
        // This node contains owl:Nothing and all the classes that are
        // equivalent to owl:Nothing - i.e. the unsatisfiable classes. We just
        // want to print out the unsatisfiable classes excluding owl:Nothing,
        // and we can used a convenience method on the node to get these
        Set<OWLClass> unsatisfiable = bottomNode.getEntitiesMinusBottom();
        if (!unsatisfiable.isEmpty()) {
            // System.out.println("The following classes are unsatisfiable: ");
            for (OWLClass cls : unsatisfiable) {
                // System.out.println(" " + cls);
            }
        } else {
            // System.out.println("There are no unsatisfiable classes");
        }
        // Now we want to query the reasoner for all descendants of Marsupial.
        // Vegetarians are defined in the ontology to be animals that don't eat
        // animals or parts of animals.
        OWLDataFactory fac = manager.getOWLDataFactory();
        // Get a reference to the vegetarian class so that we can as the
        // reasoner about it. The full IRI of this class happens to be:
        // <http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#Marsupials>
        OWLClass marsupials = fac
            .getOWLClass("http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#Marsupials");
        // Now use the reasoner to obtain the subclasses of Marsupials. We can
        // ask for the direct subclasses or all of the (proper)
        // subclasses. In this case we just want the direct ones
        // (which we specify by the "true" flag).
        NodeSet<OWLClass> subClses = reasoner.getSubClasses(marsupials, true);
        // The reasoner returns a NodeSet, which represents a set of Nodes. Each
        // node in the set represents a subclass of Marsupial. A node of
        // classes contains classes, where each class in the node is equivalent.
        // For example, if we asked for the subclasses of some class A and got
        // back a NodeSet containing two nodes {B, C} and {D}, then A would have
        // two proper subclasses. One of these subclasses would be equivalent to
        // the class D, and the other would be the class that is equivalent to
        // class B and class C. In this case, we don't particularly care about
        // the equivalences, so we will flatten this set of sets and print the
        // result
        Set<OWLClass> clses = asSet(subClses.entities());
        // for (OWLClass cls : clses) {
        // System.out.println(" " + cls);
        // }
        // We can easily
        // retrieve the instances of a class. In this example we'll obtain the
        // instances of the class Marsupials.
        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(marsupials, false);
        // The reasoner returns a NodeSet again. This time the NodeSet contains
        // individuals. Again, we just want the individuals, so get a flattened
        // set.
        Set<OWLNamedIndividual> individuals = asSet(individualsNodeSet.entities());
        // for (OWLNamedIndividual ind : individuals) {
        // System.out.println(" " + ind);
        // }
        // Again, it's worth noting that not all of the individuals that are
        // returned were explicitly stated to be marsupials. Finally, we can ask
        // for
        // the property values (property assertions in OWL speak) for a given
        // individual and property.
        // Let's get all properties for all individuals
        ont.individualsInSignature().forEach(i -> ont.objectPropertiesInSignature().forEach(p -> {
            NodeSet<OWLNamedIndividual> individualValues = reasoner.getObjectPropertyValues(i, p);
            Set<OWLNamedIndividual> values = asUnorderedSet(individualValues.entities());
            // System.out.println("The property values for "+p+" for
            // individual "+i+" are: ");
            // for (OWLNamedIndividual ind : values) {
            // System.out.println(" " + ind);
            // }
        }));
        // Finally, let's print out the class hierarchy.
        Node<OWLClass> topNode = reasoner.getTopClassNode();
        print(topNode, reasoner, 0);
    }

    private static void print(Node<OWLClass> parent, OWLReasoner reasoner, int depth) {
        // We don't want to print out the bottom node (containing owl:Nothing
        // and unsatisfiable classes) because this would appear as a leaf node
        // everywhere
        if (parent.isBottomNode()) {
            return;
        }
        // Print an indent to denote parent-child relationships
        printIndent(depth);
        // Now print the node (containing the child classes)
        printNode(parent);
        for (Node<OWLClass> child : reasoner.getSubClasses(parent.getRepresentativeElement(), true)) {
            // Recurse to do the children. Note that we don't have to worry
            // about cycles as there are non in the inferred class hierarchy
            // graph - a cycle gets collapsed into a single node since each
            // class in the cycle is equivalent.
            print(child, reasoner, depth + 1);
        }
    }

    private static void printIndent(int depth) {
        for (int i = 0; i < depth; i++) {
            // System.out.print(" ");
        }
    }

    private static void printNode(Node<OWLClass> node) {
        // The default prefix used here is only an example.
        // For real ontologies, choose a meaningful prefix - the best
        // choice depends on the actual ontology.
        DefaultPrefixManager pm = new DefaultPrefixManager(null, null, "http://owl.man.ac.uk/2005/07/sssw/people#");
        // Print out a node as a list of class names in curly brackets
        for (Iterator<OWLClass> it = node.entities().iterator(); it.hasNext();) {
            OWLClass cls = it.next();
            // User a prefix manager to provide a slightly nicer shorter name
            String shortForm = pm.getShortForm(cls);
            assertNotNull(shortForm);
        }
    }

    /**
     * This example shows how to examine the restrictions on a class.
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldLookAtRestrictions() throws Exception {
        // Create our manager
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        // Load the Koala ontology
        OWLOntology ont = load(man);
        // We want to examine the restrictions on Quokka. To do this,
        // we need to obtain a reference to the Quokka class. In this
        // case, we know the IRI (it happens to be the ontology IRI + #Quokka
        // This isn't always the case. A class may have a IRI that bears no
        // resemblance to the ontology IRI which contains axioms about the
        // class.
        IRI quokkaIRI = IRI.create(ont.getOntologyID().getOntologyIRI().get() + "#Quokka");
        OWLClass quokka = man.getOWLDataFactory().getOWLClass(quokkaIRI);
        // Now we want to collect the properties which are used in existential
        // restrictions on the class. To do this, we will create a utility class
        // - RestrictionVisitor, which acts as a filter for existential
        // restrictions. This uses the Visitor Pattern (google Visitor Design
        // Pattern for more information on this design pattern, or see
        // http://en.wikipedia.org/wiki/Visitor_pattern)
        RestrictionVisitor restrictionVisitor = new RestrictionVisitor(singleton(ont));
        // In this case, restrictions are used as (anonymous) superclasses, so
        // to get the restrictions on quokka we need to obtain the
        // subclass axioms for quokka.
        ont.subClassAxiomsForSubClass(quokka).forEach(ax -> ax.getSuperClass().accept(restrictionVisitor));
        // Ask our superclass to accept a visit from the RestrictionVisitor
        // - if it is an existential restiction then our restriction visitor
        // will answer it - if not our visitor will ignore it
        // Our RestrictionVisitor has now collected all of the properties that
        // have been restricted in existential restrictions - print them out.
        // System.out.println("Restricted properties for " + quokka
        // + ": " + restrictionVisitor.getRestrictedProperties().size());
        // for (OWLObjectPropertyExpression prop : restrictionVisitor
        // .getRestrictedProperties()) {
        // System.out.println(" " + prop);
        // }
    }

    /**
     * Visits existential restrictions and collects the properties which are
     * restricted.
     */
    private static class RestrictionVisitor implements OWLClassExpressionVisitor {

        private final @Nonnull Set<OWLClass> processedClasses;
        private final Set<OWLOntology> onts;

        RestrictionVisitor(Set<OWLOntology> onts) {
            processedClasses = new HashSet<OWLClass>();
            this.onts = onts;
        }

        @Override
        public void visit(OWLClass ce) {
            if (!processedClasses.contains(ce)) {
                // If we are processing inherited restrictions then we
                // recursively visit named supers. Note that we need to keep
                // track of the classes that we have processed so that we don't
                // get caught out by cycles in the taxonomy
                processedClasses.add(ce);
                for (OWLOntology ont : onts) {
                    ont.subClassAxiomsForSubClass(ce).forEach(ax -> ax.getSuperClass().accept(this));
                }
            }
        }

        @Override
        public void visit(OWLObjectSomeValuesFrom ce) {
            // This method gets called when a class expression is an existential
            // (someValuesFrom) restriction and it asks us to visit it
        }
    }

    /**
     * This example shows how to create and read annotations.
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldCreateAndReadAnnotations() throws Exception {
        // Create our manager
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        // Load the Koala ontology
        OWLOntology ont = load(man);
        // We want to add a comment to the Quokka class. First, we need to
        // obtain
        // a reference to the class
        OWLClass quokka = df.getOWLClass(ont.getOntologyID().getOntologyIRI().get() + "#Quokka");
        // Now we create the content of our comment. In this case we simply want
        // a plain string literal. We'll attach a language to the comment to
        // specify that our comment is written in English (en).
        OWLAnnotation commentAnno = df.getOWLAnnotation(df.getRDFSComment(),
            df.getOWLLiteral("A class which represents quokkas", "en"));
        // Specify that the class has an annotation - to do this we attach
        // an entity annotation using an entity annotation axiom (remember,
        // classes are entities)
        OWLAxiom ax = df.getOWLAnnotationAssertionAxiom(quokka.getIRI(), commentAnno);
        // Add the axiom to the ontology
        man.applyChange(new AddAxiom(ont, ax));
        // Now lets add a version info annotation to the ontology. There is no
        // 'standard' OWL annotation object for this, like there is for
        // comments and labels, so the creation of the annotation is a bit more
        // involved. First we'll create a constant for the annotation value.
        // Version info should probably contain a version number for the
        // ontology, but in this case, we'll add some text to describe why the
        // version has been updated
        OWLLiteral lit = df.getOWLLiteral("Added a comment to the quokka class");
        // The above constant is just a plain literal containing the version
        // info text/comment we need to create an annotation, which pairs a IRI
        // with the constant
        OWLAnnotation anno = df.getOWLAnnotation(df.getOWLVersionInfo(), lit);
        // Now we can add this as an ontology annotation Apply the change in the
        // usual way
        man.applyChange(new AddOntologyAnnotation(ont, anno));
        // It is worth noting that literals
        // can be typed or untyped. If literals are untyped then they can have
        // language tags, which are optional - typed literals cannot have
        // language tags. For each class in the ontology, we retrieve its
        // annotations and sift through them. If the annotation annotates the
        // class with a constant which is untyped then we check the language tag
        // to see if it is English. Firstly, get the annotation property for
        // rdfs:label
        OWLAnnotationProperty label = df.getRDFSLabel();
        // Get the annotations on the class that use the label property
        ont.classesInSignature()
            .forEach(c -> getAnnotationObjects(c, ont.importsClosure(), label).map(a -> a.getValue().asLiteral())
                .filter(v -> v.isPresent() && v.get().hasLang("en")).forEach(v -> v.get().getLiteral()));
    }

    /**
     * This example shows how to generate an ontology containing some inferred
     * information.
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldCreateInferredAxioms() throws Exception {
        // Create a reasoner factory.
        // See Profiles for a list of known reasoner factories; note that you
        // will need to add the reasoner and any dependency to the classpath for
        // this to work.
        // The structural reasoner is a mock reasoner that does no inferences;
        // it is used only for examples.
        OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        // Uncomment the line below reasonerFactory = new
        // PelletReasonerFactory(); Load an example ontology - for the purposes
        // of the example, we will just load the ontology.
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology ont = load(man);
        // Create the reasoner and classify the ontology
        OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ont);
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        // To generate an inferred ontology we use implementations of inferred
        // axiom generators to generate the parts of the ontology we want (e.g.
        // subclass axioms, equivalent classes axioms, class assertion axiom
        // etc. - see the org.semanticweb.owlapi.util package for more
        // implementations). Set up our list of inferred axiom generators
        List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
        gens.add(new InferredSubClassAxiomGenerator());
        // Put the inferred axioms into a fresh empty ontology - note that there
        // is nothing stopping us stuffing them back into the original asserted
        // ontology if we wanted to do this.
        OWLOntology infOnt = man.createOntology();
        // Now get the inferred ontology generator to generate some inferred
        // axioms for us (into our fresh ontology). We specify the reasoner that
        // we want to use and the inferred axiom generators that we want to use.
        InferredOntologyGenerator iog = new InferredOntologyGenerator(reasoner, gens);
        iog.fillOntology(man.getOWLDataFactory(), infOnt);
        // Save the inferred ontology. (Replace the IRI with one that is
        // appropriate for your setup)
        man.saveOntology(infOnt, new StringDocumentTarget());
    }

    /**
     * This example shows how to merge to ontologies (by simply combining axioms
     * from one ontology into another ontology).
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldMergeOntologies() throws Exception {
        // Just load two arbitrary ontologies for the purposes of this example
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        load(man);
        OWLOntology o = man.createOntology(IRI.create("urn:test"));
        man.addAxiom(o,
            man.getOWLDataFactory().getOWLDeclarationAxiom(man.getOWLDataFactory().getOWLClass("urn:testclass")));
        // Create our ontology merger
        OWLOntologyMerger merger = new OWLOntologyMerger(man);
        // We merge all of the loaded ontologies. Since an OWLOntologyManager is
        // an OWLOntologySetProvider we just pass this in. We also need to
        // specify the IRI of the new ontology that will be created.
        IRI mergedOntologyIRI = IRI.create("http://www.semanticweb.com/mymergedont");
        OWLOntology merged = merger.createMergedOntology(man, mergedOntologyIRI);
        // Print out the axioms in the merged ontology.
        // for (OWLAxiom ax : merged.getAxioms()) {
        // System.out.println(ax);
        // }
    }

    /**
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldWalkOntology() throws Exception {
        // This example shows how to use an ontology walker to walk the asserted
        // structure of an ontology. Suppose we want to find the axioms that use
        // a some values from (existential restriction) we can use the walker to
        // do this. We'll use the Koala ontology as an example. Load the
        // ontology from the web:
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology ont = load(man);
        // Create the walker. Pass in the koala ontology - we need to put it
        // into a set though, so we just create a singleton set in this case.
        OWLOntologyWalker walker = new OWLOntologyWalker(singleton(ont));
        // Now ask our walker to walk over the ontology. We specify a visitor
        // who gets visited by the various objects as the walker encounters
        // them. We need to create out visitor. This can be any ordinary
        // visitor, but we will extend the OWLOntologyWalkerVisitor because it
        // provides a convenience method to get the current axiom being visited
        // as we go. Create an instance and override the
        // visit(OWLObjectSomeValuesFrom) method, because we are interested in
        // some values from restrictions.
        OWLOntologyWalkerVisitorEx<Object> visitor = new OWLOntologyWalkerVisitorEx<Object>(walker) {

            @Override
            public Object visit(OWLObjectSomeValuesFrom ce) {
                // Print out the restriction
                // System.out.println(desc);
                // Print out the axiom where the restriction is used
                // System.out.println(" " + getCurrentAxiom());
                // We don't need to return anything here.
                return "";
            }
        };
        // Now ask the walker to walk over the ontology structure using our
        // visitor instance.
        walker.walkStructure(visitor);
    }

    /**
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldQueryWithReasoner() throws Exception {
        // We will load the Koala ontology and query it using a reasoner
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology ont = load(man);
        // For this particular ontology, we know that all class, properties
        // names etc. have IRIs that is made up of the ontology IRI plus # plus
        // the local name
        String prefix = ont.getOntologyID().getOntologyIRI().get() + "#";
        // Create a reasoner. We will use Pellet in this case. Make sure that
        // the latest version of the Pellet libraries are on the runtime class
        // path
        OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        // Uncomment the line below reasonerFactory = new
        // PelletReasonerFactory();
        OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ont);
        // Now we can query the reasoner, suppose we want to determine the
        // properties that instances of Quokka must have
        OWLClass quokka = man.getOWLDataFactory().getOWLClass(prefix, "Quokka");
        // printProperties(man, ont, reasoner, quokka);
        // We can also ask if the instances of a class must have a property
        OWLClass koala = man.getOWLDataFactory().getOWLClass(prefix, "KoalaWithPhD");
        OWLObjectProperty hasDegree = man.getOWLDataFactory().getOWLObjectProperty(prefix + "hasDegree");
        if (hasProperty(man, reasoner, koala, hasDegree)) {
            // System.out.println("Instances of " + koala
            // + " have a degree");
        }
    }

    private static boolean hasProperty(OWLOntologyManager man, OWLReasoner reasoner, OWLClass cls,
        OWLObjectPropertyExpression prop) {
        // To test whether the instances of a class must have a property we
        // create a some values from restriction and then ask for the
        // satisfiability of the class interesected with the complement of this
        // some values from restriction. If the intersection is satisfiable then
        // the instances of the class don't have to have the property,
        // otherwise, they do.
        OWLDataFactory dataFactory = man.getOWLDataFactory();
        OWLClassExpression restriction = dataFactory.getOWLObjectSomeValuesFrom(prop, dataFactory.getOWLThing());
        // Now we see if the intersection of the class and the complement of
        // this restriction is satisfiable
        OWLClassExpression complement = dataFactory.getOWLObjectComplementOf(restriction);
        OWLClassExpression intersection = dataFactory.getOWLObjectIntersectionOf(cls, complement);
        return !reasoner.isSatisfiable(intersection);
    }

    /**
     * This example shows how to use IRI mappers to redirect imports and
     * loading.
     * 
     * @throws Exception
     *         exception
     */
    @Ignore("This test is ignored. We do not want to fetch stuff from the network just to run a unit test for an example")
    @Test
    public void shouldUseIRIMappers() throws Exception {
        IRI mgedOntologyIri = IRI.create("http://mged.sourceforge.net/ontologies/MGEDOntology.owl");
        IRI protegeOntologyIri = IRI.create("http://protege.stanford.edu/plugins/owl/protege");
        IRI tonesRepositoryIri = IRI.create("http://owl.cs.manchester.ac.uk/repository/download");
        // Create a manager to work with
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        // Load the MGED ontology. There is a copy of the MGED ontology located
        // at the address pointed to by its ontology IRI (this is good practice
        // and is recommended in the OWL 2 spec).
        OWLOntology ontology = manager.loadOntology(mgedOntologyIri);
        // Print out the ontology IRI and its imported ontology IRIs
        printOntologyAndImports(manager, ontology);
        // We'll load the MGED ontology again, but this time, we'll get the
        // Protege ontology (that it imports) from the TONES repository. To tell
        // the ontology manager to do this we need to add an IRI mapper. We need
        // an implementation of OWLOntologyIRIMapper. Given and IRI and
        // OWLOntologyIRIMapper simply returns some other IRI. There are quite a
        // few implementations of IRI mapper in the OWL API, here we will just
        // use a really basic implementation that maps a specific IRI to another
        // specific IRI. Create a mapper that maps the Protege ontology IRI to
        // the document IRI that points to a copy in the TONES ontology
        // repository.
        IRI protegeOntologyDocumentIRI = getTONESRepositoryDocumentIRI(protegeOntologyIri, tonesRepositoryIri);
        OWLOntologyIRIMapper iriMapper = new SimpleIRIMapper(protegeOntologyIri, protegeOntologyDocumentIRI);
        // Create a new manager that we will use to load the MGED ontology
        OWLOntologyManager manager2 = OWLManager.createOWLOntologyManager();
        // Register our mapper with the manager
        manager2.getIRIMappers().add(iriMapper);
        // Now load our MGED ontology
        OWLOntology ontology2 = load(manager2);
        // Print out the details
        printOntologyAndImports(manager2, ontology2);
        // Notice that the document IRI of the protege ontology is different to
        // the document IRI of the ontology when it was loaded the first time.
        // This is due to the mapper redirecting the ontology loader. For
        // example, AutoIRIMapper: An AutoIRIMapper finds ontologies in a local
        // folder and maps their IRIs to their locations in this folder We
        // specify a directory/folder where the ontologies are located. In this
        // case we've just specified the tmp directory.
        @Nonnull File file = folder.newFolder();
        // We can also specify a flag to indicate whether the directory should
        // be searched recursively.
        OWLOntologyIRIMapper autoIRIMapper = new AutoIRIMapper(file, false);
        // We can now use this mapper in the usual way, i.e.
        manager2.getIRIMappers().add(autoIRIMapper);
        // Of course, applications (such as Protege) usually implement their own
        // mappers to deal with specific application requirements.
    }

    private static void printOntologyAndImports(OWLOntologyManager manager, OWLOntology ontology) {
        // Print ontology IRI and where it was loaded from (they will be the
        // same)
        printOntology(manager, ontology);
        // List the imported ontologies
        ontology.imports().forEach(o -> printOntology(manager, o));
    }

    /**
     * Prints the IRI of an ontology and its document IRI.
     * 
     * @param manager
     *        The manager that manages the ontology
     * @param ontology
     *        The ontology
     */
    private static void printOntology(OWLOntologyManager manager, OWLOntology ontology) {
        IRI ontologyIRI = ontology.getOntologyID().getOntologyIRI().get();
        IRI documentIRI = manager.getOntologyDocumentIRI(ontology);
        // System.out.println(ontologyIRI == null ? "anonymous" : ontologyIRI
        // .toQuotedString());
        // System.out.println(" from " + documentIRI.toQuotedString());
    }

    /**
     * Convenience method that obtains the document IRI of an ontology contained
     * in the TONES ontology repository given the ontology IRI. The TONES
     * repository contains various ontologies of interest to reasoner developers
     * and tools developers. Ontologies in the repository may be accessed in a
     * RESTful way (see http://owl.cs.manchester.ac.uk/repository/) for more
     * details). We basically get an ontology by specifying the repository IRI
     * with an ontology query parameter that has the ontology IRI that we're
     * after as its value.
     * 
     * @param ontologyIRI
     *        The IRI of the ontology.
     * @param tones
     *        tones iri
     * @return The document IRI of the ontology in the TONES repository.
     */
    private static IRI getTONESRepositoryDocumentIRI(IRI ontologyIRI, IRI tones) {
        return IRI.create(tones + "?ontology=" + ontologyIRI);
    }

    /**
     * This example shows how to extract modules.
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldExtractModules() throws Exception {
        // Create our manager
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        // Load the Koala ontology
        OWLOntology ont = load(man);
        // We want to extract a module for all toppings. We therefore have to
        // generate a seed signature that contains "Quokka" and its
        // subclasses. We start by creating a signature that consists of
        // "Quokka".
        OWLClass toppingCls = df.getOWLClass(ont.getOntologyID().getOntologyIRI().get() + "#Quokka");
        Set<OWLEntity> sig = new HashSet<OWLEntity>();
        sig.add(toppingCls);
        // We now add all subclasses (direct and indirect) of the chosen
        // classes. Ideally, it should be done using a DL reasoner, in order to
        // take inferred subclass relations into account. We are using the
        // structural reasoner of the OWL API for simplicity.
        Set<OWLEntity> seedSig = new HashSet<OWLEntity>();
        OWLReasoner reasoner = new StructuralReasoner(ont, new SimpleConfiguration(), BufferingMode.NON_BUFFERING);
        for (OWLEntity ent : sig) {
            seedSig.add(ent);
            if (OWLClass.class.isAssignableFrom(ent.getClass())) {
                NodeSet<OWLClass> subClasses = reasoner.getSubClasses((OWLClass) ent, false);
                seedSig.addAll(asList(subClasses.entities()));
            }
        }
        // We now extract a locality-based module. For most reuse purposes, the
        // module type should be STAR -- this yields the smallest possible
        // locality-based module. These modules guarantee that all entailments
        // of the original ontology that can be formulated using only terms from
        // the seed signature or the module will also be entailments of the
        // module. In easier words, the module preserves all knowledge of the
        // ontology about the terms in the seed signature or the module.
        SyntacticLocalityModuleExtractor sme = new SyntacticLocalityModuleExtractor(man, ont, ModuleType.STAR);
        IRI moduleIRI = IRI.create("urn:test:QuokkaModule.owl");
        OWLOntology mod = sme.extractAsOntology(seedSig, moduleIRI);
        // The module can now be saved as usual
    }

    /**
     * This example shows how to extract modules.
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void shouldExtractADModules() throws Exception {
        // Create our manager
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        // Load the Koala ontology
        OWLOntology ont = load(man);
        // We want to extract a module for all toppings. We therefore have to
        // generate a seed signature that contains "Quokka" and its
        // subclasses. We start by creating a signature that consists of
        // "Quokka".
        OWLClass toppingCls = df.getOWLClass(ont.getOntologyID().getOntologyIRI().get() + "#Quokka");
        Set<OWLEntity> sig = new HashSet<OWLEntity>();
        sig.add(toppingCls);
        // We now add all subclasses (direct and indirect) of the chosen
        // classes. Ideally, it should be done using a DL reasoner, in order to
        // take inferred subclass relations into account. We are using the
        // structural reasoner of the OWL API for simplicity.
        Set<OWLEntity> seedSig = new HashSet<OWLEntity>();
        OWLReasoner reasoner = new StructuralReasoner(ont, new SimpleConfiguration(), BufferingMode.NON_BUFFERING);
        for (OWLEntity ent : sig) {
            seedSig.add(ent);
            if (OWLClass.class.isAssignableFrom(ent.getClass())) {
                NodeSet<OWLClass> subClasses = reasoner.getSubClasses((OWLClass) ent, false);
                seedSig.addAll(asList(subClasses.entities()));
            }
        }
        // We now extract a locality-based module. For most reuse purposes, the
        // module type should be STAR -- this yields the smallest possible
        // locality-based module. These modules guarantee that all entailments
        // of the original ontology that can be formulated using only terms from
        // the seed signature or the module will also be entailments of the
        // module. In easier words, the module preserves all knowledge of the
        // ontology about the terms in the seed signature or the module.
        SyntacticLocalityModuleExtractor sme = new SyntacticLocalityModuleExtractor(man, ont, ModuleType.STAR);
        IRI moduleIRI = IRI.create("urn:test:QuokkaModule.owl");
        OWLOntology mod = sme.extractAsOntology(seedSig, moduleIRI);
        // The module can now be saved as usual
    }

    /**
     * The following example uses entities and axioms that are used in the OWL
     * Primer. The purpose of this example is to illustrate some of the methods
     * of creating class expressions and various types of axioms. Typically, an
     * ontology wouldn't be constructed programmatically in a long drawn out
     * fashion like this, it would be constructe in an ontology editor such as
     * Protege 4, or Swoop. The OWL API would then be used to examine the
     * asserted structure of the ontology, and in conjunction with an OWL
     * reasoner such as FaCT++ or Pellet used to query the inferred ontology.
     * 
     * @throws Exception
     *         exception
     */
    @Test
    public void owlPrimer() throws Exception {
        // The OWLOntologyManager is at the heart of the OWL API, we can create
        // an instance of this using the OWLManager class, which will set up
        // commonly used options (such as which parsers are registered etc.
        // etc.)
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        // We want to create an ontology that corresponds to the ontology used
        // in the OWL Primer. Every ontology has a IRI that uniquely identifies
        // the ontology. The IRI is essentially a name for the ontology. Note
        // that the IRI doesn't necessarily point to a location on the web - in
        // this example, we won't publish the ontology at the URL corresponding
        // to the ontology IRI below.
        IRI ontologyIRI = IRI.create("http://example.com/owlapi/families");
        // Now that we have a IRI for out ontology, we can create the actual
        // ontology. Note that the create ontology method throws an
        // OWLOntologyCreationException if there was a problem creating the
        // ontology.
        OWLOntology ont = manager.createOntology(ontologyIRI);
        // We can use the manager to get a reference to an OWLDataFactory. The
        // data factory provides a point for creating OWL API objects such as
        // classes, properties and individuals.
        OWLDataFactory factory = manager.getOWLDataFactory();
        // We first need to create some references to individuals. All of our
        // individual must have IRIs. A common convention is to take the IRI of
        // an ontology, append a # and then a local name. For example we can
        // create the individual 'John', using the ontology IRI and appending
        // #John. Note however, that there is no reuqirement that a IRI of a
        // class, property or individual that is used in an ontology have a
        // correspondance with the IRI of the ontology.
        OWLIndividual john = factory.getOWLNamedIndividual(ontologyIRI + "#John");
        OWLIndividual mary = factory.getOWLNamedIndividual(ontologyIRI + "#Mary");
        OWLIndividual susan = factory.getOWLNamedIndividual(ontologyIRI + "#Susan");
        OWLIndividual bill = factory.getOWLNamedIndividual(ontologyIRI + "#Bill");
        // The ontologies that we created aren't contained in any ontology at
        // the moment. Individuals (or classes or properties) can't directly be
        // added to an ontology, they have to be used in axioms, and then the
        // axioms are added to an ontology. We now want to add some facts to the
        // ontology. These facts are otherwise known as property assertions. In
        // our case, we want to say that John has a wife Mary. To do this we
        // need to have a reference to the hasWife object property (object
        // properties link an individual to an individual, and data properties
        // link and individual to a constant - here, we need an object property
        // because John and Mary are individuals).
        OWLObjectProperty hasWife = factory.getOWLObjectProperty(ontologyIRI + "#hasWife");
        // Now we need to create the assertion that John hasWife Mary. To do
        // this we need an axiom, in this case an object property assertion
        // axiom. This can be thought of as a "triple" that has a subject, john,
        // a predicate, hasWife and an object Mary
        OWLObjectPropertyAssertionAxiom axiom1 = factory.getOWLObjectPropertyAssertionAxiom(hasWife, john, mary);
        // We now need to add this assertion to our ontology. To do this, we
        // apply an ontology change to the ontology via the OWLOntologyManager.
        // First we create the change object that will tell the manager that we
        // want to add the axiom to the ontology
        AddAxiom addAxiom1 = new AddAxiom(ont, axiom1);
        // Now we apply the change using the manager.
        manager.applyChange(addAxiom1);
        // Now we want to add the other facts/assertions to the ontology John
        // hasSon Bill Get a refernece to the hasSon property
        OWLObjectProperty hasSon = factory.getOWLObjectProperty(ontologyIRI + "#hasSon");
        // Create the assertion, John hasSon Bill
        OWLAxiom axiom2 = factory.getOWLObjectPropertyAssertionAxiom(hasSon, john, bill);
        // Apply the change
        manager.applyChange(new AddAxiom(ont, axiom2));
        // John hasDaughter Susan
        OWLObjectProperty hasDaughter = factory.getOWLObjectProperty(ontologyIRI + "#hasDaughter");
        OWLAxiom axiom3 = factory.getOWLObjectPropertyAssertionAxiom(hasDaughter, john, susan);
        manager.applyChange(new AddAxiom(ont, axiom3));
        // John hasAge 33 In this case, hasAge is a data property, which we need
        // a reference to
        OWLDataProperty hasAge = factory.getOWLDataProperty(ontologyIRI + "#hasAge");
        // We create a data property assertion instead of an object property
        // assertion
        OWLAxiom axiom4 = factory.getOWLDataPropertyAssertionAxiom(hasAge, john, 33);
        manager.applyChange(new AddAxiom(ont, axiom4));
        // In the above code, 33 is an integer, so we can just pass 33 into the
        // data factory method. Behind the scenes the OWL API will create a
        // typed constant that it will use as the value of the data property
        // assertion. We could have manually created the constant as follows:
        OWLDatatype intDatatype = factory.getIntegerOWLDatatype();
        OWLLiteral thirtyThree = factory.getOWLLiteral("33", intDatatype);
        // We would then create the axiom as follows:
        factory.getOWLDataPropertyAssertionAxiom(hasAge, john, thirtyThree);
        // However, the convenice method is much shorter! We can now create the
        // other facts/assertion for Mary. The OWL API uses a change object
        // model, which means we can stack up changes (or sets of axioms) and
        // apply the changes (or add the axioms) in one go. We will do this for
        // Mary
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(factory.getOWLObjectPropertyAssertionAxiom(hasSon, mary, bill));
        axioms.add(factory.getOWLObjectPropertyAssertionAxiom(hasDaughter, mary, susan));
        axioms.add(factory.getOWLDataPropertyAssertionAxiom(hasAge, mary, 31));
        // Add facts/assertions for Bill and Susan
        axioms.add(factory.getOWLDataPropertyAssertionAxiom(hasAge, bill, 13));
        axioms.add(factory.getOWLDataPropertyAssertionAxiom(hasAge, mary, 8));
        // Now add all the axioms in one go - there is a convenience method on
        // OWLOntologyManager that will automatically generate the AddAxiom
        // change objects for us. We need to specify the ontology that the
        // axioms should be added to and the axioms to add.
        ont.add(axioms);
        // Now specify the genders of John, Mary, Bill and Susan. To do this we
        // need the male and female individuals and the hasGender object
        // property.
        OWLIndividual male = factory.getOWLNamedIndividual(ontologyIRI + "#male");
        OWLIndividual female = factory.getOWLNamedIndividual(ontologyIRI + "#female");
        OWLObjectProperty hasGender = factory.getOWLObjectProperty(ontologyIRI + "#hasGender");
        Set<OWLAxiom> genders = new HashSet<OWLAxiom>();
        genders.add(factory.getOWLObjectPropertyAssertionAxiom(hasGender, john, male));
        genders.add(factory.getOWLObjectPropertyAssertionAxiom(hasGender, mary, female));
        genders.add(factory.getOWLObjectPropertyAssertionAxiom(hasGender, bill, male));
        genders.add(factory.getOWLObjectPropertyAssertionAxiom(hasGender, susan, female));
        // Add the facts about the genders
        ont.add(genders);
        // Domain and Range Axioms //At this point, we have an ontology
        // containing facts about several individuals. We now want to specify
        // more information about the various properties that we have used. We
        // want to say that the domains and ranges of hasWife, hasSon and
        // hasDaughter are the class Person. To do this we need various domain
        // and range axioms, and we need a reference to the class Person First
        // get a reference to the person class
        OWLClass person = factory.getOWLClass(ontologyIRI + "#Person");
        // Now we add the domain and range axioms that specify the domains and
        // ranges of the various properties that we are interested in.
        Set<OWLAxiom> domainsAndRanges = new HashSet<OWLAxiom>();
        // Domain and then range of hasWife
        domainsAndRanges.add(factory.getOWLObjectPropertyDomainAxiom(hasWife, person));
        domainsAndRanges.add(factory.getOWLObjectPropertyRangeAxiom(hasWife, person));
        // Domain and range of hasSon and also hasDaugher
        domainsAndRanges.add(factory.getOWLObjectPropertyDomainAxiom(hasSon, person));
        domainsAndRanges.add(factory.getOWLObjectPropertyRangeAxiom(hasSon, person));
        domainsAndRanges.add(factory.getOWLObjectPropertyDomainAxiom(hasDaughter, person));
        domainsAndRanges.add(factory.getOWLObjectPropertyRangeAxiom(hasDaughter, person));
        // We also have the domain of the data property hasAge as Person, and
        // the range as integer. We need the integer datatype. The XML Schema
        // Datatype IRIs are used for data types. The OWL API provide a built in
        // set via the XSDVocabulary enum.
        domainsAndRanges.add(factory.getOWLDataPropertyDomainAxiom(hasAge, person));
        OWLDatatype integerDatatype = factory.getIntegerOWLDatatype();
        domainsAndRanges.add(factory.getOWLDataPropertyRangeAxiom(hasAge, integerDatatype));
        // Now add all of our domain and range axioms
        ont.add(domainsAndRanges);
        // Class assertion axioms //We can also explicitly say than an
        // individual is an instance of a given class. To do this we use a Class
        // assertion axiom.
        OWLClassAssertionAxiom classAssertionAx = factory.getOWLClassAssertionAxiom(person, john);
        // Add the axiom directly using the addAxiom convenience method on
        // OWLOntologyManager
        manager.addAxiom(ont, classAssertionAx);
        // Inverse property axioms //We can specify the inverse property of
        // hasWife as hasHusband We first need a reference to the hasHusband
        // property.
        OWLObjectProperty hasHusband = factory
            .getOWLObjectProperty(ont.getOntologyID().getOntologyIRI().get() + "#hasHusband");
        // The full IRI of the hasHusband property will be
        // http://example.com/owlapi/families#hasHusband since the IRI of our
        // ontology is http://example.com/owlapi/families Create the inverse
        // object properties axiom and add it
        manager.addAxiom(ont, factory.getOWLInverseObjectPropertiesAxiom(hasWife, hasHusband));
        // Sub property axioms //OWL allows a property hierarchy to be
        // specified. Here, hasSon and hasDaughter will be specified as
        // hasChild.
        OWLObjectProperty hasChild = factory
            .getOWLObjectProperty(ont.getOntologyID().getOntologyIRI().get() + "#hasChild");
        OWLSubObjectPropertyOfAxiom hasSonSubHasChildAx = factory.getOWLSubObjectPropertyOfAxiom(hasSon, hasChild);
        // Add the axiom
        manager.addAxiom(ont, hasSonSubHasChildAx);
        // And hasDaughter, which is also a sub property of hasChild
        manager.addAxiom(ont, factory.getOWLSubObjectPropertyOfAxiom(hasDaughter, hasChild));
        // Property characteristics //Next, we want to say that the hasAge
        // property is Functional. This means that something can have at most
        // one hasAge property. We can do this with a functional data property
        // axiom First create the axiom
        OWLFunctionalDataPropertyAxiom hasAgeFuncAx = factory.getOWLFunctionalDataPropertyAxiom(hasAge);
        // Now add it to the ontology
        manager.addAxiom(ont, hasAgeFuncAx);
        // The hasWife property should be Functional, InverseFunctional,
        // Irreflexive and Asymmetric. Note that the asymmetric property axiom
        // used to be called antisymmetric - older versions of the OWL API may
        // refer to antisymmetric property axioms
        Set<OWLAxiom> hasWifeAxioms = new HashSet<OWLAxiom>();
        hasWifeAxioms.add(factory.getOWLFunctionalObjectPropertyAxiom(hasWife));
        hasWifeAxioms.add(factory.getOWLInverseFunctionalObjectPropertyAxiom(hasWife));
        hasWifeAxioms.add(factory.getOWLIrreflexiveObjectPropertyAxiom(hasWife));
        hasWifeAxioms.add(factory.getOWLAsymmetricObjectPropertyAxiom(hasWife));
        // Add all of the axioms that specify the characteristics of hasWife
        ont.add(hasWifeAxioms);
        // SubClass axioms //Now we want to start specifying something about
        // classes in our ontology. To begin with we will simply say something
        // about the relationship between named classes Besides the Person class
        // that we already have, we want to say something about the classes Man,
        // Woman and Parent. To say something about these classes, as usual, we
        // need references to them:
        OWLClass man = factory.getOWLClass(ontologyIRI + "#Man");
        OWLClass woman = factory.getOWLClass(ontologyIRI + "#Woman");
        OWLClass parent = factory.getOWLClass(ontologyIRI + "#Parent");
        // It is important to realise that simply getting references to a class
        // via the data factory does not add them to an ontology - only axioms
        // can be added to an ontology. Now say that Man, Woman and Parent are
        // subclasses of Person
        manager.addAxiom(ont, factory.getOWLSubClassOfAxiom(man, person));
        manager.addAxiom(ont, factory.getOWLSubClassOfAxiom(woman, person));
        manager.addAxiom(ont, factory.getOWLSubClassOfAxiom(parent, person));
        // Restrictions //Now we want to say that Person has exactly 1 Age,
        // exactly 1 Gender and, only has gender that is male or female. We will
        // deal with these restrictions one by one and then combine them as a
        // superclass (Necessary conditions) of Person. All anonymous class
        // expressions extend OWLClassExpression. First, hasAge exactly 1
        OWLDataExactCardinality hasAgeRestriction = factory.getOWLDataExactCardinality(1, hasAge);
        // Now the hasGender exactly 1
        OWLObjectExactCardinality hasGenderRestriction = factory.getOWLObjectExactCardinality(1, hasGender);
        // And finally, the hasGender only {male female} To create this
        // restriction, we need an OWLObjectOneOf class expression since male
        // and female are individuals We can just list as many individuals as we
        // need as the argument of the method.
        OWLObjectOneOf maleOrFemale = factory.getOWLObjectOneOf(male, female);
        // Now create the actual restriction
        OWLObjectAllValuesFrom hasGenderOnlyMaleFemale = factory.getOWLObjectAllValuesFrom(hasGender, maleOrFemale);
        // Finally, we bundle these restrictions up into an intersection, since
        // we want person to be a subclass of the intersection of them
        OWLObjectIntersectionOf intersection = factory.getOWLObjectIntersectionOf(hasAgeRestriction,
            hasGenderRestriction, hasGenderOnlyMaleFemale);
        // And now we set this anonymous intersection class to be a superclass
        // of Person using a subclass axiom
        manager.addAxiom(ont, factory.getOWLSubClassOfAxiom(person, intersection));
        // Restrictions and other anonymous classes can also be used anywhere a
        // named class can be used. Let's set the range of hasSon to be Person
        // and hasGender value male. This requires an anonymous class that is
        // the intersection of Person, and also, hasGender value male. We need
        // to create the hasGender value male restriction - this describes the
        // class of things that have a hasGender relationship to the individual
        // male.
        OWLObjectHasValue hasGenderValueMaleRestriction = factory.getOWLObjectHasValue(hasGender, male);
        // Now combine this with Person in an intersection
        OWLClassExpression personAndHasGenderValueMale = factory.getOWLObjectIntersectionOf(person,
            hasGenderValueMaleRestriction);
        // Now specify this anonymous class as the range of hasSon using an
        // object property range axioms
        manager.addAxiom(ont, factory.getOWLObjectPropertyRangeAxiom(hasSon, personAndHasGenderValueMale));
        // We can do a similar thing for hasDaughter, by specifying that
        // hasDaughter has a range of Person and hasGender value female. This
        // time, we will make things a little more compact by not using so many
        // variables
        OWLClassExpression rangeOfHasDaughter = factory.getOWLObjectIntersectionOf(person,
            factory.getOWLObjectHasValue(hasGender, female));
        manager.addAxiom(ont, factory.getOWLObjectPropertyRangeAxiom(hasDaughter, rangeOfHasDaughter));
        // Data Ranges and Equivalent Classes axioms //In OWL 2, we can specify
        // expressive data ranges. Here, we will specify the classes Teenage,
        // Adult and Child by saying something about individuals ages. First we
        // take the class Teenager, all of whose instance have an age greater or
        // equal to 13 and less than 20. In Manchester Syntax this is written as
        // Person and hasAge some int[>=13, <20] We create a data range by
        // taking the integer datatype and applying facet restrictions to it.
        // Note that we have statically imported the data range facet vocabulary
        // OWLFacet
        OWLFacetRestriction geq13 = factory.getOWLFacetRestriction(MIN_INCLUSIVE, factory.getOWLLiteral(13));
        // We don't have to explicitly create the typed constant, there are
        // convenience methods to do this
        OWLFacetRestriction lt20 = factory.getOWLFacetRestriction(MAX_EXCLUSIVE, 20);
        // Restrict the base type, integer (which is just an XML Schema
        // Datatype) with the facet restrictions.
        OWLDataRange dataRng = factory.getOWLDatatypeRestriction(integerDatatype, geq13, lt20);
        // Now we have the data range of greater than equal to 13 and less than
        // 20 we can use this in a restriction.
        OWLDataSomeValuesFrom teenagerAgeRestriction = factory.getOWLDataSomeValuesFrom(hasAge, dataRng);
        // Now make Teenager equivalent to Person and hasAge some int[>=13, <20]
        // First create the class Person and hasAge some int[>=13, <20]
        OWLClassExpression teenagePerson = factory.getOWLObjectIntersectionOf(person, teenagerAgeRestriction);
        OWLClass teenager = factory.getOWLClass(ontologyIRI + "#Teenager");
        OWLEquivalentClassesAxiom teenagerDefinition = factory.getOWLEquivalentClassesAxiom(teenager, teenagePerson);
        manager.addAxiom(ont, teenagerDefinition);
        // Do the same for Adult that has an age greater than 21
        OWLDataRange geq21 = factory.getOWLDatatypeRestriction(integerDatatype,
            factory.getOWLFacetRestriction(MIN_INCLUSIVE, 21));
        OWLClass adult = factory.getOWLClass(ontologyIRI + "#Adult");
        OWLClassExpression adultAgeRestriction = factory.getOWLDataSomeValuesFrom(hasAge, geq21);
        OWLClassExpression adultPerson = factory.getOWLObjectIntersectionOf(person, adultAgeRestriction);
        OWLAxiom adultDefinition = factory.getOWLEquivalentClassesAxiom(adult, adultPerson);
        manager.addAxiom(ont, adultDefinition);
        // And finally Child
        OWLDataRange notGeq21 = factory.getOWLDataComplementOf(geq21);
        OWLClass child = factory.getOWLClass(ontologyIRI + "#Child");
        OWLClassExpression childAgeRestriction = factory.getOWLDataSomeValuesFrom(hasAge, notGeq21);
        OWLClassExpression childPerson = factory.getOWLObjectIntersectionOf(person, childAgeRestriction);
        OWLAxiom childDefinition = factory.getOWLEquivalentClassesAxiom(child, childPerson);
        manager.addAxiom(ont, childDefinition);
        // Different individuals //In OWL, we can say that individuals are
        // different from each other. To do this we use a different individuals
        // axiom. Since John, Mary, Bill and Susan are all different
        // individuals, we can express this using a different individuals axiom.
        OWLDifferentIndividualsAxiom diffInds = factory.getOWLDifferentIndividualsAxiom(john, mary, bill, susan);
        manager.addAxiom(ont, diffInds);
        // Male and Female are also different
        manager.addAxiom(ont, factory.getOWLDifferentIndividualsAxiom(male, female));
        // Disjoint classes //Two say that two classes do not have any instances
        // in common we use a disjoint classes axiom:
        OWLDisjointClassesAxiom disjointClassesAxiom = factory.getOWLDisjointClassesAxiom(man, woman);
        manager.addAxiom(ont, disjointClassesAxiom);
        // Ontology Management //Having added axioms to out ontology we can now
        // save it (in a variety of formats). RDF/XML is the default format
        // System.out.println("RDF/XML: ");
        manager.saveOntology(ont, new StringDocumentTarget());
        // OWL/XML
        // System.out.println("OWL/XML: ");
        manager.saveOntology(ont, new OWLXMLDocumentFormat(), new StringDocumentTarget());
        // Manchester Syntax
        // System.out.println("Manchester syntax: ");
        manager.saveOntology(ont, new ManchesterSyntaxDocumentFormat(), new StringDocumentTarget());
        // Turtle
        // System.out.println("Turtle: ");
        manager.saveOntology(ont, new TurtleDocumentFormat(), new StringDocumentTarget());
    }
}
