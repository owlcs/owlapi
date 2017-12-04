package org.semanticweb.owlapi.api.test.imports;

import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class TestCase {
    public static void main(String[] args) {

        Yaml yamlParser = new Yaml();
        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> value = new HashMap<>();
        value.put("c", "d:e");
        map.put("a", value);
        map.put("b", value);
        System.out.println("TestCase.main() \n" + yamlParser.dump(map));

        Object load = yamlParser.load(TestCase.class.getResourceAsStream("/owlzip/owlzip.yaml"));
        System.out.println("OWLZipClosureIRIMapper.OWLZipClosureIRIMapper() " + load);
    }
}
