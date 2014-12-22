package org.obolibrary.owl;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import org.coode.owlapi.functionalrenderer.OWLObjectRenderer;
import org.semanticweb.owlapi.formats.LabelFunctionalDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

/** Implement the writer for {@link LabelFunctionalDocumentFormat}. */
public class LabelFunctionalSyntaxStorer extends AbstractOWLOntologyStorer {

    // generated
    private static final long serialVersionUID = 40000L;

    @Override
    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat instanceof LabelFunctionalDocumentFormat;
    }

    @Override
    protected void storeOntology( OWLOntology ontology,
             Writer writer, OWLOntologyFormat format)
            throws OWLOntologyStorageException {
        try {
            OWLObjectRenderer renderer = new OWLObjectRenderer(ontology, writer);
            renderer.setPrefixManager(new LabelPrefixManager(ontology));
            ontology.accept(renderer);
            writer.flush();
        } catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    static class LabelPrefixManager extends DefaultPrefixManager implements
            PrefixManager {

        private static final long serialVersionUID = 40000L;
        
        private final OWLOntology ontology;
        
        private final PrefixManager delegate;

        LabelPrefixManager( OWLOntology ontology) {
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
        public Set<String> getPrefixNames() {
            return delegate.getPrefixNames();
        }
    }

    @Override
    protected void storeOntology(OWLOntologyManager manager,
            OWLOntology ontology, Writer writer, OWLOntologyFormat format)
            throws OWLOntologyStorageException {
        storeOntology(ontology, writer, format);
    }
}
