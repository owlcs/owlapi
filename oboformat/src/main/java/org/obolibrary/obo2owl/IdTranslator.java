package org.obolibrary.obo2owl;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.IRI;

/**
 * This class will eventually replace the id to uri translation in Owl2Obo and
 * OboO2Owl <br>
 * It is currently in-progress.
 * 
 * @author cjm
 */
public class IdTranslator {

    String OBO_IRI_PREFIX = "http://purl.obolibrary.org/obo/";
    private Map<String, String> idspaceMap = new HashMap<String, String>();

    /**
     * @param iri
     *        iri
     * @return string for iri
     */
    public String translateIRI(IRI iri) {
        return null;
    }

    /**
     * @param id
     *        id
     * @return string for id
     */
    public String translateIdToIRIString(String id) {
        if (isURI(id)) {
            return id;
        }
        if (id.contains(":")) {
            // PREFIXED ID
            int p = id.lastIndexOf(":");
            String prefix = id.substring(0, p);
            String localId = id.substring(p + 1);
            if (localId.length() > 0
                    && localId.replaceAll("[0-9]", "").length() == 0) {
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
    public boolean isURI(String id) {
        if (id.startsWith("http:") || id.startsWith("ftp:")
                || id.startsWith("https:")) {
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
        return OBO_IRI_PREFIX + prefix + "_";
    }
}
