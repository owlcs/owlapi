/*
 * This file is part of the OWL API.
 *  
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *  
 * This program is free software: you can redataInputtribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is dataInputtributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *  
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, The University of Manchester
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * dataInputtributed under the License is dataInputtributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.binaryowl.lookup;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 25/04/2012
 */
public class LookupTable {

    private IRILookupTable iriLookupTable;

    private AnonymousIndividualLookupTable anonymousIndividualLookupTable;

    private LiteralLookupTable literalLookupTable;

    private static LookupTable EMPTY_LOOKUP_TABLE = new LookupTable(new IRILookupTable(), new AnonymousIndividualLookupTable() {
        @Override
        public int getIndex(OWLAnonymousIndividual ind) {
            return System.identityHashCode(ind);
        }
    }, new LiteralLookupTable() {
        @Override
        public int getIndex(OWLLiteral literal) {
            return -1;
        }
    }
    );

    public LookupTable(IRILookupTable iriLookupTable, AnonymousIndividualLookupTable anonymousIndividualLookupTable, LiteralLookupTable literalLookupTable) {
        this.iriLookupTable = iriLookupTable;
        this.anonymousIndividualLookupTable = anonymousIndividualLookupTable;
        this.literalLookupTable = literalLookupTable;
    }

    public LookupTable(IRILookupTable iriLookupTable, LiteralLookupTable literalLookupTable) {
        this(iriLookupTable, new AnonymousIndividualLookupTable(), literalLookupTable);
    }

    public LookupTable() {
        this(new IRILookupTable(), new AnonymousIndividualLookupTable(), new LiteralLookupTable());
    }


    public static LookupTable emptyLookupTable() {
        return EMPTY_LOOKUP_TABLE;
    }

    public IRILookupTable getIRILookupTable() {
        return iriLookupTable;
    }

    public AnonymousIndividualLookupTable getAnonymousIndividualLookupTable() {
        return anonymousIndividualLookupTable;
    }

    public LiteralLookupTable getLiteralLookupTable() {
        return literalLookupTable;
    }


    //////////////////////////////////////////////////////////////////


    public void writeIRI(IRI iri, DataOutput dataOutput) throws IOException {
        iriLookupTable.writeIRI(iri, dataOutput);
    }

    public IRI readIRI(DataInput dataInput) throws IOException {
        return iriLookupTable.readIRI(dataInput);
    }

    public void writeLiteral(OWLLiteral literal, DataOutput dos) throws IOException {
        literalLookupTable.writeLiteral(dos, literal);
    }

    public OWLLiteral readLiteral(DataInput dataInput, OWLDataFactory dataFactory) throws IOException {
        return literalLookupTable.readLiteral(dataInput, dataFactory);
    }


}
