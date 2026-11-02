package com.miniweb;

import com.miniweb.app.HomeController;
import com.miniweb.framework.MiniWebServer;
import com.miniweb.framework.Router;

public class Main {

    public static void main(String[] args) throws Exception {

        Router router = new Router();

        router.registerController(new HomeController());

        MiniWebServer server = new MiniWebServer(router);

        server.start();
    }
}