package org.semanticweb.owlapi6.atomicdecompositiontest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.atomicdecomposition.AtomicDecomposition;
import org.semanticweb.owlapi6.atomicdecomposition.AtomicDecompositionImpl;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.modularity.ModuleType;
import org.semanticweb.owlapi6.modularity.SyntacticLocalityModuleExtractor;

class OldModularisationEquivalenceTestCase extends TestBase {

    static final String DRY_EUCALYPT_FOREST = "DryEucalyptForest";
    static final String QUOKKA = "Quokka";
    static final String STUDENT = "Student";
    static final String KOALA = "Koala";
    static final String STUDENT_WITH3 = "MaleStudentWith3Daughters";
    static final String KOALA_WITH_PHD = "KoalaWithPhD";
    static final String TASMANIAN_DEVIL = "TasmanianDevil";
    static final String GRADUATE_STUDENT = "GraduateStudent";
    static final String RAINFOREST = "Rainforest";
    static String ns = "http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#";

    static final String deg = "Degree";
    static final String an = "Animal";
    static final String uni = "University";
    static final String hab = "Habitat";
    static final String gender = "Gender";
    static final String parent = "Parent";
    static final String pers = "Person";
    static final String male = "Male";
    static final String mars = "Marsupials";
    static final String female = "Female";
    static final String forest = "Forest";

    static Set<OWLEntity> eSet(String... className) {
        return asSet(Stream.of(className).map(st -> Class(iri(ns, st))), OWLEntity.class);
    }

    static Stream<Set<OWLEntity>> params() {
        return Stream.of(eSet(pers), eSet(hab), eSet(forest), eSet(deg), eSet(parent),
            eSet(GRADUATE_STUDENT), eSet(RAINFOREST), eSet(mars), eSet(KOALA_WITH_PHD),
            eSet(TASMANIAN_DEVIL), eSet(uni), eSet(an), eSet(male), eSet(STUDENT_WITH3),
            eSet(female), eSet(KOALA), eSet(STUDENT), eSet(QUOKKA), eSet(gender),
            eSet(DRY_EUCALYPT_FOREST),
            eSet(GRADUATE_STUDENT, KOALA, KOALA_WITH_PHD, STUDENT_WITH3, pers, QUOKKA, STUDENT),
            eSet(DRY_EUCALYPT_FOREST, forest, hab, KOALA, KOALA_WITH_PHD, QUOKKA, RAINFOREST, uni),
            eSet(DRY_EUCALYPT_FOREST, forest, KOALA, KOALA_WITH_PHD, QUOKKA, RAINFOREST),
            eSet(deg, KOALA, KOALA_WITH_PHD, QUOKKA),
            eSet(KOALA, KOALA_WITH_PHD, STUDENT_WITH3, parent, QUOKKA),
            eSet(GRADUATE_STUDENT, KOALA, KOALA_WITH_PHD, QUOKKA),
            eSet(KOALA, KOALA_WITH_PHD, QUOKKA, RAINFOREST),
            eSet(KOALA, KOALA_WITH_PHD, mars, QUOKKA, TASMANIAN_DEVIL),
            eSet(KOALA, KOALA_WITH_PHD, QUOKKA),
            eSet(KOALA, KOALA_WITH_PHD, QUOKKA, TASMANIAN_DEVIL),
            eSet(KOALA, KOALA_WITH_PHD, QUOKKA, uni),
            eSet(an, female, GRADUATE_STUDENT, KOALA, KOALA_WITH_PHD, male, STUDENT_WITH3, mars,
                parent, pers, QUOKKA, STUDENT, TASMANIAN_DEVIL),
            eSet(KOALA, KOALA_WITH_PHD, male, STUDENT_WITH3, QUOKKA),
            eSet(KOALA, KOALA_WITH_PHD, STUDENT_WITH3, QUOKKA),
            eSet(female, KOALA, KOALA_WITH_PHD, QUOKKA), eSet(KOALA, KOALA_WITH_PHD, QUOKKA),
            eSet(GRADUATE_STUDENT, KOALA, KOALA_WITH_PHD, STUDENT_WITH3, QUOKKA, STUDENT),
            eSet(KOALA, KOALA_WITH_PHD, QUOKKA), eSet(gender, KOALA, KOALA_WITH_PHD, QUOKKA),
            eSet(DRY_EUCALYPT_FOREST, KOALA, KOALA_WITH_PHD, QUOKKA));
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
