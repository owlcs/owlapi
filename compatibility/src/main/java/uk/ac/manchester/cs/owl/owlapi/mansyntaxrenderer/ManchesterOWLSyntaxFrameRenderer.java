package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import java.io.Writer;
import java.util.Collection;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * @deprecated use org.semanticweb.owlapi.mansyntax.renderer.
 *             ManchesterOWLSyntaxFrameRenderer
 */
@Deprecated
public class ManchesterOWLSyntaxFrameRenderer
    extends org.semanticweb.owlapi.manchestersyntax.renderer.ManchesterOWLSyntaxFrameRenderer {

    /**
     * @param ontology
     *        ontology
     * @param writer
     *        writer
     * @param entityShortFormProvider
     *        provider
     */
    public ManchesterOWLSyntaxFrameRenderer(OWLOntology ontology, Writer writer,
        ShortFormProvider entityShortFormProvider) {
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
    public ManchesterOWLSyntaxFrameRenderer(Collection<OWLOntology> ontologies, Writer writer,
        ShortFormProvider entityShortFormProvider) {
        super(ontologies, writer, entityShortFormProvider);
    }
}
