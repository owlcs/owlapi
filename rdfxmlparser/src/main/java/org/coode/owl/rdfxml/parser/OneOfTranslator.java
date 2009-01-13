package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

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
 */
public class OneOfTranslator extends AbstractDescriptionTranslator {

    Logger logger = Logger.getLogger(OWLRDFConsumer.class.getName());

    public OneOfTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }


    public OWLDescription translate(URI mainNode) throws OWLException {
        URI oneOfObject = getResourceObject(mainNode, OWLRDFVocabulary.OWL_ONE_OF.getURI(), true);
        if(oneOfObject == null) {
            throw new MalformedDescriptionException(OWLRDFVocabulary.OWL_ONE_OF + " triple not present when translating oneOf");
        }
        Set<OWLIndividual> individuals = translateToIndividualSet(oneOfObject);
        for(OWLIndividual ind : individuals) {
            getConsumer().addIndividual(ind.getURI());
        }
        if(individuals.isEmpty()) {
            logger.info("Empty set in owl:oneOf class description - converting to owl:Nothing");
            return getDataFactory().getOWLNothing();
        }
        return getDataFactory().getOWLObjectOneOf(individuals);
    }
}
