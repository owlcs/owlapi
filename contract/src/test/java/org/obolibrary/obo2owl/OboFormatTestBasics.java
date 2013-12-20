package org.obolibrary.obo2owl;

import static junit.framework.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.obolibrary.oboformat.parser.OBOFormatParserException;
import org.obolibrary.oboformat.writer.OBOFormatWriter;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class OboFormatTestBasics {
    private final static File systemTempDir = new File(
            System.getProperty("java.io.tmpdir"));

    protected OBODoc parseOBOURL(String fn) throws IOException, OBOFormatParserException {
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parseURL(fn);
        assertTrue(obodoc.getTermFrames().size() > 0);
        return obodoc;
    }

    protected OBODoc parseOBOFile(String fn) throws IOException, OBOFormatParserException {
        return parseOBOFile(fn, false);
    }

    protected OBODoc parseOBOFile(String fn, boolean allowEmptyFrames)
            throws IOException, OBOFormatParserException {
        InputStream inputStream = getInputStream(fn);
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parse(new BufferedReader(new InputStreamReader(inputStream)));
        assertNotNull("The obodoc should not be null", obodoc);
        if (obodoc.getTermFrames().size() == 0 && !allowEmptyFrames) {
            fail("Term frames should not be empty.");
        }
        return obodoc;
    }

    protected InputStream getInputStream(String fn) {
        InputStream inputStream = getClass().getResourceAsStream(fn);
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
        if (inputStream == null) {
            fail("Could not find resource in classpath: " + fn);
        }
        return inputStream;
    }

    protected OBODoc parseOBOFile(File file) throws IOException, OBOFormatParserException {
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parse(file.getCanonicalPath());
        return obodoc;
    }

    protected OWLOntology parseOWLFile(String fn) throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        // TODO replace
        OWLOntology ontology = manager
                .loadOntologyFromOntologyDocument(getInputStream(fn));
        return ontology;
    }

    protected OWLOntology convert(OBODoc obodoc) throws OWLOntologyCreationException {
        OWLAPIObo2Owl bridge = new OWLAPIObo2Owl(OWLManager.createOWLOntologyManager());
        OWLOntology ontology = bridge.convert(obodoc);
        return ontology;
    }

    protected OWLOntology convert(OBODoc obodoc, String filename)
            throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
        OWLOntology ontology = convert(obodoc);
        writeOWL(ontology, filename);
        return ontology;
    }

    protected OBODoc convert(OWLOntology ontology) throws OWLOntologyCreationException {
        OWLAPIOwl2Obo bridge = new OWLAPIOwl2Obo(OWLManager.createOWLOntologyManager());
        OBODoc doc = bridge.convert(ontology);
        return doc;
    }

    protected File writeOBO(OBODoc obodoc, String fn) throws IOException {
        if (!fn.toLowerCase().endsWith(".obo")) {
            fn += ".obo";
        }
        File file = new File(systemTempDir, fn);
        OBOFormatWriter oboWriter = new OBOFormatWriter();
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file),
                "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw);
        oboWriter.write(obodoc, bw);
        bw.close();
        return file;
    }

    protected File writeOWL(OWLOntology ontology, String filename)
            throws OWLOntologyStorageException {
        return writeOWL(ontology, filename, new OWLXMLOntologyFormat());
    }

    protected File writeOWL(OWLOntology ontology, String filename,
            OWLOntologyFormat format) throws OWLOntologyStorageException {
        if (!filename.toLowerCase().endsWith(".owl")) {
            filename += ".owl";
        }
        File tempFile = new File(systemTempDir, filename);
        IRI iri = IRI.create(tempFile);
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        System.out.println("saving to " + iri);
        manager.saveOntology(ontology, format, iri);
        return tempFile;
    }

    protected static String renderOboToString(OBODoc oboDoc) throws IOException {
        OBOFormatWriter writer = new OBOFormatWriter();
        writer.setCheckStructure(true);
        StringWriter out = new StringWriter();
        BufferedWriter stream = new BufferedWriter(out);
        writer.write(oboDoc, stream);
        stream.close();
        return out.getBuffer().toString();
    }

    protected static OBODoc parseOboToString(String oboString) throws IOException,
            OBOFormatParserException {
        OBOFormatParser p = new OBOFormatParser();
        BufferedReader reader = new BufferedReader(new StringReader(oboString));
        OBODoc parsedOboDoc = p.parse(reader);
        reader.close();
        return parsedOboDoc;
    }

    protected static void renderOBO(OBODoc oboDoc) throws IOException {
        OBOFormatWriter writer = new OBOFormatWriter();
        writer.setCheckStructure(true);
        BufferedWriter stream = new BufferedWriter(new PrintWriter(System.out));
        writer.write(oboDoc, stream);
        stream.close();
    }

    protected static void renderOWL(OWLOntology owlOntology)
            throws OWLOntologyStorageException {
        OWLOntologyManager manager = owlOntology.getOWLOntologyManager();
        manager.saveOntology(owlOntology, new OWLXMLOntologyFormat(), System.out);
    }

    protected static String renderOWL(OWLOntology owlOntology,
            OWLOntologyFormat ontologyFormat) throws OWLOntologyStorageException,
            IOException {
        OWLOntologyManager manager = owlOntology.getOWLOntologyManager();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        manager.saveOntology(owlOntology, ontologyFormat, out);
        out.close();
        return out.toString();
    }

    protected IRI getIriByLabel(OWLOntology ontology, String label) {
        for (OWLAnnotationAssertionAxiom aa : ontology
                .getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
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

    protected String readResource(String resource) throws IOException {
        InputStream inputStream = getInputStream(resource);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }
        reader.close();
        inputStream.close();
        return sb.toString();
    }
}
