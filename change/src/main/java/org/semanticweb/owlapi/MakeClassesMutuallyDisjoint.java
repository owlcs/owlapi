package org.semanticweb.owlapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Jul-2007<br><br>
 * <p/>
 * Given a set of class expressions, this composite change will make them mutually disjoint.
 * The composite change offers the option of using one disjoint classes axiom to do this
 * or using multiple disjoint classes axioms to make them pairwise disjoint (for backwards
 * compatibility with OWL 1.0).
 */
public class MakeClassesMutuallyDisjoint extends AbstractCompositeOntologyChange {

    private Set<? extends OWLClassExpression> classExpressions;

    private boolean usePairwiseDisjointAxioms;

    private OWLOntology targetOntology;

    private List<OWLOntologyChange> changes;


    /**
     * Creates a composite change which makes a set of classes mutually disjoint
     *
     * @param dataFactory               The data factory which should be used for creating the axioms
     * @param classExpressions              The class expressions which should be made mutually disjoint.
     * @param usePairwiseDisjointAxioms <code>true</code> if multiple disjoint classes
     *                                  axioms should be used to make the class expressions pairwise disjoint (for backwards
     *                                  compatibility with OWL 1.0), or <code>false</code> if one disjoint classes axiom
     *                                  should be used (preferred OWL 1.1 method).
     * @param targetOntology            The target ontology which the changes will be applied to.
     */
    public MakeClassesMutuallyDisjoint(OWLDataFactory dataFactory, Set<? extends OWLClassExpression> classExpressions,
                                       boolean usePairwiseDisjointAxioms, OWLOntology targetOntology) {
        super(dataFactory);
        this.classExpressions = classExpressions;
        this.usePairwiseDisjointAxioms = usePairwiseDisjointAxioms;
        this.targetOntology = targetOntology;
        generateChanges();
    }


    private void generateChanges() {
        changes = new ArrayList<OWLOntologyChange>();
        if (usePairwiseDisjointAxioms) {
            List<OWLClassExpression> descList = new ArrayList<OWLClassExpression>(classExpressions);
            for (int i = 0; i < descList.size(); i++) {
                for (int j = i + 1; j < descList.size(); j++) {
                    changes.add(new AddAxiom(targetOntology,
                            getDataFactory().getOWLDisjointClassesAxiom(CollectionFactory.createSet(
                                    descList.get(i),
                                    descList.get(j)))));
                }
            }
        } else {
            changes.add(new AddAxiom(targetOntology, getDataFactory().getOWLDisjointClassesAxiom(classExpressions)));
        }
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }
}
