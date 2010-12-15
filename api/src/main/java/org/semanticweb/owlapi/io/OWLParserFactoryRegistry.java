package org.semanticweb.owlapi.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Nov-2006<br><br>
 * <p/>
 * The <code>OWLParserFactoryRegistry</code> provides a central point for
 * the registration of parser factories that create parsers to parse OWL
 * ontologies.  The registry is typically used by at least one type of ontology
 * factory for loading ontologies whose concrete representations are contained
 * in some kind of document.
 */
public class OWLParserFactoryRegistry {

    private static OWLParserFactoryRegistry instance;

    private List<OWLParserFactory> parserFactories;


    private OWLParserFactoryRegistry() {
        parserFactories = new ArrayList<OWLParserFactory>();
    }


    public static synchronized OWLParserFactoryRegistry getInstance() {
        if (instance == null) {
            instance = new OWLParserFactoryRegistry();
        }
        return instance;
    }


    public void clearParserFactories() {
        parserFactories.clear();
    }


    public List<OWLParserFactory> getParserFactories() {
        return Collections.unmodifiableList(parserFactories);
    }


    public void registerParserFactory(OWLParserFactory parserFactory) {
        parserFactories.add(0, parserFactory);
    }


    public void unregisterParserFactory(OWLParserFactory parserFactory) {
        parserFactories.remove(parserFactory);
    }
}
