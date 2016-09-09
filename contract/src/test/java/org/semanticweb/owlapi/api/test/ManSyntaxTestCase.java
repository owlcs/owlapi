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
package org.semanticweb.owlapi.api.test;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;

@SuppressWarnings({ "javadoc" })
@RunWith(Parameterized.class)
public class ManSyntaxTestCase extends TestBase {

    private final OWLAxiom object;

    public ManSyntaxTestCase(OWLAxiom object) {
        this.object = object;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Collection<Object[]> toReturn = new ArrayList<>();
        Builder b = new Builder();
        toReturn.add(new Object[] {b.ann()});
        toReturn.add(new Object[] {b.asymm()});
        toReturn.add(new Object[] {b.annDom()});
        toReturn.add(new Object[] {b.annRange()});
        toReturn.add(new Object[] {b.ass()});
        toReturn.add(new Object[] {b.assAnd()});
        toReturn.add(new Object[] {b.assOr()});
        toReturn.add(new Object[] {b.dRangeAnd()});
        toReturn.add(new Object[] {b.dRangeOr()});
        toReturn.add(new Object[] {b.assNot()});
        toReturn.add(new Object[] {b.assNotAnon()});
        toReturn.add(new Object[] {b.assSome()});
        toReturn.add(new Object[] {b.assAll()});
        toReturn.add(new Object[] {b.assHas()});
        toReturn.add(new Object[] {b.assMin()});
        toReturn.add(new Object[] {b.assMax()});
        toReturn.add(new Object[] {b.assEq()});
        toReturn.add(new Object[] {b.assHasSelf()});
        toReturn.add(new Object[] {b.assOneOf()});
        toReturn.add(new Object[] {b.assDSome()});
        toReturn.add(new Object[] {b.assDAll()});
        toReturn.add(new Object[] {b.assDHas()});
        toReturn.add(new Object[] {b.assDMin()});
        toReturn.add(new Object[] {b.assDMax()});
        toReturn.add(new Object[] {b.assDEq()});
        toReturn.add(new Object[] {b.dOneOf()});
        toReturn.add(new Object[] {b.dNot()});
        toReturn.add(new Object[] {b.dRangeRestrict()});
        toReturn.add(new Object[] {b.assD()});
        toReturn.add(new Object[] {b.assDPlain()});
        toReturn.add(new Object[] {b.dDom()});
        toReturn.add(new Object[] {b.dRange()});
        toReturn.add(new Object[] {b.dDef()});
        toReturn.add(new Object[] {b.decC()});
        toReturn.add(new Object[] {b.decOp()});
        toReturn.add(new Object[] {b.decDp()});
        toReturn.add(new Object[] {b.decDt()});
        toReturn.add(new Object[] {b.decAp()});
        toReturn.add(new Object[] {b.decI()});
        toReturn.add(new Object[] {b.assDi()});
        toReturn.add(new Object[] {b.dc()});
        toReturn.add(new Object[] {b.dDp()});
        toReturn.add(new Object[] {b.dOp()});
        toReturn.add(new Object[] {b.du()});
        toReturn.add(new Object[] {b.ec()});
        toReturn.add(new Object[] {b.eDp()});
        toReturn.add(new Object[] {b.eOp()});
        toReturn.add(new Object[] {b.fdp()});
        toReturn.add(new Object[] {b.fop()});
        toReturn.add(new Object[] {b.ifp()});
        toReturn.add(new Object[] {b.iop()});
        toReturn.add(new Object[] {b.irr()});
        toReturn.add(new Object[] {b.ndp()});
        toReturn.add(new Object[] {b.nop()});
        toReturn.add(new Object[] {b.opa()});
        toReturn.add(new Object[] {b.opaInv()});
        toReturn.add(new Object[] {b.opaInvj()});
        toReturn.add(new Object[] {b.oDom()});
        toReturn.add(new Object[] {b.oRange()});
        toReturn.add(new Object[] {b.chain()});
        toReturn.add(new Object[] {b.ref()});
        toReturn.add(new Object[] {b.same()});
        toReturn.add(new Object[] {b.subAnn()});
        toReturn.add(new Object[] {b.subClass()});
        toReturn.add(new Object[] {b.subData()});
        toReturn.add(new Object[] {b.subObject()});
        toReturn.add(new Object[] {b.rule()});
        toReturn.add(new Object[] {b.symm()});
        toReturn.add(new Object[] {b.trans()});
        toReturn.add(new Object[] {b.hasKey()});
        toReturn.add(new Object[] {b.bigRule()});
        return toReturn;
    }

    @Test
    public void testAssertion() throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o=setupManager().createOntology(IRI.create("urn:test:manchester"));
        o.getOWLOntologyManager().addAxiom(o, object);
        StringDocumentTarget s = saveOntology(o, new ManchesterSyntaxDocumentFormat());
        if(object instanceof OWLSubDataPropertyOfAxiom) {
        System.out.println("ManSyntaxTestCase.testAssertion() "+saveOntology(o, new ManchesterSyntaxDocumentFormat()));
        }
        loadOntologyFromString(s);
    }
}
