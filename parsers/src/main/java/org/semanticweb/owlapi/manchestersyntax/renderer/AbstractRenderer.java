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
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.io.OWLRendererIOException;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class AbstractRenderer {

    private ShortFormProvider shortFormProvider;
    private int lastNewLinePos = -1;
    private int currentPos;
    private final Writer writer;
    private final List<Integer> tabs = new ArrayList<>();
    private boolean useTabbing = true;
    private boolean useWrapping = true;

    /**
     * @param writer
     *        writer
     * @param shortFormProvider
     *        shortFormProvider
     */
    protected AbstractRenderer(Writer writer, ShortFormProvider shortFormProvider) {
        this.writer = writer;
        this.shortFormProvider = shortFormProvider;
        pushTab(0);
    }

    /**
     * @param useTabbing
     *        useTabbing
     */
    protected void setUseTabbing(boolean useTabbing) {
        this.useTabbing = useTabbing;
    }

    /**
     * @param useWrapping
     *        useWrapping
     */
    protected void setUseWrapping(boolean useWrapping) {
        this.useWrapping = useWrapping;
    }

    /**
     * @return true if use wrapping
     */
    protected boolean isUseWrapping() {
        return useWrapping;
    }

    /**
     * @return true if use tabbing
     */
    protected boolean isUseTabbing() {
        return useTabbing;
    }

    /**
     * Flush.
     * 
     * @throws OWLRendererException
     *         renderer error
     */
    protected void flush() throws OWLRendererException {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new OWLRendererIOException(e);
        }
    }

    protected void pushTab(int size) {
        tabs.add(0, size);
    }

    protected void incrementTab(int increment) {
        int base = 0;
        if (!tabs.isEmpty()) {
            base = tabs.get(0);
        }
        tabs.add(0, base + increment);
    }

    protected void popTab() {
        tabs.remove(0);
    }

    protected void writeTab() {
        int tab = tabs.get(0);
        for (int i = 0; i < tab; i++) {
            write(" ");
        }
    }

    protected int getIndent() {
        return currentPos - lastNewLinePos - 2;
    }

    protected void write(@Nullable String s) {
        if (s == null) {
            return;
        }
        int indexOfNewLine = s.indexOf('\n');
        if (indexOfNewLine != -1) {
            lastNewLinePos = currentPos + indexOfNewLine;
        }
        currentPos += s.length();
        try {
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected void write(char ch) {
        write(Character.toString(ch));
    }

    protected void writeSpace() {
        write(" ");
    }

    protected void write(ManchesterOWLSyntax keyword) {
        write(" ", keyword, " ");
    }

    protected void writeFrameKeyword(ManchesterOWLSyntax keyword) {
        write("", keyword, ": ");
    }

    protected void writeSectionKeyword(ManchesterOWLSyntax keyword) {
        write(" ", keyword, ": ");
    }

    protected void writeNewLine() {
        write("\n");
        if (useTabbing) {
            writeTab();
        }
    }

    protected void write(String prefix, ManchesterOWLSyntax keyword, String suffix) {
        write(prefix);
        write(keyword.toString());
        write(suffix);
    }

    protected ShortFormProvider getShortFormProvider() {
        return shortFormProvider;
    }

    protected void setShortFormProvider(ShortFormProvider p) {
        shortFormProvider = p;
    }
}
