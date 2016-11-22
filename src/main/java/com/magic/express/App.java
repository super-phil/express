package com.magic.express;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Hello world!
 */
@SpringBootApplication
@Controller
public class App {
    @RequestMapping("/list")
    public String list() {
        return "welcome";
    }
    @RequestMapping("/")
    public String charts() {
        return "charts";
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("Hello World!");
    }
}
