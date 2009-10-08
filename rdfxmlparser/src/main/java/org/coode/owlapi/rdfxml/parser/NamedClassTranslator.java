package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.IRI;

import java.net.URI;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-Jun-2009
 */
public class NamedClassTranslator implements ClassExpressionTranslator {

    private OWLRDFConsumer consumer;

    public NamedClassTranslator(OWLRDFConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Translates the specified main node into an <code>OWLClassExpression</code>.
     * All triples used in the translation are consumed.
     * @param mainNode The main node of the set of triples that represent the
     *                 class expression.
     * @return The class expression that represents the translation.
     * @throws org.semanticweb.owlapi.model.OWLException
     *          If the translation could not take place, possibly because the
     *          class expression (set of triples) was malformed.
     */
    public OWLClassExpression translate(IRI mainNode) {
        return consumer.getOWLClass(mainNode);
    }
}
