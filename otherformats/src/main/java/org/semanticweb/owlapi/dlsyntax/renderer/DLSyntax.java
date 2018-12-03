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
package org.semanticweb.owlapi.dlsyntax.renderer;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public enum DLSyntax {
    //@formatter:off
    /** SUBCLASS */         SUBCLASS        ("\u2291","&#8849;"),
    /** EQUIVALENT_TO */    EQUIVALENT_TO   ("\u2261","&#8801;"),
    /** NOT */              NOT             ("\u00AC","&#172;"),
    /** DISJOINT_WITH */    DISJOINT_WITH   (SUBCLASS + " " + NOT,"&#8849; &#172;"),
    /** EXISTS */           EXISTS          ("\u2203","&#8707;"),
    /** FORALL */           FORALL          ("\u2200","&#8704;"),
    /** IN */               IN              ("\u2208","&#8712;"),
    /** MIN */              MIN             ("\u2265","&#8805;"),
    /** EQUAL */            EQUAL           ("=","="),
    /** NOT_EQUAL */        NOT_EQUAL       ("\u2260","&#8800;"),
    /** MAX */              MAX             ("\u2264","&#8804;"),
    /** INVERSE */          INVERSE         ("\u207B","&#8315;"),// Superscript minus
    /** AND */              AND             ("\u2293","&#8851;"),
    /** TOP */              TOP             ("\u22A4","&#8868;"),
    /** BOTTOM */           BOTTOM          ("\u22A5","&#8869;"),
    /** OR */               OR              ("\u2294","&#8852;"),
    /** COMP */             COMP            ("\u2218","&#8728;"),
    /** WEDGE */            WEDGE           ("\u22C0","&#8896;"),
    /** IMPLIES */          IMPLIES         ("\u2190","&#8592;"),
    /** COMMA */            COMMA           (",",","),
    /** SELF */             SELF            ("self","self");
    //@formatter:on

    private final String unicodeSymbol;
    private final String htmlEscape;

    DLSyntax(String unicode, String htmlEscape) {
        unicodeSymbol = unicode;
        this.htmlEscape = htmlEscape;
    }

    @Override
    public String toString() {
        return unicodeSymbol;
    }

    /**
     * @return HTML escaped version of the DLSyntax keyword
     */
    public String toHTMLString() {
        return htmlEscape;
    }
}
