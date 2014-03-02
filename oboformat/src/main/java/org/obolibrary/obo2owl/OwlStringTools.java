package org.obolibrary.obo2owl;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Set;

import org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxOWLParser;
import org.semanticweb.owlapi.functional.renderer.OWLFunctionalSyntaxRenderer;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * Tools to read and write a set of owl axioms to/from a string. Used to
 * preserve untranslatable axioms in an owl2obo conversion.
 */
public class OwlStringTools {

    /**
     * Exception indicating an un-recoverable error during the handling of axiom
     * strings.
     */
    public static class OwlStringException extends Exception {

        // generated
        private static final long serialVersionUID = 40000L;

        /**
         * @param cause
         *        cause
         */
        protected OwlStringException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Create a string for the given set of axioms. Return null for empty sets
     * or if the set is null.
     * 
     * @param axioms
     *        axioms
     * @param translationManager
     *        translationManager
     * @return string or null
     * @throws OwlStringException
     *         OwlStringException
     * @see #translate(String, OWLOntologyManager)
     */
    public static String translate(Set<OWLAxiom> axioms,
            OWLOntologyManager translationManager) throws OwlStringException {
        if (axioms == null || axioms.isEmpty()) {
            return null;
        }
        try {
            OWLOntology ontology = translationManager.createOntology();
            translationManager.addAxioms(ontology, axioms);
            OWLFunctionalSyntaxRenderer r = new OWLFunctionalSyntaxRenderer();
            Writer writer = new StringWriter();
            r.render(ontology, writer);
            return writer.toString();
        } catch (OWLRendererException e) {
            throw new OwlStringException(e);
        } catch (OWLOntologyCreationException e) {
            throw new OwlStringException(e);
        }
    }

    /**
     * Parse the axioms from the given axiom string. Returns null for empty and
     * null strings.
     * 
     * @param axioms
     *        axioms
     * @param translationManager
     *        translationManager
     * @return set of axioms or null
     * @throws OwlStringException
     *         OwlStringException
     * @see #translate(Set,OWLOntologyManager)
     */
    public static Set<OWLAxiom> translate(String axioms,
            OWLOntologyManager translationManager) throws OwlStringException {
        if (axioms == null || axioms.isEmpty()) {
            return null;
        }
        try {
            OWLFunctionalSyntaxOWLParser p = new OWLFunctionalSyntaxOWLParser();
            OWLOntologyDocumentSource documentSource = new StringDocumentSource(
                    axioms);
            OWLOntology ontology = translationManager.createOntology();
            p.parse(documentSource, ontology);
            return ontology.getAxioms();
        } catch (UnloadableImportException e) {
            throw new OwlStringException(e);
        } catch (OWLOntologyCreationException e) {
            throw new OwlStringException(e);
        } catch (OWLParserException e) {
            throw new OwlStringException(e);
        } catch (IOException e) {
            throw new OwlStringException(e);
        }
    }
}
