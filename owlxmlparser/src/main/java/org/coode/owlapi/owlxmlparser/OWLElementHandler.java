package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.UnloadableImportException;

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
 * Date: 13-Dec-2006<br><br>
 */
public interface OWLElementHandler<O> {

    public void startElement(String name) throws OWLXMLParserException;


    public void attribute(String localName, String value) throws OWLParserException;


    public void endElement() throws OWLParserException, UnloadableImportException;


    public O getOWLObject() throws OWLXMLParserException;


    public void setParentHandler(OWLElementHandler handler) throws OWLXMLParserException;


    public void handleChild(AbstractOWLAxiomElementHandler handler) throws OWLXMLParserException;


    public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException;


    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) throws OWLXMLParserException;


    public void handleChild(OWLDataPropertyElementHandler handler) throws OWLXMLParserException;


    public void handleChild(OWLIndividualElementHandler handler) throws OWLXMLParserException;


    public void handleChild(AbstractOWLDataRangeHandler handler) throws OWLXMLParserException;


    public void handleChild(OWLLiteralElementHandler handler) throws OWLXMLParserException;


    public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException;


    public void handleChild(OWLAnonymousIndividualElementHandler handler) throws OWLXMLParserException;


    public void handleChild(OWLSubObjectPropertyChainElementHandler handler) throws OWLXMLParserException;


    public void handleChild(OWLDatatypeFacetRestrictionElementHandler handler) throws OWLXMLParserException;


    public void handleChild(OWLAnnotationPropertyElementHandler handler) throws OWLXMLParserException;


    public void handleChild(AbstractIRIElementHandler handler) throws OWLXMLParserException;


    public void handleChars(char[] chars, int start, int length) throws OWLXMLParserException;


    public void handleChild(SWRLVariableElementHandler handler) throws OWLXMLParserException;


    public void handleChild(SWRLAtomElementHandler handler) throws OWLXMLParserException;


    public void handleChild(SWRLAtomListElementHandler handler) throws OWLXMLParserException;
        

    public String getText();


    public boolean isTextContentPossible();
}
