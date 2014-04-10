package org.obolibrary.owl;

import java.io.IOException;
import java.io.Writer;

import org.semanticweb.owlapi.formats.LabelFunctionalFormat;
import org.semanticweb.owlapi.functional.renderer.FunctionalSyntaxObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
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
    protected void storeOntology(OWLOntology ontology, Writer writer,
            OWLOntologyFormat format) throws OWLOntologyStorageException {
        try {
            FunctionalSyntaxObjectRenderer renderer = new FunctionalSyntaxObjectRenderer(ontology, writer);
            renderer.setPrefixManager(new LabelPrefixManager(ontology));
            ontology.accept(renderer);
            writer.flush();
        } catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    static class LabelPrefixManager extends DefaultPrefixManager {

        // generated
        private static final long serialVersionUID = 40000L;
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
