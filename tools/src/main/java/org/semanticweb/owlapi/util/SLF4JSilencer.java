package org.semanticweb.owlapi.util;

import java.io.PrintStream;

import javax.annotation.Nullable;

/**
 * A small wrapper class that allows SLF4J error messages about not having a binding (or more than
 * one) silenced. Just add {@code SLF4J.silence();} before any logger is initialised - ideally as
 * first instruction in your main. If the default behaviour becomes useful,
 * {@code SLF4J.deSilence();} will restore things.
 */
public class SLF4JSilencer {

    private static final PrintStream originalErr = System.err;
    private static final PrintStream filterOut = new PrintStream(System.err) {

        @Override
        public void println(@Nullable String l) {
            if (l == null || !l.startsWith("SLF4J")) {
                super.println(l);
            }
        }
    };

    /**
     * Silence SLF4J warnings.
     */
    public static void silence() {
        System.setErr(filterOut);
    }

    /**
     * Restore regular behaviour.
     */
    public static void deSilence() {
        System.setErr(originalErr);
    }
}
