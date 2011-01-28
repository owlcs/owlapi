package de.uulm.ecs.ai.owlapi.krssrenderer;

import java.io.Writer;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;

import de.uulm.ecs.ai.owlapi.krssparser.KRSS2OntologyFormat;

/**
 *
 * @author Olaf Noppens
 */
public class KRSS2OWLSyntaxOntologyStorer extends AbstractOWLOntologyStorer {

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
            KRSS2OWLSyntaxRenderer renderer = new KRSS2OWLSyntaxRenderer(manager);
            renderer.render(ontology, writer);
    }
}
