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
package org.semanticweb.owlapitools.builders;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/** Builder class for OWLLiteral. */
public class BuilderLiteral extends BaseBuilder<OWLLiteral, BuilderLiteral> {

    @Nullable
    private String lang = null;
    @Nullable
    private String literalForm = null;
    @Nullable
    private Integer intValue = null;
    @Nullable
    private Double doubleValue = null;
    @Nullable
    private Float floatValue = null;
    @Nullable
    private Boolean booleanValue = null;
    @Nullable
    private OWLDatatype datatype;

    /**
     * Builder initialized from an existing object.
     * 
     * @param expected
     *        the existing object
     * @param df
     *        data factory
     */
    public BuilderLiteral(@Nonnull OWLLiteral expected, OWLDataFactory df) {
        this(df);
        if (expected.hasLang()) {
            withLanguage(expected.getLang());
        } else {
            withDatatype(expected.getDatatype());
        }
        if (expected.isBoolean()) {
            withValue(expected.parseBoolean());
        } else if (expected.isDouble()) {
            withValue(expected.parseDouble());
        } else if (expected.isFloat()) {
            withValue(expected.parseFloat());
        } else if (expected.isInteger()) {
            withValue(expected.parseInteger());
        } else {
            withLiteralForm(expected.getLiteral());
        }
    }

    /**
     * @param df
     *        data factory
     */
    @Inject
    public BuilderLiteral(OWLDataFactory df) {
        super(df);
    }

    protected void clear() {
        literalForm = null;
        intValue = null;
        doubleValue = null;
        floatValue = null;
        booleanValue = null;
    }

    /**
     * @param arg
     *        int value
     * @return builder
     */
    @Nonnull
    public BuilderLiteral withValue(int arg) {
        clear();
        intValue = arg;
        return this;
    }

    /**
     * @param arg
     *        datatype
     * @return builder
     */
    @Nonnull
    public BuilderLiteral withDatatype(@Nonnull OWL2Datatype arg) {
        return withDatatype(df.getOWLDatatype(arg.getIRI()));
    }

    /**
     * @param arg
     *        datatype
     * @return builder
     */
    @Nonnull
    public BuilderLiteral withDatatype(OWLDatatype arg) {
        lang = null;
        datatype = arg;
        return this;
    }

    /**
     * @param arg
     *        boolean value
     * @return builder
     */
    @Nonnull
    public BuilderLiteral withValue(boolean arg) {
        clear();
        booleanValue = arg;
        return this;
    }

    /**
     * @param arg
     *        double value
     * @return builder
     */
    @Nonnull
    public BuilderLiteral withValue(double arg) {
        clear();
        doubleValue = arg;
        return this;
    }

    /**
     * @param arg
     *        float value
     * @return builder
     */
    @Nonnull
    public BuilderLiteral withValue(float arg) {
        clear();
        floatValue = arg;
        return this;
    }

    /**
     * @param arg
     *        literal form
     * @return builder
     */
    @Nonnull
    public BuilderLiteral withLiteralForm(String arg) {
        clear();
        literalForm = arg;
        return this;
    }

    /**
     * @param arg
     *        language
     * @return builder
     */
    @Nonnull
    public BuilderLiteral withLanguage(String arg) {
        datatype = null;
        lang = arg;
        return this;
    }

    @SuppressWarnings("null")
    @Override
    public OWLLiteral buildObject() {
        if (intValue != null) {
            return df.getOWLLiteral(intValue.intValue());
        }
        if (doubleValue != null) {
            return df.getOWLLiteral(doubleValue.doubleValue());
        }
        if (floatValue != null) {
            return df.getOWLLiteral(floatValue.floatValue());
        }
        if (booleanValue != null) {
            return df.getOWLLiteral(booleanValue.booleanValue());
        }
        if (lang != null) {
            return df.getOWLLiteral(literalForm, lang);
        }
        if (datatype == null) {
            return df.getOWLLiteral(literalForm);
        }
        return df.getOWLLiteral(literalForm, datatype);
    }
}
