/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2014, The University of Manchester
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
 * Copyright 2014, The University of Manchester
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

import org.semanticweb.owlapi.model.UnloadableImportException;

/** @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group
 * @since 2.0.0
 * @param <O>
 *            handled type */
public interface OWLElementHandler<O> {
    /** @param name
     *            element name */
    void startElement(String name);

    /** @param localName
     *            local attribute name
     * @param value
     *            attribute value */
    void attribute(String localName, String value);

    /** @throws UnloadableImportException
     *             if an import cannot be resolved */
    void endElement() throws UnloadableImportException;

    /** @return object */
    O getOWLObject();

    /** @param handler
     *            element handler */
    void setParentHandler(OWLElementHandler<?> handler);

    /** @param handler
     *            element handler */
    void handleChild(AbstractOWLAxiomElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(AbstractClassExpressionElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(AbstractOWLObjectPropertyElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(OWLDataPropertyElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(OWLIndividualElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(AbstractOWLDataRangeHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(OWLLiteralElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(OWLAnnotationElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(OWLAnonymousIndividualElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(OWLSubObjectPropertyChainElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(OWLDatatypeFacetRestrictionElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(OWLAnnotationPropertyElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(AbstractIRIElementHandler handler);

    /** @param chars
     *            chars to handle
     * @param start
     *            start index
     * @param length
     *            end index */
    void handleChars(char[] chars, int start, int length);

    /** @param handler
     *            element handler */
    void handleChild(SWRLVariableElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(SWRLAtomElementHandler handler);

    /** @param handler
     *            element handler */
    void handleChild(SWRLAtomListElementHandler handler);

    /** @return text handled */
    String getText();

    /** @return true if text can be contained */
    boolean isTextContentPossible();
}
