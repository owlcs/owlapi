package org.obolibrary.macro;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.coode.owlapi.manchesterowlsyntax.OntologyAxiomPair;
import org.obolibrary.obo2owl.Obo2Owl;
import org.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.IRIShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleIRIShortFormProvider;


/**
 * wrapper for parsing Manchester Syntax
 * 
 * @author heiko
 *
 */
public class ManchesterSyntaxTool {

    private static final Logger log = Logger.getLogger(ManchesterSyntaxTool.class
            .getName());

	private IRIShortFormProvider iriShortFormProvider;
	private OWLDataFactory dataFactory;
	private OWLEntityChecker entityChecker;
	private ShortFormProvider shortFormProvider;
	private BidirectionalShortFormProviderAdapter bidirectionalShortFormProvider;
	private volatile boolean disposed = false; 
	private Object disposedLock = new Object();

	/**
	 * Create a new parser instance for the given ontology. By 
	 * default, this parser will also try to resolve OWLObjects 
	 * via their identifier or rdfs:label.
	 * 
	 * @param inputOntology
	 */
	public ManchesterSyntaxTool(OWLOntology inputOntology) {
		this(inputOntology, null, true);
	}

	/**
	 * Create a new parser instance for the given ontologies. By 
	 * default, this parser will also try to resolve OWLObjects 
	 * via their identifier or rdfs:label.
	 * 
	 * @param inputOntology
	 * @param auxiliaryOntologies set of additional ontologies or null
	 */
	public ManchesterSyntaxTool(OWLOntology inputOntology, Collection<OWLOntology> auxiliaryOntologies) {
		this(inputOntology, auxiliaryOntologies, true);
	}

	/**
	 * Create a new parser instance for the given ontologies.
	 * 
	 * @param inputOntology
	 * @param auxiliaryOntologies set of additional ontologies or null
	 * @param resolveEntities set to true, to enable resolution of OWLObjects via their identifier or rdfs:label
	 */
	public ManchesterSyntaxTool(OWLOntology inputOntology, Collection<OWLOntology> auxiliaryOntologies, boolean resolveEntities) {
		super();
		OWLOntologyManager manager = inputOntology.getOWLOntologyManager();
		dataFactory = manager.getOWLDataFactory();

		Set<OWLOntology> ontologies;
		if (auxiliaryOntologies != null && !auxiliaryOntologies.isEmpty()) {
			ontologies = new HashSet<OWLOntology>();
			ontologies.addAll(inputOntology.getImportsClosure());
			for (OWLOntology auxOnt : auxiliaryOntologies) {
				ontologies.addAll(auxOnt.getImportsClosure());
			}
		}
		else {
			ontologies = inputOntology.getImportsClosure();
		}

		// re-use the same short form provider for translation and parsing 
		iriShortFormProvider = new SimpleIRIShortFormProvider();
		shortFormProvider = new ShortFormProvider() {

			public void dispose() {
				// do nothing
			}

			public String getShortForm(OWLEntity owlEntity) {
				return iriShortFormProvider.getShortForm(owlEntity.getIRI());
			}
		};

		bidirectionalShortFormProvider = new BidirectionalShortFormProviderAdapter(manager,
				ontologies, shortFormProvider);
		final ShortFormEntityChecker defaultInstance = new ShortFormEntityChecker(
				bidirectionalShortFormProvider);
		if (resolveEntities) {
            entityChecker = new AdvancedEntityChecker(defaultInstance, ontologies,
                    inputOntology.getOWLOntologyManager());
		}
		else {
			entityChecker = defaultInstance;
		}
	}

	/**
	 * Parse frame expressions in Manchester syntax. 
	 * 
	 * @param expression
	 * @return set of {@link OntologyAxiomPair}
	 * @throws ParserException
	 */
	public Set<OntologyAxiomPair> parseManchesterExpressionFrames(String expression) throws ParserException {

		ManchesterOWLSyntaxEditorParser parser = createParser(expression);
		Set<OntologyAxiomPair> set =  parser.parseFrames();
		return set;
	}

