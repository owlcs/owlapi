package org.coode.owlapi.owlxmlparser;

import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLParserURISyntaxException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 * <p/>
 * A handler which knows about OWLXML
 */
public class OWLXMLParserHandler extends DefaultHandler {

    private OWLOntologyManager owlOntologyManager;

    private OWLOntology ontology;

    private List<OWLElementHandler<?>> handlerStack;

    private Map<String, OWLElementHandlerFactory> handlerMap;

    private Map<String, String> prefixName2PrefixMap = new HashMap<String, String>();

    private Locator locator;

    private Stack<URI> bases;

    private OWLOntologyLoaderConfiguration configuration;

    public OWLXMLParserHandler(OWLOntologyManager owlOntologyManager, OWLOntology ontology) {
        this(owlOntologyManager, ontology, null, new OWLOntologyLoaderConfiguration());
    }

    /**
     * Creates an OWLXML handler.
     *
     * @param owlOntologyManager The manager that should be used to obtain a data factory,
     * imported ontologies etc.
     * @param ontology The ontology that the XML representation will be parsed into.
     */
    public OWLXMLParserHandler(OWLOntologyManager owlOntologyManager, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) {
        this(owlOntologyManager, ontology, null, configuration);
    }


    @Override
	public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
        this.locator = locator;

        URI base = null;
        try {
            String systemId = locator.getSystemId();
            if (systemId != null)
                base = new URI(systemId);
        }
        catch (URISyntaxException e) {
        }

