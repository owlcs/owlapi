package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.OWLDataPropertyExpression;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

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
public abstract class AbstractDataRestrictionTranslator extends AbstractRestrictionTranslator {

    public AbstractDataRestrictionTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }


    /**
     * Translates and consumes the onProperty triple, creating a data property corresponding to the object
     * of the onProperty triple.
     * @param mainNode The subject of the triple (the main node of the restriction)
     * @throws OWLException If the on property triple doesn't exist.
     */
    protected OWLDataPropertyExpression translateOnProperty(URI mainNode) throws OWLException {
        URI onPropertyObject = getResourceObject(mainNode, OWLRDFVocabulary.OWL_ON_PROPERTY.getURI(), true);
        if(onPropertyObject == null) {
            throw new MalformedDescriptionException(OWLRDFVocabulary.OWL_ON_PROPERTY + " not present");
        }
        return getDataFactory().getOWLDataProperty(onPropertyObject);
    }

}
