package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLTypedLiteral;
import org.semanticweb.owlapi.model.IRI;

import java.net.URI;
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
 * Date: 10-Dec-2006<br><br>
 *
 */
public class TypedConstantListItemTranslator implements ListItemTranslator<OWLLiteral> {

    private static final Logger logger = Logger.getLogger(TypedConstantListItemTranslator.class.getName());

    public TypedConstantListItemTranslator(OWLRDFConsumer consumer) {
    }


    public OWLTypedLiteral translate(IRI firstObject) {
        logger.info("Cannot translate list item to a constant because rdf:first triple is a resource triple");
        return null;
    }


    public OWLLiteral translate(OWLLiteral firstObject) {
        return firstObject;
    }
}
