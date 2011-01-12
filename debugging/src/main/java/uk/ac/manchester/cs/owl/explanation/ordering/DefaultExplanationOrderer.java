package uk.ac.manchester.cs.owl.explanation.ordering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

import uk.ac.manchester.cs.bhig.util.Tree;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Jan-2008<br><br>
 * <p/>
 * Provides ordering and indenting of explanations based on various
 * ordering heuristics.
 */
public class DefaultExplanationOrderer implements ExplanationOrderer {

    private Set<OWLAxiom> currentExplanation;

    private Map<OWLEntity, Set<OWLAxiom>> lhs2AxiomMap;

    private Map<OWLAxiom, Set<OWLEntity>> entitiesByAxiomRHS;

    private SeedExtractor seedExtractor;

    private OWLOntologyManager man;

    private OWLOntology ont;

    private Map<OWLObject, Set<OWLAxiom>> mappedAxioms;

    private Set<OWLAxiom> consumedAxioms;

    private Set<AxiomType<?>> passTypes;

    private OWLEntity currentSource;

    private OWLEntity currentTarget;


    public DefaultExplanationOrderer() {
        currentExplanation = Collections.emptySet();
        lhs2AxiomMap = new HashMap<OWLEntity, Set<OWLAxiom>>();
        entitiesByAxiomRHS = new HashMap<OWLAxiom, Set<OWLEntity>>();
        seedExtractor = new SeedExtractor();
        man = OWLManager.createOWLOntologyManager();
        man.addIRIMapper(new OWLOntologyIRIMapper() {
            public IRI getDocumentIRI(IRI ontologyIRI) {
                return ontologyIRI;
            }
        });
        mappedAxioms = new HashMap<OWLObject, Set<OWLAxiom>>();

        passTypes = new HashSet<AxiomType<?>>();
        // I'm not sure what to do with disjoint classes yet.  At the
        // moment, we just shove them at the end at the top level.
        passTypes.add(AxiomType.DISJOINT_CLASSES);
        consumedAxioms = new HashSet<OWLAxiom>();
    }


    private void reset() {
        lhs2AxiomMap.clear();
        entitiesByAxiomRHS.clear();
        consumedAxioms.clear();
    }


    public ExplanationTree getOrderedExplanation(OWLAxiom entailment, Set<OWLAxiom> axioms) {

        currentExplanation = new HashSet<OWLAxiom>(axioms);
        buildIndices();


        ExplanationTree root = new EntailedAxiomTree(entailment);
        currentSource = seedExtractor.getSource(entailment);
        insertChildren(currentSource, root);
        currentTarget = seedExtractor.getTarget(entailment);


        Set<OWLAxiom> axs = root.getUserObjectClosure();

        final Set<OWLAxiom> targetAxioms = new HashSet<OWLAxiom>();
        if (currentTarget != null) {
            if (currentTarget.isOWLClass()) {
                targetAxioms.addAll(ont.getAxioms(currentTarget.asOWLClass()));
            }

            if (currentTarget.isOWLObjectProperty()) {
                targetAxioms.addAll(ont.getAxioms(currentTarget.asOWLObjectProperty()));
            }

            if (currentTarget.isOWLDataProperty()) {
                targetAxioms.addAll(ont.getAxioms(currentTarget.asOWLDataProperty()));
            }

            if (currentTarget.isOWLNamedIndividual()) {
                targetAxioms.addAll(ont.getAxioms(currentTarget.asOWLNamedIndividual()));
            }
        }

        List<OWLAxiom> rootAxioms = new ArrayList<OWLAxiom>();
        for (OWLAxiom ax : axioms) {
            if (!axs.contains(ax)) {
                rootAxioms.add(ax);
            }
        }

        Collections.sort(rootAxioms, new Comparator<OWLAxiom>() {

            public int compare(OWLAxiom o1, OWLAxiom o2) {
                if (targetAxioms.contains(o1)) {
                    return 1;
                }
                if (targetAxioms.contains(o2)) {
                    return -1;
                }
                return 0;
            }
        });


        for (OWLAxiom ax : rootAxioms) {
            root.addChild(new ExplanationTree(ax));
        }


        return root;
    }


