package com.example.SocialApi;

import lombok.Data;

import java.util.List;

@Data
public class SocialForm {
    private String link;
    private String message;
    private String imageAddress;
    private List<String> platforms;
}
