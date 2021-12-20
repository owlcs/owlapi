/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi6.apitest;

import static org.semanticweb.owlapi6.vocab.OWLRDFVocabulary.OWL_CLASS;
import static org.semanticweb.owlapi6.vocab.OWLRDFVocabulary.OWL_DISJOINT_WITH;
import static org.semanticweb.owlapi6.vocab.OWLRDFVocabulary.RDFS_SUBCLASS_OF;
import static org.semanticweb.owlapi6.vocab.OWLRDFVocabulary.RDF_TYPE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.RDFNode;
import org.semanticweb.owlapi6.documents.RDFResourceBlankNode;
import org.semanticweb.owlapi6.documents.RDFResourceIRI;
import org.semanticweb.owlapi6.documents.RDFTriple;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.NodeID;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.vocab.OWLRDFVocabulary;

class TripleComparatorTestCase extends TestBase {

    private final String ns = "http://www.co-ode.org/roberts/pto.owl#";
    private final RDFResourceIRI gold = r(Class(iri(ns, "MoleOfGoldAtom")));
    private final RDFResourceIRI disjoint = r(ObjectProperty(OWL_DISJOINT_WITH.getIRI()));
    private final RDFResourceIRI subtype = r(RDFS_SUBCLASS_OF.getIRI());

    @Test
    void shouldSort() {
        List<RDFTriple> list = new ArrayList<>(l(disjoint("MoleOfNiobiumAtom"),
            disjoint("MoleOfMercuryAtom"), disjoint("MoleOfHydrogenAtom"),
            disjoint("MoleOfSodiumAtom"), disjoint("MoleOfIodineAtom"), subtype(608551021),
            subtype(1419046060), subtype(908505087), disjoint("MoleOfManganeseAtom"),
            disjoint("MoleOfIronAtom"), disjoint("MoleOfYttriumAtom"), disjoint("MoleOfRadiumAtom"),
            disjoint("MoleOfPoloniumAtom"), disjoint("MoleOfPalladiumAtom"),
            disjoint("MoleOfLeadAtom"), disjoint("MoleOfTinAtom"), disjoint("MoleOfIndiumAtom"),
            subtype(589710844), disjoint("MoleOfPhosphorusAtom"), subtype(767224527),
            disjoint("MoleOfXenonAtom"), disjoint("MoleOfZirconiumAtom"),
            disjoint("MoleOfNickelAtom"), disjoint("MoleOfRhodiumAtom"),
            disjoint("MoleOfThalliumAtom"), disjoint("MoleOfHafniumAtom"), subtype(12186480),
            subtype(1975184526), disjoint("MoleOfVanadiumAtom"), subtype(484873262),
            disjoint("MoleOfScandiumAtom"), disjoint("MoleOfRubidiumAtom"),
            disjoint("MoleOfMolybdenumAtom"), disjoint("MoleOfTelluriumAtom"), subtype(21622515),
            disjoint("MoleOfMagnesiumAtom"), disjoint("MoleOfTungstenAtom"),
            disjoint("MoleOfPotassiumAtom"), disjoint("MoleOfSulfurAtom"),
            disjoint("MoleOfOxygenAtom"), disjoint("MoleOfHeliumAtom"),
            disjoint("MoleOfRutheniumAtom"), subtype(315300697), subtype(1711957716),
            disjoint("MoleOfLithiumAtom"), disjoint("MoleOfTitaniumAtom"),
            disjoint("MoleOfOsmiumAtom"), disjoint("MoleOfSiliconAtom"),
            disjoint("MoleOfTantalumAtom"), subtype(624417224), disjoint("MoleOfRadonAtom"),
            subtype(1556170233), subtype(r(iri(ns, "MoleOfAtom"))), disjoint("MoleOfSeleniumAtom"),
            disjoint("MoleOfNeonAtom"), disjoint("MoleOfKryptonAtom"), triple(RDF_TYPE, OWL_CLASS),
            disjoint("MoleOfPlatinumAtom"), disjoint("MoleOfSilverAtom"),
            disjoint("MoleOfStrontiumAtom"), subtype(1340998166), disjoint("MoleOfIridiumAtom"),
            disjoint("MoleOfNitrogenAtom"), disjoint("MoleOfRheniumAtom"),
            disjoint("MoleOfZincAtom")));
        Collections.sort(list);
    }

    private RDFTriple disjoint(String n) {
        return new RDFTriple(gold, disjoint, r(iri(ns, n)));
    }

    private RDFTriple triple(OWLRDFVocabulary p, OWLRDFVocabulary n) {
        return new RDFTriple(gold, r(p.getIRI()), r(n.getIRI()));
    }

    private RDFTriple subtype(int n) {
        return subtype(r(n));
    }

    private RDFTriple subtype(RDFNode n) {
        return new RDFTriple(gold, subtype, n);
    }

    private static RDFResourceIRI r(OWLEntity entity) {
        return new RDFResourceIRI(entity.getIRI());
    }

    private static RDFResourceIRI r(IRI iri) {
        return new RDFResourceIRI(iri);
    }

    private static RDFNode r(int anonid) {
        return new RDFResourceBlankNode(NodeID.nodeId(anonid, df), false, false, false);
    }
}
