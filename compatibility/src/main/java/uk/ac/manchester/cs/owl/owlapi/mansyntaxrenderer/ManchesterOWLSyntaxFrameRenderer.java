package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import java.io.Writer;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * @deprecated use
 *             org.semanticweb.owlapi.mansyntax.renderer.ManchesterOWLSyntaxFrameRenderer
 */
@Deprecated
public class ManchesterOWLSyntaxFrameRenderer
        extends
        org.semanticweb.owlapi.manchestersyntax.renderer.ManchesterOWLSyntaxFrameRenderer {

    /**
     * @param ontology
     *        ontology
     * @param writer
     *        writer
     * @param entityShortFormProvider
     *        provider
     */
    public ManchesterOWLSyntaxFrameRenderer(@Nonnull OWLOntology ontology,
            @Nonnull Writer writer,
            @Nonnull ShortFormProvider entityShortFormProvider) {
        super(ontology, writer, entityShortFormProvider);
    }

    /**
     * Instantiates a new manchester owl syntax frame renderer.
     * 
     * @param ontologies
     *        the ontologies
     * @param writer
     *        the writer
     * @param entityShortFormProvider
     *        the entity short form provider
     */
    public ManchesterOWLSyntaxFrameRenderer(
            @Nonnull Set<OWLOntology> ontologies, Writer writer,
            @Nonnull ShortFormProvider entityShortFormProvider) {
        super(ontologies, writer, entityShortFormProvider);
    }
}
