package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.IRI;

import java.net.URI;
import java.util.Set;
import java.util.logging.Logger;
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
 *
 * A base class for translators that translate a set of triples to an Nary boolean class expressions -
 * i.e. an OWLIntersectionOf or OWLUnionOf class expression.
 */
public abstract class AbstractNaryBooleanClassExpressionTranslator extends AbstractClassExpressionTranslator {

    private Logger logger = Logger.getLogger(OWLRDFConsumer.class.getName());

    public AbstractNaryBooleanClassExpressionTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }


    public OWLClassExpression translate(IRI mainNode) {
        IRI object = getResourceObject(mainNode, getPredicateIRI(), true);
        Set<OWLClassExpression> operands = translateToClassExpressionSet(object);
        if(operands.size() < 2) {
            logger.fine("Number of operands is less than 2");
            if(operands.size() == 1) {
                return operands.iterator().next();
            }
            else {
                // Zero - just return thing
                logger.fine("Number of operands is zero! Translating as owl:Thing");
                return getDataFactory().getOWLThing();
            }
        }
        return createClassExpression(operands);
    }

    protected abstract OWLClassExpression createClassExpression(Set<OWLClassExpression> operands);

    protected abstract IRI getPredicateIRI();
}
