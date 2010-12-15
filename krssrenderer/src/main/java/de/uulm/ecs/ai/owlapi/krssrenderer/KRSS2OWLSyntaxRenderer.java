package de.uulm.ecs.ai.owlapi.krssrenderer;

import java.io.IOException;
import java.io.Writer;

import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.io.OWLRendererIOException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * @author Olaf Noppens
 */
public class KRSS2OWLSyntaxRenderer extends AbstractOWLRenderer {

    public KRSS2OWLSyntaxRenderer(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }

    @Override
	public void render(OWLOntology ontology, Writer writer) throws OWLRendererException {
        try {
            KRSS2OWLObjectRenderer ren = new KRSS2OWLObjectRenderer(ontology, writer);
            ontology.accept(ren);
            writer.flush();
        } catch (IOException io) {
            throw new OWLRendererIOException(io);
        } catch (OWLRuntimeException e) {
            throw new OWLRendererException(e);
        }
    }
}
