package org.obolibrary.oboformat.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

public class Clause {
	protected String tag;

	protected Collection<Object> values;
	protected Collection<Xref> xrefs;
	protected Collection<QualifierValue> qualifierValues =
		new ArrayList<QualifierValue>();



	public Clause(OboFormatTag tag) {
		this(tag.getTag());
	}
	
	public Clause(String tag) {
		super();
		this.tag = tag;
	}
	
	public Clause(String tag, String value) {
		super();
		this.tag = tag;
		this.setValue(value);
	}
	
	public Clause(OboFormatTag tag, String value) {
		this(tag.getTag(), value);
	}

	public Clause() {
		super();
	}
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Collection<Object> getValues() {
		return values;
	}

	public void setValues(Collection<Object> values) {
		this.values = values;
	}

	public void setValue(Object v) {
		this.values = new ArrayList<Object>(1);
		values.add(v);
	}

	public void addValue(Object v) {
		if (values == null)
			values = new ArrayList<Object>(1);
		values.add(v);

	}


	public Object getValue() {
		Object value = null; 
		if (!values.isEmpty()) {
			value = values.iterator().next();
		}
		return value;
	}
	
	public <T> T getValue(Class<T> cls) {
		Object value = getValue();
		if (value != null && value.getClass().isAssignableFrom(cls)) {
			return cls.cast(value);
		}
		return null;
	}

	public Object getValue2() {
		Object value = null;
		if (values.size() > 1) {
			Iterator<Object> iterator = values.iterator();
			iterator.next();
			value = iterator.next();
		}
		return value;
	}

	public <T> T getValue2(Class<T> cls) {
		Object value = getValue2();
		if (value != null && value.getClass().isAssignableFrom(cls)) {
			return cls.cast(value);
		}
		return null;
	}
	
	public Collection<Xref> getXrefs() {
		return xrefs;
	}

	public void setXrefs(Collection<Xref> xrefs) {
		this.xrefs = xrefs;
	}

	public void addXref(Xref xref) {
		if (xrefs == null)
			xrefs = new Vector<Xref>();
		xrefs.add(xref);
	}


	public Collection<QualifierValue> getQualifierValues() {
		return qualifierValues;
	}

	public void setQualifierValues(Collection<QualifierValue> qualifierValues) {
		this.qualifierValues = qualifierValues;
	}

	public void addQualifierValue(QualifierValue qv) {
		if (qualifierValues == null)
			qualifierValues = new Vector<QualifierValue>();
		qualifierValues.add(qv);
	}


	@Override
	public String toString() {
		if (values == null)
			return tag+"=null";
		StringBuilder sb = new StringBuilder(tag);
		sb.append('(');
		for (Object ob : values) {
			sb.append(' ');
			sb.append(ob);
		}
		if (qualifierValues != null) {
			sb.append('{');
			for (QualifierValue qv : qualifierValues) {
				sb.append(qv);
				sb.append(' ');
			}
			sb.append('}');
		}
		if (xrefs != null) {
			sb.append('[');
			for (Xref x : xrefs) {
				sb.append(x);
				sb.append(' ');
			}
			sb.append(']');

		}
		sb.append(')');
		return sb.toString();
	}

	private boolean collectionsEquals(Collection<?> c1, Collection<?> c2) {
		if (c1 == null || c1.size() == 0) {
			return (c2 == null || c2.size() == 0);
		}
		if (c1 == null || c2 == null)
			return false;
		if (c1.size() != c2.size())
			return false;
		// xrefs are stored as lists to preserve order, but order is not import for comparisons
		for (Object x : c1) {
			if (!c2.contains(x))
				return false;
		}
		return true;
	}

	@Override
	public boolean equals(Object e) {

		if(e == null || !(e instanceof Clause))
			return false;

		Clause other = (Clause) e;


		if (!getTag().equals(other.getTag()))
			return false;

		if (getValues().size() == 1 && other.getValues().size() == 1) {
			// special case for comparing booleans
			//  this is a bit of a hack - ideally owl2obo would use the correct types
			if (!getValue().equals(other.getValue())) {
				if (getValue().equals(Boolean.TRUE) && other.getValue().equals("true")) {
					// special case - OK
				}
				else if (other.getValue().equals(Boolean.TRUE) && getValue().equals("true")) {
					// special case - OK					
				}
				else if (getValue().equals(Boolean.FALSE) && other.getValue().equals("false")) {
					// special case - OK
				}
				else if (other.getValue().equals(Boolean.FALSE) && getValue().equals("false")) {
					// special case - OK					
				}
				else {
					return false;
				}
				
			}
		}
		else {
			if (!getValues().equals(other.getValues()))
				return false;
		}
		if (!collectionsEquals(xrefs, other.getXrefs())) {
			return false;
		}
		/*
		if (xrefs != null) {
			if (other.getXrefs() == null)
				return false;
			if (!xrefs.equals(other.getXrefs()))
				return false;
		}
		else {
			if (other.getXrefs() != null && other.getXrefs().size() > 0) {
				return false;
			}
		}
		 */

		if (qualifierValues != null) {
			if (other.getQualifierValues() == null)
				return false;
			if (!collectionsEquals(qualifierValues, other.getQualifierValues()))
				return false;
		}
		else {
			if (other.getQualifierValues() != null && other.getQualifierValues().size() > 0) {
				return false;
			}
		}


		return true;
	}


}
