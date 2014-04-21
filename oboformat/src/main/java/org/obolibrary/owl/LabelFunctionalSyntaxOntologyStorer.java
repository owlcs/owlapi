package org.obolibrary.owl;

import java.io.IOException;
import java.io.Writer;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.formats.LabelFunctionalFormat;
import org.semanticweb.owlapi.functional.renderer.FunctionalSyntaxObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import javax.annotation.Nonnull;

/** Implement the writer for {@link LabelFunctionalFormat}. */
public class LabelFunctionalSyntaxOntologyStorer extends
        AbstractOWLOntologyStorer {

    // generated
    private static final long serialVersionUID = 40000L;

    @Override
    public boolean canStoreOntology(@Nonnull OWLOntologyFormat ontologyFormat) {
        return ontologyFormat instanceof LabelFunctionalFormat;
    }

    @Override
    protected void storeOntology(@Nonnull OWLOntology ontology, @Nonnull Writer writer,
            OWLOntologyFormat format) throws OWLOntologyStorageException {
        try {
            FunctionalSyntaxObjectRenderer renderer = new FunctionalSyntaxObjectRenderer(
                    ontology, writer);
            renderer.setPrefixManager(new LabelPrefixManager(ontology));
            ontology.accept(renderer);
            writer.flush();
        } catch (IOException e) {
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
            OWLOntologyFormat ontologyFormat = ontology.getOWLOntologyManager()
                    .getOntologyFormat(ontology);
            if (ontologyFormat instanceof PrefixManager) {
                delegate = (PrefixManager) ontologyFormat;
            } else {
                delegate = new DefaultPrefixManager();
            }
        }

        @Override
        public String getPrefixIRI(@Nonnull IRI iri) {
            for (OWLAnnotationAssertionAxiom annotation : ontology
                    .getAnnotationAssertionAxioms(iri)) {
                if (annotation.getProperty().isLabel()) {
                    OWLAnnotationValue value = annotation.getValue();
                    if (value != null && value instanceof OWLLiteral) {
                        return "<" + ((OWLLiteral) value).getLiteral() + ">";
                    }
                }
            }
            return delegate.getPrefixIRI(iri);
        }

        @Nonnull
        @Override
        public String getDefaultPrefix() {
            return delegate.getDefaultPrefix();
        }

        @Override
        public boolean containsPrefixMapping(@Nonnull String prefixName) {
            return delegate.containsPrefixMapping(prefixName);
        }

        @Nonnull
        @Override
        public String getPrefix(@Nonnull String prefixName) {
            return delegate.getPrefix(prefixName);
        }

        @Nonnull
        @Override
        public Map<String, String> getPrefixName2PrefixMap() {
            return delegate.getPrefixName2PrefixMap();
        }

        @Nonnull
        @Override
        public IRI getIRI(@Nonnull String prefixIRI) {
            return delegate.getIRI(prefixIRI);
        }

        @Nonnull
        @Override
        public Set<String> getPrefixNames() {
            return delegate.getPrefixNames();
        }

        @Nonnull
        @Override
        public Comparator<String> getPrefixComparator() {
            return delegate.getPrefixComparator();
        }

        @Override
        public void setPrefixComparator(@Nonnull Comparator<String> comparator) {
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
