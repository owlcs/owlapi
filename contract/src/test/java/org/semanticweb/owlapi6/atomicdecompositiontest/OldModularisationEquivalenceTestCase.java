package org.semanticweb.owlapi6.atomicdecompositiontest;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.atomicdecomposition.AtomicDecomposition;
import org.semanticweb.owlapi6.atomicdecomposition.AtomicDecompositionImpl;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLException;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.modularity.ModuleType;
import org.semanticweb.owlapi6.modularity.SyntacticLocalityModuleExtractor;

@RunWith(Parameterized.class)
public class OldModularisationEquivalenceTestCase extends TestBase {

    private static final String deg = "Degree";
    private static final String an = "Animal";
    private static final String uni = "University";
    private static final String hab = "Habitat";
    private static final String gender = "Gender";
    private static final String parent = "Parent";
    private static final String rf = "Rainforest";
    private static final String pers = "Person";
    private static final String male = "Male";
    private static final String mars = "Marsupials";
    private static final String female = "Female";
    private static final String taz = "TasmanianDevil";
    private static final String student = "Student";
    private static final String gstudent = "GraduateStudent";
    private static final String m3d = "MaleStudentWith3Daughters";
    private static final String kwp = "KoalaWithPhD";
    private static final String forest = "Forest";
    private static final String def = "DryEucalyptForest";
    private static final String quokka = "Quokka";
    private static final String koala = "Koala";
    private static String ns = "http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#";
    private static OWLDataFactory f = OWLManager.getOWLDataFactory();

    private static Set<OWLEntity> l(String... s) {
        return asSet(Stream.of(s).map(st -> f.getOWLClass(ns, st)), OWLEntity.class);
    }

    @Parameters(name = "{0}")
    public static List<Set<OWLEntity>> params() {
        List<Set<OWLEntity>> l = new ArrayList<>();
        l.add(l(pers));
        l.add(l(hab));
        l.add(l(forest));
        l.add(l(deg));
        l.add(l(parent));
        l.add(l(gstudent));
        l.add(l(rf));
        l.add(l(mars));
        l.add(l(kwp));
        l.add(l(taz));
        l.add(l(uni));
        l.add(l(an));
        l.add(l(male));
        l.add(l(m3d));
        l.add(l(female));
        l.add(l(koala));
        l.add(l(student));
        l.add(l(quokka));
        l.add(l(gender));
        l.add(l(def));
        l.add(l(gstudent, koala, kwp, m3d, pers, quokka, student));
        l.add(l(def, forest, hab, koala, kwp, quokka, rf, uni));
        l.add(l(def, forest, koala, kwp, quokka, rf));
        l.add(l(deg, koala, kwp, quokka));
        l.add(l(koala, kwp, m3d, parent, quokka));
        l.add(l(gstudent, koala, kwp, quokka));
        l.add(l(koala, kwp, quokka, rf));
        l.add(l(koala, kwp, mars, quokka, taz));
        l.add(l(koala, kwp, quokka));
        l.add(l(koala, kwp, quokka, taz));
        l.add(l(koala, kwp, quokka, uni));
        l.add(l(an, female, gstudent, koala, kwp, male, m3d, mars, parent, pers, quokka, student,
            taz));
        l.add(l(koala, kwp, male, m3d, quokka));
        l.add(l(koala, kwp, m3d, quokka));
        l.add(l(female, koala, kwp, quokka));
        l.add(l(koala, kwp, quokka));
        l.add(l(gstudent, koala, kwp, m3d, quokka, student));
        l.add(l(koala, kwp, quokka));
        l.add(l(gender, koala, kwp, quokka));
        l.add(l(def, koala, kwp, quokka));
        return l;
    }

    private final Set<OWLEntity> signature;

    public OldModularisationEquivalenceTestCase(Set<OWLEntity> l) {
        signature = l;
    }

    @Test
    @Ignore
    public void testModularizationWithAtomicDecompositionStar() throws OWLException {
        OWLOntology o = m.loadOntologyFromOntologyDocument(
            new StringDocumentSource(TestFiles.KOALA, new RDFXMLDocumentFormat()));
        List<OWLAxiom> module1 =
            asList(getADModule1(o, signature, ModuleType.STAR).stream().sorted());
        List<OWLAxiom> module2 = asList(getTraditionalModule(m, o, signature, ModuleType.STAR)
            .stream().filter(ax -> ax.isLogicalAxiom()).sorted());
        makeAssertion(module1, module2);
    }

    @Test
    public void testModularizationWithAtomicDecompositionTop() throws OWLException {
        OWLOntology o = m.loadOntologyFromOntologyDocument(
            new StringDocumentSource(TestFiles.KOALA, new RDFXMLDocumentFormat()));
        List<OWLAxiom> module1 =
            asList(getADModule1(o, signature, ModuleType.TOP).stream().sorted());
        List<OWLAxiom> module2 = asList(getTraditionalModule(m, o, signature, ModuleType.TOP)
            .stream().filter(ax -> ax.isLogicalAxiom()).sorted());
        makeAssertion(module1, module2);
    }

    @Test
    public void testModularizationWithAtomicDecompositionBottom() throws OWLException {
        OWLOntology o = m.loadOntologyFromOntologyDocument(
            new StringDocumentSource(TestFiles.KOALA, new RDFXMLDocumentFormat()));
        List<OWLAxiom> module1 =
            asList(getADModule1(o, signature, ModuleType.BOT).stream().sorted());
        List<OWLAxiom> module2 = asList(getTraditionalModule(m, o, signature, ModuleType.BOT)
            .stream().filter(ax -> ax.isLogicalAxiom()).sorted());
        makeAssertion(module1, module2);
    }

    protected void makeAssertion(List<OWLAxiom> module1, List<OWLAxiom> module2) {
        List<OWLAxiom> l = new ArrayList<>(module1);
        module1.removeAll(module2);
        module2.removeAll(l);
        String s1 = module1.toString().replace(ns, "");
        String s2 = module2.toString().replace(ns, "");
        if (!s1.equals(s2)) {
            System.out.println(
                "OldModularisationEquivalenceTestCase.testModularizationWithAtomicDecomposition() \n"
                    + s1 + "\n" + s2);
        }
        assertEquals(s1, s2);
    }

    protected Set<OWLAxiom> getTraditionalModule(OWLOntologyManager man, OWLOntology o,
        Set<OWLEntity> seedSig, ModuleType type) {
        SyntacticLocalityModuleExtractor sme = new SyntacticLocalityModuleExtractor(man, o, type);
        return sme.extract(seedSig);
    }

    // protected Set<OWLAxiom> getADModule(OWLOntology o, Set<OWLEntity> sig) {
    // OntologyBasedModularizer om = new OntologyBasedModularizer(o,
    // ModuleMethod.SYNTACTIC_STANDARD);
    // return new HashSet<>(om.getModule(sig.stream(), ModuleType.STAR));
    // }
    protected Set<OWLAxiom> getADModule1(OWLOntology o, Set<OWLEntity> sig, ModuleType mt) {
        AtomicDecomposition ad = new AtomicDecompositionImpl(o, mt, false);
        return asSet(ad.getModule(sig.stream(), false, mt));
    }
}
