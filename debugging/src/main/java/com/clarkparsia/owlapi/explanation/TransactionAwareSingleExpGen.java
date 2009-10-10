package com.clarkparsia.owlapi.explanation;

/**
 * <p>
 * Title: Transaction Aware Single Explanation Generator
 * </p>
 * <p>
 * Description: Extension to {@link SingleExplanationGenerator} to allow single
 * explanation generators to be efficiently used within the
 * {@link HSTExplanationGenerator}
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com>
 * </p>
 * 
 * @author Michael Smith
 */
public interface TransactionAwareSingleExpGen extends SingleExplanationGenerator {

	/**
	 * Indicates to the explanation generator that a transaction which may
	 * modify the ontology state is starting. Intended to allow the explanation
	 * generator to efficiently preserve state when used by
	 * {@link HSTExplanationGenerator}.
	 */
	public void beginTransaction();

	/**
	 * End a transaction. See {@link #beginTransaction()}
	 */
	public void endTransaction();

}
