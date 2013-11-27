package org.obolibrary.obo2owl;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class LoadAnonymousTestCase {
    @Test
    public void shouldLoad() throws OWLOntologyCreationException {
        OWLOntologyManager rootOntologyManager = OWLManager.createOWLOntologyManager();
        String input = "format-version: 1.2\n"
                + "date: 27:06:2013 17:08\n"
                + "saved-by: gkoutos\n"
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
                + "subsetdef: unit_slim \"unit slim\"\n"
                + "subsetdef: value_slim \"Value slim\"\n"
                + "default-namespace: quality\n"
                + "namespace-id-rule: * UO:$sequence(7,0,9999999)$\n"
                + "remark: Filtered by Ancestor ID equals \"UO:0000000\"\n"
                + "ontology: uo\n"
                + "ontology: pato\n"
                + "ontology: pato\n"
                + "ontology: pato\n"
                + "\n"
                + "[Term]\n"
                + "id: UO:0000000\n"
                + "name: unit\n"
                + "namespace: unit.ontology\n"
                + "def: \"A unit of measurement is a standardized quantity of a physical quality.\" [Wikipedia:Wikipedia]\n"
                + "created_by: george gkoutos\n"
                + "\n"
                + "[Term]\n"
                + "id: UO:0000001\n"
                + "name: length unit\n"
                + "namespace: unit.ontology\n"
                + "def: \"A unit which is a standard measure of the distance between two points.\" [Wikipedia:Wikipedia]\n"
                + "subset: unit_group_slim\n" + "is_a: UO:0000000 ! unit\n"
                + "relationship: is_unit_of PATO:0001708 ! 1-D extent\n"
                + "created_by: george gkoutos";
        StringDocumentSource streamDocumentSource = new StringDocumentSource(input);
        OWLOntologyLoaderConfiguration loaderConfig = new OWLOntologyLoaderConfiguration()
                .setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
        OWLOntology ontology = rootOntologyManager.loadOntologyFromOntologyDocument(
                streamDocumentSource, loaderConfig);
        System.out.println("LoadAnonymousTestCase.shouldLoad() " + ontology.getAxioms());
    }
}
