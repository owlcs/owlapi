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
package org.coode.owlapi.obo12.parser;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 03/02/2011
 */
class Modifiers {

    private Map<String, Set<String>> modifierNameValuesMap = new LinkedHashMap<>();

    public Modifiers() {}

    /**
     * Parses a list of modifiers.
     * 
     * @param modifiersList
     *        The string representation of a list of modifiers. The
     *        representation may or may not include the surrounding braces
     *        (braces will be ignored).
     * @return A list of modifiers
     */
    public static Modifiers parseModifiers(String modifiersList) {
        Modifiers modifiers = new Modifiers();
        StringTokenizer tokenizer = new StringTokenizer(modifiersList, ",");
        while (tokenizer.hasMoreTokens()) {
            String nameValuePair = tokenizer.nextToken().trim();
            String[] split = nameValuePair.split("=");
            if (split.length == 2) {
                modifiers.addModifier(split[0], split[1]);
            }
        }
        return modifiers;
    }

    /**
     * Adds a modifier
     * 
     * @param name
     *        The modifier name
     * @param value
     *        The modifier value
     */
    public void addModifier(String name, String value) {
        Set<String> values = modifierNameValuesMap.get(name);
        if (values == null) {
            values = new HashSet<>();
            modifierNameValuesMap.put(name, values);
        }
        values.add(value);
    }

    /**
     * Returns the names of modifiers stored in this modifier object
     * 
     * @return The names of modifiers (may be empty)
     */
    public Set<String> getModifierNames() {
        return modifierNameValuesMap.keySet();
    }

    public Set<String> getModifierValues(String modifierName) {
        Set<String> valuesToReturn = new HashSet<>();
        Set<String> values = modifierNameValuesMap.get(modifierName);
        if (values != null) {
            valuesToReturn.addAll(values);
        }
        return valuesToReturn;
    }
}
