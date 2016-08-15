package br.frico.bagarita.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Felipe Rico on 8/15/2016.
 */
@RestController
public class TestController {

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String test() {
        return "Test Worked!";
    }

}
