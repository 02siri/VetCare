package au.edu.rmit.sept.webapp.services;

public interface ReCaptchaService {
    boolean verifyReCaptcha(String reCaptchaResponse);
    String getSiteKey();
}