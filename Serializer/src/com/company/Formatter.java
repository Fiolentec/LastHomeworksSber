package com.company;

import java.util.List;
import java.util.Map;

public interface Formatter {
    String serializePrimitive(Object obj);
    String combineList(List<String> elements);
    String combineMap(Map<String, String> keyToElement);
}
