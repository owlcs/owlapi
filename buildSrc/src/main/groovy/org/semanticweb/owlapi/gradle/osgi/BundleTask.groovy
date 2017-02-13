package org.semanticweb.owlapi.gradle.osgi

import groovy.transform.CompileStatic
import org.gradle.api.tasks.bundling.Jar
/**
 * This class is copied from the src to the BND tools gradle plugin, modified to use a  different convention.
 */
class BundleTask extends Jar {

    BundleTask() {
        super()
        def cv = new BundleConvention(this)
        convention.plugins.bundle = cv
    }

    @Override
    protected void copy() {
        supercopy()
        println configuration?.properties
        buildBundle()
    }
    /**
     * Private method to call super.copy().
     *
     * <p>
     * We need to compile the call to super.copy() with @CompileStatic to
     * avoid a Groovy 2.4 error where the super.copy() call instead results in
     * calling this.copy() causing a StackOverflowError.
     */
    @CompileStatic
    private void supercopy() {
        super.copy()
    }
}
