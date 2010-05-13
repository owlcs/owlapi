package org.coode.owlapi.examples.dlquery;

import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.ShortFormProvider;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-May-2010
 */
public class DLQueryPrinter {

    private OWLReasoner reasoner;

    private DLQueryEngine dlQueryEngine;

    private ShortFormProvider shortFormProvider;

    public DLQueryPrinter(OWLReasoner reasoner, ShortFormProvider shortFormProvider) {
        this.reasoner = reasoner;
        this.shortFormProvider = shortFormProvider;
        dlQueryEngine = new DLQueryEngine(reasoner, shortFormProvider);
    }

    public void askQuery(String classExpression) {
        if(classExpression.isEmpty()) {
            System.out.println("No class expression specified");
        }
        else {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("\n--------------------------------------------------------------------------------\n");
                sb.append("QUERY:   ");
                sb.append(classExpression);
                sb.append("\n");
                sb.append("--------------------------------------------------------------------------------\n\n");

                // Ask for the subclasses, superclasses etc. of the specified class expression.  Print out the results.
                Set<OWLClass> superClasses = dlQueryEngine.getSuperClasses(classExpression, true);
                printEntities("SuperClasses", superClasses, sb);
                Set<OWLClass> equivalentClasses = dlQueryEngine.getEquivalentClasses(classExpression);
                printEntities("EquivalentClasses", equivalentClasses, sb);
                Set<OWLClass> subClasses = dlQueryEngine.getSubClasses(classExpression, true);
                printEntities("SubClasses", subClasses, sb);
                Set<OWLNamedIndividual> individuals = dlQueryEngine.getInstances(classExpression, true);
                printEntities("Instances", individuals, sb);

                System.out.println(sb.toString());
            }
            catch (ParserException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private void printEntities(String name, Set<? extends OWLEntity> entities, StringBuilder sb) {
        sb.append(name);
        int length = 50 - name.length();
        for(int i = 0; i < length; i++) {
            sb.append(".");
        }
        sb.append("\n\n");
        if (!entities.isEmpty()) {
            for(OWLEntity entity : entities) {
                sb.append("\t");
                sb.append(shortFormProvider.getShortForm(entity));
                sb.append("\n");
            }
        }
        else {
            sb.append("\t[NONE]\n");
        }
        sb.append("\n");
    }


}
