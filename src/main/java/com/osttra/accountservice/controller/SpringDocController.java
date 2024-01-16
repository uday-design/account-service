package com.osttra.accountservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringDocController {

    @GetMapping(path = "/")
    @Hidden
    public ModelAndView index() {
        return new ModelAndView("redirect:/swagger-ui/index.html");
    }
}
