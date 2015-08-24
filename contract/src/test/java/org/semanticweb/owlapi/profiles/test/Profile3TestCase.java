package org.semanticweb.owlapi.profiles.test;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class Profile3TestCase extends ProfileBase {

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D503() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<first:Test />"
            + "<owl:Class rdf:about='urn:test#plus1'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus1'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus2'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus2'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus3'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus3'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus4'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus4'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus5'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus5'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus6'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus6'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus7'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus7'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus8'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus8'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus9'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus9'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#minus9'/><rdf:Description rdf:about='urn:test#minus8'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#plus2'/><rdf:Description rdf:about='urn:test#minus8'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus4'/><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#minus5'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus2'/><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#minus1'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus1'/><rdf:Description rdf:about='urn:test#plus5'/><rdf:Description rdf:about='urn:test#plus8'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus8'/><rdf:Description rdf:about='urn:test#minus6'/><rdf:Description rdf:about='urn:test#minus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus3'/><rdf:Description rdf:about='urn:test#minus8'/><rdf:Description rdf:about='urn:test#plus7'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus3'/><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#plus8'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus4'/><rdf:Description rdf:about='urn:test#minus6'/><rdf:Description rdf:about='urn:test#plus8'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#plus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#minus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus5'/><rdf:Description rdf:about='urn:test#minus2'/><rdf:Description rdf:about='urn:test#plus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus5'/><rdf:Description rdf:about='urn:test#plus8'/><rdf:Description rdf:about='urn:test#plus2'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus2'/><rdf:Description rdf:about='urn:test#minus7'/><rdf:Description rdf:about='urn:test#minus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus6'/><rdf:Description rdf:about='urn:test#minus8'/><rdf:Description rdf:about='urn:test#minus5'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus2'/><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#minus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus9'/><rdf:Description rdf:about='urn:test#minus1'/><rdf:Description rdf:about='urn:test#minus2'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#minus6'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#plus9'/><rdf:Description rdf:about='urn:test#minus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus8'/><rdf:Description rdf:about='urn:test#minus9'/><rdf:Description rdf:about='urn:test#minus2'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus9'/><rdf:Description rdf:about='urn:test#minus8'/><rdf:Description rdf:about='urn:test#plus2'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus5'/><rdf:Description rdf:about='urn:test#plus8'/><rdf:Description rdf:about='urn:test#plus4'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus7'/><rdf:Description rdf:about='urn:test#plus2'/><rdf:Description rdf:about='urn:test#plus5'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus1'/><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#minus4'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#minus8'/><rdf:Description rdf:about='urn:test#plus4'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus3'/><rdf:Description rdf:about='urn:test#plus2'/><rdf:Description rdf:about='urn:test#minus6'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#minus2'/><rdf:Description rdf:about='urn:test#minus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#minus2'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus7'/><rdf:Description rdf:about='urn:test#plus8'/><rdf:Description rdf:about='urn:test#plus4'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#minus7'/><rdf:Description rdf:about='urn:test#minus5'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus5'/><rdf:Description rdf:about='urn:test#plus4'/><rdf:Description rdf:about='urn:test#minus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#minus1'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus1'/><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#minus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#plus2'/><rdf:Description rdf:about='urn:test#plus6'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus8'/><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#minus7'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus1'/><rdf:Description rdf:about='urn:test#plus9'/><rdf:Description rdf:about='urn:test#minus8'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus5'/><rdf:Description rdf:about='urn:test#minus9'/><rdf:Description rdf:about='urn:test#minus7'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus7'/><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#minus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#minus1'/><rdf:Description rdf:about='urn:test#minus2'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#plus4'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#minus7'/><rdf:Description rdf:about='urn:test#plus5'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus8'/><rdf:Description rdf:about='urn:test#minus6'/><rdf:Description rdf:about='urn:test#plus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus5'/><rdf:Description rdf:about='urn:test#minus2'/><rdf:Description rdf:about='urn:test#plus6'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus8'/><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#minus5'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus2'/><rdf:Description rdf:about='urn:test#minus4'/><rdf:Description rdf:about='urn:test#minus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D504() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<first:Test />"
            + "<owl:Class rdf:about='urn:test#plus1'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus1'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus2'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus2'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus3'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus3'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus4'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus4'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus5'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus5'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus6'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus6'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus7'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus7'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus8'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus8'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#plus9'><owl:disjointWith>"
            + "<owl:Class rdf:about='urn:test#minus9'/></owl:disjointWith></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#plus2'/><rdf:Description rdf:about='urn:test#minus4'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus3'/><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#minus4'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus9'/><rdf:Description rdf:about='urn:test#minus4'/><rdf:Description rdf:about='urn:test#plus5'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus4'/><rdf:Description rdf:about='urn:test#minus6'/><rdf:Description rdf:about='urn:test#minus2'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus2'/><rdf:Description rdf:about='urn:test#minus3'/><rdf:Description rdf:about='urn:test#plus1'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus3'/><rdf:Description rdf:about='urn:test#plus8'/><rdf:Description rdf:about='urn:test#plus7'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus8'/><rdf:Description rdf:about='urn:test#minus2'/><rdf:Description rdf:about='urn:test#plus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus7'/><rdf:Description rdf:about='urn:test#minus6'/><rdf:Description rdf:about='urn:test#plus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#minus4'/><rdf:Description rdf:about='urn:test#minus6'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus8'/><rdf:Description rdf:about='urn:test#minus5'/><rdf:Description rdf:about='urn:test#minus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus4'/><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#plus6'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus2'/><rdf:Description rdf:about='urn:test#minus1'/><rdf:Description rdf:about='urn:test#plus4'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus3'/><rdf:Description rdf:about='urn:test#plus8'/><rdf:Description rdf:about='urn:test#plus2'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#minus2'/><rdf:Description rdf:about='urn:test#plus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#minus9'/><rdf:Description rdf:about='urn:test#minus2'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus2'/><rdf:Description rdf:about='urn:test#minus5'/><rdf:Description rdf:about='urn:test#minus7'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus5'/><rdf:Description rdf:about='urn:test#plus2'/><rdf:Description rdf:about='urn:test#plus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#minus2'/><rdf:Description rdf:about='urn:test#minus7'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus9'/><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#minus2'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#plus4'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus4'/><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#plus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus2'/><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#minus6'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus7'/><rdf:Description rdf:about='urn:test#minus4'/><rdf:Description rdf:about='urn:test#plus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus5'/><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#minus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus4'/><rdf:Description rdf:about='urn:test#plus9'/><rdf:Description rdf:about='urn:test#minus8'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus4'/><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#plus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus7'/><rdf:Description rdf:about='urn:test#plus9'/><rdf:Description rdf:about='urn:test#plus5'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus4'/><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#plus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus5'/><rdf:Description rdf:about='urn:test#plus8'/><rdf:Description rdf:about='urn:test#plus7'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus8'/><rdf:Description rdf:about='urn:test#minus7'/><rdf:Description rdf:about='urn:test#plus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus4'/><rdf:Description rdf:about='urn:test#minus8'/><rdf:Description rdf:about='urn:test#plus6'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus4'/><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#minus5'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus6'/><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#minus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus1'/><rdf:Description rdf:about='urn:test#plus9'/><rdf:Description rdf:about='urn:test#minus6'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus9'/><rdf:Description rdf:about='urn:test#minus8'/><rdf:Description rdf:about='urn:test#plus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#plus3'/><rdf:Description rdf:about='urn:test#minus4'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus8'/><rdf:Description rdf:about='urn:test#minus4'/><rdf:Description rdf:about='urn:test#plus6'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus3'/><rdf:Description rdf:about='urn:test#plus5'/><rdf:Description rdf:about='urn:test#minus8'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus9'/><rdf:Description rdf:about='urn:test#plus4'/><rdf:Description rdf:about='urn:test#plus3'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus8'/><rdf:Description rdf:about='urn:test#minus4'/><rdf:Description rdf:about='urn:test#plus2'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus5'/><rdf:Description rdf:about='urn:test#minus2'/><rdf:Description rdf:about='urn:test#minus9'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus7'/><rdf:Description rdf:about='urn:test#minus3'/><rdf:Description rdf:about='urn:test#minus4'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#minus9'/><rdf:Description rdf:about='urn:test#minus4'/><rdf:Description rdf:about='urn:test#minus8'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#minus4'/><rdf:Description rdf:about='urn:test#minus1'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about='urn:test#Test'><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType='Collection'><rdf:Description rdf:about='urn:test#plus6'/><rdf:Description rdf:about='urn:test#minus7'/><rdf:Description rdf:about='urn:test#minus8'/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D601() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#C.1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#b.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.3\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.5\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C.6\"/>"
            + "<owl:Class rdf:about=\"urn:test#C.7\"/>"
            + "<owl:Class rdf:about=\"urn:test#C.8\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.6.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.6\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#a\"/>"
            + "<owl:Class rdf:about=\"urn:test#b\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.8\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.8\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.7.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.7\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">0</owl:cardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#a\"/>"
            + "<owl:Class rdf:about=\"urn:test#c\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.7\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.7\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.8.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.8\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#b\"/>"
            + "<owl:Class rdf:about=\"urn:test#c\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.6\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.6\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.5\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#b\"><rdfs:subClassOf rdf:resource=\"urn:test#c.comp\"/><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.3\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#a\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#C.1\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.1\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#b.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#c.comp\"/></owl:intersectionOf></owl:Class><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D602() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#d.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#c\"/></rdfs:subClassOf><rdfs:subClassOf rdf:resource=\"urn:test#d.comp\"/></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#c\"/></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"><rdfs:subClassOf rdf:resource=\"urn:test#d\"/><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom rdf:resource=\"urn:test#c\"/></owl:Restriction></owl:equivalentClass></owl:Class><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D603() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#p1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f2\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#p1.comp\"/></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f3\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#f3\"><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#f2\"/></rdfs:subPropertyOf><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#f1\"/></rdfs:subPropertyOf><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f2\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D604() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#c2\"/>"
            + "<owl:Class rdf:about=\"urn:test#c1\"/>"
            + "<owl:Class rdf:about=\"urn:test#C.1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#rx3\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C.1\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#rx3\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#c1\"/></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#rx4\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#c2\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.1\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#c1\"/>"
            + "<owl:Class rdf:about=\"urn:test#c2\"/></owl:intersectionOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#rx4\"><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#rx2\"/></rdfs:subPropertyOf><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#rx\"/></rdfs:subPropertyOf><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#rx3\"><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#rx1\"/></rdfs:subPropertyOf><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#rx\"/></rdfs:subPropertyOf><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#rxa\"/><owl:ObjectProperty rdf:about=\"urn:test#rx\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#rx1a\"/><owl:ObjectProperty rdf:about=\"urn:test#rx2a\"/><owl:ObjectProperty rdf:about=\"urn:test#rx3a\"><rdfs:subPropertyOf rdf:resource=\"urn:test#rx1a\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#rxa\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#rx4a\"><rdfs:subPropertyOf rdf:resource=\"urn:test#rx2a\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#rxa\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D605() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#c2\"/>"
            + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C.1\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#rx3a\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#c1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#rx4a\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#c2\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.1\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#c1\"/>"
            + "<owl:Class rdf:about=\"urn:test#c2\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#rx3a\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#A.2\"/></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#rx4\"><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#rx2\"/></rdfs:subPropertyOf><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#rx\"/></rdfs:subPropertyOf><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#rx3\"><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#rx1\"/></rdfs:subPropertyOf><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#rx\"/></rdfs:subPropertyOf><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#rxa\"/><owl:ObjectProperty rdf:about=\"urn:test#rx\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#rx1a\"/><owl:ObjectProperty rdf:about=\"urn:test#rx2a\"/><owl:ObjectProperty rdf:about=\"urn:test#rx3a\"><rdfs:subPropertyOf rdf:resource=\"urn:test#rx1a\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#rxa\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#rx4a\"><rdfs:subPropertyOf rdf:resource=\"urn:test#rx2a\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#rxa\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><oiled:Satisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D606() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#p2.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.5\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p3.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.6\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p4.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.7\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p5.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.4\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.2.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.3.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.3\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#A.14\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.14\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.3\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p4.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#p5.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.2\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p3.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#p4.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#p5.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.1\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p2.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#p3.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#p4.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#p5.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p5\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.4\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p4\"><rdfs:subClassOf rdf:resource=\"urn:test#p5.comp\"/><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.7\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p3\"><rdfs:subClassOf rdf:resource=\"urn:test#C.3\"/><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.6\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p2\"><rdfs:subClassOf rdf:resource=\"urn:test#C.2\"/><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.5\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf rdf:resource=\"urn:test#C.1\"/></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Satisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D608() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#p2.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.5\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p3.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.6\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p4.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.7\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p5.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.4\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.2.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p2\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#A.14\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.3.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.3\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.14\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.3\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p4.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#p5.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.2\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p3.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#p4.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#p5.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.1\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p2.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#p3.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#p4.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#p5.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p5\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.4\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p4\"><rdfs:subClassOf rdf:resource=\"urn:test#p5.comp\"/><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.7\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p3\"><rdfs:subClassOf rdf:resource=\"urn:test#C.3\"/><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.6\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p2\"><rdfs:subClassOf rdf:resource=\"urn:test#C.2\"/><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.5\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf rdf:resource=\"urn:test#C.1\"/></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D609() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invF1\"><owl:inverseOf><owl:ObjectProperty rdf:about=\"urn:test#f1\"/></owl:inverseOf></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><oiled:Satisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D610() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p\"/></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"/></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#A.2\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invF1\"><owl:inverseOf><owl:ObjectProperty rdf:about=\"urn:test#f1\"/></owl:inverseOf></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D611() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#p.comp\"/></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#A.2\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invF1\"><owl:inverseOf><owl:ObjectProperty rdf:about=\"urn:test#f1\"/></owl:inverseOf></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D612() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#p.comp\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invF1\"><owl:inverseOf><owl:ObjectProperty rdf:about=\"urn:test#f1\"/></owl:inverseOf></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D613() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invF1\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#V.3\"/></owl:Restriction></owl:intersectionOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invF1\"><owl:inverseOf><owl:ObjectProperty rdf:about=\"urn:test#f1\"/></owl:inverseOf></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D614() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.2\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom rdf:resource=\"urn:test#V.2\"/></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#s\"><rdfs:subPropertyOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D615() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#q.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.5\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#s\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#A.3\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#A.4\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.4\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#V.5\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.3\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#q.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#q\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D616() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f2\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p2.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p2\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf rdf:resource=\"urn:test#p2.comp\"/></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f2\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><oiled:Satisfiable/><rdf:Description rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f2\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D617() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#p2.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f2\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f2\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p2\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf rdf:resource=\"urn:test#p2.comp\"/></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f2\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D623() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#c\"/></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"/></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.4\"/></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.5\"/></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#a.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.7\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invP\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.6\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.6\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#a.comp\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.5\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#c\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.4\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#a\"/></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#A.2\"/></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#V.7\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#a\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invP\"><owl:inverseOf><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:inverseOf></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#TransitiveProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D624() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#a.comp\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#a\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.2\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#a.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.2\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#a\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#a\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#r\"/></rdfs:subPropertyOf></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#TransitiveProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Satisfiable/><rdf:Description rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D625() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#a.comp\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#a\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.2\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.2\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#a\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#a.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#a\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#r\"/></rdfs:subPropertyOf><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#TransitiveProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Satisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D626() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#c.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#d\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#c.comp\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#d\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#V.3\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#c\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#c.comp\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#r\"/></rdfs:subPropertyOf></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#TransitiveProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><rdf:Description rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D627() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#c.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#d\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#c\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#c.comp\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#c.comp\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#d\"/></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#V.3\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdfs:subPropertyOf><owl:ObjectProperty rdf:about=\"urn:test#r\"/></rdfs:subPropertyOf><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#TransitiveProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D628() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.5\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.3\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.2\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.4\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#C.2\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.2.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p1\"/>"
            + "<owl:Class rdf:about=\"urn:test#C.3\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.5\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#A.4\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.3.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.3\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#TransitiveProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Satisfiable/><rdf:Description rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D629() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#p1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#p1.comp\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#V.3\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#TransitiveProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Unsatisfiable/><rdf:Description rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D630() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#p1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#A.2\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:someValuesFrom rdf:resource=\"urn:test#p1.comp\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#TransitiveProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Unsatisfiable/><rdf:Description rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D631() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.5\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.2.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p1\"/>"
            + "<owl:Class rdf:about=\"urn:test#C.3\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.3.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.3\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.5\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#A.4\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.3\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.2\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.4\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#C.2\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#TransitiveProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Satisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D632() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#p1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#p1.comp\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#A.2\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#TransitiveProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D633() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#A.2\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"/></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#A.2\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#p1.comp\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p1\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#TransitiveProperty\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D634() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.4\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.5\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.5\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.4\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#V.2\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.3\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r1\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#p.comp\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#V.2\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r1\"/><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#p\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#p\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><oiled:Satisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D641() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#c1\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#d1\"/></rdfs:subClassOf><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#d1.comp\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#e3\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#c\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d1\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#f\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#d\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#c\"/>"
            + "<owl:Class rdf:about=\"urn:test#d\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf rdf:resource=\"urn:test#d.comp\"/></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#r\"/><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D642() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#e3\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#c\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c1\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#d1\"/></rdfs:subClassOf><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#d1.comp\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A.3\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#c\"/>"
            + "<owl:Class rdf:about=\"urn:test#d\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d1\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#f\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#d\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:allValuesFrom rdf:resource=\"urn:test#A.3\"/></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf rdf:resource=\"urn:test#d.comp\"/></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D643() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#c1\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#d1\"/></rdfs:subClassOf><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#d1.comp\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#e3\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#c\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d1\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#f\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#d\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#e3\"/>"
            + "<owl:Class rdf:about=\"urn:test#f\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf rdf:resource=\"urn:test#d.comp\"/></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#r\"/><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D644() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#e3\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#c\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d1\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#f\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#d\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><rdfs:subClassOf rdf:resource=\"urn:test#d1\"/><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#d1.comp\"/></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf rdf:resource=\"urn:test#d.comp\"/></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d1.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#r\"/><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D646() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#d.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#c\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#d\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf rdf:resource=\"urn:test#d.comp\"/></owl:Class><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D650() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#C.4\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.4\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#e.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#e\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom rdf:resource=\"urn:test#e.comp\"/></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#d\"/></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom rdf:resource=\"urn:test#C.4\"/></owl:Restriction></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#d\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf rdf:resource=\"urn:test#d.comp\"/></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C.4.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.4\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:cardinality></owl:Restriction></owl:equivalentClass><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#e.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#d\"/></owl:intersectionOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#s\"/><oiled:Unsatisfiable/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D661() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#C82.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.65\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C94.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.30\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C30.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.8\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C78.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.25\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C132.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.48\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C140\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C74\"/>"
            + "<owl:Class rdf:about=\"urn:test#C138.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C78\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C4.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C10\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.25\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C76\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C2.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.24\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C74\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C54\"/>"
            + "<owl:Class rdf:about=\"urn:test#C72.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C72\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C68.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C70\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.21\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C70\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C2\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C28\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C26\"/>"
            + "<owl:Class rdf:about=\"urn:test#C16.comp\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.7\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C26\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#R1\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C24\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C24\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C16\"/>"
            + "<owl:Class rdf:about=\"urn:test#C2\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C22\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C20\"/>"
            + "<owl:Class rdf:about=\"urn:test#C16\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.6\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C20\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C18\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C92.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.29\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C40.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.11\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C76.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.24\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C88.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.64\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C12.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.3\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C48.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.13\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C128\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C126\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C130.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.44\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C126\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C124\"/>"
            + "<owl:Class rdf:about=\"urn:test#C34.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C124\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C4\"/>"
            + "<owl:Class rdf:about=\"urn:test#C10.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C102.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.35\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C122\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C120\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C138.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.47\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C120\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C118\"/>"
            + "<owl:Class rdf:about=\"urn:test#C34\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C58\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C56\"/>"
            + "<owl:Class rdf:about=\"urn:test#C34\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C56\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C4\"/>"
            + "<owl:Class rdf:about=\"urn:test#C10.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C54\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C14\"/>"
            + "<owl:Class rdf:about=\"urn:test#C52\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C52\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C32.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C50.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C50\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C48.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.15\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C4.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C88\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C16\"/>"
            + "<owl:Class rdf:about=\"urn:test#C2\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.64\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C90.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.52\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C86\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C84\"/>"
            + "<owl:Class rdf:about=\"urn:test#C16\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.28\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C84\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C82\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.53\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C50.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.15\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C82\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C16.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C2\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.65\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C80\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C76.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C78.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C86.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.28\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C98.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.63\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C10.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.16\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C22.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.6\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C34.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.9\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C46.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.12\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C108\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C106\"/>"
            + "<owl:Class rdf:about=\"urn:test#C34.comp\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.36\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C106\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C104\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C18.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.56\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C104\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C34\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C100.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.49\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C112.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.39\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C102\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C100\"/>"
            + "<owl:Class rdf:about=\"urn:test#C34\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.35\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C136.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.46\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C100\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C98\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.49\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C38\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C36\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C108.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.36\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C36\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C34.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C34\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.9\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C32\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C30.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C2\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.14\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C30\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C22.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C28.comp\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.8\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C138\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom rdf:resource=\"urn:test#C136.comp\"/></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.47\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C136\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C116\"/>"
            + "<owl:Class rdf:about=\"urn:test#C134.comp\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.46\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C2.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.22\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C134\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C130.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C132\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.45\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C132\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C2\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4.comp\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.48\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C130\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C122\"/>"
            + "<owl:Class rdf:about=\"urn:test#C128\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.44\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C68\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C60\"/>"
            + "<owl:Class rdf:about=\"urn:test#C66\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.20\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C66\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C64\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C64\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C62\"/>"
            + "<owl:Class rdf:about=\"urn:test#C34.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C62\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C4\"/>"
            + "<owl:Class rdf:about=\"urn:test#C10.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C72.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.21\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C60\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom rdf:resource=\"urn:test#C58\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C84.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.53\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C96.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.38\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C32.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.14\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C68.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.20\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C8\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C2.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C16.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.4\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C28.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.7\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C6\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C2\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#TEST\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C6\"/>"
            + "<owl:Class rdf:about=\"urn:test#C140\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C110.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.37\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C4\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C134.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.45\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C2\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.22\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C18\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C16.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C2\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.56\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C16\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.4\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C14\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C8.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C12.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C12\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C4.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C10\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.3\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C98\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C34.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.63\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C10\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.16\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C96\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C94.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C2\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.38\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C94\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C86.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C92.comp\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.30\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C92\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C90\"/>"
            + "<owl:Class rdf:about=\"urn:test#C16.comp\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.29\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C90\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom rdf:resource=\"urn:test#C88\"/></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.52\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C118\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C4\"/>"
            + "<owl:Class rdf:about=\"urn:test#C10.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C116\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C80\"/>"
            + "<owl:Class rdf:about=\"urn:test#C114\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C114\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C96.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C112.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C112\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C110.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.39\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C110\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C102.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C108.comp\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.37\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C8.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C48\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C40.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C46.comp\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.13\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C46\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C44\"/>"
            + "<owl:Class rdf:about=\"urn:test#C34.comp\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.12\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C44\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C42\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C42\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C34\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C40\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C38\"/>"
            + "<owl:Class rdf:about=\"urn:test#C34\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.11\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:Thing rdf:about=\"urn:test#V822576\"><rdf:type rdf:resource=\"urn:test#C16\"/><rdf:type rdf:resource=\"urn:test#C2\"/><rdf:type rdf:resource=\"urn:test#C34\"/><rdf:type rdf:resource=\"urn:test#C4\"/><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:allValuesFrom rdf:resource=\"urn:test#C98.comp\"/></owl:Restriction></rdf:type><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:allValuesFrom rdf:resource=\"urn:test#C88.comp\"/></owl:Restriction></rdf:type><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:allValuesFrom rdf:resource=\"urn:test#C82.comp\"/></owl:Restriction></rdf:type><rdf:type rdf:resource=\"urn:test#C132.comp\"/><rdf:type rdf:resource=\"urn:test#C100.comp\"/><rdf:type rdf:resource=\"urn:test#C108.comp\"/><rdf:type rdf:resource=\"urn:test#C102.comp\"/><rdf:type rdf:resource=\"urn:test#C90.comp\"/><rdf:type rdf:resource=\"urn:test#C84.comp\"/><rdf:type rdf:resource=\"urn:test#C92.comp\"/><rdf:type rdf:resource=\"urn:test#C86.comp\"/><rdf:type rdf:resource=\"urn:test#C18.comp\"/><rdf:type rdf:resource=\"urn:test#C78.comp\"/><rdf:type rdf:resource=\"urn:test#C96.comp\"/><rdf:type rdf:resource=\"urn:test#C76.comp\"/><rdf:type rdf:resource=\"urn:test#C134.comp\"/><rdf:type rdf:resource=\"urn:test#C10.comp\"/><rdf:type rdf:resource=\"urn:test#C112.comp\"/></owl:Thing></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D665() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#C2.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C2\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C4.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.6\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C18\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#TOP\"/>"
            + "<owl:Class rdf:about=\"urn:test#C16\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C10.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.3\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C16\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C8\"/>"
            + "<owl:Class rdf:about=\"urn:test#C14\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C14\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#R1\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C12\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C12\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C2.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C10.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C8\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C6\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C10\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom rdf:resource=\"urn:test#C2.comp\"/></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.3\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#unsignedByte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C6\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C2.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4.comp\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#TEST\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C18\"/>"
            + "<owl:Class rdf:about=\"urn:test#TOP\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C4\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom rdf:resource=\"urn:test#C2.comp\"/></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.6\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#byte\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class><owl:Thing rdf:about=\"urn:test#V16562\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:allValuesFrom rdf:resource=\"urn:test#C2\"/></owl:Restriction></rdf:type><rdf:type rdf:resource=\"urn:test#C10.comp\"/><rdf:type rdf:resource=\"urn:test#C2.comp\"/></owl:Thing><owl:Thing rdf:about=\"urn:test#V16561\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:allValuesFrom rdf:resource=\"urn:test#C2\"/></owl:Restriction></rdf:type><rdf:type rdf:resource=\"urn:test#C4.comp\"/><rdf:type rdf:resource=\"urn:test#C2.comp\"/></owl:Thing><owl:Thing rdf:about=\"urn:test#V16560\"><rdf:type rdf:resource=\"urn:test#TEST\"/><rdf:type rdf:resource=\"urn:test#TOP\"/><oiled:R1 rdf:resource=\"urn:test#V16562\"/><oiled:R1 rdf:resource=\"urn:test#V16561\"/></owl:Thing></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D667() {
        String premiseOntology = "<rdf:RDF xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#C2.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#P.1\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C2\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">0</owl:maxCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C6.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.4\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C8.comp\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#P.2\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C12\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#R1\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C10\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C8\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#C6\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.2\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#short\">1</owl:minCardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C10\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C2\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#C6\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C2.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C4\"/></owl:intersectionOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#P.4\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#TEST\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C8.comp\"/>"
            + "<owl:Class rdf:about=\"urn:test#C12\"/></owl:intersectionOf></owl:Class><owl:Thing rdf:about=\"urn:test#V21081\"><rdf:type rdf:resource=\"urn:test#C4\"/><rdf:type rdf:resource=\"urn:test#C2\"/><rdf:type rdf:resource=\"urn:test#C6.comp\"/></owl:Thing><owl:Thing rdf:about=\"urn:test#V21080\"><rdf:type rdf:resource=\"urn:test#TEST\"/><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:allValuesFrom rdf:resource=\"urn:test#C6.comp\"/></owl:Restriction></rdf:type><oiled:R1 rdf:resource=\"urn:test#V21081\"/><rdf:type rdf:resource=\"urn:test#C8.comp\"/></owl:Thing></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D905() {
        String premiseOntology = head
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xml:base=\"urn:test\"><owl:Ontology/><owl:FunctionalProperty rdf:about=\"urn:test#p-N-to-1\"><owl:inverseOf><owl:ObjectProperty rdf:about=\"urn:test#invP-1-to-N\" /></owl:inverseOf><rdfs:domain rdf:resource=\"urn:test#cardinality-N\" /><rdfs:range rdf:resource=\"urn:test#only-d\" /></owl:FunctionalProperty><owl:ObjectProperty rdf:about=\"urn:test#p-N-to-1\" /><owl:FunctionalProperty rdf:about=\"urn:test#q-M-to-1\"><owl:inverseOf><owl:ObjectProperty rdf:about=\"urn:test#invQ-1-to-M\" /></owl:inverseOf><rdfs:domain rdf:resource=\"urn:test#cardinality-N-times-M\" /><rdfs:range rdf:resource=\"urn:test#cardinality-N\" /></owl:FunctionalProperty><owl:ObjectProperty rdf:about=\"urn:test#q-M-to-1\" /><owl:FunctionalProperty rdf:about=\"urn:test#r-N-times-M-to-1\"><owl:inverseOf><owl:ObjectProperty rdf:about=\"urn:test#invR-N-times-M-to-1\" /></owl:inverseOf><rdfs:domain rdf:resource=\"urn:test#cardinality-N-times-M\" /><rdfs:range rdf:resource=\"urn:test#only-d\" /></owl:FunctionalProperty><owl:ObjectProperty rdf:about=\"urn:test#r-N-times-M-to-1\"/>"
            + "<owl:Class rdf:about=\"urn:test#only-d\"><owl:oneOf rdf:parseType=\"Collection\"><owl:Thing rdf:about=\"urn:test#d\"/></owl:oneOf><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invP-1-to-N\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">2</owl:cardinality></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR-N-times-M-to-1\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">6</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#cardinality-N\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p-N-to-1\"/><owl:someValuesFrom rdf:resource=\"urn:test#only-d\"/></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invQ-1-to-M\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">3</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#cardinality-N-times-M\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#q-M-to-1\"/><owl:someValuesFrom rdf:resource=\"urn:test#cardinality-N\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#cardinality-N-times-M\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r-N-times-M-to-1\"/><owl:someValuesFrom rdf:resource=\"urn:test#only-d\"/></owl:Restriction></owl:equivalentClass></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D908() {
        String premiseOntology = head
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xml:base=\"urn:test\"><owl:Ontology/><owl:FunctionalProperty rdf:about=\"urn:test#p-N-to-1\"><owl:inverseOf><owl:ObjectProperty rdf:about=\"urn:test#invP-1-to-N\" /></owl:inverseOf><rdfs:domain rdf:resource=\"urn:test#cardinality-N\" /><rdfs:range rdf:resource=\"urn:test#infinite\" /></owl:FunctionalProperty><owl:ObjectProperty rdf:about=\"urn:test#p-N-to-1\" /><owl:FunctionalProperty rdf:about=\"urn:test#q-M-to-1\"><owl:inverseOf><owl:ObjectProperty rdf:about=\"urn:test#invQ-1-to-M\" /></owl:inverseOf><rdfs:domain rdf:resource=\"urn:test#cardinality-N-times-M\" /><rdfs:range rdf:resource=\"urn:test#cardinality-N\" /></owl:FunctionalProperty><owl:ObjectProperty rdf:about=\"urn:test#q-M-to-1\" /><owl:FunctionalProperty rdf:about=\"urn:test#r-N-times-M-to-1\"><owl:inverseOf><owl:ObjectProperty rdf:about=\"urn:test#invR-N-times-M-to-1\" /></owl:inverseOf><rdfs:domain rdf:resource=\"urn:test#cardinality-N-times-M\" /><rdfs:range rdf:resource=\"urn:test#infinite\" /></owl:FunctionalProperty><owl:ObjectProperty rdf:about=\"urn:test#r-N-times-M-to-1\"/>"
            + "<owl:Class rdf:about=\"urn:test#infinite\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invP-1-to-N\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">2</owl:cardinality></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR-N-times-M-to-1\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">5</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#cardinality-N\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p-N-to-1\"/><owl:someValuesFrom rdf:resource=\"urn:test#infinite\"/></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invQ-1-to-M\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">3</owl:cardinality></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#cardinality-N-times-M\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#q-M-to-1\"/><owl:someValuesFrom rdf:resource=\"urn:test#cardinality-N\"/></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#cardinality-N-times-M\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r-N-times-M-to-1\"/><owl:someValuesFrom rdf:resource=\"urn:test#infinite\"/></owl:Restriction></owl:equivalentClass></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DdifferentFrom2D001() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xmlns:second=\"urn:test#\" xml:base=\"urn:test\">"
            + "<owl:Ontology/><rdf:Description rdf:about=\"urn:test#a\"><owl:differentFrom rdf:resource=\"urn:test#b\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DdisjointWith2D001() {
        String premiseOntology = head
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#A\"><owl:disjointWith>"
            + "<owl:Class rdf:about=\"urn:test#B\"/></owl:disjointWith></owl:Class>"
            + "<first:A rdf:about=\"urn:test#a\"/><owl:Thing rdf:about=\"urn:test#a\"/>"
            + "<first:B rdf:about=\"urn:test#b\"/><owl:Thing rdf:about=\"urn:test#b\"/></rdf:RDF>";
        test(premiseOntology, true, true, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DequivalentClass2D001() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#Car\"><owl:equivalentClass>"
            + "<owl:Class rdf:about=\"urn:test#Automobile\"/></owl:equivalentClass></owl:Class>"
            + "<first:Car rdf:about=\"urn:test#car\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\" /></first:Car>"
            + "<first:Automobile rdf:about=\"urn:test#auto\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\" /></first:Automobile></rdf:RDF>";
        test(premiseOntology, true, true, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DequivalentClass2D002() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#Car\"><owl:equivalentClass>"
            + "<owl:Class rdf:about=\"urn:test#Automobile\"/></owl:equivalentClass></owl:Class></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DequivalentClass2D003() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#Car\"><rdfs:subClassOf>"
            + "<owl:Class rdf:about=\"urn:test#Automobile\"><rdfs:subClassOf rdf:resource=\"urn:test#Car\" /></owl:Class></rdfs:subClassOf></owl:Class></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DequivalentClass2D005() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#c1\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:cardinality></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#c2\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:cardinality></owl:Restriction></rdfs:subClassOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#p\"/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DequivalentClass2D0082DDirect() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#c1\"><owl:equivalentClass>"
            + "<owl:Class rdf:about=\"urn:test#c2\"/></owl:equivalentClass>"
            + "<first:annotate>description of c1</first:annotate></owl:Class><owl:AnnotationProperty rdf:about=\"urn:test#annotate\" /></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DequivalentProperty2D001() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#hasHead\"><owl:equivalentProperty><owl:ObjectProperty rdf:about=\"urn:test#hasLeader\"/></owl:equivalentProperty></owl:ObjectProperty><owl:Thing rdf:about=\"urn:test#X\">"
            + "<first:hasLeader><owl:Thing rdf:about=\"urn:test#Y\"/></first:hasLeader></owl:Thing></rdf:RDF>";
        test(premiseOntology, true, true, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DequivalentProperty2D002() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#hasHead\"><owl:equivalentProperty><owl:ObjectProperty rdf:about=\"urn:test#hasLeader\"/></owl:equivalentProperty></owl:ObjectProperty></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DequivalentProperty2D003() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#hasHead\"><rdfs:subPropertyOf rdf:resource=\"urn:test#hasLeader\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#hasLeader\"><rdfs:subPropertyOf rdf:resource=\"urn:test#hasHead\"/></owl:ObjectProperty></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DequivalentProperty2D004() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#p\"><rdfs:domain rdf:resource=\"urn:test#d\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#q\"><rdfs:domain rdf:resource=\"urn:test#d\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#q\"/><owl:FunctionalProperty rdf:about=\"urn:test#p\"/><owl:Thing rdf:about=\"urn:test#v\"/>"
            + "<owl:Class rdf:about=\"urn:test#d\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:hasValue rdf:resource=\"urn:test#v\"/></owl:Restriction></owl:equivalentClass><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#q\"/><owl:hasValue rdf:resource=\"urn:test#v\"/></owl:Restriction></owl:equivalentClass></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DmaxCardinality2D001() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><rdf:Description rdf:about=\"urn:test#sb1\"><rdf:type rdf:parseType=\"Resource\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Restriction\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">2</owl:maxCardinality><owl:onProperty rdf:resource=\"urn:test#prop\"/></rdf:type>"
            + "<first:prop rdf:resource=\"urn:test#ob1\"/>"
            + "<first:prop rdf:resource=\"urn:test#ob2\"/>"
            + "<first:prop rdf:resource=\"urn:test#ob3\"/></rdf:Description><rdf:Description rdf:about=\"urn:test#ob1\"><owl:differentFrom rdf:resource=\"urn:test#ob2\"/><owl:differentFrom rdf:resource=\"urn:test#ob3\"/></rdf:Description><rdf:Description rdf:about=\"urn:test#ob2\"><owl:differentFrom rdf:resource=\"urn:test#ob3\"/></rdf:Description><owl:ObjectProperty rdf:about=\"urn:test#prop\"/></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Dmiscellaneous2D102() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Thing rdf:about=\"urn:test#i\"><rdf:type>"
            + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#a\"/></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#s\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></rdf:type></owl:Thing></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Dmiscellaneous2D103() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Thing rdf:about=\"urn:test#i\"><rdf:type>"
            + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#a\"/></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#q\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#s\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></rdf:type></owl:Thing></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Dmiscellaneous2D202() {
        String premiseOntology = rdf
            + " xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"urn:test#fp\" /><owl:FunctionalProperty rdf:about=\"urn:test#fp\" /><owl:Thing>"
            + "<first:fp rdf:parseType=\"Literal\"><br /><img src=\"vn.png\" alt=\"Venn diagram\" longdesc=\"vn.html\" title=\"Venn\"></img></first:fp>"
            + "<first:fp rdf:parseType=\"Literal\"><br></br><img src=\"vn.png\" title=\"Venn\" alt=\"Venn diagram\" longdesc=\"vn.html\" /></first:fp></owl:Thing></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Dmiscellaneous2D203() {
        String premiseOntology = head
            + "xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"urn:test#fp\" /><owl:FunctionalProperty rdf:about=\"urn:test#fp\" /><owl:Thing>"
            + "<first:fp rdf:parseType=\"Literal\"><br /><img src=\"vn.png\" alt=\"Venn diagram\" longdesc=\"vn.html\" title=\"Venn\"></img></first:fp>"
            + "<first:fp rdf:parseType=\"Literal\"><br></br><img src=\"vn.png\" title=\"Venn\" alt=\"Venn diagram\" longdesc=\"vn.html\" /></first:fp></owl:Thing></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Dmiscellaneous2D3022DDirect() {
        String premiseOntology = head
            + "xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:AnnotationProperty rdf:about=\"urn:test#prop\" /><owl:Thing rdf:about=\"urn:test#a\">"
            + "<first:prop>foo</first:prop></owl:Thing></rdf:RDF>";
        test(premiseOntology, true, true, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Dmiscellaneous2D303() {
        String premiseOntology = head
            + "xml:base=\"urn:test\"><owl:Ontology/><owl:AnnotationProperty rdf:about='http://purl.org/dc/elements/1.0/creator'/></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DoneOf2D001() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class><owl:oneOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"urn:test#amy\"/><rdf:Description rdf:about=\"urn:test#bob\"/><rdf:Description rdf:about=\"urn:test#caroline\"/></owl:oneOf><owl:equivalentClass>"
            + "<owl:Class><owl:oneOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"urn:test#yolanda\"/><rdf:Description rdf:about=\"urn:test#zebedee\"/></owl:oneOf></owl:Class></owl:equivalentClass></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DoneOf2D004() {
        String premiseOntology = "<!DOCTYPE rdf:RDF [ <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\"><!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"> ]><rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"urn:test#p\"><rdfs:range><owl:DataRange><owl:oneOf><rdf:List><rdf:first rdf:datatype=\"&xsd;integer\">1</rdf:first><rdf:rest><rdf:List><rdf:first rdf:datatype=\"&xsd;integer\">2</rdf:first><rdf:rest><rdf:List><rdf:first rdf:datatype=\"&xsd;integer\">3</rdf:first><rdf:rest><rdf:List><rdf:first rdf:datatype=\"&xsd;integer\">4</rdf:first><rdf:rest rdf:resource=\"&rdf;nil\"/></rdf:List></rdf:rest></rdf:List></rdf:rest></rdf:List></rdf:rest></rdf:List></owl:oneOf></owl:DataRange></rdfs:range><rdfs:range><owl:DataRange><owl:oneOf><rdf:List><rdf:first rdf:datatype=\"&xsd;integer\">4</rdf:first><rdf:rest><rdf:List><rdf:first rdf:datatype=\"&xsd;integer\">5</rdf:first><rdf:rest><rdf:List><rdf:first rdf:datatype=\"&xsd;integer\">6</rdf:first><rdf:rest rdf:resource=\"&rdf;nil\"/></rdf:List></rdf:rest></rdf:List></rdf:rest></rdf:List></owl:oneOf></owl:DataRange></rdfs:range></owl:DatatypeProperty><owl:Thing rdf:about=\"urn:test#i\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:minCardinality rdf:datatype=\"&xsd;int\">1</owl:minCardinality></owl:Restriction></rdf:type></owl:Thing></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DsameAs2D001() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#c1\"><owl:sameAs>"
            + "<owl:Class rdf:about=\"urn:test#c2\"/></owl:sameAs>"
            + "<first:annotate>description of c1</first:annotate></owl:Class><owl:AnnotationProperty rdf:about=\"urn:test#annotate\" /></rdf:RDF>";
        test(premiseOntology, true, false, true, true);
    }
}
