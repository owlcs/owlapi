package org.semanticweb.owlapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.ImportsStructureEntitySorter;
import org.semanticweb.owlapi.util.ShortFormProvider;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Feb-2008<br><br>
 */
public class ShortForm2AnnotationGenerator implements OWLCompositeOntologyChange {

    // The annotation URI to be used.
    private IRI annotationIRI;

    // An optional language tag to be used - could be null;
    private String languageTag;

    private OWLOntologyManager ontologyManager;

    private ShortFormProvider shortFormProvider;

    private OWLOntology ontology;


    public ShortForm2AnnotationGenerator(OWLOntologyManager ontologyManager, OWLOntology ontology,
                                         ShortFormProvider shortFormProvider, IRI annotationIRI, String languageTag) {
        this.ontologyManager = ontologyManager;
        this.shortFormProvider = shortFormProvider;
        this.annotationIRI = annotationIRI;
        this.languageTag = languageTag;
        this.ontology = ontology;
    }


    public ShortForm2AnnotationGenerator(OWLOntologyManager ontologyManager, OWLOntology ontology,
                                         ShortFormProvider shortFormProvider, IRI annotationIRI) {
        this(ontologyManager, ontology, shortFormProvider, annotationIRI, null);
    }


    public List<OWLOntologyChange> getChanges() {
        ImportsStructureEntitySorter sorter = new ImportsStructureEntitySorter(ontology, ontologyManager);
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        Map<OWLOntology, Set<OWLEntity>> ontology2EntityMap = sorter.getObjects();
        for (OWLOntology ont : ontology2EntityMap.keySet()) {
            for (OWLEntity ent : ontology2EntityMap.get(ont)) {
                String shortForm = shortFormProvider.getShortForm(ent);
                OWLLiteral con;
                if (languageTag != null) {
                    con = ontologyManager.getOWLDataFactory().getOWLLiteral(shortForm, languageTag);
                } else {
                    con = ontologyManager.getOWLDataFactory().getOWLLiteral(shortForm);
                }
                if (ontology.containsEntityInSignature(ent)) {
                    OWLOntologyChange chg = new AddAxiom(ont,
                            ontologyManager.getOWLDataFactory().getOWLAnnotationAssertionAxiom(ontologyManager.getOWLDataFactory().getOWLAnnotationProperty(annotationIRI), ent.getIRI(), con));
                    changes.add(chg);
                }
            }
        }
        return changes;
    }
}

