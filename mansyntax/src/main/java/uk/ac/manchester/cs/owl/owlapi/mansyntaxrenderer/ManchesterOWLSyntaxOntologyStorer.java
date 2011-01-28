package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import java.io.Writer;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-May-2007<br><br>
 */
public class ManchesterOWLSyntaxOntologyStorer extends AbstractOWLOntologyStorer {

    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat.equals(new ManchesterOWLSyntaxOntologyFormat());
    }


    @Override
	protected void storeOntology(OWLOntologyManager manager, OWLOntology ontology, Writer writer, OWLOntologyFormat format) throws OWLOntologyStorageException {

        ManchesterOWLSyntaxFrameRenderer ren = new ManchesterOWLSyntaxFrameRenderer(manager, ontology, writer, new ManchesterOWLSyntaxPrefixNameShortFormProvider(format));
        ren.writeOntology();
    }
}
