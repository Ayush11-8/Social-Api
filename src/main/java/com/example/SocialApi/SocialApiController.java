package com.example.SocialApi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
public class SocialApiController {

    @Value("${facebook.accessToken}")
    public String accessToken;

    @Value("${facebook.url}")
    public String urlFacebook;

    @Value("${facebook.photos}")
    public String photosFacebook;

    @Value("${twitter.url}")
    public String urlTwitter;

    @Value("${twitter.authorization}")
    public String authTwitter;

    @Value("${twitter.cookie}")
    public String cookieTwitter;

    @Value("${pinterest.url}")
    public String urlPinterest;

    @Value("${pinterest.authorization}")
    public String authPinterest;

    @Value("${pinterest.cookie}")
    public String cookiePinterest;


    @GetMapping("/upload/")
    public String socialForm(Model model) {
        model.addAttribute("socialForm", new SocialForm());
        return "upload";
    }

    @PostMapping(value = "/upload")
    public String upload(@ModelAttribute("socialForm") SocialForm socialForm, BindingResult bindingResult, Model model,
                         @RequestParam List<String> platforms, @RequestParam(required = false) String link, @RequestParam String message,
                         @RequestParam(required = false) String imageAddress, @RequestParam String tradeName) throws Exception {

        if (bindingResult.hasErrors()) {
            return "index";
        }
        model.addAttribute("socialForm",socialForm);

        JSONObject jsonObject = new JSONObject(
                "{ 'AAPL':" +
                "{'FB':'https://optioncircle-testing.s3.amazonaws.com/fb_user.html'," +
                "'Twitter':'https://api.urlbox.io/v1/9k1O8WSzpfeMMF3b/fd636336809368b89f9265e68d02488c63971a57/png?url=https%3A%2F%2Foptioncircle-testing.s3.amazonaws.com%2Ffb_user.html&retina=true&full_page=true&width=1200&force=false&height=645'," +
                "'Pinterest':'https://i.pinimg.com/236x/b0/0e/64/b00e64e25e8ca5c5efc17321d64d0e9d.jpg'}}");
        JSONObject socialUrl = jsonObject.getJSONObject(socialForm.getTradeName());


        if (socialForm.getPlatforms().contains("Reddit")) {
            String command = "python D:\\NMBR\\Social-Api\\reddit_python.py "+socialForm.getLink()+" "+socialForm.getMessage();
            Process pipInstall1 = Runtime.getRuntime().exec("pip install praw");
            Process pipInstall2 = Runtime.getRuntime().exec("pip install requests");
            Process pipInstall3 = Runtime.getRuntime().exec("pip install sys");
            Process runPythonFile = Runtime.getRuntime().exec(command);
        }


        if (socialForm.getPlatforms().contains("Facebook")) {
            Map<String, String> paramsFacebook = new HashMap<String, String>();
            Map<String, String> paramPhotosFacebook = new HashMap<String, String>();
            paramsFacebook.put("link",socialUrl.getString("FB"));
            paramsFacebook.put("message", socialForm.getMessage());
            paramsFacebook.put("access_token", accessToken);

            paramPhotosFacebook.put("message", socialForm.getMessage());
            paramPhotosFacebook.put("url", socialForm.getImageAddress());
            paramPhotosFacebook.put("access_token", accessToken);
            RestTemplate template = new RestTemplate();

            template.postForLocation(urlFacebook, paramsFacebook, String.class);
//            template.postForLocation(photosFacebook,paramPhotosFacebook, String.class);
        }

        if (socialForm.getPlatforms().contains("Twitter")) {
            Unirest.setTimeouts(0, 0);
            Map<String, String> mapTwitter = new HashMap<String, String>();
            mapTwitter.put("text",message + " " + " Link: " + socialUrl.getString("Twitter") + " ");

            HttpResponse<String> response = Unirest.post(urlTwitter)
                    .header("Authorization", authTwitter)
                    .header("Content-Type", "application/json")
                    .header("Cookie",cookieTwitter)
                    .body(mapTwitter)
                    .asString();

            System.out.println(response.getBody() + response.getStatus());
        }

        if (socialForm.getPlatforms().contains("Pinterest")) {
            Unirest.setTimeouts(0, 0);

            JSONObject objectPinterest = new JSONObject();
            HashMap<String, String> sourceType = new LinkedHashMap<>();
            sourceType.put("source_type", "image_url");
            sourceType.put("url", socialUrl.getString("Pinterest"));

            objectPinterest.put("link", socialForm.getLink());
            objectPinterest.put("description", message);
            objectPinterest.put("board_id", "930837885421332914");
            objectPinterest.put("media_source",sourceType);

            HttpResponse<String> response = Unirest.post(urlPinterest)
                    .header("Authorization", authPinterest)
                    .header("Content-Type", "application/json")
                    .header("Cookie", cookiePinterest)
                    .body(objectPinterest)
                    .asString();

            System.out.println(response.getBody() + response.getStatus());

        }
        return "index";
    }

}
