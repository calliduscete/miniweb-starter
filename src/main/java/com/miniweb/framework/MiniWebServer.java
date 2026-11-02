package com.miniweb.framework;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class MiniWebServer {

    private final Router router;

    // Constructor
    public MiniWebServer(Router router) {
        this.router = router;
    }

    public void start() throws IOException {

        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080),
            0
        );

        server.createContext("/", exchange -> {

            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();

            int statusCode;
            String response;

            try {
                if (!method.equals("GET")) {
                    statusCode = 405;
                    response = "405 Method Not Allowed";
                } else {
                    response = router.handle(path);

                    if (response == null) {
                        statusCode = 404;
                        response = "404 Not Found";
                    } else {
                        statusCode = 200;
                    }
                }
            } catch (Exception e) {
                statusCode = 500;
                response = "500 Internal Server Error";
                e.printStackTrace();
            }

            byte[] body = response.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                "Content-Type",
                "text/plain; charset=UTF-8"
            );

            exchange.sendResponseHeaders(statusCode, body.length);

            try (OutputStream output = exchange.getResponseBody()) {
                output.write(body);
            }
        });

        server.start();

        System.out.println(
            "MiniWeb is running at http://localhost:8080"
        );
    }
}