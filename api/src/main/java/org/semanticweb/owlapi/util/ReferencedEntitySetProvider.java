package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 28-Nov-2007<br><br>
 */
public class ReferencedEntitySetProvider implements OWLEntitySetProvider<OWLEntity> {

    private Set<OWLOntology> ontologies;


    public ReferencedEntitySetProvider(Set<OWLOntology> ontologies) {
        this.ontologies = ontologies;
    }


    public Set<OWLEntity> getEntities() {
        Set<OWLEntity> entities = new HashSet<OWLEntity>();
        for(OWLOntology ont : ontologies) {
            entities.addAll(ont.getClassesInSignature());
            entities.addAll(ont.getObjectPropertiesInSignature());
            entities.addAll(ont.getDataPropertiesInSignature());
            entities.addAll(ont.getIndividualsInSignature());
            entities.addAll(ont.getAnnotationPropertiesInSignature());
            entities.addAll(ont.getDatatypesInSignature());
        }
        return entities;
    }
}
