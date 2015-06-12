package org.obolibrary.macro;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.obolibrary.obo2owl.OWLAPIObo2Owl;
import org.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.OWLAPIConfigProvider;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl;
import org.semanticweb.owlapi.manchestersyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.IRIShortFormProvider;
import org.semanticweb.owlapi.util.OntologyAxiomPair;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleIRIShortFormProvider;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * wrapper for parsing Manchester Syntax.
 * 
 * @author heiko
 */
public class ManchesterSyntaxTool {

    private static final Logger LOG = LoggerFactory.getLogger(ManchesterSyntaxTool.class);
    @Nonnull
    protected final IRIShortFormProvider iriShortFormProvider = new SimpleIRIShortFormProvider();
    @Nonnull
    private final OWLDataFactory dataFactory;
    @Nonnull
    private final AdvancedEntityChecker entityChecker;
    @Nonnull
    private final ShortFormProvider shortFormProvider = new ShortFormProvider() {

        @Override
        public void dispose() {
            // do nothing
        }

        @Override
        public String getShortForm(@Nonnull OWLEntity entity) {
            return iriShortFormProvider.getShortForm(entity.getIRI());
        }
    };
    private final AtomicBoolean disposed = new AtomicBoolean(false);

    /**
     * Create a new parser instance for the given ontology. By default, this
     * parser will also try to resolve OWLObjects via their identifier or
     * rdfs:label.
     * 
     * @param inputOntology
     *        inputOntology
     */
    public ManchesterSyntaxTool(@Nonnull OWLOntology inputOntology) {
        this(inputOntology, null);
    }

    /**
     * Create a new parser instance for the given ontologies. By default, this
     * parser will also try to resolve OWLObjects via their identifier or
     * rdfs:label.
     * 
     * @param inputOntology
     *        inputOntology
     * @param auxiliaryOntologies
     *        set of additional ontologies or null
     */
    public ManchesterSyntaxTool(@Nonnull OWLOntology inputOntology,
        @Nullable Collection<OWLOntology> auxiliaryOntologies) {
        OWLOntologyManager manager = inputOntology.getOWLOntologyManager();
        dataFactory = manager.getOWLDataFactory();
        Set<OWLOntology> ontologies = new HashSet<>(inputOntology.getImportsClosure());
        if (auxiliaryOntologies != null && !auxiliaryOntologies.isEmpty()) {
            for (OWLOntology auxOnt : auxiliaryOntologies) {
                ontologies.addAll(auxOnt.getImportsClosure());
            }
        }
        ShortFormEntityChecker defaultInstance = new ShortFormEntityChecker(new BidirectionalShortFormProviderAdapter(
            manager, ontologies, shortFormProvider));
        entityChecker = new AdvancedEntityChecker(defaultInstance, ontologies, inputOntology.getOWLOntologyManager());
    }

    /**
     * Parse frame expressions in Manchester syntax.
     * 
     * @param expression
     *        expression
     * @return set of {@link OntologyAxiomPair}
     * @throws ParserException
     *         parser exception
     */
    public Set<OntologyAxiomPair> parseManchesterExpressionFrames(@Nonnull String expression) {
        ManchesterOWLSyntaxParser parser = createParser(expression);
        return parser.parseFrames();
    }

    /**
     * Parse a class expression in Manchester syntax.
     * 
     * @param expression
     *        expression
     * @return {@link OWLClassExpression}
     * @throws ParserException
     *         parser exception
     */
    public OWLClassExpression parseManchesterExpression(@Nonnull String expression) {
        ManchesterOWLSyntaxParser parser = createParser(expression);
        return parser.parseClassExpression();
    }

    @Nonnull
    private ManchesterOWLSyntaxParser createParser(@Nonnull String expression) {
        if (disposed.get()) {
            throw new OWLRuntimeException("Illegal State: Trying to use an disposed instance.");
        }
        ManchesterOWLSyntaxParser parser = new ManchesterOWLSyntaxParserImpl(new OWLAPIConfigProvider(), dataFactory);
        parser.setStringToParse(expression);
        parser.setOWLEntityChecker(entityChecker);
        LOG.info("parsing: {}", expression);
        return parser;
    }

