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
                    .header("Authorization", "OAuth oauth_consumer_key=\"iHCsSYJ98ZKBRKig7JosiQ7uu\",oauth_token=\"1501865044606271489-CDZQ4B6DD2gCGt0UgFkstgRL0a9A6w\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1648706733\",oauth_nonce=\"zzZo9SvxpDf\",oauth_version=\"1.0\",oauth_signature=\"2fYmjoxEp7TGzB8lEkb%2FatSa7i4%3D\"")
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

    @GetMapping("/tiktok/")
    public ResponseEntity<String> tiktokApi() throws IOException, UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://www.tiktok.com/auth/authorize/?client_key=awzppo5n91dlfny1&response_type=code&scope=user.info.basic,video.list,share.sound.create,video.upload&redirect_uri=https://example.com/&state=")
                .header("Cookie", "_abck=E6F66484A1C925432D33501C05661D80~-1~YAAQXm8/F3m7uZ1/AQAASbQH5AfOQunhWfhwrEJ3Tlpqnl4mkSNR5JdyBP+jKut5+QnNuopop/N8xEP7kQluSxNcrgZPCdb6qUI0O44zn088g8SpbwctrSIDLoBV0lD32ZvK4lnXXwkX2shucmK+Y1I8G1Vb6wZBjkn5TgtLAYUbbaw/x2o07zAUuTbYSBl0H7+kL0NofxLOS6HcnwwIn7eabF1hOky0tlhzjvxie//5UNNKNNHA9mpFwuVGwiVEM4br1mAux3NK51bK3lR7lLpmSgHsOtRH3hWvrIRck8ZPD728D5H/RGfzpO+EMJq4I0UIcY1VRppzF2pnOJWUmvETbBmDkzyfIPka2tqBxO6hgohmcU14sLqbSNflBpUY3AE1W0D23g==~-1~-1~-1; ak_bmsc=558DD9D4019ABC61A950B467B02A98D8~000000000000000000000000000000~YAAQXm8/F3u7uZ1/AQAAsrUH5A+JChJ+KWAOk9P7dXfF5xsGxozo/QvEcCf5DlGQdUC5N/jIuQMAlWZOgvWUs9ihJ5laYFx1cPNQ1Z+rhsQYpICq6Q7QVMtBW1M0fqRl5mUyKgztdPa0H/BsPgfR/04TOmv/Gy0EM1QBiXdRjNrZmwSOXMI13gSdny1g+3pD7kbOR0orG6xLulC8ju4T+qk8gwVowAtYof4/T3T+NnfydBAO2pB9dE8HLcjBGSEGJ5sfYGfinc953hk9iGKxgDWxtOJ8Kh2KyerW6Sk5OOY1PLmKz6fkKKHFtoaFV5HiWo01mMU7GwOBlgYe443jQoJSgWRdFca+OqYeCVferytyJT/jbPLRqcYCd2U=; bm_mi=1B673F25290FD87B6DD1A2FE4BD232E9~AekkJotJlI2+CQ1b4S0LrcrwQJNbhiZq9Xa+igco/iOj51s5RGBFSwRPnr5O+OQD2j82zOi2/wUKITSAnLcBrtsDIk+NmgZzFNifvmWOvR6N0QLnnYA0sa+VOthfRRwDACbLhKfQzk7WssIsKtiUnM+RUBKDwpc76JG8l8QpNGTj7eKbdq3k4vff8bIy4G2zIYbefMXQWarD7HMHhFeyg3m2fT66026nrzNVgLvy4hc=; bm_sv=E1D580DCCFEFA7B50DCCEE5774634DB0~UVaNTvegKaR9yW764DBS568ngaViznpnlW+wzPgL2SYX0w2dBOS/AOYGX7YMfH1+U3banGGC3jRI2c4mAdX1GRyHOHpCPzbQHliKQl++55jfCV6Oj5U0Yj/ci7qTaycTeVZQYco7RhFYprHOslS3z7qOQwU664A8XfN3xSeDHyU=; bm_sz=CCF77DFD1281CAEAC8F4D8E98C0D7A39~YAAQXm8/F3q7uZ1/AQAASbQH5A/uxeZlaNOD9OMKwKPTpsXkNNFp4zNZ3sHVRZl8nfBwW8zcwdIsxt6EGL3ZXI2DfT6vf1MrapSuDnsISMcu81K48SUEnLt1NT4i36qTzdE3r5X4k1l40RWzTG3aHOTradm9ek+v8Pbk/hL4e22fMcMtdlWAaWxaAOXxQ4PPWbPPi69shgDK1i9ciDls8Xd1TWa7xOEy7P2i53kIlsPECqn/SX3axMDS/yXSQUbyCawLm258x8NYNzVAlqveN/0DuGDxpUIvVcoyeZUb07oF20k=~3556664~3420726; msToken=MkM6wpRNLxjPQ7G6j0B56LgUINwQYdV3evq1xNDJ0IOAiAjXYJa_o25z202VsQ3Afu9TBI0zEomGRFFE8FqZDuxC; passport_csrf_token=b1139c8070caabff1ee774a008bf9ffa; passport_csrf_token_default=b1139c8070caabff1ee774a008bf9ffa; tt_csrf_token=sli5N7yZ-D17FXDv1S8wzDz1; ttwid=1%7CCGNHJAGWYB84Tx3aKit487RLPyqF_o-cdl0ccys1QO4%7C1648798184%7C344479aba157dc8f80b5e80823f3b4ad2801a95e708731b57c2ea0117fa21f2d")
                .asString();

        System.out.println(response.getBody() + response.getStatus() );
        return ResponseEntity.status(HttpStatus.OK).body("Posted");
    }

    @PostMapping("/tiktok")
    public ResponseEntity<String> tiktokAccessToken() {

        String urlTiktok = "https://open-api.tiktok.com/oauth/access_token";
        Map<String,String> paramsTiktok = new HashMap<String,String>();
        paramsTiktok.put("client_key", "awzppo5n91dlfny1");
        paramsTiktok.put("client_secret","66d02cb9b985ce1fdd2350c47b8c9d7e");
        paramsTiktok.put("code","");
        paramsTiktok.put("grant_type","authorization_code");
        RestTemplate template = new RestTemplate();
        template.postForLocation(urlTiktok, paramsTiktok, String.class);

        return ResponseEntity.status(HttpStatus.OK).body("got access");
    }



}
