package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.ReCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReCaptchaService reCaptchaService;

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("siteKey", reCaptchaService.getSiteKey());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("user") User user, 
                                @RequestParam(name = "g-recaptcha-response", required = false) String reCaptchaResponse,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (reCaptchaResponse == null || reCaptchaResponse.isEmpty()) {
            model.addAttribute("reCaptchaError", "Please verify that you are not a robot");
            model.addAttribute("siteKey", reCaptchaService.getSiteKey());
            return "signup";
        }

        if (!reCaptchaService.verifyReCaptcha(reCaptchaResponse)) {
            model.addAttribute("reCaptchaError", "reCAPTCHA verification failed. Please try again.");
            model.addAttribute("siteKey", reCaptchaService.getSiteKey());
            return "signup";
        }

        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful. Please log in.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("siteKey", reCaptchaService.getSiteKey());
            return "signup";
        }
    }
}