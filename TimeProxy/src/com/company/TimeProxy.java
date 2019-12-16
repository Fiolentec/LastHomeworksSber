package com.company;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;

public class TimeProxy implements InvocationHandler {

    private final Object delegate;

    TimeProxy(Object delegate) {
        this.delegate = delegate;
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(T delegate) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                getInterfaces(delegate),
                new TimeProxy(delegate));
    }

    private static <T> Class<?>[] getInterfaces(T delegate) {
        Class<?> clazz = delegate.getClass();
        ArrayList<Class<?>> allInterfaces = new ArrayList<>();
        while (clazz != null) {
            Class<?>[] interfaces = clazz.getInterfaces();
            allInterfaces.addAll(Arrays.asList(interfaces));
            clazz = clazz.getSuperclass();
        }
        return allInterfaces.toArray(new Class<?>[allInterfaces.size()]);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = method.invoke(delegate, objects);
        long endTime = System.currentTimeMillis();
        System.out.printf("Time: %d\n", endTime - startTime);
        return result;
    }
}
