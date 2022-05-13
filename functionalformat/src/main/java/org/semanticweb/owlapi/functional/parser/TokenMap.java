package org.semanticweb.owlapi.functional.parser;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Map between string tokens and indexes as defined in OWLFunctionalSyntaxParserConstants. This will
 * get updated automatically every time the parser is updated.
 */
public class TokenMap {

    private static Integer DEFAULT = Integer.valueOf(OWLFunctionalSyntaxParserConstants.PN_LOCAL);
    private static final Map<String, Integer> makeTokenMap = makeTokenMap();
    private static final String[] index = reverse(makeTokenMap);

    private TokenMap() {}

    private static Map<String, Integer> makeTokenMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        for (Field f : OWLFunctionalSyntaxParserConstants.class.getDeclaredFields()) {
            if (f.getType().equals(int.class)) {
                try {
                    int indexValue = f.getInt(null);
                    String key = OWLFunctionalSyntaxParserConstants.tokenImage[indexValue];
                    if (key.charAt(0) == '"' && key.charAt(key.length() - 1) == '"') {
                        key = key.substring(1, key.length() - 1);
                    }
                    if (key.codePoints().allMatch(Character::isLetter)) {
                        map.put(key, Integer.valueOf(indexValue));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return map;
    }

    private static String[] reverse(Map<String, Integer> map) {
        String[] reverse =
            new String[map.values().stream().mapToInt(Integer::intValue).max().orElse(-1) + 1];
        Arrays.fill(reverse, "");
        map.forEach((a, b) -> reverse[b.intValue()] = a);
        return reverse;
    }

    /**
     * @param s String to match
     * @return index of the corresponding token in OWLFunctionalSyntaxParserConstants.tokenImage if
     *         one found, index of {@code <PN_LOCAL>} otherwise
     */
    public static int tokenIndex(String s) {
        return makeTokenMap.getOrDefault(s, DEFAULT).intValue();
    }

    /**
     * @param in index to match
     * @return String corresponding to index of the corresponding token in
     *         OWLFunctionalSyntaxParserConstants.tokenImage if one found, index of
     *         {@code <PN_LOCAL>} otherwise
     */
    public static String indexToken(int in) {
        if (in >= index.length || in < 0) {
            return "";
        }
        return index[in];
    }
}
