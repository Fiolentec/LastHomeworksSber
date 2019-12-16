package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonFormatter implements Formatter {


    private final int numSpace;

    public JsonFormatter(int numSpace) {
        this.numSpace = numSpace;
    }

    @Override
    public String serializePrimitive(Object value) {
        return getPrimitiveString(value);
    }

    @Override
    public String combineList(List<String> elements) {
        String delimiter = checkIfSimple(elements) ? ", " : ",\n ";
        List<String> shiftedElements = elements.stream()
                .map(e -> shiftBlockPartially(e, 1))
                .collect(Collectors.toList());
        return "[" +
                String.join(delimiter, shiftedElements) +
                "]";
    }


    @Override
    public String combineMap(Map<String, String> keyToElement) {
        ArrayList<String> mapElements = new ArrayList<>();
        keyToElement.forEach((k, v) -> mapElements.add(shiftBlock(combineKeyValue(k, v), this.numSpace)));
        return "{\n" + String.join(",\n", mapElements) + "\n}";
    }


    private String combineKeyValue(String key, String value) {
        String jsonKey = "\"" + key + "\": ";
        return jsonKey + shiftBlockPartially(value, jsonKey.length());
    }


    public static String shiftBlock(String s, int numSpace) {
        return makeSpace(numSpace) + shiftBlockPartially(s, numSpace);
    }


    public static String shiftBlockPartially(String s, int numSpace) {
        return String.join("\n" + makeSpace(numSpace), s.split("\n"));
    }

    public static String makeSpace(int numSpace) {
        return String.join("", Collections.nCopies(numSpace, " "));
    }


    private boolean checkIfSimple(List<String> elements) {
        return elements.stream().noneMatch(s -> s.contains("\n"));
    }


    private String getPrimitiveString(Object obj) {
        if (obj == null) {
            return "null";
        } else {
            String s = obj.toString();
            if (obj instanceof Number || obj instanceof Boolean) {
                return s;
            } else {
                return "\"" + s + "\"";
            }
        }
    }
}
