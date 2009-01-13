package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.*;

import java.net.URI;
import java.util.Set;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 08-Dec-2006<br><br>
 */
public abstract class AbstractDescriptionTranslator implements DescriptionTranslator {

    private OWLRDFConsumer consumer;


    protected AbstractDescriptionTranslator(OWLRDFConsumer consumer) {
        this.consumer = consumer;
    }


    public OWLRDFConsumer getConsumer() {
        return consumer;
    }

//    protected <T extends Triple> T getFirstTripleWithPredicate(URI mainNode, URI predicate) throws OWLException {
//        return (T) consumer.getFirstTripleWithPredicate(mainNode, predicate);
//    }

    protected URI getResourceObject(URI subject, URI predicate, boolean consume) throws OWLException {
        return consumer.getResourceObject(subject, predicate, consume);
    }

    protected OWLConstant getLiteralObject(URI subject, URI predicate, boolean consume) throws OWLException {
        return consumer.getLiteralObject(subject, predicate, consume);
    }

    protected boolean isTriplePresent(URI mainNode, URI predicate, URI value, boolean consume) throws OWLException {
        return consumer.isTriplePresent(mainNode, predicate, value, true);
    }

    protected Set<OWLDescription> translateToDescriptionSet(URI mainNode) throws OWLException {
        return consumer.translateToDescriptionSet(mainNode);
    }

    protected Set<OWLIndividual> translateToIndividualSet(URI mainNode) throws OWLException {
        return consumer.translateToIndividualSet(mainNode);
    }

    protected OWLDataFactory getDataFactory() throws OWLException {
        return consumer.getDataFactory();
    }

    protected OWLDescription translateToDescription(URI mainNode) throws OWLException {
        return consumer.translateDescription(mainNode);
    }

}
