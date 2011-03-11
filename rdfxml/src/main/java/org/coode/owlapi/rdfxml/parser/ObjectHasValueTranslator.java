package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

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
public class ObjectHasValueTranslator extends AbstractClassExpressionTranslator {

    public ObjectHasValueTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }

    public boolean matchesStrict(IRI mainNode) {
        return isRestrictionStrict(mainNode) && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY) && isResourcePresent(mainNode, OWL_HAS_VALUE);
    }

    public boolean matchesLax(IRI mainNode) {
        return isResourcePresent(mainNode, OWL_ON_PROPERTY) && isResourcePresent(mainNode, OWL_HAS_VALUE);
    }

    public OWLObjectHasValue translate(IRI mainNode) {
        IRI value = getConsumer().getResourceObject(mainNode, OWL_HAS_VALUE, true);
        OWLIndividual individual = getConsumer().translateIndividual(value);
        IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
        OWLObjectPropertyExpression property = getConsumer().translateObjectPropertyExpression(propertyIRI);
        return getDataFactory().getOWLObjectHasValue(property, individual);
    }
}
