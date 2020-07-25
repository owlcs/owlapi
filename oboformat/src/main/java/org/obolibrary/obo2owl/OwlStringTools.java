package org.obolibrary.obo2owl;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.functional.parser.FunctionalSyntaxForAxiomsOnlyParser;
import org.semanticweb.owlapi.functional.renderer.FunctionalSyntaxObjectRenderer;
import org.semanticweb.owlapi.io.OWLParserException;
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
     * @param translationManager translation manager
     * @return string or null
     * @deprecated use {@link #translate(Collection)}
     */
    @Deprecated
    public static String translate(Collection<OWLAxiom> axioms,
        @SuppressWarnings("unused") OWLOntologyManager translationManager) {
        return translate(axioms);
    }

    /**
     * Create a string for the given set of axioms. Return null for empty sets or if the set is
     * null.
     *
     * @param axioms axioms
     * @return string or null
     * @see #translate(String, OWLOntologyManager)
     */
    public static String translate(Collection<OWLAxiom> axioms) {
        if (axioms.isEmpty()) {
            return "";
        }
        try {
            Writer writer = new StringWriter();
            return new FunctionalSyntaxObjectRenderer(null, writer).renderAxioms(axioms);
        } catch (OWLRuntimeException e) {
            throw new OwlStringException(e);
        }
    }

    /**
     * Parse the axioms from the given axiom string. Returns null for empty and null strings.
     *
     * @param axioms axioms
     * @param translationManager translation manager
     * @return set of axioms or null
     * @see #translate(Collection, OWLOntologyManager)
     */
    public static Collection<OWLAxiom> translate(@Nullable String axioms,
        OWLOntologyManager translationManager) {
        if (axioms == null || axioms.isEmpty()) {
            return Collections.emptySet();
        }
        try {
            OWLOntology o = translationManager.createOntology();
            translate(axioms, o);
            return asList(o.axioms());
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * Parse the axioms from the given axiom string.
     *
     * @param axioms axioms
     * @param o ontology that will accept the axioms
     * @see #translate(Collection, OWLOntologyManager)
     */
    public static void translate(@Nullable String axioms, OWLOntology o) {
        if (axioms == null || axioms.isEmpty()) {
            return;
        }
        try {
            FunctionalSyntaxForAxiomsOnlyParser parser = new FunctionalSyntaxForAxiomsOnlyParser();
            parser.parse(o, o.getOWLOntologyManager().getOntologyLoaderConfiguration(), axioms);
        } catch (UnloadableImportException | OWLParserException e) {
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
