package de.uulm.ecs.ai.owlapi.krssrenderer;

import java.io.Writer;

import org.coode.owl.krssparser.KRSSOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;

/**
 * See {@link de.uulm.ecs.ai.owlapi.krssrenderer.KRSSObjectRenderer KRSSObjectRenderer}
 * for definition/explanation of the syntax.
 * Author: Olaf Noppens<br>
 * Ulm University<br>
 * Institute of Artificial Intelligence<br>
 */
public class KRSSSyntaxOntologyStorer extends AbstractOWLOntologyStorer {


    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat.equals(new KRSSOntologyFormat());
    }


    @Override  @SuppressWarnings("unused")
	protected void storeOntology(OWLOntologyManager manager, OWLOntology ontology, Writer writer,
                                 OWLOntologyFormat format) throws OWLOntologyStorageException {
        KRSSSyntaxRenderer renderer = new KRSSSyntaxRenderer(manager);
        renderer.render(ontology, writer);
    }
}