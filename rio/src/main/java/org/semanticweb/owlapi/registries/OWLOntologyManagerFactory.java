package org.semanticweb.owlapi.registries;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 16-Mar-2007
 */
public interface OWLOntologyManagerFactory {

    /** @return a new ontology manager */
    OWLOntologyManager buildOWLOntologyManager();

    /**
     * @param f
     *        the data factory the new manager will use
     * @return a new ontology manager
     */
    public OWLOntologyManager buildOWLOntologyManager(OWLDataFactory f);

    /**
     * @param f
     *        the data factory the new manager will use
     * @param storerRegistry
     *        the registry of storers that the new manager will use
     * @param parserRegistry
     *        the registry of parsers that the new manager will use
     * @return a new ontology manager
     */
    public OWLOntologyManager buildOWLOntologyManager(OWLDataFactory f,
            OWLOntologyStorerFactoryRegistry storerRegistry,
            OWLParserFactoryRegistry parserRegistry);

    /** @return a data factory */
    public OWLDataFactory getFactory();
}
