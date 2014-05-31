package org.semanticweb.owlapi.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.io.BOMSafeInputStream;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.ReaderDocumentSource;

@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class BOMSafeInputStreamTestCase {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays
                .asList(new Object[][] {
                        { "<Ontology xml:base=\"http://www.example.org/ISA14#\" ontologyIRI=\"http://www.example.org/ISA14#\"> <Declaration><Class IRI=\"Researcher\"/></Declaration></Ontology>" },
                        { "Ontology: <http://www.example.org/ISA14#>\nClass: <http://www.example.org/ISA14#Researcher>" },
                        { "Ontology(<http://www.example.org/ISA14#>\nDeclaration(Class(<http://www.example.org/ISA14#Researcher>)))" },
                        { "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n<http://www.example.org/ISA14#> rdf:type owl:Ontology .\n<http://www.example.org/ISA14#Researcher> rdf:type owl:Class ." },
                        { "<rdf:RDF xml:base=\"http://www.example.org/ISA14#\" xmlns:owl =\"http://www.w3.org/2002/07/owl#\" xmlns:rdf =\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" ><owl:Ontology rdf:about=\"#\" /><owl:Class rdf:about=\"http://www.example.org/ISA14#Researcher\"/></rdf:RDF>" }, });
    }

    private String input;

    public BOMSafeInputStreamTestCase(String in) {
        input = in;
    }

    private static InputStream in(int[] b, String s) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i : b) {
            out.write(i);
        }
        out.write(s.getBytes());
        byte[] byteArray = out.toByteArray();
        return new BOMSafeInputStream(new ByteArrayInputStream(byteArray));
    }

    private static void compare(String in, OWLOntologyDocumentSource s)
            throws IOException {
        StringBuilder b = new StringBuilder();
        Reader reader = s.getReader();
        for (int i = reader.read(); i > -1; i = reader.read()) {
            b.append((char) i);
        }
        assertEquals(in, b.toString());
    }

    private static void compare(String in, InputStream s) throws IOException {
        StringBuilder b = new StringBuilder();
        for (int i = s.read(); i > -1; i = s.read()) {
            b.append((char) i);
        }
        assertEquals(in, b.toString());
    }

    // Bytes Encoding Form
    // 00 00 FE FF | UTF-32, big-endian
    // FF FE 00 00 | UTF-32, little-endian
    // FE FF |UTF-16, big-endian
    // FF FE |UTF-16, little-endian
    // EF BB BF |UTF-8
    @Test
    public void testBOMError32big() throws IOException {
        int[] b = new int[] { 0x00, 0x00, 0xFE, 0xFF };
        compare(input, in(b, input));
    }

    @Test
    public void testBOMError32small() throws IOException {
        int[] b = new int[] { 0xFF, 0xFE, 0x00, 0x00 };
        compare(input, in(b, input));
    }

    @Test
    public void testBOMError16big() throws IOException {
        int[] b = new int[] { 0xFF, 0xFE };
        compare(input, in(b, input));
    }

    @Test
    public void testBOMError16small() throws IOException {
        int[] b = new int[] { 0xFF, 0xFE };
        compare(input, in(b, input));
    }

    @Test
    public void testBOMError8() throws IOException {
        int[] b = new int[] { 0xEF, 0xBB, 0xBF };
        compare(input, in(b, input));
    }

    @Test
    public void testBOMError32bigReader() throws IOException {
        int[] b = new int[] { 0x00, 0x00, 0xFE, 0xFF };
        compare(input,
                new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }

    @Test
    public void testBOMError32Reader() throws IOException {
        int[] b = new int[] { 0xFF, 0xFE, 0x00, 0x00 };
        compare(input,
                new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }

    @Test
    public void testBOMError16Reader() throws IOException {
        int[] b = new int[] { 0xFF, 0xFE };
        compare(input,
                new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }

    @Test
    public void testBOMError16smallReader() throws IOException {
        int[] b = new int[] { 0xFF, 0xFE };
        compare(input,
                new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }

    @Test
    public void testBOMError8Reader() throws IOException {
        int[] b = new int[] { 0xEF, 0xBB, 0xBF };
        compare(input,
                new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }
}
