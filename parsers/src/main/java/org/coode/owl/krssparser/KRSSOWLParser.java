/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.coode.owl.krssparser;

import java.io.IOException;

import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Nov-2006
 */
public class KRSSOWLParser extends AbstractOWLParser {

    @Override
    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource,
            OWLOntology ontology) throws OWLParserException, IOException,
            OWLOntologyChangeException, UnloadableImportException {
        return parse(documentSource, ontology,
                new OWLOntologyLoaderConfiguration());
    }

    @Override
    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource,
            OWLOntology ontology, OWLOntologyLoaderConfiguration configuration)
            throws OWLParserException, IOException, OWLOntologyChangeException,
            UnloadableImportException {
        try {
            KRSSOntologyFormat format = new KRSSOntologyFormat();
            KRSSParser parser;
            if (documentSource.isReaderAvailable()) {
                parser = new KRSSParser(documentSource.getReader());
            } else if (documentSource.isInputStreamAvailable()) {
                parser = new KRSSParser(documentSource.getInputStream());
            } else {
                parser = new KRSSParser(getInputStream(
                        documentSource.getDocumentIRI(), configuration));
            }
            parser.setOntology(ontology, ontology.getOWLOntologyManager()
                    .getOWLDataFactory());
            parser.parse();
            return format;
        } catch (ParseException e) {
            throw new KRSSOWLParserException(e);
        }
    }
}
