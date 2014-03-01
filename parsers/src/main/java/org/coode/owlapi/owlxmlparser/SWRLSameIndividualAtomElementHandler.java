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
 * Copyright 2011, University of Manchester
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
package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 08-Oct-2009
 */
public class SWRLSameIndividualAtomElementHandler extends
        SWRLAtomElementHandler {

    private SWRLIArgument arg0;
    private SWRLIArgument arg1;

    /**
     * @param handler
     *        owlxml handler
     */
    public SWRLSameIndividualAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(SWRLVariableElementHandler handler)
            throws OWLXMLParserException {
        if (arg0 == null) {
            arg0 = handler.getOWLObject();
        } else if (arg1 == null) {
            arg1 = handler.getOWLObject();
        }
    }

    @Override
    public void handleChild(OWLIndividualElementHandler handler)
            throws OWLXMLParserException {
        if (arg0 == null) {
            arg0 = getOWLDataFactory().getSWRLIndividualArgument(
                    handler.getOWLObject());
        } else if (arg1 == null) {
            arg1 = getOWLDataFactory().getSWRLIndividualArgument(
                    handler.getOWLObject());
        }
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        setAtom(getOWLDataFactory().getSWRLSameIndividualAtom(arg0, arg1));
        getParentHandler().handleChild(this);
    }
}
