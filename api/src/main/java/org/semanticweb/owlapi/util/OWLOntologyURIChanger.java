package org.semanticweb.owlapi.util;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveImport;
import org.semanticweb.owlapi.model.SetOntologyID;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-May-2007<br><br>
 * <p/>
 * Changes the URI of an ontology and ensures that ontologies which import
 * the ontology have their imports statements updated
 */
public class OWLOntologyURIChanger {

    private OWLOntologyManager owlOntologyManager;


    public OWLOntologyURIChanger(OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
    }


    /**
     * Changes the URI of the specified ontology to the new URI.
     *
     * @param ontology The ontology whose URI is to be changed.
     * @param newIRI
     * @return A list of changes, which when applied will change the URI of the
     *         specified ontology, and also update the imports declarations in any ontologies
     *         which import the specified ontology.
     */
    public List<OWLOntologyChange> getChanges(OWLOntology ontology, IRI newIRI) {
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        changes.add(new SetOntologyID(ontology, new OWLOntologyID(newIRI, ontology.getOntologyID().getVersionIRI())));
        for (OWLOntology ont : owlOntologyManager.getOntologies()) {
            for (OWLImportsDeclaration decl : ont.getImportsDeclarations()) {
                if (decl.getIRI().equals(ontology.getOntologyID().getOntologyIRI())) {
                    changes.add(new RemoveImport(ont, decl));
                    changes.add(new AddImport(ont, owlOntologyManager.getOWLDataFactory().getOWLImportsDeclaration(newIRI)));
                }
            }
        }
        return changes;
    }
}
