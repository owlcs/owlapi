package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 05/01/2011
 */
public class PrefixOntologyFormatTestCase extends AbstractRoundTrippingTest {

    @Override
    protected OWLOntology createOntology() throws Exception {
        OWLOntology ont = getManager().createOntology();
        PrefixOWLOntologyFormat format = (PrefixOWLOntologyFormat) ont.getOWLOntologyManager().getOntologyFormat(ont);
        format.setDefaultPrefix("http://default.com");
        format.setPrefix("a", "http://ontology.com/a#");
        format.setPrefix("b", "http://ontology.com/b#");

        return ont;
    }

    @Override
    public OWLOntology roundTripOntology(OWLOntology ont, OWLOntologyFormat format) throws Exception {
        OWLOntology ont2 = super.roundTripOntology(ont, format);
        OWLOntologyFormat ont2Format = ont2.getOWLOntologyManager().getOntologyFormat(ont2);
        if(format instanceof PrefixOWLOntologyFormat && ont2Format instanceof PrefixOWLOntologyFormat) {
            PrefixOWLOntologyFormat prefixFormat = (PrefixOWLOntologyFormat) format;
            prefixFormat.getPrefixName2PrefixMap();
            PrefixOWLOntologyFormat prefixFormat2 = (PrefixOWLOntologyFormat) ont2Format;
            for(String prefixName : prefixFormat.getPrefixNames()) {
                assertTrue(prefixFormat2.containsPrefixMapping(prefixName));
                assertEquals(prefixFormat.getPrefix(prefixName), prefixFormat2.getPrefix(prefixName));
            }

        }
        return ont2;
    }
}
