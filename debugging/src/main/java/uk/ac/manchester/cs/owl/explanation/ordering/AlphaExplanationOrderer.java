package uk.ac.manchester.cs.owl.explanation.ordering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-Jan-2008<br><br>
 * Orders an explanation in a flat list, sorting axioms
 * alphabetically.
 */
public final class AlphaExplanationOrderer implements ExplanationOrderer {

    protected OWLObjectRenderer renderer;


    public AlphaExplanationOrderer(OWLObjectRenderer renderer) {
        this.renderer = renderer;
    }

    public ExplanationTree getOrderedExplanation(OWLAxiom entailment, Set<OWLAxiom> axioms) {
        EntailedAxiomTree root = new EntailedAxiomTree(entailment);
        List<OWLAxiom> sortedAxioms = new ArrayList<OWLAxiom>();
        // XXX sorting an empty list and not using the input parameter = bug?
        Collections.sort(sortedAxioms, new Comparator<OWLAxiom>() {

            public int compare(OWLAxiom o1, OWLAxiom o2) {
                return renderer.render(o1).compareTo(renderer.render(o2));
            }
        });
        for (OWLAxiom ax : sortedAxioms) {
            root.addChild(new ExplanationTree(ax));
        }
        return root;
    }
}
