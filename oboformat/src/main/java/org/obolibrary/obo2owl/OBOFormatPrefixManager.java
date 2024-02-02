package org.obolibrary.obo2owl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.StringComparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class OBOFormatPrefixManager implements PrefixManager {

    private final String OBO_NS = "http://purl.obolibrary.org/obo/";

    @Nonnull
    private StringComparator comparator = new StringComparator() {

        private final Comparator<String> alphabetical = Comparator.comparing(s -> s);
        private final Comparator<String> byLength = Comparator.comparing(s -> -s.length());
        private final Comparator<String> byBoth = byLength.thenComparing(alphabetical);

        @Override
        public int compare(String o1, String o2) {
            return byBoth.compare(o1, o2);
        }

    };

    @Nonnull
    private final TreeMap<String, String> nsToPrefix;
    @Nonnull
    private final TreeMap<String, String> prefixToNS;

    public OBOFormatPrefixManager(@Nullable PrefixManager pm) {
        prefixToNS = new TreeMap<>(comparator);
        nsToPrefix = new TreeMap<>(comparator);
        if (pm != null) {
            copyPrefixesFrom(pm);
        }
    }

    @Nonnull
    @Override
    public StringComparator getPrefixComparator() {
        return this.comparator;
    }

    @Override
    public void setPrefixComparator(@Nonnull StringComparator comparator) {
        this.comparator = comparator;
    }

    @Nullable
    @Override
    public String getDefaultPrefix() {
        return null;
    }

    @Override
    public boolean containsPrefixMapping(@Nonnull String prefixName) {
        return prefixToNS.containsKey(prefixName);
    }

    @Nullable
    @Override
    public String getPrefix(@Nonnull String prefixName) {
        return prefixToNS.get(prefixName);
    }

    @Nonnull
    @Override
    public Map<String, String> getPrefixName2PrefixMap() {
        return Collections.unmodifiableMap(prefixToNS);
    }

    @Nonnull
    @Override
    public IRI getIRI(@Nonnull String prefixIRI) {
        if (prefixIRI.startsWith("<")) {
            return IRI.create(prefixIRI.substring(1, prefixIRI.length() - 1));
        }
        int sep = prefixIRI.indexOf(':');
        if (sep == -1) {
            if (getDefaultPrefix() != null) {
                return IRI.create(getDefaultPrefix() + prefixIRI);
            } else {
                return IRI.create(prefixIRI);
            }
        } else {
            String prefixName = prefixIRI.substring(0, sep + 1);
            if (!containsPrefixMapping(prefixName)) {
                throw new OWLRuntimeException(
                        "Prefix not registered for prefix name: " + prefixName);
            }
            String prefix = getPrefix(prefixName);
            String localName = prefixIRI.substring(sep + 1);
            return IRI.create(prefix, localName);
        }
    }

    @Nullable
    @Override
    public String getPrefixIRI(@Nonnull IRI iri) {
        String iriString = iri.toString();
        Optional<Map.Entry<String, String>> mappingOpt = nsToPrefix.entrySet().stream().filter(e -> iriString.startsWith(e.getKey())).findFirst();
        if (mappingOpt.isPresent()) {
            Map.Entry<String, String> mapping = mappingOpt.get();
            String localId = iriString.substring(mapping.getKey().length());
            return mapping.getValue() + ":" + localId;
        } else return null;
    }

    @Nullable
    @Override
    public String getPrefixIRIIgnoreQName(@Nonnull IRI iri) {
        return getPrefixIRI(iri);
    }

    @Nonnull
    @Override
    public Set<String> getPrefixNames() {
        return prefixToNS.keySet();
    }

    @Override
    public void setDefaultPrefix(@Nullable String defaultPrefix) {
    }

    /**
     * @param prefixName name The prefix name (must end with a colon)
     * @param prefix The prefix. This prefix manager does not accept prefixes that overlap with
     *               the default OBO namespace.
     */
    @Override
    public void setPrefix(@Nonnull String prefixName, @Nonnull String prefix) {
        if (!prefixName.isEmpty() && !prefixName.equals(":") && !OBO_NS.startsWith(prefix) && !prefix.startsWith(OBO_NS)) {
            String cleanPrefixName = prefixName;
            if (prefixName.endsWith(":")) {
                cleanPrefixName = prefixName.substring(0, prefixName.length() - 1);
            }
            prefixToNS.put(cleanPrefixName, prefix);
            nsToPrefix.put(prefix, cleanPrefixName);
        }
    }

    @Override
    public void copyPrefixesFrom(@Nonnull PrefixManager from) {
        copyPrefixesFrom(from.getPrefixName2PrefixMap());
    }

    @Override
    public void copyPrefixesFrom(@Nonnull Map<String, String> from) {
        for (Map.Entry<String, String> e : from.entrySet()) {
            String prefix = e.getKey();
            if (!prefix.isEmpty()) {
                setPrefix(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public void unregisterNamespace(@Nonnull String namespace) {
        List<String> toRemove = new ArrayList<>();
        for (Map.Entry<String, String> e : prefixToNS.entrySet()) {
            if (e.getValue().equals(namespace)) {
                toRemove.add(e.getKey());
            }
        }
        for (String s : toRemove) {
            prefixToNS.remove(s);
        }
        nsToPrefix.remove(namespace);
    }

    @Override
    public void clear() {
        prefixToNS.clear();
        nsToPrefix.clear();
    }
}
