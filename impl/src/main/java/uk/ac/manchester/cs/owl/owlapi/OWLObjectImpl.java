package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.util.HashCode;
import org.semanticweb.owlapi.util.OWLClassExpressionCollector;
import org.semanticweb.owlapi.util.OWLEntityCollector;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Oct-2006<br><br>
 */
public abstract class OWLObjectImpl implements OWLObject {

    private final OWLDataFactory dataFactory;

    private int hashCode = 0;


    private Set<OWLEntity> signature;

    public OWLObjectImpl(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }


    public OWLDataFactory getOWLDataFactory() {
        return dataFactory;
    }


    public Set<OWLEntity> getSignature() {
        if (signature == null) {
            OWLEntityCollector collector = new OWLEntityCollector();
            accept(collector);
            signature = Collections.unmodifiableSet(collector.getObjects());
        }
        return signature;
    }


    public Set<OWLClass> getClassesInSignature() {
        Set<OWLClass> result = new HashSet<OWLClass>();
        for (OWLEntity ent : getSignature()) {
            if (ent.isOWLClass()) {
                result.add(ent.asOWLClass());
            }
        }
        return result;
    }


    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        Set<OWLDataProperty> result = new HashSet<OWLDataProperty>();
        for (OWLEntity ent : getSignature()) {
            if (ent.isOWLDataProperty()) {
                result.add(ent.asOWLDataProperty());
            }
        }
        return result;
    }


    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        Set<OWLObjectProperty> result = new HashSet<OWLObjectProperty>();
        for (OWLEntity ent : getSignature()) {
            if (ent.isOWLObjectProperty()) {
                result.add(ent.asOWLObjectProperty());
            }
        }
        return result;
    }


    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        Set<OWLNamedIndividual> result = new HashSet<OWLNamedIndividual>();
        for (OWLEntity ent : getSignature()) {
            if (ent.isOWLNamedIndividual()) {
                result.add(ent.asOWLNamedIndividual());
            }
        }
        return result;
    }

    /**
     * A convenience method that obtains the datatypes
     * that are in the signature of this object
     * @return A set containing the datatypes that are in the signature
     *         of this object.
     */
    public Set<OWLDatatype> getDatatypesInSignature() {
        Set<OWLDatatype> result = new HashSet<OWLDatatype>();
        for (OWLEntity ent : getSignature()) {
            if (ent.isOWLDatatype()) {
                result.add(ent.asOWLDatatype());
            }
        }
        return result;
    }

    public Set<OWLClassExpression> getNestedClassExpressions() {
        OWLClassExpressionCollector collector = new OWLClassExpressionCollector();
        return this.accept(collector);
    }

    public boolean equals(Object obj) {
        return obj == this || obj != null && obj instanceof OWLObject;
    }


    public int hashCode() {
        if (hashCode == 0) {
            hashCode = HashCode.hashCode(this);
        }
        return hashCode;
    }


    final public int compareTo(OWLObject o) {
//        if (o instanceof OWLAxiom && this instanceof OWLAxiom) {
//            OWLObject thisSubj = subjectProvider.getSubject((OWLAxiom) this);
//            OWLObject otherSubj = subjectProvider.getSubject((OWLAxiom) o);
//            int axDiff = thisSubj.compareTo(otherSubj);
//            if (axDiff != 0) {
//                return axDiff;
//            }
//        }
        OWLObjectTypeIndexProvider typeIndexProvider = new OWLObjectTypeIndexProvider();
        int thisTypeIndex = typeIndexProvider.getTypeIndex(this);
        int otherTypeIndex = typeIndexProvider.getTypeIndex(o);
        int diff = thisTypeIndex - otherTypeIndex;
        if (diff == 0) {
            // Objects are the same type
            return compareObjectOfSameType(o);
        }
        else {
            return diff;
        }
    }

    protected abstract int compareObjectOfSameType(OWLObject object);


    public String toString() {
        return ToStringRenderer.getInstance().getRendering(this);
    }

    public boolean isTopEntity() {
        return false;
    }

    public boolean isBottomEntity() {
        return false;
    }

    @SuppressWarnings("unchecked")
    protected static int compareSets(Set<? extends OWLObject> set1, Set<? extends OWLObject> set2) {
        SortedSet<? extends OWLObject> ss1;
        if (set1 instanceof SortedSet) {
            ss1 = (SortedSet<? extends OWLObject>) set1;
        }
        else {
            ss1 = new TreeSet<OWLObject>(set1);
        }
        SortedSet<? extends OWLObject> ss2;
        if (set2 instanceof SortedSet) {
            ss2 = (SortedSet<? extends OWLObject>) set2;
        }
        else {
            ss2 = new TreeSet<OWLObject>(set2);
        }
        int i = 0;
        Iterator<? extends OWLObject> thisIt = ss1.iterator();
        Iterator<? extends OWLObject> otherIt = ss2.iterator();
        while (i < ss1.size() && i < ss2.size()) {
            OWLObject o1 = thisIt.next();
            OWLObject o2 = otherIt.next();
            int diff = o1.compareTo(o2);
            if (diff != 0) {
                return diff;
            }
            i++;
        }
        return ss1.size() - ss2.size();
    }
}
