package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.IRI;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


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
        if (!consumer.isList(mainNode, true)) {
            // I originally threw an exception here, but some ontologies
            // seem to have missing type triples for lists where it's obvious
            // that the node is a list
            if(logger.isLoggable(Level.FINE)) {
                logger.fine("Untyped list found: " + mainNode);
            }
        }


        IRI firstResource = getConsumer().getFirstResource(mainNode, true);
        if (firstResource != null) {
            list.add(translator.translate(firstResource));
        }
        else {
            OWLLiteral literal = getConsumer().getFirstLiteral(mainNode);
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
        IRI rest = getConsumer().getRest(mainNode, true);
        if (rest != null) {
            translateList(rest, list);
        }
    }


    public List<O> translateList(IRI mainNode) {
        List<O> list = new ArrayList<O>();
        translateList(mainNode, list);
        return list;
    }


    public Set<O> translateToSet(IRI mainNode) {
        return new HashSet<O>(translateList(mainNode));
    }
}
