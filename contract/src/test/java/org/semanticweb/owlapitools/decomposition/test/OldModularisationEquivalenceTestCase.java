package org.semanticweb.owlapitools.decomposition.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.atomicdecomposition.AtomicDecomposition;
import uk.ac.manchester.cs.atomicdecomposition.AtomicDecompositionImpl;
import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;

class OldModularisationEquivalenceTestCase extends TestBase {

    static final String DRY_EUCALYPT_FOREST = "DryEucalyptForest";
    static final String QUOKKA = "Quokka";
    static final String STUDENT = "Student";
    static final String KOALA = "Koala";
    static final String MALE_STUDENT_WITH3_DAUGHTERS = "MaleStudentWith3Daughters";
    static final String KOALA_WITH_PHD = "KoalaWithPhD";
    static final String TASMANIAN_DEVIL = "TasmanianDevil";
    static final String GRADUATE_STUDENT = "GraduateStudent";
    static final String RAINFOREST = "Rainforest";
    static String ns = "http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#";

    static Set<OWLEntity> eSet(String... className) {
        return asSet(Stream.of(className).map(st -> Class(iri(ns, st))), OWLEntity.class);
    }

    static List<Set<OWLEntity>> params() {
        List<Set<OWLEntity>> list = new ArrayList<>();
        list.add(eSet("Person"));
        list.add(eSet("Habitat"));
        list.add(eSet("Forest"));
        list.add(eSet("Degree"));
        list.add(eSet("Parent"));
        list.add(eSet(GRADUATE_STUDENT));
        list.add(eSet(RAINFOREST));
        list.add(eSet("Marsupials"));
        list.add(eSet(KOALA_WITH_PHD));
        list.add(eSet(TASMANIAN_DEVIL));
        list.add(eSet("University"));
        list.add(eSet("Animal"));
        list.add(eSet("Male"));
        list.add(eSet(MALE_STUDENT_WITH3_DAUGHTERS));
        list.add(eSet("Female"));
        list.add(eSet(KOALA));
        list.add(eSet(STUDENT));
        list.add(eSet(QUOKKA));
        list.add(eSet("Gender"));
        list.add(eSet(DRY_EUCALYPT_FOREST));
        list.add(eSet(GRADUATE_STUDENT, KOALA, KOALA_WITH_PHD, MALE_STUDENT_WITH3_DAUGHTERS,
            "Person", QUOKKA, STUDENT));
        list.add(eSet(DRY_EUCALYPT_FOREST, "Forest", "Habitat", KOALA, KOALA_WITH_PHD, QUOKKA,
            RAINFOREST, "University"));
        list.add(eSet(DRY_EUCALYPT_FOREST, "Forest", KOALA, KOALA_WITH_PHD, QUOKKA, RAINFOREST));
        list.add(eSet("Degree", KOALA, KOALA_WITH_PHD, QUOKKA));
        list.add(eSet(KOALA, KOALA_WITH_PHD, MALE_STUDENT_WITH3_DAUGHTERS, "Parent", QUOKKA));
        list.add(eSet(GRADUATE_STUDENT, KOALA, KOALA_WITH_PHD, QUOKKA));
        list.add(eSet(KOALA, KOALA_WITH_PHD, QUOKKA, RAINFOREST));
        list.add(eSet(KOALA, KOALA_WITH_PHD, "Marsupials", QUOKKA, TASMANIAN_DEVIL));
        list.add(eSet(KOALA, KOALA_WITH_PHD, QUOKKA));
        list.add(eSet(KOALA, KOALA_WITH_PHD, QUOKKA, TASMANIAN_DEVIL));
        list.add(eSet(KOALA, KOALA_WITH_PHD, QUOKKA, "University"));
        list.add(eSet("Animal", "Female", GRADUATE_STUDENT, KOALA, KOALA_WITH_PHD, "Male",
            MALE_STUDENT_WITH3_DAUGHTERS, "Marsupials", "Parent", "Person", QUOKKA, STUDENT,
            TASMANIAN_DEVIL));
        list.add(eSet(KOALA, KOALA_WITH_PHD, "Male", MALE_STUDENT_WITH3_DAUGHTERS, QUOKKA));
        list.add(eSet(KOALA, KOALA_WITH_PHD, MALE_STUDENT_WITH3_DAUGHTERS, QUOKKA));
        list.add(eSet("Female", KOALA, KOALA_WITH_PHD, QUOKKA));
        list.add(eSet(KOALA, KOALA_WITH_PHD, QUOKKA));
        list.add(eSet(GRADUATE_STUDENT, KOALA, KOALA_WITH_PHD, MALE_STUDENT_WITH3_DAUGHTERS, QUOKKA,
            STUDENT));
        list.add(eSet(KOALA, KOALA_WITH_PHD, QUOKKA));
        list.add(eSet("Gender", KOALA, KOALA_WITH_PHD, QUOKKA));
        list.add(eSet(DRY_EUCALYPT_FOREST, KOALA, KOALA_WITH_PHD, QUOKKA));
        return list;
    }

    @ParameterizedTest
    @MethodSource("params")
    @Disabled
    void testModularizationWithAtomicDecompositionStar(Set<OWLEntity> signature) {
        OWLOntology o = loadFrom(TestFiles.KOALA, new RDFXMLDocumentFormat());
        List<OWLAxiom> module1 =
            asList(getADModule1(o, signature, ModuleType.STAR).stream().sorted());
        List<OWLAxiom> module2 = asList(getTraditionalModule(m, o, signature, ModuleType.STAR)
            .stream().filter(ax -> ax.isLogicalAxiom()).sorted());
        makeAssertion(module1, module2);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testModularizationWithAtomicDecompositionTop(Set<OWLEntity> signature) {
        OWLOntology o = loadFrom(TestFiles.KOALA, new RDFXMLDocumentFormat());
        List<OWLAxiom> module1 =
            asList(getADModule1(o, signature, ModuleType.TOP).stream().sorted());
        List<OWLAxiom> module2 = asList(getTraditionalModule(m, o, signature, ModuleType.TOP)
            .stream().filter(ax -> ax.isLogicalAxiom()).sorted());
        makeAssertion(module1, module2);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testModularizationWithAtomicDecompositionBottom(Set<OWLEntity> signature) {
        OWLOntology o = loadFrom(TestFiles.KOALA, new RDFXMLDocumentFormat());
        List<OWLAxiom> module1 =
            asList(getADModule1(o, signature, ModuleType.BOT).stream().sorted());
        List<OWLAxiom> module2 = asList(getTraditionalModule(m, o, signature, ModuleType.BOT)
            .stream().filter(ax -> ax.isLogicalAxiom()).sorted());
        makeAssertion(module1, module2);
    }

    protected void makeAssertion(List<OWLAxiom> module1, List<OWLAxiom> module2) {
        List<OWLAxiom> list = new ArrayList<>(module1);
        module1.removeAll(module2);
        module2.removeAll(list);
        String s1 = module1.toString().replace(ns, "");
        String s2 = module2.toString().replace(ns, "");
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