	/**
	 * Parse a class expression in Manchester syntax. 
	 * 
	 * @param expression
	 * @return {@link OWLClassExpression}
	 * @throws ParserException
	 */
	public OWLClassExpression parseManchesterExpression(String expression) throws ParserException {

		ManchesterOWLSyntaxEditorParser parser = createParser(expression);
		OWLClassExpression ce = parser.parseClassExpression();
		return ce;
	}


	private ManchesterOWLSyntaxEditorParser createParser(String expression) {
		synchronized (disposedLock) {
			if (disposed) {
				throw new RuntimeException("Illegal State: Trying to use an disposed instance.");
			}
		}
		ManchesterOWLSyntaxEditorParser parser = 
			new ManchesterOWLSyntaxEditorParser(dataFactory, expression);

		parser.setOWLEntityChecker(entityChecker);

        if (log.isLoggable(Level.WARNING)) {
            log.log(Level.WARNING, "parsing:" + expression);
        }
		return parser;
	}

	/**
	 * Translate the {@link IRI} into the short form as expected by the parser.
	 * 
	 * @param iri
	 * @return short form
	 */
	public String getId(IRI iri){
		synchronized (disposedLock) {
			if (disposed) {
				throw new RuntimeException("Illegal State: Trying to use an disposed instance.");
			}
		}
		return iriShortFormProvider.getShortForm(iri);

	}

	/**
	 * Translate the {@link OWLEntity} identifier into the short form as 
	 * expected by the parser.
	 * 
	 * @param entity
	 * @return short form
	 */
	public String getId(OWLEntity entity) {
		synchronized (disposedLock) {
			if (disposed) {
				throw new RuntimeException("Illegal State: Trying to use an disposed instance.");
			}
		}
		return shortFormProvider.getShortForm(entity);
	}

	/**
	 * {@link OWLEntityChecker} which additionally checks for 
	 * corresponding identifiers and labels to retrieve entities.
	 * 
	 * The intended behavior is specified as follows:
	 * <ul>
	 *  <li>If the string is enclosed with matching single quotes, try to resolve as label</li>
	 *  <li>Otherwise, try to resolve as identifier</li>
	 * </ul>
	 */
	static class AdvancedEntityChecker implements OWLEntityChecker {

		private final OWLEntityChecker defaultInstance;
		private final Set<OWLOntology> ontologies;
        private OWLOntologyManager manager;

		/**
		 * @param defaultInstance
		 * @param ontologies
		 */
        AdvancedEntityChecker(OWLEntityChecker defaultInstance,
                Set<OWLOntology> ontologies, OWLOntologyManager manager) {
			super();
			this.defaultInstance = defaultInstance;
			this.ontologies = ontologies;
            this.manager = manager;
		}

		public OWLClass getOWLClass(String name) {
			OWLClass owlClass = defaultInstance.getOWLClass(name);
			if (owlClass == null) {
				IRI iri = getIRI(name);
				if (iri != null) {
					owlClass = getOWLClass(iri);
					if (owlClass == null) {

					}
				}
			}
			return owlClass;
		}

		public OWLObjectProperty getOWLObjectProperty(String name) {
			OWLObjectProperty owlObjectProperty = defaultInstance.getOWLObjectProperty(name);
			if (owlObjectProperty == null) {
				IRI iri = getIRI(name);
				if (iri != null) {
					owlObjectProperty = getOWLObjectProperty(iri);
				}
			}
			return owlObjectProperty;
		}

		public OWLDataProperty getOWLDataProperty(String name) {
			return defaultInstance.getOWLDataProperty(name);
		}

		public OWLNamedIndividual getOWLIndividual(String name) {
			OWLNamedIndividual owlIndividual = defaultInstance.getOWLIndividual(name);
			if (owlIndividual == null) {
				IRI iri = getIRI(name);
				if (iri != null) {
					owlIndividual = getOWLIndividual(iri);
				}
			}
			return owlIndividual;
		}

		public OWLDatatype getOWLDatatype(String name) {
			return defaultInstance.getOWLDatatype(name);
		}

		public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
			return defaultInstance.getOWLAnnotationProperty(name);
		}

		IRI getIRI(String name) {
			if (isQuoted(name)) {
				// anything in '....' quotes is a label
				return getIRIByLabel(name.substring(1, name.length() - 1));
			}
			if (name.length() > 2 && name.charAt(0) == '<' && name.charAt(name.length() -1 ) == '>') {
				// anything between <...> brackets is a complete IRI
				return IRI.create(name.substring(1, name.length() - 1));
			}
			return getIRIByIdentifier(name);
		}

