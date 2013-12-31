package org.semanticweb.owlapi.api.test;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.semanticweb.owlapi.io.RDFNode;
import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

@SuppressWarnings("javadoc")
public class TripleComparatorTestCase {
    String ns = "http://www.co-ode.org/roberts/pto.owl#";
    RDFResourceIRI g = r(Class(IRI(ns + "MoleOfGoldAtom")));
    RDFResourceIRI d = r(ObjectProperty(OWL_DISJOINT_WITH.getIRI()));
    RDFResourceIRI subtype = r(RDFS_SUBCLASS_OF.getIRI());

    @Test
    public void shouldSort() {
        List<RDFTriple> list = new ArrayList<RDFTriple>(Arrays.asList(
                //@formatter:off
                triple("MoleOfNiobiumAtom"), 
                triple("MoleOfMercuryAtom"),
                triple("MoleOfHydrogenAtom"), 
                triple("MoleOfSodiumAtom"),
                triple("MoleOfIodineAtom"), 
                triple(608551021), 
                triple(1419046060),
                triple(908505087), 
                triple("MoleOfManganeseAtom"),
                triple("MoleOfIronAtom"), 
                triple("MoleOfYttriumAtom"),
                triple("MoleOfRadiumAtom"), 
                triple("MoleOfPoloniumAtom"),
                triple("MoleOfPalladiumAtom"), 
                triple("MoleOfLeadAtom"),
                triple("MoleOfTinAtom"), 
                triple("MoleOfIndiumAtom"), 
                triple(589710844),
                triple("MoleOfPhosphorusAtom"), 
                triple(767224527),
                triple("MoleOfXenonAtom"), 
                triple("MoleOfZirconiumAtom"),
                triple("MoleOfNickelAtom"), 
                triple("MoleOfRhodiumAtom"),
                triple("MoleOfThalliumAtom"), 
                triple("MoleOfHafniumAtom"),
                triple(12186480), 
                triple(1975184526), 
                triple("MoleOfVanadiumAtom"),
                triple(484873262), 
                triple("MoleOfScandiumAtom"),
                triple("MoleOfRubidiumAtom"), 
                triple("MoleOfMolybdenumAtom"),
                triple("MoleOfTelluriumAtom"), 
                triple(21622515),
                triple("MoleOfMagnesiumAtom"), 
                triple("MoleOfTungstenAtom"),
                triple("MoleOfPotassiumAtom"), 
                triple("MoleOfSulfurAtom"),
                triple("MoleOfOxygenAtom"), 
                triple("MoleOfHeliumAtom"),
                triple("MoleOfRutheniumAtom"), 
                triple(315300697), 
                triple(1711957716),
                triple("MoleOfLithiumAtom"), 
                triple("MoleOfTitaniumAtom"),
                triple("MoleOfOsmiumAtom"), 
                triple("MoleOfSiliconAtom"),
                triple("MoleOfTantalumAtom"), 
                triple(624417224),
                triple("MoleOfRadonAtom"), 
                triple(1556170233), 
                new RDFTriple(g, subtype, r(IRI(ns + "MoleOfAtom"))), 
                triple("MoleOfSeleniumAtom"),
                triple("MoleOfNeonAtom"), 
                triple("MoleOfKryptonAtom"), 
                triple( RDF_TYPE,OWL_CLASS),
                triple("MoleOfPlatinumAtom"), 
                triple("MoleOfSilverAtom"),
                triple("MoleOfStrontiumAtom"), 
                triple(1340998166)
                , triple("MoleOfIridiumAtom")
                , triple("MoleOfNitrogenAtom")
                , triple("MoleOfRheniumAtom")
                , triple("MoleOfZincAtom")
                //@formatter:on
                ));
        Collections.sort(list);
    }

    private RDFTriple triple(String n) {
        return new RDFTriple(g, d, r(IRI(ns + n)));
    }

    private RDFTriple triple(OWLRDFVocabulary p, OWLRDFVocabulary n) {
        return new RDFTriple(g, r(p.getIRI()), r(n.getIRI()));
    }

    private RDFTriple triple(int n) {
        return new RDFTriple(g, subtype, r(n));
    }

    private RDFResourceIRI r(OWLEntity e) {
        return new RDFResourceIRI(e.getIRI());
    }

    private RDFResourceIRI r(IRI e) {
        return new RDFResourceIRI(e);
    }

    private RDFNode r(int s) {
        return new RDFResourceBlankNode(s);
    }
}
