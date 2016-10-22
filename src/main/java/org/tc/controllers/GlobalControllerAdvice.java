package org.tc.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.RedirectView;
import org.tc.exceptions.CourseNotFoundException;

@ControllerAdvice
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
        return new ModelAndView(new RedirectView("/unknown"));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle404(Exception e) {
        return new ModelAndView(new RedirectView("/404"));
    }
}
