package com.miniweb.framework;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Router {

    private static class Route {
        Object controller;
        Method method;

        Route(Object controller, Method method) {
            this.controller = controller;
            this.method = method;
        }
    }

    private final Map<String, Route> routes = new HashMap<>();

    public void registerController(Object controller) {

        Class<?> controllerClass = controller.getClass();

        if (!controllerClass.isAnnotationPresent(Controller.class)) {
            throw new IllegalArgumentException(
                "Class must have @Controller annotation"
            );
        }

        for (Method method : controllerClass.getDeclaredMethods()) {

            if (method.isAnnotationPresent(GetMapping.class)) {

                GetMapping mapping =
                    method.getAnnotation(GetMapping.class);

                String path = mapping.value();

                routes.put(path, new Route(controller, method));

                System.out.println(
                    "Registered GET " + path
                    + " -> " + method.getName()
                );
            }
        }
    }

    public String handle(String path) throws Exception {

        Route route = routes.get(path);

        if (route == null) {
            return null;
        }

        Object result = route.method.invoke(route.controller);

        return String.valueOf(result);
    }
}