    /**
     * Translate the {@link IRI} into the short form as expected by the parser.
     * 
     * @param iri
     *        iri
     * @return short form
     */
    public String getId(@Nonnull IRI iri) {
        if (disposed.get()) {
            throw new OWLRuntimeException("Illegal State: Trying to use an disposed instance.");
        }
        return iriShortFormProvider.getShortForm(iri);
    }

    /**
     * Translate the {@link OWLEntity} identifier into the short form as
     * expected by the parser.
     * 
     * @param entity
     *        entity
     * @return short form
     */
    public String getId(@Nonnull OWLEntity entity) {
        if (disposed.get()) {
            throw new OWLRuntimeException("Illegal State: Trying to use an disposed instance.");
        }
        return shortFormProvider.getShortForm(entity);
    }

    /**
     * {@link OWLEntityChecker} which additionally checks for corresponding
     * identifiers and labels to retrieve entities. The intended behavior is
     * specified as follows:
     * <ul>
     * <li>If the string is enclosed with matching single quotes, try to resolve
     * as label</li>
     * <li>Otherwise, try to resolve as identifier</li>
     * </ul>
     */
    static class AdvancedEntityChecker implements OWLEntityChecker {

        private final OWLEntityChecker defaultInstance;
        private final Set<OWLOntology> ontologies;
        private final OWLOntologyManager manager;

        /**
         * @param defaultInstance
         *        defaultInstance
         * @param ontologies
         *        ontologies
         * @param manager
         *        manager
         */
        AdvancedEntityChecker(OWLEntityChecker defaultInstance, Set<OWLOntology> ontologies,
            OWLOntologyManager manager) {
            this.defaultInstance = defaultInstance;
            this.ontologies = ontologies;
            this.manager = manager;
        }

        @Nullable
        @Override
        public OWLClass getOWLClass(@Nonnull String name) {
            OWLClass owlClass = defaultInstance.getOWLClass(name);
            if (owlClass == null) {
                IRI iri = getIRI(name);
                if (iri != null) {
                    owlClass = getOWLClass(iri);
                }
            }
            return owlClass;
        }

        @Nullable
        @Override
        public OWLObjectProperty getOWLObjectProperty(@Nonnull String name) {
            OWLObjectProperty owlObjectProperty = defaultInstance.getOWLObjectProperty(name);
            if (owlObjectProperty == null) {
                IRI iri = getIRI(name);
                if (iri != null) {
                    owlObjectProperty = getOWLObjectProperty(iri);
                }
            }
            return owlObjectProperty;
        }

        @Override
        public OWLDataProperty getOWLDataProperty(String name) {
            return defaultInstance.getOWLDataProperty(name);
        }

        @Nullable
        @Override
        public OWLNamedIndividual getOWLIndividual(@Nonnull String name) {
            OWLNamedIndividual owlIndividual = defaultInstance.getOWLIndividual(name);
            if (owlIndividual == null) {
                IRI iri = getIRI(name);
                if (iri != null) {
                    owlIndividual = getOWLIndividual(iri);
                }
            }
            return owlIndividual;
        }

        @Override
        public OWLDatatype getOWLDatatype(String name) {
            return defaultInstance.getOWLDatatype(name);
        }

        @Override
        public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
            return defaultInstance.getOWLAnnotationProperty(name);
        }

        @Nullable
        IRI getIRI(@Nonnull String name) {
            if (isQuoted(name)) {
                // anything in '....' quotes is a label
                return getIRIByLabel(name.substring(1, name.length() - 1));
            }
            if (name.length() > 2 && name.charAt(0) == '<' && name.charAt(name.length() - 1) == '>') {
                // anything between <...> brackets is a complete IRI
                return IRI.create(name.substring(1, name.length() - 1));
            }
            return getIRIByIdentifier(name);
        }

        private static boolean isQuoted(@Nonnull String s) {
            int length = s.length();
            if (length >= 2) {
                return s.charAt(0) == '\'' && s.charAt(length - 1) == '\'';
            }
            return false;
        }

