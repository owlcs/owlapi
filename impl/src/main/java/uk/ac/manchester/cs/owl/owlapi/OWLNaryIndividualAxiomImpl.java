package uk.ac.manchester.cs.owl.owlapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNaryIndividualAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLNaryIndividualAxiomImpl extends OWLIndividualAxiomImpl implements OWLNaryIndividualAxiom {

    private Set<OWLIndividual> individuals;


    public OWLNaryIndividualAxiomImpl(OWLDataFactory dataFactory, Set<? extends OWLIndividual> individuals, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.individuals = new TreeSet<OWLIndividual>(individuals);
    }


    public Set<OWLIndividual> getIndividuals() {
        return CollectionFactory.getCopyOnRequestSet(individuals);
    }

    public List<OWLIndividual> getIndividualsAsList() {
        return new ArrayList<OWLIndividual>(individuals);
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLNaryIndividualAxiom)) {
                return false;
            }
            return ((OWLNaryIndividualAxiom) obj).getIndividuals().equals(individuals);
        }
        return false;
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        return compareSets(individuals, ((OWLNaryIndividualAxiom) object).getIndividuals());
    }
}
