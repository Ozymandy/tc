package org.tc.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageErrorController{

    @RequestMapping(value="/404")
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView error(){
        ModelAndView mav = new ModelAndView("classpath:views/404");
        return mav;
    }
}
