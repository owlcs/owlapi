owlapi
======

OWL API main repository

The OWL API is a Java API for creating, manipulating and serialising OWL Ontologies. 
The latest version of the API supports OWL 2.

It is available under Open Source licenses (LGPL and Apache).

The following components are included:

* An API for OWL 2 and an efficient in-memory reference implementation
* RDF/XML parser and writer
* OWL/XML parser and writer
* OWL Functional Syntax parser and writer
* Turtle parser and writer
* KRSS parser
* OBO Flat file format parser
* Reasoner interfaces for working with reasoners such as FaCT++, HermiT, Pellet, Racer, JFact and Chainsaw.

[![Build Status](https://secure.travis-ci.org/ansell/owlapi.png)](http://travis-ci.org/ansell/owlapi)

# Differences from Ansell OWLAPI to OWLCS OWLAPI

* Registries to enable dynamic service registration without hardcoding everything into a java class. The registries are automatically populated using META-INF/services/ files using java.util.ServiceLoader, but the registries can easily be reset and customised. In addition, the default instance of each registry can be ignored and custom registries can be freely created.
** OWLOntologyManagerFactoryRegistry : Allow preferred OWLOntologyManager implementation to be used using a configuration variable.
** OWLOntologyStorerFactoryRegistry : OWLOntologyManagerFactoryRegistry is extended to take an optional storer registry to enable a manager to only store to a subset of formats.
** OWLParserFactoryRegistry : OWLOntologyManagerFactoryRegistry is extended to take an optional parser registry to enable a manager to only support parsing from a subset of formats*
** OWLProfileRegistry : Enables dynamic loading of profiles to extend the default set. Also allows access to profiles using IRIs, including the W3C approved URIs for the standardised profiles as detailed on http://www.w3.org/ns/owl-profile/
** OWLReasonerFactoryRegistry : Enables the use of the preexisting OWLReasonerFactory.getReasonerName method as a key to access reasoners at runtime without hardcoding the class names into the code.
* Addition of format in OWLDocumentSource to enable targeted parsing
* Export directly to Sesame OpenRDF in memory Statements or directly to any Sesame Repository or Sail
* Import and export from any available Sesame OpenRDF Rio RDFFormat
* Load and recognise loaded ontologies based on their version IRIs : The Sourceforge OWLAPI version assumes that version IRIs will be null, or one will know both the ontology IRI and the version IRI. This makes it impossible to recognise a loaded ontology as the target of an import if the import directs to the version IRI and the version IRI does not currently resolve to the ontology document.
* Basic threadsafety improvements : There are a number of places throughout the API where static int's are incremented in different places in a non-atomic way. Fixes for these are to convert to using java AtomicInteger to ensure that the increment and the resulting value are atomically set and guaranteed not to have been used in another location.
