package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

import java.net.URI;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 15-Apr-2008<br><br>
 */
public class TypeAllDisjointClassesHandler extends BuiltInTypeHandler {


    public TypeAllDisjointClassesHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ALL_DISJOINT_CLASSES.getURI());
    }


    public void handleTriple(URI subject, URI predicate, URI object) throws OWLException {
        consumeTriple(subject, predicate, object);
        URI listNode = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_MEMBERS.getURI(), true);
        if (listNode != null) {
            Set<OWLDescription> desc = getConsumer().translateToDescriptionSet(listNode);
            addAxiom(getDataFactory().getOWLDisjointClassesAxiom(desc));
        }
        else {
            throw new OWLRDFXMLParserMalformedNodeException("Malformed AllDisjointClasses -  members triple not present");
        }
    }


    public boolean canHandleStreaming(URI subject, URI predicate, URI object) throws OWLException {
        return false;
    }
}
