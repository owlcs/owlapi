package uk.ac.manchester.cs.owl.modularity;

/**
 * Enumeration for module types.
 * 
 * @author Thomas Schneider
 * @author School of Computer Science
 * @author University of Manchester
 *
 */

public enum ModuleType {
	
	/**
	 * The types of modules with their names in bottom/top and upper/lower notation.
	 */
	TOP ("top", "lower"),
	BOT ("bottom", "upper"),
	BOT_OF_TOP ("bottom-of-top", "upper-of-lower"),
	TOP_OF_BOT ("top-of-bottom", "lower-of-upper"),
    BEST ("best", "with the smallest number of logical axioms");
  
    /**
	 * The name of the module type in bottom/top notation.
	 */
	private final String name;
	  
	/**
	 * The name of the module type in upper/lower notation.
	 */
	private final String altName;
  
	/**
	 * Constructs a module type with the given name and alternative name.
	 *  
	 * @param name the name in bottom/top notation
	 * @param alt the alternative name in upper/lower notation
	 */
	ModuleType(String name, String alt){
		this.name = name;
		this.altName = alt;
	}
	
	/**
	 * Returns the name and alternative name of the module type.
	 * 
	 * @return name (in bottom/top notation), followed by alternative name (in upper/lower notation)
	 */
	public String toString(){
		return name + " (" + altName + ")";
	}


    public String getName() {
        return name;
    }
}
