package com.email.emailGenerator.Services;
import com.email.emailGenerator.EmailRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class emailService {
    @Autowired
    private WebClient webClient;
    @Value("${gemini.api.url}")
    private String geminiURL;
    @Value("${gemini.api.key}")
    private String geminiKey;



    public String generateReply(EmailRequest email){
       String prompt =build(email);
        Map<String, Object> map = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        System.out.println("DEBUG REQUEST: " + map);
        String response = webClient.post()
                .uri(geminiURL +"?key="+ geminiKey)
                .header("Content-Type","application/json")
                .bodyValue(map)
                .retrieve()
                .bodyToMono(String.class).block();
        System.out.println("Raw Gemini Response: " + response);


        // Extract Response and Return
        return extractResponseContent(response);
    }

    private String extractResponseContent(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();//json to pojo and vice-versa
            JsonNode rootNode = mapper.readTree(response); //first node of json tree
            return rootNode.path("candidates")//curly k andar
                    .get(0)// [] k andar
                    .path("content")//jo curly braces k andar hai unke liye path jo square k andar unke liye .get
                    .path("parts")//curly
                    .get(0)//[]
                    .path("text")//.path matlab key k anadr ki value
                    .asText();
        } catch (Exception e) {
            return "Error processing request: " + e.getMessage();
        }
    }

    private String build(EmailRequest emailreq){
        StringBuilder sb=new StringBuilder();
        sb.append("generate a professional email reply for the following email content .pleasee do not give subject line");
        if(emailreq.getTone()!=null && !emailreq.getTone().isEmpty()){
            sb.append(" And Use a").append(emailreq.getTone()).append("tone .");
        }
        sb .append("\n and Original email is").append(emailreq.getContent());
        String prompt = sb.toString();
        System.out.println("DEBUG PROMPT: " + prompt);
        return sb.toString();
    }
}
