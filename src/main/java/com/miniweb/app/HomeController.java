package com.miniweb.app;

import com.miniweb.framework.Controller;
import com.miniweb.framework.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to MiniMVC!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/about")
    public String about() {
        return "This website is powered by MiniMVC.";
    }
}