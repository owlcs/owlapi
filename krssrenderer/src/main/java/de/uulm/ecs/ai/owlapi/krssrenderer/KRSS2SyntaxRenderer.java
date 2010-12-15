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
 * Author: Olaf Noppens<br>
 * Ulm University<br>
 * Institute of Artificial Intelligence<br>
 */
public class KRSS2SyntaxRenderer extends AbstractOWLRenderer {

    public KRSS2SyntaxRenderer(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }

    @Override
	public void render(OWLOntology ontology, Writer writer) throws OWLRendererException {
        try {
            KRSS2ObjectRenderer ren = new KRSS2ObjectRenderer(getOWLOntologyManager(), ontology, writer);
            ontology.accept(ren);
            writer.flush();
        }
        catch (IOException io) {
            throw new OWLRendererIOException(io);
        }
        catch (OWLRuntimeException e) {
            throw new OWLRendererException(e);
        }
    }
}
