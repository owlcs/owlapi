package org.obolibrary.owl;

import java.io.IOException;
import java.io.Writer;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

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

/** Implement the writer for {@link LabelFunctionalFormat}. */
public class LabelFunctionalSyntaxOntologyStorer extends
        AbstractOWLOntologyStorer {

    // generated
    private static final long serialVersionUID = 40000L;

    @Override
    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat instanceof LabelFunctionalFormat;
    }

    @Override
    protected void storeOntology(@Nonnull OWLOntology ontology,
            @Nonnull Writer writer,
            @SuppressWarnings("unused") OWLOntologyFormat format)
            throws OWLOntologyStorageException {
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
        public String getPrefixIRI(IRI iri) {
            for (OWLAnnotationAssertionAxiom annotation : ontology
                    .getAnnotationAssertionAxioms(iri)) {
                if (annotation.getProperty().isLabel()) {
                    OWLAnnotationValue value = annotation.getValue();
                    if (value instanceof OWLLiteral) {
                        return "<" + ((OWLLiteral) value).getLiteral() + ">";
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
        public Set<String> getPrefixNames() {
            return delegate.getPrefixNames();
        }

        @Override
        public Comparator<String> getPrefixComparator() {
            return delegate.getPrefixComparator();
        }

        @Override
        public void setPrefixComparator(Comparator<String> comparator) {
            delegate.setPrefixComparator(comparator);
        }

        @SuppressWarnings("unused")
        @Override
        public void setDefaultPrefix(String defaultPrefix) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
        }

        @SuppressWarnings("unused")
        @Override
        public void setPrefix(String prefixName, String prefix) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
        }

        @SuppressWarnings("unused")
        @Override
        public void copyPrefixesFrom(PrefixManager from) {
            // do not propagate changes to the original manager
            // there should be no changes during rendering anyway
        }

        @SuppressWarnings("unused")
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
