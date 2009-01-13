package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLException;

import java.net.URI;

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
public abstract class AbstractRestrictionTranslator extends AbstractDescriptionTranslator {

    public AbstractRestrictionTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }



    final public OWLDescription translate(URI mainNode) throws OWLException {
        consumeTypeTriples(mainNode);
        return translateRestriction(mainNode);
    }

    protected abstract OWLDescription translateRestriction(URI mainNode) throws OWLException;


    /**
     * Consumes any triples that type the main node as either a restriction, or
     * an object restriction.
     * @param mainNode The main node in the set of triples that represent the object restriction
     */
    private void consumeTypeTriples(URI mainNode) throws OWLException {
        // Commented out, because these triples get thrown away anyway
//        Triple objectRestrictionTriple = getFirstTripleWithPredicate(mainNode, OWLVocabulary.getObjectRestriction());
//        if(objectRestrictionTriple != null) {
//            consumeTriple(objectRestrictionTriple);
//        }
//        Triple restrictionTriple = getFirstTripleWithPredicate(mainNode, OWLVocabulary.getRestriction());
//        if(restrictionTriple != null) {
//            consumeTriple(restrictionTriple);
//        }
//        Triple dataRestrictionTriple = getFirstTripleWithPredicate(mainNode, OWLVocabulary.getDataRestriction());
//        if(dataRestrictionTriple != null) {
//            consumeTriple(dataRestrictionTriple);
//        }
    }
}
