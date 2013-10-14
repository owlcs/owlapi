package org.obolibrary.oboformat.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.obolibrary.obo2owl.OboInOwlCardinalityTools;
import org.obolibrary.oboformat.model.Frame.FrameType;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

/**
 * An OBODoc is a container for a header frame and zero or more entity frames
 *
 */
public class OBODoc {
	protected Frame headerFrame;
	protected Map<String,Frame> termFrameMap = new HashMap<String,Frame>();
	protected Map<String,Frame> typedefFrameMap = new HashMap<String,Frame>();
	protected Map<String,Frame> instanceFrameMap = new HashMap<String,Frame>();
	protected Collection<Frame> annotationFrames = new LinkedList<Frame>();
	protected Collection<OBODoc> importedOBODocs = new LinkedList<OBODoc>();

	public OBODoc() {
		super();
	}

	public Frame getHeaderFrame() {
		return headerFrame;
	}

	public void setHeaderFrame(Frame headerFrame) {
		this.headerFrame = headerFrame;
	}

	public Collection<Frame> getTermFrames() {
		return termFrameMap.values();
	}
	public Collection<Frame> getTypedefFrames() {
		return typedefFrameMap.values();
	}
	public Collection<Frame> getInstanceFrames() {
		return instanceFrameMap.values();
	}
	public Frame getTermFrame(String id) {
		return getTermFrame(id, false);
	}

	public Frame getTermFrame(String id, boolean followImport) {
		if (followImport == false) {
			return termFrameMap.get(id);
		}
		// this set is used to check for cycles
		Set<String> visited = new HashSet<String>();
		visited.add(this.getHeaderDescriptor());
		return _getTermFrame(id, visited);
	}

	
	private Frame _getTermFrame(String id, Set<String> visitedDocs) {
		Frame f = termFrameMap.get(id);
		
		if(f!= null){
			return f;
		}
		for(OBODoc doc: importedOBODocs){
			String headerDescriptor = doc.getHeaderDescriptor();
			if( !visitedDocs.contains(headerDescriptor)){
				visitedDocs.add(headerDescriptor);
				f = doc.getTermFrame(id, true);
			}

			if(f != null)
				return f;
		}
		
		return null;
	}
	
	
	public Frame getTypedefFrame(String id) {
		return getTypedefFrame(id, false);
	}

	public Frame getTypedefFrame(String id, boolean followImports) {
		if (followImports == false) {
			return typedefFrameMap.get(id);
		}
		// this set is used to check for cycles
		Set<String> visited = new HashSet<String>();
		visited.add(this.getHeaderDescriptor());
		return _getTypedefFrame(id, visited);

	}
	
	private Frame _getTypedefFrame(String id, Set<String> visitedDocs) {
		Frame f = typedefFrameMap.get(id);
		
		if(f!= null){
			return f;
		}
		for(OBODoc doc: importedOBODocs){
				
			String headerDescriptor = doc.getHeaderDescriptor();
			if( !visitedDocs.contains(headerDescriptor)){
				visitedDocs.add(headerDescriptor);
				f = doc.getTypedefFrame(id, true);
			}
			if(f != null)
				return f;
		}
		
		return null;
		
	}
	
	
	public Frame getInstanceFrame(String id) {
		return instanceFrameMap.get(id);
	}
	

	public Collection<OBODoc> getImportedOBODocs() {
		return importedOBODocs;
	}

	public void setImportedOBODocs(Collection<OBODoc> importedOBODocs) {
		this.importedOBODocs = importedOBODocs;
	}
	
	public void addImportedOBODoc(OBODoc doc) {
		if (importedOBODocs == null) {
			importedOBODocs = new ArrayList<OBODoc>();
		}
		importedOBODocs.add(doc);
	}


	public void addFrame(Frame f) throws FrameMergeException {
		if (f.getType() == FrameType.TERM) {
			addTermFrame(f);
		}
		else if (f.getType() == FrameType.TYPEDEF) {
			addTypedefFrame(f);
			
		}
		else if (f.getType() == FrameType.INSTANCE) {
			addInstanceFrame(f);
		}
	}
	
	public void addTermFrame(Frame f) throws FrameMergeException {
		String id = f.getId();
		if (termFrameMap.containsKey(id)) {
			termFrameMap.get(id).merge(f);
		}
		else {
			termFrameMap.put(id, f);
		}
	}
	
	public void addTypedefFrame(Frame f) throws FrameMergeException {
		String id = f.getId();
		if (typedefFrameMap.containsKey(id)) {
			typedefFrameMap.get(id).merge(f);
		}
		else {
			typedefFrameMap.put(id, f);
		}
	}
	
	public void addInstanceFrame(Frame f) throws FrameMergeException {
		String id = f.getId();
		if (instanceFrameMap.containsKey(id)) {
			instanceFrameMap.get(id).merge(f);
		}
		else {
			instanceFrameMap.put(id, f);
		}
	}

	/**
	 * 
	 * Looks up the ID prefix to IRI prefix mapping.
	 * Header-Tag: idspace
	 * 
	 * @param prefix prefix
	 * @return IRI prefix as string
	 */
	public String getIDSpace(String prefix) {
		// built-in
		if (prefix.equals("RO")) {
			return "http://purl.obolibrary.org/obo/RO_";
		}
		// TODO
		return null;
	}

	public boolean isTreatXrefsAsEquivalent(String prefix) {
		if (prefix != null && prefix.equals("RO")) {
			return true;
		}
		return false;
	}
	
	public void mergeContents(OBODoc extDoc) throws FrameMergeException {
		for (Frame f : extDoc.getTermFrames())
			addTermFrame(f);
		for (Frame f : extDoc.getTypedefFrames())
			addTypedefFrame(f);
		for (Frame f : extDoc.getInstanceFrames())
			addInstanceFrame(f);
	}
	
	public void addDefaultOntologyHeader(String defaultOnt) {
		Frame hf = getHeaderFrame();
		Clause ontClause = hf.getClause(OboFormatTag.TAG_ONTOLOGY);
		if (ontClause == null) {
			ontClause = new Clause(OboFormatTag.TAG_ONTOLOGY, defaultOnt);
			hf.addClause(ontClause);
		}
	}
	
	/**
	 * Check this document for violations, i.e. cardinality constraint violations.
	 * 
	 * @throws FrameStructureException
	 * 
	 * @see OboInOwlCardinalityTools for equivalent checks in OWL 
	 */
	public void check() throws FrameStructureException {
		getHeaderFrame().check();
		for (Frame f : getTermFrames())
			f.check();
		for (Frame f : getTypedefFrames())
			f.check();
		for (Frame f : getInstanceFrames())
			f.check();
		
	}


	public String toString() {
		//StringBuffer sb = new StringBuffer();
		//for (Frame f : getTermFrames()) {
		//	sb.append(f.toString());
		//}
		//return "OBODoc("+headerFrame+" Frames("+sb.toString()+"))";
		return getHeaderDescriptor();
	}

	private String getHeaderDescriptor() {
		return "OBODoc("+headerFrame+")";
	}

}
