/*
 * This file is part of the OWL API.
 * 
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * 
 * Copyright (C) 2011, The University of Queensland
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see http://www.gnu.org/licenses/.
 * 
 * 
 * Alternatively, the contents of this file may be used under the terms of the Apache License,
 * Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable
 * instead of those above.
 * 
 * Copyright 2011, The University of Queensland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.semanticweb.owlapi.rio;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import javax.annotation.Nonnull;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.helpers.BasicParserSettings;
import org.semanticweb.owlapi.annotations.HasPriority;
import org.semanticweb.owlapi.formats.RioRDFDocumentFormatFactory;
import org.semanticweb.owlapi.formats.TrixDocumentFormatFactory;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
@HasPriority(12)
public class RioTrixParserFactory extends AbstractRioParserFactory {

    private static final long serialVersionUID = 40000L;

    /** default constructor */
    public RioTrixParserFactory() {
        super(new TrixDocumentFormatFactory());
    }

    @Nonnull
    @Override
    public OWLParser createParser() {
        return new TrixParserImpl(getRioFormatFactory());
    }
    private static class TrixParserImpl extends RioParserImpl {
        public TrixParserImpl(@Nonnull RioRDFDocumentFormatFactory formatFactory) {
            super(formatFactory);
        }

        @Override
        protected void parseDocumentSource(OWLOntologyDocumentSource documentSource, String baseUri, RDFHandler handler) throws IOException, RDFParseException, RDFHandlerException {
            RioRDFDocumentFormatFactory owlFormatFactory = getSupportedFormat();
            final RDFParser createParser = new OWLAPIRioTrixParser();
            createParser.getParserConfig().addNonFatalError(
                    BasicParserSettings.VERIFY_DATATYPE_VALUES);
            createParser.getParserConfig().addNonFatalError(
                    BasicParserSettings.VERIFY_LANGUAGE_TAGS);
            createParser.setRDFHandler(handler);
            long rioParseStart = System.currentTimeMillis();
            if (owlFormatFactory.isTextual() && documentSource.isReaderAvailable()) {
                createParser.parse(documentSource.getReader(), baseUri);
            } else if (documentSource.isInputStreamAvailable()) {
                createParser.parse(documentSource.getInputStream(), baseUri);
            } else {
                URL url = URI.create(documentSource.getDocumentIRI().toString())
                        .toURL();
                URLConnection conn = url.openConnection();
                createParser.parse(conn.getInputStream(), baseUri);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("rioParse: timing={}", System.currentTimeMillis()
                        - rioParseStart);
            }
        }
    }

}
