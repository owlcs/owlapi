package org.obolibrary.oboformat;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.obolibrary.obo2owl.OWLAPIObo2Owl;
import org.obolibrary.obo2owl.OWLAPIOwl2Obo;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.obolibrary.oboformat.writer.OBOFormatWriter;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;

public class OboFormatTestBasics extends TestBase {

    @Nonnull
    protected static String renderOboToString(@Nonnull OBODoc oboDoc) throws IOException {
        OBOFormatWriter writer = new OBOFormatWriter();
        writer.setCheckStructure(true);
        StringWriter out = new StringWriter();
        try(BufferedWriter stream = new BufferedWriter(out)){
            writer.write(oboDoc, stream);
        }
        return out.getBuffer().toString();
    }

    @Nonnull
    protected static OBODoc parseOboToString(@Nonnull String oboString) throws IOException {
        try(BufferedReader reader = new BufferedReader(new StringReader(oboString))){
            return new OBOFormatParser().parse(reader);
        }
    }

    @Nonnull
    protected OBODoc parseOBOURL(String fn) throws IOException {
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parseURL(fn);
        assertTrue(!obodoc.getTermFrames().isEmpty());
        return obodoc;
    }

    @Nonnull
    protected OBODoc parseOBOFile(String fn) {
        return parseOBOFile(fn, false, Collections.emptyMap());
    }

    @SuppressWarnings("resource")
    @Nonnull
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

    @Nonnull
    protected OBODoc parseOBOFile(@Nonnull Reader fn, boolean allowEmptyFrames, Map<String, OBODoc> cache) throws IOException {
        OBOFormatParser p = new OBOFormatParser(cache);
        OBODoc obodoc = p.parse(new BufferedReader(fn));
        assertNotNull(obodoc);
        if (obodoc.getTermFrames().isEmpty() && !allowEmptyFrames) {
            fail("Term frames should not be empty.");
        }
        return obodoc;
    }

    @SuppressWarnings("resource")
    @Nonnull
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

    @Nonnull
    protected OBODoc parseOBOFile(@Nonnull File file) throws IOException {
        OBOFormatParser p = new OBOFormatParser();
        return p.parse(file.getCanonicalPath());
    }

    @Nonnull
    protected OWLOntology parseOWLFile(String fn) throws OWLOntologyCreationException {
        // TODO replace
        return setupManager().loadOntologyFromOntologyDocument(getFile(fn));
    }

    @Nonnull
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

    @Nonnull
    protected OWLOntology convertOBOFile(String fn) {
        OWLOntology convert = convert(parseOBOFile(fn));
        writeOWL(convert);
        return convert;
    }

    @Nonnull
    protected OBODoc convert(@Nonnull OWLOntology ontology) {
        return convert(ontology, false);
    }

    @Nonnull
    protected OBODoc convert(@Nonnull OWLOntology ontology, boolean strictness) {
        OWLAPIOwl2Obo bridge = new OWLAPIOwl2Obo(setupManager());
        bridge.setStrictConversion(strictness);
        return bridge.convert(ontology);
    }

    @Nonnull
    protected String writeOBO(@Nonnull OBODoc obodoc) throws IOException {
        StringWriter target = new StringWriter();
        OBOFormatWriter oboWriter = new OBOFormatWriter();
        BufferedWriter bw = new BufferedWriter(target);
        oboWriter.write(obodoc, bw);
        bw.flush();
        return target.toString();
    }

    @Nonnull
    protected StringDocumentTarget writeOWL(@Nonnull OWLOntology ontology) {
        return writeOWL(ontology, new OWLXMLDocumentFormat());
    }

    @Nonnull
    protected StringDocumentTarget writeOWL(@Nonnull OWLOntology ontology,
        @Nonnull OWLDocumentFormat format) {
        StringDocumentTarget target = new StringDocumentTarget();
        try {
            ontology.getOWLOntologyManager().saveOntology(ontology, format, target);
        } catch (OWLOntologyStorageException e) {
            throw new OWLRuntimeException(e);
        }
        return target;
    }

    @Nullable
    protected IRI getIriByLabel(@Nonnull OWLOntology ontology, @Nonnull String label) {
        for (OWLAnnotationAssertionAxiom aa : ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue v = aa.getValue();
            OWLAnnotationProperty property = aa.getProperty();
            if (property.isLabel() && v instanceof OWLLiteral
                && label.equals(((OWLLiteral) v).getLiteral())) {
                OWLAnnotationSubject subject = aa.getSubject();
                if (subject instanceof IRI) {
                    return (IRI) subject;
                }
            }
        }
        return null;
    }

    @Nonnull
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

    protected static void renderOBO(@Nonnull OBODoc oboDoc) throws IOException {
        OBOFormatWriter writer = new OBOFormatWriter();
        writer.setCheckStructure(true);
        BufferedWriter stream = new BufferedWriter(new StringWriter());
        writer.setCheckStructure(true);
        writer.write(oboDoc, stream);
        stream.close();
    }

    protected static void renderOWL(@Nonnull OWLOntology owlOntology)
        throws OWLOntologyStorageException {
        OWLOntologyManager manager = owlOntology.getOWLOntologyManager();
        manager.saveOntology(owlOntology, new OWLXMLDocumentFormat(), new StringDocumentTarget());
    }

    protected static String renderOWL(@Nonnull OWLOntology owlOntology,
        @Nonnull OWLDocumentFormat ontologyFormat) throws OWLOntologyStorageException, IOException {
        OWLOntologyManager manager = owlOntology.getOWLOntologyManager();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        manager.saveOntology(owlOntology, ontologyFormat, out);
        out.close();
        return out.toString();
    }
}
