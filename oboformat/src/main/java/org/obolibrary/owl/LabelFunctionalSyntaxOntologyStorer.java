package org.obolibrary.owl;

import java.io.IOException;
import java.io.Writer;

import org.coode.owlapi.functionalrenderer.OWLObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

/** Implement the writer for {@link LabelFunctionalFormat}. */
public class LabelFunctionalSyntaxOntologyStorer extends
        AbstractOWLOntologyStorer {

    // generated
    private static final long serialVersionUID = -6143049869068925168L;

    @Override
    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat instanceof LabelFunctionalFormat;
    }

    @Override
    protected void storeOntology(OWLOntologyManager manager,
            OWLOntology ontology, Writer writer, OWLOntologyFormat format)
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

    @Override
    protected void storeOntology(OWLOntology ontology, Writer writer,
            OWLOntologyFormat format) throws OWLOntologyStorageException {
        storeOntology(ontology.getOWLOntologyManager(), ontology, writer,
                format);
    }

    static class LabelPrefixManager extends DefaultPrefixManager {

        // generated
        private static final long serialVersionUID = 3814624420610086487L;
        private final OWLOntology ontology;

        LabelPrefixManager(OWLOntology ontology) {
            this.ontology = ontology;
        }

        @Override
        public String getPrefixIRI(IRI iri) {
            for (OWLAnnotationAssertionAxiom annotation : ontology
                    .getAnnotationAssertionAxioms(iri)) {
                if (annotation.getProperty().isLabel()) {
                    OWLAnnotationValue value = annotation.getValue();
                    if (value != null && value instanceof OWLLiteral) {
                        return "<" + ((OWLLiteral) value).getLiteral() + ">";
                    }
                }
            }
            return super.getPrefixIRI(iri);
        }
    }
}
