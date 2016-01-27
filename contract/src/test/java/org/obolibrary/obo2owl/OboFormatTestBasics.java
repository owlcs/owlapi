package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.io.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.obolibrary.oboformat.writer.OBOFormatWriter;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;

@SuppressWarnings({ "javadoc", })
public class OboFormatTestBasics extends TestBase {

    @Nonnull
    protected OBODoc parseOBOURL(String fn) throws IOException {
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parseURL(fn);
        assertTrue(!obodoc.getTermFrames().isEmpty());
        return obodoc;
    }

    @Nonnull
    protected OBODoc parseOBOFile(String fn) {
        return parseOBOFile(fn, false);
    }

    @SuppressWarnings("resource")
    @Nonnull
    protected OBODoc parseOBOFile(String fn, boolean allowEmptyFrames) {
        InputStream inputStream = getInputStream(fn);
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc;
        try {
            obodoc = p.parse(new BufferedReader(new InputStreamReader(inputStream)));
            assertNotNull("The obodoc should not be null", obodoc);
            if (obodoc.getTermFrames().isEmpty() && !allowEmptyFrames) {
                fail("Term frames should not be empty.");
            }
            return obodoc;
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Nonnull
    protected OBODoc parseOBOFile(@Nonnull Reader fn, boolean allowEmptyFrames) throws IOException {
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parse(new BufferedReader(fn));
        assertNotNull("The obodoc should not be null", obodoc);
        if (obodoc.getTermFrames().isEmpty() && !allowEmptyFrames) {
            fail("Term frames should not be empty.");
        }
        return obodoc;
    }

    @SuppressWarnings("resource")
    @Nonnull
    protected InputStream getInputStream(String fn) {
        InputStream inputStream = OboFormatTestBasics.class.getResourceAsStream(fn);
        if (inputStream == null) {
            inputStream = getClass().getResourceAsStream("obo/" + fn);
        }
        if (inputStream == null) {
            inputStream = ClassLoader.getSystemResourceAsStream(fn);
        }
        if (inputStream == null) {
            inputStream = ClassLoader.getSystemResourceAsStream("obo/" + fn);
        }
        if (inputStream == null) {
            try {
                inputStream = new FileInputStream(new File("obo/" + fn));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return inputStream;
    }

    @Nonnull
    protected OBODoc parseOBOFile(@Nonnull File file) throws IOException {
        OBOFormatParser p = new OBOFormatParser();
        return p.parse(file.getCanonicalPath());
    }

    @Nonnull
    protected OWLOntology parseOWLFile(String fn) throws OWLOntologyCreationException {
        // TODO replace
        return m.loadOntologyFromOntologyDocument(getInputStream(fn));
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
        OWLAPIOwl2Obo bridge = new OWLAPIOwl2Obo(m);
        bridge.setStrictConversion(strictness);
        return bridge.convert(ontology);
    }

    @Nonnull
    protected String writeOBO(@Nonnull OBODoc obodoc) throws IOException {
        StringWriter target = new StringWriter();
        OBOFormatWriter oboWriter = new OBOFormatWriter();
        BufferedWriter bw = new BufferedWriter(target);
        oboWriter.setCheckStructure(true);
        oboWriter.write(obodoc, bw);
        bw.flush();
        return target.toString();
    }

    @Nonnull
    protected StringDocumentTarget writeOWL(@Nonnull OWLOntology ontology) {
        return writeOWL(ontology, new OWLXMLDocumentFormat());
    }

    @Nonnull
    protected StringDocumentTarget writeOWL(@Nonnull OWLOntology ontology, @Nonnull OWLDocumentFormat format) {
        StringDocumentTarget target = new StringDocumentTarget();
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        try {
            manager.saveOntology(ontology, format, target);
        } catch (OWLOntologyStorageException e) {
            throw new OWLRuntimeException(e);
        }
        return target;
    }

    @Nonnull
    protected static String renderOboToString(@Nonnull OBODoc oboDoc) throws IOException {
        OBOFormatWriter writer = new OBOFormatWriter();
        StringWriter out = new StringWriter();
        BufferedWriter stream = new BufferedWriter(out);
        writer.setCheckStructure(true);
        writer.write(oboDoc, stream);
        stream.close();
        return out.getBuffer().toString();
    }

    @Nonnull
    protected static OBODoc parseOboToString(@Nonnull String oboString) throws IOException {
        OBOFormatParser p = new OBOFormatParser();
        BufferedReader reader = new BufferedReader(new StringReader(oboString));
        OBODoc parsedOboDoc = p.parse(reader);
        reader.close();
        return parsedOboDoc;
    }

    protected static void renderOBO(@Nonnull OBODoc oboDoc) throws IOException {
        OBOFormatWriter writer = new OBOFormatWriter();
        writer.setCheckStructure(true);
        BufferedWriter stream = new BufferedWriter(new StringWriter());
        writer.setCheckStructure(true);
        writer.write(oboDoc, stream);
        stream.close();
    }

    protected static void renderOWL(@Nonnull OWLOntology owlOntology) throws OWLOntologyStorageException {
        OWLOntologyManager manager = owlOntology.getOWLOntologyManager();
        manager.saveOntology(owlOntology, new OWLXMLDocumentFormat(), new StringDocumentTarget());
    }

    protected static String renderOWL(@Nonnull OWLOntology owlOntology, @Nonnull OWLDocumentFormat ontologyFormat)
        throws OWLOntologyStorageException, IOException {
        OWLOntologyManager manager = owlOntology.getOWLOntologyManager();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        manager.saveOntology(owlOntology, ontologyFormat, out);
        out.close();
        return out.toString();
    }

    @Nullable
    protected IRI getIriByLabel(@Nonnull OWLOntology ontology, @Nonnull String label) {
        for (OWLAnnotationAssertionAxiom aa : ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue v = aa.getValue();
            OWLAnnotationProperty property = aa.getProperty();
            if (property.isLabel() && v instanceof OWLLiteral) {
                if (label.equals(((OWLLiteral) v).getLiteral())) {
                    OWLAnnotationSubject subject = aa.getSubject();
                    if (subject instanceof IRI) {
                        return (IRI) subject;
                    }
                }
            }
        }
        return null;
    }

    @Nonnull
    protected String readResource(String resource) throws IOException {
        InputStream inputStream = getInputStream(resource);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }
        reader.close();
        inputStream.close();
        return sb.toString();
    }
}
