package org.coode.owlapi.rdfxml.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Jan-2007<br><br>
 * <p/>
 * Translates an rdf:List into a Java <code>List</code>, or Java <code>Set</code>.
 * The type of list (i.e. the type of objects in the list) are determined by a <code>ListItemTranslator</code>.
 * The translator consumes all triples which are used in the translation.
 */
public class OptimisedListTranslator<O extends OWLObject> {

    private static final Logger logger = Logger.getLogger(OWLRDFConsumer.class.getName());

    private OWLRDFConsumer consumer;

    private ListItemTranslator<O> translator;


    protected OptimisedListTranslator(OWLRDFConsumer consumer, ListItemTranslator<O> translator) {
        this.consumer = consumer;
        this.translator = translator;
    }


    protected OWLRDFConsumer getConsumer() {
        return consumer;
    }


    private void translateList(IRI mainNode, List<O> list) {
        
        IRI firstResource = consumer.getFirstResource(mainNode, true);
        if (firstResource != null) {
            list.add(translator.translate(firstResource));
        }
        else {
            OWLLiteral literal = consumer.getFirstLiteral(mainNode);
            if (literal != null) {
                list.add(translator.translate(literal));
            }
            else {
                // Empty list?
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("Possible malformed list: rdf:first triple missing");
                }
            }
        }
        IRI rest = consumer.getRest(mainNode, true);
        if (rest != null) {
            translateList(rest, list);
        }
    }


    public List<O> translateList(IRI mainNode) {
        boolean shared = consumer.isSharedAnonymousNode(mainNode);
        List<O> list;
        if (shared) {
            Object o = consumer.getSharedAnonymousNode(mainNode);
            if (o != null && o instanceof List) {
                list = (List<O>) o;
            }
            else {
                list = new ArrayList<O>();
                translateList(mainNode, list);
                consumer.addSharedAnonymousNode(mainNode, list);
            }
        }
        else {
            list = new ArrayList<O>();
            translateList(mainNode, list);
        }


        return list;
    }


    public Set<O> translateToSet(IRI mainNode) {
        return new HashSet<O>(translateList(mainNode));
    }
}
