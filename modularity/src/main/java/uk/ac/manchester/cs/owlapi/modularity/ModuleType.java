package uk.ac.manchester.cs.owlapi.modularity;

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
	 * Module type representing top modules (aka lower modules).
	 */
	TOP ("top"),

    /**
     * Module type representing bottom modules (aka upper modules).
     */
	BOT ("bottom"),

    /**
     * @deprecated
     * Module type representing bottom-of-top modules (aka upper-of-lower modules).
     */
    @Deprecated
	BOT_OF_TOP ("bottom-of-top"),

    /**
     * @deprecated
     * Module type representing top-of-bottom modules (aka lower-of-upper modules).
     */
    @Deprecated
	TOP_OF_BOT ("top-of-bottom"),

    /**
     * Module type representing fixpoint-nested modules (top-of-bottom-of-top-of-...).
     */
    STAR ("nested");

    /**
	 * The name of the module type in bottom/top notation.
	 */
	private final String name;

	/**
	 * Constructs a module type with the given name and alternative name.
	 *
	 * @param name the name in bottom/top notation
	 */
	ModuleType(String name){
		this.name = name;
	}

	/**
	 * Returns the name and alternative name of the module type.
	 *
	 * @return name (in bottom/top notation), followed by alternative name (in upper/lower notation)
	 */
	@Override
	public String toString(){
		return name;
	}

}
