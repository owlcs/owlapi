import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Sets;

public class ClearDuplicatesReport {

    public static void main(String[] args) throws FileNotFoundException,
            IOException {
        Set<String> filesToSkip = Sets
                .newHashSet(
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss2/parser/Token.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss2/parser/ParseException.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss1/parser/JavaCharStream.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss1/parser/KRSSOWLParser.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss1/parser/KRSSOWLParserException.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss1/parser/KRSSOWLParserFactory.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss1/parser/KRSSParser.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss1/parser/KRSSParserConstants.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss1/parser/KRSSParserTokenManager.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss1/parser/ParseException.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss1/parser/Token.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss1/parser/TokenMgrError.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss2/parser/JavaCharStream.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss2/parser/KRSS2OWLParser.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss2/parser/KRSS2OWLParserException.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss2/parser/KRSS2OWLParserFactory.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss2/parser/KRSS2Parser.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss2/parser/KRSS2ParserConstants.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss2/parser/KRSS2ParserTokenManager.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/krss2/parser/TokenMgrError.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/functional/parser/CustomTokenizer.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/functional/parser/OWLFunctionalSyntaxOWLParser.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/functional/parser/OWLFunctionalSyntaxOWLParserFactory.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/functional/parser/OWLFunctionalSyntaxParser.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/functional/parser/OWLFunctionalSyntaxParserConstants.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/functional/parser/OWLFunctionalSyntaxParserTokenManager.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/functional/parser/ParseException.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/functional/parser/SimpleCharStream.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/functional/parser/Token.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/functional/parser/TokenManager.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/functional/parser/TokenMgrError.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/rdf/turtle/parser/TurtleParser.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/rdf/turtle/parser/TurtleParserConstants.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/rdf/turtle/parser/TurtleParserException.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/rdf/turtle/parser/TurtleParserTokenManager.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/rdf/turtle/parser/ParseException.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/rdf/turtle/parser/TokenMgrError.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/rdf/turtle/parser/TurtleOntologyParser.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/dlsyntax/parser/DLSyntaxParser.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/dlsyntax/parser/DLSyntaxParserConstants.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/dlsyntax/parser/DLSyntaxParserTokenManager.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/dlsyntax/parser/ParseException.java",
                        "/Users/ignazio/workspace/owlapi/parsers/src/main/java/org/semanticweb/owlapi/dlsyntax/parser/TokenMgrError.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/OWLAPIServiceLoaderModule.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/AbstractCompositeOntologyChange.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/AddClassExpressionClosureAxiom.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/AddOntologyAnnotationData.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/AddImportData.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/CoerceConstantsIntoDataPropertyRange.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/ConvertEquivalentClassesToSuperClasses.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/ConvertPropertyAssertionsToAnnotations.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/AmalgamateSubClassAxioms.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/CreateValuePartition.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/ConvertSuperClassesToEquivalentClass.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/CreateValuePartition.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/MakeClassesMutuallyDisjoint.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/MakePrimitiveSubClassesMutuallyDisjoint.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/OWLOntologyChangeDataVisitor.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/OWLOntologyChangeData.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/OWLOntologyChangeRecord.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/RemoveAxiomData.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/AddAxiomData.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/SetOntologyIDData.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/OntologyAnnotationChangeData.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/ImportChangeData.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/AxiomChangeData.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/ShortForm2AnnotationGenerator.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/change/SplitSubClassAxioms.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/expression/OWLEntityChecker.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/expression/ShortFormEntityChecker.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/AbstractRDFPrefixDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/AbstractRDFNonPrefixDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/DLSyntaxDocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/DLSyntaxDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/DLSyntaxHTMLDocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/DLSyntaxHTMLDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/FunctionalSyntaxDocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/FunctionalSyntaxDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/KRSS2DocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/KRSS2DocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/KRSSDocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/KRSSDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/LabelFunctionalDocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/LabelFunctionalDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/LatexAxiomsListDocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/LatexAxiomsListDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/LatexDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/LatexAxiomsListDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/LabelFunctionalDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/KRSSDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/KRSS2DocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/DLSyntaxHTMLDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/DLSyntaxDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/LatexDocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/LatexDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/ManchesterSyntaxDocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/ManchesterSyntaxDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/OBODocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/LatexDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/LatexAxiomsListDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/LabelFunctionalDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/KRSSDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/KRSS2DocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/DLSyntaxHTMLDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/DLSyntaxDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/OBODocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/OBODocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/OWLXMLDocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/OWLXMLDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/PrefixDocumentFormatImpl.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/PrefixDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/RDFXMLDocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/RDFXMLDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/TurtleDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/RDFXMLDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/TurtleDocumentFormatFactory.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/formats/TurtleDocumentFormat.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/io/DocumentSources.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/io/AbstractOWLParser.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/io/DocumentSources.java",
                        "/Users/ignazio/workspace/owlapi/api/src/main/java/org/semanticweb/owlapi/io/FileDocumentSource.java",
                        "/Users/ignazio/workspace/owlapi/tools/src/main/java/uk/ac/manchester/cs/owlapi/modularity/SyntacticLocalityModuleExtractor.java",
                        "/Users/ignazio/workspace/owlapi/tools/src/main/java/uk/ac/manchester/cs/owlapi/modularity/ModuleType.java",
                        "/Users/ignazio/workspace/owlapi/compatibility/src/main/java/org/coode/owlapi/obo12/parser/OBOParserTokenManager.java",
                        "", "");
        // Set<String> files = new HashSet<>();
        Map<String, List<String>> map = new HashMap<>();
        Iterator<String> readLines = IOUtils.readLines(
                new FileInputStream("reports/duplicates.txt")).iterator();
        StringBuilder b = new StringBuilder();
        List<String> refs = new ArrayList<>();
        boolean toSkip = false;
        while (readLines.hasNext()) {
            String l = readLines.next();
            if (l.startsWith("=============================")) {
                if (!toSkip) {
                    map.put(b.append("\n=============================")
                            .toString(), refs);
                }
                b = new StringBuilder();
                refs = new ArrayList<>();
                toSkip = false;
            } else {
                b.append('\n').append(l);
            }
            if (l.startsWith("Starting at line ")) {
                String name = l.substring(l.indexOf("/"));
                refs.add(name);
                if (filesToSkip.contains(name)) {
                    toSkip = true;
                }
            }
        }
        // map.values().forEach(l -> files.addAll(l));
        // files.stream().sorted().forEach(l -> {
        // if (l.contains("/parsers/")) {
        // System.out.println(l);
        // }
        // });
        System.out.println("ClearDuplicatesReport.main() " + map.size());
        PrintStream out = new PrintStream("duplicates.txt");
        map.entrySet()
                .stream()
                .sorted((e1, e2) -> e1.getKey().toString()
                        .compareTo(e2.getKey().toString()))
                .forEach(e -> out.println(e.getKey()));
    }
}
