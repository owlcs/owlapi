package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import java.io.Writer;

import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-May-2007<br><br>
 */
public class ManchesterOWLSyntaxRenderer extends AbstractOWLRenderer {

    public ManchesterOWLSyntaxRenderer(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }


    @Override
	public void render(OWLOntology ontology, Writer writer) throws OWLRendererException {
        ManchesterOWLSyntaxFrameRenderer ren = new ManchesterOWLSyntaxFrameRenderer(getOWLOntologyManager(), ontology, writer, new ManchesterOWLSyntaxPrefixNameShortFormProvider(getOWLOntologyManager(), ontology));
        ren.writeOntology();
        ren.flush();
    }
}
