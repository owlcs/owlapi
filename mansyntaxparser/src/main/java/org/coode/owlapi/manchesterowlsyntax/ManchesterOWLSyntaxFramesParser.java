package org.coode.owlapi.manchesterowlsyntax;

import java.util.Set;

import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLExpressionParser;
import org.semanticweb.owlapi.expression.OWLOntologyChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 05-Feb-2009
 */
public class ManchesterOWLSyntaxFramesParser implements OWLExpressionParser<Set<OntologyAxiomPair>> {

    private OWLDataFactory dataFactory;

    private OWLEntityChecker checker;

    private OWLOntologyChecker ontologyChecker;

    private OWLOntology defaultOntology;

    public ManchesterOWLSyntaxFramesParser(OWLDataFactory dataFactory, OWLEntityChecker checker) {
        this.dataFactory = dataFactory;
        this.checker = checker;
    }


    public void setOWLEntityChecker(OWLEntityChecker entityChecker) {
        this.checker = entityChecker;
    }

    public void setOWLOntologyChecker(OWLOntologyChecker ontologyChecker) {
        this.ontologyChecker = ontologyChecker;
    }

    public void setDefaultOntology(OWLOntology ontology) {
        this.defaultOntology = ontology;
    }

    public Set<OntologyAxiomPair> parse(String expression) throws ParserException {
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(dataFactory, expression);
        parser.setOWLEntityChecker(checker);
        parser.setDefaultOntology(defaultOntology);
        parser.setOWLOntologyChecker(ontologyChecker);
        return parser.parseFrames();
    }
}
