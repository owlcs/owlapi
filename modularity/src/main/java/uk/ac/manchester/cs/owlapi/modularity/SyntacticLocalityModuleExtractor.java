package uk.ac.manchester.cs.owlapi.modularity;

import com.clarkparsia.owlapi.modularity.locality.LocalityClass;
import com.clarkparsia.owlapi.modularity.locality.SyntacticLocalityEvaluator;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.modularity.OntologySegmenter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Implementation of module extraction based on syntactic locality.
 *
 * @author Thomas Schneider
 * @author School of Computer Science
 * @author University of Manchester
 */
public class SyntacticLocalityModuleExtractor implements OntologySegmenter {

    /**
     * Auxiliary inner class for the representation of the associated ontology and all its sub-ontologies as arrays of
     * axioms. Advantages: (1) quicker set manipulation operations; (2) storage of all referenced entities of an axiom once
     * this axiom is dealt with.
     *
     * @author Thomas Schneider
     * @author School of Computer Science
     * @author University of Manchester
     */
    protected class OntologyAxiomSet {

        /**
         * Array representing all axioms of the associated ontology.
         */
        protected OWLAxiom[] ax;


        /**
         * Creates a new OntologyAxiomSet from a given set of axioms without looking up the referenced entities.
         *
         * @param axs the set of axioms representing the ontology
         */
        public OntologyAxiomSet(Set<OWLAxiom> axs) {
            ax = axs.toArray(new OWLAxiom[axs.size()]);
        }


        /**
         * Returns the number of axioms in this set.
         *
         * @return the number of axioms in this set
         */
        public int size() {
            return ax.length;
        }


        /**
         * Returns some axiom from this set.
         *
         * @param i a number for an axiom
         * @return the i-th axiom in this set
         */
        public OWLAxiom getAxiom(int i) {
            return ax[i];
        }


        /**
         * Returns an array containing all axioms in this set.
         *
         * @return array containing all axioms in this set
         */
        public OWLAxiom[] getAllAxioms() {
            return ax;
        }


        /**
         * Returns the set of axioms that is represented by some array of Booleans.
         *
         * @param isIn an array of Booleans
         * @return the set of axioms represented by the specified array of Booleans
         */
        public Set<OWLAxiom> getAxiomSet(boolean[] isIn) {
            HashSet<OWLAxiom> gas = new HashSet<OWLAxiom>();
            for (int i = 0; i < isIn.length; i++) {
                if (isIn[i]) {
                    gas.add(ax[i]);
                }
            }
            return gas;
        }


        /**
         * Constructs an array of Booleans that represents a subset of this set. The subset either equals this set (if
         * init==true) or is the empty set (if init==false).
         *
         * @param init determines the initial value of the subset
         * @return array of Booleans representing the specified subset
         */
        public boolean[] getSubset(boolean init) {
            boolean[] subset = new boolean[ax.length];
            for (int i = 0; i < ax.length; i++)
                subset[i] = init;
            return subset;
        }


        /**
         * Clones an array of Booleans that represents a subset of this set.
         *
         * @param oldSubset an array representing the original subset
         * @return an array representing the new subset
         */
        public boolean[] cloneSubset(boolean[] oldSubset) {
            boolean[] newSubset = new boolean[ax.length];
            System.arraycopy(oldSubset, 0, newSubset, 0, ax.length);
            return newSubset;
        }


        public int subsetCardinality(boolean[] subset) {
            int card = 0;
            for (int i = 0; i < ax.length; i++) {
                if (subset[i])
                    card++;
            }
            return card;
        }


        /**
         * Transforms a subset of this set (represented by an array of Booleans) into a set of axioms.
         *
         * @param subset an array representing the subset
         * @return a set of axioms
         */
        public Set<OWLAxiom> toSet(boolean[] subset) {
            HashSet<OWLAxiom> axs = new HashSet<OWLAxiom>();
            for (int i = 0; i < ax.length; i++) {
                if (subset[i]) {
                    axs.add(ax[i]);
                }
            }
            return axs;
        }
    }


    /**
     * Type of module
     */
    protected ModuleType moduleType;

    /**
     * Represents the associated ontology.
     */
    protected OntologyAxiomSet ontologyAxiomSet;

    protected OWLOntology ontology;

