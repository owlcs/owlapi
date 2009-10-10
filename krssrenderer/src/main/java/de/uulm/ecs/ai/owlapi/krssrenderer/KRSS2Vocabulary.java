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
 * Vocabulary of KRSS2 syntax.
 *
 * Author: Olaf Noppens<br>
 * Ulm University<br>
 * Institute of Artificial Intelligence<br>
 */
public enum KRSS2Vocabulary {

    ALL("all"),
    AND("and"),
    AT_LEAST("at-least"),
    AT_MOST("at-most"),
    COMPOSE("compose"),
    DEFINE_CONCEPT("define-concept"),
    DEFINE_INDIVIDUAL("define-individual"),
    DEFINE_PRIMITIVE_CONCEPT("define-primitive-concept"),
    DEFINE_PRIMITIVE_ROLE("define-primitive-role"),
    DEFINE_ROLE("define-role"),
    DISJOINT("disjoint"),
    DISJOINT_ROLES("disjoint-roles"),
    DISTINCT("distinct"),
    DOMAIN("domain"),
    DOMAIN_ATTR(":domain"),
    EQUIVALENT("equivalent"),
    EXACTLY("exactly"),
    IMPLIES("implies"),
    IMPLIES_ROLE("implies-role"),
    INSTANCE("instace"),
    INV("inv"),
    INVERSE("inverse"),
    INVERSE_ATTR(":inverse"),
    LEFTIDENTITY_ATTR(":left-identity"),
    NIL("nil"),
    NOT("not"),
    OR("or"),
    ONE_OF("one-of"),
    PARENTS_ATTR(":parents"),
    PARENT_ATTR(":parent"),
    RANGE_ATTR(":range"),
    REFLEXIVE_ATTR(":reflexive"),
    RELATED("related"),
    RIGHTIDENTITY_ATTR(":right-identity"),
    ROLES_EQUIVALENT("roles-equivalent"),
    ROLE_INCLUSTION("role-inclusion"),
    SOME("some"),
    SUBROLE("subrole"),
    SYMMETRIC_ATTR(":symmetric"),
    TRUE("t"),
    TRANSITIVE_ATTR(":transitive");

    private String shortName;

    KRSS2Vocabulary(String name) {
        shortName = name;
    }

    public String getShortName() {
        return shortName;
    }

    public String toString() {
        return shortName;
    }
}
