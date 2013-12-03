package org.obolibrary.oboformat.parser;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.Frame.FrameType;
import org.obolibrary.oboformat.model.FrameMergeException;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.Xref;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

public class XrefExpander {

    private static Logger LOG = Logger.getLogger(XrefExpander.class.getName());

	OBODoc sourceOBODoc;
	OBODoc targetOBODoc;
	String targetBase;
	Map<String,Rule> treatMap = new HashMap<String,Rule>();
	Map<String,OBODoc> targetDocMap = new HashMap<String,OBODoc>();
	//Obo2Owl obo2owl;
	//Map<String,Clause> expandMap = new HashMap<String,Clause>();

	public XrefExpander(OBODoc src) throws InvalidXrefMapException {
		sourceOBODoc = src;
		Frame shf = src.getHeaderFrame();
		String ontId = shf.getTagValue(OboFormatTag.TAG_ONTOLOGY, String.class);
		String tgtOntId = ontId + "/xref_expansions";
		targetOBODoc = new OBODoc();
		Frame thf = new Frame(FrameType.HEADER);
		thf.addClause(new Clause(OboFormatTag.TAG_ONTOLOGY, tgtOntId));
		targetOBODoc.setHeaderFrame(thf);
		sourceOBODoc.addImportedOBODoc(targetOBODoc);
		setUp();		
	}
	public XrefExpander(OBODoc src, String targetBase) throws InvalidXrefMapException {
		sourceOBODoc = src;
		this.targetBase = targetBase;
		setUp();		
	}
	public XrefExpander(OBODoc src, OBODoc tgt) throws InvalidXrefMapException {
		sourceOBODoc = src;
		targetOBODoc = tgt;
		setUp();
	}

