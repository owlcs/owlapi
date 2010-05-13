package org.coode.owlapi.examples.dlquery;

import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.ShortFormProvider;

import java.util.Collections;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-May-2010
 * <br>
 * This example shows how to perform a "dlquery".  The DLQuery view/tab in Protege 4 works like this.
 */
public class DLQueryEngine {

    private OWLReasoner reasoner;

    private DLQueryParser parser;

    /**
     * Constructs a DLQueryEngine.  This will answer "DL queries" using the specified reasoner.  A short form provider
     * specifies how entities are rendered.
     * @param reasoner The reasoner to be used for answering the queries.
     * @param shortFormProvider A short form provider.
     */
    public DLQueryEngine(OWLReasoner reasoner, ShortFormProvider shortFormProvider) {
        this.reasoner = reasoner;
        OWLOntology rootOntology = reasoner.getRootOntology();
        parser = new DLQueryParser(rootOntology, shortFormProvider);
    }

    /**
     * Gets the superclasses of a class expression parsed from a string.
     * @param classExpressionString The string from which the class expression will be parsed.
     * @param direct Specifies whether direct superclasses should be returned or not.
     * @return The superclasses of the specified class expression
     * @throws ParserException If there was a problem parsing the class expression.
     */
    public Set<OWLClass> getSuperClasses(String classExpressionString, boolean direct) throws ParserException {
        if(classExpressionString.trim().length() == 0) {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser.parseClassExpression(classExpressionString);
        NodeSet<OWLClass> superClasses = reasoner.getSuperClasses(classExpression, direct);
        return superClasses.getFlattened();
    }
    
    /**
     * Gets the equivalent classes of a class expression parsed from a string.
     * @param classExpressionString The string from which the class expression will be parsed.
     * @return The equivalent classes of the specified class expression
     * @throws ParserException If there was a problem parsing the class expression.
     */
    public Set<OWLClass> getEquivalentClasses(String classExpressionString) throws ParserException {
        if(classExpressionString.trim().length() == 0) {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser.parseClassExpression(classExpressionString);
        Node<OWLClass> equivalentClasses = reasoner.getEquivalentClasses(classExpression);
        Set<OWLClass> result;
        if(classExpression.isAnonymous()) {
            result = equivalentClasses.getEntities();
        }
        else {
            result = equivalentClasses.getEntitiesMinus(classExpression.asOWLClass());
        }
        return result;
    }

    /**
     * Gets the subclasses of a class expression parsed from a string.
     * @param classExpressionString The string from which the class expression will be parsed.
     * @param direct Specifies whether direct subclasses should be returned or not.
     * @return The subclasses of the specified class expression
     * @throws ParserException If there was a problem parsing the class expression.
     */
    public Set<OWLClass> getSubClasses(String classExpressionString, boolean direct) throws ParserException {
        if(classExpressionString.trim().length() == 0) {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser.parseClassExpression(classExpressionString);
        NodeSet<OWLClass> subClasses = reasoner.getSubClasses(classExpression, direct);
        return subClasses.getFlattened();
    }

    /**
     * Gets the instances of a class expression parsed from a string.
     * @param classExpressionString The string from which the class expression will be parsed.
     * @param direct Specifies whether direct instances should be returned or not.
     * @return The instances of the specified class expression
     * @throws ParserException If there was a problem parsing the class expression.
     */
    public Set<OWLNamedIndividual> getInstances(String classExpressionString, boolean direct) throws ParserException {
        if(classExpressionString.trim().length() == 0) {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser.parseClassExpression(classExpressionString);
        NodeSet<OWLNamedIndividual> individuals = reasoner.getInstances(classExpression, direct);
        return individuals.getFlattened();
    }

}
