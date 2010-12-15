package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLAnnotationValueVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataVisitor;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

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
public class OWLLiteralImpl extends OWLObjectImpl implements OWLLiteral {

    private String literal;

    private OWLDatatype datatype;

    private String lang;

    public OWLLiteralImpl(OWLDataFactory dataFactory, String literal, OWLDatatype datatype) {
        super(dataFactory);
        this.literal = literal;
        this.datatype = datatype;
        this.lang = "";
    }

    public OWLLiteralImpl(OWLDataFactory dataFactory, String literal, String lang) {
        super(dataFactory);
        this.literal = literal;
        this.lang = lang;
        this.datatype = dataFactory.getRDFPlainLiteral();
    }

    public String getLiteral() {
        return literal;
    }
    
    public boolean isRDFPlainLiteral() {
        return datatype.equals(getOWLDataFactory().getRDFPlainLiteral());
    }

    public boolean hasLang() {
        return !lang.equals("");
    }

    public boolean isInteger() {
        return datatype.equals(getOWLDataFactory().getIntegerOWLDatatype());
    }

    public int parseInteger() throws NumberFormatException {
        return Integer.parseInt(literal);
    }

    public boolean isBoolean() {
        return datatype.equals(getOWLDataFactory().getBooleanOWLDatatype());
    }

    public boolean parseBoolean() throws NumberFormatException {
        if (literal.equals("0")) {
            return false;
        }
        if (literal.equals("1")) {
            return true;
        }
        if (literal.equals("true")) {
            return true;
        }
        if (literal.equals("false")) {
            return false;
        }
        return false;
    }

    public boolean isDouble() {
        return datatype.equals(getOWLDataFactory().getDoubleOWLDatatype());
    }

    public double parseDouble() throws NumberFormatException {
        return Double.parseDouble(literal);
    }

    public boolean isFloat() {
        return datatype.equals(getOWLDataFactory().getFloatOWLDatatype());
    }

    public float parseFloat() throws NumberFormatException {
        return Float.parseFloat(literal);
    }

    public String getLang() {
        return lang;
    }

    public boolean hasLang(String lang) {
        return this.lang != null && this.lang.equalsIgnoreCase(lang.trim());
    }

    public OWLDatatype getDatatype() {
        return datatype;
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLLiteral)) {
                return false;
            }
            OWLLiteral other = (OWLLiteral) obj;
            return literal.equals(other.getLiteral()) && datatype.equals(other.getDatatype()) && lang.equals(other.getLang());
        }
        return false;
    }

    public void accept(OWLDataVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLDataVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLAnnotationValueVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAnnotationValueVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLLiteral other = (OWLLiteral) object;
        int diff = literal.compareTo(other.getLiteral());
        if (diff != 0) {
            return diff;
        }
        diff = datatype.compareTo(other.getDatatype());
        if (diff != 0) {
            return diff;
        }
        return lang.compareTo(other.getLang());

    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
