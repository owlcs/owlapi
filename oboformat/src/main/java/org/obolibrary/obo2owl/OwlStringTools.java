package org.obolibrary.obo2owl;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
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
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * Tools to read and write a set of owl axioms to/from a string. Used to preserve untranslatable
 * axioms in an owl2obo conversion.
 */
public class OwlStringTools {

    private OwlStringTools() {}

    /**
     * Create a string for the given set of axioms. Return null for empty sets or if the set is
     * null.
     *
     * @param axioms axioms
     * @param translationManager translationManager
     * @return string or null
     * @see #translate(String, OWLOntologyManager)
     */
    public static String translate(Collection<OWLAxiom> axioms,
        OWLOntologyManager translationManager) {
        if (axioms.isEmpty()) {
            return "";
        }
        try {
            OWLOntology ontology = translationManager.createOntology();
            ontology.add(axioms);
            OWLFunctionalSyntaxRenderer r = new OWLFunctionalSyntaxRenderer();
            Writer writer = new StringWriter();
            PrintWriter w = new PrintWriter(writer);
            r.render(ontology, w, StandardCharsets.UTF_8);
            w.flush();
            return writer.toString();
        } catch (OWLRendererException | OWLOntologyCreationException | OWLRuntimeException e) {
            throw new OwlStringException(e);
        }
    }

    /**
     * Parse the axioms from the given axiom string. Returns null for empty and null strings.
     *
     * @param axioms axioms
     * @param translationManager translationManager
     * @return set of axioms or null
     * @see #translate(Collection, OWLOntologyManager)
     */
    public static Collection<OWLAxiom> translate(@Nullable String axioms,
        OWLOntologyManager translationManager) {
        if (axioms == null || axioms.isEmpty()) {
            return Collections.emptySet();
        }
        try {
            OWLFunctionalSyntaxOWLParser p = new OWLFunctionalSyntaxOWLParser();
            OWLOntologyDocumentSource documentSource =
                new StringDocumentSource(axioms, new FunctionalSyntaxDocumentFormat());
            OWLOntology ontology = translationManager.createOntology();
            documentSource.acceptParser(p, ontology,
                translationManager.getOntologyLoaderConfiguration());
            return asList(ontology.axioms());
        } catch (UnloadableImportException | OWLOntologyCreationException | OWLParserException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * Exception indicating an un-recoverable error during the handling of axiom strings.
     */
    public static class OwlStringException extends OWLRuntimeException {

        /**
         * @param cause cause
         */
        protected OwlStringException(Throwable cause) {
            super(cause);
        }
    }
}
