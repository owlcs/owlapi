package org.semanticweb.owlapi.rdf.rdfxml.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.rdf.rdfxml.parser.Translators.ListItemTranslator;

/**
 * Translates an rdf:List into a Java {@code List}, or Java {@code Set}. The
 * type of list (i.e. the type of objects in the list) are determined by a
 * {@code ListItemTranslator}. The translator consumes all triples which are used
 * in the translation.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 * @param <O>
 *        type
 */
class OptimisedListTranslator<O extends OWLObject> {

    private static final Logger logger = Logger
            .getLogger(OptimisedListTranslator.class.getName());
    private OWLRDFConsumer consumer;
    private ListItemTranslator<O> translator;

    /**
     * @param consumer
     *        consumer
     * @param translator
     *        translator
     */
    protected OptimisedListTranslator(OWLRDFConsumer consumer,
            ListItemTranslator<O> translator) {
        this.consumer = consumer;
        this.translator = translator;
    }

    protected OWLRDFConsumer getConsumer() {
        return consumer;
    }

    private void translateList(IRI mainNode, List<O> list) {
        IRI current = mainNode;
        while (current != null) {
            IRI firstResource = consumer.getFirstResource(current, true);
            if (firstResource != null) {
                list.add(translator.translate(firstResource));
            } else {
                OWLLiteral literal = consumer.getFirstLiteral(current);
                if (literal != null) {
                    O translate = translator.translate(literal);
                    if (translate != null) {
                        list.add(translate);
                    }
                } else {
                    // Empty list?
                    if (logger.isLoggable(Level.FINE)) {
                        logger.fine("Possible malformed list: rdf:first triple missing");
                    }
                }
            }
            current = consumer.getRest(current, true);
        }
    }

    /**
     * @param mainNode
     *        mainNode
     * @return translated list
     */
    @SuppressWarnings("unchecked")
    public List<O> translateList(IRI mainNode) {
        boolean shared = consumer.isAnonymousSharedNode(mainNode.toString());
        List<O> list;
        if (shared) {
            Object o = consumer.getSharedAnonymousNode(mainNode);
            if (o != null && o instanceof List) {
                list = (List<O>) o;
            } else {
                list = new ArrayList<O>();
                translateList(mainNode, list);
                consumer.addSharedAnonymousNode(mainNode, list);
            }
        } else {
            list = new ArrayList<O>();
            translateList(mainNode, list);
        }
        return list;
    }

    /**
     * @param mainNode
     *        mainNode
     * @return translated list
     */
    public Set<O> translateToSet(IRI mainNode) {
        return new HashSet<O>(translateList(mainNode));
    }
}
