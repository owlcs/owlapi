package org.obolibrary.obo2owl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;

/**
 * This class will eventually replace the id to uri translation in Owl2Obo and
 * OboO2Owl <br>
 * It is currently in-progress.
 * 
 * @author cjm
 */
public class IdTranslator {

    @Nonnull
    static final String OBO_IRI_PREFIX = "http://purl.obolibrary.org/obo/";
    @Nonnull
    private final Map<String, String> idspaceMap = new HashMap<>();

    /**
     * @param iri
     *        iri
     * @return string for iri
     */
    @Nullable
    public static String translateIRI(@SuppressWarnings("unused") IRI iri) {
        return null;
    }

    /**
     * @param id
     *        id
     * @return string for id
     */
    @Nullable
    public String translateIdToIRIString(@Nonnull String id) {
        if (isURI(id)) {
            return id;
        }
        if (id.contains(":")) {
            // PREFIXED ID
            int p = id.lastIndexOf(':');
            String prefix = id.substring(0, p);
            String localId = id.substring(p + 1);
            if (!localId.isEmpty() && localId.replaceAll("[0-9]", "").isEmpty()) {
                // CANONICAL
                return expandPrefix(prefix) + localId;
            }
        }
        return null;
    }

    /**
     * True if id starts with a standard URI prefix (http, ftp, https) followed
     * by a ":". Does not check if it actually conforms to URI syntax.
     * 
     * @param id
     *        id
     * @return boolean
     */
    public static boolean isURI(@Nonnull String id) {
        if (id.startsWith("http:") || id.startsWith("ftp:") || id.startsWith("https:")) {
            return true;
        }
        return false;
    }

    /**
     * Expands an OBO prefix such as "GO" to
     * "http://purl.obolibrary.org/obo/GO_". By default a prefix XX maps to
     * http://purl.obolibrary.org/obo/XX_
     * 
     * @param prefix
     *        prefix
     * @return expanded prefix
     */
    public String expandPrefix(String prefix) {
        if (idspaceMap.containsKey(prefix)) {
            return idspaceMap.get(prefix);
        }
        return OBO_IRI_PREFIX + prefix + '_';
    }
}
