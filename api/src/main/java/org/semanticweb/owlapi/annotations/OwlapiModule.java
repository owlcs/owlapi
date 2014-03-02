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
package org.semanticweb.owlapi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for Guice modules providing bindings for the OWLAPI. The bindings
 * can be for any interface. Modules annotated with {@link OwlapiModule} can be
 * loaded from the classpath and used to create the main injector. Every module
 * can create its bindings using a different strategy; e.g., hardcoding the
 * contributed mappings or using classpath scanning,
 * {@link java.util.ServiceLoader}, and similar strategies, to determine the
 * mappings to be used.<br>
 * Note: currently, no classpath scan algorithm is used, so modules need to be
 * loaded manually in an injector. Client code can add this functionality if
 * needed.
 * 
 * @author ignazio
 * @since 4.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlapiModule {}
