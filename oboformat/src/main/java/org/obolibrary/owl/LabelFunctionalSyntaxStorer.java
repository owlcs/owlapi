package org.obolibrary.owl;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.semanticweb.owlapi.formats.LabelFunctionalDocumentFormat;
import org.semanticweb.owlapi.functional.renderer.FunctionalSyntaxObjectRenderer;
import org.semanticweb.owlapi.io.OWLStorer;
import org.semanticweb.owlapi.io.OWLStorerParameters;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.IRIShortFormProvider;
import org.semanticweb.owlapi.util.PrefixManagerImpl;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.StringComparator;

/**
 * Implement the writer for {@link LabelFunctionalDocumentFormat}.
 */
@ParametersAreNonnullByDefault
public class LabelFunctionalSyntaxStorer implements OWLStorer {

    @Override
    public boolean canStoreOntology(OWLDocumentFormat ontologyFormat) {
        return ontologyFormat instanceof LabelFunctionalDocumentFormat;
    }

    @Override
    public void storeOntology(OWLOntology ontology, PrintWriter writer, OWLDocumentFormat format,
        OWLStorerParameters storerParameters) throws OWLOntologyStorageException {
        try {
            FunctionalSyntaxObjectRenderer renderer =
                new FunctionalSyntaxObjectRenderer(ontology, writer);
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
        public String getShortForm(OWLEntity entity) {
            return getShortForm(entity.getIRI());
        }

        @Override
        @Nullable
        public String getPrefixIRI(IRI iri) {
            for (OWLAnnotationAssertionAxiom annotation : asList(
                ontology.annotationAssertionAxioms(iri))) {
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
        public IRI getIRI(String prefixIRI) {
            return delegate.getIRI(prefixIRI);
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
