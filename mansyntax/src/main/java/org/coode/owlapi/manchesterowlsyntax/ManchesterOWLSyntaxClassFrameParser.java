package org.coode.owlapi.manchesterowlsyntax;


import java.util.Set;

import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLExpressionParser;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLDataFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Sep-2007<br><br>
 *
 * An expression parser that parses a Manchester OWL Syntax Class Frame to produce a
 * set of axioms that represent the class frame.
 */
public class ManchesterOWLSyntaxClassFrameParser implements OWLExpressionParser<Set<OntologyAxiomPair>> {

    private OWLDataFactory dataFactory;

    private OWLEntityChecker checker;

    public ManchesterOWLSyntaxClassFrameParser(OWLDataFactory dataFactory, OWLEntityChecker checker) {
        this.dataFactory = dataFactory;
        this.checker = checker;
    }


    public void setOWLEntityChecker(OWLEntityChecker entityChecker) {
        this.checker = entityChecker;
    }


    public Set<OntologyAxiomPair> parse(String expression) throws ParserException {
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(dataFactory, expression);
        parser.setOWLEntityChecker(checker);
        return parser.parseClassFrameEOF();
    }
}