		private boolean isQuoted(String s) {
			final int length = s.length();
			if (length > 2) {
				return s.charAt(0) == '\'' && s.charAt(length - 1) == '\'';
			}
			return false;
		}

		IRI getIRIByIdentifier(String id) {
            Obo2Owl b = new Obo2Owl(manager);
			b.setObodoc(new OBODoc());
			return b.oboIdToIRI(id);
		}

		/**
		 * Retrieve an {@link IRI} by rdfs:label
		 * 
		 * @param label
		 * @return {@link IRI} or null
		 */
		IRI getIRIByLabel(String label) {
			IRI iri = null;
			for (OWLOntology o : ontologies) {
				Set<OWLAnnotationAssertionAxiom> aas = o.getAxioms(AxiomType.ANNOTATION_ASSERTION);
				for (OWLAnnotationAssertionAxiom aa : aas) {
					OWLAnnotationValue v = aa.getValue();
					OWLAnnotationProperty property = aa.getProperty();
					if (v instanceof OWLLiteral && property.isLabel()) {
						if (label.equals( ((OWLLiteral)v).getLiteral())) {
							OWLAnnotationSubject obj = aa.getSubject();
							if (obj instanceof IRI) {
								return (IRI)obj;
							}
						}
					}
				}
			}
			return iri;
		}

		/**
		 * Retrieve the {@link OWLClass} for a given {@link IRI}, if it has 
		 * at least one {@link OWLDeclarationAxiom}.
		 * 
		 * @param iri
		 * @return {@link OWLClass} or null
		 */
		OWLClass getOWLClass(IRI iri) {
			for(OWLOntology o : ontologies) {
				OWLClass c = o.getOWLOntologyManager().getOWLDataFactory().getOWLClass(iri);
				if (o.getDeclarationAxioms(c).size() > 0) {
					return c;
				}
				if (o.getOWLOntologyManager().getOWLDataFactory().getOWLNothing().equals(c)) {
					return c;
				}
			}
			return null;
		}

		/**
		 * Retrieve the {@link OWLNamedIndividual} for a given {@link IRI}, 
		 * if it has at least one corresponding {@link OWLDeclarationAxiom}.
		 * 
		 * @param iri
		 * @return {@link OWLNamedIndividual} or null
		 */
		OWLNamedIndividual getOWLIndividual(IRI iri) {
			for (OWLOntology o : ontologies) {
				OWLDataFactory dataFactory = o.getOWLOntologyManager().getOWLDataFactory();
				OWLNamedIndividual c = dataFactory.getOWLNamedIndividual(iri);
				for (OWLDeclarationAxiom da : o.getDeclarationAxioms(c)) {
					if (da.getEntity() instanceof OWLNamedIndividual) {
						return (OWLNamedIndividual) da.getEntity();
					}
				}
			}
			return null;
		}

		/**
		 * Retrieve the {@link OWLObjectProperty} for a given {@link IRI}, 
		 * if it has at least one {@link OWLDeclarationAxiom}.
		 * 
		 * @param iri
		 * @return {@link OWLObjectProperty} or null
		 */
		OWLObjectProperty getOWLObjectProperty(IRI iri) {
			for (OWLOntology o : ontologies) {
				OWLDataFactory dataFactory = o.getOWLOntologyManager().getOWLDataFactory();
				OWLObjectProperty p = dataFactory.getOWLObjectProperty(iri);
				if (o.getDeclarationAxioms(p).size() > 0) {
					return p;
				}
			}
			return null;
		}
	}

	/**
	 * Call this method to dispose the internal data structures. 
	 * This will remove also the listeners registered with the ontology manager.
	 */
	public void dispose() {
		synchronized (disposedLock) {
			if (!disposed) {
				disposed = true;
				iriShortFormProvider = null;
				dataFactory = null;
				entityChecker = null;
				shortFormProvider.dispose();
				shortFormProvider = null;
				bidirectionalShortFormProvider.dispose();
				bidirectionalShortFormProvider = null;
			}
		}
	}
}
