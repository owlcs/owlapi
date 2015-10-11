package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.AnonymousIndividualProperties;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class OutputSyntaxSortTestCase extends TestBase {

    String[] input = new String[] { "Prefix(:=<http://www.co-ode.org/ontologies/pizza/pizza.owl#>)\n" +
        "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n" +
        "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n" +
        "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n" +
        "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n" +
        "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n" +
        "Ontology(<http://www.co-ode.org/ontologies/pizza/pizza.owl>\n" +
        "Annotation(rdfs:comment \"An example ontology\"@en)\n" +
        "Annotation(owl:versionInfo \"v.1.4.\"@en)\n" +
        "Annotation(owl:versionInfo \"v.1.5. Removed protege\"@en)\n" +
        "Annotation(owl:versionInfo \"version 1.5\"^^xsd:string)\n" +
        "Declaration(Class(:American))\n" +
        "Declaration(Class(:AmericanHot))\nDeclaration(Class(:AnchoviesTopping))\nDeclaration(Class(:ArtichokeTopping))\nDeclaration(Class(:AsparagusTopping))\nDeclaration(Class(:Cajun))\nDeclaration(Class(:CajunSpiceTopping))\nDeclaration(Class(:CaperTopping))\nDeclaration(Class(:Capricciosa))\nDeclaration(Class(:Caprina))\nDeclaration(Class(:CheeseTopping))\nDeclaration(Class(:CheeseyPizza))\nDeclaration(Class(:CheeseyVegetableTopping))\nDeclaration(Class(:ChickenTopping))\nDeclaration(Class(:Country))\nDeclaration(Class(:DeepPanBase))\nDeclaration(Class(:DomainConcept))\nDeclaration(Class(:Fiorentina))\nDeclaration(Class(:FishTopping))\nDeclaration(Class(:Food))\nDeclaration(Class(:FourCheesesTopping))\nDeclaration(Class(:FourSeasons))\nDeclaration(Class(:FruitTopping))\nDeclaration(Class(:FruttiDiMare))\nDeclaration(Class(:GarlicTopping))\nDeclaration(Class(:Giardiniera))\nDeclaration(Class(:GoatsCheeseTopping))\nDeclaration(Class(:GorgonzolaTopping))\nDeclaration(Class(:GreenPepperTopping))\nDeclaration(Class(:HamTopping))\nDeclaration(Class(:HerbSpiceTopping))\nDeclaration(Class(:Hot))\nDeclaration(Class(:HotGreenPepperTopping))\nDeclaration(Class(:HotSpicedBeefTopping))\nDeclaration(Class(:IceCream))\nDeclaration(Class(:InterestingPizza))\nDeclaration(Class(:JalapenoPepperTopping))\nDeclaration(Class(:LaReine))\nDeclaration(Class(:LeekTopping))\nDeclaration(Class(:Margherita))\nDeclaration(Class(:MeatTopping))\nDeclaration(Class(:MeatyPizza))\nDeclaration(Class(:Medium))\nDeclaration(Class(:Mild))\nDeclaration(Class(:MixedSeafoodTopping))\nDeclaration(Class(:MozzarellaTopping))\nDeclaration(Class(:Mushroom))\nDeclaration(Class(:MushroomTopping))\nDeclaration(Class(:NamedPizza))\nDeclaration(Class(:Napoletana))\nDeclaration(Class(:NonVegetarianPizza))\nDeclaration(Class(:NutTopping))\nDeclaration(Class(:OliveTopping))\nDeclaration(Class(:OnionTopping))\nDeclaration(Class(:ParmaHamTopping))\nDeclaration(Class(:Parmense))\nDeclaration(Class(:ParmesanTopping))\nDeclaration(Class(:PeperonataTopping))\nDeclaration(Class(:PeperoniSausageTopping))\nDeclaration(Class(:PepperTopping))\nDeclaration(Class(:PetitPoisTopping))\nDeclaration(Class(:PineKernels))\nDeclaration(Class(:Pizza))\nDeclaration(Class(:PizzaBase))\nDeclaration(Class(:PizzaTopping))\nDeclaration(Class(:PolloAdAstra))\nDeclaration(Class(:PrawnsTopping))\nDeclaration(Class(:PrinceCarlo))\nDeclaration(Class(:QuattroFormaggi))\nDeclaration(Class(:RealItalianPizza))\nDeclaration(Class(:RedOnionTopping))\nDeclaration(Class(:RocketTopping))\nDeclaration(Class(:Rosa))\nDeclaration(Class(:RosemaryTopping))\nDeclaration(Class(:SauceTopping))\nDeclaration(Class(:Siciliana))\nDeclaration(Class(:SlicedTomatoTopping))\nDeclaration(Class(:SloppyGiuseppe))\nDeclaration(Class(:Soho))\nDeclaration(Class(:Spiciness))\nDeclaration(Class(:SpicyPizza))\nDeclaration(Class(:SpicyPizzaEquivalent))\nDeclaration(Class(:SpicyTopping))\nDeclaration(Class(:SpinachTopping))\nDeclaration(Class(:SultanaTopping))\nDeclaration(Class(:SundriedTomatoTopping))\nDeclaration(Class(:SweetPepperTopping))\nDeclaration(Class(:ThinAndCrispyBase))\nDeclaration(Class(:ThinAndCrispyPizza))\nDeclaration(Class(:TobascoPepperSauce))\nDeclaration(Class(:TomatoTopping))\nDeclaration(Class(:UnclosedPizza))\nDeclaration(Class(:ValuePartition))\nDeclaration(Class(:VegetableTopping))\nDeclaration(Class(:VegetarianPizza))\nDeclaration(Class(:VegetarianPizzaEquivalent1))\nDeclaration(Class(:VegetarianPizzaEquivalent2))\nDeclaration(Class(:VegetarianTopping))\nDeclaration(Class(:Veneziana))\nDeclaration(Class(<urn:classexpression>))\nDeclaration(Class(<urn:iri>))\nDeclaration(ObjectProperty(:hasBase))\nDeclaration(ObjectProperty(:hasCountryOfOrigin))\nDeclaration(ObjectProperty(:hasIngredient))\nDeclaration(ObjectProperty(:hasSpiciness))\nDeclaration(ObjectProperty(:hasTopping))\nDeclaration(ObjectProperty(:isBaseOf))\nDeclaration(ObjectProperty(:isIngredientOf))\nDeclaration(ObjectProperty(:isToppingOf))\nDeclaration(ObjectProperty(<urn:op>))\n"
        + "Declaration(DataProperty(<urn:dp>))\n"
        + "Declaration(DataProperty(<urn:testdp>))\n"
        + "Declaration(NamedIndividual(:America))\nDeclaration(NamedIndividual(:England))\nDeclaration(NamedIndividual(:France))\nDeclaration(NamedIndividual(:Germany))\nDeclaration(NamedIndividual(:Italy))\nDeclaration(NamedIndividual(<urn:iri>))\nDeclaration(AnnotationProperty(<urn:ap>))\n"
        +
        "SubAnnotationPropertyOf(<urn:ap> <urn:ap>)\n" +
        "AnnotationPropertyRange(<urn:ap> <urn:iri>)\n" +
        "AnnotationPropertyDomain(<urn:ap> <urn:iri>)\n" +
        "SubObjectPropertyOf(:hasBase :hasIngredient)\n" +
        "InverseObjectProperties(:hasBase :isBaseOf)\n" +
        "FunctionalObjectProperty(:hasBase)\n" +
        "InverseFunctionalObjectProperty(:hasBase)\n" +
        "ObjectPropertyDomain(:hasBase :Pizza)\n" +
        "ObjectPropertyRange(:hasBase :PizzaBase)\n" +
        "InverseObjectProperties(:hasIngredient :isIngredientOf)\n" +
        "TransitiveObjectProperty(:hasIngredient)\n" +
        "ObjectPropertyDomain(:hasIngredient :Food)\n" +
        "ObjectPropertyRange(:hasIngredient :Food)\n" +
        "FunctionalObjectProperty(:hasSpiciness)\n" +
        "ObjectPropertyRange(:hasSpiciness :Spiciness)\n" +
        "SubObjectPropertyOf(:hasTopping :hasIngredient)\n" +
        "InverseObjectProperties(:hasTopping :isToppingOf)\n" +
        "InverseFunctionalObjectProperty(:hasTopping)\n" +
        "ObjectPropertyDomain(:hasTopping :Pizza)\n" +
        "ObjectPropertyRange(:hasTopping :PizzaTopping)\n" +
        "SubObjectPropertyOf(:isBaseOf :isIngredientOf)\n" +
        "FunctionalObjectProperty(:isBaseOf)\n" +
        "InverseFunctionalObjectProperty(:isBaseOf)\n" +
        "ObjectPropertyDomain(:isBaseOf :PizzaBase)\n" +
        "ObjectPropertyRange(:isBaseOf :Pizza)\n" +
        "TransitiveObjectProperty(:isIngredientOf)\n" +
        "ObjectPropertyDomain(:isIngredientOf :Food)\n" +
        "ObjectPropertyRange(:isIngredientOf :Food)\n" +
        "SubObjectPropertyOf(:isToppingOf :isIngredientOf)\n" +
        "FunctionalObjectProperty(:isToppingOf)\n" +
        "ObjectPropertyDomain(:isToppingOf :PizzaTopping)\n" +
        "ObjectPropertyRange(:isToppingOf :Pizza)\n" +
        "SubObjectPropertyOf(<urn:op> <urn:op>)\n" +
        "InverseObjectProperties(<urn:op> <urn:op>)\n" +
        "InverseObjectProperties(<urn:op> ObjectInverseOf(<urn:op>))\n" +
        "FunctionalObjectProperty(<urn:op>)\n" +
        "InverseFunctionalObjectProperty(<urn:op>)\n" +
        "SymmetricObjectProperty(<urn:op>)\n" +
        "AsymmetricObjectProperty(<urn:op>)\n" +
        "TransitiveObjectProperty(<urn:op>)\n" +
        "ReflexiveObjectProperty(<urn:op>)\n" +
        "IrreflexiveObjectProperty(<urn:op>)\n" +
        "ObjectPropertyDomain(<urn:op> <urn:classexpression>)\n" +
        "ObjectPropertyRange(<urn:op> <urn:classexpression>)\n" +
        "SubDataPropertyOf(<urn:dp> <urn:dp>)\n" +
        "FunctionalDataProperty(<urn:dp>)\n" +
        "DataPropertyDomain(<urn:dp> <urn:classexpression>)\n" +
        "DataPropertyRange(<urn:dp> xsd:int)\n" +
        "DataPropertyRange(<urn:dp> <testString>)\n" +
        "DataPropertyRange(<urn:dp> DataOneOf(\"true\"^^xsd:boolean))\n" +
        "DataPropertyRange(<urn:dp> xsd:int)\n" +
        "DataPropertyRange(<urn:testdp> DatatypeRestriction(xsd:date xsd:maxInclusive \"1971-09-24\"^^xsd:date xsd:minInclusive \"1970-10-22\"^^xsd:date))\n"
        +
        "DataPropertyRange(<urn:testdp> DatatypeRestriction(xsd:date xsd:minInclusive \"1973-09-24\"^^xsd:date xsd:maxInclusive \"1974-10-22\"^^xsd:date))\n"
        +
        "SubClassOf(:American :NamedPizza)\n" +
        "SubClassOf(:American ObjectSomeValuesFrom(:hasTopping :MozzarellaTopping))\n" +
        "SubClassOf(:American ObjectSomeValuesFrom(:hasTopping :PeperoniSausageTopping))\n" +
        "SubClassOf(:American ObjectSomeValuesFrom(:hasTopping :TomatoTopping))\n" +
        "SubClassOf(:American ObjectAllValuesFrom(:hasTopping ObjectUnionOf(:MozzarellaTopping :PeperoniSausageTopping :TomatoTopping)))\n"
        +
        "SubClassOf(:American ObjectHasValue(:hasCountryOfOrigin :America))\n" +
        "SubClassOf(:ArtichokeTopping :VegetableTopping)\n" +
        "SubClassOf(:ArtichokeTopping ObjectSomeValuesFrom(:hasSpiciness :Mild))\n" +
        "AnnotationAssertion(rdfs:label :Cajun \"Cajun\"@pt)\n" +
        "AnnotationAssertion(rdfs:comment :Cajun \"Cajun\"@pt)\n" +
        "AnnotationAssertion(rdfs:label :Cajun \"A Cajun test\"@pt)\n" +
        "AnnotationAssertion(rdfs:comment :Cajun \"A Cajun test\"@pt)\n" +
        "SubClassOf(:Cajun ObjectAllValuesFrom(:hasTopping ObjectUnionOf(:MozzarellaTopping :OnionTopping :PeperonataTopping :PrawnsTopping :TobascoPepperSauce :TomatoTopping)))\n"
        +
        "SubClassOf(:Capricciosa ObjectAllValuesFrom(:hasTopping ObjectUnionOf(:AnchoviesTopping :CaperTopping :HamTopping :MozzarellaTopping :OliveTopping :PeperonataTopping :TomatoTopping)))\n"
        +
        "DisjointClasses(:Capricciosa :Caprina)\n" +
        "DisjointClasses(:Capricciosa :Fiorentina)\nDisjointClasses(:Capricciosa :FourSeasons)\nDisjointClasses(:Capricciosa :FruttiDiMare)\nDisjointClasses(:Capricciosa :Giardiniera)\nDisjointClasses(:Capricciosa :LaReine)\nDisjointClasses(:Capricciosa :Margherita)\nDisjointClasses(:Capricciosa :Mushroom)\nDisjointClasses(:Capricciosa :Napoletana)\nDisjointClasses(:Capricciosa :Parmense)\nDisjointClasses(:Capricciosa :PolloAdAstra)\nDisjointClasses(:Capricciosa :PrinceCarlo)\nDisjointClasses(:Capricciosa :QuattroFormaggi)\nDisjointClasses(:Capricciosa :Rosa)\nDisjointClasses(:Capricciosa :Siciliana)\nDisjointClasses(:Capricciosa :SloppyGiuseppe)\nDisjointClasses(:Capricciosa :Soho)\nDisjointClasses(:Capricciosa :UnclosedPizza)\n"
        +
        "DisjointClasses(:Capricciosa :Veneziana)\n" +
        "AnnotationAssertion(rdfs:comment :CheeseyPizza \"Any pizza that has at least 1 cheese topping.\"@en)\n" +
        "AnnotationAssertion(rdfs:label :CheeseyPizza \"PizzaComQueijo\"@pt)\n" +
        "EquivalentClasses(:CheeseyPizza ObjectIntersectionOf(:Pizza ObjectSomeValuesFrom(:hasTopping :CheeseTopping)))\n"
        +
        "AnnotationAssertion(rdfs:comment :CheeseyVegetableTopping \"This class will be inconsistent. This is because we have given it 2 disjoint parents, which means it could never have any members (as nothing can simultaneously be a CheeseTopping and a VegetableTopping). NB Called ProbeInconsistentTopping in the ProtegeOWL Tutorial.\"@en)\n"
        +
        "AnnotationAssertion(rdfs:label :CheeseyVegetableTopping \"CoberturaDeQueijoComVegetais\"@pt)\n" +
        "SubClassOf(:CheeseyVegetableTopping :CheeseTopping)\n" +
        "SubClassOf(:CheeseyVegetableTopping :VegetableTopping)\n" +
        "AnnotationAssertion(rdfs:comment :Country \"A class that is equivalent to the set of individuals that are described in the enumeration - ie Countries can only be either America, England, France, Germany or Italy and nothing else. Note that these individuals have been asserted to be allDifferent from each other.\"@en)\n"
        +
        "AnnotationAssertion(rdfs:label :Country \"Pais\"@pt)\n" +
        "EquivalentClasses(:Country ObjectIntersectionOf(:DomainConcept ObjectOneOf(:America :England :France :Germany :Italy)))\n"
        +
        "SubClassOf(:Giardiniera ObjectSomeValuesFrom(:hasTopping :LeekTopping))\n" +
        "SubClassOf(:Giardiniera ObjectSomeValuesFrom(:hasTopping :MozzarellaTopping))\n" +
        "SubClassOf(:Giardiniera ObjectSomeValuesFrom(:hasTopping :MushroomTopping))\n" +
        "SubClassOf(:Giardiniera ObjectSomeValuesFrom(:hasTopping :OliveTopping))\n" +
        "DisjointClasses(:Giardiniera :LaReine :Margherita :Mushroom :Napoletana :Parmense :PolloAdAstra)\n"
        +
        "EquivalentClasses(:InterestingPizza ObjectIntersectionOf(:Pizza ObjectMinCardinality(3 :hasTopping)))\n" +
        "SubClassOf(:LaReine ObjectSomeValuesFrom(:hasTopping :TomatoTopping))\n" +
        "SubClassOf(:LaReine ObjectAllValuesFrom(:hasTopping ObjectIntersectionOf(:HamTopping :MozzarellaTopping :MushroomTopping :OliveTopping :TomatoTopping)))\n"
        +
        "EquivalentClasses(:MeatyPizza ObjectIntersectionOf(:Pizza ObjectSomeValuesFrom(:hasTopping :MeatTopping)))\n" +
        "SubClassOf(:Napoletana ObjectSomeValuesFrom(:hasTopping :TomatoTopping))\n" +
        "SubClassOf(:Napoletana ObjectHasValue(:hasCountryOfOrigin :Italy))\n" +
        "EquivalentClasses(:NonVegetarianPizza ObjectIntersectionOf(:Pizza ObjectComplementOf(:VegetarianPizza)))\n" +
        "EquivalentClasses(:RealItalianPizza ObjectIntersectionOf(:Pizza ObjectHasValue(:hasCountryOfOrigin :Italy)))\n"
        +
        "EquivalentClasses(:Spiciness ObjectUnionOf(:Hot :Medium :Mild))\n" +
        "EquivalentClasses(:SpicyPizza ObjectIntersectionOf(:Pizza ObjectSomeValuesFrom(:hasTopping :SpicyTopping)))\n"
        +
        "EquivalentClasses(:SpicyPizzaEquivalent ObjectIntersectionOf(:Pizza ObjectSomeValuesFrom(:hasTopping ObjectIntersectionOf(:PizzaTopping ObjectSomeValuesFrom(:hasSpiciness :Hot)))))\n"
        +
        "EquivalentClasses(:SpicyTopping ObjectIntersectionOf(:PizzaTopping ObjectSomeValuesFrom(:hasSpiciness :Hot)))\n"
        +
        "EquivalentClasses(:ThinAndCrispyPizza ObjectIntersectionOf(:Pizza ObjectAllValuesFrom(:hasBase :ThinAndCrispyBase)))\n"
        +
        "EquivalentClasses(:VegetarianPizza ObjectIntersectionOf(:Pizza ObjectComplementOf(ObjectSomeValuesFrom(:hasTopping :FishTopping)) ObjectComplementOf(ObjectSomeValuesFrom(:hasTopping :MeatTopping))))\n"
        +
        "EquivalentClasses(:VegetarianPizzaEquivalent1 ObjectIntersectionOf(:Pizza ObjectAllValuesFrom(:hasTopping :VegetarianTopping)))\n"
        +
        "EquivalentClasses(:VegetarianPizzaEquivalent2 ObjectIntersectionOf(:Pizza ObjectAllValuesFrom(:hasTopping ObjectUnionOf(:CheeseTopping :FruitTopping :HerbSpiceTopping :NutTopping :SauceTopping :VegetableTopping))))\n"
        +
        "EquivalentClasses(:VegetarianTopping ObjectIntersectionOf(:PizzaTopping ObjectUnionOf(:CheeseTopping :FruitTopping :HerbSpiceTopping :NutTopping :SauceTopping :VegetableTopping)))\n"
        +
        "EquivalentClasses(<urn:classexpression> <urn:iri>)\n" +
        "SubClassOf(<urn:classexpression> <testString>)\n" +
        "SubClassOf(<urn:classexpression> ObjectIntersectionOf(<testString> <urn:classexpression>))\nSubClassOf(<urn:classexpression> ObjectOneOf(<urn:iri>))\nSubClassOf(<urn:classexpression> ObjectSomeValuesFrom(<urn:op> <urn:classexpression>))\nSubClassOf(<urn:classexpression> ObjectAllValuesFrom(<urn:op> <urn:classexpression>))\nSubClassOf(<urn:classexpression> ObjectHasValue(<urn:op> _:genid1))\nSubClassOf(<urn:classexpression> ObjectMinCardinality(1 <urn:op>))\nSubClassOf(<urn:classexpression> ObjectExactCardinality(1 <urn:op>))\nSubClassOf(<urn:classexpression> ObjectMaxCardinality(1 <urn:op>))\nSubClassOf(<urn:classexpression> DataSomeValuesFrom(<urn:dp> xsd:int))\nSubClassOf(<urn:classexpression> DataAllValuesFrom(<urn:dp> xsd:int))\nSubClassOf(<urn:classexpression> DataHasValue(<urn:dp> \"true\"^^xsd:boolean))\nSubClassOf(<urn:classexpression> DataMinCardinality(1 <urn:dp>))\nSubClassOf(<urn:classexpression> DataExactCardinality(1 <urn:dp>))\nSubClassOf(<urn:classexpression> DataMaxCardinality(1 <urn:dp>))\n"
        +
        "ClassAssertion(:Country :America)\n" +
        "ClassAssertion(owl:Thing :America)\nClassAssertion(:Country :England)\nClassAssertion(owl:Thing :England)\nClassAssertion(:Country :France)\nClassAssertion(owl:Thing :France)\nClassAssertion(:Country :Germany)\nClassAssertion(owl:Thing :Germany)\nClassAssertion(:Country :Italy)\nClassAssertion(owl:Thing :Italy)\n"
        +
        "ClassAssertion(<urn:classexpression> _:genid1)\n" +
        "DifferentIndividuals(:America :England :France :Germany :Italy)\n" +
        "ObjectPropertyAssertion(<urn:op> _:genid1 _:genid1)\n" +
        "ObjectPropertyAssertion(<urn:op> _:genid2 _:genid2)\n" +
        "NegativeObjectPropertyAssertion(<urn:op> _:genid1 _:genid1)\n" +
        "NegativeObjectPropertyAssertion(<urn:op> _:genid2 _:genid2)\n" +
        "DataPropertyAssertion(<urn:dp> _:genid1 \"testString\"^^xsd:int)\n" +
        "DataPropertyAssertion(<urn:dp> _:genid1 \"true\"^^xsd:boolean)\n" +
        "NegativeDataPropertyAssertion(<urn:dp> _:genid1 \"true\"^^xsd:boolean)\n" +
        "AnnotationAssertion(<urn:ap> <urn:i> \"true\"^^xsd:boolean))",
        "Prefix(:=<http://www.co-ode.org/ontologies/pizza/pizza.owl#>)\n" +
            "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n" +
            "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n" +
            "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n" +
            "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n" +
            "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n" +
            "Ontology(<http://www.co-ode.org/ontologies/pizza/pizza.owl>\n" +
            "Annotation(owl:versionInfo \"version 1.5\"^^xsd:string)\n" +
            "Annotation(rdfs:comment \"An example ontology\"@en)\n" +
            "Annotation(owl:versionInfo \"v.1.5. Removed protege\"@en)\n" +
            "Annotation(owl:versionInfo \"v.1.4.\"@en)\n" +
            "Declaration(DataProperty(<urn:testdp>))\n" +
            "Declaration(Class(:AmericanHot))\nDeclaration(Class(:AnchoviesTopping))\nDeclaration(Class(:ArtichokeTopping))\nDeclaration(Class(:AsparagusTopping))\nDeclaration(Class(:Cajun))\nDeclaration(Class(:CajunSpiceTopping))\nDeclaration(Class(:CaperTopping))\nDeclaration(Class(:Capricciosa))\nDeclaration(Class(:Caprina))\nDeclaration(Class(:CheeseTopping))\nDeclaration(Class(:CheeseyPizza))\nDeclaration(Class(:CheeseyVegetableTopping))\nDeclaration(Class(:ChickenTopping))\nDeclaration(Class(:Country))\nDeclaration(Class(:DeepPanBase))\nDeclaration(Class(:DomainConcept))\nDeclaration(Class(:Fiorentina))\nDeclaration(Class(:FishTopping))\nDeclaration(Class(:Food))\nDeclaration(Class(:FourCheesesTopping))\nDeclaration(Class(:FourSeasons))\nDeclaration(Class(:FruitTopping))\nDeclaration(Class(:FruttiDiMare))\nDeclaration(Class(:GarlicTopping))\nDeclaration(Class(:Giardiniera))\nDeclaration(Class(:GoatsCheeseTopping))\nDeclaration(Class(:GorgonzolaTopping))\nDeclaration(Class(:GreenPepperTopping))\nDeclaration(Class(:HamTopping))\nDeclaration(Class(:HerbSpiceTopping))\nDeclaration(Class(:Hot))\nDeclaration(Class(:HotGreenPepperTopping))\nDeclaration(Class(:HotSpicedBeefTopping))\nDeclaration(Class(:IceCream))\nDeclaration(Class(:InterestingPizza))\nDeclaration(Class(:JalapenoPepperTopping))\nDeclaration(Class(:LaReine))\nDeclaration(Class(:LeekTopping))\nDeclaration(Class(:Margherita))\nDeclaration(Class(:MeatTopping))\nDeclaration(Class(:MeatyPizza))\nDeclaration(Class(:Medium))\nDeclaration(Class(:Mild))\nDeclaration(Class(:MixedSeafoodTopping))\nDeclaration(Class(:MozzarellaTopping))\nDeclaration(Class(:Mushroom))\nDeclaration(Class(:MushroomTopping))\nDeclaration(Class(:NamedPizza))\nDeclaration(Class(:Napoletana))\nDeclaration(Class(:NonVegetarianPizza))\nDeclaration(Class(:NutTopping))\nDeclaration(Class(:OliveTopping))\nDeclaration(Class(:OnionTopping))\nDeclaration(Class(:ParmaHamTopping))\nDeclaration(Class(:Parmense))\nDeclaration(Class(:ParmesanTopping))\nDeclaration(Class(:PeperonataTopping))\nDeclaration(Class(:PeperoniSausageTopping))\nDeclaration(Class(:PepperTopping))\nDeclaration(Class(:PetitPoisTopping))\nDeclaration(Class(:PineKernels))\nDeclaration(Class(:Pizza))\nDeclaration(Class(:PizzaBase))\nDeclaration(Class(:PizzaTopping))\nDeclaration(Class(:PolloAdAstra))\nDeclaration(Class(:PrawnsTopping))\nDeclaration(Class(:PrinceCarlo))\nDeclaration(Class(:QuattroFormaggi))\nDeclaration(Class(:RealItalianPizza))\nDeclaration(Class(:RedOnionTopping))\nDeclaration(Class(:RocketTopping))\nDeclaration(Class(:Rosa))\nDeclaration(Class(:RosemaryTopping))\nDeclaration(Class(:SauceTopping))\nDeclaration(Class(:Siciliana))\nDeclaration(Class(:SlicedTomatoTopping))\nDeclaration(Class(:SloppyGiuseppe))\nDeclaration(Class(:Soho))\nDeclaration(Class(:Spiciness))\nDeclaration(Class(:SpicyPizza))\nDeclaration(Class(:SpicyPizzaEquivalent))\nDeclaration(Class(:SpicyTopping))\nDeclaration(Class(:SpinachTopping))\nDeclaration(Class(:SultanaTopping))\nDeclaration(Class(:SundriedTomatoTopping))\nDeclaration(Class(:SweetPepperTopping))\nDeclaration(Class(:ThinAndCrispyBase))\nDeclaration(Class(:ThinAndCrispyPizza))\nDeclaration(Class(:TobascoPepperSauce))\nDeclaration(Class(:TomatoTopping))\nDeclaration(Class(:UnclosedPizza))\nDeclaration(Class(:ValuePartition))\nDeclaration(Class(:VegetableTopping))\nDeclaration(Class(:VegetarianPizza))\nDeclaration(Class(:VegetarianPizzaEquivalent1))\nDeclaration(Class(:VegetarianPizzaEquivalent2))\nDeclaration(Class(:VegetarianTopping))\nDeclaration(Class(:Veneziana))\nDeclaration(Class(<urn:classexpression>))\nDeclaration(Class(<urn:iri>))\nDeclaration(ObjectProperty(:hasBase))\nDeclaration(ObjectProperty(:hasCountryOfOrigin))\nDeclaration(ObjectProperty(:hasIngredient))\nDeclaration(ObjectProperty(:hasSpiciness))\nDeclaration(ObjectProperty(:hasTopping))\nDeclaration(ObjectProperty(:isBaseOf))\nDeclaration(ObjectProperty(:isIngredientOf))\nDeclaration(ObjectProperty(:isToppingOf))\nDeclaration(ObjectProperty(<urn:op>))\nDeclaration(DataProperty(<urn:dp>))\nDeclaration(NamedIndividual(:America))\nDeclaration(NamedIndividual(:England))\nDeclaration(NamedIndividual(:France))\nDeclaration(NamedIndividual(:Germany))\nDeclaration(NamedIndividual(:Italy))\nDeclaration(NamedIndividual(<urn:iri>))\nDeclaration(AnnotationProperty(<urn:ap>))\n"
            +
            "Declaration(Class(:American))\n" +
            "SubAnnotationPropertyOf(<urn:ap> <urn:ap>)\n" +
            "AnnotationPropertyRange(<urn:ap> <urn:iri>)\n" +
            "AnnotationPropertyDomain(<urn:ap> <urn:iri>)\n" +
            "InverseObjectProperties(:hasBase :isBaseOf)\n" +
            "FunctionalObjectProperty(:hasBase)\n" +
            "InverseFunctionalObjectProperty(:hasBase)\n" +
            "ObjectPropertyDomain(:hasBase :Pizza)\n" +
            "InverseObjectProperties(:hasTopping :isToppingOf)\n" +
            "ObjectPropertyRange(:hasBase :PizzaBase)\n" +
            "TransitiveObjectProperty(:hasIngredient)\n" +
            "ObjectPropertyDomain(:hasIngredient :Food)\n" +
            "ObjectPropertyRange(:hasIngredient :Food)\n" +
            "FunctionalObjectProperty(:hasSpiciness)\n" +
            "SubObjectPropertyOf(:hasBase :hasIngredient)\n" +
            "ObjectPropertyRange(:hasSpiciness :Spiciness)\n" +
            "SubObjectPropertyOf(:hasTopping :hasIngredient)\n" +
            "InverseFunctionalObjectProperty(:hasTopping)\n" +
            "ObjectPropertyDomain(:hasTopping :Pizza)\n" +
            "ObjectPropertyRange(:hasTopping :PizzaTopping)\n" +
            "SubObjectPropertyOf(:isBaseOf :isIngredientOf)\n" +
            "FunctionalObjectProperty(:isBaseOf)\n" +
            "InverseObjectProperties(:hasIngredient :isIngredientOf)\n" +
            "InverseFunctionalObjectProperty(:isBaseOf)\n" +
            "ObjectPropertyDomain(:isBaseOf :PizzaBase)\n" +
            "TransitiveObjectProperty(:isIngredientOf)\n" +
            "ObjectPropertyDomain(:isIngredientOf :Food)\n" +
            "ObjectPropertyRange(:isIngredientOf :Food)\n" +
            "SubObjectPropertyOf(:isToppingOf :isIngredientOf)\n" +
            "FunctionalObjectProperty(:isToppingOf)\n" +
            "ObjectPropertyRange(:isToppingOf :Pizza)\n" +
            "TransitiveObjectProperty(<urn:op>)\n" +
            "SubObjectPropertyOf(<urn:op> <urn:op>)\n" +
            "InverseObjectProperties(<urn:op> <urn:op>)\n" +
            "InverseObjectProperties(<urn:op> ObjectInverseOf(<urn:op>))\n" +
            "FunctionalObjectProperty(<urn:op>)\n" +
            "ObjectPropertyDomain(:isToppingOf :PizzaTopping)\n" +
            "InverseFunctionalObjectProperty(<urn:op>)\n" +
            "ObjectPropertyRange(:isBaseOf :Pizza)\n" +
            "SymmetricObjectProperty(<urn:op>)\n" +
            "AsymmetricObjectProperty(<urn:op>)\n" +
            "ReflexiveObjectProperty(<urn:op>)\n" +
            "IrreflexiveObjectProperty(<urn:op>)\n" +
            "ObjectPropertyDomain(<urn:op> <urn:classexpression>)\n" +
            "ObjectPropertyRange(<urn:op> <urn:classexpression>)\n" +
            "SubDataPropertyOf(<urn:dp> <urn:dp>)\n" +
            "FunctionalDataProperty(<urn:dp>)\n" +
            "DataPropertyDomain(<urn:dp> <urn:classexpression>)\n" +
            "DataPropertyRange(<urn:dp> xsd:int)\n" +
            "DataPropertyRange(<urn:testdp> DatatypeRestriction(xsd:date xsd:maxInclusive \"1974-10-22\"^^xsd:date xsd:minInclusive \"1973-09-24\"^^xsd:date))\n"
            +
            "DataPropertyRange(<urn:testdp> DatatypeRestriction(xsd:date xsd:minInclusive \"1970-10-22\"^^xsd:date xsd:maxInclusive \"1971-09-24\"^^xsd:date))\n"
            +
            "DataPropertyRange(<urn:dp> DataOneOf(\"true\"^^xsd:boolean))\n" +
            "DataPropertyRange(<urn:dp> xsd:int)\n" +
            "DataPropertyRange(<urn:dp> <testString>)\n" +
            "SubClassOf(:American :NamedPizza)\n" +
            "SubClassOf(:American ObjectSomeValuesFrom(:hasTopping :MozzarellaTopping))\n" +
            "SubClassOf(:American ObjectHasValue(:hasCountryOfOrigin :America))\n" +
            "SubClassOf(:ArtichokeTopping :VegetableTopping)\n" +
            "SubClassOf(:ArtichokeTopping ObjectSomeValuesFrom(:hasSpiciness :Mild))\n" +
            "AnnotationAssertion(rdfs:label :Cajun \"Cajun\"@pt)\n" +
            "AnnotationAssertion(rdfs:comment :Cajun \"A Cajun test\"@pt)\n" +
            "AnnotationAssertion(rdfs:label :Cajun \"A Cajun test\"@pt)\n" +
            "SubClassOf(:American ObjectSomeValuesFrom(:hasTopping :PeperoniSausageTopping))\n" +
            "SubClassOf(:American ObjectSomeValuesFrom(:hasTopping :TomatoTopping))\n" +
            "SubClassOf(:American ObjectAllValuesFrom(:hasTopping ObjectUnionOf(:MozzarellaTopping :PeperoniSausageTopping :TomatoTopping)))\n"
            +
            "AnnotationAssertion(rdfs:comment :Cajun \"Cajun\"@pt)\n" +
            "SubClassOf(:Cajun ObjectAllValuesFrom(:hasTopping ObjectUnionOf(:MozzarellaTopping :OnionTopping :PeperonataTopping :PrawnsTopping :TobascoPepperSauce :TomatoTopping)))\n"
            +
            "SubClassOf(:Capricciosa ObjectAllValuesFrom(:hasTopping ObjectUnionOf( :CaperTopping :AnchoviesTopping :HamTopping :MozzarellaTopping :OliveTopping :PeperonataTopping :TomatoTopping)))\n"
            +
            "DisjointClasses(:Capricciosa :Fiorentina)\nDisjointClasses(:Capricciosa :FourSeasons)\nDisjointClasses(:Capricciosa :FruttiDiMare)\nDisjointClasses(:Capricciosa :Giardiniera)\nDisjointClasses(:Capricciosa :LaReine)\nDisjointClasses(:Capricciosa :Margherita)\nDisjointClasses(:Capricciosa :Mushroom)\nDisjointClasses(:Capricciosa :Napoletana)\nDisjointClasses(:Capricciosa :Parmense)\nDisjointClasses(:Capricciosa :PolloAdAstra)\nDisjointClasses(:Capricciosa :PrinceCarlo)\nDisjointClasses(:Capricciosa :QuattroFormaggi)\nDisjointClasses(:Capricciosa :Rosa)\nDisjointClasses(:Capricciosa :Siciliana)\nDisjointClasses(:Capricciosa :SloppyGiuseppe)\nDisjointClasses(:Capricciosa :Soho)\nDisjointClasses(:Capricciosa :UnclosedPizza)\n"
            +
            "DisjointClasses(:Capricciosa :Caprina)\n" +
            "DisjointClasses(:Capricciosa :Veneziana)\n" +
            "AnnotationAssertion(rdfs:label :CheeseyPizza \"PizzaComQueijo\"@pt)\n" +
            "AnnotationAssertion(rdfs:comment :CheeseyPizza \"Any pizza that has at least 1 cheese topping.\"@en)\n" +
            "EquivalentClasses(ObjectIntersectionOf(:Pizza ObjectSomeValuesFrom(:hasTopping :CheeseTopping)):CheeseyPizza )\n"
            +
            "AnnotationAssertion(rdfs:comment :CheeseyVegetableTopping \"This class will be inconsistent. This is because we have given it 2 disjoint parents, which means it could never have any members (as nothing can simultaneously be a CheeseTopping and a VegetableTopping). NB Called ProbeInconsistentTopping in the ProtegeOWL Tutorial.\"@en)\n"
            +
            "AnnotationAssertion(rdfs:label :CheeseyVegetableTopping \"CoberturaDeQueijoComVegetais\"@pt)\n" +
            "SubClassOf(:CheeseyVegetableTopping :CheeseTopping)\n" +
            "SubClassOf(:CheeseyVegetableTopping :VegetableTopping)\n" +
            "AnnotationAssertion(rdfs:label :Country \"Pais\"@pt)\n" +
            "EquivalentClasses(:Country ObjectIntersectionOf(:DomainConcept ObjectOneOf(:America :England :France :Germany :Italy)))\n"
            +
            "AnnotationAssertion(rdfs:comment :Country \"A class that is equivalent to the set of individuals that are described in the enumeration - ie Countries can only be either America, England, France, Germany or Italy and nothing else. Note that these individuals have been asserted to be allDifferent from each other.\"@en)\n"
            +
            "SubClassOf(:Giardiniera ObjectSomeValuesFrom(:hasTopping :MushroomTopping))\n" +
            "SubClassOf(:Giardiniera ObjectSomeValuesFrom(:hasTopping :LeekTopping))\n" +
            "DisjointClasses(:Giardiniera :LaReine :Mushroom :Margherita :Napoletana :Parmense :PolloAdAstra)\n"
            +
            "SubClassOf(:Giardiniera ObjectSomeValuesFrom(:hasTopping :MozzarellaTopping))\n" +
            "SubClassOf(:Giardiniera ObjectSomeValuesFrom(:hasTopping :OliveTopping))\n" +
            "EquivalentClasses(:InterestingPizza ObjectIntersectionOf(:Pizza ObjectMinCardinality(3 :hasTopping)))\n" +
            "SubClassOf(:LaReine ObjectAllValuesFrom(:hasTopping ObjectIntersectionOf(:HamTopping :MozzarellaTopping :MushroomTopping :OliveTopping :TomatoTopping)))\n"
            +
            "SubClassOf(:LaReine ObjectSomeValuesFrom(:hasTopping :TomatoTopping))\n" +
            "EquivalentClasses(:MeatyPizza ObjectIntersectionOf(:Pizza ObjectSomeValuesFrom(:hasTopping :MeatTopping)))\n"
            +
            "SubClassOf(:Napoletana ObjectHasValue(:hasCountryOfOrigin :Italy))\n" +
            "EquivalentClasses(:RealItalianPizza ObjectIntersectionOf(:Pizza ObjectHasValue(:hasCountryOfOrigin :Italy)))\n"
            +
            "EquivalentClasses(:NonVegetarianPizza ObjectIntersectionOf(:Pizza ObjectComplementOf(:VegetarianPizza)))\n"
            +
            "SubClassOf(:Napoletana ObjectSomeValuesFrom(:hasTopping :TomatoTopping))\n" +
            "EquivalentClasses(:Spiciness ObjectUnionOf(:Medium :Mild :Hot ))\n" +
            "EquivalentClasses(:SpicyPizza ObjectIntersectionOf(:Pizza ObjectSomeValuesFrom(:hasTopping :SpicyTopping)))\n"
            +
            "EquivalentClasses(:SpicyPizzaEquivalent ObjectIntersectionOf(:Pizza ObjectSomeValuesFrom(:hasTopping ObjectIntersectionOf(:PizzaTopping ObjectSomeValuesFrom(:hasSpiciness :Hot)))))\n"
            +
            "EquivalentClasses(:SpicyTopping ObjectIntersectionOf(:PizzaTopping ObjectSomeValuesFrom(:hasSpiciness :Hot)))\n"
            +
            "EquivalentClasses(:ThinAndCrispyPizza ObjectIntersectionOf(ObjectAllValuesFrom( :hasBase :ThinAndCrispyBase) :Pizza ))\n"
            +
            "EquivalentClasses(:VegetarianPizzaEquivalent1 ObjectIntersectionOf(:Pizza ObjectAllValuesFrom(:hasTopping :VegetarianTopping)))\n"
            +
            "EquivalentClasses(:VegetarianPizzaEquivalent2 ObjectIntersectionOf(:Pizza ObjectAllValuesFrom(:hasTopping ObjectUnionOf(:CheeseTopping :FruitTopping :HerbSpiceTopping :NutTopping :SauceTopping :VegetableTopping))))\n"
            +
            "EquivalentClasses(:VegetarianPizza ObjectIntersectionOf(:Pizza ObjectComplementOf(ObjectSomeValuesFrom(:hasTopping :FishTopping)) ObjectComplementOf(ObjectSomeValuesFrom(:hasTopping :MeatTopping))))\n"
            +
            "EquivalentClasses(<urn:classexpression> <urn:iri>)\n" +
            "EquivalentClasses(ObjectIntersectionOf( ObjectUnionOf(:CheeseTopping :FruitTopping :HerbSpiceTopping :SauceTopping :NutTopping  :VegetableTopping) :PizzaTopping) :VegetarianTopping )\n"
            +
            "SubClassOf(<urn:classexpression> ObjectIntersectionOf(<testString> <urn:classexpression>))\nSubClassOf(<urn:classexpression> ObjectOneOf(<urn:iri>))\nSubClassOf(<urn:classexpression> ObjectSomeValuesFrom(<urn:op> <urn:classexpression>))\nSubClassOf(<urn:classexpression> ObjectAllValuesFrom(<urn:op> <urn:classexpression>))\nSubClassOf(<urn:classexpression> ObjectHasValue(<urn:op> _:genid1))\nSubClassOf(<urn:classexpression> ObjectMinCardinality(1 <urn:op>))\nSubClassOf(<urn:classexpression> ObjectExactCardinality(1 <urn:op>))\nSubClassOf(<urn:classexpression> ObjectMaxCardinality(1 <urn:op>))\nSubClassOf(<urn:classexpression> DataSomeValuesFrom(<urn:dp> xsd:int))\nSubClassOf(<urn:classexpression> DataAllValuesFrom(<urn:dp> xsd:int))\nSubClassOf(<urn:classexpression> DataHasValue(<urn:dp> \"true\"^^xsd:boolean))\nSubClassOf(<urn:classexpression> DataMinCardinality(1 <urn:dp>))\nSubClassOf(<urn:classexpression> DataExactCardinality(1 <urn:dp>))\nSubClassOf(<urn:classexpression> DataMaxCardinality(1 <urn:dp>))\n"
            +
            "SubClassOf(<urn:classexpression> <testString>)\n" +
            "ClassAssertion(owl:Thing :America)\nClassAssertion(:Country :England)\nClassAssertion(owl:Thing :England)\nClassAssertion(:Country :France)\nClassAssertion(owl:Thing :France)\nClassAssertion(:Country :Germany)\nClassAssertion(owl:Thing :Germany)\nClassAssertion(:Country :Italy)\nClassAssertion(owl:Thing :Italy)\n"
            +
            "ClassAssertion(:Country :America)\n" +
            "ObjectPropertyAssertion(<urn:op> _:genid1 _:genid1)\n" +
            "ObjectPropertyAssertion(<urn:op> _:genid2 _:genid2)\n" +
            "NegativeDataPropertyAssertion(<urn:dp> _:genid1 \"true\"^^xsd:boolean)\n" +
            "NegativeObjectPropertyAssertion(<urn:op> _:genid1 _:genid1)\n" +
            "ClassAssertion(<urn:classexpression> _:genid1)\n" +
            "NegativeObjectPropertyAssertion(<urn:op> _:genid2 _:genid2)\n" +
            "DifferentIndividuals(:America :Germany :England :France :Italy)\n" +
            "DataPropertyAssertion(<urn:dp> _:genid1 \"testString\"^^xsd:int)\n" +
            "DataPropertyAssertion(<urn:dp> _:genid1 \"true\"^^xsd:boolean)\n" +
            "AnnotationAssertion(<urn:ap> <urn:i> \"true\"^^xsd:boolean))"
    };
    private final OWLDocumentFormat format;

    public OutputSyntaxSortTestCase(OWLDocumentFormat format) {
        this.format = format;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> getData() {
        return Arrays.<Object[]> asList(new Object[] { new ManchesterSyntaxDocumentFormat() },
            new Object[] { new FunctionalSyntaxDocumentFormat() },
            new Object[] { new TurtleDocumentFormat() },
            new Object[] { new RDFXMLDocumentFormat() },
            new Object[] { new OWLXMLDocumentFormat() });
    }

    @Test
    public void shouldOutputAllInSameOrder() throws OWLOntologyStorageException, OWLOntologyCreationException {
        AnonymousIndividualProperties.setRemapAllAnonymousIndividualsIds(false);
        try {
        List<OWLOntology> ontologies = new ArrayList<>();
        List<String> set = new ArrayList<>();
        for (String s : input) {
            OWLOntology o = loadOntologyFromString(new StringDocumentSource(s, IRI.generateDocumentIRI(),
                new FunctionalSyntaxDocumentFormat(), null));
            set.add(saveOntology(o, format).toString());
            ontologies.add(o);
        }
        for (int i = 0; i < ontologies.size() - 1; i++) {
            equal(ontologies.get(i), ontologies.get(i + 1));
        }
        for (int i = 0; i < set.size() - 1; i++) {
            assertEquals(set.get(i), set.get(i + 1));
        }
        } finally {
            AnonymousIndividualProperties.resetToDefault();
        }
    }
}
