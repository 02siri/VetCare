package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.ReCaptchaService;
import au.edu.rmit.sept.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReCaptchaService reCaptchaService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("siteKey", reCaptchaService.getSiteKey());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, 
                               @RequestParam String password, 
                               @RequestParam(name = "g-recaptcha-response", required = false) String reCaptchaResponse,
                               HttpServletRequest request, 
                               Model model) {
        if (reCaptchaResponse == null || reCaptchaResponse.isEmpty()) {
            model.addAttribute("reCaptchaError", "Please verify that you are not a robot");
            model.addAttribute("siteKey", reCaptchaService.getSiteKey());
            return "login";
        }

        if (!reCaptchaService.verifyReCaptcha(reCaptchaResponse)) {
            model.addAttribute("reCaptchaError", "reCAPTCHA verification failed. Please try again.");
            model.addAttribute("siteKey", reCaptchaService.getSiteKey());
            return "login";
        }

        User user = userService.authenticate(username, password);
        if (user != null) {
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid username or password");
            model.addAttribute("siteKey", reCaptchaService.getSiteKey());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        request.getSession().invalidate();
        redirectAttributes.addFlashAttribute("message", "You have been successfully logged out.");
        return "redirect:/";
    }
}