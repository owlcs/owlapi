package org.coode.owlapi.owlxml.renderer;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.io.OWLRendererIOException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 12-Dec-2006<br><br>
 */
public class OWLXMLRenderer extends AbstractOWLRenderer {

    public OWLXMLRenderer(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }

    public void render(OWLOntology ontology, Writer writer, OWLOntologyFormat format) throws OWLRendererException {
        try {
            OWLXMLWriter w = new OWLXMLWriter(writer, ontology);
            w.startDocument(ontology);


            if(format instanceof PrefixOWLOntologyFormat) {
                PrefixOWLOntologyFormat fromPrefixFormat = (PrefixOWLOntologyFormat) format;
                final Map<String,String> map = fromPrefixFormat.getPrefixName2PrefixMap();
                for(String prefixName : map.keySet()) {
                    String prefix = map.get(prefixName);
                    if(prefix != null && prefix.length() > 0) {
                        w.writePrefix(prefixName, prefix);          
                    }
                }
                if(!map.containsKey("rdf:")) {
                    w.writePrefix("rdf:", Namespaces.RDF.toString());
                }
                if(!map.containsKey("rdfs:")) {
                    w.writePrefix("rdfs:", Namespaces.RDFS.toString());
                }
                if(!map.containsKey("xsd:")) {
                    w.writePrefix("xsd:", Namespaces.XSD.toString());
                }
                if(!map.containsKey("owl:")) {
                    w.writePrefix("owl:", Namespaces.OWL.toString());
                }
            }
            else {
                w.writePrefix("rdf:", Namespaces.RDF.toString());
                w.writePrefix("rdfs:", Namespaces.RDFS.toString());
                w.writePrefix("xsd:", Namespaces.XSD.toString());
                w.writePrefix("owl:", Namespaces.OWL.toString());
            }


            OWLXMLObjectRenderer ren = new OWLXMLObjectRenderer(w);
            ontology.accept(ren);
            w.endDocument();
            writer.flush();
        }
        catch (IOException e) {
            throw new OWLRendererIOException(e);
        }
    }


    @Override
	public void render(OWLOntology ontology, Writer writer) throws OWLRendererException {
        render(ontology, writer, getOWLOntologyManager().getOntologyFormat(ontology));
    }
}
