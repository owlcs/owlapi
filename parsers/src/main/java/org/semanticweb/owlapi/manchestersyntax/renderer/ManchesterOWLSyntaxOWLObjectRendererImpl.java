/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.manchestersyntax.renderer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

/**
 * An implementation of the OWLObjectRenderer interface. (Renders standalone
 * class class expressions and axioms in the manchester syntax).
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class ManchesterOWLSyntaxOWLObjectRendererImpl implements OWLObjectRenderer {

    @Nonnull
    private ManchesterOWLSyntaxObjectRenderer ren;
    @Nonnull
    private final WriterDelegate writerDelegate;

    /** default constructor */
    public ManchesterOWLSyntaxOWLObjectRendererImpl() {
        writerDelegate = new WriterDelegate();
        ren = new ManchesterOWLSyntaxObjectRenderer(writerDelegate, new SimpleShortFormProvider());
    }

    @Nonnull
    @Override
    public synchronized String render(@Nonnull OWLObject object) {
        writerDelegate.reset();
        object.accept(ren);
        return writerDelegate.toString();
    }

    @Override
    public synchronized void setShortFormProvider(ShortFormProvider shortFormProvider) {
        ren = new ManchesterOWLSyntaxObjectRenderer(writerDelegate, shortFormProvider);
    }

    private static class WriterDelegate extends Writer {

        private StringWriter delegate;

        /** default constructor */
        WriterDelegate() {}

        protected void reset() {
            delegate = new StringWriter();
        }

        @Nonnull
        @Override
        public String toString() {
            return delegate.getBuffer().toString();
        }

        @Override
        public void close() throws IOException {
            delegate.close();
        }

        @Override
        public void flush() {
            delegate.flush();
        }

        @Override
        public void write(char[] cbuf, int off, int len) {
            delegate.write(cbuf, off, len);
        }
    }
}
