package de.uulm.ecs.ai.owlapi.krssrenderer;
/*
* Copyright (C) 2008, Ulm University
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
 * Author: Olaf Noppens<br>
 * Ulm University<br>
 * Institute of Artificial Intelligence<br>
 */
public enum KRSSVocabulary {

    ALL("all"),
    AND("and"),
    AT_LEAST("at-least"),
    AT_MOST("at-most"),
    DEFINE_CONCEPT("define-concept"),
    DEFINE_PRIMITIVE_CONCEPT("define-primitive-concept"),
    DEFINE_PRIMITIVE_ROLE("define-primitive-role"),
    DEFINE_ROLE("define-role"),
    DISTINCT("distinct"),
    DISJOINT("disjoint"),
    DOMAIN("domain"),
    EQUAL("equal"),
    EXACTLY("exactly"),
    IMPLIES("implies"),
    INSTANCE("instance"),
    INVERSE("inv"),
    NIL("nil"),
    NOT("not"),
    OR("or"),
    RANGE("range"),
    RELATED("related"),
    SOME("some"),
    SYMMETRIC("symmetric"),
    TRUE("t"),
    TOP("top"),
    TRANSITIVE("transitive");

    private String shortName;

    KRSSVocabulary(String name) {
        shortName = name;
    }

    public String getShortName() {
        return shortName;
    }

    @Override
	public String toString() {
        return shortName;
    }
}
