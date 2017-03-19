package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.StringComparator;
import org.semanticweb.owlapi.util.StringLengthComparator;
import org.semanticweb.owlapi.vocab.Namespaces;

/**
 * Copied from DefaultPrefixManager.
 * 
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 6.0.0
 */
public class PrefixManagerImpl implements PrefixManager {

    private final Map<String, String> reverseprefix2NamespaceMap;
    private Map<String, String> prefix2NamespaceMap;
    private StringComparator comparator;

    /**
     * Default constructor setting the comparator to string lenght comparator.
     */
    public PrefixManagerImpl() {
        comparator = new StringLengthComparator();
        prefix2NamespaceMap = new TreeMap<>(comparator);
        reverseprefix2NamespaceMap = new TreeMap<>(comparator);
        setupDefaultPrefixes();
    }

    @Override
    public String toString() {
        return prefix2NamespaceMap.toString().replace(",", ",\n");
    }

    private static boolean noSplits(String s, int index) {
        return s.indexOf('#', index) < 0 && s.indexOf('/', index) < 0;
    }

    @Override
    public StringComparator getPrefixComparator() {
        return comparator;
    }

    @Override
    public void setPrefixComparator(StringComparator comparator) {
        checkNotNull(comparator, "comparator cannot be null");
        this.comparator = comparator;
        Map<String, String> p = prefix2NamespaceMap;
        prefix2NamespaceMap = new TreeMap<>(comparator);
        prefix2NamespaceMap.putAll(p);
    }

    @Override
    public void clear() {
        prefix2NamespaceMap.clear();
        reverseprefix2NamespaceMap.clear();
    }

    @Override
    public Stream<String> prefixNames() {
        return prefix2NamespaceMap.keySet().stream();
    }

    private void setupDefaultPrefixes() {
        setPrefix("owl:", Namespaces.OWL.toString());
        setPrefix("rdfs:", Namespaces.RDFS.toString());
        setPrefix("rdf:", Namespaces.RDF.toString());
        setPrefix("xsd:", Namespaces.XSD.toString());
        setPrefix("xml:", Namespaces.XML.toString());
    }

    @Override
    @Nullable
    public String getPrefixIRI(IRI iri) {
        String prefix = reverseprefix2NamespaceMap.get(iri.getNamespace());
        if (prefix == null) {
            String iriString = iri.toString();
            String prefixed = null;
            for (String s : reverseprefix2NamespaceMap.keySet()) {
                if (iriString.startsWith(s) && noSplits(iriString, s.length())) {
                    prefix = reverseprefix2NamespaceMap.get(s);
                    prefixed = iriString.replace(s, prefix);
                }
            }
            if (prefixed != null && XMLUtils.isQName(prefixed)) {
                return prefixed;
            }
        }
        if (prefix == null) {
            return null;
        }
        return iri.prefixedBy(prefix);
    }

    @Override
    @Nullable
    public String getDefaultPrefix() {
        return prefix2NamespaceMap.get(":");
    }

    @Override
    public void setDefaultPrefix(@Nullable String defaultPrefix) {
        String prefixToUnregister = prefix2NamespaceMap.get(":");
        if (prefixToUnregister != null) {
            prefix2NamespaceMap.remove(":");
            reverseprefix2NamespaceMap.remove(prefixToUnregister, ":");
        }
        if (defaultPrefix == null) {
            return;
        }
        prefix2NamespaceMap.put(":", defaultPrefix);
        if (!reverseprefix2NamespaceMap.containsKey(defaultPrefix)) {
            reverseprefix2NamespaceMap.put(defaultPrefix, ":");
        }
    }

    @Override
    public boolean containsPrefixMapping(String prefixName) {
        return prefix2NamespaceMap.get(prefixName) != null;
    }

    @Override
    public void copyPrefixesFrom(PrefixManager from) {
        copyPrefixesFrom(from.getPrefixName2PrefixMap());
    }

    @Override
    public void copyPrefixesFrom(Map<String, String> from) {
        from.forEach(this::setPrefix);
    }

    @Override
    public IRI getIRI(String prefixIRI) {
        if (prefixIRI.startsWith("<")) {
            return IRI.create(prefixIRI.substring(1, prefixIRI.length() - 1));
        }
        int sep = prefixIRI.indexOf(':');
        if (sep == -1) {
            if (getDefaultPrefix() == null) {
                return IRI.create(prefixIRI);
            }
            return IRI.create(getDefaultPrefix() + prefixIRI);
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

    @Override
    public Map<String, String> getPrefixName2PrefixMap() {
        return Collections.unmodifiableMap(prefix2NamespaceMap);
    }

    @Override
    @Nullable
    public String getPrefix(String prefixName) {
        return prefix2NamespaceMap.get(prefixName);
    }

    @Override
    public void setPrefix(String inputPrefixName, String prefix) {
        checkNotNull(inputPrefixName, "prefixName cannot be null");
        checkNotNull(prefix, "prefix cannot be null");
        String _prefixName = inputPrefixName;
        if (!_prefixName.endsWith(":")) {
            _prefixName += ":";
        }
        prefix2NamespaceMap.put(_prefixName, prefix);
        reverseprefix2NamespaceMap.put(prefix, _prefixName);
    }

    @Override
    public void unregisterNamespace(String namespace) {
        List<String> toRemove = new ArrayList<>();
        prefix2NamespaceMap.forEach((k, v) -> {
            if (v.equals(namespace)) {
                toRemove.add(k);
            }
        });
        reverseprefix2NamespaceMap.remove(namespace);
        prefix2NamespaceMap.keySet().removeAll(toRemove);
    }

    @Override
    public String getShortForm(IRI iri) {
        String sf = getPrefixIRI(iri);
        if (sf == null) {
            return iri.toQuotedString();
        } else {
            return sf;
        }
    }

    @Override
    public String getShortForm(OWLEntity entity) {
        return getShortForm(entity.getIRI());
    }
}
