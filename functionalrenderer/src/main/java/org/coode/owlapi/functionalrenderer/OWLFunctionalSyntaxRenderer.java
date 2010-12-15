package org.coode.owlapi.functionalrenderer;

import java.io.IOException;
import java.io.Writer;

import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.io.OWLRendererIOException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */
public class OWLFunctionalSyntaxRenderer extends AbstractOWLRenderer {

    public OWLFunctionalSyntaxRenderer(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }


    @Override
	public void render(OWLOntology ontology, Writer writer) throws OWLRendererException {
        try {
            OWLObjectRenderer ren = new OWLObjectRenderer(getOWLOntologyManager(), ontology, writer);
            ontology.accept(ren);
            writer.flush();
        }
        catch (IOException e) {
            throw new OWLRendererIOException(e);
        }
    }
}
