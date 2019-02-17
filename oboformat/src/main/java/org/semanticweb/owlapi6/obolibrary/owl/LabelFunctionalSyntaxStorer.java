package org.semanticweb.owlapi6.obolibrary.owl;

import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;

import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.formats.LabelFunctionalDocumentFormat;
import org.semanticweb.owlapi6.functional.renderer.FunctionalSyntaxObjectRenderer;
import org.semanticweb.owlapi6.io.OWLStorer;
import org.semanticweb.owlapi6.io.OWLStorerParameters;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationValue;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.utilities.IRIShortFormProvider;
import org.semanticweb.owlapi6.utilities.PrefixManagerImpl;
import org.semanticweb.owlapi6.utilities.ShortFormProvider;
import org.semanticweb.owlapi6.utilities.StringComparator;

/**
 * Implement the writer for {@link LabelFunctionalDocumentFormat}.
 */
public class LabelFunctionalSyntaxStorer implements OWLStorer {

    @Override
    public boolean canStoreOntology(OWLDocumentFormat ontologyFormat) {
        return ontologyFormat instanceof LabelFunctionalDocumentFormat;
    }

    @Override
    public void storeOntology(OWLOntology ontology, PrintWriter writer, OWLDocumentFormat format,
        OWLStorerParameters storerParameters) throws OWLOntologyStorageException {
        try {
            FunctionalSyntaxObjectRenderer renderer = new FunctionalSyntaxObjectRenderer(ontology, writer);
            renderer.setPrefixManager(new LabelPrefixManager(ontology));
            ontology.accept(renderer);
            writer.flush();
        } catch (OWLRuntimeException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    static class LabelPrefixManager implements PrefixManager {

        private final OWLOntology ontology;
        private final PrefixManager delegate;

        LabelPrefixManager(OWLOntology ontology) {
            this.ontology = ontology;
            OWLDocumentFormat ontologyFormat = ontology.getFormat();
            if (ontologyFormat instanceof PrefixManager) {
                delegate = (PrefixManager) ontologyFormat;
            } else {
                delegate = new PrefixManagerImpl();
            }
        }

        @Override
        public PrefixManager setIRIShortFormProvider(IRIShortFormProvider isfp) {
            delegate.setIRIShortFormProvider(isfp);
            return this;
        }

        @Override
        public PrefixManager setShortFormProvider(ShortFormProvider sfp) {
            delegate.setShortFormProvider(sfp);
            return this;
        }

        @Override
        public String getShortForm(IRI iri) {
            String sf = getPrefixIRI(iri);
            if (sf == null) {
                return iri.toQuotedString();
            } else {
                return sf;
            }
        }

        @Override
        public String getShortForm(String iri) {
            return getShortForm(ontology.getOWLOntologyManager().getOWLDataFactory().getIRI(iri));
        }

        @Override
        public String getShortForm(OWLEntity entity) {
            return getShortForm(entity.getIRI());
        }

        @Override
        @Nullable
        public String getPrefixIRI(IRI iri) {
            for (OWLAnnotationAssertionAxiom annotation : asList(ontology.annotationAssertionAxioms(iri))) {
                if (annotation.getProperty().isLabel()) {
                    OWLAnnotationValue value = annotation.getValue();
                    if (value instanceof OWLLiteral) {
                        return '<' + ((OWLLiteral) value).getLiteral() + '>';
                    }
                }
            }
            return delegate.getPrefixIRI(iri);
        }

        @Override
        @Nullable
        public String getDefaultPrefix() {
            return delegate.getDefaultPrefix();
        }

        @Override
        public PrefixManager withDefaultPrefix(@Nullable String defaultPrefix) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
            return this;
        }

        @Override
        public String getPrefixIRIIgnoreQName(IRI iri) {
            return ontology.annotationAssertionAxioms(iri).filter(a -> a.getProperty().isLabel())
                .map(OWLAnnotationAssertionAxiom::getValue)
                // pick rdfs:label literal values
                .filter(OWLLiteral.class::isInstance).findAny().map(v -> OWLLiteral.class.cast(v))
                // if there is at least one literal label, use it
                .map(v -> '<' + v.getLiteral() + '>')
                // else delegate
                .orElse(delegate.getPrefixIRIIgnoreQName(iri));
        }

        @Override
        public boolean containsPrefixMapping(String prefixName) {
            return delegate.containsPrefixMapping(prefixName);
        }

        @Override
        @Nullable
        public String getPrefix(String prefixName) {
            return delegate.getPrefix(prefixName);
        }

        @Override
        public Map<String, String> getPrefixName2PrefixMap() {
            return delegate.getPrefixName2PrefixMap();
        }

        @Override
        public IRI getIRI(String prefixIRI, OWLDataFactory df) {
            return delegate.getIRI(prefixIRI, df);
        }

        @Override
        public Stream<String> prefixNames() {
            return delegate.prefixNames();
        }

        @Override
        public StringComparator getPrefixComparator() {
            return delegate.getPrefixComparator();
        }

        @Override
        public PrefixManager withPrefixComparator(StringComparator comparator) {
            return delegate.withPrefixComparator(comparator);
        }

        @Override
        public PrefixManager withPrefix(String prefixName, String prefix) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
            return this;
        }

        @Override
        public PrefixManager copyPrefixesFrom(PrefixManager from) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
            return this;
        }

        @Override
        public PrefixManager copyPrefixesFrom(Map<String, String> from) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
            return this;
        }

        @Override
        public PrefixManager unregisterNamespace(String namespace) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
            return this;
        }

        @Override
        public PrefixManager clear() {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
            return this;
        }
    }
}
