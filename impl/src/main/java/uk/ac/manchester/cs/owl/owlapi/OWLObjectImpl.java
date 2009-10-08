package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.AxiomSubjectProvider;
import org.semanticweb.owlapi.util.HashCode;
import org.semanticweb.owlapi.util.OWLEntityCollector;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

import java.util.*;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Oct-2006<br><br>
 */
public abstract class OWLObjectImpl implements OWLObject {

    private OWLDataFactory dataFactory;

    private int hashCode = 0;

    private static OWLObjectTypeIndexProvider typeIndexProvider = new OWLObjectTypeIndexProvider();

    private static AxiomSubjectProvider subjectProvider = new AxiomSubjectProvider();

    private Set<OWLEntity> signature;

    public OWLObjectImpl(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }


    public OWLDataFactory getOWLDataFactory() {
        return dataFactory;
    }


    public Set<OWLEntity> getSignature() {
        if (signature == null) {
            Set<OWLEntity> sig = new HashSet<OWLEntity>();
            OWLEntityCollector collector = new OWLEntityCollector();
            accept(collector);
            sig.addAll(collector.getObjects());
            signature = new HashSet<OWLEntity>(sig);
        }
        return Collections.unmodifiableSet(signature);
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
        for(OWLEntity ent : getSignature()) {
            if(ent.isOWLDatatype()) {
                result.add(ent.asOWLDatatype());
            }
        }
        return result;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return obj instanceof OWLObject;
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
        int thisTypeIndex = typeIndexProvider.getTypeIndex(this);
        int otherTypeIndex = typeIndexProvider.getTypeIndex(o);
        int diff = thisTypeIndex - otherTypeIndex;
        if (diff == 0) {
            // Objects are the same type
            return compareObjectOfSameType(o);
        } else {
            return diff;
        }
    }

    protected abstract int compareObjectOfSameType(OWLObject object);


    public String toString() {
        return ToStringRenderer.getInstance().getRendering(this);
    }

    protected static int compareSets(Set<? extends OWLObject> set1, Set<? extends OWLObject> set2) {
        SortedSet<? extends OWLObject> ss1;
        if (set1 instanceof SortedSet) {
            ss1 = (SortedSet) set1;
        } else {
            ss1 = new TreeSet(set1);
        }
        SortedSet<? extends OWLObject> ss2;
        if (set2 instanceof SortedSet) {
            ss2 = (SortedSet) set2;
        } else {
            ss2 = new TreeSet(set2);
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
