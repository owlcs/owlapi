package org.obolibrary.oboformat;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Test: silently ignore annotations of import statements in the header. See
 * bug/request: http://code.google.com/p/oboformat/issues/detail?id=19
 */
@SuppressWarnings("javadoc")
public class ParseBehaviorTest extends OboFormatTestBasics {

    @Test
    public void testIgnoreAnnotations() throws Exception {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLOntologyDocumentSource source = new StringDocumentSource(
                "format-version: 1.2\n"
                        + "data-version: 2012-07-09\n"
                        + "import: http://purl.obolibrary.org/obo/chebi.owl\n"
                        + "import: http://purl.obolibrary.org/obo/go.owl\n"
                        + "import: http://purl.obolibrary.org/obo/pato.owl\n"
                        + "import: http://purl.obolibrary.org/obo/uberon.owl\n"
                        + "remark: 2012-07-09\n"
                        + "ontology: nbo\n"
                        + "[Term]\n"
                        + "id: NBO:0000541\n"
                        + "name: dipsosis\n"
                        + "def: \"\\\"A pathological drinking behavior characterised by an excessive desire to drink.\\\" [NBO:GVG]\" []\n"
                        + "xref: MP:0002119\n"
                        + "is_a: NBO:0000961 ! increased frequency of drinking\n"
                        + "property_value: created:by \"George Gkoutos\" xsd:string\n"
                        + "property_value: http://purl.obolibrary.org/obo/namespace \"behavior_ontology\" xsd:string\n"
                        + "property_value: http://purl.org/dc/elements/1.1/date 2011-04-10T10:41:10Z xsd:string\n"
                        + "property_value: note \"'participates in' some \\n('regulation of drinking behavior' and (has_quality some \\n('increased frequency' and towards some 'liquid consumption' and owl:qualifier some chronic)))\"");
        m.loadOntologyFromOntologyDocument(source);
    }
}
