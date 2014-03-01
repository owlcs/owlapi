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
package org.coode.owlapi.obo.parser;

/**
 * Concrete implementations of this interface allow specific behaviour for
 * processing specific tag value pairs in an OBO file to be specified. <br>
 * <h3>Tag-Value Pairs (From the OBO 1.4 Guide)</h3> Tag-value pairs consist of
 * a tag name, an unescaped colon, the tag value, and a newline: <br>
 * &lt;tag&gt;: &lt;value&gt; {&lt;trailing modifiers&gt;} ! &lt;comment&gt; The
 * tag name is always a string. The value is always a string, but the value
 * string may require special parsing depending on the tag with which it is
 * associated. <br>
 * In general, tag-value pairs occur on a single line. Multi-line values are
 * possible using escape characters (see escape characters). <br>
 * In general, each stanza type expects a particular set of pre-defined tags.
 * However, a stanza may contain any tag. If a parser does not recognize a tag
 * name for a particular stanza, no error will be generated. This allows new
 * experimental tags to be added without breaking existing parsers. See handling
 * unrecognized tags for specifics. <br>
 * <h3>Trailing Modifiers</h3> Any tag-value pair may be followed by a trailing
 * modifier. Trailing modifiers have been introduced into the OBO 1.2
 * Specification to allow the graceful addition of new features to existing
 * tags. <br>
 * A trailing modifier has the following structure: <br>
 * {&lt;name&gt;=&lt;value&gt;, &lt;name=value&gt;, &lt;name=value&gt;} That is,
 * trailing modifiers are lists of name-value pairs. <br>
 * Parser implementations may choose to decode and/or round-trip these trailing
 * modifiers. However, this is not required. A parser may choose to ignore or
 * strip away trailing modifiers. <br>
 * For this reason, trailing modifiers should only include information that is
 * optional or experimental. <br>
 * Trailing modifiers may also occur within dbxref definitions (see dbxref
 * formatting).
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 10-Jan-2007
 */
public interface TagValueHandler {

    /**
     * Gets the name of the tag handled by this tag value handler
     * 
     * @return The name of the tag
     */
    String getTagName();

    /**
     * Handles a tag. This is called by the OBOConsumer during parsing to handle
     * tags that match the value returned by the {@link #getTagName()} method.
     * 
     * @param currentId
     *        The id of the current frame.
     * @param value
     *        The value of the tag
     * @param qualifierBlock
     *        qualifierBlock
     * @param comment
     *        The hidden comment. This is made up of any characters between !
     *        and the end of line.
     */
    void handle(String currentId, String value, String qualifierBlock,
            String comment);
}
