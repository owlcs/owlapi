package org.obolibrary.owl;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.formats.LabelFunctionalDocumentFormat;
import org.semanticweb.owlapi.functional.renderer.FunctionalSyntaxObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.AbstractOWLStorer;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.StringComparator;

/** Implement the writer for {@link LabelFunctionalDocumentFormat}. */
public class LabelFunctionalSyntaxStorer extends AbstractOWLStorer {

    // generated
    private static final long serialVersionUID = 40000L;

    @Override
    public boolean canStoreOntology(OWLDocumentFormat ontologyFormat) {
        return ontologyFormat instanceof LabelFunctionalDocumentFormat;
    }

    @Override
    protected void storeOntology(@Nonnull OWLOntology ontology,
            @Nonnull PrintWriter writer, OWLDocumentFormat format)
            throws OWLOntologyStorageException {
        try {
            FunctionalSyntaxObjectRenderer renderer = new FunctionalSyntaxObjectRenderer(
                    ontology, writer);
            renderer.setPrefixManager(new LabelPrefixManager(ontology));
            ontology.accept(renderer);
            writer.flush();
        } catch (OWLRuntimeException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    static class LabelPrefixManager implements PrefixManager {

        private static final long serialVersionUID = 40000L;
        @Nonnull
        private final OWLOntology ontology;
        @Nonnull
        private final PrefixManager delegate;

        LabelPrefixManager(@Nonnull OWLOntology ontology) {
            this.ontology = ontology;
            OWLDocumentFormat ontologyFormat = ontology.getOWLOntologyManager()
                    .getOntologyFormat(ontology);
            if (ontologyFormat instanceof PrefixManager) {
                delegate = (PrefixManager) ontologyFormat;
            } else {
                delegate = new DefaultPrefixManager();
            }
        }

        @Override
        public String getPrefixIRI(IRI iri) {
            for (OWLAnnotationAssertionAxiom annotation : asList(ontology
                    .annotationAssertionAxioms(iri))) {
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
        public String getDefaultPrefix() {
            return delegate.getDefaultPrefix();
        }

        @Override
        public boolean containsPrefixMapping(String prefixName) {
            return delegate.containsPrefixMapping(prefixName);
        }

        @Override
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
        public void setPrefixComparator(StringComparator comparator) {
            delegate.setPrefixComparator(comparator);
        }

        @Override
        public void setDefaultPrefix(String defaultPrefix) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
        }

        @Override
        public void setPrefix(String prefixName, String prefix) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
        }

        @Override
        public void copyPrefixesFrom(PrefixManager from) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
        }

        @Override
        public void copyPrefixesFrom(Map<String, String> from) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
        }

        @Override
        public void unregisterNamespace(String namespace) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
        }

        @Override
        public void clear() {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
        }
    }
}
