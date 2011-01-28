package de.uulm.ecs.ai.owlapi.krssrenderer;

import java.io.Writer;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;

import de.uulm.ecs.ai.owlapi.krssparser.KRSS2OntologyFormat;

/**
 * See {@link de.uulm.ecs.ai.owlapi.krssrenderer.KRSS2ObjectRenderer KRSS2ObjectRenderer}
 * for definition/explanation of the syntax.
 *
 * Author: Olaf Noppens<br>
 * Ulm University<br>
 * Institute of Artificial Intelligence<br>
 */
public class KRSS2SyntaxOntologyStorer extends AbstractOWLOntologyStorer {

    /**
     * @inheritDoc
     */
    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat.equals(new KRSS2OntologyFormat());
    }

    // I changed this class to extend AbstractOWLOntologyStorer - Matthew Horridge

    @Override  @SuppressWarnings("unused")
	protected void storeOntology(OWLOntologyManager manager, OWLOntology ontology, Writer writer, OWLOntologyFormat format) throws
            OWLOntologyStorageException {
        KRSS2SyntaxRenderer renderer = new KRSS2SyntaxRenderer(manager);
        renderer.render(ontology, writer);
    }
}
