package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-Jun-2009
 *
 * A special short form provider that delegates to a prefix manager to obtain short forms.  The only difference
 * between this short form provider and a prefix manager is that names with the default prefix do not have a colon
 * with this short form provider.
 */
public class ManchesterOWLSyntaxPrefixNameShortFormProvider implements ShortFormProvider {

    private DefaultPrefixManager prefixManager;


    /**
     * Constructs a short form provider that reuses any prefix name mappings obtainable from the format of
     * the specified ontology (the manager will be asked for the ontology format of the specified ontology)
     * @param man The manager
     * @param ont The ontology
     */
    public ManchesterOWLSyntaxPrefixNameShortFormProvider(OWLOntologyManager man, OWLOntology ont) {
        this(man.getOntologyFormat(ont));
    }

    /**
     * Constructs a short form provider that reuses any prefix name mappings from the specified ontology format
     * @param format The format from which prefix name mappings will be reused
     */
    public ManchesterOWLSyntaxPrefixNameShortFormProvider(OWLOntologyFormat format) {
        prefixManager = new DefaultPrefixManager();
        if(format instanceof PrefixOWLOntologyFormat) {
            PrefixOWLOntologyFormat prefixFormat = (PrefixOWLOntologyFormat) format;
            for(String prefixName : prefixFormat.getPrefixName2PrefixMap().keySet()) {
                prefixManager.setPrefix(prefixName, prefixFormat.getPrefix(prefixName));
            }
        }
    }

    /**
     * Constructs a short form provider that uses the specified prefix mappings
     * @param prefixManager A prefix manager which will be used to obtain prefix mappings
     */
    public ManchesterOWLSyntaxPrefixNameShortFormProvider(DefaultPrefixManager prefixManager) {
        this.prefixManager = prefixManager;
    }

    public PrefixManager getPrefixManager() {
        return new DefaultPrefixManager(prefixManager);
    }

    /**
     * Gets the short form for the specified entity.
     * @param entity The entity.
     * @return A string which represents a short rendering
     *         of the speicified entity.
     */
    public String getShortForm(OWLEntity entity) {
        String sf = prefixManager.getShortForm(entity);
        if(sf.startsWith(":")) {
            return sf.substring(1);
        }
        else {
            return sf;
        }
    }

    /**
     * Gets the short form for an IRI
     * @param iri The IRI
     * @return The short form for the specified IRI
     */
    public String getShortForm(IRI iri) {
        return iri.toQuotedString();
    }

    /**
     * Disposes of the short form proivider.  This frees any
     * resources and clears any caches.
     */
    public void dispose() {
        prefixManager.dispose();
    }
}
