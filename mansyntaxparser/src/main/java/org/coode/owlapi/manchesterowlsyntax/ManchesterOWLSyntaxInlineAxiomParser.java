package org.coode.owlapi.manchesterowlsyntax;

import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLExpressionParser;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 13-May-2009
 */
public class ManchesterOWLSyntaxInlineAxiomParser implements OWLExpressionParser<OWLAxiom> {

    private OWLDataFactory dataFactory;

    private OWLEntityChecker checker;


    public ManchesterOWLSyntaxInlineAxiomParser(OWLDataFactory dataFactory,
                                                OWLEntityChecker checker) {
        this.dataFactory = dataFactory;
        this.checker = checker;
    }


    public void setOWLEntityChecker(OWLEntityChecker entityChecker) {
        this.checker = entityChecker;
    }


    public OWLAxiom parse(String expression) throws ParserException {
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(dataFactory, expression);
        parser.setOWLEntityChecker(checker);
        return parser.parseAxiom();
    }
}
