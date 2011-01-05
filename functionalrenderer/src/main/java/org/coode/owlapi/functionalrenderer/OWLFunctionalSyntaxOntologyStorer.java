package org.coode.owlapi.functionalrenderer;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Jan-2007<br><br>
 */
public class OWLFunctionalSyntaxOntologyStorer extends AbstractOWLOntologyStorer {

    /**
     * Determines if this storer can store an ontology in the specified ontology format.
     * @param ontologyFormat The desired ontology format.
     * @return <code>true</code> if this storer can store an ontology in the desired
     *         format.
     */
    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat.equals(new OWLFunctionalSyntaxOntologyFormat());
    }


    @Override
	protected void storeOntology(OWLOntologyManager manager, OWLOntology ontology, Writer writer, OWLOntologyFormat format) throws
                                                                                                                            OWLOntologyStorageException {
        try {
            OWLObjectRenderer ren = new OWLObjectRenderer(manager, ontology, writer);
            if(format instanceof PrefixOWLOntologyFormat) {
                PrefixOWLOntologyFormat prefixFormat = (PrefixOWLOntologyFormat) format;
                DefaultPrefixManager man = new DefaultPrefixManager();
                Map<String, String> map = prefixFormat.getPrefixName2PrefixMap();
                for(String pn : map.keySet()) {
                    man.setPrefix(pn, map.get(pn));
                }
                ren.setPrefixManager(man);
            }
            ontology.accept(ren);
            writer.flush();
        }
        catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }
}
