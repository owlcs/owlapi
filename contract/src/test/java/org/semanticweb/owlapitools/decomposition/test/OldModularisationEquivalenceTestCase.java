package org.semanticweb.owlapitools.decomposition.test;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.atomicdecomposition.AtomicDecomposition;
import uk.ac.manchester.cs.atomicdecomposition.AtomicDecompositionImpl;
import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;

@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class OldModularisationEquivalenceTestCase extends TestBase {

    public static final String KOALA = "<?xml version=\"1.0\"?>\n"
                    + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#\" xml:base=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl\">\n"
                    + "  <owl:Ontology rdf:about=\"\"/>\n"
                    + "  <owl:Class rdf:ID=\"Female\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:FunctionalProperty rdf:about=\"#hasGender\"/></owl:onProperty><owl:hasValue><Gender rdf:ID=\"female\"/></owl:hasValue></owl:Restriction></owl:equivalentClass></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Marsupials\"><owl:disjointWith><owl:Class rdf:about=\"#Person\"/></owl:disjointWith><rdfs:subClassOf><owl:Class rdf:about=\"#Animal\"/></rdfs:subClassOf></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Student\"><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"#Person\"/><owl:Restriction><owl:onProperty><owl:FunctionalProperty rdf:about=\"#isHardWorking\"/></owl:onProperty><owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</owl:hasValue></owl:Restriction><owl:Restriction><owl:someValuesFrom><owl:Class rdf:about=\"#University\"/></owl:someValuesFrom><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasHabitat\"/></owl:onProperty></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"KoalaWithPhD\"><owl:versionInfo>1.2</owl:versionInfo><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:hasValue><Degree rdf:ID=\"PhD\"/></owl:hasValue><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasDegree\"/></owl:onProperty></owl:Restriction><owl:Class rdf:about=\"#Koala\"/></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"University\"><rdfs:subClassOf><owl:Class rdf:ID=\"Habitat\"/></rdfs:subClassOf></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Koala\"><rdfs:subClassOf><owl:Restriction><owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">false</owl:hasValue><owl:onProperty><owl:FunctionalProperty rdf:about=\"#isHardWorking\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:someValuesFrom><owl:Class rdf:about=\"#DryEucalyptForest\"/></owl:someValuesFrom><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasHabitat\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf rdf:resource=\"#Marsupials\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Animal\"><rdfs:seeAlso>Male</rdfs:seeAlso><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasHabitat\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:cardinality><owl:onProperty><owl:FunctionalProperty rdf:about=\"#hasGender\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><owl:versionInfo>1.1</owl:versionInfo></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Forest\"><rdfs:subClassOf rdf:resource=\"#Habitat\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Rainforest\"><rdfs:subClassOf rdf:resource=\"#Forest\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"GraduateStudent\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasDegree\"/></owl:onProperty><owl:someValuesFrom><owl:Class><owl:oneOf rdf:parseType=\"Collection\"><Degree rdf:ID=\"BA\"/><Degree rdf:ID=\"BS\"/></owl:oneOf></owl:Class></owl:someValuesFrom></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf rdf:resource=\"#Student\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Parent\"><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"#Animal\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasChildren\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass><rdfs:subClassOf rdf:resource=\"#Animal\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"DryEucalyptForest\"><rdfs:subClassOf rdf:resource=\"#Forest\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Quokka\"><rdfs:subClassOf><owl:Restriction><owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</owl:hasValue><owl:onProperty><owl:FunctionalProperty rdf:about=\"#isHardWorking\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf rdf:resource=\"#Marsupials\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"TasmanianDevil\"><rdfs:subClassOf rdf:resource=\"#Marsupials\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"MaleStudentWith3Daughters\"><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"#Student\"/><owl:Restriction><owl:onProperty><owl:FunctionalProperty rdf:about=\"#hasGender\"/></owl:onProperty><owl:hasValue><Gender rdf:ID=\"male\"/></owl:hasValue></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasChildren\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">3</owl:cardinality></owl:Restriction><owl:Restriction><owl:allValuesFrom rdf:resource=\"#Female\"/><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasChildren\"/></owl:onProperty></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Degree\"/>\n  <owl:Class rdf:ID=\"Gender\"/>\n"
                    + "  <owl:Class rdf:ID=\"Male\"><owl:equivalentClass><owl:Restriction><owl:hasValue rdf:resource=\"#male\"/><owl:onProperty><owl:FunctionalProperty rdf:about=\"#hasGender\"/></owl:onProperty></owl:Restriction></owl:equivalentClass></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Person\"><rdfs:subClassOf rdf:resource=\"#Animal\"/><owl:disjointWith rdf:resource=\"#Marsupials\"/></owl:Class>\n"
                    + "  <owl:ObjectProperty rdf:ID=\"hasHabitat\"><rdfs:range rdf:resource=\"#Habitat\"/><rdfs:domain rdf:resource=\"#Animal\"/></owl:ObjectProperty>\n"
                    + "  <owl:ObjectProperty rdf:ID=\"hasDegree\"><rdfs:domain rdf:resource=\"#Person\"/><rdfs:range rdf:resource=\"#Degree\"/></owl:ObjectProperty>\n"
                    + "  <owl:ObjectProperty rdf:ID=\"hasChildren\"><rdfs:range rdf:resource=\"#Animal\"/><rdfs:domain rdf:resource=\"#Animal\"/></owl:ObjectProperty>\n"
                    + "  <owl:FunctionalProperty rdf:ID=\"hasGender\"><rdfs:range rdf:resource=\"#Gender\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#ObjectProperty\"/><rdfs:domain rdf:resource=\"#Animal\"/></owl:FunctionalProperty>\n"
                    + "  <owl:FunctionalProperty rdf:ID=\"isHardWorking\"><rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#boolean\"/><rdfs:domain rdf:resource=\"#Person\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#DatatypeProperty\"/></owl:FunctionalProperty>\n"
                    + "  <Degree rdf:ID=\"MA\"/>\n</rdf:RDF>";
    private static String ns = "http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#";
    private static OWLDataFactory f = OWLManager.getOWLDataFactory();

    private static Set<OWLEntity> l(String... s) {
        return asSet(Stream.of(s).map(st -> f.getOWLClass(ns, st)), OWLEntity.class);
    }

    @Parameters(name = "{0}")
    public static List<Set<OWLEntity>> params() {
        List<Set<OWLEntity>> l = new ArrayList<>();
        l.add(l("Person"));
        l.add(l("Habitat"));
        l.add(l("Forest"));
        l.add(l("Degree"));
        l.add(l("Parent"));
        l.add(l("GraduateStudent"));
        l.add(l("Rainforest"));
        l.add(l("Marsupials"));
        l.add(l("KoalaWithPhD"));
        l.add(l("TasmanianDevil"));
        l.add(l("University"));
        l.add(l("Animal"));
        l.add(l("Male"));
        l.add(l("MaleStudentWith3Daughters"));
        l.add(l("Female"));
        l.add(l("Koala"));
        l.add(l("Student"));
        l.add(l("Quokka"));
        l.add(l("Gender"));
        l.add(l("DryEucalyptForest"));
        l.add(l("GraduateStudent", "Koala", "KoalaWithPhD", "MaleStudentWith3Daughters", "Person",
                        "Quokka", "Student"));
        l.add(l("DryEucalyptForest", "Forest", "Habitat", "Koala", "KoalaWithPhD", "Quokka",
                        "Rainforest", "University"));
        l.add(l("DryEucalyptForest", "Forest", "Koala", "KoalaWithPhD", "Quokka", "Rainforest"));
        l.add(l("Degree", "Koala", "KoalaWithPhD", "Quokka"));
        l.add(l("Koala", "KoalaWithPhD", "MaleStudentWith3Daughters", "Parent", "Quokka"));
        l.add(l("GraduateStudent", "Koala", "KoalaWithPhD", "Quokka"));
        l.add(l("Koala", "KoalaWithPhD", "Quokka", "Rainforest"));
        l.add(l("Koala", "KoalaWithPhD", "Marsupials", "Quokka", "TasmanianDevil"));
        l.add(l("Koala", "KoalaWithPhD", "Quokka"));
        l.add(l("Koala", "KoalaWithPhD", "Quokka", "TasmanianDevil"));
        l.add(l("Koala", "KoalaWithPhD", "Quokka", "University"));
        l.add(l("Animal", "Female", "GraduateStudent", "Koala", "KoalaWithPhD", "Male",
                        "MaleStudentWith3Daughters", "Marsupials", "Parent", "Person", "Quokka",
                        "Student", "TasmanianDevil"));
        l.add(l("Koala", "KoalaWithPhD", "Male", "MaleStudentWith3Daughters", "Quokka"));
        l.add(l("Koala", "KoalaWithPhD", "MaleStudentWith3Daughters", "Quokka"));
        l.add(l("Female", "Koala", "KoalaWithPhD", "Quokka"));
        l.add(l("Koala", "KoalaWithPhD", "Quokka"));
        l.add(l("GraduateStudent", "Koala", "KoalaWithPhD", "MaleStudentWith3Daughters", "Quokka",
                        "Student"));
        l.add(l("Koala", "KoalaWithPhD", "Quokka"));
        l.add(l("Gender", "Koala", "KoalaWithPhD", "Quokka"));
        l.add(l("DryEucalyptForest", "Koala", "KoalaWithPhD", "Quokka"));
        return l;
    }

    private Set<OWLEntity> signature;

    public OldModularisationEquivalenceTestCase(Set<OWLEntity> l) {
        signature = l;
    }

    @Test
    @Ignore
    public void testModularizationWithAtomicDecompositionStar() throws OWLException {
        OWLOntology o = m.loadOntologyFromOntologyDocument(
                        new StringDocumentSource(KOALA, new RDFXMLDocumentFormat()));
        List<OWLAxiom> module1 =
                        asList(getADModule1(o, signature, ModuleType.STAR).stream().sorted());
        List<OWLAxiom> module2 = asList(getTraditionalModule(m, o, signature, ModuleType.STAR)
                        .stream().filter(ax -> ax.isLogicalAxiom()).sorted());
        makeAssertion(module1, module2);
    }

    @Test
    public void testModularizationWithAtomicDecompositionTop() throws OWLException {
        OWLOntology o = m.loadOntologyFromOntologyDocument(
                        new StringDocumentSource(KOALA, new RDFXMLDocumentFormat()));
        List<OWLAxiom> module1 =
                        asList(getADModule1(o, signature, ModuleType.TOP).stream().sorted());
        List<OWLAxiom> module2 = asList(getTraditionalModule(m, o, signature, ModuleType.TOP)
                        .stream().filter(ax -> ax.isLogicalAxiom()).sorted());
        makeAssertion(module1, module2);
    }

    @Test
    public void testModularizationWithAtomicDecompositionBottom() throws OWLException {
        OWLOntology o = m.loadOntologyFromOntologyDocument(
                        new StringDocumentSource(KOALA, new RDFXMLDocumentFormat()));
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
        AtomicDecomposition ad = new AtomicDecompositionImpl(o);
        return asSet(ad.getModule(sig.stream(), false, mt));
    }
}