        bases.push(base);
    }

    public OWLXMLParserHandler(OWLOntologyManager owlOntologyManager, OWLOntology ontology, OWLElementHandler<?> topHandler) {
        this(owlOntologyManager, ontology, topHandler, new OWLOntologyLoaderConfiguration());
    }


    /**
     * Creates an OWLXML handler with the specified top level handler.  This allows OWL/XML
     * representations of axioms to be embedded in abitrary XML documents e.g. DIG 2.0 documents.
     * (The default handler behaviour expects the top level element to be an Ontology
     * element).
     *
     * @param owlOntologyManager The manager that should be used to obtain a data factory,
     * imported ontologies etc.
     * @param ontology The ontology object that the XML representation should be parsed into.
     * @param topHandler The handler for top level elements - may be <code>null</code>, in which
     * case the parser will expect an Ontology element to be the root element.
     */
    public OWLXMLParserHandler(OWLOntologyManager owlOntologyManager, OWLOntology ontology, OWLElementHandler<?> topHandler, OWLOntologyLoaderConfiguration configuration) {
        this.owlOntologyManager = owlOntologyManager;
        this.ontology = ontology;
        this.bases = new Stack<URI>();
        this.configuration = configuration;
        handlerStack = new ArrayList<OWLElementHandler<?>>();
        prefixName2PrefixMap = new HashMap<String, String>();
        prefixName2PrefixMap.put("owl:", Namespaces.OWL.toString());
        prefixName2PrefixMap.put("xsd:", Namespaces.XSD.toString());
        if (topHandler != null) {
            handlerStack.add(0, topHandler);
        }
        handlerMap = new HashMap<String, OWLElementHandlerFactory>();

        addFactory(new AbstractElementHandlerFactory(ONTOLOGY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLOntologyHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(ANNOTATION) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLAnnotationElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(LITERAL) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLLiteralElementHandler(handler);
            }
        }, "Constant");


        addFactory(new AbstractElementHandlerFactory(IMPORT) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLImportsHandler(handler);
            }
        }, "Imports");


        addFactory(new AbstractElementHandlerFactory(CLASS) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLClassElementHandler(handler);
            }
        }, "OWLClass");

        addFactory(new AbstractElementHandlerFactory(ANNOTATION_PROPERTY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLAnnotationPropertyElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(ANNOTATION_PROPERTY_DOMAIN) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLAnnotationPropertyDomainElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(ANNOTATION_PROPERTY_RANGE) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLAnnotationPropertyRangeElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(SUB_ANNOTATION_PROPERTY_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLSubAnnotationPropertyOfElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectPropertyElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_INVERSE_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLInverseObjectPropertyElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATA_PROPERTY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataPropertyElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(NAMED_INDIVIDUAL) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLIndividualElementHandler(handler);
            }
        }, "Individual");


        addFactory(new AbstractElementHandlerFactory(DATA_COMPLEMENT_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataComplementOfElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATA_ONE_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataOneOfElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATATYPE) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDatatypeElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATATYPE_RESTRICTION) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDatatypeRestrictionElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATA_INTERSECTION_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataIntersectionOfElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATA_UNION_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataUnionOfElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(FACET_RESTRICTION) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDatatypeFacetRestrictionElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_INTERSECTION_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectIntersectionOfElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_UNION_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectUnionOfElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_COMPLEMENT_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectComplementOfElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_ONE_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectOneOfElementHandler(handler);
            }
        });

        // Object Restrictions

        addFactory(new AbstractElementHandlerFactory(OBJECT_SOME_VALUES_FROM) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectSomeValuesFromElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_ALL_VALUES_FROM) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectAllValuesFromElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_HAS_SELF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectExistsSelfElementHandler(handler);
            }
        }, "ObjectExistsSelf");

        addFactory(new AbstractElementHandlerFactory(OBJECT_HAS_VALUE) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectHasValueElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_MIN_CARDINALITY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectMinCardinalityElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_EXACT_CARDINALITY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectExactCardinalityElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_MAX_CARDINALITY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectMaxCardinalityElementHandler(handler);
            }
        });

        // Data Restrictions

        addFactory(new AbstractElementHandlerFactory(DATA_SOME_VALUES_FROM) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataSomeValuesFromElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATA_ALL_VALUES_FROM) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataAllValuesFromElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATA_HAS_VALUE) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataHasValueElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATA_MIN_CARDINALITY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataMinCardinalityElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATA_EXACT_CARDINALITY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataExactCardinalityElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATA_MAX_CARDINALITY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataMaxCardinalityElementHandler(handler);
            }
        });

        // Axioms

        addFactory(new AbstractElementHandlerFactory(SUB_CLASS_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLSubClassAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(EQUIVALENT_CLASSES) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLEquivalentClassesAxiomElementHandler(handler);
            }
        });


        addFactory(new AbstractElementHandlerFactory(DISJOINT_CLASSES) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDisjointClassesAxiomElementHandler(handler);
            }
        });


        addFactory(new AbstractElementHandlerFactory(DISJOINT_UNION) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDisjointUnionElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(UNION_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLUnionOfElementHandler(handler);
            }
        });


        addFactory(new AbstractElementHandlerFactory(SUB_OBJECT_PROPERTY_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLSubObjectPropertyOfAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY_CHAIN) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLSubObjectPropertyChainElementHandler(handler);
            }
        }, "SubObjectPropertyChain");

        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY_CHAIN) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLSubObjectPropertyChainElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(EQUIVALENT_OBJECT_PROPERTIES) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLEquivalentObjectPropertiesAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DISJOINT_OBJECT_PROPERTIES) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDisjointObjectPropertiesAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY_DOMAIN) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectPropertyDomainElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY_RANGE) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectPropertyRangeAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(INVERSE_OBJECT_PROPERTIES) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLInverseObjectPropertiesAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(FUNCTIONAL_OBJECT_PROPERTY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLFunctionalObjectPropertyAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(INVERSE_FUNCTIONAL_OBJECT_PROPERTY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLInverseFunctionalObjectPropertyAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(SYMMETRIC_OBJECT_PROPERTY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLSymmetricObjectPropertyAxiomElementHandler(handler);
            }
        });


        addFactory(new AbstractElementHandlerFactory(ASYMMETRIC_OBJECT_PROPERTY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLAsymmetricObjectPropertyElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(REFLEXIVE_OBJECT_PROPERTY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLReflexiveObjectPropertyAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(IRREFLEXIVE_OBJECT_PROPERTY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLIrreflexiveObjectPropertyAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(TRANSITIVE_OBJECT_PROPERTY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLTransitiveObjectPropertyAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(SUB_DATA_PROPERTY_OF) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLSubDataPropertyOfAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(EQUIVALENT_DATA_PROPERTIES) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLEquivalentDataPropertiesAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DISJOINT_DATA_PROPERTIES) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDisjointDataPropertiesAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATA_PROPERTY_DOMAIN) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataPropertyDomainAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATA_PROPERTY_RANGE) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataPropertyRangeAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(FUNCTIONAL_DATA_PROPERTY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLFunctionalDataPropertyAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(SAME_INDIVIDUAL) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLSameIndividualsAxiomElementHandler(handler);
            }
        }, "SameIndividuals");

        addFactory(new AbstractElementHandlerFactory(DIFFERENT_INDIVIDUALS) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDifferentIndividualsAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(CLASS_ASSERTION) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLClassAssertionAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY_ASSERTION) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLObjectPropertyAssertionAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(NEGATIVE_OBJECT_PROPERTY_ASSERTION) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLNegativeObjectPropertyAssertionAxiomElementHandler(handler);
            }
        });


        addFactory(new AbstractElementHandlerFactory(NEGATIVE_DATA_PROPERTY_ASSERTION) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLNegativeDataPropertyAssertionAxiomElementHandler(handler);
            }
        });


        addFactory(new AbstractElementHandlerFactory(DATA_PROPERTY_ASSERTION) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDataPropertyAssertionAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(ANNOTATION_ASSERTION) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLAnnotationAssertionElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory("EntityAnnotation") {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new LegacyEntityAnnotationElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DECLARATION) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDeclarationAxiomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(IRI_ELEMENT) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new IRIElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(ABBREVIATED_IRI_ELEMENT) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new AbbreviatedIRIElementHandler(handler);
            }
        });


        addFactory(new AbstractElementHandlerFactory(ANONYMOUS_INDIVIDUAL) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLAnonymousIndividualElementHandler(handler);
            }
        });


        addFactory(new AbstractElementHandlerFactory(HAS_KEY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLHasKeyElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATATYPE_DEFINITION) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new OWLDatatypeDefinitionElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DL_SAFE_RULE) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new SWRLRuleElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(BODY) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new SWRLAtomListElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(HEAD) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new SWRLAtomListElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(VARIABLE) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new SWRLVariableElementHandler(handler);
            }
        });


        addFactory(new AbstractElementHandlerFactory(CLASS_ATOM) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new SWRLClassAtomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(OBJECT_PROPERTY_ATOM) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new SWRLObjectPropertyAtomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DATA_PROPERTY_ATOM) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new SWRLDataPropertyAtomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(BUILT_IN_ATOM) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new SWRLBuiltInAtomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(DIFFERENT_INDIVIDUALS_ATOM) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new SWRLDifferentIndividualsAtomElementHandler(handler);
            }
        });

        addFactory(new AbstractElementHandlerFactory(SAME_INDIVIDUAL_ATOM) {
            public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
                return new SWRLSameIndividualAtomElementHandler(handler);
            }
        });
    }

    public OWLOntologyLoaderConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Gets the line number that the parser is at.
     *
     * @return A positive integer that represents the line number or
     *         -1 if the line number is not known.
     */
    public int getLineNumber() {
        if (locator != null) {
            return locator.getLineNumber();
        }
        else {
            return -1;
        }
    }

    public int getColumnNumber() {
        if (locator != null) {
            return locator.getColumnNumber();
        }
        else {
            return -1;
        }
    }

    private Map<String, IRI> iriMap = new HashMap<String, IRI>();

    public IRI getIRI(String iriStr) throws OWLParserException {
        try {
            IRI iri = iriMap.get(iriStr);
            if (iri == null) {
                URI uri = new URI(iriStr);
                if (!uri.isAbsolute()) {
                    URI base = getBase();
                    if (base == null)
                        throw new OWLXMLParserException("Unable to resolve relative URI", getLineNumber(), getColumnNumber());
//                    iri = IRI.create(getBase().resolve(uri));
                    iri = IRI.create(base + iriStr);
                }
                else {
                    iri = IRI.create(uri);
                }
                iriMap.put(iriStr, iri);
            }
            return iri;
        }
        catch (URISyntaxException e) {
            throw new OWLParserURISyntaxException(e, getLineNumber(), getColumnNumber());
        }
    }

    private String getNormalisedAbbreviatedIRI(String input) {
        if (input.indexOf(':') != -1) {
            return input;
        }
        else {
            return ":" + input;
        }
    }

    public IRI getAbbreviatedIRI(String abbreviatedIRI) throws OWLParserException {
        String normalisedAbbreviatedIRI = getNormalisedAbbreviatedIRI(abbreviatedIRI);
        int sepIndex = normalisedAbbreviatedIRI.indexOf(':');
        String prefixName = normalisedAbbreviatedIRI.substring(0, sepIndex + 1);
        String localName = normalisedAbbreviatedIRI.substring(sepIndex + 1);
        String base = prefixName2PrefixMap.get(prefixName);
        if (base == null) {
            throw new OWLXMLParserException("Prefix name not defined: " + prefixName, getLineNumber(), getColumnNumber());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(base);
        sb.append(localName);
        return getIRI(sb.toString());
    }


    public Map<String, String> getPrefixName2PrefixMap() {
        return prefixName2PrefixMap;
    }


    private void addFactory(OWLElementHandlerFactory factory, String... legacyElementNames) {
        handlerMap.put(factory.getElementName(), factory);
        for (String elementName : legacyElementNames) {
            handlerMap.put(elementName, factory);
        }
    }


    public OWLOntology getOntology() {
        return ontology;
    }


    public OWLDataFactory getDataFactory() {
        return getOWLOntologyManager().getOWLDataFactory();
    }


    @Override
	public void startDocument() throws SAXException {

    }


    @Override
	public void endDocument() throws SAXException {

    }


    @Override
	public void characters(char ch[], int start, int length) throws SAXException {
        if (!handlerStack.isEmpty()) {
            try {
                OWLElementHandler<?> handler = handlerStack.get(0);
                if (handler.isTextContentPossible()) {
                    handler.handleChars(ch, start, length);
                }
            }
            catch (OWLException e) {
                throw new SAXException(e);
            }
        }
    }


    @Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            processXMLBase(attributes);
            if (localName.equals(OWLXMLVocabulary.PREFIX.getShortName())) {
                String name = attributes.getValue(OWLXMLVocabulary.NAME_ATTRIBUTE.getShortName());
                String iriString = attributes.getValue(OWLXMLVocabulary.IRI_ATTRIBUTE.getShortName());
                if (name != null && iriString != null) {
                    if (name.endsWith(":")) {
                        prefixName2PrefixMap.put(name, iriString);
                    }
                    else {
                        prefixName2PrefixMap.put(name + ":", iriString);
                    }
                }
                return;
            }
            OWLElementHandlerFactory handlerFactory = handlerMap.get(localName);
            if (handlerFactory != null) {
                OWLElementHandler<?> handler = handlerFactory.createHandler(this);
                if (!handlerStack.isEmpty()) {
                    OWLElementHandler<?> topElement = handlerStack.get(0);
                    handler.setParentHandler(topElement);
                }
                handlerStack.add(0, handler);
                for (int i = 0; i < attributes.getLength(); i++) {
                    handler.attribute(attributes.getLocalName(i), attributes.getValue(i));
                }
                handler.startElement(localName);
            }
        }
        catch (OWLParserException e) {
            throw new TranslatedOWLParserException(e);
        }
    }

    protected void processXMLBase(Attributes attributes) {
        String base = attributes.getValue(Namespaces.XML.toString(), "base");
        if (base != null) {
            bases.push(URI.create(base));
        }
        else {
            bases.push(bases.peek());
        }
    }

    /**
     * Return the base URI for resolution of relative URIs
     *
     * @return base URI or null if unavailable (xml:base not present and the
     *         document locator does not provide a URI)
     */
    public URI getBase() {
        return bases.peek();
    }


    @Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
            if (localName.equals(OWLXMLVocabulary.PREFIX.getShortName())) {
                return;
            }
            if (!handlerStack.isEmpty()) {
                OWLElementHandler<?> handler = handlerStack.remove(0);
                handler.endElement();
            }
            bases.pop();
        }
        catch (OWLParserException e) {
            // Temporarily translate to a SAX parse exception
            throw new TranslatedOWLParserException(e);
        }
        catch (UnloadableImportException e) {
            // Temporarily translate to a SAX parse exception
            throw new TranslatedUnloadableImportException(e);
        }
    }


    @Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
        prefixName2PrefixMap.put(prefix, uri);
    }


    @Override
	public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
        return super.resolveEntity(publicId, systemId);
    }


    public OWLOntologyManager getOWLOntologyManager() {
        return owlOntologyManager;
    }
}
