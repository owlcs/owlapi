package com.clarkparsia.owlapi.explanation;

import com.clarkparsia.owlapi.explanation.util.ExplanationProgressMonitor;


/**
 * <p/>
 * Description: The explanation generator interface for returning all the
 * explanations for an unsatisfiable class. This interface extends the
 * {@link SingleExplanationGenerator} so it can be used to generate single
 * explanations, too. As in SingleExplanationGenerator,
 * {@link SatisfiabilityConverter} can be used to convert an arbitrary axiom
 * into a class description that will be passed as an argument to the
 * explanation functions.
 * </p>
 * <p/>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com>
 * </p>
 * @author Evren Sirin
 */
public interface MultipleExplanationGenerator extends ExplanationGenerator, SingleExplanationGenerator {

    /**
     * Sets the progress monitor for this multiple explanation generator.
     * @param progressMonitor The progress monitor.
     */
    public void setProgressMonitor(ExplanationProgressMonitor progressMonitor);
}
