/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.rdf.turtle.parser;

import org.semanticweb.owlapi.model.IRI;

/*
 * The Class ConsoleTripleHandler.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
class ConsoleTripleHandler implements TripleHandler {

    @Override
    public void handleTriple(IRI subject, IRI predicate, IRI object) {
        System.out.println(subject + " --> " + predicate + " --> " + object);
    }

    @Override
    public void handleTriple(IRI subject, IRI predicate, String object) {
        System.out.println(subject + " --> " + predicate + " --> " + object);
    }

    @Override
    public void handleTriple(IRI subject, IRI predicate, String object,
            String lang) {
        System.out.println(subject + " --> " + predicate + " --> " + object
                + '@' + lang);
    }

    @Override
    public void handleTriple(IRI subject, IRI predicate, String object,
            IRI datatype) {
        System.out.println(subject + " --> " + predicate + " --> " + object
                + "^^" + datatype);
    }

    @Override
    public void handlePrefixDirective(String prefixName, String prefix) {
        System.out.println("PREFIX: " + prefixName + " -> " + prefix);
    }

    @Override
    public void handleBaseDirective(IRI base) {
        System.out.println("BASE: " + base);
    }

    @Override
    public void handleComment(String comment) {
        System.out.println("COMMENT: " + comment);
    }

    @Override
    public void handleEnd() {
        System.out.println("END");
    }
}
