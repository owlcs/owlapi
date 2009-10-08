package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLDataFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLLiteralImpl extends OWLObjectImpl implements OWLLiteral {
//
//    private static RandomAccessFile db;
//
//    private int length = 0;
//
//    private long filePointer;
//
//    static {
//        try {
//            File file = File.createTempFile("owlapiliteraldb", ".txt");
//            db = new RandomAccessFile(file, "rw");
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private String literal;


    public OWLLiteralImpl(OWLDataFactory dataFactory, String literal) {
        super(dataFactory);
        this.literal = literal;
//        this.length = literal.length();
//        try {
//            filePointer = db.getFilePointer();
//            db.writeChars(literal);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
//        literal = null;
        return hash;
    }

    public String getLiteral() {
//        if(literal != null) {
//            return literal;
//        }
//        try {
//            StringBuilder sb = new StringBuilder();
//            db.seek(filePointer);
//            for(int i = 0; i < length; i++) {
//                sb.append(db.readChar());
//            }
//            return sb.toString();
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return literal;
    }


    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLLiteral)) {
                return false;
            }

            OWLLiteralImpl other = (OWLLiteralImpl) obj;
//            if(other.length != this.length) {
//                return false;
//            }
            return other.getLiteral().equals(getLiteral());
        }
        return false;
    }
}
