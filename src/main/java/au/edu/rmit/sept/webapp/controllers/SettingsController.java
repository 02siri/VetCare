package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showSettings(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "settings";
    }

    @PostMapping("/update")
    public String updateSettings(@ModelAttribute User updatedUser, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null) {
            currentUser.setEmail(updatedUser.getEmail());
            currentUser.setNotificationsEnabled(updatedUser.isNotificationsEnabled());
            currentUser.setDarkModeEnabled(updatedUser.isDarkModeEnabled());
            userService.updateUser(currentUser);
            redirectAttributes.addFlashAttribute("message", "Settings updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to update settings.");
        }
        return "redirect:/settings";
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount(@RequestParam String password, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && userService.verifyPassword(user, password)) {
            userService.deleteUser(user);
            request.getSession().invalidate();
            redirectAttributes.addFlashAttribute("message", "Your account has been deleted successfully.");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid password or user not logged in. Account deletion failed.");
            return "redirect:/settings";
        }
    }
}