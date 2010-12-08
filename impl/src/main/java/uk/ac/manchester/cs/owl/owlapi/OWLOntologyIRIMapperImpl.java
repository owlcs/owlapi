package uk.ac.manchester.cs.owl.owlapi;

import java.util.Map;
import java.util.TreeMap;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Nov-2006<br><br>
 */
public class OWLOntologyIRIMapperImpl implements OWLOntologyIRIMapper {

    private Map<IRI, IRI> iriMap;


    public OWLOntologyIRIMapperImpl() {
        iriMap = new TreeMap<IRI, IRI>();
    }


    public IRI getDocumentIRI(IRI ontologyIRI) {
        IRI iri = iriMap.get(ontologyIRI);
        if (iri != null) {
            return iri;
        }
        else {
            return ontologyIRI;
        }
    }

    public void addMapping(IRI ontologyIRI, IRI documentIRI) {
        iriMap.put(ontologyIRI, documentIRI);
    }
}
