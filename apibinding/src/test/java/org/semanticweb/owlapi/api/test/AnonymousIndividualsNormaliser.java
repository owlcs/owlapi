package org.semanticweb.owlapi.api.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.util.OWLObjectDuplicator;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Jul-2009
 */
public class AnonymousIndividualsNormaliser extends OWLObjectDuplicator {

    private Map<OWLAnonymousIndividual, OWLAnonymousIndividual> renamingMap = new HashMap<OWLAnonymousIndividual, OWLAnonymousIndividual>();

    private OWLDataFactory dataFactory;

    private int counter = 0;

    /**
     * Creates an object duplicator that duplicates objects using the specified
     * data factory.
     * @param dataFactory The data factory to be used for the duplication.
     */
    public AnonymousIndividualsNormaliser(OWLDataFactory dataFactory) {
        super(dataFactory);
        this.dataFactory = dataFactory;
    }

    public Set<OWLAxiom> getNormalisedAxioms(Set<OWLAxiom> axioms) {
        List<OWLAxiom> axiomsList = new ArrayList<OWLAxiom>(axioms);
        Collections.sort(axiomsList);
        Set<OWLAxiom> normalised = new HashSet<OWLAxiom>();
        for(OWLAxiom ax : axiomsList) {
            OWLAxiom dup = duplicateObject(ax);
            normalised.add(dup);
        }
        return normalised;
    }

    @Override
	public void visit(OWLAnonymousIndividual individual) {
        OWLAnonymousIndividual ind = renamingMap.get(individual);
        if(ind == null) {
            counter++;
            ind = dataFactory.getOWLAnonymousIndividual("anon-ind-" + counter);
            renamingMap.put(individual, ind);
        }
        setLastObject(ind);
    }
}
