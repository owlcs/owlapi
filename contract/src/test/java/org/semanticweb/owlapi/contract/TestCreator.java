package org.semanticweb.owlapi.contract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("javadoc")
public class TestCreator {

    public static void _main() throws Exception {
        Map<String, PrintStream> streams = new HashMap<String, PrintStream>();
        File[] roots = new File[] { new File("api/src/main/java"),
                new File("apibinding/src/main/java"),
                new File("impl/src/main/java"), new File("misc/src/main/java"),
                new File("parsers/src/main/java"),
                new File("tutorial2012/src/main/java") };
        for (File root : roots) {
            visit(root, null, streams);
        }
        for (PrintStream out : streams.values()) {
            out.println("}");
            out.close();
        }
    }

    private static PrintStream initPrintStream(String _name)
            throws FileNotFoundException {
        String name = _name.replace("/", "_");
        PrintStream out = new PrintStream(
                "apibinding/src/test/java/org/semanticweb/owlapi/unit/test/GeneratedJUnitTest"
                        + name + ".java");
        out.println("package org.semanticweb.owlapi.contract;import static org.mockito.Mockito.mock;import java.io.*;import java.net.*;import java.util.*;import org.junit.Test;import static org.junit.Assert.*;import java.io.*;import java.net.*;import java.util.*;import org.coode.owl.krssparser.*;import org.coode.owlapi.examples.*;import org.coode.owlapi.functionalparser.*;import org.coode.owlapi.functionalrenderer.*;import org.coode.owlapi.latex.*;import org.coode.owlapi.manchesterowlsyntax.*;import org.coode.owlapi.obo.parser.*;import org.coode.owlapi.obo.renderer.*;import org.coode.owlapi.owlxml.renderer.*;import org.coode.owlapi.owlxmlparser.*;import org.coode.owlapi.rdf.model.*;import org.coode.owlapi.rdf.rdfxml.*;import org.coode.owlapi.rdf.renderer.*;import org.coode.owlapi.rdfxml.parser.*;import org.coode.owlapi.turtle.*;import org.coode.string.*;import org.coode.xml.*;import org.junit.Test;import org.semanticweb.owlapi.*;import org.semanticweb.owlapi.apibinding.*;import org.semanticweb.owlapi.debugging.*;import org.semanticweb.owlapi.expression.*;import org.semanticweb.owlapi.io.*;import org.semanticweb.owlapi.metrics.*;import org.semanticweb.owlapi.model.*;import org.semanticweb.owlapi.modularity.*;import org.semanticweb.owlapi.normalform.*;import org.semanticweb.owlapi.profiles.*;import org.semanticweb.owlapi.rdf.syntax.*;import org.semanticweb.owlapi.rdf.util.*;import org.semanticweb.owlapi.reasoner.*;import org.semanticweb.owlapi.reasoner.impl.*;import org.semanticweb.owlapi.reasoner.knowledgeexploration.OWLKnowledgeExplorerReasoner;import org.semanticweb.owlapi.reasoner.structural.*;import org.semanticweb.owlapi.util.*;import org.semanticweb.owlapi.util.ImportsStructureObjectSorter.ObjectSelector;import org.semanticweb.owlapi.vocab.*;import org.xml.sax.*;import uk.ac.manchester.cs.bhig.util.*;import uk.ac.manchester.cs.owl.explanation.ordering.*;import uk.ac.manchester.cs.owl.owlapi.*;import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.*;import uk.ac.manchester.cs.owl.owlapi.turtle.parser.*;import uk.ac.manchester.cs.owlapi.dlsyntax.*;import uk.ac.manchester.cs.owlapi.dlsyntax.parser.*;import uk.ac.manchester.cs.owlapi.modularity.ModuleType;import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;import uk.ac.manchester.owl.owlapi.tutorial.*;import uk.ac.manchester.owl.owlapi.tutorial.examples.*;import com.clarkparsia.owlapi.explanation.*;import com.clarkparsia.owlapi.explanation.io.*;import com.clarkparsia.owlapi.explanation.util.*;import com.clarkparsia.owlapi.modularity.locality.*;import de.uulm.ecs.ai.owlapi.krssparser.*;import de.uulm.ecs.ai.owlapi.krssrenderer.*;");
        out.println("public class GeneratedJUnitTest" + name + "{");
        return out;
    }

    @SuppressWarnings("resource")
    private static void visit(File root, File current,
            Map<String, PrintStream> outMap) throws ClassNotFoundException,
            FileNotFoundException {
        if (current == null) {
            for (File f : root.listFiles()) {
                visit(root, f, outMap);
            }
        } else if (current.isDirectory()) {
            for (File f : current.listFiles()) {
                visit(root, f, outMap);
            }
        } else if (current.getName().endsWith(".java")) {
            String id = current.getParentFile().getAbsolutePath()
                    .replace(root.getAbsolutePath(), "");
            PrintStream out = outMap.get(id);
            if (out == null) {
                out = initPrintStream(id);
                outMap.put(id, out);
            }
            String fullyQualifiedName = current.getAbsolutePath()
                    .replace(".java", "").replace(root.getAbsolutePath(), "")
                    .replace("/", ".").substring(1);
            Class<?> theClass = Class.forName(fullyQualifiedName);
            if (theClass.isInterface()) {
                out.println("@Test\npublic void shouldTestInterface"
                        + theClass.getSimpleName() + "()throws Exception{");
                out.println(theClass.getSimpleName() + " testSubject0 = mock("
                        + theClass.getSimpleName() + ".class);");
            } else {
                out.println("@Test\npublic void shouldTest"
                        + theClass.getSimpleName() + "()throws Exception{");
                int counter = 0;
                Constructor<?>[] constructors = theClass.getConstructors();
                if (constructors.length > 0) {
                    for (Constructor<?> c : constructors) {
                        out.print(theClass.getSimpleName() + " testSubject"
                                + counter++ + " = new "
                                + theClass.getSimpleName() + "(");
                        for (int i = 0; i < c.getParameterTypes().length; i++) {
                            out.print("mock("
                                    + c.getParameterTypes()[i].getSimpleName()
                                    + ".class)");
                            if (i < c.getParameterTypes().length - 1) {
                                out.print(",");
                            }
                        }
                        out.println(");");
                    }
                } else {
                    out.print(theClass.getSimpleName() + " testSubject0 = new "
                            + theClass.getSimpleName() + "(){};");
                }
            }
            int counter = 0;
            for (Method m : theClass.getMethods()) {
                if (!methodNamesToSkip.contains(m.getName())) {
                    if (!m.getReturnType().toString().equals("void")) {
                        out.print(m.getReturnType().getSimpleName() + " result"
                                + counter++ + " = ");
                    }
                    out.print("testSubject0." + m.getName() + "(");
                    for (int i = 0; i < m.getParameterTypes().length; i++) {
                        out.print("mock("
                                + m.getParameterTypes()[i].getSimpleName()
                                + ".class)");
                        if (i < m.getParameterTypes().length - 1) {
                            out.print(",");
                        }
                    }
                    out.println(");");
                }
            }
            out.println("}");
        }
    }

    private static Set<String> methodNamesToSkip = new HashSet<String>(
            Arrays.asList("wait", "equals", "toString()", "hashCode",
                    "getClass", "notify", "notifyAll"));
}
