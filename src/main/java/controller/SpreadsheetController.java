package controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by venkatamunnangi on 10/4/16.
 */
public class SpreadsheetController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
