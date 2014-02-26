package org.semanticweb.owlapi.io;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;

public class OWLParserFactoryImpl<T extends OWLParser> implements
        OWLParserFactory {

    private Class<T> type;

    public OWLParserFactoryImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public OWLParser createParser(OWLOntologyManager owlOntologyManager) {
        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            throw new OWLRuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Override
    public Set<OWLOntologyFormatFactory> getSupportedFormats() {
        return createParser(null).getSupportedFormats();
    }
}
