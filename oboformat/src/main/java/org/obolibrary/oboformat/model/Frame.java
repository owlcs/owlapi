package org.obolibrary.oboformat.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.obolibrary.obo2owl.OboInOwlCardinalityTools;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

public class Frame {
	
	public enum FrameType {
		HEADER, TERM, TYPEDEF, INSTANCE, ANNOTATION
	}
	
	
	protected Collection<Clause> clauses;
	protected String id;
	protected FrameType type;
	
	
	public Frame() {
		super();
		init();
	}
	
	public Frame(FrameType type) {
		super();
		init();
		this.type = type;
	}

	protected void init() {
		clauses = new ArrayList<Clause>();
	}


	public FrameType getType() {
		return type;
	}

	public void setType(FrameType type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public Collection<Clause> getClauses() {
		return clauses;
	}
	
	public Collection<Clause> getClauses(String tag) {
		Collection<Clause> cls = new ArrayList<Clause>();
		for (Clause cl: clauses) {
			if (cl.getTag().equals(tag)) {
				cls.add(cl);
			}
		}
		return cls;
	}
	
	public Collection<Clause> getClauses(OboFormatTag tag) {
		return getClauses(tag.getTag());
	}

	
	/**
	 * @param tag
	 * @return null if no value set, otherwise first value
	 */
	public Clause getClause(String tag) {
		for (Clause cl: clauses) {
			if (cl.getTag().equals(tag)) {
				return cl;
			}
			// TODO - throw exception if more than one clause of this type?
		}
		return null; 
	}

	public Clause getClause(OboFormatTag tag) {
		return getClause(tag.getTag());
	}

	public void setClauses(Collection<Clause> clauses) {
		this.clauses = clauses;
	}

	public void addClause(Clause cl) {
		clauses.add(cl);	
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Frame(");
		sb.append(id);
		sb.append(' ');
		for (Clause cl: clauses) {
			sb.append(cl.toString());
		}
		sb.append(')');
		return sb.toString();
	}

	public Object getTagValue(String tag) {
		if (getClause(tag) == null)
			return null;
		return getClause(tag).getValue();
	}
	
	public Object getTagValue(OboFormatTag tag) {
		return getTagValue(tag.getTag());
	}

	public <T> T getTagValue(String tag, Class<T> cls) {
		if (getClause(tag) == null)
			return null;
		Object value = getClause(tag).getValue();
		if (value != null && value.getClass().isAssignableFrom(cls)) {
			return cls.cast(value);
		}
		return null;
	}
	
	public <T> T getTagValue(OboFormatTag tag, Class<T> cls) {
		return getTagValue(tag.getTag(), cls);
	}
	
	public Collection<Object> getTagValues(OboFormatTag tag) {
		return getTagValues(tag.getTag());
	}
	
	public Collection<Object> getTagValues(String tag) {
		Collection<Object> vals = new Vector<Object>();
		for (Clause c : getClauses(tag)) {
			vals.add(c.getValue());
		}
		return vals;
	}
	
	public <T> Collection<T> getTagValues(OboFormatTag tag, Class<T> cls) {
		return getTagValues(tag.getTag(), cls);
	}
	
	public <T> Collection<T> getTagValues(String tag, Class<T> cls) {
		Collection<T> vals = new Vector<T>();
		for (Clause c : getClauses(tag)) {
			vals.add(c.getValue(cls));
		}
		return vals;
	}

	public Collection<Xref> getTagXrefs(String tag) {
		Collection<Xref> xrefs = new Vector<Xref>();
		for (Object ob : getClause(tag).getValues()) {
			if (ob instanceof Xref) {
				xrefs.add((Xref)ob);
			}
		}
		return xrefs;
	}

	public Set<String> getTags() {
		Set<String> tags = new HashSet<String>();
		for (Clause cl : getClauses()) {
			tags.add(cl.getTag());
		}
		return tags;
	}

	public void merge(Frame extFrame) throws FrameMergeException {
		
		if(this == extFrame)
			return;
		
		if (!extFrame.getId().equals(getId())) {
			throw new FrameMergeException("ids do not match");
		}
		if (!extFrame.getType().equals(getType())) {
			throw new FrameMergeException("frame types do not match");
		}
		for (Clause c : extFrame.getClauses()) {
			addClause(c);
		}
		// note we do not perform a document structure check at this point
	}
	
	/**
	 * Check this frame for violations, i.e. cardinality constraint violations.
	 * 
	 * @throws FrameStructureException
	 * 
	 * @see OboInOwlCardinalityTools for equivalent checks in OWL 
	 */
	public void check() throws FrameStructureException {
		if (FrameType.HEADER.equals(type)) {
			checkMaxOneCardinality(OboFormatTag.TAG_ONTOLOGY, 
					OboFormatTag.TAG_FORMAT_VERSION,
					OboFormatTag.TAG_DATE, 
					OboFormatTag.TAG_DEFAULT_NAMESPACE,
					OboFormatTag.TAG_SAVED_BY,
					OboFormatTag.TAG_AUTO_GENERATED_BY);
		}
		if (FrameType.TYPEDEF.equals(type)) {
			checkMaxOneCardinality(OboFormatTag.TAG_DOMAIN,
					OboFormatTag.TAG_RANGE,
					OboFormatTag.TAG_IS_METADATA_TAG,
					OboFormatTag.TAG_IS_CLASS_LEVEL_TAG);
		}
		if (!FrameType.HEADER.equals(getType())) {
			if (getClauses(OboFormatTag.TAG_ID).size() != 1) {
				throw new FrameStructureException(this, "cardinality of id field must be 1");
			}
			if (this.getClause(OboFormatTag.TAG_ID).getValue() == null) {
				throw new FrameStructureException(this, "id field must not be null");
			}
			if (this.getId() == null) {
				throw new FrameStructureException(this, "id field must be set");
			}
		}
		Collection<Clause> iClauses = getClauses(OboFormatTag.TAG_INTERSECTION_OF);
		if (iClauses.size() == 1) {
			throw new FrameStructureException(this, "single intersection_of tags are not allowed");
		}
		checkMaxOneCardinality(OboFormatTag.TAG_IS_ANONYMOUS,
				OboFormatTag.TAG_NAME,
//				OboFormatTag.TAG_NAMESPACE,
				OboFormatTag.TAG_DEF,
				OboFormatTag.TAG_COMMENT,
				OboFormatTag.TAG_IS_ANTI_SYMMETRIC,
				OboFormatTag.TAG_IS_CYCLIC,
				OboFormatTag.TAG_IS_REFLEXIVE,
				OboFormatTag.TAG_IS_SYMMETRIC,
				OboFormatTag.TAG_IS_TRANSITIVE,
				OboFormatTag.TAG_IS_FUNCTIONAL,
				OboFormatTag.TAG_IS_INVERSE_FUNCTIONAL,
				OboFormatTag.TAG_IS_OBSELETE,
				OboFormatTag.TAG_CREATED_BY,
				OboFormatTag.TAG_CREATION_DATE);		
	}
	
	private void checkMaxOneCardinality(OboFormatTag...tags) throws FrameStructureException {
		for (OboFormatTag tag : tags) {
			if (getClauses(tag).size() > 1) {
				throw new FrameStructureException(this, "multiple "+tag.getTag()+" tags not allowed.");
			}
		}
	}


}
