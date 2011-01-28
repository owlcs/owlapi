package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22/12/2010
 */
public class SectionMap {

    private Map<Object, Set<OWLAxiom>> object2Axioms = new HashMap<Object, Set<OWLAxiom>>();

    public boolean isEmpty() {
        return object2Axioms.isEmpty();
    }

    public void add(Object o, OWLAxiom forAxiom) {
        Set<OWLAxiom> axioms = object2Axioms.get(o);
        if(axioms == null) {
            axioms = new HashSet<OWLAxiom>();
            object2Axioms.put(o, axioms);
        }
        axioms.add(forAxiom);
    }

    public void remove(Object o) {
        object2Axioms.remove(o);
    }

    public Collection<Object> getSectionObjects() {
        return object2Axioms.keySet();
    }

    public Set<Set<OWLAnnotation>> getAnnotationsForSectionObject(Object sectionObject) {
        Collection<OWLAxiom> axioms = object2Axioms.get(sectionObject);
        if(axioms == null) {
            return new HashSet<Set<OWLAnnotation>>();
        }
        Set<Set<OWLAnnotation>> annos = new HashSet<Set<OWLAnnotation>>();
        for(OWLAxiom ax : axioms) {
            annos.add(ax.getAnnotations());
        }
        return annos;
    }

}
