package org.semanticweb.owlapi.gradle.osgi

import aQute.libg.reporter.ReporterAdapter
import aQute.service.reporter.Reporter
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logger

class GradleLoggingReporter extends ReporterAdapter {
    Logger logger

    GradleLoggingReporter(Logger logger) {
        this.logger = logger
    }

    @Override
    Reporter.SetLocation error(String s, Object... args) {
        logMsg(LogLevel.ERROR,super.error(s, args),s, args )
    }

    Reporter.SetLocation logMsg(LogLevel level, Reporter.SetLocation location, String s, Object[] args) {
        if (logger.isEnabled(level)) {
            def msg = String.format(s, args)
            logger.log(level, msg)
        }
        return location
    }

    @Override
    Reporter.SetLocation warning(String s, Object... args) {
        logMsg(LogLevel.WARN,super.warning(s, args),s, args )
    }

    @Override
    void trace(String s, Object... args) {
        logMsg(LogLevel.DEBUG,super.error(s, args),s, args )
    }

    @Override
    Reporter.SetLocation exception(Throwable t, String s, Object... args) {
        if (logger.isEnabled(LogLevel.ERROR)) {
            def msg = String.format(s, args)
            logger.log(LogLevel.ERROR, msg,t)
        }
        return super.exception(t,s, args)
    }
}
