package org.semanticweb.owlapi.modularity.locality;

/**
 * Types of locality classes that can be used. Note that although these are
 * named after the syntactic locality classes, when used for the
 * {@link SemanticLocalityModuleExtractor}, {@link LocalityClass#BOTTOM}
 * corresponds to \empty_set and {@link LocalityClass#TOP} corresponds to
 * \Delta.
 *
 * @author Marc Robin Nolte
 *
 */
public enum LocalityClass {
	/**
	 * Locality module class obtained by giving concepts and roles not present in
	 * the signature empty interpretations when extracting a locality based module.
	 */
	BOTTOM,

	/**
	 * Locality class obtained when nesting {@link SyntacticLocalityType#BOTTOM} and
	 * {@link SyntacticLocalityType#TOP} modules until stabilization.
	 */
	STAR,

	/**
	 * Locality module class obtained by giving concepts in the signature top
	 * concept interpretation and roles not present universal role interpretation
	 * when extracting a locality based module.
	 */
	TOP
}
