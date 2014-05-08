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

import javax.annotation.Nonnull;

/** @author Olaf Noppens, Ulm University, Institute of Artificial Intelligence */
public enum KRSSVocabulary {
    //@formatter:off
/** ALL */              ALL("all"), 
/** AND */              AND("and"), 
/** AT_LEAST */         AT_LEAST("at-least"), 
/** AT_MOST */          AT_MOST("at-most"), 
/** DEFINE_CONCEPT */   DEFINE_CONCEPT("define-concept"), 
/** DEFINE_PRIMITIVE_CONCEPT */ DEFINE_PRIMITIVE_CONCEPT("define-primitive-concept"), 
/** DEFINE_PRIMITIVE_ROLE */    DEFINE_PRIMITIVE_ROLE("define-primitive-role"), 
/** DEFINE_ROLE */      DEFINE_ROLE("define-role"), 
/** DISTINCT */         DISTINCT("distinct"), 
/** DISJOINT */         DISJOINT("disjoint"), 
/** DOMAIN */           DOMAIN("domain"), 
/** EQUAL */            EQUAL("equal"), 
/** EXACTLY */          EXACTLY("exactly"), 
/** IMPLIES */          IMPLIES("implies"), 
/** INSTANCE */         INSTANCE("instance"), 
/** INVERSE */          INVERSE("inv"), 
/** NIL */              NIL("nil"), 
/** NOT */              NOT("not"), 
/** OR */               OR("or"), 
/** RANGE */            RANGE("range"), 
/** RELATED */          RELATED("related"), 
/** SOME */             SOME("some"), 
/** SYMMETRIC */        SYMMETRIC("symmetric"), 
/** TRUE */             TRUE("t"), 
/** TOP */              TOP("top"), 
/** TRANSITIVE */       TRANSITIVE("transitive");
    //@formatter:on
    @Nonnull
    private final String shortName;

    KRSSVocabulary(@Nonnull String name) {
        shortName = name;
    }

    /** @return short name */
    public String getShortName() {
        return shortName;
    }

    @Nonnull
    @Override
    public String toString() {
        return shortName;
    }
}