    /**
     * Represents the manager for the associated ontology.
     */
    protected OWLOntologyManager manager;


    /**
     * Creates a new module extractor for a subset of a given ontology, its manager, and a specified type of locality.
     *
     * @param man        the manager for the associated ontology
     * @param ont        the associated ontology
     * @param axs        the subset of the ontology as a set of axioms
     * @param moduleType the type of module this extractor will construct
     */
    public SyntacticLocalityModuleExtractor(OWLOntologyManager man, OWLOntology ont, Set<OWLAxiom> axs, ModuleType moduleType) {
        setModuleType(moduleType);

        manager = man;
        ontology = ont;
        ontologyAxiomSet = new OntologyAxiomSet(axs);
    }


    /**
     * Creates a new module extractor for a given ontology, its manager, and a specified type of locality.
     *
     * @param man        the manager for the associated ontology
     * @param ont        the associated ontology
     * @param moduleType the type of module this extractor will construct
     */
    public SyntacticLocalityModuleExtractor(OWLOntologyManager man, OWLOntology ont, ModuleType moduleType) {
        this(man, ont, ont.getAxioms(), moduleType);
    }


    /**
     * Changes the module type for this extractor without deleting the stored referenced entities.
     *
     * @param moduleType the new type of module
     */
    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }


    /**
     * Returns the module type for this extractor.
     *
     * @return module type for this extractor
     */
    public ModuleType getModuleType() {
        return moduleType;
    }


    /**
     * This auxiliary method extracts a module from a given sub-ontology of the associated ontology for a given signature
     * and locality type. The module will contain only logical axioms, no annotation or declaration axioms.
     * The sub-ontology and module are represented as arrays of Booleans.
     * <p/>
     * This method is (if necessary, iteratively) called by the public method extract.
     *
     * @param subOnt        an array of Booleans representing the sub-ontology
     * @param signature     the seed signature (set of entities) for the module; on return of the method, this will contain the signature of the module
     * @param localityClass the type of locality
     * @param verbose       a flag for verbose output (test purposes)
     * @return an array of Booleans representing the module
     */
    protected boolean[] extractLogicalAxioms(boolean[] subOnt, Set<OWLEntity> signature, LocalityClass localityClass, boolean verbose) {
        boolean[] mod = ontologyAxiomSet.getSubset(false);
        boolean[] q2 = ontologyAxiomSet.cloneSubset(subOnt);

        SyntacticLocalityEvaluator sle = new SyntacticLocalityEvaluator(localityClass);

        boolean change = true;
        int loopNumber = 0;
        while (change) {
            change = false;
            loopNumber++;
            if (verbose) {
                System.out.println("  Loop " + loopNumber);
            }
            for (int i = 0; i < q2.length; i = i + 1) {
                if (q2[i]) {
                    if (!sle.isLocal(ontologyAxiomSet.getAxiom(i), signature)) {
                        if (verbose){
                            System.out.println("      Non-local axiom:   " + ontologyAxiomSet.getAxiom(i));
                        }
                        mod[i] = true;
                        q2[i] = false;
                        int oldSize = signature.size();
                        signature.addAll(ontologyAxiomSet.getAxiom(i).getSignature());
                        // only triggering a change when the signature has changed doesn't improve performance
                        if (signature.size() > oldSize) {
                            change = true;
                            if (verbose) {
                                System.out.print("    New signature:   ");
                                for (OWLEntity ent : signature) {
                                    System.out.print(ent + ", ");
                                }
                                System.out.println();
                            }
                        }
                    }
                    else {
                        if (verbose){
                            System.out.println("      Local axiom:       " + ontologyAxiomSet.getAxiom(i));
                        }
                    }
                }
            }
        }
        return mod;
    }


    /**
     * This method extracts a module from a given sub-ontology of the associated ontology for a given signature and
     * locality type. The module will only contain logical axioms, no annotation or declaration axioms.
     * The sub-ontology and module are represented as sets of axioms.
     *
     * @param subOnt        a set of axioms representing the sub-ontology
     * @param signature     the seed signature (set of entities) for the module; on return of the method, this will contain the signature of the module
     * @param localityClass the type of locality
     * @param verbose       a flag for verbose output (test purposes)
     * @return a set of axioms representing the module
     */
    protected Set<OWLAxiom> extract(Set<OWLAxiom> subOnt, Set<OWLEntity> signature, LocalityClass localityClass, boolean verbose) {
        HashSet<OWLAxiom> mod = new HashSet<OWLAxiom>();
        HashSet<OWLAxiom> q2 = new HashSet<OWLAxiom>(subOnt);

        SyntacticLocalityEvaluator sle = new SyntacticLocalityEvaluator(localityClass);

        boolean change = true;
        int loopNumber = 0;
        while (change) {
            change = false;
            loopNumber++;
            if (verbose) {
                System.out.println("  Loop " + loopNumber);
            }
            HashSet<OWLAxiom> q2remove = new HashSet<OWLAxiom>();
            for (OWLAxiom ax : q2) {
                if (!sle.isLocal(ax, signature)) {
                    if (verbose){
                        System.out.println("      Non-local axiom:   " + ax);
                    }
                    mod.add(ax);
                    q2remove.add(ax);
                    int oldSize = signature.size();
                    signature.addAll(ax.getSignature());
                    // only triggering a change when the signature has changed doesn't improve performance
                    if (signature.size() > oldSize) {
                        change = true;
                        if (verbose) {
                            System.out.print("    New signature:   ");
                            for (OWLEntity ent : signature) {
                                System.out.print(ent + ", ");
                            }
                            System.out.println();
                        }
                    }
                }
                else {
                    if (verbose){
                        System.out.println("      Local axiom:       " + ax);
                    }
                }
            }
            q2.removeAll(q2remove);
        }
        return mod;
    }

    /**
     * This method enriches a module that contains only logical axioms with all necessary
     * entity declaration axioms, entity annotation axioms, and axiom annotation axioms.
     * The module and enriched module are represented as sets of axioms.
     *
     * @param module  a set of axioms representing the original module
     * @param sig     a set of entities representing the signature of the original module
     * @param verbose a flag for verbose output (test purposes)
     * @return a set of axioms representing the enriched module
     */

    protected Set<OWLAxiom> enrich(Set<OWLAxiom> module, Set<OWLEntity> sig, boolean verbose) {
        Set<OWLAxiom> enrichedModule = new HashSet<OWLAxiom>(module);

        if (verbose) {
            System.out.println("\nEnriching with declaration axioms ...");
        }

//        // Adding all entity declaration axioms
//        for (int i = 0; i < ontologyAxiomSet.size(); i++) {
//            OWLAxiom axiom = ontologyAxiomSet.getAxiom(i);
//            if (OWLDeclarationAxiom.class.isAssignableFrom(axiom.getClass())) {
//                if (sig.contains(((OWLDeclarationAxiom) axiom).getEntity())) {
//                    enrichedModule.add(axiom);
//                    if (verbose) {
//                        System.out.println("  Added declaration axiom:   " + axiom);
//                    }
//                }
//            }
//        }

        // Adding all entity declaration axioms
        // Adding all entity annotation axioms
        if (ontology != null) {
            for (OWLEntity entity : sig) {
                Set<OWLDeclarationAxiom> declarationAxioms = ontology.getDeclarationAxioms(entity);
                enrichedModule.addAll(declarationAxioms);
                if (verbose) {
                    for (OWLDeclarationAxiom declarationAxiom : declarationAxioms) {
                            System.out.println("  Added entity declaration axiom:   " + declarationAxiom);
                    }
                }
                Set<OWLAnnotationAssertionAxiom> entityAnnotationAxioms = entity.getAnnotationAssertionAxioms(ontology);
                enrichedModule.addAll(entityAnnotationAxioms);
                if (verbose) {
                    for (OWLAnnotationAssertionAxiom entityAnnotationAxiom : entityAnnotationAxioms) {
                            System.out.println("  Added entity annotation axiom:   " + entityAnnotationAxiom);
                    }
                }
            }
        }

//        boolean change = true;
//        while (change) {
//            int oldModuleSize = enrichedModule.size();
//            Iterator<OWLAxiom> iterator = enrichedModule.iterator();
//            while (iterator.hasNext()) {
//                OWLAxiom axiom = iterator.next();
//                enrichedModule.addAll(axiom.getAnnotationAssertionAxioms(ontology));
//            }
//            change = (enrichedModule.size() > oldModuleSize);
//        }
        return enrichedModule;
    }

    protected Set<OWLAxiom> extractUnnestedModule(Set<OWLEntity> sig, LocalityClass cls, boolean verbose) {
        if (verbose) {
            System.out.print("\nExtracting " + cls + " module ... ");
            for (OWLEntity ent : sig) {
                System.out.print(ent + ", ");
            }
            System.out.println();
        }
        boolean[] subOnt = ontologyAxiomSet.getSubset(true);
        Set<OWLEntity> signature = new HashSet<OWLEntity>(sig);
        boolean[] module = extractLogicalAxioms(subOnt, signature, cls, verbose);
        Set<OWLAxiom> moduleAsSet = ontologyAxiomSet.toSet(module);
        return enrich(moduleAsSet, signature, verbose);
    }


    /**
     * Extracts a module from the associated ontology for a given signature and the associated module type, and returns the
     * module as a set of axioms. The module will include annotation and declaration axioms for all entities and
     * axioms in it.
     *
     * @param sig the seed signature (set of entities) for the module
     * @return the module
     */
    public Set<OWLAxiom> extract(Set<OWLEntity> sig) {
        return extract(sig, false);
    }

    protected Set<OWLAxiom> extract(Set<OWLEntity> sig, boolean verbose) {
        switch (moduleType) {
            case TOP: {
                return extractUnnestedModule(sig, LocalityClass.TOP_TOP, verbose);
            }
            case BOT: {
                return extractUnnestedModule(sig, LocalityClass.BOTTOM_BOTTOM, verbose);
            }
            case STAR: {
                boolean[] subOnt = ontologyAxiomSet.getSubset(true);
                boolean nextStepNecessary = true;
                boolean inFirstStep = true;
                LocalityClass localityClass = LocalityClass.BOTTOM_BOTTOM;
                Set<OWLEntity> seedSig = new HashSet<OWLEntity>(sig);
                while (nextStepNecessary) {
                    if (verbose) {
                        System.out.print("\nExtracting " + localityClass + " module ... ");
                        for (OWLEntity ent : sig) {
                            System.out.print(ent + ", ");
                        }
                        System.out.println();
                    }
                    int previousModuleSize = ontologyAxiomSet.subsetCardinality(subOnt);
                    seedSig = new HashSet<OWLEntity>(sig);
                    subOnt = extractLogicalAxioms(subOnt, seedSig, localityClass, verbose);
                    if ((ontologyAxiomSet.subsetCardinality(subOnt) == previousModuleSize) && !inFirstStep) {
                        nextStepNecessary = false;
                    }
                    inFirstStep = false;

                    if (localityClass == LocalityClass.BOTTOM_BOTTOM) localityClass = LocalityClass.TOP_TOP;
                    else                                              localityClass = LocalityClass.BOTTOM_BOTTOM;
                }
                Set<OWLAxiom> moduleAsSet = ontologyAxiomSet.toSet(subOnt);
                return enrich(moduleAsSet, seedSig, verbose);
            }
            default:
                throw new RuntimeException("Unsupported module type: " + moduleType);
        }
    }


    /**
     * Extracts a module from the associated ontology for a given signature and the associated module type, and returns the
     * module as an ontology.
     *
     * @param signature the seed signature (set of entities) for the module
     * @param iri       the IRI for the module
     * @return the module, having the specified IRI
     * @throws OWLOntologyChangeException   if adding axioms to the module fails
     * @throws OWLOntologyCreationException if the module cannot be created
     */
    public OWLOntology extractAsOntology(Set<OWLEntity> signature, IRI iri) throws OWLOntologyCreationException {
        return extractAsOntology(signature, iri, false);
    }

    protected OWLOntology extractAsOntology(Set<OWLEntity> signature, IRI iri, boolean verbose) throws OWLOntologyCreationException {
        Set<OWLAxiom> axs = extract(signature, verbose);
        OWLOntology newOnt = manager.createOntology(iri);
        LinkedList<AddAxiom> addaxs = new LinkedList<AddAxiom>();
        for (OWLAxiom ax : axs) {
            addaxs.add(new AddAxiom(newOnt, ax));
        }
        manager.applyChanges(addaxs);
        return newOnt;
    }

}
