package com.example.SocialApi;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SocialApiApplication {

	public static void main(String[] args) throws UnirestException, IOException {
		SpringApplication.run(SocialApiApplication.class, args);

	}

}
