package org.obolibrary.oboformat;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.RDFSComment;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.RDFSLabel;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubAnnotationPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_STRING;

import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OntologyConfigurator;

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public class LoadAnonymousTestCase extends TestBase {

    private static OWLLiteral literal(String s) {
        return Literal(s, XSD_STRING);
    }

    private static OWLAnnotationAssertionAxiom comment(OWLAnnotationSubject s,
        OWLAnnotationValue v) {
        return AnnotationAssertion(RDFSComment(), s, v);
    }

    private static OWLAnnotationAssertionAxiom label(OWLAnnotationSubject s, OWLAnnotationValue v) {
        return AnnotationAssertion(RDFSLabel(), s, v);
    }

    private static OWLAnnotationProperty ap(String s, String r) {
        return df.getOWLAnnotationProperty(s, r);
    }

    @Test
    public void shouldLoad() throws OWLOntologyCreationException {
        String input = "format-version: 1.2\n" + "date: 27:06:2013 17:08\n" + "saved-by: gkoutos\n"
            + "auto-generated-by: OBO-Edit 2.3\n"
            + "subsetdef: abnormal_slim \"Abnormal/normal slim\"\n"
            + "subsetdef: absent_slim \"Absent/present slim\"\n"
            + "subsetdef: attribute_slim \"Attribute slim\"\n"
            + "subsetdef: cell_quality \"cell_quality\"\n"
            + "subsetdef: disposition_slim \"Disposition slim\"\n"
            + "subsetdef: mpath_slim \"Pathology slim\"\n"
            + "subsetdef: prefix_slim \"prefix slim\"\n"
            + "subsetdef: relational_slim \"Relational slim: types of quality that require an additional entity in order to exist\"\n"
            + "subsetdef: scalar_slim \"Scalar slim\"\n"
            + "subsetdef: unit_group_slim \"unit group slim\"\n"
            + "subsetdef: unit_slim \"unit slim\"\n" + "subsetdef: value_slim \"Value slim\"\n"
            + "default-namespace: quality\n" + "namespace-id-rule: * UO:$sequence(7,0,9999999)$\n"
            + "remark: Filtered by Ancestor ID equals \"UO:0000000\"\n" + "ontology: uo\n"
            + "ontology: pato\n" + "ontology: pato\n" + "ontology: pato\n" + '\n' + "[Term]\n"
            + "id: UO:0000000\n" + "name: unit\n" + "namespace: unit.ontology\n"
            + "def: \"A unit of measurement is a standardized quantity of a physical quality.\" [Wikipedia:Wikipedia]\n"
            + "created_by: george gkoutos\n" + '\n' + "[Term]\n" + "id: UO:0000001\n"
            + "name: length unit\n" + "namespace: unit.ontology\n"
            + "def: \"A unit which is a standard measure of the distance between two points.\" [Wikipedia:Wikipedia]\n"
            + "subset: unit_group_slim\n" + "is_a: UO:0000000 ! unit\n"
            + "relationship: is_unit_of PATO:0001708 ! 1-D extent\n" + "created_by: george gkoutos";
        StringDocumentSource streamDocumentSource =
            new StringDocumentSource(input, new OBODocumentFormat());
        OntologyConfigurator loaderConfig = new OntologyConfigurator()
            .setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
        OWLOntology ontology =
            m1.loadOntologyFromOntologyDocument(streamDocumentSource, loaderConfig);
        OWLAnnotationProperty date = ap("http://www.geneontology.org/formats/oboInOwl#", "date");
        OWLAnnotationProperty mpathSlim = ap("http://purl.obolibrary.org/obo/uo#", "mpath_slim");
        OWLAnnotationProperty subsetProperty =
            ap("http://www.geneontology.org/formats/oboInOwl#", "SubsetProperty");
        OWLAnnotationProperty attributeSlim =
            ap("http://purl.obolibrary.org/obo/uo#", "attribute_slim");
        OWLAnnotationProperty hasOBONamespace =
            ap("http://www.geneontology.org/formats/oboInOwl#", "hasOBONamespace");
        OWLAnnotationProperty autogeneratedby =
            ap("http://www.geneontology.org/formats/oboInOwl#", "auto-generated-by");
        OWLAnnotationProperty hasDbXref =
            ap("http://www.geneontology.org/formats/oboInOwl#", "hasDbXref");
        OWLAnnotationProperty defaultnamespace =
            ap("http://www.geneontology.org/formats/oboInOwl#", "default-namespace");
        OWLAnnotationProperty hasOBOFormatVersion =
            ap("http://www.geneontology.org/formats/oboInOwl#", "hasOBOFormatVersion");
        OWLAnnotationProperty iao0000115 = ap("http://purl.obolibrary.org/obo/", "IAO_0000115");
        OWLAnnotationProperty namespaceIdRule =
            ap("http://www.geneontology.org/formats/oboInOwl#", "NamespaceIdRule");
        OWLAnnotationProperty createdBy =
            ap("http://www.geneontology.org/formats/oboInOwl#", "created_by");
        OWLAnnotationProperty inSubset =
            ap("http://www.geneontology.org/formats/oboInOwl#", "inSubset");
        OWLAnnotationProperty savedby =
            ap("http://www.geneontology.org/formats/oboInOwl#", "saved-by");
        OWLClass pato0001708 = Class(IRI("http://purl.obolibrary.org/obo/", "PATO_0001708"));
        OWLClass uo0 = Class(IRI("http://purl.obolibrary.org/obo/", "UO_0000000"));
        OWLClass uo1 = Class(IRI("http://purl.obolibrary.org/obo/", "UO_0000001"));
        OWLAnnotationProperty id = ap("http://www.geneontology.org/formats/oboInOwl#", "id");
        OWLAnnotationProperty abnormalSlim =
            ap("http://purl.obolibrary.org/obo/uo#", "abnormal_slim");
        OWLAnnotationProperty scalarSlim = ap("http://purl.obolibrary.org/obo/uo#", "scalar_slim");
        OWLLiteral literal = literal("Wikipedia:Wikipedia");
        OWLAnnotationProperty unitSlim = ap("http://purl.obolibrary.org/obo/uo#", "unit_slim");
        OWLAnnotationProperty absentSlim = ap("http://purl.obolibrary.org/obo/uo#", "absent_slim");
        OWLObjectProperty isUnitOf =
            ObjectProperty(IRI("http://purl.obolibrary.org/obo/uo#", "is_unit_of"));
        OWLAnnotationProperty cellQuality =
            ap("http://purl.obolibrary.org/obo/uo#", "cell_quality");
        OWLAnnotationProperty unitGroupSlim =
            ap("http://purl.obolibrary.org/obo/uo#", "unit_group_slim");
        OWLAnnotationProperty valueSlim = ap("http://purl.obolibrary.org/obo/uo#", "value_slim");
        OWLAnnotationProperty prefixSlim = ap("http://purl.obolibrary.org/obo/uo#", "prefix_slim");
        OWLAnnotationProperty dispositionSlim =
            ap("http://purl.obolibrary.org/obo/uo#", "disposition_slim");
        OWLAnnotationProperty relationalSlim =
            ap("http://purl.obolibrary.org/obo/uo#", "relational_slim");
        Set<OWLAxiom> expected = Sets.newHashSet(Declaration(date), Declaration(autogeneratedby),
            Declaration(hasDbXref), Declaration(defaultnamespace), Declaration(subsetProperty),
            Declaration(hasOBOFormatVersion), Declaration(iao0000115), Declaration(namespaceIdRule),
            Declaration(createdBy), Declaration(inSubset), Declaration(savedby),
            Declaration(pato0001708), Declaration(uo0), Declaration(RDFSComment()),
            Declaration(RDFSLabel()), Declaration(hasOBONamespace), Declaration(uo1),
            Declaration(id), SubAnnotationPropertyOf(mpathSlim, subsetProperty),
            AnnotationAssertion(hasOBONamespace, uo1.getIRI(), literal("unit.ontology")),
            comment(attributeSlim.getIRI(), literal("Attribute slim")),
            label(iao0000115.getIRI(), literal("definition")),
            AnnotationAssertion(hasOBONamespace, uo0.getIRI(), literal("unit.ontology")),
            SubAnnotationPropertyOf(unitSlim, subsetProperty),
            comment(valueSlim.getIRI(), literal("Value slim")),
            SubAnnotationPropertyOf(absentSlim, subsetProperty),
            SubAnnotationPropertyOf(abnormalSlim, subsetProperty),
            label(uo1.getIRI(), literal("length unit")),
            label(hasOBOFormatVersion.getIRI(), literal("has_obo_format_version")),
            label(namespaceIdRule.getIRI(), literal("namespace-id-rule")),
            SubClassOf(uo1, ObjectSomeValuesFrom(isUnitOf, pato0001708)),
            SubAnnotationPropertyOf(cellQuality, subsetProperty),
            comment(relationalSlim.getIRI(), literal(
                "Relational slim: types of quality that require an additional entity in order to exist")),
            SubAnnotationPropertyOf(prefixSlim, subsetProperty),
            SubAnnotationPropertyOf(scalarSlim, subsetProperty),
            comment(scalarSlim.getIRI(), literal("Scalar slim")),
            comment(abnormalSlim.getIRI(), literal("Abnormal/normal slim")),
            SubAnnotationPropertyOf(attributeSlim, subsetProperty),
            label(uo0.getIRI(), literal("unit")),
            SubAnnotationPropertyOf(dispositionSlim, subsetProperty),
            comment(unitSlim.getIRI(), literal("unit slim")),
            SubAnnotationPropertyOf(relationalSlim, subsetProperty),
            AnnotationAssertion(id, uo1.getIRI(), literal("UO:0000001")),
            comment(mpathSlim.getIRI(), literal("Pathology slim")),
            AnnotationAssertion(createdBy, uo1.getIRI(), literal("george gkoutos")),
            label(hasDbXref.getIRI(), literal("database_cross_reference")), SubClassOf(uo1, uo0),
            label(hasOBONamespace.getIRI(), literal("has_obo_namespace")),
            AnnotationAssertion(id, uo0.getIRI(), literal("UO:0000000")),
            AnnotationAssertion(createdBy, uo0.getIRI(), literal("george gkoutos")),
            comment(prefixSlim.getIRI(), literal("prefix slim")),
            comment(cellQuality.getIRI(), literal("cell_quality")),
            comment(absentSlim.getIRI(), literal("Absent/present slim")),
            label(subsetProperty.getIRI(), literal("subset_property")),
            SubAnnotationPropertyOf(unitGroupSlim, subsetProperty),
            comment(unitGroupSlim.getIRI(), literal("unit group slim")),
            comment(dispositionSlim.getIRI(), literal("Disposition slim")),
            label(inSubset.getIRI(), literal("in_subset")),
            SubAnnotationPropertyOf(valueSlim, subsetProperty),
            AnnotationAssertion(inSubset, uo1.getIRI(), unitGroupSlim.getIRI()),
            df.getOWLAnnotationAssertionAxiom(iao0000115, uo0.getIRI(),
                literal("A unit of measurement is a standardized quantity of a physical quality."),
                singleton(Annotation(hasDbXref, literal))),
            df.getOWLAnnotationAssertionAxiom(iao0000115, uo1.getIRI(),
                literal("A unit which is a standard measure of the distance between two points."),
                singleton(Annotation(hasDbXref, literal))));
        assertEquals(expected, asUnorderedSet(ontology.axioms()));
    }
}
