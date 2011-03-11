package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25/02/2011
 */
public class SubClassOfUntypedOWLClassStrictTestCase extends AbstractFileTestCase {

    @Override
    protected String getFileName() {
        return "SubClassOfUntypedOWLClass.rdf";
    }

    public void testAxioms() {
        OWLOntology ont = createOntology();
        assertTrue(ont.getAxioms(AxiomType.SUBCLASS_OF).isEmpty());
        OWLOntologyFormat format = getManager().getOntologyFormat(ont);
        assertTrue(format instanceof RDFXMLOntologyFormat);
        RDFXMLOntologyFormat rdfxmlFormat = (RDFXMLOntologyFormat) format;
        Set<RDFTriple> triples = rdfxmlFormat.getOntologyLoaderMetaData().getUnparsedTriples();
        assertTrue(triples.size() == 1);
    }

    @Override
    protected OWLOntologyLoaderConfiguration getConfiguration() {
        return super.getConfiguration().setStrict(true);
    }
}
