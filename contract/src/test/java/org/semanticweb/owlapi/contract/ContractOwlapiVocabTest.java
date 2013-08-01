package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.BuiltInVocabulary;
import org.semanticweb.owlapi.vocab.DublinCoreVocabulary;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWL2Datatype.Category;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.vocab.SKOSVocabulary;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;
import org.semanticweb.owlapi.vocab.SWRLVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlapiVocabTest {
    @Test
    public void shouldTestBuiltInVocabulary() throws Exception {
        BuiltInVocabulary testSubject0 = BuiltInVocabulary.DUBLIN_CORE;
        BuiltInVocabulary[] result0 = BuiltInVocabulary.values();
        String result2 = testSubject0.name();
        String result3 = testSubject0.toString();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestDublinCoreVocabulary() throws Exception {
        DublinCoreVocabulary testSubject0 = DublinCoreVocabulary.CONTRIBUTOR;
        String result0 = testSubject0.toString();
        DublinCoreVocabulary[] result1 = DublinCoreVocabulary.values();
        IRI result3 = testSubject0.getIRI();
        String result5 = testSubject0.getShortName();
        String result6 = testSubject0.getQName();
        String result7 = testSubject0.name();
        int result12 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestNamespaces() throws Exception {
        Namespaces testSubject0 = Namespaces.OWL;
        String result0 = testSubject0.toString();
        Namespaces[] result1 = Namespaces.values();
        String result3 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOWL2Datatype() throws Exception {
        OWL2Datatype testSubject0 = OWL2Datatype.OWL_RATIONAL;
        OWL2Datatype[] result0 = OWL2Datatype.values();
        boolean result2 = testSubject0.isFinite();
        IRI result3 = testSubject0.getIRI();
        boolean result4 = OWL2Datatype.isBuiltIn(IRI("urn:aFake"));
        if (result4) {
            OWL2Datatype result5 = OWL2Datatype.getDatatype(IRI("urn:aFake"));
        }
        URI result6 = testSubject0.getURI();
        String result7 = testSubject0.getShortName();
        Set<IRI> result8 = OWL2Datatype.getDatatypeIRIs();
        Pattern result9 = testSubject0.getPattern();
        Category result10 = testSubject0.getCategory();
        boolean result11 = testSubject0.isNumeric();
        Collection<OWLFacet> result12 = testSubject0.getFacets();
        boolean result13 = testSubject0.isInLexicalSpace("");
        String result14 = testSubject0.name();
        String result15 = testSubject0.toString();
        int result20 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOWLFacet() throws Exception {
        OWLFacet testSubject0 = OWLFacet.FRACTION_DIGITS;
        String result0 = testSubject0.toString();
        OWLFacet[] result1 = OWLFacet.values();
        IRI result3 = testSubject0.getIRI();
        String result4 = testSubject0.getShortName();
        Set<String> result5 = OWLFacet.getFacets();
        OWLFacet result6 = OWLFacet.getFacet(IRI("urn:aFake"));
        String result7 = testSubject0.getSymbolicForm();
        Set<IRI> result8 = OWLFacet.getFacetIRIs();
        OWLFacet result9 = OWLFacet.getFacetByShortName("");
        OWLFacet result10 = OWLFacet.getFacetBySymbolicName("");
        String result11 = testSubject0.name();
        int result16 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOWLRDFVocabulary() throws Exception {
        OWLRDFVocabulary testSubject0 = OWLRDFVocabulary.OWL_ALL_DIFFERENT;
        String result0 = testSubject0.toString();
        OWLRDFVocabulary[] result1 = OWLRDFVocabulary.values();
        IRI result3 = testSubject0.getIRI();
        Namespaces result5 = testSubject0.getNamespace();
        String result6 = testSubject0.getShortName();
        String result7 = testSubject0.name();
        int result12 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOWLXMLVocabulary() throws Exception {
        OWLXMLVocabulary testSubject0 = OWLXMLVocabulary.ABBREVIATED_IRI_ATTRIBUTE;
        String result0 = testSubject0.toString();
        OWLXMLVocabulary[] result1 = OWLXMLVocabulary.values();
        IRI result3 = testSubject0.getIRI();
        String result5 = testSubject0.getShortName();
        String result6 = testSubject0.name();
        int result11 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestPrefixOWLOntologyFormat() throws Exception {
        PrefixOWLOntologyFormat testSubject0 = new PrefixOWLOntologyFormat();
        String result0 = testSubject0.getPrefix("");
        IRI result1 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clearPrefixes();
        testSubject0.copyPrefixesFrom(new DefaultPrefixManager());
        testSubject0.copyPrefixesFrom(mock(PrefixOWLOntologyFormat.class));
        Map<String, String> result2 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result3 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result4 = testSubject0.containsPrefixMapping("");
        String result5 = testSubject0.getDefaultPrefix();
        String result6 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result7 = testSubject0
                .getParameter(mock(Object.class), mock(Object.class));
        boolean result8 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result9 = testSubject0.asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result10 = testSubject0.getOntologyLoaderMetaData();
        testSubject0.setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestSKOSVocabulary() throws Exception {
        SKOSVocabulary testSubject0 = SKOSVocabulary.ALTLABEL;
        SKOSVocabulary[] result0 = SKOSVocabulary.values();
        Set<OWLClass> result2 = SKOSVocabulary.getClasses(mock(OWLDataFactory.class));
        IRI result3 = testSubject0.getIRI();
        EntityType<?> result4 = testSubject0.getEntityType();
        URI result5 = testSubject0.getURI();
        Set<OWLAnnotationProperty> result6 = SKOSVocabulary
                .getAnnotationProperties(mock(OWLDataFactory.class));
        String result7 = testSubject0.getLocalName();
        Set<OWLObjectProperty> result8 = SKOSVocabulary
                .getObjectProperties(mock(OWLDataFactory.class));
        Set<OWLDataProperty> result9 = SKOSVocabulary
                .getDataProperties(mock(OWLDataFactory.class));
        String result10 = testSubject0.name();
        String result11 = testSubject0.toString();
        int result16 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestSWRLBuiltInsVocabulary() throws Exception {
        SWRLBuiltInsVocabulary testSubject0 = SWRLBuiltInsVocabulary.ABS;
        SWRLBuiltInsVocabulary[] result0 = SWRLBuiltInsVocabulary.values();
        IRI result2 = testSubject0.getIRI();
        URI result3 = testSubject0.getURI();
        String result4 = testSubject0.getShortName();
        int result5 = testSubject0.getMinArity();
        int result6 = testSubject0.getMaxArity();
        SWRLBuiltInsVocabulary result8 = SWRLBuiltInsVocabulary
                .getBuiltIn(IRI("urn:aFake"));
        String result10 = testSubject0.name();
        String result11 = testSubject0.toString();
        int result16 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestSWRLVocabulary() throws Exception {
        SWRLVocabulary testSubject0 = SWRLVocabulary.ARGUMENT_1;
        SWRLVocabulary[] result0 = SWRLVocabulary.values();
        IRI result2 = testSubject0.getIRI();
        URI result3 = testSubject0.getURI();
        String result4 = testSubject0.getShortName();
        String result5 = testSubject0.name();
        String result6 = testSubject0.toString();
        int result11 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestXSDVocabulary() throws Exception {
        XSDVocabulary testSubject0 = XSDVocabulary.ANY_SIMPLE_TYPE;
        String result0 = testSubject0.toString();
        XSDVocabulary[] result1 = XSDVocabulary.values();
        IRI result3 = testSubject0.getIRI();
        String result4 = testSubject0.getShortName();
        String result5 = testSubject0.name();
        int result10 = testSubject0.ordinal();
    }
}
