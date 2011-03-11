package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.util.Set;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_INTERSECTION_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_UNION_OF;

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
 * <p/>
 * Translates a set of triples to an <code>OWLUnionOf</code>.
 */
public class ObjectUnionOfTranslator extends AbstractClassExpressionTranslator {

    public ObjectUnionOfTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }

    public boolean matchesStrict(IRI mainNode) {
        IRI listNode = getConsumer().getResourceObject(mainNode, OWL_UNION_OF, false);
        return isClassExpressionStrict(mainNode) && isClassExpressionListStrict(listNode, 2);
    }

    public boolean matchesLax(IRI mainNode) {
        return isResourcePresent(mainNode, OWL_UNION_OF);
    }

    public OWLObjectUnionOf translate(IRI mainNode) {
        IRI listNode = getConsumer().getResourceObject(mainNode, OWL_UNION_OF, true);
        Set<OWLClassExpression> classExpressions = getConsumer().translateToClassExpressionSet(listNode);
        return getDataFactory().getOWLObjectUnionOf(classExpressions);
    }
}
