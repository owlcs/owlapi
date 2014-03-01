package org.coode.owlapi.manchesterowlsyntax;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLOntologyChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * Interface for a parser able to parse Manchester OWL Syntax. This covers
 * Protege use of the parser.
 * 
 * @author ignazio
 */
public interface ManchesterOWLSyntaxParser {

    /**
     * Parsing "Inline" Axioms.
     * 
     * @return axiom
     * @throws ParserException
     *         parsing error
     */
    OWLAxiom parseAxiom() throws ParserException;

    /**
     * Parses an OWL class expression that is represented in Manchester OWL
     * Syntax.
     * 
     * @return The parsed class expression
     * @throws ParserException
     *         If a class expression could not be parsed.
     */
    OWLClassExpression parseClassExpression() throws ParserException;

    /**
     * @return class frames
     * @throws ParserException
     *         parsing error
     */
    Set<OntologyAxiomPair> parseClassFrameEOF() throws ParserException;

    /**
     * @param datatype
     *        datatype to use, if one exists in the context. If null, the
     *        datatype will be decided by the literal itself.
     * @return parsed literal
     */
    OWLLiteral parseLiteral(OWLDatatype datatype);

    /**
     * @return literal parsed
     * @deprecated use parseLiteral(null) to get the same result
     */
    @Deprecated
    OWLLiteral parseConstant();

    /**
     * @param owlEntityChecker
     *        owlEntityChecker
     */
    void setOWLEntityChecker(OWLEntityChecker owlEntityChecker);

    /**
     * @param owlOntologyChecker
     *        owlOntologyChecker
     */
    void setOWLOntologyChecker(OWLOntologyChecker owlOntologyChecker);

    /** @return object property chain */
    List<OWLObjectPropertyExpression> parseObjectPropertyChain();

    /**
     * @param ont
     *        ont
     * @return format
     * @throws ParserException
     *         parsing error
     * @throws UnloadableImportException
     *         import error
     */
    ManchesterOWLSyntaxOntologyFormat parseOntology(OWLOntology ont)
            throws ParserException, UnloadableImportException;

    /** @return list of class expressions */
    Set<OWLClassExpression> parseClassExpressionList();

    /**
     * @return list of object properties
     * @throws ParserException
     *         if a parser exception is raised
     */
    Set<OWLObjectPropertyExpression> parseObjectPropertyList()
            throws ParserException;

    /**
     * @param ignored
     *        this parameter is ignored
     * @return same result as parseClassExpressionList()
     * @deprecated use parseClassExpressionList()
     */
    @Deprecated
    Set<OWLClassExpression> parseClassExpressionList(boolean ignored);

    /**
     * @return class axiom
     * @throws ParserException
     *         if a parser exception is raised
     * @deprecated same as parseAxiom except for a cast in the return type
     */
    @Deprecated
    OWLClassAxiom parseClassAxiom() throws ParserException;

    /** @return data range */
    OWLDataRange parseDataRange();

    /** @return property list (object or data) */
    Set<OWLPropertyExpression<?, ?>> parsePropertyList();

    /** @return list of rule frames */
    List<OntologyAxiomPair> parseRuleFrame();

    /**
     * @return IRI for a SWRL variable
     * @throws ParserException
     *         if a parser exception is raised
     */
    IRI parseVariable() throws ParserException;
}
