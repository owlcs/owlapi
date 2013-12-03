package org.obolibrary.oboformat.model;

public class Xref {
	String idref;
	String annotation;

	public Xref(String idref) {
		super();
		this.idref = idref;
	}
	public String getIdref() {
		return idref;
	}
	public void setIdref(String idref) {
		this.idref = idref;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public boolean equals(Object e) {

		if(e == null || !(e instanceof Xref))
			return false;

		Xref other = (Xref) e;

		if (!idref.equals(other.idref))
			return false;

		if (false) {
			// TODO: make this configurable?
			//   xref comments are treated as semi-invisible
			if (annotation == null && other.annotation == null)
				return true;
			if (annotation == null || other.annotation == null)
				return false;
			return annotation.equals(other.annotation);
		}
		return true;

	}

	public String toString() {
		if (annotation == null)
			return idref;
		return "<"+idref+" \""+annotation+"\">";
	}
}
