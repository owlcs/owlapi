package org.coode.obo.parser;

import org.semanticweb.owl.io.AbstractOWLParser;
import org.semanticweb.owl.io.OWLOntologyInputSource;
import org.semanticweb.owl.io.OWLParserException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyFormat;
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
 * Date: 10-Jan-2007<br><br>
 */
public class OWLOBOParser extends AbstractOWLParser {

    public OWLOntologyFormat parse(OWLOntologyInputSource inputSource, OWLOntology ontology) throws OWLParserException {
        OBOParser parser;
        if (inputSource.isReaderAvailable()) {
            parser = new OBOParser(inputSource.getReader());
        }
        else if (inputSource.isInputStreamAvailable()) {
            parser = new OBOParser(inputSource.getInputStream());
        }
        else {
            parser = new OBOParser(getInputStream(inputSource.getPhysicalURI()));
        }
        parser.setHandler(new OBOConsumer(getOWLOntologyManager(), ontology));
        try {
            parser.parse();
        }
        catch (ParseException e) {
            throw new OWLParserException(e);
        }
        catch(TokenMgrError e) {
            throw new OWLParserException(e);
        }
        return new OBOOntologyFormat();
    }
}
