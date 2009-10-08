package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;

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
public abstract class AbstractClassExpressionTranslator implements ClassExpressionTranslator {

    private OWLRDFConsumer consumer;


    protected AbstractClassExpressionTranslator(OWLRDFConsumer consumer) {
        this.consumer = consumer;
    }


    public OWLRDFConsumer getConsumer() {
        return consumer;
    }

//    protected <T extends Triple> T getFirstTripleWithPredicate(IRI mainNode, IRI predicate) {
//        return (T) consumer.getFirstTripleWithPredicate(mainNode, predicate);
//    }

    protected IRI getResourceObject(IRI subject, IRI predicate, boolean consume) {
        return consumer.getResourceObject(subject, predicate, consume);
    }

    protected OWLLiteral getLiteralObject(IRI subject, IRI predicate, boolean consume) {
        return consumer.getLiteralObject(subject, predicate, consume);
    }

    protected boolean isTriplePresent(IRI mainNode, IRI predicate, IRI value, boolean consume) {
        return consumer.isTriplePresent(mainNode, predicate, value, true);
    }

    protected Set<OWLClassExpression> translateToClassExpressionSet(IRI mainNode) {
        return consumer.translateToClassExpressionSet(mainNode);
    }

    protected Set<OWLIndividual> translateToIndividualSet(IRI mainNode) {
        return consumer.translateToIndividualSet(mainNode);
    }

    protected OWLDataFactory getDataFactory() {
        return consumer.getDataFactory();
    }

    protected OWLClassExpression translateToClassExpression(IRI mainNode) {
        return consumer.translateClassExpression(mainNode);
    }

}
