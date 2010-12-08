package org.semanticweb.owlapi.util;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 07-Feb-2007<br><br>
 * <p/>
 * An ontology IRI mapper that can be used to map ontology IRIs
 * to ontology document IRIs which share the same base.
 */
public class CommonBaseIRIMapper implements OWLOntologyIRIMapper {

    private IRI base;

    private Map<IRI, IRI> iriMap;


    /**
     * Creates a mapper, which maps ontology URIs to URIs which share
     * the specified base
     */
    public CommonBaseIRIMapper(IRI base) {
        this.base = base;
        iriMap = new HashMap<IRI, IRI>();
    }


    /**
     * Adds a mapping from an ontology IRI to an ontology document IRI which
     * has a base of this mapper and a specified local name - in
     * other words the document IRI will be determined by resolving
     * the local name against the URI base of this mapper.
     */
    public void addMapping(IRI ontologyIRI, String localName) {
        IRI documentIRI = base.resolve(localName);
        iriMap.put(ontologyIRI, documentIRI);
    }


    /**
     * Given an ontology IRI, this method maps the ontology IRI
     * to a document IRI that points to some concrete representation
     * of the ontology.
     * @param ontologyIRI The ontology IRI to be mapped.
     * @return The document IRI of the ontology, or <code>null</code>
     *         if the mapper doesn't have mapping for the specified ontology IRI.
     */
    public IRI getDocumentIRI(IRI ontologyIRI) {
        return iriMap.get(ontologyIRI);
    }
}
