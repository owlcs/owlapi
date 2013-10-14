package org.obolibrary.oboformat.model;

public class QualifierValue {
	protected String qualifier;
	protected Object value;
	
	public QualifierValue(String q, String v) {
		qualifier = q;
		value = v;
	}
	public String getQualifier() {
		return qualifier;
	}
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
	public String toString() {
		return "{"+qualifier+"="+value+"}";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((qualifier == null) ? 0 : qualifier.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof QualifierValue))
			return false;
		QualifierValue other = (QualifierValue) obj;
		if (qualifier == null) {
			if (other.qualifier != null)
				return false;
		} else if (!qualifier.equals(other.qualifier))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