	public void setUp() throws InvalidXrefMapException {
		// required for translation of IDs
		//obo2owl = new Obo2Owl();
		//obo2owl.setObodoc(sourceOBODoc);

		Set<String> relationsUsed = new HashSet<String>();
		Map<String,String> relationsUseByIdSpace = new HashMap<String,String>();
	
		for (Clause c : sourceOBODoc.getHeaderFrame().getClauses()) {
			String [] parts;
			String v = c.getValue(String.class);
			if (v == null) {
                LOG.log(Level.SEVERE, "problem with header clause in xref expansion: "
                        + c);
				continue;
			}
			parts = v.split("\\s");
			String relation = null;
			String idSpace = parts[0];
			if (c.getTag().equals(OboFormatTag.TAG_TREAT_XREFS_AS_EQUIVALENT.getTag())) {
				addRule(parts[0], new EquivalenceExpansion());
				//				addMacro(idSpace,"is_specific_equivalent_of","Class: ?X EquivalentTo: ?Y and "+oboIdToIRI(parts[1])+" some "+oboIdToIRI(parts[2]));
			}
			else if (c.getTag().equals(OboFormatTag.TAG_TREAT_XREFS_AS_GENUS_DIFFERENTIA.getTag())) {
				addRule(idSpace, new GenusDifferentiaExpansion(parts[1],parts[2]));
				//				addMacro(idSpace,"is_generic_equivalent_of","Class: ?Y EquivalentTo: ?X and "+oboIdToIRI(parts[1])+" some "+oboIdToIRI(parts[2]));
				relationsUsed.add(parts[1]);
				relationsUseByIdSpace.put(idSpace, parts[1]);
				relation = parts[1];
			}
			else if (c.getTag().equals(OboFormatTag.TAG_TREAT_XREFS_AS_REVERSE_GENUS_DIFFERENTIA.getTag())) {
				addRule(idSpace, new ReverseGenusDifferentiaExpansion(parts[1],parts[2]));
				//				addMacro(idSpace,"is_generic_equivalent_of","Class: ?Y EquivalentTo: ?X and "+oboIdToIRI(parts[1])+" some "+oboIdToIRI(parts[2]));
				relationsUsed.add(parts[1]);
				relationsUseByIdSpace.put(idSpace, parts[1]);
				relation = parts[1];
			}
			else if (c.getTag().equals(OboFormatTag.TAG_TREAT_XREFS_AS_HAS_SUBCLASS.getTag())) {
				addRule(idSpace, new HasSubClassExpansion());
			}
			else if (c.getTag().equals(OboFormatTag.TAG_TREAT_XREFS_AS_IS_A.getTag())) {
				addRule(idSpace, new IsaExpansion());
			}
			else if (c.getTag().equals(OboFormatTag.TAG_TREAT_XREFS_AS_RELATIONSHIP.getTag())) {
				addRule(idSpace, new RelationshipExpansion(parts[1]));
				relationsUsed.add(parts[1]);
				relationsUseByIdSpace.put(idSpace, parts[1]);
				relation = parts[1];
			}
			else {
				continue;
			}

			if (targetBase != null) {
				// create a new bridge ontology for every expansion macro
				OBODoc tgt = new OBODoc();
				Frame thf = new Frame(FrameType.HEADER);
				thf.addClause(new Clause(OboFormatTag.TAG_ONTOLOGY, targetBase + "-" + idSpace.toLowerCase()));
				tgt.setHeaderFrame(thf);
				targetDocMap.put(idSpace, tgt);
				sourceOBODoc.addImportedOBODoc(tgt);
				if (relation != null) {
				    // See 4.4.2
				    // "In addition, any Typedef frames for relations used in a header macro
				    //   are also copied into the corresponding bridge ontology
				    
					Frame tdf = sourceOBODoc.getTypedefFrame(relation);
					if (tdf != null) {
						try {
							tgt.addTypedefFrame(tdf);
						} catch (FrameMergeException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		}
	}

	public OBODoc getTargetDoc(String idSpace) {
		if (targetOBODoc != null) {
            return targetOBODoc;
        }
		return targetDocMap.get(idSpace);
	}

	//	private String oboIdToIRI(String id) {
	//		return obo2owl.oboIdToIRI(id).toQuotedString();
	//	}

	/*
	private void addMacro(String idSpace, String prefix, String macro) throws FrameMergeException {
		String id = prefix+"_"+idSpace;
		Frame f = new Frame(Frame.FrameType.TYPEDEF);
		f.setId(id);
		Clause c = new Clause();
		c.setTag(OboFormatTag.TAG_EXPAND_ASSERTION_TO.toString());
		c.setValue(macro);
		sourceOBODoc.addFrame(f );		
	}
	 */

	private void addRule(String db, Rule rule) throws InvalidXrefMapException {
		if (treatMap.containsKey(db)) {
			throw new InvalidXrefMapException(db);
		}
		rule.idSpace = db;
		treatMap.put(db, rule);
	}

	public void expandXrefs() {
		for (Frame f : sourceOBODoc.getTermFrames()) {
			String id = f.getTagValue(OboFormatTag.TAG_ID, String.class);
			Collection<Clause> clauses = f.getClauses(OboFormatTag.TAG_XREF);
			for (Clause c : clauses) {
				Xref x = c.getValue(Xref.class);
				if (x != null) {
					String xid = x.getIdref();
					String s = getIDSpace(xid);
					if (treatMap.containsKey(s)) {
						treatMap.get(s).expand(f, id, xid);
					}
				}
			}
		}
	}

	private String getIDSpace(String x) {
		String[] parts = x.split(":", 2);
		return parts[0];
	}

	public abstract class Rule {
		protected String xref;
		public String idSpace;

		public abstract void expand(Frame sf, String id, String xref);

		protected Frame getTargetFrame(String id) {
			Frame f = getTargetDoc(idSpace).getTermFrame(id);
			if (f == null) {
				try {
					f = new Frame();
					f.setId(id);
					getTargetDoc(idSpace).addTermFrame(f);
				} catch (FrameMergeException e) {
					// this should be impossible
					e.printStackTrace();
				}
			}
			return f;
		}


	}

	public class EquivalenceExpansion extends Rule {
		public void expand(Frame sf, String id, String xref) {
			Clause c = new Clause(OboFormatTag.TAG_EQUIVALENT_TO, xref);
			sf.addClause(c);
		}

	}

	public class HasSubClassExpansion extends Rule {
		public void expand(Frame sf, String id, String xref) {
			Clause c = new Clause(OboFormatTag.TAG_IS_A, id);
			getTargetFrame(xref).addClause(c);
		}			
	}

	public class GenusDifferentiaExpansion extends Rule {

		protected String rel;
		protected String tgt;
		public GenusDifferentiaExpansion(String rel, String tgt) {
			this.rel = rel;
			this.tgt = tgt;
		}

		public void expand(Frame sf, String id, String xref) {
			Clause gc = new Clause(OboFormatTag.TAG_INTERSECTION_OF, xref);
			Clause dc = new Clause(OboFormatTag.TAG_INTERSECTION_OF);
			dc.setValue(rel);
			dc.addValue(tgt);
			getTargetFrame(id).addClause(gc);
			getTargetFrame(id).addClause(dc);
		}			
	}

	public class ReverseGenusDifferentiaExpansion extends Rule {

		protected String rel;
		protected String tgt;
		public ReverseGenusDifferentiaExpansion(String rel, String tgt) {
			this.rel = rel;
			this.tgt = tgt;
		}

		public void expand(Frame sf, String id, String xref) {
			Clause gc = new Clause(OboFormatTag.TAG_INTERSECTION_OF, id);
			Clause dc = new Clause(OboFormatTag.TAG_INTERSECTION_OF);
			dc.setValue(rel);
			dc.addValue(tgt);
			getTargetFrame(xref).addClause(gc);
			getTargetFrame(xref).addClause(dc);
		}			
	}

	public class IsaExpansion extends Rule {

		public void expand(Frame sf, String id, String xref) {
			Clause c = new Clause(OboFormatTag.TAG_IS_A, xref);
			getTargetFrame(id).addClause(c);
		}			
	}

	public class RelationshipExpansion extends Rule {

		protected String rel;
		public RelationshipExpansion(String rel) {
			this.rel = rel;
		}
		public void expand(Frame sf, String id, String xref) {
			Clause c = new Clause(OboFormatTag.TAG_RELATIONSHIP, rel);
			c.addValue(xref);
			getTargetFrame(id).addClause(c);
		}			
	}

}
