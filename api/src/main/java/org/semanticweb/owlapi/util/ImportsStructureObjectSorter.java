package org.semanticweb.owlapi.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 30-May-2008<br><br>
 *
 * Sorts objects into sets based on where they appear in the imports closure of an ontology.
 * Consider ontology B that imports ontology A.  A map will
 * be generated that maps each ontology, A, B, to a set of objects that are associated with
 * the ontology.  If an object is associated with ontology A and associated with ontology B
 * then it will only appear in the set of objects that are associated with ontology A since
 * A appears higher up the imports closure.  An example of the use of this class is to
 * obtain a map of ontologies to sets of entities where each set of entities contains entities
 * that are first mentioned in the ontology that maps to them. 
 * 
 * @see org.semanticweb.owlapi.util.ImportsStructureEntitySorter
 */
public class ImportsStructureObjectSorter<O> {

    private OWLOntology ontology;

    private OWLOntologyManager manager;

    private ObjectSelector<O> objectSelector;


    /**
     * Creates a sorter for the specified ontology, whose imports closure is obtained
     * with the specified manager, and for each ontology whose objects are selected
     * using the specified object selector.
     * @param ontology The ontology
     * @param manager The manager that will be used to obtain the imports closure
     * @param objectSelector The selector that will be used to select objects that
     * are associated with each ontology.
     */
    public ImportsStructureObjectSorter(OWLOntology ontology, OWLOntologyManager manager,
                                        ObjectSelector<O> objectSelector) {
        this.ontology = ontology;
        this.manager = manager;
        this.objectSelector = objectSelector;
    }


    /**
     * Gets a map that maps ontologies to sets of associated objects.  The ontologies
     * will be the ontologies that are contained in the imports closure of the original
     * specified ontology.
     * @return The map.
     */
    public Map<OWLOntology, Set<O>> getObjects() {
        List<OWLOntology> imports = manager.getSortedImportsClosure(ontology);
        Map<OWLOntology, Set<O>> ontology2EntityMap = new HashMap<OWLOntology, Set<O>>();
        Set<O> processed = new HashSet<O>();
        for(int i = imports.size() - 1; i > -1; i--) {
            OWLOntology currentOnt = imports.get(i);
            Set<O> objects = new HashSet<O>();
            for(O obj : objectSelector.getObjects(currentOnt)) {
                if(!processed.contains(obj)) {
                    processed.add(obj);
                    objects.add(obj);
                }
            }
            ontology2EntityMap.put(currentOnt, objects);
        }
        return ontology2EntityMap;
    }


    public static interface ObjectSelector<O> {

        public Set<O> getObjects(OWLOntology ontology);
    }


}
