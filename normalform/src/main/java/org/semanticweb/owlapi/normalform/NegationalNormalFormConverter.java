package org.semanticweb.owlapi.normalform;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.util.NNF;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Sep-2007<br><br>
 */
public class NegationalNormalFormConverter implements NormalFormRewriter {

    private NNF nnf;

    private OWLObjectComplementOfExtractor extractor;


    public NegationalNormalFormConverter(OWLDataFactory dataFactory) {
        nnf = new NNF(dataFactory);
        extractor = new OWLObjectComplementOfExtractor();
    }


    public boolean isInNormalForm(OWLClassExpression classExpression) {
        // The classExpression is in negational normal form if negations
        // only appear in front of named concepts
        extractor.getComplementedClassExpressions(classExpression);
        for (OWLClassExpression desc : extractor.getComplementedClassExpressions(classExpression)) {
            if (desc.isAnonymous()) {
                return false;
            }
        }
        return true;
    }


    public OWLClassExpression convertToNormalForm(OWLClassExpression classExpression) {
        nnf.reset();
        return classExpression.accept(nnf);
    }


}
