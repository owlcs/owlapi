/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Ulm University
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.krss2.renderer;

/**
 * Vocabulary of KRSS2 syntax.
 *
 * @author Olaf Noppens, Ulm University, Institute of Artificial Intelligence
 */
public enum KRSS2Vocabulary implements KRSS {
    //@formatter:off
    /** ALL. */                      ALL("all"),
    /** AND. */                      AND("and"),
    /** AT_LEAST. */                 AT_LEAST("at-least"),
    /** AT_MOST. */                  AT_MOST("at-most"),
    /** COMPOSE. */                  COMPOSE("compose"),
    /** DEFINE_CONCEPT. */           DEFINE_CONCEPT("define-concept"),
    /** DEFINE_INDIVIDUAL. */        DEFINE_INDIVIDUAL("define-individual"),
    /** DEFINE_PRIMITIVE_CONCEPT. */ DEFINE_PRIMITIVE_CONCEPT("define-primitive-concept"),
    /** DEFINE_PRIMITIVE_ROLE. */    DEFINE_PRIMITIVE_ROLE("define-primitive-role"),
    /** DEFINE_ROLE. */              DEFINE_ROLE("define-role"),
    /** DISJOINT. */                 DISJOINT("disjoint"),
    /** DISJOINT_ROLES. */           DISJOINT_ROLES("disjoint-roles"),
    /** DISTINCT. */                 DISTINCT("distinct"),
    /** DOMAIN. */                   DOMAIN("domain"),
    /** DOMAIN_ATTR. */              DOMAIN_ATTR(":domain"),
    /** EQUIVALENT. */               EQUIVALENT("equivalent"),
    /** EXACTLY. */                  EXACTLY("exactly"),
    /** IMPLIES. */                  IMPLIES("implies"),
    /** IMPLIES_ROLE. */             IMPLIES_ROLE("implies-role"),
    /** INSTANCE. */                 INSTANCE("instace"),
    /** INV. */                      INV("inv"),
    /** INVERSE. */                  INVERSE("inverse"),
    /** INVERSE_ATTR. */             INVERSE_ATTR(":inverse"),
    /** LEFTIDENTITY_ATTR. */        LEFTIDENTITY_ATTR(":left-identity"),
    /** NIL. */                      NIL("nil"),
    /** NOT. */                      NOT("not"),
    /** OR. */                       OR("or"),
    /** ONE_OF. */                   ONE_OF("one-of"),
    /** PARENTS_ATTR. */             PARENTS_ATTR(":parents"),
    /** PARENT_ATTR. */              PARENT_ATTR(":parent"),
    /** RANGE_ATTR. */               RANGE_ATTR(":range"),
    /** REFLEXIVE_ATTR. */           REFLEXIVE_ATTR(":reflexive"),
    /** RELATED. */                  RELATED("related"),
    /** RIGHTIDENTITY_ATTR. */       RIGHTIDENTITY_ATTR(":right-identity"),
    /** ROLES_EQUIVALENT. */         ROLES_EQUIVALENT("roles-equivalent"),
    /** ROLE_INCLUSTION. */          ROLE_INCLUSTION("role-inclusion"),
    /** SOME. */                     SOME("some"),
    /** SUBROLE. */                  SUBROLE("subrole"),
    /** SYMMETRIC_ATTR. */           SYMMETRIC_ATTR(":symmetric"),
    /** TRUE. */                     TRUE("t"),
    /** TRANSITIVE_ATTR. */          TRANSITIVE_ATTR(":transitive");
    //@formatter:on

    private final String shortName;

    KRSS2Vocabulary(String name) {
        shortName = name;
    }

    /**
     * @return short name
     */
    public String getShortName() {
        return shortName;
    }

    @Override
    public String toString() {
        return shortName;
    }
}