    private List<OWLEntity> getRHSEntitiesSorted(OWLAxiom ax) {
        Collection<OWLEntity> entities = getRHSEntities(ax);
        List<OWLEntity> sortedEntities = new ArrayList<OWLEntity>(entities);
        Collections.sort(sortedEntities, new Comparator<OWLObject>() {

            public int compare(OWLObject o1, OWLObject o2) {
                if (o1 instanceof OWLProperty) {
                    return -1;
                }
                else {
                    return 1;
                }
            }
        });
        return sortedEntities;
    }


    private void insertChildren(OWLEntity entity, ExplanationTree tree) {
        Set<OWLAxiom> currentPath = new HashSet<OWLAxiom>(tree.getUserObjectPathToRoot());
        Set<? extends OWLAxiom> axioms = Collections.emptySet();
        if (entity == null) {

        }
        else if (entity.isOWLClass()) {
            axioms = ont.getAxioms(entity.asOWLClass());
        }
        else if (entity.isOWLObjectProperty()) {
            axioms = ont.getAxioms(entity.asOWLObjectProperty());
        }
        else if (entity.isOWLDataProperty()) {
            axioms = ont.getAxioms(entity.asOWLDataProperty());
        }
        else if (entity.isOWLNamedIndividual()) {
            axioms = ont.getAxioms(entity.asOWLNamedIndividual());
        }
        for (OWLAxiom ax : axioms) {
            if (passTypes.contains(ax.getAxiomType())) {
                continue;
            }
            Set<OWLAxiom> mapped = getIndexedSet(entity, mappedAxioms, true);
            if (consumedAxioms.contains(ax) || mapped.contains(ax) || currentPath.contains(ax)) {
                continue;
            }
            mapped.add(ax);
            consumedAxioms.add(ax);
            ExplanationTree child = new ExplanationTree(ax);
            tree.addChild(child);
            for (OWLEntity ent : getRHSEntitiesSorted(ax)) {
                insertChildren(ent, child);
            }
        }
        sortChildrenAxioms(tree);
    }


    private void sortChildrenAxioms(ExplanationTree tree) {

        Comparator<Tree<OWLAxiom>> comparator = new OWLAxiomTreeComparator();
        tree.sortChildren(comparator);
    }


    private void buildIndices() {
        reset();
        AxiomMapBuilder builder = new AxiomMapBuilder();
        for (OWLAxiom ax : currentExplanation) {
            ax.accept(builder);
        }
        try {

            if (ont != null) {
                man.removeOntology(ont);
            }
            ont = man.createOntology(IRI.create("http://www.semanticweb.org/ontology" + System.currentTimeMillis()));

            List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
            for (OWLAxiom ax : currentExplanation) {
                changes.add(new AddAxiom(ont, ax));
                ax.accept(builder);
            }
            man.applyChanges(changes);
        }
        catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }


    /**
     * A utility method that obtains a set of axioms that are indexed by some object
     * @param obj The object that indexed the axioms
     * @param map The map that provides the index structure
     * @param addIfEmpty A flag that indicates whether an empty set of axiom should be
     * added to the index if there is not value present for the indexing object.
     * @return A set of axioms (may be empty)
     */
    private static <K, E> Set<E> getIndexedSet(K obj, Map<K, Set<E>> map, boolean addIfEmpty) {
        Set<E> values = map.get(obj);
        if (values == null) {
            values = new HashSet<E>();
            if (addIfEmpty) {
                map.put(obj, values);
            }
        }
        return values;
    }


    /**
     * Gets axioms that have a LHS corresponding to the specified entity.
     * @param lhs The entity that occurs on the left hand side of the axiom.
     * @return A set of axioms that have the specified entity as their left hand side.
     */
    protected Set<OWLAxiom> getAxiomsForLHS(OWLEntity lhs) {
        return getIndexedSet(lhs, lhs2AxiomMap, true);
    }


    private Collection<OWLEntity> getRHSEntities(OWLAxiom axiom) {
        return getIndexedSet(axiom, entitiesByAxiomRHS, true);
    }


