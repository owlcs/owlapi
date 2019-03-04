package uk.ac.manchester.owl.owlapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class PrepareForRelease {
    public static void main(String[] args) throws IOException {
        String newVersion = "5.1.10";
        String[] toReplace = new String[] {"5.1.10-SNAPSHOT", "5.1.10"};
        treat(newVersion, toReplace,
            "../api/src/main/java/org/semanticweb/owlapi/util/VersionInfo.java");
        treat(newVersion, toReplace,
            "../osgidistribution/src/test/java/org/semanticweb/owlapi/api/test/VerifyVersionInfoIntegrationTestCase.java");
        treat(newVersion, toReplace, "../pom.xml");
        treat(newVersion, toReplace, "../api/pom.xml");
        treat(newVersion, toReplace, "../impl/pom.xml");
        treat(newVersion, toReplace, "../tools/pom.xml");
        treat(newVersion, toReplace, "../parsers/pom.xml");
        treat(newVersion, toReplace, "../oboformat/pom.xml");
        treat(newVersion, toReplace, "../rio/pom.xml");
        treat(newVersion, toReplace, "../compatibility/pom.xml");
        treat(newVersion, toReplace, "../apibinding/pom.xml");
        treat(newVersion, toReplace, "../contract/pom.xml");
        treat(newVersion, toReplace, "../distribution/pom.xml");
        treat(newVersion, toReplace, "../osgidistribution/pom.xml");
    }

    protected static void treat(String newVersion, String[] toReplace, String fileName)
        throws IOException, FileNotFoundException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        try (PrintStream out = new PrintStream(new File(fileName))) {
            out.println(lines.stream().map(s -> replaceall(s, newVersion, toReplace))
                .collect(Collectors.joining("\n")));
        }
    }

    private static String replaceall(String in, String replacement, String... values) {
        String toReturn = in;
        for (String s : values) {
            toReturn = toReturn.replace(s, replacement);
        }
        return toReturn;
    }
}
