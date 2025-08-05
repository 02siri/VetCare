package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

public class BaseController {

    @ModelAttribute
    public void addUserToModel(Model model, HttpServletRequest request) {
        User user = getCurrentUser(request);
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
    }

    protected User getCurrentUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    protected void setCurrentUser(User user, HttpServletRequest request) {
        request.getSession().setAttribute("user", user);
    }
}