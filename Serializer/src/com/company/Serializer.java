package com.company;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

public class Serializer {

    final Formatter formatter;

    public Serializer(Formatter formatter) {
        this.formatter = formatter;
    }

    public String serialize(Object obj) {
        return serializeDeclaredFields(obj);
    }

    public static Map<String, Object> getFields(Object obj) {
        HashMap<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Access error");
            }
        }
        return map;
    }

    private String serializeObject(Object obj) {
        if (obj == null) {
            return formatter.serializePrimitive(null);
        }
        Class<?> clazz = obj.getClass();
        if (Collection.class.isAssignableFrom(clazz)) {
            return serializeCollection((Collection<?>) obj);
        } else if (Map.class.isAssignableFrom(clazz)) {
            return serializeMap((Map<?, ?>) obj);
        } else if (isSimple(clazz)) {
            return formatter.serializePrimitive(obj);
        } else {
            return serializeDeclaredFields(obj);
        }
    }

    private String serializeCollection(Collection<?> collection) {
        List<String> serializedElements = new ArrayList<>();
        collection.forEach(v -> serializedElements.add(serializeObject(v)));
        return formatter.combineList(serializedElements);
    }

    private String serializeMap(Map<?, ?> map) {
        Map<String, String> serializedElements = new HashMap<>();
        map.forEach((k, v) -> serializedElements.put(k.toString(), serializeObject(v)));
        return formatter.combineMap(serializedElements);
    }

    private boolean isSimple(Class<?> type) {
        return type.isPrimitive() || Serializable.class.isAssignableFrom(type);
    }

    public String serializeDeclaredFields(Object obj) {
        return serializeMap(getFields(obj));
    }

}
