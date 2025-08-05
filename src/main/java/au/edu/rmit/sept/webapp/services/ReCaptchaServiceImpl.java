package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Service
public class ReCaptchaServiceImpl implements ReCaptchaService {

    @Value("${google.recaptcha.key.site}")
    private String siteKey;

    @Value("${google.recaptcha.key.secret}")
    private String secretKey;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    @Override
    public boolean verifyReCaptcha(String reCaptchaResponse) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", secretKey);
        requestMap.add("response", reCaptchaResponse);

        Map<String, Object> responseMap = restTemplate.postForObject(GOOGLE_RECAPTCHA_VERIFY_URL, requestMap, Map.class);

        if(responseMap == null || responseMap.get("success") == null) {
            return false;
        }

        return (Boolean)responseMap.get("success");
    }

    @Override
    public String getSiteKey() {
        return siteKey;
    }
}