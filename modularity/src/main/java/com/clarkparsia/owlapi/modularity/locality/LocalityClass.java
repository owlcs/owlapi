package com.clarkparsia.owlapi.modularity.locality;

/**
 * <p>
 * Title: Locality Class Enumeration
 * </p>
 * <p>
 * Description: Recognized set of locality classes
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com>
 * </p>
 * 
 * @author Mike Smith
 */
public enum LocalityClass {

	/**
	 * Locality class obtained when concepts and roles not present in the
	 * signature are given empty interpretations. In the literature, ^{r
	 * \leftarrow \emptyset}_{A \leftarrow \emptyset}
	 */
	BOTTOM_BOTTOM,

	/**
	 * Locality class obtained when concepts not present in the signature are
	 * given top concept interpretation, roles not present are given empty
	 * interpretation. In the literature, ^{r \leftarrow \emptyset}_{A
	 * \leftarrow \Delta}
	 */
	TOP_BOTTOM,

	/**
	 * Locality class obtained when concepts not present in the signature are
	 * given top concept interpretation, roles not present are universal role
	 * interpretation. In the literature, ^{r \leftarrow \Delta \times
	 * \Delta}_{A \leftarrow \Delta}
	 */
	TOP_TOP
}