        @Nullable
        IRI getIRIByIdentifier(@Nonnull String id) {
            OWLAPIObo2Owl b = new OWLAPIObo2Owl(manager);
            b.setObodoc(new OBODoc());
            return b.oboIdToIRI(id);
        }

        /**
         * Retrieve an {@link IRI} by rdfs:label.
         * 
         * @param label
         *        label
         * @return {@link IRI} or null
         */
        @Nullable
        IRI getIRIByLabel(@Nonnull String label) {
            for (OWLOntology o : ontologies) {
                Set<OWLAnnotationAssertionAxiom> aas = o.getAxioms(AxiomType.ANNOTATION_ASSERTION);
                for (OWLAnnotationAssertionAxiom aa : aas) {
                    OWLAnnotationValue v = aa.getValue();
                    OWLAnnotationProperty property = aa.getProperty();
                    if (isMatchingLabel(label, v, property)) {
                        OWLAnnotationSubject obj = aa.getSubject();
                        if (obj instanceof IRI) {
                            return (IRI) obj;
                        }
                    }
                }
            }
            return null;
        }

        /**
         * @param label
         *        label to match
         * @param v
         *        annotation value
         * @param property
         *        property to check
         * @return true if property is a label, v is a literal and v matches
         *         label
         */
        protected boolean isMatchingLabel(String label, OWLAnnotationValue v, OWLAnnotationProperty property) {
            return property.isLabel() && v instanceof OWLLiteral && label.equals(((OWLLiteral) v).getLiteral());
        }

        /**
         * Retrieve the {@link OWLClass} for a given {@link IRI}, if it has at
         * least one {@link OWLDeclarationAxiom}.
         * 
         * @param iri
         *        iri
         * @return {@link OWLClass} or null
         */
        @Nullable
        OWLClass getOWLClass(@Nonnull IRI iri) {
            for (OWLOntology o : ontologies) {
                OWLClass c = o.getOWLOntologyManager().getOWLDataFactory().getOWLClass(iri);
                if (!o.getDeclarationAxioms(c).isEmpty()) {
                    return c;
                }
                if (o.getOWLOntologyManager().getOWLDataFactory().getOWLNothing().equals(c)) {
                    return c;
                }
            }
            return null;
        }

        /**
         * Retrieve the {@link OWLNamedIndividual} for a given {@link IRI}, if
         * it has at least one corresponding {@link OWLDeclarationAxiom}.
         * 
         * @param iri
         *        iri
         * @return {@link OWLNamedIndividual} or null
         */
        @Nullable
        OWLNamedIndividual getOWLIndividual(@Nonnull IRI iri) {
            for (OWLOntology o : ontologies) {
                OWLNamedIndividual c = o.getOWLOntologyManager().getOWLDataFactory().getOWLNamedIndividual(iri);
                for (OWLDeclarationAxiom da : o.getDeclarationAxioms(c)) {
                    if (da.getEntity() instanceof OWLNamedIndividual) {
                        return (OWLNamedIndividual) da.getEntity();
                    }
                }
            }
            return null;
        }

        /**
         * Retrieve the {@link OWLObjectProperty} for a given {@link IRI}, if it
         * has at least one {@link OWLDeclarationAxiom}.
         * 
         * @param iri
         *        iri
         * @return {@link OWLObjectProperty} or null
         */
        @Nullable
        OWLObjectProperty getOWLObjectProperty(@Nonnull IRI iri) {
            for (OWLOntology o : ontologies) {
                OWLObjectProperty p = o.getOWLOntologyManager().getOWLDataFactory().getOWLObjectProperty(iri);
                if (!o.getDeclarationAxioms(p).isEmpty()) {
                    return p;
                }
            }
            return null;
        }
    }

    /**
     * Call this method to dispose the internal data structures. This will
     * remove also the listeners registered with the ontology manager.
     */
    public void dispose() {
        if (!disposed.getAndSet(true)) {
            shortFormProvider.dispose();
        }
    }
}
