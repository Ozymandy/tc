package org.tc.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.support.HandlerMethodInvocationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.tc.exceptions.CourseNotFoundException;

import javax.servlet.ServletException;

@ControllerAdvice(basePackages = {"org.tc.controllers"} )
public class GlobalControllerAdvice {
    @ModelAttribute
    public void globalAttributes(Model model) {
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
    }
    @ExceptionHandler({CourseNotFoundException.class,
            NumberFormatException.class})
    public ModelAndView unknownCourse(RuntimeException e) {
        ModelAndView mav = new ModelAndView("classpath:views/unknown");
        return mav;
    }
    @ExceptionHandler(value = NullPointerException.class)

    public ModelAndView error(){
        ModelAndView mav = new ModelAndView("classpath:views/404");
        return mav;
    }
}