    protected void indexAxiomsByRHSEntities(OWLObject rhs, OWLAxiom axiom) {
        getIndexedSet(axiom, entitiesByAxiomRHS, true).addAll(rhs.getSignature());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * tree comparator
     * XXX this class is stateless, a singleton might be used to access it
     */
    private static final class OWLAxiomTreeComparator implements Comparator<Tree<OWLAxiom>> {
        public int compare(Tree<OWLAxiom> o1, Tree<OWLAxiom> o2) {


            OWLAxiom ax1 = o1.getUserObject();
            OWLAxiom ax2 = o2.getUserObject();

            // Equivalent classes axioms always come last
            if (ax1 instanceof OWLEquivalentClassesAxiom) {
                return 1;
            }
            if (ax2 instanceof OWLEquivalentClassesAxiom) {
                return -1;
            }
            if (ax1 instanceof OWLPropertyAxiom) {
                return -1;
            }
            int childCount1 = o1.getChildCount();
            childCount1 = childCount1 > 0 ? 0 : 1;
            int childCount2 = o2.getChildCount();
            childCount2 = childCount2 > 0 ? 0 : 1;
            int diff = childCount1 - childCount2;
            if (diff != 0) {
                return diff;
            }
            if (ax1 instanceof OWLSubClassOfAxiom && ax2 instanceof OWLSubClassOfAxiom) {
                OWLSubClassOfAxiom sc1 = (OWLSubClassOfAxiom) ax1;
                OWLSubClassOfAxiom sc2 = (OWLSubClassOfAxiom) ax2;
                return sc1.getSuperClass().compareTo(sc2.getSuperClass());
            }

            return 1;
        }
    }
    @SuppressWarnings("unused")
    private static class SeedExtractor implements OWLAxiomVisitor {

        private OWLEntity source;

        private OWLEntity target;


        public SeedExtractor() {
        }


        public OWLEntity getSource(OWLAxiom axiom) {
            axiom.accept(this);
            return source;
        }


        public OWLEntity getTarget(OWLAxiom axiom) {
            axiom.accept(this);
            return target;
        }


        public void visit(OWLSubClassOfAxiom axiom) {
            if (!axiom.getSubClass().isAnonymous()) {
                source = axiom.getSubClass().asOWLClass();
            }
            if (!axiom.getSuperClass().isOWLNothing()) {
                OWLClassExpression classExpression = axiom.getSuperClass();
                if (!classExpression.isAnonymous()) {
                    target = classExpression.asOWLClass();
                }
            }
        }


        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {

        }


        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            for (OWLClassExpression ce : axiom.getClassExpressions()) {
                if (!ce.isAnonymous()) {
                    if (source == null) {
                        source = ce.asOWLClass();
                    }
                    else if (target == null) {
                        target = ce.asOWLClass();
                    }
                    else {
                        break;
                    }
                }
            }
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
        }


        public void visit(OWLImportsDeclaration axiom) {
        }


        public void visit(OWLObjectPropertyDomainAxiom axiom) {
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            if (!axiom.getSubProperty().isAnonymous()) {
                source = axiom.getSubProperty().asOWLObjectProperty();
            }
            if (!axiom.getSuperProperty().isAnonymous()) {
                target = axiom.getSuperProperty().asOWLObjectProperty();
            }
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
        }


        public void visit(OWLDeclarationAxiom axiom) {
        }


        public void visit(OWLAnnotationAssertionAxiom axiom) {
        }

        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            if (!axiom.getClassExpression().isAnonymous()) {
                source = axiom.getIndividual().asOWLNamedIndividual();
                target = axiom.getClassExpression().asOWLClass();
            }
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            for (OWLClass cls : axiom.getNamedClasses()) {
                if (source == null) {
                    source = cls;
                }
                else if (target == null) {
                    target = cls;
                }
                else {
                    break;
                }
            }
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
        }


        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        }


        public void visit(OWLSameIndividualAxiom axiom) {
        }


        public void visit(OWLSubPropertyChainOfAxiom axiom) {
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        }


        public void visit(SWRLRule rule) {
        }

        public void visit(OWLHasKeyAxiom axiom) {
        }

        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        }

        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        }

        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        }


        public void visit(OWLDatatypeDefinitionAxiom axiom) {
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * A visitor that indexes axioms by their left and right hand sides.
     */
    @SuppressWarnings("unused")
    private class AxiomMapBuilder implements OWLAxiomVisitor {

        public void visit(OWLSubClassOfAxiom axiom) {
            if (!axiom.getSubClass().isAnonymous()) {
                getAxiomsForLHS(axiom.getSubClass().asOWLClass()).add(axiom);
                indexAxiomsByRHSEntities(axiom.getSuperClass(), axiom);
            }
        }


        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {

        }


        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(axiom);
            }
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(axiom);
            }
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isAnonymous()) {
                    getAxiomsForLHS(desc.asOWLClass()).add(axiom);
                }
                indexAxiomsByRHSEntities(desc, axiom);
            }
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
            getAxiomsForLHS(axiom.getProperty().asOWLDataProperty()).add(axiom);
            indexAxiomsByRHSEntities(axiom.getDomain(), axiom);
        }


        public void visit(OWLImportsDeclaration axiom) {
        }


        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(axiom);
            }
            indexAxiomsByRHSEntities(axiom.getDomain(), axiom);
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                if (!prop.isAnonymous()) {
                    getAxiomsForLHS(prop.asOWLObjectProperty()).add(axiom);
                }
                indexAxiomsByRHSEntities(prop, axiom);
            }
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
            for (OWLIndividual ind : axiom.getIndividuals()) {
                if (!ind.isAnonymous()) {
                    getAxiomsForLHS(ind.asOWLNamedIndividual()).add(axiom);
                    indexAxiomsByRHSEntities(ind, axiom);
                }
            }
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                getAxiomsForLHS(prop.asOWLDataProperty()).add(axiom);
                indexAxiomsByRHSEntities(prop, axiom);
            }
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                if (!prop.isAnonymous()) {
                    getAxiomsForLHS(prop.asOWLObjectProperty()).add(axiom);
                }
                indexAxiomsByRHSEntities(prop, axiom);
            }
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(axiom);
            }
            indexAxiomsByRHSEntities(axiom.getRange(), axiom);
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {

        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(axiom);
            }
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            if (!axiom.getSubProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getSubProperty().asOWLObjectProperty()).add(axiom);
            }
            indexAxiomsByRHSEntities(axiom.getSuperProperty(), axiom);
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
            getAxiomsForLHS(axiom.getOWLClass()).add(axiom);
        }


        public void visit(OWLDeclarationAxiom axiom) {
        }


        public void visit(OWLAnnotationAssertionAxiom axiom) {
        }

        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(axiom);
            }
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLDataProperty()).add(axiom);
            }
            indexAxiomsByRHSEntities(axiom.getRange(), axiom);
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLDataProperty()).add(axiom);
            }
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                getAxiomsForLHS(prop.asOWLDataProperty()).add(axiom);
                indexAxiomsByRHSEntities(prop, axiom);
            }
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            if (!axiom.getIndividual().isAnonymous()) {
                getAxiomsForLHS(axiom.getIndividual().asOWLNamedIndividual()).add(axiom);
                indexAxiomsByRHSEntities(axiom.getClassExpression(), axiom);
            }
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isAnonymous()) {
                    getAxiomsForLHS(desc.asOWLClass()).add(axiom);
                }
                indexAxiomsByRHSEntities(desc, axiom);
            }
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            indexAxiomsByRHSEntities(axiom.getSubject(), axiom);
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(axiom);
            }
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(axiom);
            }
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            getAxiomsForLHS(axiom.getSubProperty().asOWLDataProperty()).add(axiom);
            indexAxiomsByRHSEntities(axiom.getSuperProperty(), axiom);
        }


        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(axiom);
            }
        }


        public void visit(OWLSameIndividualAxiom axiom) {
            for (OWLIndividual ind : axiom.getIndividuals()) {
                if (!ind.isAnonymous()) {
                    getAxiomsForLHS(ind.asOWLNamedIndividual()).add(axiom);
                    indexAxiomsByRHSEntities(ind, axiom);
                }
            }
        }


        public void visit(OWLSubPropertyChainOfAxiom axiom) {

        }


        public void visit(OWLDatatypeDefinitionAxiom axiom) {
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            if (!axiom.getFirstProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getFirstProperty().asOWLObjectProperty()).add(axiom);
            }
            indexAxiomsByRHSEntities(axiom.getFirstProperty(), axiom);
            indexAxiomsByRHSEntities(axiom.getSecondProperty(), axiom);
        }


        public void visit(SWRLRule rule) {
        }

        public void visit(OWLHasKeyAxiom axiom) {
            if (!axiom.getClassExpression().isAnonymous()) {
                indexAxiomsByRHSEntities(axiom.getClassExpression().asOWLClass(), axiom);
            }
        }

        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        }

        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        }

        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        }
    }
}
