package com.example.SocialApi;

import com.fasterxml.jackson.core.JsonProcessingException;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SocialApiController {

    @Value("${facebook.accessToken}")
    public String accessToken;

    @Value("${facebook.url}")
    public String urlFaceboook;

    @Value("${facebook.photos}")
    public String photosFacebook;

    @Value("${twitter.url}")
    public String urlTwitter;

    @Value("${pinterest.url}")
    public String urlPinterest;

    @Value("${pinterest.authorization}")
    public String authPinterest;

    @Value("${pinterest.cookie}")
    public String cookiePinterest;


    @GetMapping("/upload/")
    public String greetingForm(Model model) {
        model.addAttribute("socialForm", new SocialForm());
        return "upload";
    }

    @PostMapping(value = "/upload")
    public String upload(@ModelAttribute("socialForm") SocialForm socialForm, BindingResult bindingResult, Model model, @RequestParam List<String> platforms, @RequestParam String link, @RequestParam String message, @RequestParam(required = false) String imageAddress) throws UnirestException, JsonProcessingException {

        if (bindingResult.hasErrors()) {
            return "index";
        }

        model.addAttribute("socialForm",socialForm);


        if (socialForm.getPlatforms().contains("Facebook")) {
            Map<String, String> paramsFacebook = new HashMap<String, String>();
            Map<String, String> paramPhotosFacebook = new HashMap<String, String>();
            paramsFacebook.put("link", socialForm.getLink());
            paramsFacebook.put("message", socialForm.getMessage());
            paramsFacebook.put("access_token", accessToken);

            paramPhotosFacebook.put("message", socialForm.getMessage());
            paramPhotosFacebook.put("url", socialForm.getImageAddress());
            paramPhotosFacebook.put("access_token", accessToken);
            RestTemplate template = new RestTemplate();

            template.postForLocation(urlFaceboook, paramsFacebook, String.class);

            template.postForLocation(photosFacebook,paramPhotosFacebook, String.class);

        }

        if (socialForm.getPlatforms().contains("Twitter")) {
            Unirest.setTimeouts(0, 0);
            Map<String, String> mapTwitter = new HashMap<String, String>();
            mapTwitter.put("text", " Link: " + link + " " + message + " " + imageAddress);

            HttpResponse<String> response = Unirest.post(urlTwitter)
                    .header("Authorization", "OAuth oauth_consumer_key=\"iHCsSYJ98ZKBRKig7JosiQ7uu\",oauth_token=\"1501865044606271489-CDZQ4B6DD2gCGt0UgFkstgRL0a9A6w\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1648574308\",oauth_nonce=\"MSmOLHHJxa0\",oauth_version=\"1.0\",oauth_signature=\"ADtSkM1EjUGzk8H%2BEDDast5g80o%3D\"")
                    .header("Content-Type", "application/json")
                    .header("Cookie", "guest_id=v1%3A164751135559720933; guest_id_ads=v1%3A164751135559720933; guest_id_marketing=v1%3A164751135559720933; personalization_id=\"v1_amDlAQCyJ7j41JdWQovEKg==\"")
                    .body(mapTwitter)
                    .asString();

            System.out.println(response.getBody() + response.getStatus());
        }

        if (socialForm.getPlatforms().contains("Pinterest")) {
            Unirest.setTimeouts(0, 0);

            JSONObject objectPinterest = new JSONObject();
            HashMap<String, String> sourceType = new LinkedHashMap<>();
            sourceType.put("source_type", "image_url");
            sourceType.put("url", imageAddress);


            objectPinterest.put("link", link);
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





    @PostMapping("/facebook")
    public ResponseEntity<String> postStatusOnFacebook(@RequestParam String link, @RequestParam String message ) {


        //facebook
        Map<String,String> paramsFacebook = new HashMap<String,String>();
        paramsFacebook.put("link", link);
        paramsFacebook.put("message",message);
        paramsFacebook.put("access_token",accessToken);
        RestTemplate template = new RestTemplate();
        template.postForLocation(urlFaceboook, paramsFacebook, String.class);

        return ResponseEntity.status(HttpStatus.OK).body("Posted");
    }

    @PostMapping("/twitter")
    public ResponseEntity<String> postStatusOnTwitter() throws IOException, UnirestException {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("https://api.twitter.com/2/tweets")
                .header("Authorization", "OAuth oauth_consumer_key=\"iHCsSYJ98ZKBRKig7JosiQ7uu\",oauth_token=\"1501865044606271489-GxsrI62SaT1zjn07m057j48BE3wYAS\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1648120687\",oauth_nonce=\"cxbZRKVUvCT\",oauth_version=\"1.0\",oauth_signature=\"rHU3PZCOwviaiQwCcYIPmsomFH4%3D\"")
                .header("Content-Type", "application/json")
                .header("Cookie", "guest_id=v1%3A164751135559720933; guest_id_ads=v1%3A164751135559720933; guest_id_marketing=v1%3A164751135559720933; personalization_id=\"v1_amDlAQCyJ7j41JdWQovEKg==\"")
                .body("{\n    \"text\": \"https://developers.tiktok.com/doc/login-kit-web\"\n}")
                .asString();

        System.out.println(response.getBody() + response.getStatus() );
        return ResponseEntity.status(HttpStatus.OK).body("Posted");
    }

    @PostMapping("/pinterest")
    public ResponseEntity<String> postStatusOnPinterest() throws IOException, UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("https://api.pinterest.com/v5/pins/")
                .header("Authorization", "Bearer pina_AEASRCAWABIDSAIAGAAOWDGMKL63K7YBACGSPDC5NRO3NORFZIJPSYQUVX5ZLSEPOGD57UKBCX5RCTB76EORMZ2KRXXRCPQA")
                .header("Content-Type", "application/json")
                .header("Cookie", "_auth=0; _pinterest_sess=TWc9PSZ5d3JVVjFPNE9WSzBMVTVuVERaeVoxQ2xyMXN4OFpVZG5KZHhROGRNeVRTTGJUNEdzSUsyQ1BLRmhjRW5wUmVLZUp3bTNRQ25CQmRVcWlXOXY2K0V5c0d1UzJEazlnNm96WWk1TnE4QzZoRT0mNGZxU0FSdGN2YXlVblA0azFPLzg1UGJvbWw4PQ==; _ir=0")
                .body("{\r\n  \"link\": \"https://www.pinterest.com/\",\r\n  \"title\": \"demo\",\r\n  \"description\": \"demo api\",\r\n  \"alt_text\": null,\r\n  \"board_id\": \"930837885421332914\",\r\n  \"board_section_id\": null,\r\n  \"media_source\": {\r\n    \"source_type\": \"image_url\",\r\n    \"url\": \"https://i.pinimg.com/736x/4f/66/8d/4f668dba89d1bd9ba46ca51c6a745bb0.jpg\"\r\n  }\r\n}")
                .asString();

        System.out.println(response.getBody() + response.getStatus() );
        return ResponseEntity.status(HttpStatus.OK).body("Posted");
    }
}
