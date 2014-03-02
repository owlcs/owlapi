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
package uk.ac.manchester.owl.owlapi.tutorialowled2011;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({ "javadoc" })
public class Formatter {

    public static void main(String[] args) throws Exception {
        System.out.println("Formatter.main() " + Long.MAX_VALUE
                / (1000 * 86000 * 365));
        Map<String, String> specials = new HashMap<String, String>();
        specials.put("public void test", "\\begin{beamerboxesrounded}{");
        specials.put("() throws Exception \\{", "}\n\\scriptsize");
        String[] keywords = new String[] { " class ", " void ", " extends ",
                "public", " static final", "return", "throws" };
        Pattern stringPattern = Pattern.compile("(\"[\\w\\.\\:\\s\\#/\\-]*\")");
        BufferedReader r = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                "../OWLAPI3/tutorial2011/uk/ac/manchester/owl/owlapi/tutorialowled2011/TutorialSnippets.java")));
        String line = r.readLine();
        while (line != null) {
            if (line.trim().length() == 0) {
                System.out.println("\\end{beamerboxesrounded}\n\n");
            } else {
                line = line.replace("{", "\\{").replace("}", "\\}")
                        .replace("_", "\\_");
                if (line.trim().startsWith("//")) {
                    System.out
                            .println("\\codecomment{" + line.trim() + "}\\\\");
                } else {
                    // regular code
                    for (String s : specials.keySet()) {
                        line = line.replace(s, specials.get(s));
                    }
                    for (String s : keywords) {
                        line = line.replace(s, "\\codekeyword{" + s + "}");
                    }
                    line = line
                            .replace("\t", "\\hspace{4mm}")
                            .replace("    ", "\\hspace{4mm}")
                            .replace("\\hspace{4mm}\\hspace{4mm}",
                                    "\\hspace{4mm}");
                    Matcher match = stringPattern.matcher(line);
                    List<String> strings = new ArrayList<String>();
                    while (match.find()) {
                        strings.add(match.group(1));
                    }
                    for (String s : strings) {
                        line = line.replace(s, "\\codestring{" + s + "}");
                    }
                    if (!line.contains("beamerboxesrounded")) {
                        System.out.print("\\coderegular{");
                        System.out.print(line.trim());
                        System.out.println("}\\\\");
                    } else {
                        System.out.println(line.trim());
                    }
                }
            }
            line = r.readLine();
        }
        r.close();
    }
}
