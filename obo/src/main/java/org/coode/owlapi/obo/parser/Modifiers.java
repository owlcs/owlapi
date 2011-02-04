package org.coode.owlapi.obo.parser;

import java.util.*;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 03/02/2011
 */
public class Modifiers {

    private Map<String, Set<String>> modifierNameValuesMap = new LinkedHashMap<String, Set<String>>();

    public Modifiers() {

    }

    /**
     * Parses a list of modifiers.
     * @param modifiersList The string representation of a list of modifiers.  The representation may or may not
     * include the surrounding braces (braces will be ignored).
     * @return A list of modifiers
     */
    public static Modifiers parseModifiers(String modifiersList) {
        Modifiers modifiers = new Modifiers();
        String stripped = modifiersList.replace("{", "").replace("}", "");
        StringTokenizer tokenizer = new StringTokenizer(modifiersList, ",");
        while(tokenizer.hasMoreTokens()) {
            String nameValuePair = tokenizer.nextToken().trim();
            String [] split = nameValuePair.split("=");
            if(split.length == 2) {
                modifiers.addModifier(split[0], split[1]);
            }
        }
        return modifiers;
    }

    /**
     * Adds a modifier
     * @param name The modifier name
     * @param value The modifier value
     */
    public void addModifier(String name, String value) {
        Set<String> values = modifierNameValuesMap.get(name);
        if(values == null) {
            values = new HashSet<String>();
            modifierNameValuesMap.put(name, values);
        }
        values.add(value);
    }

    /**
     * Returns the names of modifiers stored in this modifier object
     * @return The names of modifiers (may be empty)
     */
    public Set<String> getModifierNames() {
        return modifierNameValuesMap.keySet();
    }

    public Set<String> getModifierValues(String modifierName) {
        Set<String> valuesToReturn = new HashSet<String>();
        Set<String> values = modifierNameValuesMap.get(modifierName);
        if(values != null) {
            valuesToReturn.addAll(values);
        }
        return valuesToReturn;
    }

}
