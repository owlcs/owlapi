package org.obolibrary.oboformat;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import org.obolibrary.obo2owl.OWLAPIObo2Owl;
import org.obolibrary.obo2owl.OWLAPIOwl2Obo;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.obolibrary.oboformat.writer.OBOFormatWriter;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;

public class OboFormatTestBasics extends TestBase {

    protected static String renderOboToString(OBODoc oboDoc) throws IOException {
        OBOFormatWriter writer = new OBOFormatWriter();
        writer.setCheckStructure(true);
        StringWriter out = new StringWriter();
        writer.write(oboDoc, new PrintWriter(out));
        return out.getBuffer().toString();
    }

    protected static OBODoc parseOboToString(String oboString) throws IOException {
        return new OBOFormatParser().parse(new StringReader(oboString));
    }

    protected OBODoc parseOBOFile(String fn) {
        return parseOBOFile(fn, false, Collections.emptyMap());
    }

    protected OBODoc parseOBOFile(String fn, boolean allowEmptyFrames, Map<String, OBODoc> cache) {
        try (InputStream inputStream = new FileInputStream(getFile(fn))) {
            OBOFormatParser p = new OBOFormatParser(cache);
            OBODoc obodoc = p.parse(new BufferedReader(new InputStreamReader(inputStream)));
            assertNotNull(obodoc);
            if (obodoc.getTermFrames().isEmpty() && !allowEmptyFrames) {
                fail("Term frames should not be empty.");
            }
            return obodoc;
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OBODoc parseOBOFile(Reader fn, boolean allowEmptyFrames, Map<String, OBODoc> cache) throws IOException {
        OBOFormatParser p = new OBOFormatParser(cache);
        OBODoc obodoc = p.parse(new BufferedReader(fn));
        assertNotNull(obodoc);
        if (obodoc.getTermFrames().isEmpty() && !allowEmptyFrames) {
            fail("Term frames should not be empty.");
        }
        return obodoc;
    }

    protected File getFile(String fn) {
        URL inputStream = OboFormatTestBasics.class.getResource(fn);
        if (inputStream == null) {
            inputStream = getClass().getResource("obo/" + fn);
        }
        if (inputStream == null) {
            inputStream = ClassLoader.getSystemResource(fn);
        }
        if (inputStream == null) {
            inputStream = ClassLoader.getSystemResource("obo/" + fn);
        }
        if (inputStream == null) {
            return new File("obo/" + fn);
        }
        try {
            return new File(inputStream.toURI());
        } catch (URISyntaxException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OBODoc parseOBOFile(File file) throws IOException {
        OBOFormatParser p = new OBOFormatParser();
        return p.parse(file.getCanonicalPath());
    }

    protected OWLOntology parseOWLFile(String fn) throws OWLOntologyCreationException {
        OWLOntologyManager manager = setupManager();
        // TODO replace
        return manager.loadOntologyFromOntologyDocument(getFile(fn));
    }

    protected OWLOntology convert(OBODoc obodoc) {
        OWLAPIObo2Owl bridge = new OWLAPIObo2Owl(setupManager());
        OWLOntology ontology;
        try {
            ontology = bridge.convert(obodoc);
            return ontology;
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology convertOBOFile(String fn) {
        OWLOntology convert = convert(parseOBOFile(fn));
        writeOWL(convert);
        return convert;
    }

    protected OBODoc convert(OWLOntology ontology) {
        return convert(ontology, false);
    }

    protected OBODoc convert(OWLOntology ontology, boolean strictness) {
        OWLAPIOwl2Obo bridge = new OWLAPIOwl2Obo(setupManager());
        bridge.setStrictConversion(strictness);
        return bridge.convert(ontology);
    }

    protected String writeOBO(OBODoc obodoc) throws IOException {
        StringWriter target = new StringWriter();
        OBOFormatWriter oboWriter = new OBOFormatWriter();
        BufferedWriter bw = new BufferedWriter(target);
        oboWriter.write(obodoc, new PrintWriter(bw));
        bw.flush();
        return target.toString();
    }

    protected StringDocumentTarget writeOWL(OWLOntology ontology) {
        return writeOWL(ontology, new OWLXMLDocumentFormat());
    }

    protected StringDocumentTarget writeOWL(OWLOntology ontology, OWLDocumentFormat format) {
        StringDocumentTarget target = new StringDocumentTarget();
        try {
            ontology.saveOntology(format, target);
        } catch (OWLOntologyStorageException e) {
            throw new OWLRuntimeException(e);
        }
        return target;
    }

    protected @Nullable IRI getIriByLabel(OWLOntology ontology, String label) {
        Optional<OWLAnnotationAssertionAxiom> anyMatch =
            ontology.axioms(AxiomType.ANNOTATION_ASSERTION)
                .filter(aa -> aa.getProperty().isLabel() && aa.getValue() instanceof OWLLiteral
                    && label.equals(((OWLLiteral) aa.getValue()).getLiteral()))
                .filter(aa -> aa.getSubject().isIRI()).findAny();
        if (anyMatch.isPresent()) {
            return (IRI) anyMatch.get().getSubject();
        }
        return null;
    }

    protected String readResource(String resource) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = new FileInputStream(getFile(resource));
            Reader r = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(r);) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }
}
