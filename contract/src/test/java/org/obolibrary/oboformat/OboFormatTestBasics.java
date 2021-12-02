package org.obolibrary.oboformat;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

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
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLRuntimeException;

public class OboFormatTestBasics extends TestBase {

    @Nonnull
    protected static String renderOboToString(@Nonnull OBODoc oboDoc) {
        OBOFormatWriter writer = new OBOFormatWriter();
        writer.setCheckStructure(true);
        StringWriter out = new StringWriter();
        try (BufferedWriter stream = new BufferedWriter(out)) {
            writer.write(oboDoc, stream);
        } catch (IOException ex) {
            throw new OWLRuntimeException(ex);
        }
        return out.getBuffer().toString();
    }

    @Nonnull
    protected static OBODoc parseOboToString(@Nonnull String oboString) {
        try (BufferedReader reader = new BufferedReader(new StringReader(oboString))) {
            return new OBOFormatParser().parse(reader);
        } catch (IOException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @Nonnull
    protected OBODoc parseOBOURL(String fn) {
        try {
            OBODoc obodoc = new OBOFormatParser().parseURL(fn);
            assertTrue(!obodoc.getTermFrames().isEmpty());
            return obodoc;
        } catch (IOException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @Nonnull
    protected OBODoc parseOBOFile(String fn) {
        return parseOBOFile(fn, false, Collections.emptyMap());
    }

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
        } catch (IOException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @Nonnull
    protected OBODoc parseOBOFile(@Nonnull Reader fn, boolean allowEmptyFrames,
        Map<String, OBODoc> cache) {
        try {
            OBODoc obodoc = new OBOFormatParser(cache).parse(new BufferedReader(fn));
            assertNotNull(obodoc);
            if (obodoc.getTermFrames().isEmpty() && !allowEmptyFrames) {
                fail("Term frames should not be empty.");
            }
            return obodoc;
        } catch (IOException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

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
        } catch (URISyntaxException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @Nonnull
    protected OBODoc parseOBOFile(@Nonnull File file) {
        try {
            return new OBOFormatParser().parse(file.getCanonicalPath());
        } catch (IOException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @Nonnull
    protected OWLOntology parseOWLFile(String fn) {
        return loadOntologyFromFile(getFile(fn));
    }

    @Nonnull
    protected OWLOntology convert(OBODoc obodoc) {
        return convert(obodoc, new OWLAPIObo2Owl(setupManager()));
    }

    @Nonnull
    protected OWLOntology convert(OBODoc obodoc, OWLAPIObo2Owl bridge) {
        try {
            return bridge.convert(obodoc);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
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
    protected String writeOBO(@Nonnull OBODoc obodoc) {
        try {
            StringWriter target = new StringWriter();
            OBOFormatWriter oboWriter = new OBOFormatWriter();
            BufferedWriter bw = new BufferedWriter(target);
            oboWriter.write(obodoc, bw);
            bw.flush();
            return target.toString();
        } catch (IOException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @Nonnull
    protected StringDocumentTarget writeOWL(@Nonnull OWLOntology ontology) {
        return saveOntology(ontology, new OWLXMLDocumentFormat());
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
    protected String readResource(String resource) {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = new FileInputStream(getFile(resource));
            Reader r = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(r);) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException ex) {
            throw new OWLRuntimeException(ex);
        }
        return sb.toString();
    }

    protected static void renderOBO(@Nonnull OBODoc oboDoc) {
        OBOFormatWriter writer = new OBOFormatWriter();
        writer.setCheckStructure(true);
        try (BufferedWriter stream = new BufferedWriter(new StringWriter())) {
            writer.setCheckStructure(true);
            writer.write(oboDoc, stream);
        } catch (IOException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected void renderOWL(@Nonnull OWLOntology owlOntology) {
        renderOWL(owlOntology, new OWLXMLDocumentFormat());
    }

    protected String renderOWL(@Nonnull OWLOntology owlOntology,
        @Nonnull OWLDocumentFormat ontologyFormat) {
        return saveOntology(owlOntology, ontologyFormat).toString();
    }
}
