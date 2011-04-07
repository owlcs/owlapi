/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, Ulm University
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
 * Copyright 2011, Ulm University
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

package de.uulm.ecs.ai.owlapi.krssrenderer;
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

    @Override
	public String toString() {
        return shortName;
    }
}
