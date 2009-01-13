package de.uulm.ecs.ai.owl.krssparser;

import org.semanticweb.owl.io.AbstractOWLParser;
import org.semanticweb.owl.io.OWLOntologyInputSource;
import org.semanticweb.owl.io.OWLParserException;
import org.semanticweb.owl.model.*;


/*
 * Copyright (C) 2007, Ulm University
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
 * The KRSS2OWLParser differs from the {@link org.coode.owl.krssparser.KRSSOWLParser KRSSOWLParser} that
 * it supports an extended KRSS vocabulary available in many reasoning systems. For instance, CGIs can
 * be added with help of (implies subclass superclass), range, domain, inverse, functinal attribute can be
 * provided for roles.
 * Note that DatatypeProperties are not supported within KRSS2.
 *
 * @author Olaf Noppens<br>
 * Ulm University
 * Institute of Artificial Intelligence
 */
public class KRSS2OWLParser extends AbstractOWLParser {


    public OWLOntologyFormat parse(OWLOntologyInputSource inputSource, OWLOntology ontology) throws OWLParserException {
        try {
            KRSS2OntologyFormat format = new KRSS2OntologyFormat();
            KRSS2Parser parser;
            if (inputSource.isReaderAvailable()) {
                parser = new KRSS2Parser(inputSource.getReader());
            } else if (inputSource.isInputStreamAvailable()) {
                parser = new KRSS2Parser(inputSource.getInputStream());
            } else {
                parser = new KRSS2Parser(getInputStream(inputSource.getPhysicalURI()));
            }
            parser.setOntology(ontology, getOWLOntologyManager().getOWLDataFactory());
            parser.setIgnoreOntologyBaseURI(format.isIgnoreOntologyURI());
            parser.parse();
            return format;
        }
        catch (ParseException e) {
            throw new KRSS2OWLParserException(e);
        }
    }

   


}
