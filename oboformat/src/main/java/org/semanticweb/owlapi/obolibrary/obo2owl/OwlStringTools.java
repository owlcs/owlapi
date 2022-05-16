package org.semanticweb.owlapi.obolibrary.obo2owl;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.functional.parser.FunctionalSyntaxForAxiomsOnlyParser;
import org.semanticweb.owlapi.functional.renderer.FunctionalSyntaxObjectRenderer;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.obolibrary.oboformat.parser.OBOFormatException;

/**
 * Tools to read and write a set of owl axioms to/from a string. Used to
 * preserve untranslatable axioms in an owl2obo conversion.
 */
public class OwlStringTools {

    private OwlStringTools() {}

    /**
     * Create a string for the given set of axioms. Return null for empty sets
     * or if the set is null.
     *
     * @param axioms
     *        axioms
     * @return string or null
     * @see #translate(String, OWLOntology)
     */
    public static String translate(Collection<OWLAxiom> axioms) {
        if (axioms.isEmpty()) {
            return "";
        }
        try {
            Writer writer = new StringWriter();
            return new FunctionalSyntaxObjectRenderer(null, writer).renderAxioms(axioms);
        } catch (OWLRuntimeException e) {
            throw new OBOFormatException(e);
        }
    }

    /**
     * Parse the axioms from the given axiom string. Returns null for empty and
     * null strings.
     *
     * @param axioms
     *        axioms
     * @param o
     *        ontology to modify
     */
    public static void translate(@Nullable String axioms, OWLOntology o) {
        if (axioms == null || axioms.isEmpty()) {
            return;
        }
        try {
            FunctionalSyntaxForAxiomsOnlyParser parser = new FunctionalSyntaxForAxiomsOnlyParser();
            parser.parse(o, o.getOWLOntologyManager().getOntologyConfigurator(), axioms);
        } catch (UnloadableImportException | OWLParserException e) {
            throw new OWLRuntimeException(e);
        }
    }
}
